

//========================================================================================
// ���ʕϐ�
// �쐬�ҁFt2nakamura
// �쐬���F2016/09/15
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
//�쐬�ҁFt2nakamura
//�쐬���F20160912
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConHattyuLiteralMstId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�ꗗ�̸ر
    //funClearList();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    // �{�^���񊈐�
    funDisBtn();

    return true;
}

//========================================================================================
//��ʏ��擾����
//�쐬�ҁFhisahori
//�쐬���F2014/10/10
//����  �F�@mode  �F�������[�h
//        1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
//�߂�l�F����I��:true�^�ُ�I��:false
//�T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGENAAA";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGENAAA, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    //funSaveKengenInfo();

    return true;

}
//========================================================================================
//�����{�^����������
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/01
//����  �F�Ȃ�
//�T�v  �F�f�U�C���X�y�[�X�̌������s��
//========================================================================================
function funSearch() {

 var frm = document.frm00;    //̫�тւ̎Q��

var cdHattyu = document.getElementById("cdhattyu").value;
// ���l�ȊO�G���[

 // ���p�ϊ�
  var halfVal = cdHattyu.replace(/[�I-�`]/g,
    function( tmpStr ) {
      // �����R�[�h���V�t�g
      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
    }
  );

for (var i = 0; i < halfVal.length; i++) {
	 if (halfVal.charAt(i) == "0" ||  parseInt(halfVal.charAt(i))){

	 } else {
		 funErrorMsgBox("���l����͂��Ă��������B");
		 return false;
	 }
}

var nmHattyu = document.getElementById("nmhattyu").value;
var formatStr = nmHattyu.replace(/^\s+|\s+$/g, "");
nmHattyu = formatStr;

 //������ү���ޕ\��
 funShowRunMessage();

 //��������
 //�������E�B���h�E�\���ׁ̈AsetTimeout�ŏ����\��
 setTimeout(function(){ funDataSearch() }, 0);

 // �X�N���[����ԏ�ɐݒ肷��
 document.getElementById("sclList").scrollTop = 0;

 return true;

}

//========================================================================================
//���e�������擾����
//�쐬�ҁFt2nakamura
//�쐬���F2016/9/12
//����  �F�Ȃ�
//�T�v  �F���e�����f�[�^�̌������s��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3580";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3580");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3580I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3580O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    //�I���s�̏�����
    funSetCurrentRow("");

    //�ꗗ�̸ر
    funClearList();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //��ʏ�����
        //funInit();
        funClearRunMessage();
        return false;
    }
    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3580, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        funClearRunMessage();
        return false;
    }

    //�ް�����������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";

        // ������J�e�S���}�X�^���ꗗ�̍쐬
        funAddHattyuusakiListt(xmlResAry[2]);

        //������ү���ޔ�\��
        funClearRunMessage();

    } else {

    	// �Ώۃf�[�^�Ȃ�
    	funErrorMsgBox(E000030);

       //��\���F�Ώۃf�[�^�Ȃ�
        tblList.style.display = "none";

        // �{�^���񊈐�
        funDisBtn();

        //������ү���ޔ�\��
        funClearRunMessage();

        return false;
    }

	// ���s�\�{�^����������
	frm.btnInsert.disabled = false;
	frm.btnExcel.disabled = false;
	frm.btnLineAdd.disabled = false;



    return true;
}

//========================================================================================
//������R�[�h�A������
//�쐬�ҁFt2nakamura
//�쐬���F2016/1006
//����  �F�Ȃ�
//�T�v  �F�J�e�S���ɕR�t�����e�����R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeCategory(row) {

 var frm = document.frm00;    //̫�тւ̎Q��
 var XmlId = "RGEN3650";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3650");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3650I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3650O);


 //�w�肳�ꂽ�����於���N���A����B
 var nm_literal = document.getElementById("nm_literal-" + row);


 // ������XMĻ�قɐݒ�
 if (funReadyOutput(XmlId, xmlReqAry, 1, rowcnt ,row) == false) {
     return false;
 }

 // ���ُ����擾
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3650, xmlReqAry, xmlResAry, 1) == false) {

     return false;
 }

 // �G���[���b�Z�[�W����ł͂Ȃ��ꍇ�G���[
 var errorMsg = funXmlRead(xmlResAry[2], "msg_error", 0);

 // if (errorMsg != "") {
//	 funErrorMsgBox(errorMsg);
//     return false;
// }

 // hidden�����於
 nm_literal.value = funXmlRead(xmlResAry[2], "nm_literal", 0);

 // �����於
 var table = document.getElementById("tblList");

 table.rows.item(row).cells(1).innerHTML = "<input type=\"hidden\" name=\"nm_literal\" id=\"nm_literal-" + row + "\" value=\"" + funXmlRead(xmlResAry[2], "nm_literal", 0) + "\" style=\"width:217px\">";
	 if(funXmlRead(xmlResAry[2], "nm_literal", 0) == ""){
		 table.rows.item(row).cells(1).innerHTML += "&nbsp;"
     } else {
 table.rows.item(row).cells(1).innerHTML += funXmlRead(xmlResAry[2], "nm_literal", 0);
     }


 if (errorMsg != "") {
	 funErrorMsgBox(errorMsg);

     return false;
 }
 return true;

}


//========================================================================================
//�s�ǉ��{�^����������
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//����  �F�Ȃ�
//�T�v  �F������}�X�^�[���ɍs�ǉ�����
//========================================================================================
function funLineAdd() {

		// �������I�����Ă��Ȃ����i�����サ�����Ȃ��j
	//	if (funSelChk() == "") {
	////		funErrorMsgBox(E000031);
	//		funErrorMsgBox(funSelChk.Msg);
	//		return false;
		//}

	// ������}�X�^-���ꗗ�\��
	tblList.style.display = "block";

	// ���׍s��ǉ�
	funAddHattyuusakiMst(rowcnt, "");


	insertFlg(rowcnt);

	document.getElementById("nm_2nd_literal-" + rowcnt).value = "  �l";

	rowcnt++;
	// �ǉ������s�܂ŃX�N���[��
	goBottom();

}

//========================================================================================
//������}�X�^�[���@�e�[�u���ꗗ�쐬����
//�쐬�ҁFE.Kitazawa
//�쐬���F2014/09/01
//����  �FxmlData    �FXML�f�[�^
//�T�v  �F�f�U�C���X�y�[�X���ꗗ���쐬����
//========================================================================================
function funAddHattyuusakiListt(xmlData) {

	var tableNm = "table";

    //�����擾���A�s����ۑ�
	rowcnt = funGetLength(xmlData);

	// �L�[��ێ�
	var hdn_cd_literal = "";
	var hdn_cd_2nd_literal = "";

	for(var i = 0; i < rowcnt; i++){

    	// �s�ǉ�
		funAddHattyuusakiMst(i, xmlData);

	}
}

//========================================================================================
//�s�ǉ��������Ɉ�ԉ��ɃX�N���[�X����
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//========================================================================================
function goBottom() {
    var obj = document.getElementById("sclList");
    if(!obj) return;
    obj.scrollTop = obj.scrollHeight;
}

//========================================================================================
//������}�X�^��� ���׍s�쐬����
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//����  �F�@row     �F�s�ԍ�
//    �F�AxmlData �FXML�f�[�^�i�u�s�ǉ��v���́A""�j
//�T�v  �F�f�U�C���X�y�[�X���ꗗ�̍s�ǉ�����
//========================================================================================
function funAddHattyuusakiMst(row, xmlData) {

		var detail = document.getElementById("detail");

	 var html;

	 // �s�ݒ�
	 var tr = document.createElement("tr");
	 tr.setAttribute("class", "disprow");
	 tr.setAttribute("id", "tr" + row);

//	 // �폜�`�F�b�N�{�b�N�X
//	 var td1 = document.createElement("td");
//	 td1.setAttribute("class", "column");
//	 html = "<input type=\"checkbox\" id=\"check-" + row + "\" name=\"check\" value=\"" + row + "\" >";
//	 // �X�V�t���O
//	 html += "<input type=\"hidden\" id=\"update_flgl-" + row + "\" name=\"update_flgl\" value=\" \">";
//	 td1.innerHTML = html;
//	 td1.style.textAlign = "center";
//	 td1.style.width = 0;
//	 tr.appendChild(td1);


	 // ������R�[�h
	 var td2 = document.createElement("td");
	 td2.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"cd_literal\" id=\"cd_literal-" + row + "\" value=\"" + fillsZero(funXmlRead(xmlData, "cd_literal", row), 6) + "\" style=\"width:98px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 // �X�V�t���O
	 html += "<input type=\"hidden\" id=\"update_flgl-" + row + "\" name=\"update_flgl\" value=\" \">";
	 html += "<input type=\"hidden\" id=\"hiddenCd_literal-" + row + "\" name=\"hiddenCd_literal\" value=\"" + funXmlRead(xmlData, "cd_literal", row) + "\">";
	 td2.innerHTML = html;
	 td2.style.textAlign="left";
	 td2.style.width = 99;
	 tr.appendChild(td2);

	 // �����於
	 var td3 = document.createElement("td");
	 td3.setAttribute("class", "column");
	 html = funXmlRead(xmlData, "nm_literal", row);
	 if(html == ""){
         html = "&nbsp;"
     }
	 html += "<input type=\"hidden\" name=\"nm_literal\" id=\"nm_literal-" + row + "\" value=\"" + funXmlRead(xmlData, "nm_literal", row) + "\" style=\"width:217px\">";
	 //html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td3.innerHTML = html;
	 td3.style.textAlign="left";
	 td3.style.width = 218;
	 tr.appendChild(td3);

	 // ������\����
	 var td4 = document.createElement("td");
	 td4.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"no_sort\" id=\"no_sort-" + row + "\" value=\"" + funXmlRead(xmlData, "no_sort", row) + "\" style=\"width:59px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td4.innerHTML = html;
	 td4.style.textAlign="left";
	 td4.style.width = 60;
	 tr.appendChild(td4);

	 // �S���҃R�[�h
	 var td5 = document.createElement("td");
	 td5.setAttribute("class", "column");
	 html = funXmlRead(xmlData, "cd_2nd_literal", row);
	 if(html == ""){
         html = "&nbsp;"
     }
	 html += "<input type=\"hidden\" id=\"hiddenCd_2nd_literal-" + row + "\" name=\"hiddenCd_2nd_literal\" value=\"" + funXmlRead(xmlData, "cd_2nd_literal", row) + "\">";
	 td5.innerHTML += html;
	 td5.style.textAlign="left";
	 td5.style.width = 60;
	 tr.appendChild(td5);

	 // �S����
	 var td6 = document.createElement("td");
	 td6.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"nm_2nd_literal\" id=\"nm_2nd_literal-" + row + "\" value=\"" + funXmlRead(xmlData, "nm_2nd_literal", row) + "\" style=\"width:375px; ime-mode:active;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 html += "<input type=\"hidden\" id=\"hiddenNm_2nd_literal-" + row + "\" name=\"hiddenNm_2nd_literal\" value=\"" + funXmlRead(xmlData, "nm_2nd_literal", row) + "\">";
	 td6.innerHTML = html;
	 td6.style.textAlign="left";
	 td6.style.width = 376;
	 tr.appendChild(td6);

	 // ��񃊃e�����\����
	 var td7 = document.createElement("td");
	 td6.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"no_2nd_sort\" id=\"no_2nd_sort-" + row + "\" value=\"" + funXmlRead(xmlData, "no_2nd_sort", row) + "\" style=\"width:62px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td7.innerHTML = html;
	 td7.style.textAlign="left";
	 td7.style.width = 63;
	 tr.appendChild(td7);

	 // ���[���A�h���X
	 var td8 = document.createElement("td");
	 td8.setAttribute("class", "column");
	 html = "<input type=\"text\" name=\"mail_address\" id=\"mail_address-" + row + "\" value=\"" + funXmlRead(xmlData, "mail_address", row) + "\" style=\"width:268px; ime-mode:disabled;\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 td8.innerHTML = html;
	 td8.style.textAlign="left";
	 td8.style.width = 268;
	 tr.appendChild(td8);

	 // ���g�p�`�F�b�N�{�b�N�X
	 var td9 = document.createElement("td");
	 td9.setAttribute("class", "column");
	 html = "<input type=\"checkbox\" name=\"flg_mishiyo\" id=\"flg_mishiyo-" + row + "\" style=\"width:57px\"";
	 html += "onchange=\"funHattyuuUpdate(" + row + ");\" >";
	 html += "<input type=\"hidden\" id=\"hiddenFlg_mishiyo-" + row + "\" name=\"hiddenFlg_mishiyo\" value=\" \">";
	 td9.innerHTML = html;
	 td9.style.textAlign = "center";
	 td9.style.width = 58;
	 tr.appendChild(td9);

	 detail.appendChild(tr);

	 // ���g�p�̃`�F�b�N�{�b�N�Xchecked
   	 var flg_misiyo = funXmlRead(xmlData, "flg_mishiyo", row);
	 if (flg_misiyo == 2) {
		 document.getElementById("flg_mishiyo-" + row).checked = true;
	 } else {
		 document.getElementById("flg_mishiyo-" + row).checked = false;
	 }
	 // �`�F�b�N�{�b�N�X��\���i�����_�ł͎g�p���Ă��Ȃ�20161006�j
	 //document.getElementById("check-" + row).style.display = "none";


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
//�o�^�{�^����������
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/28
//����  �F�Ȃ�
//�T�v  �F������}�X�^�[���̓o�^
//========================================================================================
function funUpdate() {

	 var frm = document.frm00;   //̫�тւ̎Q��
	 var upFildNm = "";          //�A�b�v���[�h����t�B�[���h��
	// var delFileNm = "";         //�폜����t�@�C����
	// var lstSyurui = {};         //�o�^�����ނ̔z��i�d���`�F�b�N�p�j
	// var strMsg = ""				//�m�F���b�Z�[�W�t������

	 // �ۑ��t�@�C���̃T�u�t�H���_�[�擾�i�H��A�E��A�������C����_�Ōq���j
	 //var subFolder = funSelChk();
	 // �������I������Ă��Ȃ��ꍇ
	//	if (subFolder == "") {
	////		funErrorMsgBox(E000031);
	//		funErrorMsgBox(funSelChk.Msg);
	//		return false;
	//	}

	// �I��������ރR�[�h��_�Ōq���ׁA�t�@�C�����ɐݒ�
	//var subFlst = "";

	// �ꗗ�f�[�^���Ȃ�
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	// �\������Ă���ꗗ���`�F�b�N
	// �Q�ƃt�@�C���A�\���pinput���󔒂łȂ�����
	// ���b�Z�[�W���X�g
	var msgStr = "";

	for(var i = 0; i < rowcnt; i++){
	     // �s�I�u�W�F�N�g
	 	var tr = document.getElementById("tr" + i);

		 //var check = document.getElementById("check-" + i);

	     // ������R�[�h
	     var cd_literal = document.getElementById("cd_literal-" + i);

	     // �����於
	     //var  nm_literal = document.getElementById("nm_literal-" + i);

	     // ������\����
	     var no_sort = document.getElementById("no_sort-" + i);

	     // �S����
	     var  nm_2nd_literal = document.getElementById("nm_2nd_literal-" + i);

	     // �S���ҕ\����
	     var no_2nd_sort = document.getElementById("no_2nd_sort-" + i);

	     // ���[���A�h���X
	     var  mail_address = document.getElementById("mail_address-" + i);

	     // update_flgl
	     var  update_flgl = document.getElementById("update_flgl-" + i);




		// ������R�[�h���󔒂̏ꍇ�A�G���[�i�c�a�o�^�ł��Ȃ��j
		if (cd_literal.value == "") {
			msgStr += ((i + 1) + E000054 + "<br>");

		// ���l�`�F�b�N
		} else {
			if (isNaN(cd_literal.value)) {
				msgStr += ((i + 1) + E000054 + "<br>");
			}
		}

//		// �����於���󔒂̏ꍇ�A�G���[�i�c�a�o�^�ł��Ȃ��j
//		// �󔒂ł͂Ȃ��ꍇ���l�łȂ��ꍇ�A�G���[�iDB�o�^�ł��Ȃ��j
//		if (nm_literal.value == "") {
//			msgStr += ((i + 1) + E000055 + "<br>");
//		}

		// ������\�������󔒂̏ꍇ�A�G���[�i�c�a�o�^�ł��Ȃ��j
		// �󔒂ł͂Ȃ��ꍇ���l�`�F�b�N
		if (no_sort.value == "") {
			msgStr += ((i + 1) + E000056 + "<br>");
		} else {
			if (isNaN(no_sort.value)) {
				msgStr += ((i + 1) + E000055 + "<br>");
			}
		}

		// �S���҂��󔒂̏ꍇ�A�G���[�i�c�a�o�^�ł��Ȃ��j
		var nm2ndLiteral = nm_2nd_literal.value;
		var samaStr = nm2ndLiteral.substr(nm2ndLiteral.length-1);
		if (nm_2nd_literal.value == "") {
			msgStr += ((i + 1) + E000057 + "<br>");

		} else if (samaStr != "�l") {
			msgStr += ((i + 1) + "�s�ڂɁu�l�v����͂��Ă��������B" + "<br>");
		}

		// �S���ҕ\�������󔒂̏ꍇ�A�G���[�iDB�o�^�ł��Ȃ��j
		// �S���ҕ\���������l�łȂ��ꍇ�A�G���[�iDB�o�^�ł��Ȃ��j
		if (no_2nd_sort.value == "") {
			msgStr += ((i + 1) + E000046 + "<br>");
		} else {
			if (isNaN(no_2nd_sort.value)) {
				msgStr += ((i + 1) + E000047 + "<br>");
			}
		}

		// ���[���A�h���X�󔒂̏ꍇ�A�G���[�iDB�o�^�ł��Ȃ��j
		if (mail_address.value == "") {
			msgStr += ((i + 1) + E000048 + "<br>");

		// ���[���A�h���X����łȂ����[���A�h���X�`���łȂ��ꍇ�A�G���[�yDB�o�^�ł��Ȃ��j
		} else if (mail_address.value != "" && !mail_address.value.match(/^[A-Za-z0-9]+[\w-]+@[\w\.-]+\.\w{2,}$/)) {
			msgStr += ((i + 1) + E000049 + "<br>");
		}



	}
	// �G���[���b�Z�[�W������ꍇ�\������B
	if (msgStr != "") {
		funErrorMsgBox(msgStr);
		return false;
	}


	// �ꗗ�f�[�^���Ȃ�
	if (rowcnt == 0) {
		funErrorMsgBox(E000030);
		return false;
	}

	 // �o�^�m�F
	if (funConfMsgBox(I000002 ) != ConBtnYes) {
		return;
	}

	funHattyuusakiMstInsert();


//	// �o�^�A�X�V�A�폜����
//	for (var i = 0; i < rowcnt; i++) {
//		var checkbox = document.getElementById("check-" + i);
//		var updateFlg = document.getElementById("update_flgl-" + i);
//
//		if (checkbox.checked) {
//			// �폜
//			funHattyuusakiMstDelete(i);
//		} else if (checkbox.checked == false && updateFlg.value == 1) {
//			// �o�^
//			if (funHattyuusakiDifferenceCheck(i)) {
//
//				funHattyuusakiMstInsert(i);
//			} else {
//				// �G���[����
//				funErrorMsgBox("������R�[�h�������ꍇ�A�ʂ̔����於�A������\�����͓��͂ł��܂���B");
//				return false;
//			}
//
//		} else if (checkbox.checked == false && updateFlg.value == 2) {
//			// �X�V
//			if (funHattyuusakiDifferenceCheck(i)) {
//				funHattyuusakiMstUpdate(i);
//			} else {
//				// �G���[����
//				funErrorMsgBox("������R�[�h�������ꍇ�A�ʂ̔����於�A������\�����͓��͂ł��܂���B");
//				return false;
//			}
//		}
//	}

	// �o�^�A�X�V�A�폜��̍Č���



//	// ������}�X�^�[�c�a�o�^�A�X�V�A�폜
//	if (funHattyuusakiMstUpdate()) {
//	 //����o�^
//	 funInfoMsgBox(I000005);
//	}

}

//========================================================================================
//������}�X�^�[DB�X�V����
//�쐬�ҁFt2nakamura
//�쐬���F2016/0916
//����  �FXmlId     �FXmlId
//�T�v  �F������}�X�^�[DB�X�V�̎��s
//========================================================================================
function funHattyuusakiMstInsert(){

	var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3630";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3630");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3630I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3630O);

    //XML�̏�����
    setTimeout("xmlFGEN3630I.src = '../../model/FGEN3630I.xml';", ConTimer);

	 // ������ү���ޕ\��
	 funShowRunMessage();


	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1, rowcnt) == false) {
	    //������ү���ޔ�\��
	    funClearRunMessage();
		return false;
	}

	//������}�X�^DB�o�^
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3630, xmlReqAry, xmlResAry, 1) == false) {
	    //������ү���ޔ�\��
	    funClearRunMessage();
	    //funErrorMsgBox(dspMsg);
		return false;
	}

	//������ү���ޔ�\��
	 funClearRunMessage();



	 if (funXmlRead(xmlResAry[2], "updateErrorFlg_return", 0) == "false") {  //updateErrorFlg_return
		 funSearch();
	 	return true;
	 } else {
	     //error
		 //����ү���ނ̕\��
		 var dspMsg = funXmlRead(xmlResAry[2], "meisaiNum", 0);

	 	funErrorMsgBox((parseInt(dspMsg)+1) + "�s�ڂ̔�����R�[�h�A�S���҃R�[�h���d���̂��߁A�X�V�Ɏ��s���܂����B");
	     return false;
	 }



	 return true;

}


//========================================================================================
//������}�X�^�[�o�^�A�X�V�`�F�b�N����
//�쐬�ҁFt2nakamura
//�쐬���F2016/0929
//����  �FXmlId     �FXmlId
//�T�v  �F������}�X�^�[CD��Ӄ`�F�b�N
//========================================================================================
function funHattyuusakiDifferenceCheck(row) {

	var errorFlg = true;

	var updateHattyuusakiJoin = "";
	// �X�V�Ώ۔�����R�[�h
	var beforeCdLiteralValue = document.getElementById("cd_literal-" + row).value;
	 // ���p�ϊ�
	  var cdLiteralValue = beforeCdLiteralValue.replace(/[�I-�`]/g,
	    function( tmpStr ) {
	      // �����R�[�h���V�t�g
	      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
	    }
	  );

	// �X�V�Ώ۔����於
	//var nmLiteralValue = document.getElementById("nm_literal-" + row).value;
	var table = document.getElementById("tblList");
	var nmLiteralValue = table.rows.item(row).cells(1).innerText;
	// �X�V�Ώ۔�����\����
	var noSortValue = document.getElementById("no_sort-" + row).value;

	// �X�V�Ώۂ̔�����R�[�h�Ɣ����於�Ɣ����於�\�������W���C���g
	updateHattyuusakiJoin = cdLiteralValue + "@" + nmLiteralValue + "@" + noSortValue;

	for (var i = 0; i < rowcnt; i++) {
		// ��r��������R�[�h
		var beforeTaisyouCdLiteral = document.getElementById("cd_literal-" + i).value;
		 // ���p�ϊ�
		  var taisyouCdLiteral = beforeTaisyouCdLiteral.replace(/[�I-�`]/g,
		    function( tmpStr ) {
		      // �����R�[�h���V�t�g
		      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
		    }
		  );



		// ��r�������於
		var table = document.getElementById("tblList");
		var taisyoNmLiteralValue = table.rows.item(i).cells(1).innerText;
		//var taisyoNmLiteralValue = document.getElementById("nm_literal-" + i).value;
		// ��r��������\����
		var taisyoSortlValue = document.getElementById("no_sort-" + i).value;

		// ��r���̔�����R�[�h�Ɣ����於�Ɣ����於�\�������W���C���g
		hikakutaisyouHattyuusakiJoin = taisyouCdLiteral + "@" + taisyoNmLiteralValue + "@" + taisyoSortlValue;
		// ������R�[�h�������ꍇ
		if (taisyouCdLiteral == cdLiteralValue) {
			if (hikakutaisyouHattyuusakiJoin != updateHattyuusakiJoin) {
				errorFlg = false;
			}
		}
	}
	return errorFlg;
}

//========================================================================================
//EXCEL�o�͏���
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/30
//�T�v  �FExcel�o�͂��s��
//========================================================================================
function funFileExcel() {

	var frm = document.frm00;    //̫�тւ̎Q��

    var XmlId = "RGEN3670";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3670");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3670I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3670O);

    // Excel�o�͊m�Fү���ނ̕\��
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    //XML�̏�����
    setTimeout("xmlFGEN3670I.src = '../../model/FGEN3670I.xml';", ConTimer);
    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // �o��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3670, xmlReqAry, xmlResAry,
         1) == false) {
        return false;
    }

    //̧���߽�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlFGEN3670O, "URLValue", 0);

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);

    return true;
}

//========================================================================================
//����ʑJ�ڏ���
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/03
//�T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext() {
    //��ʂ����
    close(self);
    return true;
}

//========================================================================================
//������}�X�^�[����ύX�����ꍇ�X�V�t���O2�����Ă�
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//����  �F�Ȃ�
//�T�v  �FDB�o�^�A�X�V�t���O�����Ă�
//========================================================================================
function funHattyuuUpdate(row) {

	var frm = document.frm00;   //̫�тւ̎Q��

	// ������R�[�h�ݒ�ɂ�蔭���於��ݒ肷��B
	if ("cd_literal" == event.srcElement.name) {
		var cd_literal = document.getElementById("cd_literal-" + row).value;
		if (cd_literal == "") {
			funErrorMsgBox("������R�[�h����͂��Ă�������");
			return false;
		} else {
			if (isNaN(cd_literal)) {
				 funErrorMsgBox("���l����͂��Ă��������B");
				 return false;
			}
		}
		document.getElementById("cd_literal-" + row).value = fillsZero(cd_literal, 6)
		// �����於�擾
		funChangeCategory(row);
	}

	// ������\�������l�ȊO�G���[
	if ("no_sort" == event.srcElement.name) {
		var no_sort = document.getElementById("no_sort-" + row).value;

		if (no_sort != "") {

			for (var i = 0; i < no_sort.length; i++) {
				 if (parseInt(no_sort.charAt(i))){

				 } else {
					 funErrorMsgBox("���l����͂��Ă��������B");
					 return false;
				 }
			}
		} else {
			funErrorMsgBox("������\��������͂��Ă��������B");
		}
	}

	// �S���ҕ\�������l�ȊO�G���[
	if ("no_2nd_sort" == event.srcElement.name) {
		var no_2nd_sort = document.getElementById("no_2nd_sort-" + row).value;

		if (no_2nd_sort != "") {

			for (var i = 0; i < no_2nd_sort.length; i++) {
				 if (parseInt(no_2nd_sort.charAt(i))){

				 } else {
					 funErrorMsgBox("���l����͂��Ă��������B");
					 return false;
				 }
			}
		}  else {
			funErrorMsgBox("�S���ҕ\��������͂��Ă��������B");
		}
	}

	var obj = document.getElementById("update_flgl-" + row);
	// �V�����s��ǉ��������̂��C���T�[�g�t���O�ɐݒ�
	if (obj.value == 1) {
		obj.value = 1;
	} else {
		obj.value=2;
	}


    // �S����
    var  nm_2nd_literal = document.getElementById("nm_2nd_literal-" + row);
	if (nm_2nd_literal.name == "nm_2nd_literal") {
	    var nm_2nd_literalValue = document.getElementById("nm_2nd_literal-" + row).value;
	    var formatNm_2nd_literalValue =nm_2nd_literalValue.replace(/^\s+|\s+$/g, "");
	    document.getElementById("nm_2nd_literal-" + row).value = formatNm_2nd_literalValue;
	}


//	// ������R�[�h���p�ɕϊ�
//	var cd_literal = document.getElementById("cd_literal-" + row);
//
//	if ( !cd_literal.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (cd_literal.name == "cd_literal") {
//			 var cd_literalValue = document.getElementById("cd_literal-" + row).value;
//			 // ���p�ϊ�
//			  var halfVal = cd_literalValue.replace(/[�I-�`]/g,
//			    function( tmpStr ) {
//			      // �����R�[�h���V�t�g
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("cd_literal-" + row).value = halfVal;
//
//		}
//	}
//	// ������\�������p�ɕϊ�
//	var no_sort = document.getElementById("no_sort-" + row);
//
//	if ( !no_sort.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (no_sort.name == "no_sort") {
//			 var no_sortValue = document.getElementById("no_sort-" + row).value;
//			 // ���p�ϊ�
//			  var halfVal = no_sortValue.replace(/[�I-�`]/g,
//			    function( tmpStr ) {
//			      // �����R�[�h���V�t�g
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("no_sort-" + row).value = halfVal;
//
//		}
//	}
//	// �S���ҕ\�������p�ɕϊ�
//	var no_2nd_sort = document.getElementById("no_2nd_sort-" + row);
//
//	if ( !no_2nd_sort.value.match(/^(\w| |'|,|&)+$/) ){
//
//		if (no_2nd_sort.name == "no_2nd_sort") {
//			var no_2nd_sortValue = document.getElementById("no_2nd_sort-" + row).value;
//			 // ���p�ϊ�
//			  var halfVal = no_2nd_sortValue.replace(/[�I-�`]/g,
//			    function( tmpStr ) {
//			      // �����R�[�h���V�t�g
//			      return String.fromCharCode( tmpStr.charCodeAt(0) - 0xFEE0 );
//			    }
//			  );
//			  document.getElementById("no_sort-" + row).value = halfVal;
//
//		}
//	}
	return true;

}
//========================================================================================
//������}�X�^�[����ύX�����ꍇ�X�V�t���O1�����Ă�
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//����  �F�Ȃ�
//�T�v  �FDB�o�^�A�o�^�t���O�����Ă�
//========================================================================================
function insertFlg(row) {

	var obj = document.getElementById("update_flgl-" + row);
	obj.value=1;


}

//========================================================================================
//�����p�̔�����R�[�h�A�����於��ύX�����ꍇ�AExcel�{�^���񊈐�����
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/30
//����  �F�Ȃ�
//�T�v  �F�ꗗ�̏�������������
//========================================================================================
function hattyuusakiFocusOut() {

	if (rowcnt != 0) {
		// 	�uExcel�v�{�^���񊈐�
		document.getElementById("btnExcel").disabled = true;
	}

}

//========================================================================================
//�ꗗ�N���A����
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/13
//����  �F�Ȃ�
//�T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

 //�ꗗ�̸ر
 xmlFGEN3580O.src = "";
 tblList.style.display = "none";
 var detail = document.getElementById("detail");
 while(detail.firstChild){
     detail.removeChild(detail.firstChild);
 }
}

//========================================================================================
//�`�F�b�N�{�b�N�X����
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/13
//����  �F�Ȃ�
//�T�v  �F�`�F�b�N�{�b�N�XON�EOFF
//========================================================================================
function funEnterChk(row){

	if (frm.che[row].checked) {
		frm.che[row].checked==false;
	} else {
		frm.che[row].checked==true;
	}
}



//========================================================================================
//XML�t�@�C���ɏ�������
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/12
//����  �F�@XmlId  �FXMLID
//    �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//    �F�BMode   �F�������[�h
//        1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
//�߂�l�F�Ȃ�
//�T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode, row, no) {

 var frm = document.frm00;    //̫�тւ̎Q��
 var i;

 for (i = 0; i < reqAry.length; i++) {
     //��ʏ����\��
     if (XmlId.toString() == "RGENAAA") {
    	//USERINFO
         funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
         funXmlWrite(reqAry[i], "id_user", "", 0);

     // �����{�^������
     } else if (XmlId.toString() == "RGEN3580"){
         switch (i) {
             case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0);
                 break;
             case 1:    //SA890
            	// �J�e�S���ɔ������ݒ�
                 funXmlWrite(reqAry[i], "cd_category", 'C_hattyuusaki', 0);
                 funXmlWrite(reqAry[i], "cd_literal", frm.cdhattyu.value, 0);
                 funXmlWrite(reqAry[i], "nm_literal", frm.nmhattyu.value, 0);
                 break;
         }

     //�o�^
     }  else if (XmlId.toString() == "RGEN3630"){

    	 ;
         switch (i) {
             case 0:    //USERINFO
                 funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                 funXmlWrite(reqAry[i], "id_user", "", 0);
                 break;
             case 1:    //RGEN3630
            	 var gyouCnt = 0;
            	 for (var y = 0; y < row; y++) {
            			//var checkbox = document.getElementById("check-" + y);
            			var updateFlg = document.getElementById("update_flgl-" + y);

//            		 if (checkbox.checked) {
//     		            if(y != 0){
//    		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
//    		            }
//
//    	        		 // �����敪�F3
//            			 funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 3, gyouCnt);
//    	        		 // ������R�[�h
//            			 funXmlWrite_Tbl(reqAry[i], "table", "cd_literal", document.getElementById("cd_literal-" + y).value, gyouCnt);
//    	        		 // �����於
//            			 funXmlWrite_Tbl(reqAry[i], "table", "nm_literal", document.getElementById("nm_literal-" + y).value, gyouCnt);
//    	                 // �����於�\����
//            			 funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
//    	        		 // �S����
//            			 funXmlWrite_Tbl(reqAry[i], "table", "cd_2nd_literal", document.getElementById("hiddenCd_2nd_literal-" + y).value, gyouCnt);
//    	                 // �S���ҏ�
//            			 funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
//    	        		 // ���[���A�h���X
//            			 funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
//    	        		 // ���g�p
//    	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
//    	                 if (flg_mishiyo.checked) {
//    	                	 funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1, gyouCnt);
//    	                 } else {
//    	                	 funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 0, gyouCnt);
//    	                 }
//
//    	                 gyouCnt++;

            		// }

            		 if (updateFlg.value == 1) {
            				// �o�^
            				if (funHattyuusakiDifferenceCheck(y)) {
            		            if(gyouCnt != 0){
            		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
            		            }

	           	        		 // �����敪�F1
            					funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 1, gyouCnt);
            					// ���הԍ�
            					funXmlWrite_Tbl(reqAry[i], "table", "meisaiNum", y, gyouCnt);
	           	        		 // ������R�[�h
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_literal", document.getElementById("cd_literal-" + y).value, gyouCnt);
            					var table = document.getElementById("tblList");
            					var nmLiteralValue = table.rows.item(y).cells(1).innerText;
	           	        		 // �����於
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_literal",nmLiteralValue, gyouCnt);
	           	                 // �����於�\����
            					funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
	           	        		 // �S����
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_2nd_literal", document.getElementById("nm_2nd_literal-" + y).value, gyouCnt);
	           	                 // �S���ҏ�
            					funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
	           	        		 // ���[���A�h���X
            					funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
	           	        		 // ���g�p
	           	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
	           	                 if (flg_mishiyo.checked) {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 2,  gyouCnt);
	           	                 } else {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1,  gyouCnt);
	           	                 }
            				} else {
            					// �G���[����
            					funErrorMsgBox("������R�[�h�������ꍇ�A�ʂ̔����於�A������\�����͓��͂ł��܂���B");
            					return false;

            				}

            				 gyouCnt++;

            		 } else if (updateFlg.value == 2) {

            				// �X�V
            				if (funHattyuusakiDifferenceCheck(y)) {
            		            if(gyouCnt != 0){
            		            	funAddRecNode_Tbl(reqAry[i], "FGEN3630", "table");
            		            }
	           	        		 // �����敪�F2
            					funXmlWrite_Tbl(reqAry[i], "table", "shoriKbn", 2, gyouCnt);
            					// ���הԍ�
               					funXmlWrite_Tbl(reqAry[i], "table", "meisaiNum", y, gyouCnt);
	           	        		 // ������R�[�h
            					funXmlWrite_Tbl(reqAry[i], "table", "hiddenCd_literal", document.getElementById("hiddenCd_literal-" + y).value, gyouCnt);
	           	        		 // ������R�[�h
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_literal",document.getElementById("cd_literal-" + y).value, gyouCnt);

            					var table = document.getElementById("tblList");
            					var nmLiteralValue = table.rows.item(y).cells(1).innerText;
	           	        		 // �����於
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_literal",nmLiteralValue, gyouCnt);
	           	                 // �����於�\����
            					funXmlWrite_Tbl(reqAry[i], "table", "no_sort", document.getElementById("no_sort-" + y).value, gyouCnt);
	           	        		 // �S���҃R�[�h
            					funXmlWrite_Tbl(reqAry[i], "table", "cd_2nd_literal", document.getElementById("hiddenCd_2nd_literal-" + y).value, gyouCnt);
	           	        		 // �S����
            					funXmlWrite_Tbl(reqAry[i], "table", "nm_2nd_literal", document.getElementById("nm_2nd_literal-" + y).value, gyouCnt);
	           	                 // �S���ҏ�
            					funXmlWrite_Tbl(reqAry[i], "table", "no_2nd_sort", document.getElementById("no_2nd_sort-" + y).value, gyouCnt);
	           	        		 // ���[���A�h���X
            					funXmlWrite_Tbl(reqAry[i], "table", "mail_address", document.getElementById("mail_address-" + y).value, gyouCnt);
	           	        		 // ���g�p
	           	                 var flg_mishiyo = document.getElementById("flg_mishiyo-" + y);
	           	                 if (flg_mishiyo.checked) {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 2, gyouCnt);
	           	                 } else {
	           	                	funXmlWrite_Tbl(reqAry[i], "table", "flg_mishiyo", 1, gyouCnt);
	           	                 }
            				} else {
            					// �G���[����
            					funErrorMsgBox("������R�[�h�������ꍇ�A�ʂ̔����於�A������\�����͓��͂ł��܂���B");
            					return false;
            				}

            				 gyouCnt++;
            		 }


            	 }
            	 // �o�^�A�X�V�f�[�^���Ȃ��ꍇ���b�Z�[�W�\��
            	 if (gyouCnt == 0) {
            		 funClearRunMessage();
            		 funErrorMsgBox("���׃f�[�^�͕ύX����Ă��܂���B");
            		 return false;
            	 }
            	 break;
         }


     } else if (XmlId.toString() == "RGEN3670"){

         switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
             funXmlWrite(reqAry[i], "id_user", "", 0);
             break;
         case 1:
        	//RGEN3670
             funXmlWrite(reqAry[i], "cdhattyu", document.getElementById("cdhattyu").value, 0);
             funXmlWrite(reqAry[i], "nmhattyu", document.getElementById("nmhattyu").value, 0);

             break;
         }
     }
     // ������R�[�h�ݒ�
     else if (XmlId.toString() == "RGEN3650"){

         switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
             funXmlWrite(reqAry[i], "id_user", "", 0);
             break;
         case 1:
        	//RGEN3650
       		 // ������R�[�h
        	 funXmlWrite(reqAry[i], "cd_literal",document.getElementById("cd_literal-" + no).value, 0);

             break;
         }
     }
 }

 return true;

}

//========================================================================================
//�N���A����
//�쐬�ҁFt2nakamura
//�쐬���F2016/09/15
//����  �F�Ȃ�
//�T�v  �F�u�s�ǉ��v�`�u�폜�v�{�^����񊈐��ɂ���
//========================================================================================
function funDisBtn() {

	var frm = document.frm00;    //̫�тւ̎Q��

	frm.btnInsert.disabled = true;
	frm.btnExcel.disabled = true;
	frm.btnExcel.disabled = true;
	frm.btnLineAdd.disabled = true;
}

//========================================================================================
//�ꗗ�I������
//�쐬�ҁFH.Shima
//�쐬���F2014/9/12
//����  �F�C���f�b�N�X
//�T�v  �F�I���s���n�C���C�g
//========================================================================================
function clickItiran(row){
 funSetCurrentRow(row);
}
