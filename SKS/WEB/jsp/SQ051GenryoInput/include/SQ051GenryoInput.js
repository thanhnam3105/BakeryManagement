
//��ʐݒ�(BODY��۰�ތ�����ق̐ݒ肪�s���Ȃ����߁ABODY��۰�ޑO�ɍs��)
funInitScreen(ConGenryoInputId);

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
	var blnCheckKakunin;
//add end --------------------------------------------------------------------------------------
// 20160513  KPX@1600766 ADD start
// �p�~���싖��
	var blnHaishiKyoka = false;
// 20160513  KPX@1600766 ADD end

//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //�߂�l�̏�����
    window.returnValue = "";

    //������ү���ޔ�\��
    funClearRunMessage();

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
	//���͏��ޔ�
	funBunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// �m�F�N���b�N����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// �T�v  �F�m�F�ҏ���\������
//========================================================================================
function funChkKakunin(obj) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var oDate;
    var oName;

    //�m�F�����ޯ����������Ԃ̏ꍇ
    if (obj.checked) {
        oDate = funGetSystemDate();
        oName = funXmlRead(xmlUSERINFO_O, "nm_user", 0);
    } else {
        oDate = "";
        oName = "";
    }

    //�m�F�ҏ��̐ݒ�
    frm.txtCheckDt.value = oDate;
    frm.txtCheckName.value = oName;

    return true;
}

//========================================================================================
// �o�^�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�Ȃ�
// �T�v  �F��ʓ��e�̓o�^�A�X�V���s���B
//========================================================================================
function funEdit() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0520";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA380");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA380I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA380O);
    var confMsg;
    var resMsg;
    var NewGenryoCd;

    if (frm.hidMode.value == 2) {
        confMsg = I000002;
    } else {
        confMsg = I000003;
    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(confMsg) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //�Ǘ��䒠No300 TT���� 2009/09/30 -------------------------START
    //�V�K�o�^�Ō������ނ��̔Ԃ���Ă��Ȃ��ꍇ
    /*if  (frm.hidMode.value == 2 && frm.txtGenryoCd.value == "") {
        //�̔ԂɕK�v�ȕϐ��̐錾
        var XmlId_Saiban = "J010";
        var FuncIdAry_Saiban = new Array(ConResult,ConUserInfo,"SA410");
        var xmlReqAry_Saiban = new Array(xmlUSERINFO_I,xmlSA410I);
        var xmlResAry_Saiban = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA410O);

        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId_Saiban, xmlReqAry_Saiban, 1) == false) {
            funClearRunMessage();
            return false;
        }

        //�̔ԏ��������s����
        if (funAjaxConnection(XmlId_Saiban, FuncIdAry_Saiban, xmlJ010, xmlReqAry_Saiban, xmlResAry_Saiban, 1) == false) {
            return false;
        }

        //�������ނ̕ҏW
        NewGenryoCd = funXmlRead(xmlResAry_Saiban[2], "new_code", 0);
        NewGenryoCd = ConGenryoCdPrefix + ("00000" + NewGenryoCd).slice(-5);
        funXmlWrite(xmlResAry_Saiban[2], "new_code", NewGenryoCd, 0);

    } else {
        NewGenryoCd = "";
    }*/
	//---------------------------------------------------------END

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0520, xmlReqAry, xmlResAry, 1) == false) {
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
		//�ޔ�l�߂�����
		funBunsekiDataBack();
//add end --------------------------------------------------------------------------------------
        return false;
    }

    //����ү���ނ̕\��
    resMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //����
        funInfoMsgBox(resMsg);


        //�Ǘ��䒠No300 TT���� 2009/09/30 -------------------------START

        /*if (NewGenryoCd != "") {
            //�������ނ̐ݒ�
            frm.txtGenryoCd.value = NewGenryoCd;
        }*/

        //�V�K�����o�^��
        if  (frm.hidMode.value == 2 && frm.txtGenryoCd.value == ""){

            //�̔Ԃ��������R�[�h���擾
            NewGenryoCd = funXmlRead(xmlResAry[2], "cd_genryo", 0);
            frm.txtGenryoCd.value = NewGenryoCd;

        }

        //---------------------------------------------------------END

        var opener = window.dialogArguments;      //�����̎擾
        var opener_form = opener.document.forms(0);

        opener_form.hidMode.value = 1;
        opener_form.hidKaishaCd.value = frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value;    //��к���
        if (opener_form.hidKojoCd.value == "") {
            opener_form.hidKojoCd.value = "0";    //�H�꺰��
        }
        opener_form.hidGenryoCd.value = frm.txtGenryoCd.value;    //��������

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
		//���͏��ޔ�
		funBunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------

        //�߂�l�̐ݒ�
        window.returnValue = "1";

    } else {
        //�ُ�
        funErrorMsgBox(resMsg);

        //�߂�l�̐ݒ�
        window.returnValue = "";
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �I���{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// �T�v  �F��ʂ����
//========================================================================================
function funClose() {

    //��ʂ����
    close(self);

    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var opener = window.dialogArguments;      //�����̎擾
    var opener_form = opener.document.forms(0);
    var frm = document.frm00;                 //̫�тւ̎Q��
    var XmlId = "JSP0510";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140","SA400");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I,xmlSA400I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O,xmlSA400O);

    //�����̑ޔ�
    frm.hidMode.value = opener_form.hidMode.value;            //�N��Ӱ�ށi1�F�ڍׁA2�F�V�K�j
    if (frm.hidMode.value == 1) {
        frm.hidKaishaCd.value = opener_form.hidKaishaCd.value;    //��к���
        frm.hidKojoCd.value = opener_form.hidKojoCd.value;    //��������
        frm.hidGenryoCd.value = opener_form.hidGenryoCd.value;    //��������
    }

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0510, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);

    //��ʂ̏�����
// 20160513  KPX@1600766 MOD start
//    funInit(xmlResAry[3]);
    if (funInit(xmlResAry[3]) == false) {
        funClearRunMessage();
        funClose();
        return false;
    }
// 20160513  KPX@1600766 MOD end

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
    if(frm.chkKakunin.checked == true){
    	blnCheckKakunin=true;
    }
    else{
	    blnCheckKakunin=false;
    }
//add end --------------------------------------------------------------------------------------

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�o�^���݂̐���
    frm.btnEdit.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //�������ނ������͂̏ꍇ
        if (funXmlRead(xmlSA400O, "cd_genryo", 0) == "") {
            //���͒l����(�V�K����)
            if (GamenId.toString() == ConGmnIdGenryoInputNew.toString()) {
                //�V�K
                if (KinoId.toString() == ConFuncIdNew.toString()) {
                    frm.btnEdit.disabled = false;
                }
            }

        } else {
            //���͒l����(��������)
            if (GamenId.toString() == ConGmnIdGenryoInputUpd.toString()) {
                //�ҏW(�V�K�����̂�)
                if (KinoId.toString() == ConFuncIdEdit.toString()) {
                    //�V�K�����̏ꍇ
                    if (isNaN(funXmlRead(xmlSA400O, "cd_genryo", 0).charAt(0))) {
                        frm.btnEdit.disabled = false;
                    }

                //�ҏW(�S��)
                } else if (KinoId.toString() == ConFuncIdAll.toString()) {
                    frm.btnEdit.disabled = false;

// 20160513  KPX@1600766 ADD start
                    //�ҏW(�V�X�e���Ǘ���) �p�~���싖��
                } else if (KinoId.toString() == ConFuncIdSysMgr.toString()) {
                    frm.btnEdit.disabled = false;
                    //�p�~���싖��
                    blnHaishiKyoka = true;
// 20160513  KPX@1600766 ADD end
                }
            }
        }
    }

    return true;

}

//========================================================================================
// ��ʏ���������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�@xmlData �F �������XML
// �T�v  �F��ʂ�����������
//========================================================================================
function funInit(xmlData) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    //��ʐ���
    if (frm.hidMode.value == 1) {
        //�ڍ�Ӱ��
        funItemReadOnly(frm.ddlKaisha, true);
        funItemReadOnly(frm.txtGenryoCd, true);

        //�V�K�����̏ꍇ
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
        	funItemReadOnly(frm.txtGenryoName, false);
        } else {
            funItemReadOnly(frm.txtGenryoName, true);
        }

        funItemReadOnly(frm.txtHyojian, false);
        funItemReadOnly(frm.txtTenkabutu, false);
        for (i = 0; i < 5; i++) {
            funItemReadOnly(frm.txtEiyoNo[i], false);
            funItemReadOnly(frm.txtWariai[i], false);
        }
        funItemReadOnly(frm.txtInputDt, true);
        funItemReadOnly(frm.txtInputName, true);
        funItemReadOnly(frm.chkKakunin, false);
        funItemReadOnly(frm.txtCheckDt, true);
        funItemReadOnly(frm.txtCheckName, true);
// 20160513  KPX@1600766 MOD start
        //�p�~�`�F�b�N�̓V�X�e���Ǘ��҂̂�
//        funItemReadOnly(frm.chkHaishi, false);
        funItemReadOnly(frm.chkHaishi, true);
// 20160513  KPX@1600766 MOD end
        funItemReadOnly(frm.txtKakuteiCd, false);

        //���������A���͐V�K�����Ŕp�~�敪��1�̏ꍇ
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0)) == false ||
            (funXmlRead(xmlData, "kbn_haishi", 0) == "1" && isNaN(funXmlRead(xmlData, "cd_genryo", 0)))) {
            funItemReadOnly(frm.chkHaishi, true);
            funItemReadOnly(frm.txtKakuteiCd, true);
        }

        //�V�K�����Ŕp�~�敪��1�̏ꍇ
        if (funXmlRead(xmlData, "kbn_haishi", 0) == "1" && isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
            funItemReadOnly(frm.btnEdit, true);
        }

// 20160513  KPX@1600766 ADD start
        // ��ʌ������V�X�e���Ǘ��ҁi�p�~���싖�j�̎��̂݃`�F�b�N�A�p�~������
        if (blnHaishiKyoka) {
        	//�V�K����
        	if (isNaN(funXmlRead(xmlData, "cd_genryo", 0)) == true) {
        		funItemReadOnly(frm.chkHaishi, false);
        	}
        	funItemReadOnly(frm.btnEdit, false);
        }
// 20160513  KPX@1600766 ADD end

        //��ʏ��̐ݒ�
//20160513  KPX@1600766 MOD start
        //�\�������Ђ��R���{�{�b�N�X�ɑ��݂��Ȃ����A�G���[�Ƃ���B
//        funDefaultIndex(frm.ddlKaisha, 2);
        if (funDefaultIndex(frm.ddlKaisha, 2) == false) {
        	funErrorMsgBox("�Ώۉ�Ђ̌����͏ڍו\����������Ă��܂���B");
        	return false;
        }
//20160513  KPX@1600766 MOD end
// 20160610  KPX@1502111_No.5 ADD start
        //�V�K�����̏ꍇ
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
        	//�V�K���������R�[�h�ޔ�
        	frm.hidGenryoBushoCd.value = funXmlRead(xmlData, "cd_busho", 0);
        	//�V�K�����̘A�g���擾�E�\��
        	funRenkeiDsp();
        }
// 20160610  KPX@1502111_No.5 ADD end

        frm.txtGenryoCd.value = funXmlRead(xmlData, "cd_genryo", 0);
        frm.txtGenryoName.value = funXmlRead(xmlData, "nm_genryo", 0);
        frm.txtSakusan.value = funXmlRead(xmlData, "ritu_sakusan", 0);
        frm.txtShokuen.value = funXmlRead(xmlData, "ritu_shokuen", 0);
        frm.txtSosan.value = funXmlRead(xmlData, "ritu_sousan", 0);
        frm.txtGanyu.value = funXmlRead(xmlData, "ritu_abura", 0);
// ADD start 20121005 QP@20505 No.24
        frm.txtMsg.value = funXmlRead(xmlData, "ritu_msg", 0);
// ADD end 20121005 QP@20505 No.24
        frm.txtHyojian.value = funXmlRead(xmlData, "hyojian", 0);
        frm.txtTenkabutu.value = funXmlRead(xmlData, "tenkabutu", 0);
        for (i = 0; i < 5; i++) {
            frm.txtEiyoNo[i].value = funXmlRead(xmlData, "no_eiyo" + (i+1), 0);
            frm.txtWariai[i].value = funXmlRead(xmlData, "wariai" + (i+1), 0);
        }
        frm.txtMemo.value = funXmlRead(xmlData, "memo", 0);
        //frm.txtInputDt.value = funXmlRead(xmlData, "dt_toroku", 0);
        //frm.txtInputName.value = funXmlRead(xmlData, "nm_toroku", 0);
        frm.txtInputDt.value = funXmlRead(xmlData, "dt_koshin", 0);
        frm.txtInputName.value = funXmlRead(xmlData, "nm_koshin", 0);
        if (funXmlRead(xmlData, "dt_kakunin", 0) != "") {
            frm.chkKakunin.checked = true;
            frm.txtCheckDt.value = funXmlRead(xmlData, "dt_kakunin", 0);
            frm.txtCheckName.value = funXmlRead(xmlData, "nm_kakunin", 0);
        } else {
            frm.chkKakunin.checked = false;
            frm.txtCheckDt.value = "";
            frm.txtCheckName.value = "";
        }
        if (funXmlRead(xmlData, "kbn_haishi", 0) == "1") {
            frm.chkHaishi.checked = true;
        } else {
            frm.chkHaishi.checked = false;
        }
        frm.txtKakuteiCd.value = funXmlRead(xmlData, "cd_kakutei", 0);

        //̫����ݒ�
        if (isNaN(funXmlRead(xmlData, "cd_genryo", 0))) {
            frm.txtGenryoName.focus();
        } else {
            frm.txtSakusan.focus();
        }

    } else {
        //�V�K����Ӱ��
        funItemReadOnly(frm.txtGenryoCd, true);
        funItemReadOnly(frm.txtHyojian, true);
        funItemReadOnly(frm.txtTenkabutu, true);
        for (i = 0; i < 5; i++) {
            funItemReadOnly(frm.txtEiyoNo[i], true);
            funItemReadOnly(frm.txtWariai[i], true);
        }
        funItemReadOnly(frm.txtInputDt, true);
        funItemReadOnly(frm.txtInputName, true);
        funItemReadOnly(frm.chkKakunin, true);
        funItemReadOnly(frm.txtCheckDt, true);
        funItemReadOnly(frm.txtCheckName, true);
        funItemReadOnly(frm.chkHaishi, true);
        funItemReadOnly(frm.txtKakuteiCd, true);
        //��ʏ��̐ݒ�
        funDefaultIndex(frm.ddlKaisha, 1);
        frm.txtInputDt.value = funGetSystemDate();
        frm.txtInputName.value = funXmlRead(xmlUSERINFO_O, "nm_user", 0);

        //̫����ݒ�
        frm.ddlKaisha.focus();
    }

// 20160610  KPX@1502111_No.5 ADD start
    //�A�g���F�S��txtSampleNo
    funItemReadOnly(frm.txtShainCd, true);
    funItemReadOnly(frm.txtNen, true);
    funItemReadOnly(frm.txtOiNo, true);
    funItemReadOnly(frm.txtEdaNo, true);
    funItemReadOnly(frm.txtHinNm, true);
    funItemReadOnly(frm.txtSampleNo, true);
// 20160610  KPX@1502111_No.5 ADD end

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
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
        if (XmlId.toString() == "JSP0510") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    if (frm.hidMode.value == 1) {
                        //�ڍ�
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInputUpd, 0);
                    } else {
                        //�V�K
                        funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInputNew, 0);
                    }
                    break;
                case 2:    //SA400
                    if (frm.hidMode.value == 1) {
                        //�ڍ�
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_busho", frm.hidKojoCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_genryo", frm.hidGenryoCd.value, 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "2", 0);
                    } else {
                        //�V�K
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_busho", frm.hidKojoCd.value, 0);
                        funXmlWrite(reqAry[i], "cd_genryo", "", 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    }
                    break;
            }

        //�o�^���݉���
        } else if (XmlId.toString() == "JSP0520"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA380
                    if (frm.ddlKaisha.length == 0) {
                        funXmlWrite(reqAry[i], "cd_kaisha", "", 0);
                    } else {
                        funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    }
                    //���͎ҏ��
                    if (frm.hidMode.value == 1) {
                        //�X�V
                        //funXmlWrite(reqAry[i], "id_toroku", funXmlRead(xmlSA400O, "id_toroku", 0), 0);
                        //funXmlWrite(reqAry[i], "dt_toroku", funXmlRead(xmlSA400O, "dt_toroku", 0), 0);
                        funXmlWrite(reqAry[i], "id_koshin", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_koshin", funGetSystemDate(), 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "1", 0);
                    } else {
                        //�o�^
                        funXmlWrite(reqAry[i], "cd_genryo", funXmlRead(xmlSA410O, "new_code", 0), 0);
                        funXmlWrite(reqAry[i], "id_toroku", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_toroku", funGetSystemDate(), 0);
                        funXmlWrite(reqAry[i], "kbn_shori", "0", 0);
                    }
                    //�m�F�ҏ��
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
                    //�X�V���̊m�F
                    var fg = funKoshinJohoCheck();
                    var fg_henkou = funBunsekiDataHensyuCheck();

                    if(fg_henkou == 1){
	                    funXmlWrite(reqAry[i], "fg_henkou", "true", 0);
                    }
                    else{
                    	funXmlWrite(reqAry[i], "fg_henkou", "false", 0);
                    }

                    if (fg == 1) {
                    	//�m�F���Ƀ��[�U�����i�[
                        funXmlWrite(reqAry[i], "kbn_kakunin", "1", 0);
                        funXmlWrite(reqAry[i], "id_kakunin", funXmlRead(xmlUSERINFO_O, "id_user", 0), 0);
                        funXmlWrite(reqAry[i], "dt_kakunin", funGetSystemDate(), 0);
                    } else if(fg == 0) {
                    	//�m�F����NULL���i�[
                        funXmlWrite(reqAry[i], "kbn_kakunin", "", 0);
                        funXmlWrite(reqAry[i], "id_kakunin", "", 0);
                        funXmlWrite(reqAry[i], "dt_kakunin", "", 0);
                    }else{
                    	return false;
                    }
//mod end --------------------------------------------------------------------------------------

                    if (frm.chkHaishi.checked) {
                        //�p�~
                        funXmlWrite(reqAry[i], "kbn_haishi", "1", 0);
                    } else {
                        //�g�p��
                        funXmlWrite(reqAry[i], "kbn_haishi", "", 0);
                    }
                    break;
            }

        //�����̔�
        } else if (XmlId.toString() == "J010"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA410
                    funXmlWrite(reqAry[i], "kbn_shori", "cd_genryo", 0);
                    break;
            }
        //�����A�g�F�z�������N�擾
        } else if (XmlId.toString() == "JW821"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2260
                	//�����R�[�h��茟��
                	funXmlWrite(reqAry[i], "syoriMode", "0", 0);
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.hidKaishaCd.value, 0);
                    funXmlWrite(reqAry[i], "cd_genryo", frm.hidGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "cd_busho", frm.hidGenryoBushoCd.value, 0);
                    break;
            }
        //�����A�g�F���얼�E�T���v��No�擾
        } else if (XmlId.toString() == "JW822"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2270
                	//�����R�[�h��茟��
                	funXmlWrite(reqAry[i], "syoriMode", "1", 0);
                    funXmlWrite(reqAry[i], "cd_shain", frm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", frm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", frm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", frm.txtEdaNo.value, 0);
//                    funXmlWrite(reqAry[i], "seq_shisaku", frm.hidShisakuSeq.value, 0);
                    break;
            }
        }

    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;                  //�ݒ�XML�̌���
    var atbName;
    var atbCd;
    var i;

    //�����擾
    reccnt = funGetLength(xmlData);

    //�����ޯ���̸ر
    funClearSelect(obj, 1);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //���Ͻ�
            atbName = "nm_kaisha";
            atbCd = "cd_kaisha";
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
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
//20160513  KPX@1600766 ADD start
//    var selIndex;
    var selIndex = -1;
//20160513  KPX@1600766 ADD end
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //��к���(հ�ޏ��̉��)
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //��к���(�o�^�ς̉��)
                if (obj.options[i].value == funXmlRead(xmlSA400O, "cd_kaisha", 0)) {
                    selIndex = i;
                }
                break;
        }
    }
//20160513  KPX@1600766 ADD start
    //�R���{�{�b�N�X�ɕ\�������Ђ����݂��Ȃ���
    if (selIndex < 0) return false;
//20160513  KPX@1600766 ADD end

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �V�X�e�����t�擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/03
// ����  �F�Ȃ�
// �߂�l�F�V�X�e�����t(YYYY/MM/DD�`��)
// �T�v  �F�V�X�e�����t���擾����
//========================================================================================
function funGetSystemDate() {

    var wDate;
    var wDay;
    var oDate;

    //���ѓ��t�̎擾
    wDate = new Date();
    oDate = wDate.getYear();
    wDay = wDate.getMonth() + 1;
    if (wDay.toString().length == 1) {
        oDate += "/0" + wDay;
    } else {
        oDate += "/" + wDay;
    }
    wDay = wDate.getDate();
    if (wDay.toString().length == 1) {
        oDate += "/0" + wDay;
    } else {
        oDate += "/" + wDay;
    }

    return oDate;

}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
//========================================================================================
// ���͏��ޔ�����
// �쐬�ҁFTT.k-katayama
// �쐬���F2010/10/13
// �T�v  �F���͏���ޔ�������
//========================================================================================
function funBunsekiDataTaihi() {
    var frm = document.frm00;                 //̫�тւ̎Q��

    frm.hidGenryoName.value = frm.txtGenryoName.value;
	frm.hidSakusan.value = frm.txtSakusan.value;
	frm.hidShokuen.value = frm.txtShokuen.value;
	frm.hidSosan.value = frm.txtSosan.value;
	frm.hidGanyu.value = frm.txtGanyu.value;
// ADD start 20121005 QP@20505 No.24
	frm.hidMsg.value = frm.txtMsg.value;
// ADD end 20121005 QP@20505 No.24
	frm.hidHyojian.value = frm.txtHyojian.value;
	frm.hidTenkabutu.value = frm.txtTenkabutu.value;
	frm.hidEiyoNo1.value = frm.txtEiyoNo[0].value;
	frm.hidEiyoNo2.value = frm.txtEiyoNo[1].value;
	frm.hidEiyoNo3.value = frm.txtEiyoNo[2].value;
	frm.hidEiyoNo4.value = frm.txtEiyoNo[3].value;
	frm.hidEiyoNo5.value = frm.txtEiyoNo[4].value;
	frm.hidWariai1.value = frm.txtWariai[0].value;
	frm.hidWariai2.value = frm.txtWariai[1].value;
	frm.hidWariai3.value = frm.txtWariai[2].value;
	frm.hidWariai4.value = frm.txtWariai[3].value;
	frm.hidWariai5.value = frm.txtWariai[4].value;
	frm.hidMemo.value = frm.txtMemo.value;
	frm.hidKakuteiCd.value = frm.txtKakuteiCd.value;
	//�m�F���̑ޔ�
//20160817  KPX@1502111 DEL start
	//funGetInfo �ɂ� blnCheckKakunin �ɑޔ�
//	frm.hidKakunin.value = frm.chkKakunin.checked;
//20160817  KPX@1502111 DEL end
	frm.hidCheckDt.value = frm.txtCheckDt.value;
	frm.hidCheckName.value = frm.txtCheckName.value;

}

//========================================================================================
// ���͏��ҏW�`�F�b�N����
// �쐬�ҁFTT.k-katayama
// �쐬���F2010/10/13
// �T�v  �F���͏���ޔ�������
//========================================================================================
function funBunsekiDataHensyuCheck() {
    var frm = document.frm00;                 //̫�тւ̎Q��

    //���͍��ڂ��ҏW����Ă���ꍇ�A1��ԋp����
	if ( frm.hidGenryoName.value != frm.txtGenryoName.value ) {
		return 1;
	}
	if ( frm.hidSakusan.value != frm.txtSakusan.value ) {
		return 1;
	}
	if ( frm.hidShokuen.value != frm.txtShokuen.value ) {
		return 1;
	}
	if ( frm.hidSosan.value != frm.txtSosan.value ) {
		return 1;
	}
	if ( frm.hidGanyu.value != frm.txtGanyu.value ) {
		return 1;
	}
// ADD start 20121005 QP@20505 No.24
	if ( frm.hidMsg.value != frm.txtMsg.value ) {
		return 1;
	}
// ADD end 20121005 QP@20505 No.24
	if ( frm.hidHyojian.value != frm.txtHyojian.value ) {
		return 1;
	}
	if ( frm.hidTenkabutu.value != frm.txtTenkabutu.value ) {
		return 1;
	}
	if ( frm.hidEiyoNo1.value != frm.txtEiyoNo[0].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo2.value != frm.txtEiyoNo[1].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo3.value != frm.txtEiyoNo[2].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo4.value != frm.txtEiyoNo[3].value ) {
		return 1;
	}
	if ( frm.hidEiyoNo5.value != frm.txtEiyoNo[4].value ) {
		return 1;
	}
	if ( frm.hidWariai1.value != frm.txtWariai[0].value ) {
		return 1;
	}
	if ( frm.hidWariai2.value != frm.txtWariai[1].value ) {
		return 1;
	}
	if ( frm.hidWariai3.value != frm.txtWariai[2].value ) {
		return 1;
	}
	if ( frm.hidWariai4.value != frm.txtWariai[3].value ) {
		return 1;
	}
	if ( frm.hidWariai5.value != frm.txtWariai[4].value ) {
		return 1;
	}
	if ( frm.hidMemo.value != frm.txtMemo.value ) {
		return 1;
	}
	if ( frm.hidKakuteiCd.value != frm.txtKakuteiCd.value ) {
		return 1;
	}

	return 0;

}

//========================================================================================
// �X�V���m�F����
// �쐬�ҁFTT.k-katayama
// �쐬���F2010/10/13
// �T�v  �F�X�V�����m�F����
//========================================================================================
function funKoshinJohoCheck() {
    var frm = document.frm00;                 //̫�тւ̎Q��
    var retFlg = 0;
    var confMsg = "�m�F�ς݃f�[�^�̂��ߊm�F�҂��N���A���ĕۑ����܂�����낵���ł��傤���H";

	//�m�F�`�F�b�N

	if(blnCheckKakunin){
		if (frm.chkKakunin.checked) {
			//���̓f�[�^�ҏW�`�F�b�N
			if (funBunsekiDataHensyuCheck() == 1) {
				//���̓f�[�^���ҏW����Ă���ꍇ

				//YES�ENO����
			    if (funConfMsgBox(confMsg) == ConBtnYes){
			    	//�u�͂��v����
			    	retFlg = 0;
			    } else {
			    	//�u�������v����
			    	retFlg = 2;
			    }

			} else {
				//���̓f�[�^�����ҏW�̏ꍇ
		    	retFlg = 1;

			}
		} else {
	    	retFlg = 0;
		}
	}
	else{
		if(frm.chkKakunin.checked){
			retFlg = 1;
		}
		else{
			retFlg = 0;
		}
	}

	return retFlg;

}

//========================================================================================
// �ޔ�l�߂�����
// �쐬�ҁFTT.k-katayama
// �쐬���F2010/10/13
// �T�v  �F�ޔ����Ă������͏���߂�
//========================================================================================
function funBunsekiDataBack() {
    var frm = document.frm00;                 //̫�тւ̎Q��

//20160817  KPX@1502111 MOD start
//    frm.chkKakunin.checked = frm.hidKakunin.value;
    frm.chkKakunin.checked = blnCheckKakunin;
//20160817  KPX@1502111 MOD end
    frm.txtCheckDt.value = frm.hidCheckDt.value;
	frm.txtCheckName.value = frm.hidCheckName.value;

}
//add end --------------------------------------------------------------------------------------

//20160610  KPX@1502111_No.5 ADD start
//========================================================================================
//�V�K�����̘A�g���擾�E�\������
//�쐬�ҁFTT.kitazawa
//�쐬���F2016/06/10
//�T�v  �F�V�K�����\���Ŏ��ƌ����̘A�g�����擾���A�\������
//========================================================================================
function funRenkeiDsp() {

    var frm = document.frm00;                 //̫�тւ̎Q��
    var XmlId = "JW821";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2260");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2260I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2260O);

    var FuncIdAry2 = new Array(ConResult,ConUserInfo,"FGEN2270");
    var xmlReqAry2 = new Array(xmlUSERINFO_I,xmlFGEN2270I);
    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2270O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJW821, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //�f�[�^���擾�ł�����
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true" ) {
    	frm.txtShainCd.value = funXmlRead(xmlResAry[2], "cd_shain", 0);
    	frm.txtNen.value = funXmlRead(xmlResAry[2], "nen", 0);
    	frm.txtOiNo.value = funXmlRead(xmlResAry[2], "no_oi", 0);
    	frm.txtEdaNo.value = funXmlRead(xmlResAry[2], "no_eda", 0);
    	frm.hidShisakuSeq.value = funXmlRead(xmlResAry[2], "seq_shisaku", 0);

        //������XMĻ�قɐݒ�
        if (funReadyOutput("JW822", xmlReqAry2, 1) == false) {
            funClearRunMessage();
            return false;
        }

        //����ݏ��A�����ޯ���̏����擾
        if (funAjaxConnection(XmlId, FuncIdAry2, xmlJW821, xmlReqAry2, xmlResAry2, 1) == false) {
            return false;
        }

    	//���얼
        frm.txtHinNm.value = funXmlRead(xmlResAry2[2], "nm_hin", 0);
        //�T���v��No
        frm.txtSampleNo.value = funGetSampleNo(frm.hidShisakuSeq.value, xmlResAry2[2]);

    }
    return true;
}

//========================================================================================
//�V�K�����̘A�g���擾�E�\������
//�쐬�ҁFTT.kitazawa
//�쐬���F2016/06/10
//����  �F�@shisakuSeq  �F����SEQ
//      �F�AxmlData     �FXML
//�߂�l�F�T���v��No
//�T�v  �F����SEQ�ɊY������T���v��No���擾����
//========================================================================================
function funGetSampleNo(shisakuSeq, xmlData) {

	var reccnt;                  //�ݒ�XML�̌���
    var i;
    var retSampleNo = "";        //�߂�l

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return retSampleNo;
    }
    //�T���v��No���擾���Ă��Ȃ��ꍇ
    if (funXmlRead(xmlData, "flg_return", 0) == "" ) {
    	return retSampleNo;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, "seq_shisaku", i) == shisakuSeq) {
        	retSampleNo = funXmlRead(xmlData, "nm_sample", i);
        	break;
        }
    }

    return retSampleNo;
}

//20160610  KPX@1502111_No.5 ADD end
