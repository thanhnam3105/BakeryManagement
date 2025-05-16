//========================================================================================
//�yKPX@1602367�z
// ���ʕϐ�
// �쐬�ҁFMay Thu
// �쐬���F2016/09/13
//========================================================================================

//========================================================================================
// �����\������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/13
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {
    //��ʐݒ�
    funInitScreen(ConSeihinSearchId);

    //������ү���ޕ\��
    funShowRunMessage();

    //������ү���ޔ�\��
    funClearRunMessage();
	//�I���{�^�����������i�R�[�h�ɋ�ɂ���
	window.returnValue = "";
    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFMay Thu
// �쐬���F2016/09/13
// ����  �F�@mode  �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�������A�Ώێ��ށA����������擾����
//========================================================================================
function funGetInfo(mode) {
	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN3590";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3590");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3590I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3590O);

	// ������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
		return false;
	}
	// ����ݏ��A�����ޯ���̏����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3590, xmlReqAry, xmlResAry, mode) == false) {
		return false;
	}
    //scrflag = other �ˌ���
     funCreateSizaiTableList(xmlResAry[2]);
     funCountInfoDisplay(funGetLength(xmlResAry[2]),"divCountInfo");

	return true;
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
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
        if (XmlId.toString() == "RGEN3590"){
              //FGEN3590
				funXmlWrite(reqAry[i], "name_seihin", frm.inputNmHimei.value, 0);
		}
    }
    return true;

}

//========================================================================================
// ���ޏ��@�e�[�u���ꗗ�쐬����
// �쐬�ҁFMay Thu
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
	}

}

//========================================================================================
// ���ޏ�� ���׍s�쐬����
// �쐬�ҁFMay Thu
// �쐬���F2016/09/15
// ����  �F�@row     �F�s�ԍ� �i0 �` �j
//       �F�AxmlData �FXML�f�[�^
// �T�v  �F���ꗗ�̍s�ǉ�����
//========================================================================================
function funAddSizaiTable(row, xmlData) {
	var detail = document.getElementById("detail");
	var html;

	// �s�ݒ�
	var tr = document.createElement("tr");
	tr.setAttribute("class", "disprow");
	tr.setAttribute("id", "tr" + row);

	var td1 = document.createElement("td");
	td1.setAttribute("class", "column");
	td1.innerHTML = funXmlRead(xmlData, "cd_hin", row);
	if (td1.innerHTML == "") {
		td1.innerHTML = "&nbsp;";
	}
	td1.style.textAlign = "left";
	td1.style.width =85;
	tr.appendChild(td1);	

	// 
	var td2 = document.createElement("td");
	td2.setAttribute("class", "column");
	td2.innerHTML = funXmlRead(xmlData, "nm_seihin", row);
	if (td2.innerHTML == "") {
		td2.innerHTML = "&nbsp;";
	}
	td2.style.textAlign = "left";
	td2.style.width = 200;
	tr.appendChild(td2);

	// 
	var td3 = document.createElement("td");
	td3.setAttribute("class", "column");
	td3.innerHTML = funXmlRead(xmlData, "nisugata_hyoji", row);
	if (td3.innerHTML == "") {
		td3.innerHTML = "&nbsp;";
	}
	td3.style.textAlign = "left";
	td3.style.width = 200;
	tr.appendChild(td3);

	detail.appendChild(tr);
	
}

//========================================================================================
// �I���{�^������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/16
// �T�v  �F�I������
//========================================================================================
function funEndClick(){
	//�I������
	funEnd();
	
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
	if(!window.opener || window.opener.closed){
		// ���[�_���ŊJ������
		// ��ʂ����
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
// �����{�^����������
// �쐬�ҁFMay Thu
// �쐬���F2016/09/16
// ����  �F�Ȃ�
// �T�v  �F�f�[�^�̌������s��
//========================================================================================
function funSeihinNameSearch() {
	var frm = document.frm00;    //̫�тւ̎Q��
	var detail = document.getElementById("detail");
	// ���׃f�[�^�폜 ��������ƁA�f�[�^���d�Ȃ��Ă��邩��A�܂��f�[�^������ƁA�폜����
	while(detail.firstChild){
		detail.removeChild(detail.firstChild);
	}
    //��������
    //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
   setTimeout(function(){ funSearch0() }, 0);
}

//========================================================================================
//�����{�^����������
//�쐬�ҁFMay Thu
//�쐬���F2016/09/16
//����  �F�Ȃ�
//�T�v  �F���ރf�[�^�̌������s��
//========================================================================================
function funSearch0() {
	var frm = document.frm00;    //̫�тւ̎Q��
	//������ү���ޕ\��
    funShowRunMessage();

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
//�����{�^����������
//�쐬�ҁFMay Thu
//�쐬���F2016/09/16
//����  �F�Ȃ�
//�T�v  �F���ރf�[�^�������ʂ�Ԃ��B
//========================================================================================
function funChoiceSeihin() {
	    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }
    //�߂�l�̐ݒ�
    window.returnValue = funXmlRead(xmlFGEN3590O, "cd_hin", funGetCurrentRow());

    //��ʂ����
    close(self);
}





