
//��ʐݒ�(BODY��۰�ތ�����ق̐ݒ肪�s���Ȃ����߁ABODY��۰�ޑO�ɍs��)
funInitScreen(ConKasihaAddId);

//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
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

    //��ʏ�����
    funClear();

    //�߂�l�̏�����
    window.returnValue = "";

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �����{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
// ����  �F�Ȃ�
// �T�v  �F�����S����Ѓf�[�^�̌������s��
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
// �쐬���F2009/04/08
// ����  �F�Ȃ�
// �T�v  �F�����S����Ѓf�[�^�̌������s��
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA220");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA220I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA220O);
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
        funClearList();
        funClearRunMessage();
        return false;
    }

    //�������������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1010, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
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
// �쐬���F2009/04/08
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //�ꗗ�̸ر
    funClearList();

    //̫����ݒ�
    frm.txtKaishaName.focus();

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlSA220O.src = "";
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
// �쐬���F2009/04/08
// �T�v  �F�I���f�[�^���Ăяo������ʂɕԂ�
//========================================================================================
function funSelect() {

    var retVal = "";

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�߂�l�̐ݒ�
    retVal = funXmlRead(xmlSA220O, "cd_kaisha", funGetCurrentRow());
    retVal += ConDelimiter + funXmlRead(xmlSA220O, "nm_kaisha", funGetCurrentRow());
    window.returnValue = retVal;

    //��ʂ����
    close(self);

}

//========================================================================================
// �I���{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
// �T�v  �F��ʂ����
//========================================================================================
function funClose() {

    //�߂�l�̐ݒ�
    window.returnValue = "";

    //��ʂ����
    close(self);

}

//========================================================================================
// �y�[�W�J��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
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

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
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

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/08
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
        //�������݉���
        if (XmlId.toString() == "JSP1010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA220
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //��ʏ����\��
        } else if (XmlId.toString() == "JSP9030") {
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

