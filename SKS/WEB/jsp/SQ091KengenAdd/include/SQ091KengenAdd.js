
//��ʐݒ�(BODY��۰�ތ�����ق̐ݒ肪�s���Ȃ����߁ABODY��۰�ޑO�ɍs��)
funInitScreen(ConKengenAddId);

//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClear();

    //�߂�l�̏�����
    window.returnValue = "";

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@mode     �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �T�v  �F���ʂ̃R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeCmb(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��

    switch (mode) {
        case 2:    //�@�\
            //�����ޯ���̸ر
            funClearSelect(frm.ddlKino, 2);
            funClearSelect(frm.ddlData, 2);

            //�����ޯ���쐬
            funCreateComboBox(frm.ddlKino, mode);
            break;
        case 3:    //�Q�Ɖ\�ް�
            //�����ޯ���̸ر
            funClearSelect(frm.ddlData, 2);

            //�����ޯ���쐬
            funCreateComboBox(frm.ddlData, mode);
            break;
    }

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //�����ޯ���̍Đݒ�
    funClearSelect(frm.ddlKino, 2);
    funClearSelect(frm.ddlData, 2);

    //̫����ݒ�
    frm.txtKino.focus();

    return true;

}

//========================================================================================
// �̗p�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F�I���f�[�^���Ăяo������ʂɕԂ�
//========================================================================================
function funSelect() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;

    //�K�{���ڂ����͂���Ă��Ȃ��ꍇ
    if (frm.txtKino.value == "") {
        funErrorMsgBox("�@�\��" + E000003);
        return false;
    }
    if (frm.ddlGamen.selectedIndex == 0) {
        funErrorMsgBox("���" + E000003);
        return false;
    }
    if (frm.ddlKino.selectedIndex == 0) {
        funErrorMsgBox("�@�\" + E000003);
        return false;
    }
    if (frm.ddlData.selectedIndex == 0) {
        funErrorMsgBox("�Q�Ɖ\�f�[�^" + E000003);
        return false;
    }

    //�ݸ�ٸ��ð��݂����͂���Ă���ꍇ
    if (frm.txtKino.value.indexOf("'") != -1) {
        funErrorMsgBox("�@�\��" + E000004);
        return false;
    }
    if (frm.txtBiko.value.indexOf("'") != -1) {
        funErrorMsgBox("���l" + E000004);
        return false;
    }

    //�߂�l�̐ݒ�
    retVal = frm.txtKino.value + ConDelimiter;
    retVal += frm.ddlGamen.options[frm.ddlGamen.selectedIndex].value + ConDelimiter;
    retVal += frm.ddlKino.options[frm.ddlKino.selectedIndex].value + ConDelimiter;
    retVal += frm.ddlData.options[frm.ddlData.selectedIndex].value + ConDelimiter;
    retVal += frm.txtBiko.value;
    window.returnValue = retVal;

    //��ʂ����
    close(self);

}

//========================================================================================
// �I���{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F��ʂ����
//========================================================================================
function funClose() {

    window.returnValue = "";

    //��ʂ����
    close(self);

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //�����̎擾
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //̫�тւ̎Q��
    var XmlId = "JSP9030";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlGamen, 1);

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
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
        if (XmlId.toString() == "JSP9030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var atbName;
    var atbCd;
    var reccnt;
    var i;

    //�����ޯ���̸ر
    funClearSelect(obj, 2);

    //�����̎擾
    reccnt = funGetLengthLocal(xmlAuthority, mode);

    if (reccnt == 0) {
        return true;
    }

    switch (mode) {
        case 1:    //���
            atbName = "nm_gamen";
            atbCd = "id_gamen";
            break;
        case 2:    //�@�\
            atbName = "nm_kino";
            atbCd = "id_kino";
            break;
        case 3:    //�Q�Ɖ\�f�[�^
            atbName = "nm_data";
            atbCd = "id_data";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlReadLocal(xmlAuthority, atbCd, i, mode) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlReadLocal(xmlAuthority, atbName, i, mode);
            objNewOption.value = funXmlReadLocal(xmlAuthority, atbCd, i, mode) + ConDelimiter + funXmlReadLocal(xmlAuthority, atbName, i, mode);
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// XML�ǂݏo������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@xmlRead      �F�ǂݏo����XML
//       �F�AAttributeName�F���ږ�
//       �F�BRowNo        �F�s�ԍ�
//       �F�Cmode         �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �߂�l�F�擾�l
// �T�v  �FXML�t�@�C������w�荀�ڂ̒l���擾����B
//========================================================================================
function funXmlReadLocal(xmlRead, AttributeName, RowNo, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var objNode;
    var GIndex;
    var KIndex;
    var retVal;

    //Ӱ�ށ�1�ŁA��ʺ����ޯ�����I������Ă��Ȃ��ꍇ
    if (mode != 1 && frm.ddlGamen.selectIndex == 0) {
        return "";
    }

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        switch (mode) {
            case 1:    //���
                objNode = xmlRead.documentElement.childNodes;
                break;
            case 2:    //�@�\
                GIndex = frm.ddlGamen.selectedIndex - 1;
                objNode = xmlRead.documentElement.childNodes.item(GIndex).childNodes;
                break;
            case 3:    //�Q�Ɖ\�f�[�^
                GIndex = frm.ddlGamen.selectedIndex - 1;
                KIndex = frm.ddlKino.selectedIndex - 1;
                objNode = xmlRead.documentElement.childNodes.item(GIndex).childNodes.item(KIndex).childNodes;
                break;
        }

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return "";
        }
        //�����l���擾
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML���R�[�h�����擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@xmlData �F�����擾XML
//       �F�Amode    �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �߂�l�F���R�[�h����
// �T�v  �FXML�t�@�C�����R�[�h�������擾����
//========================================================================================
function funGetLengthLocal(xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var obj;
    var GIndex;
    var KIndex;

    reccnt = 0;

    //Ӱ�ށ�1�ŁA��ʺ����ޯ�����I������Ă��Ȃ��ꍇ
    if (mode != 1 && frm.ddlGamen.selectIndex == 0) {
        return reccnt;
    }

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        switch (mode) {
            case 1:    //���
                obj = xmlData.documentElement;
                break;
            case 2:    //�@�\
                GIndex = frm.ddlGamen.selectedIndex - 1;
                obj = xmlData.documentElement.childNodes.item(GIndex);
                break;
            case 3:    //�Q�Ɖ\�f�[�^
                GIndex = frm.ddlGamen.selectedIndex - 1;
                KIndex = frm.ddlKino.selectedIndex - 1;
                obj = xmlData.documentElement.childNodes.item(GIndex).childNodes.item(KIndex);
                break;
        }

        try {
            //�qɰ�ނ̑�������
            if (obj.hasChildNodes()) {
                //���݂���ꍇ
                reccnt = obj.childNodes.length;
            } else {
                //���݂��Ȃ��ꍇ
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

