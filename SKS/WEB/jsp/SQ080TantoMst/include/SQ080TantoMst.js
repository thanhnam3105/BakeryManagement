//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConTantoMstId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    // DEL 2013/9/25 okano�yQP@30151�zNo.28 start
//    funClear();
    // DEL 2013/9/25 okano�yQP@30151�zNo.28 end

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// ���[�UID���X�g�t�H�[�J�X����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F���[�UID�ɕR�t���f�[�^���擾���\������
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0820";
	// MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080","SA210","SA260","SA290");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I,xmlSA210I,xmlSA260I,xmlSA290I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O,xmlSA210O,xmlSA260O,xmlSA290O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080","SA210","SA260","SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I,xmlSA210I,xmlSA260I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O,xmlSA210O,xmlSA260O,xmlSA290O,xmlSA050O);
	// MOD 2013/11/7 QP@30154 okano end

    //հ��ID�������͂̏ꍇ
    if (frm.txtUserId.value == "") {
        funClear();
        return true;
    }

    // DEL 2013/9/25 okano�yQP@30151�zNo.28 start
//    //������ү���ޕ\��
//    funShowRunMessage();
    // DEL 2013/9/25 okano�yQP@30151�zNo.28 end
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //հ�ޏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0820, xmlReqAry, xmlResAry, 1) == false) {
//�yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
        //���[�UID������
        frm.txtUserId.value = "";

        frm.txtUserId.focus();
//�yQP@10713�z2011/10/28 TT H.SHIMA -ADD End
        return false;
    }
    // ADD 2013/11/20 QP@30154 okano start
    frm.hidEditMode2.value = funXmlRead(xmlResAry[4], "md_edit", 0);
    // ADD 2013/11/20 QP@30154 okano end

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[5], 3);
	// ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[6], 4);
	// ADD 2013/11/7 QP@30154 okano end
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 5);

    //�l�̐ݒ�
    funSetData();

    // ADD 2014/06/11 hisahori�yQP@30151�z�ۑ�No.41 start
    funChangeKaisha2();
    // ADD 2014/06/11 hisahori�yQP@30151�z�ۑ�No.41 end

    //�V�Kհ�ނ̏ꍇ
    if (funXmlRead(xmlResAry[4], "password", 0) == "") {
        xmlResAry[3].src = "";
    }

    //�ް�����������
    if (funGetLength(xmlResAry[3]) > 0 && funXmlRead(xmlResAry[3], "flg_return", 0) == "true" && funXmlRead(xmlResAry[3], "cd_kaisha", 0) != "") {
        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";

        // DEL 2013/9/25 okano�yQP@30151�zNo.28 start
//        //������ү���ޔ�\��
//        funClearRunMessage();
        // DEL 2013/9/25 okano�yQP@30151�zNo.28 end
    }

    return true;

}

//========================================================================================
// ���[�UID�ύX��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funChangeUserId() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    xmlSA210O.src = "";
    tblList.style.display = "none";

    //�����ޯ���̍Đݒ�
    funClearSelect(frm.ddlBusho, 2);
    funClearSelect(frm.ddlTeam, 2);

    //�l�̐ݒ�
    frm.txtPass.value = "";
    frm.ddlKengen.selectedIndex = 0;
    frm.txtUserName.value = "";
    frm.ddlKaisha.selectedIndex = 0;
    frm.ddlBusho.selectedIndex = 0;
    frm.ddlGroup.selectedIndex = 0;
    frm.ddlTeam.selectedIndex = 0;
    frm.ddlYakushoku.selectedIndex = 0;

    return true;

}

//========================================================================================
// �S���Ҍ�����ʋN������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�S���Ҍ�����ʂ��N������
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/04/24
// ���e  �F���X�g�̕\���������L����(�ۑ�\��14)
//========================================================================================
function funSearchUser() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;

    //�S���Ҍ�����ʂ��N������
//    retVal = funOpenModalDialog("../SQ081TantoSearch/SQ081TantoSearch.jsp", this, "dialogHeight:720px;dialogWidth:740px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ081TantoSearch/SQ081TantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //հ��ID�̐ݒ�
        frm.txtUserId.value = retVal;

//�yQP@10713�z2011/10/28 TT H.SHIMA -MOD Start
//funSearch���ĂԂ�jsp����onBlur��funSearch()���Ă΂��d�����ɂȂ�B
        //�S���ҏ��̕\��
        //funSearch();

// 2015/03/03 TT.Kitazawa�yQP@40812�zADD start�ionBlur�C�x���g�����܂��������Ȃ��ׁj
        frm.txtUserId.onblur();
// 2015/03/03 TT.Kitazawa�yQP@40812�zADD end

        //̫����ݒ�
        frm.txtPass.focus();
//�yQP@10713�z2011/10/28 TT H.SHIMA -MOD End
    }

    return true;

}

//========================================================================================
// ��ЃR���{�{�b�N�X�A�������i�����ƁA�O���[�v�j
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
        funClearSelect(frm.ddlTeam, 2);
        // ADD 2013/11/7 QP@30154 okano end
        return true;
    }
    // ADD 2013/11/7 QP@30154 okano start
    funClearSelect(frm.ddlTeam, 2);
    // ADD 2013/11/7 QP@30154 okano end

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 3);
	// ADD 2013/11/7 QP@30154 okano start
    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 4);
	// ADD 2013/11/7 QP@30154 okano end

    return true;

}

//========================================================================================
// ��ЃR���{�{�b�N�X�A�������i�O���[�v�̂݁j
// �쐬�ҁFT.Hisahori
// �쐬���F2014/06/11
// ����  �F�Ȃ�
// �T�v  �F��ЂɕR�t���O���[�v�R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKaisha2() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","SA050");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlSA050I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlSA050O);

 // 2015/07/30 TT.Kitazawa�yQP@40812�zADD start
    // �V�K�Ј��ԍ��̏ꍇ
    if(frm.hidEditMode2.value == "cash"){
    	return false;
    }
    // 2015/07/30 TT.Kitazawa�yQP@40812�zADD end

if (frm.ddlGroup.selectedIndex == 0 || frm.ddlGroup.options[frm.ddlGroup.selectedIndex].text == "") {

        funClearSelect(frm.ddlGroup, 2);
        funClearSelect(frm.ddlTeam, 2);

        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
            return false;
        }

        //���������擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9010, xmlReqAry, xmlResAry, 1) == false) {
            return false;
        }

        //�����ޯ���̍쐬
        funCreateComboBox(frm.ddlGroup, xmlResAry[3], 4);

    }
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
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 5);

    return true;

}

//========================================================================================
// �����S����Вǉ���ʋN������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�����S����Вǉ���ʂ��N������
//========================================================================================
function funAddList() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //�����S����Вǉ���ʂ��N������
    retVal = funOpenModalDialog("../SQ082KasihaAdd/SQ082KasihaAdd.jsp", this, "dialogHeight:610px;dialogWidth:785px;status:no;scroll:no");

    if (retVal != "") {
        //�����S����Ђ̒ǉ�
        if (funXmlRead(xmlSA210O, "cd_kaisha", funGetLength(xmlSA210O)-1) != "" || funGetLength(xmlSA210O) == 0) {
            funAddRecNode(xmlSA210O, "SA210");
        }
        funXmlWrite(xmlSA210O, "cd_kaisha", retVal.split(ConDelimiter)[0], funGetLength(xmlSA210O)-1);
        funXmlWrite(xmlSA210O, "nm_kaisha", retVal.split(ConDelimiter)[1], funGetLength(xmlSA210O)-1);

        //�l��ݒ肵��XML�̍�۰��
        xmlBuff.load(xmlSA210O);
        xmlSA210O.load(xmlBuff);

        if (tblList.style.display == "none") {
           tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);
    }

    return true;

}

//========================================================================================
// �����S����Ѝ폜����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F�I���s�̐����S����Ђ��폜����
//========================================================================================
function funDelList() {

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�w�肳�ꂽ�S����Ђ��폜����
    funSelectRowDelete(xmlSA210O);

    return true;

}

//========================================================================================
// �o�^�A�X�V�A�폜�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�폜
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0830";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA270");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA270I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA270O);
    var dspMsg;
    // 2015/07/30 TT.Kitazawa�yQP@40812�zADD start
    // �Ј��ԍ������͂̏ꍇ�A�f�[�^�n���ŃG���[�ɂȂ��
    if (frm.txtUserId.value == "") {
    	funErrorMsgBox("�Ј��ԍ��͕K�{���ڂł��B\\n���͂��ĉ������B");
    	return false;
    }
    // 2015/07/30 TT.Kitazawa�yQP@40812�zADD end

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

    //XML�̏�����
    setTimeout("xmlSA270I.src = '../../model/SA270I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0830, xmlReqAry, xmlResAry, 1) == false) {
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

    	// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
    	//���o�^���[�U�̏ꍇ
    	if(hidKariMode == "1"){
    		funChkLogin();
    	}
    	// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

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
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();
    xmlSA210O.src = "";
    tblList.style.display = "none";

    //��ʂ̐���
    funInit();

    //�����ޯ���̍Đݒ�
    funClearSelect(frm.ddlBusho, 2);
    funClearSelect(frm.ddlTeam, 2);

    //̫����ݒ�
    frm.txtUserId.focus();

    return true;

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //Ͻ��ƭ�
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
        case 1:    //���o�^հ��
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 2:    //���o�^հ�ޏI����
        	wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 end
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

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

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0810";
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140","SA170","SA310");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I,xmlSA170I,xmlSA310I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O,xmlSA170O,xmlSA310O);
	// MOD 2013/11/7 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA050","SA140","SA170","SA310","FGEN2120");
//	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA050I,xmlSA140I,xmlSA170I,xmlSA310I,xmlFGEN2120I);
//	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA050O,xmlSA140O,xmlSA170O,xmlSA310O,xmlFGEN2120O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA170","SA310","FGEN2120");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA170I,xmlSA310I,xmlFGEN2120I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA170O,xmlSA310O,xmlFGEN2120O);
	// MOD 2013/11/7 QP@30154 okano end
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 end

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0810, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
	// MOD 2013/11/7 QP@30154 okano start
//	    funCreateComboBox(frm.ddlKengen, xmlResAry[4], 1);
//	    funCreateComboBox(frm.ddlKaisha, xmlResAry[3], 2);
//	    funCreateComboBox(frm.ddlGroup, xmlResAry[2], 4);
//	    funCreateComboBox(frm.ddlYakushoku, xmlResAry[5], 6);
    funCreateComboBox(frm.ddlKengen, xmlResAry[3], 1);
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 2);
    funCreateComboBox(frm.ddlYakushoku, xmlResAry[4], 6);
	// MOD 2013/11/7 QP@30154 okano end

    // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
    frm.txtUserId.value=funXmlRead_3(xmlResAry[5], "table", "id_user", 0, 0);
    hidKariMode=funXmlRead_3(xmlResAry[5], "table", "kbn_kari", 0, 0);
    if(hidKariMode == "1"){
        funSearch();
    }
    // ADD 2013/9/25 okano�yQP@30151�zNo.28 end

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

    //�o�^�A�X�V�A�폜���݂̐���
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //�S����Ͻ�����ݽ
        if (GamenId.toString() == ConGmnIdTantoMst.toString()) {
            //�ҏW
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                //�Ώۃf�[�^���S�Ă̏ꍇ
     // MOD 2014/06/11 start �yQP@30154�z�ۑ�No.41
     //           if (DataId == "9") {
     //               frm.btnInsert.disabled = false;
     //           }
                frm.btnInsert.disabled = false;
     // MOD 2014/06/11 end �yQP@30154�z�ۑ�No.41
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;

            //�ҏW(�����A�߽ܰ�ނ̂�)
            } else if (KinoId.toString() == ConFuncIdEdit2.toString()) {
                frm.btnUpdate.disabled = false;

            // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
            //�ҏW�i���o�^���[�U�j
            } else if (KinoId.toString() == ConFuncIdEditCash.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = true;
                frm.btnDelete.disabled = true;
                frm.btnClear.disabled = true;
                frm.btnSearchUser.disabled = true;
                funItemReadOnly(frm.txtUserId, true);
            } else if (KinoId.toString() == ConFuncIdEditPass.toString()) {
                frm.btnInsert.disabled = true;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = true;
                frm.btnClear.disabled = true;
                frm.btnSearchUser.disabled = true;
                funItemReadOnly(frm.txtUserId, true);
            // ADD 2013/9/25 okano�yQP@30151�zNo.28 end
            }

            hidMode.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// �擾�f�[�^�ݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/06
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�擾�����S���ҏ�����ʂɐݒ肷��
//========================================================================================
function funSetData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    //��ʂ̐���
    funInit();

    //���ݐ���
    // ADD 2013/11/20 QP@30154 okano start
    if(frm.hidEditMode2.value == "cash"){
    	funItemReadOnly(frm.txtPass, false);
	    funItemReadOnly(frm.ddlKengen, true);
    	funItemReadOnly(frm.txtUserName, false);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
	    // 2015/07/30 TT.Kitazawa�yQP@40812�zADD start
    	// �V�K�Ј��ԍ����͎��F�ҏW�����̐l�͌����R���{�{�b�N�X��������
    	if (hidMode.value == ConFuncIdEdit) {
    	    funItemReadOnly(frm.ddlKengen, false);
    	    funItemReadOnly(frm.ddlKaisha, false);
    	    funItemReadOnly(frm.ddlBusho, false);
    	    // �V�K�o�^�{�^���͊�����
    	    frm.btnInsert.disabled = false;
    	}
    	// 2015/07/30 TT.Kitazawa�yQP@40812�zADD end

    	funItemReadOnly(frm.ddlGroup, false);
	    funItemReadOnly(frm.ddlTeam, false);
	    funItemReadOnly(frm.ddlYakushoku, false);
	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 start
        frm.btnAddList.disabled = false;
        frm.btnDelList.disabled = false;
        frm.btnUpdate.disabled = true;  //�V���[�U�}�X�^�ɂ������݂��Ȃ����[�UID�̏ꍇ�A�X�V�{�^���񊈐�
	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 end

    } else {
    // ADD 2013/11/20 QP@30154 okano end
    if (hidMode.value == ConFuncIdEdit) {
        // ADD 2013/11/20 QP@30154 okano start
    	if(frm.hidEditMode2.value == "system"){
        	// 2015/07/30 TT.Kitazawa�yQP@40812�zMOD start
        	// ���͂����Ј��ԍ������ݎ��A�V�K�o�^�{�^���͔񊈐�
    		frm.btnInsert.disabled = true;
           	// 2015/07/30 TT.Kitazawa�yQP@40812�zMOD end
            frm.btnAddList.disabled = false;
            frm.btnDelList.disabled = false;
    	    funItemReadOnly(frm.txtPass, false);
    	    funItemReadOnly(frm.ddlKengen, false);
    	    funItemReadOnly(frm.txtUserName, false);
    	    funItemReadOnly(frm.ddlKaisha, false);
    	    funItemReadOnly(frm.ddlBusho, false);
    	    funItemReadOnly(frm.ddlGroup, false);
    	    funItemReadOnly(frm.ddlTeam, false);
    	    funItemReadOnly(frm.ddlYakushoku, false);
    	} else {
        // ADD 2013/11/20 QP@30154 okano end

    	// 2015/07/30 TT.Kitazawa�yQP@40812�zMOD start
    	// ���͂����Ј��ԍ������ݎ��A�V�K�o�^�{�^���͔񊈐�
        frm.btnInsert.disabled = true;
       	// 2015/07/30 TT.Kitazawa�yQP@40812�zMOD end
        frm.btnAddList.disabled = false;
        frm.btnDelList.disabled = false;
	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 start
        frm.btnUpdate.disabled = false;  //�V���[�U�}�X�^�ɂ������݂��Ȃ����[�UID�̏ꍇ�A�X�V�{�^���񊈐��������߁A�Č������Ɋ�����
	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 end

        //�t�H�[������
	    //2012/03/01 TT H.SHIMA Java6�Ή� add Start
        // MOD 2013/11/20 QP@30154 okano start
//		    funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, false);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, false);
//		    funItemReadOnly(frm.ddlBusho, false);
//		    funItemReadOnly(frm.ddlGroup, false);
//		    funItemReadOnly(frm.ddlTeam, false);
//		    funItemReadOnly(frm.ddlYakushoku, false);
	    funItemReadOnly(frm.txtPass, false);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.txtUserName, false);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
	    funItemReadOnly(frm.ddlGroup, false);
	    funItemReadOnly(frm.ddlTeam, false);
	    funItemReadOnly(frm.ddlYakushoku, false);
        // MOD 2013/11/20 QP@30154 okano end
	    //2012/03/01 TT H.SHIMA Java6�Ή� add End
	// DEL 2013/11/20 QP@30154 okano start

	// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
//		} else if (hidMode.value == ConFuncIdEditCash) {
//		    funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, true);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, true);
//		    funItemReadOnly(frm.ddlBusho, true);
//		    funItemReadOnly(frm.ddlGroup, false);
//		    funItemReadOnly(frm.ddlTeam, false);
//		    funItemReadOnly(frm.ddlYakushoku, false);
	// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
	// DEL 2013/11/20 QP@30154 okano end
	    // ADD 2013/11/20 QP@30154 okano start
    	}

	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 start
        frm.btnUpdate.disabled = false;  //�V���[�U�}�X�^�ɂ������݂��Ȃ����[�UID�̏ꍇ�A�X�V�{�^���񊈐��������߁A�Č������Ɋ�����
	    // ADD 2014/06/11 QP@30151 �ۑ�Mo.41 end

        }
        // ADD 2013/11/20 QP@30154 okano end

    }

    //�l�̐ݒ�
    frm.txtPass.value = funXmlRead(xmlSA260O, "password", 0);
    funDefaultIndex(frm.ddlKengen, 1);
    frm.txtUserName.value = funXmlRead(xmlSA260O, "nm_user", 0);
    funDefaultIndex(frm.ddlKaisha, 2);
    funDefaultIndex(frm.ddlBusho, 3);
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//    funDefaultIndex(frm.ddlGroup, 4);
//    funDefaultIndex(frm.ddlTeam, 5);
//    funDefaultIndex(frm.ddlYakushoku, 6);
    if(hidMode.value == ConFuncIdEditCash){
    	frm.ddlGroup.selectedIndex = 0;
    	frm.ddlTeam.selectedIndex = 0;
    	frm.ddlYakushoku.selectedIndex = 0;
    } else {
	    funDefaultIndex(frm.ddlGroup, 4);
	    funDefaultIndex(frm.ddlTeam, 5);
	    funDefaultIndex(frm.ddlYakushoku, 6);
    }
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start

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

  //2012/03/01 TT H.SHIMA Java6�Ή� mod Start
    //��ʂ̐���
//    if (hidMode.value == ConFuncIdEdit) {
//        //�ҏW
//        funItemReadOnly(frm.txtPass, false);
//        funItemReadOnly(frm.ddlKengen, false);
//        funItemReadOnly(frm.txtUserName, false);
//        funItemReadOnly(frm.ddlKaisha, false);
//        funItemReadOnly(frm.ddlBusho, false);
//        funItemReadOnly(frm.ddlGroup, false);
//        funItemReadOnly(frm.ddlTeam, false);
//        funItemReadOnly(frm.ddlYakushoku, false);
//    } else {
        //�ҏW(�����A�߽ܰ�ނ̂�)
        funItemReadOnly(frm.txtPass, false);
        funItemReadOnly(frm.ddlKengen, true);
        funItemReadOnly(frm.txtUserName, false);
        funItemReadOnly(frm.ddlKaisha, true);
        funItemReadOnly(frm.ddlBusho, true);
        funItemReadOnly(frm.ddlGroup, true);
        funItemReadOnly(frm.ddlTeam, true);
        funItemReadOnly(frm.ddlYakushoku, true);
//    }
      //2012/03/01 TT H.SHIMA Java6�Ή� mod End

    frm.btnAddList.disabled = true;
    frm.btnDelList.disabled = true;

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
    var j;

    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\��
        if (XmlId.toString() == "JSP0810") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                // MOD 2013/10/24 QP@30154 okano start
//	                case 1:    //SA050
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 2:    //SA140
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
//	                case 3:    //SA170
//	                    break;
//	                case 4:    //SA310
//	                    funXmlWrite(reqAry[i], "cd_category", "K_yakusyoku", 0);
//	                    funXmlWrite(reqAry[i], "id_user", "", 0);
//	                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
//	                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 2:    //SA170
                    break;
                case 3:    //SA310
                    funXmlWrite(reqAry[i], "cd_category", "K_yakusyoku", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                // MOD 2013/10/24 QP@30154 okano end
            }

        //հ��ID۽�̫���
        } else if (XmlId.toString() == "JSP0820"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 2:    //SA210
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
                case 3:    //SA260
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    break;
                case 4:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
               // ADD 2013/10/24 QP@30154 okano start
                case 5:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                break;
               // ADD 2013/10/24 QP@30154 okano end
            }

        //�o�^�A�X�V�A�폜���݉���
        } else if (XmlId.toString() == "JSP0830"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA270
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "id_user", frm.txtUserId.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "password", frm.txtPass.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "nm_user", frm.txtUserName.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "cd_yakushoku", frm.ddlYakushoku.options[frm.ddlYakushoku.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_user", "kbn_shori", frm.hidEditMode.value, 0);
                    //�����S����Ђ̐ݒ�
                    for (j = 0; j < funGetLength(xmlSA210O); j++) {
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "SA270", "ma_tantokaisya");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "id_user", frm.txtUserId.value, j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "cd_tantokaisha", funXmlRead(xmlSA210O, "cd_kaisha", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    if (funGetLength(xmlSA210O) == 0) {
                        if (reqAry[i].xml == "") {
                            funAddRecNode_Tbl(reqAry[i], "SA270", "ma_tantokaisya");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "id_user", frm.txtUserId.value, j);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "cd_tantokaisha", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_tantokaisya", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    break;
            }

        //��к��ޑI��
        } else if (XmlId.toString() == "JSP9010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
               // ADD 2013/10/24 QP@30154 okano start
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                break;
               // ADD 2013/10/24 QP@30154 okano end
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
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTantoMst, 0);
                    break;
            }
        }
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
        //�V���O���T�C���I��
        else if (XmlId.toString() == "JSP0010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    break;
                case 1:    //SA010
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                    funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                    funXmlWrite(reqAry[i], "kbn_login", "1", 0);
                    break;
            }
        }
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 end
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
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //�����ޯ���̸ر(�󔒂���)
    funClearSelect(obj, 2);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //����Ͻ�
            atbName = "nm_kengen";
            atbCd = "cd_kengen";
            break;
        case 2:    //����Ͻ�(���)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 3:    //����Ͻ�(����)
            atbName = "nm_busho";
            atbCd = "cd_busho";
            break;
        case 4:    //��ٰ��Ͻ�
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 5:    //���Ͻ�
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        case 6:    //����Ͻ�
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
// �쐬���F2009/04/06
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
            case 1:    //��������
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_kengen", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //��к���
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //��������
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_busho", 0)) {
                    selIndex = i;
                }
                break;
            case 4:    //��ٰ�ߺ���
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
            case 5:    //��Ѻ���
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_team", 0)) {
                    selIndex = i;
                }
                break;
            case 6:    //��E����
                if (obj.options[i].value == funXmlRead(xmlSA260O, "cd_yakushoku", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
function funClose(){

    if (hidMode.value == ConFuncIdEditKari.toString() || hidMode.value == ConFuncIdEditPass.toString() || hidMode.value == ConFuncIdEditCash.toString()) {
        funNext(2);
    }
    else {
        funNext(0);
     }

    return true;
}

//========================================================================================
//���O�C�����`�F�b�N����
//�쐬�ҁFY.Nishigawa
//�쐬���F2011/01/28
//�T�v  �F���[�U�̔F�؂��s��
//========================================================================================
function funChkLogin() {

var frm = document.frm00;    //̫�тւ̎Q��
var XmlId = "JSP0010";
var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

//������XMĻ�قɐݒ�
if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
   funClearRunMessage();
   return false;
}

//հ�ޔF�؂��s��
if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
   return false;
} else {
   //�ƭ��ɑJ��
   funNext(1);
}

return true;

}
// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

