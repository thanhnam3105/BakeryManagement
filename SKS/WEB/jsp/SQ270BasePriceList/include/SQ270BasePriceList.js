//========================================================================================
// �����\������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/01
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

	var frm = document.frm00; // ̫�тւ̎Q��

    //��ʐݒ� // Const.js�Őݒ�
    funInitScreen(ConBasePriceListId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClear();

 // �����\�����A�L���ł݂̂Ō���
    //�ް��擾�i�m�F�E���F�`�F�b�N�j
    funDataSearch();

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;
}

//========================================================================================
// �Ő��̓��͐��䏈��
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/01
// ����  �F�Ȃ�
// �T�v  �F�Ő��̓��͐�����s��
//========================================================================================
function funUseHansuNo(obj) {

    var frm = document.frm00;    //̫�тւ̎Q��

    if (obj.checked) {
        //�Ő����͉\
        funItemDisabled(frm.txtHansu, false);
    } else {
        //�Ő����͕s��
        funItemDisabled(frm.txtHansu, true);
    }
}

//========================================================================================
// �����{�^����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/01
// ����  �F�Ȃ�
// �T�v  �F�x�[�X�P���̌������s��
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //�߰�ނ̐ݒ�
    funSetCurrentPage(1);

    //�ް��擾
    // �S���`�F�b�N�Ή�
    if (frm.chkAll.checked) {
        //�S���ް��擾
        funDataAllSearch();
    } else if (frm.chkMishiyo.checked) {
    	// ���g�p�`�F�b�N�Ή�
    	// ���g�p���́A���[�J�[���A��ޖ��̂ݏ����Ƃ��ėL���Ƃ���
    	funDataMishiyoSearch();
    } else {
        //�ް��擾�i�m�F�E���F�`�F�b�N�j
        funDataSearch();
    }

}

//========================================================================================
// �S����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F�x�[�X�P���̌������s���i�S���擾�F�m�F�E���F�E�ł̂ݏ�Ԃ��`�F�b�N���Ȃ��j
//========================================================================================
function funDataAllSearch() {

  var frm = document.frm00;    //̫�тւ̎Q��
  var XmlId = "RGEN3510";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3510");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3510I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3510O);

  //������ү���ޕ\��
  funShowRunMessage();

  //�I���s�̏�����
  funSetCurrentRow("");

  //�ꗗ�̸ر
  funClearList();

  //������XMĻ�قɐݒ�
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      //�ꗗ�̸ر
      funClearList();
      //������ү���ޔ�\��
      funClearRunMessage();
      return false;
  }

  //���������Ɉ�v����x�[�X�P���ް����擾
  if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3510, xmlReqAry, xmlResAry, 1) == false) {
      //�ꗗ�̸ر
      funClearList();
      return false;
  }

  //�ް�����������
  if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
      //�\��
      tblList.style.display = "block";

      // �x�[�X�P���ꗗ�̍쐬
      funCreateBasePriceList(xmlResAry[2]);

      //������ү���ޔ�\��
      funClearRunMessage();

  } else {
      //���ה�\��
      tblList.style.display = "none";

      //������ү���ޔ�\��
      funClearRunMessage();
  }

  return true;

}

//========================================================================================
// ���g�p�S����������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/27
// ����  �F�Ȃ�
// �T�v  �F�x�[�X�P���̌������s��
//         �i���g�p�S���擾�F�Ő��E�m�F�E���F�E�S���E�L���ł̂ݏ�Ԃ��`�F�b�N���Ȃ��j
//========================================================================================
function funDataMishiyoSearch() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3720";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3720");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3720I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3720O);

	//������ү���ޕ\��
	funShowRunMessage();

	//�I���s�̏�����
	funSetCurrentRow("");

	//�ꗗ�̸ر
	funClearList();

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		//�ꗗ�̸ر
		funClearList();
		//������ү���ޔ�\��
		funClearRunMessage();
		return false;
	}

	//���������Ɉ�v����x�[�X�P���ް����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3720, xmlReqAry, xmlResAry, 1) == false) {
		//�ꗗ�̸ر
		funClearList();
		return false;
	}

	//�ް�����������
	if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
		//�\��
		tblList.style.display = "block";

		// �x�[�X�P���ꗗ�̍쐬
		funCreateBasePriceList(xmlResAry[2]);

		//������ү���ޔ�\��
		funClearRunMessage();

	} else {
		//���ה�\��
		tblList.style.display = "none";

		//������ү���ޔ�\��
		funClearRunMessage();
	}

	return true;

}

//========================================================================================
// ��������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F�x�[�X�P���̌������s��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3500";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3500");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3500I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3500O);

    //������ү���ޕ\��
    funShowRunMessage();

    //�I���s�̏�����
    funSetCurrentRow("");

    //�ꗗ�̸ر
    funClearList();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v����x�[�X�P���f�[�^���擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3500, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        return false;
    }

    //�ް�����������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";

        // �x�[�X�P���ꗗ�̍쐬
        funCreateBasePriceList(xmlResAry[2]);

        //������ү���ޔ�\��
        funClearRunMessage();

    } else {
        //���ה�\��
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    return true;

}

//========================================================================================
// ��ʏ�������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();
    funItemDisabled(frm.txtHansu, false);
    frm.ddlMakerName.selectedIndex = 0;
    frm.ddlHouzai.selectedIndex = 0;

    //�ꗗ�̸ر
    funClearList();

    //��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
    xmlFGEN3000O.load(xmlFGEN3000);		// ���[�J�[��
    xmlFGEN3010O.load(xmlFGEN3010);		// ��ޖ�

    //�����ޯ���̍Đݒ�
    funCreateComboBox(frm.ddlMakerName, xmlFGEN3000O, 1);
    funDefaultIndex(frm.ddlMakerName, 1);

    // ��̕�޺����ޯ����ݒ�
    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
    funDefaultIndex(frm.ddlHouzai, 1);

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlFGEN3500O.src = "";
    tblList.style.display = "none";
    var detail = document.getElementById("detail");
    while(detail.firstChild){
        detail.removeChild(detail.firstChild);
    }
}

//========================================================================================
// ��ʑJ�ڏ���
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// �T�v  �F�����ޒ��B�����j���[��ʂɑJ�ڂ���
//========================================================================================
function funNext() {
    //��ʂ����
    close(self);
    return true;

//	// �����ޒ��B�����j���[
//    var wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
//
//    //�J��
//    funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//    return true;
}

//========================================================================================
// �x�[�X�P���o�^��ʂ֑J��
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �Fmode���[�h  �F  3:�R�s�[ 2:�o�^ 4:���F
// �T�v  �F�Q�ƃ��[�U�̉{�������ɂď����𔻒肷��
//========================================================================================
function funOpenBasePriceAdd(mode){
    var frm = document.frm00;    // �t�H�[���ւ̎Q��

    // �I������Ă��Ȃ��ꍇ
    if(funGetCurrentRow().toString() == ""){
        funErrorMsgBox(E000002);	// 	�Ώۍs���I������Ă��܂���
        return false;
    }
    // �I������Ă���ꍇ
    else{
        // �������[�h��ۑ�����
        frm.mode.value = mode;
        // �x�[�X�P���o�^�E���F��ʋN�����ʒm�ɐ���
        if(funBasePriceAddTuti(1)){

        	// ���F�{�^�������Ȃ�Ίm�F�ς݂ł��邩���`�F�b�N����
            if(mode == 4){
                if(!funCheckKakuninData(1)){
                    return;
                }
            }
            // �x�[�X�P���o�^�E���F��ʂ�\������
            var win = window.open("../SQ280BasePriceAdd/SQ280BasePriceAdd.jsp","shisaquick_genka","menubar=no,resizable=yes");
            // �ĕ\���ׂ̈Ƀt�H�[�J�X�ɂ���
            win.focus();
        }
    }
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3000";

    // ���[�J�[���̂ݎ擾
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3000, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


    // ��ʌ�������
    var reccnt = funGetLength(xmlResAry[1]);
    for (i = 0; i < reccnt; i++) {
        var GamenId = funXmlRead(xmlResAry[1], "id_gamen", i);

        // ���ID�ŕ���
        switch (GamenId.toString()) {
            case ConGmnIdBasePriceAdd.toString():
                // �x�[�X�P���o�^�E���F��ʂ̌����L��̏ꍇ�̂ݎg�p�\
                frm.btnCopy.disabled = false;
                frm.btnNew.disabled = false;
                frm.btnShonin.disabled = false;
                break;
        }
    }

    // �����ޯ���̍쐬
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);

    // ��̫�Ă̺����ޯ���ݒ�l��ޔ�
    xmlFGEN3000.load(xmlResAry[2]);

    return true;
}

//========================================================================================
// �x�[�X�P���ꗗ�쐬����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// �T�v  �F�x�[�X�P���ꗗ���쐬����
//========================================================================================
function funCreateBasePriceList(xmlData) {

    var html;
    var reccnt;

    //�����擾
    reccnt = funGetLength(xmlData);

    var detail = document.getElementById("detail");
    var tr;

    for(var i = 0; i < reccnt; i++){

        // �s�ݒ�
        tr = document.createElement("tr");
        tr.setAttribute("class", "disprow");

        // ��
        var td1 = document.createElement("td");
        td1.setAttribute("class", "column");

        html = funXmlRead(xmlData, "no_row", i);
        html += "<input type=\"hidden\" id=\"cd_maker_" + i + "\" value=\"" + funXmlRead(xmlData, "cd_maker", i) + "\">";
        html += "<input type=\"hidden\" id=\"cd_houzai_" + i + "\" value=\"" + funXmlRead(xmlData, "cd_houzai", i) + "\">";
        html += "<input type=\"hidden\" id=\"no_hansu_" + i + "\" value=\"" + funXmlRead(xmlData, "no_hansu", i) + "\">";
        td1.innerHTML = html;
        td1.style.textAlign = "right";
        td1.style.width = 30;
        tr.appendChild(td1);

        // ���[�J�[��
        var td2 = document.createElement("td");
        td2.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_maker", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td2.innerHTML = html;
        td2.style.textAlign="left";
        td2.style.width = 160;
        tr.appendChild(td2);

        // �Ő�
        var td3 = document.createElement("td");
        td3.setAttribute("class", "column");
        html = funXmlRead(xmlData, "no_hansu", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td3.innerHTML = html;
        td3.style.textAlign="right";
        td3.style.width = 31;
        tr.appendChild(td3);

        // �ł̕�ޖ�
        var td4 = document.createElement("td");
        td4.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_han_houzai", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td4.innerHTML = html;
        td4.style.textAlign="left";
        td4.style.width = 396;
        tr.appendChild(td4);

        // �o�^��
        var td5 = document.createElement("td");
        td5.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_kakunin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td5.innerHTML = html;
        td5.style.textAlign="left";
        td5.style.width = 120;
        tr.appendChild(td5);

        // ���F��
        var td6 = document.createElement("td");
        td6.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_shonin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td6.innerHTML = html;
        td6.style.textAlign="left";
        td6.style.width = 120;
        tr.appendChild(td6);

        // �L���J�n��
        var td7 = document.createElement("td");
        td7.setAttribute("class", "column");
        html = funXmlRead(xmlData, "dt_yuko", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td7.innerHTML = html;
        td7.style.textAlign="left";
        td7.style.width = 89;
        tr.appendChild(td7);

        // �X�V��
        var td8 = document.createElement("td");
        td8.setAttribute("class", "column");
        html = funXmlRead(xmlData, "dt_koshin", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td8.innerHTML = html;
        td8.style.textAlign="left";
        td8.style.width = 80;
        tr.appendChild(td8);

        // �x�[�X��ޖ�
        var td9 = document.createElement("td");
        td9.setAttribute("class", "column");
        html = funXmlRead(xmlData, "nm_base_houzai", i);
        if(html == ""){
            html = "&nbsp;"
        }
        td9.innerHTML = html;
        td9.style.textAlign="left";
        td9.style.width = 395;
        tr.appendChild(td9);

        detail.appendChild(tr);
    }
}

//========================================================================================
// �����֘A����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //�R�s�[�A�o�^�A���F�{�^���̐���
    frm.btnNew.disabled = false;
    frm.btnCopy.disabled = false;
    frm.btnShonin.disabled = false;

    return true;
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
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
        // ���[�J�[�h���b�v�_�E��
        if (XmlId.toString() == "RGEN3000") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3010
                    break;
            }

        // ��ރh���b�v�_�E��
        } else if (XmlId.toString() == "RGEN3010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3010
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    break;
            }

        //�����{�^������ �x�[�X�P���ꗗ��������
        } else if (XmlId.toString() == "RGEN3500"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3500
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);

                    // �m�F�ς݃`�F�b�N
                    if (frm.chkKakunin.checked) {
                    	funXmlWrite(reqAry[i], "chk_kakunin", "1", 0);
                    } else if (frm.chkShonin.checked) {
                    	funXmlWrite(reqAry[i], "chk_kakunin", "1", 0);
                    	frm.chkKakunin.checked = false;
                    } else {
                    	funXmlWrite(reqAry[i], "chk_kakunin", "", 0);
                    }

                    // ���F�ς݃`�F�b�N
                    if (frm.chkShonin.checked) {
                    	funXmlWrite(reqAry[i], "chk_shonin", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "chk_shonin", "", 0);
                    }
                    // �L���ł̂݃`�F�b�N
                    if (frm.chkHanOnly.checked) {
                    	funXmlWrite(reqAry[i], "chk_hanonly", "1", 0);
                    } else {
                    	funXmlWrite(reqAry[i], "chk_hanonly", "", 0);
                    }
                    break;
            }

        //�����{�^�������i�S���`�F�b�N�j
        } else if (XmlId.toString() == "RGEN3510"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3510
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value, 0);
                    break;
            }

        // ���F�{�^������
        } else if (XmlId.toString() == "RGEN3550"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        // �o�^�{�^������
        } else if (XmlId.toString() == "RGEN2160"){

            var cd_maker = document.getElementById("cd_maker_" + funGetCurrentRow()).value;
            var cd_houzai = document.getElementById("cd_houzai_" + funGetCurrentRow()).value;
            var no_hansu = document.getElementById("no_hansu_" + funGetCurrentRow()).value;
            var mishiyo;
            if (frm.chkMishiyo.checked) {
            	mishiyo = 1;
            } else {
            	mishiyo = "2";
            }

            var put_code = document.getElementById("mode").value + "-" + cd_maker + "-" + cd_houzai + "-" + no_hansu + "-2" + "-" + mishiyo;

            // �����R�[�h�u���y���[�J�[�R�[�h:::��ރR�[�h:::�Ő��z
            var put_code = put_code.replace(/-/g,":::");
//            alert(put_code);
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_code, 0);
                    break;
            }
        //�����{�^�������i���g�p�`�F�b�N�j
        } else if (XmlId.toString() == "RGEN3720"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN3720
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    var mishiyo;
                    if (frm.chkMishiyo.checked) {
                    	mishiyo = 1;
                    } else {
                    	mishiyo = 2;
                    }
                    funXmlWrite(reqAry[i], "chk_mishiyo", mishiyo, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

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

        case 2:    //����Ͻ��i��2�J�e�S���j
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
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
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var selIndex = 0;

    // �����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �x�[�X�P���o�^�E���F��ʋN�����ʒm
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F�I�������x�[�X�P�����Z�b�V�����֕ۑ�����
//========================================================================================
function funBasePriceAddTuti(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;
}

//========================================================================================
// ���F�{�^���������A�m�F�ς݂��`�F�b�N����
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funCheckKakuninData(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3550";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3550");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3550I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3550O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlFGEN3550, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //�ް�����������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) != "true") {
        return false;
    }

    return true;
}

//========================================================================================
// ��ޖ��R���{�{�b�N�X�A������
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/05
// ����  �F�Ȃ�
// �T�v  �F���[�J�[�ɕR�t����ރR���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeMaker() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3010";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3010");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3010I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3010O);

    //��޺����ޯ���̸ر
    funClearSelect(frm.ddlHouzai, 2);

    //������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	//�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3010, xmlReqAry, xmlResAry, 1) == false) {
		return false;
	}

	//��޺����ޯ���̍쐬
	funCreateComboBox(frm.ddlHouzai, xmlResAry[2], 2);
	xmlFGEN3010.load(xmlResAry[2]);

	return true;

}

