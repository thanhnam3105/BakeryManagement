//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʐݒ�
    funInitScreen(ConLoginId);

    //������ү���ޕ\��
    funShowRunMessage();

    //�V���O���T�C���I���̏ꍇ
    if (frm.hidMode.value == "1") {
        //հ�ޔF�؂��s��
        funChkLogin();
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    //̫����ݒ�
    frm.txtUserId.focus();

    return true;
}

//========================================================================================
// ���O�C�����`�F�b�N����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F���[�U�̔F�؂��s��
//========================================================================================
function funChkLogin() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA010");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA010I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA010O);

    // ADD 2013/9/25 okano�yQP@30151�zNo.28 start
    var XmlId2 = "JSP0020";
    var FuncIdAry2 = new Array(ConResult,ConUserInfo,"SA020");
    var xmlReqAry2 = new Array(xmlUSERINFO_I,xmlSA020I);
    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA020O);
    // ADD 2013/9/25 okano�yQP@30151�zNo.28 end

    //������XMĻ�قɐݒ�
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//	    if (funReadyOutput(xmlReqAry) == false) {
//	        //۸޲݉�ʂ���̋N���ɐؑ�
//	        frm.hidMode.value = "2";
//	        funClearRunMessage();
//	        return false;
//	    }
//
//	    //հ�ޔF�؂��s��
//	    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
//	        //۸޲݉�ʂ���̋N���ɐؑ�
//	        frm.hidMode.value = "2";
//	        return false;
//	    } else {
//	        //�ƭ��ɑJ��
//	        funNext();
//	    }
//
//	    return true;

    if (funReadyOutput(xmlReqAry)) {
        if (funReadyOutput(xmlReqAry2)) {
        	//հ�ޔF�؂��s��
            if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0010, xmlReqAry, xmlResAry, 2) == false) {
                //۸޲݉�ʂ���̋N���ɐؑ�
                frm.hidMode.value = "2";
                return false;
            } else {
            	if(funXmlRead(xmlResAry[2],"flg_sign",0) == "true"){
//DEL 2014/06/06 hisahori �yQP@30154�z�ۑ�No.41 �V���O���T�C���I�����A�L���b�V�����X����̐V���[�U�}�X�^�l���Q�Ƃ��Ȃ�
//            		funSignonLogin();
//DEL 2014/06/06
            	} else {
	            	//�߽ܰ������
	            	if(funAjaxConnection(XmlId2, FuncIdAry2, xmlJSP0020, xmlReqAry2, xmlResAry2, 2) == false){
	                    //�ƭ��ɑJ��
	            		var wUrl;
	                	if(funXmlRead(xmlResAry[2],"flg_eigyo",0) == "true"){
	                		wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";
	                	} else {
	                		wUrl = "../SQ080TantoMst/SQ080TantoMst.jsp";
	                	}
	                	frm.hidUserId.value = funXmlRead(xmlResAry[1],"id_user",0);
	                	funPassLogin(wUrl);
	            	} else {
	            		funNext();
	            	}
            	}
                return true;
            }
        }
    }

    //۸޲݉�ʂ���̋N���ɐؑ�
    frm.hidMode.value = "2";
    funClearRunMessage();
    return false;
    // MOD 2013/9/25 okano�yQP@30151�zNo.28 end

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// ����  �F�@reqAry :�@�\ID�ʑ��MXML(�z��)
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(reqAry) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                if (frm.hidMode.value == "1") {
                    funXmlWrite(reqAry[i], "id_user", frm.hidUserId.value, 0);
                } else {
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                }
                break;

            case 1:    //SA010
                if (frm.hidMode.value == "1") {
                    funXmlWrite(reqAry[i], "id_user", frm.hidUserId.value, 0);
                } else {
                    funXmlWrite(reqAry[i], "id_user", frm.txtUserId.value, 0);
                }
                funXmlWrite(reqAry[i], "password", frm.txtPass.value, 0);
                funXmlWrite(reqAry[i], "kbn_login", frm.hidMode.value, 0);
                break;
        }
    }

    return true;

}

//========================================================================================
// ���C�����j���[��ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F���j���[��ʂɑJ�ڂ���
//========================================================================================
function funNext() {

    var wUrl;

    wUrl = "../SQ020MainMenu/SQ020MainMenu.jsp";

    //Ҳ��ƭ���\��
    funUrlConnect(wUrl, ConConectPost, document.frm00);
//    window.open(wUrl, "shisaquick", "menubar=no,resizable=yes");

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
    if (funConfMsgBox("���O�C��" + I000001) == ConBtnYes) {
        //��ʂ����
        close();
    }

    return true;
}

//========================================================================================
// �yQP@00342�z�c�Ɠo�^�փ{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F�c�Ɠo�^�p�̉����[�U�Ń��O�C�����A�S���҃}�X�^�����e�i�c�Ɓj�֑J��
//========================================================================================
function funEigyoLogin() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2110";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput_Eigyo(xmlReqAry) == false) {
        funClearRunMessage();
        return false;
    }

    //հ�ޔF�؂��s��
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
        return false;
    } else {
        //�S���҃}�X�^�����e�i�c�Ɓj�ɑJ��
        funNext_Eigyo();
    }

    return true;

}

//========================================================================================
// �yQP@00342�zXML�t�@�C���ɏ������݁i�c�Ɠo�^�ցj
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@reqAry :�@�\ID�ʑ��MXML(�z��)
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput_Eigyo(reqAry) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
                funXmlWrite(reqAry[i], "id_user", "9090909090", 0);

                if(frm.hidUserId.value == ""){

                }
                else{
                	funBuryZero(frm.hidUserId, 10);
                	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
                }

                break;
        }
    }

    return true;

}


//========================================================================================
// �yQP@00342�z�S���҃}�X�^�����e�i�c�Ɓj��ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F���j���[��ʂɑJ�ڂ���
//========================================================================================
function funNext_Eigyo() {

    var wUrl;

    wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";

    //�S���҃}�X�^�����e�i�c�Ɓj��\��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
//========================================================================================
//�yQP@00342�z�ݸ�ٻ�ݵ�-�����ڽ������
//�쐬�ҁFOkano
//�쐬���F2013/09/20
//�T�v  �F�o�^�p�̉����[�U�Ń��O�C�����A�e�����ɑΉ������S���҃}�X�^�����e�֑J��
//========================================================================================
function funSignonLogin() {

 var frm = document.frm00;    //̫�тւ̎Q��
 var XmlId = "RGEN2110";
 var FuncIdAry = new Array(ConResult,ConUserInfo);
 var xmlReqAry = new Array(xmlUSERINFO_I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

 //������XMĻ�قɐݒ�
 if (funReadyOutput_Signon(xmlReqAry) == false) {
     funClearRunMessage();
     return false;
 }

 //հ�ޔF�؂��s��
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
     return false;
 } else {
     //�S���҃}�X�^�����e�ɑJ��
     funNext_Signon();
 }

 return true;

}

//========================================================================================
//�yQP@00342�zXML�t�@�C���ɏ�������
//�쐬�ҁFOkano
//�쐬���F2013/09/20
//����  �F�@reqAry :�@�\ID�ʑ��MXML(�z��)
//�߂�l�F�Ȃ�
//�T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput_Signon(reqAry) {

 var frm = document.frm00;    //̫�тւ̎Q��
 var i;

 for (i = 0; i < reqAry.length; i++) {
     switch (i) {
         case 0:    //USERINFO
             funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
             funXmlWrite(reqAry[i], "id_user", "9090909092", 0);

             if(frm.hidUserId.value == ""){

             }
             else{
             	funBuryZero(frm.hidUserId, 10);
             	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
             }

             break;
     }
 }

 return true;

}

//========================================================================================
//�yQP@00342�z�S���҃}�X�^�����e��ʑJ�ڏ���
//�쐬�ҁFM.Jinbo
//�쐬���F2009/03/19
//�T�v  �F���j���[��ʂɑJ�ڂ���
//========================================================================================
function funNext_Signon() {

    var wUrl;

    if(funXmlRead(xmlSA010O,"flg_eigyo",0) == "true"){
        wUrl = "../SQ150EigyoTantoMst/SQ150EigyoTantoMst.jsp";
    } else {
        wUrl = "../SQ080TantoMst/SQ080TantoMst.jsp";
    }

    //�S���҃}�X�^�����e��\��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
//�yQP@00342�z�p�X���[�h�`�F�b�N���s��
//�쐬�ҁFY.Nishigawa
//�쐬���F2011/01/28
//�T�v  �F�p�X���[�h�ύX�p�̉����[�U�Ń��O�C�����A���[�U�ɑΉ������S���҃}�X�^�����e�֑J��
//========================================================================================
function funPassLogin(wUrl) {

var frm = document.frm00;    //̫�тւ̎Q��
var XmlId = "RGEN2110";
var FuncIdAry = new Array(ConResult,ConUserInfo);
var xmlReqAry = new Array(xmlUSERINFO_I);
var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

//������XMĻ�قɐݒ�
if (funReadyOutput_Pass(xmlReqAry) == false) {
   funClearRunMessage();
   return false;
}

//հ�ޔF�؂��s��
if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2110, xmlReqAry, xmlResAry, 2) == false) {
   return false;
} else {
   //�S���҃}�X�^�����e�ɑJ��
   funUrlConnect(wUrl, ConConectPost, document.frm00);
}

return true;

}

//========================================================================================
//�yQP@00342�zXML�t�@�C���ɏ������݁i�c�Ɠo�^�ցj
//�쐬�ҁFY.Nishigawa
//�쐬���F2011/01/28
//����  �F�@reqAry :�@�\ID�ʑ��MXML(�z��)
//�߂�l�F�Ȃ�
//�T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput_Pass(reqAry) {

var frm = document.frm00;    //̫�тւ̎Q��
var i;

for (i = 0; i < reqAry.length; i++) {
   switch (i) {
       case 0:    //USERINFO
           funXmlWrite(reqAry[i], "kbn_shori", 2, 0);
           funXmlWrite(reqAry[i], "id_user", "9090909091", 0);

           if(frm.hidUserId.value == ""){

           }
           else{
           	funBuryZero(frm.hidUserId, 10);
           	funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", frm.hidUserId.value + ":::", 0);
           }

           break;
   }
}

return true;

}
// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

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
 var frm = document.frm00;    //̫�тւ̎Q��

 var XmlId = "JSP9030";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130");
 var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I);
 var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O);

 //������XMĻ�قɐݒ�
 if (funReadyOutput_Eigyo(xmlReqAry) == false) {
     funClearRunMessage();
     return false;
 }

 //���[�U���擾�̂�
 if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP9030, xmlReqAry, xmlResAry, 3) == false) {
     return false;
 }

 // �w���v�t�@�C���p�X�擾�F�t�H�[���ɕۑ�����
 frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "table", "help_file", 0, 0);

 // �w���v�t�@�C���ďo��
 funHelpCall();
 return true;

}
//ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 end


