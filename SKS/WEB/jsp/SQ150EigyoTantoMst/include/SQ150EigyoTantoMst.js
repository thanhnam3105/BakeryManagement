
//========================================================================================
// ���ʕϐ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
//���o�^���[�U���[�h��ێ�
var hidKariMode = "";
var hidKengenIppan = "";
var hidKengenHonbu = "";
var hidKengenSystem = "";

// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
var hidEigyoKengen = "";
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End


//========================================================================================
// �yQP@00342�z�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConEigyoTantoMstId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //�h���b�v�_�E�����X�g��ReadOnly�ɐݒ�
    funDdlReadOnly();

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2040","FGEN2050","FGEN2120");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2040I,xmlFGEN2050I,xmlFGEN2120I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2040O,xmlFGEN2050O,xmlFGEN2120O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2040, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //�yUSERINFO�zհ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


    //�yFGEN2040�z�����R���{�{�b�N�X�ݒ�
    createKengenCombo(xmlResAry[2]);

    //�yFGEN2050�z������ЃR���{�{�b�N�X�ݒ�
    //���[�v�J�E���g�擾
    var roop_cnt = funXmlRead_3(xmlResAry[3], "table", "roop_cnt", 0, 0);
    //�R���{�{�b�N�X����
    for(var i=0 ; i<roop_cnt; i++){
        //�����R�[�h�A�������擾
        var cd_kaisha = funXmlRead_3(xmlResAry[3], "table", "cd_kaisha", 0, i);
        var nm_kaisha = funXmlRead_3(xmlResAry[3], "table", "nm_kaisha", 0, i);

        //�󔒍s�ݒ�
        if(i==0){
            objNewOption = document.createElement("option");
		    frm.ddlKaisha.options.add(objNewOption);
		    objNewOption.innerText = "";
		    objNewOption.value = "";
        }

        //�R���{�{�b�N�X����
        objNewOption = document.createElement("option");
        frm.ddlKaisha.options.add(objNewOption);
        objNewOption.innerText = nm_kaisha;
        objNewOption.value = cd_kaisha;
    }

    //�����R���{�{�b�N�X�@�󔒍s�ݒ�
    objNewOption = document.createElement("option");
	frm.ddlBusho.options.add(objNewOption);
	objNewOption.innerText = "";
	objNewOption.value = "";


    //�yFGEN2120�z���[�UID�ݒ�
    //���[�UID�ݒ�
    frm.txtUserId.value=funXmlRead_3(xmlResAry[4], "table", "id_user", 0, 0);
    hidKariMode=funXmlRead_3(xmlResAry[4], "table", "kbn_kari", 0, 0);

    //�����֘A�̏������s��
    funSaveKengenInfo();

    // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
    if(hidKariMode == "1"){
        funSearch();
    }
    // ADD 2013/9/25 okano�yQP@30151�zNo.28 end

    return true;

}

//========================================================================================
// �����R���{�{�b�N�X����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �FXML�f�[�^
//========================================================================================
function createKengenCombo(xmlData){

	var frm = document.frm00;    //̫�тւ̎Q��

	//�����ޯ���̸ر
    funClearSelect(frm.ddlKengen, 2);

    //���[�v�J�E���g�擾
    var roop_cnt = funXmlRead_3(xmlData, "table", "roop_cnt", 0, 0);

    //�R���{�{�b�N�X����
    for(var i=0 ; i<roop_cnt; i++){
        //�����R�[�h�A�������擾
        var cd_kengen = funXmlRead_3(xmlData, "table", "cd_kengen", 0, i);
        var nm_kengen = funXmlRead_3(xmlData, "table", "nm_kengen", 0, i);

        //�R���{�{�b�N�X����
    	objNewOption = document.createElement("option");
        frm.ddlKengen.options.add(objNewOption);
        objNewOption.innerText = nm_kengen;
        objNewOption.value = cd_kengen;
    }
    hidKengenIppan = funXmlRead_3(xmlData, "table", "cd_kengen_ippan", 0, 0);
	hidKengenHonbu = funXmlRead_3(xmlData, "table", "cd_kengen_honbu", 0, 0);
	hidKengenSystem = funXmlRead_3(xmlData, "table", "cd_kengen_system", 0, 0);
}

//========================================================================================
// �����R���{�{�b�N�X�ύX
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
//========================================================================================
function funChangeKengen() {

    var frm = document.frm00;    //̫�тւ̎Q��

    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//    //�I�������擾
//    var cd_kengen_sentaku = frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value;
//
//// �yQP@10713�z2011/10/28 TT H.SHIMA -DEL Start
//    //�g�p�ېݒ�
////    if( cd_kengen_sentaku == hidKengenIppan ){
////    	//�c�Ɓi��ʁj�����̏ꍇ�͎g�p�\
////    	frm.btnJosiSearch.disabled=false;
////    	frm.btnJosiDel.disabled=false;
////    }
////    else{
////    	//�c�Ɓi��ʁj�����ȊO�̏ꍇ�͎g�p�s��
////    	frm.btnJosiSearch.disabled=true;
////    	frm.btnJosiDel.disabled=true;
////    	funDelJosi();
////    }
//// �yQP@10713�z2011/10/28 TT H.SHIMA -DEL End
//
//// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
//    switch(cd_kengen_sentaku){
//    case hidKengenIppan :
//    	frm.btnJosiSearch.disabled=false;
//    	frm.btnJosiDel.disabled=false;
//    	hidEigyoKengen = "1";
//    	break;
//    case hidKengenHonbu :
//    case hidKengenSystem :
//    	hidEigyoKengen = "2";
//    default :
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//    	funDelJosi();
//    }
    if(hidMode.value == ConFuncIdEigyoTantoEditPass.toString() || hidMode.value == ConFuncIdEigyoTantoEditCash.toString()){
    //  2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;

    	frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
    //  2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19
   } else {
	    //�I�������擾
	    var cd_kengen_sentaku = frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value;

	    switch(cd_kengen_sentaku){
	    case hidKengenIppan :
	    //  2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//	    	frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;

	        frm.btnAddList.disabled=false;
	        frm.btnDelList.disabled=false;
	    //  2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19

	        hidEigyoKengen = "1";
	    	break;
	    case hidKengenHonbu :
	    case hidKengenSystem :
	    	hidEigyoKengen = "2";
	    default :
	    //  2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;
//	    	funDelJosi();

	        frm.btnAddList.disabled=true;
	        frm.btnDelList.disabled=true;
	    //  2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19
	    }
    }
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 end

    funChangeKaisha();

// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End


    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //�����\��
    var XmlId = "RGEN2040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2040O);
    var mode = 1;

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2040, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //�yFGEN2040�z�����R���{�{�b�N�X�ݒ�
    createKengenCombo(xmlResAry[2]);

    //2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
	// ���L�����o�[�F�S���҃f�[�^�̃N���A
	xmlFGEN2060O.src = "";
    //2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19

    //��ʂ̏�����
    frm.reset();

    //2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//    frm.btnJosiSearch.disabled=false;
//    frm.btnJosiDel.disabled=false;

    frm.btnAddList.disabled=false;
    frm.btnDelList.disabled=false;
    tblList.style.display = "none";
    //2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19

    //2012/03/02 TT H.SHIMA Java6�Ή� add Start
    funDdlReadOnly();
    //2012/03/02 TT H.SHIMA Java6�Ή� add End

    //̫����ݒ�
    frm.txtUserId.focus();

    return true;

}


//========================================================================================
// ���[�UID���X�g�t�H�[�J�X����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F���[�UID�ɕR�t���f�[�^���擾���\������
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��

    var XmlId = "RGEN2050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2060","FGEN2040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2060I,xmlFGEN2040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2060O,xmlFGEN2040O);

    //հ��ID�������͂̏ꍇ
    if (frm.txtUserId.value == "") {
        funClear();
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //հ�ޏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2050, xmlReqAry, xmlResAry, 1) == false) {
        //���[�UID������
        frm.txtUserId.value = "";

        //2012/03/02 TT H.SHIMA Java6�Ή� add Start
        funDdlReadOnly();
        //2012/03/02 TT H.SHIMA Java6�Ή� add End

        //���L�����o�[��\��
        tblList.style.display = "none";
        return false;
    }

    //�yFGEN2040�z�����R���{�{�b�N�X�ݒ�
    createKengenCombo(xmlResAry[3]);

    //�l�̐ݒ�
    funSetData();

//  2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
    //���L�����o�[����������
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true" && funXmlRead(xmlResAry[2], "id_member", 0) != "") {
        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";
    }
//  2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19

    //�yQP@10713�z�V�T�N�C�b�N���� No.25
    //�����R�[�h�ݒ�
    //funChangeKaisha();
    funChangeKengen();
    funDefaultIndex(frm.ddlBusho, 3);
 //2014/05/05 add start �ۑ�No.30 �V�X�e���Ǘ��҂́A��� �����R���{�I��s��
//    if (hidMode.value.toString() == ConFuncIdEigyoTantoEditHonbu.toString()) {
//    }
//    else
    if (hidMode.value.toString() == ConFuncIdEigyoTantoEditSystem.toString()) {
    // 2015/06/01 ADD start TT.Kitazawa�yQP@40812�z
    // �V�X�e���Ǘ��ҁA�y�щ��o�^���[�U�̓R���{�I����
    } else if (ConFuncIdEigyoTantoEditKari == hidMode.value) {
    	// ���o�^���[�U�F����ID����͎��̓R���{�I��s�A���L�u�ǉ��v�u�폜�v
    	if (funXmlRead(xmlResAry[2], "nm_user", 0) != "") {
    		funDdlReadOnly();
    	    funItemReadOnly(document.frm00.btnAddList, true);
    	    funItemReadOnly(document.frm00.btnDelList, true);
    	}
    // 2015/06/01 ADD end TT.Kitazawa�yQP@40812�z
    }
    else {
        funDdlReadOnly();
    }
 //2014/05/05 add end �ۑ�No.30

    return true;

}

//========================================================================================
// �擾�f�[�^�ݒ菈��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�擾�����S���ҏ�����ʂɐݒ肷��
//========================================================================================
function funSetData() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //2012/03/02 TT H.SHIMA Java6�Ή� add Start
    funDdlReadOnly();
    //2012/03/02 TT H.SHIMA Java6�Ή� add End

    //�l�̐ݒ�
    frm.txtPass.value = funXmlRead(xmlFGEN2060O, "password", 0);
    funDefaultIndex(frm.ddlKengen, 1);
    frm.txtUserName.value = funXmlRead(xmlFGEN2060O, "nm_user", 0);
    funDefaultIndex(frm.ddlKaisha, 2);
//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//    frm.txtUserNameJosi.value = funXmlRead(xmlFGEN2060O, "nm_josi", 0);
//    frm.hdnUserNameJosi.value = funXmlRead(xmlFGEN2060O, "id_josi", 0);
//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

    //�yQP@10713�z�V�T�N�C�b�N���� No.25
    //var kengen_ippan = funXmlRead(xmlFGEN2060O, "kengen_ippan", 0);
    var eigyo_kengen = funXmlRead(xmlFGEN2060O, "eigyo_kengen", 0);
    //if(kengen_ippan == "0"){
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//	    if(eigyo_kengen == "0"){
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;
//	    }
//	    else {
//			frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;
//
//	    	//2012/03/02 TT H.SHIMA Java6�Ή� add Start
//	    	funItemReadOnly(frm.txtPass, false);
//		    funItemReadOnly(frm.ddlKengen, false);
//		    funItemReadOnly(frm.txtUserName, false);
//		    funItemReadOnly(frm.ddlKaisha, false);
//		    funItemReadOnly(frm.ddlBusho, false);
//		    //2012/03/02 TT H.SHIMA Java6�Ή� add End
//	    }
    if(hidMode.value == ConFuncIdEigyoTantoEditPass.toString()){
    	funItemReadOnly(frm.txtUserId, true);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19
    	frm.btnInsert.disabled=true;
    	frm.btnUpdate.disabled=false;
    	frm.btnDelete.disabled=true;
    	frm.btnClear.disabled=true;
    	frm.btnEnd.disabled=false;
    	frm.btnSearchUser.disabled=true;
    	frm.btnMenu.disabled=true;

//  2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
        frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
//  2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19

    } else if(hidMode.value == ConFuncIdEigyoTantoEditCash.toString()){
    	funItemReadOnly(frm.txtUserId, true);
	    funItemReadOnly(frm.ddlKengen, true);
	    funItemReadOnly(frm.ddlKaisha, true);
	    funItemReadOnly(frm.ddlBusho, true);
//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//    	frm.btnJosiSearch.disabled=true;
//    	frm.btnJosiDel.disabled=true;
//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19
    	frm.btnInsert.disabled=false;
    	frm.btnUpdate.disabled=true;
    	frm.btnDelete.disabled=true;
    	frm.btnClear.disabled=true;
    	frm.btnEnd.disabled=false;
    	frm.btnSearchUser.disabled=true;
    	frm.btnMenu.disabled=true;

//  2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
    	frm.btnAddList.disabled=true;
        frm.btnDelList.disabled=true;
//  2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19

    }else{
	    if(eigyo_kengen == "0" || eigyo_kengen == "3" ){
	    //  2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//	    	frm.btnJosiSearch.disabled=true;
//	    	frm.btnJosiDel.disabled=true;

	        frm.btnAddList.disabled=true;
	        frm.btnDelList.disabled=true;
	    //  2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19
	    }
	    else {
	    //  2015/03/03 MOD start TT.Kitazawa�yQP@40812�zNo.19
//			frm.btnJosiSearch.disabled=false;
//	    	frm.btnJosiDel.disabled=false;

	        frm.btnAddList.disabled=false;
	        frm.btnDelList.disabled=false;
	        //  2015/03/03 MOD end TT.Kitazawa�yQP@40812�zNo.19

	    	//2012/03/02 TT H.SHIMA Java6�Ή� add Start
	    	funItemReadOnly(frm.txtPass, false);
		    funItemReadOnly(frm.ddlKengen, false);
		    funItemReadOnly(frm.txtUserName, false);
		    funItemReadOnly(frm.ddlKaisha, false);
		    funItemReadOnly(frm.ddlBusho, false);
		    //2012/03/02 TT H.SHIMA Java6�Ή� add End
	    }
    }
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 end

    return true;

}

//========================================================================================
// �o�^�A�X�V�A�폜�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�폜
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2070";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2080");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2080I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2080O);
    var dspMsg;

    if (mode == 1) {
        dspMsg = I000002;
    } else if (mode == 2) {
        dspMsg = I000003;
    } else {
        dspMsg = I000004;
    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //�����敪�̑ޔ�
    frm.hidEditMode.value = mode;

    //XML�̏�����
    setTimeout("xmlFGEN2080I.src = '../../model/FGEN2080I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2070, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

    	//���o�^���[�U�̏ꍇ
    	if(hidKariMode == "1"){
    		funChkLogin();
    	}

        //����
        funInfoMsgBox(dspMsg);

        //��ʏ����擾�E�ݒ�
        //�����ޯ���̸ر(�󔒂���)
	    funClearSelect(frm.ddlKengen, 1);
	    funClearSelect(frm.ddlKaisha, 1);
	    funClearSelect(frm.ddlBusho, 2);
        if (funGetInfo(1) == false) {
            return false;
        }

        //��ʂ̏�����
        funClear();
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// ���O�C�����`�F�b�N����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F���[�U�̔F�؂��s��
//========================================================================================
function funChkLogin() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //հ�ޔF�؂��s��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
        return false;
    } else {
    	// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
        //�ƭ��ɑJ��
//        	funNextMenu();
    	funNext(1);
    	// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
    }

    return true;

}

// DEL 2013/9/25 okano�yQP@30151�zNo.28 start
//========================================================================================
// ���C�����j���[��ʑJ�ڏ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F���j���[��ʂɑJ�ڂ���
//========================================================================================
//function funNextMenu() {
//
//    var wUrl;
//
//    wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
//
//    //Ҳ��ƭ���\��
//    funUrlConnect(wUrl, ConConectPost, document.frm00);
//
//    return true;
//}
// DEL 2013/9/25 okano�yQP@30151�zNo.28 end

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
    var j;

    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\��
        if (XmlId.toString() == "RGEN2040") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2040
                	funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 2:    //FGEN2050
                    break;
            }

        //հ��ID۽�̫���
        } else if (XmlId.toString() == "RGEN2050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2060
                	//�O�[���폜
// MOD start 20120705 hisahori
//                    var id_user = frm.txtUserId.value.replace(/^0+/,"");
                    var id_user = frm.txtUserId.value;
                    if (id_user != 0){
                    	id_user = id_user.replace(/^0+/,"");
                    }
// MOD start 20120705 hisahori
                    funXmlWrite(reqAry[i], "id_user", id_user, 0);
                    break;
                case 2:    //FGEN2040
                	funXmlWrite(reqAry[i], "id_user", id_user, 0);
                    break;
            }

        //�o�^�A�X�V�A�폜���݉���
        } else if (XmlId.toString() == "RGEN2070"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2080
                	funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                	funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                	funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "nm_user", frm.txtUserName.value, 0);
                	funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                	funXmlWrite(reqAry[i], "cd_busho", frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value, 0);
                    //2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//                	funXmlWrite(reqAry[i], "id_josi", frm.hdnUserNameJosi.value, 0);
                    //2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19
                	funXmlWrite(reqAry[i], "kbn_shori", frm.hidEditMode.value, 0);

                    //2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
                    funXmlWrite(reqAry[i], "id_member", funXmlRead(xmlFGEN2060O, "id_member", 0), 0);
                    // ���L�����o�[2���ڈȍ~
                    for( j = 1; j < funGetLength(xmlFGEN2060O); j++ ){
                    	funAddRecNode_Tbl(reqAry[i], "RGEN2070", "table");
                    	funXmlWrite(reqAry[i], "id_member", funXmlRead(xmlFGEN2060O, "id_member", j), j);
                    }
                    //2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19
                	break;
            }

        //��к��ޑI��
        } else if (XmlId.toString() == "RGEN2060"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2070
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
//xml�ɐݒ肷��l�A�����̒ǉ�
                    funXmlWrite(reqAry[i], "eigyo_kengen", hidEigyoKengen , 0);
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End
                    break;
            }

        //�V���O���T�C���I��
        } else if (XmlId.toString() == "JSP0010"){
            switch (i) {
                case 0:    //USERINFO
	                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
	                funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
	                break;
            	case 1:    //SA010
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
	                funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
	                funXmlWrite(reqAry[i], "kbn_login", "1", 0);
	                break;
            }
        }
    }

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var DataId;
    var reccnt;
    var i;

// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD Start
	//���������l�ݒ�
	hidEigyoKengen = "1";
// �yQP@10713�z2011/10/28 TT H.SHIMA -ADD End

    //�o�^�A�X�V�A�폜���݂̐���
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);

        //�S����Ͻ�����ݽ�i�c�Ɓj
        if (GamenId.toString() == ConGmnIdEigyoTantoMst.toString()) {
            //�ҏW�i��ʁj
            if (KinoId.toString() == ConFuncIdEigyoTantoEditIppan.toString()) {
                frm.btnInsert.disabled = true;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = true;
            }
            //�ҏW�i���o�^���[�U�j
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditKari.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = true;
                frm.btnDelete.disabled = true;
            }
            //�ҏW�i�{�������j
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditHonbu.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }
            //�ҏW�i�V�X�e���Ǘ��ҁj
            else if (KinoId.toString() == ConFuncIdEigyoTantoEditSystem.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }

            hidMode.value = KinoId;
        }
    }

    return true;

}

//========================================================================================
// ���[�UID�ύX��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funChangeUserId() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����

    //�����ޯ���̍Đݒ�
    funClearSelect(frm.ddlBusho, 2);

    //�l�̐ݒ�
    frm.txtPass.value = "";
    frm.ddlKengen.selectedIndex = 0;
    frm.txtUserName.value = "";
    frm.ddlKaisha.selectedIndex = 0;
//2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//    frm.txtUserNameJosi.value = "";
//    frm.hdnUserNameJosi.value = "";
//
//    //�ҏW�ۂ̐ݒ�
//    frm.btnJosiSearch.disabled=false;
//    frm.btnJosiDel.disabled=false;
//2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

    //2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
    frm.btnAddList.disabled=false;
    frm.btnDelList.disabled=false;
    //2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19
    return true;

}

//========================================================================================
// �S���Ҍ�����ʋN�������i�S���Ҍ������[�h�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F�S���Ҍ�����ʂ��N������
//========================================================================================
function funSearchUser() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;

    //�S���Ҍ������[�h�ݒ�
    frm.hidOpnerSearch.value = "1";

    //�S���Ҍ�����ʂ��N������
    //2015/05/15 MOD start TT.Kitazawa�yQP@40812�zNo.19
//    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1000px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1100px;status:no;scroll:no");
    //2015/05/15 MOD end TT.Kitazawa�yQP@40812�zNo.19
    //retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //���̕���
        var id_user = retVal.split(":")[0];

        //հ��ID�̐ݒ�
        frm.txtUserId.value = id_user;

        //�S���ҏ��̕\��
        funSearch();

//�yQP@10713�z2011/11/07 TT H.SHIMA -ADD Start �S���Ҍ�����̕����R���{�ݒ�
        funChangeKengen();
        funDefaultIndex(frm.ddlBusho, 3);
//�yQP@10713�z2011/11/07 TT H.SHIMA -ADD End

        //̫����ݒ�
        //frm.txtPass.focus();
    }


    return true;

}

//2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
/*
//========================================================================================
// �S���Ҍ�����ʋN�������i��i�������[�h�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F�S���Ҍ�����ʂ��N������
//========================================================================================
function funSearchUserJosi() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;

    var cd_kaisha = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;
    var cd_busho = frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value;
    if(cd_kaisha == "" || cd_busho == "" ){
        funInfoMsgBox(E000014);
    	return false;
    }

    //�S���Ҍ������[�h�ݒ�
    frm.hidOpnerSearch.value = "2";

    //�S���Ҍ�����ʂ��N������
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1000px;status:no;scroll:no");
    //retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    if (retVal != "") {
        //���̕���
        var id_user = retVal.split(":")[0];
        var nm_user = retVal.split(":")[1];

        //հ��ID�̐ݒ�
        frm.hdnUserNameJosi.value = id_user;
        frm.txtUserNameJosi.value = nm_user;
    }

    return true;

}*/
//2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

//========================================================================================
// ��ЃR���{�{�b�N�X�A������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F��ЂɕR�t�������R���{�{�b�N�X�𐶐�����
//========================================================================================
function funChangeKaisha() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2070");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2070I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2070O);

    if (frm.ddlKaisha.selectedIndex == 0) {
        funClearSelect(frm.ddlBusho, 2);
        return true;
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2060, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlBusho, xmlResAry[2], 3);

    return true;

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //Ͻ��ƭ�
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
        case 1:    //���o�^հ��
        	wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 2:    //���o�^հ�ޏI����
        	wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
        // ADD 2013/9/25 okano�yQP@30151�zNo.28 end
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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

    //�����ޯ���̸ر(�󔒂���)
    funClearSelect(obj, 2);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //����Ͻ�
            atbName = "nm_kengen";
            atbCd = "cd_kengen";
            break;
        case 2:    //����Ͻ�(���)
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
            break;
        case 3:    //����Ͻ�(����)
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
// �f�t�H���g�l�I������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
            case 1:    //��������
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_kengen", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //��к���
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //��������
                if (obj.options[i].value == funXmlRead(xmlFGEN2060O, "cd_busho", 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
/*
//========================================================================================
// ��i�N���A
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDelJosi() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��iID�N���A
    frm.hdnUserNameJosi.value="";
    //��i���N���A
    frm.txtUserNameJosi.value="";

    return true;
}*/
//2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

//========================================================================================
//�h���b�v�_�E�����X�g��ReadOnly�ݒ菈��
//�쐬�ҁFH.Shima
//�쐬���F2012/03/02
//�T�v  �F�h���b�v�_�E�����X�g�̐ݒ���s��
//========================================================================================
function funDdlReadOnly(){
    var frm = document.frm00;    //̫�тւ̎Q��

    funItemReadOnly(frm.ddlKengen, true);
    funItemReadOnly(frm.ddlKaisha, true);
    funItemReadOnly(frm.ddlBusho, true);

    return true;
}

// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
function funClose(){

    if (hidMode.value == ConFuncIdEigyoTantoEditKari.toString() || hidMode.value == ConFuncIdEigyoTantoEditPass.toString() || hidMode.value == ConFuncIdEigyoTantoEditCash.toString()) {
        funNext(2);
    } else {
        funNext(0);
    }

    return true;
}
// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

//2015/03/03 ADD start TT.Kitazawa�yQP@40812�zNo.19
//========================================================================================
// �S���Ҍ�����ʋN�������i��i�������[�h�j
// �쐬�ҁFTT.Kitazawa
// �쐬���F2015/03/03
// ����  �F�Ȃ�
// �T�v  �F�S���Ҍ�����ʂ��N������
//========================================================================================
function funAddList() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    var cd_kaisha = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;
    var cd_busho = frm.ddlBusho.options[frm.ddlBusho.selectedIndex].value;
    if(cd_kaisha == "" || cd_busho == "" ){
        funInfoMsgBox(E000014);
        return false;
    }

    //�S���Ҍ������[�h�ݒ�
    frm.hidOpnerSearch.value = "2";

    //�S���Ҍ�����ʂ��N������
    retVal = funOpenModalDialog("../SQ151EigyoTantoSearch/SQ151EigyoTantoSearch.jsp", this, "dialogHeight:800px;dialogWidth:1100px;status:no;scroll:no");

    if (retVal != "") {
        //���̕���
        var id_user = retVal.split(":")[0];
        var nm_user = retVal.split(":")[1];

        //�O�[���폜�i�󔒂̎��͗��Ȃ��j
        id_user = id_user.replace(/^0+/,"");

        // �������g�͓o�^�ł��Ȃ��i�󔒂̎��͗��Ȃ��j
        if (id_user == frm.txtUserId.value.replace(/^0+/,"")) {
            funInfoMsgBox(E000040);
            return false;
        }
        // ���X�g�ɑ��݂���ꍇ
        for(var i = 0; i < funGetLength(xmlFGEN2060O); i++ ){
            if (id_user == funXmlRead(xmlFGEN2060O, "id_member", i)) {
                funInfoMsgBox(E000041);
                return false;
            }
        }

        //���L�����o�[�̒ǉ��F
        if (funXmlRead(xmlFGEN2060O, "id_member", funGetLength(xmlFGEN2060O)-1) != "" ) {
            // id_member���ݒ�ς̏ꍇ�Anode��ǉ�
            funAddRecNode(xmlFGEN2060O, "FGEN2060");
        }
        // �I���������[�UID�A���[�U����XML�ɕۑ�
        funXmlWrite(xmlFGEN2060O, "id_member", id_user, funGetLength(xmlFGEN2060O)-1);
        funXmlWrite(xmlFGEN2060O, "nm_member", nm_user, funGetLength(xmlFGEN2060O)-1);

        //�l��ݒ肵��XML�̍�۰��
        xmlBuff.load(xmlFGEN2060O);
        xmlFGEN2060O.load(xmlBuff);

        // ���L�����o�[���X�g��\��
        if (tblList.style.display == "none") {
            tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);

    }

    return true;

}

//========================================================================================
// ���L�����o�[�폜����
// �쐬�ҁFTT.Kitazawa
// �쐬���F2015/03/03
// ����  �F�Ȃ�
// �T�v  �F�I���s�̋��L�����o�[���폜����
//========================================================================================
function funDelList() {

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�w�肳�ꂽ���L�����o�[���폜����
    if (funGetLength(xmlFGEN2060O) > 1) {
    	//�w��s���폜
        funSelectRowDelete(xmlFGEN2060O);
    } else {
    	//�f�[�^��1���̎��A���L�����o�[���N���A
        funXmlWrite(xmlFGEN2060O, "id_member", "", 0);
        funXmlWrite(xmlFGEN2060O, "nm_member", "", 0);
        // �I���s���N���A
        funClearCurrentRow(tblList);
        // ���L�����o�[���X�g���\��
        tblList.style.display = "none"
    }

    return true;

}
//2015/03/03 ADD end TT.Kitazawa�yQP@40812�zNo.19
