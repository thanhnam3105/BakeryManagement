//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConShisakuListId);

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
// ���@No�̓��͐��䏈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F���@No�̓��͐�����s��
//========================================================================================
function funUseSeihoNo(obj) {

    var frm = document.frm00;    //̫�тւ̎Q��

    if (obj.checked) {
        //���@No���͉\
        funItemDisabled(frm.txtSeihouNo, false);
    } else {
        //���@No���͕s��
        funItemDisabled(frm.txtSeihouNo, true);
    }

}

//========================================================================================
// �����`�[���R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F�����O���[�v�ɕR�t�������`�[���R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeGroup() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlTeam, 2);
        funClearSelect(frm.ddlTanto, 2);
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
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2);
    funClearSelect(frm.ddlTanto, 2);

    return true;

}

//========================================================================================
// �S���҃R���{�{�b�N�X�A������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F�����O���[�v�A�����`�[���ɕR�t���S���҃R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeTeam() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0320";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA250");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA250I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA250O);

    if (frm.ddlTeam.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlTanto, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //�S���ҏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0320, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlTanto, xmlResAry[2], 3);

    return true;

}

//========================================================================================
// �����{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    //�߰�ނ̐ݒ�
    funSetCurrentPage(1);

    //�ް��擾
    funDataSearch();

}

//========================================================================================
// ��������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F����f�[�^�̌������s��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0330";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA200");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA200I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA200O);
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

    //���������Ɉ�v���鎎���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0330, xmlReqAry, xmlResAry, 1) == false) {
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

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();
    frm.hidJwsMode.value = "";
    funItemDisabled(frm.txtSeihouNo, true);
    frm.ddlUser.selectedIndex = 0;
    frm.ddlGenre.selectedIndex = 0;
    frm.ddlHyoujiName.selectedIndex = 0;
//    frm.ddlYouto.selectedIndex = 0;
//    frm.ddlGenryo.selectedIndex = 0;
    frm.txtYouto.value = "";
    frm.txtGenryo.value = "";

    //�ꗗ�̸ر
    funClearList();

    //��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
    xmlSA050O.load(xmlSA050);
    xmlSA080O.load(xmlSA080);
    xmlSA250O.load(xmlSA250);

    //�����ޯ���̍Đݒ�
    funCreateComboBox(frm.ddlGroup, xmlSA050O, 1);
    funDefaultIndex(frm.ddlGroup, 1);
    funCreateComboBox(frm.ddlTeam, xmlSA080O, 2);
    funDefaultIndex(frm.ddlTeam, 2);
    funCreateComboBox(frm.ddlTanto, xmlSA250O, 3);
    funDefaultIndex(frm.ddlTanto, 3);

    //̫����ݒ�
    frm.chkTaisho[0].focus();

    funSaveKengenInfo();

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlSA200O.src = "";
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
// �쐬���F2009/03/25
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //���C�����j���[
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// ���������N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F��������������������
//========================================================================================
function funJokenClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����(����No�`��ܰ�ތ����܂ł�ر)
    frm.txtShisakuNo.value = "";
    frm.txtSeihouNo.value = "";
    frm.txtShisakuName.value = "";
    frm.ddlGroup.selectedIndex = 0;
    frm.ddlTeam.selectedIndex = 0;
    frm.ddlTanto.selectedIndex = 0;
    frm.ddlUser.selectedIndex = 0;
    frm.ddlGenre.selectedIndex = 0;
    frm.ddlHyoujiName.selectedIndex = 0;
//    frm.ddlYouto.selectedIndex = 0;
//    frm.ddlGenryo.selectedIndex = 0;
    frm.txtYouto.value = "";
    frm.txtGenryo.value = "";
    frm.txtKeyword.value = "";

    //�����ޯ���̸ر
    funClearSelect(frm.ddlTeam, 2);
    funClearSelect(frm.ddlTanto, 2);

    //�ꗗ�̸ر
    xmlSA200O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

    return true;

}

//========================================================================================
// ����f�[�^��ʋN������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�@StartMode �F�N�����[�h
//           1:�V�K�A2:�ڍׁA3:���@�x���R�s�[
// �T�v  �F����f�[�^��ʂ��N������
//========================================================================================
function funJavaWebStart(StartMode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0340";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA550");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA550I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA550O);

    //�V�K�ȊO�ōs���I������Ă��Ȃ��ꍇ
    if (StartMode != 2 && funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //VM���ް�ޮ��������s��
    funGetJWSInstall();
    if (funCheckVersion() == false) {
        frm.hidJwsMode.value = "";
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //�N��Ӱ�ނ̐ݒ�
    switch (StartMode) {
        case 1:    //�����ް����(�ڍ�)
            frm.hidJwsMode.value = ConGmnIdShisakuDataEdit;
            break;
        case 2:    //�����ް����(�V�K)
            frm.hidJwsMode.value = ConGmnIdShisakuDataNew;
            break;
        case 3:    //�����ް����(���@�x����߰)
            frm.hidJwsMode.value = ConGmnIdSeihoCopy;
            break;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        frm.hidJwsMode.value = "";
        return false;
    }

    //JNLP̧�ق��쐬����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0340, xmlReqAry, xmlResAry, 1) == false) {
        //�yQP@20505�zNo.39 2012/09/25 TT H.Shima ADD Start
        funAccessLogSave(StartMode,1);
        //�yQP@20505�zNo.39 2012/09/25 TT H.Shima ADD End
        frm.hidJwsMode.value = "";
        return false;
    }

//�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start
    funAccessLogSave(StartMode,0);
//�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End

    //������ү���ޔ�\��
    funClearRunMessage();

    var width = screen.width / 2 - 200;
    var height = screen.height / 2 + 50;

    //�����ް���ʂ��N������
    window.open("../../jws/" + funXmlRead(xmlResAry[2], "jnlp_path", 0), "", "top=" + width + ",left=" + height + ",width=100,height=100");

    return true;

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0310";
//    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA070","SA080","SA120","SA230","SA250","SA280");
//    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA070I,xmlSA080I,xmlSA120I,xmlSA230I,xmlSA250I,xmlSA280I);
//    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA070O,xmlSA080O,xmlSA120O,xmlSA230O,xmlSA250O,xmlSA280O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA070","SA080","SA120","SA250");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA070I,xmlSA080I,xmlSA120I,xmlSA250I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA070O,xmlSA080O,xmlSA120O,xmlSA250O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0310, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlUser, xmlResAry[2], 4);
    funCreateComboBox(frm.ddlGenre, xmlResAry[4], 4);
    funCreateComboBox(frm.ddlHyoujiName, xmlResAry[6], 4);
//    funCreateComboBox(frm.ddlYouto, xmlResAry[7], 4);
//    funCreateComboBox(frm.ddlGenryo, xmlResAry[9], 4);

    //��̫�Ă̺����ޯ���ݒ�l��ޔ�
    xmlSA050.load(xmlResAry[3]);
    xmlSA080.load(xmlResAry[5]);
//    xmlSA250.load(xmlResAry[8]);
    xmlSA250.load(xmlResAry[7]);

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�ڍׁA�V�K�A���@�x����߰���݂̐���
    frm.btnEdit.disabled = true;
    frm.btnNew.disabled = true;
    frm.btnCopy.disabled = true;

    //�������Z���݂̐���
    frm.btnGenka.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //�����ް��ꗗ
        if (GamenId.toString() == ConGmnIdShisakuList.toString()) {
            hidShisakuListKengen.value = funXmlRead(obj, "id_data", i);
            //�������Z
            if (KinoId.toString() == ConFuncIdGenkaShisan.toString()) {
                frm.chkTaisho[3].checked = "true";

            }

        //�����ް����(�ڍ�)
        } else if (GamenId.toString() == ConGmnIdShisakuDataEdit.toString()) {
            //�{��
            if (KinoId.toString() == ConFuncIdRead.toString()) {
                frm.btnEdit.disabled = false;

            //�ҏW
            } else if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnEdit.disabled = false;
            }

        //�����ް����(�V�K)
        } else if (GamenId.toString() == ConGmnIdShisakuDataNew.toString()) {
            //�ҏW
            if (KinoId.toString() == ConFuncIdNew.toString()) {
                frm.btnNew.disabled = false;
            }

        //�����ް����(���@�x����߰)
        } else if (GamenId.toString() == ConGmnIdSeihoCopy.toString()) {
            //�ҏW
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnCopy.disabled = false;
            }
        }

        //�������Z���
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {
            frm.btnGenka.disabled = false;
        }
    }
    return true;

}

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
        //��ʏ����\��
        if (XmlId.toString() == "JSP0310") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA030
                    funXmlWrite(reqAry[i], "cd_category", "K_yuza", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 3:    //SA070
                    funXmlWrite(reqAry[i], "cd_category", "K_jyanru", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 4:    //SA080
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
                case 5:    //SA120
                    funXmlWrite(reqAry[i], "cd_category", "K_ikatuhyouzi", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
//                case 6:    //SA230
//                    funXmlWrite(reqAry[i], "cd_category", "K_yoto", 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
//                    break;
//                case 7:    //SA250
                case 6:    //SA250
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "cd_team", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
//                case 8:    //SA280
//                    funXmlWrite(reqAry[i], "cd_category", "K_tokucyogenryo", 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
//                    break;
            }

        //��Ѻ��ޑI��
        } else if (XmlId.toString() == "JSP0320"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA250
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    // ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 start
                    // �S���҃R���{�{�b�N�X�ݒ�F�I�������O���[�v�̉��CD�i��s�����j
                    funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA050, "cd_kaisha",frm.ddlGroup.selectedIndex-1), 0);
                    // ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 end
                    break;
            }

        //�������݉���
        } else if (XmlId.toString() == "JSP0330"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA200
                    if (frm.chkTaisho[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken1", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken1", "", 0);
                    }
                    if (frm.chkTaisho[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken2", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken2", "", 0);
                    }
                    if (frm.chkTaisho[2].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken3", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken3", "", 0);
                    }

               //2009/10/27 TT.Y.NISHIGAWA ADD START   [�������Z�F�����˗��������Ώ�]
                    if (frm.chkTaisho[3].checked) {
                        funXmlWrite(reqAry[i], "kbn_joken4", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_joken4", "", 0);
                    }
               //2009/10/27 TT.A.ISONO ADD END   [�������Z�F�����˗��������Ώ�]

                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_tanto", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_user", frm.ddlUser.options[frm.ddlUser.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_genre", frm.ddlGenre.options[frm.ddlGenre.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_ikatu", frm.ddlHyoujiName.options[frm.ddlHyoujiName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }

        //JNLP�쐬
        } else if (XmlId.toString() == "JSP0340"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA550
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    if (frm.hidJwsMode.value == ConGmnIdShisakuDataNew) {
                        funXmlWrite(reqAry[i], "no_shisaku", "0-0-0", 0);
                    } else {
                        funXmlWrite(reqAry[i], "no_shisaku", funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow()), 0);
                    }
                    funXmlWrite(reqAry[i], "mode", frm.hidJwsMode.value, 0);
                    break;
            }

        //��ٰ�ߺ��ޑI��
        } else if (XmlId.toString() == "JSP9020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShisakuList, 0);
                    break;
            }
        }

        //�������Z��ʋN�����ʒm
        else if (XmlId.toString() == "RGEN1090"){

            var get_shisaku;   // ����R�[�h�擾�p
            var put_shisaku;   // ����R�[�h���M�p

            // XML��莎��R�[�h�擾
            no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());

            // ����R�[�h�u���y�Ј�CD::�N:::�ǔԁz
            put_shisaku = no_shisaku.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_shisaku, 0);
                    break;
            }
        }
        //�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start
        //����A�N�Z�X���O�o��
        else if (XmlId.toString() == "JSP0350"){
        	switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //SA530
            	var no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());
            	var shisaku = no_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);

                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", frm.hidJwsMode.value, 0);
                funXmlWrite(reqAry[i], "not_right", "NULL", 0);
                break;
        	}
        }
        else if (XmlId.toString() == "JSP0351"){
        	switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //SA530
            	var no_shisaku = funXmlRead(xmlSA200O, "no_shisaku", funGetCurrentRow());
            	var shisaku = no_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);

                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", frm.hidJwsMode.value, 0);
                funXmlWrite(reqAry[i], "not_right", 1, 0);
                break;
        	}
        }
      //�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End
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
        case 1:    //��ٰ��Ͻ�
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 2:    //���Ͻ�
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        case 3:    //հ�ްϽ�
            atbName = "nm_user";
            atbCd = "id_user";
            break;
        case 4:    //����Ͻ�
            atbName = "nm_literal";
            atbCd = "cd_literal";
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
// �f�t�H���g�l�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
            case 1:    //��ٰ�ߺ���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //��Ѻ���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_team", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //�S���Һ���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "id_user", 0)) {
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
// �u�l�o�[�W�����`�F�b�N
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F�T�[�o�ƃN���C�A���g�̂u�l�̃o�[�W�������r����
//========================================================================================
function funCheckVersion() {

//alert(myVersion);

	if (javawsInstalled == 1) {
        //�����ް�ޮ݂��ݽİق���Ă���ꍇ
        return true;

    } else {
        //�����ް�ޮ݂��ݽİق���Ă��Ȃ��ꍇ


        if(myVersion == 6){
        	//if (funConfMsgBox(I000009) == ConBtnYes) {
	            //�ݽİװ�N��
	            location.href = ConJreInstallUrl;
	        //}
        }
        else if(myVersion >= 7){
        	//if (funConfMsgBox(I000013) == ConBtnYes) {
	            //�ݽİװ�N��
	            //location.href = ConJreInstallUrl_5;
	            location.href = ConJreInstallUrl_6;
	        //}
        }
        else{
        }

        //JRE�ݽİيm�Fү����
        /*if (funConfMsgBox(I000009) == ConBtnYes) {
            //�ݽİװ�N��
            location.href = ConJreInstallUrl;
        }*/

        return false;
    }

}

//========================================================================================
// �y�[�W�J��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(NextPage);

    //�w���߰�ނ��ް��擾
    funDataSearch();
}

//========================================================================================
// �������Z��ʂ֑J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�Ȃ�
// �T�v  �F�������Z��ʂ֑J�ڂ���
//========================================================================================
function funGenkaShisan() {

    //�u$�v�L���i�[
    var dara;

    //�I���s�́u$�v�L���擾
    dara = funXmlRead(xmlSA200O, "dara", funGetCurrentRow());

    //�����˗��s���I������Ă���ꍇ
    if(dara == GenkaMark){

        //�������Z��ʋN�����ʒm�ɐ���
        if(funGenkaTuti(1)){
            //�������Z��ʂ֑J��
            //funUrlConnect("../SQ110GenkaShisan/GenkaShisan.jsp", "POST", document.frm00);

            window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=no,resizable=yes");

        }
        //�������Z��ʋN�����ʒm�Ɏ��s
        else{

        }

    }
    //�����˗��s���I������Ă��Ȃ��ꍇ
    else{
        //�G���[���b�Z�[�W��\��
        funErrorMsgBox(E000005);
        return false;
    }

    return true;

}

//========================================================================================
// �������Z��ʋN�����ʒm
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/27
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funGenkaTuti(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1090";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1090, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;

}

//========================================================================================
// ��ʎQ�ƃ��O�̒ǉ�
// �쐬�ҁFH.Shima
// �쐬���F2012/9/20
// ����  �Fmode     �F���[�h  1,�ڍ� 2,�V�K 3,���@�x���R�s�[
//       �Fkengen   �F���� 0,�����L 1,������
// �T�v  �F�Q�Ɖ�ʂƎQ�ƃ��[�U�̃��O��ۑ�����
//========================================================================================
function funAccessLogSave(mode, kengen) {
    var mId = new Array(["JSP0350"]		//�����L
    					  ,["JSP0351"]);//������
    var XmlId = mId[kengen];
    var mFuncIdAry = new Array(ConResult,ConUserInfo,"SA530");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA530I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA530O);

    //������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	switch(mode){
	case 1:	//�ڍ�
		if (funAjaxConnection(XmlId, mFuncIdAry, xmlSA530, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
		break;
	case 2:	//�V�K
		break;
	case 3:	//���@�x���R�s�[
		if (funAjaxConnection(XmlId, mFuncIdAry, xmlSA530, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
		break;
	}

}