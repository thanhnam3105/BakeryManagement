//========================================================================================
// �����\������
// �쐬�ҁFM.Sakamoto
// �쐬���F2014/02/19
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj;

    //��ʐݒ�
    funInitScreen(ConGenchoMenuId);

    //������ү���ޕ\��
    funShowRunMessage();

    //հ�޾���ݏ����擾
    funGetUserInfo(1);

    //������ү���ޔ�\��
    funClearRunMessage();

    obj = document.getElementById("btnMenu");

    if (obj && obj.type == "button") {
    	if (!frm.btnMenu.length) {
    		// �{�^����1�����Ȃ���
    		frm.btnMenu.focus();

    	} else {
    		//̫����ݒ�
    		frm.btnMenu[0].focus();
    	}
    }
    //��ʐݒ�
    funInitScreen(ConGenchoMenuId);

    return true;
}

//========================================================================================
// �Z�b�V�������擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// ����  �F�@Mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �T�v  �F���[�U�̃Z�b�V���������擾����
//========================================================================================
function funGetUserInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP9030";
	// ADD 2016/09/01 May Thu �yKPX@1602367�zUpdate Start
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130","FGEN3490");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I,xmlFGEN3490I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O,xmlFGEN3490O);
	// ADD 2016/09/01 May Thu �yKPX@1602367�zUpdate Start

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    //����ݏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
     //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 1, "divUserInfo");
    //���ݕ\��
    funCreateMenuButton(xmlResAry[1], ConGenchoMenuId, "divBtn");
	//maythu add start
 	funOrderInfoDisplay(xmlResAry[3], 1, "divOrderInfo");
	//maythu add end
    //�yQP@00342�z�^�C�g���\��
    var flg_busho = funXmlRead_3(xmlResAry[2], "table", "flg_kenkyu", 0, 0);
    var obj = document.getElementById("divTitle");
    if( flg_busho == "1" ){
        obj.innerHTML = "<font style=\"color:#FFFDC8;font-size:32px;font-style:bold;\">���V�T�N�C�b�N��</font>";
    }
    else{
        ConSystemId = ConSystemId_genka;
        obj.innerHTML = "<font style=\"color:#FFFDC8;font-size:32px;font-style:bold;\">���������Z�V�X�e����</font>";
        //��ʐݒ�
        funInitScreen(ConMainMenuId);
    }
    //ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 start
    // �w���v�t�@�C���p�X�擾
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "table", "help_file", 0, 0);
    //ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 end

    return true;

}
//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// ����  �F�@reqAry :�@�\ID�ʑ��MXML(�z��)
//       �F�AMode           �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {

        // ��ʏ����\��
        if (XmlId.toString() == "JSP9030") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:
                    break;
            }
        // �R�X�g�e�[�u���o�^�E���F�{�^������
        // �u�x�[�X�P���o�^�E���F�v���j���[�{�^������
        } else if (XmlId.toString() == "RGEN2160"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", "1::::::::::::1", 0);
                    break;
            }


    } else if (XmlId.toString() == "RGEN3200"){
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", "1", 0);
                break;
        }


      // ���ގ�z�˗����N�����ʒm
    }  else if (XmlId.toString() == "RGEN3390"){
            // XML��莎��R�[�h�擾
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //���C�����j���[
            wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";
            break;
        case 3:    //�f�U�C���X�y�[�X�o�^
            wUrl = "../SQ210DesignSpaceAdd/SQ210DesignSpaceAdd.jsp";
            funUrlConnect(wUrl, ConConectPost, document.frm00);
            return true;
            break;
        case 7:    //�����ޒ��B�� �J�e�S���}�X�^�����e�i���X
            wUrl = "../SQ061GentyoLiteralMst/SQ061GentyoLiteralMst.jsp";
            break;
         // �yQP@40404�z2014/09/01 TT E.Kitazawa add end
        case 9:    //���O�C��
            wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
//		// �yKPX@1602367�z2016/08/31 May Thu add end
	  }
	  if(wUrl != null) {
         //�J��
         funUrlConnect(wUrl, ConConectPost, document.frm00);
         return true;
      } else {
         return funOpen(mode);
	  }
}

//========================================================================================
// ��ʋN������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/12
// �T�v  �F��ʂ��N������
//========================================================================================
function funOpen(mode) {

    var wUrl;

    if(funTuti(mode)){
	    //�J�ڐ攻��
    	switch (mode) {
        	case 1:    //�R�X�g�e�[�u���o�^�E���F
            	funCostTblAddTuti(1);
            	wUrl = "../SQ190CostTblAdd/SQ190CostTblAdd.jsp";
            	break;
        	case 2:    //�R�X�g�e�[�u���ꗗ
            	wUrl = "../SQ180CostTblList/SQ180CostTblList.jsp";
            	break;
            case 4:    //�f�U�C���X�y�[�X�_�E�����[�h
            	wUrl = "../SQ240DesignSpaceDL/SQ240DesignSpaceDL.jsp";
            	break;
        	case 5:    //���ގ�z����
        		funShizaiCodeListAddTuti(1);
            	wUrl = "../SQ230ShizaiTehaiInput/SQ230ShizaiTehaiInput.jsp";
            	break;
			// �yKPX@1602367�z2016/08/31 May Thu add start
			case 10:   //�x�[�X�P���o�^�E���F
				funBasePriceAddTuti(1);
		    	wUrl = "../SQ280BasePriceAdd/SQ280BasePriceAdd.jsp";
		    	break;
			case 11:   //�x�[�X�P���ꗗ
		    	wUrl = "../SQ270BasePriceList/SQ270BasePriceList.jsp";
		    	break;
        	case 12:   //���ގ�z�ψꗗ
        		funShizaiCodeListAddTuti(1);
				var logic = "shizaiTehai"
				wUrl = "../SQ260ShizaiCodeList/SQ260ShizaiCodeList.jsp" + "?" + logic;
		    	break;
        	case 13:   //������}�X�^���
            	wUrl = "../SQ310HattyuLiteralMst/SQ310HattyuLiteralMst.jsp";
		    	break;
			// �yKPX@1602367�z2016/08/31 May Thu add end
        	case 14:
            	wUrl = "../SQ250ShizaiTehaiOutput/ShizaiTehaiOutput.jsp";
            	break;
        	case 15:
            	wUrl = "../SQ200CostTblRef/SQ200CostTblRef.jsp";
            	break;
            case 16:
            	wUrl = GenchoUrl;
            	break;
        }

        //�J��
        if(wUrl == GenchoUrl){
        	var chromeURL = decodeURIComponent(wUrl);
        	var lngWidth  = self.screen.width - 12;
        	var lngHeight = self.screen.height - 167;
			lngHeight = lngHeight + 109;
			lngWidth = lngWidth   + 2;
			//var strName	  = "NEBICK";
			var strOpt = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,width=" + lngWidth + ",height=" + lngHeight + ",left=0,top=0";
			var strBrowser = "legacyBrowser";
			var strOptChrome = "--start-maximized  --new-window ";
			openWindow(strBrowser, chromeURL, "", [strOpt, strOptChrome,""]);
        }else{
          var win = window.open(wUrl, "gensi","menubar=no,resizable=yes");

        	// �ĕ\���ׂ̈Ƀt�H�[�J�X�ɂ���
        	win.focus();
        }
    }
    return true;
}
function openWindow(targetBrowser, url, title, options) {
	var command = "";
	var chromeURL = "";
	var ret = 0;
	// �w�肳�ꂽ�u���E�U�ł̋N��
	switch (targetBrowser) {
	case "legacyBrowser":
		window.open(url, title, options[0]);
		break;
	default:
		break;
	}
	// �w��Ȃ��A�܂��͎w��̃u���E�U���C���X�g�[������Ă��Ȃ�
	if (targetBrowser == "" || ret == -2) {
		window.open(url, title, options[0]);
	}

	return true;
}

function funTuti(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN3390";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3390, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    return true;

}


//========================================================================================
// �I���{�^������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F��ʂ��I������
//========================================================================================
function funClose() {

    //�m�Fү���ޕ\��
    if (funConfMsgBox("�����ޒ��B�����j���[" + I000001) == ConBtnYes) {
        //��ʂ����
        close();
    }

    return true;
}

//========================================================================================
// �R�X�g�e�[�u���o�^�E���F��ʋN�����ʒm
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/27
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funCostTblAddTuti(mode) {

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
//���ގ�z�ψꗗ
//�쐬�ҁFnakamura
//�쐬���F2016/11/15
//����  �F�Ȃ�
//�T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
//========================================================================================
function funShizaiCodeListAddTuti(mode) {

 var frm = document.frm00;    //̫�тւ̎Q��
 var XmlId = "RGEN3200";
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




//ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 start
//========================================================================================
//�yQP@40812�z�w���v��ʂ�\��
//�쐬�ҁFE.Kitazawa
//�쐬���F2015/07/27
//����  �F�Ȃ�
//�߂�l�F�Ȃ�
//�T�v  �F�w���v��ʂ�\������
//========================================================================================
function funHelpDisp() {

	// �w���v�t�@�C���p�X�̓t�H�[���ɕۑ���
	// �w���v�t�@�C���ďo��
	funHelpCall()
	return true;

}
//ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 end

// ADD 2016/09/29 Koizumi �yKPX@1602367�zStart
//========================================================================================
// �R�X�g�e�[�u���o�^�E���F��ʋN�����ʒm
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/09/29
// ����  �F�Ȃ�
// �T�v  �F�I����������R�[�h���Z�b�V�����֕ۑ�����
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
//ADD 2016/09/29 Koizumi �yKPX@1602367�zEnd
