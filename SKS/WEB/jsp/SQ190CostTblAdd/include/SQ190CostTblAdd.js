var modeCostTblAdd = 1;
var backCostTblAdd = 1;
var costHansu = 0;

//========================================================================================
// �����\������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConCostTblAddId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
    setTimeout(function(){ funGetInfo(1) }, 0);

    return true;

}

//========================================================================================
// �w�b�_�쐬����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
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

                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                // �J���}�ҏW
                document.getElementById(id).innerText = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i),0));
                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }

        } else {

            key = "nm_title";
            id  = "nm_title_" + i;

            document.getElementById(id).value = funXmlRead(xmlData, key, i);

            if (mode == 4) {
                document.getElementById(id).readOnly = true;
            }

            for (j = 1; j < 31; j++) {
                key = "no_value" + funZeroPudding(j);
                id  = "no_value_" + i + "_" + j;

                // �J���}�ҏW
                // �����_�ȉ�2�ʂ܂ŕ\���i3�ʈȉ��؂�̂āj
                document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));

                if (mode == 4) {
                    document.getElementById(id).readOnly = true;
                }
            }
        }
    }

    // �L���J�n���A���l����
    if (mode == 4) {
        document.getElementById("txtYuko").readOnly = true;
        document.getElementById("txtBiko").readOnly = true;
    }
}

//========================================================================================
// ���g�p�ʌv�Z����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F���g�p�ʂ��v�Z����
//========================================================================================
function funCalc() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var key;
    var headerId;
    var calcRsltId;
    var val;

    // �yQP@30297�zNo.16 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start
    //  ���g�p�� < 1 ��������
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }
    // �yQP@30297�zMo.16 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod end

    for (i = 0; i < 30; i++) {
        headerId  = "txtHeader_" + (i + 1);
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + (i + 1);
            // �yQP@30297�zNo.20 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start
            // �����_�ȉ��؂�̂�
            document.getElementById(calcRsltId).value = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
            // �yQP@30297�zNo.20 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod end
        }
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
    }
}


//========================================================================================
// �A�b�v���v�Z����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�A�b�v�����P�����v�Z����
//========================================================================================
function funUpRituCalc() {

    var frm = document.frm00;    //̫�тւ̎Q��
	if(frm.txtUpRitu.value == "" || parseInt(frm.txtUpRitu.value) == 0) {
        return;
    }

    var key;
    var valueId;
    var val;
    var total;
    var row;
    var col;
    var baseKey;
    var baseVal;

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

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlFGEN3030O.src = "";
    tblList.style.display = "none";

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3030";

    // ��ޏ����擾���Ȃ�
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3030");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3030I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3030O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    //�ް�����������
    if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
        // �������[�h���擾����
        modeCostTblAdd = parseInt(funXmlRead(xmlResAry[3], "mode", 0));

    	// �ꗗ�쐬
    	funCreateCostTbl(xmlResAry[3]);

        funDefaultIndex(frm.ddlMakerName, xmlResAry[3], 1);
        // ��ރR���{�{�b�N�X�̎擾�E�ݒ�
        funChangeMaker();
        funDefaultIndex(frm.ddlHouzai, xmlResAry[3], 2);

//�yKPX@1602367�zadd start
        // �Ő��R���{�{�b�N�X�̎擾�E�ݒ�
        funChangeBaseHouzai();
        funDefaultIndex(frm.ddlBaseHansu, xmlResAry[3], 3);

        frm.costHouzai.value = funXmlRead(xmlResAry[3], "name_hansu", 0);	// �R�X�g��ޖ�
//�yKPX@1602367�zadd end

        frm.txtHansu.value = funXmlRead(xmlResAry[3], "no_hansu", 0);
        frm.txtYuko.value  = funXmlRead(xmlResAry[3], "dt_yuko", 0);
        frm.txtBiko.value  = funXmlRead(xmlResAry[3], "biko", 0);

        frm.txtBiko_kojo.value  = funXmlRead(xmlResAry[3], "biko_kojo", 0);

        // �o�^�i�m�F�j�`�F�b�N�{�b�N�X�Ɗm�F�҂�ݒ肷��
        if((funXmlRead(xmlResAry[3], "id_kakunin", 0) != "") && modeCostTblAdd != 1) {
            frm.chkKakunin.checked = true;
            document.getElementById("lblKakunin").innerHTML = funXmlRead(xmlResAry[3], "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            document.getElementById("lblKakunin").innerHTML = "�|";
        }

        // ���F�`�F�b�N�{�b�N�X�Ə��F�҂�ݒ肷��
        if(funXmlRead(xmlResAry[3], "id_shonin", 0) != "") {
            frm.chkShonin.checked = true;
            document.getElementById("lblShonin").innerHTML = funXmlRead(xmlResAry[3], "nm_shonin", 0);
        } else {
            frm.chkShonin.checked = false;
            document.getElementById("lblShonin").innerHTML = "�|";
        }
        // ���g�p
        if(parseInt(funXmlRead(xmlResAry[3], "flg_mishiyo", 0)) == 1) {
            frm.chkMishiyo.checked = true;
        } else {
            frm.chkMishiyo.checked = false;
        }

        // �V�K�o�^
        //�yKPX@1602367�zmod
        // ���j���[���̐V�K�o�^�E�ꗗ���̃R�s�[
        if(modeCostTblAdd == 1) {
//        if(modeCostTblAdd == 1 || modeCostTblAdd == 3) {
            frm.chkShonin.disabled = true;
            // �폜�{�^����\��
            frm.btnDataDelete.style.display = "none";
            frm.chkMishiyo.disabled = true;		// ���g�p�`�F�b�N
        // �X�V
        } else if(modeCostTblAdd == 2) {
            frm.ddlMakerName.disabled = true;
            frm.ddlHouzai.disabled = true;
            frm.txtHansu.readOnly = true;
            frm.chkShonin.disabled = true;
            frm.ddlBaseHansu.disabled = true;	// �Ńv���_�E�� �yKPX@1602367�zmod
            frm.chkMishiyo.disabled = false;
        } else if(modeCostTblAdd == 4) {
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
            frm.ddlBaseHansu.disabled = true;	// �Ńv���_�E�� �yKPX@1602367�zmod
            frm.chkMishiyo.disabled = true;		// ���g�p�`�F�b�N
        }

        // �߂����擾����
        backCostTblAdd = parseInt(funXmlRead(xmlResAry[3], "back", 0));

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
// �o�^�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F���F
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    if (modeCostTblAdd == 4) {

        // ���F�{�^����������
        funShonin(mode);

    } else {

        // �o�^�{�^����������
        funEdit(mode);
    }

    return true;

}

//========================================================================================
// �o�^�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F���F
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3050O);
    var dspMsg;
    // �V�K�o�^���A�Ő���"max+1"�o�Ȃ���΃G���[
	if(!isNaN(costHansu) && frm.txtHansu.value != ""){
    if(modeCostTblAdd == 1 &&
    		frm.txtHansu.value != costHansu) {
    	//�R�X�g�e�[�u���Ő��̒l���s���ł��B$1����͂��Ă��������B
    	funErrorMsgBox(E000058 + costHansu + E000059);
    	return false;
    }
	}

//
//    if (mode == 1) {
//        dspMsg = I000002;
//    } else if (mode == 2) {
//        dspMsg = I000003;
//    } else {
//        dspMsg = I000004;
//    }
//
//    //�m�Fү���ނ̕\��
//    if (funConfMsgBox(dspMsg) != ConBtnYes) {
//        return false;
//    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(I000002) != ConBtnYes) {
        return false;
    }

    //XML�̏�����
    setTimeout("xmlFGEN3050I.src = '../../model/FGEN3050I.xml';", ConTimer);

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3050, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

        //����
        funInfoMsgBox(dspMsg);

        // �R�X�g�e�[�u���ꗗ���X�V����
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� ---------------------mod start
        // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
        try{

        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }

        // �yQP@30297�z TT.nishigawa �ۑ�Ή� ---------------------mod end
        switch(modeCostTblAdd){
        case 1: // �V�K�Ȃ�X�V��
            modeCostTblAdd = 2;
            backCostTblAdd = 1; // ���j���[��ʂɖ߂�
            break;
        case 3: // �R�s�[�Ȃ�X�V
            modeCostTblAdd = 2;
            backCostTblAdd = 2; // �R�X�g�e�[�u���ꗗ��ʂɖ߂�
            break;
        }
        funCostTblAddTuti(1);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// ���F�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F���F
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funShonin(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3060");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3060I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3060O);
    var dspMsg;

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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3060, xmlReqAry, xmlResAry, 1) == false) {
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

        // �R�X�g�e�[�u���ꗗ���X�V����
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� ---------------------mod start
        // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
        try{
        	window.opener.document.frm00.btnSearch.click();
        }catch (e) {

        }
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� ---------------------mod end

        switch(modeCostTblAdd){
        case 1: // �V�K�Ȃ�X�V��
            modeCostTblAdd = 2;
            backCostTblAdd = 1; // ���j���[��ʂɖ߂�
            break;
        case 3: // �R�s�[�Ȃ�X�V
            modeCostTblAdd = 2;
            backCostTblAdd = 2; // �R�X�g�e�[�u���ꗗ��ʂɖ߂�
            break;
        }

        funCostTblAddTuti(1);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }
    }

    return true;

}

//�yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add start
//========================================================================================
//�폜�{�^����������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/08/22
//����  �F�Ȃ�
//�T�v  �F�\���f�[�^�̍폜�������s��
//========================================================================================
function funDataDelete() {

     var frm = document.frm00;    //̫�тւ̎Q��
     var XmlId = "RGEN3110";
     var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3110");
     var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3110I);
     var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3110O);

     //�폜�m�F
     if (funConfMsgBox(I000004) != ConBtnYes) {
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
     if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3110, xmlReqAry, xmlResAry, 1) == false) {
         return false;
     }

     //������ү���ޔ�\��
     funClearRunMessage();

     if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

         //����ү���ނ̕\��
         funInfoMsgBox("�R�X�g�e�[�u����" + I000007);

         // �R�X�g�e�[�u���ꗗ���X�V����
         // �ꗗ��ʂ������オ���Ă��Ȃ��ꍇ�ɃG���[�ƂȂ�ׁAtry~catch�ŉ��
         try{
             window.opener.document.frm00.btnSearch.click();
         }catch (e) {

         }

         // �R�X�g�e�[�u���ꗗ��ʂɖ߂�modeCostTblAdd=2(�o�^) �̂�
         modeCostTblAdd = 2;
         backCostTblAdd = 2; // �R�X�g�e�[�u���ꗗ��ʂɖ߂�

         // �R�X�g�e�[�u���ꗗ��ʂɖ߂�
         funNext();

     }

     return true;
}
//�yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add end


//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
        if (XmlId.toString() == "RGEN3030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3030
                    break;
            }

        // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start
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
        // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end

            // �o�^�{�^������
        } else if (XmlId.toString() == "RGEN3050") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3050

                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
//                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "dt_yuko", frm.txtYuko.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "biko", frm.txtBiko.value, 0);
                    var no_basehansu  = frm.ddlBaseHansu.options[frm.ddlBaseHansu.selectedIndex].value;
                    no_basehansu = no_basehansu.split("�ŁE")[0];

                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_basehansu", no_basehansu, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "name_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].innerText, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "name_hansu", frm.costHouzai.value, 0);

                    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "biko_kojo", frm.txtBiko_kojo.value, 0);
                    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "", 0);
                    }

                    if (frm.chkKakunin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_kakunin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_kakunin", "", 0);
                    }
                    // ���g�p�`�F�b�N�{�b�N�X �yKPX@1602367�zadd start
                    if (frm.chkMishiyo.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_mishiyo", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_mishiyo", "0", 0);
                    }
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_category", "maker_name", 0);
                    //�yKPX@1602367�zadd end

                    for (j = 0; j < 31; j++) {

                        if (j <= 0) {
                            funXmlWrite_Tbl(reqAry[i], "cost", "nm_title", "", j);
                            funXmlWrite_Tbl(reqAry[i], "cost", "no_row", "0", j);
                            for (k = 0; k < 30; k++) {
                            	// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                                funXmlWrite_Tbl(reqAry[i], "cost", "no_value" + funZeroPudding(k + 1), document.getElementById("txtHeader_" + (k + 1)).value.replace(/,/g,""), j);
                                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
                            }
                        } else {

                            funXmlWrite_Tbl(reqAry[i], "cost", "nm_title", document.getElementById("nm_title_" + j).value, j);
                            funXmlWrite_Tbl(reqAry[i], "cost", "no_row", j, j);
                            for (k = 0; k < 30; k++) {
                            	// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                                funXmlWrite_Tbl(reqAry[i], "cost", "no_value" + funZeroPudding(k + 1), document.getElementById("no_value_" + j + "_" + (k + 1)).value.replace(/,/g,""), j);
                                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
                            }
                        }
                        funAddRecNode_Tbl(reqAry[i], "FGEN3050", "cost");
                    }
                    break;
            }

        // ���F�{�^������
        } else if (XmlId.toString() == "RGEN3060") {

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3060
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "cost_list", "no_hansu", frm.txtHansu.value, 0);

                    if (frm.chkShonin.checked) {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "1", 0);
                    } else {
                        funXmlWrite_Tbl(reqAry[i], "cost_list", "chk_shonin", "", 0);
                    }

                    break;
            }

        //�����{�^������
        } else if (XmlId.toString() == "RGEN3080"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3080
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }

        //�yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add start
        //�폜�{�^������
            // ���F�ς݃f�[�^���K�b�c���폜���Ăn�j�H
        } else if (XmlId.toString() == "RGEN3110"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3110
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }
        //�yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add end

        } else if (XmlId.toString() == "RGEN2160"){

            var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            var no_hansu  = frm.txtHansu.value;
//�yKPX@1602367�zadd start
            // ���g�p
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = 2;
            }

//            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd;
            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd + "-" + mishiyo;
//�yKPX@1602367�zadd end

            // �����R�[�h�u���y���[�J�[�R�[�h:::��ރR�[�h:::�Ő��z
            var put_code = put_code.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
// �yKPX@1602367�z add start
        // �x�[�X�P���̃f�[�^���擾
	    } else if (XmlId.toString() == "RGEN3600"){
	    	var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
            var no_hansu  = frm.ddlBaseHansu.options[frm.ddlBaseHansu.selectedIndex].value;
            no_hansu = no_hansu.split("�ŁE")[0];

            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-" + backCostTblAdd;

            // �����R�[�h�u���y���[�J�[�R�[�h:::��ރR�[�h:::�Ő��z
            var put_code = put_code.replace(/-/g,":::");
	        switch (i) {
	            case 0:    //USERINFO
	                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	                funXmlWrite(reqAry[i], "id_user", "", 0);
	                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
	                break;
	            case 1:    //FGEN3600
	            	funXmlWrite(reqAry[i], "dt_yuko", frm.txtYuko.value, 0);
                    break;
	        }
	    // �Ő����X�g�擾
        } else if (XmlId.toString() == "RGEN3610"){
        	var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
            var cd_houzai = frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;

            var put_code = modeCostTblAdd + "-" + cd_maker + "-" + cd_houzai;

            // �����R�[�h�u���y���[�h:::���[�J�[�R�[�h:::��ރR�[�h�z
            var put_code = put_code.replace(/-/g,":::");
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
                case 1:    //RGEN3610
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    break;
	            case 2:    //FGEN3660
                    break;
            }
// �yKPX@1602367�z add end
        }
    }

    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
        case 1:    //����Ͻ� ���[�J�[��
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
            //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start
        case 2:    //����Ͻ��i��2�J�e�S���j ��ޖ�
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
          //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end
//�yKPX@1602367�zadd start
        case 3:    // �x�[�X�P�����X�g�}�X�^ �Ő�
            atbName = "no_hansu";
            atbCd = "no_hansu";
            break;
//�yKPX@1602367�zadd end
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
        	// ��ޖ� ���� �V�K�̏ꍇ�͖��g�p������
        	if(atbCd == "cd_2nd_literal"){
        		if(modeCostTblAdd == 1) {
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
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
//�yKPX@1602367�zadd start
            case 3:    // �Ő�
            	obj.options[i].value = obj.options[i].value.split("�ŁE")[0];
                if (obj.options[i].value == funXmlRead(xmlData, "no_basehansu", 0)) {
                    selIndex = i;
                }
                break;
//�yKPX@1602367�zadd end
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext() {

    var wUrl;

    //�J�ڐ攻��

    if (backCostTblAdd == 1) {
        funEnd();
    } else {

        window.close();


    }

    return true;
}
//========================================================================================
// �I������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/16
// �T�v  �F�I������
//========================================================================================
function funEnd(){
	var wUrl;
	// �e�E�B���h�E�̑��݂��`�F�b�N
	if(window.opener ){
		// �e�E�C���h�E�����݂���
		window.close();
	} else {
	    //��ʂ����
	    close(self);
	    return true;

//        // �����ޒ��B�����j���[
//        wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//        //�yQP@40404�z�ۑ�-No.112  E.kitazawa --------- mod start
//        //������ү���ޕ\��
//        funShowRunMessage();
//
//        //�J��
//        //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
//        setTimeout(function(){ funUrlConnect(wUrl, ConConectPost, document.frm00) }, 0);
        //�yQP@40404�z�ۑ�-No.112  E.kitazawa --------- mod end
	}
	return true;
}

//========================================================================================
// �R�X�g�e�[�u���o�^�E���F��ʋN�����ʒm
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funCostTblAddTuti(mode) {

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
// �쐬�ҁF
// �쐬���F
// ����  �F�Ȃ�
// �T�v  �F���b�g���ɒl�����͂��ꂽ�ꍇ�A���͒l�ɂ����
//     �m�F�ҁE���F�҃`�F�b�N�{�b�N�X���N���A����
//========================================================================================
function funNumOnly() {

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

    if("0123456789\b\t".indexOf(m, 0) < 0) return false;

    funCheckChange();

    return true;
}

//========================================================================================
// �l�̌ܓ�
// �쐬�ҁF
// �쐬���F
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
// �쐬�ҁF
// �쐬���F
// ����  �F�Ȃ�
// �T�v  �F�e���ڍX�V���A�`�F�b�N�{�b�N�X���N���A����
//========================================================================================
function funCheckChange() {

    var frm = document.frm00;    //̫�тւ̎Q��

    if(modeCostTblAdd == 2){

        if (frm.chkShonin.checked) {
            frm.chkShonin.checked = false;
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
            document.getElementById("lblShonin").innerHTML = "�|";
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
        }

        if (frm.chkKakunin.checked) {
            frm.chkKakunin.checked = false;
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
            document.getElementById("lblKakunin").innerHTML = "�|";
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
        }
    }

    return false;
}

//========================================================================================
// �\�����擾
// �쐬�ҁF
// �쐬���F
// ����  �F�Ȃ�
// �T�v  �F��ށA�Ő��ύX���A�\�������Ď擾����
//========================================================================================
function funExistData() {

    var frm = document.frm00;    //̫�тւ̎Q��

    var cd_maker  = frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value;
    var cd_houzai =  frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value;
    var no_hansu  = frm.txtHansu.value;
    if(modeCostTblAdd == 1 && cd_maker != "" && cd_houzai != "" && no_hansu != ""){

        var XmlId = "RGEN3080";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3080");
        var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3080I);
        var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3080O);

        // �yQP@40404�z E.kitazawa 2014.12.26 ----------- del start
        // mode�l���ݒ肳��Ă��Ȃ� �� հ�ޏ��̏����敪���ݒ�
        //  �� հ�ޏ�񂪎擾�ł��Ȃ� �� userInfoData���ݒ�ׁ̈ANullPointerException
//            var mode;
        // �yQP@40404�z E.kitazawa  --------------------- del end

        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {		//�yKPX@1602367�zmod
            funClearRunMessage();
            return false;
        }

        //����ݏ��A�����ޯ���̏����擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3080, xmlReqAry, xmlResAry, 1) == false) {		//�yKPX@1602367�zmod
            return false;
        }
    }

    return;
}

//========================================================================================
// ���̓`�F�b�N
// �쐬�ҁF
// �쐬���F
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
//        funErrorMsgBox("���b�g���������͂ł��B");
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

        	// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
        	try{
        		costValue = document.getElementById("no_value_" + (row + 1) + "_"  + (col + 1)).value;

                if (costValue != "") {
                    costCount++;
                }
        	}catch(e){

        	}
        	// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
        }

        if (costCount != colCount) {
            frm.chkKakunin.checked = false;
            funErrorMsgBox("�F���܂��̓R�X�g" + E000050);
            return;
        }
    }
}

//�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
//========================================================================================
// ���lFormat�ϊ�
// �쐬�ҁFTT.nishigawa
// �쐬���F
// ����  �Fval:�ϊ��l
//       keta:�������i�؂�̂Č��j
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
//�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

//�yQP@30297�z No.25 E.kitazawa �ۑ�Ή� --------------------- mod start
//========================================================================================
// �J�[�\���ړ������F�����L�[����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/10/21
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
//�yQP@30297�z No.25 E.kitazawa �ۑ�Ή� --------------------- mod end

//�yQP@30297�zNo.19  E.kitazawa �ۑ�Ή� --------------------- add start
//========================================================================================
//���[�J�[���R���{�{�b�N�X�A������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/01
//����  �F�Ȃ�
//�T�v  �F���[�J�[�ɕR�t����ރR���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeMaker() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3010";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3010");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3010I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3010O);

    //��޺����ޯ���̸ر
    funClearSelect(frm.ddlHouzai, 2);
    funClearSelect(frm.ddlBaseHansu, 2);	//�Ő������ޯ���̸ر �yKPX@1602367�zadd
	frm.txtHansu.value = "";				//�Ő��N���A�yKPX@1602367�zadd

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
//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end

//�yKPX@1602367�zadd start
//========================================================================================
// �x�[�X��ޖ��R���{�{�b�N�X�A������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/14
// ����  �F�Ȃ�
// �T�v  �F���[�J�[�A��ޖ��ɕR�t���Ő��R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeBaseHouzai() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3610";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3610","FGEN3660");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3610I,xmlFGEN3660I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3610O,xmlFGEN3660O);

	funClearSelect(frm.ddlBaseHansu, 2);	//�Ő������ޯ���̸ر
	frm.txtHansu.value = "";				//�Ő��N���A

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3010, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//�Ő������ޯ���̍쐬
	funCreateComboBox(frm.ddlBaseHansu, xmlResAry[2], 3);
	xmlFGEN3610.load(xmlResAry[2]);
    var no_hansu = funXmlRead(xmlResAry[3], "no_hansu", 0);
    if (no_hansu == "") {
    	costHansu = 1;

    } else {
    	costHansu = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);

    }

//	var costHansuM = (parseInt(funXmlRead(xmlResAry[3], "no_hansu", 0)) + 1);
//	if(costHansuM == NaN) {
//		costHansu = 1;
//	} else {
//		costHansu = costHansuM;
//	}

	return true;

}

//========================================================================================
// �x�[�X�P����񌟍�����
// �쐬�ҁFBRC Koizumi
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�Ő��ύX���A�Y���̃x�[�X�P�������擾�E�ݒ肷��
//========================================================================================
function funBaseSearch() {

	var frm = document.frm00;    //̫�тւ̎Q��

	var XmlId = "RGEN3600";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3600");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3600I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3600O);

	//������ү���ޕ\��
	funShowRunMessage();

	//�ꗗ�̸ر
	funClearList();
	xmlFGEN3600O.src = "";
	frm.txtHansu.value = "";

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//���������Ɉ�v�����ް����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3600, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//�ް�����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//�\��
		tblList.style.display = "block";
		// �ꗗ�쐬
		funCreateCostTbl(xmlResAry[2]);
		// �L���J�n���A���l�i�����j
		frm.txtYuko.value  = funXmlRead(xmlResAry[2], "dt_yuko", 0);
		frm.txtBiko.value  = funXmlRead(xmlResAry[2], "biko", 0);
		// �R�X�g�Ő�
		frm.txtHansu.value = costHansu;
		// �R�X�g���
//		frm.costHouzai.value = funXmlRead(xmlResAry[2], "name_houzai", 0);

		//������ү���ޔ�\��
		funClearRunMessage();

	} else {
		//������ү���ޔ�\��
		funClearRunMessage();
	}

	return true;
}
//�yKPX@1602367�zadd end

