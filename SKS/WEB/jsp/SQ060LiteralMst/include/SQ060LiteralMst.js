//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConLiteralMstId);

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
// �J�e�S���R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F�J�e�S���ɕR�t�����e�����R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeCategory() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0620";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA110");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA110I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA110O);

    //�ú�؈ȉ���ر
    funInit();

    if (frm.ddlCategory.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlLiteral, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���ُ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0620, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlLiteral, xmlResAry[2], 2);

    return true;

}

//========================================================================================
// ���e�������擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �F���e�����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0630";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA100");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA100I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA100O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    if (frm.ddlLiteral.selectedIndex == 0) {
        funInit();
        return true;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //��ʏ�����
        funInit();
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0630, xmlReqAry, xmlResAry, 1) == false) {
        //��ʏ�����
        funClear();
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //��ʂ̏�����
    funSetData(xmlResAry[2]);

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
    var XmlId = "JSP0640";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA330");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA330I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA330O);
    var dspMsg;

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

    //�����敪�̑ޔ�
    frm.hidEditMode.value = mode;

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0640, xmlReqAry, xmlResAry, 1) == false) {
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
        funInit();
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

    //��ʏ�����
    funInit();
    funCreateComboBox(frm.ddlCategory, xmlSA040O, 1);
    funClearSelect(frm.ddlLiteral, 2);

    //̫����ݒ�
    frm.ddlCategory.focus();

    return true;

}

//========================================================================================
// Excel�o�̓{�^������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �T�v  �FCSV�t�@�C���̏o�͂��s��
//========================================================================================
function funOutput() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0650";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA320");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA320I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA320O);
    var dspMsg;

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //CSV̧�ق��쐬����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0650, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //���ʔ���
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //�װ��������ү���ނ�\��
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //̧���߽�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlSA320O, "URLValue", 0);

    //������ү���ޔ�\��
    funClearRunMessage();

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, document.frm00);

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
    var XmlId = "JSP0610";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA040","SA050","SA300");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA040I,xmlSA050I,xmlSA300I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA040O,xmlSA050O,xmlSA300O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0610, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlCategory, xmlResAry[2], 1);
    funClearSelect(frm.ddlLiteral, 2);
    funCreateComboBox(frm.ddlUseEdit, xmlResAry[4], 3);
    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 4);

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F�������̑ޔ��A��ʐ�����s��
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
    frm.btnDelete.disabled = true;
    frm.btnExcel.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //����Ͻ�����ݽ
        if (GamenId.toString() == ConGmnIdLiteralMst.toString()) {
            //�ҏW or ���ъǗ���
            if (KinoId.toString() == ConFuncIdEdit.toString() || KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }

            hidGamenId.value = GamenId;
            hidKinoId.value = KinoId;

        //����Ͻ�CSV
        } else if (GamenId.toString() == ConGmnIdLiteralCsv.toString()) {
            //CSV�o��
            if (KinoId.toString() == ConFuncIdRead.toString() || KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnExcel.disabled = false;
            }

            //���۰ق̐���
            funGamenControl(true);

            if (hidGamenId.value == "") {
                hidGamenId.value = GamenId;
            }
            if (hidKinoId.value == "") {
                hidKinoId.value = KinoId;
            }
        }
    }

    return true;

}

//========================================================================================
// ���e�����f�[�^�ݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@xmlData �F ���e�������XML
// �T�v  �F�擾�������e�����f�[�^����ʂɐݒ肷��
//========================================================================================
function funSetData(xmlData) {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʐ���
    if (funXmlRead(xmlData, "flg_edit", 0) == "1" || hidKinoId.value == ConFuncIdSysMgr) {
        //�ҏW�� or ���ъǗ���
        funGamenControl(false);

    } else {
        //�ҏW�s��
        funGamenControl(true);
    }

    //��ʏ��̐ݒ�
//    frm.txtLiteralCd.value = ("000" + funXmlRead(xmlData, "cd_literal", 0)).slice(-3);
    frm.txtLiteralName.value = funXmlRead(xmlData, "nm_literal", 0);
    frm.txtValue1.value = funXmlRead(xmlData, "value1", 0);
    frm.txtValue2.value = funXmlRead(xmlData, "value2", 0);
    frm.txtSortNo.value = funXmlRead(xmlData, "no_sort", 0);
    frm.txtBikou.value = funXmlRead(xmlData, "biko", 0);
    funDefaultIndex(frm.ddlUseEdit, 2);
    funDefaultIndex(frm.ddlGroup, 3);

    return true;

}

//========================================================================================
// ��ʏ�����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F��ʂ�������Ԃɖ߂�
//========================================================================================
function funInit() {

    var frm = document.frm00;    //̫�тւ̎Q��

    if (hidGamenId.value == ConGmnIdLiteralCsv) {
        funItemDisabled(frm.ddlLiteral, true);
        funGamenControl(true);
    } else {
        funGamenControl(false);
    }

    //��ʏ��̐ݒ�
//    frm.txtLiteralCd.value = "";
    frm.txtLiteralName.value = "";
    frm.txtValue1.value = "";
    frm.txtValue2.value = "";
    frm.txtSortNo.value = "";
    frm.txtBikou.value = "";
    funCreateComboBox(frm.ddlUseEdit, xmlSA300O, 3);
    funDefaultIndex(frm.ddlUseEdit, 1);
    funCreateComboBox(frm.ddlGroup, xmlSA050O, 4);
    frm.ddlGroup.selectedIndex = 0;

}

//========================================================================================
// ��ʃR���g���[������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@flg �F ����l(true:�g�p�s�Afalse:�g�p�\)
// �߂�l�F�Ȃ�
// �T�v  �F��ʃR���g���[���̓��͐�����s��
//========================================================================================
function funGamenControl(flg) {

    var frm = document.frm00;    //̫�тւ̎Q��

//    funItemReadOnly(frm.txtLiteralCd, true);
    funItemReadOnly(frm.txtLiteralName, flg);
    funItemReadOnly(frm.txtValue1, flg);
    funItemReadOnly(frm.txtValue2, flg);
    funItemReadOnly(frm.txtSortNo, flg);
    funItemReadOnly(frm.txtBikou, flg);
    funItemReadOnly(frm.ddlUseEdit, flg);
    funItemReadOnly(frm.ddlGroup, flg);
    if (hidGamenId.value == ConGmnIdLiteralMst) {
        funItemDisabled(frm.btnInsert, flg);
        funItemDisabled(frm.btnUpdate, flg);
        funItemDisabled(frm.btnDelete, flg);
    }

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
        if (XmlId.toString() == "JSP0610") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA040
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
                    break;
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
                    break;
                case 3:    //SA300
                    break;
            }

        //�ú�غ��ޑI��
        } else if (XmlId.toString() == "JSP0620"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA110
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
                    break;
            }

        //���ٺ��ޑI��
        } else if (XmlId.toString() == "JSP0630"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA100
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    break;
            }

        //�o�^�A�X�V�A�폜���݉���
        } else if (XmlId.toString() == "JSP0640"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA330
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "flg_edit", frm.ddlUseEdit.options[frm.ddlUseEdit.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", frm.hidEditMode.value, 0);
                    break;
            }

        //Excel���݉���
        } else if (XmlId.toString() == "JSP0650"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA320
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
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

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�󔒍s�̒ǉ�
        funClearSelect(obj, 2);
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //�ú��Ͻ�
            //�����ޯ���̸ر
            funClearSelect(obj, 2);

            atbName = "nm_category";
            atbCd = "cd_category";
            break;
        case 2:    //����Ͻ�
            //�����ޯ���̸ر
            funClearSelect(obj, 2);

            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 3:    //�ҏW��
            //�����ޯ���̸ر
            funClearSelect(obj, 1);

            atbName = "nm_editflg";
            atbCd = "cd_editflg";
            break;
        case 4:    //��ٰ��Ͻ�
            //�����ޯ���̸ر
            funClearSelect(obj, 2);

            atbName = "nm_group";
            atbCd = "cd_group";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            if (mode == 2) {
                //���ٺ���(���ށ{����)
                objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "�F" + funXmlRead(xmlData, atbName, i);
            } else {
                //���ٺ��ވȊO(���̂̂�)
                objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            }
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    //
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //�ҏW�ۺ���
                if (obj.options[i].value == 1) {
                    selIndex = i;
                }
                break;
            case 2:    //�ҏW�ۺ���
                if (obj.options[i].value == funXmlRead(xmlSA100O, "flg_edit", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //��ٰ�ߺ���
                if (obj.options[i].value == funXmlRead(xmlSA100O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

