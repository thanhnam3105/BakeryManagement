//========================================================================================
// �����\������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConCostTblRefId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClear();

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �Ő��̓��͐��䏈��
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F���@No�̓��͐�����s��
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
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    //�ް��擾
    funDataSearch();

}

//========================================================================================
// ��������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F����f�[�^�̌������s��
//========================================================================================
function funDataSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3040");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3040I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3040O);

    //������ү���ޕ\��
    funShowRunMessage();

    //�I���s�̏�����
    funSetCurrentRow("");

    frm.txtShiyoRyo.value = "";

    funClearHeader();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v���鎎���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3040, xmlReqAry, xmlResAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        return false;
    }

    //�ް�����������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {

        // �w�b�_�쐬
        funCreateHeader(xmlResAry[2]);
        // �yQP@30297�z E.kitazawa �ۑ�Ή� --------------------- del start
//        funRowDelete_Tbl(xmlResAry[2], "xmlFGEN3040", 0);
        // �yQP@30297�z E.kitazawa �ۑ�Ή� --------------------- del end

        // �yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add start
        // �ꗗ�쐬�i���ו��j
        funCreateCostTbl(xmlResAry[2]);

        //������ү���ޔ�\��
        funClearRunMessage();
        // �yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add end

        //�\��
        tblHeader.style.display = "block";
        tblList.style.display = "block";

    } else {
        //��\��
        tblHeader.style.display = "none";
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    return true;

}

//========================================================================================
// �w�b�_����������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�w�b�_������������
//========================================================================================
function funClearHeader() {
    var id;

    for (i = 0; i < 30; i++) {
        id  = "tblHeader" + funZeroPudding(i + 1);
        document.getElementById(id).value = "";

        id  = "calcRslt" + funZeroPudding(i + 1);
        document.getElementById(id).innerText = "";
    }

    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
    document.getElementById("txtYuko").innerHTML = "";
    //20140616 del start�yQP@30297�z�ۑ�
    //document.getElementById("txtBiko").innerHTML = "";
    //20140616 del end�yQP@30297�z�ۑ�
    document.getElementById("txtBiko_kojo").innerHTML = "";
    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
}

//========================================================================================
// �w�b�_����������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�w�b�_������������
//========================================================================================
function funCreateHeader(data) {

    var key;
    var id;

    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
    for (i = 0; i < 30; i++) {
        key = "no_value" + funZeroPudding(i + 1);;
        id  = "tblHeader" + funZeroPudding(i + 1);
        document.getElementById(id).value = funAddComma(funXmlRead(data, key, 0));
    }
    document.getElementById("txtYuko").innerHTML = funXmlRead(data, "dt_yuko", 0);
    //20140616 del start�yQP@30297�z�ۑ�
    //document.getElementById("txtBiko").innerHTML = funXmlRead(data, "biko", 0);
    //20140616 del end�yQP@30297�z�ۑ�
    document.getElementById("txtBiko_kojo").innerHTML = funXmlRead(data, "biko_kojo", 0);
    // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
}

//========================================================================================
// ���g�p�ʌv�Z����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F���g�p�ʂ��v�Z����
//========================================================================================
function funCalc() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var key;
    var headerId;
    var calcRsltId;
    var val;

    // �yQP@30297�zNo.17 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start
    //  ���g�p�� < 1 ��������
    // if(frm.txtShiyoRyo.value == "" || parseInt(frm.txtShiyoRyo.value) == 0) {
    if(frm.txtShiyoRyo.value == "" || parseFloat(frm.txtShiyoRyo.value) == 0) {
        return;
    }
    // �yQP@30297�zNo.17 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod end

    for (i = 0; i < 30; i++) {
        headerId  = "tblHeader" + funZeroPudding(i + 1);
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
        val = document.getElementById(headerId).value.replace(/,/g,"");
        if(val != ""){
            calcRsltId  = "calcRslt" + funZeroPudding(i + 1);
            // �yQP@30297�zNo.20 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start
            // document.getElementById(calcRsltId).innerText = funAddComma(Math.floor((val / frm.txtShiyoRyo.value.replace(/,/g,"")) * 10) / 10);
            // �����_�ȉ��؂�̂�
            document.getElementById(calcRsltId).innerText = funAddComma(Math.floor(val / frm.txtShiyoRyo.value.replace(/,/g,"")));
            // �yQP@30297�zNo.20 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod end
        }
        // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
    }
}

//========================================================================================
// ��ʏ��������@�i�N���A�{�^�����������j
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();
    frm.ddlMakerName.selectedIndex = 0;
    frm.ddlHouzai.selectedIndex = 0;

    //�ꗗ�̸ر
    funClearList();

    //��̫�Ă̺����ޯ���ݒ�l��ǂݍ���
    xmlFGEN3000O.load(xmlFGEN3000);
    xmlFGEN3010O.load(xmlFGEN3010);

    //�����ޯ���̍Đݒ�
    funCreateComboBox(frm.ddlMakerName, xmlFGEN3000O, 1);
    funDefaultIndex(frm.ddlMakerName, 1);
    // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
    // ��̕�޺����ޯ����ݒ�
//    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 1);
    funCreateComboBox(frm.ddlHouzai, xmlFGEN3010O, 2);
    funDefaultIndex(frm.ddlHouzai, 1);
    // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end

    return true;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlFGEN3040O.src = "";
    tblList.style.display = "none";

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/25
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3000";
 // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
/*    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000","FGEN3010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I,xmlFGEN3010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O,xmlFGEN3010O);
*/
    // ���[�J�[���̂ݎ擾
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3000O);
 // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3000, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // �yQP@30297�zNo.19 2014/10/28 E.kitazawa �ۑ�Ή� --------------------- mod start
    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlMakerName, xmlResAry[2], 1);
//    funCreateComboBox(frm.ddlHouzai, xmlResAry[3], 1);

    //��̫�Ă̺����ޯ���ݒ�l��ޔ�
    xmlFGEN3000.load(xmlResAry[2]);
//    xmlFGEN3010.load(xmlResAry[3]);
    // �yQP@30297�zNo.19 2014/10/28 E.kitazawa �ۑ�Ή� --------------------- mod end

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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
        if (XmlId.toString() == "RGEN3000") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN3000
                    break;
                case 2:    //FGEN3010 --- �p�~�yQP@30297�zNo.19
                    break;
            }

            // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start
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
            // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end

        //�������݉���
        } else if (XmlId.toString() == "RGEN3040"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN3040
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "no_hansu", frm.txtHansu.value);
                    break;
            }
        }
        //�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
        //���F�Ō���
        else if (XmlId.toString() == "RGEN3090"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN3090
                    funXmlWrite(reqAry[i], "cd_maker", frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_houzai", frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value, 0);
                    break;
            }
        }
        //�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
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

          //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start
        case 2:    //����Ͻ��i��2�J�e�S���j
            atbName = "nm_2nd_literal";
            atbCd = "cd_2nd_literal";
            break;
          //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end
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
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/25
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
//    var selIndex;
//    var i;
    var selIndex = 0;
    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �I���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/07
// �T�v  �F�I������
//========================================================================================
function funEndClick(){
    //�I������
    funEnd();
}

//========================================================================================
// �I������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F�I������
//========================================================================================
function funEnd(){

    //��ʂ����
    window.close();

}

//�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
// ���[�J���A��ޑI�����ɏ��F�Ŏ擾
function funHanSearch() {

 var frm = document.frm00;    //̫�тւ̎Q��
 var XmlId = "RGEN3090";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN3090");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN3090I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN3090O);


 //���[�J���A��ނ��I������Ă��Ȃ��ꍇ�͏������~
 if(frm.ddlMakerName.options[frm.ddlMakerName.selectedIndex].value == ""){
	 //�Ő��̸ر
	 frm.txtHansu.value = "";
	 return;
 }
 if(frm.ddlHouzai.options[frm.ddlHouzai.selectedIndex].value == ""){
	 //�Ő��̸ر
	 frm.txtHansu.value = "";
	 return;
 }

 //������ү���ޕ\��
 funShowRunMessage();
 //������XMĻ�قɐݒ�
 if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
     funClearRunMessage();
     return false;
 }

 //���������Ɉ�v���鎎���ް����擾
 if (funAjaxConnection(XmlId, FuncIdAry, xmlFGEN3090, xmlReqAry, xmlResAry, 1) == false) {
     return false;
 }

 //�����擾
 reccnt = funGetLength(xmlResAry[2]);

 //�������������ꍇ
 if(reccnt > 1){
	 funInfoMsgBox("���F����Ă���Ő�������������܂����B\\n�J���}��؂�ŕ\�����܂��B");
 }

 //�Ő��̐ݒ�
 var val = "";
 for(i=0; i<reccnt; i++){
	 if(i >= 1){
		 val = val + ",";
	 }
	 val = val + funXmlRead(xmlResAry[2], "no_hansu", i);
 }
 frm.txtHansu.value = val;

//������ү���ޔ�\��
 funClearRunMessage();

 return true;

}
//�yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end


//�yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add start
//========================================================================================
// �R�X�g�e�[�u���\������
// �쐬�ҁFE.kitazawa
// �쐬���F2014/08/20
// ����  �F�@xmlData  �FXML�f�[�^(�z��j
// �T�v  �F�R�X�g�e�[�u�����쐬����
//========================================================================================
function funCreateCostTbl(xmlData) {

  var key;
  var id;

  for (i = 1; i < 31; i++) {

      key = "nm_title";
      id  = "nm_title_" + i;

      document.getElementById(id).value = funXmlRead(xmlData, key, i);

      for (j = 1; j < 31; j++) {
          key = "no_value" + funZeroPudding(j);
          id  = "no_value_" + i + "_" + j;

          // �����_�ȉ�2�ʂ܂ŕ\���i3�ʈȉ��؂�̂āj
          document.getElementById(id).value = funAddComma(funNumFormatChange(funXmlRead(xmlData, key, i), 2));
      }
  }

}


//���lFormat�ϊ�
//�����@�@val:�ϊ��l
//�@�@�@keta:�������i�؂�̂Č��j
//�߂�l�@�ϊ��l�i�����l�łȂ��ꍇ�͋󕶎��ԋp�j
function funNumFormatChange(val , keta) {

	// �J���}�폜
	val = val.replace(/,/g,"");

	// ���l�`�F�b�N
  if ( val == parseFloat(val)){

  	if(keta == 0){
  		return Math.floor(val);
  	}

  	// ���l�̏ꍇ�ɁA�w�菬���؂�̂�
  	return funShosuKirisute(val,keta);
  } else {
  	// ���l�łȂ�
  	return "";
  }
}
//�yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add end

//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start
//========================================================================================
// ���[�J�[���R���{�{�b�N�X�A������
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/01
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
  //�Ő��̸ر
  frm.txtHansu.value = "";

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
//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end


