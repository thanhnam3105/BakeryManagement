
//========================================================================================
// ���ʕϐ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
//�F�w��
 var color_read="#ffffff";
 var color_henshu="#ffff88";

var CurrentRow;
//�yQP@10713�z20111114 hagiwara add start
var cnt_sample;
//�yQP@10713�z20111114 hagiwara add end

//20160513  KPX@1600766 ADD start
// �O���[�v��ВP���J���t���O�i���e�����}�X�^�j
var tankaHyoujiFlg = "";
//20160513  KPX@1600766 ADD end

// �y�����\���s��Ή��z20211108 BRC.yanagita ADD start
// ��ʏ����\���t���O�i�������Z�j
var shokiHyoujiFlg = "0";
// �y�����\���s��Ή��z20211108 BRC.yanagita ADD end


//========================================================================================
// ��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// �T�v  �F��ʂ̏����������s��
//========================================================================================
function funLoad() {

    return true;

}


//========================================================================================
// �w�b�_�[�t���[���A�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_head() {

    //̫�тւ̎Q��
    var frm = document.frm00;

  //�yQP@30297�zadd start 20140501
    //���ʏ��\��
    funGetKyotuInfo(1);
  //�yQP@30297�zadd end 20140501

    return true;

}

//========================================================================================
// ���׃t���[���A�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_dtl() {

    //��{���\��
    funKihonHyoji();
    //ADD 20140501 start �yQP@30297�z
    funSaveKengenInfo2();
    //ADD 20140501 end
    return true;

}

// ADD start 20140919 �yQP@30154�zNo.11�̉ۑ�Ή�
// ========================================================================================
// ��ʃ��[�h��A�X�e�[�^�X�ɂ����DB�l��␳
// �쐬�ҁFhisahori
// �쐬���F2014/09/19
// �T�v �F�c�Ƃ��m�F�����ς̃f�[�^�ŁAtr_shisan_shisaku_kotei�Ƀf�[�^���Ȃ��ꍇ�A�l��o�^�B
// �c�Ɗm�F�����̃f�[�^�͑S�T���v���ɂ����č��ڌŒ�`�F�b�N���I���łȂ���΂Ȃ�Ȃ��B�i���ǃ��[�U�Ń`�F�b�N�͂������Ƃ͂ł���j
// ========================================================================================
// �c�ƃt���O=3�ȏ�A���A���ڌŒ�`�F�b�N���I�t�̏ꍇ
// ���ǃt���O��3 �ɕύX �yKPX@1502111_No7�z
//MOD start 20150722 �p�����[�^�ǉ�
//function funLoad_datahosei(){
function funLoad_datahosei(mode){
//MOD end 20150722
	var frm = document.frm00;
	var headerFrm = parent.header.document.frm00;
	var flgcheck = "";
	var recCnt = 0;
	// MOD start 20150722
	// �c�ƃt���O=3�ȏ�A���A���ڌŒ�`�F�b�N���I�t�̏ꍇ
//	if (headerFrm.hdnStatus_eigyo.value >= 3 && frm.chkKoumoku_0.value != "1") {

//20160607  KPX@1502111_No7 MOD start
	//�u�c3�v�ˁu��3�v �ɕύX
//	if (headerFrm.hdnStatus_eigyo.value >= 3 ) {
	if (headerFrm.hdnStatus_seikan.value >= 3 ) {
//20160607  KPX@1502111 MOD end
		if (mode == 0) {
			// ���ڌŒ�t���O��ON�ŌŒ�t�@�C���������Ƃ�
			if (funKoteiChk(1) == 1) {
				// ���ڌŒ�t���O�̃`�F�b�N���O�����������s�������A�ĕ\��

			// �y�����\���s��Ή��z20211111 BRC.yanagita ADD start
			// ��ʏ����\���t���O���u�P�v�ɍX�V
			
				shokiHyoujiFlg = "1"
				funLoad_dtl();
			// �y�����\���s��Ή��z20211111 BRC.yanagita ADD end
			};
		} else {
			// �Œ�t�@�C�����Ȃ����A�f�[�^���쐬����
			flgcheck = "check";
			// �y�����\���s��Ή��z20211111 BRC.yanagita ADD start
			// ��ʏ����\���t���O���u�Q�v�ɍX�V
			
			shokiHyoujiFlg = "2"
			// �y�����\���s��Ή��z20211111 BRC.yanagita ADD end
		
			funTorokuKoumokuKotei(1);
			funLoad_dtl();
			// �Čv�Z�t���O��off�ɂ���i���ڌŒ�`�F�b�N�t���O��DB�ɓo�^���Ă��邾���Ȃ̂ŁA�Čv�Z�͕s�v�B�j
			headerFrm.FgSaikeisan.value = "false";
		}
		// MOD end 20150722
	}
}
// ADD end 20140919

//========================================================================================
// ���׃t���[���A��{���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// �T�v  �F��ʂ̊�{���\���������s��
//========================================================================================
function funKihonHyoji() {

    //��{���擾
    funGetKihonInfo(1);

    return true;

}

//========================================================================================
// ���׃t���[���A�������\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// �T�v  �F��ʂ̌������\���������s��
//========================================================================================
function funGenryoHyoji() {

    //�������擾
    funGetGenryoInfo(1);

    return true;

}

//========================================================================================
// ���׃t���[���A���ޏ��\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// �T�v  �F��ʂ̎��ޏ��\���������s��
//========================================================================================
function funShizaiHyoji() {

    //���ޏ��擾
    funGetShizaiInfo(1);

    return true;

}

//========================================================================================
// �H��ꗗ�擾
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// �T�v  �F��ʂ̍H��ꗗ�擾�������s��
//========================================================================================
function funKojoChange() {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1020O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  �H����擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1020, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  �H����\��
    //------------------------------------------------------------------------------------
    //�H��R���{�{�b�N�X����
    funCreateComboBox(frm.ddlSeizoKojo , xmlResAry[2] , 5, 1);


    //�����ݒ�
    frm.hdnCdketasu.value = funXmlRead(xmlResAry[2], "cd_keta", 0);

    return true;
}


//========================================================================================
// �����􂢑ւ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/02
// �T�v  �F��ʂ̌����􂢑ւ��������s��
//========================================================================================
function funAraigae(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0060");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0060I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0060O );
    var mode = 1;

    //�󔒂��I�����ꂽ�ꍇ�͏������s��Ȃ�
    if(frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value == ""){
        return false;
    }

    //MOD 2015/03/30 TT.Kitazawa�yQP@40812�z�ǉ�-No.2 start
    // ������Ђ̕ύX�L���Ń��b�Z�[�W��ύX
    var msg = "";
    var flgKaisha = 0;
    if (frm.hdnKaisha.value == frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value) {
    //�m�F���b�Z�[�W��\��
// 2010.11.05 Mod Arai Start ���b�Z�[�W�ύX --------------------------------------------------------
    //var msg = "�����H���" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "�֕ύX���܂��B��낵���ł����H";
//    var msg = "�����H���" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "�֕ύX���A�f�[�^�ۑ��܂ōs���܂��B��낵���ł����H";
// 2010.11.05 Mod Arai End -------------------------------------------------------------------------
        msg = "�����H���" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "�֕ύX���A�f�[�^�ۑ��܂ōs���܂��B��낵���ł����H";
    } else {
        // ������Ђ�ύX
    	flgKaisha = 1;
        msg = "������Ђ�" + frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].innerText + "�֕ύX���A�f�[�^�ۑ��܂ōs���܂��B\\n";
        msg += "�ύX��A��ʂ���܂��B��낵���ł����H";

    }
    //MOD 2015/03/30 TT.Kitazawa�yQP@40812�z�ǉ�-No.2 end

    if(funConfMsgBox(msg) == ConBtnYes){
        // 2010.10.21 Del Arai �y�o�O�Ή� ���b�Z�[�W��\���z Start
        //�Čv�Z�t���O���I��
        //setFg_saikeisan();
        //�Čv�Z�t���O��off�ɂ���
        var headerFrm = parent.header.document.frm00;
        headerFrm.FgSaikeisan.value = "false";
        // 2010.10.21 Del Arai �y�o�O�Ή� ���b�Z�[�W��\���z End

    }else{

        return false;

    }

    //------------------------------------------------------------------------------------
    //                               �����􂢑ւ����擾
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0060I.src = '../../model/FGEN0060I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0020, xmlReqAry, xmlResAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ADD 2015/03/30 TT.Kitazawa�yQP@40812�z�ǉ�-No.2 start
    // ������ЕύX���A��ʂ����
    if (flgKaisha) {
        //�r������
        funcHaitaKaijo(mode);
        //�I�����b�Z�[�W��\��
        funInfoMsgBox(S000001);

        //�w�b�_�[�t���[���̏I���{�^�������t���O
        parent.header.window.end_click = true;
        //��ʂ����
        parent.close();
        return true;
    }
    //ADD 2015/03/30 TT.Kitazawa�yQP@40812�z�ǉ�-No.2 end

    //------------------------------------------------------------------------------------
    //                                �����􂢑ւ����\��
    //------------------------------------------------------------------------------------
    //��{���\��
    funGetKihonInfo(mode);

    //�������\��
    //funGetGenryoInfo(mode);

    //Hidden���ڂɉ�Аݒ�
    frm.hdnKaisha.value = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;

    //Hidden���ڂɍH��ݒ�
    frm.hdnKojo.value = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //�I�����b�Z�[�W��\��
    funInfoMsgBox(S000001);

    return true;
}

//========================================================================================
// �yQP@00342�z�ð��������ʂ��N��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�Ȃ�
//========================================================================================
function funStatusRireki_btn() {

	//̫�тւ̎Q��
	var headerFrm = parent.header.document.frm00;

	//�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

   	//�X�e�[�^�X�����֑J��
    window.open("../SQ140StatusRireki/SQ140StatusRireki.jsp","_blank","menubar=no,resizable=yes");

    return true;

}


//========================================================================================
// �������Z�A���ʏ��擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A���ʏ��擾
//========================================================================================
function funGetKyotuInfo(mode) {


    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010", "FGEN2020", "FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I, xmlFGEN2020I, xmlFGEN2130I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O, xmlFGEN2020O, xmlFGEN2130O );


    //------------------------------------------------------------------------------------
    //                                  ���ʏ��擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                    �����ݒ�
    //------------------------------------------------------------------------------------
    funSaveKengenInfo();


    //------------------------------------------------------------------------------------
    //                                  ���ʏ��\��
    //------------------------------------------------------------------------------------
    //հ�ޏ��\��
    funInformationDisplay(xmlResAry, 110, "divUserInfo");

    //�yQP@00342�z���ݽð���̐ݒ�
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);

    //�yQP@00342�z���������t���O�ݒ�------------------------------------------------------------------------
    frm.hdnBusho_kenkyu.value = funXmlRead_3(xmlResAry[4], "table", "flg_kenkyu", 0, 0);
	frm.hdnBusho_seikan.value = funXmlRead_3(xmlResAry[4], "table", "flg_seikan", 0, 0);
	frm.hdnBusho_gentyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_gentyo", 0, 0);
	frm.hdnBusho_kojo.value = funXmlRead_3(xmlResAry[4], "table", "flg_kojo", 0, 0);
	frm.hdnBusho_eigyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_eigyo", 0, 0);

    //�yQP@00342�z����R�[�h�\��
    frm.txtShainCd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
    frm.txtNen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
    frm.txtOiNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
    frm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //�i���\��
    frm.txtHinNm.value = funXmlRead_3(xmlResAry[2], "kihon", "hin", 0, 0);

    //�yQP@00342�z�̗p�T���v��No�ݒ�
    frm.txtSaiyou.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_nm", 0, 0);

    //�yQP@00342�z���Z�����ݒ�
    frm.txtKizitu.value = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);

    //�yQP@00342�z�}�Ԏ�ސݒ�
    frm.txtShuruiEda.value = funXmlRead_3(xmlResAry[2], "kihon", "shurui_eda", 0, 0);

    // 2010.10.01 Add Arai �yQP@00412_�V�T�N�C�b�N���ǁ@�Č���27�z START
    //�˗��ԍ��ݒ�
    divIraiNo.innerText = "IR@" + funXmlRead_3(xmlResAry[2], "kihon", "no_irai", 0, 0);
	// 2010.10.01 Add Arai �yQP@00412_�V�T�N�C�b�N���ǁ@�Č���27�z END

// ADD start �yH24�N�x�Ή��z 2012/4/18 hisahori
	frm.hdnNmMotHin.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_motHin", 0, 0);
// ADD end �yH24�N�x�Ή��z 2012/4/18 hisahori

    //�Čv�Z�{�^���̌����ҏW
    //kengenBottunSetting(frm.btnSaikeisan);

    //�o�^�{�^���̌����ҏW
    //kengenBottunSetting(frm.btnToroku);


    //�yQP@00342�z�e���ڂ̐ݒ�i�w�b�_������Hidden���ځj
    frm.strHaitaCdShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shisaku_haita", 0, 0);
    frm.strHaitaNmShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_shisaku_haita", 0, 0);
    frm.strHaitaKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisha_haita", 0, 0);
    frm.strHaitaBusho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_busho_haita", 0, 0);
    frm.strHaitaUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user_haita", 0, 0);
    frm.strKengenMoto.value = funXmlRead_3(xmlResAry[2], "kihon", "kengen_moto", 0, 0);

    //�yQP@00342�z���ݽð���̐ݒ�
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);

    //���b�Z�[�W�{�b�N�X�\��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;
    //�r���̏ꍇ
    if(frm.strKengenMoto.value == "999"){
        //�\��
        funHaitaDisp();

        headerFrm.btnTyusi.disabled = true;
        headerFrm.btnEdaban.disabled = true;
        headerFrm.btnSaikeisan.disabled = true;
        headerFrm.btnToroku.disabled = true;
    }

    // 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� START==============
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "kihon", "help_file", 0, 0);
    // 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� END  ==============


    //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Add Start
    frm.hdnSaiyou_column.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_no", 0, 0);
    //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Add End


    //------------------------------------------------------------------------------------
    //                                 �����ڰѕ`��
    //------------------------------------------------------------------------------------
    //�����ڰт̕`��
    parent.detail.location.href="GenkaShisan_dtl.jsp";

    //�����I��
    return true;
}
function funHaitaDisp() {
    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    //�yQP@00342�z�e���ڂ̎擾
    var cd_shisaku = headerFrm.strHaitaCdShisaku.value;
    var nm_shisaku = headerFrm.strHaitaNmShisaku.value;
    var kaisha = headerFrm.strHaitaKaisha.value;
    var busho = headerFrm.strHaitaBusho.value;
    var user = headerFrm.strHaitaUser.value;
    //�r�����\��
    alert("���L���[�U���g�p���ł��B\n" + "����CD�F" + cd_shisaku + "\n���얼�F" + nm_shisaku + "\n��� �F " + kaisha + "\n���� �F " + busho + "\n���� �F " + user);
    return true;
}
function funHaitaDisp_btn() {
    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;

    //�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //�yQP@00342�z�e���ڂ̎擾
    var cd_shisaku = headerFrm.strHaitaCdShisaku.value;
    var nm_shisaku = headerFrm.strHaitaNmShisaku.value;
    var kaisha = headerFrm.strHaitaKaisha.value;
    var busho = headerFrm.strHaitaBusho.value;
    var user = headerFrm.strHaitaUser.value;
    var Kengen = headerFrm.strKengenMoto.value;
    //�r���̏ꍇ
    if(Kengen.toString() == "999"){
        //�r�����\��
        alert("���L���[�U���g�p���ł��B\n" + "����CD�F" + cd_shisaku + "\n���얼�F" + nm_shisaku + "\n��� �F " + kaisha + "\n���� �F " + busho + "\n���� �F " + user);
    }else{
        alert("���ɎQ�Ƃ���Ă�����͂��܂���B");
    }
    return true;
}


//========================================================================================
// �������Z�A��{���擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A��{���擾
//========================================================================================
function funGetKihonInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0011";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0011");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0011I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0011O );
    var Kengen;


    //------------------------------------------------------------------------------------
    //                                  ��{���擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0011, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  ��{���\��
    //------------------------------------------------------------------------------------

    //------------------------------------ �����O���[�v ----------------------------------
    frm.txtGroup.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_gurupu", 0, 0);


    //------------------------------------- �����`�[�� -----------------------------------
    frm.txtTeam.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_chi-mu", 0, 0);


    //------------------------------------- �ꊇ�\�� -------------------------------------
    frm.txtIkkatu.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ikatu", 0, 0);


    //-------------------------------------- ���[�U --------------------------------------
    frm.txtUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user", 0, 0);


    //--------------------------------------- �p�r ---------------------------------------
    frm.txtYouto.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_yoto", 0, 0);

    // ADD 2013/10/22 QP@30154 okano start
    //------------------------------------- �̐Ӊ�� -------------------------------------
    frm.txtHanseki.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_hanseki", 0, 0);
    // ADD 2013/10/22 QP@30154 okano end
    //------------------------------------- ������� -------------------------------------
    //������ЃR���{�{�b�N�X����
    funCreateComboBox(frm.ddlSeizoKaisya , xmlResAry[2] , 2, 0);
    //������ЃR���{�{�b�N�X�I��
    funDefaultIndex(frm.ddlSeizoKaisya, 2);
    //�ҏW����
    kengenComboSetting(frm.ddlSeizoKaisya);
    //Hidden���ڂɉ�Аݒ�
    frm.hdnKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_kaisya", 0, 0);
    //Hidden���ڂɍH��ݒ�
    frm.hdnKojo.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_kojyo", 0, 0);

    //------------------------------------- �����H�� -------------------------------------
    //�����H��R���{�{�b�N�X����
    funCreateComboBox(frm.ddlSeizoKojo , xmlResAry[2] , 3, 1);
    //�����H��R���{�{�b�N�X�I��
    funDefaultIndex(frm.ddlSeizoKojo, 3);
    //�ҏW����
    kengenComboSetting(frm.ddlSeizoKojo);
    //�H��ύX�{�^�������ҏW
    kengenBottunSetting(frm.btnArigae);

    //------------------------------------- �S���c�� -------------------------------------
    frm.txtTantoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_tantoegyo", 0, 0);


    //------------------------------------- �������@ -------------------------------------
    frm.txtSeizohoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_seizohoho", 0, 0);


    //------------------------------------- �[�U���@ -------------------------------------
    frm.txtJutenhoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_jyutenhoho", 0, 0);


    //------------------------------------- �E�ە��@ -------------------------------------
    frm.txtSakinhoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_sakinhoho", 0, 0);


    //------------------------------------ �e��E��� ------------------------------------
    frm.txtYouki.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_yoki", 0, 0);


    //---------------------------------- �yQP@00342�z�e�ʁi���l���́j---------------------------------
    frm.txtYouryo.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    setKanma(frm.txtYouryo);

    //---------------------------------- �yQP@00342�z�e�ʁi�P�ʁj---------------------------------
    frm.txtYouryo_tani.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);


    //-------------------------------------- �yQP@00342�z���萔 --------------------------------------
    frm.txtIrisu.value = funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    setKanma(frm.txtIrisu);


    //--------------------------------------- �yQP@00342�z�׎p ---------------------------------------
    frm.txtNisugata.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0);


    //------------------------------------- �戵���x -------------------------------------
    frm.txtOndo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ondo", 0, 0);


    //------------------------------------- �ܖ����� -------------------------------------
    frm.txtShomiKikan.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kikan", 0, 0);

// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//    //------------------------------------- �yQP@00342�z������] -------------------------------------
//    frm.txtGenkaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka", 0, 0);
//    setKanma(frm.txtGenkaKibo);
//
//
//    //----------------------------------- �yQP@00342�z������]�P�� -----------------------------------
//    frm.txtGenkaTani.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_nm_tani", 0, 0);
//    frm.hdnGenkaTaniCd.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_cd_tani", 0, 0);
//
//
//    //-------------------------------------�yQP@00342�z ������] -------------------------------------
//    frm.txtBaikaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_baika", 0, 0);
//    setKanma(frm.txtBaikaKibo);
//
//
//    //----------------------------------- ������]�P�� -----------------------------------
//    frm.txtBaikaTani.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_nm_tani", 0, 0);
//
//
//    //------------------------------------- �z�蕨�� -------------------------------------
//    frm.txtSoteiButuryo.value = funXmlRead_3(xmlResAry[2], "kihon", "soote_buturyo", 0, 0);
//
//
//	//------------------------------------- �yQP@00342�z�������� -------------------------------------
//    frm.txtHatubaiJiki.value = funXmlRead_3(xmlResAry[2], "kihon", "ziki_hatubai", 0, 0);
//
//
//    //------------------------------------- �yQP@00342�z�̔����� -------------------------------------
//    //�̔����ԁi�ʔNor�X�|�b�g�j
//    frm.txtHanbaiKikan_t.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_t", 0, 0);
//
//    //�̔����ԁi���l�j
//    frm.txtHanbaiKikan_s.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_suti", 0, 0);
//
//    //�̔����ԁi�����j
//    frm.txtHanbaiKikan_k.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_k", 0, 0);
//
//
//    //------------------------------------- �yQP@00342�z�v�攄�� -------------------------------------
//    frm.txtKeikakuUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_uriage", 0, 0);
//
//
//    //------------------------------------- �yQP@00342�z�v�旘�v -------------------------------------
//    frm.txtKeikakuRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_rieki", 0, 0);
//
//
//    //------------------------------------ �yQP@00342�z�̔��㔄�� ------------------------------------
//    frm.txtHanbaigoUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_uriage", 0, 0);
//
//
//    //------------------------------------ �yQP@00342�z�̔��㗘�v ------------------------------------
//    frm.txtHanbaigoRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_rieki", 0, 0);
//
//
//    //------------------------------------ �yQP@00342�z�������b�g ------------------------------------
//    frm.txtSeizoRot.value = funXmlRead_3(xmlResAry[2], "kihon", "seizo_roto", 0, 0);
// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
    //�T���v�����̊�{���\��
    funKihonSubDisplay(xmlResAry[2],"divKihonSub");

    var recCnt;
    var i;
    //�񐔎擾
    recCnt = frm.cnt_sample.value;
    //
    for(i = 0; i < recCnt;i++){
    	// MOD 2013/12/4 okano�yQP@30154�zstart
//	    	setKanma(document.getElementById("txtGenkaKibo" + i).value);
//	    	setKanma(document.getElementById("txtBaikaKibo" + i).value);
    	setKanma(document.getElementById("txtGenkaKibo" + i));
    	setKanma(document.getElementById("txtBaikaKibo" + i));
    	// MOD 2013/12/4 okano�yQP@30154�zend
    	// ADD 2013/12/4 okano�yQP@30154�zstart
    	setKanma(document.getElementById("txtSoteiButuryo_s" + i));
    	// ADD 2013/12/4 okano�yQP@30154�zend
    }
// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    //----------------------------------- �yQP@00342�z�������Z����-------------------------------
    frm.txtGenkaMemo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan", 0, 0);


    //----------------------------- �yQP@00342�z�������Z�����i�c�ƘA���p�j --------------------------
    frm.txtGenkaMemoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan_eigyo", 0, 0);


    //------------------------------------ �R�[�h���� -------------------------------------
    frm.hdnCdketasu.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_ketasu", 0, 0);

    //�yQP@00342�z��ʐ���
    kengenSetting_kihon();

//20160617  KPX@1502111_No.5 ADD start
    //�������\���O�A���ƌ����`�F�b�N�����s
    // �Čv�Z�{�^���F�����\�i�H��F���m�F�����A���ǁF���m�F�����j
    // �A�g��̎���X�e�[�^�X���c�ƂR�̎��A���ƌ����P���i�����v(�~)/Kg�j��ݒ肷��
    // ���ƌ��������F�X�e�[�^�X�Ɋ֌W�Ȃ��l���ݒ肳��Ă��Ȃ����Ains��100������
    var headerFrm = parent.header.document.frm00;
    if(headerFrm.btnSaikeisan.disabled == false) {
    	//���ƌ����̒P�������X�V
    	funRenkeiGenryo();
    }
//20160617  KPX@1502111_No.5 ADD end


    //------------------------------------------------------------------------------------
    //                            �������\���C�x���g����
    //------------------------------------------------------------------------------------
    //�������\���C�x���g�𔭐�������
    frm.BtnEveGenryo.fireEvent('onclick');


    //�����I��
    return true;

}


//========================================================================================
// �yQP@00342�z��ʐ���i���ʁ���{��񕔁j
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
//========================================================================================
function kengenSetting_kihon(){

    var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	var i;
	var recCnt;
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    //�����擾
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	//�񐔎擾
    recCnt = detailFrm.cnt_sample.value;
    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    //�r���̏ꍇ
    if(headerFrm.strKengenMoto.value == "999"){

    	//�I���A�X�e�[�^�X�����A�g�p���\���A�w���v�\���@�ȊO�͎g�p�s��
    	headerFrm.btnSaikeisan.disabled = true;
    	headerFrm.btnToroku.disabled = true;
    	//headerFrm.btnInsatu.disabled = true;

    	detailFrm.ddlSeizoKaisya.disabled=true;
    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

    	detailFrm.ddlSeizoKojo.disabled=true;
    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

    	detailFrm.btnArigae.disabled = true;

		// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
    	for(i = 0;i < recCnt;i++){
//    		detailFrm.txtSeizoRot.disabled=true;
//    		detailFrm.txtSeizoRot.style.backgroundColor=color_read;
			// MOD 2014/8/7 shima�yQP@30154�zNo.80 start
//    		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
    		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
    		// MOD 2014/8/7 shima�yQP@30154�zNo.80 end
    	}
    	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

    	detailFrm.txtGenkaMemo.disabled=true;
    	detailFrm.txtGenkaMemo.style.backgroundColor=color_read;

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;

    }
    //�r���łȂ��ꍇ
    else{
    	//�ҏW����
	    if(headerFrm.hdnKengen.value == ConFuncIdEdit.toString()){

	        //���Y�Ǘ���
	        if(seikan == "1"){
	        	headerDoc.getElementById("btnTyusi").style.visibility = "visible";
		        headerDoc.getElementById("btnEdaban").style.visibility = "visible";

		        headerFrm.btnTyusi.disabled=false;
	        	headerFrm.btnEdaban.disabled=false;

	        }

	        //�����ޒ��B��
	        else if(gentyo == "1"){
	        	detailFrm.btnArigae.disabled=true;

		        detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

				// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
		    	for(i = 0;i < recCnt;i++){
//		    		detailFrm.txtSeizoRot.disabled=true;
//		    		detailFrm.txtSeizoRot.style.backgroundColor=color_read;
					// MOD 2014/8/7 shima�yQP@30154�zNo.80 start
//		    		detailDoc.getElementById("txtSeizoRot"+i).disabled=true;
		    		detailDoc.getElementById("txtSeizoRot"+i).style.backgroundColor=color_read;
	        		detailDoc.getElementById("txtSeizoRot"+i).readOnly=true;
	        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 end
		    	}
		    	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

	        }

	        //�H��
	        else if(kojo == "1"){
	        	detailFrm.btnArigae.disabled=true;

	        	detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

		    	//�H��̊m�F�����̏ꍇ
		    	/*
				 * if(headerFrm.hdnStatus_kojo.value == "2"){
				 * detailFrm.txtSeizoRot.disabled=true;
				 * detailFrm.txtSeizoRot.style.backgroundColor=color_read; }
				 */
	        }

	        //�X�e�[�^�X����
	        var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
		    var st_seikan = headerFrm.hdnStatus_seikan.value;
		    var st_gentyo = headerFrm.hdnStatus_gentyo.value;
		    var st_kojo    = headerFrm.hdnStatus_kojo.value;
		    var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

		    headerFrm.btnSaikeisan.disabled = false;

		    //���Y�Ǘ����X�e�[�^�X >= 2�@�̏ꍇ
	        if( st_seikan >= 2 ){
				detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

		    	detailFrm.btnArigae.disabled = true;
	        }

	        //20160607  �yKPX@1502111_No8�z ADD start
	        //�H��X�e�[�^�X �� 2 �@�̏ꍇ�i�����F�H��j
	        //�@�����F���ǂ̏ꍇ�́A���Y�Ǘ����X�e�[�^�X �� 3
	        if( kojo == "1" && st_kojo >= 2 ){
	        	for(i = 0;i < recCnt; i++){
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        	}
		    	headerFrm.btnSaikeisan.disabled = true;
	        }
	        //20160607  �yKPX@1502111_No8�z ADD end

	        //���Y�Ǘ����X�e�[�^�X >= 3�@�̏ꍇ
	        if( st_seikan >= 3 ){
	        	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
	        	for(i = 0;i < recCnt; i++){
	        		//detailFrm.txtSeizoRot.disabled=true;
	        		//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 start
//	        		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 end
	        	}
	        	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
		    	headerFrm.btnSaikeisan.disabled = true;
	        }

	        //�c�ƃX�e�[�^�X >= 4�@�̏ꍇ
	        if( st_eigyo >= 4 ){
	        	headerFrm.btnTyusi.disabled=true;

	        	headerFrm.btnEdaban.disabled=true;

	        	detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

	        	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
	        	for(i = 0;i < recCnt; i++){
	        		//detailFrm.txtSeizoRot.disabled=true;
	        		//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 start
//	        		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 end
	        	}
	        	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

		    	headerFrm.btnSaikeisan.disabled = true;
	        }
	    }
	    //�{��+Excel����
	    else if(headerFrm.hdnKengen.value == ConFuncIdReadExcel.toString()){

	    	headerFrm.btnSaikeisan.disabled = true;

	    	detailFrm.ddlSeizoKaisya.disabled=true;
	    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

	    	detailFrm.ddlSeizoKojo.disabled=true;
	    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

	    	detailFrm.btnArigae.disabled = true;

			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
	        for(i = 0;i < recCnt; i++){
	        	//detailFrm.txtSeizoRot.disabled=true;
	        	//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        	// MOD 2014/8/7 shima�yQP@30154�zNo.80 start
//	        	detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        	detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
        		// MOD 2014/8/7 shima�yQP@30154�zNo.80 end
	        }
	        // MOD 2013/7/2 shima�yQP@30151�zNo.37 end

	    }
    }


 // 20160513  KPX@1600766 ADD start
    if (kenkyu == "1") {
    	// �������F����̐�����Ђɑ΂���P���J���t���O���擾
    	tankaHyoujiFlg = funGetTankaFlg();

    	if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
    		// �Čv�Z�{�^���񊈐�
    		headerFrm.btnSaikeisan.disabled = true;
    	}
    } else {
    	// �������ȊO�͑S�\���i��ʕ\����������Ă���ׁj
    	tankaHyoujiFlg = "9";
    }
// 20160513  KPX@1600766 ADD end
    return true;
}


//========================================================================================
// �������Z�A�������擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A�������擾
//========================================================================================
function funGetGenryoInfo(mode) {

    var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0012";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0012");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0012I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0012O );
    var keisanKomoku;


    //------------------------------------------------------------------------------------
    //                                  ��{���擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0011, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                  �������\��
    //------------------------------------------------------------------------------------
    //�����e�[�u������
    funGenryo_LeftDisplay(xmlResAry[2], "divGenryo_Left");

    //�����e�[�u���E��
    funGenryo_RightDisplay(xmlResAry[2], "divGenryo_Right");

    //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD Start
    //�����e�[�u�����ڔ�\��
    funGenryo_DispDecision(xmlResAry[2]);
    //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD End

    //�v�Z���ڕ\��
    keisanKomoku = funXmlRead_3(xmlResAry[2], "kihon", "ragio_kesu_kg", 0, 0);

    //1:�Œ��/���
    frm.radioKoteihi[0].checked = "true";
    if(keisanKomoku.toString() == "1"){
        frm.radioKoteihi[0].checked = "true";
    }
    //2:�Œ��/kg
    else if(keisanKomoku.toString() == "2"){
        frm.radioKoteihi[1].checked = "true";
        //�yQP@10713�z 11/10/31 TT H.Shima
        radio_state = false;
    }

    //�����ҏW
    kengenRadioSetting(frm.radioKoteihi[0]);
    kengenRadioSetting(frm.radioKoteihi[1]);

    //�yQP@00342�z�����ޒ��B���̏ꍇ�i�Œ��׼޵���݂͑I��s�j
    if(headerFrm.hdnBusho_gentyo.value == "1"){
    	frm.radioKoteihi[0].disabled = true;
    	frm.radioKoteihi[1].disabled = true;
    }

    //�����H���\��
    //frm.txtSeizoKotei.value = funXmlRead_3(xmlResAry[2], "kihon", "koute", 0, 0);
//20160622  KPX@1502111_No.10 ADD start
    //�T���v���R�s�[�{�^���̌����ҏW�i������funGenryoShisanDisplay�Ŏg�p�s�ɕύX�j
    kengenBottunSetting(frm.btnSampleCopy);
//20160622  KPX@1502111_No.10 ADD end

    //���Z���\��
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");

    //�}�X�^�P���E�����{�^���̌����ҏW
    kengenBottunSetting(frm.btnBckMst);

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
    //�T���v��No�R���{�{�b�N�X����
    funCreateComboBox(frm.ddlSeizoNo , xmlResAry[2] , 6, 0);
    //�����H�����ׂ̏����\��
    seizo_output();
//mod end   -------------------------------------------------------------------------------

    // 20170515 KPX@1700856 ADD Start
    funGetTankaZeroGenryo();
    // 20170515 KPX@1700856 ADD End
    
    //------------------------------------------------------------------------------------
    //                            ���ޏ��\���C�x���g����
    //------------------------------------------------------------------------------------
    //���ޏ��\���C�x���g�𔭐�������
    frm.BtnEveShizai.fireEvent('onclick');

    //�����I��
    return true;

}


//========================================================================================
// �������Z�A���ޏ��擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A���ޏ��擾
//========================================================================================
function funGetShizaiInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0013";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0013");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0013I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0013O );

    //------------------------------------------------------------------------------------
    //                                  ��{���擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0011, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  �������\��
    //------------------------------------------------------------------------------------
    //���ރe�[�u���\��
    funShizaiDisplay(xmlResAry[2],"divGenryoShizai");
    funSetDtShisan();

    //�ꊇ�I�� �����ҏW
    kengenCheckboxSetting(frm.chkIkkatuShizai);

    //�ގ��i���� �����ҏW
    kengenBottunSetting(frm.btnRuiziSearch);

    //���ލ폜 �����ҏW
    kengenBottunSetting(frm.btnShizaiDelete);


	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD start

    // ��ʏ����\��������(��ʏ����\���t���O���u�O�v)�̂Ƃ��ȉ����������s
	if (shokiHyoujiFlg == "0"){
	
	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD end
	
// MOD start 20150722
// ADD start 20140919
//    funLoad_datahosei();
    // �c�ƃt���O��3
    // �Œ�f�[�^���Ȃ��f�[�^���������ꍇ�A���ڌŒ�t���O�̃`�F�b�N���O��
    funLoad_datahosei(0);


// ADD end 20140919
// MOD end 20150722

	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD start
	}
	else if (shokiHyoujiFlg == "1"){
	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD end
// MOD start 20150722
// ADD start 20140919	
	    // �Œ�f�[�^���Ȃ��f�[�^���������ꍇ�A���R�[�h��ǉ�����
		 funLoad_datahosei(1); 
// ADD end 20140919
// MOD end 20150722
	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD start
	}
	else {
		shokiHyoujiFlg = "0"; //��ʏ����\���t���O�������l(��ʏ����\���t���O���u�O�v)�ɖ߂�
	}
	// �y�����\���s��Ή��z20211111 BRC.yanagita ADD end

    //�����I��
    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    //ͯ���ڰт�̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    // ADD 2013/11/5 QP@30154 okano start
    var DataId;
    // ADD 2013/11/5 QP@30154 okano end
    var reccnt;
    var i;

    //�������[�v
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        // ADD 2013/11/5 QP@30154 okano start
        DataId = funXmlRead(obj, "id_data", i);
        // ADD 2013/11/5 QP@30154 okano end

        //�������Z���
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {

            //�����ݒ�
            headerFrm.hdnKengen.value = KinoId.toString();
            // ADD 2013/11/5 QP@30154 okano start
            headerFrm.hdnDataId.value = DataId.toString();
            // ADD 2013/11/5 QP@30154 okano end

        }
    }

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��
    var i;

    for (i = 0; i < reqAry.length; i++) {

        //��ʏ����\���i���ʏ��j
        if (XmlId.toString() == "RGEN0010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0010
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);

                    //�yQP@00342�z
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
                case 2:    //FGEN2020
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

     // 20160513  KPX@1600766 ADD start
        // �O���[�v��Ђ̒P���J���t���O�擾
        else if (XmlId.toString() == "JSP0630") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA100
                    funXmlWrite(reqAry[i], "cd_category", "K_tanka_hyoujigaisha", 0);
                    funXmlWrite(reqAry[i], "cd_literal", detailFrm.hdnKaisha.value, 0);
                    // �f�[�^�Ȃ��ł��G���[�Ƃ��Ȃ�
                    funXmlWrite(reqAry[i], "req_flg", "1", 0);
                    break;
            }
        }
     // 20160513  KPX@1600766 ADD end

        // 20170515 KPX@1700856 ADD Start
        // �P��0�~�������R�[�h�擾
        else if (XmlId.toString() == "RGEN2290") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2290
                    funXmlWrite(reqAry[i], "cd_category", "K_tanka_zero_genryo", 0);
                    // �f�[�^�Ȃ��ł��G���[�Ƃ��Ȃ�
                    funXmlWrite(reqAry[i], "req_flg", "1", 0);
                    break;
            }
        }
        // 20170515 KPX@1700856 ADD end
        
        //ADD 2015/07/31 TT.Kitazawa�yQP@40812�zNo.6 end
        //���[�����M���āA�擾�i�X�[�^�X���j
        else if (XmlId.toString() == "RGEN2020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2020
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
      //ADD 2015/07/31 TT.Kitazawa�yQP@40812�zNo.6 end

        //��ʏ����\���i��{���j
        else if (XmlId.toString() == "RGEN0011"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0011
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);

                    //�yQP@00342�z
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

        //��ʏ����\���i�������j
        else if (XmlId.toString() == "RGEN0012"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0012
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    //�yQP@00342�z
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

        //��ʏ����\���i�������j
        else if (XmlId.toString() == "RGEN0013"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0013
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    //�yQP@00342�z
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //�yQP@00342�z�������Z��ʋN�����ʒm
        else if (XmlId.toString() == "RGEN2160"){
            // XML��莎��R�[�h�擾
            var no_shisaku = headerFrm.txtShainCd.value;
            var nen = headerFrm.txtNen.value;
            var oi = headerFrm.txtOiNo.value;
            var no_eda = headerFrm.txtEdaNo.value;

            var put_shisaku = no_shisaku + "-" + nen + "-" + oi + "-" + no_eda;

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

        //�o�^
        else if (XmlId.toString() == "RGEN0041"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0040

                    //���[�v�J�E���g
                    var recCnt;
                    var j;

                    //-------------------------- [kihon]�e�[�u���i�[ --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // �yQP@00342�z�}��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // �̗p�T���v���m�n
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);

                    // �H��@�S�����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // �H��@�S���H��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);

                    // ���萔
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // �׎p
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // ������]�P��CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // �z�蕨��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // �̔�����
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // �v�攄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // �v�旘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // �̔��㔄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // �̔��㗘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);

                    // �������b�g
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // �������Z����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);

                    //�yQP@00342�z�������Z�����i�c�ƘA���p�j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);

                    //�yQP@00342�z�I���X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", headerFrm.hdnInsStatus.value, 0);

                    //�yQP@00342�z�������X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", headerFrm.hdnStatus_kenkyu.value, 0);

                    //�yQP@00342�z���Y�Ǘ����X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", headerFrm.hdnStatus_seikan.value, 0);

                    //�yQP@00342�z�����ޒ��B���X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", headerFrm.hdnStatus_gentyo.value, 0);

                    //�yQP@00342�z�H��X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", headerFrm.hdnStatus_kojo.value, 0);

                    //�yQP@00342�z�c�ƃX�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", headerFrm.hdnStatus_eigyo.value, 0);

                    //�yQP@00342�z�����ݒ�i�������j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kenkyu", headerFrm.hdnBusho_kenkyu.value, 0);

                    //�yQP@00342�z�����ݒ�i���Y�Ǘ����j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_seikan", headerFrm.hdnBusho_seikan.value, 0);

                    //�yQP@00342�z�����ݒ�i�����ޒ��B���j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_gentyo", headerFrm.hdnBusho_gentyo.value, 0);

                    //�yQP@00342�z�����ݒ�i�H��j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kojo", headerFrm.hdnBusho_kojo.value, 0);

                    //�yQP@00342�z�����ݒ�i�c�Ɓj
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_eigyo", headerFrm.hdnBusho_eigyo.value, 0);


                    // �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
                    var koteihiFg = "";
                    for (j = 0; j < detailFrm.radioKoteihi.length; j++){
                       if(detailFrm.radioKoteihi[j].checked == true){
                           koteihiFg = detailFrm.radioKoteihi[j].value;
                       }
                    }
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ragio_kesu_kg", koteihiFg, 0);

                    //-------------------------- [genryo]�e�[�u���i�[ -------------------------
                    //�����\�̍s���擾
                    recCnt = detailFrm.cnt_genryo.value;
                    //�����s�J�E���g
                    var gyoCnt = 1;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    for( j = 0; j < recCnt; j++ ){

                        //�����s���ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("hdnCd_kotei_"+gyoCnt)){
                            if (recInsert != 0) {
                                //���R�[�h�}��
                                funAddRecNode_Tbl(reqAry[i], "FGEN0040", "genryo");
                            }
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                            // �H��SEQ
                            funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "genryo", "tanka", detailDoc.getElementById("txtTanka_"+gyoCnt).value, recInsert);
                            // ����
                            funXmlWrite_Tbl(reqAry[i], "genryo", "budomari", detailDoc.getElementById("txtBudomari_"+gyoCnt).value, recInsert);
                            // �s�ԍ�
                            funXmlWrite_Tbl(reqAry[i], "genryo", "no_gyo", gyoCnt, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }

                        //�����s�J�E���g+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]�e�[�u���i�[ -------------------------
                    //�v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0040", "keisan");
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start
                        //���ڌŒ�`�F�b�N
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 end
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ���Z��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);
                        // �L�������i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", detailDoc.getElementById("txtYukoBudomari_"+j).value, j);
                        // ���Ϗ[�U�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", detailDoc.getElementById("txtHeikinZyu_"+j).value, j);
                        // �Œ��/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", detailDoc.getElementById("txtCaseKotei_"+j).value, j);
                        // �Œ��/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", detailDoc.getElementById("txtKgKotei_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano start
                        // ���v/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // ���v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // �����v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // �yQP@00342�z���Z���~

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa start
                        // �[�U�ʐ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // �[�U�ʖ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // ���v�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // ��d
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // ���x���ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // ��d���Z�ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // ������i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // �ޗ���i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // �����v�i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // �����v�i�~�j/��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // ������i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // �ޗ���i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // �����v�i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // ����
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // �e���i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa end
                    }


                    //-------------------------- [shizai]�e�[�u���i�[ -------------------------
                    //���ރe�[�u���I�u�W�F�N�g�擾
                    var tblShizai = detailDoc.getElementById("tblList4");
                    //���ޕ\�̍s���擾
                    recCnt = tblShizai.rows.length;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                        //���ލs�ɉ������͂���Ă��Ȃ��ꍇ
                        if( detailFrm.txtCdShizai[j].value == ""
                            && detailFrm.txtNmShizai[j].value == ""
                            && detailFrm.txtTankaShizai[j].value == ""
                            && detailFrm.txtBudomariShizai[j].value == ""
                            && detailFrm.txtSiyouShizai[j].value == ""){

                            //�������Ȃ�


                        }else{

                            //XML�֒ǉ�
                            if (recInsert != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN0040", "shizai");
                            }
                            // ����SEQ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "seq_shizai", recInsert+1, recInsert);
                            // ���CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kaisya", detailFrm.hdnKaisha_Shizai[j].value, recInsert);
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kojyo", detailFrm.hdnKojo_Shizai[j].value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_shizai", detailFrm.txtCdShizai[j].value, recInsert);
                            // ���ޖ�
                            funXmlWrite_Tbl(reqAry[i], "shizai", "nm_shizai", detailFrm.txtNmShizai[j].value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "tanka", detailFrm.txtTankaShizai[j].value, recInsert);
                            // �����i���j
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            // �g�p��/���
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            // �o�^��ID
                            funXmlWrite_Tbl(reqAry[i], "shizai", "id_toroku", detailFrm.hdnId_toroku[j].value, recInsert);
                            // �o�^��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "dt_toroku", detailFrm.hdnDt_toroku[j].value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }
                    }

                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
                    //-------------------------- [kihonsub]�e�[�u���i�[ -------------------------
                    //�v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                    	if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0040", "kihonsub");
                        }

                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

                        // �������b�g
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);

                    }
                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

                    break;
            }
        }

        // ADD start 20140919
        // ���ڌŒ�`�F�b�N�Ώۍ��ڒl �o�^
        else if (XmlId.toString() == "RGEN2021"){
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN2021

                    // ���[�v�J�E���g
                    var recCnt;
                    var j;

                    // -------------------------- [kihon]�e�[�u���i�[
					// --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // �yQP@00342�z�}��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // -------------------------- [keisan]�e�[�u���i�[
					// -------------------------
                    // �v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;

                    // XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2021", "keisan");
                        }
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ------------------------
                        // �[�U�ʐ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // �[�U�ʖ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // ���v�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // ��d
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // ���x���ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // ��d���Z�ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // ������i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // �ޗ���i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // �����v�i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // �����v�i�~�j/��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // ������i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // �ޗ���i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // �����v�i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // ����
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // �e���i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                    }
                    break;
            }
        }
        // ADD end 20140919

        // ADD start 20150722
        // ���ڌŒ�`�F�b�NON �Œ�t�@�C���Ȃ��̎��A���ڌŒ�`�F�b�N��OFF�ɂ���
        // �Čv�Z�Œl���Z�b�g�����
        else if (XmlId.toString() == "RGEN2022"){
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN2022

                    // ���[�v�J�E���g
                    var recCnt;
                    var j;

                    // -------------------------- [kihon]�e�[�u���i�[
					// --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // �yQP@00342�z�}��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // -------------------------- [keisan]�e�[�u���i�[
					// -------------------------
                    // �v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;

                    // XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2022", "keisan");
                        }
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ���ڌŒ�`�F�b�N
                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_koumokuchk", detailDoc.getElementById("chkKoumoku_"+j).value, j);
                   }
                    break;
            }
        }
        // ADD end 20150722

        //�H�ꌟ���i��ЕύX���j
        else if (XmlId.toString() == "RGEN1020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", GenkaSisanId, 0);
                    break;
            }
        }

        //�����􂢑ւ�
        else if (XmlId.toString() == "RGEN0020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0060

                    //���[�v�J�E���g
                    var recCnt;
                    var j;

                    //-------------------------- [kihon]�e�[�u���i�[ --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);

                    // �yQP@00342�z�}��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // �̗p�T���v���m�n
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    //�V���CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "new_cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    //�V�H��CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "new_cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // �H��@�S�����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.hdnKaisha.value, 0);
                    // �H��@�S���H��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.hdnKojo.value, 0);
                    // ���萔
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // �׎p
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);

                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // ������]�P��CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);

                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // �z�蕨��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // �̔�����
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);

                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                    // �v�攄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // �v�旘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // �̔��㔄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // �̔��㗘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);
                    // �������b�g
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // �������Z����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);

                    // �yQP@00342�z�������Z�����i�c�ƘA���p�j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);

                //MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
                    //�yQP@00342�z�I���X�e�[�^�X�ݒ�i0�Œ�F���ۑ��j
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", "0", 0);
                    //�yQP@40812�z��ƃR�[�h�ݒ�i9�Œ�F���ۑ��i�H��ύX�j�j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", "9", 0);
                //MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

                    //�yQP@00342�z�������X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", headerFrm.hdnStatus_kenkyu.value, 0);

                    //�yQP@00342�z���Y�Ǘ����X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", headerFrm.hdnStatus_seikan.value, 0);

                    //�yQP@00342�z�����ޒ��B���X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", headerFrm.hdnStatus_gentyo.value, 0);

                    //�yQP@00342�z�H��X�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", headerFrm.hdnStatus_kojo.value, 0);

                    //�yQP@00342�z�c�ƃX�e�[�^�X�ݒ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", headerFrm.hdnStatus_eigyo.value, 0);

                    //�yQP@00342�z�����ݒ�i�������j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kenkyu", headerFrm.hdnBusho_kenkyu.value, 0);

                    //�yQP@00342�z�����ݒ�i���Y�Ǘ����j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_seikan", headerFrm.hdnBusho_seikan.value, 0);

                    //�yQP@00342�z�����ݒ�i�����ޒ��B���j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_gentyo", headerFrm.hdnBusho_gentyo.value, 0);

                    //�yQP@00342�z�����ݒ�i�H��j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kojo", headerFrm.hdnBusho_kojo.value, 0);

                    //�yQP@00342�z�����ݒ�i�c�Ɓj
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_eigyo", headerFrm.hdnBusho_eigyo.value, 0);

                    // �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
                    var koteihiFg = "";
                    for (j = 0; j < detailFrm.radioKoteihi.length; j++){
                       if(detailFrm.radioKoteihi[j].checked == true){
                           koteihiFg = detailFrm.radioKoteihi[j].value;
                       }
                    }
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ragio_kesu_kg", koteihiFg, 0);

                    //-------------------------- [genryo]�e�[�u���i�[ -------------------------
                    //�����\�̍s���擾
                    recCnt = detailFrm.cnt_genryo.value;
                    //�����s�J�E���g
                    var gyoCnt = 1;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    for( j = 0; j < recCnt; j++ ){

                        //�����s���ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("hdnCd_kotei_"+gyoCnt)){
                            if (recInsert != 0) {
                                //���R�[�h�}��
                                funAddRecNode_Tbl(reqAry[i], "FGEN0060", "genryo");
                            }
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                            // �H��SEQ
                            funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "genryo", "tanka", detailDoc.getElementById("txtTanka_"+gyoCnt).value, recInsert);
                            // ����
                            funXmlWrite_Tbl(reqAry[i], "genryo", "budomari", detailDoc.getElementById("txtBudomari_"+gyoCnt).value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }

                        //�����s�J�E���g+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]�e�[�u���i�[ -------------------------
                    //�v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0060", "keisan");
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start
                        // //���ڌŒ�`�F�b�N
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 end
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ���Z��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);
                        // �L�������i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", detailDoc.getElementById("txtYukoBudomari_"+j).value, j);
                        // ���Ϗ[�U�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", detailDoc.getElementById("txtHeikinZyu_"+j).value, j);
                        // �Œ��/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", detailDoc.getElementById("txtCaseKotei_"+j).value, j);
                        // �Œ��/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", detailDoc.getElementById("txtKgKotei_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano start
                        // ���v/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // ���v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // �����v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // �yQP@00342�z���Z���~

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa start
                        // �[�U�ʐ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // �[�U�ʖ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // ���v�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // ��d
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // ���x���ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // ��d���Z�ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // ������i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // �ޗ���i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // �����v�i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // �����v�i�~�j/��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // ������i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // �ޗ���i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // �����v�i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // ����
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // �e���i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa end
                    }


                    //-------------------------- [shizai]�e�[�u���i�[ -------------------------
                    //���ރe�[�u���I�u�W�F�N�g�擾
                    var tblShizai = detailDoc.getElementById("tblList4");
                    //���ޕ\�̍s���擾
                    recCnt = tblShizai.rows.length;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                        //���ލs�ɉ������͂���Ă��Ȃ��ꍇ
                        if( detailFrm.txtCdShizai[j].value == ""
                            && detailFrm.txtNmShizai[j].value == ""
                            && detailFrm.txtTankaShizai[j].value == ""
                            && detailFrm.txtBudomariShizai[j].value == ""
                            && detailFrm.txtSiyouShizai[j].value == ""){

                            //�������Ȃ�


                        }else{

                            //XML�֒ǉ�
                            if (recInsert != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN0060", "shizai");
                            }
                            // ����SEQ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "seq_shizai", recInsert+1, recInsert);
                            // ���CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kaisya", detailFrm.hdnKaisha_Shizai[j].value, recInsert);
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kojyo", detailFrm.hdnKojo_Shizai[j].value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_shizai", detailFrm.txtCdShizai[j].value, recInsert);
                            // ���ޖ�
                            funXmlWrite_Tbl(reqAry[i], "shizai", "nm_shizai", detailFrm.txtNmShizai[j].value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "tanka", detailFrm.txtTankaShizai[j].value, recInsert);
                            // �����i���j
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            // �g�p��/���
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            // �o�^��ID
                            funXmlWrite_Tbl(reqAry[i], "shizai", "id_toroku", detailFrm.hdnId_toroku[j].value, recInsert);
                            // �o�^��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "dt_toroku", detailFrm.hdnDt_toroku[j].value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }
                    }

                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
                    //-------------------------- [kihonsub]�e�[�u���i�[ -------------------------
                    //�s���擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                    	//XML�֒ǉ�
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0060", "kihonsub");
                        }
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // ������]
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka", detailDoc.getElementById("txtGenkaKibo"+j).value, j);
	                    // ������]
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
	                    // �z�蕨��
	                    // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_s", detailDoc.getElementById("txtSoteiButuryo_s"+j).value, j);
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_u", detailDoc.getElementById("txtSoteiButuryo_u"+j).value, j);
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_k", detailDoc.getElementById("txtSoteiButuryo_k"+j).value, j);
	                    // ADD 2013/9/6 okano�yQP@30151�zNo.30 end
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei", detailDoc.getElementById("txtSoteiButuryo"+j).value, j);
	                    // �v�攄��
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikaku_uriage", detailDoc.getElementById("txtKeikakuUriage"+j).value, j);
	                    // �v�旘�v
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikaku_rieki", detailDoc.getElementById("txtKeikakuRieki"+j).value, j);
	                    // �̔��㔄��
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbaigo_uriage", detailDoc.getElementById("txtHanbaigoUriage"+j).value, j);
	                    // �̔��㗘�v
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbaigo_rieki", detailDoc.getElementById("txtHanbaigoRieki"+j).value, j);
	                    // �������b�g
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);

                    }
                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

                    break;
            }
        }


        //�Čv�Z
        else if (XmlId.toString() == "RGEN0030"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0030

                    //���[�v�J�E���g
                    var recCnt;
                    var j;

                    //-------------------------- [kihon]�e�[�u���i�[ --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);

                    //�yQP@00342�z
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // ���萔
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // �������[�h
                    funXmlWrite_Tbl(reqAry[i], "kihon", "mode", "1", 0);

                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);

                    //�yQP@00342�z
                    // ������]�P��CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_tani", detailFrm.hdnGenkaTaniCd.value, 0);


                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
                    var koteihiFg = "";
                    for (j = 0; j < detailFrm.radioKoteihi.length; j++){
                       if(detailFrm.radioKoteihi[j].checked == true){
                           koteihiFg = detailFrm.radioKoteihi[j].value;
                       }
                    }
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ragio_kesu_kg", koteihiFg, 0);

                    //-------------------------- [genryo]�e�[�u���i�[ -------------------------
                    //�����\�̍s���擾
                    recCnt = detailFrm.cnt_genryo.value;
                    //�����s�J�E���g
                    var gyoCnt = 1;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    for( j = 0; j < recCnt; j++ ){

                        //�����s���ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("hdnCd_kotei_"+gyoCnt)){
                            if (recInsert != 0) {
                                //���R�[�h�}��
                                funAddRecNode_Tbl(reqAry[i], "FGEN0030", "genryo");
                            }
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                            // �H��SEQ
                            funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "genryo", "tanka", detailDoc.getElementById("txtTanka_"+gyoCnt).value, recInsert);
                            // ����
                            funXmlWrite_Tbl(reqAry[i], "genryo", "budomari", detailDoc.getElementById("txtBudomari_"+gyoCnt).value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }

                        //�����s�J�E���g+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]�e�[�u���i�[ -------------------------
                    //���Z���ږ����͗�i�m�F���b�Z�[�W�p�j
                    var msgRetu = "";
                    //�v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0030", "keisan");
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start
                        // //���ڌŒ�`�F�b�N
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 end
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // �L�������i���j
                        var yuuko_budomari = detailDoc.getElementById("txtYukoBudomari_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", yuuko_budomari, j);
                        // ���Ϗ[�U�ʁi���j
                        var heikinjyutenryo = detailDoc.getElementById("txtHeikinZyu_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", heikinjyutenryo, j);
                        // �Œ��/�P�[�X
                        var kesu_kotehi = detailDoc.getElementById("txtCaseKotei_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", kesu_kotehi, j);
                        // �Œ��/KG
                        var kg_kotehi = detailDoc.getElementById("txtKgKotei_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", kg_kotehi, j);
                        // ADD 2013/11/1 QP@30154 okano start
                        // ���v/�P�[�X
                        var kesu_rieki = detailDoc.getElementById("txtCaseRieki_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", kesu_rieki, j);
                        // ���v/KG
                        var kg_rieki = detailDoc.getElementById("txtKgRieki_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", kg_rieki, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // ���Z��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);

                        //�����͍��ڂ̃`�F�b�N�i�L�������E���Ϗ[�U�ʁE�Œ��̂����ꂩ�������͂̏ꍇ�j
                        //�yQP@00342�z
                        //�Œ��/�P�[�X�̏ꍇ�i�Œ��/�P�[�X�̓��̓`�F�b�N�j
                        if(koteihiFg == "1"){
                            // MOD 2013/11/1 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == ""){
                            // MOD 2013/11/25 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == "" || kesu_rieki == ""){
                            if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == ""){
                            // MOD 2013/11/25 QP@30154 okano end
                            // MOD 2013/11/1 QP@30154 okano end
	                        	//�yQP@00342�z���Z���~
	                        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+j).value;
	                        	if(fg_chusi == "1"){
	                        		//���Z���~�̏ꍇ�̓��b�Z�[�W�������Ȃ�
	                        	}
	                        	else{
	                                msgRetu += "�y" + (j+1) + "��ځz";
	                        	}
	                        }
                        }
                        //�Œ��/KG�̏ꍇ�i�Œ��/KG�̓��̓`�F�b�N�j
                        else if(koteihiFg == "2"){
                            // MOD 2013/11/1 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" ){
                            // MOD 2013/11/25 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" || kg_rieki == "" ){
                            if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" ){
                            // MOD 2013/11/25 QP@30154 okano end
                            // MOD 2013/11/1 QP@30154 okano end
	                        	//�yQP@00342�z���Z���~
	                        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+j).value;
	                        	if(fg_chusi == "1"){
	                        		//���Z���~�̏ꍇ�̓��b�Z�[�W�������Ȃ�
	                        	}
	                        	else{
	                                msgRetu += "�y" + (j+1) + "��ځz";
	                        	}
	                        }
                        }


//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
                        //�����H����
                        var seizo_han = detailDoc.getElementById("txtSeizoHan_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seizokotei_han", seizo_han, j);
                        //�����H��
                        var seizo_shosai = detailDoc.getElementById("hdnSeizoShosai_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seizokotei_shosai", seizo_shosai, j);
//mod end   -------------------------------------------------------------------------------

						// �yQP@00342�z���Z���~

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa start
                        // �[�U�ʐ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // �[�U�ʖ����i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // ���v�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // ��d
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // ���x���ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // ��d���Z�ʁi�s�j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // ������i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // �ޗ���i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // �����v�i�~�j/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // �����v�i�~�j/��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // ������i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // �ޗ���i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // �����v�i�~�j/�s
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // ����
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // �e���i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 �ǉ��ۑ�No.11 nishigawa end

                    }


                    //-------------------------- [shizai]�e�[�u���i�[ -------------------------
                    //�����̓t���O
                    var minyuFg = false;
                    //���ރe�[�u���I�u�W�F�N�g�擾
                    var tblShizai = detailDoc.getElementById("tblList4");
                    //���ޕ\�̍s���擾
                    recCnt = tblShizai.rows.length;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                        //���ލs�ɉ������͂���Ă��Ȃ��ꍇ
                        if( detailFrm.txtCdShizai[j].value == ""
                            && detailFrm.txtNmShizai[j].value == ""
                            && detailFrm.txtTankaShizai[j].value == ""
                            && detailFrm.txtBudomariShizai[j].value == ""
                            && detailFrm.txtSiyouShizai[j].value == ""){

                            //�������Ȃ�


                        }else{

                            //XML�֒ǉ�
                            if (recInsert != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN0030", "shizai");
                            }
                            // ����SEQ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "seq_shizai", recInsert+1, recInsert);
                            // ���CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kaisya", detailFrm.hdnKaisha_Shizai[j].value, recInsert);
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kojyo", detailFrm.hdnKojo_Shizai[j].value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_shizai", detailFrm.txtCdShizai[j].value, recInsert);
                            // ���ޖ�
                            funXmlWrite_Tbl(reqAry[i], "shizai", "nm_shizai", detailFrm.txtNmShizai[j].value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "tanka", detailFrm.txtTankaShizai[j].value, recInsert);
                            if(detailFrm.txtTankaShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // �����i���j
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            if(detailFrm.txtBudomariShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // �g�p��/���
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            if(detailFrm.txtSiyouShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // �o�^��ID
                            funXmlWrite_Tbl(reqAry[i], "shizai", "id_toroku", detailFrm.hdnId_toroku[j].value, recInsert);
                            // �o�^��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "dt_toroku", detailFrm.hdnDt_toroku[j].value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }
                    }

                    //�P���E�����E�g�p�ʂ̖����͊m�F�`�F�b�N
                    if(minyuFg){
                        //�m�F���b�Z�[�W��\��
                        if(funConfMsgBox(E000012) == ConBtnYes){

                        }else{

                            return false;
                        }
                    }

                    //���Ϗ[�U�ʁE�L�������E�Œ������͊m�F�`�F�b�N
                    if(msgRetu != ""){
                        //�m�F���b�Z�[�W��\��
                        if(funConfMsgBox(msgRetu + "\\n" + E000013) == ConBtnYes){

                        }else{

                            return false;
                        }
                    }

                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
                    //-------------------------- [kihonsub]�e�[�u���i�[ -------------------------
                    //�񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0030", "kihonsub");
                        }
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // ������]
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka",  detailDoc.getElementById("txtGenkaKibo"+j).value, j);

	                    // ������]�P��CD
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka_tani", detailDoc.getElementById("hdnGenkaTaniCd"+j).value, j);

	                    // ������]
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
                    }
                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

                    break;
            }
        }

        //���͍��ڂ̕ύX�m�F
        else if (XmlId.toString() == "RGEN0050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0020

                    //���[�v�J�E���g
                    var recCnt;
                    var j;

                    //-------------------------- [kihon]�e�[�u���i�[ --------------------------
                    // ����CD-�Ј�CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // ����CD-�N
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // ����CD-�ǔ�
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // �yQP@00342�z����CD-�}��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // �̗p�T���v���m�n
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    // �H��@�S�����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // �H��@�S���H��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // ���萔
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // �׎p
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // ������]�P��CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // ������]
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // �z�蕨��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // �̔�����
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // �v�攄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // �v�旘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // �̔��㔄��
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // �̔��㗘�v
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);

                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                    // �������b�g
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima�yQP@30151�zNo.37 end

                    // �������Z����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);
                    // �������Z�����i�c�ƘA���p�j
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);
                    // �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
                    var koteihiFg = "";
                    for (j = 0; j < detailFrm.radioKoteihi.length; j++){
                       if(detailFrm.radioKoteihi[j].checked == true){
                           koteihiFg = detailFrm.radioKoteihi[j].value;
                       }
                    }
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ragio_kesu_kg", koteihiFg, 0);

                    //-------------------------- [genryo]�e�[�u���i�[ -------------------------
                    //�����\�̍s���擾
                    recCnt = detailFrm.cnt_genryo.value;
                    //�����s�J�E���g
                    var gyoCnt = 1;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    for( j = 0; j < recCnt; j++ ){

                        //�����s���ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("hdnCd_kotei_"+gyoCnt)){
                            if (recInsert != 0) {
                                //���R�[�h�}��
                                funAddRecNode_Tbl(reqAry[i], "FGEN0020", "genryo");
                            }
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                            // �H��SEQ
                            funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "genryo", "tanka", detailDoc.getElementById("txtTanka_"+gyoCnt).value, recInsert);
                            // ����
                            funXmlWrite_Tbl(reqAry[i], "genryo", "budomari", detailDoc.getElementById("txtBudomari_"+gyoCnt).value, recInsert);
                            // �s�ԍ�
                            funXmlWrite_Tbl(reqAry[i], "genryo", "no_gyo", gyoCnt, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }

                        //�����s�J�E���g+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]�e�[�u���i�[ -------------------------
                    //�v�Z�\�̗񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0020", "keisan");
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start
                        // //���ڌŒ�`�F�b�N
                       if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "", j);
                        }
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 end
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ���Z��
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);
                        // �L�������i���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", detailDoc.getElementById("txtYukoBudomari_"+j).value, j);
                        // ���Ϗ[�U�ʁi���j
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", detailDoc.getElementById("txtHeikinZyu_"+j).value, j);
                        // �Œ��/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", detailDoc.getElementById("txtCaseKotei_"+j).value, j);
                        // �Œ��/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", detailDoc.getElementById("txtKgKotei_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano start
                        // ���v/�P�[�X
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // ���v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // �����v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // �yQP@00342�z���Z���~

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);
                    }


                    //-------------------------- [shizai]�e�[�u���i�[ -------------------------
                    //���ރe�[�u���I�u�W�F�N�g�擾
                    var tblShizai = detailDoc.getElementById("tblList4");
                    //���ޕ\�̍s���擾
                    recCnt = tblShizai.rows.length;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                        //���ލs�ɉ������͂���Ă��Ȃ��ꍇ
                        if( detailFrm.txtCdShizai[j].value == ""
                            && detailFrm.txtNmShizai[j].value == ""
                            && detailFrm.txtTankaShizai[j].value == ""
                            && detailFrm.txtBudomariShizai[j].value == ""
                            && detailFrm.txtSiyouShizai[j].value == ""){

                            //�������Ȃ�


                        }else{

                            //XML�֒ǉ�
                            if (recInsert != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN0020", "shizai");
                            }
                            // ����SEQ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "seq_shizai", recInsert+1, recInsert);
                            // ���CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kaisya", detailFrm.hdnKaisha_Shizai[j].value, recInsert);
                            // �H��CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kojyo", detailFrm.hdnKojo_Shizai[j].value, recInsert);
                            // ����CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_shizai", detailFrm.txtCdShizai[j].value, recInsert);
                            // ���ޖ�
                            funXmlWrite_Tbl(reqAry[i], "shizai", "nm_shizai", detailFrm.txtNmShizai[j].value, recInsert);
                            // �P��
                            funXmlWrite_Tbl(reqAry[i], "shizai", "tanka", detailFrm.txtTankaShizai[j].value, recInsert);
                            // �����i���j
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            // �g�p��/���
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            // XML���R�[�h�}���J�E���g+1
                            recInsert++;
                        }
                    }

                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
                    //-------------------------- [kihonsub]�e�[�u���i�[ -------------------------
                    //�񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0020", "kihonsub");
                        }
                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // �������b�g
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);
                    }
                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end


                    break;
            }
        }


        //���
        else if (XmlId.toString() == "RGEN0051"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0050

                    //���[�v�J�E���g
                    var recCnt;
                    var j;
                    //-------------------------- [table]�e�[�u���i�[ -------------------------
                    //�񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
    				// 20160524  KPX@1600766 ADD start
                    // �H��@�S�����
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, recInsert);
    				// 20160524  KPX@1600766 ADD end

                    for( j = 0; j < recCnt; j++ ){
                        //�񐔂ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("chkInsatu"+j)){
                            //�I�u�W�F�N�g���`�F�b�N����Ă���ꍇ
                            if(detailDoc.getElementById("chkInsatu"+j).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN0050", "table");
                                }
                                // ����CD-�Ј�CD
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // ����CD-�N
                                funXmlWrite_Tbl(reqAry[i], "table", "nen", headerFrm.txtNen.value, recInsert);
                                // ����CD-�ǔ�
                                funXmlWrite_Tbl(reqAry[i], "table", "no_oi", headerFrm.txtOiNo.value, recInsert);

                                // �yQP@00342�z����CD-�}��
			                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", headerFrm.txtEdaNo.value, recInsert);

                                // ����SEQ
                                funXmlWrite_Tbl(reqAry[i], "table", "seq_shisaku", detailDoc.getElementById("chkInsatu"+j).value, recInsert);

                                // �yQP@00342�z�[�U�ʐ���
			                    funXmlWrite_Tbl(reqAry[i], "table", "zyuten_sui", detailDoc.getElementById("txtSuiZyuten_"+j).value, recInsert);

			                    // �yQP@00342�z�[�U�ʖ���
			                    funXmlWrite_Tbl(reqAry[i], "table", "zyuten_yu", detailDoc.getElementById("txtYuZyuten_"+j).value, recInsert);


                                // XML���R�[�h�}���J�E���g+1
                                recInsert++;
                            }
                        }

                    }

                    break;
            }
        }

        //�}�X�^�P���E����
        else if (XmlId.toString() == "RGEN0070"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0070

                    //���[�v�J�E���g
                    var recCnt;
                    var j;
                    //-------------------------- [table]�e�[�u���i�[ -------------------------
                    //�񐔎擾
                    recCnt = detailFrm.cnt_genryo.value;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //�����s�J�E���g
                    var gyoCnt = 1;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        //�񐔂ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("chkGenryo_"+gyoCnt)){
                            //�I�u�W�F�N�g���`�F�b�N����Ă���ꍇ
                            if(detailDoc.getElementById("chkGenryo_"+gyoCnt).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN0070", "genryo");
                                }
                                // ����CD-�Ј�CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // ����CD-�N
                                funXmlWrite_Tbl(reqAry[i], "genryo", "nen", headerFrm.txtNen.value, recInsert);
                                // ����CD-�ǔ�
                                funXmlWrite_Tbl(reqAry[i], "genryo", "no_oi", headerFrm.txtOiNo.value, recInsert);

                                // �yQP@00342�z����CD-�}��
                                funXmlWrite_Tbl(reqAry[i], "genryo", "no_eda", headerFrm.txtEdaNo.value, recInsert);

                                // �s�ԍ�
                                funXmlWrite_Tbl(reqAry[i], "genryo", "seq_gyo", gyoCnt, recInsert);
                                // �H��CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                                // �H��SEQ
                                funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                                // ����CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                                // XML���R�[�h�}���J�E���g+1
                                recInsert++;
                            }
                        }

                        //�����s�J�E���g+1
                        gyoCnt++;

                    }

                    break;
            }
        //2010/02/23 NAKAMURA ADD START-------------------------------------------------
        }else if (XmlId.toString() == "RGEN0090") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0090
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    //�yQP@00342�z
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //2010/02/23 NAKAMURA ADD END---------------------------------------------------

        //���ތ���
        if (XmlId.toString() == "RGEN0080") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0080
                    //���ރe�[�u���I�u�W�F�N�g�擾
                    tblShizai = detailDoc.getElementById("tblList4");
                    //���ޕ\�̍s���擾
                    recCnt = tblShizai.rows.length;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    // ����SEQ
                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", 1, 0);
                    // ���CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // �H��CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // ����CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", detailFrm.txtCdShizai[CurrentRow].value, 0);

                    break;
            }
        }
        //�yQP@00342�z�}�ԍ쐬
        else if (XmlId.toString() == "RGEN2200") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2180
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "shurui", headerFrm.hdnShuruiEda.value, 0);
//ADD start 20120410 hisahori
                    funXmlWrite(reqAry[i], "eda_nm_shisaku", headerFrm.hdnShisakuNmEda.value, 0);
//ADD end 20120410 hisahori
                  //�yQP@40812�zNo.3 ADD start 2015/03/03 TT.Kitazawa
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                  //�yQP@40812�zNo.3 ADD end 2015/03/03 TT.Kitazawa
                    break;
            }
        }
//20160617  KPX@502111_No.5 ADD start
        //���ƌ������擾�E�X�V
        else if (XmlId.toString() == "RGEN2280") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2280
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //���ƌ������擾�E�X�V
        else if (XmlId.toString() == "JW821") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2260
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    //�������[�h�F����m�����
                    funXmlWrite(reqAry[i], "syoriMode", "1", 0);
                    break;
            }
        }
//20160617  KPX@502111_No.5 ADD start

    }

    return true;

}


//========================================================================================
// ���ލs�폜����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// �T�v  �F�I������Ă��鎑�ލs���폜����
//========================================================================================
function funDeleteShizai(){

    //�����ڰт�Document�Q��
    var detailDoc = parent.detail.document;
    var frm = document.frm00;

    //�e�[�u���Q��
    var tblShizai = detailDoc.getElementById("tblList4");
    var gyoCount = tblShizai.rows.length;

    //���ލs�̍폜
    for(var i=0; i<gyoCount; i++){
        //�I������Ă���s�̏ꍇ
        if(frm.chkShizaiGyo[i].checked){
            //�s�폜
            tblShizai.deleteRow(i);
            i = i - 1;
            gyoCount = gyoCount - 1;
        }
    }

    //���݂̍s������蒼��
    gyoCount = tblShizai.rows.length;

    //���ލs�̒ǉ�
    for(var i=gyoCount; i<20; i++){
        //�ŏI�s�ǉ�
        funAddShizai();
    }

    return true;
}

//========================================================================================
// ���ލs�ǉ�����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// �T�v  �F�ŏI�s��ǉ�����
//========================================================================================
function funAddShizai(){

    //�����ڰт�Document�Q��
    var detailDoc = parent.detail.document;
    var frm = document.frm00;

    // �H��@�S�����
    var cd_kaisha = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;
    // �H��@�S���H��
    var cd_kojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //�e�[�u���Q��
    var tblShizai = detailDoc.getElementById("tblList4");

    //�s���擾
    var strlength = tblShizai.rows.length;

    //TR�v�f�ǉ�
    var row = tblShizai.insertRow(strlength);

    //TD�v�f�ǉ�
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);

    //�I��
    cell1.style.backgroundColor = "#ffff88";
    cell1.className = "column";
    cell1.setAttribute("class","column");
    cell1.setAttribute("width","48px");
    cell1.setAttribute("align","center");
    cell1.innerHTML = "<input type=\"checkbox\" name=\"chkShizaiGyo\" tabindex=\"27\" />&nbsp;";

    //�H��L��
    cell2.className = "column";
    cell2.setAttribute("class","column");
    cell2.setAttribute("width","50px");
    cell2.setAttribute("align","center");
    cell2.innerHTML = "<input type=\"text\" id=\"txtKigouKojo\" name=\"txtKigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"\" tabindex=\"-1\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKaisha_Shizai\" name=\"hdnKaisha_Shizai\" value=\"" + cd_kaisha + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKojo_Shizai\"   name=\"hdnKojo_Shizai\" value=\"" + cd_kojo + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnId_toroku\"   name=\"hdnId_toroku\" value=\"\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnDt_toroku\"   name=\"hdnDt_toroku\" value=\"\" />";

    //���ރR�[�h
    cell3.style.backgroundColor = "#ffff88";
    cell3.className = "column";
    cell3.setAttribute("class","column");
    cell3.setAttribute("width","100px");
    cell3.setAttribute("align","center");

    //�yQP@00342�z���ރe�[�u���C��
    //cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funShizaiSearch()\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";
	cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funChangeSelectRowColor3(this);funShizaiSearch();\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";

    //���ޖ�
    cell4.style.backgroundColor = "#ffff88";
    cell4.className = "column";
    cell4.setAttribute("class","column");
    cell4.setAttribute("width","353px");
    cell4.setAttribute("align","left");
    cell4.innerHTML = "<input type=\"text\" id=\"txtNmShizai\" name=\"txtNmShizai\" class=\"table_text_disb\"  style=\"ime-mode:active;text-align:left\" value=\"\" tabindex=\"27\" />";

    //�P��
    cell5.style.backgroundColor = "#ffff88";
    cell5.className = "column";
    cell5.setAttribute("class","column");
    cell5.setAttribute("width","100px");
    cell5.setAttribute("align","right");
    cell5.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //����
    cell6.style.backgroundColor = "#ffff88";
    cell6.className = "column";
    cell6.setAttribute("class","column");
    cell6.setAttribute("width","80px");
    cell6.setAttribute("align","right");
    cell6.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //�g�p��/�P�[�X
    cell7.style.backgroundColor = "#ffff88";
    cell7.className = "column";
    cell7.setAttribute("class","column");
    cell7.setAttribute("width","120");
    cell7.setAttribute("align","right");
    cell7.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"table_text_disb\" style=\"text-align:right;\" value=\"\" tabindex=\"27\" />";

    //���z
    cell8.className = "column";
    cell8.setAttribute("class","column");
    cell8.setAttribute("width","100px");
    cell8.setAttribute("align","right");
    cell8.innerHTML = "&nbsp;";

    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
//       �F�CkaraFg   �F�󔒑I���̐ݒ�i0�F�󔒖����A1�F�󔒗L��j
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, karaFg) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;
	var tableNm;

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

        //�̗p�T���v��No
        case 1:
            atbName = "nm_sample"; //�T���v����
            atbCd = "seq_shisaku"; //����SEQ
            tableNm = "tr_shisan_shisaku";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾

            //�u�̗p�����v�}��
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = "�̗p����";
            objNewOption.value = SaiyouNashiValue;

            break;

        //�������
        case 2:
            atbName = "nm_kaisya"; //��Ж�
            atbCd = "cd_kaisya"; //���CD
            tableNm = "kaisya";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        //�����H��
        case 3:
            atbName = "nm_kojyo"; //�H�ꖼ
            atbCd = "cd_kojyo"; //�H��CD
            tableNm = "kojo";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        //������]�P��
        case 4:
            atbName = "kibo_genka_nm_tani"; //���e������
            atbCd = "kibo_genka_cd_tani"; //���e����CD
            tableNm = "tani";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        //�����H��i��ЕύX���j
        case 5:
            atbName = "nm_kojyo"; //�H�ꖼ
            atbCd = "cd_kojyo"; //�H��CD
            tableNm = "kojyo";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
        //�T���v��No�R���{�{�b�N�X
        case 6:
            atbName = "nm_sanpuru"; //�T���v��No
            atbCd = "seq_shisaku"; //����SEQ
            tableNm = "keisan";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾

            for (i = 0; i < reccnt; i++) {
		        if (funXmlRead_3(xmlData, tableNm, atbCd, 0, i) != "") {
		            objNewOption = document.createElement("option");
		            obj.options.add(objNewOption);
		            if(funXmlRead_3(xmlData, tableNm, atbName, 0, i) == ""){
		            	objNewOption.innerText = "";
		            }
		            else{
			            objNewOption.innerText = funXmlRead_3(xmlData, tableNm, atbName, 0, i);
		            }
		            objNewOption.value = funXmlRead_3(xmlData, tableNm, atbCd, 0, i);
		        }
   			}

   			//�擪�s�̍폜
    	    obj.options[0] = null;

		    //�����ޯ������̫�ĕ\��
		    obj.selectedIndex = 0;

		    return true;

//mod end   -------------------------------------------------------------------------------

		//�yQP@00342�z�}�Ԏ��
        case 7:
            atbName = "nm_literal";
            atbCd = "cd_literal";
            tableNm = "table";
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead_3(xmlData, tableNm, atbCd, 0, i) != "" && funXmlRead_3(xmlData, tableNm, atbName, 0, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead_3(xmlData, tableNm, atbName, 0, i);
            objNewOption.value = funXmlRead_3(xmlData, tableNm, atbCd, 0, i);
        }
    }

    //�擪�s�̍폜
    if(karaFg == 0){
        obj.options[0] = null;
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFY.nishigawa
// �쐬���F2009/10/22
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    //XML�ƃR���{�{�b�N�XVALUE�l�𔻒�
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //�̗p�T���v��No
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0010O, "kihon", "saiyo_no", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //�������
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "cd_kaisya", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //�����H��i�����\���j
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "cd_kojyo", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 4:    //������]�P��
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kibo_genka_cd_tani", 0, 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// �X�V���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�X�V���\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funUpdateDisplay(xmlData, ObjectId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var tableNm;          //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var kenkyu_tanto;     //�������X�V��
    var kenkyu_date;      //�������X�V���t
    var seihon_tanto;     //���Y�{���X�V��
    var seihon_date       //���Y�{���X�V���t
    var genshizai_tanto;  //�����ޒ��B�X�V��
    var genshizai_date;   //�����ޒ��B�X�V���t
    var kozyo_tanto;      //�H��X�V��
    var kozyo_date;       //�H��X�V���t
    var saiyo_date;       //�̗p�����No�X�V��
    var sam_id_koshin;    //�T���v��No�X�V��

    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = document.getElementById(ObjectId);

    //�e�[�u�����ݒ�
    tableNm = "kihon";

    //XML���l�擾
    kenkyu_tanto = funXmlRead_3(xmlData, tableNm, "kenkyu_tanto", 0, 0);
    kenkyu_date = funXmlRead_3(xmlData, tableNm, "kenkyu_date", 0, 0);
    seihon_tanto = funXmlRead_3(xmlData, tableNm, "seihon_tanto", 0, 0);
    seihon_date = funXmlRead_3(xmlData, tableNm, "seihon_date", 0, 0);
    genshizai_tanto = funXmlRead_3(xmlData, tableNm, "genshizai_tanto", 0, 0);
    genshizai_date = funXmlRead_3(xmlData, tableNm, "genshizai_date", 0, 0);
    kozyo_tanto = funXmlRead_3(xmlData, tableNm, "kozyo_tanto", 0, 0);
    kozyo_date = funXmlRead_3(xmlData, tableNm, "kozyo_date", 0, 0);
    saiyo_date = funXmlRead_3(xmlData, tableNm, "saiyo_date", 0, 0);
    sam_id_koshin = funXmlRead_3(xmlData, tableNm, "sam_id_koshin", 0, 0);

    //�o��HTML�ݒ�
    OutputHtml = "<table width=\"360px\" border=\"0\">";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td width=\"100px\">";
    OutputHtml += "           �������F";
    OutputHtml += "        </td>";
    OutputHtml += "        <td width=\"150px\">";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + kenkyu_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td width=\"80px\">" + kenkyu_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>���Y�Ǘ��F</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + seihon_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + seihon_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>�����ޒ��B�F</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + genshizai_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + genshizai_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>�H��F</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + kozyo_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + kozyo_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>�T���v��No�m��F</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + sam_id_koshin + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + saiyo_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";

    //HTML���o��s
    obj.innerHTML = OutputHtml;

    return true;

}

//========================================================================================
//�������Z�A�T���v�����̏��擾���\������
//�쐬�ҁFH.Shima
//�쐬���F2013/7/2
//����  �F�@xmlData �F�ݒ�XML
//      �F�AObjectId�F�ݒ�I�u�W�F�N�gID
//�߂�l�F�Ȃ�
//�T�v  �F��{���e�[�u���i�����j�\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funKihonSubDisplay(xmlData, ObjectId){

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
	var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
	var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
	var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

	var OutputHtml;       //�o��HTML
	var i;                //���[�v�J�E���g
	var cnt_sample;

	var genka_kibo;							//��]����
	var genka_tani;							//
	var genka_tanicd;						//
	var baika_kibo;							//��]����
	var baika_tani;							//
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	var sotei_buturyo_s;
	var sotei_buturyo_u;
	var sotei_buturyo_k;
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
	var sotei_buturyo;						//�z�蕨��
	var hatubai_jiki;						//����[�i����
	var hanbai_kikan_t;						//�̔�����
	var hanbai_kikan_s;						//
	var hanbai_kikan_k;						//
	var keikaku_uriage;						//�v�攄��
	var keikaku_rieki;						//�v�旘�v
	var hanbaigo_uriage;					//
	var hanbaigo_rieki;						//
	var seizo_rot;							//�������b�g
	// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
	var fg_chusi;							//���~�t���O
	var fg_koumokuchk;						//���ڌŒ�`�F�b�N
	// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

	var tableKihonNm = "kihon";
	var tableKihonSubNm = "kihonsub";

	var obj;
	//HTML�o�̓I�u�W�F�N�g�ݒ�
	obj = detailDoc.getElementById(ObjectId);

	//�񐔎擾
	cnt_sample = funXmlRead_3(xmlData, tableKihonNm, "cnt_genryo", 0, i);

	OutputHtml = "";

	OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";

	OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;\">";

	OutputHtml += "<tr>";

	//�yQP@40812�zNo.16 ADD start 2015/03/03 TT.Kitazawa
	//------------------------------------- �T���v��No. -------------------------------------
    //�T���v��No
    var nm_sample = "";
    //�yQP@40812�zNo.6 MOD start 2015/08/26 TT.Kitazawa
    var no_iraisample = "";
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		nm_sample = funXmlRead_3(xmlData, tableKihonSubNm, "nm_sample", 0, i);
		// ���[���n���p�F�T���v��No.�̕ۑ�
		if( no_iraisample == ""){
			no_iraisample = nm_sample;
		}
		else{
			no_iraisample = no_iraisample + "," + nm_sample;
		}

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"175\" >";
		OutputHtml += "    <input type=\"text\" id = \"nm_sample" + i + "\" readonly style=\"width:100%;border-width:0px;text-align:right;\" value=\"" + nm_sample + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	headerFrm.hdnNo_iraisampleForMail.value = no_iraisample;
	//�yQP@40812�zNo.6 MOD end 2015/08/26 TT.Kitazawa

//	OutputHtml += "</tr><tr>" ;          /* del 2015/08/21 */
	//�yQP@40812�zNo.16 ADD end  2015/03/03 TT.Kitazawa

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- ������] -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		genka_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka", 0, i);
		genka_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);
		genka_tanicd = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_cd_tani", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"175\" >";
		OutputHtml += "    <input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:80px;\" class=\"table_text_view\" readonly  value=\"" + genka_kibo + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtGenkaTani" + i + "\" name=\"txtGenkaTani" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + genka_tani + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"hidden\" id=\"hdnGenkaTaniCd" + i + "\" name=\"hdnGenkaTaniCd" + i + "\" style=\"width:82px;\" value=\"" + genka_tanicd + "\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- ������] -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		baika_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_baika", 0, i);
		baika_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" style=\"width:80px;\" class=\"table_text_view\" readonly  value=\"" + baika_kibo + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start
	//------------------------------------- �̔����� -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		hanbai_kikan_t = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_t", 0, i);
		hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		hanbai_kikan_k = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_k", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_t" + i + "\" name=\"txtHanbaiKikan_t" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_t + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:20px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_k" + i + "\" name=\"txtHanbaiKikan_k" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	OutputHtml += "</tr><tr>" ;
	// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start

	//------------------------------------- �z�蕨�� -------------------------------------
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		sotei_buturyo_s = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo_s", 0, i);
		if( sotei_buturyo_s == "" ){
		} else {
			sotei_buturyo_s = sotei_buturyo_s * 1;
		}
		sotei_buturyo_u = funXmlRead_3(xmlData, tableKihonSubNm, "sotei_buturyo_u", 0, i);
		sotei_buturyo_k = funXmlRead_3(xmlData, tableKihonSubNm, "sotei_buturyo_k", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		// MOD 2013/12/24 QP@30154 okano start
//			OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"width:70px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_s + "\" tabindex=\"-1\" />";
		// MOD 2013/12/24 QP@30154 okano end
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_u" + i + "\" name=\"txtSoteiButuryo_u" + i + "\" style=\"width:40px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_u + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_k" + i + "\" name=\"txtSoteiButuryo_k" + i + "\" style=\"width:50px;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>";
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		sotei_buturyo = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;\" name=\"txtSoteiButuryo" + i + "\" class=\"table_text_view\" readonly value=\"" + sotei_buturyo + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- �������� -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		hatubai_jiki = funXmlRead_3(xmlData, tableKihonSubNm, "ziki_hatubai", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_view\" readonly value=\"" + hatubai_jiki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	// DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start�i��Ɉړ��j
/*    //------------------------------------- �̔����� -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		hanbai_kikan_t = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_t", 0, i);
		hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		hanbai_kikan_k = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_k", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_t" + i + "\" name=\"txtHanbaiKikan_t" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_t + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:20px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_k" + i + "\" name=\"txtHanbaiKikan_k" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	OutputHtml += "</tr><tr>" ;
 */
	// DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 end

	//------------------------------------- �v�攄�� -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		keikaku_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_uriage", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_view\" readonly value=\"" + keikaku_uriage + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- �v�旘�v -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		keikaku_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_rieki", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_view\" readonly value=\"" + keikaku_rieki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- �̔��㔄�� -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		hanbaigo_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_uriage", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoUriage" + i + "\" class=\"table_text_view\" readonly value=\"" + hanbaigo_uriage + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- �̔��㗘�v -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XML�f�[�^�擾
		hanbaigo_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_rieki", 0, i);

		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\"  style=\"ime-mode:active;\" name=\"txtHanbaigoRieki" + i + "\" class=\"table_text_view\" readonly value=\"" + hanbaigo_rieki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- �������b�g -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		// MOD 2014/8/7 shima�yQP@30154�zNo.63 start
		//XML�f�[�^�擾
		seizo_rot = funXmlRead_3(xmlData, tableKihonSubNm, "seizo_roto", 0, i);
		//���~�t���O�擾
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		//���ڌŒ�`�F�b�N�擾
		fg_koumokuchk = funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);
		//HTML����
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		if(fg_koumokuchk == "1"){
			//���ڃ`�F�b�N��͔w�i���F
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:active;background-color:" + color_read + "\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" readonly value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		} else if(fg_chusi == "1"){
			//���Z���~��͔w�i�F���O���[��
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:disabled;background-color:#c0c0c0\";\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		}else{
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:active;\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		}
		OutputHtml += "</td>";
		// MOD 2014/8/7 shima�yQP@30154�zNo.63 end
	}

	OutputHtml += "</tr></table>" ;


	//------------------------------------------------------------------------------------
	//                                  HTML�o��
	//------------------------------------------------------------------------------------
	//HTML���o��
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	return true;
}

//========================================================================================
// �����e�[�u���i�����j�ύX�A�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@xmlUser �F�X�V���i�[XML��
// �߂�l�F�Ȃ�
// �T�v  �F�����e�[�u���i�����j�ύX�A�����\��
//========================================================================================
function funGenryo_HenkouDisplay(xmlData) {

    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var tablekihonNm = "henkou";
    var reccnt = funGetLength_3(xmlData, tablekihonNm, 0); //�����擾
    var henko_renraku;

    //�ύX�A���ݒ�
    for(i = 0; i < reccnt; i++){

        //XML�f�[�^�擾
        henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tablekihonNm, "henkourenraku", 0, i));

        //�ύX
        detailDoc.getElementById("txtHenkouRen_"+(i+1)).value = henko_renraku;
    }

    return true;
}


//========================================================================================
// �����e�[�u���i�����j���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�����e�[�u���i�����j�\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funGenryo_LeftDisplay(xmlData, ObjectId) {

    var headerFrm = parent.header.document.frm00; //ͯ�ް̫�юQ��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var Kengen = headerFrm.hdnKengen.value;       //�����F�@�\ID
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableGenryoNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var cnt_genryo;       //�s��
    var sort_kotei;       //�H��
    var cd_kotei;         //�H��CD
    var seq_kotei;        //�H��SEQ
    var cd_genryo;        //����CD
    var nm_genryo;        //������
    var henko_renraku;    //�ύX
    var tanka;            //�P��
    var budomari;         //����
    var genryo_fg;        //�����s�t���O
    var i;                //���[�v�J�E���g
    //2010/02/18 NAKAMURA ADD START------------
    var hdnKojoNmTanka;		//�H�ꖼ �P��
    var hdnKojoNmBudomari;	//�H�ꖼ ����
    //2010/02/18 NAKAMURA ADD END--------------

    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableGenryoNm = "genryo";

    //�s���擾
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //�o��HTML�ݒ�
    OutputHtml += "<input type=\"hidden\" id=\"cnt_genryo\" name=\"cnt_genryo\" value=\"" + cnt_genryo + "\">";

	//�e�[�u���\��
	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
	//OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"475px\" style=\"word-break:break-all;word-wrap:break-word;\">";
	OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"605px\" style=\"word-break:break-all;word-wrap:break-word;\">";
	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
    for(i = 0; i < cnt_genryo; i++){

        //HTML
        var sentaku_checkbox; //�I���`�F�b�N�{�b�N�X
        var tanka_textbox;    //�P���e�L�X�g�{�b�N�X
        var budomari_textbox; //�����e�L�X�g�{�b�N�X
        var cd_kotei_hidden;  //�H��CD�B������
        var seq_kotei_hidden; //�H��SEQ�B������
        var chkDisabled;      //�`�F�b�N�{�b�N�X���͐ݒ�
        var txtReadonly;      //�e�L�X�g�{�b�N�X���͐ݒ�
        var txtClass;         //�e�L�X�g�{�b�N�X�w�i�F
// 20160513  KPX@1600766 ADD start
        var txtType = "text";    //�e�L�X�g�{�b�N�X�^�C�v
// 20160513  KPX@1600766 ADD start

        //XML�f�[�^�擾
        sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
        cd_kotei = funXmlRead_3(xmlData, tableGenryoNm, "cd_kotei", 0, i);
        seq_kotei = funXmlRead_3(xmlData, tableGenryoNm, "seq_kotei", 0, i);
        cd_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "cd_genryo", 0, i));
        nm_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_genryo", 0, i));
        henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "henko_renraku", 0, i));
        tanka = funXmlRead_3(xmlData, tableGenryoNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableGenryoNm, "budomari", 0, i);

        genryo_fg = funXmlRead_3(xmlData, tableGenryoNm, "genryo_fg", 0, i);
        //2010/02/18 NAKAMURA ADD START----------------------------------------
        hdnKojoNmTanka = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_tanka", 0, i));
        hdnKojoNmBudomari = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_budomari", 0, i));
        //2010/02/18 NAKAMURA ADD END------------------------------------------


        //�yQP@00342�z�r��
        if(headerFrm.strKengenMoto.value == "999"){
        	chkDisabled = "disabled";
            txtReadonly = "readonly";
            txtClass = henshuNgClass;
        }
        else{
        	//�ҏW����
        	if(Kengen.toString() == ConFuncIdEdit.toString()){

        		//�ҏW�\�ɐݒ�
        		chkDisabled = "";
	            txtReadonly = "";
	            txtClass = henshuOkClass;

        		//�yQP@00342�z�X�e�[�^�X����
				var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
				var st_seikan = headerFrm.hdnStatus_seikan.value;
				var st_gentyo = headerFrm.hdnStatus_gentyo.value;
				var st_kojo    = headerFrm.hdnStatus_kojo.value;
				var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

				//�yQP@00342�z�����擾
			    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
			    var seikan = headerFrm.hdnBusho_seikan.value;
			    var gentyo = headerFrm.hdnBusho_gentyo.value;
			    var kojo = headerFrm.hdnBusho_kojo.value;
			    var eigyo = headerFrm.hdnBusho_eigyo.value;

				//�yQP@00342�z�����ޒ��B���Ŋm�F�����̏ꍇ
				/*
				 * if( gentyo == "1" && st_gentyo == 2){ chkDisabled =
				 * "disabled"; txtReadonly = "readonly"; txtClass =
				 * henshuNgClass; }
				 */

				//�yQP@00342�z�H��Ŋm�F�����̏ꍇ
				/*
				 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
				 * txtReadonly = "readonly"; txtClass = henshuNgClass; }
				 */


			    //20160607 �yKPX@1502111_No8�z ADD start
			    // �H��Ŋm�F�����i�H��X�e�[�^�X �� 2�j �̏ꍇ
		        // �����F���ǂ͐��Y�Ǘ����X�e�[�^�X �� 3
			    if( kojo == "1" && st_kojo >= 2 ){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
				//20160607 �yKPX@1502111_No8�z ADD end

				//�yQP@00342�z���Y�Ǘ����X�e�[�^�X >= 3 or �c�ƃX�e�[�^�X >= 4�@�̏ꍇ
				if( st_seikan >= 3 || st_eigyo >= 4){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
	        }
	        //�yQP@00342�z�{��+Excel����
	        else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	            chkDisabled = "disabled";
	            txtReadonly = "readonly";
	            txtClass = henshuNgClass;
	        }
        }

// 20160513  KPX@1600766 ADD start
        //�������ȊO�͑S�\��
        //�������F�O���[�v��Ђ̒P���\������i���e�����}�X�^���j
        if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
            chkDisabled = "disabled";
            txtReadonly = "readonly";
            txtClass = henshuNgClass;

            if (tankaHyoujiFlg == "0") {
                //�P����\��
                txtType = "hidden";
            }
        }
// 20160513  KPX@1600766 ADD end


        if(genryo_fg == "1"){
            //�����s�̏ꍇ�͑I���A�P���A�����A�H��CD�A�H��SEQ�I�u�W�F�N�g�̐���
            sentaku_checkbox = "<input type=\"checkbox\" id=\"chkGenryo_" + (i+1) + "\" name=\"chkGenryo_" + (i+1) + "\" height=\"12px\" " + chkDisabled + "  tabindex=\"18\" />";
// 20160513  KPX@1600766 MOD start
            //�������F�P����\���̃O���[�v��Ђ͕\�����B��
//            tanka_textbox = "<input type=\"text\" onchange=\"ShisakuGenka_Tankachanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\"   id=\"txtTanka_" + (i+1) + "\" name=\"txtTanka_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
//            budomari_textbox = "<input type=\"text\" onchange=\"ShisakuGenka_Budomarichanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomari_" + (i+1) + "\" name=\"txtBudomari_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
            tanka_textbox = "<input type=\"" + txtType + "\" onchange=\"ShisakuGenka_Tankachanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\"   id=\"txtTanka_" + (i+1) + "\" name=\"txtTanka_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
            budomari_textbox = "<input type=\"" + txtType + "\" onchange=\"ShisakuGenka_Budomarichanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomari_" + (i+1) + "\" name=\"txtBudomari_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
// 20160513  KPX@1600766 MOD start
            cd_kotei_hidden = "            <input type=\"hidden\"  id=\"hdnCd_kotei_" + (i+1) + "\" name=\"hdnCd_kotei_" + (i+1) + "\" value=\"" + cd_kotei + "\">";
            seq_kotei_hidden = "            <input type=\"hidden\" id=\"hdnSeq_kotei_" + (i+1) + "\" name=\"hdnSeq_kotei_" + (i+1) + "\" value=\"" + seq_kotei + "\">";

        	//2010/02/16 NAKAMURA ADD START---
        	//�s
        	OutputHtml += "        		<input type=\"hidden\" id=\"gyo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + (i + 1) + "\" title=\"" + (i + 1) + "\" tabindex=\"-1\" name=\"gyo\" style=\"text-align:center;\" />";
        	//2010/02/16 NAKAMURA ADD END-----

        }else if(genryo_fg == "2"){
            //�H���s�̏ꍇ�͑I���A�P���A�����A�H��CD�A�H��SEQ�͋�
            sentaku_checkbox = "&nbsp;";
            tanka_textbox = "&nbsp;";
            budomari_textbox = "&nbsp;";
            cd_kotei_hidden = "";
            seq_kotei_hidden = "";
        }

        //�e�[�u���^�O����
        //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 MOD Start
//        OutputHtml += "    <tr class=\"disprow\">";
        OutputHtml += "    <tr class=\"disprow\" id=\"tableRowL_" + i + "\" name=\"tableRowL_" + i + "\">";
        //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 MOD End

        //�I��
        OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\">" + sentaku_checkbox + "</td>";

        //�H��
        OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\"><input type=\"text\" id=\"sort_kote\" name=\"sort_kote\" class=\"table_text_view\" style=\"text-align:center;\" readonly value=\"" + sort_kotei + "\" tabindex=\"-1\" /></td>";

        //2010/02/18 NAKAMURA UPDATE START---------------------------
        //�����R�[�h
        //OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
        //OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
        //OutputHtml += "        </td>";
		if(genryo_fg == "1"){
			//�����s�̏ꍇW�N���b�N���̃C�x���g��ݒ�
	        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
	        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
	        OutputHtml += "        </td>";
		}else if(genryo_fg == "2"){
			//�H���s�̏ꍇW�N���b�N�̃C�x���g�͖��ݒ�
	        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
	        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
	        OutputHtml += "        </td>";
		}
		//2010/02/18 NAKAMURA UDPATE END-----------------------------

        //������
		// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
		//OutputHtml += "        <td class=\"column\" style=\"width:180px;\">";
		OutputHtml += "        <td class=\"column\" style=\"width:310px;\">";
		// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
        //2010/02/18 NAKAMURA UPDATE START------------------------------------
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryoNm_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
        //2010/02/18 NAKAMURA UPDATE END--------------------------------------

        //�H��CD
        OutputHtml += cd_kotei_hidden;
        //�H��SEQ
        OutputHtml += seq_kotei_hidden;
        OutputHtml += "        </td>";

        //�ύX
        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:20px;\"><input type=\"text\" name=\"txtHenkouRen_"+(i+1)+"\" id=\"txtHenkouRen_"+(i+1)+"\" class=\"table_text_view\" readonly value=\"" + henko_renraku + "\" onDblClick=\"openWin(" + (i+1) + ")\" tabindex=\"-1\" /></td>";

        //�P��
        OutputHtml += "        <td class=\"column\" style=\"width:70px;\">";
        OutputHtml += "            " + tanka_textbox;
        OutputHtml += "        </td>";

        //����
        OutputHtml += "        <td class=\"column\" style=\"width:45px;\">";
        OutputHtml += "            " + budomari_textbox;
        OutputHtml += "        </td>";

        //2010/02/18 NAKAMURA ADD START-------------------
        //�H�ꖼ �P��
        OutputHtml += "        <input type=\"hidden\" value=\"" + hdnKojoNmTanka + "\" name=\"hdnKojoNmTanka_" + (i+1) + "\" id=\"hdnKojoNmTanka_" + (i+1) + "\" >";
        //�H�ꖼ ����
        OutputHtml += "        <input type=\"hidden\" value=\"" + hdnKojoNmBudomari + "\" name=\"hdnKojoNmTanka_" + (i+1) + "\" id=\"hdnKojoNmBudomari_" + (i+1) + "\" >";
		//2010/02/18 NAKAMURA ADD END---------------------

		OutputHtml += "    </tr>";

    }

    OutputHtml += "</table>";


    //HTML���o��
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
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

        var detailDoc = parent.detail.document;		//�����ڰт�Document�Q��

        var gyoNo;				//�s�ԍ�
        var GenryoCd;			//�����R�[�h
        var GenryoNm;			//������
        var KojoNmTanka;		//�P���@�H�ꖼ
        var KojoNmBudomari;		//�P���@�H�ꖼ
        var subwinOnCloseEv;	//�T�u�E�B���h�E�N���[�Y���C�x���g
        gyoNo = detailDoc.getElementById("gyo_" + wclickgyoNo);							//�w��s�̍s�ԍ��\���p�̗񐧌�Q��
        GenryoCd = detailDoc.getElementById("txtCd_genryo_" + wclickgyoNo);				//�w��s�̌����R�[�h�\���p�̗񐧌�Q��
        GenryoNm = detailDoc.getElementById("txtCd_genryoNm_" + wclickgyoNo);			//�w��s�̌������\���p�̗񐧌�Q��
        KojoNmTanka = detailDoc.getElementById("hdnKojoNmTanka_" + wclickgyoNo);		// �w��s�̒P��
																						// �H�ꖼ�ێ��񐧌�Q��
        KojoNmBudomari = detailDoc.getElementById("hdnKojoNmBudomari_" + wclickgyoNo);	// �w��s�̕���
																						// �H�ꖼ�ێ��񐧌�Q��

        //�T�u�E�B���h�E��HTML���L�q
        outputHtml = "";

    	outputHtml = outputHtml + "		 	<input type=\"hidden\" style=\"border:none;\" id=\"GyoNo\" size=\"35\" readonly name=\"GyoNo\" value=\"" + gyoNo.value + "\" />";

        outputHtml = outputHtml + "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
        /*
		 * outputHtml = outputHtml + " <tr>"; outputHtml = outputHtml + "
		 * <td style=\"text-align:left;\">�s�ԍ�</td>"; outputHtml = outputHtml + "
		 * <td style=\"text-align:left;\">�F</td>"; outputHtml = outputHtml + "
		 * <td>"; outputHtml = outputHtml + " <input type=\"text\"
		 * style=\"border:none;\" id=\"GyoNo\" size=\"35\" readonly
		 * name=\"GyoNo\" value=\"" + gyoNo.value + "\" />"; outputHtml =
		 * outputHtml + " </td>"; outputHtml = outputHtml + " </tr>";
		 */
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">�����R�[�h</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">�F</td>";
        outputHtml = outputHtml + "      <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"GenryoCd\" size=\"35\" readonly name=\"GenryoCd\" value=\"" + GenryoCd.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">������</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">�F</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"GenryoNm\" size=\"35\" readonly name=\"GenryoNm\" value=\"" + GenryoNm.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">�P���@�H�ꖼ</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">�F</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"TankaKojoNm\" size=\"35\" readonly name=\"TankaKojoNm\" value=\"" + KojoNmTanka.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">�����@�H�ꖼ</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">�F</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"BudomariKojoNm\" size=\"35\" readonly name=\"BudomariKojoNm\" value=\"" + KojoNmBudomari.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //�T�u�E�B���h�E����
        win = new Window("win01", {
                    title: "�����ڍ׏��"
                    ,className: "alphacube"
                    //,top:595
                    //,left:15
                    ,top:600+event.y //�}�E�X�|�C���^��Y���W�i+600�����j
                    ,left:30+event.x //�}�E�X�|�C���^��X���W�i+30�����j
                    ,width:280
                    ,height:80
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
                    //,parent:headerFrm
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
// �����e�[�u���i�E���j���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�����e�[�u���i�E���j�\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funGenryo_RightDisplay(xmlData, ObjectId) {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var obj;              //�ݒ��I�u�W�F�N�g
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableHaigoNm;     //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var cnt_genryo;       //�s��
    //�yQP@10713�z20111031 hagiwara del start
    //var cnt_sample;       //��
    //�yQP@10713�z20111031 hagiwara del end
    var table_size;       //�e�[�u����
    var seq_shisaku;      //����SEQ
    var shisakuDate;      //������t
    var nm_sample;        //�T���v��NO�i���́j
    var haigo;            //�z��
    var kingaku;          //���z
    var i;                //���[�v�J�E���g
    var j;                //���[�v�J�E���g
    var color = "#ffffff";

// 20160513  KPX@1600766 ADD start
    var txtType= "text";      //�e�L�X�g�{�b�N�X�^�C�v
    if (tankaHyoujiFlg == "0") {
    	//�P���\���s�F���z��\��
    	txtType = "hidden";
    }
//20160513  KPX@1600766 ADD start

    //------------------------------------------------------------------------------------
    //                                    �����ݒ�
    //------------------------------------------------------------------------------------
    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableHaigoNm = "shisaku";

    //�񐔎擾
    cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

    //�s���擾
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //�e�[�u�����擾
    table_size = 175 * cnt_sample;


    //------------------------------------------------------------------------------------
    //                                  �e�[�u������
    //------------------------------------------------------------------------------------
    //�e�[�u������
    OutputHtml += "<table id=\"dataTable2\" name=\"dataTable2\" cellspacing=\"0\" width=\"" + table_size + "px;\">";

    //�E���w�b�_�T�C�Y�w��
    OutputHtml += "<colgroup>";
    for(i = 0; i < cnt_sample; i++){
        OutputHtml += "   <col style=\"width:175px;\"/>";
    }
    OutputHtml += "</colgroup>";

    //�E���w�b�_�e�[�u���ݒ�
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "    <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    for(i = 0; i < cnt_sample; i++){

        //XML�f�[�^�擾
        seq_shisaku = funXmlRead_3(xmlData, tableHaigoNm+i, "seq_shisaku", 0, 1);
        shisakuDate = funXmlRead_3(xmlData, tableHaigoNm+i, "shisakuDate", 0, 1);
        nm_sample = funXmlRead_3(xmlData, tableHaigoNm+i, "nm_sample", 0, 1);

        OutputHtml += "        <th class=\"columntitle\">";
        OutputHtml += "            <table frame=\"void\" width=\"100%\" height=\"50px\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#bbbbbb\">";
        OutputHtml += "                <tr><td align=\"center\" colspan=\"2\" height=\"12px\"><input type=\"checkbox\" name=\"chkInsatu" + i + "\" id=\"chkInsatu" + i + "\" height=\"12px\" value=\"" + seq_shisaku + "\" tabindex=\"19\"/></td></tr>";

        //������t
        if(shisakuDate.length < 10){
            shisakuDate = "0000/00/00";
            color = "#0066FF";
        }
        OutputHtml += "                <tr><td align=\"center\" colspan=\"2\"><font color=\""+ color +"\">" + shisakuDate + "</font></td></tr>";

        //�T���v��No
        OutputHtml += "                <tr>";
        OutputHtml += "                   <td style=\"width:175px;\" align=\"center\" colspan=\"2\">";
        OutputHtml += "                      <input type=\"text\" style=\"border-width:0px;background-color:#0066FF;color:#FFFFFF;text-align:center;\" readonly value=\"" + nm_sample + "\" tabindex=\"-1\" />";

        OutputHtml += "                   </td>";
        OutputHtml += "                </tr>";

    	OutputHtml += "                <tr><td align=\"center\"><font color=\"#ffffff\" style=\"\">�z��(kg)��</font></td><td align=\"center\" style=\"width:45%;\"><font color=\"#ffffff\">���z(�~)</font></td></tr>";
        OutputHtml += "            </table>";
        OutputHtml += "        </th>";
    }
    OutputHtml += "    </tr>";
    OutputHtml += "</thead>";

    //�E�����׃e�[�u���ݒ�
    OutputHtml += "<tbody>";
    OutputHtml += "    <table class=\"detail\" align=\"left\" id=\"tblList2\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\">";

    //�s���[�v
    for(i = 0; i < cnt_genryo; i++){

    	//�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 MOD Start
//    	OutputHtml += "        <tr class=\"disprow\">";
        OutputHtml += "        <tr class=\"disprow\" id=\"tableRowR_" + i + "\" name=\"tableRowR_" + i + "\" >";
        //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 MOD End

        //�񃋁[�v
        for(j = 0; j < cnt_sample; j++){

            //XML�f�[�^�擾
            haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
            kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

            OutputHtml += "            <td class=\"column\" style=\"width:175px;\" align=\"left\">";
            OutputHtml += "                <table frame=\"void\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">";
            OutputHtml += "                    <tr>";
            OutputHtml += "                        <td class=\"dot_r\" style=\"width:55%;text-align:right;\">" + "<input type=\"text\" style=\"width:82px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + haigo + "\" tabindex=\"-1\" />" + "</td>";
// 20160513  KPX@1600766 MOD start
//            OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"text\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
            OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"" + txtType + "\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
// 20160513  KPX@1600766 MOD end
            OutputHtml += "                    </tr>";
            OutputHtml += "                </table>";
            OutputHtml += "            </td>";
        }
        OutputHtml += "        </tr>";
    }

    OutputHtml += "    </table>";
    OutputHtml += "</tbody>";

    OutputHtml += "</table>";


    //------------------------------------------------------------------------------------
    //                                  HTML�o��
    //------------------------------------------------------------------------------------
    //HTML���o��
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
}


//========================================================================================
// �������Z�e�[�u�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�������Z�e�[�u���\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funGenryoShisanDisplay(xmlData, ObjectId) {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var headerFrm = parent.header.document.frm00; //ͯ�ް̫�юQ��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var Kengen = headerFrm.hdnKengen.value;       //�����F�@�\ID
	//�yQP@10713�z20111031 TT H.SHIMA
    var frm = document.frm00;				//�t�H�[���Q��
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableKeisanNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var cnt_genryo;       //�s��
    var cnt_sample;       //��
    var table_size;       //�e�[�u����
    var txtReadonly;      //�e�L�X�g�{�b�N�X���͐ݒ�
    var txtClass;         //�e�L�X�g�{�b�N�X�w�i�F

//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 start
    var fg_KoumokuChk     //���ڌŒ�`�F�b�N
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
    var nm_sanpuru        //�T���v��No
    var seq_shisaku;      //����SEQ
    var shisan_date;      //���Z��
    var jyuuten_suiso;    //�[�U�ʐ����i���j
    var jyuuten_yuso;     //�[�U�ʖ����i���j
    var gookezyuryo;      //���v��
    var hizyu;            //��d
    var yuuko_budomari;   //�L�������i���j
    var reberuryo;        //���x���ʁi���j
    var heikinjyutenryo;  //���Ϗ[�U�ʁi���j
    var hizyukasanryo;    //��d���Z�ʁi���j
    var kesu_genryohi;    //������/�P�[�X
    var kesu_zairyohi;    //�ޗ���/�P�[�X
    var kesu_kotehi;      //�Œ��/�P�[�X
    var kesu_genkake;     //�����v/�P�[�X
    var ko_genkake;       //�����v/��
    var kg_genryohi;      //������/KG
    var kg_zairyohi;      //�ޗ���/KG
    var kg_kotehi;		  //�Œ��/KG
    var kg_genkake;       //�����v/KG
    var baika;            //����
    var arari;            //�e���i���j
    // ADD 2013/11/1 QP@30154 okano start
    var kesu_rieki;       //���v/�P�[�X
    var kg_rieki;         //���v/KG
    // ADD 2013/11/1 QP@30154 okano end

    var i;                //���[�v�J�E���g
    var j;                //���[�v�J�E���g


    //�yQP@00342�z�����擾
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

    //�yQP@00342�z�X�e�[�^�X����
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
    var seizo_han;         //�����H����
    var seizo_shosai;      //�����H��
    var seq_shisaku_seizo; //����SEQ
//mod end --------------------------------------------------------------------------------

//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 Start
    var arrShisaku_seq = new Array();
    var saiyo_column;
    saiyo_column = headerFrm.hdnSaiyou_column.value;
//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 End
    // ADD 2013/11/1 QP@30154 okano start
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // ADD 2013/11/1 QP@30154 okano end
// 20160513  KPX@1600766 ADD start
    var txtType = "text";      //�e�L�X�g�{�b�N�X�^�C�v
// 20160513  KPX@1600766 ADD end

    //------------------------------------------------------------------------------------
    //                                    �����ݒ�
    //------------------------------------------------------------------------------------
    //�yQP@00342�z�r��
    if(headerFrm.strKengenMoto.value == "999"){
    	txtReadonly = "readonly";
        txtClass = henshuNgClass;
    }
    else{
    	//�ҏW����
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	    	//�yQP@00342�z�ҏW�\�i���Y�Ǘ����A�H��j
	    	if(seikan == "1" || kojo == "1"){
	    		txtReadonly = "";
	       		txtClass = henshuOkClass;

	       		//�yQP@00342�z�H��Ŋm�F�����̏ꍇ
	       		/*
				 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
				 * txtReadonly = "readonly"; txtClass = henshuNgClass; }
				 */

	       		//20160607 �yKPX@1502111_No8�z ADD start
	        	// �����F�H��Ŋm�F����(�H��X�e�[�^�X��2)�̏ꍇ
		        // �����F���ǂ͐��Y�Ǘ����X�e�[�^�X �� 3
				if( kojo == "1" && st_kojo >= 2 ){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
				//20160607 �yKPX@1502111_No8�z ADD end

	       		//�yQP@00342�z���Y�Ǘ����X�e�[�^�X >= 3 or �c�ƃX�e�[�^�X >= 4�@�̏ꍇ
				if( st_seikan >= 3 || st_eigyo >= 4){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
	    	}
	    	else{
	    		txtReadonly = "readonly";
		        txtClass = henshuNgClass;
// 20160513  KPX@1600766 ADD start
		        //�ҏW�s��
		        if (frm.radioKoteihi != null) {
		        	frm.radioKoteihi[0].disabled = true;
		        	frm.radioKoteihi[1].disabled = true;
		        }
// 20160513  KPX@1600766 ADD end
// 20160622  KPX@1502111_No.10 ADD start
		        //�T���v���R�s�[�{�^���̌����ҏW
		        detailDoc.frm00.btnSampleCopy.disabled = true;
// 20160622  KPX@1502111_No.10 ADD end
		    }


	    }
	    //�yQP@00342�z�{��+Excel����
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        txtReadonly = "readonly";
	        txtClass = henshuNgClass;
	    }
    }

// 20160513  KPX@1600766 ADD start
    //�������F�P���\�����ׂĈȊO�͕ҏW�s��
   	if (tankaHyoujiFlg == "1" ) {
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
    } else if (tankaHyoujiFlg == "0") {
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        //�P����\��
        txtType = "hidden";
    }
//20160513  KPX@1600766 ADD end


    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableKeisanNm = "keisan";

    //�񐔎擾
    cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

    //�s���擾
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //�e�[�u�����擾
    table_size = 175 * cnt_sample;

	// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
    //�����
	//OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";
	// DEL 2013/7/2 shima�yQP@30151�zNo.37 end
    //����SEQ
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        seq_shisaku = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);

        //����SEQ
        OutputHtml += "<input type=\"hidden\"  id=\"hdnSeq_Shisaku_" + i + "\" name=\"hdnSeq_Shisaku_" + i + "\" value=\"" + seq_shisaku + "\">";

        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 Start
        arrShisaku_seq[i] = seq_shisaku;
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 End
    }

    //------------------------------------------------------------------------------------
    //                                  �e�[�u������
    //------------------------------------------------------------------------------------
    OutputHtml += "<table class=\"detail\" id=\"tblList3\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\" bordercolordark=\"#000000\">";

//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
    OutputHtml += "    <tr>";
    var seikanchkBuf="";
	//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
    //���ڌŒ�`�F�b�N�͔r���@or�@���Y�Ǘ��łȂ��ꍇ�A����s�\�Ƃ���i�X�e�[�^�X�ɂ�鐧��͍s��Ȃ��j
    if((seikan != 1) || (headerFrm.strKengenMoto.value == "999")){
    	seikanchkBuf = "disabled=\"disabled\"";
    }
	//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
        //HTML����
        OutputHtml += "        <td align=\"center\" style=\"width:175px;height:18px\" >";
        //�yQP@40812�zNo.30 MOD start 2015/03/03 TT.Kitazawa
        //  �Œ�`�F�b�N�������݂̂������A�Čv�Z�����Ȃ��Ă�OK
        if(fg_KoumokuChk == "1"){
//            OutputHtml += "            <input type=\"checkbox\" onclick=\"setFg_saikeisan(" + i +");funKChangeMode(" + i + ")\" name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " checked=\"checked\" />";
            OutputHtml += "            <input type=\"checkbox\" onclick=\"funKChangeMode(" + i + ")\" name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " checked=\"checked\" />";
        }else{
            OutputHtml += "            <input type=\"checkbox\" onclick=\"setFg_saikeisan(" + i +");funKChangeMode(" + i + ")\"   name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " />";
        }
        //�yQP@40812�zNo.30 MOD end 2015/03/03 TT.Kitazawa
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 end
    //�T���v��No
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        nm_sanpuru = funXmlRead_3(xmlData, tableKeisanNm, "nm_sanpuru", 0, i);

        //HTML����
        OutputHtml += "        <td style=\"width:175px;height:18px;\">";

        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "          <input type=\"text\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"txtSample" + i + "\" readonly style=\"width:100%;border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
    //�����H����
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        seizo_han = funXmlRead_3(xmlData, tableKeisanNm, "seizokotei_han", 0, i);
        seizo_shosai = funXmlRead_3(xmlData, tableKeisanNm, "seizokotei_shosai", 0, i);
        seq_shisaku_seizo = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);
        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";

        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "          <input type=\"text\"  id=\"txtSeizoHan_" + i + "\" name=\"txtSeizoHan_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + seizo_han + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\"  id=\"txtSeizoHan_" + i + "\" name=\"txtSeizoHan_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + seizo_han + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"hidden\"  id=\"hdnSeizoShosai_" + i + "\" name=\"hdnSeizoShosai_" + i + "\" value=\"" + seizo_shosai + "\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
//mod end --------------------------------------------------------------------------------

    //���Z��
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){

        //XML�f�[�^�擾
        shisan_date = funXmlRead_3(xmlData, tableKeisanNm, "shisan_date", 0, i);
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);

        //�yQP@00342�z�ҏW�\�i�H��j
        var txtReadonly_sisanbi = "readonly";
	    var txtClass_sisanbi = henshuNgClass;

        /*
		 * if(kojo == "1" && st_kojo != "2" && st_eigyo != "4"){
		 * txtReadonly_sisanbi = ""; txtClass_sisanbi = henshuOkClass; }
		 */

	    //�yH24�N�x�Ή��zNo.11 Start
        /*
		 * if(kojo == "1" && st_seikan != "3" && st_eigyo != "4"){
		 * txtReadonly_sisanbi = ""; txtClass_sisanbi = henshuOkClass; }
		 */
	    //�yH24�N�x�Ή��zNo.11 End

        //�yQP@00342�z���Z���~
        var val = "";
        var tab = 20;
        if(fg_chusi == "1"){
        	val = "���Z���~";
        	txtReadonly_sisanbi = "readonly";
	    	txtClass_sisanbi = henshuNgClass;
	    	tab = -1;
        }
        else{
        	val = shisan_date;
        }

        //HTML����
        OutputHtml += "        <td style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" onblur=\"hidukeSetting(this)\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" />";
        //�yH24�N�x�Ή��zNo.11 Start
//        OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" onblur=\"hidukeSetting(this)\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" onFocus = \"funLineHighLight(this)\" />";
        //�yH24�N�x�Ή��zNo.11 End
        OutputHtml += "            <input type=\"hidden\" id=\"hdnSisanChusi_" + i + "\" name=\"hdnSisanChusi_" + i + "\" value=\"" + fg_chusi + "\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�[�U�ʐ����i���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        jyuuten_suiso = funXmlRead_3(xmlData, tableKeisanNm, "jyuuten_suiso", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtSuiZyuten_" + i + "\" name=\"txtSuiZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_suiso + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtSuiZyuten_" + i + "\" name=\"txtSuiZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_suiso + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�[�U�ʖ����i���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        jyuuten_yuso = funXmlRead_3(xmlData, tableKeisanNm, "jyuuten_yuso", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtYuZyuten_" + i + "\" name=\"txtYuZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_yuso + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtYuZyuten_" + i + "\" name=\"txtYuZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_yuso + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end

        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //���v��
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        gookezyuryo = funXmlRead_3(xmlData, tableKeisanNm, "gookezyuryo", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ gookezyuryo + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"total" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ gookezyuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //��d
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        hizyu = funXmlRead_3(xmlData, tableKeisanNm, "hizyu", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyu + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"hijyu" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyu + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�L�������i���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//�yH24�N�x�Ή�No15�z2012/04/16 kazama mod start
    	//xml�f�[�^�擾
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod start
    	//���Z���~
//    	if(fg_chusi == "1"){
//    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod end
//�C����\�[�X
       	//XML�f�[�^�擾
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);

// 20160513  KPX@1600766 MOD start
    	if(fg_KoumokuChk == "1"){
     		//XML�f�[�^�擾
    		yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
     		//HTML����
     		OutputHtml += "        <td style=\"width:175px;height:18px;\">";
//     		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" readonly tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
     		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" readonly tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
     		OutputHtml += "        </td>";
         }else if(fg_chusi == "1" && txtClass == henshuOkClass){
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end

    		//XML�f�[�^�擾
    		yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
    		//HTML����
    		OutputHtml += "        <td style=\"height:18px;\">";
//    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
    		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
    		OutputHtml += "        </td>";
    		if(fg_chusi[i+1] != 1){
    			// ���Z���~�ݒ�̏I��
    			fg_chusi = 0;
    		}
    	}else{
	        //XML�f�[�^�擾
	        yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
	        //HTML����
	        OutputHtml += "        <td style=\"height:18px;\">";
	        //�yQP@00342�z
	        // �yQP@10713�z20111101 hagiwara mod start
	        //OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" />";
//	        OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" />";
	        OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" />";
	        // �yQP@10713�z20111101 hagiwara mod end
	        OutputHtml += "        </td>";
    	}
    	//�yH24�N�x�Ή��z2012/04/16 kazama mod end
// 20160513  KPX@1600766 MOD end
    }
    OutputHtml += "    </tr>";



    //���x���ʁi���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        reberuryo = funXmlRead_3(xmlData, tableKeisanNm, "reberuryo", 0, i);

        //HTML����
        OutputHtml += "        <td style=\"height:18px;\">";


        // �yQP@10713�z20111101 hagiwara mod start
// 20160513  KPX@1600766 MOD start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" />";
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end

        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //���Ϗ[�U�ʁi���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//�yH24�N�x�Ή��z2012/04/16 kazama mod start
    	//xml�f�[�^�擾
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);

//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod start
    	//���Z���~
//    	if(fg_chusi == "1"){
//    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod end
//�C����\�[�X
       	//XML�f�[�^�擾
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);

// 20160513  KPX@1600766 MOD start
        if(fg_KoumokuChk == "1"){
            //XML�f�[�^�擾
            heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

            //HTML����
            OutputHtml += "        <td style=\"width:175px;height:18px;\">";
//            OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" readonly tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
            OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" readonly tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
            OutputHtml += "        </td>";

         }else if(fg_chusi == "1" && txtClass == henshuOkClass){
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end

    		//XML�f�[�^�擾
    		heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

    		//HTML����
    		OutputHtml += "        <td style=\"height:18px;\">";
//    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
    		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
    		OutputHtml += "        </td>";

    	}else{
	        //XML�f�[�^�擾
	        heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

	        //HTML����
	        OutputHtml += "        <td style=\"height:18px;\">";
	        //�yQP@00342�z
	        // �yQP@10713�z20111101 hagiwara mod start
	        //OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" />";
//	        OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
	        OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
	        // �yQP@10713�z20111101 hagiwara mod end
	        OutputHtml += "        </td>";
    	}
// 20160513  KPX@1600766 MOD end
    	//�yH24�N�x�Ή��z2012/04/16 kazama mod end
    }
    OutputHtml += "    </tr>";



    //��d���Z�ʁi���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        hizyukasanryo = funXmlRead_3(xmlData, tableKeisanNm, "hizyukasanryo", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"hijyuKasan" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"hijyuKasan" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //������/�P�[�X
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kesu_genryohi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genryohi", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"genryohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"genryohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //�ޗ���`���v�^�P�[�X�܂Ŕ�\��
        txtType = "hidden";
    }
// 20160513  KPX@1600766 ADD end

    //�ޗ���/�P�[�X
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kesu_zairyohi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_zairyohi", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"zairyohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"zairyohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
//20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


    //�Œ��/�P�[�X
	OutputHtml += "    <tr>";
	for(i = 0; i < cnt_sample; i++){
		//�yH24�N�x�Ή��z2012/04/16 kazama mod start
		//xml�f�[�^�擾
		var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
        //XML�f�[�^�擾
		fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N

// 20160513  KPX@1600766 MOD start
		//XML�f�[�^�擾
		kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

      //�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod start
    	//���Z���~
//    	if(fg_chusi == "1"){
    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod end

    		//XML�f�[�^�擾
//			kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

			//HTML����
			OutputHtml += "        <td style=\"height:18px;\">";

			if(parent.detail.document.frm00.radioKoteihi[0].checked){
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
//�C����\�[�X
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N
			}else{
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}

			OutputHtml += "        </td>";

		}else{
    		//XML�f�[�^�擾
//			kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

			//HTML����
			OutputHtml += "        <td style=\"height:18px;\">";

//�yQP@10713�z2011/11/14 TT H.SHIMA -MOD Start
			// �yQP@10713�z20111117 hagiwara mod start
			if(parent.detail.document.frm00.radioKoteihi[0].checked){
			// �yQP@10713�z20111117 hagiwara mod end
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
				//�yQP@00342�z
				// �yQP@10713�z20111031 hagiwara mod start
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
				// �yQP@10713�z20111031 hagiwara mod end
//�C����\�[�X
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N

			}else{
				// �yQP@10713�z20111031 hagiwara mod start
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				// �yQP@10713�z20111031 hagiwara mod end
			}
			OutputHtml += "        </td>";
		}
		//�yH24�N�x�Ή��z2012/04/16 kazama mod end
//20160513  KPX@1600766 MOD end



	// ADD 2013/11/1 okano�yQP@30154�zstart
	}
	OutputHtml += "    </tr>";

    //���v/�P�[�X
	OutputHtml += "    <tr>";
	for(i = 0; i < cnt_sample; i++){
		//xml�f�[�^�擾
		var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        //XML�f�[�^�擾
		fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
		//XML�f�[�^�擾
		kesu_rieki = funXmlRead_3(xmlData, tableKeisanNm, "kesu_rieki", 0, i);

		//HTML����
		OutputHtml += "        <td style=\"height:18px;\">";

		// MOD 2013/12/24 QP@30154 okano start
//			if(kojo != "1"){
// 20160513  KPX@1600766 MOD start
		if(cd_kaisha == "1"){
			if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
					if(fg_KoumokuChk == "1"){
//						OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					}else{
						if(txtClass == henshuOkClass){
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}else{
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}
					}
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
		}else{
		// MOD 2013/12/24 QP@30154 okano end
	    	//���Z���~
	    	if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }

				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
//20160513  KPX@1600766 MOD end
		// DEL 2013/12/24 QP@30154 okano start
//			} else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
		// DEL 2013/12/24 QP@30154 okano end
		}
		OutputHtml += "        </td>";

	}
	OutputHtml += "    </tr>";
	// ADD 2013/11/1 okano�yQP@30154�zend



	//�yH24�N�x�Ή��z2012/04/16 kazama add start
//	shisan_fg = shisan_fg2		//���Z���~�t���O�Đ�
	//�yH24�N�x�Ή��z2012/04/16 kazama add end

    //�����v/�P�[�X
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kesu_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genkake", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�s�Q�l�F������t
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sankouKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�����v/��
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        ko_genkake = funXmlRead_3(xmlData, tableKeisanNm, "ko_genkake", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�s�Q�l�FKG������t
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sankouKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //������/KG �\��
        txtType = "text";
    }
// 20160513  KPX@1600766 ADD end

    //������/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kg_genryohi = funXmlRead_3(xmlData, tableKeisanNm, "kg_genryohi", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"genryohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"genryohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //�ޗ���`���v�^KG�܂Ŕ�\��
        txtType = "hidden";
    }
// 20160513  KPX@1600766 ADD end

    //�ޗ���/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kg_zairyohi = funXmlRead_3(xmlData, tableKeisanNm, "kg_zairyohi", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"zairyohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"zairyohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
//20160513  KPX@1600766 MOD end
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�Œ��/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//�yH24�N�x�Ή��z2012/04/19 kazama mod start
    	//xml�f�[�^�擾
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
        //XML�f�[�^�擾
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
//ADD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N

// 20160513  KPX@1600766 MOD start
		kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

		//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod start
    	//���Z���~
//    	if(fg_chusi == "1"){
    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//�yH24�N�x�Ή�No15�z2012/05/08 TT H.SHIMA mod end

			//XML�f�[�^�擾
//    		kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

			//HTML����
			OutputHtml += "        <td style=\"height:18px;\">";

			if(!parent.detail.document.frm00.radioKoteihi[0].checked){
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
//�C����\�[�X
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N
			}else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}

		}else{
			//XML�f�[�^�擾
//			kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

			//HTML����
			OutputHtml += "        <td style=\"height:18px;\">";

	//�yQP@10713�z2011/10/21 TT H.SHIMA -MOD Start
			// �yQP@10713�z20111117 hagiwara mod start
			if(!parent.detail.document.frm00.radioKoteihi[0].checked){
				// �yQP@10713�z20111117 hagiwara mod end
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
//�C���O�\�[�X
				//�yQP@00342�z
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
//�C����\�[�X
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa �yQP@30151�zNo.13 end    //���ڌŒ�`�F�b�N
			}else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}
		}
 //20160513  KPX@1600766 MOD end
       //�yH24�N�x�Ή��z2012/04/19 kazama mod end
        OutputHtml += "        </td>";
    //�yQP@10713�z2011/10/21 TT H.SHIMA -MOD End
    }
    OutputHtml += "    </tr>";



	// ADD 2013/11/1 okano�yQP@30154�zstart
    //���v/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//xml�f�[�^�擾
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        //XML�f�[�^�擾
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
		//XML�f�[�^�擾
		kg_rieki = funXmlRead_3(xmlData, tableKeisanNm, "kg_rieki", 0, i);


		//HTML����
		OutputHtml += "        <td style=\"height:18px;\">";
		// MOD 2013/12/24 QP@30154 okano start
//	    	if(kojo != "1"){
		if(cd_kaisha == "1"){
			if(fg_chusi == "1" && txtClass == henshuOkClass){

// 20160513  KPX@1600766 MOD start
				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
					if(fg_KoumokuChk == "1"){
//						OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					}else{
						if(txtClass == henshuOkClass){
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}else{
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}
					}
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
		}else{
		// MOD 2013/12/24 QP@30154 okano end
	    	//���Z���~
	    	if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
//20160513  KPX@1600766 MOD end
		// DEL 2013/12/24 QP@30154 okano start
//	    	} else {
//	    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
		// DEL 2013/12/24 QP@30154 okano end
    	}
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
	// ADD 2013/11/1 okano�yQP@30154�zend



    //�����v/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kg_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kg_genkake", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //����
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        baika = funXmlRead_3(xmlData, tableKeisanNm, "baika", 0, i);

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //�e���i���j
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        arari = funXmlRead_3(xmlData, tableKeisanNm, "arari", 0, i);
        if(arari == ""){
        }
        else{
        	arari = arari + "�@��";
        }

        //HTML����
        OutputHtml += "        <td  style=\"height:18px;\">";
        // �yQP@10713�z20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sori" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // �yQP@10713�z20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    OutputHtml += "</table>";


    //------------------------------------------------------------------------------------
    //                                  HTML�o��
    //------------------------------------------------------------------------------------
    //HTML���o��
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 Start
    if(saiyo_column > 0){
    	funSaiyoDisp(saiyo_column,arrShisaku_seq,cnt_sample);
    }
	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/13 End

    return true;
}


//========================================================================================
// ���ރe�[�u�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���ރe�[�u���\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funShizaiDisplay(xmlData, ObjectId) {

    var headerFrm = parent.header.document.frm00; //ͯ�ް̫�юQ��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var detailFrm = parent.detail.document.frm00; //�����ڰт�̫�юQ��
    var Kengen = headerFrm.hdnKengen.value;       //�����F�@�\ID
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableShizaiNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var chkDisabled;      //�`�F�b�N�{�b�N�X���͐ݒ�
    var txtReadonly;      //�e�L�X�g�{�b�N�X���͐ݒ�
    var txtClass;         //�e�L�X�g�{�b�N�X�w�i
    var objColor;         //�I�u�W�F�N�g�w�i�F

    var cnt_genryo;       //�s��
    var goke_shizai;      //���ދ��z���v
    var seq_shizai;       //����SEQ
    var cd_kaisya;        //���CD
    var cd_kojyo;         //�H��CD
    var kigo_kojyo;       //�H��L��
    var cd_shizai;        //����CD
    var nm_shizai;        //���ޖ�
    var tanka;            //�P��
    var budomari;         //�����i���j
    var shiyouryo;        //�g�p��/���
    var kei_kingaku;      //���z�v
    var id_toroku;        //�o�^��ID
    var dt_toroku;        //�o�^��

    var i;                //���[�v�J�E���g

    //�yQP@00342�z�X�e�[�^�X����
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//�yQP@00342�z�����擾
	var kenkyu = headerFrm.hdnBusho_kenkyu.value;
	var seikan = headerFrm.hdnBusho_seikan.value;
	var gentyo = headerFrm.hdnBusho_gentyo.value;
	var kojo = headerFrm.hdnBusho_kojo.value;
	var eigyo = headerFrm.hdnBusho_eigyo.value;
// 20160513  KPX@1600766 ADD start
    var txtType = "text";      //�e�L�X�g�{�b�N�X�^�C�v
// 20160513  KPX@1600766 ADD end

    //�yQP@00342�z�r��
    if(headerFrm.strKengenMoto.value == "999"){
    	chkDisabled = "disabled";
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        objColor = henshuNgColor;
    }
    else{
    	//�yQP@00342�z�ҏW����
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	        chkDisabled = "";
	        txtReadonly = "";
	        txtClass = henshuOkClass;
	        objColor = henshuOkColor;

	        //�yQP@00342�z�����ޒ��B���Ŋm�F�����̏ꍇ
			/*
			 * if( gentyo == "1" && st_gentyo == 2){ chkDisabled = "disabled";
			 * txtReadonly = "readonly"; txtClass = henshuNgClass; objColor =
			 * henshuNgColor; }
			 */

	        //�yQP@00342�z�H��Ŋm�F�����̏ꍇ
			/*
			 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
			 * txtReadonly = "readonly"; txtClass = henshuNgClass; objColor =
			 * henshuNgColor; }
			 */

	        //20160607 �yKPX@1502111_No8�z ADD start
	        // �����F�H��Ŋm�F����(�H��X�e�[�^�X��2)�̏ꍇ
	        // �����F���ǂ͐��Y�Ǘ����X�e�[�^�X �� 3
			if( kojo == "1" && st_kojo >= 2 ){
				chkDisabled = "disabled";
		        txtReadonly = "readonly";
		        txtClass = henshuNgClass;
		        objColor = henshuNgColor;
			}
	        //20160607 �yKPX@1502111_No8�z ADD end

	        //�yQP@00342�z���Y�Ǘ����X�e�[�^�X >= 3 or �c�ƃX�e�[�^�X >= 4�@�̏ꍇ
			if( st_seikan >= 3 || st_eigyo >= 4){
				chkDisabled = "disabled";
		        txtReadonly = "readonly";
		        txtClass = henshuNgClass;
		        objColor = henshuNgColor;
			}

	    }
	    //�yQP@00342�z�{��+Excel����
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        chkDisabled = "disabled";
	        txtReadonly = "readonly";
	        txtClass = henshuNgClass;
	        objColor = henshuNgColor;
	    }
    }

 // 20160513  KPX@1600766 ADD start
    //�������F�P���\�����ׂĈȊO�͕ҏW�s��
    if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0") {
        chkDisabled = "disabled";
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        objColor = henshuNgColor;
        //�P���E������ ��\��
        txtType = "hidden";
    }
//20160513  KPX@1600766 ADD end

    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableShizaiNm = "shizai";

    //�s���擾
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_shizai", 0, 0);
    detailFrm.hdnShizaiCount.value = cnt_genryo;

    goke_shizai = funXmlRead_3(xmlData, tablekihonNm, "goke_shizai", 0, 0);
// 20160513  KPX@1600766 ADD start
	if (txtType == "hidden") {
		goke_shizai = "&nbsp;";
	}
//20160513  KPX@1600766 ADD end

	//���ތv�\��
    OutputHtml += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#888888\" width=\"975\" height=\"16px\">";
    OutputHtml += "    <tr><td width=\"800\">���ތv</td>";
    OutputHtml += "    <td align=\"right\"><label name=\"lblShizaikei\" id=\"lblShizaikei\">" + goke_shizai + "</label></td></tr>";
    OutputHtml += "</table>";


    //���ލs���ݒ�
    OutputHtml += "<input type=\"hidden\" id=\"cnt_shizai\" name=\"cnt_shizai\" value=\"" + cnt_genryo + "\">";

    //�o��HTML�ݒ�
    OutputHtml += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"970px\" height=\"400px\">";
    OutputHtml += "<tr>";
    OutputHtml += "    <td colspan=\"2\" valign=\"top\">";
    OutputHtml += "        <div class=\"scroll_genka\" id=\"sclList4\" style=\"width:975px;height:97%;overflow-x:hidden;\" rowSelect=\"true\" />";
    OutputHtml += "        <table id=\"dataTable4\" name=\"dataTable4\" cellspacing=\"0\" width=\"955px;\" align=\"center\">";
    OutputHtml += "            <colgroup>";
    OutputHtml += "                <col style=\"width:50px;\"/>";
    OutputHtml += "                <col style=\"width:50px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "                <col style=\"width:355px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "                <col style=\"width:80px;\"/>";
    OutputHtml += "                <col style=\"width:120px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "            </colgroup>";
    OutputHtml += "            <thead class=\"rowtitle\">";
    OutputHtml += "                <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "                    <th class=\"columntitle\">�I��</th>";
    OutputHtml += "                    <th class=\"columntitle\">�H��</th>";
    OutputHtml += "                    <th class=\"columntitle\">���ރR�[�h</th>";
    OutputHtml += "                    <th class=\"columntitle\">���ޖ�</th>";
    OutputHtml += "                    <th class=\"columntitle\">�P����</th>";
    OutputHtml += "                    <th class=\"columntitle\">����(%)��</th>";
    OutputHtml += "                    <th class=\"columntitle\">�g�p��/�����</th>";
    OutputHtml += "                    <th class=\"columntitle\">���z</th>";
    OutputHtml += "                </tr>";
    OutputHtml += "            </thead>";
    OutputHtml += "            <tbody>";
    OutputHtml += "                <table class=\"detail\" id=\"tblList4\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:956px;display:list-item\">";

    //���ޏ��\�����[�v
    for( i = 0; i < cnt_genryo; i++ ){

        //XML�f�[�^�擾
        seq_shizai = funXmlRead_3(xmlData, tableShizaiNm, "seq_shizai", 0, i);
        cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, i);
        cd_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyo", 0, i);
        kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, i);
        cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, i);
        nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, i);
        tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, i);
        shiyouryo = funXmlRead_3(xmlData, tableShizaiNm, "shiyouryo", 0, i);
        kei_kingaku = funXmlRead_3(xmlData, tableShizaiNm, "kei_kingaku", 0, i);
// 20160513  KPX@1600766 ADD start
        if (txtType == "hidden") {
            kei_kingaku =  "&nbsp;";
        }
//20160513  KPX@1600766 ADD end

        id_toroku = funXmlRead_3(xmlData, tableShizaiNm, "id_toroku", 0, i);
        dt_toroku = funXmlRead_3(xmlData, tableShizaiNm, "dt_toroku", 0, i);


        OutputHtml += "                    <tr class=\"disprow\">";

        //�I��
        OutputHtml += "                        <td bgcolor=\"" + objColor + "\" class=\"column\" width=\"50px\" align=\"center\">";
        OutputHtml += "                            <input name=\"chkShizaiGyo\" type=\"checkbox\" " + chkDisabled + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //�H��L��
        OutputHtml += "                        <td class=\"column\" width=\"50px\" align=\"center\">";
        OutputHtml += "                            <input type=\"text\"    id=\"txtKigouKojo\"     name=\"txtKigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\""+ kigo_kojyo + "\" tabindex=\"-1\" />";
        //��ЃR�[�h
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnKaisha_Shizai\" name=\"hdnKaisha_Shizai\" value=\"" + cd_kaisya + "\" />";
        //�H��R�[�h
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnKojo_Shizai\"   name=\"hdnKojo_Shizai\" value=\"" + cd_kojyo + "\" />";
        //�o�^��ID
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnId_toroku\"   name=\"hdnId_toroku\" value=\"" + id_toroku + "\" />";
        //�o�^��
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnDt_toroku\"   name=\"hdnDt_toroku\" value=\"" + dt_toroku + "\" />";
        OutputHtml += "                        </td>";

        //���ރR�[�h
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"center\">";
        //�yQP@00342�z���ރe�[�u���C��
        //OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funShizaiSearch()\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"" + txtClass + "\" style=\"text-align:center\" value=\"" + cd_shizai + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funChangeSelectRowColor3(this);funShizaiSearch();\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"" + txtClass + "\" style=\"text-align:center\" value=\"" + cd_shizai + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";

        //���ޖ�
        OutputHtml += "                        <td class=\"column\" width=\"353px\" align=\"left\">";
        OutputHtml += "                            <input type=\"text\" style=\"ime-mode:active;text-align:left\" id=\"txtNmShizai\" name=\"txtNmShizai\" class=\"" + txtClass + "\" value=\"" + nm_shizai + "\"  " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";

// 20160513  KPX@1600766 MOD start
        //�P��
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"right\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //����
        OutputHtml += "                        <td class=\"column\" width=\"80px\" align=\"right\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //�g�p��/�P�[�X
        OutputHtml += "                        <td class=\"column\" width=\"120px\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + shiyouryo + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + shiyouryo + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
// 20160513  KPX@1600766 MOD end
        //���z
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"right\">" + kei_kingaku + "</td>";

        OutputHtml += "                    </tr>";
    }

    OutputHtml += "                </table>";
    OutputHtml += "            </tbody>";
    OutputHtml += "        </table>";
    OutputHtml += "    </td>";
    OutputHtml += "</tr>";
    OutputHtml += "</table>";

    //HTML���o��
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
}

//========================================================================================
// �X�e�[�^�X�N���A��ʗp�̍��ڂ�ϐ��ɕێ�
// �쐬�ҁFHisahori
// �쐬���F2012/06/15
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �Fhidden���ڂɁA�T���v��NO�̃L�[�A�T���v��NO�A���Z���A���Z���~�t���O���Z�b�g����
//========================================================================================
function funSetDtShisan() {
	var frm = document.frm00;				//�t�H�[���Q��
    var detailDoc = parent.detail.document;
	var cntroop = frm.cnt_sample.value;
	var setSeqShisaku = "";
	var setSampleNo = "";
	var setShisanHi = "";
	var setShisanChushi = "";
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	var setChkKoumoku = "";					//���ڌŒ�`�F�b�N
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

	for(var m = 0; m < cntroop; m++){
		if (m == 0){
			setSeqShisaku = setSeqShisaku + detailDoc.getElementById("hdnSeq_Shisaku_" + m).value;
		} else {
			setSeqShisaku = setSeqShisaku + "," + detailDoc.getElementById("hdnSeq_Shisaku_" + m).value;
		}
	}
	for(var i = 0; i < cntroop; i++){
		if (i == 0){
			setSampleNo = setSampleNo + detailDoc.getElementById("txtSample" + i).value;
		} else {
			setSampleNo = setSampleNo + "," + detailDoc.getElementById("txtSample" + i).value;
		}
	}
	for(var j = 0; j < cntroop; j++){
		if (j == 0){
			setShisanHi = setShisanHi + detailDoc.getElementById("txtShisanHi_" + j).value;
		} else {
			setShisanHi = setShisanHi + "," + detailDoc.getElementById("txtShisanHi_" + j).value;
		}
	}
	for(var k = 0; k < cntroop; k++){
		if (k == 0){
			setShisanChushi = setShisanChushi + detailDoc.getElementById("hdnSisanChusi_" + k).value;
		} else {
			setShisanChushi = setShisanChushi + "," + detailDoc.getElementById("hdnSisanChusi_" + k).value;
		}
	}
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	//���ڌŒ�`�F�b�N
	for(var k = 0; k < cntroop; k++){
		if (k == 0){
			setChkKoumoku = setChkKoumoku + detailDoc.getElementById("chkKoumoku_" + k).value;
		} else {
			setChkKoumoku = setChkKoumoku + "," + detailDoc.getElementById("chkKoumoku_" + k).value;
		}
	}
	frm.hidsetChkKoumoku.value = setChkKoumoku;
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

	frm.hidsetSeqShisaku.value = setSeqShisaku;
	frm.hidsetSampleNo.value = setSampleNo;
	frm.hidsetShisanHi.value = setShisanHi;
	frm.hidsetShisanChushi.value = setShisanChushi;
	return true;
}


//========================================================================================
// �o�^�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�Ȃ�
// �T�v  �F�����A���ޏ��̓o�^���s��
//========================================================================================
function funToroku() {

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��
    var retVal;

    //�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

	//�yQP@00342�z�����擾
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

    //�yQP@00342�z�����擾
    var Kengen = headerFrm.hdnKengen.value;

    //�yQP@00342�z�ҏW����
	if(Kengen.toString() == ConFuncIdEdit.toString()){
		//�yQP@00342�z�X�e�[�^�X�ύX�_�C�A���O�\��
	    args = new Array();
    	args[0] = headerDoc;
		args[1] = detailDoc;

	    //�yQP@00342�z�X�e�[�^�X�ύX��ʂ��N������
		// ����I���̏ꍇ�A�I��׼޵���ݒl���߂�
	    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Status.jsp", args, "dialogHeight:350px;dialogWidth:500px;status:no;scroll:no");

	    //�yQP@00342�z�I���{�^������
	    if(retVal == "false"){
	    	//���ۑ��X�e�[�^�X��ݒ�
	    	headerFrm.hdnInsStatus.value = "0";

	    	return true;
	    }
	    //�yQP@00342�z�X�e�[�^�X�N���A
	    else if(retVal == "Clear"){

	    	//�ĕ\��
	        funGetKyotuInfo(1);

	    }
		//�yQP@00342�z�X�e�[�^�X���I������Ă���ꍇ
	    else{
	    	//�I���X�e�[�^�X��ޔ�
		    headerFrm.hdnInsStatus.value = retVal;

//20160617  KPX@502111_No.5 ADD start
		    // �H��X�e�[�^�X�F������
		    if (kojo == 1 && retVal == 2) {
		    	// �A�g�掩�ƌ����̉c�ƃX�e�[�^�X�m�F�����O�̎�
		    	//�i�X�e�[�^�X�l���Z�b�g�A���c��3�̎� ""�j
		    	if (headerFrm.hdnRenkeiStatus_eigyo.value > 0) {
		            //�G���[�\��
		            funErrorMsgBox(E000042);
		            return false;
		    	}
		    }
		    // ���ǃX�e�[�^�X�F������
		    if (seikan == 1 && retVal == 2) {
		    	// �A�g�掩�ƌ����̉c�ƃX�e�[�^�X�m�F�����O�̎�
		    	if (headerFrm.hdnRenkeiStatus_eigyo.value > 0) {
		    		//�G���[�\��
		            funErrorMsgBox(E000042);
		            return false;
		    	}
		    }
//20160617  KPX@502111_No.5 ADD end

		    //�o�^����
	    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 start
		    // �I���X�e�[�^�X��n��
//		    if(funTorokuInfo(1)){
		    if(funTorokuInfo(1, retVal)){
	    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 end

		    	//�o�^�������ɍĕ\�����s��
		    	funGetKyotuInfo(1);

		    }
	    }
	}
	//�yQP@00342�z�{��+Excel����
	else if(Kengen.toString() == ConFuncIdReadExcel.toString()){

		//�o�^����
    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 start
//	    funTorokuInfo(1);
	    funTorokuInfo(1, 0);
    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 end
	}

    return true;

}

//========================================================================================
// �o�^����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
//         �Aval   �F�I��ԍ�
// �T�v  �F�����A���ޏ��̓o�^���s��
//========================================================================================
function funTorokuInfo(mode, val) {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN0041";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0040");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0040I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0040O );

    //------------------------------------------------------------------------------------
    //                               �v�Z���e�̕ύX�m�F
    //------------------------------------------------------------------------------------
    //�Čv�Z���s���Ă��Ȃ��ꍇ
    if(frm.FgSaikeisan.value == "true"){
        //�G���[���b�Z�[�W��\��
        funErrorMsgBox(E000006);
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                      �o�^
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0040I.src = '../../model/FGEN0040I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0041, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     ���ʕ\��
    //------------------------------------------------------------------------------------
    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 start
        if(val > 0){
        	// ���[���N���m�F���b�Z�[�W�ɕύX�F�uYES�v�̎����[���N��
        	// ���[���N���O�ɁA�X�V��X�e�[�^�X���擾����
    		if(funConfMsgBox(dspMsg) == ConBtnYes){
    			funMail();
    		}
    	} else {
    		//����
    		funInfoMsgBox(dspMsg);
    	}
        // MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 end
        //�����I��
	    return true;
    }
    else{
    	return false;
    }
}

// ADD start 20140919
// ========================================================================================
// �o�^����
// �쐬�ҁFhisahori
// �쐬���F2014/09/19
// ���� �F
// �T�v �F���ڌŒ�`�F�b�N�Ώۂ̒l��o�^
// ========================================================================================
function funTorokuKoumokuKotei(mode) {
    // ------------------------------------------------------------------------------------
    // �ϐ��錾
    // ------------------------------------------------------------------------------------
    var frm = document.frm00;    // ̫�тւ̎Q��
    var XmlId = "RGEN2021";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2021");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2021I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2021O );

    // ------------------------------------------------------------------------------------
    // �o�^
    // ------------------------------------------------------------------------------------
    // XML�̏�����
    setTimeout("xmlFGEN2021I.src = '../../model/FGEN2021I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    // ����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2021, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // ------------------------------------------------------------------------------------
    // ���ʕ\��
    // ------------------------------------------------------------------------------------
    // ����ү���ނ̕\��
//   dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
//   if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
//   //����
//   funInfoMsgBox(dspMsg);
//   //�����I��
//   return true;
//   }
//   else{
//   return false;
//   }

    return true;

}
// ADD end 20140919

//ADD start 20150722
//========================================================================================
//�o�^����
//�쐬�ҁFkitazawa
//�쐬���F2015/07/22
//���� �F
//�T�v �F���ڌŒ�`�F�b�NON�ŌŒ�f�[�^�����݂��Ȃ��f�[�^���`�F�b�N
//       ���ڌŒ�`�F�b�N��OFF�ɂ��� �� �Čv�Z�����s�����
//========================================================================================
function funKoteiChk(mode) {
 // ------------------------------------------------------------------------------------
 // �ϐ��錾
 // ------------------------------------------------------------------------------------
 var frm = document.frm00;    // ̫�тւ̎Q��
 var XmlId = "RGEN2022";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2022");
 var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2022I );
 var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2022O );

 // ------------------------------------------------------------------------------------
 // �o�^
 // ------------------------------------------------------------------------------------
 // XML�̏�����
 setTimeout("xmlFGEN2022I.src = '../../model/FGEN2022I.xml';", ConTimer);

 // ������XMĻ�قɐݒ�
 if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
     funClearRunMessage2();
     return -1;
 }

 // ����ݏ��A���ʏ����擾
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2022, xmlReqAry, xmlResAry, mode) == false) {
     return -1;
 }

 var msg_cd = funXmlRead(xmlResAry[2], "msg_cd", 0);
 return msg_cd;

}
//ADD end 20150722

//========================================================================================
// �}�X�^�P���E�����{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F�}�X�^�̒P���A�����l���擾����
//========================================================================================
function funBckMst(){

    //�Čv�Z�t���O��on�ɂ���
    setFg_saikeisan();

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var detailDoc = parent.detail.document;
    var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
    var XmlId = "RGEN0070";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0070");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0070I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0070O );
    var mode = 1;


    //------------------------------------------------------------------------------------
    //                              �}�X�^�P���E�����􂢖߂�
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0070I.src = '../../model/FGEN0070I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0070, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                     �����\��
    //------------------------------------------------------------------------------------
    //���ʔ���
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //�װ��������ү���ނ�\��
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //�����\��
    var reccnt = funGetLength_3(xmlResAry[2], "genryo", 0); //�����擾
    for(var i = 0; i < reccnt; i++){
        var seq_gyo = funXmlRead(xmlResAry[2], "seq_gyo", i);
        detailDoc.getElementById("txtTanka_"+seq_gyo).value = funXmlRead(xmlResAry[2], "tanka", i);
        detailDoc.getElementById("txtBudomari_"+seq_gyo).value = funXmlRead(xmlResAry[2], "budomari", i);
        detailDoc.getElementById("txtHenkouRen_"+seq_gyo).value = funXmlRead(xmlResAry[2], "henko_renraku", i);
        //�yQP@10713�z 2011/12/08 TT H.Shima DEL Start
		//cnt = seq_gyo - 1;
        //detailDoc.getElementById("txtTanka_"+seq_gyo).value = maTanka[cnt];
        //detailDoc.getElementById("txtBudomari_"+seq_gyo).value = maBudomari[cnt];
		//�yQP@10713�z 2011/12/08 TT H.Shima DEL End

//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/5/19 START -----------------------------------
        detailDoc.getElementById("hdnKojoNmTanka_"+seq_gyo).value = funXmlRead(xmlResAry[2], "tanka_kojoNm", i);
        detailDoc.getElementById("hdnKojoNmBudomari_"+seq_gyo).value = funXmlRead(xmlResAry[2], "budomari_kojoNm", i);

        //�T�u�E�B���h�E���\������Ă��邩
        if(subwinShowBln){
            //�T�u�E�B���h�E��[�s�ԍ�]�擾
            subwinGyoNo = $("GyoNo");

            //�P���ύX�s�ƕ\�����Ă���T�u�E�B���h�E�̍s�ԍ�����v�����ꍇ
            if(seq_gyo == subwinGyoNo.value){

                //�T�u�E�B���h�E��[�P���@�H�ꖼ]��ݒ�
                subwinTankaKojoNm = $("TankaKojoNm");
                subwinTankaKojoNm.value = funXmlRead(xmlResAry[2], "tanka_kojoNm", i);;

                //�T�u�E�B���h�E��[�����@�H�ꖼ]��ݒ�
                subwinTankaKojoNm = $("BudomariKojoNm");
                subwinTankaKojoNm.value = funXmlRead(xmlResAry[2], "budomari_kojoNm", i);;

                //�E�B���h�E�̃��t���b�V��
                win.refresh();
             }
        }

//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/5/19 END   -----------------------------------

        //�����̃`�F�b�N���O��
        detailDoc.getElementById("chkGenryo_" + seq_gyo).checked = false;

    }

    return true;
}

//========================================================================================
// �Čv�Z�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F�Čv�Z���s��
//========================================================================================
function funSaikeisan(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
    var XmlId = "RGEN0030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0030");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0030I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0030O );
    var mode = 1;

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               �Čv�Z�t���O������
    //------------------------------------------------------------------------------------
    //�Čv�Z�t���O��off�ɂ���
    headerFrm.FgSaikeisan.value = "false";


    //------------------------------------------------------------------------------------
    //                                     �Čv�Z
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0030I.src = '../../model/FGEN0030I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     ���ʕ\��
    //------------------------------------------------------------------------------------
    //�����e�[�u������
    //funGenryo_LeftDisplay(xmlResAry[2], "divGenryo_Left");
    //�����e�[�u���E��
    funGenryo_RightDisplay(xmlResAry[2], "divGenryo_Right");

    //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD Start
    //�����e�[�u�����ڔ�\��
    funGenryo_DispDecision(xmlResAry[2]);
    //�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD End

    //���Z���\��
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");
    //���ރe�[�u���\��
    funShizaiDisplay(xmlResAry[2],"divGenryoShizai");
//    funSetDtShisan();
    //�ύX�A���\��
    funGenryo_HenkouDisplay(xmlResAry[2]);

    //�Čv�Z�������ʕ\��
    var Errmsg = funXmlRead_3(xmlResAry[2], "kihon", "err_msg", 0, 0);
    if(Errmsg == ""){
        //�I�����b�Z�[�W��\��
        funInfoMsgBox(S000002);
    }
    else{
        //�G���[���b�Z�[�W��\��
        funInfoMsgBox("�Čv�Z���s���ʁF\n"+Errmsg);

    }

    //�����I��
    return true;

}

//========================================================================================
// ����{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F������s��
//========================================================================================
function funInsatu(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
    var XmlId = "RGEN0051";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0050");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0050I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0050O );
    var mode = 1;

    //�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               ���͍��ڂ̕ύX�m�F
    //------------------------------------------------------------------------------------
    if(funInputCheck(mode) == "true"){
        //�����Ȃ�

    }else{
        //���b�Z�[�W�\��
        if(funXmlRead(xmlFGEN0020O, "hantei", 0) == "false"){
            funInfoMsgBox(E000009);
        }
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                     ���
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0050I.src = '../../model/FGEN0050I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0051, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     ���ʕ\��
    //------------------------------------------------------------------------------------
    //���ʔ���
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //�װ��������ү���ނ�\��
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //̧���߽�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlFGEN0050O, "URLValue", 0);

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);

    //�����I��
    return true;

}

//========================================================================================
// ���͍��ڂ̕ύX�m�F
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F���͍��ڂ̕ύX�m�F���s��
//========================================================================================
function funInputCheck(mode){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
    var XmlId = "RGEN0050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0020O );

    //------------------------------------------------------------------------------------
    //                                ���͍��ڂ̕ύX�m�F
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN0020I.src = '../../model/FGEN0020I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0050, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     ���ʕԋp
    //------------------------------------------------------------------------------------
    //���ʂ�ԋp
    return funXmlRead(xmlResAry[2], "hantei", 0);


}

//========================================================================================
// ���ތ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F����R�[�h��莑�ނ̌������s��
//========================================================================================
function funShizaiSearch(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var XmlId = "RGEN0080";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0080");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0080I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0080O );
    var mode = 1;

    //�yQP@00342�z���ރe�[�u���C��
    //���ރR�[�h�������͂łȂ��ꍇ�@���@disabled�łȂ��ꍇ
    if(frm.txtCdShizai[CurrentRow].value != "" && frm.txtCdShizai[CurrentRow].readOnly != true){

        //------------------------------------------------------------------------------------
        //                                    ���ތ���
        //------------------------------------------------------------------------------------
        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage2();
            return false;
        }

        //����ݏ��A���ʏ����擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0080, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }

        //------------------------------------------------------------------------------------
        //                                     ���ʕ\��
        //------------------------------------------------------------------------------------
        //���ʔ���
        dspMsg = funXmlRead(xmlResAry[2], "msg_error", 0);
        if (dspMsg != "") {
            //�װ��������ү���ނ�\��
            funInfoMsgBox(dspMsg);
        }

        //���CD
        frm.hdnKaisha_Shizai[CurrentRow].value = funXmlRead(xmlResAry[2], "cd_kaisya", 0);
        //�H��CD
        frm.hdnKojo_Shizai[CurrentRow].value = funXmlRead(xmlResAry[2], "cd_kojyo", 0);
        //�H��L��
        frm.txtKigouKojo[CurrentRow].value = funXmlRead(xmlResAry[2], "kigo_kojyo", 0);
        //���ޖ�
        frm.txtNmShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "nm_shizai", 0);
        //�P��
        frm.txtTankaShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "tanka", 0);
        //����
        frm.txtBudomariShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "budomari", 0);


    }

    return true;
}

//========================================================================================
// �Čv�Z�t���O�ύX
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F�Čv�Z�t���O��on�ɂ���
//========================================================================================
function setFg_saikeisan(){

    //ͯ���ڰт�Document�Q��
    var headerFrm = parent.header.document.frm00;

    //�Čv�Z�t���O��on�ɂ���
    headerFrm.FgSaikeisan.value = "true";

    return true;

}
// �yQP@00342�z���Z���~
function setFg_saikeisan_sisan(index){

    //ͯ���ڰт�Document�Q��
    var headerFrm = parent.header.document.frm00;
    var detailDoc = parent.detail.document;		//�����ڰт�Document�Q��

    var fg = detailDoc.getElementById("hdnSisanChusi_"+index).value;

    //�Čv�Z�t���O��on�ɂ���
    if(fg == "1"){
    }
    else{
    	headerFrm.FgSaikeisan.value = "true";
    }


    return true;

}

//========================================================================================
// [���쌴�����]�e�[�u����[�P��]�ύX���C�x���g
// �쐬�ҁFE.Nakamura
// �쐬���F2010/02/18
// �T�v  �F�e�[�u�����ێ����[�P���@�H�ꖼ]�̒l���N���A���A�Čv�Z�t���O��on�ɂ���
//========================================================================================
function ShisakuGenka_Tankachanged(ChangeGyoNo){

	var detailDoc = parent.detail.document;		//�����ڰт�Document�Q��
	var KojoNmTanka;							//[�P���@�H�ꖼ]�ێ��񐧌�p
	var subwinGyoNo;							//�T�u�E�B���h�E��[�s�ԍ�]����p
	var subwinTankaKojoNm;						//�T�u�E�B���h�E��[�P���@�H�ꖼ]����p

	//�P���ύX�s��[�P���@�H�ꖼ]�񐧌�Q��
	KojoNmTanka = detailDoc.getElementById("hdnKojoNmTanka_" + ChangeGyoNo);

	//�P���ύX�s��[�P���@�H�ꖼ]��̒l���N���A
    KojoNmTanka.value = "";

    //�T�u�E�B���h�E���\������Ă��邩
    if(subwinShowBln){
    	//�T�u�E�B���h�E��[�s�ԍ�]�擾
    	subwinGyoNo = $("GyoNo");
    	if(ChangeGyoNo == subwinGyoNo.value){
    		//�P���ύX�s�ƕ\�����Ă���T�u�E�B���h�E�̍s�ԍ�����v�����ꍇ
    		//�T�u�E�B���h�E��[�P���@�H�ꖼ]�̐���Q�Ƃ��s���N���A
    		subwinTankaKojoNm = $("TankaKojoNm");
    		subwinTankaKojoNm.value = "";
    		//�T�u�E�B���h�E�̃y�[�W���t���b�V��
    		win.refresh();
    	}
    }

    //�Čv�Z�t���O��on�ɂ���
    setFg_saikeisan();

    return true;

}

//========================================================================================
// [���쌴�����]�e�[�u����[����]�ύX���C�x���g
// �쐬�ҁFE.Nakamura
// �쐬���F2010/02/18
// �T�v  �F�e�[�u�����ێ����[�����@�H�ꖼ]�̒l���N���A���A�Čv�Z�t���O��on�ɂ���
//========================================================================================
function ShisakuGenka_Budomarichanged(ChangeGyoNo){

	var detailDoc = parent.detail.document;		//�����ڰт�Document�Q��
	var KojoNmBudomari;							//[�����@�H�ꖼ]�ێ��񐧌�p
	var subwinGyoNo;							//�T�u�E�B���h�E��[�s�ԍ�]����p
	var subwinBudomariKojoNm;					//�T�u�E�B���h�E��[�����@�H�ꖼ]����p

	//�����ύX�s��[�����@�H�ꖼ]�񐧌�Q��
	KojoNmBudomari = detailDoc.getElementById("hdnKojoNmBudomari_" + ChangeGyoNo);

	//�����ύX�s��[�����@�H�ꖼ]��̒l���N���A
    KojoNmBudomari.value = "";

    //�T�u�E�B���h�E���\������Ă��邩
    if(subwinShowBln){
    	//�T�u�E�B���h�E��[�s�ԍ�]�擾
    	subwinGyoNo = $("GyoNo");
    	if(ChangeGyoNo == subwinGyoNo.value){
    		//�P���ύX�s�ƕ\�����Ă���T�u�E�B���h�E�̍s�ԍ�����v�����ꍇ

    		//�T�u�E�B���h�E��[�����@�H�ꖼ]�̐���Q�Ƃ��s���N���A
    		subwinBudomariKojoNm = $("BudomariKojoNm");
    		subwinBudomariKojoNm.value = "";
    		//�T�u�E�B���h�E�̃y�[�W���t���b�V��
    		win.refresh();
    	}
    }

    //�Čv�Z�t���O��on�ɂ���
    setFg_saikeisan();

    return true;

}

//========================================================================================
// �������J���}�}��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
// �T�v  �F������3�����ɃJ���}��}������
//========================================================================================
function setKanma(obj){

    obj.value = funAddComma(obj.value);
    return true;
}


//========================================================================================
// �󔒒u���֐��i"" �� "&nbsp;"�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�Ȃ�
// �T�v  �F"" �� "&nbsp;" �֒u������
//========================================================================================
function funKuhakuChg(strChk) {

    //�󔒂̏ꍇ
    if(strChk == ""){
        return "";
    }
    //�󔒂łȂ��ꍇ
    else{
        return strChk;
    }

}


//========================================================================================
// �R���{�{�b�N�X�̌����ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@obj    :�R���{�{�b�N�X�I�u�W�F�N�g
// �T�v  �F�R���{�{�b�N�X�̌����ҏW
//========================================================================================
function kengenComboSetting(obj){

    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //�ҏW����
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.style.backgroundColor = henshuOkColor;
        obj.disabled = false;
    }
    //�{��+Excel����
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.style.backgroundColor = henshuNgColor;
        obj.disabled = true;
    }

    return true;
}

//========================================================================================
// �e�L�X�g�G���A�̌����ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@obj    :�e�L�X�g�G���A�I�u�W�F�N�g
// �T�v  �F�e�L�X�g�G���A�̌����ҏW
//========================================================================================
function kengenTextareaSetting(obj){

    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //�ҏW����
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.style.backgroundColor = henshuOkColor;
        obj.readOnly = false;
    }
    //�{��+Excel����
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.style.backgroundColor = henshuNgColor;
        obj.readOnly = true;
    }

    return true;
}

//========================================================================================
// ���W�I�{�^���̌����ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@obj    :���W�I�{�^���I�u�W�F�N�g
// �T�v  �F���W�I�{�^���̌����ҏW
//========================================================================================
function kengenRadioSetting(obj){

    kengenBottunSetting(obj);

    return true;
}

//========================================================================================
// �`�F�b�N�{�b�N�X�̌����ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@obj    :�`�F�b�N�{�b�N�X�I�u�W�F�N�g
// �T�v  �F�`�F�b�N�{�b�N�X�̌����ҏW
//========================================================================================
function kengenCheckboxSetting(obj){

    kengenBottunSetting(obj);

    return true;
}

//========================================================================================
// �{�^���̌����ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�@obj    :���W�I�{�^���I�u�W�F�N�g
// �T�v  �F���W�I�{�^���̌����ҏW
//========================================================================================
function kengenBottunSetting(obj){

    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //�yQP@00342�z�X�e�[�^�X����
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//�yQP@00342�z�����擾
	var kenkyu = headerFrm.hdnBusho_kenkyu.value;
	var seikan = headerFrm.hdnBusho_seikan.value;
	var gentyo = headerFrm.hdnBusho_gentyo.value;
	var kojo = headerFrm.hdnBusho_kojo.value;
	var eigyo = headerFrm.hdnBusho_eigyo.value;

    //�yQP@00342�z�r��
    if(headerFrm.strKengenMoto.value == "999"){
    	obj.disabled = true;
    }
    else{
    	//�yQP@00342�z�ҏW����
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	        obj.disabled = false;

	        //�yQP@00342�z�����ޒ��B���Ŋm�F�����̏ꍇ
	        /*
			 * if( gentyo == "1" && st_gentyo == 2){ obj.disabled = true; }
			 */

	        //�yQP@00342�z�H��Ŋm�F�����̏ꍇ
	        /*
			 * if( kojo == "1" && st_kojo == 2){ obj.disabled = true; }
			 */

       		//20160607 KPX@1502111_No8�zADD start
	        // �����F�H��Ŋm�F����(�H��X�e�[�^�X��2)�̏ꍇ
			if( kojo == "1" && st_kojo >= 2 ){
				obj.disabled = true;
			}
       		//20160607 KPX@1502111_No8�zADD end

	        //�yQP@00342�z���Y�Ǘ����X�e�[�^�X >= 3 or �c�ƃX�e�[�^�X >= 4�@�̏ꍇ
			if( st_seikan >= 3 || st_eigyo >= 4){
				obj.disabled = true;
			}
	    }
	    //�yQP@00342�z�{��+Excel����
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        obj.disabled = true;
	    }
	 // 20160513  KPX@1600766 ADD start
    	if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
    		//�������F�P���J�����ׂĈȊO�̏ꍇ
    		obj.disabled = true;
    	}
	 // 20160513  KPX@1600766 ADD end
    }
    return true;
}


//========================================================================================
// �ގ��i������ʂ��N���i�_�C�A���O�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�Ȃ�
// �T�v  �F�ގ��i������ʂ��N������
//========================================================================================
function funRuiziSearch() {

    //�ގ��i������ʂ��N��
    funOpenModalDialog("../SQ111RuiziSearch/RuiziSearch.jsp", window, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;");

    return true;

}

//========================================================================================
// ����0���߁A�؂�グ����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�@�I�u�W�F�N�g
//       �F�A����
// �T�v  �F����0���߁A�؂�グ����
//========================================================================================
function funShosuUp(obj, keta){

    obj.value = funShosuKiriage(obj.value, keta);

    return true;
}

//========================================================================================
// ����0���߁A�؎̂ď���
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�@�I�u�W�F�N�g
//       �F�A����
// �T�v  �F����0���߁A�؎̂ď���
//========================================================================================
function funShosuKiri(obj, keta){

    obj.value = funShosuKirisute(obj.value, keta);

    return true;
}



//========================================================================================
// �R�[�h0���ߏ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�@�I�u�W�F�N�g
// �T�v  �F�R�[�h0���ߏ���
//========================================================================================
function funInsertCdZero(obj){

    var frm = document.frm00;

    funBuryZero(obj, frm.hdnCdketasu.value);
    return true;
}


//========================================================================================
// �����P�ʐݒ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/03
// ����  �F�@�I�u�W�F�N�g
// �T�v  �F�����P�ʁ������P�ʂ֐ݒ肷��
//========================================================================================
function baikaTaniSetting(){

    var detailFrm = document.frm00;        //�����ڰт�̫�юQ��

    detailFrm.txtBaikaTani.value = detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].innerText;

    return true;
}


//========================================================================================
// ���t�t�H�[�}�b�g�ݒ�Ăяo��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/03
// ����  �F�@�I�u�W�F�N�g
// �T�v  �Fyyyy/mm/dd�`���֕ϊ�����
//========================================================================================
function hidukeSetting(obj){

    //obj.value = funDateFomatChange(obj.value);
	FormatDateYYYYMMDD(obj, obj.value, 8);
    return true;
}

//========================================================================================
// ���t�t�H�[�}�b�g�ݒ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/03
// ����  �F�@�I�u�W�F�N�g
// �T�v  �Fyyyy/mm/dd�`���֕ϊ�����
//========================================================================================
function FormatDateYYYYMMDD(obj, val, len){

    str_val = val;

    // ********** ���p���l�`�F�b�N ********** //
    checkFlg = 0;
    arrNumber = new Array("1","2","3","4","5","6","7","8","9","0","/");
    for(i = 0; i < str_val.length; i++) {
        c_val = str_val.substring(i, i + 1);
        for(j = 0; j < arrNumber.length; j++){
            c_num = arrNumber[j];
            if (c_val == c_num) {
                checkFlg++;
            }
        }
    }

    if (str_val.length != checkFlg) {
        obj.value = val;
        return;
    }

    // ********** ���p���l�`�F�b�N ********** //

    // ********** �����񂩂�[/]���������� ********** //
    ret_val = '';
    for(i = 0; i < val.length; i++){
        c_val = val.substring(i, i + 1);
        if (c_val == '/') {
            c_dumy = '';
        } else {
            c_dumy = c_val;
        }
        ret_val = ret_val + c_dumy;
    }

    str_val = ret_val;

    // ********** �����񂩂�[/]���������� ********** //

    // ********** �����񒷃`�F�b�N ********** //
    if (str_val.length != len) {
        obj.value = val;
        return;
    }

    // ********** �����񒷃`�F�b�N ********** //

    // ********** ���t�`�F�b�N ********** //
    n_y = str_val.substring(0,4);
    n_m = str_val.substring(4,6);
    n_d = str_val.substring(6,8);

    monthEndDay = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

    cal = new Date;

    n_day = 0;

    // ���`�F�b�N
    if ((n_m < 1) || (12 < n_m)) {
    	obj.value = val;
    	return;
    }

    // ���邤�N�v�Z
    cal.setYear(n_y);
    cal.setMonth(n_m - 1);
    n_day = monthEndDay[n_m - 1];

    if ((n_m == 2)&&(((n_y%4 == 0)&&(n_y%100 != 0))||(n_y%400 == 0))){
    	n_day = 29;
    }

    if ((n_d < 0) || (n_day < n_d)) {
        obj.value = val;
        return;
    }

    // ********** ���t�`�F�b�N ********** //

    // ********** �t�H�[�}�b�g������Ԋ� ********** //
    obj.value = n_y + "/" + n_m + "/" + n_d;

    // ********** �t�H�[�}�b�g������Ԋ� ********** //
}



//========================================================================================
// ���ނ̈ꊇ�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/03
// ����  �F�@�I�u�W�F�N�g
// �T�v  �Fyyyy/mm/dd�`���֕ϊ�����
//========================================================================================
function shizaiIkkatu(){

    var frm = document.frm00;        //�����ڰт�̫�юQ��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��

    //�e�[�u���Q��
    var tblShizai = detailDoc.getElementById("tblList4");

    var gyoCount = tblShizai.rows.length;

    if(frm.chkIkkatuShizai.checked){

        for(var i=0; i<gyoCount; i++){
            frm.chkShizaiGyo[i].checked = true;
        }

    }else{

        for(var i=0; i<gyoCount; i++){
            frm.chkShizaiGyo[i].checked = false;
        }

    }
}

//========================================================================================
// �I���{�^��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// ����  �F�Ȃ�
// �T�v  �F��ʂ��I�����A����f�[�^�ꗗ��ʂ֑J�ڂ���
//========================================================================================
function funEnd(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var mode = 1;

    //�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               �v�Z���e�̕ύX�m�F
    //------------------------------------------------------------------------------------
    //�Čv�Z���s���Ă��Ȃ��ꍇ
    if(frm.FgSaikeisan.value == "true"){

        //�m�F���b�Z�[�W��\��
        if(funConfMsgBox(E000007) == ConBtnYes){

        	//2010/02/23 NAKAMURA ADD START-----
        	//�r������
        	funcHaitaKaijo(mode)
            //2010/02/23 NAKAMURA ADD END-------

            //��ʂ����
            parent.close();

            return true;
        }else{
            //�������Ȃ�
            return true;
        }

    }else{
        //�������Ȃ�

    }

    //------------------------------------------------------------------------------------
    //                               ���͍��ڂ̕ύX�m�F
    //------------------------------------------------------------------------------------
    if(funInputCheck(mode) == "true"){
        //�����Ȃ�

    }else{
        //���b�Z�[�W�\��
        if(funXmlRead(xmlFGEN0020O, "hantei", 0) == "false"){

            //�m�F���b�Z�[�W��\��
            if(funConfMsgBox(E000008) == ConBtnYes){

	        	//2010/02/23 NAKAMURA ADD START-----
	        	//�r������
	        	funcHaitaKaijo(mode)
	            //2010/02/23 NAKAMURA ADD END-------

                //��ʂ����
                parent.close();

                return true;
            }else{
                //�������Ȃ�
                return true;
            }
        }
        return false;
    }

    //2010/02/23 NAKAMURA ADD START-----
	//�r������
	funcHaitaKaijo(mode)
    //2010/02/23 NAKAMURA ADD END-------

    //��ʍ��ڊJ��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��

    //��ʂ����
    parent.close();

    return true;
}

//========================================================================================
// �r����������
// �쐬�ҁFE.Nakamura
// �쐬���F2010/02/23
// ����  �Fmode  �F�������[�h
//           	1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�I���{�^���������@�r���������������
//========================================================================================
function funcHaitaKaijo(mode){

	//ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.strKengenMoto.value;

    //�yQP@00342�z�ҏW�����i�r���łȂ��ꍇ�j
    if(Kengen.toString() != "999"){

        //------------------------------------------------------------------------------------
        //                                    �ϐ��錾
        //------------------------------------------------------------------------------------
        var frm = document.frm00;    //̫�тւ̎Q��
        var XmlId = "RGEN0090";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0090");
        var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0090I );
        var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0090O );

        //------------------------------------------------------------------------------------
        //                                  ���ʏ��擾
        //------------------------------------------------------------------------------------
        //������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage2();
            return false;
        }

        //����ݏ��A���ʏ����擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0090, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// �I���s����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̔w�i�F��ύX����B
//========================================================================================
//�yQP@00342�z���ރe�[�u���C��
/*function funChangeSelectRowColor2() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //�w�i�F��ύX
        //oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //�w�i�F��߂�
            //oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
        }

        //���čs�̑ޔ�
        CurrentRow = oTR.rowIndex;
    }

    return true;

}*/
function funChangeSelectRowColor3(oSrc) {

    //�e�[�u���Q��
    var oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {

        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {

        }

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //���čs�̑ޔ�
        CurrentRow = oTR.rowIndex;
    }

    return true;

}

//========================================================================================
// ������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/11
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�������̉摜��\������
//========================================================================================
function funShowRunMessage2() {

    //�����ڰт�̫�юQ��
    var frm = parent.detail.document.frm00;

    frm.BtnEveStart.fireEvent('onclick');

    return true;

}

//========================================================================================
// �����I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/11
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�������̉摜���\���ɂ���
//========================================================================================
function funClearRunMessage2() {

    //�����ڰт�̫�юQ��
    var frm = parent.detail.document.frm00;

    frm.BtnEveEnd.fireEvent('onclick');

    return true;

}

//DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.14 start
// 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� START==============
//var hndl;
//function funHelpDisp() {
//
//	//�yQP@00342�z���F�{�^���Ƀt�H�[�J�X�Z�b�g
//    parent.header.focus();
//
//    //�w���v�\���i���������ׂɈ������Ŏ��s�j
//    hndl = setInterval("funOpenHelp()",1000);
//    return true;
//}
//function funOpenHelp() {
//    try{
//        //ͯ�ް̫�юQ��
//        var headerFrm = parent.header.document.frm00;
//        //�e���ڂ̎擾
//        var helppath = headerFrm.strHelpPath.value;
//        //�E�B���h�E�̐ݒ�
//        var w = screen.availWidth-10;
//        var h = screen.availHeight-30;
//        //�E�B���h�E�I�[�v��
//        var nwin=window.open(helppath,"HelpPage","menubar=no,resizable=yes,scrollbars=yes,width="+w+",height="+h+",left=0,top=0");
//        nwin.document.title="�������Z�V�X�e���@�w���v���";
//        //�������Ŏ��s���Ă������̂�����
//        clearInterval(hndl);
//     }catch(e){
//
//     }
//}
// 2010/05/12 �V�T�N�C�b�N�i�����j�v�] �y�Č�No12�z�w���v�̕\���@TT���� END  ==============
//DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.14 end


//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
//========================================================================================
// �����H���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�T���v��No�R���{�{�b�N�X���I�����ꂽ�����H���̏ڍׂ�\������
//========================================================================================
function seizo_output(){

    //̫�сA�޷���Ăւ̎Q��
    var frm = document.frm00;
    var detailDoc = parent.detail.document;

    //�R���{�{�b�N�X�Ŏw�肳�ꂽ�L�[����ɖ��ו\��
    var no = frm.ddlSeizoNo.selectedIndex;
    var seizo_shosai = detailDoc.getElementById("hdnSeizoShosai_"+no);
    frm.txtSeizoKotei.value = seizo_shosai.value;

}
//add end   -------------------------------------------------------------------------------


//========================================================================================
// �yQP@00342�z���Z���~�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function ShisanChusi(){

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

    //�yH24�N�x�Ή��z2012/04/17 kazama add start
    var yuuko_budomari;		//�L�������i���j
    var heikinjyutenryo;	//���Ϗ[�U�ʁi���j
    var kesu_kotehi;		//�Œ��/�P�[�X
    var kg_kotehi;			//�Œ��/KG
    // ADD 2013/11/1 okano�yQP@30154�zstart
    var kesu_rieki;			//���v/�P�[�X
    var kg_rieki;			//���v/KG
    // ADD 2013/11/1 okano�yQP@30154�zend
    //�yH24�N�x�Ή��z2012/04/17 kazama add end
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 start
    var set_color;
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 end

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //�񐔎擾
	var recCnt = detailFrm.cnt_sample.value;

	//�I���`�F�b�N
	var chkFg = false;
	for( var j = 0; j < recCnt; j++ ){

		//�񐔂ɑ΂���I�u�W�F�N�g�����݂���ꍇ
	    if(detailDoc.getElementById("chkInsatu"+j)){

		    //�I�u�W�F�N�g���`�F�b�N����Ă���ꍇ
		    if(detailDoc.getElementById("chkInsatu"+j).checked){
		    	chkFg = true;
			}
		}
	}

	//�I��񂪂Ȃ��ꍇ
	if(chkFg == false){
		//�I������Ă��Ȃ��ꍇ
		funInfoMsgBox(E000019);

		return;
	}

//20160617  KPX@1502111_No.5 ADD start
	//�A�g�o�^�̊m�F
	chkHaigoLink();
	//���삪�z�������N�ɓo�^����Ă���ꍇ
	if (detailFrm.hdnRenkeiSeqShisaku.value > 0) {
		for(j = 0; j < recCnt; j++ ){
		    //�I���
		    if(detailDoc.getElementById("chkInsatu"+j).checked){
		    	//�I���̎���V�[�P���X���o�^����Ă��邩
		    	if(detailFrm.hdnRenkeiSeqShisaku.value == detailDoc.getElementById("hdnSeq_Shisaku_"+j).value) {
		    		//�A�g�o�^����Ă���T���v���͎��Z���~�s��
		    		funErrorMsgBox(E000043);

		    		return;
		    	}
		    }
		}
	}
//20160617  KPX@1502111_No.5 ADD end

	//�m�F���b�Z�[�W�\��
	if(funConfMsgBox(E000018) == ConBtnYes){

		//�񐔎擾
		for( var j = 0; j < recCnt; j++ ){

			// ID�쐬
			txtId1 = "txtYukoBudomari_" + j;
			txtId2 = "txtHeikinZyu_" + j;
			txtId3 = "txtCaseKotei_" + j;
			txtId4 = "txtKgKotei_" + j;
			// ADD 2013/11/1 okano�yQP@30154�zstart
			txtId5 = "txtCaseRieki_" + j;
			txtId6 = "txtKgRieki_" + j;
			// ADD 2013/11/1 okano�yQP@30154�zend
			//�`�F�b�N�{�b�N�X�t���O�擾
			var checkbox = detailFrm.radioKoteihi[0].checked;

			//�񐔂ɑ΂���I�u�W�F�N�g�����݂���ꍇ
			if(detailDoc.getElementById("chkInsatu"+j)){

				//�I�u�W�F�N�g���`�F�b�N����Ă���ꍇ
				if(detailDoc.getElementById("chkInsatu"+j).checked){

					// ���Z��
					detailDoc.getElementById("txtShisanHi_"+j).value = "���Z���~";

					// ���Z���~
					detailDoc.getElementById("hdnSisanChusi_"+j).value = "1";

					//�yH24�N�x�Ή�No15�z2012/04/17 kazama add start
					detailDoc.getElementById(txtId1).style.background = "#c0c0c0";
					detailDoc.getElementById(txtId2).style.background = "#c0c0c0";

					if(checkbox == true){
						detailDoc.getElementById(txtId3).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						detailDoc.getElementById(txtId5).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zend
					}else{
						detailDoc.getElementById(txtId4).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						detailDoc.getElementById(txtId6).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zend
					}
					//�yH24�N�x�Ή�No15�z2012/04/17 kazama add end
				//�yH24�N�x�Ή�No15�z2012/04/26 SHIMA add Start
				}else{
					if(detailDoc.getElementById("hdnSisanChusi_"+j).value == 1){
						detailDoc.getElementById(txtId1).style.background = "#c0c0c0";
						detailDoc.getElementById(txtId2).style.background = "#c0c0c0";

						if(checkbox == true){
							detailDoc.getElementById(txtId3).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano�yQP@30154�zstart
							detailDoc.getElementById(txtId5).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano�yQP@30154�zend
						}else{
							detailDoc.getElementById(txtId4).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano�yQP@30154�zstart
							detailDoc.getElementById(txtId6).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano�yQP@30154�zend
						}
					}else{
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//						detailDoc.getElementById(txtId1).style.background = "#ffff88";
//						detailDoc.getElementById(txtId2).style.background = "#ffff88";
//
//						if(checkbox == true){
//							detailDoc.getElementById(txtId3).style.background = "#ffff88";
//						}else{
//							detailDoc.getElementById(txtId4).style.background = "#ffff88";
//						}
//�C����\�[�X
						//�Œ�`�F�b�N��ON�̏ꍇ�̓O���[�ɂ���
						if (detailDoc.getElementById("chkKoumoku_"+ j).checked) {
							set_color = "#c0c0c0";
					    }else{
							set_color = "#ffff88";
					    }
						detailDoc.getElementById(txtId1).style.background = set_color;
						detailDoc.getElementById(txtId2).style.background = set_color;
						if(checkbox == true){
							detailDoc.getElementById(txtId3).style.background = set_color;
							// ADD 2013/11/1 okano�yQP@30154�zstart
							detailDoc.getElementById(txtId5).style.background = set_color;
							// ADD 2013/11/1 okano�yQP@30154�zend
						}else{
							detailDoc.getElementById(txtId4).style.background = set_color;
							// ADD 2013/11/1 okano�yQP@30154�zstart
							detailDoc.getElementById(txtId6).style.background = set_color;
							// ADD 2013/11/1 okano�yQP@30154�zend
						}
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					}
				//�yH24�N�x�Ή�No15�z2012/04/26 SHIMA add End
				}
			}
		}

		//�������b�Z�[�W
		funInfoMsgBox(S000003);

		return;

	}
    else{
    	//�������Ȃ�
        return true;
    }
}



//--------------------------------------------�}�ԍ쐬--------------------------------------------------

//========================================================================================
// �yQP@00342�z�}�ԍ쐬�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function fun_Edaban(){

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��
    var retVal;

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    args = new Array();
    args[0] = headerDoc;
	args[1] = detailDoc;

    //�}�ԍ쐬��ʂ��N������
//MOD start 2012/4/6 hisahori
//    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Edaban.jsp", args, "dialogHeight:100px;dialogWidth:450px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Edaban.jsp", args, "dialogHeight:150px;dialogWidth:640px;status:no;scroll:no");
//MOD end 2012/4/6 hisahori

    //�I��
// MOD start 20120410 hisahori
//    if(retVal == null){
//    	return true;
//    }
//    else if(retVal == "false"){
//    	//�������Ȃ�
//    	return true;
//    }
    if(retVal == null){
    	return true;
    }
	else if(retVal[0] == "false"){  // �I���{�^���ŕ���ꂽ�ꍇ�A�Ԃ�l��false�̏ꍇ
    	return true;
    }
// MOD end 20120410 hisahori
    //�o�^
    else{

    	//�}�ԑޔ�
// MOD start 2012/4/10 hisahori
//    	headerFrm.hdnShuruiEda.value = retVal;
    	headerFrm.hdnShuruiEda.value = retVal[0];
    	headerFrm.hdnShisakuNmEda.value = retVal[1];
// MOD end 2012/4/10 hisahori

    	//�ύX�m�F
    	if(funInputCheck(1) == "false"){
        	//�m�F���b�Z�[�W
        	if(funConfMsgBox(E000017) == ConBtnYes){

    	    	// MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 start
//        		if(funTorokuInfo(1)){
            	if(funTorokuInfo(1, 0)){
        	    // MOD 2015/06/12 TT.Kitazawa�yQP@40812�zNo.6 end
                	//�}�ԍ쐬
                	funExec_Edaban(1);
                }
                //�o�^���s
                else{
                	return false;

                }
	        }
	        else{
	        	//�}�ԍ쐬
	        	funExec_Edaban(1);

	        }
  		}
  		else{
  			//�}�ԍ쐬
  			funExec_Edaban(1);

  		}

  		return true;
    }
}

//========================================================================================
// �yQP@00342�z�}�ԍ쐬����
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function funExec_Edaban(mode){

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

	//------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2200";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2180");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2180I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2180O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  �}�ԍ쐬
    //------------------------------------------------------------------------------------
// ADD start 20120413 hisahori
    //XML�̏�����
    setTimeout("xmlFGEN2180I.src = '../../model/FGEN2180I.xml';", ConTimer);
// ADD end 20120413 hisahori

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2200, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //�r������
    funcHaitaKaijo(mode);

    //�}�Ԑݒ�
    headerFrm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //�ĕ\��
// MOD 2012/4/4 hisahori
//    funGetKyotuInfo(1);

	    var FuncIdAry = new Array(ConResult,ConUserInfo);
	    var xmlReqAry2 = new Array(xmlUSERINFO_I);
	    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O);

	    //������XMĻ�قɐݒ�i�쐬�����}�Ԃ�ݒ�j
	    if (funReadyOutput("RGEN2160", xmlReqAry2, 1) == false) {
	        return false;
	    }
	    //����ݏ��A���ʏ����擾
	    if (funAjaxConnection("RGEN2160", FuncIdAry, xmlRGEN2160, xmlReqAry2, xmlResAry2, mode) == false) {
	        return false;
	    }
	    //����ʂ����i�I���{�^���������̏����Ɠ����j
    	end_click = true; //�I���{�^�������t���O
        parent.close(); //��ʂ����
	    //��

		//�������Z��ʁi�c�Ɓj��\��
	    window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=no,resizable=yes");

// MOD 2012/4/4 hisahori

}

//========================================================================================
// �yQP@00342�z�}�ԍ쐬�q��ʁ@�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function funLoad_Edaban(){
	//------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2190";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2170");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2170I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2170O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  �H����擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput_Edaban(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2190, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                 �}�Ԏ�ރR���{�{�b�N�X�\��
    //------------------------------------------------------------------------------------
    //�}�Ԏ�ރR���{�{�b�N�X����
    funCreateComboBox(frm.ddlEda , xmlResAry[2] , 7, 0);

//ADD start 2012/4/6 hisahori
	// �i����e�i�������Z�j��ʂ���擾���A�}�ԍ쐬��ʂ̎��얼�ɕ\��
	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
    var frm = document.frm00;
   	var str_HinNm = opener_header.hdnNmMotHin.value;
    frm.txtShisakuNm.value = str_HinNm;
//ADD end 2012/4/6 hisahori
}

//========================================================================================
// �yQP@00342�z�}�ԍ쐬�q��ʁ@�o�^�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function edaban_toroku(){

	var frm = document.frm00;

// MOD start 2012/4/10 hisahori
//	//�}�ԑI��l�̎擾
//	var sentaku = frm.ddlEda.options[frm.ddlEda.selectedIndex].value;
//	//�߂�l�̐ݒ�
//	window.returnValue = sentaku;

	var sentaku = frm.ddlEda.options[frm.ddlEda.selectedIndex].value;
	var EdaNm = frm.txtEdaShisakuNm.value;
    var arrRtnVal = new Array(sentaku, EdaNm);
	window.returnValue = arrRtnVal;
// MOD end 2012/4/10 hisahori

	//��ʂ����
    close(self);

}

//========================================================================================
// �yQP@00342�z�}�ԍ쐬�q��ʁ@�I���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2010/10/08
//========================================================================================
function edaban_close(){

	//�߂�l�̐ݒ�
// MOD start 20120419 hisahori
//	window.returnValue = "false";
    var arrRtnVal = new Array("false", "");
	window.returnValue = arrRtnVal;
// MOD end 20120419 hisahori

	//��ʂ����
    close(self);

}

//========================================================================================
// �yQP@00342�zXML�t�@�C���ɏ������݁i�}�ԍ쐬�q��ʁj
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput_Edaban(XmlId, reqAry, mode) {

    var i;
    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\���i�ð���ݒ�j
        if (XmlId.toString() == "RGEN2190") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2170
                    break;
            }
        }


    }
    return true;
}


//=================================== �X�e�[�^�X�ݒ�_�C�A���O�@JavaScript  ================================

//========================================================================================
// �yQP@00342�z�ð���ݒ菉���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
//========================================================================================
function funLoad_Status(){

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var opener_detail = window.dialogArguments[1].frm00;		//����̫��
    var frm = document.frm00;

    //�߂�l�̏�����
	window.returnValue = "false";

   	//�X�e�[�^�X�擾
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

   	//�����擾
    var kenkyu = opener_header.hdnBusho_kenkyu.value;
    var seikan = opener_header.hdnBusho_seikan.value;
    var gentyo = opener_header.hdnBusho_gentyo.value;
    var kojo = opener_header.hdnBusho_kojo.value;
    var eigyo = opener_header.hdnBusho_eigyo.value;
    // ADD 2013/11/5 QP@30154 okano start
    var DataId = opener_header.hdnDataId.value;
    // ADD 2013/11/5 QP@30154 okano end

    //���ݽð���ݒ�
    document.getElementById("divStatusKenkyu_now").innerHTML = funStatusSetting("1",st_kenkyu);
    document.getElementById("divStatusSeikan_now").innerHTML = funStatusSetting("2",st_seikan);
    document.getElementById("divStatusGentyo_now").innerHTML = funStatusSetting("3",st_gentyo);
    document.getElementById("divStatusKojo_now").innerHTML = funStatusSetting("4",st_kojo);
    document.getElementById("divStatusEigyo_now").innerHTML = funStatusSetting("5",st_eigyo);

    //��ʐ���
    var output_html = "";
    output_html = output_html + "<table id=\"dataTable\" name=\"dataTable\" border=\"0\" cellspacing=\"0\" width=\"99%\" align=\"left\"> ";

    //���Y�Ǘ����̏ꍇ
	if( seikan == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>�ۑ��i�X�e�[�^�X�ύX�����j</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//���Z�˗��@�F�@�X�e�[�^�X�u��1�v�u�c2�v�̏ꍇ�ɕҏW��
		if(st_seikan == 1 && st_eigyo == 2){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"1\" tabindex=\"4\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"1\" tabindex=\"4\" disabled></td> ";
		}
		output_html = output_html + "    	<td>���Z�˗�</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//�m�F�����@�F�@�X�e�[�^�X�u��2�v�u��2�v�u�H2�v�̏ꍇ�ɕҏW��
		// MOD 2013/11/5 QP@30154 okano start
//			if(st_seikan == 2 && st_gentyo == 2 && st_kojo == 2 && st_eigyo != 4){
		if(((st_seikan == 2 && st_gentyo == 2 && st_kojo == 2) || (DataId == 1 && st_seikan != 3 && st_eigyo == 2)) && st_eigyo != 4){
		// MOD 2013/11/5 QP@30154 okano end
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}
		output_html = output_html + "    	<td>�m�F����</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //�m�F����� �F �X�e�[�^�X�u�c�R�v�ȍ~�͕ҏW�s�� if( st_eigyo >= 3 || st_seikan < 3){
		 * output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\" disabled></td> "; }
		 * else{ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\"></td> "; }
		 * output_html = output_html + " <td>�m�F�����</td> "; output_html =
		 * output_html + " </tr> ";
		 */
	}
	//�����ޒ��B���̏ꍇ
	else if( gentyo == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>�ۑ��i�X�e�[�^�X�ύX�����j</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";
		//�m�F�����@�F�@�X�e�[�^�X�u��1�v�̏ꍇ�ɕҏW��
		if(st_gentyo == 1 && st_eigyo != 4){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}
		output_html = output_html + "    	<td>�m�F����</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //�m�F����� �F �X�e�[�^�X�u��1�`2�v�u��2�v�̏ꍇ�ɕҏW�� if(st_seikan >= 1 && st_seikan <= 2 &&
		 * st_gentyo == 2 && st_eigyo != 4){ output_html = output_html + "
		 * <td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"3\"
		 * tabindex=\"6\"></td> "; } else{ output_html = output_html + "
		 * <td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"3\"
		 * tabindex=\"6\" disabled></td> "; } output_html = output_html + "
		 * <td>�m�F�����</td> "; output_html = output_html + " </tr> ";
		 */
	}
	//�H��̏ꍇ
	else if( kojo == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>�ۑ��i�X�e�[�^�X�ύX�����j</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//�m�F�����@�F�@�X�e�[�^�X�u�c2�v�u��2�v�u�H1�v�̏ꍇ�ɕҏW��
		if(st_eigyo == 2 && st_seikan == 2 && st_kojo == 1){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}

		output_html = output_html + "    	<td>�m�F����</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //�m�F����� �F �X�e�[�^�X�u���R�v�ȍ~�͕ҏW�s�� if(st_seikan >= 3 || st_eigyo == 4 ||
		 * st_kojo != 2){ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\" disabled></td> "; }
		 * else{ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\"></td> "; }
		 * output_html = output_html + " <td>�m�F�����</td> "; output_html =
		 * output_html + " </tr> ";
		 */
	}
	output_html = output_html + "</table> ";

    //HTML�ݒ�
    document.getElementById("status_table").innerHTML = output_html;

    //�X�e�[�^�X�N���A�{�^��
    if( seikan == "1"){
    	document.getElementById("btnClear").style.visibility = "visible";
    }

	return true;
}


//========================================================================================
// �yQP@00342�z�ð���ݒ�@�o�^�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
//========================================================================================
function fun_Toroku_status(){

	//***** ADD�yH24�N�x�Ή��z20120420 hagiwara S **********
	var headFrm = window.dialogArguments[0].frm00;								// �w�b�_�[�t�H�[��
	var dtlFrm = window.dialogArguments[1].frm00;								// ����̫��
	var divGenryo_Left = dtlFrm.document.getElementById("divGenryo_Left");		// �������\���G���A
	var cntGenryo = divGenryo_Left.document.getElementById("cnt_genryo").value;	// �������s��
	var genryoList = divGenryo_Left.document.getElementById("tblList1");		// �������
	var errFlg = 0;																// 1:�G���[���b�Z�[�W�A�������f(���ۑ�����)
																				// 2:�G���[���b�Z�[�W(���ۑ�����)
	var errGyou = 0;															// �G���[���ŏ��Ɍ��������s
	//***** ADD�yH24�N�x�Ή��z20120420 hagiwara E **********

	//�I��׼޵���ݒl�擾
    var j;
    var st_setting;
    var radio = document.getElementsByName("chk");
    for( j=0; j<radio.length; j++ ){
	    if( radio[j].checked ){
	   		st_setting = radio[j].value;
	    }
    }
    
    // 20170515 KPX@1700856 ADD Start
    var arrTankaZeroGenryo = dtlFrm.hdnTankaZeroGenryo.value.split(ConDelimiter);
    // 20170515 KPX@1700856 ADD end

    //�߂�l�̐ݒ�
	window.returnValue = st_setting;

	//***** ADD�yH24�N�x�Ή��z20120420 hagiwara S **********
	// �P���܂��͕������󔒂܂��̓[���̏ꍇ�A�G���[���b�Z�[�W�\��
	// �G���[����t���O��������
	errFlg = 0;

    for(var i = 1; i < cntGenryo; i++){
    	// �`�F�b�N�{�b�N�X�A����CD�̗��������݂���
// ADD start 20120713 hisahori
		// �ǂ̃T���v���ɂ��z������Ă��Ȃ������s�͔�\���ƂȂ��Ă��邽�߁A�P���E�����̃`�F�b�N���Ȃ�
		if(genryoList.rows[i].style.display != "none"){
// ADD end 20120713 hisahori
	    	if((genryoList.rows[i].cells[0].childNodes[0].value === "on") && (genryoList.rows[i].cells[2].childNodes[0].value !== "") && (genryoList.rows[i].cells[2].childNodes[0].value !== undefined)){
	    		
	    		// 20170515 KPX@1700856 MOD Start
	    		var targetGenryoCd = genryoList.rows[i].cells[2].childNodes[0].value;
	    		var isNotTankaZeroGenryoCd = true;
	    		for(var j = 0;j < arrTankaZeroGenryo.length ; j++){
	    			// ����CD��0�~���̏ꍇ(6��0����)
	    			if(targetGenryoCd === ("000000"+arrTankaZeroGenryo[j]).slice(-6)){
	    				isNotTankaZeroGenryoCd = false;
	    			}
	    		}
	    		
	    		// ����CD��7024�ȊO�̏ꍇ
	    		//if(genryoList.rows[i].cells[2].childNodes[0].value !== "007024"){
	    		// ����CD���P��0�~���ȊO�̏ꍇ
	    		if(isNotTankaZeroGenryoCd){
	    		// 20170515 KPX@1700856 MOD End
	    			// �P��=(0 or �� or undefined) or ����=(0 or �� or undefined)�̏ꍇ
	    			if((genryoList.rows[i].cells[5].childNodes[0].value === "0.00") || (genryoList.rows[i].cells[5].childNodes[0].value === "") || (genryoList.rows[i].cells[5].childNodes[0].value === undefined) || (genryoList.rows[i].cells[6].childNodes[0].value === "0.00") || (genryoList.rows[i].cells[6].childNodes[0].value === "") || (genryoList.rows[i].cells[6].childNodes[0].value === undefined)){

	    				// ���Y�Ǘ����̏ꍇ
	    				if(headFrm.hdnBusho_seikan.value === "1"){
	    					// �X�e�[�^�X���u�����v���������́A�m�F�����̃��W�I�{�^���Ƀ`�F�b�N�������Ă���ꍇ
	    					if(headFrm.hdnStatus_seikan.value === "3" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/29 TT H.SHIMA DEL Start
	    					// �X�e�[�^�X���u-�v�u�˗��v���������͎��Z�˗��̃��W�I�{�^���Ƀ`�F�b�N�������Ă���ꍇ
	//    					else if(headFrm.hdnStatus_seikan.value === "1" || headFrm.hdnStatus_seikan.value === "2" || st_setting === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/29 TT H.SHIMA DEL End
	    				}
	    				// �����ޒ��B��
	    				else if(headFrm.hdnBusho_gentyo.value === "1"){
	    					// �X�e�[�^�X���u�����v���������́A�m�F�����̃��W�I�{�^���Ƀ`�F�b�N�������Ă���ꍇ
	    					if(headFrm.hdnStatus_gentyo.value === "2" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/30 TT H.SHIMA DEL Start
	    					// �X�e�[�^�X���u-�v�̏ꍇ
	//    					else if(headFrm.hdnStatus_gentyo.value === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/30 TT H.SHIMA DEL End
	    				}
	    				// �H��̏ꍇ
	    				else if(headFrm.hdnBusho_kojo.value === "1"){
	    					// �X�e�[�^�X���u�����v���������́A�m�F�����̃��W�I�{�^���Ƀ`�F�b�N�������Ă���ꍇ
	    					if(headFrm.hdnStatus_kojo.value === "2" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/29 TT H.SHIMA DEL Start
	    					// �X�e�[�^�X���u-�v�̏ꍇ
	//    					else if(headFrm.hdnStatus_kojo.value === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					�yH24�N�x�Ή� �C���Q�z2012/05/29 TT H.SHIMA DEL End
	    				}
	    			}
	    		}
	    	}
// ADD start 20120713 hisahori
		}
// ADD end 20120713 hisahori
    }

    // �e�G���[��ɑΉ����������������Ȃ�
    switch(errFlg){
	    case 1:
	    	// �G���[���b�Z�[�W��\�����A�o�^�����𒆒f
			//funErrorMsgBox("�y�������z[�s��" + errGyou + "]" + E000024);
			funErrorMsgBox("�y�������z" + E000024);
			window.returnValue = "false";
			break;
	    case 2:
	    	// �G���[���b�Z�[�W��\��
			//funErrorMsgBox("�y�������z[�s��" + errGyou + "]" + E000024);
			funErrorMsgBox("�y�������z" + E000024);
			break;
    }
    //***** ADD�yH24�N�x�Ή��z20120420 hagiwara E **********

	//��ʂ����
    close(self);
}

//========================================================================================
// �yQP@00342�z�ð���ݒ�@�I���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
//========================================================================================
function fun_Close_st(){

	//�߂�l�̐ݒ�
	window.returnValue = "false";

	//��ʂ����
    close(self);
}

//========================================================================================
// �yQP@00342�z�ð���ݒ�@�X�e�[�^�X�N���A�{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
//========================================================================================
function fun_status_clear(){

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var opener_detail = window.dialogArguments[1].frm00;		//����̫��

    var retVal;

    args = new Array();
    args[0] = opener_header.txtShainCd.value;
	args[1] = opener_header.txtNen.value;
	args[2] = opener_header.txtOiNo.value;
	args[3] = opener_header.txtEdaNo.value;
// ADD start 20120615 hisahori
	args[4] = opener_detail.hidsetSeqShisaku.value;
	args[5] = opener_detail.hidsetSampleNo.value;
	args[6] = opener_detail.hidsetShisanHi.value;
	args[7] = opener_detail.hidsetShisanChushi.value;
// ADD start 20120615 hisahori

	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start
	args[8] = opener_detail.hidsetChkKoumoku.value;		//���ڌŒ�`�F�b�N
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end

    //�X�e�[�^�X�N���A��ʂ��N������
//    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:500px;dialogWidth:650px;status:no;scroll:no");
	// MOD 20160607  KPX@1502111_No9 start
//    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:650px;dialogWidth:650px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:500px;dialogWidth:650px;status:no;scroll:no");
	// MOD 20160607  KPX@1502111_No9 end

    //�N���A������
    if(retVal == "Clear"){
    	//�߂�l�̐ݒ�
		window.returnValue = retVal;

		//��ʂ����
	    close(self);
    }
}

//========================================================================================
//�yQP@10713�z�Œ��v�Z���������f�[�^�؂�ւ�����
//�쐬�ҁFH.Shima
//�쐬���F2011/10/21
//�X�V�ҁFRyo.Hagiwara
//�X�V���F2011/11/17
//����  �F�؂�ւ��t���O�@�P�@���@�u�Œ��/�P�[�X�v�@2�@���@�u�Œ��/KG�v
//�T�v  �F��ʂ̐�����s��
//========================================================================================
function funChangeMode(changeFlg) {

	var headId1 = document.getElementById("kotehi_case");
	var headId2 = document.getElementById("kotehi_kg");
	// ADD 2013/11/1 okano�yQP@30154�zstart
	var headId3 = document.getElementById("rieki_case");
	var headId4 = document.getElementById("rieki_kg");
	// ADD 2013/11/1 okano�yQP@30154�zend

	//�yH24�N�x�Ή�No15�z2012/04/16 kazama add start
	var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
	var tableKeisanNm;    //�ǂݍ��݃e�[�u����
    tableKeisanNm = "keisan";
	//�yH24�N�x�Ή�No15�z2012/04/25 kazama add end
	// MOD 2013/12/24 QP@30154 okano start
//		// ADD 2013/11/11 okano�yQP@30154�zstart
//	    var headerFrm = parent.header.document.frm00; //ͯ�ް̫�юQ��
//		// ADD 2013/11/11 okano�yQP@30154�zend
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // MOD 2013/12/24 QP@30154 okano end

	if(changeFlg == 1){
		for(var i = 0; i < cnt_sample; i++){
			//�yH24�N�x�Ή�No15�z2012/04/16 kazama add start
			//���Z���~
			var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+i).value;
			//�yH24�N�x�Ή�No15�z2012/04/16 kazama add end

			// ID�쐬
			txtId1 = "txtCaseKotei_" + i;
			txtId2 = "txtKgKotei_" + i;
			// ADD 2013/11/1 okano�yQP@30154�zstart
			txtId3 = "txtCaseRieki_" + i;
			txtId4 = "txtKgRieki_" + i;
			// ADD 2013/11/1 okano�yQP@30154�zend

			//�yH24�N�x�Ή�No15�z2012/04/16 kazama add start
	// �yQP@10713�z20111117 hagiwara mod start
			if(fg_chusi == 1){
				// readOnly�؂�ւ�
				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					document.getElementById(txtId1).readOnly = "";
//�C����\�[�X
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId1).readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						document.getElementById(txtId3).readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						document.getElementById(txtId1).readOnly = "";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId3).readOnly = "";
						} else {
							document.getElementById(txtId3).readOnly = "readonly";
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					document.getElementById(txtId2).readOnly = "readonly";
					document.getElementById(txtId1).style.background = "#c0c0c0";
					document.getElementById(txtId2).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					document.getElementById(txtId4).readOnly = "readonly";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							document.getElementById(txtId3).style.background = "#c0c0c0";
//						} else {
//							document.getElementById(txtId3).style.background = "#ffffff";
//						}
					document.getElementById(txtId3).style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					document.getElementById(txtId4).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
				}else{
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					parent.detail.document.frm00.txtId1.readOnly = "";
//�C����\�[�X
				    if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId1.readOnly ="readonly";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						parent.detail.document.frm00.txtId3.readOnly ="readonly";
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						parent.detail.document.frm00.txtId1.readOnly = "";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId3.readOnly = "";
						} else {
							parent.detail.document.frm00.txtId3.readOnly = "readonly";
						}
						// ADD 2013/11/1 okano�yQP@30154�zend

				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					parent.detail.document.frm00.txtId2.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					parent.detail.document.frm00.txtId4.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//						} else {
//							parent.detail.document.frm00.txtId3.style.background = "#ffffff";
//						}
					parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					parent.detail.document.frm00.txtId4.style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
				}
			}else{
				// readOnly�؂�ւ�
				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					document.getElementById(txtId1).readOnly = "";
//					document.getElementById(txtId2).readOnly = "readonly";
//					document.getElementById(txtId1).style.background = "#ffff88";
//					document.getElementById(txtId2).style.background = "#ffffff";
//�C����\�[�X
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId1).readOnly = "readonly";
						document.getElementById(txtId1).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						document.getElementById(txtId3).readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								document.getElementById(txtId3).style.background = "#c0c0c0";
//							} else {
//								document.getElementById(txtId3).style.background = "#ffffff";
//							}
						document.getElementById(txtId3).style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						document.getElementById(txtId1).readOnly = "";
						document.getElementById(txtId1).style.background = "#ffff88";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId3).readOnly = "";
							document.getElementById(txtId3).style.background = "#ffff88";
						} else {
							document.getElementById(txtId3).readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								document.getElementById(txtId3).style.background = "#ffffff";
							document.getElementById(txtId3).style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
					document.getElementById(txtId2).readOnly = "readonly";
					document.getElementById(txtId2).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					document.getElementById(txtId4).readOnly = "readonly";
					document.getElementById(txtId4).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
				}else{
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					parent.detail.document.frm00.txtId1.readOnly = "";
//					parent.detail.document.frm00.txtId2.readOnly = "readonly";
//					// �e�L�X�g�{�b�N�X�F�؂�ւ�
//					parent.detail.document.frm00.txtId1.style.background = "#ffff88";
//					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//�C����\�[�X
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId1.readOnly = "readonly";
						// �e�L�X�g�{�b�N�X�F�؂�ւ�
						parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						parent.detail.document.frm00.txtId3.readOnly = "readonly";
						// �e�L�X�g�{�b�N�X�F�؂�ւ�
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//							} else {
//								parent.detail.document.frm00.txtId3.style.background = "#ffffff";
//							}
						parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						parent.detail.document.frm00.txtId1.readOnly = "";
						// �e�L�X�g�{�b�N�X�F�؂�ւ�
						parent.detail.document.frm00.txtId1.style.background = "#ffff88";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId3.readOnly = "";
							// �e�L�X�g�{�b�N�X�F�؂�ւ�
							parent.detail.document.frm00.txtId3.style.background = "#ffff88";
							// ADD 2013/11/1 okano�yQP@30154�zend
						} else {
							parent.detail.document.frm00.txtId3.readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								parent.detail.document.frm00.txtId3.style.background = "#ffffff";
							parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
					}
					parent.detail.document.frm00.txtId2.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					// ADD 2013/11/1 okano�yQP@30154�zstart
					parent.detail.document.frm00.txtId4.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId4.style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
				}
			}
			//�yH24�N�x�Ή��z2012/04/18 kazama mod end
		}
	}
	else if(changeFlg == 2){
		for(var i = 0; i < cnt_sample; i++){
			//�yH24�N�x�Ή�No15�z2012/04/18 kazama mod start
			//�yQP@00342�z���Z���~
        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+i).value;
			txtId1 = "txtCaseKotei_" + i;
			txtId2 = "txtKgKotei_" + i;
			// ADD 2013/11/1 okano�yQP@30154�zstart
			txtId3 = "txtCaseRieki_" + i;
			txtId4 = "txtKgRieki_" + i;
			// ADD 2013/11/1 okano�yQP@30154�zend

			if(fg_chusi == 1){
				if(document.frm00.radioKoteihi != undefined){
					document.getElementById(txtId1).readOnly = "readonly";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					document.getElementById(txtId3).readOnly = "readonly";
					// ADD 2013/11/1 okano�yQP@30154�zend
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					document.getElementById(txtId2).readOnly = "";
//�C����\�[�X
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId2).readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						document.getElementById(txtId4).readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						document.getElementById(txtId2).readOnly = "";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId4).readOnly = "";
						} else {
							document.getElementById(txtId4).readOnly = "readonly";
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					document.getElementById(txtId1).style.background = "#ffffff";
					document.getElementById(txtId2).style.background = "#c0c0c0";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					document.getElementById(txtId3).style.background = "#ffffff";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							document.getElementById(txtId4).style.background = "#c0c0c0";
//						} else {
//							document.getElementById(txtId4).style.background = "#ffffff";
//						}
					document.getElementById(txtId4).style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					// ADD 2013/11/1 okano�yQP@30154�zend
				}else{
					parent.detail.document.frm00.txtId1.readOnly = "readonly";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					parent.detail.document.frm00.txtId3.readOnly = "readonly";
					// ADD 2013/11/1 okano�yQP@30154�zend
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					parent.detail.document.frm00.txtId2.readOnly = "";
//�C����\�[�X
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId2.readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						parent.detail.document.frm00.txtId4.readOnly = "readonly";
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						parent.detail.document.frm00.txtId2.readOnly = "";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId4.readOnly = "";
						} else {
							parent.detail.document.frm00.txtId4.readOnly = "readonly";
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
					parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					parent.detail.document.frm00.txtId3.style.background = "#ffffff";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//						} else {
//							parent.detail.document.frm00.txtId4.style.background = "#ffffff";
//						}
					parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					// ADD 2013/11/1 okano�yQP@30154�zend
				}
				if(fg_chusi[i+1] != 1){
					//���Z���~�ݒ�̏I��
					fg_chusi = 0;
				}

			}else{

				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					document.getElementById(txtId1).readOnly = "readonly";
//					document.getElementById(txtId2).readOnly = "";
//					document.getElementById(txtId1).style.background = "#ffffff";
//					document.getElementById(txtId2).style.background = "#ffff88";
//�C����\�[�X
					document.getElementById(txtId1).readOnly = "readonly";
					document.getElementById(txtId1).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					document.getElementById(txtId3).readOnly = "readonly";
					document.getElementById(txtId3).style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId2).readOnly = "readonly";
						document.getElementById(txtId2).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						document.getElementById(txtId4).readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								document.getElementById(txtId4).style.background = "#c0c0c0";
//							} else {
//								document.getElementById(txtId4).style.background = "#ffffff";
//							}
						document.getElementById(txtId4).style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						document.getElementById(txtId2).readOnly = "";
						document.getElementById(txtId2).style.background = "#ffff88";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId4).readOnly = "";
							document.getElementById(txtId4).style.background = "#ffff88";
						} else {
							document.getElementById(txtId4).readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								document.getElementById(txtId4).style.background = "#ffffff";
							document.getElementById(txtId4).style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
				}else{
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//�C���O�\�[�X
//					parent.detail.document.frm00.txtId1.readOnly = "readonly";
//					parent.detail.document.frm00.txtId2.readOnly = "";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
//					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//					parent.detail.document.frm00.txtId2.style.background = "#ffff88";
//�C����\�[�X
					parent.detail.document.frm00.txtId1.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zstart
					parent.detail.document.frm00.txtId3.readOnly = "readonly";
					// �e�L�X�g�{�b�N�X�F�؂�ւ�
					parent.detail.document.frm00.txtId3.style.background = "#ffffff";
					// ADD 2013/11/1 okano�yQP@30154�zend
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId2.readOnly = "readonly";
						parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						parent.detail.document.frm00.txtId4.readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//							} else{
//								parent.detail.document.frm00.txtId4.style.background = "#ffffff";
//							}
						parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }else{
						parent.detail.document.frm00.txtId2.readOnly = "";
						parent.detail.document.frm00.txtId2.style.background = "#ffff88";
						// ADD 2013/11/1 okano�yQP@30154�zstart
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId4.readOnly = "";
							parent.detail.document.frm00.txtId4.style.background = "#ffff88";
						} else {
							parent.detail.document.frm00.txtId4.readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								parent.detail.document.frm00.txtId4.style.background = "#ffffff";
							parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano�yQP@30154�zend
				    }
//MOD 2013/07/11 ogawa �yQP@30151�zNo.13 end
				}
			}
			//�yH24�N�x�Ή�No15�z2012/04/18 kazama mod end
		}
	}
	// �yQP@10713�z20111117 hagiwara mod end
}

//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start
//========================================================================================
//�yQP@30151�zNo13 ���ڌŒ�`�F�b�N�؂�ւ�����
//�쐬�ҁFF.ogawa
//�쐬���F2013/07/11
//�X�V�ҁF
//�X�V���F
//����  �F�ꗗ�̍���
//�T�v  �F���͉s�̕ύX���s��
//========================================================================================
function funKChangeMode(changeCnt) {

    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

	//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
	//ͯ�ް̫�юQ�� & ���Y�Ǘ����Ɖc�Ƃ̃X�e�[�^�X�擾
    var headerFrm = parent.header.document.frm00;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//�Čv�Z�{�^����ҏW�ɐݒ�
    headerFrm.btnSaikeisan.disabled = false;

	//20160607 �yKPX@1502111_No8�z ADD start
    //�H��X�e�[�^�X�擾
    var st_kojo    = headerFrm.hdnStatus_kojo.value;
    //�����擾
    var kojo = headerFrm.hdnBusho_kojo.value;

	// �����F�H��Ŋm�F����(�H��X�e�[�^�X��2)�̏ꍇ
    if( kojo == "1" && st_kojo >= 2 ){
		return true;
	}
	//20160607�yKPX@1502111_No8�z ADD end

	//���Y�Ǘ����̊m�F�����ȍ~�̏ꍇ�́A���ڕҏW�����ɏ����I��
	if( ( st_seikan >= 3 ) || (st_eigyo >= 4)){
		return true;
	}
	//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

    //���Z���~
    var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+changeCnt).value;
    // ADD 2013/12/24 QP@30154 okano start
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // ADD 2013/12/24 QP@30154 okano end
    // ID�쐬
    txtId1 = "txtCaseKotei_" + changeCnt;    //�Œ��/�P�[�X
    txtId2 = "txtKgKotei_" + changeCnt;      //�Œ��/�s
    txtId3 = "txtYukoBudomari_" + changeCnt; //�L�������i���j
    txtId4 = "txtHeikinZyu_" + changeCnt;    //���Ϗ[�U�ʁi���j
	// ADD 2013/11/1 okano�yQP@30154�zstart
    txtId5 = "txtCaseRieki_" + changeCnt;    //���v/�P�[�X
    txtId6 = "txtKgRieki_" + changeCnt;      //���v/�s
	// ADD 2013/11/1 okano�yQP@30154�zend
	// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
    txtId7 = "txtSeizoRot" + changeCnt;      //�������b�g
    // ADD 2014/8/7 shima�yQP@30154�zNo.63 end

    //���ڌŒ�`�F�b�N ON������
    if (detailDoc.getElementById("chkKoumoku_"+ changeCnt).checked) {
		// readOnly�؂�ւ�
//		if(document.frm00.radioKoteihi != undefined){
			document.getElementById(txtId1).readOnly = "readonly";
			document.getElementById(txtId2).readOnly = "readonly";
			document.getElementById(txtId3).readOnly = "readonly";
			document.getElementById(txtId4).readOnly = "readonly";
			document.getElementById(txtId3).style.background = "#c0c0c0";
			document.getElementById(txtId4).style.background = "#c0c0c0";
			// ADD 2013/11/1 okano�yQP@30154�zstart
			document.getElementById(txtId5).readOnly = "readonly";
			document.getElementById(txtId6).readOnly = "readonly";
			// ADD 2013/11/1 okano�yQP@30154�zend
			// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
			document.getElementById(txtId7).readOnly = "readonly";
			document.getElementById(txtId7).style.background = "#ffffff";
			// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

			//�Œ��/����̏ꍇ
			if(detailFrm.radioKoteihi[0].checked == true){
    			document.getElementById(txtId1).style.background = "#c0c0c0";
    			document.getElementById(txtId2).style.background = "#ffffff";
    			// ADD 2013/11/1 okano�yQP@30154�zstart
    			document.getElementById(txtId5).style.background = "#c0c0c0";
    			document.getElementById(txtId6).style.background = "#ffffff";
    			// ADD 2013/11/1 okano�yQP@30154�zend
	    	//�Œ��/kg�̏ꍇ
    		}else{
				document.getElementById(txtId1).style.background = "#ffffff";
				document.getElementById(txtId2).style.background = "#c0c0c0";
				// ADD 2013/11/1 okano�yQP@30154�zstart
				document.getElementById(txtId5).style.background = "#ffffff";
				document.getElementById(txtId6).style.background = "#c0c0c0";
				// ADD 2013/11/1 okano�yQP@30154�zend
    		}
//    	}
//    	else {
//    		parent.detail.document.frm00.txtId1.readOnly = "readonly";
//			parent.detail.document.frm00.txtId2.readOnly = "readonly";
//			parent.detail.document.frm00.txtId3.readOnly = "readonly";
//			parent.detail.document.frm00.txtId4.readOnly = "readonly";
//			parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//			parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//			//�Œ��/����̏ꍇ
//			if(detailFrm.radioKoteihi[0].checked == true){
//    			// �e�L�X�g�{�b�N�X�F�؂�ւ�
//    			parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
//    			parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//        	    	//�Œ��/kg�̏ꍇ
//    		}else{
//				// �e�L�X�g�{�b�N�X�F�؂�ւ�
//				parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//				parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
//   			}
//   		}
        //���ڌŒ�`�F�b�N OFF������
    }else{
    	//�Œ��/����̏ꍇ
    	if(detailFrm.radioKoteihi[0].checked == true){
    		//�v�Z���~��
    		if(fg_chusi == 1){
    			// readOnly�؂�ւ�
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "";
    				document.getElementById(txtId2).readOnly = "readonly";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#c0c0c0";
    				document.getElementById(txtId2).style.background = "#ffffff";
    				document.getElementById(txtId3).style.background = "#c0c0c0";
    				document.getElementById(txtId4).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

    				// ADD 2013/11/1 okano�yQP@30154�zstart
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "";
    				document.getElementById(txtId6).readOnly = "readonly";
    				// ADD 2013/12/24 QP@30154 okano start
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).style.background = "#c0c0c0";
    				document.getElementById(txtId6).style.background = "#ffffff";
    				// ADD 2013/11/1 okano�yQP@30154�zend
//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "";
//    				parent.detail.document.frm00.txtId2.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// �e�L�X�g�{�b�N�X�F�؂�ւ�
//    				parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//    			}
    		}else{
    			// readOnly�؂�ւ�
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "";
    				document.getElementById(txtId2).readOnly = "readonly";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#ffff88";
    				document.getElementById(txtId2).style.background = "#ffffff";
    				document.getElementById(txtId3).style.background = "#ffff88";
    				document.getElementById(txtId4).style.background = "#ffff88";
    				// ADD 2013/11/1 okano�yQP@30154�zstart
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "";
    				document.getElementById(txtId6).readOnly = "readonly";
    				document.getElementById(txtId5).style.background = "#ffff88";
    				document.getElementById(txtId6).style.background = "#ffffff";
    				// ADD 2013/12/24 QP@30154 okano start
    				} else {
        				document.getElementById(txtId5).style.background = "#c0c0c0";
        				document.getElementById(txtId6).style.background = "#ffffff";
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				// ADD 2013/11/1 okano�yQP@30154�zend
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = color_henshu;
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "";
//    				parent.detail.document.frm00.txtId2.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// �e�L�X�g�{�b�N�X�F�؂�ւ�
//    				parent.detail.document.frm00.txtId1.style.background = "#ffff88";
//    				parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId3.style.background = "#ffff88";
//    				parent.detail.document.frm00.txtId4.style.background = "#ffff88";
//    			}
    		}
    	}
    	//�Œ��/kg�̏ꍇ
    	else {
       		//�v�Z���~��
    		if(fg_chusi == 1){
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "readonly";
    				document.getElementById(txtId2).readOnly = "";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#ffffff";
    				document.getElementById(txtId2).style.background = "#c0c0c0";
    				document.getElementById(txtId3).style.background = "#c0c0c0";
    				document.getElementById(txtId4).style.background = "#c0c0c0";
    				// ADD 2013/11/1 okano�yQP@30154�zstart
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "readonly";
    				document.getElementById(txtId6).readOnly = "";
    				// ADD 2013/12/24 QP@30154 okano start
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).style.background = "#ffffff";
    				document.getElementById(txtId6).style.background = "#c0c0c0";
    				// ADD 2013/11/1 okano�yQP@30154�zend
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId2.readOnly = "";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// �e�L�X�g�{�b�N�X�F�؂�ւ�
//    				parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//    			}
    		}else{
//    			if(document.frm00.radioKoteihi != undefined){
   					document.getElementById(txtId1).readOnly = "readonly";
   					document.getElementById(txtId2).readOnly = "";
   					document.getElementById(txtId3).readOnly = "";
   					document.getElementById(txtId4).readOnly = "";
   					document.getElementById(txtId1).style.background = "#ffffff";
   					document.getElementById(txtId2).style.background = "#ffff88";
   					document.getElementById(txtId3).style.background = "#ffff88";
   					document.getElementById(txtId4).style.background = "#ffff88";
   					// ADD 2013/11/1 okano�yQP@30154�zstart
   					// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
   					// ADD 2013/12/24 QP@30154 okano end
   					document.getElementById(txtId5).readOnly = "readonly";
   					document.getElementById(txtId6).readOnly = "";
   					document.getElementById(txtId5).style.background = "#ffffff";
   					document.getElementById(txtId6).style.background = "#ffff88";
   					// ADD 2013/12/24 QP@30154 okano start
    				} else {
       					document.getElementById(txtId5).style.background = "#ffffff";
       					document.getElementById(txtId6).style.background = "#c0c0c0";
    				}
   					// ADD 2013/12/24 QP@30154 okano end
   					// ADD 2013/11/1 okano�yQP@30154�zend
					// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = color_henshu;
    				// ADD 2014/8/7 shima�yQP@30154�zNo.63 end

//   				}else{
//   					parent.detail.document.frm00.txtId1.readOnly = "readonly";
//   					parent.detail.document.frm00.txtId2.readOnly = "";
//   					parent.detail.document.frm00.txtId3.readOnly = "";
//   					parent.detail.document.frm00.txtId4.readOnly = "";
//  					// �e�L�X�g�{�b�N�X�F�؂�ւ�
//   					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//   					parent.detail.document.frm00.txtId2.style.background = "#ffff88";
//   					parent.detail.document.frm00.txtId3.style.background = "#ffff88";
//   					parent.detail.document.frm00.txtId4.style.background = "#ffff88";
//   				}
   			}
   		}
   	}
}
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 end

//========================================================================================
//�yQP@10713�z�s�I�����̃n�C���C�g����
//�쐬�ҁFRyo.Hagiwara
//�쐬���F2011/11/01
//�X�V�ҁF
//�X�V���F
//����  �F�I��DOM
//�T�v  �F�s�I�����ɂ��̍s�̔w�i�F��ύX����
//========================================================================================
var defaultColor;
var tempId = "";

function funLineHighLight(element){
	if(tempId != ""){
		//�yH24�N�x�Ή��z2012/04/25 kazama add start
		var focusColor = "";		//�n�C���C�g�F���r���邽�߂̕ϐ�

		//�N���b�N���̔w�i�F��ێ�
		focusColor = Element.getStyle(tempId.slice(0, tempId.length-1) + 0, "background-color");
		//�n�C���C�g�J���[�łȂ�
		if(focusColor != "#7fffd4"){
			//��̍Ō�܂Ń��[�v
			for(var i = 0; i < defaultColor.length; i++){
				//�f�t�H���g�ɃN���b�N���̔w�i�F��ێ�
				defaultColor[i] = Element.getStyle(tempId.slice(0, tempId.length-1) + i, "background-color");
			}
		}
		//�yH24�N�x�Ή��z2012/04/25 kazama add end

		for(var i = 0; i < defaultColor.length; i++){
			Element.setStyle(tempId.slice(0, tempId.length-1) + i, {
				'background-color':defaultColor[i]
			});
		}
	}
	defaultColor = new Array(cnt_sample);
	var id = element.id;
	tempId = id;
	var num = parseInt(id.slice(-1));

	for(var i = num; i < cnt_sample; i++){
		defaultColor[i] = Element.getStyle(id.slice(0, id.length-1) + i, "background-color");
	}
	for(var i = num-1; i >= 0; i--){
		defaultColor[i] = Element.getStyle(id.slice(0, id.length-1) + i, "background-color");
	}

	for(var i = 0; i < defaultColor.length; i++){
		Element.setStyle(id.slice(0, id.length-1) + i, {
			'background-color':'#7FFFD4'
		});
	}
}

//========================================================================================
//�y�V�T�N�C�b�NH24�N�x�Ή��z�̗p�T���v��No�ȊO�O���[����
//�쐬�ҁFH.SHIMA
//�쐬���F2012/04/13
//�X�V�ҁF
//�X�V���F
//����  �F�@saiyoColumn     �F�̗p�T���v����ԍ�
//        �AarrSampleSeq    �F�T���v����ԍ�(�z��)
//        �BseqCnt          �F�T���v����
//�߂�l�F�Ȃ�
//�T�v  �F�̗p�T���v��No�ȊO�̌v�Z���ڗ�̔w�i�F���O���[�ɂ���
//========================================================================================
function funSaiyoDisp(saiyoColumn,arrSampleSeq,seqCnt){
	var gray = "#C0C0C0";
	for(var i = 0; i < seqCnt; i++){
		if(saiyoColumn != arrSampleSeq[i]){
			document.getElementById("txtSample" + i).style.background = gray;			//�T���v��No
			document.getElementById("txtSeizoHan_" + i).style.background = gray;		//�����H����
			document.getElementById("txtShisanHi_" + i).style.background = gray;		//���Z��
			document.getElementById("txtSuiZyuten_" + i).style.background = gray;		//�[�U�ʐ���(g)
			document.getElementById("txtYuZyuten_" + i).style.background = gray;		//�[�U�ʖ���(g)
			document.getElementById("total" + i).style.background = gray;				//���v��(g)
			document.getElementById("hijyu" + i).style.background = gray;				//��d
			document.getElementById("txtYukoBudomari_" + i).style.background = gray;	//�L������(%)
			document.getElementById("txtLebelRyo_" + i).style.background = gray;		//���x����(kg)
			document.getElementById("txtHeikinZyu_" + i).style.background = gray;		//���Ϗd�_��(kg)
			document.getElementById("hijyuKasan" + i).style.background = gray;			//��d���Z��(kg)
			document.getElementById("genryohi" + i).style.background = gray;			//������(�~)/�P�[�X
			document.getElementById("zairyohi" + i).style.background = gray;			//�ޗ���(�~)/�P�[�X
			document.getElementById("txtCaseKotei_" + i).style.background = gray;		//�Œ��(�~)/�P�[�X
			document.getElementById("genkakei" + i).style.background = gray;			//�����v(�~)/�P�[�X
			document.getElementById("sankouKo" + i).style.background = gray;			//�s�Q�l�F������t
			document.getElementById("genkakeiKo" + i).style.background = gray;			//�����v(�~)/��
			document.getElementById("sankouKG" + i).style.background = gray;			//�s�Q�l�FKG������t
			document.getElementById("genryohiKG" + i).style.background = gray;			//������(�~)/kg
			document.getElementById("zairyohiKG" + i).style.background = gray;			//�ޗ���(�~)/kg
			document.getElementById("txtKgKotei_" + i).style.background = gray;			//�Œ��(�~)/kg
			document.getElementById("txtKgGenkake_" + i).style.background = gray;		//�����v(�~)/kg
			document.getElementById("baika" + i).style.background = gray;				//����
			document.getElementById("sori" + i).style.background = gray;				//�e��(%)
			// ADD 2013/11/1 QP@30154 okano start
			document.getElementById("txtCaseRieki_" + i).style.background = gray;		//���v(�~)/�P�[�X
			document.getElementById("txtKgRieki_" + i).style.background = gray;			//���v(�~)/kg
			// ADD 2013/11/1 QP@30154 okano end
		}
	}
}

//========================================================================================
//�y�V�T�N�C�b�NH24�N�x�Ή��z�������e�[�u�����ڔ�\��
//�쐬�ҁFH.SHIMA
//�쐬���F2012/04/20
//�X�V�ҁF
//�X�V���F
//����  �F�@xmlData �F�ݒ�XML
//�߂�l�F�Ȃ�
//�T�v  �F������񍶂̌����̒l���󔒁A��������0�̏ꍇ�͔�\��
//========================================================================================
function funGenryo_DispDecision(xmlData){

	//�s��\�����邩�̔���t���O�z��
	var rowDispflg = new Array();
	//�H���s���ێ��z��
	var koteiArray = new Array();

	//�e�[�u�����ݒ�
	tablekihonNm = "kihon";
	tableHaigoNm = "shisaku";

	//�񐔎擾
	cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);
	//�s���擾
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	var kotei_cont = 0;

	//�s���[�v
	for(var i = 0; i < cnt_genryo; i++){

		var atai = funXmlRead_3(xmlData, tableHaigoNm+0, "sort", 0, i+2);

		//�s�ɒl�������Ă��邩�m�F�p�t���O
		var nullflg  = false;

		//�l�̊m�F
		if(atai == "" || atai == undefined){
			//���̍s�̃\�[�g�ԍ�������
			var next = funXmlRead_3(xmlData, tableHaigoNm+0, "sort", 0, i+3);
			//�l�������Ă����
			if(next != "" && next != undefined){			//��[�H��]
				//�H���s���ێ�
				koteiArray[kotei_cont] = i;
				kotei_cont++;
				rowDispflg[i] = "";

			//�l�������Ă��Ȃ���΍H�����ڂł͂Ȃ�
			}else{
				if(koteiArray[koteiArray.length-1] != "EOF"){//��[�H���v�J�n]
					//�擾�H���s�̏I���ݒ�
					koteiArray[kotei_cont] = "EOF";
					//�J�E���g�����Z�b�g
					kotei_cont = 0;

				//�H�����v���ڌォ
				}else if(koteiArray[kotei_cont] == "EOF"){	//��[�H���v�I��]
					rowDispflg[i] = true;
				}

				if(koteiArray[kotei_cont] != "EOF"){		//��[�H�����v]
					var koteirow = parseInt(koteiArray[kotei_cont]);
					nullflg = false;
					//�H���̌����̃t���O�`�F�b�N
					for(k = eval(koteirow + 1);;k++){
						//�������ڂł͂Ȃ�
						if(rowDispflg[k] == ""){
							break;
						//�������ڂ�true�Ȃ��
						}else if(rowDispflg[k] == true){
							nullflg=true;
							break;
						}
						//�擾�̃G���[
						if(rowDispflg[koteirow + k] == undefined){
							return;
						}
					}
					//�t���O�ݒ�
					rowDispflg[eval(koteirow)] = nullflg;
					//�H�����v�s�Ƀt���O�ݒ�
					rowDispflg[i] = nullflg;
					kotei_cont++;
				}
			}
		}else{												//��[����]
			//�񃋁[�v
			for(j = 0; j < cnt_sample && !nullflg ; j++){

				//XML�f�[�^�擾
				haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
				kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

				//�z���ɒl�������Ă��� ���� 0�łȂ�
				if(haigo != "" && haigo != 0){
					nullflg = true;
				}
			}
			rowDispflg[i] = nullflg;
		}
	}

	//�����\������
	if(document.frm00.btnSaikeisan == undefined){
		//�e�[�u����\������
		for(i = 0; i < rowDispflg.length ; i++){
			if(!rowDispflg[i]){
				//�������e�[�u������\��
				document.getElementById("tableRowL_"+i).style.display = "none";
				//�������e�[�u���E��\��
				document.getElementById("tableRowR_"+i).style.display = "none";
			}
		}
	}else{
		//�e�[�u����\������
		for(i = 0; i < rowDispflg.length ; i++){
			if(!rowDispflg[i]){
				parent.detail.document.getElementById("tableRowR_"+i).style.display = "none";
			}
		}
	}

}
//�yQP@30297�zadd start 20140501
//========================================================================================
//�����֘A����
//�쐬�ҁFY.Nishigawa
//�쐬���F2009/10/23
//�T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo2() {

  var obj = xmlUSERINFO_O;
  var GamenId;
  var KinoId;
  var reccnt;
  var i;

  //�������[�v
  reccnt = funGetLength(obj);
  for (i = 0; i < reccnt; i++) {

      GamenId = funXmlRead(obj, "id_gamen", i);

      //�R�X�g�e�[�u���Q�Ɖ��
      if (GamenId.toString() == ConGmnIdCostTblRef.toString()) {
          document.getElementById("btnCostTblRef").style.visibility = "visible";
      }
  }

  return true;
}

//========================================================================================
//�R�X�g�Q�ƃ{�^������
//========================================================================================
function funCostTblRef() {

  window.open("../SQ200CostTblRef/SQ200CostTblRef.jsp","_blank","menubar=no,resizable=yes");

  return true;

}
//�yQP@30297�zadd end 20140501

//ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start
//========================================================================================
// ���[�����M�{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// �T�v  �F���[�����M��ʂ�\��
//========================================================================================
function funMail(){
    //ͯ���ڰт�̫�юQ��
    var headerFrm = document.frm00;
    var mode = 1;		// �Z�b�V�����Ȃ�

 // 20150831 TT.Kitazawa del start �i�X�e�[�^�X���̍Ď擾�s�v�j
 /*
    // �X�e�[�^�X��񂪍Ď擾�O�ׁ̈A�ŐV�̃X�e�[�^�X���擾����
    var XmlId = "RGEN2020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2020O );


    //------------------------------------------------------------------------------------
    //                                  �X�e�[�^�X���擾
    //------------------------------------------------------------------------------------
    // XML�̏�����
    setTimeout("xmlFGEN2020I.src = '../../model/FGEN2020I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

*/
    //���݂̃X�e�[�^�X���
    var stnew_ary = new Array(5);
//  �����O�̃X�e�[�^�X�����ׁ̈A�c�a���Ď擾
//     stnew_ary[0] = funStatusSetting("1" ,headerFrm.hdnStatus_kenkyu.value);
//    stnew_ary[1] = funStatusSetting("2", headerFrm.hdnStatus_seikan.value);
//    stnew_ary[2] = funStatusSetting("3", headerFrm.hdnStatus_gentyo.value);
//    stnew_ary[3] = funStatusSetting("4", headerFrm.hdnStatus_kojo.value);
//    stnew_ary[4] = funStatusSetting("5", headerFrm.hdnStatus_eigyo.value);
/*
    stnew_ary[0] = funStatusSetting("1", funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, 0));
    stnew_ary[1] = funStatusSetting("2", funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, 0));
    stnew_ary[2] = funStatusSetting("3", funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, 0));
    stnew_ary[3] = funStatusSetting("4", funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, 0));
    stnew_ary[4] = funStatusSetting("5", funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, 0));
*/
    //������
    // 20150826 TT.Kitazawa mod start
//    var sisaku_ary = new Array(3);
    var sisaku_ary = new Array(4);
	//����No
    sisaku_ary[0] = headerFrm.txtShainCd.value + "-" + headerFrm.txtNen.value + "-" + headerFrm.txtOiNo.value + "-" + headerFrm.txtEdaNo.value;
	//�i��
	sisaku_ary[1] = headerFrm.txtHinNm.value;
	//���Z����
	sisaku_ary[2] = headerFrm.txtKizitu.value;
	//�T���v��No.
	sisaku_ary[3] = headerFrm.hdnNo_iraisampleForMail.value;
	// 20150826 TT.Kitazawa mod end

	//�A�����[�����M�i�������Z�X�e�[�^�X�X�V�j
    //    ���[�U���A������A�X�e�[�^�X����n��
	funMailGenkaSisan(xmlUSERINFO_O, sisaku_ary, stnew_ary);

}
//ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 end

//20160513  KPX@1600766 ADD start
//========================================================================================
// ���e�������擾����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/04/18
// ����  �F�Ȃ�
// �߂�l�F���ׂĊJ��:9�A�P���J��:1�A�P���J���s�F0
// �T�v  �F�O���[�v��ВP���J���t���O�̎擾���s��
//========================================================================================
function funGetTankaFlg() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP0630";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA100");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA100I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA100O);

    // �߂�l�̐ݒ�i�P���J���s�j
    var ret = "0";

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return ret;
    }

    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0630, xmlReqAry, xmlResAry, 1) == false) {
    	return ret;
    }

    ret = funXmlRead(xmlResAry[2], "value1", 0);
    //��ЃR�[�h���o�^����Ă��Ȃ���
    if (ret == "") ret = "0";

    return ret;

}
//20160513  KPX@1600766 ADD end
//20160617  KPX@1502111_No.5 ADD start
//========================================================================================
// ���ƌ����A�g���擾�E�X�V����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/17
// ����  �F�Ȃ�
// �߂�l�F
// �T�v  �F�A�g���Ă��鎩�ƌ������A���Z�z���f�[�^�̒P���X�V���s��
//         �H��F������(�H��X�e�[�^�X��2)�A���ǁF������(���ǃX�e�[�^�X��2)
//========================================================================================
function funRenkeiGenryo() {

    var headerFrm = parent.header.document.frm00;
    var XmlId = "RGEN2280";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2280");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2280I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2280O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
    	return false;
    }

    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2280, xmlReqAry, xmlResAry, 1) == false) {
    	return false;
    }

    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    //���ƌ����A�g�f�[�^����

        //�����擾
        var reccnt = funGetLength(xmlResAry[2]);
        //�A�g�`�F�b�N�p
        headerFrm.hdnRenkeiStatus_eigyo.value = "";
        //�A�g��c�ƃX�e�[�^�X
        var st_eigyo = "";
        for (var i=0 ; i<reccnt; i++) {
        	//�A�g��c�ƃX�e�[�^�X
        	st_eigyo = funXmlRead_3(xmlResAry[2], "table", "st_eigyo", 0, i);
        	//���Z�z���X�V��
        	chkKosin = funXmlRead_3(xmlResAry[2], "table", "chkKosin", 0, i);
        	//�c�ƂR�ȏ�A���Z�z�����X�V���A�Čv�Z�t���Oon
        	if (st_eigyo >= 3) {
        		if (chkKosin == "1") {
        			//�Čv�Z�t���O��on�ɂ���
        			setFg_saikeisan();
        		}
        	} else {
        		//�H��F�X�e�[�^�X�������ɂ��鎞�A���͐��ǁF�X�e�[�^�X�������ɂ��鎞
        		//�@�c�Ɓ��R�̘A�g����������΃G���[
        		headerFrm.hdnRenkeiStatus_eigyo.value = st_eigyo;
        	}
        }
    }

    return true;

}

//========================================================================================
// ���Z���~�m�F����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/17
// ����  �F�Ȃ�
// �߂�l�F
// �T�v  �F�A�g�o�^���Ă���T���v���̎���V�[�P���X���擾����
//========================================================================================
function chkHaigoLink() {

    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

    var XmlId = "JW821";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2260");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2260I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2260O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJW821, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    detailFrm.hdnRenkeiSeqShisaku.value = "";
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	//�A�g�o�^���Ă��鎎��V�[�P���X��ۑ�
    	detailFrm.hdnRenkeiSeqShisaku.value = funXmlRead_3(xmlResAry[2], "table", "seq_shisaku", 0, 0);
    }

    return true;

}
//20160617  KPX@1502111_No.5 ADD end
//20160622  KPX@1502111_No.10 ADD start
//========================================================================================
// �T���v���R�s�[����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�Ȃ�
// �߂�l�F
// �T�v  �F�T���v���R�s�[�w���ʂ�\��
//========================================================================================
function funSampleCopy() {

    var headerFrm = parent.header.document.frm00;
    var detailDoc = parent.detail.document;
    var frm = document.frm00;   //̫�тւ̎Q��
    var sample;					//�T���v����
//20160826 KPX@1502111_No.10 MOD Start
    var chkSaki;				//�R�s�[��`�F�b�N�t���O
    var chkMoto;				//�R�s�[���`�F�b�N�t���O
//	var chkCnt = 0;				//�`�F�b�N�J�E���g
    var chkCnt_saki = 0;		//�R�s�[��`�F�b�N�J�E���g
    var chkCnt_moto = 0;		//�R�s�[���`�F�b�N�J�E���g
//20160826 KPX@1502111_No.10 MOD End
    var retVal;

    var args = new Array();		//�q��ʂɓn���p�����[�^

    //�񐔎擾
	var recCnt = frm.cnt_sample.value;

	for( var j = 0; j < recCnt; j++ ){
		// �T���v����
		sample = detailDoc.getElementById("txtSample"+j).value;
		// �R�s�[��Ɋ܂߂邩�ǂ����̃t���O
		chkSaki = "0";
//20160826 KPX@1502111_No.10 ADD Start
		// �R�s�[���Ɋ܂߂邩�ǂ����̃t���O
		chkMoto = "0";
//20160826 KPX@1502111_No.10 ADD End
		// ���Z���~
		if (detailDoc.getElementById("hdnSisanChusi_"+j).value == "1") {
			chkSaki = "1";
		}
		// ���ڌŒ�`�F�b�N��ON
		if (detailDoc.getElementById("chkKoumoku_"+ j).checked) {
			chkSaki = "1";
		}
		
//20160826 KPX@1502111_No.10 ADD Start
		// �e���ڂɓ��͂����邩
		if(funSampCpy(j, "", 0)){
			chkMoto = "1";
		}
//20160826 KPX@1502111_No.10 ADD End
		
//20160826 KPX@1502111_No.10 MOD Start
		// �R�s�[��Ɋ܂߂Ȃ��T���v����
		if (chkSaki == '1') {
			//chkCnt = chkCnt + 1;
			chkCnt_saki = chkCnt_saki + 1;
		}
//20160826 KPX@1502111_No.10 MOD End
		
//20160826 KPX@1502111_No.10 ADD Start
		// �R�s�[���Ɋ܂߂Ȃ��T���v����
		if(chkMoto == '1') {
			chkCnt_moto = chkCnt_moto + 1;
		}
//20160826 KPX@1502111_No.10 ADD End
		
//20160826 KPX@1502111_No.10 MOD Start
		// �t���O��t������
//		args[j] = sample + ConDelimiter + chkSaki;
		args[j] = sample + ConDelimiter + chkSaki + ConDelimiter + chkMoto;
	}
//20160826 KPX@1502111_No.10 MOD End
	
//20160826 KPX@1502111_No.10 MOD Start
	//�S�ẴT���v�������Z���~�A���͍��ڌŒ�`�F�b�NON��
//	if (recCnt == chkCnt) {
	if (recCnt == chkCnt_saki || recCnt == chkCnt_moto) {
		// �R�s�[��ɓ����T���v�����Ȃ��ꍇ�A�G���[���b�Z�[�W��\��
        funErrorMsgBox(E000044);
        return false;
	}
//20160826 KPX@1502111_No.10 MOD End

	//�T���v���R�s�[��ʂ��N������
	var retVal = funOpenModalDialog("../SQ135SampleCopy/SQ135SampleCopy.jsp", args, "dialogHeight:250px;dialogWidth:450px;status:no;scroll:no");

    //�߂�l
    if(retVal == null){
    	return true;
    } else if(retVal[0] == "false"){  	// �I���{�^���ŕ���ꂽ�ꍇ
    	return true;
    } else {
    	//�߂�l�F[0]�R�s�[���T���v����A [1]�R�s�[��T���v����
    	//�R�s�[���A�R�s�[�悪������
    	if (retVal[0] == retVal[1]) {
    		//�㏑���̊m�F
    		chkSaki = 1;
    	//�R�s�[�悪�󂩂ǂ����`�F�b�N����
    	} else if (funSampCpy(retVal[0], retVal[1], 1)) {
    		//�S�ċ�̎�
    		chkSaki = 0;
    	} else {
    		//�ǂꂩ�ɒl�������Ă��鎞
    		chkSaki = 1;
    	}

    	if (chkSaki == 1) {
    		//�m�F���b�Z�[�W�\��
    		if(funConfMsgBox(I000018) == ConBtnYes){
    			//�R�s�[���s�FretVal[0] ��  retVal[1]
        		funSampCpy(retVal[0], retVal[1], 2);

    			//�Čv�Z�t���O��on�ɂ���
    			headerFrm.FgSaikeisan.value = "true";
    		}

    	} else {
			//�R�s�[���s�FretVal[0] ��  retVal[1]
    		funSampCpy(retVal[0], retVal[1], 2);

			//�Čv�Z�t���O��on�ɂ���
			headerFrm.FgSaikeisan.value = "true";
    	}

     }
}

//========================================================================================
// ��ʏ��R�s�[����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�@motoNo   �F�R�s�[����ԍ�
//       �F�AsakiNo   �F�R�s�[���ԍ�
//       �F�Bmode     �F�������[�h�i0�FmotoNo�f�[�^�`�F�b�N�A1�FsakiNo�f�[�^�`�F�b�N�A2�F�R�s�[���s�����j
// �߂�l�Ftrue:����I���imode=0,1 �̎��A�S�ċ�j�A false:�ُ킠��imode=0,1 �̎��A�����f�[�^����j
// �T�v  �F�T���v���R�s�[�w���ʂ�\��
//========================================================================================
function funSampCpy(motoNo, sakiNo, mode) {

	var detailDoc = parent.detail.document;
	var no;

	if(mode == 0) {
		no = motoNo;
	} else if(mode == 1) {
		no = sakiNo;
	} else if(mode == 2) {
		//�L�������i���j
		detailDoc.getElementById("txtYukoBudomari_"+sakiNo).value = detailDoc.getElementById("txtYukoBudomari_"+motoNo).value;
		//���Ϗ[�U�ʁi���j
		detailDoc.getElementById("txtHeikinZyu_"+sakiNo).value = detailDoc.getElementById("txtHeikinZyu_"+motoNo).value;

		if (document.frm00.radioKoteihi[0].checked){
			//�Œ��/�P�[�X
			detailDoc.getElementById("txtCaseKotei_"+sakiNo).value = detailDoc.getElementById("txtCaseKotei_"+motoNo).value;
			//���v/�P�[�X
			detailDoc.getElementById("txtCaseRieki_"+sakiNo).value = detailDoc.getElementById("txtCaseRieki_"+motoNo).value;

		}else{
			//�Œ��/KG
			detailDoc.getElementById("txtKgKotei_"+sakiNo).value = detailDoc.getElementById("txtKgKotei_"+motoNo).value;
			//���v/KG
			detailDoc.getElementById("txtKgRieki_"+sakiNo).value = detailDoc.getElementById("txtKgRieki_"+motoNo).value;
		}
		return true;

	} else {
		return false;
	}


	//�ҏW�f�[�^�ɐ��l�������Ă��邩�ǂ����`�F�b�N����
	//�L�������i���j
	if (detailDoc.getElementById("txtYukoBudomari_"+no).value != "") {
		return false;
	}
	//���Ϗ[�U�ʁi���j
	if (detailDoc.getElementById("txtHeikinZyu_"+no).value != "") {
		return false;
	}
	if (document.frm00.radioKoteihi[0].checked){
		//�Œ��/�P�[�X
		if (detailDoc.getElementById("txtCaseKotei_"+no).value != "") {
			return false;
		}
		//���v/�P�[�X
		if (detailDoc.getElementById("txtCaseRieki_"+no).value != "") {
			return false;
		}
	} else {
		//�Œ��/KG
		if (detailDoc.getElementById("txtKgKotei_"+no).value != "") {
			return false;
		}
		//���v/KG
		if (detailDoc.getElementById("txtKgRieki_"+no).value != "") {
			return false;
		}
	}
	return true;
}
//20160622  KPX@1502111_No.10 ADD end

//20170515 KPX@1700856 ADD Start
//========================================================================================
//�P��0�~�����擾����
//�쐬�ҁFShima
//�쐬���F2017/05/12
//����  �F�Ȃ�
//�߂�l�F�P��0�~�������R�[�h
//�T�v  �F�P��0�~�������R�[�h�̎擾���s��
//========================================================================================
function funGetTankaZeroGenryo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN2290";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2290O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }
    
	//XML�̏�����
	setTimeout("xmlFGEN2290I.src = '../../model/FGEN2290I.xml';", ConTimer);
    //���������Ɉ�v���������ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2290, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    var length = funGetLength(xmlResAry[2]);
    frm.hdnRenkeiSeqShisaku.value = "";
    for(var i = 0;i < length;i++){
    	if(0 < i){
    		frm.hdnTankaZeroGenryo.value += ConDelimiter;
    	}
    	frm.hdnTankaZeroGenryo.value += funXmlRead(xmlResAry[2], "value1", i);
    }

    return true;

}
//20170515 KPX@1700856 ADD End

