//========================================================================================
// ���ʕϐ�
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
//========================================================================================
//�_�E�����[�h�A�A�b�v���[�h�t�@�C������ێ�
// �J�e�S���I���Ŏ擾�����t�@�C������ۑ�
var strDL_FileNm = "";
// �A�b�v���[�h�t�@�C����
var strUL_FileNm = "";

//DB�ɓo�^�ł���ő�t�@�C�����̒���
var MAXLEN_FILENM = 250;

//========================================================================================
// �����\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConShiryoRefId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClear();

    //�J�e�S���I���̏����l�ݒ�
    funSetCatgorySel();

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
	var XmlId = "RGEN3400";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3400");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3400I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3400O);

//	������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		funClearRunMessage();
		return false;
	}

//	����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3400, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}

//	հ�ޏ��\��
	funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


//	�����ޯ���̍쐬
	funCreateComboBox(frm.ddlCategoryName, xmlResAry[2], 1);

//	��̫�Ă̺����ޯ���ݒ�l��ޔ�
	xmlFGEN3400.load(xmlResAry[2]);

	return true;
}

//========================================================================================
// �J�e�S���I���̏����l�ݒ�
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�I������Ă����J�e�S�����Z�b�g����i��ʍĕ\���ɑΉ��j
//========================================================================================
function funSetCatgorySel() {

	var selectCategory;                // �e�E�B���h�E�ɕۑ������J�e�S��
	var frm = document.frm00;          // ̫�тւ̎Q��


	// �e�E�B���h�E����I���J�e�S�����擾�i�ĕ\���p�j
	if(!window.opener || window.opener.closed){ // �e�E�B���h�E�̑��݂��`�F�b�N
		// ���݂��Ȃ�
	}
	else{
		// �e�E�B���h�E�̕ۑ��J�e�S���̑��݊m�F
		if (window.opener.frm00.sq220Category) {
			selectCategory = parseInt(window.opener.frm00.sq220Category.value);
			// �ݒ肳��Ă��鎞
			if (selectCategory > 0) {
				frm.ddlCategoryName.selectedIndex = selectCategory;
				funFileSearch();
			}

		}
	}

}

//========================================================================================
// �_�E�����[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�t�@�C���_�E�����[�h
//========================================================================================
function funDownLoad() {

	var frm = document.frm00;          // ̫�тւ̎Q��
    var shiryoNm = frm.shiryoName;     // �\���pInput
    var selCategory = frm.ddlCategoryName; 	// �J�e�S���I��

    //�J�e�S�����I������Ă��Ȃ��ꍇ�͏������~
    if(selCategory.options[selCategory.selectedIndex].value == ""){
		//���b�Z�[�W�\��
    	funErrorMsgBox(E000026);
		return;
    }

    // �t�@�C�����̃`�F�b�N
	if (shiryoNm.value == "") {
		//���b�Z�[�W�\���i�t�@�C�����w�肳��Ă��Ȃ��j
		funErrorMsgBox(E000027);
		return;
	}

	// �Q�ƃ{�^���Œu����������ꍇ  ------------ �s�v�I
	if (shiryoNm.value != strDL_FileNm) {
		//���b�Z�[�W�\���i�t�@�C�����w�肳��Ă��Ȃ��j
		funErrorMsgBox(E000029);
		return;
	}

    //** ̧���߽�̑ޔ� **/
	// �_�E�����[�h�t�@�C����
    frm.strFilePath.value = shiryoNm.value;
    // �_�E�����[�h�p�X�iconst��`���j
    frm.strServerConst.value = "UPLOAD_SANKOSHIRYO_FOLDER";
    // �I���J�e�S�����i�T�u�t�H���_�[���j
    frm.strSubFolder.value = selCategory.options[selCategory.selectedIndex].innerText;

    // �޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);
}


//========================================================================================
// �A�b�v���[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�Q�ƃt�@�C���̃A�b�v���[�h���s��
//========================================================================================
function funUpLoad() {

	var frm = document.frm00;          // ̫�тւ̎Q��
	var shiryoNm = frm.shiryoName      // �c�a�o�^����Ă���t�@�C����
    var inputName = frm.inputName;     // �\���pInput
    var sansyoInput = frm.filename;    // �Q��Input
    var delFname = frm.strFilePath;    // �T�[�o�[�t�@�C���폜�p�i�c�a�o�^����Ă���t�@�C�����j
    var selCategory = frm.ddlCategoryName; 	// �J�e�S���I��

	var value = 0;

    //�J�e�S�����I������Ă��Ȃ��ꍇ�͏������~
    if(selCategory.options[selCategory.selectedIndex].value == ""){
		//���b�Z�[�W�\��
    	funErrorMsgBox(E000026);
    	return;
    }

    // �A�b�v���[�h�t�@�C���̃`�F�b�N
	if (inputName.value == "") {
		//���b�Z�[�W�\���i�A�b�v���[�h�t�@�C�����w�肳��Ă��Ȃ��j
		funErrorMsgBox(E000027);
		return;
	}
    // �A�b�v���[�h�t�@�C���̃`�F�b�N
	if (sansyoInput.value == "") {
		//���b�Z�[�W�\���i�A�b�v���[�h�t�@�C�����w�肳��Ă��Ȃ��j
		funErrorMsgBox(E000027);
		return;
	}

	// �\���pinput���ύX����Ă���
	if (inputName.value != sansyoInput.value) {
		//���b�Z�[�W�\���i�A�b�v���[�h�t�@�C�����w�肳��Ă��Ȃ��j
		funErrorMsgBox(E000028);
		return;
	}

	// �Q�ƃt�@�C���t���p�X���t�@�C�������擾�i�g���q�̃`�F�b�N�s�v�j
	// �c�a�o�^�p�ɕۑ��i�A�b�v���[�h����t�@�C�����j
	strUL_FileNm = funGetFileNm(sansyoInput.value);

	// �t�@�C�����̒����`�F�b�N�i�c�a�����j
	if (strUL_FileNm.length > MAXLEN_FILENM) {
		funErrorMsgBox("�t�@�C�������������܂��B�i�Q�T�O�����܂Łj�F\\n" + strUL_FileNm);
		return false;
	}
	// �t�@�C�����̕s�������`�F�b�N
	if (!funChkFileNm(strUL_FileNm)) {
		funErrorMsgBox("�t�@�C�����ɕs���������܂܂�Ă��܂��B�F\\n" + strUL_FileNm);
		return false;
	}

	// �A�b�v���[�h�̊m�F
	if (strDL_FileNm == "") {
		if (funConfMsgBox(I000016) != ConBtnYes) {
			return;
		}

	// �w�肵���J�e�S���łc�a�Ɏ������ۑ�����Ă���ꍇ�A�㏑���m�F
	} else {
		// �ۑ����������̃t�@�C�������ύX����Ă���
		if (strDL_FileNm != strUL_FileNm) {
			// �t�@�C�������Ⴄ�F�㏑���m�F
			if (funConfMsgBox(I000014) != ConBtnYes) {
				return;
			}

		} else {
			// �t�@�C�����������F�X�V�m�F
			if (funConfMsgBox(I000003) != ConBtnYes) {
				return;
			}
		}
	}

    //** ̧���߽�̑ޔ� **//
    // �A�b�v���[�h�p�X�iconst��`���j
    frm.strServerConst.value = "UPLOAD_SANKOSHIRYO_FOLDER";
    // �I���J�e�S�����i�T�u�t�H���_�[���j
    frm.strSubFolder.value = selCategory.options[selCategory.selectedIndex].innerText;
	// ���łɓo�^����Ă���t�@�C�����i�I���J�e�S������ǉ��F�T�[�o�[���폜����j
	if (shiryoNm.value != "") {
		delFname.value = frm.strSubFolder.value + "\\" + shiryoNm.value;
	}

	// �t�@�C���A�b�v���[�h�T�[�u���b�g�̎��s�i�c�a�o�^����Ɏ��s�������j
	funFileUpload(frm);


	//**�c�a�o�^�ƃA�b�v���[�h�����͓����ɑ��� **//
	// �A�b�v���[�h����t�@�C�������Q�l�����c�a�ɓo�^
	// �Q�ƃ{�^���őI�������t�@�C������n��
	funShiryoTblIns(selCategory.options[selCategory.selectedIndex].value);

}

//========================================================================================
// �A�b�v���[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �Fstr �t�@�C���p�X
// �߂�l�F�t�@�C����
// �T�v  �F�t�@�C���p�X�������Ƃ��ēn���ĕ�������
//========================================================================================
function funGetFileNm(str){
	var FileName = "";				// �߂�l

	var strTmp = str.split("\\");	// �t�H���_�[�A�t�@�C�����𕪉�

	// �t�@�C�������擾
	FileName = strTmp[strTmp.length - 1];
	return FileName

/*	var pattern = /(\w+):(?:\\([^\\:\*?\"<>\|]+))*(?:\\(([^:\\\*?\"<>\|]+)\.+([^:\\\*?\"<>\|]+)$))/;

	FilePath.result = str.match(pattern);
	if(FilePath.result){
		FilePath.FullPath = FilePath.result[0];			//�t���p�X
		FilePath.Drive = FilePath.result[1];			//�h���C�u
		FilePath.ParentDir = FilePath.result[2];		//�e�̃f�B���N�g��
		FilePath.FileName = FilePath.result[3];			//�t�@�C����
		FilePath.FileNameShort = FilePath.result[4];	//�g���q���������t�@�C����
		FilePath.FileExt = FilePath.result[5].toLowerCase();	//�g���q�i�������ϊ��j


		// �t�@�C�������������p�X
		FilePath.Path = FilePath.FullPath.slice(0, FilePath.FileName.length * -1);

		return true;
	}
	return false;
*/
}

//========================================================================================
// �A�b�v���[�h�{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �Ffrm ̫�тւ̎Q��
// �T�v  �F�t�@�C���A�b�v���[�h�T�[�u���b�g�̎��s
//========================================================================================
function funFileUpload(frm){


	// �t�@�C���A�b�v���[�h�T�[�u���b�g�̎��s
	frm.action="/" + ConUrlPath + "/FileUpLoadExec";
	frm.submit();

}

//========================================================================================
// �Q�ƃ{�^����������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �Fsel  0 -- �J�e�S���I����
//              1 -- �Q�ƃ{�^��������
// �T�v  �F�t�@�C���I�������t�@�C���p�X��\���pInput�ɃZ�b�g����
//         �\���pInput ���N���A����Ă����ꍇ�ɑΉ����邽��
//========================================================================================
function funSetInput(sel) {

	var frm = document.frm00;          // ̫�тւ̎Q��
    var shiryoNm = frm.shiryoName;     // �_�E�����[�h�pInput
    var inputName = frm.inputName;     // �\���pInput
    var sansyoInput = frm.filename;    // �Q��Input

    if (sel == 0) {
    	// �J�e�S���I���iselect���ύX����Ă��Ȃ��ꍇ�ɂ��Ή��j
    	// ��U�A�l��߂� onChange�łc�a�l���ݒ�  --- �s�v�H
    	shiryoNm.value = strDL_FileNm;
    } else {
    	// �Q�ƃ{�^�������i�\���p�ɃZ�b�g�j
    	inputName.value = sansyoInput.value;
    }

    return true;

}

//========================================================================================
// �Q�ƃ{�^���L�[���������iENTER�L�[�Ή��j
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/11/20
// ����  �F keyCode�F�L�[�R�[�h
// �T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funEnterFile(keyCode) {

	var frm = document.frm00;          // ̫�тւ̎Q��
    var sansyoInput = frm.filename;    // �Q��Input

    // ENTER�L�[�������A�Q�ƃ{�^���N���b�N�̓��������s
    if (keyCode == 13) {
    	// ENTER�L�[�����ŃN���b�N�C�x���g�𔭐�������
    	sansyoInput.click();
    } else if (keyCode == 46) {
    	// DELETE�L�[�łȂ����N���A�����
    	return false;
    }
}


//========================================================================================
// �Q�ƃ{�^�������Ńt�@�C���I����
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�t�@�C���I�������t�@�C���p�X��\���pInput�ɃZ�b�g����
//========================================================================================
function funChangeFile() {

	funSetInput(1);

    return true;

}

//========================================================================================
// �N���A�{�^�����������F�폜
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�\���pInput�̃N���A���s���iinput type="file" �̒l�͕ύX�ł��Ȃ����߁j
//========================================================================================
//function funClearInput() {
//
//	var frm = document.frm00;          // ̫�тւ̎Q��
//    var inputName = frm.inputName;     // �\���pInput
//
//    // �\���p�ɃZ�b�g
//    inputName.value = "";
//
//    return true;
//
//}

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
        // ��ʏ����\���i�J�e�S���ꗗ�擾�j
        if (XmlId.toString() == "RGEN3400") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3400
                    break;
            }
        // �J�e�S���I��
        } else if (XmlId.toString() == "RGEN3210"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3210
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value, 0);
                    break;
            }
        // �A�b�v���[�h�{�^�������i�Q�l�����c�a�o�^�j
        } else if (XmlId.toString() == "RGEN3220"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3220
                    funXmlWrite(reqAry[i], "cd_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "nm_literal", frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].innerText, 0);
                    funXmlWrite(reqAry[i], "nm_file", strUL_FileNm, 0);
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
        case 1:    //����Ͻ�
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
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    // �����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �I���{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F�I������
//========================================================================================
function funEndClick(){

	//�I������
	funEnd();
}

//========================================================================================
// �I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// �T�v  �F�I������
//========================================================================================
function funEnd(){

	// �e�E�B���h�E����I���J�e�S�����擾�i�ĕ\���p�j
	if(!window.opener || window.opener.closed){ // �e�E�B���h�E�̑��݂��`�F�b�N
		// ���݂��Ȃ�
	} else {
		// �e�E�B���h�E�̕ۑ��J�e�S���̑��݊m�F
		if (window.opener.frm00.sq220Category) {
			// �e�E�B���h�E�ɕۑ����Ă���J�e�S�������폜
			window.opener.frm00.sq220Category.value = "";
		}
		// �e�E�B���h�E���t�H�[�J�X�ɂ���
		window.opener.focus();
	}

	// ��ʂ����
	window.close();

}

//========================================================================================
// ��ʂ̏���������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

	var frm = document.frm00;    //̫�тւ̎Q��

	//��ʂ̏�����
	frm.reset();
	frm.ddlCategoryName.selectedIndex = 0;

	//��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
	xmlFGEN3400O.load(xmlFGEN3400);

	//�����ޯ���̍Đݒ�
	funCreateComboBox(frm.ddlCategoryName, xmlFGEN3400O, 1);
	funDefaultIndex(frm.ddlCategoryName, 1);

 return true;

}


//========================================================================================
// �J�e�S���I������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �F�Ȃ�
// �T�v  �F�I�������J�e�S����莑���t�@�C���p�X��ݒ肷��
//========================================================================================
function funFileSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��

    var XmlId = "RGEN3210";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3210");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3210I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3210O);



	if(!window.opener || window.opener.closed){ // �e�E�B���h�E�̑��݂��`�F�b�N
		// ���݂��Ȃ�
	}
	else{
		// �e�E�B���h�E�̕ۑ��J�e�S���̑��݊m�F
		if (window.opener.frm00.sq220Category) {
			// �ĕ\���I���̂��߂ɐe�E�B���h�E�ɕۑ�
			window.opener.frm00.sq220Category.value = frm.ddlCategoryName.selectedIndex;
		}
	}

	// �\���p input ���N���A
	frm.shiryoName.value = "";
	// �I�������J�e�S���ŕۑ�����Ă���t�@�C�������N���A
	strDL_FileNm = "";

    // �J�e�S�����I������Ă��Ȃ��ꍇ�A�t�@�C���p�X���N���A���ď������~
    if(frm.ddlCategoryName.options[frm.ddlCategoryName.selectedIndex].value == ""){
    	return;
    }

    // ������ү���ޕ\��
    funShowRunMessage();

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // ���������Ɉ�v����J�e�S���f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3210, xmlReqAry, xmlResAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // �����擾
    reccnt = funGetLength(xmlResAry[2]);

    // �������������ꍇ�i�P�F�P�̃n�Y�I�j
    if(reccnt > 1){
   	 funInfoMsgBox("����������܂����B");
    }

    //�_�E�����[�h�t�@�C�����̐ݒ�
    var val = "";
    for(i=0; i<reccnt; i++){
    	if(i >= 1){
    		val = val + ",";
    	}
    	val = val + funXmlRead(xmlResAry[2], "nm_file", i);
    }

    // �_�E�����[�h�p input �ɐݒ�
    frm.shiryoName.value = val;
    // �Q�ƃ{�^�������Œu�������̂őޔ�
    strDL_FileNm = val;

    // ������ү���ޔ�\��
    funClearRunMessage();

    return true;

}


//========================================================================================
// �Q�l�����Ǘ��e�[�u���o�^
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
// ����  �Fliteral  �J�e�S���ԍ�
//         strUL_FileNm �A�b�v���[�h����Q�l�����t�@�C����
// �T�v  �F�Q�l�����Ǘ��e�[�u���ɃJ�e�S���ԍ��ƃt�@�C������o�^����
//========================================================================================
function funShiryoTblIns(literal) {
	   var frm = document.frm00;    //̫�тւ̎Q��

	    var XmlId = "RGEN3220";
	    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3220");
	    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3220I);
	    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3220O);


	    // �J�e�S�����I������Ă��Ȃ��ꍇ�A�Q�l�����t�@�C�������ݒ肳��Ă��Ȃ��������~
	    if(literal == "" || strUL_FileNm == ""){
	    	return;
	    }

	    // ������ү���ޕ\��
	    funShowRunMessage();

	    // ������XMĻ�قɐݒ�
	    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
	        funClearRunMessage();
	        return false;
	    }

	    // �Q�l�����Ǘ��e�[�u���o�^
	    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3220, xmlReqAry, xmlResAry, 1) == false) {
	        return false;
	    }

	    //������ү���ޔ�\��
	    funClearRunMessage();

	    //����ү���ނ̕\��
	    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);

	    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

	        //����
	        // �_�E�����[�h�p input �ɐݒ�  ---------  �s�v�I
	        frm.shiryoName.value = strDL_FileNm;

	    } else {

	        //error
	    	funErrorMsgBox(dspMsg);
	    }
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
