
//��ʐݒ�(BODY��۰�ތ�����ق̐ݒ肪�s���Ȃ����߁ABODY��۰�ޑO�ɍs��)
funInitScreen(ConTantoSearchId);

//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
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
// ��ЃR���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F��ЂɕR�t�������R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9010";
    // MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlSA050O);
    // MOD 2013/11/7 QP@30154 okano end

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        // ADD 2013/11/7 QP@30154 okano start
        funClearSelect(frm.ddlGroup, 2);
        // ADD 2013/11/7 QP@30154 okano end
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 2);
    // ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 3);
    // ADD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// �O���[�v�R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�O���[�v�ɕR�t���`�[���R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        funClearSelect(frm.ddlTeam, 2);
        return true;
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
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 4);

    return true;

}

//========================================================================================
// �����{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�S���҃f�[�^�̌������s��
//========================================================================================
function funSearch() {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(1);

    //�w���߰�ނ��ް��擾
    funSearchData();
}

//========================================================================================
// ��������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�S���҃f�[�^�̌������s��
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0920";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA240");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA240I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA240O);
    var RecCnt;
    var PageCnt;
    var ListMaxRow;

    //������ү���ޕ\��
    funShowRunMessage();

    //�I���s�̏�����
    funSetCurrentRow("");

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList()
        funClearRunMessage();
        return false;
    }

    //�������������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0920, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList()
        return false;
    }

    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "�^" + PageCnt + "�y�[�W";

    //�ް�����������
    if (RecCnt > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //�ꗗ�̸ر
    funClearList()

    //�����ޯ���̍Đݒ�
    frm.ddlKaisha.selectedIndex = 0;
    funClearSelect(frm.ddlBusho, 2);
    // MOD 2013/11/7 QP@30154 okano start
//    	frm.ddlGroup.selectedIndex = 0;
    funClearSelect(frm.ddlGroup, 2);
    // MOD 2013/11/7 QP@30154 okano end
    funClearSelect(frm.ddlTeam, 2);

    //̫����ݒ�
    frm.txtUserId.focus();

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlSA240O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// �I���{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// �T�v  �F�I���f�[�^���Ăяo������ʂɕԂ�
//========================================================================================
function funSelect() {

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�߂�l�̐ݒ�
    window.returnValue = funXmlRead(xmlSA240O, "id_user", funGetCurrentRow());

    //��ʂ����
    close(self);

}

//========================================================================================
// �I���{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
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
// �쐬���F2009/04/06
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //�����̎擾
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //̫�тւ̎Q��
    var XmlId = "JSP0910";
    // MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O);
    // MOD 2013/11/7 QP@30154 okano end

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0910, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬
    // MOD 2013/11/7 QP@30154 okano start
//	    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 1);
//	    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 3);
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    // MOD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
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
        if (XmlId.toString() == "JSP0910") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                // MOD 2013/11/7 QP@30154 okano start
//	                case 1:    //SA050
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 2:    //SA140
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // MOD 2013/11/7 QP@30154 okano end
            }

        //�������݉���
        } else if (XmlId.toString() == "JSP0920"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA240
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //��к��ޑI��
        } else if (XmlId.toString() == "JSP9010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // ADD 2013/11/7 QP@30154 okano start
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // ADD 2013/11/7 QP@30154 okano end
            }

        //��ٰ�ߺ��ޑI��
        } else if (XmlId.toString() == "JSP9020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;                  //�ݒ�XML�̌���
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
        case 1:    //����Ͻ�(���)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 2:    //����Ͻ�(����)
            atbName = "nm_busho";
            atbCd = "cd_busho";
            break;
        case 3:    //��ٰ��Ͻ�
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 4:    //���Ͻ�
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �y�[�W�J��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(NextPage);

    //�w���߰�ނ��ް��擾
    funSearchData();
}

