//========================================================================================
// �����\������
// �쐬�ҁFhisahori
// �쐬���F2014/10/07
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    funInitScreen(ConGentyoLiteralMstId);

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
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
// ����  �F�Ȃ�
// �T�v  �F�J�e�S���ɕR�t�����e�����R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeCategory() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0621";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA111");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA111I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA111O);

    //�ú�؈ȉ���ر
    funInit();
    funInit2nd(1);

    if (frm.ddlCategory.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlLiteral, 2);
        funClearSelect(frm.ddl2ndLiteral, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���ُ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0621, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlLiteral, xmlResAry[2], 2);
    funClearSelect(frm.ddl2ndLiteral, 2);

    return true;

}

//========================================================================================
// ���e�������擾����
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
// ����  �F�Ȃ�
// �T�v  �F���e�����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0631";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA101");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA101I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA101O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    funInit();
    funInit2nd(1);

    // ��񃊃e�����ȉ����N���A
    if (frm.ddlLiteral.selectedIndex == 0) {
        funClearSelect(frm.ddl2ndLiteral, 2);
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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0631, xmlReqAry, xmlResAry, 1) == false) {
        //��ʏ�����
        funClear();
        return false;
    }

    //�����ޯ���̍쐬 add
    funCreateComboBox(frm.ddl2ndLiteral, xmlResAry[2], 5); //mode=5  ��񃊃e����

    //������ү���ޔ�\��
    funClearRunMessage();

    //��ʂ̏�����
    funSetData(xmlResAry[2]);

    return true;

}

//========================================================================================
//��񃊃e�������擾����
//�쐬�ҁFhisahori
//�쐬���F2014/10/10
//����  �F�Ȃ�
//�T�v  �F��񃊃e�����f�[�^�̌������s��
//========================================================================================
function funSearch2nd() {

  var frm = document.frm00;    //̫�тւ̎Q��
  var XmlId = "JSP0632";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"SA102");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA102I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA102O);
  var PageCnt;
  var RecCnt;
  var ListMaxRow;

  if (frm.ddl2ndLiteral.value == 0) {
	  funInit2nd(2);
      return true;
  }

  //������ү���ޕ\��
  funShowRunMessage();

  //������XMĻ�قɐݒ�
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      //��ʏ�����
	  funInit2nd(2);
      funClearRunMessage();
      return false;
  }

  //���������Ɉ�v���������ް����擾
  if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0632, xmlReqAry, xmlResAry, 1) == false) {
      //��ʏ�����
      funClear();
      return false;
  }

  //������ү���ޔ�\��
  funClearRunMessage();

  //��ʂ̏�����
  funSetData_2nd(xmlResAry[2]);

  return true;

}

//========================================================================================
// �o�^�A�X�V�A�폜�{�^����������
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�폜
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0641";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA331");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA331I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA331O);
    var dspMsg;

    if (mode == 1) {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "��P���e���� ��V�K�o�^���܂��B��낵���ł����H";
        }else{
    		dspMsg = "��Q���e���� ��V�K�o�^���܂��B��낵���ł����H";
        }
    } else if (mode == 2) {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "��P���e���� ���X�V���܂��B��낵���ł����H";
        }else{
    		dspMsg = "��Q���e���� ���X�V���܂��B��낵���ł����H";
        }
    } else {
    	if (frm.txt2ndLiteralName.value == ""){
    		dspMsg = "��P���e���� ���폜���܂��B��낵���ł����H";
        }else{
    		dspMsg = "��Q���e���� ���폜���܂��B��낵���ł����H";
        }
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
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0641, xmlReqAry, xmlResAry, 1) == false) {
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
        funInit2nd(1);
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
    funInit2nd(1);
    funCreateComboBox(frm.ddlCategory, xmlSA041O, 1);
    funClearSelect(frm.ddlLiteral, 2);
    funClearSelect(frm.ddl2ndLiteral, 2);

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
		// ���j���[����̑J��
		wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
		// ���j���[�ɖ߂�
		funUrlConnect(wUrl, ConConectPost, document.frm00);

	}
	return true;
}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/04
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {
	return funEnd();
//    var wUrl;
//
//    //�J�ڐ攻��
//    switch (mode) {
//        case 0:    //Ͻ��ƭ�
//            wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//            break;
//    }
//
//    //�J��
//    funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0611";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA041","SA300");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA041I,xmlSA300I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA041O,xmlSA300O);
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0611, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlCategory, xmlResAry[2], 1);
    funClearSelect(frm.ddlLiteral, 2);
    funClearSelect(frm.ddl2ndLiteral, 2);
    funCreateComboBox(frm.ddlUseEdit, xmlResAry[3], 3);

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
//    frm.btnExcel.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //����Ͻ�����ݽ
        if (GamenId.toString() == ConGmnIdGentyoLiteralMst.toString()) {
            //�ҏW or ���ъǗ���
            if (KinoId.toString() == ConFuncIdEdit.toString() || KinoId.toString() == ConFuncIdSysMgr.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }

            hidGamenId.value = GamenId;
            hidKinoId.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// ���e�����f�[�^�ݒ菈��
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
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
    // ��񃊃e�����g�p�t���O��1�Ȃ�A�`�F�b�N�{�b�N�X���I����
    if (funXmlRead(xmlData, "flg_2ndedit", 0) == "1"){
    	frm.chk2ndliteral.checked = true;
		frm.ddl2ndLiteral.disabled = false; //��񃊃e�����R���{�{�b�N�X������
		frm.txt2ndLiteralName.disabled = false; //��񃊃e������ ������
		frm.txt2ndSortNo.disabled = false; //���\�[�g ������

		frm.txt2ndliteralHissu.value = "�i�K�{�j"; //��񃊃e�����̕K�{�}�[�N�t�^
		frm.txt2ndHyojiHissu.value = "�i�K�{�j"; //���\�����̕K�{�}�[�N�t�^
    }else {
    	frm.chk2ndliteral.checked = false;
		frm.ddl2ndLiteral.disabled = true; //��񃊃e�����R���{�{�b�N�X�񊈐���
		frm.txt2ndLiteralName.disabled = true; //��񃊃e������ �񊈐���
		frm.txt2ndSortNo.disabled = true; //���\�[�g �񊈐���

		frm.txt2ndliteralHissu.value = ""; //��񃊃e�����̕K�{�}�[�N�폜
		frm.txt2ndHyojiHissu.value = ""; //���\�����̕K�{�}�[�N�폜
    }
    frm.txtLiteralName.value = funXmlRead(xmlData, "nm_literal", 0);
//    frm.txtValue1.value = funXmlRead(xmlData, "value1", 0);
//    frm.txtValue2.value = funXmlRead(xmlData, "value2", 0);
    frm.txtSortNo.value = funXmlRead(xmlData, "no_sort", 0);
    frm.txtBikou.value = funXmlRead(xmlData, "biko", 0);
    funDefaultIndex(frm.ddlUseEdit, 2);
//    funDefaultIndex(frm.ddlGroup, 3);

    return true;

}

//========================================================================================
//��񃊃e�����f�[�^�ݒ菈��
//�쐬�ҁFhisahori
//�쐬���F2014/10/10
//����  �F�@xmlData �F ��񃊃e�������XML
//�T�v  �F�擾�������e�����f�[�^����ʂɐݒ肷��
//========================================================================================
function funSetData_2nd(xmlData) {

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
  frm.txt2ndLiteralName.value = funXmlRead(xmlData, "nm_2nd_literal", 0); // ��񃊃e������
  if (frm.txt2ndSortNo.value == null){
	  // �l��������΋󔒂̂�
  }else{
	  frm.txt2ndSortNo.value = funXmlRead(xmlData, "no_2nd_sort", 0); // ��񃊃e�����̕\����
  }

  return true;

}

//========================================================================================
// ��ʏ�����
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F��ʂ�������Ԃɖ߂�
//========================================================================================
function funInit() {

    var frm = document.frm00;    //̫�тւ̎Q��

//    if (hidGamenId.value == ConGmnIdLiteralCsv) {
//        funItemDisabled(frm.ddlLiteral, true);
//        funGamenControl(true);
//    } else {
//        funGamenControl(false);
//    }

    //��ʏ��̐ݒ�
//    frm.txtLiteralCd.value = "";
    frm.txtLiteralName.value = "";
//    frm.txtValue1.value = "";
//    frm.txtValue2.value = "";
    frm.txtSortNo.value = "";
    frm.txtBikou.value = "";
    funCreateComboBox(frm.ddlUseEdit, xmlSA300O, 3);
    funDefaultIndex(frm.ddlUseEdit, 1);
//    funCreateComboBox(frm.ddlGroup, xmlSA050O, 4);
//    frm.ddlGroup.selectedIndex = 0;

}

//========================================================================================
//��ʏ�����
//�쐬�ҁFhisahori
//�쐬���F2014/10/10
//����  �Fmode 1:��Q���e�����g�p�`�F�b�N�{�b�N�X������������  2:
//�߂�l�F�Ȃ�
//�T�v  �F��ʂ�������Ԃɖ߂�
//========================================================================================
function funInit2nd(mode) {

  var frm = document.frm00;    //̫�тւ̎Q��

//  if (hidGamenId.value == ConGmnIdLiteralCsv) {
//      funItemDisabled(frm.ddlLiteral, true);
//      funGamenControl(true);
//  } else {
//      funGamenControl(false);
//  }

  //��ʏ��̐ݒ�
  if (mode == 1){
	  frm.chk2ndliteral.checked = false;
	  frm.txt2ndLiteralName.disabled = true; //��񃊃e������ �񊈐���
	  frm.txt2ndSortNo.disabled = true; //���\�[�g �񊈐���

		frm.txt2ndliteralHissu.value = ""; //��񃊃e�����̕K�{�}�[�N�폜
		frm.txt2ndHyojiHissu.value = ""; //���\�����̕K�{�}�[�N�폜
  }
  frm.txt2ndLiteralName.value = ""; // ��񃊃e������
  frm.txt2ndSortNo.value = ""; // ��񃊃e�����̕\����

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
//    funItemReadOnly(frm.txtValue1, flg);
//    funItemReadOnly(frm.txtValue2, flg);
    funItemReadOnly(frm.txtSortNo, flg);
    funItemReadOnly(frm.txtBikou, flg);
    funItemReadOnly(frm.ddlUseEdit, flg);
//    funItemReadOnly(frm.ddlGroup, flg);
    if (hidGamenId.value == ConGmnIdGentyoLiteralMst) {
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
//            switch (i) {
//                case 0:    //USERINFO
//                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    break;
//                case 1:    //SA040
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
//                    break;
//                case 2:    //SA050
//                    funXmlWrite(reqAry[i], "id_user", "", 0);
//                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdLiteralMst, 0);
//                    break;
//                case 3:    //SA300
//                    break;
//            }

        //�yQP@40404�zADD start 2014/10/10
        } else if (XmlId.toString() == "JSP0611") {
                switch (i) {
                    case 0:    //USERINFO
                        funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                        funXmlWrite(reqAry[i], "id_user", "", 0);
                        break;
                    case 1:    //SA041
                        funXmlWrite(reqAry[i], "id_user", "", 0);
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGentyoLiteralMst, 0);
                        break;
                    case 2:    //SA300
                        break;
              }
        //�yQP@40404�zADD start 2014/10/10

        //�ú�غ��ޑI��
        } else if (XmlId.toString() == "JSP0621"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA111
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 1), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGentyoLiteralMst, 0);
                    break;
            }

        //���ٺ��ޑI��
        } else if (XmlId.toString() == "JSP0631"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA101
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    break;
            }

        //������ٺ��ޑI��
        } else if (XmlId.toString() == "JSP0632"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA102
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_2nd_literal", frm.ddl2ndLiteral.options[frm.ddl2ndLiteral.selectedIndex].value, 0);
                    break;
            }

        //�o�^�A�X�V�A�폜���݉���
        } else if (XmlId.toString() == "JSP0641"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA331
                    funXmlWrite(reqAry[i], "cd_category", frm.ddlCategory.options[frm.ddlCategory.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlLiteral.options[frm.ddlLiteral.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "flg_edit", frm.ddlUseEdit.options[frm.ddlUseEdit.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", frm.hidEditMode.value, 0);
                    funXmlWrite(reqAry[i], "cd_2nd_literal", frm.ddl2ndLiteral.options[frm.ddl2ndLiteral.selectedIndex].value, 0);
                    if(frm.chk2ndliteral.checked == true){
                        funXmlWrite(reqAry[i], "flg_2ndedit", "1", 0);
                    }else{
                        funXmlWrite(reqAry[i], "flg_2ndedit", "", 0);

                    }
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
        case 5:    //��񃊃e����
            //�����ޯ���̸ر
            funClearSelect(obj, 2);

            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
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
// �쐬�ҁFhisahori
// �쐬���F2014/10/10
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
                if (obj.options[i].value == funXmlRead(xmlSA101O, "flg_edit", 0)) {
                    selIndex = i;
                }
                break;
//            case 3:    //��ٰ�ߺ���
//                if (obj.options[i].value == funXmlRead(xmlSA101O, "cd_group", 0)) {
//                    selIndex = i;
//                }
//                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//��񃊃e�����g�p�`�F�b�N�{�b�N�X  �I���^�I�t
//�쐬�ҁFhisahori
//�쐬���F2014/10/10
//����  �F
//�T�v  �F��񃊃e�����R���{�{�b�N�X�̊������^�񊈐���
//========================================================================================
function click_2ndliteral(){

	var frm = document.frm00;    //̫�тւ̎Q��

	//�I����
	if(frm.chk2ndliteral.checked == true){
		frm.ddl2ndLiteral.disabled = false;
		frm.txt2ndLiteralName.disabled = false; //��񃊃e������ ������
		frm.txt2ndSortNo.disabled = false; //���\�[�g ������

		frm.txt2ndliteralHissu.value = "�i�K�{�j"; //��񃊃e�����̕K�{�}�[�N�t�^
		frm.txt2ndHyojiHissu.value = "�i�K�{�j"; //���\�����̕K�{�}�[�N�t�^
	}
	//�I�t��
	else{
		frm.ddl2ndLiteral.disabled = true;
		frm.txt2ndLiteralName.value = ''; //��񃊃e������ ��
		frm.txt2ndSortNo.value = ''; //���\�[�g ��
		frm.txt2ndLiteralName.disabled = true; //��񃊃e������ �񊈐���
		frm.txt2ndSortNo.disabled = true; //���\�[�g �񊈐���

		frm.txt2ndliteralHissu.value = ""; //��񃊃e�����̕K�{�}�[�N�폜
		frm.txt2ndHyojiHissu.value = ""; //���\�����̕K�{�}�[�N�폜
	}
}

