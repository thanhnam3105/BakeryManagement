var selectHattyusaki = "";
// ���`�F�b�N�F
var checkColor       = "#ffffff";
// ���`�F�b�N�F
var uncheckColor     = "#99ffff";
//========================================================================================
// �w�b�_�����\������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/4
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_head() {

    var frm = document.frm00; // ̫�тւ̎Q��

    // ��ʏ����擾�E�ݒ�
    if (funGetUserInfo(1) == false) {
        return false;
    }

    //�����ڰт̕`��
    parent.detail.location.href="ShizaiTehaiOutput_dtl.jsp";

    return true;
}

//========================================================================================
// �ڍ׏����\������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/14
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_dtl() {

    var frm = document.frm00; // ̫�тւ̎Q��

    funGetDetailInfo(1);

    // �����ރR�[�h�̔�\��
    frm.txtOldShizai.style.visibility                     = 'hidden';
    // �d�l�ύX���W�I�{�^���̔�\��
    document.getElementById('siyohenko').style.visibility = 'hidden';
    // �����ލ݌ɂ̔�\��
    frm.txtOldShizaiZaiko.style.visibility                = 'hidden';
    //    frm.btnOldShizaiZaikoCheck.style.visibility           = 'hidden';

    // ���ł̔�\��
    frm.txtRakuhan.style.visibility                       = 'hidden';
//    frm.btnRakuhanCheck.style.visibility                  = 'hidden';


    return true;
}
//========================================================================================
// �����֘A����
// �쐬�ҁFcho
// �쐬���F2016/10/04
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var DataId;
    var reccnt;
    var i;
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        //GamenId = funXmlRead(obj, "id_gamen", i);
        //KinoId = funXmlRead(obj, "id_kino", i);
        DataId = funXmlRead(obj, "id_data", i);
                if (DataId == "2") {
                    frm.btnYobidasi.disabled = true;
                }
			}
		}


//=========================================================================================
// ========================================================================================
// ��ʏ��擾����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �����P�Fmode �F�������[�h
//         1�F�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
// ========================================================================================
function funGetUserInfo(mode) {

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN2130I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN2130O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            mode) == false) {
        return false;
    }

    // հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");


    // ����
    if (funXmlRead(xmlResAry[2], "flg_gentyo", 0)) {

        // �������[�U�[�̓{�^��������
       // frm.btnToroku.disabled = false;
       // frm.btnOutput.disabled = false;
        frm.btnClear.disabled  = false;

    } else {
        // �H�ꃆ�[�U�[�̓{�^����񊈐�

        //frm.btnYobidasi.disabled = true;
       // frm.btnOutput.disabled = true;
        frm.btnClear.disabled  = true;
    }

	funSaveKengenInfo();



    return true;
}
//========================================================================================
//��ʏ��擾����
//�쐬�ҁFH.Shima
//�쐬���F2014/09/09
//�����P�Fmode �F�������[�h
//      1�F�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
//�߂�l�F����I��:true�^�ُ�I��:false
//�T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetDetailInfoTMPLoad(mode, data) {
	 var detail = parent.detail.document.frm00;
	 var frm = document.frm00; // ̫�тւ̎Q��
	 var XmlId = "RGEN3200";
	 var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310", "FGEN3200");
	 var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I, xmlFGEN3200I);
	 var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O , xmlFGEN3200O);

	 // ������XMĻ�قɐݒ�
	 if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
	     funClearRunMessage();
	     return false;
	 }

	 // ����ݏ��A�����ޯ���̏����擾
	 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
	      mode) == false) {
	     return false;
	 }


	 funTempSearch(data);

	 // �m�F���ڔw�i�F�ݒ�
	 funCheckSetting();
    return true;
}
//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �����P�Fmode �F�������[�h
//         1�F�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
// ========================================================================================
function funGetDetailInfo(mode) {

	var detail = parent.detail.document.frm00;
    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3310", "FGEN3200");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3310I, xmlFGEN3200I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3310O , xmlFGEN3200O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    // ������P
    funCreateComboBox(frm.cmbOrderCom1, xmlResAry[2], 1, 2);



    // ������Q
    funCreateComboBox(frm.cmbOrderCom2, xmlResAry[2], 1, 2);

     //�S����
    //frm.txtTanto.value = funXmlRead(xmlResAry[1], "nm_user", 0);


    // �Ώێ���
    funCreateComboBox(frm.cmbTargetSizai, xmlResAry[3], 2, 2);

    // ������
    //funCreateComboBox(frm.cmbOrder, xmlResAry[2], 1, 2);

    // �m�F���ڔw�i�F�ݒ�
    funCheckSetting();

    // ���ގ�z�Y�t�f�[�^�擾




    return true;
}

//========================================================================================
// ������S���Ґݒ菈��
// �쐬�ҁFH.Shima
// �쐬���F2014/10/09
// �����P�Fmode �F�������[�h
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F������S���ҏ����擾����
//========================================================================================
function funChangeHattyusaki(mode){

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3290";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3290O);

    switch(mode){
    case 1:
        if(frm.cmbOrderCom1.selectedIndex == 0) {
            frm.txtOrderUser1.value = "";
            return true;

        }
        break;
    case 2:
        if(frm.cmbOrderCom2.selectedIndex == 0) {
            frm.txtOrderUser2.value = "";
            return true;
        }
        break;
    }
    selectHattyusaki = mode;
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //��я����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3290, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
//0926cho_�C��
    switch(mode){
    case 1:
        // ������S���҂P
        funCreateComboBox(frm.txtOrderUser1, xmlResAry[2], 3, 1);
        break;
    case 2:
        // ������S���҂Q
        funCreateComboBox(frm.txtOrderUser2, xmlResAry[2], 3, 1);
        break;
    }
    return true;

}

//========================================================================================
//���ގ�z�Y�t�e�[�u���f�[�^����
//�쐬�ҁFt2nakamura
//�쐬���F2016/11/01
//�����P�Fmode �F�������[�h
//�߂�l�F����I��:true�^�ُ�I��:false
//�T�v  �F
//========================================================================================
function funShizaiTmp(data) {

	var dataArray = data.split(":::");
	if (dataArray[0] == 1) {

		funGetDetailInfoTMPLoad(1, dataArray);
	}
}

//========================================================================================
// ���ގ�z�e�[�u���f�[�^����
// �쐬�ҁFt2nakamura
// �쐬���F2016/10/31
// �����P�Fmode �F�������[�h
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F
//========================================================================================
function funTempSearch(data){

	var detail = parent.detail.document.frm00�G

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3730";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3730");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3730I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3730O);
    //XML�̏�����
     setTimeout("xmlFGEN3730I.src = '../../model/FGEN3730I.xml';", ConTimer);

    if (data[0] == "1") {
        funXmlWrite(xmlReqAry[1], "kbn", data[0], 0);
        funXmlWrite(xmlReqAry[1], "cd_shain", data[1], 0);
        funXmlWrite(xmlReqAry[1], "nen", data[2], 0);
        funXmlWrite(xmlReqAry[1], "seq_shizai", data[3], 0);
        funXmlWrite(xmlReqAry[1], "no_oi", data[4], 0);
        funXmlWrite(xmlReqAry[1], "no_eda", data[5], 0);
        funXmlWrite(xmlReqAry[1], "cd_shohin", data[6], 0);
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //��я����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3730, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }
	// ���ۑ����擾�ł�������{
	if (funXmlRead(xmlResAry[2], "flg_return_success", 0) == "true") {
	    funShizaiTehaiTmpData();
	}
    return true;

}


//========================================================================================
// ���Y��z�e�[�u���f�[�^�擾
//�쐬�ҁFt2nakamura
//�쐬���F2016/10/02
//
//�߂�l�F�Ȃ�
//�T�v  �F���ގ�z���́A���Y���ގ�z�˗��o�͂���̑J�ڎ����Y��z�e�[�u������f�[�^���擾
//========================================================================================
function funShizaiTehaiTmpData() {

    // ��
    var han = funXmlRead(xmlFGEN3730O, "han", 0);
    if (han == 1) {
    	parent.detail.document.getElementById("revision_new").checked = true;
    	parent.detail.document.getElementById("revision_rev").checked = false;
    	parent.detail.document.getElementById("revision_new").onclick();

    } else {
    	parent.detail.document.getElementById("revision_new").checked = false;
    	parent.detail.document.getElementById("revision_rev").checked = true;
    	parent.detail.document.getElementById("revision_rev").onclick();
    }

    // ������R�[�h�P
    var cd_literal_1 = funXmlRead(xmlFGEN3730O, "cd_literal_1", 0);
    // �����於�P
   	parent.detail.document.frm00.cmbOrderCom1.value = cd_literal_1;
   	parent.detail.document.frm00.cmbOrderCom1.onchange();
    // �S���҃R�[�h
    var cd_2nd_literal_1 = funXmlRead(xmlFGEN3730O, "cd_2nd_literal_1", 0);
    parent.detail.document.frm00.txtOrderUser1.value = cd_2nd_literal_1;

    // ������R�[�h�Q
    var cd_literal_2 =  funXmlRead(xmlFGEN3730O, "cd_literal_2", 0);
    if (cd_literal_2 != "") {
    	parent.detail.document.frm00.cmbOrderCom2.value = cd_literal_2;
    	parent.detail.document.frm00.cmbOrderCom2.onchange();
    }
    // �S���҃R�[�h�Q
    var cd_2nd_literal_2 = funXmlRead(xmlFGEN3730O, "cd_2nd_literal_2", 0);
    parent.detail.document.frm00.txtOrderUser2.value = cd_2nd_literal_2;

    // ���e
    var hyoji_naiyo = funXmlRead(xmlFGEN3730O, "hyoji_naiyo", 0);
    parent.detail.document.frm00.txtNaiyo.value = hyoji_naiyo;

    // �׎p
    var hyoji_nisugata = funXmlRead(xmlFGEN3730O, "hyoji_nisugata", 0);
    parent.detail.document.frm00.txtNisugata.value = hyoji_nisugata;
    // �Ώێ���
    var cd_taisyoshizai = funXmlRead(xmlFGEN3730O, "cd_taisyoshizai", 0);
    parent.detail.document.frm00.cmbTargetSizai.value = cd_taisyoshizai;
    //parent.detail.document.frm00.cmbTargetSizai.onchange();

    // �[����
    var hyoji_nounyusaki = funXmlRead(xmlFGEN3730O, "hyoji_nounyusaki", 0);
    parent.detail.document.frm00.txtDelivery.value = hyoji_nounyusaki;
    // �����ރR�[�hhidden
    var hyoji_cd_shizai = funXmlRead(xmlFGEN3730O, "hyoji_cd_shizai", 0);
    parent.detail.document.frm00.txtOldShizai.value = hyoji_cd_shizai;
    // �V���ރR�[�hhidden
    var hyoji_cd_shizai_new = funXmlRead(xmlFGEN3730O, "hyoji_cd_shizai_new", 0);
    parent.detail.document.frm00.txtNewShizai.value = hyoji_cd_shizai_new;
    // �d�l�ύX
    var shiyohenko = funXmlRead(xmlFGEN3730O, "shiyohenko", 0);
    if (shiyohenko == 1) {
    	parent.detail.document.frm00.specificationChange.checked = true;
    	parent.detail.document.frm00.specificationChange_nasi.checked = false;
    } else if (shiyohenko == 2) {
    	parent.detail.document.frm00.specificationChange.checked = false;
    	parent.detail.document.frm00.specificationChange_nasi.checked = true;
    } else {

    	parent.detail.document.frm00.specificationChange.checked = true;
    	parent.detail.document.frm00.specificationChange_nasi.checked = false;
    }
    // �݌v�P
    var sekkei1 = funXmlRead(xmlFGEN3730O, "sekkei1", 0);
    parent.detail.document.frm00.txtDesign1.value = sekkei1;
    // �݌v2
    var sekkei2 = funXmlRead(xmlFGEN3730O, "sekkei2", 0);
    parent.detail.document.frm00.txtDesign2.value = sekkei2;
    // �݌v3
    var sekkei3 = funXmlRead(xmlFGEN3730O, "sekkei3", 0);
    parent.detail.document.frm00.txtDesign3.value = sekkei3;
    // �ގ�
    var zaishitsu = funXmlRead(xmlFGEN3730O, "zaishitsu", 0);
    parent.detail.document.frm00.txtZaishitsu.value = zaishitsu;
    // ���l
    var biko_tehai = funXmlRead(xmlFGEN3730O, "biko_tehai", 0);
    parent.detail.document.frm00.txtBiko.value = biko_tehai;
    // ����F
    var printcolor = funXmlRead(xmlFGEN3730O, "printcolor", 0);
    parent.detail.document.frm00.txtPrintColor.value = printcolor;
    // �F�ԍ�
    var no_color = funXmlRead(xmlFGEN3730O, "no_color", 0);
    parent.detail.document.frm00.txtColorNo.value = no_color;
    // �ύX���e�ڍ�
    var henkounaiyoushosai = funXmlRead(xmlFGEN3730O, "henkounaiyoushosai", 0);
    parent.detail.document.frm00.txtChangesDetail.value = henkounaiyoushosai;
    // �[��
    var nouki = funXmlRead(xmlFGEN3730O, "nouki", 0);
    parent.detail.document.frm00.txtDeliveryTime.value = nouki;
    // ����
    var suryo = funXmlRead(xmlFGEN3730O, "suryo", 0);
    parent.detail.document.frm00.txtQuantity.value = suryo;
    // �����ލ݌�
    var old_sizaizaiko = funXmlRead(xmlFGEN3730O, "old_sizaizaiko", 0);
    parent.detail.document.frm00.txtOldShizaiZaiko.value = old_sizaizaiko;
    // ����
    var rakuhan = funXmlRead(xmlFGEN3730O, "rakuhan", 0);
    parent.detail.document.frm00.txtRakuhan.value = rakuhan;



}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFH.Shima
// �쐬���F2009/09/12
// �����P�FXmlId  �FXMLID
// �����Q�FreqAry �F�@�\ID�ʑ��MXML(�z��)
// �����R�FMode   �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var header = parent.header.document.frm00;
    var detail = parent.detail.document.frm00;
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\��
        if (XmlId.toString() == "RGEN3200") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
        else if (XmlId.toString() == "RGEN3290"){
            switch(i){
            case 0://USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:
                // ������R�[�h
                var hattyusaki = "";
                switch(selectHattyusaki){
                case 1:
                    hattyusaki = detail.cmbOrderCom1.value;
                    break;
                case 2:
                    hattyusaki = detail.cmbOrderCom2.value;
                    break;
                }

                funXmlWrite_Tbl(reqAry[i], "table", "cd_hattyusaki", hattyusaki, 0);

            }
        }
        // �폜
        else if(XmlId.toString() == "RGEN3320"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3370

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {

	                var objShain      = detail.hdnShain.value.split(ConDelimiter);
	                var objNen        = detail.hdnNen.value.split(ConDelimiter);
	                var objNoOi       = detail.hdnNoOi.value.split(ConDelimiter);
	                var objSeqShizai  = detail.hdnSeqShizai.value.split(ConDelimiter);
	                var objNoEda      = detail.hdnNoEda.value.split(ConDelimiter);
	                var objShohinCd   = detail.hdnCdShohin.value.split(ConDelimiter);

	                // �z��̐����擾
	                var max_row = objShain.length;

	                for(var j = 0;j < max_row; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3320", "table");
	                    }

	                    // �Ј��R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", objShain[j], j);
	                    // �N�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", objNen[j], j);
	                    // �ǔ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", objNoOi[j], j);
	                    // seq����
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", objSeqShizai[j], j);
	                    // �}��
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", objNoEda[j], j);
	                    // ���i�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", objShohinCd[j], j);
	                    // �敪
	                    funXmlWrite_Tbl(reqAry[i], "table", "kbn", "2", j);
	                }

            	} else {

            		sizaiNyuryokuKey(reqAry[i]);

            	}
            }
        }
        // �o�^
        else if (XmlId.toString() == "RGEN3360"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:

            	var element = parent.detail.document.getElementById("sizaid");
            	// �����I��
            	if (element.hasChildNodes()) {

	            	var len = parent.detail.document.frm00.loopCnt.value;
	            	var num = Number(len);
	            	for (var index = 0; index < num; index++) {

		              	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
		              	var result = data.split(":::");

	                    if(index != 0){
	                    	funAddRecNode_Tbl(reqAry[i], "FGEN3360", "table");
	                    }

	                    // �Ј��R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", result[0], index);
	                    // �N�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", result[1], index);
	                    // �ǔ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", result[2], index);
	                    // �}��
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", result[4], index);
	                    // seq����
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_no", result[3], index);

	                    // �݌v�@
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, index);
	                    // �݌v�A
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, index);
	                    // �݌v�B
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, index);
	                    // �ގ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, index);
	                    // ����F
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, index);
	                    // �F�ԍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, index);

	                    // �ǉ�
	                    // ���l
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, index);
	                    // �ύX���e�ڍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkou", detail.txtChangesDetail.value, index);
	                    // �[��
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, index);
	                    // ����
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, index);


                        // �ǉ�updateData
                        // ���e
                        funXmlWrite_Tbl(reqAry[i], "table", "naiyo", result[8], index);
                        // ���i�R�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", result[5], index);
                        // ���i��
                        funXmlWrite_Tbl(reqAry[i], "table", "nm_shohin", result[6], index);
                        // �[����
                        funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", result[9], index);
                        // �����ރR�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", result[10], index);
                        // �V���ރR�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai_new", result[11], index);
	                }


                } else {

	                for(var j = 0;j < 1; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3360", "table");
	                    }

	                    // �Ј��R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", detail.cd_shain.value, j);
	                    // �N�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", detail.nen.value, j);
	                    // �ǔ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", detail.no_oi.value, j);
	                    // �}��
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", detail.no_eda.value, j);
	                    // seq����
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_no", detail.seq_shizai.value, j);

	                    // �݌v�@
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, j);
	                    // �݌v�A
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, j);
	                    // �݌v�B
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, j);
	                    // �ގ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, j);
	                    // ����F
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, j);
	                    // �F�ԍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, j);

		                // �ǉ�
		                // ���l
		                funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, j);
		                // �ύX���e�ڍ�
		                funXmlWrite_Tbl(reqAry[i], "table", "henkou", detail.txtChangesDetail.value, j);
		                // �[��
		                funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, j);
		                // ����
		                funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, j);


	                    // �ǉ�updateData
	                    // ���e
	                    funXmlWrite_Tbl(reqAry[i], "table", "naiyo", "", j);
	                    // ���i�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", detail.shohinCd.value, j);
	                    // ���i��
	                    funXmlWrite_Tbl(reqAry[i], "table", "nm_shohin", detail.hinmei.value, j);
	                    // �[����
	                    funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", detail.delivery.value, j);
	                    // �����ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", detail.txtOldShizai.value, j);
	                    // �V���ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai_new", detail.txtNewShizai.value, j);


	                }
                }

                break;
            }
        }
        // Excel�o��
        else if(XmlId.toString() == "RGEN3370"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3370

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {

            		var len = parent.detail.document.frm00.loopCnt.value;
            		var num = Number(len);

            		// �f�[�^�����[�v
            		for (var exCnt = 0; exCnt < num; exCnt++) {

            	    	var data = parent.detail.document.getElementById("sizaiInser_" + exCnt).value;
            	    	var result = data.split(":::");

            	        if(exCnt != 0){
            	            funAddRecNode_Tbl(reqAry[i], "RGEN3370", "table");
            	        }

                        //-------------------PDF�p------------------------

                        // �Ј��R�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", result[0], exCnt);
                        // �N�R�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "nen", result[1], exCnt);
                        // �ǔ�
                        funXmlWrite_Tbl(reqAry[i], "table", "no_oi", result[2], exCnt);
                        // seq����
                        funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", result[3], exCnt);
                        // �}��
                        funXmlWrite_Tbl(reqAry[i], "table", "no_eda", result[4], exCnt);
                        // ���i�R�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohinPDF", result[5], exCnt);


                        // �V��/����
                        var valRevision = "";
                        for(var j = 0; j < detail.revision.length; j++){
                            if(detail.revision[j].checked){
                                valRevision = detail.revision[j].value;
                                break;
                            }
                        }

                        funXmlWrite_Tbl(reqAry[i], "table", "revision", valRevision, exCnt);
                        // ������P(���)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_com1", detail.cmbOrderCom1.value, exCnt);
                        // ������P(���[�U)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_user1", detail.txtOrderUser1.value, exCnt);
                        // ������Q(���)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_com2", detail.cmbOrderCom2.value, exCnt);
                        // ������Q(���[�U)
                        funXmlWrite_Tbl(reqAry[i], "table", "hattyusaki_user2", detail.txtOrderUser2.value, exCnt);
                        // ���e
                        funXmlWrite_Tbl(reqAry[i], "table", "naiyo", detail.txtNaiyo.value, exCnt);
                        // ���i�R�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", detail.txtSyohin.value, exCnt);
                        // �i��
                        funXmlWrite_Tbl(reqAry[i], "table", "hinmei", detail.txtHinmei.value, exCnt);
                        // �׎p
                        funXmlWrite_Tbl(reqAry[i], "table", "nisugata", detail.txtNisugata.value, exCnt);
                        // �Ώێ���
                        funXmlWrite_Tbl(reqAry[i], "table", "taisyosizai", detail.cmbTargetSizai.value, exCnt);
                        // ������
                        //funXmlWrite(reqAry[i], "table", "hattyusaki", detail.cmbOrder.value, 0);
                        // �[����
                        funXmlWrite_Tbl(reqAry[i], "table", "nounyusaki", detail.txtDelivery.value, exCnt);
                        // �����ރR�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "old_shizai_cd", detail.txtOldShizai.value, exCnt);
                        // �V���ރR�[�h
                        funXmlWrite_Tbl(reqAry[i], "table", "new_shizai_cd", detail.txtNewShizai.value, exCnt);
                        // �d�l�ύX
                        var valSiyohenko = "";
                        for(var j = 0; j < detail.specificationChange.length; j++){
                            if(detail.specificationChange[j].checked){
                                valSiyohenko = detail.specificationChange[j].value;
                                break;
                            }
                        }
                        funXmlWrite_Tbl(reqAry[i], "table", "siyohenko", valSiyohenko, exCnt);
                        // �݌v�@
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, exCnt);
                        // �݌v�A
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, exCnt);
                        // �݌v�B
                        funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, exCnt);
                        // �ގ�
                        funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, exCnt);
                        // ���l
                        funXmlWrite_Tbl(reqAry[i], "table", "biko_tehai", detail.txtBiko.value, exCnt);
                        // ����F
                        funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, exCnt);
                        // �F�ԍ�
                        funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, exCnt);
                        // �ύX���e�ڍ�
                        funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, exCnt);
                        // �[��
                        funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, exCnt);
                        // ����
                        funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, exCnt);
                        // �����ލ݌�
                        funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, exCnt);
                         // ����
                        funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, exCnt);


            		}
            	// ���ޓ���
            	} else {

                    // �V��/����
                    var valRevision = "";
                    for(var j = 0; j < detail.revision.length; j++){
                        if(detail.revision[j].checked){
                            valRevision = detail.revision[j].value;
                            break;
                        }
                    }
                    funXmlWrite(reqAry[i], "revision", valRevision, 0);
                    // ������P(���)
                    funXmlWrite(reqAry[i], "hattyusaki_com1", detail.cmbOrderCom1.value, 0);
                    // ������P(���[�U)
                    funXmlWrite(reqAry[i], "hattyusaki_user1", detail.txtOrderUser1.value, 0);
                    // ������Q(���)
                    funXmlWrite(reqAry[i], "hattyusaki_com2", detail.cmbOrderCom2.value, 0);
                    // ������Q(���[�U)
                    funXmlWrite(reqAry[i], "hattyusaki_user2", detail.txtOrderUser2.value, 0);
                    // ���e
                    funXmlWrite(reqAry[i], "naiyo", detail.txtNaiyo.value, 0);

                    // ���i�R�[�h
                    funXmlWrite(reqAry[i], "cd_shohin", detail.txtSyohin.value, 0);
                    // �i��
                    funXmlWrite(reqAry[i], "hinmei", detail.txtHinmei.value, 0);
                    // �׎p
                    funXmlWrite(reqAry[i], "nisugata", detail.txtNisugata.value, 0);
                    // �Ώێ���
                    funXmlWrite(reqAry[i], "taisyosizai", detail.cmbTargetSizai.value, 0);
                    // ������
                    //funXmlWrite(reqAry[i], "hattyusaki", detail.cmbOrder.value, 0);
                    // �[����
                    funXmlWrite(reqAry[i], "nounyusaki", detail.txtDelivery.value, 0);
                    // �����ރR�[�h
                    funXmlWrite(reqAry[i], "old_shizai_cd", detail.txtOldShizai.value, 0);
                    // �V���ރR�[�h
                    funXmlWrite(reqAry[i], "new_shizai_cd", detail.txtNewShizai.value, 0);
                    // �d�l�ύX
                    var valSiyohenko = "";
                    for(var j = 0; j < detail.specificationChange.length; j++){
                        if(detail.specificationChange[j].checked){
                            valSiyohenko = detail.specificationChange[j].value;
                            break;
                        }
                    }
                    funXmlWrite(reqAry[i], "siyohenko", valSiyohenko, 0);
                    // �݌v�@
                    funXmlWrite(reqAry[i], "sekkei1", detail.txtDesign1.value, 0);
                    // �݌v�A
                    funXmlWrite(reqAry[i], "sekkei2", detail.txtDesign2.value, 0);
                    // �݌v�B
                    funXmlWrite(reqAry[i], "sekkei3", detail.txtDesign3.value, 0);
                    // �ގ�
                    funXmlWrite(reqAry[i], "zaishitsu", detail.txtZaishitsu.value, 0);
                    // ���l
                    funXmlWrite(reqAry[i], "biko_tehai", detail.txtBiko.value, 0);
                    // ����F
                    funXmlWrite(reqAry[i], "printcolor", detail.txtPrintColor.value, 0);
                    // �F�ԍ�
                    funXmlWrite(reqAry[i], "no_color", detail.txtColorNo.value, 0);
                    // �ύX���e�ڍ�
                    funXmlWrite(reqAry[i], "henkounaiyoushosai", detail.txtChangesDetail.value, 0);
                    // �[��
                    funXmlWrite(reqAry[i], "nouki", detail.txtDeliveryTime.value, 0);
                    // ����
                    funXmlWrite(reqAry[i], "suryo", detail.txtQuantity.value, 0);
                    // �����ލ݌�
                    funXmlWrite(reqAry[i], "old_sizaizaiko", detail.txtOldShizaiZaiko.value, 0);
                     // ����
                    funXmlWrite(reqAry[i], "rakuhan", detail.txtRakuhan.value, 0);

                    //-------------------PDF�p------------------------

                    // �Ј��R�[�h
                	funXmlWrite(reqAry[i], "cd_shain", detail.cd_shain.value, 0);
                    // �N�R�[�h
                	funXmlWrite(reqAry[i], "nen", detail.nen.value, 0);
                    // �ǔ�
                	funXmlWrite(reqAry[i], "no_oi", detail.no_oi.value, 0);
                    // seq����
                	funXmlWrite(reqAry[i], "seq_shizai", detail.seq_shizai.value, 0);
                    // �}��
                	funXmlWrite(reqAry[i], "no_eda", detail.no_eda.value, 0);
            	}

                break;

            case 2:    // FGEN3380

            	var kbn = detail.flg_hatyuu_status.value;

            	var element = parent.detail.document.getElementById("sizaid");
            	// �����I��
            	if (element.hasChildNodes()) {

            		heddenParamSet("FGEN3380",reqAry[i]);

            		// ���ޓ���
            	} else {

            		// �敪
            		funXmlWrite(reqAry[i], "kbn", "1", index);
                    // �Ј��R�[�h
                	funXmlWrite(reqAry[i], "cd_shain", detail.cd_shain.value, 0);
                    // �N�R�[�h
                	funXmlWrite(reqAry[i], "nen", detail.nen.value, 0);

                    // �ǔ�
                	funXmlWrite(reqAry[i], "no_oi", detail.no_oi.value, 0);

                    // seq����
                	funXmlWrite(reqAry[i], "seq_shizai", detail.seq_shizai.value, 0);

                    // �}��
                	funXmlWrite(reqAry[i], "no_eda", detail.no_eda.value, 0);

                	// ���i�R�[�h
                	funXmlWrite(reqAry[i], "cd_shohin", detail.shohinCd.value, 0);
                	// ������R�[�h�P
                	funXmlWrite(reqAry[i], "cd_hattyuusaki", detail.cmbOrderCom1.value, 0);
                	// �Ώێ��ރR�[�h
                	funXmlWrite(reqAry[i], "cd_taisyoshizai", detail.cmbTargetSizai.value, 0);

            	}

                break;
            }
        }
        // �폜����_��z�ς݊m�F
        else if(XmlId.toString() == "RGEN3390"){
            switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3390

            	var element = parent.detail.document.getElementById("sizaid");
            	if (element.hasChildNodes()) {


	                var objShain      = detail.hdnShain.value.split(ConDelimiter);
	                var objNen        = detail.hdnNen.value.split(ConDelimiter);
	                var objNoOi       = detail.hdnNoOi.value.split(ConDelimiter);
	                var objSeqShizai  = detail.hdnSeqShizai.value.split(ConDelimiter);
	                var objNoEda      = detail.hdnNoEda.value.split(ConDelimiter);
	                var objShohinCd   = detail.hdnCdShohin.value.split(ConDelimiter);

	                // �z��̐����擾
	                var max_row = objShain.length;

	                for(var j = 0;j < max_row; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3390", "table");
	                    }

	                    // �Ј��R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", objShain[j], j);
	                    // �N�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "nen", objNen[j], j);
	                    // �ǔ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_oi", objNoOi[j], j);
	                    // seq����
	                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", objSeqShizai[j], j);
	                    // �}��
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", objNoEda[j], j);
	                    // ���i�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shohin", objShohinCd[j], j);
	                    // �敪
	                    funXmlWrite_Tbl(reqAry[i], "table", "kbn", "2", j);

	                }

            	} else {
            		sizaiNyuryokuKey(reqAry[i]);
            	}
            }
        }
        // ���ۑ�����
        else if(XmlId.toString() == "RGEN3700") {

        	switch (i) {

            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:

                var objShain      = detail.hdnShain.value.split(ConDelimiter);
                // ����
                if (objShain != "") {

	                // ���ޓY�t�e�[�u���C���T�[�g�p
                	var loopCnt = detail.loopCnt.value;
                	var loopCntNum = parseInt(loopCnt);

	                for (var sizaiCnt = 0; sizaiCnt < loopCntNum; sizaiCnt++) {

	                	var data = parent.detail.document.getElementById("sizaiInser_" + sizaiCnt).value;
	                	var result = data.split(":::");

	                    if(sizaiCnt != 0){
	                    	funAddRecNode_Tbl(reqAry[i], "FGEN3700", "table");
	                    }
	                	// �Ј��R�[�h
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shain", result[0], sizaiCnt);
	                	// �N
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_nen", result[1],  sizaiCnt);
	                	// �ǔ�
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_no_oi", result[2], sizaiCnt);
	                	// seq�ԍ�
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_seq_shizai", result[3], sizaiCnt);
	                	// �}��
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_no_eda", result[4], sizaiCnt);
	                	// �敪�i�����I������̏ꍇ�敪�F2�j
	                	funXmlWrite_Tbl(reqAry[i], "table", "insert_kbn_shizai", 2, sizaiCnt);
	                    // �V�Ł^����
	                    var revision_new = parent.detail.document.getElementById("revision_new").checked;
	                    if (revision_new) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 1, sizaiCnt)
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 0, sizaiCnt)
	                    }

	                	// ������P
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_literal1", detail.cmbOrderCom1.value, sizaiCnt);
	                    // �S���҂P
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal1", detail.txtOrderUser1.value, sizaiCnt);
	                	// ������Q
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_liiteral2", detail.cmbOrderCom2.value, sizaiCnt);
	                    // �S���҂Q
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal2", detail.txtOrderUser2.value, sizaiCnt);
	                    //
	                    // ���e
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_naiyo", result[8], sizaiCnt);
	                    // ���i�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shohin", result[5], sizaiCnt);
	                    // ���i��
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_shohin", result[6], sizaiCnt);
	                    // �׎p
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nisugata", result[7], sizaiCnt);
	                    // �Ώێ���
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_taisyoshizai", detail.cmbTargetSizai.value, sizaiCnt);
	                    // �[����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nounyusaki", result[9], sizaiCnt);
	                    // �����ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai", result[10], sizaiCnt);
	                    // �V���ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai_new", result[11], sizaiCnt);
	                    // �d�l�ύX
	                    if (parent.detail.document.getElementById("specificationChange_ari").checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 1, sizaiCnt);
	                    } else if (parent.detail.document.getElementById("specificationChange_nasi").checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 2, sizaiCnt);
	                    }
	                    // �݌v�@
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, sizaiCnt);
	                    // �݌v�A
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, sizaiCnt);
	                    // �݌v�B
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, sizaiCnt);
	                    // �ގ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, sizaiCnt);
	                    // ���l
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, sizaiCnt);// *
	                    // ����F
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, sizaiCnt);
	                    // �F�ԍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, sizaiCnt);
	                    // �ύX���e�ڍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, sizaiCnt);
	                    // �[��
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, sizaiCnt);
	                    // ����
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, sizaiCnt);
	                    // �����ލ݌�
	                    funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, sizaiCnt);
	                    // ����
	                    funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, sizaiCnt);

	                    // �\�����e
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // �\���׎p
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nisugata", detail.txtNisugata.value, sizaiCnt);
	                    // �\���[����
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nounyusaki", detail.txtDelivery.value, sizaiCnt);
	                    // �\�������ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai", detail.txtOldShizai.value, sizaiCnt);
	                    // �\���V���ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai_new", detail.txtNewShizai.value, sizaiCnt);
	                    // �ǉ�
	                    // �ő�x����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_dt_han_payday", result[13], sizaiCnt);
	                    // �ő�
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_han_pay", result[14], sizaiCnt);
	                    // �ăt�@�C����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_file_aoyaki", result[15], sizaiCnt);
	                    // �ăt�@�C���p�X
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_path_aoyaki", result[16], sizaiCnt);

	                }
	                // ���ގ�z���͂���J�ڂ̎�
                } else {

	                for(var j = 0;j < 1; j++){

	                    if(j != 0){
	                        funAddRecNode_Tbl(reqAry[i], "FGEN3700", "table");
	                    }

	                    // �Ј��R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shain", detail.cd_shain.value, j);
	                    // �N�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nen", detail.nen.value, j);
	                    // �ǔ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_no_oi", detail.no_oi.value, j);
	                    // �}��
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_no_eda", detail.no_eda.value, j);
	                    // seq����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_seq_shizai", detail.seq_shizai.value, j);
	                    // �敪���� 	���ގ�z���͂���̏ꍇ�敪�F
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_kbn_shizai", detail.flg_hatyuu_status.value, j);

	                    // �V�Ł^����
	                    var revision_new = parent.detail.document.getElementById("revision_new").checked;
	                    if (revision_new) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 1, sizaiCnt)
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_han", 0, sizaiCnt)
	                    }

	                	// ������P
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_literal1", detail.cmbOrderCom1.value, sizaiCnt);
	                    // �S���҂P
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal1", detail.txtOrderUser1.value, sizaiCnt);
	                	// ������Q
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_liiteral2", detail.cmbOrderCom2.value, sizaiCnt);
	                    // �S���҂Q
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_2nd_literal2", detail.txtOrderUser2.value, sizaiCnt);

	                    // ���e
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // ���i�R�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shohin", detail.shohinCd.value, sizaiCnt);

	                    // ���i��
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_shohin", detail.hinmei.value, sizaiCnt);
	                    // �׎p
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nisugata", detail.nisugata.value, sizaiCnt);
	                    // �Ώێ���
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_taisyoshizai", detail.cmbTargetSizai.value, sizaiCnt);
	                    // �[����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nounyusaki", detail.delivery.value, sizaiCnt);
	                    // �����ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai", detail.olsShizai.value, sizaiCnt);
	                    // �V���ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_cd_shizai_new", detail.newShizai.value, sizaiCnt);
	                    // �d�l�ύX
	                    if (detail.specificationChange.checked) {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 1, sizaiCnt);
	                    } else {
	                    	funXmlWrite_Tbl(reqAry[i], "table", "insert_shiyohenko", 0, sizaiCnt);
	                    }

	                    // �݌v�@
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei1", detail.txtDesign1.value, j);
	                    // �݌v�A
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei2", detail.txtDesign2.value, j);
	                    // �݌v�B
	                    funXmlWrite_Tbl(reqAry[i], "table", "sekkei3", detail.txtDesign3.value, j);
	                    // �ގ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "zaishitsu", detail.txtZaishitsu.value, sizaiCnt);
	                    // ���l
	                    funXmlWrite_Tbl(reqAry[i], "table", "biko", detail.txtBiko.value, sizaiCnt);// *
	                    // ����F
	                    funXmlWrite_Tbl(reqAry[i], "table", "printcolor", detail.txtPrintColor.value, sizaiCnt);
	                    // �F�ԍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "no_color", detail.txtColorNo.value, sizaiCnt);
	                    // �ύX���e�ڍ�
	                    funXmlWrite_Tbl(reqAry[i], "table", "henkounaiyoushosai", detail.txtChangesDetail.value, sizaiCnt);
	                    // �[��
	                    funXmlWrite_Tbl(reqAry[i], "table", "nouki", detail.txtDeliveryTime.value, sizaiCnt);
	                    // ����
	                    funXmlWrite_Tbl(reqAry[i], "table", "suryo", detail.txtQuantity.value, sizaiCnt);
	                    // �����ލ݌�
	                    funXmlWrite_Tbl(reqAry[i], "table", "old_sizaizaiko", detail.txtOldShizaiZaiko.value, sizaiCnt);
	                    // ����
	                    funXmlWrite_Tbl(reqAry[i], "table", "rakuhan", detail.txtRakuhan.value, sizaiCnt);

	                    // �\�����e
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_naiyo", detail.txtNaiyo.value, sizaiCnt);
	                    // �\���׎p
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nisugata", detail.txtNisugata.value, sizaiCnt);
	                    // �\���[����
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_nounyusaki", detail.txtDelivery.value, sizaiCnt);
	                    // �\�������ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai", detail.txtOldShizai.value, sizaiCnt);
	                    // �\���V���ރR�[�h
	                    funXmlWrite_Tbl(reqAry[i], "table", "hyoji_cd_shizai_new", detail.txtNewShizai.value, sizaiCnt);
	                    // �ǉ�
	                    // �ő�x����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_dt_han_payday", "", sizaiCnt);
	                    // �ő�
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_han_pay", "", sizaiCnt);
	                    // �ăt�@�C����
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_nm_file_aoyaki", "", sizaiCnt);
	                    // �ăt�@�C���p�X
	                    funXmlWrite_Tbl(reqAry[i], "table", "insert_file_path_aoyaki", "", sizaiCnt);
	                }
                }

                break;
        	}
        }
        // ���ގ�z���͂���J�ڎ��A���ގ�z�e�[�u���ɐ�������f�[�^������ꍇ�ݒ肷��B
        else if (XmlId.toString() == "RGEN3730") {
        	switch (i) {

            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;

            case 1:
            	var kbn = funXmlRead(reqAry[1], "kbn", 0);

            	if (kbn == 1) {

            		heddenParamSetNyuryoku("FGEN3730", reqAry[i]);

            	}
            	else {
            		heddenParamSet("FGEN3730",reqAry[i])            	}

        	}
        }

    }

    return true;

}
//========================================================================================
//�����I�� ���ۑ����A�o�^
//�쐬�ҁFt2nakamura
//�쐬���F2016/11/04
//����  �F
//�T�v  �F
//========================================================================================
function heddenParamSetNyuryoku(key, xmlNo) {

	for (var index = 0; index < 1; index++) {

        if(index != 0){
            funAddRecNode_Tbl(xmlNo, key, "table");
        }
		// �敪
        funXmlWrite_Tbl(xmlNo, "table", "kbn", funXmlRead(xmlNo, "kbn", 0), index);
        // �Ј��R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "cd_shain", funXmlRead(xmlNo, "cd_shain", 0), index);
        // �N�R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "nen", funXmlRead(xmlNo, "nen", 0), index);
        // �ǔ�
        funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", funXmlRead(xmlNo, "seq_shizai", 0), index);
        // seq����
        funXmlWrite_Tbl(xmlNo, "table", "no_oi", funXmlRead(xmlNo, "no_oi", 0), index);
        // �}��
        funXmlWrite_Tbl(xmlNo, "table", "no_eda", funXmlRead(xmlNo, "no_eda", 0), index);
        // ���i�R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",funXmlRead(xmlNo, "cd_shohin", 0), index);
        // �Ώێ���
        funXmlWrite_Tbl(xmlNo, "table", "cd_taisyoshizai","", index);


	}
}


//========================================================================================
//�����I�� ���ۑ����A�o�^
//�쐬�ҁFt2nakamura
//�쐬���F2016/11/04
//����  �F
//�T�v  �F
//========================================================================================
function heddenParamSet(key, xmlNo) {

	var len = parent.detail.document.frm00.loopCnt.value;
	var num = Number(len);
	for (var index = 0; index < num; index++) {

    	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
    	var result = data.split(":::");

        if(index != 0){
            funAddRecNode_Tbl(xmlNo, key, "table");
        }

		// �敪
        funXmlWrite_Tbl(xmlNo, "table", "kbn", "2", index);
        // �Ј��R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "cd_shain", result[0], index);
        // �N�R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "nen", result[1], index);
        // �ǔ�
        funXmlWrite_Tbl(xmlNo, "table", "no_oi", result[2], index);
        // seq����
        funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", result[3], index);
         // �}��
        funXmlWrite_Tbl(xmlNo, "table", "no_eda", result[4], index);
        // ���i�R�[�h
        funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",result[5], index);
    	// ������R�[�h�P
        funXmlWrite_Tbl(xmlNo, "table", "cd_hattyuusaki", parent.detail.document.frm00.cmbOrderCom1.value, 0);
    	// �Ώێ��ރR�[�h
        funXmlWrite_Tbl(xmlNo, "table", "cd_taisyoshizai", parent.detail.document.frm00.cmbTargetSizai.value, 0);

	}
}


function sizaiNyuryokuKey(xmlNo) {

    // �Ј��R�[�h
	funXmlWrite(xmlNo, "cd_shain", parent.detail.document.frm00.cd_shain.value, 0);
    // �N�R�[�h
	funXmlWrite(xmlNo, "nen", parent.detail.document.frm00.nen.value, 0);
    // �ǔ�
	funXmlWrite(xmlNo, "no_oi", parent.detail.document.frm00.no_oi.value, 0);
    // seq����
	funXmlWrite(xmlNo, "seq_shizai", parent.detail.document.frm00.seq_shizai.value, 0);
    // �}��
	funXmlWrite(xmlNo, "no_eda", parent.detail.document.frm00.no_eda.value, 0);
	// ���i�R�[�h
	funXmlWrite(xmlNo, "cd_shohin", parent.detail.document.frm00.shohinCd.value, 0);
	// �敪
	funXmlWrite(xmlNo, "kbn", parent.detail.document.frm00.flg_hatyuu_status.value, 0);


}

//========================================================================================
//�����I�� ���i���A�o�^
//�쐬�ҁFt2nakamura
//�쐬���F2016/11/10
//����  �F
//�T�v  �F
//========================================================================================
function heddenParamSetToroku(key, xmlNo) {

	var len = parent.detail.document.frm00.loopCnt.value;
	var num = Number(len);
	for (var index = 0; index < num; index++) {

  	var data = parent.detail.document.getElementById("sizaiInser_" + index).value;
  	var result = data.split(":::");

      if(index != 0){
          funAddRecNode_Tbl(xmlNo, key, "table");
      }

		// �敪
      funXmlWrite_Tbl(xmlNo, "table", "kbn", "2", index);
      // �Ј��R�[�h
      funXmlWrite_Tbl(xmlNo, "table", "cd_shain", result[0], index);
      // �N�R�[�h
      funXmlWrite_Tbl(xmlNo, "table", "nen", result[1], index);
      // �ǔ�
      funXmlWrite_Tbl(xmlNo, "table", "no_oi", result[2], index);
      // seq����
      funXmlWrite_Tbl(xmlNo, "table", "seq_shizai", result[3], index);
       // �}��
      funXmlWrite_Tbl(xmlNo, "table", "no_eda", result[4], index);
      // ���i�R�[�h
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin",result[5], index);
      // ���i��
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin_nm",result[6], index);
      // �[����
      funXmlWrite_Tbl(xmlNo, "table", "cd_shohin_nm",result[6], index);
      // �����ރR�[�h

      // �V���ރR�[�h





	}
}




//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/19
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
        case 1:    // ������Ͻ�
            atbName = "nm_hattyusaki";
            atbCd   = "cd_hattyusaki";
            break;

        case 2:    // ���e�����}�X�^
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;
        case 3:    // cho������S����
            atbName = "nm_tantosha";
            atbCd   = "cd_tantosha";
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
// ���Ń��W�I�{�^���؂�ւ�����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F���ڂ̕\����Ԃ�؂�ւ���
//========================================================================================
function funKaihan(mode){
    var frm = document.frm00;    //̫�тւ̎Q��

    switch(mode){
    case 1:
        // �����ރR�[�h�̔�\��
        frm.txtOldShizai.style.visibility                     = 'hidden';
        // �d�l�ύX���W�I�{�^���̔�\��
        document.getElementById('siyohenko').style.visibility = 'hidden';
        // �����ލ݌ɂ̔�\��
        frm.txtOldShizaiZaiko.style.visibility                = 'hidden';
//        frm.btnOldShizaiZaikoCheck.style.visibility           = 'hidden';
        // ���ł̔�\��
        frm.txtRakuhan.style.visibility                       = 'hidden';
//        frm.btnRakuhanCheck.style.visibility                  = 'hidden';
        break;

    case 2:
        // �����ރR�[�h�̕\��
        frm.txtOldShizai.style.visibility                     = 'visible';
        // �d�l�ύX���W�I�{�^���̕\��
        document.getElementById('siyohenko').style.visibility = 'visible';
        // �����ލ݌ɂ̕\��
        frm.txtOldShizaiZaiko.style.visibility                = 'visible';
//        frm.btnOldShizaiZaikoCheck.style.visibility           = 'visible';
        // ���ł̕\��
        frm.txtRakuhan.style.visibility                       = 'visible';
 //       frm.btnRakuhanCheck.style.visibility                  = 'visible';
        break;
    }
}
//========================================================
//test
//==========================================================
function funtest()
{



	  var Syohin = parent.detail.document.frm00.hdnSyohin_disp.value

	  var Hinmei = parent.detail.document.frm00.hdnHinmei_disp.value;

	  var Nisugata = parent.detail.document.frm00.hdnNisugata_disp.value;

	  var OldShizai = parent.detail.document.frm00.hdnOldShizai_disp.value;

	  var hdnNewShizai = parent.detail.document.frm00.hdnNewShizai_disp.value;

	  var txtDesign1 = parent.detail.document.frm00.txtDesign1.value

	  var txtDesign2 = parent.detail.document.frm00.txtDesign2.value

	  var txtDesign3 = parent.detail.document.frm00.txtDesign3.value

	  var txtPrintColor = parent.detail.document.frm00.txtPrintColor.value

	  var txtColorNo = parent.detail.document.frm00.txtColorNo.value



}

//========================================================================================
// ���ތ�����ʕ\������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/12
// �T�v  �F���ތ�����ʂ�\������
//========================================================================================
function funShizaiSearch(mode){

    var header = parent.header.document.frm00;
    var detail = parent.detail.document.frm00;

    var wUrl;

    switch(mode){
    case 1:
    	// ���ގ�z���͂���̃f�[�^�ێ�
    	// �Ώې�R�[�h
    	var cmbTargetSizai =detail.cmbTargetSizai.value;
    	// ���i�R�[�h
    	var txtSyohin = detail.txtSyohin.value;
    	// �i��
    	var txtHinmei = detail.txtHinmei.value;
    	// �׎p
    	var txtNisugata =  detail.txtNisugata.value;
    	// �����ރR�[�h
    	var txtOldShizai =  detail.txtOldShizai.value;
    	// �V���ރR�[�h
    	var txtNewShizai = detail.txtNewShizai.value;
    	// ���e
    	var txtNaiyo = detail.txtNaiyo.value;
    	// �[����
    	var txtDelivery = detail.txtDelivery.value;

    	var put_code = cmbTargetSizai + ":::" + txtSyohin + ":::" + txtHinmei + ":::"
    					+ txtNisugata + ":::" + txtOldShizai + ":::" + txtNewShizai + ":::"
    					+ txtNaiyo + ":::" + txtDelivery + ":::";
    	funXmlWrite(xmlUSERINFO_I, "MOVEMENT_CONDITION", put_code, 0);
    	// �ǂݏo�� funXmlRead(xmlUSERINFO_I, "MOVEMENT_CONDITION", 0)

        // ���ރR�[�h�������
        var logic = "shizai"
        wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;

        funModalCall(wUrl);

        if (detail.hdnShain.value != "") {


	        // �ԋp�����ڍ׉�ʂɕ\��
	        detail.cmbTargetSizai.value = detail.hdnTaisyosizai_disp.value; // �Ώێ���

	        detail.txtSyohin.value      = detail.hdnSyohin_disp.value;      // ���i�R�[�h
	        detail.txtHinmei.value      = detail.hdnHinmei_disp.value;      // �i��
	        detail.txtNisugata.value    = detail.hdnNisugata_disp.value;    // �׎p
	        detail.txtOldShizai.value   = detail.hdnOldShizai_disp.value;   // �����ރR�[�h
	        detail.txtNewShizai.value   = detail.hdnNewShizai_disp.value;   // �V���ރR�[�h

	        detail.txtNaiyo.value       = detail.hdnNaiyo_disp.value;		// ���e
	        detail.txtDelivery.value    = detail.hdnNounyusaki_disp.value;	// �[����

	        // ���ޓY�t�e�[�u������Ď擾
	        funTempSearch(new Array(1));
	        // �ҏW�t���O��true��
	        setFg_hensyu();

	     // �I�����Ȃ��ŏI���{�^�������������ꍇ���ގ�z���͂���擾�����f�[�^��ݒ肵�Ȃ����B
    	} else if (detail.hdnShain.value == "" && txtNewShizai != ""){
    		var sizaitehaiData = funXmlRead(xmlUSERINFO_I, "MOVEMENT_CONDITION", 0)

    		var shizaitehaiList = sizaitehaiData.split(":::");

	        detail.cmbTargetSizai.value = shizaitehaiList[0]; // �Ώێ���

	        detail.txtSyohin.value      = shizaitehaiList[1];	// ���i�R�[�h
	        detail.txtHinmei.value      = shizaitehaiList[2];   // �i��
	        detail.txtNisugata.value    = shizaitehaiList[3];   // �׎p
	        detail.txtOldShizai.value   = shizaitehaiList[4];   // �����ރR�[�h
	        detail.txtNewShizai.value   = shizaitehaiList[5];   // �V���ރR�[�h

	        detail.txtNaiyo.value       = shizaitehaiList[6];	// ���e
	        detail.txtDelivery.value    = shizaitehaiList[7];	// �[1����
   	}

        break;

    case 2:
        // �I�������`�F�b�N�̏�����
        detail.hdnRuiziSelect.value = "false";

        // �ގ��i�������
        var logic = "ruizi"
        wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;
        funModalCall(wUrl);

        // �I�������ŉ�ʂ��I�����ꂽ�ꍇ
        if(detail.hdnRuiziSelect.value === "true"){

            funCheckSetting();

            // �ҏW�t���O��true��
            setFg_hensyu();
        }

        break;

    }

    // �Z�b�V�����폜
    funXmlWrite(xmlUSERINFO_I, "MOVEMENT_CONDITION", "", 0);
}

//========================================================================================
// ���[�_���N����������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/12
// �T�v  �F�Ώۂ̉�ʂ����[�_���_�C�A���O�ŕ\������
//========================================================================================
function funModalCall(wUrl){

    funOpenModalDialog(wUrl , this, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;scroll:no");

    return true;
}

//========================================================================================
// �@�\�J�ڏ���
// �쐬�ҁFH.Shima
// �쐬���F2014/10/20
// �T�v  �F�@�\�J�ڋ��ʊ֐�
//========================================================================================
function funNextLogic(mode){

    var detail = parent.detail.document.frm00;

    var shohin = detail.txtSyohin.value;

    // ���̓`�F�b�N�@���i�R�[�h
    if(shohin.length < 1) {
        funErrorMsgBox(E000036);
        return false;
    }

    switch(mode){
    case 1:
    	funUpDInsert(2);
    	funExcelOut(1);
        //funTorokuCheck(1);
        break;

    case 2:
        funUpDInsert(1);
        break;

    case 3:
        funDelete(1);
        break;
    }

    return true;
}

//========================================================================================
// �o�^�`�F�b�N
// �쐬�ҁFH.Shima
// �쐬���F2014/10/15
// �T�v  �F�e���ڂ̊m�F�`�F�b�N���s��
//========================================================================================
function funTorokuCheck(mode){

    // �S�Ă̍��ڂ��`�F�b�N���ꂽ��
    if(!funEditCheck()){
        // �x���\��
        funErrorMsgBox(E000037);
        return false;
    }

    // �m�Fү���ނ̕\��
    if (funConfMsgBox(I000002) != ConBtnYes) {
        return false;
    }

    funToroku(mode);

}

//========================================================================================
// �o�^����
// �쐬�ҁFH.Shima
// �쐬���F2014/10/03
// �T�v  �F�o�^���s��
//========================================================================================
function funToroku(mode) {
	var messagemode = 1;
    if(mode == 2) {
    	mode = 1;
    	messagemode = 2;
    }


	var header = parent.header.document.frm00; // ̫�тւ̎Q��
    var detail = parent.detail.document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3360";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3360");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3360I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3360O);

    //XML�̏�����
    setTimeout("xmlFGEN3360I.src = '../../model/FGEN3360I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }


    // �X�V����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3360, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    //�Čv�Z�t���O��OFF�ɂ���
     header.hdnHensyu.value = "false";
     if(messagemode == 1 ) {
    	 // �o�^�������b�Z�[�W
    	 funInfoMsgBox(I000005);
     }

    return true;
}

//========================================================================================
// EXCEL�o�͏���
// �쐬�ҁFH.Shima
// �쐬���F2014/10/07
// �T�v  �FExcel�o�͂��s��
//========================================================================================
function funExcelOut(mode) {
    var header = parent.header.document.frm00; // ̫�тւ̎Q��
    var detail = parent.detail.document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3370";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3370", "FGEN3380");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3370I,xmlFGEN3380I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3370O, xmlFGEN3380O);

    // �S�Ă̍��ڂ��`�F�b�N���ꂽ��
    if(!funEditCheck()){
        // �x���\��
        funErrorMsgBox(E000037);
        return false;
    }

//    // �ҏW����Ă��邩
//    var flgHensyu = header.hdnHensyu.value;
//
//    if(flgHensyu === "true"){
//        // �ҏW
//        if(funConfMsgBox(I000015) != ConBtnYes) {
//            // �o�͂𒆎~
//            return false;
//        } else {
//            // �o�^����
//            if(funToroku(1) == false){
//                return false;
//            }
//        }
//    }else{
//        // Excel�o�͊m�Fү���ނ̕\��
//        if (funConfMsgBox(I000008) != ConBtnYes) {
//            return false;
//        }
//    }

    // �o�^����
    if (funToroku(2) == true) {

//        // Excel�o�͊m�Fү���ނ̕\��
//        if (funConfMsgBox(I000008) != ConBtnYes) {
//            return false;
//       }
    } else {
    	return false;
    }

    //XML�̏�����
    setTimeout("xmlFGEN3370I.src = '../../model/FGEN3370I.xml';", ConTimer);
    setTimeout("xmlFGEN3380I.src = '../../model/FGEN3380I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // �o��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3370, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    header.strServerConst.value = "DOWNLOAD_FOLDER_TEMP";

    //̧���߽�̑ޔ�
    header.strFilePath.value = funXmlRead(xmlResAry[2], "URLValue", 0);

    // excelFileName
    header.strExcelFilePath.value = funXmlRead(xmlResAry[2], "excelFileName", 0);
    // pdfFileName
    header.strPdfFilePath.value = funXmlRead(xmlResAry[2], "pdfFileName", 0);

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect2(ConConectGet, header);

    // ���[���A�h���X�P
    header.strMaiAddress1.value = funXmlRead(xmlResAry[2], "mail_address1", 0);
    // ���[���A�h���X�Q
    header.strMaiAddress2.value = funXmlRead(xmlResAry[2], "mail_address2", 0);

    // ���[���[�N��
    setTimeout(funMailTo, ConTimer);

  //�߂�l�̐ݒ�
    window.returnValue = "3" + ":::" + detail.cmbOrderCom1.value + ":::" + detail.cmbTargetSizai.value;

    return true;
}

//========================================================================================
// ���ۑ��{�^������
// �쐬�ҁFt2nakamura
// �쐬���F2016/10/28
// �T�v  �F���ގ�z�e�[�u������z�ɍX�V�A���ގ�z�Y�t�e�[�u���o�^
//========================================================================================
function funUpDInsert(mode) {
 var messagemode = 1;
 if(mode == 2) {
 	mode = 1;
 	messagemode = 2;
 }

 var header = parent.header.document.frm00; // ̫�тւ̎Q��
 var detail = parent.detail.document.frm00; // ̫�тւ̎Q��

 var XmlId = "RGEN3700";
 var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3700");
 var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3700I);
 var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3700O);

 //XML�̏�����
 setTimeout("xmlFGEN3700I.src = '../../model/FGEN3700I.xml';", ConTimer);

 // ������XMĻ�قɐݒ�
 if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
     funClearRunMessage();
     return false;
 }


 // �X�V����
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3700, xmlReqAry, xmlResAry,
      mode) == false) {
     return false;
 }

 //�Čv�Z�t���O��OFF�ɂ���
  header.hdnHensyu.value = "false";
 if(messagemode == 1 ) {
   // �o�^�������b�Z�[�W
   funInfoMsgBox(I000005);
 }
 window.returnValue = "2" + ":::" + detail.cmbOrderCom1.value + ":::" + detail.cmbTargetSizai.value;

 return true;
}

//========================================================================================
// �Q�l�����o�͏���
// �쐬�ҁFH.Shima
// �쐬���F2014/10/03
// �T�v  �F�Q�l������\������
//========================================================================================
function funReference() {

    // �Q�l�����E�B���h�E�I�[�v��
    var win = window.open("../SQ220ShiryoRef/SQ220ShiryoRef.jsp","sansyo","menubar=no,resizable=yes");
    // �ĕ\���ׂ̈Ƀt�H�[�J�X�ɂ���
    win.focus();

    return true;
}

//========================================================================================
// �폜����
// �쐬�ҁFH.Shima
// �쐬���F2014/10/03
// �T�v  �F�폜���s��
//========================================================================================
function funDelete(mode) {

    var header = parent.header.document.frm00; // ̫�тւ̎Q��
    var detailDoc = parent.detail.document;
    var detail = detailDoc.frm00;
    var XmlId = "RGEN3320";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3320");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3320I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3320O);

    // �폜�m�F
    if(!funDeleteCheck(mode)){
        return false;
    }

    // XML�̏�����
    setTimeout("xmlFGEN3320I.src = '../../model/FGEN3320I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // �o��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3320, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    // �폜�������b�Z�[�W
    funInfoMsgBox(I000007);

    // �t�H�[���̏�����
    detail.reset();
    detail.txtOldShizai.style.visibility                   = 'hidden';
    detailDoc.getElementById('siyohenko').style.visibility = 'hidden';
    detail.txtOldShizaiZaiko.style.visibility              = 'hidden';
//    detail.btnOldShizaiZaikoCheck.style.visibility         = 'hidden';
    detail.txtRakuhan.style.visibility                     = 'hidden';
//    detail.btnRakuhanCheck.style.visibility                = 'hidden';
    detail.cmbOrderCom1.selectedIndex = 0;
    detail.cmbOrderCom1.onchange();
    detail.cmbOrderCom2.selectedIndex = 0;
    detail.cmbOrderCom2.onchange();

    return true;
}


//========================================================================================
// �폜�m�F
// �쐬�ҁFH.Shima
// �쐬���F2014/12/4
// �T�v  �F��z�ς݃f�[�^���܂܂��ꍇ�͊m�F���b�Z�[�W��\������
//========================================================================================
function funDeleteCheck(mode){

    var header = parent.header.document.frm00; // ̫�тւ̎Q��
    var detailDoc = parent.detail.document;
    var detail = detailDoc.frm00;
    var XmlId = "RGEN3390";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3390");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3390I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3390O);

    // XML�̏�����
    setTimeout("xmlFGEN3390I.src = '../../model/FGEN3390I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // �o��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3390, xmlReqAry, xmlResAry,
         mode) == false) {
        return false;
    }

    // ��z�ς݌����擾
    var cnt_zumi = funXmlRead_3(xmlResAry[2], "table", "count_tehaizumi", 0, 0);
    if(0 < cnt_zumi){
        // ��z�ς݃f�[�^�̍폜�m�F���b�Z�[�W�̕\��
        if (funConfMsgBox(I000017) != ConBtnYes) {
            return false;
        }
    }
    // �m�Fү���ނ̕\��
    else if (funConfMsgBox(I000004) != ConBtnYes) {
        return false;
    }

    return true;
}


//========================================================================================
// �ҏW�m�F�F�ݒ菈��
// �쐬�ҁFH.Shima
// �쐬���F2014/10/10
// �T�v  �F���ڂ̔w�i�F�𐅐F�ɂ���B
//========================================================================================
function funCheckSetting(){
    var detail = parent.detail.document.frm00;

    // �w�i�F��ύX
    detail.txtDesign1.style.backgroundColor        = uncheckColor;
    detail.txtDesign2.style.backgroundColor        = uncheckColor;
    detail.txtDesign3.style.backgroundColor        = uncheckColor;
    detail.txtZaishitsu.style.backgroundColor      = uncheckColor;
    detail.txtBiko.style.backgroundColor           = uncheckColor;
    detail.txtPrintColor.style.backgroundColor     = uncheckColor;
    detail.txtColorNo.style.backgroundColor        = uncheckColor;
    detail.txtChangesDetail.style.backgroundColor  = uncheckColor;
    detail.txtDeliveryTime.style.backgroundColor   = uncheckColor;
    detail.txtQuantity.style.backgroundColor       = uncheckColor;
    detail.txtOldShizaiZaiko.style.backgroundColor = uncheckColor;
    detail.txtRakuhan.style.backgroundColor        = uncheckColor;
}

//========================================================================================
// �ҏW�����m�F����
// �쐬�ҁFH.Shima
// �쐬���F2014/10/10
// �T�v  �F�Ώۍ��ڂ̔w�i�F�𔒐F�ɂ���B
//========================================================================================
function funCheckComplete(mode){
    var detail = parent.detail.document.frm00;
    var object;

    switch(mode){
    case 1:  // �݌v�@
        object = detail.txtDesign1;
        break;
    case 2:  // �݌v�A
        object = detail.txtDesign2;
        break;
    case 3:  // �݌v�B
        object = detail.txtDesign3;
        break;
    case 4:  // �ގ�
        object = detail.txtZaishitsu;
        break;
    case 5:  // ���l
        object = detail.txtBiko;
        break;
    case 6:  // ����F
        object = detail.txtPrintColor;
        break;
    case 7:  // �F�ԍ�
        object = detail.txtColorNo;
        break;
    case 8:  // �ύX���e�ڍ�
        object = detail.txtChangesDetail;
        break;
    case 9:  // �[��
        object = detail.txtDeliveryTime;
        break;
    case 10: // ����
        object = detail.txtQuantity;
        break;
    case 11: // �����ލ݌�
        object = detail.txtOldShizaiZaiko;
        break;
    case 12: // ����
        object = detail.txtRakuhan;
        break;
    }

    object.style.backgroundColor = checkColor;

}
//========================================================================================
// �S�ҏW�����m�F����
// �쐬�ҁFH.Shima
// �쐬���F2014/10/10
// �T�v  �F�Ώۍ��ڂ̔w�i�F�𔒐F�ɂ���B
//========================================================================================
function funCheckCompleteALL(){
	for(var i = 1 ; i < 13; i++) {
		funCheckComplete(i);
	}
}

//========================================================================================
// �`�F�b�N�ς݊m�F����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F�`�F�b�N�ς݂𔻒f����
//========================================================================================
function funEditCheck(){
    var detailDoc = parent.detail.document;
    var detailFrm = parent.detail.document.frm00;

    var common     = 0; // ����
    var newEdition = 1; // �V��
    var revision   = 2; // ����

    var No = 0;
    var arrObj = new Array();
    arrObj[No++] = new Array("txtDesign1", common);          // �݌v�@
    arrObj[No++] = new Array("txtDesign2", common);          // �݌v�A
    arrObj[No++] = new Array("txtDesign3", common);          // �݌v�B
    arrObj[No++] = new Array("txtZaishitsu", common);        // �ގ�
    arrObj[No++] = new Array("txtBiko", common);             // ���l
    arrObj[No++] = new Array("txtPrintColor", common);       // ����F
    arrObj[No++] = new Array("txtColorNo", common);          // �F�ԍ�
    arrObj[No++] = new Array("txtChangesDetail", common);    // �ύX���e�ڍ�
    arrObj[No++] = new Array("txtDeliveryTime", common);     // �[��
    arrObj[No++] = new Array("txtQuantity", common);         // ����
    arrObj[No++] = new Array("txtOldShizaiZaiko", revision); // �����ލ݌�
    arrObj[No++] = new Array("txtRakuhan", revision);        // ����

    for(var i = 0; i < arrObj.length; i++){
        // �w�i�F�擾
        var bgColor = detailDoc.getElementById(arrObj[i][0]).style.backgroundColor;

        // ���`�F�b�N�L��
        if(bgColor === uncheckColor){

            // ���Ń`�F�b�N�擾
            var valRev = "";
            for(var j = 0; j < detailFrm.revision.length; j++){
                if(detailFrm.revision[j].checked){
                    valRev = detailFrm.revision[j].value;
                    break;
                }
            }

            // ���łɃ`�F�b�N�������Ă��Ȃ� ���� ���Ŏ��ɕ\������鍀�ڂ̏ꍇ
            if(valRev != revision && revision === arrObj[i][1]){
                // �������Ȃ�
            } else {
                return false;
            }
        }
    }

    return true;
}

//========================================================================================
// �ҏW�t���O�ύX
// �쐬�ҁFH.Shima
// �쐬���F2014/10/10
// �T�v  �F�ҏW�t���O��on�ɂ���
//========================================================================================
function setFg_hensyu(){

    //ͯ���ڰт�Document�Q��
    var headerFrm = parent.header.document.frm00;

    //�Čv�Z�t���O��on�ɂ���
    headerFrm.hdnHensyu.value = "true";

    return true;

}

//========================================================================================
// ��ʏI������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F��ʂ��I������
//========================================================================================
function funEnd() {

    parent.close();

    return true;
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

/**
 * ���[�����M�t�H�[�}�b�g�ƃ��[���𑗐M����B
 * @returns
 */
function funMailTo() {
    var detail = parent.detail.document.frm00;
	var header = parent.header.document.frm00; // ̫�тւ̎Q��
	// ���[��
	var mailAddress = header.strMaiAddress1.value;
	var mail2 = funXmlRead(xmlFGEN3370O, "mail_address2", 0);
	    //START �Ώێ��ގ擾 Makoto Takada 
    // �Ώێ���
    var hyouji_taisyosizai_disp = funXmlRead(xmlFGEN3370O, "nm_Taisyosizai", 0);
    //END   �Ώێ��ގ擾 Makoto Takada  


	if (mail2 != "") {
		mailAddress += "," + mail2;
	}

	var subject     = "�y���ގ�z�˗����z";
	//var revision_new = parent.detail.document.getElementById("revision_new").value;
	var revision_new = parent.detail.document.getElementById("revision_new").checked;

	if(revision_new) {
		subject += "�V��:";
	} else {
		subject += "����:";
	}
	var hyoji_hinmei = detail.txtHinmei.value;
	subject +=hyoji_hinmei;
	var hyoji_nisugata = detail.txtNisugata.value;
	subject +=hyoji_nisugata;
	//START �Ώێ��ގ擾 Makoto Takada 
	subject +=hyouji_taisyosizai_disp;
    //END   �Ώێ��ގ擾 Makoto Takada  
	var hyoji_txtDelivery = detail.txtDelivery.value
	subject += "(" + hyoji_txtDelivery + ")";

	var body        = funXmlRead(xmlFGEN3370O, "nm_hattyusaki1", 0) + "�@" + funXmlRead(xmlFGEN3370O, "nm_2nd_hattyusaki1", 0) + "%0d%0a";
		if(funXmlRead(xmlFGEN3370O, "nm_hattyusaki2", 0) != "") {
		    body        +=funXmlRead(xmlFGEN3370O, "nm_hattyusaki2", 0) + "�@" + funXmlRead(xmlFGEN3370O, "nm_2nd_hattyusaki2", 0) + "%0d%0a";
		}
		//20170322 tamura delete start
	    //body        += "%0d%0a%0d%0a������ς����b�ɂȂ落�ɗL��Ƃ��������܂��B%0d%0a";
	    //20170322 tamura delete end
	    //20170322 tamura add start
	    body          += "%0d%0a������ς����b�ɂȂ落�ɗL��������܂��B%0d%0a";
	    //20170322 tamura add end  
		body        += "���L�̂Ƃ���A���ނ̎�z�����肢�v���܂��B%0d%0a%0d%0a";
�@�@�@�@//20170314 tamura delete start
		//body        += "�ŉ��f�[�^�F�ʃ��[���ɂēY�t�������܂��B%0d%0a";
		//20170314 tamura delete end
		//20170314 tamura add start
		body        += "�ŉ��f�[�^�F%0d%0a";
		//20170314 tamura add end
		body        += "�y���e�z�F" + detail.txtNaiyo.value + "%0d%0a";
		body        += "�y�[���z�F" + detail.txtDeliveryTime.value  + "%0d%0a%0d%0a";
		body        += hyoji_txtDelivery + "�@�Ɩ��ۗl%0d%0a%0d%0a";
		body        += "��L�̂Ƃ���˗��������Ă���܂��B%0d%0a";
		body        += "�ڍׂ̔����́A�H�ꂩ�璼�ڂ��A�������肢�v���܂��B%0d%0a%0d%0a";
		body        += "�����ރR�[�h�F%0d%0a%0d%0a";
		body        += "�ȏ�A�����X�������肢�\���グ�܂��B%0d%0a";
		//20170329 tamura delete start
		//body        += "�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{%0d%0a";
		//body        += "���Y�{���@�����ޒ��B���@���މ�%0d%0a";
		//20170329 tamura delete end
		//20170329 tamura add start
		body        += "�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{%0d%0a";
		body        += "�L���[�s�[�������%0d%0a";
        body        += "���Y�{���@�����ޒ��B���@���މ�%0d%0a";
		body        += "�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{�{%0d%0a";
        //20170329 tamura add end
    var url = "mailto:" + mailAddress + "?subject="+ subject + "&amp;body=" + body  ;
    var windowName = "childWindow";
    var winWidth = "400";
    var winHeight = "300";
    var x = (screen.width - winWidth) / 2;
    var y = (screen.height - winHeight) / 2;
    var options = "width=" + winWidth + ", \
                   height=" + winHeight + ", \
                   left="+ x + ", \
                   top=" + y;
    open(url, windowName, options);
}