//========================================================================================
// �yQP@00342�z�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/24
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

	//�����y�[�W�ݒ�
	funSetCurrentPage(1);

    //��ʐݒ�
    funInitScreen(ConStatusRirekiId);

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

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
    var XmlId = "RGEN2010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2000");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2000I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2000O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�X�e�[�^�X�����̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //HTML�o�̓I�u�W�F�N�g�ݒ�
    var obj = document.getElementById("divStatusRireki");
    OutputHtml = "";

    OutputHtml = OutputHtml + "<table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:910px;\">";
    var i; //���[�v�J�E���g
    var cnt_roop = funXmlRead_3(xmlResAry[2], "kihon", "roop_cnt", 0, 0);

    //����R�[�h�\��
	frm.hidShisakuNo_shaincd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
	frm.hidShisakuNo_nen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
	frm.hidShisakuNo_oi.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
	frm.hidShisakuNo_eda.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);
    var obj1 = document.getElementById("divStatusRirekiCd");
    var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
    var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
    var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
    var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
    obj1.innerHTML = hidShisakuNo_shaincd + " - " + hidShisakuNo_nen + " - " +  hidShisakuNo_oi + " - " + hidShisakuNo_eda;

    for(i = 0; i < cnt_roop; i++){
    	//�ݒ�l�擾
    	var no_row = funXmlRead_3(xmlResAry[2], "kihon", "no_row", 0, i);
    	var dt_henkou_ymd = funXmlRead_3(xmlResAry[2], "kihon", "dt_henkou_ymd", 0, i);
    	var dt_henkou_hms = funXmlRead_3(xmlResAry[2], "kihon", "dt_henkou_hms", 0, i);
    	var nm_henkou = funXmlRead_3(xmlResAry[2], "kihon", "nm_henkou", 0, i);
    	var nm_zikko_ca = funXmlRead_3(xmlResAry[2], "kihon", "nm_zikko_ca", 0, i);
    	var nm_zikko_li = funXmlRead_3(xmlResAry[2], "kihon", "nm_zikko_li", 0, i);
    	var st_kenkyu = funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, i);
    	var st_seisan = funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, i);
    	var st_gensizai = funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, i);
    	var st_kojo = funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, i);
    	var st_eigyo = funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, i);
    	var cd_zikko_li = funXmlRead_3(xmlResAry[2], "kihon", "cd_zikko_li", 0, i); //ADD �yH24�N�x�Ή��z 2012/04/16 �� �ۑ����e���ʗv�f

    	//�e�[�u���ݒ�
    	//ADD �yH24�N�x�Ή��z 2012/04/16 �� S
    	if(cd_zikko_li == "0"){	// �ۑ����e���ʗv�f��"0"�i���ۑ��j�Ȃ�Δw�i�͔�
        	OutputHtml = OutputHtml + "<tr class=\"disprow\" >";

        //ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
    	} else if(cd_zikko_li == "9"){
    		// �ۑ����e���ʗv�f��"9"�i�H��ύX�j�Ȃ�Δw�i�͔�
           	OutputHtml = OutputHtml + "<tr class=\"disprow\" >";
        //ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end
    	}
    	else{					// ���ۑ��ȊO�͔����u���[(�Q�l�F:Const.js���A�N�A�}����)
        	OutputHtml = OutputHtml + "<tr class=\"disprow\" bgcolor=\"#7fffd4\">";
    	}
    	//ADD �yH24�N�x�Ή��z 2012/04/16 �� E
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"30\"  align=\"right\" >" + no_row + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"80\"  align=\"left\" >" + dt_henkou_ymd + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"70\"  align=\"left\" >" + dt_henkou_hms + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"100\" align=\"left\" >" + nm_henkou + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"80\"  align=\"left\" >" + nm_zikko_ca + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"295\" align=\"left\" >" + nm_zikko_li + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("1",st_kenkyu) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("2",st_seisan) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("3",st_gensizai) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("4",st_kojo) + "</td>";
    	OutputHtml = OutputHtml + "<td class=\"column\" width=\"50\"  align=\"center\" >" + funStatusSetting("5",st_eigyo) + "</td>";
    	OutputHtml = OutputHtml + "</tr>";

    }
    OutputHtml = OutputHtml + "</table>";

    //HTML���o��
    obj.innerHTML = OutputHtml;

    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "�^" + PageCnt + "�y�[�W";

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
        if (XmlId.toString() == "RGEN2010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2000
                	var hidShisakuNo_shaincd = frm.hidShisakuNo_shaincd.value;
                	var hidShisakuNo_nen = frm.hidShisakuNo_nen.value;
                	var hidShisakuNo_oi = frm.hidShisakuNo_oi.value;
                	var hidShisakuNo_eda = frm.hidShisakuNo_eda.value;
                	var page = funGetCurrentPage();

                    funXmlWrite(reqAry[i], "cd_shain", hidShisakuNo_shaincd, 0);
                    funXmlWrite(reqAry[i], "nen", hidShisakuNo_nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", hidShisakuNo_oi, 0);
                    funXmlWrite(reqAry[i], "no_eda", hidShisakuNo_eda, 0);
                    funXmlWrite(reqAry[i], "no_page", page, 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �yQP@00342�z�y�[�W�J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/24
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(NextPage);

    //�w���߰�ނ��ް��擾
    funGetInfo(1);
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
    window.close();
}

