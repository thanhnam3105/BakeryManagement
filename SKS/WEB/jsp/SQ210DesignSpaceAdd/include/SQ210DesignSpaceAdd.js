//========================================================================================
// ���ʕϐ�
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
//========================================================================================
//�f�[�^�ێ�
var rowcnt = 0;			// ���׍s��
var cd_kaisha = 0;		// ��ЃR�[�h

// DB�ɓo�^�ł���ő�t�@�C�����̒���
var MAXLEN_FILENM = 100;

// DB��������MAX�X�V����
var MAX_DtKoshin = "";

//========================================================================================
// �����\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConDesignSpaceAddId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�ꗗ�̸ر
    funClearList();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�@mode  �F�������[�h
//          1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏��i�����A��ށj���擾����
// �Z�b�V���������O��Key�̃f�U�C���X�y�[�X�ꗗ���擾�i�A�b�v���[�h��̍ă��[�h�Ή��j
//========================================================================================
function funGetInfo(mode) {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3250";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3250","FGEN3260");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3250I,xmlFGEN3260I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3250O,xmlFGEN3260O);

	//��ʃ��[�h���̌����L�[
	var start_kojo ="";
	var start_syokuba = "";
	var start_line = "";

	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}

	// ����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3250, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}

	// հ�ޏ��\��
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");
	// հ�ނ̉�ЃR�[�h�擾
	cd_kaisha = funXmlRead(xmlResAry[1], "cd_kaisha", 0);

	// ��̫�Ă̺����ޯ���ݒ�l��ޔ�
	xmlSA290.load(xmlResAry[2]);

	// ���
	xmlFGEN3250.load(xmlResAry[3]);

	// �����H��i���O�C�����[�U�̉�Ђ̕����ꗗ�j
	funCreateComboBox(frm.ddlSeizoKojo, xmlResAry[2], 2, 1);

	// �f�U�C���X�y�[�X���
	if (funXmlRead(xmlResAry[4], "flg_return", 0) == "true") {
		// �f�U�C���X�y�[�X���
		xmlFGEN3260.load(xmlResAry[4]);
		// �A�b�v���[�h��̍ă��[�h
		start_kojo = funXmlRead(xmlResAry[4], "cd_seizokojo", 0);
		start_syokuba = funXmlRead(xmlResAry[4], "cd_shokuba", 0);
		start_line = funXmlRead(xmlResAry[4], "cd_line", 0);

		// �擾�f�[�^�̐����H���Index���Z�b�g�i�󔒂Ȃ��j
		funSetIndex(frm.ddlSeizoKojo, xmlSA290, "cd_busho", start_kojo, 0);
	}

	// �E������ޯ����ݒ�
    funChangeKojo();

    if (start_syokuba) {
    	// �擾�f�[�^�̐E���Index���Z�b�g�i�󔒂���ˋ󔒂Ȃ��ɕύX�F2014/11/26�j
//		funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", start_syokuba, 1);
		funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", start_syokuba, 0);
		// ���C�������ޯ����ݒ�
		funChangeShokuba();

    	// �擾�f�[�^�̃��C����Index���Z�b�g�i�󔒂���ˋ󔒂Ȃ��ɕύX�F2014/11/26�j
//		funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", start_line, 1);
		funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", start_line, 0);

		// �ꗗ�\���F�{�^���̊�����
		if (funGetLength(xmlFGEN3260) > 0) {
	        //�\��
	        tblList.style.display = "block";
	        // �f�U�C���X�y�[�X���ꗗ�̍쐬
	        funCreateDesignSpaceList(xmlFGEN3260);

	    	// ���s�\�{�^����������
	    	frm.btnLineAdd.disabled = false;
	    	frm.btnLineDel.disabled = false;
	    	frm.btnInsert.disabled = false;
	    	frm.btnDelete.disabled = false;

		}
	}

	return true;
}

//========================================================================================
// �����H��R���{�{�b�N�X�A������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�����H��ɕR�t���E��R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3230O);

//	// �f�U�C�����ꗗ�̏�����
//	funClearList();
	// �{�^����񊈐�
	funDisBtn();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3230, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//�E������ޯ���̍쐬�F�󔒂Ȃ��ɕύX�i2014/11/26�j
//	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 2);
	funCreateComboBox(frm.ddlShokuba, xmlResAry[2], 3 , 1);
	//ײݺ����ޯ���̸ر
//	funClearSelect(frm.ddlLine, 2);
	// ���C�������ޯ����ݒ�
	funChangeShokuba();

	return true;

}

//========================================================================================
// �E��R���{�{�b�N�X�A������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�E��ɕR�t���������C���R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeShokuba() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3240";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3240");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3240I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3240O);


//	// �f�U�C�����ꗗ�̏�����
//	funClearList();
	// �{�^����񊈐�
	funDisBtn();

	// �󔒂Ȃ��ׁ̈A�C���i2014/11/26�j
//	if (frm.ddlShokuba.selectedIndex == 0) {
	if (frm.ddlShokuba.selectedIndex < 0) {
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

	//�����ޯ���̍쐬�F�󔒂Ȃ��ɕύX�i2014/11/26�j
//	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 2);
	funCreateComboBox(frm.ddlLine, xmlResAry[2], 4 , 1);

	return true;

}


//========================================================================================
// �������C���R���{�{�b�N�X
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�ꗗ����������
//========================================================================================
function funChangeLine() {

//	// �f�U�C�����ꗗ�̏�����
//	funClearList();
	// �{�^����񊈐�
	funDisBtn();
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
	if (frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value == "") {
		funSelChk.Msg = "�����H�ꂪ�I������Ă��܂���B";
		return "";
	}
	strRet += frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value + "_";

	// �E��
	if (frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value == "") {
		funSelChk.Msg = "�E�ꂪ�I������Ă��܂���B";
		return "";
	}
	strRet += frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value + "_";

	// �������C��
	if (frm.ddlLine.options[frm.ddlLine.selectedIndex].value == "") {
		funSelChk.Msg = "�������C�����I������Ă��܂���B";
		return "";
	}
	strRet += frm.ddlLine.options[frm.ddlLine.selectedIndex].value + "_";

	return strRet;
}

//========================================================================================
// �����{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X�̌������s��
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��

    // �������I������Ă��邩�H
    // �i���ގ�z���͂ł̌����ł͕K�{�����łȂ��̂�SQL��InputCheck�ɂ���Ȃ��j
	if (funSelChk() == "") {
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

    //������ү���ޕ\��
    funShowRunMessage();

    //��������
    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
    setTimeout(function(){ funDataSearch() }, 0);

}

//========================================================================================
// ��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X�f�[�^�̌������s��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3260";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3260");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3260I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3260O);

    //�I���s�̏�����
    funSetCurrentRow("");

    //�ꗗ�̸ر
    funClearList();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        funClearRunMessage();
        return false;
    }

    //�ް�����������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";

        // �f�U�C���X�y�[�X���ꗗ�̍쐬
        funCreateDesignSpaceList(xmlResAry[2]);

    } else {
    	// �Ώۃf�[�^�Ȃ�
    	funErrorMsgBox(E000030);

        //��\���F�Ώۃf�[�^�Ȃ�
        tblList.style.display = "none";
        // �������̍X�V����
        MAX_DtKoshin = "";
    }

	// ���s�\�{�^����������
	frm.btnLineAdd.disabled = false;
	frm.btnLineDel.disabled = false;
	frm.btnInsert.disabled = false;
	frm.btnDelete.disabled = false;

    //������ү���ޔ�\��
    funClearRunMessage();
	return true;

}

//========================================================================================
// �s�ǉ��{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X���ɍs�ǉ�����
//========================================================================================
function funLineAdd() {

	// �������I�����Ă��Ȃ����i�����サ�����Ȃ��j
	if (funSelChk() == "") {
//		funErrorMsgBox(E000031);
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

    //�f�U�C���X�y�[�X���ꗗ�\��
    tblList.style.display = "block";

    // ���׍s��ǉ�
    funAddDesignSpace(++rowcnt, "");

    // ���
    var ddlSyurui = document.getElementById("ddlSyurui-" + rowcnt);

	// ��ނ̃Z���N�g�l�ݒ�i�󔒂���j
    funCreateComboBox(ddlSyurui, xmlFGEN3250O, 1, 2);
}

//========================================================================================
// �s�폜�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X���ꗗ�̎w��s���\���ɂ���
//         �ۑ��f�[�^�����鎞�F�c�a�폜�A�f�[�^�폜
//========================================================================================
function funLineDel() {

	var frm = document.frm00;   //̫�тւ̎Q��
	var retBool = false;		//�폜�t�@�C�������鎞�F�폜����

	// �s�I������Ă��Ȃ��ꍇ
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }

    // �s�ԍ�
    var row_no = funGetCurrentRow() + 1;
	// �ۑ��t�@�C���̃T�u�t�H���_�[��
	var subFolder = "";

	// �ۑ��t�@�C�������擾�i��\���A��ރR�[�h��t���j
	var fileNm = document.getElementById("nm_file-" + row_no);

	// �I���s�Ƀt�@�C�����ۑ�����Ă���ꍇ�A�f�[�^�폜
	if (fileNm.value != "") {
		// �폜�m�F
		if (funConfMsgBox(I000004) != ConBtnYes) {
			return;
		}

		// �ۑ��t�@�C���̃T�u�t�H���_�[�����擾�i�I��ԍ���_�Ōq���j
		subFolder = funSelChk();
		// �������I������Ă��Ȃ��ꍇ
		if (subFolder == "") {
			funErrorMsgBox(funSelChk.Msg);
			return false;
		}

		//** ̧���߽�̑ޔ� **/
		// �A�b�v���[�h�p�X�iconst��`���j
		frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";

		// �ۑ��t�@�C���������ރR�[�h�𕪊�
		//  [1]�F��ރR�[�h�i�J�e�S���P_�J�e�S���Q�j�A [2]�F�t�@�C����
		var strTmp = fileNm.value.split("\\");

		// �폜�t�@�C���p�X�i�T�u�t�H���_�[���F�����R�[�h�܂ށj
		//  �t�H���_�[���폜����̂ŁA�t�@�C�����͓n���Ȃ�
		frm.strFilePath.value = subFolder + strTmp[0];


		// �r�������F�������̍X�V��������ύX���������珈�����~
		if (!funKoshinChk()) {
			return false;
		}

		// �t�@�C���폜�i�T�[�o�[�j
		funFileDeleteUrlConnect(ConConectGet, frm);

		// �t�@�C���폜�i�c�a�j
		retBool = funDesignSpaceDelLine();

		fileNm.value = "";			// �ۑ��t�@�C����������
	}

	// reNumber ���K�v�ƂȂ�ׁA�s�ԍ��͔�\��
	var tr = document.getElementById("tr" + row_no);
	// �I���s���B��
	tr.style.display = "none";

    //�I���s�̏�����
    funSetCurrentRow("");

	if (retBool) {
		//����F�폜����
		funInfoMsgBox(I000007);

		// �Č����F�Ώۍs�ȊO�̃f�[�^�̍X�V�ҁE������UPDATE�����
		funSearch();
	}
}


//========================================================================================
// �o�^�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X���̓o�^�i�􂢑ւ��j���s��
//========================================================================================
function funUpdate() {

    var frm = document.frm00;   //̫�тւ̎Q��
    var upFildNm = "";          //�A�b�v���[�h����t�B�[���h��
    var delFileNm = "";         //�폜����t�@�C����
    var lstSyurui = {};         //�o�^�����ނ̔z��i�d���`�F�b�N�p�j
    var strMsg = ""				//�m�F���b�Z�[�W�t������

    // �ۑ��t�@�C���̃T�u�t�H���_�[�擾�i�H��A�E��A�������C����_�Ōq���j
    var subFolder = funSelChk();
    // �������I������Ă��Ȃ��ꍇ
	if (subFolder == "") {
//		funErrorMsgBox(E000031);
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
    for(var i = 1; i <= rowcnt; i++){
        // �s�I�u�W�F�N�g
    	var tr = document.getElementById("tr" + i);

    	// �A�b�v���[�h����t�@�C����
    	var fileName = document.getElementById("fileName-" + i);

    	// �\���t�@�C����
    	var inputName = document.getElementById("inputName-" + i);
    	// �ۑ�����Ă���t�@�C�����i��\���j
    	var nm_file = document.getElementById("nm_file-" + i);

        // ��ރI�u�W�F�N�g
        var ddlSyurui = document.getElementById("ddlSyurui-" + i);

    	// ��ރR�[�h�i�Z���N�g�l�j
        var selSyurui = ddlSyurui.options[ddlSyurui.selectedIndex].value;

    	// �\���s�̂ݏ�������
    	if (tr.style.display != "none") {
    		// �\���s
    		displayln = true;

    		// �\���pinput�t�@�C�������󔒂̏ꍇ�A�G���[�i�c�a�o�^�ł��Ȃ��j
    		if (inputName.value == "") {
    			funErrorMsgBox(E000027);
    			return false;
    		}

    		// ��ނ��I������Ă��Ȃ�
    		if (ddlSyurui.selectedIndex < 1) {
    			funErrorMsgBox("���ׂĂ̎�ނ�I�����ĉ������B");
    			return false;
    		}
    		// ������ނ��I������Ă��Ȃ����I
    		if (lstSyurui[selSyurui] == null) {
    			lstSyurui[selSyurui] = ddlSyurui.options[ddlSyurui.selectedIndex].innerHTML;
    		} else {
    			// ��ނ̏d��
    			funErrorMsgBox("�I��������ނ��d�����Ă��܂��B");
    			return false;
    		}
    		// �t�@�C�����̒����`�F�b�N�i�c�a�����j
			if (inputName.value.length > MAXLEN_FILENM) {
				funErrorMsgBox("�t�@�C�������������܂��B�i�P�O�O�����܂Łj�F\\n" + inputName.value);
				return false;
			}
			// �t�@�C�����̕s�������`�F�b�N
			if (!funChkFileNm(inputName.value)) {
    			funErrorMsgBox("�t�@�C�����ɕs���������܂܂�Ă��܂��B�F\\n" + inputName.value);
    			return false;
			}


    		// �N���A�{�^������������Ă��Ȃ�����
    		if ((fileName.value != "") && (inputName.value != "")) {
    			// �A�b�v���[�h�����F�T�[�o�[�n���i":::"�ŋ�؂�j
    			// �t�B�[���h���i�A�b�v���[�h�`�F�b�N�p�j
    			upFildNm += "fileName-" + i + ":::";
    			// �T�u�t�H���_�[�Ɏ�ރR�[�h��ǉ�
    			subFlst += subFolder + selSyurui + ":::";
    		}

    		// �ύX�O�t�@�C�����폜
    		// �c�a�ɕۑ�����Ă��� ���� �ύX���ꂽ�Ԏ��t�@�C���̎��A
    		if ((nm_file.value != "") && (inputName.style.color == "red")) {
    			// �T�u�t�H���_�[����t������B�i":::"�ŋ�؂�j
    			// nm_file �ɂ͕ۑ��f�[�^�̎�ރR�[�h���t���ρi"\\"�ŋ�؂��Ă���j
    			delFileNm += subFolder + nm_file.value + ":::";
    		}
    	}
    }

    // �\���s���Ȃ�
	if (!displayln) {
    	funErrorMsgBox(E000030);
        return false;
	}

    if (upFildNm == "") {
    	strMsg = "�i�A�b�v���[�h�Ώۃt�@�C���͂���܂���B�j"
    }
    // �o�^�m�F
	if (funConfMsgBox(I000002 + strMsg) != ConBtnYes) {
		return;
	}

	// �r�������F�������̍X�V��������ύX���������珈�����~
	if (!funKoshinChk()) {
		return false;
	}


	//** ̧���߽�̑ޔ� **/
    // �A�b�v���[�h�p�X�iconst��`���j
    frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
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

    // �f�U�C���X�y�[�X�t�@�C���c�a�X�V����
	// �I�������Ńf�U�C���X�y�[�X�t�@�C�����폜��A�o�^
	if (funDesignSpaceUpdate("RGEN3280")) {
        //����o�^
        funInfoMsgBox(I000005);
	}

}

//========================================================================================
// �폜�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F�w������̃f�U�C���X�y�[�X�����폜����
//========================================================================================
function funDelete() {

    var frm = document.frm00;   //̫�тւ̎Q��
    var delFileNm = "";         //�폜����t�@�C����

    // �\���s�������ꍇ�A�G���[
	var displayln = false;

    // �ۑ��t�@�C���̃T�u�t�H���_�[�擾�i�H��A�E��A�������C����_�Ōq���j
    var subFolder = funSelChk();
    // �������I������Ă��Ȃ��ꍇ
	if (subFolder == "") {
//		funErrorMsgBox(E000031);
		funErrorMsgBox(funSelChk.Msg);
		return false;
	}

	// �ꗗ�f�[�^���Ȃ�
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	// �\������Ă���ꗗ���`�F�b�N
    for(var i = 1; i <= rowcnt; i++){
        // �s�I�u�W�F�N�g
    	var tr = document.getElementById("tr" + i);

    	// �ύX�O�t�@�C�����폜
    	var nm_file = document.getElementById("nm_file-" + i);
    	var strTmp = null;

    	// �\���s�̂ݏ�������
    	if (tr.style.display != "none") {
    		// �\���s
    		displayln = true;

    		// �ۑ�����Ă���t�@�C����������ꍇ�ɍ폜�i��\���j
    		if (nm_file.value != "") {
    			// nm_file �ɂ͕ۑ��f�[�^�̎�ރR�[�h���t���ρi"\\"�ŋ�؂��Ă���j
    			// ��ރR�[�h�𕪊� [1]�F��ރR�[�h�i�J�e�S���P_�J�e�S���Q�j�A [2]�F�t�@�C����
    			strTmp = nm_file.value.split("\\");

    			// �폜�t�@�C���p�X�ɃT�u�t�H���_�[����t������B�i":::"�ŋ�؂�j
    			//  �t�H���_�[���폜����̂ŁA�t�@�C�����͓n���Ȃ�
    			delFileNm += subFolder + strTmp[0] + ":::";
    		}
    	}
    }

    // �\���s���Ȃ�
    if (!displayln) {
		funErrorMsgBox(E000030);
		return false;
    }

    // �폜�m�F
    if (funConfMsgBox(I000004) != ConBtnYes) {
    	return;
    }

    // �r�������F�������̍X�V��������ύX���������珈�����~
	if (!funKoshinChk()) {
		return false;
	}


    if (delFileNm != "") {
    	//** ̧���߽�̑ޔ� **/
    	// �A�b�v���[�h�p�X�iconst��`���j
    	frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
    	// �폜�t�@�C���p�X�i":::"�ŋ�؂�j
    	frm.strFilePath.value = delFileNm;

    	// �T�[�o�[�t�@�C���̍폜
		funFileDeleteUrlConnect(ConConectGet, frm);

    }

    // �f�U�C���X�y�[�X�t�@�C���c�a�X�V����
	// �I�������̃f�U�C���X�y�[�X�t�@�C�����폜
	if (funDesignSpaceUpdate("RGEN3285")) {
        //����F�폜����
        funInfoMsgBox(I000007);
	}

	// �f�U�C�����ꗗ�̏�����
	funClearList();

}

//========================================================================================
// �f�U�C���X�y�[�X�t�@�C��DB�X�V����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/17
// ����  �FXmlId     �FXmlId
// �T�v  �F�f�U�C���X�y�[�X�t�@�C��DB�X�V�̎��s
//========================================================================================
function funDesignSpaceUpdate(XmlId){

	var frm = document.frm00;    //̫�тւ̎Q��
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3280");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3280I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3280O);

    // ������ү���ޕ\��
    funShowRunMessage();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
	    //������ү���ޔ�\��
	    funClearRunMessage();
		return false;
	}

	//�f�U�C���X�y�[�X�t�@�C��DB�o�^
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3280, xmlReqAry, xmlResAry, 1) == false) {
	    //������ү���ޔ�\��
	    funClearRunMessage();
		return false;
	}

    //������ү���ޔ�\��
    funClearRunMessage();


    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	return true;
    } else {
        //error
    	funErrorMsgBox(dspMsg);
        return false;
    }

}

//========================================================================================
// �s�폜�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/17
// ����  �F
// �T�v  �F�f�U�C���X�y�[�X�t�@�C��DB�폜�����̎��s
//========================================================================================
function funDesignSpaceDelLine(){

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3270";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3270");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3270I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3270O);

    // ������ү���ޕ\��
    funShowRunMessage();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}


	//�f�U�C���X�y�[�X�t�@�C���s�폜
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3270, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

    //������ү���ޔ�\��
    funClearRunMessage();

    //����ү���ނ̕\��

    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	return true;

    } else {

        return false;
    }


}


//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    var frm = document.frm00;    //̫�тւ̎Q��

	// �ꗗ�̸ر
	xmlFGEN3260O.src = "";
	tblList.style.display = "none";
	// ���׍s�i�ǂݍ��ݕ��j
	var detail = document.getElementById("detail");
	// ���׃f�[�^�폜
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}

	// �s�J�E���g�̃N���A
	rowcnt = 0;
	// �{�^����񊈐�
	funDisBtn();

}

//========================================================================================
// �N���A����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/27
// ����  �F�Ȃ�
// �T�v  �F�u�s�ǉ��v�`�u�폜�v�{�^����񊈐��ɂ���
//========================================================================================
function funDisBtn() {

    var frm = document.frm00;    //̫�тւ̎Q��

	// �{�^���̏�����Ԑݒ�F�u�����v�����܂Ŕ񊈐�
	frm.btnLineAdd.disabled = true;
	frm.btnLineDel.disabled = true;
	frm.btnInsert.disabled = true;
	frm.btnDelete.disabled = true;

}

//========================================================================================
// �Q�l�����{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
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
// �c�a�X�V�F�r������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/12/03
// �߂�l�F true  :�������s��
//          false�F�������s�s�i�X�V����Ă���j
// �T�v  �F�J�e�S���f�[�^�̍X�V�������擾
//========================================================================================
function funKoshinChk(){

	var XmlId = "RGEN3410";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3410");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3410I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3410O);

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3410, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//�ް������F�f�[�^�����݂��Ȃ��Ă� max_koshin ��""���Ԃ�
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		var max_koshin = funXmlRead(xmlResAry[2], "max_koshin", 0);
//		alert("�������F" + MAX_DtKoshin + "   ���݁F" + max_koshin);

		// 1.����������f�[�^���X�V���ꂽ
		// 2.�������Ƀf�[�^�����������̂ɍ쐬����Ă���
		// 3.�������Ƀf�[�^���������̂ɍ폜����Ă���
		if (MAX_DtKoshin != max_koshin) {

			// �G���[���b�Z�[�W�\��
			funErrorMsgBox("���ɍX�V����Ă��܂��B\\n�ēx�����������Ă��������B");

			return false;

		}

	}
	return true;


}

//========================================================================================
// �I���{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// �T�v  �F�Z�b�V���������N���A����
//========================================================================================
function funEndClick(){

    var XmlId = "RGEN3265";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

	//�I������
	funEnd();
}

//========================================================================================
// �I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// �T�v  �F�I������
//========================================================================================
//function funEnd(){
//
//	var wUrl;
//
//	// �������j���[
//	wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//	// �J��
//	funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//	return true;
//}
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
// �f�U�C���X�y�[�X���@�e�[�u���ꗗ�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �FxmlData    �FXML�f�[�^
// �T�v  �F�f�U�C���X�y�[�X���ꗗ���쐬����
//========================================================================================
function funCreateDesignSpaceList(xmlData) {

    //�����擾���A�s����ۑ�
	rowcnt = funGetLength(xmlData);
    // �������̍X�V����
    MAX_DtKoshin = "";

    for(var i = 1; i <= rowcnt; i++){

    	if (MAX_DtKoshin < funXmlRead(xmlData, "dt_koshin", i-1)) {
    		// �������̍X�V������MAX�l��ۑ�
    		MAX_DtKoshin = funXmlRead(xmlData, "dt_koshin", i-1);
    	}

    	// �s�ǉ�
    	funAddDesignSpace(i, xmlData);

    	// ��ރR�[�h�̎擾�i�J�e�S����"_"�Ō����j
    	var cd_syurui = funXmlRead(xmlData, "cd_literal", i-1) + "_" +  funXmlRead(xmlData, "cd_2nd_literal", i-1);

    	// ��ޖ��̎擾
    	var syuruiNm = funXmlRead(xmlData, "nm_syurui", i-1);

        // ���
        var ddlSyurui = document.getElementById("ddlSyurui-" + i);
        // �\���t�@�C����
    	var inputName = document.getElementById("inputName-" + i);

    	// ��ނ̃Z���N�g�l�ݒ�i�󔒂���j
        funCreateComboBox(ddlSyurui, xmlFGEN3250O, 1, 2);

        // ��ޑI��Index�̐ݒ� �i��Q�J�e�S���j
        funSetIndex(ddlSyurui, xmlFGEN3250O, "cd_2nd_literal", cd_syurui, 1);

        // �ۑ��t�@�C������\���i��ޑI��Index��ύX���Ă���Z�b�g�j
        inputName.value = funXmlRead(xmlData, "nm_file", i-1);

   }

}

//========================================================================================
// �f�U�C���X�y�[�X��� ���׍s�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �F�@row     �F�s�ԍ�
//       �F�AxmlData �FXML�f�[�^�i�u�s�ǉ��v���́A""�j
// �T�v  �F�f�U�C���X�y�[�X���ꗗ�̍s�ǉ�����
//========================================================================================
function funAddDesignSpace(row, xmlData) {

	var detail = document.getElementById("detail");

    var html;

    // �s�ݒ�
    var tr = document.createElement("tr");
    tr.setAttribute("class", "disprow");
    tr.setAttribute("id", "tr" + row);

    // ���i��\���j
    var td1 = document.createElement("td");
    td1.setAttribute("class", "column");
    td1.innerHTML = row;
    td1.style.textAlign = "center";
    td1.style.width = 29;
    td1.style.display = "none";
    tr.appendChild(td1);

    // �I�����W�I�{�^��
    var td2 = document.createElement("td");
    td2.setAttribute("class", "column");
    html = "<input type=\"radio\" name=\"chk\" value=\"" + row + "\" ";
    html += "onkeydown=\"if(event.keyCode == 13){funEnterRadio(" + row + ");}\" >";
    td2.innerHTML = html;
    td2.style.textAlign = "center";
    td2.style.width = 30;
    tr.appendChild(td2);


    // ��ށi�Z���N�g�{�b�N�X�j
    var td3 = document.createElement("td");
    td3.setAttribute("class", "column");
    html = "<select name=\"ddlSyurui-" + row + "\" id=\"ddlSyurui-" + row + "\" style=\"width:270px;\" onChange=\"funChangeSyurui(" + row + ")\" >";
    html += "</select>";
    td3.innerHTML = html;
    td3.style.textAlign="left";
    td3.style.width = 280;
    tr.appendChild(td3);

    // �t�@�C����
    var td4 = document.createElement("td");
    td4.setAttribute("class", "column");
    // �ۑ�����Ă���t�@�C�����i��\���j
    html = "<input type=\"hidden\" id=\"nm_file-" + row + "\" value=\"";
    // �����{�^�������i�f�[�^�����݂���j�̏ꍇ
    if (xmlData != "") {
    	// ��ރR�[�h�̎擾�i�J�e�S����"_"�Ō����j
    	var cd_syurui = funXmlRead(xmlData, "cd_literal", row-1) + "_" +  funXmlRead(xmlData, "cd_2nd_literal", row-1);
    	// �����t�@�C�����Ɏ�ރR�[�h��t������i�T�u�t�H���_�[�̈ꕔ�j
    	html += cd_syurui + "\\";
    	html += funXmlRead(xmlData, "nm_file", row-1);
    }
    html += "\" tabindex=\"-1\">";

    // �Q�ƃ{�^����input ��\���p�t�@�C�����ŉB��
    html += "<div style=\"position: relative;\">";
    // �Q�ƃ{�^���ionChange�C�x���g�����ł͕\���t�@�C�������N���A���ē����t�@�C����I���������Z�b�g����Ȃ��̂�onclick�C�x���g�ŕ\���t�@�C�����ɃZ�b�g�j
    html += "<input type=\"file\" class=\"normalbutton\" value=\"\" style=\"width:528px;\" name=\"fileName-" + row + "\" id=\"fileName-" + row + "\"";
    html += "onChange=\"funChangeFile(" + row + ")\" onclick=\"funSetInput(" + row + ")\" ";
    // �Q�ƃ{�^��ENTER�L�[�Ń_�C�A���O���J��
    html += "onkeydown=\"funEnterFile(" + row + ", event.keyCode);\" >";
    // �\���p�t�@�C�����i�X�N���[�����Ă��^�C�g���̉��ɕ\���j
    html += "<span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
    html += "<input type=\"text\" value=\"";
    // �\���p�t�@�C�����F�^�u�ړ��𖳌��Ƃ���
    html += "\" name=\"inputName-" + row + "\" id=\"inputName-" + row + "\" size=\"91\" readonly tabindex=\"-1\" >";
    html += "</span>";
    html += "</div>";
    td4.innerHTML = html;
    td4.style.textAlign="left";
    td4.style.width = 530;
    tr.appendChild(td4);

    // �N���A�{�^���F�폜�i2014-11-20�j
/*    var td5 = document.createElement("td");
    td5.setAttribute("class", "column");
    html = "<input type=\"button\" class=\"normalbutton\" name=\"btnClear-" + row + "\" id=\"btnClear-" + row + "\" ";
    html += "value=\"�N���A\" onClick=\"funClearInput(" + row + ")\" >";
    td5.innerHTML = html;
    td5.style.textAlign="center";
    td5.style.width = 100;
    tr.appendChild(td5);
*/
    // �X�V��
    var td6 = document.createElement("td");
    td6.setAttribute("class", "column");
    td6.setAttribute("name", "nm_koshin-" + row);
    td6.setAttribute("id", "nm_koshin-" + row);
    if (xmlData != "") {
    	td6.innerHTML = funXmlRead(xmlData, "nm_koshin", row-1);
    } else {
    	td6.innerHTML = "&nbsp;"
    }
    td6.style.textAlign = "center";
    td6.style.width = 160;
    tr.appendChild(td6);

	// �X�V����
    var td7 = document.createElement("td");
    td7.setAttribute("class", "column");
    td7.setAttribute("name", "dt_koshin-" + row);
    td7.setAttribute("id", "dt_koshin-" + row);
    if (xmlData != "") {
    	td7.innerHTML = funXmlRead(xmlData, "dt_koshin", row-1);
    } else {
    	td7.innerHTML = "&nbsp;"
    }
    td7.style.textAlign = "left";
    td7.style.width = 150;
    tr.appendChild(td7);

    detail.appendChild(tr);

}

//*** �f�U�C���X�y�[�X���F���׍s������ ***//
//========================================================================================
// ��ޑI������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�t�@�C�������N���A
//========================================================================================
function funChangeSyurui(row) {
	var frm = document.frm00;    //̫�тւ̎Q��

	var inputName = document.getElementById("inputName-" + row);

	// �u�Q�Ɓv�{�^���Őݒ肵���t�@�C�������H
	if (inputName.style.color != "red") {
		// �ۑ��t�@�C�����̎��A�N���A����
		// �A�b�v���[�h�t�@�C�����w�肷�邱�ƁI
		inputName.value = "";
	}

	// ENTER�L�[�ōs�I������Ή�
	funEnterRadio(row)
}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funSetInput(row) {

	var inputName = document.getElementById("inputName-" + row);		// �\���p�t�@�C����
	var fileName = document.getElementById("fileName-" + row);			// �Q�ƃt�@�C����
	var nm_koshin = document.getElementById("nm_koshin-" + row);		// �X�V��
	var dt_koshin = document.getElementById("dt_koshin-" + row);		// �X�V��

	// �t�@�C���_�C�A���O
	if (fileName.value == "") {
		// �N���A�{�^�����폜���ꂽ�̂ł����ł̒u�������͕s�v�ƂȂ����B
/*		if (inputName.style.color == "red") {
			//inputName.value = fileName.value;
		}
*/
/*
		// �Q�ƃt�@�C������\���p�ɃZ�b�g�i�A�b�v���[�h�t�@�C�����ƈ�v������j
		inputName.value = fileName.value;
		// �A�b�v���[�h�t�@�C�����͕����F��ύX
		inputName.style.color = "red";
		// �X�V�ҁE�X�V�����N���A
		nm_koshin.innerHTML = "&nbsp;";
		dt_koshin.innerHTML = "&nbsp;";
*/

		} else {
		// �Q�ƃt�@�C���t���p�X���t�@�C�������擾���\���p�ɃZ�b�g
		//�i�g���q�̃`�F�b�N�s�v�j
		inputName.value = funGetFileNm(fileName.value);
		// �A�b�v���[�h�t�@�C�����͕����F��ύX
		inputName.style.color = "red";
		// �X�V�ҁE�X�V�����N���A
		nm_koshin.innerHTML = "&nbsp;";
		dt_koshin.innerHTML = "&nbsp;";
	}
    return true;

}


//========================================================================================
// �Q�ƃ{�^���L�[���������iENTER�L�[�Ή��j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/20
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
// ���W�I�{�^�����������iENTER�L�[�j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/20
// ����  �Frow   �F�s�ԍ��i�P�`�j
// �T�v  �FENTER�L�[���s�I�����������s�����
//========================================================================================
function funEnterRadio(row) {

    var frm = document.frm00;    //̫�тւ̎Q��

    if (!!frm.chk[row-1]) {
		frm.chk[row-1].click();
	} else {
		// Index ���t���Ă��Ȃ�
		frm.chk.click();
	}
}

//========================================================================================
// �A�b�v���[�h�t�@�C�������ύX���ꂽ
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�\���p�t�@�C�����ɃZ�b�g����
//========================================================================================
function funChangeFile(row) {

	funSetInput(row);

}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �Fstr   �t�@�C���p�X
// �߂�l�F�t�@�C����
// �T�v  �F�t�@�C���p�X�������Ƃ��ēn���ĕ�������
//========================================================================================
function funGetFileNm(str){

	var FileName = "";				// �߂�l

	var strTmp = str.split("\\");	// �t�H���_�[�A�t�@�C�����𕪉�

	// �t�@�C�������擾
	FileName = strTmp[strTmp.length - 1];
	return FileName

}

//========================================================================================
// �N���A�{�^�����������F�{�^���폜�i2014-11-20�j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/11
// ����  �Frow   �F�s�ԍ�
// �T�v  �F�t�@�C�������N���A
//========================================================================================
function funClearInput(row) {
	var frm = document.frm00;    //̫�тւ̎Q��

    // �\���p�t�@�C����
	var inputName = document.getElementById("inputName-" + row);
	// �X�V��
	var nm_koshin = document.getElementById("nm_koshin-" + row);
	// �X�V��
	var dt_koshin = document.getElementById("dt_koshin-" + row);

    // �\���p�ɃZ�b�g
    inputName.value = "";
    // �X�V�ҁE�X�V�����N���A
    nm_koshin.innerHTML = "&nbsp;";
    dt_koshin.innerHTML = "&nbsp;";

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
		if (XmlId.toString() == "RGEN3250"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //SA290
				funXmlWrite(reqAry[i], "id_user", "", 0);
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdDesignSpaceAdd, 0);
				break;
			case 2:    //FGEN3250�i��ނ��擾�j
				break;
			case 3:    //FGEN3260�i�f�U�C���X�y�[�X�ꗗ�j
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
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
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
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				break;
			}

			//�����{�^������
		} else if (XmlId.toString() == "RGEN3260"){
			var cd_seizokojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;
			var cd_shokuba = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
			var cd_line = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;

            var put_code = mode + "-" + cd_seizokojo + "-" + cd_shokuba + "-" + cd_line;

            // �����R�[�h�u���y�����H��:::�E��:::�������C���z
            put_code = put_code.replace(/-/g,":::");

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				// �A�b�v���[�h��̍ă��[�h�p
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
				break;
			case 1:    //FGEN3260
				funXmlWrite(reqAry[i], "cd_seizokojo", cd_seizokojo, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", cd_shokuba, 0);
				funXmlWrite(reqAry[i], "cd_line", cd_line, 0);
				break;
			}

			//�I���{�^�������i�����R�[�h���N���A�j
		} else if (XmlId.toString() == "RGEN3265"){

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			}

			// �s�폜�{�^������
		} else if (XmlId.toString() == "RGEN3270"){

		    // �I���s�ԍ�
		    var row_no = funGetCurrentRow() + 1;
			// �I���s�̕ۑ��t�@�C��������ރR�[�h�ƃt�@�C�����ɕ���
			var str_fnm = document.getElementById("nm_file-" + row_no).value;
			var ary_fnm = str_fnm.split("\\");
			// ��ރR�[�h
			var cd_syurui =ary_fnm[0];
			// ��ރR�[�h����ޖ����擾
			var nm_syurui = funGetXmldata(xmlFGEN3250O, "cd_2nd_literal", cd_syurui, "nm_2nd_literal");

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);

				funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
				funXmlWrite(reqAry[i], "nm_syurui", nm_syurui, 0);
				break;
			}

			// �o�^�{�^������
		} else if (XmlId.toString() == "RGEN3280"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //�o�^
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "syoriMode", "ADD", 0);

				var str_fnm = "";
				var cd_syurui = "";
				var nm_syurui = "";

				// �\���s�̎�ށA�t�@�C������ۑ��i":::"�Ōq����j
				// �\������Ă���ꗗ���`�F�b�N
			    for(var ln = 1; ln <= rowcnt; ln++){
			        // �s�I�u�W�F�N�g
			    	var tr = document.getElementById("tr" + ln);
			    	// �\���s�̂݃`�F�b�N
			    	if (tr.style.display != "none") {
			    		// �\���t�@�C����
			    		var inputName = document.getElementById("inputName-" + ln);
			    		// ��ރI�u�W�F�N�g
			    		var ddlSyurui = document.getElementById("ddlSyurui-" + ln);

			    		// ��ޖ��i�Z���N�g�l�j
			    		cd_syurui += ddlSyurui.options[ddlSyurui.selectedIndex].value + ":::";
			    		nm_syurui += ddlSyurui.options[ddlSyurui.selectedIndex].innerText + ":::";
			    		// �o�^�t�@�C����
			    		str_fnm += inputName.value + ":::";
			    	}
			    }

			    funXmlWrite(reqAry[i], "nm_syurui", nm_syurui, 0);
			    funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
			    funXmlWrite(reqAry[i], "nm_file", str_fnm, 0);
				break;
			}

			// �폜�{�^������
		} else if (XmlId.toString() == "RGEN3285"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //�폜
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "syoriMode", "DEL", 0);
				break;
			}

			// �X�V�����`�F�b�N
		} else if (XmlId.toString() == "RGEN3410"){
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //�폜
				funXmlWrite(reqAry[i], "cd_seizokojo", frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value, 0);
				funXmlWrite(reqAry[i], "cd_line", frm.ddlLine.options[frm.ddlLine.selectedIndex].value, 0);
				break;
			}
		}

	}

	return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
//       �F�Ckara     �F1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
//       �F�Didx      �F�I����Ԃ�Index
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
        case 1:    //����Ͻ�
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
        case 2:    //�����}�X�^
            atbName = "nm_busho";
            atbCd = "cd_busho";
            break;
        case 3:    //�E��}�X�^
            atbName = "nm_shokuba";
            atbCd = "cd_shokuba";
            break;
        case 4:    //���C���}�X�^
            atbName = "nm_line";
            atbCd = "cd_line";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
/*            if (i == idx) {
            	objNewOption.setAttribute("selected", "selected");
            }
*/
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;
//    obj.selectedIndex = idx;

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
// �쐬���F2014/09/17
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �FXML�f�[�^
//       �F�Bkomoku   �F�������ږ�
//       �F�Ctext     �F�����l
//       �F�DaddIdx   �F0�F�󔒂Ȃ��A1�F�󔒂��� �iindex��+1�j
// �߂�l�F��v�����s�ԍ���Ԃ�
// �T�v  �F�R���{�{�b�N�X��Index ��ݒ肷��
//========================================================================================
function funSetIndex(obj, xmlData, komoku, text, addIdx) {

	if (text == "") {
		return 0;
	}

	//�����擾
	var reccnt = funGetLength(xmlData);

	for (var i = 0; i < reccnt; i++) {
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (funXmlRead(xmlData, komoku, i) == text) {
			//�����ޯ����Index�ݒ�i�󔒂���̎��A+1�j
			obj.selectedIndex = i + addIdx;
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
// �쐬���F2014/09/17
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
// �o�^�{�^���������`�F�b�N����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/20
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
