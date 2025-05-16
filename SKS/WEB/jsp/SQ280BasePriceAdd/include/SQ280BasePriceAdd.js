var modeBasePriceAdd = 1;
var backBasePriceAdd = 1;

//========================================================================================
// �����\������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {
    //��ʐݒ�
    funInitScreen(ConBasePriceAddId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
    setTimeout(function(){ funGetInfo(1) }, 0);

    return true;

}

//========================================================================================
// �w�b�_�쐬����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�w�b�_���쐬����
//========================================================================================
function funCreateCostTbl(xmlData) {

    var key;
    var id;
    var mode;

    // �������[�h���擾����
    mode = parseInt(funXmlRead(xmlData, "mode", 0));

    for (i = 0; i < 31; i++) {

        if(i == 0) {

            for (j = 1; j < 31; j++) {

                key = "no_value" + funZeroPudding(j);
                id  = "txtHeader_" + j;

                // �J���}�ҏW
                document.getElementById(id).innerText = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i),0));
                // ���F
                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }

        } else {

            key = "nm_title";
            id  = "nm_title_" + i;

            document.getElementById(id).value = funXmlRead(xmlData, key, i);
            // ���F
            if (mode == 4) {
                document.getElementById(id).readOnly = true;
            }

            for (j = 1; j < 31; j++) {
                key = "no_value" + funZeroPudding(j);
                id  = "no_value_" + i + "_" + j;

                // �J���}�ҏW
                // �����_�ȉ�2�ʂ܂ŕ\���i3�ʈȉ��؂�̂āj
                document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));
                // ���F
                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }
        }
    }

    // ���F:�L���J�n���A���l����
    if (mode == 4) {
        document.getElementById("txtYuko").readOnly = true;
        document.getElementById("txtBiko").readOnly = true;
    }
}

//========================================================================================
// ���g�p�ʌv�Z����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F���g�p�ʂ��v�Z����
//========================================================================================
function funCalc() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var key;
    var headerId;
    var calcRsltId;
    var val;

    //  ���g�p�� < 1 ��������
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }

    for (i = 0; i < 30; i++) {
        headerId  = "txtHeader_" + (i + 1);
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + (i + 1);
            // �����_�ȉ��؂�̂�
            document.getElementById(calcRsltId).value = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
        }
    }
}


//========================================================================================
// �A�b�v���v�Z����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�A�b�v�����P�����v�Z����
//========================================================================================
function funUpRituCalc() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var key;
    var valueId;
    var val;
    var total;
    var row;
    var col;

    if(frm.txtUpRitu.value == "" || parseInt(frm.txtUpRitu.value) == 0) {
        return;
    }

    for (row = 0; row < 30; row++) {
        for (col = 0; col < 30; col++) {
            valueId  = "no_value_" + (row + 1) + "_"  + (col + 1);
            val = document.getElementById(valueId).value.replace(/,/g,"");;
            if(val != ""){
                total = val * (frm.txtUpRitu.value / 100);

                // �����_2�ʂ܂ŕ\���i3�ʈȉ��؂�̂āj
                document.getElementById(valueId).value =
                	funAddComma(
                			funNumFormatChange(
                					funRound(parseFloat(val) + parseFloat(total), 3).toString()
                					,2
                			)
                	);
                funCheckChange();
            }
        }
    }
}

////========================================================================================
//// �N���A�{�^����������
//// �쐬�ҁFBRC Koizumi
//// �쐬���F2016/09/06
//// ����  �F�Ȃ�
//// �T�v  �F��ʂ�����������
////========================================================================================
//function funClear() {
//
//    var frm = document.frm00;    //̫�тւ̎Q��
//
//    //��ʂ̏�����
//    frm.reset();
//    frm.ddlMakerName.selectedIndex = 0;
//    frm.ddlHouzai.selectedIndex = 0;
//
//    //�ꗗ�̸ر
//    funClearList();
//
//    //��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
//    xmlFGEN3000O.load(xmlFGEN3000);
//    xmlFGEN3010O.load(xmlFGEN3010);
//
//    //�����ޯ���̍Đݒ�
//    funCreateComboBox(frm.ddlMakerName, xmlFGEN3000O, 1);
//    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
//
//    return true;
//
//}
//
////========================================================================================
//// �ꗗ�N���A����
//// �쐬�ҁFBRC Koizumi
//// �쐬���F2016/09/06
//// ����  �F�Ȃ�
//// �T�v  �F�ꗗ�̏�������������
////========================================================================================
//function funClearList() {
//
//    //�ꗗ�̸ر
//    xmlFGEN3520O.src = "";
//    tblList.style.display = "none";
//
//}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3520";

    // ��ޏ����擾���Ȃ�
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3520");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3520I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3520O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3520, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    //�ް�����������
    if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
    	// �ꗗ�쐬
    	funCreateCostTbl(xmlResAry[3]);

        funDefaultIndex(frm.ddlMakerName, xmlResAry[3], 1);
        // ��ރR���{�{�b�N�X�̎擾�E�ݒ�
        funChangeMaker();
        funDefaultIndex(frm.ddlHouzai, xmlResAry[3], 2);

        // �������[�h���擾����
        modeBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

        frm.txtHouzai.value = funXmlRead(xmlResAry[3], "name_hansu", 0);

        if(modeBasePriceAdd == 3) {
        	// �R�s�[���́A�Ő����J�E���g�A�b�v
        	frm.txtHansu.value = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);
        } else {
        frm.txtHansu.value = funXmlRead(xmlResAry[3], "no_hansu", 0);
        }

        frm.txtYuko.value  = funXmlRead(xmlResAry[3], "dt_yuko", 0);
        frm.txtBiko.value  = funXmlRead(xmlResAry[3], "biko", 0);

//        frm.txtBiko_kojo.value  = funXmlRead(xmlResAry[3], "biko_kojo", 0);

        // �m�F�`�F�b�N�{�b�N�X�Ɗm�F�҂�ݒ肷��
        if((funXmlRead(xmlResAry[3], "id_kakunin", 0) != "") && modeBasePriceAdd != 3) {
            frm.chkKakunin.checked = true;
            document.getElementById("lblKakunin").innerHTML = funXmlRead(xmlResAry[3], "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "�|";
        }

        // ���F�`�F�b�N�{�b�N�X�Ə��F�҂�ݒ肷��
        if((funXmlRead(xmlResAry[3], "id_shonin", 0) != "") && modeBasePriceAdd != 3) {
            frm.chkShonin.checked = true;
            document.getElementById("lblShonin").innerHTML = funXmlRead(xmlResAry[3], "nm_shonin", 0);
        } else {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "�|";
        }

        // ���g�p�`�F�b�N��ݒ肷��
        if(parseInt(funXmlRead(xmlResAry[3], "flg_mishiyo", 0)) == 1) {
            frm.chkMishiyo.checked = true;
        } else {
            frm.chkMishiyo.checked = false;
        }
//
//        // �������[�h���擾����
//        modeBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

        // �V�K�o�^
        // ���j���[���̐V�K�o�^�E�ꗗ���̃R�s�[
        if(modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
            frm.chkShonin.disabled = true;
            // �폜�{�^����\��
            frm.btnDataDelete.style.display = "none";
            frm.chkMishiyo.disabled = true;		// ���g�p�`�F�b�N

        // �X�V
        } else if(modeBasePriceAdd == 2) {
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.txtHansu.readOnly = true;
            frm.chkShonin.disabled = true;
            frm.txtHouzai.readOnly = true;			// 	��ޖ�
            frm.chkMishiyo.disabled = false;
        } else if(modeBasePriceAdd == 4) {
            frm.btnDataEdit.value = "���F";
            frm.chkKakunin.disabled = true;
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.btnCalc.disabled = true;
            frm.btnUpRituCalc.disabled = true;
            frm.txtShiyoRyo.readOnly = true;
            frm.txtUpRitu.readOnly = true
            frm.txtHansu.readOnly = true;
            frm.txtYuko.readOnly  = true;
            frm.txtBiko.readOnly  = true;
            frm.txtHouzai.readOnly = true;			// 	��ޖ�
            frm.chkMishiyo.disabled = true;		// ���g�p�`�F�b�N
        }

        // �߂����擾����
        backBasePriceAdd = parseInt(funXmlRead(xmlResAry[3], "back", 0));

        //�\��
        tblHeader.style.display = "block";
        tblList.style.display = "block";

    } else {
        //��\��
        tblHeader.style.display = "none";
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    //��̫�Ă̺����ޯ���ݒ�l��ޔ�
    xmlFGEN3000.load(xmlResAry[2]);

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �o�^�i���F�j�{�^����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@mode �F�����敪
//           1�F�o�^�A4�F���F�A5�F��ޓo�^
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {
    if (modeBasePriceAdd == 4) {
        // ���F�{�^����������
        funShonin(mode);
    } else if (modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
    	var frm = document.frm00;    //̫�тւ̎Q��
    	// �V�K or �R�s�[�{�^����������
    	// �ł̕�ޖ����I�� ���� ��ޖ��ɓ��͗L�̏ꍇ��ޖ��̓o�^�������s��
    	var hanHouzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    	var baseHouzai = frm.txtHouzai.value;

    	if (hanHouzai == "" && baseHouzai != "") {
    		// ��ޖ��̓o�^�`�F�b�N���s��
    		if(!fucHouzaiToroku()){
    			return false;
    		}
    	}
    	funEdit(modeBasePriceAdd);
    } else {
        // �o�^�{�^����������
        funEdit(mode);
    }

    return true;

}

//========================================================================================
// ��ޖ��̓o�^�`�F�b�N
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// �T�v  �F��ޖ��̓o�^�`�F�b�N���s��
//========================================================================================
function fucHouzaiToroku() {
	var frm = document.frm00;    //̫�тւ̎Q��
	var houzai =  funGetXmldata(xmlFGEN3010O, "nm_2nd_literal", frm.txtHouzai.value, "cd_2nd_literal");

	if (houzai != "") {
		funErrorMsgBox(E000053);
		return false;
	}

  return true;

}

//========================================================================================
// �o�^�{�^����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�R�s�[
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�������s��
//========================================================================================
function funEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3530";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3530");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3530I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3530O);
    var dspMsg;

	    //�m�Fү���ނ̕\��
	    if (funConfMsgBox(I000002) != ConBtnYes) {
	        return false;
	    }

    //XML�̏�����
    setTimeout("xmlFGEN3530I.src = '../../model/FGEN3530I.xml';", ConTimer);


    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3530, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //����
        funInfoMsgBox(dspMsg);

        // �x�[�X�P���ꗗ���X�V����
        // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        switch(modeBasePriceAdd){
        case 1: // �V�K�Ȃ�X�V��
            modeBasePriceAdd = 2;
            backBasePriceAdd = 1; // ���j���[��ʂɖ߂�
            break;
        case 3: // �R�s�[�Ȃ�X�V
            modeBasePriceAdd = 2;
            backBasePriceAdd = 2; // �x�[�X�P���ꗗ��ʂɖ߂�
            break;
        }

        // �x�[�X�P���o�^�E���F��ʋN�����ʒm
        funBasePriceAddTuti(1);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// ���F�{�^����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F���F
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funShonin(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3540";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3540");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3540I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3540O);

    // ���F�҃`�F�b�N�{�b�N�X
    if (!frm.chkShonin.checked) {
    	funErrorMsgBox(E000060);
    	return false;
    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(I000019) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // ���F���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3540, xmlReqAry, xmlResAry, 1) == false) {
        frm.chkShonin.checked = false;
        funClearRunMessage();
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //����
        funInfoMsgBox(dspMsg);

        // �x�[�X�P���ꗗ���X�V����
        // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        switch(modeBasePriceAdd){
        case 1: // �V�K�Ȃ�X�V��
            modeBasePriceAdd = 2;
            backBasePriceAdd = 1; // ���j���[��ʂɖ߂�
            break;
        case 3: // �R�s�[�Ȃ�X�V
            modeBasePriceAdd = 2;
            backBasePriceAdd = 2; // �x�[�X�P���ꗗ��ʂɖ߂�
            break;
        }

        // �x�[�X�P���o�^�E���F��ʋN�����ʒm
        funBasePriceAddTuti(1);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// �폜�{�^����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�\���f�[�^�̍폜�������s��
//========================================================================================
function funDataDelete() {

     var frm = document.frm00;    //̫�тւ̎Q��
     var XmlId = "RGEN3570";
     var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3570");
     var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3570I);
     var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3570O);

     //�폜�m�F
     if (funConfMsgBox(I000020) != ConBtnYes) {
         return false;
     }

     //������ү���ޕ\��
     funShowRunMessage();

     //������XMĻ�قɐݒ�
     if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
         funClearRunMessage();
         return false;
     }

     //�폜���������s����
     if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3570, xmlReqAry, xmlResAry, 1) == false) {
         return false;
     }

     //������ү���ޔ�\��
     funClearRunMessage();

     if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

         //����ү���ނ̕\��
         funInfoMsgBox("�x�[�X�P����" + I000007);

         // �x�[�X�P���ꗗ���X�V����
         // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
         try{
             window.opener.document.frm00.btnSearch.click();
         }catch (e) {

         }

         // �v�C�� �� CostTbl BasePrice
         // �x�[�X�P���ꗗ��ʂɖ߂�modeBasePriceAdd=2(�o�^) �̂�
         modeBasePriceAdd = 2;
         backBasePriceAdd = 2; // �x�[�X�P���ꗗ��ʂɖ߂�

         // �x�[�X�P���ꗗ��ʂɖ߂�
         funNext();

     }

     return true;
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {

        // ��ʏ����\��
        if (XmlId.toString() == "RGEN3520") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3520
                    break;
            }

        // ��ރh���b�v�_�E��
        } else if (XmlId.toString() == "RGEN3010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3010
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    break;
            }

        // �o�^�{�^������
        } else if (XmlId.toString() == "RGEN3530") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3530

                	// �x�[�X�P�����X�g�}�X�^�A�x�[�X�P���}�X�^
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "dt_yuko", frm.txtYuko.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "biko", frm.txtBiko.value, 0);

//                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "biko_kojo", frm.txtBiko_kojo.value, 0);

                	// �ł̕�ޖ����I�� ���� ��ޖ��ɓ��͗L�̏ꍇ��ޖ��̓o�^�������s��
                	var hanHouzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].innerText;
                	var baseHouzai = frm.txtHouzai.value;

                	if (hanHouzai == "" && baseHouzai != "") {
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", baseHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", baseHouzai, 0);
                	} else if (hanHouzai != "" && baseHouzai != ""){
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", hanHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", baseHouzai, 0);
                	}
                	else {
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_hansu", hanHouzai, 0);
                		funXmlWrite_Tbl(reqAry[i], "base_price_list", "name_houzai", hanHouzai, 0);
                	}
                	// ���F�҃`�F�b�N�{�b�N�X
                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "", 0);
                    }
                    // �o�^�҃`�F�b�N�{�b�N�X
                    if (frm.chkKakunin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_kakunin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_kakunin", "", 0);
                    }
                    // ���g�p�`�F�b�N�{�b�N�X
                    if (frm.chkMishiyo.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_mishiyo", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_mishiyo", "0", 0);
                    }

                    for (j = 0; j < 31; j++) {

                        if (j <= 0) {
                            funXmlWrite_Tbl(reqAry[i], "base_price", "nm_title", "", j);
                            funXmlWrite_Tbl(reqAry[i], "base_price", "no_row", "0", j);
                            for (k = 0; k < 30; k++) {
                                funXmlWrite_Tbl(reqAry[i], "base_price", "no_value" + funZeroPudding(k + 1), document.getElementById("txtHeader_" + (k + 1)).value.replace(/,/g,""), j);
                            }
                        } else {

                            funXmlWrite_Tbl(reqAry[i], "base_price", "nm_title", document.getElementById("nm_title_" + j).value, j);
                            funXmlWrite_Tbl(reqAry[i], "base_price", "no_row", j, j);
                            for (k = 0; k < 30; k++) {
                                funXmlWrite_Tbl(reqAry[i], "base_price", "no_value" + funZeroPudding(k + 1), document.getElementById("no_value_" + j + "_" + (k + 1)).value.replace(/,/g,""), j);
                            }
                        }
                        funAddRecNode_Tbl(reqAry[i], "FGEN3530", "base_price");
                    }

	            	// ��ޖ��o�^�p
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "cd_category", "maker_name", 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "cd_literal", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "nm_literal", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].innerText, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "nm_2nd_literal", frm.txtHouzai.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_literal", "kbn_shori", "1", 0);

                    break;
            }

        // ���F�{�^������
        } else if (XmlId.toString() == "RGEN3540") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3540
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "base_price_list", "no_hansu", frm.txtHansu.value, 0);

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "base_price_list", "chk_shonin", "", 0);
                    }

                    break;
            }

        //��ޖ��v���_�E���A�Ő��ύX��
        } else if (XmlId.toString() == "RGEN3560"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3560
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    funXmlWrite(reqAry[i], "name_hanhouzai", frm.txtHouzai.value);
                    break;
            }

        //�폜�{�^������
            // ���F�ς݃f�[�^���K�b�c���폜���Ăn�j�H
        } else if (XmlId.toString() == "RGEN3570"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3570
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }

        } else if (XmlId.toString() == "RGEN2160"){
        	// ���[�J�[�R�[�h
            var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            // �ł̕�ރR�[�h
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            // �Ő�
            var no_hansu  = frm.txtHansu.value;
            // ���g�p
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = 2;
            }

            // �C��
            var put_code = modeBasePriceAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backBasePriceAdd + "-" + mishiyo;

            // �����R�[�h�u���y���[�h:::���[�J�[�R�[�h:::�ł̕�ރR�[�h:::�Ő�:::�o�b�N���[�h�z
            var put_code = put_code.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
        }
    }

    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //�����ޯ���̸ر
    funClearSelect(obj, 2);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //����Ͻ�
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 2:    //����Ͻ��i��2�J�e�S���j
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
        	// ��ޖ� ���� �V�K�A�R�s�[�̏ꍇ�͖��g�p������
        	if(atbCd == "cd_2nd_literal"){
        		if(modeBasePriceAdd == 1 || modeBasePriceAdd == 3) {
        			if(parseInt(funXmlRead(xmlData, "flg_mishiyo", i)) != 1){
	        			objNewOption = document.createElement("option");
	                    obj.options.add(objNewOption);
	                    objNewOption.innerText = funXmlRead(xmlData, atbName, i);
	                    objNewOption.value = funXmlRead(xmlData, atbCd, i);
        			}
        		} else {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
        	} else {
        		objNewOption = document.createElement("option");
                obj.options.add(objNewOption);
                objNewOption.innerText = funXmlRead(xmlData, atbName, i);
                objNewOption.value = funXmlRead(xmlData, atbCd, i);
        	}
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    //
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    // ���[�J�[��
                if (obj.options[i].value == funXmlRead(xmlData, "cd_maker", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    // ���
                if (obj.options[i].value == funXmlRead(xmlData, "cd_houzai", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext() {

    var wUrl;

    //�J�ڐ攻��

    if (backBasePriceAdd == 1) {
        //��ʂ����
        close(self);
        return true;

//        // �����ޒ��B�����j���[
//        wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//        //������ү���ޕ\��
//        funShowRunMessage();
//
//        //�J��
//        //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
//        setTimeout(function(){ funUrlConnect(wUrl, ConConectPost, document.frm00) }, 0);

    } else {

        window.close();


    }

    return true;
}

//========================================================================================
// �x�[�X�P���o�^�E���F��ʋN�����ʒm
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�I�������x�[�X�P�����Z�b�V�����֕ۑ�����
//========================================================================================
function funBasePriceAddTuti(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;
}

//========================================================================================
// ���b�g�����l�`�F�b�N
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F���b�g���ɒl�����͂��ꂽ�ꍇ�A���͒l�ɂ����
//        �m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A����
//========================================================================================
function funNumOnly() {

	// Ctrl + A or C or V or X ���� �X�V�̏ꍇ
	// �m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A
    if (event.ctrlKey) {
        switch(event.keyCode) {
            case 65:  //Keyboard.A
            case 67:  //Keyboard.C
            case 86:  //Keyboard.V
            case 88:  //Keyboard.X
            funCheckChange();
            return true;
        }
    }

	// ���A�� �e���L�[���� ���� �X�V�̏ꍇ
	// �m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A
    switch(event.keyCode) {
        case 37:   // <--
        case 39:   // -->
        case 96:   // 0
        case 97:   // 1
        case 98:   // 2
        case 99:   // 3
        case 100:  // 4
        case 101:  // 5
        case 102:  // 6
        case 103:  // 7
        case 104:  // 8
        case 105:  // 9
        funCheckChange();
        return true;
    }

    m = String.fromCharCode(event.keyCode);

    // ���l�ȊO�̏ꍇ�A�������Ȃ�
    if("0123456789\b\t".indexOf(m, 0) < 0) return false;

    // �X�V�̏ꍇ�A�m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A
    funCheckChange();

    return true;
}


//========================================================================================
// �l�̌ܓ�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�A�b�v���v�Z �����_2�ʂ܂ŕ\���i3�ʈȉ��؂�̂āj
//========================================================================================
function funRound(val, precision)
{
     //�����_���ړ�������ׂ̐���10�ׂ̂���ŋ��߂�
     //��) �����_�ȉ�2���̏ꍇ�� 100 ��������K�v������
     digit = Math.pow(10, precision);

     // �l�̌ܓ������������� digit ���|���ď����_���ړ�
     val = val * digit;

     // round���g���Ďl�̌ܓ�
     val = Math.round(val);

     // �ړ������������_�� digit �Ŋ��邱�Ƃł��Ƃɖ߂�
     val = val / digit;

     return val;
}

//========================================================================================
// �m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�e���ڍX�V���A�`�F�b�N�{�b�N�X���N���A����
//========================================================================================
function funCheckChange() {
    // �X�V�̏ꍇ�A�m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A
    var frm = document.frm00;    //̫�тւ̎Q��

    if(modeBasePriceAdd == 2){
    	// ���F�`�F�b�N�{�b�N�X
        if (frm.chkShonin.checked) {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "�|";
        }
        // �m�F�`�F�b�N�{�b�N�X
        if (frm.chkKakunin.checked) {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "�|";
        }
    }

    return false;
}

//========================================================================================
// �\�����擾
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F��ށA�Ő��ύX���A�\�������Ď擾����
//========================================================================================
function funExistData() {

    var frm = document.frm00;    //̫�тւ̎Q��

    var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
    var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    var no_hansu  = frm.txtHansu.value;
    if(modeBasePriceAdd == 1 && cd_maker != "" && cd_houzai != "" && no_hansu != ""){

        var XmlId = "RGEN3560";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3560");
        var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3560I);
        var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3560O);


        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage();
            return false;
        }

        //����ݏ��A�����ޯ���̏����擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3560, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }
    }

    return;
}

//========================================================================================
// ���̓`�F�b�N
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F�m�F�`�F�b�N�@�`�F�b�N���ɓ��͒l���`�F�b�N����
//========================================================================================
function funCheckData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var col;
    var row;
    var lotValue;
    var titleValue;
    var costValue;
    var lotCount;
    var costCount;

    var rowCount = 0;
    var colCount = 0;

    lotCount = 0;
    headerCount = 0;

    if(frm.chkKakunin.checked == false) {
        return;
    }

    for (col = 0; col < 30; col++) {

        lotValue = document.getElementById("txtHeader_" + (col + 1)).value;

        if (lotValue != "") {
            colCount++;
        }
    }

    // ���b�g���ɒl�����邩�`�F�b�N����
    if (colCount <= 0) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("���b�g��" + E000050);
        return;
    }

    for (col = 0; col < 30; col++) {

        lotValue = document.getElementById("txtHeader_" + (col + 1)).value;

        if (lotValue != "") {
            lotCount++;
        } else {
            break;
        }
    }

    // �A�������`�F�b�N����
    if (colCount != lotCount) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("���b�g��" + E000050);
        return;
    }

    // �F����ǉ�
    colCount++;

    costCount = 0;

    for (row = 0; row < 30; row++) {

        costCount = 0;

        // �F��
        titleValue = document.getElementById("nm_title_" + (row + 1)).value;

        if (titleValue != "") {
            costCount++;
        }

        for (col = 0; col < 30; col++) {

            costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

            if (costValue != "") {
                costCount++;
            }
        }

        if (costCount > 0) {
            rowCount++;
        }
    }

    if (colCount <= 0 || rowCount <= 0) {
        frm.chkKakunin.checked = false;
        funErrorMsgBox("�F���܂��̓R�X�g" + E000050);
        return;
    }

    for (row = 0; row < rowCount; row++) {

        costCount = 0;

        // �F��
        titleValue = document.getElementById("nm_title_" + (row + 1)).value;

        if (titleValue != "") {
            costCount++;
        }

        for (col = 0; col < colCount; col++) {

        	try{
        		costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

                if (costValue != "") {
                    costCount++;
                }
        	}catch(e){

        	}
        }

        if (costCount != colCount) {
            frm.chkKakunin.checked = false;
            funErrorMsgBox("�F���܂��̓R�X�g" + E000050);
            return;
        }
    }
}

//========================================================================================
// ���lFormat�ϊ�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �Fval:�ϊ��l
//         keta:�������i�؂�̂Č��j
// �߂�l�@�ϊ��l�i�����l�łȂ��ꍇ�͋󕶎��ԋp�j
// �T�v  �F
//========================================================================================
function funNumFormatChange(val , keta) {

	// �J���}�폜
	val = val.replace(/,/g,"");

	// ���l�`�F�b�N
    if ( val == parseFloat(val)){

    	if(keta == 0){
    		return Math.floor(val);
    	}

    	// ���l�̏ꍇ�ɁA�w�菬���؂�̂�
    	return funShosuKirisute(val,keta);
    } else {
    	// ���l�łȂ�
    	return "";
    }
}

//========================================================================================
// �J�[�\���ړ������F�����L�[����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// �T�v  �F���s�i��s�j�Ƀt�H�[�J�X���ڂ�
//========================================================================================
function funCellIdou(code) {

	// �����L�[�̎��̂݃J�[�\���ړ�����
	if ((code != 38) && (code != 40)) {
		return;
	}

	// next�G�������gID
	var nextId = "";
	// �A�N�e�B�u�G�������gID
	var currentId = document.activeElement.id;

	// �s�Ɨ񖼂𕪉�
	var tmpId = currentId.split("_");

	var row = "";		// �s�ԍ�
	var iti = 0;		// �s�ԍ��ʒu

	if (tmpId[0] == "no") {
		// �P���Z���̏ꍇ�F�s�ԍ�
		iti = tmpId.length - 2;
		row = tmpId[iti];
	} else {
		// ���O�Z���̏ꍇ�F�s�ԍ�
		iti = tmpId.length - 1;
		row = tmpId[iti];
	}


	for(i=0; i<tmpId.length; i++) {
		// �s�ԍ��{�P
		if (i == iti) {
			// ���L�[
			if (code == 40) {
				if (row < 30) row++;

			// ���L�[
			} else {
				if (row > 1) row--;
			}
			nextId += row;
			if (i == (tmpId.length - 2)) {
				// �P���Z���̏ꍇ�Acol�ԍ���ǉ�
				nextId +=  "_" + tmpId[tmpId.length - 1];
			}
			break;

		} else {
			nextId += tmpId[i] + "_";
		}
	}
	// ���s�Ƀt�H�[�J�X���ڂ�
	document.getElementById(nextId).focus();
	//�N���b�N���đI���s�̔w�i�F��ύX
	document.getElementById(nextId).click();
}

//========================================================================================
// ���[�J�[���R���{�{�b�N�X�A������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/06
// ����  �F�Ȃ�
// �T�v  �F���[�J�[�ɕR�t����ރR���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeMaker() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3010";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3010");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3010I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3010O);

    //��޺����ޯ���̸ر
    funClearSelect(frm.ddlHouzai, 2);

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3010, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//��޺����ޯ���̍쐬
	funCreateComboBox(frm.ddlHouzai, xmlResAry[2], 2);
	xmlFGEN3010.load(xmlResAry[2]);

	return true;

}


//========================================================================================
//XML�f�[�^��茟���s�̃R�[�h�������O �ϊ�
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/10/04
//����  �F�@xmlData  �FXML�f�[�^
//     �F�Akomoku   �F�������ږ�
//     �F�Btext     �F�����l
//     �F�Cret      �F�擾���ږ�
//return�F�R�[�h�l���͖��O
//�T�v  �F��v����R�[�h���͖��O���擾
//========================================================================================
function funGetXmldata(xmlData, komoku, text, ret) {

	//�����擾
	var reccnt = funGetLength(xmlData);
	// �߂�l
	var retStr = "";
	for (var i = 0; i < reccnt; i++) {
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (funXmlRead(xmlData, komoku, i) == text) {
			//�w�荀�ږ��̒l��Ԃ�
			retStr = funXmlRead(xmlData, ret, i);
			break;
		}
	}

return retStr;
}
