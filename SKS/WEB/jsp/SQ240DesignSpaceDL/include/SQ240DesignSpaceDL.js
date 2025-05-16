//�f�[�^�ێ�
var cd_kaisha = 0;		// ��ЃR�[�h

//========================================================================================
// �����\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConDesignSpaceDLId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�ꗗ�̸ر
    funClearList();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
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
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��

	var XmlId = "RGEN3250";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290","FGEN3250");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I,xmlFGEN3250I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O,xmlFGEN3250O);


    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3250, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

	// հ�ޏ��\��
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");
	// հ�ނ̉�ЃR�[�h�擾
	cd_kaisha = funXmlRead(xmlResAry[1], "cd_kaisha", 0);

	// �����H��i���O�C�����[�U�̉�Ђ̕����ꗗ�F�󔒂���j
	funCreateComboBox(frm.ddlSeizoKojo, xmlResAry[2], 2, 2);

	// ��ށi�󔒂���j
	funCreateComboBox(frm.ddlSyurui, xmlResAry[3], 1, 2);

	// ���[�_���E�B���h�E�F���ގ�z���͂���̑J�ڂ̏ꍇ
    if (!!window.dialogArguments) {
    	var win = window.dialogArguments[0];
    	var argKojo = window.dialogArguments[1];	// �����H��
    	var argShokuba = window.dialogArguments[2];	// �E��
    	var argLine = window.dialogArguments[3];	// �������C��

    	// �������I������Ă��鎞�AselectedIndex��ݒ肷��
    	if (argKojo != "") {
    		// �����H��̑I��
    		funSetIndex(frm.ddlSeizoKojo, xmlSA290O, "cd_busho", argKojo);
    		// �E��R���{�{�b�N�X�̍쐬
    		funChangeKojo();

    		if (argShokuba != "") {
    			// �E��̑I��
    			funSetIndex(frm.ddlShokuba, xmlFGEN3230O, "cd_shokuba", argShokuba);
    			// ���C���R���{�{�b�N�X�̍쐬
    			funChangeShokuba();

        		if (argLine != "") {
        			// ���C���̑I��
        			funSetIndex(frm.ddlLine, xmlFGEN3240O, "cd_line", argLine);
        		}
    		}

    		// �����p���������Ō���
    		funSearch()
    	}
    }

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

	// �f�U�C�����ꗗ�̏�����
//	funClearList();

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


	// �f�U�C�����ꗗ�̏�����
//	funClearList();

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
// �������C���R���{�{�b�N�X
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�ꗗ����������
//========================================================================================
function funChangeLine() {

	// �f�U�C�����ꗗ�̏�����
//	funClearList();
}

//========================================================================================
// ��ރR���{�{�b�N�X
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�ꗗ����������
//========================================================================================
function funChangeSyurui() {

	// �f�U�C�����ꗗ�̏�����
//	funClearList();
}


//========================================================================================
// ���������̃`�F�b�N����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �߂�l�F���������������I�����Ă��Ȃ����Ffalse
//         �ǂꂩ�̏�����I�����Ftrue
// �T�v  �F�����������`�F�b�N
//========================================================================================
function funSelChk() {

	var frm = document.frm00;   //̫�тւ̎Q��

	// �����H��
	if (frm.ddlSeizoKojo.selectedIndex > 0) {
		return true;
	}

	// �E��
	if (frm.ddlShokuba.selectedIndex > 0) {
		return true;
	}

	// �������C��
	if (frm.ddlLine.selectedIndex > 0) {
		return true;
	}
	// ���
	if (frm.ddlSyurui.selectedIndex> 0) {
		return true;
	}

	return false;
}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

	//�ꗗ�̸ر
	xmlFGEN3260O.src = "";
	// ��\��
	tblList.style.display = "none";

}


//========================================================================================
// �����{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�f�U�C���X�y�[�X�f�[�^�̌������s��
//========================================================================================
function funSearch() {

	var frm = document.frm00;    //̫�тւ̎Q��

    // �������I������Ă��邩�H
	if (funSelChk() == "") {
		funErrorMsgBox(E000031);
		return false;
	}

    // �f�[�^�擾
    funDataSearch();

}

//========================================================================================
// ��������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
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
		return false;
	}

	//������ү���ޕ\��
	funShowRunMessage();

	//���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3260, xmlReqAry, xmlResAry, 1) == false) {
		funClearRunMessage();
		return false;
	}

	// �f�U�C���X�y�[�X�f�[�^�����̃`�F�b�N
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//xmlFGEN3260O ��薾�ו\��
		tblList.style.display = "block";

	} else {
		//��\���F�Ώۃf�[�^�Ȃ�
		tblList.style.display = "none";
		//�Ώۃt�@�C��������܂���B
		funErrorMsgBox(E000030);
	}


	//������ү���ޔ�\��
	funClearRunMessage();
	return true;

}

//========================================================================================
// �_�E�����[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �F�Ȃ�
// �T�v  �F�I���t�@�C���_�E�����[�h
//========================================================================================
function funDownLoad() {

	var frm = document.frm00;          // ̫�тւ̎Q��
	var chkbox = frm.chk;              // �`�F�b�N�{�b�N�X�I�u�W�F�N�g

	var subFolder = "";					// �T�u�t�H���_�[���i�����R�[�h��"_"�Ōq���j
    var fileNm = "";					// �_�E�����[�h����t�@�C�����i":::"�ŋ�؂�j

    // �ꗗ�\���\������Ă��邩
    if(tblList.style.display == "none") {
		//���b�Z�[�W�\��
    	funErrorMsgBox(E000030);
		return;
    }

    // �`�F�b�N����Ă���t�@�C�������擾
	if (!chkbox.length) {
		// 1���̎� undefined �ɂȂ�AIndex �͕t���Ȃ�
		if(chkbox.checked) {
			if (funGetRowdata(0)) {
				// �T�u�t�H���_�[���F�����R�[�h�Ɏ�ރR�[�h��ǉ�
				subFolder = funGetRowdata.subFolder;
				// �_�E�����[�h�t�@�C����
				fileNm = funGetRowdata.fileNm;
			}
		}

	} else {
		for(var i = 0; i < chkbox.length; i++){
			if(chkbox[i].checked) {
				if (funGetRowdata(i)) {
					// �T�u�t�H���_�[���F��ЃR�[�h + �����R�[�h�i�����H��_�E��_�������C��_�j�Ɏ�ރR�[�h��ǉ�
					subFolder += funGetRowdata.subFolder + ":::";
					// �_�E�����[�h�t�@�C����
					fileNm += funGetRowdata.fileNm + ":::";
				}
			}
		}
	}

	// �t�@�C�����I������Ă��Ȃ��ꍇ�͏������~
	if(fileNm == ""){
		//���b�Z�[�W�\��
		funErrorMsgBox("�ꗗ�\����P���ȏ�I�����Ă��������B");
		return;
	}

	//** ̧���߽�̑ޔ� **/
	// �_�E�����[�h�p�X�iconst��`���j
	frm.strServerConst.value = "UPLOAD_DESIGNSPACE_FOLDER";
	// �T�u�t�H���_�[��
	frm.strSubFolder.value = subFolder;
	// �_�E�����[�h�t�@�C�����i":::"�ŋ�؂�j
	frm.strFilePath.value = fileNm;

	// �޳�۰�މ�ʂ̋N��
	funFileDownloadUrlConnect(ConConectGet, frm);

}

//========================================================================================
// �_�E�����[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// ����  �Frow       �F�s�ԍ�
// �߂�l�FsubFolder �F�T�u�t�H���_�[��
//         fileNm    �F�_�E�����[�h�t�@�C����
// �T�v  �F�I���s�̃f�[�^���擾���A�T�u�t�H���_�[���A�t�@�C������Ԃ�
//========================================================================================
function funGetRowdata(row) {

	// ���������R�[�h�i�����H��_�E��_�������C��_�j
	var kensaku_code = funXmlRead(xmlFGEN3260O, "cd_seizokojo", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_shokuba", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_line", row) + "_";

	// �T�u�t�H���_�[�F��ЃR�[�h��擪�ɒǉ�
//	funGetRowdata.subFolder = kensaku_code + funGetXmldata(xmlFGEN3250O, "nm_2nd_literal", funXmlRead(xmlFGEN3260O, "nm_syurui", row), "cd_2nd_literal") ;
	funGetRowdata.subFolder = cd_kaisha + "_" + kensaku_code + funXmlRead(xmlFGEN3260O, "cd_literal", row) + "_" + funXmlRead(xmlFGEN3260O, "cd_2nd_literal", row);

	// �_�E�����[�h�t�@�C����
	funGetRowdata.fileNm = funXmlRead(xmlFGEN3260O, "nm_file", row);

	return true;
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
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
				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdDesignSpaceDL, 0);
				break;
			case 2:    //FGEN3250�i��ނ��擾�j
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
			var cd_seizokojo = "";
			var cd_shokuba = "";
			var cd_line = "";
			var cd_syurui = "";

			// �I������Ă��������ݒ�
			if (frm.ddlSeizoKojo.selectedIndex > 0) {
				cd_seizokojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;
			}
			if (frm.ddlShokuba.selectedIndex > 0) {
				cd_shokuba = frm.ddlShokuba.options[frm.ddlShokuba.selectedIndex].value;
			}
			if (frm.ddlLine.selectedIndex > 0) {
				cd_line = frm.ddlLine.options[frm.ddlLine.selectedIndex].value;
			}
			if (frm.ddlSyurui.selectedIndex > 0) {
				// �J�e�S���� "_"�Ō���
				cd_syurui = frm.ddlSyurui.options[frm.ddlSyurui.selectedIndex].value;
			}

			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:    //FGEN3260
				funXmlWrite(reqAry[i], "cd_seizokojo", cd_seizokojo, 0);
				funXmlWrite(reqAry[i], "cd_shokuba", cd_shokuba, 0);
				funXmlWrite(reqAry[i], "cd_line", cd_line, 0);
				funXmlWrite(reqAry[i], "cd_syurui", cd_syurui, 0);
				break;
			}
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
//       �F�Didx      �F�I����Ԃ�Index
//�T�v  �F�R���{�{�b�N�X���쐬����
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
		}
	}

	//�����ޯ������̫�ĕ\��
	obj.selectedIndex = 0;

	return true;
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

	for (var i = 0; i < reccnt; i++) {
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (funXmlRead(xmlData, komoku, i) == text) {
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
// �I���{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// �T�v  �F�I������
//========================================================================================
function funEndClick(){

	//�I������
	funEnd();
}

//========================================================================================
// �I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/24
// �T�v  �F�I������
//========================================================================================
function funEnd(){
    //��ʂ����
    close(self);

//	var wUrl;
//	// �e�E�B���h�E�̑��݂��`�F�b�N
//	if(window.opener && !window.opener.closed == true){
//		// ���[�_���ŊJ������
//	    //��ʂ����
//	    close(self);
//	} else {
//		// ���j���[����̑J��
//		wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//		// ���j���[�ɖ߂�
//		funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//	}
	return true;
}
