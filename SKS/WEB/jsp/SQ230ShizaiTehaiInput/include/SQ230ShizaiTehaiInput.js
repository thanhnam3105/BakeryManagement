//========================================================================================
// ���ʕϐ�
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/19
//========================================================================================
//�f�[�^�ێ�
var sv_seihinCd = "";			// ���i�R�[�h�ޔ�
var seihin_errmsg = "";			// ���i�R�[�h���̓G���[���b�Z�[�W�i�t�H�[�J�X�ړ��Ή��j
//May thu START 2016.10.02
var rowcnt = 0;			// ���׍s��
var cd_kaisha = 0;		// ��ЃR�[�h
// DB�ɓo�^�ł���ő�t�@�C�����̒���
var MAXLEN_FILENM = 255;
// DB��������MAX�X�V����
var MAX_DtKoshin = "";
var E000067 = "�o�^�{�^����������Ă��Ȃ����߁A�u���ގ�z�˗��o�͉�ʁv�ɑJ�ڂł��܂���B\\n�o�^�{�^���������ĉ������B";
//May thu START 2016.10.02
//========================================================================================
// �����\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {
    //��ʐݒ�
    funInitScreen(ConShizaiTehaiInputId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }
    //��ʂ̏�����
    //funClear();

    //������ү���ޔ�\��
    funClearRunMessage();
    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@mode  �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�������A�Ώێ��ށA����������擾����
//========================================================================================
function funGetInfo(mode) {
	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3200";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3200","FGEN3310","FGEN2130","FGEN3450");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3200I,xmlFGEN3310I,xmlFGEN2130I,xmlFGEN3450I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3200O,xmlFGEN3310O,xmlFGEN2130O,xmlFGEN3450O);

	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}

	// ����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}
	// հ�ޏ��\��
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

	// �A�b�v���[�h���
	if (funXmlRead(xmlResAry[6], "flg_return", 0) == "true") {
		xmlFGEN3450.load(xmlResAry[6]);
		// �A�b�v���[�h��̍ă��[�h
        funSearchConditionSet(xmlResAry[6]);
        funSearch();
	}

	// ����
	if (funXmlRead(xmlResAry[5], "flg_gentyo", 0)) {
		// �������[�U�[�͓o�^�{�^��������
		frm.btnInsert.disabled = false;
	} else {
		// �H�ꃆ�[�U�[�͓o�^�{�^����񊈐�
		frm.btnInsert.disabled = true;
	}
	// ���i�R�[�h
	frm.inputSeihinCd.focus();
	frm.inputSeihinCd.click();

	return true;
}

//========================================================================================
// ��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

  var frm = document.frm00;    //̫�тւ̎Q��

  //��ʂ̏�����
  frm.reset();

  //�ꗗ�̸ر
  funClearList();

  return true;

}


//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

//	var frm = document.frm00;    //̫�тւ̎Q��

	// ���ޏ��
	// �ꗗ�̸ر
	xmlFGEN3450O.src = "";

	tblList.style.display = "none";
	// ���׍s�i�ǂݍ��ݕ��j
	var detail = document.getElementById("detail");
	// ���׃f�[�^�폜
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}

	// �������
	xmlFGEN0012O.src = "";
	var Genryo_Left = document.getElementById("divGenryo_Left");
	var Genryo_Right = document.getElementById("divGenryo_Right");
	Genryo_Left.style.display = "none";
	Genryo_Right.style.display = "none";


}

//========================================================================================
// ���i�R�[�h��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �FkeyCode �F ENTER ���� TAB
// �T�v  �F���i�R�[�h��莎��No�ꗗ�A���i���A�׎p�̌������s��
//========================================================================================
function funSeihinSearch(keyCode) {

	var frm = document.frm00;    //̫�тւ̎Q��
	// ���i�R�[�h
	var inputSeihin = document.getElementById("inputSeihinCd");

	// ���i�R�[�h�G���[���b�Z�[�W���N���A
	seihin_errmsg = "";
	// ���i�R�[�h���ύX����Ă��Ȃ����A���������s���Ȃ�
	if (inputSeihin.value == sv_seihinCd) {
		// �󔒂�ENTER�L�[�������̓G���[��\��
		if (inputSeihin.value == "" && keyCode == 13) {
			seihin_errmsg = "���i�R�[�h�͕K�{���ڂł��B\\n ���͂��ĉ������B";
			funErrorMsgBox(seihin_errmsg);
			// ���i�R�[�h�Ƀt�H�[�J�X��߂��i���Ɉڂ��Ă��܂��j
			inputSeihin.focus();
			return false;
		} else {
			// �󔒂ł��^�u�ړ����͎���
			return;
		}
	}

	var XmlId = "RGEN3420";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3420","FGEN3430");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3420I,xmlFGEN3430I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3420O,xmlFGEN3430O);

	// ���i���A�׎p���N���A
	frm.seihinNm.value = "";
	frm.nisugata.value = "";
	// �����H��
	frm.seizoKojoCd.value = "";
	frm.seizoKojoNm.value = "";
	//�����ޯ���̸ر
	funClearSelect(frm.ddlShisakuNo, 2);
	funClearSelect(frm.ddlShokuba, 2);
	funClearSelect(frm.ddlLine, 2);

	// ���ޏ��ꗗ�̏�����
	funClearList();

	//������ү���ޕ\��
	funShowRunMessage();

	// ���i�R�[�h��ޔ�
	sv_seihinCd = inputSeihin.value;

	// ���i�R�[�h�����̓`�F�b�N�� RGEN3420��
	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}


	// ����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3420, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		// ���i�R�[�h�Ƀt�H�[�J�X���Z�b�g�i���Ɉڂ��Ă��܂��j
		inputSeihin.focus();
		return false;
	}

	//������ү���ޔ�\��
	funClearRunMessage();

	//����No�ꗗ����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// ����No�̃R���{�{�b�N�X���쐬
		funCreateComboBox(frm.ddlShisakuNo, xmlResAry[2], 5, 2);
	}else {
		// ����No���擾�ł��Ȃ�
		seihin_errmsg = "����No";
	}

	//���i���A�׎p
	if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
		frm.seihinNm.value = funXmlRead(xmlResAry[3], "name_hinmei", 0);
		frm.nisugata.value = funXmlRead(xmlResAry[3], "name_nisugata", 0);
	} else {
		// ���i�����擾�ł��Ȃ�
		if (seihin_errmsg != "") seihin_errmsg += "�E";
		seihin_errmsg += "���i��";
	}

	if (seihin_errmsg != ""){
		seihin_errmsg += "���擾�ł��܂���B���i�R�[�h���ē��͂��ĉ������B";
		funErrorMsgBox(seihin_errmsg);
		// ���i�R�[�h�Ƀt�H�[�J�X��߂��i���Ɉڂ��Ă��܂��j
		inputSeihin.focus();
		return false;
	}

	// �����H��i�����j�F����No.�I�����ɃZ�b�g

	return true;

}

//========================================================================================
// �����H��R���{�{�b�N�X�A������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�����H��ɕR�t���E��R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3230O);

	// ���ޏ��ꗗ�̏�����
	funClearList();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3230, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//�E������ޯ���̍쐬
	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 2);
	//ײݺ����ޯ���̸ر
	funClearSelect(frm.ddlLine, 2);

	//�E��R���{�{�b�N�X�̑ޔ�
	xmlFGEN3230.load(xmlResAry[2]);

	return true;

}

//========================================================================================
// �E��R���{�{�b�N�X�A������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�E��ɕR�t���������C���R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeShokuba() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3240";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3240");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3240I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3240O);

	if (frm.ddlShokuba.selectedIndex == 0) {
		//�����ޯ���̸ر
		funClearSelect(frm.ddlLine, 2);
		return true;
	}

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3240, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//�����ޯ���̍쐬
	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 2);

	return true;

}

//========================================================================================
// ����No�R���{�{�b�N�X�t�H�[�J�X���̏���
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/25
// ����  �F�Ȃ�
// �T�v  �F���i�R�[�h���̓G���[���̃t�H�[�J�X�ړ��̑Ή�
//         �ʏ�s�`�a�ړ��̎��͂��̂܂܃t�H�[�J�X���c��
//========================================================================================
function funFocusShisakuNo() {

	var frm = document.frm00;    //̫�тւ̎Q��

	// 2�ȏ�̎��A�l���Z�b�g����Ă���i�����f�[�^�Ȃ��̎��A1�ڋ󔒁j
	if (frm.ddlShisakuNo.length < 2) {
		// ���i�R�[�h���̓G���[�̎��A���i�R�[�h�Ƀt�H�[�J�X���ڂ�
		if (seihin_errmsg != "") {
			// ���b�Z�[�W���N���A���A���i�R�[�h�Ƀt�H�[�J�X�ړ�
			seihin_errmsg = "";
			frm.inputSeihinCd.focus();
		}
	}
}

//========================================================================================
// ����No�R���{�{�b�N�X
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�ꗗ����������
//========================================================================================
function funChangeShisakuNo() {

	var frm = document.frm00;    //̫�тւ̎Q��

	// ���ޏ��ꗗ�̏�����
	funClearList();

	if (frm.ddlShisakuNo.selectedIndex == 0) {
		// �����H��i�����j�̃N���A
		frm.seizoKojoCd.value = "";
		frm.seizoKojoNm.value = "";
		// �E��A�������C���N���A
		funClearSelect(frm.ddlShokuba, 2);
		funClearSelect(frm.ddlLine, 2);
		return true;
	}

	// �����H��i�����j�F����No�ꗗ�̑I��index���
	frm.seizoKojoCd.value = funXmlRead(xmlFGEN3420O, "cd_busho", frm.ddlShisakuNo.selectedIndex - 1);
	// �����}�X�^���Ώە��������Z�b�g
	frm.seizoKojoNm.value = funGetXmldata(xmlSA290O, "cd_busho", frm.seizoKojoCd.value, "nm_busho");

	// �E��R���{�{�b�N�X�𐶐�
	funChangeKojo();

}


//========================================================================================
// �����{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ރf�[�^�̌������s��
//========================================================================================
function funSearch() {
	var frm = document.frm00;    //̫�тւ̎Q��

	// ���i�R�[�h���ύX����Ă���ꍇ�A�G���[���b�Z�[�W�\��
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox(E000066);
//		funErrorMsgBox("���i�R�[�h���ύX����Ă��܂��B\\n����No���������ĉ������B");
		frm.inputSeihinCd.focus();
		return false;
	}
	//������ү���ޕ\��
	funShowRunMessage();
    //��������
    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
    setTimeout(function(){ funSearch0() }, 0);
}

//========================================================================================
//�����{�^����������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�Ȃ�
//�T�v  �F���ރf�[�^�̌������s��
//========================================================================================
function funSearch0() {
	var frm = document.frm00;    //̫�тւ̎Q��
	//�ް��擾
    if (funDataSearch()) {
    	// �������擾�E�\��
    	funGetGenryoInfo(1)
    } else {
		// ���i�R�[�h�Ƀt�H�[�J�X���Z�b�g
		frm.inputSeihinCd.focus();
    }

}

//========================================================================================
// ��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ރf�[�^�̌������s��
//========================================================================================
function funDataSearch() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3450";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3450");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3450I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3450O);


	//�I���s�̏�����
	funSetCurrentRow("");

	//�ꗗ�̸ر
	funClearList();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	//���������Ɉ�v���鎎���ް����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3450, xmlReqAry, xmlResAry, 1) == false) {
		//������ү���ޔ�\��
		funClearRunMessage();
		return false;
	}

	//�ް�����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//�\��
		tblList.style.display = "block";

		// �E��i1���ڂ̒l�j
		var cd_shokuba = funXmlRead(xmlResAry[2], "cd_shokuba", 0);
		// �������C���i1���ڂ̒l�j
		var cd_line = funXmlRead(xmlResAry[2], "cd_line", 0);

		// �ݒ肳��Ă��鎞�A�E��R���{�{�b�N�X��index��ݒ�
		if (cd_shokuba != "") {
			funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", cd_shokuba);
			// �������C���R���{�{�b�N�X��ݒ�
			funChangeShokuba();

			// �ݒ肳��Ă��鎞�A�E��R���{�{�b�N�X��index��ݒ�
			if (cd_line != "") {
				funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", cd_line);
			}
		} else {
			// �E��R���{�{�b�N�X�Đݒ�F�󔒑I��
			funCreateComboBox(frm.ddlShokuba, xmlFGEN3230, 3 , 2);
			// �������C���N���A
			funClearSelect(frm.ddlLine, 2);
		}

		// ���ޏ��ꗗ�̍쐬
		funCreateSizaiTableList(xmlResAry[2]);

	} else {
		//��\��
		tblList.style.display = "none";

	}

	//������ү���ޔ�\��
	funClearRunMessage();

	return true;

}


//========================================================================================
// ���ޏ��@�e�[�u���ꗗ�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �FxmlData    �FXML�f�[�^
// �T�v  �F���ޏ��ꗗ���쐬����
//========================================================================================
function funCreateSizaiTableList(xmlData) {

	//�����擾���A�s����ۑ�
	rowcnt = funGetLength(xmlData);

	for(var i = 0; i < rowcnt; i++){
		// �s�ǉ�
		funAddSizaiTable(i, xmlData);

		// ������R�[�h�̎擾
		var cdHattyusaki = funXmlRead(xmlData, "cd_hattyusaki", i);
		// ������R���{�{�b�N�X
		var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + i);
    	// ������R���{�{�b�N�X�ݒ�
        funCreateComboBox(ddlHattyusaki, xmlFGEN3310O, 2, 2);

		// �Ώێ��ޖ��̎擾
		var cdTaisyoShizai = funXmlRead(xmlData, "cd_taisyoshizai", i);
		// �Ώێ��ރR���{�{�b�N�X
		var ddlShizai = document.getElementById("ddlShizai-" + i);
    	// �Ώێ��ރR���{�{�b�N�X�ݒ�
        funCreateComboBox(ddlShizai, xmlFGEN3200O, 1, 2);

        // �V�K�`�F�b�N���I�t�̎��A�񊈐�
        ddlHattyusaki.disabled = true;
        ddlShizai.disabled = true;

		// ������I��Index�̐ݒ�
		funSetIndex(ddlHattyusaki, xmlFGEN3310O, "cd_hattyusaki", cdHattyusaki);
		// �Ώێ��ޑI��Index�̐ݒ�i���e�����}�X�^�j
		funSetIndex(ddlShizai, xmlFGEN3200O, "cd_literal", cdTaisyoShizai);
	}
}
//========================================================================================
// ���ޏ�� ���׍s�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@row     �F�s�ԍ� �i0 �` �j
//       �F�AxmlData �FXML�f�[�^
// �T�v  �F�f�U�C���X�y�[�X���ꗗ�̍s�ǉ�����
//========================================================================================
function funAddSizaiTable(row, xmlData) {

	var detail = document.getElementById("detail");

	var html;
	// �����`�F�b�N
	var flgKanryo = funXmlRead(xmlData, "chk_kanryo", row);
	// ��z�X�e�[�^�X
	var flgTtehaiStatus = funXmlRead(xmlData, "flg_tehai_status", row);
	// �s�ݒ�
	var tr = document.createElement("tr");
	tr.setAttribute("class", "disprow");
	tr.setAttribute("id", "tr" + row);

	var td1 = document.createElement("td");
	td1.setAttribute("class", "column");
	// ����No. seq
	html = "<input type=\"hidden\" name=\"seq_shizai-" + row + "\" id=\"seq_shizai-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "seq_shizai", row) + "\" >";
	// ���̓X�e�[�^�X�̕\�� START 2017.02.09 Makoto Takada
	html += "<input type=\"hidden\" name=\"flg_tehai_status_org-" + row + "\" id=\"flg_tehai_status_org-" + row + "\"  value=\"";
	html += flgTtehaiStatus + "\" >";
	// ���̓X�e�[�^�X�̕\�� END 2017.02.09 Makoto Takada
	// ���ރR�[�h�i�ޔ�p�j
	html += "<input type=\"hidden\" name=\"cd_shizai-" + row + "\" id=\"cd_shizai-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "cd_shizai", row) + "\" >";
	// ��s�̑Ή�
	var cd_sizai =funXmlRead(xmlData, "cd_shizai", row);
	if (cd_sizai == "") {
		html += "&nbsp;";
	} else {
		// ���ރR�[�h �O�[���t���i�\���p�j
		html += ("000000" + cd_sizai).substr(funXmlRead(xmlData, "cd_shizai", row).length);
	}
	td1.innerHTML = html;
	td1.style.textAlign = "left";
	td1.style.width = 50;//80 -58
	tr.appendChild(td1);

	// ���ޖ�
	var td2 = document.createElement("td");
	td2.setAttribute("class", "column");
	td2.innerHTML = funXmlRead(xmlData, "nm_shizai", row);
	if (td2.innerHTML == "") {
		td2.innerHTML = "&nbsp;";
	}
	td2.style.textAlign = "left";
	td2.style.width = 168;
	tr.appendChild(td2);

	// �P��
	var td3 = document.createElement("td");
	td3.setAttribute("class", "column");
	td3.innerHTML = funXmlRead(xmlData, "tanka", row);
	if (td3.innerHTML == "") {
		td3.innerHTML = "&nbsp;";
	}
	td3.style.textAlign = "right";
	td3.style.width = 55;//70 -50
	tr.appendChild(td3);

	// �V�K�`�F�b�N�{�b�N�X
	var td4 = document.createElement("td");
	td4.setAttribute("class", "column");
	html = "<input type=\"checkbox\" name=\"chkNew\" value=\"\" onClick=\"funClickChkNew(" + row + ")\" style=\width:45px;\�h";
	// ��z�X�e�[�^�X=3�i��z�ς݁j�̎��A�񊈐�
	if (flgTtehaiStatus == "3") {
		html += "checked disabled";
	}
	html += " >";
	td4.innerHTML = html;
	td4.style.textAlign = "center";
	td4.style.width = 45;//43
	tr.appendChild(td4);

	// �V���ރR�[�h�ireadonly �� �V�Kon�ŋ��j
	var td5 = document.createElement("td");
	td5.setAttribute("class", "column");
	html = "<span class=\"ninput\" format=\"6,0\" comma=\"false\">";
	html += "<input type=\"text\"  name=\"inputNewShizaiCd-" + row + "\" id=\"inputNewShizaiCd-" + row + "\"  value=\"";
	//  �o�^����Ă��鎞�A�O�[���t��
	var cd_shizai_new = funXmlRead(xmlData, "cd_shizai_new", row);
	if (cd_shizai_new != "") {
		cd_shizai_new = "000000" + cd_shizai_new;
		html += cd_shizai_new.substr(funXmlRead(xmlData, "cd_shizai_new", row).length);
	}
	html += "\" onChange=\"funChangeNewShizaiCd(" + row + ")\"";
	// ��ɂ������AonChange �C�x���g���������Ȃ���
//	html += " onkeydown=\"if(event.keyCode == 13 || event.keyCode == 9){funChangeNewShizaiCd(" + row + ")} \"";
//	html += " size=\"17\" style=\"ime-mode:disabled;\" onBlur=\"funChangeNewShizaiCd(" + row + "); funBuryZero(this, 6);\" readonly>";
	html += " size=\"8\" style=\"ime-mode:disabled;\" onBlur=\"funBuryZero(this, 6);\" style=\width:60px;\�hreadonly>";
	html += "</span>";
	td5.innerHTML = html;
	td5.style.textAlign = "left";
	td5.style.width = 60;//100 - 60
	tr.appendChild(td5);

	// �V���ޖ�
	var td6 = document.createElement("td");
	td6.setAttribute("class", "column");
	html = "<input type=\"text\"  name=\"inputNewShizaiNm-" + row + "\" id=\"inputNewShizaiNm-" + row + "\" onChange=\"funKanryoChk(" + row + ")\"  value=\"";
	html += funXmlRead(xmlData, "nm_shizai_new", row);
	html += "\"  style=\"width:228px;\" readonly>";
//	html += "\"  style=\"width:232px;\" tabindex=\"-1\" readonly>";
	td6.innerHTML = html;
	td6.style.textAlign = "left";
	td6.style.width = 229;
	tr.appendChild(td6);

	// ������i�Z���N�g�{�b�N�X�j
	var td7 = document.createElement("td");
	td7.setAttribute("class", "column");
	html = "<select name=\"ddlHattyusaki-" + row + "\" id=\"ddlHattyusaki-" + row + "\" style=\"width:233px;\" onChange=\"funChangeHattyusaki(" + row + ")\" onFocus=\"funSetHattyusaki(" + row + ")\">";
	html += "</select>";
	html += "<input type=\"hidden\" name=\"flgHattyusaki-" + row + "\" id=\"flgHattyusaki-" + row + "\"  value=\"\" >";
	td7.innerHTML = html;
	td7.style.textAlign="left";
	td7.style.width = 236;
	tr.appendChild(td7);

	// �Ώێ��ށi�Z���N�g�{�b�N�X�j
	var td8 = document.createElement("td");
	td8.setAttribute("class", "column");
	html = "<select name=\"ddlShizai-" + row + "\" id=\"ddlShizai-" + row + "\" style=\"width:144px;\" onChange=\"funChangeTaisyoShizai(" + row + ")\" >";
	html += "</select>";
	td8.innerHTML = html;
	td8.style.textAlign="left";
	td8.style.width = 144;
	tr.appendChild(td8);

	var td9 = document.createElement("td");
	td9.setAttribute("class", "column");
	// ��z�X�e�[�^�X
	html = "<input type=\"hidden\" name=\"flg_tehai_status-" + row + "\" id=\"flg_tehai_status-" + row + "\"  value=\"";
	html += flgTtehaiStatus + "\" >";
	// �����`�F�b�N�{�b�N�X
	html += "<input type=\"checkbox\" name=\"chkKanryo\" value=\"\" style=\"width:43px;\" onClick=\"funClickKanryo(" + row + ")\"";
	// �����`�F�b�N
	if (flgKanryo == "1") {
		html += " checked";
	}
	// ��z�X�e�[�^�X=3�i��z�ς݁j�̎��A�񊈐�
	if (flgTtehaiStatus == "3") {
		html += " disabled";
	}
	html += " >";
	td9.innerHTML = html;
	td9.style.textAlign = "center";
	td9.style.width = 45;
	tr.appendChild(td9);

	// ���ގ�z�e�[�u���̍X�V����
	var td12 = document.createElement("td");
	td12.setAttribute("class", "column");
	html = "<input type=\"hidden\" name=\"dt_tehai_koshin-" + row + "\" id=\"dt_tehai_koshin-" + row + "\"  value=\"";
	html += funXmlRead(xmlData, "dt_tehai_koshin", row) + "\" >";
	td12.innerHTML =  html;
	tr.appendChild(td12);

	// May Thu �yKPX@1602367�z add start 2016/09/05
	// �t�@�C���� �ŉ�����۰��
    var td13 = document.createElement("td");
    td13.setAttribute("class", "column");
    // �ۑ�����Ă���t�@�C�����i��\���j
    html = "<input type=\"hidden\" id=\"nm_file_henshita-" + row + "\" value=\"";
	html += funXmlRead(xmlData, "nm_file_henshita", row);
    html += "\" tabindex=\"-1\">";
    // �Q�ƃ{�^����input ��\���p�t�@�C�����ŉB��
    html += "<div style=\"position: relative;\">";
   // �Q�ƃ{�^���ionChange�C�x���g�����ł͕\���t�@�C�������N���A���ē����t�@�C����I���������Z�b�g����Ȃ��̂�onclick�C�x���g�ŕ\���t�@�C�����ɃZ�b�g�j
//    html += "<input type=\"file\" class=\"normalbutton\" value=\"\" style=\"width:380px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "<input type=\"file\" class=\"normalbutton\" value=\"";
    html += funXmlRead(xmlData, "file_path_henshita", row);
    html += "\" style=\"width:270px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "onChange=\"funChangeFile(" + row + ")\" onclick=\"funSetInput(" + row + ")\" ";
    // �Q�ƃ{�^��ENTER�L�[�Ń_�C�A���O���J��
    html += "onkeydown=\"funEnterFile(" + row + ", event.keyCode);\" >";
    // �\���p�t�@�C�����i�X�N���[�����Ă��^�C�g���̉��ɕ\���j
    html += "<span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
    html += "<input type=\"text\" value=\"";
    html += funXmlRead(xmlData, "nm_file_henshita", row);
    // �\���p�t�@�C�����F�^�u�ړ��𖳌��Ƃ���89 �� 59 50
    html += "\" name=\"inputName-" + row + "\" id=\"inputName-" + row + "\" size=\"37\" style=\"width:210px;\" readonly tabindex=\"-1\" >";
    html += "</span>";
    html += "</div>";
    td13.innerHTML = html;
    td13.style.textAlign="left";
    td13.style.width = 270;//383 363
//    td13.colspan = 2;
    tr.appendChild(td13);

	// ����
	var td14 = document.createElement("td");
	td14.setAttribute("class", "column");
	td14.setAttribute("class", "column");
	html = "<input type=\"button\" name=\"btnhattyu\" value=\"����\"  style=\"width:44px;\" onClick=\"funClickBtnHattyu(" + row + ")\"";
	html += " >";
	td14.innerHTML = html;
	td14.style.textAlign = "center";
	td14.style.width = 45;
	tr.appendChild(td14);

	// ������
	var td15 = document.createElement("td");
	td15.setAttribute("class", "column");
	td15.innerHTML = funXmlRead(xmlData, "nm_hattyu", row);
	if (td15.innerHTML == "") {
		td15.innerHTML = "&nbsp;";
	}
	td15.style.textAlign = "center";
	td15.style.width = 90;//157 - 127
	tr.appendChild(td15);

	// �������� ���@������
	var td16 = document.createElement("td");
	td16.setAttribute("class", "column");
//	var dthattyu = funXmlRead(xmlData, "dt_hattyu", row);
//	var dthattyu = "2007-08-16 10:30:15";
//	td16.innerHTML = funGetDateString(dthattyu);
	td16.innerHTML = funXmlRead(xmlData, "dt_hattyu", row);
	if (td16.innerHTML == "") {
		td16.innerHTML = "&nbsp;";
	}
	td16.style.textAlign = "left";
	td16.style.width = 75;//90 - 80
	tr.appendChild(td16);
    // May Thu �yKPX@1602367�z add end 2016/09/05

	// �X�V��
	var td10 = document.createElement("td");
	td10.setAttribute("class", "column");
	td10.innerHTML = funXmlRead(xmlData, "nm_koshin", row);
	if (td10.innerHTML == "") {
		td10.innerHTML = "&nbsp;";
	}
	td10.style.textAlign = "center";
	td10.style.width = 90;//157 127
	tr.appendChild(td10);

	// �X�V����
	var td11 = document.createElement("td");
	td11.setAttribute("class", "column");
//	var dtkoushin = funXmlRead(xmlData, "dt_koshin", row);
//	td11.innerHTML = funGetDateString(dtkoushin);
	td11.innerHTML = funXmlRead(xmlData, "dt_koshin", row);
	if (td11.innerHTML == "") {
		td11.innerHTML = "&nbsp;";
	}
	td11.style.textAlign = "left";
	td11.style.width = 75;//90 - 77
	tr.appendChild(td11);

	detail.appendChild(tr);
}
//========================================================================================
// ����������t(10�����j��؂�o���B
// �쐬�ҁFMay Thu
// �쐬���F2016/09/05
// ����  �F����  �F��10������
// �T�v  �F
//========================================================================================
function funGetDateString(str) {
   if(str == null || str == "") {
      return "";
   }
   return str.substr(0,10);
}
//*** ���ޏ��F���׍s������ ***//
//========================================================================================
// �����{�^����������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/05
// ����  �Frow   �F�s�ԍ�
// �T�v  �F���ގ�z�˗����o�͉�ʂ֑J�ڂ���
//========================================================================================
function funClickBtnHattyu(row) {

	// �V���ރR�[�h�A�V���ޖ��A������A�Ώێ��ނ��ׂĂ��ݒ肳��Ă��Ȃ����A�u���ގ�z�˗����o�͉�ʁv�֑J�ڕs�Ƃ���
	if (!funNyuryokuChk(row, 1)) {
		funErrorMsgBox(E000061);
		return false;
	}

    var frm = document.frm00;        //̫�тւ̎Q��
    //START 2017.02.09 Makoto Takada ���ގ�z�˗��o�͉�ʂɑJ�ڂ��Ă��o�^�ł��Ȃ����̂́A�J�ڂ����Ȃ��`�ɕύX
    var flg_tehai_status_org = document.getElementById("flg_tehai_status_org-" + row).value;
    if (funTorokuStatusChk(row)) {
        funErrorMsgBox(E000067);
        return false;
    }
    //END 2017.02.09 Makoto Takada ���ގ�z�˗��o�͉�ʂɑJ�ڂ��Ă��o�^�ł��Ȃ����̂́A�J�ڂ����Ȃ��`�ɕύX
    //Drop down list �̒l���o�������ꍇ
    var taishoshizai    = document.getElementById("ddlShizai-" + row); //�Ώێ���
    var hattyusaki    = document.getElementById("ddlHattyusaki-" + row); //������
    var shisakuNo      = frm.ddlShisakuNo.value; //����R�[�h
    var flg_hatyuu_status = frm.flg_hatyuu_status.value; //flag
    var seizoKojoCd       = frm.seizoKojoCd.value;    //�����H��R�[�h ���͂���ƁA�o��A�Ȃ��ƁA��
    var seizoKojoNm       = frm.seizoKojoNm.value;    //�����H�ꖼ�@���͂���ƁA�o��A�Ȃ��ƁA��
    var cd_taishoshizai = taishoshizai.options[taishoshizai.selectedIndex].value;    //�Ώێ��ރR�[�h
    var nm_taishoshizai = taishoshizai.options[taishoshizai.selectedIndex].text;    //�Ώێ��ޖ�
    var cd_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].value; //������R�[�h
    var nm_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].text; //�����於
    var seihinCd      = frm.inputSeihinCd.value;    //���i�R�[�h
    var cd_shizai     = document.getElementById("cd_shizai-" + row).value;    //���ރR�[�h
    var nm_shizai     = funXmlRead(xmlFGEN3450O, "nm_shizai", row);    //���ޖ�
    var cd_shizai_new = document.getElementById("inputNewShizaiCd-" + row).value;    // �V���ރR�[�h
    var nm_shizai_new = document.getElementById("inputNewShizaiNm-" + row).value;    // �V���ޖ�
    var cd_shain = shisakuNo.substring(0,10);    //cd_shain
    var nen = shisakuNo.substring(11,13);        //nen
    var no_oi = shisakuNo.substring(14,17);      //no_oi
    var no_eda = shisakuNo.substring(18,21);     //no_eda
    var seq_shizai = document.getElementById("seq_shizai-" + row).value; //
    var seihinNm = frm.seihinNm.value;
    var nisugata = frm.nisugata.value ;
    var wUrl;
    //��ʈړ��ƃp�����[�^
        wUrl = "../SQ250ShizaiTehaiOutput/ShizaiTehaiOutput.jsp"+ "?" + "flg_hatyuu_status=" + flg_hatyuu_status
                                                                + "&" + "seizoKojoCd=" + seizoKojoCd
                                                                + "&" + "seizoKojoNm=" + seizoKojoNm
                                                                + "&" + "cd_taishoshizai=" + cd_taishoshizai
                                                                + "&" + "nm_taishoshizai=" + nm_taishoshizai
                                                                + "&" + "cd_hattyusaki=" + cd_hattyusaki
                                                                + "&" + "nm_hattyusaki=" + nm_hattyusaki
                                                                + "&" + "seihinCd=" + seihinCd
                                                                + "&" + "cd_shizai=" + cd_shizai
                                                                + "&" + "nm_shizai=" + nm_shizai
                                                                + "&" + "cd_shizai_new =" + cd_shizai_new
                                                                + "&" + "nm_shizai_new =" + nm_shizai_new
                                                                + "&" + "cd_shain=" + cd_shain
                                                                + "&" + "nen=" + nen
                                                                + "&" + "no_oi=" + no_oi
                                                                + "&" + "no_eda=" + no_eda
                                                                + "&" + "seihinNm=" + seihinNm
                                                                + "&" + "nisugata=" + nisugata
                                                                + "&" + "seq_shizai=" + seq_shizai;
        //�J��
//        var win = window.open(wUrl,"gensi","menubar=no,resizable=yes");
        var width, height;
      var win = funOpenModalDialog(wUrl,"gensi","dialogHeight:800px;dialogWidth:1600px;menubar=no,resizable=yes");
//      	    , this
//			, "");

      if (win != null && win != "") {
    	 var returnParam = win.split(":::");

	      if(returnParam[0] == 2 || returnParam[0] == 3) {
	    	  // hattyuusakiko-do
	    	  if (cd_hattyusaki != returnParam[1]) {

	    		  document.getElementById("ddlHattyusaki-" + row).value = Number(returnParam[1]);
	    		  hattyusaki.onchange();

	    	  }

	    	  if (taishoshizai != returnParam[2]) {
				  document.getElementById("ddlShizai-" + row).value = ( '000' + returnParam[2] ).slice( -3 );
				  document.getElementById("ddlShizai-" + row).onchange();
	    	  }
	      }
      }
	  // �u���ۑ��v�܂��́A�u���M�v������̏ꍇ
	  // �o�^���������s����
	  if (!funToroku()) {
		  // �o�^���Ȃ�������Č���
		  funSearch();
	  }


        // �ĕ\���ׂ̈Ƀt�H�[�J�X�ɂ���
        //win.focus();


    return true;

}

//*** ���ޏ��F���׍s������ ***//
//========================================================================================
// �V�K�`�F�b�N
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�V�K�`�F�b�N���A�V���ރR�[�h�̕ҏW��Ԃ�ύX����
//========================================================================================
function funClickChkNew(row) {
	var frm = document.frm00;        //̫�тւ̎Q��
	var chkNew = frm.chkNew;         // �V�K�`�F�b�N�{�b�N�X
	var chkKanryo = frm.chkKanryo;   // �����`�F�b�N�{�b�N�X

	// ���R�[�h���P���̎�
	if (!chkNew.length) {
		// �w��s�̐V���ރR�[�h�̕ҏW��Ԃ�ύX
		funShizaiNewHenko(row, chkNew, chkKanryo);
	// �`�F�b�N���ꂽ�s�̐ݒ�
	} else {
		// �w��s�̐V���ރR�[�h�̕ҏW��Ԃ�ύX
		funShizaiNewHenko(row, chkNew[row], chkKanryo[row]);
	}

}

//========================================================================================
// �V�K�`�F�b�N����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow      �F�s�ԍ�
//       �Fchkobj   �F�V�K�`�F�b�N�I�u�W�F�N�g
//       �FchkKanryo�F�����`�F�b�N�I�u�W�F�N�g
// �T�v  �F�V���ރR�[�h�̕ҏW��Ԃ�ύX
//========================================================================================
function funShizaiNewHenko(row, chkobj, chkKanryo) {

	// �V���ރR�[�h
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);
	// �V���ޖ�
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// ������R���{�{�b�N�X
	var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);
	// �Ώێ��ރR���{�{�b�N�X
	var ddlShizai = document.getElementById("ddlShizai-" + row);

	// �V�K�`�F�b�N�̏��
	if(chkobj.checked == true) {
		// �V�K�`�F�b�N��on �̎��A�ҏW��
		shizaiCd.readOnly = false;
		shizaiCd.style.backgroundColor = "#ffff88";
		shizaiCd.focus();
		shizaiCd.click();

		// �V�K�`�F�b�N���I���̎��A������
		ddlHattyusaki.disabled = false;
		ddlShizai.disabled = false;

	} else {
		// �V���ރR�[�h�A������A�Ώێ��ނ̂��ׂĂ��󔒂̎��i�m�F�s�v�j
		if (!funNyuryokuChk(row, 2)) {
			shizaiCd.readOnly = true;
			shizaiCd.style.backgroundColor = "#ffffff";
			// �V�K�`�F�b�N���I�t�̎��A�񊈐�
			ddlHattyusaki.disabled = true;
			ddlShizai.disabled = true;

		//�V���ރR�[�h�A������A�Ώێ��ނ��ݒ肳��Ă��鎞�i�ǂꂩ��j
		}else {
			// �ҏW�s�ɂ��邩�m�F
			if (funConfMsgBox("�V���ރR�[�h�A���͔�����E�Ώێ��ނ��ݒ肳��Ă��܂��B\n�`�F�b�N���͂����ăN���A���܂����H") == ConBtnYes) {
				// �V�K�`�F�b�N��off �̎��A�ҏW�s��
				shizaiCd.readOnly = true;
				shizaiCd.style.backgroundColor = "#ffffff";
				// �V���ރR�[�h���N���A
				shizaiCd.value = "";
				// �V���ޖ����N���A
				shizaiNm.value = "";

				// �V�K�`�F�b�N���I�t�̎��A�񊈐��iindex���N���A�ɂ���j
				ddlHattyusaki.disabled = true;
				ddlShizai.disabled = true;
				// index���N���A
				ddlHattyusaki.selectedIndex = 0;
				ddlShizai.selectedIndex = 0;
				// �����`�F�b�N���O��
				if(chkKanryo.checked == true) {
					chkKanryo.checked = false;
				}

			} else {
				// �V�K�`�F�b�N��on�ɕύX
				chkobj.checked = true;
			}

		}
	}

}

//========================================================================================
// �V���ރR�[�h�ύX
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F���ރe�[�u����莑�ޖ����擾����
//========================================================================================
function funChangeNewShizaiCd(row) {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3440";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3440");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3440I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3440O);

	// �V���ރR�[�h
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);

	// �V���ޖ�
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// ������
	var hattyusaki = document.getElementById("ddlHattyusaki-" + row);

	// ���^�[���L�[����������Ă��ҏW�s�̏�Ԃł͉������Ȃ�
	if (shizaiCd.readOnly) {
		return false;
	}

	// �V���ޖ����N���A
	shizaiNm.value = "";
	// �V���ރR�[�h���󔒂̎�
	if(shizaiCd.value == ""){
		// �����`�F�b�N���O��
		funKanryoChk(row);
		return false;
	}

	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// �����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3440, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	// �ް�����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// �V���ޖ�
		shizaiNm.value = funXmlRead(xmlResAry[2], "nm_shizai", 0);
		// �����`�F�b�N
		funKanryoChk(row);
//�yKPX@1602367�z add start
		hattyusaki.focus();	// ������Ƀt�H�[�J�X����
		shizaiNm.readOnly = true; // ���͕s�ɂ���
//�yKPX@1602367�z add end
	} else {

		// ���ރR�[�h�����G���[
		funErrorMsgBox("���ރR�[�h�����݂��܂���B���ޖ�������͂��Ă��������B");
//�yKPX@1602367�z add start
		// �����`�F�b�N���O��
		funKanryoChk(row);

		shizaiNm.readOnly = false; // ���͉ɂ���
		// �V���ޖ��Ƀt�H�[�J�X����
		shizaiNm.focus();
//�yKPX@1602367�z add end
	}


}


//========================================================================================
// �����`�F�b�N
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�����`�F�b�N���̏���
//========================================================================================
function funClickKanryo(row) {

	var frm = document.frm00;          //̫�тւ̎Q��
	var chkKanryo = null;              // �����`�F�b�N�{�b�N�X

	// Index�̊m�F
	if (!frm.chkKanryo.length) {
		// ���R�[�h���P���̎�
		chkKanryo = frm.chkKanryo;
	} else {
		// �w��s�̃`�F�b�N�{�b�N�X
		chkKanryo = frm.chkKanryo[row];
	}

	if (chkKanryo.checked == true) {
		// �V���ރR�[�h�A������A�Ώێ��ނ��ׂĂ��ݒ肳��Ă��Ȃ����A�`�F�b�N�ł��Ȃ�
		if (!funNyuryokuChk(row, 1)) {
			funErrorMsgBox(E000065);
//			funErrorMsgBox("���͍��ڂ��ݒ肳��Ă��Ȃ����߁A�����`�F�b�N���n�m�ɂł��܂���B");
			chkKanryo.checked = false;
		}
	}
}


//========================================================================================
// �����`�F�b�N
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�V�K�`�F�b�NOn�A�V���ރR�[�h�A������A�Ώێ��ނ��ݒ肳��Ă��鎞�A
//         �����`�F�b�N��On�ɂ���
//========================================================================================
function funKanryoChk(row) {
	var frm = document.frm00;     //̫�тւ̎Q��

	var chkNew = null;            // �V�K�`�F�b�N�{�b�N�X
	var chkKanryo = null;         // �����`�F�b�N�{�b�N�X

	// Index�̊m�F
	if (!frm.chkNew.length) {
		// ���R�[�h���P���̎�
		chkNew = frm.chkNew;
		chkKanryo = frm.chkKanryo;
	} else {
		// �w��s�̃`�F�b�N�{�b�N�X
		chkNew = frm.chkNew[row];
		chkKanryo = frm.chkKanryo[row];
	}

	// �V���ރR�[�h�A������A�Ώێ��ނ��ݒ肳��Ă��邩�H
	if (!funNyuryokuChk(row, 1)) {
		// ���ݒ�̍��ڂ�����Ί����`�F�b�N���͂���
		if(chkKanryo.checked == true && chkKanryo.disabled == false) {
			// �����`�F�b�N��Off�ɕύX
			chkKanryo.checked = false;
		}
		return;
	}

	// �V�K�`�F�b�N��on�̎�
	if(chkNew.checked == true) {
		// �����`�F�b�N��On�ɕύX
		chkKanryo.checked = true;
	}
	return;
}

//========================================================================================
// ���̓`�F�b�N
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
//       �Fmode  �F1 or 2
// �߂�l�Ftrue  �Fmode==1:���ׂĐݒ肳��Ă��鎞
//                 mode==2�F��ł��ݒ肳��Ă��鎞
//         false �F�����͂���
// �T�v  �F�V���ރR�[�h�A������A�Ώێ��ނ��ݒ肳��Ă��邩�H
//========================================================================================
function funNyuryokuChk(row, mode) {

	// �V���ރR�[�h
	var shizaiCd = document.getElementById("inputNewShizaiCd-" + row);
	// �V���ޖ�
	var shizaiNm = document.getElementById("inputNewShizaiNm-" + row);
	// ������R���{�{�b�N�X
	var selHattyusaki = document.getElementById("ddlHattyusaki-" + row);
	// �Ώێ��ރR���{�{�b�N�X
	var selShzai = document.getElementById("ddlShizai-" + row);

	// ���ׂĐݒ肳��Ă��鎞�Atrue
	if (mode == 1) {
		// �V���ރR�[�h�A�V���ޖ��A������A�Ώێ��ނ̂ǂꂩ�����ݒ�
		if ((shizaiCd.value == "") || (shizaiNm.value == "") ||  (selHattyusaki.selectedIndex < 1) || (selShzai.selectedIndex < 1)) {
			return false;
		}
	} else {
		// �V���ރR�[�h���󔒁A�����斢�I���A�Ώێ��ޖ��I��
		if ((shizaiCd.value == "") &&  (selHattyusaki.selectedIndex < 1) && (selShzai.selectedIndex < 1)) {
			return false;
		}
	}

	return true;
}



//========================================================================================
// ������R���{�{�b�N�X�ύX
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F������R���{�{�b�N�X�ύX���̏���
//========================================================================================
function funChangeHattyusaki(row) {

	// �����`�F�b�N
	funKanryoChk(row);

}


//========================================================================================
// �Ώێ��ރR���{�{�b�N�X�ύX
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�Ώێ��ރR���{�{�b�N�X�ύX���̏���
//========================================================================================
function funChangeTaisyoShizai(row) {

	// �����`�F�b�N
	funKanryoChk(row);

}
//*** ���׍s������ �I��� ***//


//========================================================================================
// �o�^��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ރe�[�u���X�V�A���ގ�z�e�[�u���o�^
//========================================================================================
function funToroku() {

	var frm = document.frm00;          //̫�тւ̎Q��
	var chkNew = frm.chkNew;           // �V�K�`�F�b�N�{�b�N�X
	var chkKanryo = frm.chkKanryo;     // �����`�F�b�N�{�b�N�X
	var retBool = false;
	var retUpload = false;


	// ���i�R�[�h���ύX����Ă���ꍇ�A�G���[���b�Z�[�W�\��
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox("���i�R�[�h���ύX����Ă���̂ŁA�o�^�ł��܂���B");
		frm.inputSeihinCd.focus();
		return false;
	}
	if(funTorokuInputCheck()) {
		return false;
	}


	// �o�^�m�F
	if (funConfMsgBox(I000002) != ConBtnYes) {
		return false;
	}

	// ���׍s�i�ǂݍ��ݕ��j
	var detail = document.getElementById("detail");

	// ���׃f�[�^
	if (!detail.firstChild){
		// ���ޏ�񂪂Ȃ���
		retBool = funTorokuChk(-1, null, null);

	// ���R�[�h���P���̎�
	} else if (!chkNew.length) {
		// �`�F�b�N�{�b�N�X��n���A�o�^�i���ރe�[�u���X�V�A��z�e�[�u���o�^or�폜�j
		retBool = funTorokuChk(0, chkNew, chkKanryo);

	} else {
		// �`�F�b�N���ꂽ�s�̐ݒ�
		for(var i = 0; i < chkNew.length; i++){
			// �`�F�b�N�{�b�N�X��n���A�o�^�i���ރe�[�u���X�V�A��z�e�[�u���o�^or�폜�j
			retBool = funTorokuChk(i, chkNew[i], chkKanryo[i]);
			if (!retBool) break;
		}
	}
	//�t�@�C���A�b�v���[�h���Ɏ��{
	retUpload = funfileUpload();

	// �������b�Z�[�W
    if (retBool || retUpload ) {
// ���[�U�[���r���[�ł̎w�E�i���M���������΂₭�s�����ߕs�v�ȃ��b�Z�[�W���폜�j Maythu
//    	// �o�^���܂����B
//    	funInfoMsgBox("�ŐV�̃f�[�^���������܂�");
    	// �Č������s
    	funSearch();
    }

	return true;

}
//========================================================================================
// ���������̍Đݒ菈��
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �߂�l�F�S������I�����Ă��Ȃ����F""
//         �I�����F�I���������Ȃ����R�[�h������
// �T�v  �F�����������`�F�b�N���A�����R�[�h���Ȃ����������Ԃ�
//========================================================================================
function funSearchConditionSet(resultAray) {

	var frm = document.frm00;		//̫�тւ̎Q��
	var sisakuNo            = funXmlRead(resultAray, "sisakuNo" , 0);
	frm.inputSeihinCd.value = funXmlRead(resultAray, "cd_seihin", 0);
	funSeihinSearch();
	frm.ddlShisakuNo.value =  sisakuNo;
	funChangeShisakuNo();
	return true;
}

//========================================================================================
// ���������̃`�F�b�N����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �߂�l�F�S������I�����Ă��Ȃ����F""
//         �I�����F�I���������Ȃ����R�[�h������
// �T�v  �F�����������`�F�b�N���A�����R�[�h���Ȃ����������Ԃ�
//========================================================================================
function funSelChk() {

	var frm = document.frm00;		//̫�тւ̎Q��
	var strRet = cd_kaisha + "_"	//�߂�l�i��ЃR�[�h���Z�b�g�j

	// �����H��
	if (frm.seizoKojoCd.value == "") {
		funSelChk.Msg = "�����H�ꂪ�I������Ă��܂���B";
		return "";
	}
	strRet += frm.seizoKojoCd.value + "_";

	// ����NO
	if (frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value == "") {
		funSelChk.Msg = "����NO���I������Ă��܂���B";
		return "";
	}
	strRet += frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value + "_";

	return strRet;
}

//========================================================================================
// �A�b�v���[�h����
// �쐬�ҁFMay Thu
// �쐬���F2016/09/30
// ����  �F�Ȃ�
// �T�v  �F�ŉ��A�b�v���[�h
//========================================================================================
function funfileUpload() {
    var frm = document.frm00;   //̫�тւ̎Q��
    var upFildNm = "";          //�A�b�v���[�h����t�B�[���h��
    var delFileNm = "";         //�폜����t�@�C����
    var lstSyurui = {};         //�o�^�����ނ̔z��i�d���`�F�b�N�p�j
    var strMsg = ""				//�m�F���b�Z�[�W�t������

    // �ۑ��t�@�C���̃T�u�t�H���_�[�擾�i�H��j
    var subFolder = funSelChk();
    // �������I������Ă��Ȃ��ꍇ
	if (subFolder == "") {
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

	// �I��������ރR�[�h��_�Ōq���ׁA�t�@�C�����ɐݒ�
	var subFlst = "";
	// �\���s�������ꍇ�A�G���[
	var displayln = false;

	// �ꗗ�f�[�^���Ȃ�
	if (rowcnt == 0) {
    	funErrorMsgBox(E000030);
        return false;
	}

	// �\������Ă���ꗗ���`�F�b�N
	// �Q�ƃt�@�C���A�\���pinput���󔒂łȂ�����
    for(var i = 0; i < rowcnt; i++){
        // �s�I�u�W�F�N�g
    	var tr = document.getElementById("tr" + i);

    	// �A�b�v���[�h����t�@�C����
    	var fileName = document.getElementById("fileName-" + i);

    	// �\���t�@�C����
    	var inputName = document.getElementById("inputName-" + i);
    	// �ۑ�����Ă���t�@�C�����i��\���j
    	var nm_file_henshita = document.getElementById("nm_file_henshita-" + i);

    	var cd_shizai = document.getElementById("cd_shizai-" + i);

    	// �\���s�̂ݏ�������
    	if (tr.style.display != "none") {
    		// �\���s
    		displayln = true;

    		// �\���pinput�t�@�C�������󔒂̏ꍇ�A�������΂��G���[�i�c�a�o�^�ł��Ȃ��j
    		if (inputName.value == "") {
    		    continue;
    		}
    		// �t�@�C�����̒����`�F�b�N�i�c�a�����j
			if (inputName.value.length > MAXLEN_FILENM) {
				funErrorMsgBox(E000062 + MAXLEN_FILENM + E000063);
//				funErrorMsgBox("�t�@�C�������������܂��B�i" + MAXLEN_FILENM + "�j�����F\\n" + inputName.value);
				return false;
			}
			// �t�@�C�����̕s�������`�F�b�N
			if (!funChkFileNm(inputName.value)) {
				funErrorMsgBox(E000066 + inputName.value);
//    			funErrorMsgBox("�t�@�C�����ɕs���������܂܂�Ă��܂��B�F\\n" + inputName.value);
    			return false;
			}
    		// �N���A�{�^������������Ă��Ȃ�����
    		if ((fileName.value != "") && (inputName.value != "") && (inputName.style.color == "red")) {
    			// �A�b�v���[�h�����F�T�[�o�[�n���i":::"�ŋ�؂�j
    			// �t�B�[���h���i�A�b�v���[�h�`�F�b�N�p�j
    			upFildNm += "fileName-" + i + ":::";
    			// �T�u�t�H���_�[�Ɏ�ރR�[�h��ǉ�
    			subFlst += subFolder  + cd_shizai.value + ":::";
    		}
    		// �ύX�O�t�@�C�����폜
    		// �c�a�ɕۑ�����Ă��� ���� �ύX���ꂽ�Ԏ��t�@�C���̎��A
    		if ( (nm_file_henshita.value != "") && (inputName.style.color == "red")) {
    			// �T�u�t�H���_�[����t������B�i":::"�ŋ�؂�j
    			// nm_file_henshita �ɂ͕ۑ��f�[�^�̎�ރR�[�h���t���ρi"\\"�ŋ�؂��Ă���j
    			delFileNm += subFolder + cd_shizai.value + "\\" + nm_file_henshita.value + ":::";
    		}
    	}
    }
    // �\���s���Ȃ�
	if (!displayln) {
    	funErrorMsgBox(E000030);
        return false;
	}
    if (upFildNm == "") {
        return false;
    }
	// �r�������F�������̍X�V��������ύX���������珈�����~
	if (!funKoshinChk()) {
		return false;
	}
	//** ̧���߽�̑ޔ� **/
    // �A�b�v���[�h�p�X�iconst��`���j
    frm.strServerConst.value = "UPLOAD_HANSITA_FOLDER";
    // �A�b�v���[�h�t�@�C���̃t�B�[���h���i ":::"�ŋ�؂�j
    frm.strFieldNm.value = upFildNm;
    // �A�b�v���[�h�t�@�C���̃T�u�t�H���_�[�i�t�@�C���P�ʂ� ":::"�ŋ�؂�j
    frm.strSubFolder.value = subFlst;
    // �폜�t�@�C���p�X�i":::"�ŋ�؂�j
    frm.strFilePath.value = delFileNm;
    // �t�@�C���A�b�v���[�h�T�[�u���b�g�̎��s
    if (upFildNm != "") {
    	// �t�@�C���A�b�v���[�h�T�[�u���b�g�̎��s
    	frm.action="/" + ConUrlPath + "/FileUpLoadExec";
    	frm.submit();
    }
    return true;
}
//========================================================================================
// �s�̓o�^�`�F�b�N
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�w��s�̍s�f�[�^��xml�ɕۑ�����
//========================================================================================
function funTorokuChk(row, chkNewObj, chkKnryoObj) {
	var frm = document.frm00;          //̫�тւ̎Q��

	// �w��s�̍s�f�[�^���Z�b�g����ׂ�xml ��ݒ�
//xmlFGEN3450.load(xmlFGEN3450O);   // ��s�̎��A�ݒ肳��Ă��Ȃ�
	funAddRecNode(xmlFGEN3450, "FGEN3460");

	// �Ώۍs�f�[�^��擪�s�ɃZ�b�g����
	// ���i�R�[�h�i�O�[���܂� �� �O�[�����폜�j
//	funXmlWrite(xmlFGEN3450, "cd_seihin", frm.inputSeihinCd.value, 0);
	funXmlWrite(xmlFGEN3450, "cd_seihin", frm.inputSeihinCd.value.replace(/^0+/,""), 0);
	// ���i��
	funXmlWrite(xmlFGEN3450, "nm_shohin", frm.seihinNm.value, 0);

	// ����No�i�I���f�[�^�j
	var no_sisaku = "";
	if (frm.ddlShisakuNo.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "sisakuNo", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "sisakuNo", frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value, 0);

		no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
		funXmlWrite(xmlFGEN3450, "cd_shain", no_sisaku[0], 0);
		funXmlWrite(xmlFGEN3450, "nen", no_sisaku[1], 0);
		funXmlWrite(xmlFGEN3450, "no_oi", no_sisaku[2], 0);
		funXmlWrite(xmlFGEN3450, "no_eda", no_sisaku[3], 0);
	}

	// �����H����
	funXmlWrite(xmlFGEN3450, "cd_seizokojo", frm.seizoKojoCd.value, 0);

	if (frm.ddlShokuba.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "cd_shokuba", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
	}
	if (frm.ddlLine.selectedIndex < 1) {
		funXmlWrite(xmlFGEN3450, "cd_line", "", 0);
	} else {
		funXmlWrite(xmlFGEN3450, "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
	}

	if (row < 0) {
		// �ꗗ���Ȃ�
		funXmlWrite(xmlFGEN3450, "seq_shizai", "", 0);
		// ���Z���ރe�[�u���X�V�����i�G���[�j
		if (!funShizaiUpdate()) {
			return false;
		}

	} else {
		// ������R���{�{�b�N�X
		var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);
		// �Ώێ��ރR���{�{�b�N�X
		var ddlShizai = document.getElementById("ddlShizai-" + row);
		// ���ގ�z�X�e�[�^�X
		var tehai_status = document.getElementById("flg_tehai_status-" + row).value;

		//May Thu 2016/09/28 �yKPX@1602367�z add start
		// �Q�ƃt�@�C����
        var inputName = document.getElementById("inputName-" + row).value;
        // �Q�ƃt�@�C���p�X
//		var filePath = document.getElementById("fileName-" + row).value;
//		filePath = funSetfilePath(filePath);   //CUT ����
		//May Thu 2016/09/28 �yKPX@1602367�z add end

		// �s�����擾
		// ����No.�V�[�P���X
		funXmlWrite(xmlFGEN3450, "seq_shizai", document.getElementById("seq_shizai-" + row).value, 0);
		// �����ރR�[�h
		var cd_shizai = document.getElementById("cd_shizai-" + row).value;
		funXmlWrite(xmlFGEN3450, "cd_shizai", cd_shizai, 0);
//		funXmlWrite(xmlFGEN3450, "cd_shizai", document.getElementById("cd_shizai-" + row).value, 0);

		// �V���ޖ�
		var nm_shizai_new = document.getElementById("inputNewShizaiNm-" + row).value;
		funXmlWrite(xmlFGEN3450, "nm_shizai_new", nm_shizai_new, 0);

		// �V���ރR�[�h�G���[�̎��A�V���ޖ����󔒁i�R�[�h���N���A�ρj
		// �V���ރR�[�h�i�O�[���폜�j
		var cd_shizai_new = document.getElementById("inputNewShizaiCd-" + row).value;
		funXmlWrite(xmlFGEN3450, "cd_shizai_new", cd_shizai_new.replace(/^0+/,""), 0);

		// �Ώێ��ރR���{�{�b�N�X
		funXmlWrite(xmlFGEN3450, "cd_taisyoshizai", ddlShizai.options[ddlShizai.selectedIndex].value, 0);

		// ������R���{�{�b�N�X
		funXmlWrite(xmlFGEN3450, "cd_hattyusaki", ddlHattyusaki.options[ddlHattyusaki.selectedIndex].value, 0);
		// ���ގ�z�e�[�u���̌������̍X�V����
		funXmlWrite(xmlFGEN3450, "dt_tehai_koshin", document.getElementById("dt_tehai_koshin-" + row).value, 0);

		// �ۑ��t�@�C���̃T�u�t�H���_�[
	    var subFolder = funSelChk();
	    subFolder = subFolder + cd_shizai;

	    // �t�@�C�����L�̏ꍇ �ŉ��t�@�C���p�X�i�T�u�t�H���_�j��ݒ肷��
	    if(inputName != "") {
	    	funXmlWrite(xmlFGEN3450, "file_path_henshita", subFolder, 0);	// �ŉ��t�@�C���p�X
	    } else {
	    	funXmlWrite(xmlFGEN3450, "file_path_henshita", "", 0);	// �ŉ��t�@�C���p�X
	    }

		//May Thu �yKPX@1602367�z 2016/09/28 add start
		// �t�@�C�����@�ŉ��A�b�v���[�h
		funXmlWrite(xmlFGEN3450, "nm_file_henshita", inputName, 0);
//
//		funXmlWrite(xmlFGEN3450, "file_path_henshita", subFolder, 0);	// �ŉ��t�@�C���p�X
//		funXmlWrite(xmlFGEN3450, "file_path_henshita", filePath, 0);
		//May Thu �yKPX@1602367�z 2016/09/28 add end

		// �����`�F�b�N
		if (chkKnryoObj.checked == true) {
			funXmlWrite(xmlFGEN3450, "chk_kanryo", 1, 0);

			// ���ގ�z�X�e�[�^�X�� 3�i��z�ρj �ȊO�̎��A���ގ�z�e�[�u���o�^�E�X�V
			if (tehai_status != 3) {
				// ���ގ�z�e�[�u���o�^�E�X�V���Ɏ��s
				// �i���ގ�z�e�[�u���X�V�`�F�b�N�Ō������_���X�V���ꂽ���A�G���[���Ԃ�j
					if (funTehaiInsert()) {
						// �X�V�������������A���Z���ރe�[�u�����X�V
						if (!funShizaiUpdate()) {
							return false;
						}
					}
			} else {
				// ���ގ�z�X�e�[�^�X�� 3�i��z�ρj �̎��A�X�V���Ȃ�
				// ���Z���ރe�[�u���X�V����
				if (!funShizaiUpdate()) {
					return false;
				}
			}

		} else {
			funXmlWrite(xmlFGEN3450, "chk_kanryo", 0, 0);

			// ���ގ�z�X�e�[�^�X�� 1�i�����́j or 2�i����z�j �̎��A���ގ�z�e�[�u���폜
			if (tehai_status == 1 || tehai_status == 2) {
				// ���ގ�z�e�[�u���폜���Ɏ��s
				// �i���ގ�z�e�[�u���폜�`�F�b�N�Ō������_���X�V���ꂽ���A�G���[���Ԃ�j
					if (funTehaiDelete()) {
						// �폜�������������A���Z���ރe�[�u�����X�V
						if (!funShizaiUpdate()) {
							return false;
						}
					}
			} else {
				// ���ގ�z�X�e�[�^�X�� 3�i��z�ρj �̎��A�폜���Ȃ�
				// ���Z���ރe�[�u���X�V����
				if (!funShizaiUpdate()) {
					return false;
				}
			}
		}
	}
	return true;

}

//========================================================================================
//�L�[���ڂ�XML�t�@�C���ɏ������݁A�s���擾
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�@XmlId  �FXMLID
//      �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//�߂�l�F�Ȃ�
//�T�v  �F�c�a�����ׁ̈A�s�����擾���AXML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funRowDataOutput(XmlId, reqAry) {
  // �I���s�ԍ�

}




//========================================================================================
// ���Z���ރe�[�u���X�V����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ރe�[�u���X�V
//========================================================================================
function funShizaiUpdate() {

	var XmlId = "RGEN3460";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3460");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3460I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3460O);


	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// ������ү���ޕ\��
	funShowRunMessage();

	// ����ݏ��A���ʏ����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3460, xmlReqAry, xmlResAry, 1) == false) {
	    //������ү���ޔ�\��
	    funClearRunMessage();
		return false;
	}

	// ������ү���ޔ�\��
    funClearRunMessage();

    // ��������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error
        return false;
    }

}

//========================================================================================
// ���ގ�z�e�[�u���o�^����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ގ�z�e�[�u���o�^
//========================================================================================
function funTehaiInsert() {

	var XmlId = "RGEN3470";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3470");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3470I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3470O);


	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// ������ү���ޕ\��
	funShowRunMessage();

	//����ݏ��A���ʏ����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3470, xmlReqAry, xmlResAry, 1) == false) {
		// ������ү���ޔ�\��
	    funClearRunMessage();
		return false;
	}

	// ������ү���ޔ�\��
    funClearRunMessage();

    // ��������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error�i�����ɂ͗��Ȃ��j
        return false;
    }


}

//========================================================================================
//���ގ�z�e�[�u���폜����
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�Ȃ�
//�T�v  �F���ގ�z�e�[�u���폜
//========================================================================================
function funTehaiDelete() {

	var XmlId = "RGEN3480";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3480");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3480I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3480O);


	// ������ү���ޕ\��
	funShowRunMessage();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//����ݏ��A���ʏ����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3480, xmlReqAry, xmlResAry, 1) == false) {
		// ������ү���ޔ�\��
		funClearRunMessage();
		return false;
	}

	// ������ү���ޔ�\��
	funClearRunMessage();

	// ��������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		return true;
	} else {
		//error
		return false;
	}


}

//========================================================================================
//�f�U�C���X�y�[�X�{�^����������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�Ȃ�
//�T�v  �F�f�U�C���X�y�[�X�_�E�����[�h��ʂ�\������
//========================================================================================
function funDesignSpace() {

	var frm = document.frm00;          //̫�тւ̎Q��

	var args = new Array("","","","");		// �_�C�A���O�ɓn���p�����[�^
	var retVal;

	args[0] = window;			//"SQ230ShizaiTehaiInput.jsp";
	// �I�����ꂽ�����H�����n��
	args[1] = frm.seizoKojoCd.value;
	if (frm.ddlShokuba.selectedIndex > 0) {
		args[2] = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
	}
	if (frm.ddlLine.selectedIndex > 0) {
		args[3] = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;
	}

	// �f�U�C���X�y�[�X�_�E�����[�h��ʂ����[�_���ŊJ��
    retVal = funOpenModalDialog("../SQ240DesignSpaceDL/SQ240DesignSpaceDL.jsp"
    		, args
    		, "dialogHeight:900px;dialogWidth:1360px;minimize=yes;maximize=yes;status:no;scroll:no");

	return true;

}

//========================================================================================
// �Q�l�����{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�Q�l������ʂ�\������
//========================================================================================
function funSiryo() {

    // �Q�l�����E�B���h�E�I�[�v��
	var win = window.open("../SQ220ShiryoRef/SQ220ShiryoRef.jsp","sansyo","menubar=no,resizable=yes");
    // �ĕ\���ׂ̈Ƀt�H�[�J�X�ɂ���
    win.focus();

    return true;

}


//========================================================================================
// �������Z�A�������擾���\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@mode  �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A�������擾
//========================================================================================
function funGetGenryoInfo(mode) {

	//------------------------------------------------------------------------------------
	//                                    �ϐ��錾
	//------------------------------------------------------------------------------------
	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN0012";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0012");
	var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0012I );
	var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0012O );


	//------------------------------------------------------------------------------------
	//                                  ��{���擾
	//------------------------------------------------------------------------------------
	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		funClearRunMessage2();
		return false;
	}

	//������ү���ޕ\��
	funShowRunMessage();


	//����ݏ��A���ʏ����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0012, xmlReqAry, xmlResAry, mode) == false) {
		//������ү���ޔ�\��
		funClearRunMessage();
		return false;
	}


	//------------------------------------------------------------------------------------
	//                                  �������\��
	//------------------------------------------------------------------------------------
	var Genryo_Left = document.getElementById("divGenryo_Left");
	var Genryo_Right = document.getElementById("divGenryo_Right");
	// �\���s��
	var dsp_cnt = 0;

	//�����e�[�u������
	dsp_cnt = funGenryo_LeftDisplay(xmlResAry[2], Genryo_Left);

	//�����e�[�u���E��
	funGenryo_RightDisplay(xmlResAry[2], Genryo_Right, dsp_cnt);

	// �\��
	Genryo_Left.style.display = "block";
	Genryo_Right.style.display = "block";

	//������ү���ޔ�\��
	funClearRunMessage();

	//�����I��
	return true;

}


//========================================================================================
// �����e�[�u���i�����j���\��
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�g
// �߂�l�F�\������s���i�j
// �T�v  �F�����e�[�u���i�����j�\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funGenryo_LeftDisplay(xmlData, obj) {

	var tablekihonNm;     //�ǂݍ��݃e�[�u����
	var tableGenryoNm;    //�ǂݍ��݃e�[�u����
	var OutputHtml;       //�o��HTML
	var cnt_genryo;       //�s��
	var sort_kotei;       //�H��
	var cd_kotei;         //�H��CD
	var seq_kotei;        //�H��SEQ
	var cd_genryo;        //����CD
	var nm_genryo;        //������
	var henko_renraku;    //�ύX
	var tanka;            //�P��
	var budomari;         //����
	var genryo_fg;        //�����s�t���O
	var i;                //���[�v�J�E���g
	var hdnKojoNmTanka;		//�H�ꖼ �P��
	var hdnKojoNmBudomari;	//�H�ꖼ ����

	//HTML�o�̓I�u�W�F�N�g�ݒ�
	OutputHtml = "";

	//�e�[�u�����ݒ�
	tablekihonNm = "kihon";
	tableGenryoNm = "genryo";

	//�s���擾
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	// �������̌����R�[�h��艺�̕�����\�����Ȃ��ׁA�\���s���擾
	var dsp_cnt = 0;
	for(i = 0; i < cnt_genryo; i++){
		// �s��
		sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
		// �s�����ݒ肳��Ă���s�܂ł�\������
		if (sort_kotei != "") {
			dsp_cnt = i+1;
		}
	}

	//�o��HTML�ݒ�
	OutputHtml += "<input type=\"hidden\" id=\"cnt_genryo\" name=\"cnt_genryo\" value=\"" + cnt_genryo + "\">";

	//�e�[�u���\��
	OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"605px\" style=\"word-break:break-all;word-wrap:break-word;\">";
//	for(i = 0; i < cnt_genryo; i++){
	// �s���ڍׂ͕\�����Ȃ�
	for(i = 0; i < dsp_cnt; i++){

		//HTML
		var sentaku_checkbox = ""; //�I���`�F�b�N�{�b�N�X
		var tanka_textbox = "";    //�P���e�L�X�g�{�b�N�X
		var budomari_textbox = ""; //�����e�L�X�g�{�b�N�X
		var txtReadonly = "readonly";      //�e�L�X�g�{�b�N�X���͐ݒ�
		var txtClass = henshuNgClass;      //�e�L�X�g�{�b�N�X�w�i�F

		//XML�f�[�^�擾
		sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
		cd_kotei = funXmlRead_3(xmlData, tableGenryoNm, "cd_kotei", 0, i);
		seq_kotei = funXmlRead_3(xmlData, tableGenryoNm, "seq_kotei", 0, i);
		cd_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "cd_genryo", 0, i));
		nm_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_genryo", 0, i));
		henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "henko_renraku", 0, i));
		tanka = funXmlRead_3(xmlData, tableGenryoNm, "tanka", 0, i);
		budomari = funXmlRead_3(xmlData, tableGenryoNm, "budomari", 0, i);
		genryo_fg = funXmlRead_3(xmlData, tableGenryoNm, "genryo_fg", 0, i);
		hdnKojoNmTanka = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_tanka", 0, i));
		hdnKojoNmBudomari = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_budomari", 0, i));


		if(genryo_fg == "1"){
			//�����s�̏ꍇ�͑I���A�P���A�����A�H��CD�A�H��SEQ�I�u�W�F�N�g�̐���
			sentaku_checkbox = "&nbsp;";
			tanka_textbox = "<input type=\"text\" class=\"" + txtClass + "\" style=\"width:70px;text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"-1\" />";
			budomari_textbox = "<input type=\"text\" class=\"" + txtClass + "\" style=\"width:45px;text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"-1\" />";

			//�s
			OutputHtml += "  <input type=\"hidden\" id=\"gyo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + (i + 1) + "\" title=\"" + (i + 1) + "\" tabindex=\"-1\" name=\"gyo\" style=\"text-align:center;\" />";
		}else if(genryo_fg == "2"){
			//�H���s�̏ꍇ�͑I���A�P���A�����A�H��CD�A�H��SEQ�͋�
			sentaku_checkbox = "&nbsp;";
			tanka_textbox = "&nbsp;";
			budomari_textbox = "&nbsp;";
		}



		//�e�[�u���^�O����
		OutputHtml += "    <tr class=\"disprow\" id=\"tableRowL_" + i + "\" name=\"tableRowL_" + i + "\">";

		//�I��
		OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\">" + sentaku_checkbox + "</td>";

		//�H��
		OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\"><input type=\"text\" id=\"sort_kote\" name=\"sort_kote\" class=\"table_text_view\" style=\"text-align:center;\" readonly value=\"" + sort_kotei + "\" tabindex=\"-1\" /></td>";

		//�����R�[�h
		if(genryo_fg == "1"){
			//�����s�̏ꍇW�N���b�N���̃C�x���g��ݒ�
			OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
			OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
			OutputHtml += "        </td>";
		}else if(genryo_fg == "2"){
			//�H���s�̏ꍇW�N���b�N�̃C�x���g�͖��ݒ�
			OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
			OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
			OutputHtml += "        </td>";
		}

		//������
		OutputHtml += "        <td class=\"column\" style=\"width:310px;\">";
		OutputHtml += "            <input type=\"text\" id=\"txtCd_genryoNm_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" />";

		OutputHtml += "        </td>";

		//�ύX
		OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:20px;\"><input type=\"text\" name=\"txtHenkouRen_"+(i+1)+"\" id=\"txtHenkouRen_"+(i+1)+"\" class=\"table_text_view\" readonly value=\"" + henko_renraku + "\"  tabindex=\"-1\" /></td>";

		//�P��
		OutputHtml += "        <td class=\"column\" style=\"width:70px;\">";
		OutputHtml += "            " + tanka_textbox;
		OutputHtml += "        </td>";

		//����
		OutputHtml += "        <td class=\"column\" style=\"width:45px;\">";
		OutputHtml += "            " + budomari_textbox;
		OutputHtml += "        </td>";

		OutputHtml += "    </tr>";

	}

	OutputHtml += "</table>";

	//HTML���o��
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	// �\���s����Ԃ�
	return dsp_cnt;
//	return true;
}


//========================================================================================
//�����e�[�u���i�E���j���\��
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�@xmlUser �F�X�V���i�[XML��
//      �F�AObjectId�F�ݒ�I�u�W�F�N�g
//      �F�B�\���s��
//�߂�l�F�Ȃ�
//�T�v  �F�����e�[�u���i�E���j�\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funGenryo_RightDisplay(xmlData, obj, cnt) {

	//------------------------------------------------------------------------------------
	//                                    �ϐ��錾
	//------------------------------------------------------------------------------------
	var tablekihonNm;     //�ǂݍ��݃e�[�u����
	var tableHaigoNm;     //�ǂݍ��݃e�[�u����
	var OutputHtml;       //�o��HTML
	var cnt_genryo;       //�s��
	var table_size;       //�e�[�u����
	var seq_shisaku;      //����SEQ
	var shisakuDate;      //������t
	var nm_sample;        //�T���v��NO�i���́j
	var haigo;            //�z��
	var kingaku;          //���z
	var i;                //���[�v�J�E���g
	var j;                //���[�v�J�E���g
	var color = "#ffffff";

	//------------------------------------------------------------------------------------
	//                                    �����ݒ�
	//------------------------------------------------------------------------------------
	//HTML�o�̓I�u�W�F�N�g�ݒ�
	OutputHtml = "";

	//�e�[�u�����ݒ�
	tablekihonNm = "kihon";
	tableHaigoNm = "shisaku";

	//�񐔎擾
	cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

	//�s���擾
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	//�e�[�u�����擾
	table_size = 175 * cnt_sample;


	//------------------------------------------------------------------------------------
	//                                  �e�[�u������
	//------------------------------------------------------------------------------------
	//�e�[�u������
	OutputHtml += "<table id=\"dataTable2\" name=\"dataTable2\" cellspacing=\"0\" width=\"" + table_size + "px;\">";

	//�E���w�b�_�T�C�Y�w��
	OutputHtml += "<colgroup>";
	for(i = 0; i < cnt_sample; i++){
		OutputHtml += "   <col style=\"width:175px;\"/>";
	}
	OutputHtml += "</colgroup>";

	//�E���w�b�_�e�[�u���ݒ�
	OutputHtml += "<thead class=\"rowtitle\">";
	OutputHtml += "    <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
	for(i = 0; i < cnt_sample; i++){

		//XML�f�[�^�擾
		seq_shisaku = funXmlRead_3(xmlData, tableHaigoNm+i, "seq_shisaku", 0, 1);
		shisakuDate = funXmlRead_3(xmlData, tableHaigoNm+i, "shisakuDate", 0, 1);
		nm_sample = funXmlRead_3(xmlData, tableHaigoNm+i, "nm_sample", 0, 1);

		OutputHtml += "        <th class=\"columntitle\">";
		OutputHtml += "            <table frame=\"void\" width=\"100%\" height=\"50px\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#bbbbbb\">";
		OutputHtml += "                <tr><td align=\"center\" colspan=\"2\" height=\"20px\">&nbsp;</td></tr>";

		//������t
		if(shisakuDate.length < 10){
			shisakuDate = "0000/00/00";
			color = "#0066FF";
		}
		OutputHtml += "                <tr><td align=\"center\" colspan=\"2\"><font color=\""+ color +"\">" + shisakuDate + "</font></td></tr>";

		//�T���v��No
		OutputHtml += "                <tr>";
		OutputHtml += "                   <td style=\"width:175px;\" align=\"center\" colspan=\"2\">";
		OutputHtml += "                      <input type=\"text\" style=\"border-width:0px;background-color:#0066FF;color:#FFFFFF;text-align:center;\" readonly value=\"" + nm_sample + "\" tabindex=\"-1\" />";

		OutputHtml += "                   </td>";
		OutputHtml += "                </tr>";

		OutputHtml += "                <tr><td align=\"center\"><font color=\"#ffffff\" style=\"\">�z��(kg)��</font></td><td align=\"center\" style=\"width:45%;\"><font color=\"#ffffff\">���z(�~)</font></td></tr>";
		OutputHtml += "            </table>";
		OutputHtml += "        </th>";
	}
	OutputHtml += "    </tr>";
	OutputHtml += "</thead>";

	//�E�����׃e�[�u���ݒ�
	OutputHtml += "<tbody>";
	OutputHtml += "    <table class=\"detail\" align=\"left\" id=\"tblList2\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\">";

	//�s���[�v
//	for(i = 0; i < cnt_genryo; i++){
	// ���\���Ŏ擾�����s���܂ŕ\������
	for(i = 0; i < cnt; i++){

		OutputHtml += "        <tr class=\"disprow\" id=\"tableRowR_" + i + "\" name=\"tableRowR_" + i + "\" >";

		//�񃋁[�v
		for(j = 0; j < cnt_sample; j++){

			//XML�f�[�^�擾
			haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
			kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

			OutputHtml += "            <td class=\"column\" style=\"width:175px;\" align=\"left\">";
			OutputHtml += "                <table frame=\"void\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">";
			OutputHtml += "                    <tr>";
			OutputHtml += "                        <td class=\"dot_r\" style=\"width:55%;text-align:right;\">" + "<input type=\"text\" style=\"width:82px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + haigo + "\" tabindex=\"-1\" />" + "</td>";
			OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"text\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
			OutputHtml += "                    </tr>";
			OutputHtml += "                </table>";
			OutputHtml += "            </td>";
		}
		OutputHtml += "        </tr>";
	}

	OutputHtml += "    </table>";
	OutputHtml += "</tbody>";

	OutputHtml += "</table>";


	//------------------------------------------------------------------------------------
	//                                  HTML�o��
	//------------------------------------------------------------------------------------
	//HTML���o��
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	return true;
}

//========================================================================================
//����ʑJ�ڏ���
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/01
//�T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {
    var XmlId = "RGEN3455";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3455, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
    //��ʂ����
    close(self);
    return true;
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
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
		if (XmlId.toString() == "RGEN3200"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //SA290
				funXmlWrite(reqAry[i], "id_user", "", 0);
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShizaiTehaiInput, 0);
				break;
			case 2:    //FGEN3200�i�Ώێ��ނ��擾�j
				break;
			case 3:    //FGEN3310�i��ނ��擾�j
				break;
			case 4:    //FGEN2130�i���������t���O�擾�j
				break;
			case 5:    //FGEN2130�i���������t���O�擾�j
				break;
			}

			//�����H��I��ύX
		} else if (XmlId.toString() == "RGEN3230") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3230:���O�C�����[�U�̉��
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.seizoKojoCd.value, 0);
				break;
			}

			//�E��I��ύX
		} else if (XmlId.toString() == "RGEN3240") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3240:���O�C�����[�U�̉��
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.seizoKojoCd.value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				break;
			}

			//���i�R�[�h����
		} else if (XmlId.toString() == "RGEN3420"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3420�i����No�j
				// ���i�R�[�h�i���l�j
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				break;
			case 2:    //FGEN3430�i�i���}�X�^�����j
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				break;
			}

			//�V���ރR�[�h����
		} else if (XmlId.toString() == "RGEN3440"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3440
			    // �I���s�ԍ�
			    var row_no = funGetCurrentRow();
			    // �V���ރR�[�h�@cd_shizai
			    var shizaiCd = document.getElementById("inputNewShizaiCd-" + row_no).value;
				// ����
				funXmlWrite(reqAry[i], "cd_busho", frm.seizoKojoCd.value, 0);
				funXmlWrite(reqAry[i], "cd_shizai", shizaiCd, 0);
				break;
			}

			//�����{�^������
		} else if (XmlId.toString() == "RGEN3450"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				// ����No
				var no_sisakutmp = "";
				var no_sisakuwork = "";
				var put_code      =  frm.inputSeihinCd.value  + ":::" ;
				if (frm.ddlShisakuNo.selectedIndex < 1) {
					put_code = put_code + ":::" + ":::" + ":::" + ":::";
				} else {
					no_sisakuwork =frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value;
					no_sisakutmp = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
					put_code     = put_code + no_sisakuwork + ":::" + no_sisakutmp[0] + ":::"
					put_code     = put_code + no_sisakutmp[1] + ":::" +  no_sisakutmp[2] + ":::" +  no_sisakutmp[3];
				}
				// �A�b�v���[�h��̍ă��[�h�p
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
				break;
			case 1:    //FGEN3450
				// ���i�R�[�h
				funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
				// ����No
				var no_sisaku = "";
				if (frm.ddlShisakuNo.selectedIndex < 1) {
					funXmlWrite(reqAry[i], "sisakuNo", "", 0);
				} else {
					funXmlWrite(reqAry[i], "sisakuNo", frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value, 0);
					no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
					funXmlWrite(reqAry[i], "cd_shain", no_sisaku[0], 0);
					funXmlWrite(reqAry[i], "nen", no_sisaku[1], 0);
					funXmlWrite(reqAry[i], "no_oi", no_sisaku[2], 0);
					funXmlWrite(reqAry[i], "no_eda", no_sisaku[3], 0);
				}

				break;
			}

			//�o�^�{�^�������F���ރe�[�u���X�V
		} else if (XmlId.toString() == "RGEN3460"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3460

				// ���i�R�[�h
				funXmlWrite(reqAry[i], "cd_seihin", funXmlRead(xmlFGEN3450, "cd_seihin", 0), 0);
				// ����No.�A�����H����A�s���� Xml �ɃZ�b�g
				funXmlWrite(reqAry[i], "sisakuNo", funXmlRead(xmlFGEN3450, "sisakuNo", 0), 0);

				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				//�Ώۍs���Ȃ����A""���Z�b�g
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				funXmlWrite(reqAry[i], "cd_shizai_new", funXmlRead(xmlFGEN3450, "cd_shizai_new", 0), 0);
				funXmlWrite(reqAry[i], "nm_shizai_new", funXmlRead(xmlFGEN3450, "nm_shizai_new", 0), 0);
				funXmlWrite(reqAry[i], "cd_seizokojo", funXmlRead(xmlFGEN3450, "cd_seizokojo", 0), 0);
				funXmlWrite(reqAry[i], "cd_shokuba", funXmlRead(xmlFGEN3450, "cd_shokuba", 0), 0);
				funXmlWrite(reqAry[i], "cd_line", funXmlRead(xmlFGEN3450, "cd_line", 0), 0);
				funXmlWrite(reqAry[i], "cd_taisyoshizai", funXmlRead(xmlFGEN3450, "cd_taisyoshizai", 0), 0);
				funXmlWrite(reqAry[i], "cd_hattyusaki", funXmlRead(xmlFGEN3450, "cd_hattyusaki", 0), 0);
				funXmlWrite(reqAry[i], "chk_kanryo", funXmlRead(xmlFGEN3450, "chk_kanryo", 0), 0);
				//May Thu �yKPX@1602367�z 2016/09/28 add start
				funXmlWrite(reqAry[i], "nm_file_henshita", funXmlRead(xmlFGEN3450, "nm_file_henshita", 0), 0);
				funXmlWrite(reqAry[i], "file_path_henshita", funXmlRead(xmlFGEN3450, "file_path_henshita", 0), 0);
				//May Thu �yKPX@1602367�z 2016/09/28 add end
				break;
			}
			//�o�^�{�^�������F���ގ�z�e�[�u���o�^
		} else if (XmlId.toString() == "RGEN3470"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3470
				// ����No.�A�����H����A�s���� Xml �ɃZ�b�g
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				funXmlWrite(reqAry[i], "cd_seihin", funXmlRead(xmlFGEN3450, "cd_seihin", 0), 0);
				funXmlWrite(reqAry[i], "nm_shohin", funXmlRead(xmlFGEN3450, "nm_shohin", 0), 0);
				funXmlWrite(reqAry[i], "cd_shizai", funXmlRead(xmlFGEN3450, "cd_shizai", 0), 0);
				funXmlWrite(reqAry[i], "cd_shizai_new", funXmlRead(xmlFGEN3450, "cd_shizai_new", 0), 0);

				// �������̎��ގ�z�e�[�u���̍X�V����
				funXmlWrite(reqAry[i], "dt_tehai_koshin", funXmlRead(xmlFGEN3450, "dt_tehai_koshin", 0), 0);
				break;
			}
			//�o�^�{�^�������F���ގ�z�e�[�u���폜
		} else if (XmlId.toString() == "RGEN3480"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3480
				// ����No.
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3450, "cd_shain", 0), 0);
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3450, "nen", 0), 0);
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3450, "no_oi", 0), 0);
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3450, "no_eda", 0), 0);
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3450, "seq_shizai", 0), 0);

				// �������̎��ގ�z�e�[�u���̍X�V����
				funXmlWrite(reqAry[i], "dt_tehai_koshin", funXmlRead(xmlFGEN3450, "dt_tehai_koshin", 0), 0);

				break;
			}

			//�����i�������j
		}  else if (XmlId.toString() == "RGEN0012"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN0012
				// ����No
				var no_sisaku = frm.ddlShisakuNo.options[frm.ddlShisakuNo.selectedIndex].value.split("-");
				funXmlWrite(reqAry[i], "cd_shain", no_sisaku[0], 0);
				funXmlWrite(reqAry[i], "nen", no_sisaku[1], 0);
				funXmlWrite(reqAry[i], "no_oi", no_sisaku[2], 0);
				funXmlWrite(reqAry[i], "no_eda", no_sisaku[3], 0);
				break;
			}
		} else if (XmlId.toString() == "RGEN3455"){
			//�����{�^������
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			case 1:    //FGEN3450
				break;
			}
			// ������I���i�V�K�j
		} else if (XmlId.toString() == "RGEN3310"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3310
				// ���g�p�t���O
				funXmlWrite(reqAry[i], "flg_mishiyo", 1, 0);
				break;
			}
			//�o�^�{�^�������F���ގ�z�e�[�u���o�^
		}
    }
    return true;

}


//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
//       �F�Ckara     �F1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, kara) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //�����ޯ���̸ر
    funClearSelect(obj, kara);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //����Ͻ��i�Ώێ��ށj
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 2:    //������
            atbName = "nm_hattyusaki";
            atbCd = "cd_hattyusaki";
            break;
        case 3:    //�E��}�X�^
            atbName = "nm_shokuba";
            atbCd = "cd_shokuba";

            break;
        case 4:    //���C���}�X�^
            atbName = "nm_line";
            atbCd = "cd_line";
            break;
        case 5:    //����No�i���ރe�[�u���j
            atbName = "no_shisaku";
            atbCd = "no_shisaku";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
//�yKPX@1602367 20161019�z add start
            if(mode == 2) {
            	// ������R�[�h�̏ꍇ�̂�int�^�ɕϊ�����
            	objNewOption.value = parseInt(funXmlRead(xmlData, atbCd, i),10);
            } else {
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
//�yKPX@1602367 20161019�z add end
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;
    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

/*    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    // �����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;
*/
    return true;
}


//========================================================================================
// �R���{�{�b�N�X�I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �FXML�f�[�^
//       �F�Bkomoku   �F�������ږ�
//       �F�Ctext     �F�����l
// �߂�l�F��v�����s�ԍ���Ԃ�
// �T�v  �F�R���{�{�b�N�X��Index ��ݒ肷��
//========================================================================================
function funSetIndex(obj, xmlData, komoku, text) {

	if (text == "") {
		return 0;
	}

	//�����擾
	var reccnt = funGetLength(xmlData);
//�yKPX@1602367�z20161019 mod start
	var xmlValue ;
	for (var i = 0; i < reccnt; i++) {
		if(komoku == "cd_hattyusaki") {
			xmlValue = parseInt(funXmlRead(xmlData, komoku, i),10);
		} else {
			xmlValue = funXmlRead(xmlData, komoku, i)
		}
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (xmlValue == text) {
//�yKPX@1602367�z20161019 mod end
			//�����ޯ����Index�ݒ�i�󔒂���̏ꍇ�A+1�j
			obj.selectedIndex = i + 1;
			return i;
		}
	}

	//�����ޯ������̫�ĕ\��
	obj.selectedIndex = 0;

	return 0;

}

//========================================================================================
// XML�f�[�^��茟���s�̃R�[�h�������O �ϊ�
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�@xmlData  �FXML�f�[�^
//       �F�Akomoku   �F�������ږ�
//       �F�Btext     �F�����l
//       �F�Cret      �F�擾���ږ�
// return�F�R�[�h�l���͖��O
// �T�v  �F��v����R�[�h���͖��O���擾
//========================================================================================
function funGetXmldata(xmlData, komoku, text, ret) {

	//�����擾
	var reccnt = funGetLength(xmlData);
	// �߂�l
	var retStr = "";
	for (var i = 0; i < reccnt; i++) {
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (funXmlRead(xmlData, komoku, i) == text) {
			//�w�荀�ږ��̒l��Ԃ�
			retStr = funXmlRead(xmlData, ret, i);
			break;
		}
	}

  return retStr;
}

//========================================================================================
// �󔒒u���֐��i"" �� "&nbsp;"�j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F"" �� "&nbsp;" �֒u������
//========================================================================================
function funKuhakuChg(strChk) {

  //�󔒂̏ꍇ
  if(strChk == ""){
      return "";
  }
  //�󔒂łȂ��ꍇ
  else{
      return strChk;
  }

}

//�yKPX@1602367�z
//========================================================================================
// ���i�R�[�h���ύX���ꂽ
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �F�Ȃ�
// �T�v  �F���i�R�[�h�������ݒ肷��B
//========================================================================================
function funSeihinSearchBox(){
	var frm = document.frm00;          //̫�тւ̎Q��
	var args = new Array("");		// �_�C�A���O�ɓn���p�����[�^
    args[0] = window;			//"SQ230ShizaiTehaiInput.jsp";

	// ���i�R�[�h������ʂ����[�_���ŊJ��
    var retVal = funOpenModalDialog("../SQ330SeihinSearch/SQ330SeihinSearch.jsp"
    		, args
    		, "dialogHeight:500px;dialogWidth:600px;minimize=yes;maximize=yes;status:no;scroll:no");

    if(retVal != "") {
    	//���i�R�[�h���R�s�[
	    frm.inputSeihinCd.value = retVal;
	    //2017.02.03 �w�E������ʂ̋N�������Ɉ�a��������Ƃ̂��ƂŐ��i����������A�׎p�A���i���̍Č������{������ǉ� START Makoto Takada
	    funSeihinSearch();
	    //2017.02.03 �w�E������ʂ̋N�������Ɉ�a��������Ƃ̂��ƂŐ��i����������A�׎p�A���i���̍Č������{������ǉ� END Makoto Takada
	}
}

//========================================================================================
// �A�b�v���[�h�t�@�C�������ύX���ꂽ
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�\���p�t�@�C�����ɃZ�b�g����
//========================================================================================
function funChangeFile(row) {
	funSetInput(row);

}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funSetInput(row) {
	var inputName = document.getElementById("inputName-" + row);		// �\���p�t�@�C����
	var fileName = document.getElementById("fileName-" + row);			// �Q�ƃt�@�C����
	// �t�@�C���_�C�A���O
	if (fileName.value == "") {


		} else {
		// �Q�ƃt�@�C���t���p�X���t�@�C�������擾���\���p�ɃZ�b�g
		//�i�g���q�̃`�F�b�N�s�v�j
		inputName.value = funGetFileNm(fileName.value);
		// �A�b�v���[�h�t�@�C�����͕����F��ύX
		inputName.style.color = "red";

	}
    return true;

}

//========================================================================================
// �Q�ƃ{�^���L�[���������iENTER�L�[�Ή��j
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �Frow    �F�s�ԍ�
//         keyCode�F�L�[�R�[�h
// �T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funEnterFile(row, keyCode) {
	// ENTER�L�[�������A�Q�ƃ{�^���N���b�N�̓��������s
	if (keyCode == 13) {
		var fileName = document.getElementById("fileName-" + row);			// �Q�ƃ{�^��
		// ENTER�L�[�����ŃN���b�N�C�x���g�𔭐�������
		fileName.click();
	} else {
		return false;
	}
}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �Fstr   �t�@�C���p�X
// �߂�l�F�t�@�C����
// �T�v  �F�t�@�C���p�X�������Ƃ��ēn���ĕ�������
//========================================================================================
function funGetFileNm(str){

	var FileName = "";				// �߂�l

	var strTmp = str.split("\\");	// �t�H���_�[�A�t�@�C�����𕪉�

	// �t�@�C�������擾
	FileName = strTmp[strTmp.length - 1];
	return FileName;

}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/28
// ����  �Fstr   �t�@�C����
// �߂�l�F�t�@�C���p�X
// �T�v  �F�t�@�C�����������Ƃ��ēn���ĕ�������
//========================================================================================
function funSetfilePath(str){
	var FilePath = "";
	var result = str.lastIndexOf("\\");
    FilePath = str.substring(0,result);
	return FilePath;
}

//========================================================================================
// �o�^�{�^���������`�F�b�N����
// �쐬�ҁFMay Thu
// �쐬���F2016/09/30
// ����  �F�t�@�C����
// �߂�l�FBoolean    true�F�o�^��   false�F�s����������
// �T�v  �F�t�@�C�����̒��ɕs���������܂܂�Ă��邩�`�F�b�N����
//========================================================================================
function funChkFileNm(str){

	// �s���������܂܂�Ă���ꍇ�Afalse��Ԃ�
	var ret = str.match(/[;/?:@&=+$,%#]/);
	if (ret) {
		return false;
	}
	return true;

}
//========================================================================================
// �c�a�X�V�F�r������
// �쐬�ҁFMay thu
// �쐬���F2014/12/03
// �߂�l�F true  :�������s��
//          false�F�������s�s�i�X�V����Ă���j
// �T�v  �F�O��X�V�Ɠ��l�̃f�[�^���擾���āA�������s�s�\�Ƃ���B
//========================================================================================
function funKoshinChk(){
//���݂͍X�V�r���͍s��Ȃ��Ƃ��Ă���̂ŁA��������͋󃁃\�b�h�Ŏ��{
	return true;
}
//========================================================================================
// ���i�R�[�h��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �FkeyCode �F ENTER ���� TAB
// �T�v  �F���i�R�[�h��莎��No�ꗗ�A���i���A�׎p�̌������s��
//========================================================================================
function funSeihinSearch() {
	var frm = document.frm00;    //̫�тւ̎Q��
	// ���i�R�[�h
	var inputSeihin = document.getElementById("inputSeihinCd");
	var XmlId = "RGEN3420";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3420","FGEN3430");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3420I,xmlFGEN3430I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3420O,xmlFGEN3430O);

	// ���i���A�׎p���N���A
	frm.seihinNm.value = "";
	frm.nisugata.value = "";
	// �����H��
	frm.seizoKojoCd.value = "";
	frm.seizoKojoNm.value = "";
	//�����ޯ���̸ر
	funClearSelect(frm.ddlShisakuNo, 2);
	funClearSelect(frm.ddlShokuba, 2);
	funClearSelect(frm.ddlLine, 2);

	// ���ޏ��ꗗ�̏�����
	funClearList();

	//������ү���ޕ\��
	funShowRunMessage();

	// ���i�R�[�h��ޔ�
	sv_seihinCd = inputSeihin.value;

	// ���i�R�[�h�����̓`�F�b�N�� RGEN3420��
	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	// ����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3420, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		// ���i�R�[�h�Ƀt�H�[�J�X���Z�b�g�i���Ɉڂ��Ă��܂��j
		inputSeihin.focus();
		return false;
	}

	//������ү���ޔ�\��
	funClearRunMessage();

	//����No�ꗗ����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		// ����No�̃R���{�{�b�N�X���쐬
		funCreateComboBox(frm.ddlShisakuNo, xmlResAry[2], 5, 2);
	}else {
		// ����No���擾�ł��Ȃ�
		seihin_errmsg = "����No";
	}

	//���i���A�׎p
	if (funXmlRead(xmlResAry[3], "flg_return", 0) == "true") {
		frm.seihinNm.value = funXmlRead(xmlResAry[3], "name_hinmei", 0);
		frm.nisugata.value = funXmlRead(xmlResAry[3], "name_nisugata", 0);
	} else {
		// ���i�����擾�ł��Ȃ�
		if (seihin_errmsg != "") seihin_errmsg += "�E";
		seihin_errmsg += "���i��";
	}

	if (seihin_errmsg != ""){
		seihin_errmsg += "���擾�ł��܂���B���i�R�[�h���ē��͂��ĉ������B";
		funErrorMsgBox(seihin_errmsg);
		// ���i�R�[�h�Ƀt�H�[�J�X��߂��i���Ɉڂ��Ă��܂��j
		inputSeihin.focus();
		return false;
	}
	// �����H��i�����j�F����No.�I�����ɃZ�b�g
	return true;
}
//========================================================================================
// �����{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F���ރf�[�^�̌������s��
//========================================================================================
function refunSearch() {
	var frm = document.frm00;    //̫�тւ̎Q��

	// ���i�R�[�h���ύX����Ă���ꍇ�A�G���[���b�Z�[�W�\��
	if (frm.inputSeihinCd.value != sv_seihinCd) {
		funErrorMsgBox(E000066);
//		funErrorMsgBox("���i�R�[�h���ύX����Ă��܂��B\\n����No���������ĉ������B");
		frm.inputSeihinCd.focus();
		return false;
	}
    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
    funSearch0();
}

//========================================================================================
// ������R���{�{�b�N�X�ύX�i�V�K�I�����j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow   �F�s�ԍ�
// �T�v  �F������R���{�{�b�N�X�ύX���̏���
//========================================================================================
function funSetHattyusaki(row) {

	var flghattyusaki = document.getElementById("flgHattyusaki-" + row); //������

	if(flghattyusaki.value != ""){
		// �g�p�\�Ȕ�����i���g�p�������j�ɐݒ�ς�
		return;
	}

	var hattyusaki    = document.getElementById("ddlHattyusaki-" + row); //������
	var cd_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].value; //������R�[�h
	var nm_hattyusaki = hattyusaki.options[hattyusaki.selectedIndex].text; //�����於

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3310";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            1) == false) {
        return false;
    }

    // ������R���{�{�b�N�X
    var ddlHattyusaki = document.getElementById("ddlHattyusaki-" + row);

    // ������R���{�{�b�N�X�ݒ�
    funCreateComboBox(ddlHattyusaki, xmlFGEN3310O, 2, 2);

    // ������I��Index�̐ݒ�
    funSetIndex(ddlHattyusaki, xmlFGEN3310O, "cd_hattyusaki", cd_hattyusaki);

    flghattyusaki.value = "1";
    return;
}
//START�yKPX@1602367�zMakoto Takada �w��s�̓o�^�Ώۂ��o�^�K�v�����`�F�b�N���܂��B
//========================================================================================
// �s�̓o�^�`�F�b�N
// �쐬�ҁFMakoto Takada
// �쐬���F2017/02/09
// ����  �F�Ȃ�
// �T�v  �F�w��s�̓o�^�Ώۂ��o�^�K�v�����`�F�b�N����B
//========================================================================================
function funTorokuStatusChk(row) {
	var frm = document.frm00;          //̫�тւ̎Q��@@@
	// �\���t�@�C����
	var inputName = document.getElementById("inputName-" + row);
	if(inputName.style.color == "red") {
		return true;
	}
    var flg_tehai_status_org = document.getElementById("flg_tehai_status_org-" + row).value; 
	if (flg_tehai_status_org == "") {
		return true;
	}
	return false;
}
//END �yKPX@1602367�z�w��s�̓o�^�Ώۂ��o�^�K�v�����`�F�b�N���܂��B Makoto Takada
//START�yKPX@1602367�zMakoto Takada �E��A�������C���̃`�F�b�N
//========================================================================================
// �s�̓o�^�`�F�b�N(�r��������O�Ƀ`�F�b�N����K�v������̂�JAVASCRIPT�Ń`�F�b�N����B)
// �쐬�ҁFMakoto Takada
// �쐬���F2017/03/13
// ����  �F�Ȃ�
// �T�v �F
//========================================================================================
function funTorokuInputCheck() {
	var frm = document.frm00;          //̫�тւ̎Q��@@@
	// �E�ꂪ�ύX����Ă���ꍇ�A�G���[���b�Z�[�W�\��
	if (frm.ddlShokuba.selectedIndex < 1) {
		funErrorMsgBox("�E��͕K�{���ڂł��B\\n�I�����Ă��������B");
		return true;
	}
	// �������C���̃`�F�b�N���ύX����Ă���ꍇ�A�G���[���b�Z�[�W�\��
	if (frm.ddlLine.selectedIndex < 1) {
		funErrorMsgBox("�������C���͕K�{���ڂł��B\\n�I�����ĉ������B");
		return true;
	}
	return false;
}
//�yKPX@1602367 20170313�z add END
