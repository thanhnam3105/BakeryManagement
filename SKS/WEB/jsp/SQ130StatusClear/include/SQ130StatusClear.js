
// ADD start 20120620 hisahori
// �O���[�o���ϐ�
var stKojo;  // �H��X�e�[�^�X
var tblSampleRowCount;  // ���Z���N���A�T���v���I���e�[�u��row��
// ADD end 20120620 hisahori
//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
var stEigyo;  // �c�ƃX�e�[�^�X
//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

//========================================================================================
// �����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/26
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

	var cd_shain = window.dialogArguments[0];
	var nen = window.dialogArguments[1];
	var no_oi = window.dialogArguments[2];
	var no_eda = window.dialogArguments[3];
// ADD start 20120615 hisahori
	var seqshisaku = window.dialogArguments[4];
	var sampleno = window.dialogArguments[5];
	var shisanbi = window.dialogArguments[6];
	var shisanchushi = window.dialogArguments[7];
// ADD end 20120615 hisahori
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	var chkKoumoku = window.dialogArguments[8];
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

    var frm = document.frm00;    //̫�тւ̎Q��
	frm.hidShisakuNo_shaincd.value = cd_shain;
	frm.hidShisakuNo_nen.value = nen;
	frm.hidShisakuNo_oi.value = no_oi;
	frm.hidShisakuNo_eda.value = no_eda;
// ADD start 20120615 hisahori
	frm.hidSeqShisaku.value = seqshisaku;
	frm.hidSampleNo.value = sampleno;
	frm.hidShisanHi.value = shisanbi;
	frm.hidShisanChushi.value = shisanchushi;
// ADD end 20120615 hisahori
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	frm.hidChkKoumoku.value = chkKoumoku;
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

    //��ʐݒ�
    funInitScreen(ConStatusClearId);

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

//ADD start 20120614 hisahori
  //20160607  KPX@1502111_No9 ��\���ɕύX
	//���Z���N���A�I��\��\��
	funDtShisanClearDisplay("divDtShisanClearSelect","");
//ADD end 20120614 hisahori

    return true;

}

//========================================================================================
// �yQP@00342�z��ʏ��擾����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/24
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2010","FGEN2020");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2010I,xmlFGEN2020I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2010O,xmlFGEN2020O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�X�e�[�^�X�����̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2020, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //�ݒ�l�擾
    var nm_clear = funXmlRead_3(xmlResAry[2], "kihon", "nm_clear", 0, 0);
    var st_kenkyu = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);

    //���݃X�e�[�^�X�ݒ�
    var divStatusKenkyu_now = document.getElementById("divStatusKenkyu_now");
    var divStatusSeikan_now = document.getElementById("divStatusSeikan_now");
    var divStatusGentyo_now = document.getElementById("divStatusGentyo_now");
    var divStatusKojo_now = document.getElementById("divStatusKojo_now");
    var divStatusEigyo_now = document.getElementById("divStatusEigyo_now");
    divStatusKenkyu_now.innerHTML = funStatusSetting("1",funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0));
    divStatusSeikan_now.innerHTML = funStatusSetting("2",funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0));
    divStatusGentyo_now.innerHTML = funStatusSetting("3",funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0));
    divStatusKojo_now.innerHTML = funStatusSetting("4",funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0));
    divStatusEigyo_now.innerHTML = funStatusSetting("5",funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0));

    //�X�e�[�^�X�N���A�e�[�u������
    //���݃X�e�[�^�X�擾
    var st_kenkyu = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    var st_seisan = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    var st_gensizai = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    var st_kojo = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
// ADD start 20120620 hisahori
	stKojo = st_kojo;
// ADD end 20120620 hisahori
    var st_eigyo = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);
 // ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
    stEigyo = st_eigyo;
 // ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

    //�����J�n
    var divStatusTable = document.getElementById("divStatusTable");
    var outputHtml = "";
    var i = 0;
    var roop_cnt = funXmlRead_3(xmlResAry[2], "kihon", "roop_cnt", 0, 0);
    var flg_now_down = false;
    var bgColor="";
    var disabled = "";
    var checked = "";

// MOD start 20120704 hisahori
	var countRadioNo = 0;
// MOD end 20120704 hisahori

    //�������[�v
    for(i = roop_cnt-1; i >= 0; i--){
        var cd_clear = funXmlRead_3(xmlResAry[2], "kihon", "cd_clear", 0, i);
        var nm_clear = funXmlRead_3(xmlResAry[2], "kihon", "nm_clear", 0, i);
        var r_st_kenkyu = funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, i);
        var r_st_seisan = funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, i);
        var r_st_gensizai = funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, i);
        var r_st_kojo = funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, i);
        var r_st_eigyo = funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, i);

        //�����̏����l�ݒ�
        if( flg_now_down ){
            bgColor="#FFFFFF";
	    	disabled = "";
	    	checked = "";
        }
        else{
            bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
        }

        //���݃X�e�[�^�X���u��3�����v�u�c4�v�̏ꍇ
        if( st_seisan < 3 && st_eigyo == 4 ){
        	if(cd_clear == 2){
        		flg_now_down = true;
        	}
        }

        //�X�e�[�^�X����i�c�Ƃ̗̍p�L�������j
	    else if( st_eigyo == 4 && cd_clear == 11){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i�c�Ƃ̊m�F�����j
	    else if( st_eigyo == 3  && cd_clear == 10){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i���Y�Ǘ����̊m�F�����j
	    else if( st_eigyo == 2  && st_seisan == 3  && cd_clear == 9){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i���Y�Ǘ����̍H��˗��i�����ޒ��B���E�H��@���F�ς݁j�j
	    else if( st_seisan == 2 && st_kojo == 2 && st_gensizai == 2 && cd_clear == 8){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i���Y�Ǘ����̍H��˗��i�H��@���F�ς݁j�j
	    else if( st_seisan == 2 && st_gensizai == 1 && st_kojo == 2  && cd_clear == 7){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i���Y�Ǘ����̍H��˗��i�����ޒ��B���@���F�ς݁j�j
	    else if( st_seisan == 2 && st_gensizai == 2 && st_kojo == 1 && cd_clear == 6){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i���Y�Ǘ����̍H��˗��j
	    else if( st_seisan == 2 && st_gensizai == 1 && st_kojo == 1  && cd_clear == 5){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i�c�Ƃ̏������́i�����ޒ��B���@���F�ς݁j�j
	    else if( st_seisan == 1 && st_gensizai == 2 && st_eigyo == 2  && cd_clear == 4){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i�c�Ƃ̏������́j
	    else if( st_seisan == 1 && st_gensizai == 1 && st_eigyo == 2  && cd_clear == 3){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i�������̎��Z�˗��i�����ޒ��B���@���F�ς݁j�j
	    else if( st_gensizai == 2 && st_eigyo == 1  && cd_clear == 2){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }
	    //�X�e�[�^�X����i�������̎��Z�˗��j
	    else if( st_gensizai == 1 && st_eigyo == 1  && cd_clear == 1){
	    	bgColor="#FFFF88";
	    	disabled = "disabled";
	    	checked = "";
	    	flg_now_down = true;
	    }

	    //�X�e�[�^�X����i�����ޒ��B���j
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 2){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 4){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }
	    if( st_seisan <= 2 && st_gensizai == 1 && cd_clear == 6){
	        bgColor="#A9A9A9";
	    	disabled = "disabled";
	    	checked = "";
	    }

	    //�e�[�u������
        var cleateHtml = "";
	    cleateHtml = cleateHtml + "<tr class=\"disprow\">";
	    if( cd_clear == 11 ){
	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\"		bgcolor=\"" + bgColor + "\"	align=\"center\">&nbsp;</td>";
	    }
	    else{
// MOD start 20120704 hisahori
//	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\"		bgcolor=\"" + bgColor + "\"	align=\"center\"><input type=\"radio\" name=\"chk\" value=\"" + cd_clear + "\" " + disabled + " " + checked + "></td>";
//			countRadioNo++;
	        cleateHtml = cleateHtml + "<td class=\"column\" width=\"32\" bgcolor=\"" + bgColor + "\" align=\"center\"><input type=\"radio\" name=\"chk\" value=\"" + cd_clear + "\" " + disabled + " " + checked + " onClick=\"radioStatusChange()\"></td>";
// MOD end 20120704 hisahori
	    }
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"351\" 	bgcolor=\"" + bgColor + "\"	align=\"left\"  >" + nm_clear + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("1",r_st_kenkyu) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("2",r_st_seisan) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("3",r_st_gensizai) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("4",r_st_kojo) + "</td>";
		cleateHtml = cleateHtml + "<td class=\"column\" width=\"50\"  	bgcolor=\"" + bgColor + "\"	align=\"center\"  >" + funStatusSetting("5",r_st_eigyo) + "</td>";
		cleateHtml = cleateHtml + "</tr>";
		outputHtml = cleateHtml + outputHtml;
    }

    outputHtml = "<table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:630;\">" + outputHtml;
    outputHtml = outputHtml + "</table>";
    divStatusTable.innerHTML = outputHtml;

    return true;
}

//========================================================================================
// �yQP@00342�zXML�t�@�C���ɏ�������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/24
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
        if (XmlId.toString() == "RGEN2020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2010

                    break;
                case 2:    //FGEN2020
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    break;
            }
        }
        //�N���A���s
        else if (XmlId.toString() == "RGEN2030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2030
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
					//�N���A�R�[�h�擾
					var cd_clear = "";
					for(var j = 0; j < frm.chk.length; j++){
				        if(frm.chk[j].checked) {
				            cd_clear = frm.chk[j].value;
				            break;
				        }
					}
					// ADD start 20120622 hisahori
					if (cd_clear == ""){
        				funErrorMsgBox("�y�X�e�[�^�X�N���A���s�z�@�F�@ " + E000025);
        				return false;
					}
					// ADD end 20120622 hisahori

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    funXmlWrite(reqAry[i], "cd_clear", cd_clear, 0);

                    //ADD start 20120618 hisahori
                    //�I�����ꂽ���Z�����N���A����T���v��NO�iseq_shisaku�j
                    var cnttblrow;
                    if (tblSampleRowCount == 0){
	                    cnttblrow = 0; // ���Z���~�T���v�������Ȃ��ꍇ�Arow�J�E���g0
                    } else {
	                    cnttblrow = tblClearShisanHi.rows.length;
                    }
                    var checkNo = "";
					for(var k = 0; k < cnttblrow; k++){
						if (document.getElementById("chkSampleGyo_" + k).checked){
							if (checkNo == ""){
								checkNo = checkNo + document.getElementById("hdnTblSeqShisaku_" + k).value;
							} else {
								checkNo = checkNo + "chk" + document.getElementById("hdnTblSeqShisaku_" + k).value; //xml�̗v�f���Łu,�v���g���Ȃ�
							}
						}
					}
                    funXmlWrite(reqAry[i], "no_clearcheck", checkNo, 0);
                    //ADD end 20120618 hisahori

                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �yQP@00342�z�X�e�[�^�X�N���A���s
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/26
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�X�e�[�^�X�N���A���s
//========================================================================================
function funClear(){

	var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2030");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2030I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2030O);
    var mode = 1;

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage(); //���������b�Z�[�W���I������
        return false;
    }

    //����ݏ��A�X�e�[�^�X�����̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //����
        funInfoMsgBox(dspMsg);
    }

    //�߂�l�̐ݒ�
	window.returnValue = "Clear";

	//��ʂ����
    close(self);
}

//========================================================================================
// �yQP@00342�z��ʏI��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/24
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F��ʂ����B
//========================================================================================
function funClose() {

	//�߂�l�̐ݒ�
	window.returnValue = "false";

    //��ʂ����
    close(self);
}

//20160607  KPX@1502111_No9 �ȉ��\������Ȃ��I
// ���Z���N���A�����폜
//========================================================================================
// ���Z���N���A�T���v���I��\
// �쐬�ҁFT.Hisahori
// �쐬���F2012/06/14
// ����  �FObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���Z���N���A�I��\��HTML���𐶐��A�o�͂���B
//========================================================================================
function funDtShisanClearDisplay(ObjectId) {
    var frm = document.frm00;    //̫�тւ̎Q��
    var divDtShisanSelect = document.getElementById(ObjectId);
    var OutputHtml;       //�o��HTML
    var chkDisabled;      //�`�F�b�N�{�b�N�X���͐ݒ� -- ���g�p
    var objColor;         //�I�u�W�F�N�g�w�i�F

// MOD start 20120704
//	var chkEnable = "enable";
//    if (stKojo <= 1){
//    	chkEnable = "disabled";
//    }
	var chkEnable = "disabled";
// MOD end 20120704

    //�T���v��NO�A���Z���z��쐬
    var getSeqShisaku = frm.hidSeqShisaku.value;
    var getSampleNo = frm.hidSampleNo.value;
    var getShisanHi = frm.hidShisanHi.value;
    var getShisanChushi = frm.hidShisanChushi.value;
    var arrSeqShisaku = getSeqShisaku.split(",");
    var arrSampleNo = getSampleNo.split(",");
    var arrShisanHi = getShisanHi.split(",");
    var arrShisanChushi = getShisanChushi.split(",");
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
    var getChkKoumoku = frm.hidChkKoumoku.value;
    var arrChkKoumoku = getChkKoumoku.split(",");
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end


    chkDisabled = "disabled";		//���g�p
    txtClass = "table_text_view";

	var cnt_sampleNo = arrShisanHi.length;
    //���~�t���O�̐�
    var cntChushi = 0;
    for(var i = 0; i < cnt_sampleNo; i++ ){
    	if (arrShisanChushi[i] == 1){
    		cntChushi++;
    	}
    }
    if (cnt_sampleNo == cntChushi){
	    tblSampleRowCount = 0; // ���Z���~�T���v�������Ȃ��ꍇ�Arow�J�E���g0
    }

    //HTML�o�̓I�u�W�F�N�g�ݒ�
    OutputHtml = "";

    //�e�[�u�����ݒ�
    OutputHtml += "<input type=\"hidden\" id=\"cnt_sampleNo\" name=\"cnt_sampleNo\" value=\"" + cnt_sampleNo + "\">";

    //�o��HTML�ݒ�
    OutputHtml += "      <div class=\"scroll_genka\" id=\"divDtShisanClearSelect\" style=\"width:346px;height:85px;overflow-x:hidden;\" rowSelect=\"true\" />";
    OutputHtml += "      <table class=\"detail\" id=\"tblClearShisanHi\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:330px;�@>";

	// �T���v�������Z���~�ł͂Ȃ��T���v���̂ݕ\��
	var cntNumbering = 0;
    for(var j = 0; j < cnt_sampleNo; j++ ){
    	if (arrShisanChushi[j] != "1"){
	        OutputHtml += "    <tr�@class=\"disprow\">";
	        //�I��
	        OutputHtml += "        <td bgcolor=\"#ffffff\" class=\"column\" width=\"32px\" align=\"center\">";
	        OutputHtml += "            <input name=\"chkSampleGyo_" + cntNumbering + "\" type=\"checkbox\" " + chkEnable + " />";
	        OutputHtml += "        </td>";
	        //�T���v��NO
	        OutputHtml += "        <td class=\"column\" width=\"151\"  	bgcolor=\"" + "#FFFFFF" + "\"	align=\"left\"  >" + arrSampleNo[j] + "</td>";
	        //���Z��
	        OutputHtml += "        <td class=\"column\" width=\"150\"  	bgcolor=\"" + "#FFFFFF" + "\"	align=\"left\"  >" + arrShisanHi[j] + "</td>";
	        //�T���v��NO�L�[�iseq_shisaku�j
	        OutputHtml += "        <td class=\"hidden\" >";
	        OutputHtml += "            <input type=\"hidden\" value=\"" + arrSeqShisaku[j] + "\" name=\"hdnTblSeqShisaku_" + cntNumbering + "\" id=\"hdnTblSeqShisaku_" + cntNumbering + "\" >";
	        OutputHtml += "        </td>";
	    	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	        // ���ڌŒ�`�F�b�N
	        OutputHtml += "        <td class=\"hidden\" >";
	        OutputHtml += "            <input type=\"hidden\" value=\"" + arrChkKoumoku[j] + "\" name=\"hdnTblChkKoumoku_" + cntNumbering + "\" id=\"hdnTblChkKoumoku_" + cntNumbering + "\" >";
	        OutputHtml += "        </td>";
	    	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end
	        OutputHtml += "    </tr>";

	        cntNumbering ++;
	   }
    }
    OutputHtml += "      </table>";

    //HTML���o��
	divDtShisanSelect.innerHTML = OutputHtml;

    OutputHtml = null;
    return true;
}

//========================================================================================
// �X�e�[�^�X�߂��惉�W�I�{�^���`�F���W�C�x���g
// �쐬�ҁFT.Hisahori
// �쐬���F2012/07/04
// ����  �F
// �߂�l�F�Ȃ�
// �T�v  �F�H��P�ɂȂ�X�e�[�^�X�߂��悪�I�����ꂽ�ꍇ�A���Z���N���A�T���v��NO�`�F�b�N�{�b�N�X������������
//========================================================================================
function radioStatusChange(){
    var frm = document.frm00;    //̫�тւ̎Q��
    var flgChkboxSelect = 0;
// ADD start 20120711
    if (stKojo <= 1){
    } else {
// ADD end 20120711

		// ���Z���N���A�T���v��NO�@�`�F�b�N�{�b�N�X�̐����擾
	    var cnttblrow;
	    if (tblSampleRowCount == 0){
	        cnttblrow = 0; // ���Z���~�T���v�������Ȃ��ꍇ�Arow�J�E���g0
	    } else {
	        cnttblrow = tblClearShisanHi.rows.length;
	    }
	    if (cnttblrow == 0){
	    	return;
	    }

		if (frm.chk[0].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[1].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[2].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[3].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[4].checked == true){
			flgChkboxSelect = 1;
		} else if (frm.chk[5].checked == true){
			flgChkboxSelect = 1;
		} else{
			//�c�ƁF�m�F��A���Z���N���A�ł��Ȃ�
			flgChkboxSelect = 0;
		}
//20160607  KPX@1502111_No9 DEL start  ���Z�����N���A���Ȃ�
//		//���Z���N���A�T���v��No�`�F�b�N�{�b�N�X�̊���/�񊈐���؂�ւ�
//		if (flgChkboxSelect == 1){
//			for(var k = 0; k < cnttblrow; k++){
//				document.getElementById("chkSampleGyo_" + k).disabled = false;
//				document.getElementById("chkSampleGyo_" + k).checked = true;
//				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
//				//�c�Ɗm�F������X�e�[�^�X�N���A����ꍇ�A���ڌŒ�`�F�b�NON�̗�F�u���Z���v�̃`�F�b�N�s��
//				if ((stEigyo > 2) && (document.getElementById("hdnTblChkKoumoku_" + k).value == 1)) {
//					document.getElementById("chkSampleGyo_" + k).disabled = true;
//					document.getElementById("chkSampleGyo_" + k).checked = false;
//				}
//				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end
//			}
//		} else {
//			for(var k = 0; k < cnttblrow; k++){
//				document.getElementById("chkSampleGyo_" + k).disabled = true;
//				document.getElementById("chkSampleGyo_" + k).checked = false;
//			}
//		}
//20160607  KPX@1502111_No9 DEL end
// ADD start 20120711
    }
// ADD end 20120711

}



