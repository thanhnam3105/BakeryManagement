
//========================================================================================
// ���ʕϐ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
//�@�\ID�A�f�[�^�Q��ID��ێ�
var hidKinoId = "";
var hidDataId = "";
// �yQP@10713�z2011.10.27 ADD Start hisahori
var hidUserId = "";
// �yQP@10713�z2011.10.27 ADD End
var flg_kenkyu = "";
var flg_seikan = "";
var flg_gentyo = "";
var flg_kojo = "";
var flg_eigyo = "";
var flg_tab="0";
//***** ADD�yH24�N�x�Ή��z20120416 hagiwara S **********
var search_cnt = 0;
//***** ADD�yH24�N�x�Ή��z20120416 hagiwara E **********


//========================================================================================
// �yQP@00342�z�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

	var frm = document.frm00;    //̫�тւ̎Q��

    //��ʐݒ�
    funInitScreen(ConGenkaListId);

    //�߰�ނ̐ݒ�
    funSetCurrentPage(1);

    //�^�u�ݒ�
    document.getElementById('todo').style.backgroundColor='#8380F5';
	document.getElementById('todo').style.color='#ffffff';
	//frm.chkKanryo.disabled = true;

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    //funClear();

    return true;

}

//========================================================================================
// �yQP@00342�z�^�u�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
//========================================================================================
function todo_click(){

	var frm = document.frm00;    //̫�тւ̎Q��

	//�^�u�ݒ�
	document.getElementById('todo').style.backgroundColor='#8380F5';
	document.getElementById('itiran').style.backgroundColor='#ffffff';
	document.getElementById('todo').style.color='#ffffff';
	document.getElementById('itiran').style.color='#777777';

	//�߰�ނ̐ݒ�
    funSetCurrentPage(1);

	//���������ݒ�
	flg_tab="0";
	//frm.chkKanryo.checked = false;
	//frm.chkKanryo.disabled = true;

}
function itiran_click(){

	var frm = document.frm00;    //̫�тւ̎Q��

	//�^�u�ݒ�
	document.getElementById('itiran').style.backgroundColor='#8380F5';
	document.getElementById('todo').style.backgroundColor='#ffffff';
	document.getElementById('itiran').style.color='#ffffff';
	document.getElementById('todo').style.color='#777777';

	//�߰�ނ̐ݒ�
    funSetCurrentPage(1);

	//���������ݒ�
	flg_tab="1";
	if(frm.chkMinyuryoku.checked == false){
			frm.chkKanryo.disabled = false;
	}


}

//========================================================================================
// �yQP@00342�z�����̓`�F�b�N�{�b�N�X�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
//========================================================================================
function minyuryoku_click(){

	var frm = document.frm00;    //̫�тւ̎Q��

	//�����͑I����
	if(frm.chkMinyuryoku.checked == true){
		frm.chkKanryo.checked = false;
		frm.chkKanryo.disabled = true;

		frm.txtKizitu_From.value="";
		frm.txtKizitu_From.disabled=true;
		frm.txtKizitu_From.style.backgroundColor="#cccccc";

		frm.txtKizitu_To.value="";
		frm.txtKizitu_To.disabled=true;
		frm.txtKizitu_To.style.backgroundColor="#cccccc";
	}
	//�����͖��I����
	else{
		frm.chkKanryo.disabled = false;

		frm.txtKizitu_From.disabled=false;
		frm.txtKizitu_From.style.backgroundColor="#ffffff";

		frm.txtKizitu_To.disabled=false;
		frm.txtKizitu_To.style.backgroundColor="#ffffff";

	}
}

//========================================================================================
// �yQP@00342�z��ʏ��擾����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2120";
    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD Start
//    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA080","SA140","SA290","FGEN2140","FGEN2130");
//    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA080I,xmlSA140I,xmlSA290I,xmlFGEN2140I,xmlFGEN2130I);
//    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA080O,xmlSA140O,xmlSA290O,xmlFGEN2140O,xmlFGEN2130O);
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA030","SA050","SA080","SA140","SA290","FGEN2140","FGEN2130","SA250");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA030I,xmlSA050I,xmlSA080I,xmlSA140I,xmlSA290I,xmlFGEN2140I,xmlFGEN2130I,xmlSA250I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA030O,xmlSA050O,xmlSA080O,xmlSA140O,xmlSA290O,xmlFGEN2140O,xmlFGEN2130O,xmlSA250O);
    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD End
    //*******
    //2015/03/30 TT.Kitazawa�yQP@40812�zNo.24  xmlResAry[9]�F ���g�p�i�S���҂̎擾�j
    // cd_kaisha="" �Ō����ׁ̈A�O���擾
    //*******

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2120, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

// �yQP@10713�z2011.10.27 ADD Start hisahori
        var tmpUserID;
        var i;
        tmpUserID = funXmlRead(xmlResAry[1], "id_user", 0);
	    for (i = tmpUserID.length; i < 10; i++) {
	        tmpUserID = "0" + tmpUserID;
	    }
        hidUserId = tmpUserID;
// �yQP@10713�z2011.10.27 ADD End

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬--------------------------------------------------------------------------
    //���[�U�R���{�{�b�N�X
    funCreateComboBox(frm.ddlUser, xmlResAry[2], 4, 2);

    // DEL 2013/10/22 QP@30154 okano start
    //�O���[�v�E�`�[���R���{�{�b�N�X
    //�������u����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�v�̏ꍇ
//	    if(hidDataId == "1"){
//		    funCreateComboBox(frm.ddlGroup, xmlResAry[3], 1, 1);
//	    	funCreateComboBox(frm.ddlTeam, xmlResAry[4], 2, 2);
//	    	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
//	    	funCreateComboBox(frm.ddlTanto, xmlResAry[9], 3 ,2);
//	    	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
//
//	    	//�����ޯ���̍Đݒ�
//		    funDefaultIndex(frm.ddlGroup, 1);
//		    funDefaultIndex(frm.ddlTeam, 2);
//		    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
//		    funDefaultIndex(frm.ddlTanto, 3);
//		    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
//	// �yQP@10713�z2011.10.27 ADD Start hisahori
//	        frm.txtShisakuNo.value = hidUserId;
//	// �yQP@10713�z2011.10.27 ADD End
//
//	    }
//	    //�������u����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�v�ȊO�̏ꍇ
//	    else{
    // DEL 2013/10/22 QP@30154 okano end

    	// ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 start
        // ���S���Ҍ����p�F�I���O���[�v�̉��CD��n���ׁA��ٰ�ߏ���ޔ�
    	xmlSA050.load(xmlResAry[3]);
    	// ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 end
    	funCreateComboBox(frm.ddlGroup, xmlResAry[3], 1, 2);
	    funClearSelect(frm.ddlTeam, 2);
	    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
	    funClearSelect(frm.ddlTanto, 2);
	    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
	// DEL 2013/10/22 QP@30154 okano start
//    	}
    // DEL 2013/10/22 QP@30154 okano end

    //��ЁE�����R���{�{�b�N�X
    //�������u�����H��̂݁v�̏ꍇ
    if(hidDataId == "2"){
    	funCreateComboBox(frm.ddlKaisha, xmlResAry[5], 5, 1);
    	funCreateComboBox(frm.ddlBusho, xmlResAry[6], 6, 1);
    }
    //�������u�����H��̂݁v�ȊO�̏ꍇ
    else{
    	funCreateComboBox(frm.ddlKaisha, xmlResAry[5], 5, 2);
    	funClearSelect(frm.ddlBusho, 2);
    }

    //�}�Ԏ�ރR���{�{�b�N�X
    funCreateComboBox(frm.ddlShurui, xmlResAry[7], 4, 2);


    //���������t���O�ݒ�------------------------------------------------------------------------
    var flg_kenkyu = funXmlRead_3(xmlResAry[8], "table", "flg_kenkyu", 0, 0);
	var flg_seikan = funXmlRead_3(xmlResAry[8], "table", "flg_seikan", 0, 0);
	var flg_gentyo = funXmlRead_3(xmlResAry[8], "table", "flg_gentyo", 0, 0);
	var flg_kojo = funXmlRead_3(xmlResAry[8], "table", "flg_kojo", 0, 0);
	var flg_eigyo = funXmlRead_3(xmlResAry[8], "table", "flg_eigyo", 0, 0);


	//�󋵕����R���{�{�b�N�X����-----------------------------------------------------------------
	funClearSelect(frm.ddlZyokyo, 2);
	var obj = frm.ddlZyokyo;
	// DEL 2013/8/7 okano�yQP@30151�zNo.12 start
//	//������
//	if( flg_kenkyu == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "������";
//    	objNewOption.value = "1";
//
//	}
//	//���Y�Ǘ���
//	else if( flg_seikan == "1" ){
	// DEL 2013/8/7 okano�yQP@30151�zNo.12 end
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "������";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "���Y�Ǘ���";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "�����ޒ��B��";
    	objNewOption3.value = "3";

    	var objNewOption4 = document.createElement("option");
    	obj.options.add(objNewOption4);
    	objNewOption4.innerText = "�H��";
    	objNewOption4.value = "4";

    	var objNewOption5 = document.createElement("option");
    	obj.options.add(objNewOption5);
    	objNewOption5.innerText = "�c��";
    	objNewOption5.value = "5";

	// DEL 2013/8/7 okano�yQP@30151�zNo.12 start
//	}
//	//�����ޒ��B��
//	else if( flg_gentyo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "�����ޒ��B��";
//    	objNewOption.value = "3";
//
//	}
//    //�H��
//	else if( flg_kojo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "�H��";
//    	objNewOption.value = "4";
//
//	}
//	//�c��
//	else if( flg_eigyo == "1" ){
//		var objNewOption = document.createElement("option");
//	    obj.options.add(objNewOption);
//    	objNewOption.innerText = "�c��";
//    	objNewOption.value = "5";
//
//	}
	// DEL 2013/8/7 okano�yQP@30151�zNo.12 end

	//�X�e�[�^�X�R���{�{�b�N�X
	funClearSelect(frm.ddlStatus, 2);

    return true;

}

//========================================================================================
// �����`�[���R���{�{�b�N�X�A������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�Ȃ�
// �T�v  �F�����O���[�v�ɕR�t�������`�[���R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeGroup() {

	// DEL 2013/10/22 QP@30154 okano start
//		//�������u����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�v�̏ꍇ
//	    if(hidDataId == "1"){
//	    	//�������Ȃ�
//	    	//return true;
//	    }
	// DEL 2013/10/22 QP@30154 okano end

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2130";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA080O);

    if (frm.ddlGroup.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlTeam, 2);
        //�yQP@20505�zNo.31 2012/09/20 TT H.Shima ADD Start
        funClearSelect(frm.ddlTanto, 2);
        //�yQP@20505�zNo.31 2012/09/20 TT H.Shima ADD End
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }


    //��я����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2130, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlTeam, xmlResAry[2], 2 , 2);
    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
    funClearSelect(frm.ddlTanto, 2);
    //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End

    return true;

}

//========================================================================================
// ������ЃR���{�{�b�N�X�A������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�Ȃ�
// �T�v  �F������ЂɕR�t�������H��R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKaisha() {

	//�������u���H�ꕪ�̂݁v�̏ꍇ
    if(hidDataId == "2"){
    	//�������Ȃ�
    	return true;
    }

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2140";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA290O);

    if (frm.ddlKaisha.selectedIndex == 0) {
        //�����ޯ���̸ر
        funClearSelect(frm.ddlBusho, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }


    //��я����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2140, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 6 , 2);

    return true;

}

//========================================================================================
//�S���҃R���{�{�b�N�X�A������
//�쐬�ҁFH.Shima
//�쐬���F2012/09/13
//����  �F�Ȃ�
//�T�v  �F�����O���[�v�A�����`�[���ɕR�t���S���҃R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeTeam() {

  var frm = document.frm00;    //̫�тւ̎Q��
  var XmlId = "JSP0320";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"SA250");
  var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA250I);
  var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA250O);

  if (frm.ddlTeam.selectedIndex == 0) {
      //�����ޯ���̸ر
      funClearSelect(frm.ddlTanto, 2);
      return true;
  }

  //������XMĻ�قɐݒ�
  if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
      return false;
  }

  //�S���ҏ����擾
  if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0320, xmlReqAry, xmlResAry, 1) == false) {
      return false;
  }

  //�����ޯ���̍쐬
  funCreateComboBox(frm.ddlTanto, xmlResAry[2], 3, 2);

  return true;

}

//========================================================================================
// �󋵕����R���{�{�b�N�X�A������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�Ȃ�
// �T�v  �F�󋵕����ɕR�t���X�e�[�^�X�R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeZyokyo() {

    var frm = document.frm00;    //̫�тւ̎Q��

	//�����ޯ���̸ر
    funClearSelect(frm.ddlStatus, 2);

    //�󔒑I����
    if (frm.ddlZyokyo.selectedIndex == 0) {
        return true;
    }

    //�I�����擾
    var zyokyo = frm.ddlZyokyo.options[frm.ddlZyokyo.selectedIndex].value;

	//�R���{�{�b�N�X����
	var obj = frm.ddlStatus;
	if(zyokyo == "1"){
		//���������I������Ă���ꍇ
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "���Z�˗�";
    	objNewOption1.value = "2";

	}
	else if(zyokyo == "2"){
		//���Y�Ǘ������I������Ă���ꍇ
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "�Ȃ�";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "���Z�˗�";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "�m�F����";
    	objNewOption3.value = "3";

	}
	else if(zyokyo == "3"){
		//�����ޒ��B�����I������Ă���ꍇ
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "�Ȃ�";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "�m�F����";
    	objNewOption2.value = "2";

	}
	else if(zyokyo == "4"){
		//�H�ꂪ�I������Ă���ꍇ
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "�Ȃ�";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "�m�F����";
    	objNewOption2.value = "2";

	}
	else if(zyokyo == "5"){
		//�c�Ƃ��I������Ă���ꍇ
		var objNewOption1 = document.createElement("option");
	    obj.options.add(objNewOption1);
    	objNewOption1.innerText = "�Ȃ�";
    	objNewOption1.value = "1";

    	var objNewOption2 = document.createElement("option");
    	obj.options.add(objNewOption2);
    	objNewOption2.innerText = "���Z�˗�";
    	objNewOption2.value = "2";

    	var objNewOption3 = document.createElement("option");
    	obj.options.add(objNewOption3);
    	objNewOption3.innerText = "�m�F����";
    	objNewOption3.value = "3";

    	var objNewOption4 = document.createElement("option");
    	obj.options.add(objNewOption4);
    	objNewOption4.innerText = "�̗p�L������";
    	objNewOption4.value = "4";

	}

    return true;

}



//========================================================================================
// ��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�Ȃ�
// �T�v  �FXML�f�[�^�擾��HTML�I�u�W�F�N�g�����i�ċA�����j���ꗗ�\��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2150";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2150");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2150I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2150O);
    var RecCnt;
    var PageCnt;
    var ListMaxRow;

    //***** ADD�yH24�N�x�Ή��z20120411 hagiwara S **********
    search_cnt = 0;
    //***** ADD�yH24�N�x�Ή��z20120411 hagiwara E **********


    //������ү���ޕ\��
	showWin_mati();

	//�I���s�̏�����
	funSetCurrentRow("");

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }

    //***** ADD�yH24�N�x�Ή��z20120411 hagiwara S **********
    // ����p��xmlFGEN2210I�ɂ��������c��
    if (funReadyOutput(XmlId, [xmlUSERINFO_I, xmlFGEN2210I], 1) == false) {
        // �ꗗ�̸ر
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }
    //***** ADD�yH24�N�x�Ή��z20120411 hagiwara E **********

    //���������Ɉ�v���鎎���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2150, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        setTimeout(function(){ closeWin_mati(); },0);
        return false;
    }

    //�\�����Ɍ����n�A�N�V�����𑀍�s��
    document.getElementById("todo").disabled = true;
    document.getElementById("itiran").disabled = true;
    document.getElementById("btnSearch").disabled = true;

    //�ꗗ�̸ر
    funClearList_search();


    //�擾�ް��̐ݒ�
    //HTML�o�̓I�u�W�F�N�g�ݒ�
    var obj = document.getElementById("divMeisai");
    var roop_cnt = funXmlRead(xmlResAry[2], "roop_cnt", 0);
    //***** ADD�yH24�N�x�Ή��z20120416 hagiwara S **********
    // �����������p�Ɍ������ʑ������i�[
    search_cnt = funXmlRead(xmlFGEN2150O, "roop_cnt", 0);
    //***** ADD�yH24�N�x�Ή��z20120416 hagiwara E **********
    var i;
    var tableNm = "table";
    var output_html = "";
    output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

    //�ċA�����i�[���}���`�X���b�h�j
    //�����҂��E�B���h�E��GIF�A�j�����~�܂�Ȃ��悤�ɂ���ׁA�ċA�������ɂ�
    //setTimeout��p���āA��ʂɏ�����Ԃ�
    DataSet( xmlResAry ,output_html , 0 , roop_cnt);

    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "�^" + PageCnt + "�y�[�W";

    FuncIdAry = null;
    xmlReqAry = null;

    return true;

}
function DataSet( xmlResAry ,html , i , cnt){
	if(i < cnt){
		var tableNm = "table";

		//���X�|���X�f�[�^�擾-------------------------------------------------------------------------------
    	var no_row = funXmlRead_3(xmlResAry[2], tableNm, "no_row", 0, i);
    	var hi_kizitu = funSetNbsp_kizitu(funXmlRead_3(xmlResAry[2], tableNm, "hi_kizitu", 0, i));
    	var cnt_irai = funXmlRead_3(xmlResAry[2], tableNm, "cnt_irai", 0, i);
    	var no_shisaku = funXmlRead_3(xmlResAry[2], tableNm, "no_shisaku", 0, i);
    	var no_eda = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);
    	var nm_shisaku = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_shisaku", 0, i));
    	var nm_shurui = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shurui", 0, i));
    	var no_iraisample = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "no_iraisample", 0, i)));
    	var st_kenkyu = funXmlRead_3(xmlResAry[2], tableNm, "st_kenkyu", 0, i);
    	var st_seikan = funXmlRead_3(xmlResAry[2], tableNm, "st_seikan", 0, i);
    	var st_gentyo = funXmlRead_3(xmlResAry[2], tableNm, "st_gentyo", 0, i);
    	var st_kojo = funXmlRead_3(xmlResAry[2], tableNm, "st_kojo", 0, i);
    	var st_eigyo = funXmlRead_3(xmlResAry[2], tableNm, "st_eigyo", 0, i);
    	var cd_saiyou = funXmlRead_3(xmlResAry[2], tableNm, "cd_saiyou", 0, i);
    	var no_saiyou = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "no_saiyou", 0, i)));
    	var nm_team = funXmlRead_3(xmlResAry[2], tableNm, "nm_team", 0, i);
    	var nm_liuser = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_liuser", 0, i));
    	var nm_kaisha_seizo = funXmlRead_3(xmlResAry[2], tableNm, "nm_kaisha_seizo", 0, i);
    	var nm_kaisha_busho = funXmlRead_3(xmlResAry[2], tableNm, "nm_kaisha_busho", 0, i);
    	var memo_eigyo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "memo_eigyo", 0, i));
    	// �yQP@10713�z2011.10.28 ADD start hisahori
    	var cd_nisugata = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "cd_nisugata", 0, i)));
    	var nm_ondo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_ondo", 0, i));
    	var nm_tantoeigyo = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantoeigyo", 0, i));
    	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD Start
//    	var nm_tantosha = funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantosha", 0, i));
    	var nm_tantosha = funSetNbsp(funEscape(funXmlRead_3(xmlResAry[2], tableNm, "nm_tantosha", 0, i)));
    	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD End
    	// �yQP@10713�z2011.10.28 ADD end

		//HTML�o�̓I�u�W�F�N�g�ݒ�---------------------------------------------------------------------------
		//TR�s�J�n
		var output_html = "";
		output_html = output_html + "<tr class=\"disprow\">";

		/*�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD �S���җ�ǉ��ɂ��T�C�Y�̒��� Start */
		//�sNo
//		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"28\"  align=\"right\" >" +
				"<div id=\"no_row_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_row + "</div></td>";

		//�I�����W�I�{�^��
//		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"center\">" +
		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"center\">" +
				"<input type=\"radio\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" value=\"" + i + "\" tabindex=\"-1\"></td>";

		//���Z����
//		output_html = output_html + "    <td class=\"column\" width=\"80\"  align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"79\"  align=\"left\"  >" +
				"<div id=\"hi_kizitu_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + hi_kizitu +"</div></td>";

		//�˗���
//		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"30\"  align=\"right\" >" +
				"<div id=\"cnt_irai_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + cnt_irai + "</div></td>";

		//����No
//		output_html = output_html + "    <td class=\"column\" width=\"130\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"125\" align=\"left\"  >" +
				"<div id=\"no_shisaku_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_shisaku + "</div></td>";

		//�}�ԍ�
//		output_html = output_html + "    <td class=\"column\" width=\"31\"  align=\"right\" >" +
		output_html = output_html + "    <td class=\"column\" width=\"32\"  align=\"right\" >" +
				"<div id=\"no_eda_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + no_eda + "</div></td>";

		//���얼
//		output_html = output_html + "    <td class=\"column\" width=\"193\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"188\" align=\"left\"  >" +
				"<input type=\"text\" id=\"nm_shisaku_" + i + "\" style=\"background-color:transparent;width:190;border-width:0px;text-align:left;\" readonly value=\"" + nm_shisaku + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//�}�Ԏ��
//		output_html = output_html + "    <td class=\"column\" width=\"41\"  align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"42\"  align=\"left\"  >" +
				"<div id=\"nm_shurui_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_shurui + "</div></td>";

		//���Z�˗������No
//		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"96\" align=\"left\"  >" +
//				"<input type=\"text\" id=\"no_iraisample_" + i + "\" style=\"background-color:transparent;width:99;border-width:0px;text-align:left;\" readonly value=\"" + no_iraisample + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";
		"<input type=\"text\" id=\"no_iraisample_" + i + "\" style=\"background-color:transparent;width:96;border-width:0px;text-align:left;\" readonly value=\"" + no_iraisample + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//�������X�e�[�^�X
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_kenkyu_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("1",st_kenkyu) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("1",st_kenkyu) + "\"></td>";

		//���Y�Ǘ����X�e�[�^�X
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_seikan_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("2",st_seikan) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("2",st_seikan) + "\"></td>";

		//�����ޒ��B���X�e�[�^�X
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_gentyo_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("3",st_gentyo) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("3",st_gentyo) + "\"></td>";

		//�H��X�e�[�^�X
		//output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  >" +
		//		"<div id=\"st_kojo_" + i + "\" onClick=\"openWin(" + i + ")\">" + funStatusSetting("4",st_kojo) + "</div></td>";

		output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("4",st_kojo) + "\"></td>";

		//�c�ƃX�e�[�^�X
		if(st_eigyo == 4){
			//�s�̗p
			if(cd_saiyou == "-1"){
				output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/husaiyo.GIF\"></td>";
			}
			else{
				output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("5",st_eigyo) + "\"></td>";
			}
		}
		else{
			output_html = output_html + "    <td class=\"column\" width=\"50\"  align=\"center\"  ><img src=\"../image/" + funStatusSetting_img("5",st_eigyo) + "\"></td>";
		}

		//�̗p�L��
//		output_html = output_html + "    <td class=\"column\" width=\"95\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<input type=\"text\" id=\"no_saiyou_" + i + "\" style=\"background-color:transparent;width:95;border-width:0px;text-align:left;\" readonly value=\"" + no_saiyou + "\" onDblClick=\"openWin(" + i + ")\" tabindex=\"-1\" tabindex=\"-1\" /></td>";

		//�`�[����
//		output_html = output_html + "    <td class=\"column\" width=\"101\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"99\" align=\"left\"  >" +
				"<div id=\"nm_team_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_team + "</div></td>";

		//�S���Җ�
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<div id=\"nm_liuser_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_tantosha + "</div></td>";

		//���[�U��
//		output_html = output_html + "    <td class=\"column\" width=\"199\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"191\" align=\"left\"  >" +
				"<div id=\"nm_liuser_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_liuser + "</div></td>";

		//�������
//		output_html = output_html + "    <td class=\"column\" width=\"100\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"97\" align=\"left\"  >" +
				"<div id=\"nm_kaisha_seizo_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_kaisha_seizo + "</div></td>";

		//�����H��
//		output_html = output_html + "    <td class=\"column\" width=\"100\" align=\"left\"  >" +
		output_html = output_html + "    <td class=\"column\" width=\"96\" align=\"left\"  >" +
				"<div id=\"nm_kaisha_busho_" + i + "\" onDblClick=\"openWin(" + i + ")\">" + nm_kaisha_busho + "</div></td>";

		/*�yQP@20505�zNo.31 2012/09/14 TT H.Shima MOD �S���җ�ǉ��ɂ��T�C�Y�̒��� End   */

		//�������Z�����i�c�ƘA���p�j
		output_html = output_html + "<input type=\"hidden\" id=\"memo_eigyo_" + i + "\" value=\"" + memo_eigyo + "\">";

		// �yQP@10713�z2011.10.28 ADD start hisahori
		//�׎p
		output_html = output_html + "<input type=\"hidden\" id=\"cd_nisugata_" + i + "\" value=\"" + cd_nisugata + "\">";
		//�戵���x
		output_html = output_html + "<input type=\"hidden\" id=\"nm_ondo_" + i + "\" value=\"" + nm_ondo + "\">";
		//�����H��
		output_html = output_html + "<input type=\"hidden\" id=\"nm_kaisha_busho2_" + i + "\" value=\"" + nm_kaisha_busho + "\">";
		//�S���c��
		output_html = output_html + "<input type=\"hidden\" id=\"nm_tantoeigyo" + i + "\" value=\"" + nm_tantoeigyo + "\">";
		//�������S���ҁi����No�擪10��ID�̃��[�U���j
		output_html = output_html + "<input type=\"hidden\" id=\"nm_tantosha" + i + "\" value=\"" + nm_tantosha + "\">";
		// �yQP@10713�z2011.10.28 ADD end

		//�̗p�����No�i����SEQ�j
		output_html = output_html + "<input type=\"hidden\" id=\"cd_saiyou_" + i + "\" value=\"" + cd_saiyou + "\">";

		//TR�s��
		output_html = output_html + "</tr>";
		html = html + output_html;

		//�ċA�����i���f�[�^��HTML�����j
		setTimeout(function(){ DataSet( xmlResAry , html , (i+1) , cnt ); }, 0);
	}
	else{
		//�ꗗ����HTML�ݒ�
		var obj = document.getElementById("divMeisai");
		html = html + "</table>";
		obj.innerHTML = html;

		//�����҂��E�B���h�E��\��
		closeWin_mati();

		//�\���I����Ɍ����n�A�N�V�����𑀍�\
	    document.getElementById("todo").disabled = false;
	    document.getElementById("itiran").disabled = false;
	    document.getElementById("btnSearch").disabled = false;

		xmlResAry = null;
		html = null;

		//�����I��
		return true;
	}
}
function funClearList_search() {

    //�ꗗ�̸ر
    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
    spnRecInfo.style.display = "none";

}

//========================================================================================
// �ꗗ�I������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/2/1
// ����  �F�Ȃ�
//========================================================================================
function clickItiran(row){
	funChangeSelectRowColor();
}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/2/1
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// �����҂��E�B���h�E����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/02/1
//========================================================================================
var win_mati; //�T�u�E�B���h�E�I�u�W�F�N�g�i�������Z��ʓ��@�O���[�o���ϐ��j
function openWin_mati(){

    //�T�u�E�B���h�E����������Ă��Ȃ��ꍇ
    if(win_mati == null){

        //�T�u�E�B���h�E��HTML���L�q
        outputHtml = "";
        outputHtml = outputHtml + "<table cellpadding=\"0\" width=\"180\" height=\"80\" cellspacing=\"0\" border=\"0\">";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td valign=\"middle\" align=\"center\" style=\"font-size:17pt\"><img src=\"../image/loading.gif\">�������D�D�D</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //�T�u�E�B���h�E����
        win_mati = new Window("win02", {
        			title:"�ꗗ��`�撆"
                    ,className: "alphacube"
                    ,top:(window.document.body.clientHeight - 80) / 2
                    ,left:(window.document.body.clientWidth - 180) / 2
                    ,width:180
                    ,height:80
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
                    ,closable:false
              });

        //�T�u�E�B���h�E����HTML��ݒ�
        win_mati.setHTMLContent(outputHtml);
    }
}

//========================================================================================
// �����҂��E�B���h�E�\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/02/1
//========================================================================================
function showWin_mati(){
	try{
    	//�T�u�E�B���h�E��\��
        win_mati.show();
    }
    catch (e) {
    }
}

//========================================================================================
// �����҂��E�B���h�E��\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/02/1
//========================================================================================
function closeWin_mati(){
	try{
    	//�\������Ă���T�u�E�B���h�E��j���icatch�Ώہj
        win_mati.destroy();
        //�T�u�E�B���h�E��������
        win_mati = null;
        //������
        openWin_mati();
    }
    catch (e) {
        //�T�u�E�B���h�E��������
        win_mati = null;
        //������
        openWin_mati();
    }
}

//========================================================================================
// �����ڍ׏��\�� ����m�F
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/02/12
// ����  �FgyoNo(W�N���b�N���ꂽ�s�ԍ�)
// �߂�l�F�Ȃ�
// �T�v  �F�����ڍ׏��\��
//========================================================================================
var win; //�T�u�E�B���h�E�I�u�W�F�N�g�i�������Z��ʓ��@�O���[�o���ϐ��j
var subwinShowBln;		//�T�u�E�B���h�E�\���t���O�i�������Z��ʓ��@�O���[�o���ϐ��j
function openWin(wclickgyoNo){

    //����������
    //�@�T�u�E�B���h�E�����̂Ɠ����ɁA�f�����ēx�\�����s�����ꍇ�ɃG���[����������
    //�@�G���[������̓T�u�E�B���h�E���\������Ȃ��Ȃ�
    //�@�΍�Ƃ��āA�����s�̘A�Ŗh�~�@etc

    //�T�u�E�B���h�E����������Ă��Ȃ��ꍇ
    if(win == null){

        //�T�u�E�B���h�E��HTML
        var outputHtml;

        var detailDoc = document;		//�����ڰт�Document�Q��

        var no_shisaku;		//����No
        var no_eda;			//�}��
        var nm_shisaku;	//���얼
        var no_iraisample;	//�˗������No
        var no_saiyou;		//�̗p�����No
        // �yQP@10713�z2011.10.28 ADD start hisahori
        var cd_nisugata;
        cd_nisugata = detailDoc.getElementById("cd_nisugata_" + wclickgyoNo);
        // �yQP@10713�z2011.10.28 ADD end
        var memo_eigyo;	//�������Z�����i�c�ƘA���p�j
        var subwinOnCloseEv;	//�T�u�E�B���h�E�N���[�Y���C�x���g
        no_shisaku = detailDoc.getElementById("no_shisaku_" + wclickgyoNo);
        nm_shisaku = detailDoc.getElementById("nm_shisaku_" + wclickgyoNo);
        no_eda = detailDoc.getElementById("no_eda_" + wclickgyoNo);
        no_saiyou = detailDoc.getElementById("no_saiyou_" + wclickgyoNo);
        no_iraisample = detailDoc.getElementById("no_iraisample_" + wclickgyoNo);
        memo_eigyo = detailDoc.getElementById("memo_eigyo_" + wclickgyoNo);

        //�T�u�E�B���h�E��HTML���L�q
        outputHtml = "";

        outputHtml = outputHtml + "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"100%\">";
        //  �yQP@10713�z20111101 hagiwara mod start
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">������No�i�}�ԁj �F" + no_shisaku.innerHTML + "( " + no_eda.innerHTML + " )</td>";
        outputHtml = outputHtml + "   </tr>";
        /*
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;width:200;\">������No�i�}�ԁj �F</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_shisaku.innerHTML + "( " + no_eda.innerHTML + " )</td>";
        outputHtml = outputHtml + "   </tr>";
        */
        //  �yQP@10713�z20111101 hagiwara mod end
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">�����얼 �F</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + nm_shisaku.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        //  �yQP@10713�z20111101 hagiwara mod start
        /*
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���˗������No �F</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
		outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_iraisample.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���̗p�����No �F</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + no_saiyou.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        */

        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���׎p �F" + document.getElementById('cd_nisugata_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���戵�����x �F" + document.getElementById('nm_ondo_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">�������H�� �F" + document.getElementById('nm_kaisha_busho2_' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���S���c�� �F" + document.getElementById('nm_tantoeigyo' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���������S���� �F" + document.getElementById('nm_tantosha' + wclickgyoNo).value + "</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td>&nbsp;</td>";
        outputHtml = outputHtml + "   </tr>";
        //  �yQP@10713�z20111101 hagiwara mod end

        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">���������Z�����i�c�ƘA���p�j �F</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"word-break: break-all;text-align:left;\">" + memo_eigyo.value +"</td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //�T�u�E�B���h�E����
        win = new Window("win01", {
                    title: "�������Z���"
                    ,className: "alphacube"
                    //,top:300+event.y //�}�E�X�|�C���^��Y���W�i+600�����j
                    //,left:event.x //�}�E�X�|�C���^��X���W�i+30�����j
                    ,top:0
                    ,left:0
                    ,width:350
                    ,height:300
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
              });

        //�T�u�E�B���h�E���Ɂu����v�{�^����ǉ�
        win.setDestroyOnClose();

        //�T�u�E�B���h�E����HTML��ݒ�
        win.setHTMLContent(outputHtml);

        //�T�u�E�B���h�E�N���[�Y���C�x���g
		subwinOnCloseEv = {
			onClose: function(eventName, winObj) {
				subwinShowBln = false;
			}
		}

		//�T�u�E�B���h�E�ɃC�x���g��ݒ�
		Windows.addObserver(subwinOnCloseEv);

		//�T�u�E�B���h�E�\���t���O�I��
		subwinShowBln = true;

        //�T�u�E�B���h�E��\��
        win.show();

    }
    //�T�u�E�B���h�E����������Ă���ꍇ
    else{

        try{
            //�\������Ă���T�u�E�B���h�E��j���icatch�Ώہj
            win.destroy();

            //�T�u�E�B���h�E��������
            win = null;

            //���g���Ăяo���A�T�u�E�B���h�E�̍ĕ\��
            openWin(wclickgyoNo);

        }
        //win=null���������ł͉�ʏ�̃T�u�E�B���h�E�͏��������ꂸ�ɁA
        //win.destroy()���s��ɃG���[�ƂȂ�B
        //��L���R�ɂ��A�T�u�E�B���h�E�������Ă���ꍇ�́A
        //win.destroy()�̗�O��ߑ����A�T�u�E�B���h�E�̏������ƍĕ\�����s��
        catch (e) {

            //�T�u�E�B���h�E��������
            win = null;

            //���g���Ăяo���A�T�u�E�B���h�E�̍ĕ\��
            openWin(wclickgyoNo);

        }
    }
}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
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
        if (XmlId.toString() == "RGEN2120") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA030
                    funXmlWrite(reqAry[i], "cd_category", "K_yuza", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 2:    //SA050
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 3:    //SA080
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 4:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 5:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                case 6:    //FGEN2140
                    break;
                case 7:    //FGEN2130
                    break;
                //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
                    // ******** �s�v�I
                case 8:    //SA250
    	        	funXmlWrite(reqAry[i], "cd_group", "", 0);
                    funXmlWrite(reqAry[i], "cd_team", "", 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
                //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
            }
        }
        //��ٰ�ߺ��ޑI��
        else if (XmlId.toString() == "RGEN2130"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA080
                    funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
            }
        }
        //������БI��
        else if (XmlId.toString() == "RGEN2140"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA290
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
                    break;
            }
        }
        //����
        else if (XmlId.toString() == "RGEN2150"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2150
                	//�`�F�b�N�{�b�N�X�ݒ�
                	var minyuryoku = 0;
                	var kakunin = 0;
                	if(frm.chkMinyuryoku.checked == true){
                		minyuryoku = 1;
                	}
                	if(frm.chkKanryo.checked == true){
                		kakunin = 1;
                	}

                	//XML��������
                	funXmlWrite(reqAry[i], "kbn_joken1", flg_tab, 0);
                	funXmlWrite(reqAry[i], "kbn_joken2", minyuryoku, 0);
                	funXmlWrite(reqAry[i], "kbn_joken3", kakunin, 0);
                	funXmlWrite(reqAry[i], "no_shisaku", frm.txtShisakuNo.value, 0);
                	funXmlWrite(reqAry[i], "nm_shisaku", frm.txtShisakuNm.value, 0);
                	funXmlWrite(reqAry[i], "hi_kizitu_from", frm.txtKizitu_From.value, 0);
                	funXmlWrite(reqAry[i], "hi_kizitu_to", frm.txtKizitu_To.value, 0);
                	funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_user", frm.ddlUser.options[frm.ddlUser.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "busho_zyokyou", frm.ddlZyokyo.options[frm.ddlZyokyo.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "status", frm.ddlStatus.options[frm.ddlStatus.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "eda_shurui", frm.ddlShurui.options[frm.ddlShurui.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
                	funXmlWrite(reqAry[i], "cd_tanto", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value,0);
                	//�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
                    break;
            }
        }

        //�������Z��ʋN�����ʒm
        else if (XmlId.toString() == "RGEN2160"){
            // XML��莎��R�[�h�擾
            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
            var no_shisaku = obj_no_shisaku.innerHTML;
            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
            var no_eda = obj_no_eda.innerHTML;

            var put_shisaku = no_shisaku + "-" + no_eda;

            // ����R�[�h�u���y�Ј�CD::�N:::�ǔ�:::�}�ԁz
            var put_shisaku = put_shisaku.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_shisaku, 0);
                    break;
            }
        }

        //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
        else if (XmlId.toString() == "JSP0320"){
            switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	        	funXmlWrite(reqAry[i], "cd_group", frm.ddlGroup.options[frm.ddlGroup.selectedIndex].value, 0);
                funXmlWrite(reqAry[i], "cd_team", frm.ddlTeam.options[frm.ddlTeam.selectedIndex].value, 0);
                funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanItiran, 0);
             // ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 start
                // ���S���҃R���{�{�b�N�X�ݒ�F�I�������O���[�v�̉��CD�i��s�����j
                funXmlWrite(reqAry[i], "cd_kaisha", funXmlRead(xmlSA050, "cd_kaisha",frm.ddlGroup.selectedIndex-1), 0);
             // ADD 2015/03/30 TT.Kitazawa�yQP@40812�zNo.24 end
                break;
            }
        }
        //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End

		//�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start
        else if (XmlId.toString() == "RGEN2220"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

	            funXmlWrite(reqAry[i], "cd_shisaku", put_shisaku, 0);
                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisan, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
	        	break;
        	}
        }

        else if (XmlId.toString() == "RGEN2221"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanEigyo, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
	        	break;
        	}
        }
        // ADD 2013/10/22 QP@30154 okano start
        else if (XmlId.toString() == "RGEN2230"){
        	switch (i) {
	        case 0:    //USERINFO
	            funXmlWrite(reqAry[i], "kbn_shori", 1, 0);
	            funXmlWrite(reqAry[i], "id_user", "", 0);
	            break;
	        case 1:
	            var obj_no_shisaku = document.getElementById("no_shisaku_" + funGetCurrentRow());
	            var no_shisaku = obj_no_shisaku.innerHTML;
	            var obj_no_eda = document.getElementById("no_eda_" + funGetCurrentRow());
	            var no_eda = obj_no_eda.innerHTML;

	            var put_shisaku = no_shisaku + "-" + no_eda;

	            var shisaku = put_shisaku.split("-");

	            funXmlWrite(reqAry[i], "no_shisaku", put_shisaku, 0);
	            funXmlWrite(reqAry[i], "cd_shain", shisaku[0], 0);
	            funXmlWrite(reqAry[i], "nen", shisaku[1], 0);
	            funXmlWrite(reqAry[i], "no_oi", shisaku[2], 0);
	            funXmlWrite(reqAry[i], "no_eda", shisaku[3], 0);

                funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenkaShisanEigyo, 0);
                funXmlWrite(reqAry[i], "id_user", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                funXmlWrite(reqAry[i], "mode", mode, 0);
	        	break;
        	}
        }
        // ADD 2013/10/22 QP@30154 okano end
      //�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start
    }
    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
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
        case 1:    //��ٰ��Ͻ�
            atbName = "nm_group";
            atbCd = "cd_group";
            break;
        case 2:    //���Ͻ�
            atbName = "nm_team";
            atbCd = "cd_team";
            break;
        case 3:    //հ�ްϽ�
            atbName = "nm_user";
            atbCd = "id_user";
            break;
        case 4:    //����Ͻ�
            atbName = "nm_literal";
            atbCd = "cd_literal";
            break;
        case 5:    //��Ѓ}�X�^
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 6:    //�����}�X�^
            atbName = "nm_busho";
            atbCd = "cd_busho";
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
// �������ޔ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// �T�v  �F�������̑ޔ����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //�������Z�ꗗ���
        if (GamenId.toString() == ConGmnIdGenkaShisanItiran.toString()) {
            hidKinoId = KinoId;
			hidDataId = DataId;
        }
        //�������Z���
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {
        	document.getElementById("btnGenka").style.visibility = "visible";
        }
        //�������Z�i�c�Ɓj���
        if (GamenId.toString() == ConGmnIdGenkaShisanEigyo.toString()) {
	        document.getElementById("btnGenka_Eigyo").style.visibility = "visible";
        }

    }
    return true;

}

//========================================================================================
// �y�[�W�J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(NextPage);

    //�w���߰�ނ��ް��擾
    funDataSearch();
}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

	//���������N���A
    funClear_zyoken();

    //�ꗗ�̸ر
    funClearList();

    //***** ADD�yH24�N�x�Ή��z20120416 hagiwara S **********
    // ���������s�\�ɂ��邽�ߌ�������������
    search_cnt = 0;
    //***** ADD�yH24�N�x�Ή��z20120416 hagiwara E **********

    return true;

}

//========================================================================================
// ���������N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear_zyoken() {

	var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();

// ADD start 20120705 hisahori
	// �m�F�����`�F�b�N�{�b�N�X�A���Z�����e�L�X�g�{�b�N�X�̊�����
		frm.txtKizitu_From.disabled=false;
		frm.txtKizitu_From.style.backgroundColor="#ffffff";
		frm.txtKizitu_To.disabled=false;
		frm.txtKizitu_To.style.backgroundColor="#ffffff";;
		frm.chkKanryo.disabled = false;
// ADD end 20120705 hisahori

    //�`�[���R���{�{�b�N�X
	//DEL 2013/10/22 QP@30154 okano start
//	    //�������u����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�v�̏ꍇ
//
//	    if(hidDataId == "1"){
//	    	//�����ޯ���̍Đݒ�
//		    funDefaultIndex(frm.ddlGroup, 1);
//		    funDefaultIndex(frm.ddlTeam, 2);
//	//�yQP@20505�zNo.31 2012/09/18 TT H.Shima ADD Start
//		    funChangeTeam();
//		    funDefaultIndex(frm.ddlTanto, 3);
//	//�yQP@20505�zNo.31 2012/09/18 TT H.Shima ADD End
//	//  �yQP@10713�z2011.10.27 ADD Start hisahori
//		    frm.txtShisakuNo.value = hidUserId;
//	//  �yQP@10713�z2011.10.27 ADD End
//	    }
//	    //�������u����O���[�v���i�{�l�{�`�[�����[�_�[�ȏ�j�v�ȊO�̏ꍇ
//	    else{
    //DEL 2013/10/22 QP@30154 okano end
    	funClearSelect(frm.ddlTeam, 2);
//�yQP@20505�zNo.31 2012/09/18 TT H.Shima ADD Start
    	funClearSelect(frm.ddlTanto, 2);
//�yQP@20505�zNo.31 2012/09/18 TT H.Shima ADD End
    //DEL 2013/10/22 QP@30154 okano start
//    }
	//DEL 2013/10/22 QP@30154 okano end

    //�����R���{�{�b�N�X
    //�������u�����H��̂݁v�̏ꍇ
    if(hidDataId == "2"){

    }
    //�������u�����H��̂݁v�ȊO�̏ꍇ
    else{
    	funClearSelect(frm.ddlBusho, 2);
    }

    //�X�e�[�^�X�R���{�{�b�N�X
    funClearSelect(frm.ddlStatus, 2);

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //���C�����j���[
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// �������Z��ʂ֑J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�Ȃ�
// �T�v  �F�������Z��ʂ֑J�ڂ���
//========================================================================================
function funNextPage(mode) {

    //�I������Ă��Ȃ��ꍇ
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }
    //�I������Ă���ꍇ
    else{
    	//�������Z��ʋN�����ʒm�ɐ���
        if(funGenkaTuti(1)){

        	//�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start
	    	funAccessLogSave(mode);
	    	//�yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End

        	//�������Z���
	    	if(mode == 1){
	    		//�������Z��ʂ֑J��
	    		window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=no,resizable=yes");
	            //window.open("../SQ110GenkaShisan/GenkaShisan.jsp","shisaquick_genka","menubar=yes,resizable=yes");
	    	}
	    	//�������Z��ʁi�c�Ɓj
	    	else if(mode == 2){
	    		//�������Z��ʁi�c�Ɓj�֑J��
	            window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=no,resizable=yes");
	            //window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=yes,resizable=yes");
	    	}
	    	//�X�e�[�^�X����
	    	else if(mode == 3){
	    		//�X�e�[�^�X�����֑J��
	            window.open("../SQ140StatusRireki/SQ140StatusRireki.jsp","_blank","menubar=no,resizable=yes");
	    	}
        }
        //�������Z��ʋN�����ʒm�Ɏ��s
        else{

        }
    }

    return true;

}

//========================================================================================
// �������Z��ʋN�����ʒm
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/27
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funGenkaTuti(mode) {

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
// �󔒎��̐ݒ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
//========================================================================================
function funSetNbsp(val) {

    if( val == "" || val == "NULL" ){
    	val = "&nbsp;";
    }

    return val;
}
function funSetNbsp_kizitu(val) {

    if( val == "" || val == "����" ){
    	val = "&nbsp;";
    }

    return val;
}

//========================================================================================
// HTML�����G�X�P�[�v
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/31
//========================================================================================
function funEscape(val) {

    return val.replace(/<|>|&|'|"|\s/g, function(s){
    	var map = {"<":"&lt;", ">":"&gt;", "&":"&amp;", "'":"&#39;", "\"":"&quot;", " ":"&nbsp;"};
    	return map[s];
	});
}


//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
            case 1:    //��ٰ�ߺ���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_group", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //��Ѻ���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_team", 0)) {
                    selIndex = i;
                }
                break;
            //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD Start
            case 3:
            	if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "id_user", 0)) {
                    selIndex = i;
                }
                break;
            //�yQP@20505�zNo.31 2012/09/14 TT H.Shima ADD End
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//�u����v�{�^���N���b�N�C�x���g
// �쐬�ҁFRyo.Hagiwara
// �쐬���F2012/04/02
// ����  �F�Ȃ�
// �T�v  �F���݈ꗗ�ɏo�͂���Ă���f�[�^�����Ƃ�Excel�t�@�C�����o�͂���B
//========================================================================================
function funOutput() {

	// �I������Ă��Ȃ��ꍇ
    if(search_cnt <= 0){
    	funErrorMsgBox(E000023);
        return false;
    }

    //--------------------------------------------------
    var frm = document.frm00;    // ̫�тւ̎Q��
    var XmlId = "RGEN2210";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2210");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2210I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2210O);

    // ���������Ɉ�v���鎎���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2210, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    // ̧���߽�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlFGEN2210O, "URLValue", 0);

    // �޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);

    FuncIdAry = null;
    xmlReqAry = null;

	return true;

}

//========================================================================================
// ��ʎQ�ƃ��O�̒ǉ�
// �쐬�ҁFH.Shima
// �쐬���F2012/09/20
// ����  �Fmode     �F���[�h  1,�������Z 2,�������Z(�c��) 3,�X�e�[�^�X����
// �T�v  �F�Q�Ɖ�ʂƎQ�ƃ��[�U�̃��O��ۑ�����
//========================================================================================
function funAccessLogSave(mode) {

	var _XmlId = new Array("RGEN2220", "RGEN2221","");
	var XmlId = _XmlId[mode - 1];
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2220");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2220I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2220O);

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
		return false;
	}

	switch(mode){
	case 1:
	case 2:
		if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2220, xmlReqAry, xmlResAry, 1) == false) {
			return false;
		}
	case 3:
		break;

	}
}

// ADD 2013/10/22 QP@30154 okano  start
//========================================================================================
//�������Z��ʂ֑J��
//�쐬�ҁFR.Okano
//�쐬���F2013/10/24
//����  �Fmode     �F���[�h  1,�������Z 2,�������Z(�c��)
//�T�v  �F�Q�ƃ��[�U�̉{�������ɂď����𔻒肷��
//========================================================================================
function funGenkaOpen(mode){
	var frm = document.frm00;    //̫�тւ̎Q��
	var XmlId = "RGEN2230";
	var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2230");
	var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2230I);
	var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2230O);

	// ADD H.Shima Start
	//�I������Ă��Ȃ��ꍇ
    if(funGetCurrentRow().toString() == ""){
    	funErrorMsgBox(E000002);
        return false;
    }
    // ADD H.Shima End

	//������XMĻ�قɐݒ�
	if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
	    return false;
	}

	//���[�U�����擾
	if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2230, xmlReqAry, xmlResAry, 1) == false) {
	    return false;
	}

	//�������Z���
	funNextPage(mode);
}
// ADD 2013/10/22 QP@30154 okano end
