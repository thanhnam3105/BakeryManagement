//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConGenryoInfoMstId);

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

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.5
	//�����t�H�[�J�X�̐ݒ�
	document.frm00.txtGenryoName.focus();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// ��ЃR���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F��ЂɕR�t���H��R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0420";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA180");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA180I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA180O);

    if (frm.ddlKaisha.selectedIndex == -1) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlKojo, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //�H������擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0420, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlKojo, xmlResAry[2], 2);

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	frm.rdoShiyo[0].disabled = true;
	frm.rdoShiyo[1].checked = "true";
//add end --------------------------------------------------------------------------------------

    return true;

}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//========================================================================================
// �H��R���{�{�b�N�X�A������
// �쐬�ҁFk-katayama
// �쐬���F2010/07/08
// ����  �F�Ȃ�
//========================================================================================
function funChangeKojo() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //�I���H��̎擾
	var selectKojo = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value;
	var selectKojoNm = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].innerText;

	// �H�ꖢ�I���̏ꍇ�A�g�p���т��g�p�s�ɂ���
	if ( selectKojo == "" || selectKojoNm == "�V�K�o�^����") {
		frm.rdoShiyo[0].disabled = true;
		frm.rdoShiyo[1].checked = "true";

	} else {
		frm.rdoShiyo[0].disabled = false;
		frm.rdoShiyo[0].checked = "true";

	}

}
//add end --------------------------------------------------------------------------------------

//========================================================================================
// �����{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F�����f�[�^�̌������s��
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
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F�����f�[�^�̌������s��
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0430";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA390");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA390I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA390O);
    var PageCnt;
    var RecCnt;
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

    //���������Ɉ�v���錴���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0430, xmlReqAry, xmlResAry, 1) == false) {
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
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

    //�ꗗ�̸ر
    funClearList();
    xmlSA390O.src = "";

    //��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
    xmlSA140O.load(xmlSA140);
    xmlSA180O.load(xmlSA180);

    //�����ޯ���̍Đݒ�
    funCreateComboBox(frm.ddlKaisha, xmlSA140O, 1);
    funDefaultIndex(frm.ddlKaisha, 1);
    funCreateComboBox(frm.ddlKojo, xmlSA180O, 2);
    funDefaultIndex(frm.ddlKojo, 2);

    //̫����ݒ�
    if (frm.btnSearch.disabled) {
        frm.txtGenryoCd.focus();
    } else {
        frm.btnSearch.focus();
    }

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	//�H��I��������
	funChangeKojo();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
//    xmlSA390O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
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
// ���͒l���͉�ʋN������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�@StartMode �F�N�����[�h
//           1:�ڍׁA2:�V�K����
// �T�v  �F���͒l���͉�ʂ��N������
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/06/24
// ���e  �F�H��R���{�Ŗ��I�����\�ɂ���(�ۑ�\��16)
//========================================================================================
function funOpenGenryoInput(StartMode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;

    if (StartMode == 1) {
        //�s���I������Ă��Ȃ��ꍇ
        if (funGetCurrentRow().toString() == "") {
            funErrorMsgBox(E000002);
            return false;
        }
    }

    //���Ұ��̐ݒ�
    frm.hidMode.value = StartMode;
    if (StartMode == 1) {
        frm.hidKaishaCd.value = funXmlRead(xmlSA390O, "cd_kaisha", funGetCurrentRow());
        if (frm.ddlKojo.selectedIndex > 0) {
            //�H��R���{�I����
            frm.hidKojoCd.value = frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value;
        } else {
            //�H��R���{���I����
            frm.hidKojoCd.value = funXmlRead(xmlSA390O, "cd_busho", funGetCurrentRow());
        }
        frm.hidGenryoCd.value = funXmlRead(xmlSA390O, "cd_genryo", funGetCurrentRow());
    }

    //���͒l���͉�ʂ��N������
//20160610  KPX@1502111_No.5 MOD start
//    retVal = funOpenModalDialog("../SQ051GenryoInput/SQ051GenryoInput.jsp", this, "dialogHeight:750px;dialogWidth:800px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ051GenryoInput/SQ051GenryoInput.jsp", this, "dialogHeight:800px;dialogWidth:800px;status:no;scroll:no");
//20160610  KPX@1502111_No.5 MOD end

    //�������s���Ă���A�����͒l���͂œo�^�������s��ꂽ�ꍇ
    if (xmlSA390O.xml != "" && retVal == "1") {
        //�Č�������
        funSearchData();
        //�I���s�̸ر
        funSetCurrentRow("");
    }

    return true;

}

//========================================================================================
// �폜�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �F�I���s�̍폜�������s��
//========================================================================================
function funDelete() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0440";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA370");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA370I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA370O);
    var resMsg;

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�m�Fү���ނ̕\��
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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0440, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //����ү���ނ̕\��
    resMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //����
        funInfoMsgBox(resMsg);
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //�Č�������
    funSearch();
    //�I���s�̸ر
    funSetCurrentRow("");

    return true;

}

//========================================================================================
// Excel�o�̓{�^������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �T�v  �FCSV�t�@�C���̏o�͂��s��
//========================================================================================
function funOutput() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0450";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA360");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA360I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA360O);
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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0450, xmlReqAry, xmlResAry, 1) == false) {
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
    frm.strFilePath.value = funXmlRead(xmlSA360O, "URLValue", 0);

    //������ү���ޔ�\��
    funClearRunMessage();

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, document.frm00);

    return true;

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0410";
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA180","SA880");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA180I,xmlSA880I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA180O,xmlSA880O);
//add end --------------------------------------------------------------------------------------

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0410, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

/*    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    funCreateComboBox(frm.ddlKojo, xmlResAry[3], 2);*/

    //��̫�Ă̺����ޯ���ݒ�l��ޔ�
    xmlSA140.load(xmlResAry[2]);
    xmlSA180.load(xmlResAry[3]);

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
    //�g�p���т̃��x�����擾�E�ݒ�
    var shiyoNm = funXmlRead(xmlResAry[4], "nm_shiyo", 0);
    var shiyoNmLst = shiyoNm.substring(0,1) + "<br>" + shiyoNm.substring(1,2);
    var objShiyoNm = document.getElementById("divShiyoNm");
    var objShiyoNmLst = document.getElementById("divShiyoNmLst");
    objShiyoNm.innerHTML = shiyoNm;
    objShiyoNmLst.innerHTML = shiyoNmLst;

    //�g�p���уt���O�̏����ݒ�
	frm.rdoShiyo[1].checked = "true";
	frm.rdoShiyo[0].disabled = "true";		//�g�p���� ���g�p
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�ڍׁA�V�K�����A�폜���݂̐���
    frm.btnSearch.disabled = true;
    frm.btnEdit.disabled = true;
    frm.btnNew.disabled = true;
    frm.btnDel.disabled = true;
    frm.btnExcel.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //�������͏��Ͻ�����ݽ
        if (GamenId.toString() == ConGmnIdGenryoInfoMst.toString()) {
            //�{��
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnSearch.disabled = false;

            //�ҏW
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnSearch.disabled = false;
                frm.btnDel.disabled = false;
            }

        //�������͏��Ͻ�CSV
        } else if (GamenId.toString() == ConGmnIdGenryoInfoCsv.toString()) {
            //CSV�o��
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnExcel.disabled = false;
            }

        //���͒l����(��������)
        } else if (GamenId.toString() == ConGmnIdGenryoInputUpd.toString()) {
            //�{��
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnEdit.disabled = false;

            //�ҏW(�V�K�����̂�)
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnEdit.disabled = false;

            //�ҏW(�S��)
            } else if (KinoId.toString() == ConFuncIdAll.toString()) {
                frm.btnEdit.disabled = false;
// 20160513  KPX@1600766 ADD start
            //�ҏW(�V�X�e���Ǘ��ҁE�p�~���싖�j
            } else if (KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnEdit.disabled = false;
// 20160513  KPX@1600766 ADD end
            }

        //���͒l����(�V�K����)
        } else if (GamenId.toString() == ConGmnIdGenryoInputNew.toString()) {
            //�V�K
            if (KinoId.toString() == ConFuncIdNew.toString()) {
                frm.btnNew.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
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
        if (XmlId.toString() == "JSP0410") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
                case 2:    //SA180
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //��к��ޑI��
        } else if (XmlId.toString() == "JSP0420"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA180
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //�������݉���
        } else if (XmlId.toString() == "JSP0430"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA390
                    if (frm.chkGenryo[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkGenryo[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojo", frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
                    //�g�p����
                    if (frm.rdoShiyo[0].checked
                    	&& frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value != "" ) {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "", 0);
                    }
//add end --------------------------------------------------------------------------------------

                    break;
            }

        //�폜���݉���
        } else if (XmlId.toString() == "JSP0440"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA370
                    funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA390O, "cd_kaisha", funGetCurrentRow()), 0);
                    funXmlWrite(reqAry[i], "cd_genryo", funXmlRead(xmlSA390O, "cd_genryo", funGetCurrentRow()), 0);
                    funXmlWrite(reqAry[i], "flg_haishi", funXmlRead(xmlSA390O, "kbn_haishi", funGetCurrentRow()), 0);
                    break;
            }

        //Excel���݉���
        } else if (XmlId.toString() == "JSP0450"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA360
                    if (frm.chkGenryo[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkGenryo[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    funXmlWrite(reqAry[i], "cd_genryo", frm.txtGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_genryo", frm.txtGenryoName.value, 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojo", frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value, 0);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
                    //�g�p����
                    if (frm.rdoShiyo[0].checked
                    	&& frm.ddlKojo.options[frm.ddlKojo.selectedIndex].value != "" ) {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "flg_shiyo", "", 0);
                    }
//add end --------------------------------------------------------------------------------------
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/06/24
// ���e  �F�H��R���{�Ŗ��I�����\�ɂ���(�ۑ�\��16)
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;                  //�ݒ�XML�̌���
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //�����ޯ���̸ر
    funClearSelect(obj, 1);

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
        case 1:    //���Ͻ�
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 2:    //�H��Ͻ�
            //�󔒍s�̒ǉ�
            funClearSelect(obj, 2);
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
            obj.options[0].innerText = "�S�H��_�c";
//add end --------------------------------------------------------------------------------------

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

    //�����S����Ђ����o�^�̏ꍇ
    if (obj.length == 0) {
        //�󔒍s�̒ǉ�
        funClearSelect(obj, 2);
        return true;
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/06/24
// �T�v  �F�H��R���{�Ŗ��I�����\�ɂ���(�ۑ�\��16)
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //��к���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //�H�����
/*                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_busho", 0)) {
                    selIndex = i;
                }*/
                selIndex = 0;
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �p�~�����w�i�F�ύX����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�p�~�����̔w�i�F��ύX����
//========================================================================================
function funChangeHaishiColor(obj, mode) {

    var i;
    var reccnt = funGetLength(xmlSA390O);

    //�w�i�F�̕ύX
    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlSA390O, "kbn_haishi", i) == "1") {
            //�p�~�̏ꍇ
            tblList.rows(i).style.backgroundColor = haishiRowColor;
        } else {
            //�g�p�\�̏ꍇ
            tblList.rows(i).style.backgroundColor = deactiveSelectedColor;
        }
    }

    return true;
}

//========================================================================================
// �I���s����(���[�J����)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/01
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̔w�i�F��ύX����B
//========================================================================================
function funChangeSelectRowColorLocal() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (funGetCurrentRow() == "" ? 0 : funGetCurrentRow() / 1);

        //�w�i�F��ύX
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //�w�i�F��߂�
            if (funXmlRead(xmlSA390O, "kbn_haishi", BeforeRow) == "0") {
                //�g�p�\
                oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
            } else {
                //�p�~
                oTBL.rows(BeforeRow).style.backgroundColor = haishiRowColor;
            }
        }

        //���čs�̑ޔ�
        funSetCurrentRow(oTR.rowIndex);
    }

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

