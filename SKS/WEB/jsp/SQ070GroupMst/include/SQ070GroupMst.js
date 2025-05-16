//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConGroupMstId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClear();

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �����f�[�^�؂�ւ�����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F��ʂ̐�����s��
//========================================================================================
function funChangeMode() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʐ���
    if (frm.rdoMode[0].checked) {
        //��ٰ��
        funItemReadOnly(frm.txtGroup, false);
        funItemReadOnly(frm.ddlTeam, true);
        funItemReadOnly(frm.txtTeam, true);
        // ADD 2013/10/24 QP@30154 okano start
        funItemReadOnly(frm.ddlKaisha, false);
        // ADD 2013/10/24 QP@30154 okano end

        //��ʏ��̐ݒ�
        if (frm.ddlGroup.selectedIndex == 0) {
            frm.txtGroup.value = "";
        } else {
            frm.txtGroup.value = frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[1];
        }
        funClearSelect(frm.ddlTeam, 2);
        frm.txtTeam.value = "";
        spnHisuG.innerHTML = "<font color=\"red\">�i�K�{�j</font>";
        spnHisuT.innerHTML = "";
        // ADD 2013/10/24 QP@30154 okano start
        spnHisuK.innerHTML = "<font color=\"red\">�i�K�{�j</font>";
        // ADD 2013/10/24 QP@30154 okano end

    } else {
        //���
        funItemReadOnly(frm.txtGroup, true);
        funItemReadOnly(frm.ddlTeam, false);
        funItemReadOnly(frm.txtTeam, false);
        // ADD 2013/10/24 QP@30154 okano start
        funItemReadOnly(frm.ddlKaisha, true);
        // ADD 2013/10/24 QP@30154 okano end

        //��ʏ��̐ݒ�
        frm.txtGroup.value = "";
        spnHisuG.innerHTML = "";
        spnHisuT.innerHTML = "<font color=\"red\">�i�K�{�j</font>";
        // ADD 2013/10/24 QP@30154 okano start
        spnHisuK.innerHTML = "";
        // ADD 2013/10/24 QP@30154 okano end

        //��Ѻ��ސݒ�
        funChangeGroup();
    }

    return true;

}

//========================================================================================
// �O���[�v�R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F�O���[�v�ɕR�t���`�[���R���{�{�b�N�X�𐶐�����
//       �F�I�����ꂽ�O���[�v�R���{�{�b�N�X�̖��̂�ݒ肷��
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //��ٰ�ߖ��̸ر
        frm.txtGroup.value = "";
        //��т̸ر
        funClearSelect(frm.ddlTeam, 2);
        frm.txtTeam.value = "";
        // ADD 2013/10/24 QP@30154 okano start
        //��к����ޯ���̑I����������
        frm.ddlKaisha.selectedIndex = 0;
        // ADD 2013/10/24 QP@30154 okano end
        return true;
    }

    //���̂̐ݒ�
    if (frm.rdoMode[0].checked) {
        //��ٰ�ߖ���ݒ�
        frm.txtGroup.value = frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[1];
    } else {
        //��і��̸ر
        frm.txtTeam.value = "";
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //��я����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9020, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2);
    // ADD 2013/10/24 QP@30154 okano start
    //��к����ޯ����ݒ�
    for (i = 0; i < frm.ddlKaisha.options.length; i++) {
    	if(frm.ddlKaisha.options[i].value.split(ConDelimiter)[0] == frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[2]) {
    		frm.ddlKaisha.selectedIndex = i;
    	}
    }
    // ADD 2013/10/24 QP@30154 okano end

    return true;

}

//========================================================================================
// �`�[�����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F�I�����ꂽ�`�[���R���{�{�b�N�X�̖��̂�ݒ肷��
//========================================================================================
function funChangeTeam() {

    var frm = document.frm00;    //̫�тւ̎Q��

    if (frm.ddlTeam.selectedIndex == 0) {
        //��і��̸ر
        frm.txtTeam.value = "";
    } else {
        //��і���ݒ�
        frm.txtTeam.value = frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value.split(ConDelimiter)[1];
    }

    return true;

}

//========================================================================================
// �o�^�A�X�V�A�폜�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�폜
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId;
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);
    var dspMsg;
    var xmlObj;

    if (mode == 1) {
        dspMsg = I000002;
    } else if (mode == 2) {
        dspMsg = I000003;
    } else {
        dspMsg = I000004;
    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //�Ώ��ް��̔���
    if (frm.rdoMode[0].checked) {
        //��ٰ�߂̏ꍇ
        XmlId = "JSP0720";
        FuncIdAry[2] = "SA060";
        xmlReqAry[1] = xmlSA060I;
        xmlResAry[2] = xmlSA060O;
        xmlObj = xmlJSP0720;
    } else {
        //��т̏ꍇ
        XmlId = "JSP0730";
        FuncIdAry[2] = "SA090";
        xmlReqAry[1] = xmlSA090I;
        xmlResAry[2] = xmlSA090O;
        xmlObj = xmlJSP0730;
    }

    //�����敪�̑ޔ�
    frm.hidEditMode.value = mode;

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlObj, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //����
        funInfoMsgBox(dspMsg);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }

        //��ʂ̏�����
        funClear();
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //��ʐ���
    frm.rdoMode[0].checked = true;
    funChangeMode();

    //̫����ݒ�
    frm.rdoMode[0].focus();

    return true;

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //Ͻ��ƭ�
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0710";
    // MOD 2013/10/24 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O);
    // MOD 2013/10/24 QP@30154 okano end

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0710, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 1);
    // ADD 2013/10/24 QP@30154 okano start
    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 3);
    // ADD 2013/10/24 QP@30154 okano end

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/06/24
// ���e  �F�폜�{�^���̃R�����g��(�ۑ�\��13)
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�o�^�A�X�V�A�폜���݂̐���
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
//    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //��ٰ������ݽ
        if (GamenId.toString() == ConGmnIdGroupMst.toString()) {
            //�ҏW
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
//                frm.btnDelete.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
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
        //��ʏ����\��
        if (XmlId.toString() == "JSP0710") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
                // ADD 2013/10/24 QP@30154 okano start
                case 2:    //SA140
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
                // ADD 2013/10/24 QP@30154 okano end
            }

        //�o�^�A�X�V�A�폜���݉���(��ٰ��)
        } else if (XmlId.toString() == "JSP0720"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA060
                    if (frm.hidEditMode.value == 1) {
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else if (frm.hidEditMode.value == 2) {
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_shori", "3", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "nm_group", frm.txtGroup.value, 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value.split(ConDelimiter)[0], 0);
                    break;
            }

        //�o�^�A�X�V�A�폜���݉���(���)
        } else if (XmlId.toString() == "JSP0730"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA090
                    if (frm.hidEditMode.value == 1) {
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else if (frm.hidEditMode.value == 2) {
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_shori", "3", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "nm_team", frm.txtTeam.value, 0);
                    break;
            }

        //��ٰ�ߺ��ޑI���A���׼޵���ݑI��
        } else if (XmlId.toString() == "JSP9020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value.split(ConDelimiter)[0], 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGroupMst, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;                  //�ݒ�XML�̌���
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
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //��ٰ��Ͻ�
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 2:    //���Ͻ�
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        // ADD 2013/10/24 QP@30154 okano start
        case 3:    //���Ͻ�
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        // ADD 2013/10/24 QP@30154 okano end
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "�F" + funXmlRead(xmlData, atbName, i);
            // MOD 2013/10/24 QP@30154 okano start
//            objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i);
            if(mode == 1){
            	objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i) + ConDelimiter + funXmlRead(xmlData, "cd_kaisha", i);
            } else {
            	objNewOption.value = funXmlRead(xmlData, atbCd, i) + ConDelimiter + funXmlRead(xmlData, atbName, i);
            }
            // MOD 2013/10/24 QP@30154 okano end
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

