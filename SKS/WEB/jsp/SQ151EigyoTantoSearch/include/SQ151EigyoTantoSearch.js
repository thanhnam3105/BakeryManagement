//========================================================================================
// ���ʕϐ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
//�������[�h��ێ�
var hidShoriMode = "";


//��ʐݒ�(BODY��۰�ތ�����ق̐ݒ肪�s���Ȃ����߁ABODY��۰�ޑO�ɍs��)
funInitScreen(ConEigyoTantoSearchId);

//========================================================================================
// �yQP@00342�z�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
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
    var XmlId = "RGEN2080";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2090");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2090I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2090O);
    
    //�������[�h�ݒ�
    hidShoriMode = opener_form.hidOpnerSearch.value;
    
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2080, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬�i�S���Ҍ������[�h�j
    if(hidShoriMode == "1"){
    	funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    }
    //�����ޯ���̍쐬�i��i�������[�h�j
    else{
    	//��ЃR�[�h�A��Ж��擾
        var cd_kaisha = opener_form.ddlKaisha.options[opener_form.ddlKaisha.selectedIndex].value;
        var nm_kaisha = opener_form.ddlKaisha.options[opener_form.ddlKaisha.selectedIndex].innerText;
        //�R���{�{�b�N�X����
    	var objNewOptionKaisha = document.createElement("option");
        frm.ddlKaisha.options.add(objNewOptionKaisha);
        objNewOptionKaisha.innerText = nm_kaisha;
        objNewOptionKaisha.value = cd_kaisha;
        
        //�����R�[�h�A�������擾
        var cd_busho = opener_form.ddlBusho.options[opener_form.ddlBusho.selectedIndex].value;
        var nm_busho = opener_form.ddlBusho.options[opener_form.ddlBusho.selectedIndex].innerText;
        //�R���{�{�b�N�X����
    	var objNewOptionBusho = document.createElement("option");
        frm.ddlBusho.options.add(objNewOptionBusho);
        objNewOptionBusho.innerText = nm_busho;
        objNewOptionBusho.value = cd_busho;
        
    }
    
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
    var XmlId = "RGEN2090";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2100");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2100I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2100O);

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2090, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 2);

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
        if (XmlId.toString() == "RGEN2080") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2090
                    break;
            }

        //�������݉���
        } else if (XmlId.toString() == "RGEN2100"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2110
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "nm_user", frm.txtTantoName.value, 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    funXmlWrite(reqAry[i], "kbn_shori",hidShoriMode , 0);
                    break;
            }

        //��к��ޑI��
        } else if (XmlId.toString() == "RGEN2090") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2100
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    break;
            }
        }
    }

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
    var XmlId = "RGEN2100";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2110");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2110I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2110O);
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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2100, xmlReqAry, xmlResAry, 1) == false) {
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
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //�ꗗ�̸ر
    funClearList();

    //�����ޯ���̍Đݒ�
    //�S���Ҍ������[�h
    if(hidShoriMode == "1"){
    	frm.ddlKaisha.selectedIndex = 0;
	    funClearSelect(frm.ddlBusho, 2);
    }
    //��i�������[�h
    else{
    	//�������Ȃ�
    }

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
    xmlFGEN2110O.src = "";
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
    window.returnValue = funXmlRead(xmlFGEN2110O, "id_user", funGetCurrentRow()) + ":" + funXmlRead(xmlFGEN2110O, "nm_user", funGetCurrentRow());

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

