//========================================================================================
// ���ʕϐ�
// �쐬�ҁFt2nakamura
// �쐬���F2016/10/03
//========================================================================================
var loop_cnt = 0;
//DB�ɓo�^�ł���ő�t�@�C�����̒���
var MAXLEN_FILENM = 250;


//========================================================================================
// �����\������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/4
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00; // ̫�тւ̎Q��

    //��ʐݒ�
    funInitScreen(ConShizaiTehaiZumiListId);

    // ��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    document.getElementById("lblTitle").innerHTML = "���ގ�z�ψꗗ";

    // �I��������񊈐�
    document.getElementById("btnCompletion").disabled = true;
    document.getElementById("btnUpLoad").disabled = true;
    document.getElementById("btnOutput").disabled = true;

    // ��z�ς݂Ƀ`�F�b�N
    frm.chkTehaizumi.checked = true;

    // ��z�敪�`�F�b�N�{�b�N�X��񊈐�
    document.getElementById("chkTehaizumi").disabled = true;
    document.getElementById("chkMitehai").disabled = true;
    document.getElementById("chkMinyuryoku").disabled = true;

    document.getElementById("chkEdit").disabled = true;		// �ҏW�`�F�b�N�{�b�N�X

    // �A�b�v���[�h�t���O��0�ɖ߂�
    uploadFlg = 0;

    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �����P�Fmode �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3200","FGEN3300","FGEN3310", "FGEN3330","SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3200I, xmlFGEN3300I, xmlFGEN3310I,xmlFGEN3330I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3200O, xmlFGEN3300O, xmlFGEN3310O, xmlFGEN3330O,xmlSA290O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        //funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            mode) == false) {
        return false;
    }

    // հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    // �Ώێ���
    funCreateComboBox(frm.ddlShizai, xmlResAry[2], 2, 2);

    // �S����
    funCreateComboBox(frm.ddlTanto, xmlResAry[3], 3, 2);

    // ������
    funCreateComboBox(frm.ddlHattyusaki, xmlResAry[4], 1, 2);

	// �A�b�v���[�h���
    if (funXmlRead(xmlResAry[5], "flg_return", 0) == "true") {
    	//xmlFGEN3330.load(xmlResAry[5]);
    	if (funXmlRead(xmlResAry[5], "chkTehaizumi", 0) == "1") {
    		frm.chkTehaizumi.checked = true;

    	}
    	// ���ރR�[�h
    	frm.txtShizaiCd.value = funXmlRead(xmlResAry[5], "txtShizaiCd", 0);
    	// ���ޖ�
    	frm.txtShizaiNm.value = funXmlRead(xmlResAry[5], "txtShizaiNm", 0);
    	// �����ރR�[�h
    	frm.txtOldShizaiCd.value = funXmlRead(xmlResAry[5], "txtOldShizaiCd", 0);
    	// ���i�R�[�h
    	frm.txtSyohinCd.value = funXmlRead(xmlResAry[5], "txtSyohinCd", 0);
    	// ���i��
    	frm.txtSyohinNm.value = funXmlRead(xmlResAry[5], "txtSyohinNm", 0);
    	// �[����
    	frm.txtSeizoukojo.value = funXmlRead(xmlResAry[5], "txtSeizoukojo", 0);
    	// �Ώێ���
    	frm.ddlShizai.value = funXmlRead(xmlResAry[5], "ddlShizai", 0);

    	// ������
    	frm.ddlHattyusaki.value = funXmlRead(xmlResAry[5], "ddlHattyusaki", 0);
    	// ������
    	frm.ddlTanto.value = funXmlRead(xmlResAry[5], "ddlTanto", 0);
    	// ������from
    	frm.txtHattyubiFrom.value = funXmlRead(xmlResAry[5], "txtHattyubiFrom", 0);
    	// ������to
    	frm.txtHattyubiTo.value = funXmlRead(xmlResAry[5], "txtHattyubiTo", 0);
    	// �[����from
    	frm.txtNounyudayFrom.value = funXmlRead(xmlResAry[5], "txtNounyudayFrom", 0);
    	// �[����to
    	frm.txtNounyudayTo.value = funXmlRead(xmlResAry[5], "txtNounyudayTo", 0);
    	// �ő�x����from
    	frm.txtHanPaydayFrom.value = funXmlRead(xmlResAry[5], "txtHanPaydayFrom", 0);
    	// �ő�x����to
    	frm.txtHanPaydayTo.value = funXmlRead(xmlResAry[5], "txtHanPaydayTo", 0);
    	// ���x��
    	var chkMshiharai = funXmlRead(xmlResAry[5], "chkMshiharai", 0)
    	if (chkMshiharai == 1) {
    		frm.chkMshiharai.checked = true;
    	} else {
    		frm.chkMshiharai.checked = false;
    	}


		// �A�b�v���[�h��̍ă��[�h
        //funSearchConditionSet(xmlResAry[5]);
        funSearch();
	}
}

//========================================================================================
//�A�b�v���[�h��������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/24
//����  �F�Ȃ�
//�T�v  �F���ރe�[�u���X�V�A���ގ�z�e�[�u���o�^
//========================================================================================
function funToroku() {

	var frm = document.frm00;          //̫�тւ̎Q��
	var nm_tanto = frm.nm_tanto;           // �S����
	var chkKanryo = frm.chkKanryo;     // �����`�F�b�N�{�b�N�X
	var retBool = false;
	var retUpload = false;

	// �o�^�m�F
	if (funConfMsgBox(I000002) != ConBtnYes) {
		return;
	}

	// ���׍s�i�ǂݍ��ݕ��j
	var detail = document.getElementById("tblList");

	// ���׃f�[�^
	if (!detail.firstChild){
		// ���ޏ�񂪂Ȃ���
		retBool = funTorokuChk(-1);
	// ���R�[�h���P���̎�
	} else if (loop_cnt == 1) {
		// �`�F�b�N�{�b�N�X��n���A�o�^�i���ރe�[�u���X�V�A��z�e�[�u���o�^or�폜�j
		retBool = funTorokuChk(0);
	} else {
		//
		for(var i = 0; i < loop_cnt; i++){
			// �`�F�b�N�{�b�N�X��n���A�o�^�i���ރe�[�u���X�V�A��z�e�[�u���o�^or�폜�j
			retBool = funTorokuChk(i);
			if (!retBool) break;
		}
	}
	//�t�@�C���A�b�v���[�h���Ɏ��{
	retUpload = funfileUpload();

	// �������b�Z�[�W
 if (retBool || retUpload ) {
 	// �o�^���܂����B
 	funInfoMsgBox("�ŐV�̃f�[�^���������܂�");
     //@@@@@
 	// �Č������s
 	funSearch();
 }

	return true;

}

//========================================================================================
//�A�b�v���[�h����
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/20
//����  �F�Ȃ�
//�T�v  �F�Ă��A�b�v���[�h
//========================================================================================
function funfileUpload() {
 var frm = document.frm00;   //̫�тւ̎Q��
 var upFildNm = "";          //�A�b�v���[�h����t�B�[���h��
 var delFileNm = "";         //�폜����t�@�C����
 var lstSyurui = {};         //�o�^�����ނ̔z��i�d���`�F�b�N�p�j
 var strMsg = ""				//�m�F���b�Z�[�W�t������

 var subFolder = "";

// // �ۑ��t�@�C���̃T�u�t�H���_�[�擾�i�H��j
// var subFolder = funSelChk();
// // �������I������Ă��Ȃ��ꍇ
//	if (subFolder == "") {
//		funErrorMsgBox(funSelChk.Msg);
//		return false;
//	}
//
	// �I��������ރR�[�h��_�Ōq���ׁA�t�@�C�����ɐݒ�
	var subFlst = "";
//	// �\���s�������ꍇ�A�G���[
//	var displayln = false;

	// �ꗗ�f�[�^���Ȃ�
	if (loop_cnt == 0) {
 	funErrorMsgBox(E000030);
     return false;
	}

	// �\������Ă���ꗗ���`�F�b�N
	// �Q�ƃt�@�C���A�\���pinput���󔒂łȂ�����
 for(var i = 0; i < loop_cnt; i++){
     // �s�I�u�W�F�N�g
 	//var tr = document.getElementById("tr");

 	// �A�b�v���[�h����t�@�C����
 	var filename = document.getElementById("filename_" + i);

 	// �\���t�@�C����
 	var inputName = document.getElementById("inputName_" + i);

 	// �ۑ�����Ă���t�@�C�����i��\���j
 	var nm_file = document.getElementById("nm_file_" + i);

 	// �Ј��R�[�h
 	var cd_shain = document.getElementById("cd_shain_" + i);
 	// �N
 	var nen = document.getElementById("nen_" + i);
 	// �ǔ�
 	var no_oi = document.getElementById("no_oi_" + i);
 	// ����SEQ
 	var seq_shizai = document.getElementById("seq_shizai_" + i);
 	// �}��
 	var no_eda = document.getElementById("no_eda_" + i);
 	// ���i�ԍ�
 	var cd_shohin = document.getElementById("cd_shohin_" + i);

 	//var cd_shizai = document.getElementById("cd_shizai-" + i);

 	// �\���s�̂ݏ�������
 	//if (tr.style.display != "none") {

 	//if (loop_cnt >= 1) {
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
				funErrorMsgBox(E000064 + inputName.value);
// 			funErrorMsgBox("�t�@�C�����ɕs���������܂܂�Ă��܂��B�F\\n" + inputName.value);
 			return false;
			}
 		// �N���A�{�^������������Ă��Ȃ�����
 		if ((filename.value != "") && (inputName.value != "") && (inputName.style.color == "red")) {
 			// �A�b�v���[�h�����F�T�[�o�[�n���i":::"�ŋ�؂�j
 			// �t�B�[���h���i�A�b�v���[�h�`�F�b�N�p�j
 			upFildNm += "filename_" + i + ":::";

 			// �T�u�t�H���_�[�Ɏ�ރR�[�h��ǉ�
 			//subFlst += subFolder  + cd_shizai.value + ":::";
 			subFolder = cd_shain.value + "_" + nen.value + "_" + no_oi.value + "_" + seq_shizai.value + "_" + no_eda.value + "_" + cd_shohin.value;
 			subFlst += subFolder + ":::";
 		}
 		// �ύX�O�t�@�C�����폜
 		// �c�a�ɕۑ�����Ă��� ���� �ύX���ꂽ�Ԏ��t�@�C���̎��A
 		if ( (nm_file.value != "") && (inputName.style.color == "red")) {
 			// �T�u�t�H���_�[����t������B�i":::"�ŋ�؂�j
 			// nm_file_henshita �ɂ͕ۑ��f�[�^�̎�ރR�[�h���t���ρi"\\"�ŋ�؂��Ă���j
 			delFileNm += subFolder  + "\\" + nm_file.value + ":::";
 		}

 	//}
 }
 // �\���s���Ȃ�
	if (!displayln) {
 	funErrorMsgBox(E000030);
     return false;
	}
 if (upFildNm == "") {
     return false;
 }
//	// �r�������F�������̍X�V��������ύX���������珈�����~
//	if (!funKoshinChk()) {
//		return false;
//	}
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
 	frm.action="/" + ConUrlPath + "/FileUpLoadExec"; // ConUrlPath �� Shisaquick
 	frm.submit();
 }
 return true;
}


//========================================================================================
//�s�̓o�^�`�F�b�N
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/14
//����  �F�Ȃ�
//�T�v  �F�w��s�̍s�f�[�^��xml�ɕۑ�����
//========================================================================================
function funTorokuChk(row) {
	var frm = document.frm00;          //̫�тւ̎Q��

	// �w��s�̍s�f�[�^���Z�b�g����ׂ�xml ��ݒ�
	//xmlFGEN3450.load(xmlFGEN3450O);   // ��s�̎��A�ݒ肳��Ă��Ȃ�
	funAddRecNode(xmlFGEN3690, "FGEN3690");

	if (row < 0) {
		// �ꗗ���Ȃ�
		funXmlWrite(xmlFGEN3690, "cd_shain", "", 0);
		// ���Z���ރe�[�u���X�V�����i�G���[�j
		if (!funShizaiUpdate()) {
			return false;
		}

	} else {
		// �Ј��R�[�h
		var cd_shain = document.getElementById("cd_shain_" + row).value;
		// �N
		var nen = document.getElementById("nen_" + row).value
		// �ǔ�
		var no_oi = document.getElementById("no_oi_" + row).value;
		// �}��
		var no_eda = document.getElementById("no_eda_" + row).value;
		// ����SEQ
		var seq_shizai = document.getElementById("seq_shizai_" + row).value;
	 	// ���i�ԍ�
	 	var cd_shohin = document.getElementById("cd_shohin_" + row).value;

		// �Ώۍs�f�[�^��擪�s�ɃZ�b�g����
		// �Ј��R�[�h
		funXmlWrite(xmlFGEN3690, "cd_shain", cd_shain, 0);
		// �N
		funXmlWrite(xmlFGEN3690, "nen", nen, 0);
		// �ǔ�
		funXmlWrite(xmlFGEN3690, "no_oi", no_oi, 0);
		// �}��
		funXmlWrite(xmlFGEN3690, "no_eda", no_eda, 0);
		// ����SEQ
		funXmlWrite(xmlFGEN3690, "seq_shizai", seq_shizai, 0);
		// ���i�ԍ�
		funXmlWrite(xmlFGEN3690, "cd_shohin", cd_shohin, 0);
		// �[����
		funXmlWrite(xmlFGEN3690, "nounyu_day", document.getElementById("nounyu_day_" + row).value, 0);
		// �ő�
		funXmlWrite(xmlFGEN3690, "han_pay", document.getElementById("han_pay_" + row).value, 0);
		// �ő�x����
		funXmlWrite(xmlFGEN3690, "han_payday", document.getElementById("han_payday_" + row).value, 0);
		// �ăt�@�C����
		var inputName = document.getElementById("inputName_" + row).value == "?" ? document.getElementById("inputName_" + row).value = "" : document.getElementById("inputName_" + row).value;
		funXmlWrite(xmlFGEN3690, "inputName", inputName, 0);
		// �ăt�@�C���p�X
		// �쐬
		// �Ј��ԍ�
		var subFolder = cd_shain + "_" + nen + "_" + no_oi + "_" + seq_shizai + "_" + no_eda + "_" + cd_shohin;
		funXmlWrite(xmlFGEN3690, "filename", subFolder, 0);

		// �X�V����
		if (!funShizaiUpdate()) {
			 funClearRunMessage();
			return false;
		}

	}
	return true;

}

//========================================================================================
//���Z���ރe�[�u���X�V����
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/14
//����  �F�Ȃ�
//�T�v  �F���ރe�[�u���X�V
//========================================================================================
function funShizaiUpdate() {

	var XmlId = "RGEN3690";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3690");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3690I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3690O);


	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	// ������ү���ޕ\��
	funShowRunMessage();

	// ����ݏ��A���ʏ����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3690, xmlReqAry, xmlResAry, 1) == false) {
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
// XML�t�@�C���ɏ�������
// �쐬�ҁFH.Shima
// �쐬���F2009/04/01
// �����P�FXmlId  �FXMLID
// �����Q�FreqAry �F�@�\ID�ʑ��MXML(�z��)
// �����R�FMode   �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\��
        if (XmlId.toString() == "RGEN3200") {
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            	case 5:    //SA290
    				funXmlWrite(reqAry[i], "id_user", "", 0);
    				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdShizaiTehaiList, 0);
    				break;
            }
        }
        // ����
        else if (XmlId.toString() == "RGEN3330") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);


                // ��z�ς�(checkbox)
                var chkTehaizumi = frm.chkTehaizumi.checked == true ? 1 : 0;
                // ���x��
                var chkMishiharai = frm.chkMshiharai.checked == true ? 1 : 0;
                // ���ރR�[�h�{���ޖ��{�����ރR�[�h�{���i�R�[�h�{���i�� + �[���� + �Ώێ��� + ������ + ������ + ������ + �[���� + �ő�x����
                var put_code = chkTehaizumi + ":::"
                				+ frm.txtShizaiCd.value + ":::" + frm.txtShizaiNm.value + ":::" + frm.txtOldShizaiCd.value + ":::"
                				+ frm.txtSyohinCd.value + ":::" + frm.txtSyohinNm.value + ":::" + frm.txtSeizoukojo.value + ":::"
                				+ frm.ddlShizai.value + ":::" + frm.ddlHattyusaki.value + ":::" + frm.ddlTanto.value + ":::"
                				+ frm.txtHattyubiFrom.value + ":::" + frm.txtHattyubiTo.value + ":::" + frm.txtNounyudayFrom.value + ":::" + frm.txtNounyudayTo.value + ":::"
                				+ frm.txtHanPaydayFrom.value + ":::" + frm.txtHanPaydayTo.value + ":::" + chkMishiharai + ":::";
                // �A�b�v���[�h��̍ă��[�h�p
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);

                break;
            case 1:    // FGEN3330
                // �`�F�b�N�{�b�N�X�ݒ�
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML��������
                // ��z�敪
                // ��z�ς�
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // ����z
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // ������
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* �������� �P�s�� */
                // ���ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // ���ޖ�
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // �����ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* �������� �Q�s�� */
                // ���i�R�[�h
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // ���i��
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // �[����i�����H��j
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // �[����i�����H�ꖼ�j
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* �������� �R�s�� */
                // �Ώێ���
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* �������� �S�s�� */
                // ������From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // ������To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // �[����From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // �[����To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // �`�F�b�N�{�b�N�X�ݒ�
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // ���x��
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

                break;
            }
        }
        // �I������
        else if (XmlId.toString() == "RGEN3340"){

            switch (i) {
            case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0)
                 break;
            case 1:    //FGEN3340

                // �s���擾
                var max_row = frm.hidListRow.value;
                var row = 0;

                var cd_shain       = "";
                var nen            = "";
                var no_oi          = "";
                var seq_shizai     = "";
                var no_eda         = "";

                for(var j = 0; j < max_row; j++){

                    if(max_row <= 1){

                        if(frm.chkShizai.checked){
                            // XML��莎��R�[�h�擾
                            cd_shain       = document.getElementById("cd_shain_" + j).value;
                            nen            = document.getElementById("nen_" + j).value;
                            no_oi          = document.getElementById("no_oi_" + j).value;
                            seq_shizai     = document.getElementById("seq_shizai_" + j).value;
                            no_eda         = document.getElementById("no_eda_" + j).value;

                            funXmlWrite_Tbl(reqAry[i],"table" , "cd_shain", cd_shain, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "nen", nen, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_oi", no_oi, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "seq_shizai", seq_shizai, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_eda", no_eda, row);
                        }
                    } else {

                        if(frm.chkShizai[j].checked){

                            if (row != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN3340", "table");
                            }
                            // XML��莎��R�[�h�擾
                            cd_shain       = document.getElementById("cd_shain_" + j).value;
                            nen            = document.getElementById("nen_" + j).value;
                            no_oi          = document.getElementById("no_oi_" + j).value;
                            seq_shizai     = document.getElementById("seq_shizai_" + j).value;
                            no_eda         = document.getElementById("no_eda_" + j).value;

                            funXmlWrite_Tbl(reqAry[i],"table" , "cd_shain", cd_shain, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "nen", nen, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_oi", no_oi, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "seq_shizai", seq_shizai, row);
                            funXmlWrite_Tbl(reqAry[i],"table" , "no_eda", no_eda, row);

                            row++; // �C���N�������g
                        }
                    }
                }

                // �I���s�������ꍇ�G���[
                if(cd_shain == "") {
                    funErrorMsgBox(E000002);
                    return false;
                }
                break;
            }
        }  // Excel�o�͌���
        else if (XmlId.toString() == "RGEN3620") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3620
                // �`�F�b�N�{�b�N�X�ݒ�
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML��������
                // ��z�敪
                // ��z�ς�
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // ����z
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // ������
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* �������� �P�s�� */
                // ���ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // ���ޖ�
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // �����ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* �������� �Q�s�� */
                // ���i�i���i�j�R�[�h
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // ���i�i���i�j��
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // �[����i�����H��j
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // �[����i�����H��j��
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* �������� �R�s�� */
                // �Ώێ���
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* �������� �S�s�� */
                // ������From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // ������To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // �[����From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // �[����To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // �`�F�b�N�{�b�N�X�ݒ�
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // ���x��
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

                break;
            }
          // �A�b�v���[�h�{�^�������F���ގ�z�e�[�u���X�V
        } else if  (XmlId.toString() == "RGEN3690") {
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
				break;
			case 1:

				// �Ј��R�[�h
				funXmlWrite(reqAry[i], "cd_shain", funXmlRead(xmlFGEN3690, "cd_shain", 0), 0);
				// �N
				funXmlWrite(reqAry[i], "nen", funXmlRead(xmlFGEN3690, "nen", 0), 0);
				// �ǔ�
				funXmlWrite(reqAry[i], "no_oi", funXmlRead(xmlFGEN3690, "no_oi", 0), 0);
				// �}��
				funXmlWrite(reqAry[i], "no_eda", funXmlRead(xmlFGEN3690, "no_eda", 0), 0);
				// ����SEQ
				funXmlWrite(reqAry[i], "seq_shizai", funXmlRead(xmlFGEN3690, "seq_shizai", 0), 0);
				// ���i�ԍ�
				funXmlWrite(reqAry[i], "cd_shohin", funXmlRead(xmlFGEN3690, "cd_shohin", 0), 0);
				// �[����
				funXmlWrite(reqAry[i], "nounyu_day", funXmlRead(xmlFGEN3690, "nounyu_day", 0), 0);
				// �ő�
				funXmlWrite(reqAry[i], "han_pay", funXmlRead(xmlFGEN3690, "han_pay", 0), 0);
				// �ő�x����
				funXmlWrite(reqAry[i], "han_payday", funXmlRead(xmlFGEN3690, "han_payday", 0), 0);
				// �\���p�t�@�C����
				funXmlWrite(reqAry[i], "inputName", funXmlRead(xmlFGEN3690, "inputName", 0), 0);
				// �t�@�C���p�X
				funXmlWrite(reqAry[i], "filename", funXmlRead(xmlFGEN3690, "filename", 0), 0);

				break;
			}
        } else if (XmlId.toString() == "RGEN3700"){
			//�����{�^������
			switch (i) {
			case 0:    //USERINFO
				funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
				funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", mode, 0);
				break;
			case 1:    //FGEN3700
				break;
			}
		}
    }
    return true;

}

//========================================================================================
// ���X�g�̐���
// �쐬�ҁFH.Shima
// �쐬���F2014/09/12
// �����P�F�擾XML
// �����Q�FHTML
// �����R�F�C���f�b�N�X
// �����S�F�ő�s��
// �T�v  �F���X�g���𐶐�����B
//========================================================================================
function DataSet(xmlResAry, html , i , cnt){

    if(i < cnt){
        var tableNm = "table";

        //���X�|���X�f�[�^�擾-------------------------------------------------------------------------------
        var row_no           = funXmlRead_3(xmlResAry[2], tableNm, "row_no", 0, i);							// �sNo
       	var nm_tanto      = funXmlRead_3(xmlResAry[2], tableNm, "nm_tanto", 0, i);						// �S����
        var naiyo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "naiyo", 0, i));							// ���e
        var cd_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shohin", 0, i));						// ���i�i���i�j�R�[�h
        var nm_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shohin", 0, i));						// ���i�i���i�j��
        var nisugata         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nisugata", 0, i));			// �׎p�^�����i�����j
        var target_shizai = funXmlRead_3(xmlResAry[2], tableNm, "nm_taisyo_shizai", 0, i);							// �Ώێ��ށi���e�������j
        var nm_hattyusaki    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_hattyusaki", 0, i));		// ������
        var cd_hattyusaki    = funXmlRead_3(xmlResAry[2], tableNm, "cd_hattyusaki", 0, i);					// ������R�[�h
        var nm_nounyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyusaki", 0, i));					// �[����i�����H��j
        var cd_shizai_old    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai_old", 0, i));		// �����ރR�[�h
        var cd_shizai        = funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai", 0, i);						// ���ރR�[�h
        var nm_shizai     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shizai", 0, i));						// ���ޖ�
        var sekkei1       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, i));			// �݌v�@
        var sekkei2       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, i));			// �݌v�A
        var sekkei3       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, i));			// �݌v�B
        var zaishitsu     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, i));			// �ގ�
        var biko_tehai    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, i));		// ���l
        var printcolor    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, i));		// ����F
        var no_color      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, i));			// �F�ԍ�
        var henkounaiyoushosai   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, i));		// �ύX���e�ڍ�
        var nouki         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, i));							// �[��
        var suryo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, i));							// ����
        var nounyu_day    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyu_day", 0, i));					// �[����
        var han_pay       = funXmlRead_3(xmlResAry[2], tableNm, "han_pay", 0, i);						// �ő�
        var dt_han_payday   = funXmlRead_3(xmlResAry[2], tableNm, "dt_han_payday", 0, i);				// �ő�x����
        // null�̏ꍇ'1900/01/01'�ɍX�V�����̂�'1900/01/01'�̏ꍇ""�ɍX�V
        if (dt_han_payday == '1900/01/01') {
        	dt_han_payday = "";
        }
        var han_upload    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "file_path_aoyaki", 0, i));				// �ăA�b�v���[�h
        var nm_file       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i));				// �ۑ��t�@�C����

        var cd_shain      = funXmlRead_3(xmlResAry[2], tableNm, "cd_shain", 0, i);						// �Ј��R�[�h
        var nen           = funXmlRead_3(xmlResAry[2], tableNm, "nen", 0, i);							// �N
        var no_oi         = funXmlRead_3(xmlResAry[2], tableNm, "no_oi", 0, i);							// �ǔ�
        var seq_shizai    = funXmlRead_3(xmlResAry[2], tableNm, "seq_shizai", 0, i);					// ����SEQ
        var no_eda        = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);						// �}��
        var flg_status    = funXmlRead_3(xmlResAry[2], tableNm, "flg_status", 0, i);					// ��z�X�e�[�^�X
        var toroku_disp   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_koshin_disp", 0, i));	// �o�^��

        var objColor;
        objColor = henshuOkColor;

        // �����͐F
        var notInputColor     = "#99ffff";
        // ����z�F
        var notArrangeColor   = "#ffbbff";

        //HTML�o�̓I�u�W�F�N�g�ݒ�---------------------------------------------------------------------------
        //TR�s�J�n
        var output_html = "";

        //�sNo
        if("3" === flg_status){
            // ��z�ς�
            output_html += "<tr class=\"disprow\" bgcolor=\"" + deactiveSelectedColor + "\">";
        } else if("2" === flg_status) {
            // ����z
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notArrangeColor + "\">";
        } else {
            // ������
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notInputColor + "\">";
        }

        //�sNo
        output_html += "    <td class=\"column\" width=\"30\"  align=\"right\">";
        output_html += "        <input type=\"text\" id=\"no_row_" + i + "\" name=\"no_row_" + i + "\" style=\"background-color:transparent;width:26px;border-width:0px;text-align:left\" readOnly value=\"" + row_no + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_shain_" + i + "\" name=\"cd_shain_" + i + "\" readOnly value=\"" + cd_shain + "\" >";
        output_html += "        <input type=\"hidden\" id=\"nen_" + i + "\" name=\"nen_" + i + "\" readOnly value=\"" + nen + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_oi_" + i + "\" name=\"no_oi_" + i + "\" readOnly value=\"" + no_oi + "\" >";
        output_html += "        <input type=\"hidden\" id=\"seq_shizai_" + i + "\" name=\"seq_shizai_" + i + "\" readOnly value=\"" + seq_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_eda_" + i + "\" name=\"no_eda_" + i + "\" readOnly value=\"" + no_eda + "\" >";
        output_html += "    </td>";

        //�I���{�^��
        output_html += "    <td class=\"column\" width=\"30\" align=\"center\">";
        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
        output_html += "    </td>";

        //�S����
        output_html += "    <td class=\"column\" width=\"100\" align=\"left\" id=\"nm_tanto_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_tanto_" + i + "\" name=\"nm_tanto_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nm_tanto + "\" >";
        output_html += "    </td>";

        //���e
        output_html += "    <td class=\"column\" width=\"200\" align=\"left\" id=\"naiyo_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"naiyo_" + i + "\" name=\"naiyo_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + naiyo + "\" >";
        output_html += "    </td>";

        //���i�i���i�j
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shohin_" + i + "\" name=\"cd_shohin_" + i + "\" style=\"background-color:transparent;width:47;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shohin,6) + "\" >";
        output_html += "    </td>";

        //���i�i���i�j��
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shohin_" + i + "\" name=\"nm_shohin_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_shohin + "\" >";
        output_html += "    </td>";

        //�׎p
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nisugata_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nisugata_" + i + "\" name=\"nisugata_" + i + "\" style=\"background-color:transparent;width:145px;border-width:0px;text-align:left\" readOnly value=\"" + nisugata + "\" >";
        output_html += "    </td>";

        //�Ώێ���
        output_html += "    <td class=\"column\" width=\"120\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_target_shizai_" + i + "\" name=\"nm_target_shizai_" + i + "\" style=\"background-color:transparent;width:115px;border-width:0px;text-align:left\" readOnly value=\"" + target_shizai + "\" >";
        output_html += "    </td>";

        //������
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nm_hattyusaki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_hattyusaki_" + i + "\" name=\"nm_hattyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_hattyusaki + "\" >";
        output_html += "    </td>";

        //�[����
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" id=\"nm_nounyusaki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nm_nounyusaki_" + i + "\" name=\"nm_nounyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_nounyusaki + "\" >";
        output_html += "    </td>";

        //�����ރR�[�h
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" id=\"cd_shizai_old-" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_old_" + i + "\" name=\"cd_shizai_old_" + i + "\" style=\"background-color:transparent;width:48;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai_old,6) + "\" >";
        output_html += "    </td>";

        //���ރR�[�h
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_" + i + "\" name=\"cd_shizai_" + i + "\" style=\"background-color:transparent;width:47px;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai, 6) + "\" >";
        output_html += "    </td>";

        //���ޖ�
        output_html += "    <td class=\"column\" width=\"150\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shizai_" + i + "\" name=\"nm_shizai_" + i + "\" style=\"background-color:transparent;width:144;border-width:0px;text-align:left\" readOnly value=\"" + nm_shizai + "\" >";
        output_html += "    </td>";

        //�݌v�@
        output_html += "    <td class=\"column\" width=\"200\" align=\"left\" id=\"sekkei1_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei1_" + i + "\" name=\"sekkei1_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei1 + "\" >";
        output_html += "    </td>";

        //�݌v�A
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"sekkei2_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei2_" + i + "\" name=\"sekkei2_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei2 + "\" >";
        output_html += "    </td>";

        //�݌v�B
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"sekkei3_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"sekkei3_" + i + "\" name=\"sekkei3_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei3 + "\" >";
        output_html += "    </td>";

        //�ގ�
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"zaishitsu_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"zaishitsu_" + i + "\" name=\"zaishitsu_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + zaishitsu + "\" >";
        output_html += "    </td>";

        //���l
        output_html += "    <td class=\"column\" width=\"294\" align=\"left\" id=\"biko_tehai_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"biko_tehai_" + i + "\" name=\"biko_tehai_" + i + "\" style=\"background-color:transparent;width:294;border-width:0px;text-align:left\" readOnly value=\"" + biko_tehai + "\" >";
        output_html += "    </td>";

        //����F
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"printcolor_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"printcolor_" + i + "\" name=\"printcolor_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + printcolor + "\" >";
        output_html += "    </td>";

        //�F�ԍ�
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" id=\"no_color_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"no_color_" + i + "\" name=\"no_color_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + no_color + "\" >";
        output_html += "    </td>";

        //�ύX���e�ڍ�
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" id=\"henkounaiyoushosai_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"henkounaiyoushosai_" + i + "\" name=\"henkounaiyoushosai_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + henkounaiyoushosai + "\" >";
        output_html += "    </td>";

        //�[��
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"nouki_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nouki_" + i + "\" name=\"nouki_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + nouki + "\" >";
        output_html += "    </td>";

        //����
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"suryo_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"suryo_" + i + "\" name=\"suryo_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + suryo + "\" >";
        output_html += "    </td>";

        //�o�^��
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" id=\"toroku_disp_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"toroku_disp_" + i + "\" name=\"toroku_disp_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + toroku_disp + "\" >";
        output_html += "    </td>";

        //�[����
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" id=\"nounyu_day_" + i + "\" >";
        output_html += "        <input type=\"text\" id=\"nounyu_day_" + i + "\" name=\"nounyu_day_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nounyu_day + "\" >";
        output_html += "    </td>";

        //�ő�
        output_html += "    <td class=\"column ninput\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"han_pay_" + i + "\" name=\"han_pay_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" value=\"" + han_pay + "\" >";
        output_html += "    </td>";

        //�ő�x����
        output_html += "    <td class=\"column \" width=\"98\" align=\"left\" >";
        output_html += "        <input class=\"disb_text\" type=\"text\" id=\"han_payday_" + i + "\" name=\"han_payday_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" value=\"" + dt_han_payday + "\" >";
        output_html += "    </td>";

        //�ŉ��i�āj�A�b�v���[�h
        output_html += "    <td class=\"column\" width=\"468\" align=\"left\" >";
        //�ۑ�����Ă���t�@�C����
        output_html += "        <input type=\"hidden\" id=\"nm_file_" + i + "\" name=\"nm_file_" + i + "\" value=\"" + nm_file + "\" tabindex=\"-1\">";
        output_html += "        <div style=\"position: relative;\">";
        // �Q�ƃ{�^��
        output_html += "            <input type=\"file\" id=\"filename_" + i + "\" name=\"filename_" + i + "\" class=\"normalbutton\" size=\"464\" style=\"width:464px;\"";
        output_html += "onChange=\"funChangeFile(" + i + ")\" onclick=\"funSetInput(" + i + ")\" onkeydown=\"funEnterFile(" + i + ", event.keyCode);\" >";
        // �\���p�t�@�C����
        output_html += "            <span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
        output_html += "                <input type=\"text\" id=\"inputName_" + i + "\" name=\"inputName_" + i + "\" value=\""+ funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i) + "\" size=\"76\" readonly tabindex=\"-1\" >";
        output_html += "            </sapn>";
        output_html += "        </div>";
        output_html += "    </td>";


        //TR�s��
        output_html += "</tr>";
        html += output_html;

        //�ċA�����i���f�[�^��HTML�����j
        setTimeout(function(){ DataSet( xmlResAry , html , ( i + 1 ) , cnt ); }, 0);
    } else {
        //�ꗗ����HTML�ݒ�
        var obj = document.getElementById("divMeisai");
        html = html + "</table>";
        obj.innerHTML = html;

        // �I���s�̏�����
        funSetCurrentRow("");

        //�\���I����Ɍ����n�A�N�V�����𑀍�\
        document.getElementById("btnSearch").disabled = false;
        document.getElementById("btnClear").disabled = false;
        document.getElementById("btnEnd").disabled = false;
        document.getElementById("btnUpLoad").disabled = false;
        document.getElementById("btnOutput").disabled = false;

        var limit_over = funXmlRead_3(xmlResAry[2], "table", "limit_over", 0, (i - 1));
        if("0" != limit_over){
        	funErrorMsgBox(E000051 + limit_over + E000052);
        }

        xmlResAry = null;
        html = null;

        // ���b�Z�[�W�̔�\��
        funClearRunMessage();

        //�����I��
        return true;
    }
}

//========================================================================================
// ��������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F������茟�����s���A���ʂ��ꗗ�ɕ\��
//========================================================================================
function funSearch(){
    var frm = document.frm00; // ̫�тւ̎Q��

    // ��z�敪�I���`�F�b�N
    var checkFlg = frm.chkTehaizumi.checked;

    if(!checkFlg){
        funErrorMsgBox(E000038);
        return false;
    }
    funClearList();
    funShowRunMessage();

    setTimeout(function(){ funDataSearch() }, 0);
}

//========================================================================================
// �f�[�^��������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F������茟�����s���A���ʂ��ꗗ�ɕ\��
//========================================================================================
function funDataSearch(){

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3330";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3330");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3330I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3330O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }
    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3330, xmlReqAry, xmlResAry,
            1) == false) {
        //������ү���ޔ�\��
        funClearRunMessage();

        // �ҏW���[�h�߂�
        funChangeEditResearch();
        // �ҏW�`�F�b�N�{�b�N�X�`�F�b�N�O��
        frm.chkEdit.checked = false;
        // �ҏW�`�F�b�N�{�b�N�X�񊈐�
        frm.chkEdit.disabled = true;

        frm.btnUpLoad.disabled = true;
        frm.btnOutput.disabled = true;
        return false;
    }


    // �ҏW�`�F�b�N�L�Ō������ꂽ�ꍇ
    if (frm.chkEdit.checked) {
    	funChangeEditResearch();
    	// �ҏW�`�F�b�N�{�b�N�X�`�F�b�N�O��
        frm.chkEdit.checked = false;
    }

    //�\�����Ɍ����n�A�N�V�����𑀍�s��
    frm.btnUpLoad.disabled = true;
    frm.btnOutput.disabled = true;
    frm.btnSearch.disabled = true;
    frm.btnClear.disabled = true;
    frm.btnCompletion.disabled = true;
    frm.btnEnd.disabled = true;

    // �������ʍs���̐ݒ�
    loop_cnt = funXmlRead(xmlResAry[2], "loop_cnt", 0);
    frm.hidListRow.value = loop_cnt;


    var output_html = "";
    output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

    setTimeout(function(){ DataSet(xmlResAry, output_html ,0 ,loop_cnt); }, 0);

    frm.chkEdit.disabled = false;

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/19
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
//       �F�Ckara     �F�󔒍s���e���[�h
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
        case 1:    // ������Ͻ�
            atbName = "nm_hattyusaki";
            atbCd   = "cd_hattyusaki";
            break;

        case 2:    // ���e�����}�X�^
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;

        case 3:    // �S����
            atbName = "nm_user";
            atbCd   = "id_user";
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
// �ꗗ�I������
// �쐬�ҁFH.Shima
// �쐬���F2014/9/12
// ����  �F�C���f�b�N�X
// �T�v  �F�I���s���n�C���C�g
//========================================================================================
function clickItiran(row){
    funSetCurrentRow(row);
}

//========================================================================================
// �N���A����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// ����  �F�Ȃ�
// �T�v  �F��������������������B
//========================================================================================
function funClear(){

    funClearJoken();
    funClearList();
}

//========================================================================================
// ���������N���A�{�^����������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F��������������������
//========================================================================================
function funClearJoken(){
    var frm = document.frm00;    //̫�тւ̎Q��

    frm.reset();
    // ��z�ς݂Ƀ`�F�b�N
    frm.chkTehaizumi.checked = true;
    // Excel�{�^���񊈐�
    frm.btnOutput.disabled = true;

}

//========================================================================================
// ���X�g�N���A����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList(){

    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
}

//========================================================================================
// �d���r��
// �쐬�ҁFH.Shima
// �쐬���F2014/10/02
// ����  �F�z��
// �T�v  �F��ӂ̃��X�g��ԋp
//========================================================================================
function convertUniqueList(arrayObject) {

    for(var i = 0; i < (arrayObject.length - 1); i++){
        for(var j = (arrayObject.length - 1);i < j; j--){
            if(arrayObject[i] === arrayObject[j]){
                // �폜���Ĕz����l�߂�
                arrayObject.splice(j, 1);
            }
        }
    }

    return arrayObject;
}

//========================================================================================
// ���l���ёւ��d���r��
// �쐬�ҁFH.Shima
// �쐬���F2014/10/02
// ����  �F�z��
// �T�v  �F�\�[�g���ꂽ��ӂ̃��X�g��ԋp
//========================================================================================
function convertUniqueSortList(arrayObject) {
    // �\�[�g
    arrayObject = arrayObject.sort(function(a, b) { return a - b;});

    // �d���폜
    var i = 0;
    var j = 1;

    while(j <= arrayObject.length){
        if(arrayObject[i] === arrayObject[j]){
            // �폜���Ĕz����l�߂�
            arrayObject.splice(j, 1);
        } else {
            i++;
            j++;
        }
    }
    return arrayObject;
}

//========================================================================================
// �\��������̍쐬
// �쐬�ҁFH.Shima
// �쐬���F2014/10/02
// ����  �F�z��
// �T�v  �F��ʕ\���p��������쐬
//========================================================================================
function createDispString(arrayObject){
    var str = "";
    for(var i = 0;i < arrayObject.length; i++){
        var dispNo = (i + 1);
        if(i != 0){
            // ��؂�
            str += " ";
        }
        str += "(" + dispNo + ")" + arrayObject[i];
    }
    return str;
}

//========================================================================================
// �I������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// ����  �F�Ȃ�
// �T�v  �F���j���[��ʂɖ߂�B
//========================================================================================
function funEnd(){

    var XmlId = "RGEN3700";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������Ɉ�v����f�U�C���X�y�[�X�f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3700, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
    //��ʂ����
    close(self);

    return true;
}

//========================================================================================
// �󔒎��̐ݒ�
// �쐬�ҁFH.Shima
// �쐬���F2014/09/19
//========================================================================================
function funSetNbsp(val) {

    if( val == "" || val == "NULL" ){
        val = "&nbsp;";
    }

    return val;
}

//========================================================================================
//�[�����ߏ���
//�쐬�ҁFH.Shima
//�쐬���F2014/12/12
//========================================================================================
function fillsZero(obj, keta){
    var ret = obj;

    while(ret.length < keta){
        ret = "0" + ret;
    }
    return ret;
}

//========================================================================================
//�t�@�C���ύX
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/09/14
//����  �Frow   �F�s�ԍ�
//�T�v  �F�\���p�t�@�C�����ɃZ�b�g����
//========================================================================================
function funChangeFile(row) {

	funSetInput(row);

	return true;
}

//========================================================================================
//�Q�ƃ{�^����������
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/09/14
//����  �Frow   �F�s�ԍ�
//�T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funSetInput(row) {

	var inputName = document.getElementById("inputName_" + row);		// �\���p�t�@�C�����i�t�@�C�����̂݁j
	var fileName = document.getElementById("filename_" + row);			// �Q�ƃt�@�C�����i�t���p�X�j

	// �t�@�C���_�C�A���O
	if (fileName.value != "") {
		// �Q�ƃt�@�C���t���p�X���t�@�C�������擾���\���p�ɃZ�b�g
		//�i�g���q�̃`�F�b�N�s�v�j
		inputName.value = funGetFileNm(fileName.value);
		// �A�b�v���[�h�t�@�C�����͕����F��ύX
		inputName.style.color = "red";
	}
return true;

}

//========================================================================================
//�t�@�C���p�X�ҏW����
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/09/14
//����  �Fstr   �t�@�C���p�X
//�߂�l�F�t�@�C����
//�T�v  �F�t�@�C���p�X�������Ƃ��ēn���ĕ�������
//========================================================================================
function funGetFileNm(str){

	var fileName = "";			// �t�@�C����
	var tmp = str.split("\\");	// �t�@�C���p�X�i�t�H���_�[�A�t�@�C�����j�𕪉�

	// �t�@�C�������擾
	fileName = tmp[tmp.length - 1];

	return fileName;
}

//========================================================================================
//�Q�ƃ{�^���L�[���������iENTER�L�[�Ή��j
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/09/14
//����  �Frow    �F�s�ԍ�
//   keyCode�F�L�[�R�[�h
//�T�v  �F�A�b�v���[�h�t�@�C�����w��
//========================================================================================
function funEnterFile(row, keyCode) {

	// ENTER�L�[�������A�Q�ƃ{�^���N���b�N�̓��������s
	if (keyCode == 13) {
		var fileName = document.getElementById("inputName_" + row);			// �Q�ƃ{�^��
		// ENTER�L�[�����ŃN���b�N�C�x���g�𔭐�������
		fileName.click();
	} else {
		return false;
	}
}

//========================================================================================
// EXCEL�o�͏���
// �쐬�ҁFt2nakamura
// �쐬���F2016/10/06
// �T�v  �FExcel�o�͂��s��
//========================================================================================
function funExcelOut() {
	// �t�H�[���Q��
	var frm = document.frm00;

	var XmlId = "RGEN3620";
	var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3620");
	var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3620I);
	var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3620O);

	// Excel�o�͊m�Fү���ނ̕\��
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    // XML�̏�����
    setTimeout("xmlFGEN3620I.src = '../../model/FGEN3620I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // �o�͎��s
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3620, xmlReqAry, xmlResAry,
            1) == false) {
        return false;
    }

    // �t�@�C���p�X�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlFGEN3620O, "URLValue", 0);

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);

	return true;
}

//========================================================================================
// �ҏW���[�h�ؑ�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/28
// ����  �F
// �T�v  �F�ҏW���[�h�̐؂�ւ����s��
//========================================================================================
function funChangeEdit() {

	var edit = document.getElementById("chkEdit");		// �ҏW�`�F�b�N�{�b�N�X

	if(edit.checked == true){

		// �w�b�_(colgroup)
		document.getElementById("colHeadTanto").style.display = 'none';	// �S����
		document.getElementById("colHeadNaiyo").style.display = 'none';	// ���e
		document.getElementById("colHeadNisugata").style.display = 'none';	// �׎p
		document.getElementById("colHeadNmHattyusaki").style.display = 'none';	// ������
		document.getElementById("colHeadNmNounyusaki_").style.display = 'none';	// �[����
		document.getElementById("colHeadCdShizaiOld").style.display = 'none';	// �����ރR�[�h
		document.getElementById("colHeadSekkei1").style.display = 'none';	// �݌v�@
		document.getElementById("colHeadSekkei2").style.display = 'none';	// �݌v�A
		document.getElementById("colHeadSekkei3").style.display = 'none';	// �݌v�B
		document.getElementById("colHeadZaishitsu").style.display = 'none';	// �ގ�
		document.getElementById("colHeadBikoTehai").style.display = 'none';	// ���l
		document.getElementById("colHeadPrintcolor").style.display = 'none';	// ����F
		document.getElementById("colHeadNo_color").style.display = 'none';	// �F�ԍ�
		document.getElementById("colHeadHenkounaiyoushosai").style.display = 'none';	// �ύX���e�ڍ�
		document.getElementById("colHeadNouki").style.display = 'none';	// �[��
		document.getElementById("colHeadSuryo").style.display = 'none';	// ����
		document.getElementById("colHeadTorokuDisp").style.display = 'none';	// �o�^��

		for (var i = 0; i < loop_cnt; i++){
			// �ꗗ
			document.getElementById("nm_tanto_" + i).style.display = 'none';		// �S����
			document.getElementById("naiyo_" + i).style.display = 'none';			// ���e
			document.getElementById("nisugata_" + i).style.display = 'none';	// �׎p
			document.getElementById("nm_hattyusaki_" + i).style.display = 'none';	// ������
			document.getElementById("nm_nounyusaki_" + i).style.display = 'none';	// �[����
			document.getElementById("cd_shizai_old-" + i).style.display = 'none';	// �����ރR�[�h
			document.getElementById("sekkei1_" + i).style.display = 'none';	// �݌v�@
			document.getElementById("sekkei2_" + i).style.display = 'none';	// �݌v�A
			document.getElementById("sekkei3_" + i).style.display = 'none';	// �݌v�B
			document.getElementById("zaishitsu_" + i).style.display = 'none';	// �ގ�
			document.getElementById("biko_tehai_" + i).style.display = 'none';	// ���l
			document.getElementById("printcolor_" + i).style.display = 'none';	// ����F
			document.getElementById("no_color_" + i).style.display = 'none';	// �F�ԍ�
			document.getElementById("henkounaiyoushosai_" + i).style.display = 'none';	// �ύX���e�ڍ�
			document.getElementById("nouki_" + i).style.display = 'none';	// �[��
			document.getElementById("suryo_" + i).style.display = 'none';	// ����
			document.getElementById("toroku_disp_" + i).style.display = 'none';	// �o�^��
		}

		document.getElementById("dataTable").style.width="1420px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="1360px";

	} else if (edit.checked == false){

		// �w�b�_
		document.getElementById("colHeadTanto").style.display = '';	// �S����
		document.getElementById("colHeadNaiyo").style.display = '';	// ���e
		document.getElementById("colHeadNisugata").style.display = '';	// �׎p
		document.getElementById("colHeadNmHattyusaki").style.display = '';	// ������
		document.getElementById("colHeadNmNounyusaki_").style.display = '';	// �[����
		document.getElementById("colHeadCdShizaiOld").style.display = '';	// �����ރR�[�h
		document.getElementById("colHeadSekkei1").style.display = '';	// �݌v�@
		document.getElementById("colHeadSekkei2").style.display = '';	// �݌v�A
		document.getElementById("colHeadSekkei3").style.display = '';	// �݌v�B
		document.getElementById("colHeadZaishitsu").style.display = '';	// �ގ�
		document.getElementById("colHeadBikoTehai").style.display = '';	// ���l
		document.getElementById("colHeadPrintcolor").style.display = '';	// ����F
		document.getElementById("colHeadNo_color").style.display = '';	// �F�ԍ�
		document.getElementById("colHeadHenkounaiyoushosai").style.display = '';	// �ύX���e�ڍ�
		document.getElementById("colHeadNouki").style.display = '';	// �[��
		document.getElementById("colHeadSuryo").style.display = '';	// ����
		document.getElementById("colHeadTorokuDisp").style.display = '';	// �o�^��

		for (var i = 0; i < loop_cnt; i++){
			// �ꗗ
			document.getElementById("nm_tanto_" + i).style.display = '';		// �S����
			document.getElementById("naiyo_" + i).style.display = '';			// ���e
			document.getElementById("nisugata_" + i).style.display = '';	// �׎p
			document.getElementById("nm_hattyusaki_" + i).style.display = '';	// ������
			document.getElementById("nm_nounyusaki_" + i).style.display = '';	// �[����
			document.getElementById("cd_shizai_old-" + i).style.display = '';	// �����ރR�[�h
			document.getElementById("sekkei1_" + i).style.display = '';	// �݌v�@
			document.getElementById("sekkei2_" + i).style.display = '';	// �݌v�A
			document.getElementById("sekkei3_" + i).style.display = '';	// �݌v�B
			document.getElementById("zaishitsu_" + i).style.display = '';	// �ގ�
			document.getElementById("biko_tehai_" + i).style.display = '';	// ���l
			document.getElementById("printcolor_" + i).style.display = '';	// ����F
			document.getElementById("no_color_" + i).style.display = '';	// �F�ԍ�
			document.getElementById("henkounaiyoushosai_" + i).style.display = '';	// �ύX���e�ڍ�
			document.getElementById("nouki_" + i).style.display = '';	// �[��
			document.getElementById("suryo_" + i).style.display = '';	// ����
			document.getElementById("toroku_disp_" + i).style.display = '';	// �o�^��
			document.getElementById("nounyu_day_" + i).style.display = '';	// �[����
		}
		document.getElementById("dataTable").style.width="4365px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="4313px";
	}
	return true;
}

//========================================================================================
// �ҏW���[�h�߂�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/28
// ����  �F
// �T�v  �F�ҏW���[�h�Ō������ꂽ�ꍇ�A�߂�
//========================================================================================
function funChangeEditResearch() {

	var edit = document.getElementById("chkEdit");		// �ҏW�`�F�b�N�{�b�N�X

	if(edit.checked == true){
		// �w�b�_
		document.getElementById("colHeadTanto").style.display = '';	// �S����
		document.getElementById("colHeadNaiyo").style.display = '';	// ���e
		document.getElementById("colHeadNisugata").style.display = '';	// �׎p
		document.getElementById("colHeadNmHattyusaki").style.display = '';	// ������
		document.getElementById("colHeadNmNounyusaki_").style.display = '';	// �[����
		document.getElementById("colHeadCdShizaiOld").style.display = '';	// �����ރR�[�h
		document.getElementById("colHeadSekkei1").style.display = '';	// �݌v�@
		document.getElementById("colHeadSekkei2").style.display = '';	// �݌v�A
		document.getElementById("colHeadSekkei3").style.display = '';	// �݌v�B
		document.getElementById("colHeadZaishitsu").style.display = '';	// �ގ�
		document.getElementById("colHeadBikoTehai").style.display = '';	// ���l
		document.getElementById("colHeadPrintcolor").style.display = '';	// ����F
		document.getElementById("colHeadNo_color").style.display = '';	// �F�ԍ�
		document.getElementById("colHeadHenkounaiyoushosai").style.display = '';	// �ύX���e�ڍ�
		document.getElementById("colHeadNouki").style.display = '';	// �[��
		document.getElementById("colHeadSuryo").style.display = '';	// ����
		document.getElementById("colHeadTorokuDisp").style.display = '';	// �o�^��

		document.getElementById("dataTable").style.width="4365px";
		document.getElementById("sclList").style.width="100%";

		document.getElementById("tblList").style.width="4315px";
	}
	return true;
}

//========================================================================================
//�o�^�{�^���������`�F�b�N����
//�쐬�ҁFMay Thu
//�쐬���F2016/09/30
//����  �F�t�@�C����
//�߂�l�FBoolean    true�F�o�^��   false�F�s����������
//�T�v  �F�t�@�C�����̒��ɕs���������܂܂�Ă��邩�`�F�b�N����
//========================================================================================
function funChkFileNm(str){

	// �s���������܂܂�Ă���ꍇ�Afalse��Ԃ�
	var ret = str.match(/[;/?:@&=+$,%# \s]/);// ���p�󔒍Z
	if (ret) {
		return false;
	}
	return true;

}
//========================================================================================
//���������̍Đݒ菈��
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/01
//����  �F�Ȃ�
//�߂�l�F�S������I�����Ă��Ȃ����F""
//      �I�����F�I���������Ȃ����R�[�h������
//�T�v  �F�����������`�F�b�N���A�����R�[�h���Ȃ����������Ԃ�
//========================================================================================
function funSearchConditionSet(resultAray) {

	var frm = document.frm00;		//̫�тւ̎Q��
	// ��z�σ`�F�b�N
	var chkTehaizumi = funXmlRead(resultAray, "chkTehaizumi" , 0);

	if (chkTehaizumi == 1) {
		frm.chkTehaizumi.checked = true;
	} else {
		frm.chkTehaizumi.checked = false;
	}

	// ������
	frm.inputSeihinCd.value = funXmlRead(resultAray, "txtShizaiCd", 0);
	// ���ރR�[�h
	frm.txtShizaiCd.value = funXmlRead(resultAray, "txtShizaiCd", 0);
	// ���ޖ�
	frm.txtShizaiNm.value = funXmlRead(resultAray, "txtShizaiNm", 0);
	// �����ރR�[�h
	frm.txtOldShizaiCd.value = funXmlRead(resultAray, "txtOldShizaiCd", 0);
	// ���i�R�[�h
	frm.txtSyohinCd.value = funXmlRead(resultAray, "txtSyohinCd", 0);
	// ���i��
	frm.txtSyohinNm.value = funXmlRead(resultAray, "txtSyohinNm", 0);
	// �[����
	frm.txtSeizoukojo.value = funXmlRead(resultAray, "txtSeizoukojo", 0);
	// �Ώێ���
	frm.ddlShizai.value = funXmlRead(resultAray, "ddlShizai", 0);

	// ������
	frm.ddlHattyusaki.value = funXmlRead(resultAray, "ddlHattyusaki", 0);
	// ������
	frm.ddlTanto.value = funXmlRead(resultAray, "ddlTanto", 0);
	// ������from
	frm.txtHattyubiFrom.value = funXmlRead(resultAray, "txtHattyubiFrom", 0);
	// ������to
	frm.txtHattyubiTo.value = funXmlRead(resultAray, "txtHattyubiTo", 0);
	// �[����from
	frm.txtNounyudayFrom.value = funXmlRead(resultAray, "txtNounyudayFrom", 0);
	// �[����to
	frm.txtNounyudayTo.value = funXmlRead(resultAray, "txtNounyudayTo", 0);
	// �ő�x����from
	frm.txtHanPaydayFrom.value = funXmlRead(resultAray, "txtHanPaydayFrom", 0);
	// �ő�x����to
	frm.txtHanPaydayTo.value = funXmlRead(resultAray, "txtHanPaydayTo", 0);
	// ���x��
	var chkMshiharai = funXmlRead(resultAray, "chkMshiharai", 0)
	if (chkMshiharai == 1) {
		frm.chkMshiharai.checked = true;
	} else {
		frm.chkMshiharai.checked = false;
	}


	return true;
}

//========================================================================================
//�[����i�����H��j���͎�
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/10/05
//����  �F�Ȃ�
//�T�v  �F�����H��R�[�h�ݒ�
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //̫�тւ̎Q��

	// �[����i�����H��j�R�[�h��ݒ�
//	frm.seizoKojoCd.value = funGetXmldata(xmlSA290O, "nm_busho", frm.txtSeizoukojo.value, "cd_busho");
	// Excel�{�^���񊈐�
	funChangeSearch();

	return;
}

//========================================================================================
//���������ύX
//�쐬�ҁFBRC Koizumi
//�쐬���F2016/10/13
//����  �F�Ȃ�
//�T�v  �FExcel�{�^���񊈐�
//========================================================================================
function funChangeSearch() {

	// �{�^���񊈐�
	document.getElementById("btnOutput").disabled = true;

	return;
}


//========================================================================================
// ���������ύX
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/13
// ����  �F�Ȃ�
// �T�v  �FExcel�{�^���񊈐�
//========================================================================================
function funTextChange(value){
	// ���������ύX���A÷�Ă��ύX���ꂽ�ꍇ�̂݃{�^����񊈐��ɂ���
	if (textValue == value) {
		return;
	}

	funChangeSearch();
	return;
}