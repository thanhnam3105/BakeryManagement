//========================================================================================
// ���ʕϐ�
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
//�F�w��
 var color_read_font="#0060FF";
 var color_read="#ffffff";
 var color_henshu="#ffff88";
 var hiduke = "";

//========================================================================================
// ��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F��ʂ̏����������s��
//========================================================================================
function funLoad() {

    return true;

}


//========================================================================================
// �w�b�_�[�t���[���A�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_head() {

    //̫�тւ̎Q��
    var frm = document.frm00;

    //���ʏ��\��
    funGetKyotuInfo(1);

    return true;

}

//========================================================================================
// ���׃t���[���A�����\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad_dtl() {

    //��{���\��
    funKihonHyoji();

    return true;

}

//========================================================================================
// ���׃t���[���A��{���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
// �T�v  �F��ʂ̎��ޏ��\���������s��
//========================================================================================
function funShizaiHyoji() {

    //���ޏ��擾
    funGetShizaiInfo(1);

    return true;

}


//========================================================================================
// �������Z�A���ʏ��擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
    // MOD 2013/10/22 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010","FGEN2020");
//	    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I , xmlFGEN2020I );
//	    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O , xmlFGEN2020O );
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010","FGEN2020","FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I , xmlFGEN2020I , xmlFGEN2130I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O , xmlFGEN2020O , xmlFGEN2130O );
    // MOD 2013/10/22 QP@30154 okano end


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


    //����R�[�h�\��
    frm.txtShainCd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
    frm.txtNen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
    frm.txtOiNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
    frm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //�i���\��
    frm.txtHinNm.value = funXmlRead_3(xmlResAry[2], "kihon", "hin", 0, 0);

    //�̗p�T���v��No�ݒ�
    frm.txtSaiyou.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_nm", 0, 0);

    //���Z�����ݒ�
    frm.txtKizitu.value = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);
    hiduke = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);

    //�}�Ԏ�ސݒ�
    frm.txtShuruiEda.value = funXmlRead_3(xmlResAry[2], "kihon", "shurui_eda", 0, 0);

    // �˗��ԍ��ݒ�
    divIraiNo.innerText = "IR@" + funXmlRead_3(xmlResAry[2], "kihon", "no_irai", 0, 0);


    //�e���ڂ̐ݒ�i�w�b�_������Hidden���ځj
    frm.strHaitaCdShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shisaku_haita", 0, 0);
    frm.strHaitaNmShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_shisaku_haita", 0, 0);
    frm.strHaitaKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisha_haita", 0, 0);
    frm.strHaitaBusho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_busho_haita", 0, 0);
    frm.strHaitaUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user_haita", 0, 0);
    frm.strKengenMoto.value = funXmlRead_3(xmlResAry[2], "kihon", "kengen_moto", 0, 0);

    //���ݽð���̐ݒ�
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);
    // ADD 2013/10/22 QP@30154 okano start
    //���������t���O�ݒ�
    frm.hdnBusho_kenkyu.value = funXmlRead_3(xmlResAry[4], "table", "flg_kenkyu", 0, 0);
	frm.hdnBusho_seikan.value = funXmlRead_3(xmlResAry[4], "table", "flg_seikan", 0, 0);
	frm.hdnBusho_gentyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_gentyo", 0, 0);
	frm.hdnBusho_kojo.value = funXmlRead_3(xmlResAry[4], "table", "flg_kojo", 0, 0);
	frm.hdnBusho_eigyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_eigyo", 0, 0);
    // ADD 2013/10/22 QP@30154 okano end

    //���b�Z�[�W�{�b�N�X�\��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;
    //�r���̏ꍇ
    if(frm.strKengenMoto.value == "999"){
        //�\��
        funHaitaDisp();

        headerFrm.btnToroku.disabled = true;
    }

    // �w���v�̕\��
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "kihon", "help_file", 0, 0);

    //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Add Start
    frm.hdnSaiyou_column.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_no", 0, 0);
    //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Add End

    //------------------------------------------------------------------------------------
    //                                 �����ڰѕ`��
    //------------------------------------------------------------------------------------
    //�����ڰт̕`��
    parent.detail.location.href="GenkaShisan_dtl_Eigyo.jsp";

    //�����I��
    return true;
}
function funHaitaDisp() {
    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    //�e���ڂ̎擾
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

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //�e���ڂ̎擾
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
// �ð��������ʂ��N��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
//========================================================================================
function funStatusRireki_btn() {

	//̫�тւ̎Q��
	var headerFrm = parent.header.document.frm00;

    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

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
// �������Z�A��{���擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
    //�yQP@10713�z�V�T�N�C�b�N���� No13
    var Nisugata;


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
    frm.txtSeizoKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisya", 0, 0);


    //------------------------------------- �����H�� -------------------------------------
    frm.txtSeizoKojo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_jojyo", 0, 0);


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


    //---------------------------------- �e�ʁi���l���́j---------------------------------
    frm.txtYouryo.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    //�yQP@10713�z�V�T�N�C�b�N���� No.13
    Nisugata = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    setKanma(frm.txtYouryo);


    //---------------------------------- �e�ʁi�P�ʁj---------------------------------
    frm.txtYouryo_tani.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);
    //�yQP@10713�z�V�T�N�C�b�N���� No.13
    Nisugata = Nisugata + " " + funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);


    //-------------------------------------- ���萔 --------------------------------------
    frm.txtIrisu.value = funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    //�yQP@10713�z�V�T�N�C�b�N���� No.13
    Nisugata = Nisugata + " / " +  funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    setKanma(frm.txtIrisu);


    //--------------------------------------- �׎p ---------------------------------------
    //�yQP@10713�z�V�T�N�C�b�N���� No.13
    if(funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0) == null){
    	frm.txtNisugata.value = Nisugata;
    }else{
    	frm.txtNisugata.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0);
    }


    //------------------------------------- �戵���x -------------------------------------
    frm.txtOndo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ondo", 0, 0);


    //------------------------------------- �ܖ����� -------------------------------------
    frm.txtShomiKikan.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kikan", 0, 0);


// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//    //------------------------------------- ������] -------------------------------------
//    frm.txtGenkaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka", 0, 0);
//    setKanma(frm.txtGenkaKibo);
//
//
//    //----------------------------------- ������]�P�� -----------------------------------
//    //������]�P�ʃR���{�{�b�N�X����
//    funCreateComboBox(frm.ddlGenkaTani , xmlResAry[2] , 4, 1);
//    //������]�P�ʃR���{�{�b�N�X�I��
//    funDefaultIndex(frm.ddlGenkaTani, 4);
//
//
//    //------------------------------------- ������] -------------------------------------
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
//    //------------------------------------- �������� -------------------------------------
//    frm.txtHatubaiJiki.value = funXmlRead_3(xmlResAry[2], "kihon", "ziki_hatubai", 0, 0);
//
//
//    //------------------------------------- �̔����� -------------------------------------
//    //�̔����ԁi�ʔNor�X�|�b�g�j�R���{�{�b�N�X����
//    funCreateComboBox(frm.ddlHanbaiKikan_t , xmlResAry[2] , 6, 1);
//    funDefaultIndex(frm.ddlHanbaiKikan_t, 5);
//
//    //�̔����ԁi���l�j
//    frm.txtHanbaiKikan_s.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_suti", 0, 0);
//
//    //�̔����ԁi�����j�R���{�{�b�N�X����
//    funCreateComboBox(frm.ddlHanbaiKikan_k , xmlResAry[2] , 7, 1);
//    funDefaultIndex(frm.ddlHanbaiKikan_k, 6);
//
//
//    //------------------------------------- �v�攄�� -------------------------------------
//    frm.txtKeikakuUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_uriage", 0, 0);
//
//
//    //------------------------------------- �v�旘�v -------------------------------------
//    frm.txtKeikakuRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_rieki", 0, 0);
//
//
//    //------------------------------------ �̔��㔄�� ------------------------------------
//    frm.txtHanbaigoUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_uriage", 0, 0);
//
//
//    //------------------------------------ �̔��㗘�v ------------------------------------
//    frm.txtHanbaigoRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_rieki", 0, 0);
//
//
//    //------------------------------------ �������b�g ------------------------------------
//    frm.txtSeizoRot.value = funXmlRead_3(xmlResAry[2], "kihon", "seizo_roto", 0, 0);
// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	//�T���v�����̊�{���\��
    funKihonSubDisplay(xmlResAry[2],"divKihonSub");

    var recCnt;
    var i;
    //�񐔎擾
    recCnt = frm.cnt_sample.value;

    for(i = 0; i < recCnt;i++){
    	//------------------------------------- ������] -------------------------------------
    	setKanma(document.getElementById("txtGenkaKibo" + i));

        //----------------------------------- ������]�P�� -----------------------------------
        //������]�P�ʃR���{�{�b�N�X����
        funCreateComboBox(document.getElementById("ddlGenkaTani" + i) , xmlResAry[2] , 4, 1);

        //������]�P�ʃR���{�{�b�N�X�I��
        funDefaultIndex(document.getElementById("ddlGenkaTani" + i), 4);

        //------------------------------------- ������] -------------------------------------
        setKanma(document.getElementById("txtBaikaKibo" + i));

        //------------------------------------- �̔����� -------------------------------------
        //�̔����ԁi�ʔNor�X�|�b�g�j�R���{�{�b�N�X����
        funCreateComboBox(document.getElementById("ddlHanbaiKikan_t" + i) , xmlResAry[2] , 6, 1);
        funDefaultIndex(document.getElementById("ddlHanbaiKikan_t" + i), 5);

        //�̔����ԁi�����j�R���{�{�b�N�X����
        funCreateComboBox(document.getElementById("ddlHanbaiKikan_k" + i) , xmlResAry[2] , 7, 1);
        funDefaultIndex(document.getElementById("ddlHanbaiKikan_k" + i), 6);

        // ADD 2013/12/4 okano�yQP@30154�zstart
        setKanma(document.getElementById("txtSoteiButuryo_s" + i));
        // ADD 2013/12/4 okano�yQP@30154�zend
        // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
        //------------------------------------- �z�蕨�� -------------------------------------
        funCreateComboBox(document.getElementById("ddlSoteiButuryo_u" + i) , xmlResAry[2] , 8, 1);
        funDefaultIndex(document.getElementById("ddlSoteiButuryo_u" + i), 7);

        funCreateComboBox(document.getElementById("ddlSoteiButuryo_k" + i) , xmlResAry[2] , 9, 1);
        funDefaultIndex(document.getElementById("ddlSoteiButuryo_k" + i), 8);
        // ADD 2013/9/6 okano�yQP@30151�zNo.30 end

    }
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    //----------------------------------- �������Z����-------------------------------
    //frm.txtGenkaMemo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan", 0, 0);


    //----------------------------- �������Z�����i�c�ƘA���p�j --------------------------
    frm.txtGenkaMemoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan_eigyo", 0, 0);


    //------------------------------------ �R�[�h���� -------------------------------------
    frm.hdnCdketasu.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_ketasu", 0, 0);

    //------------------------------------------------------------------------------------
    //                            �������\���C�x���g����
    //------------------------------------------------------------------------------------
    //�������\���C�x���g�𔭐�������
    frm.BtnEveGenryo.fireEvent('onclick');

    //��ʐ���
    kengen_statusSetting();

    //�����I��
    return true;

}


//========================================================================================
// �����ҏW�A�ð���ҏW
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function kengen_statusSetting(){

    var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

    //�����擾
    var Kengen = headerFrm.strKengenMoto.value;

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	var recCnt;
    var i;

    // ADD 2013/10/22 QP@30154 okano start
    //�����擾
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;
    // ADD 2013/10/22 QP@30154 okano end

//20160628  KPX@1502111_No.10 ADD start
    //�T���v���R�s�[�{�^�������̐ݒ�
    // �{�������̎��g�p�s��
    kengenBottunSetting(detailFrm.btnSampleCopy);
    //�c�ƍ̗p�ȍ~�͎g�p�s��
    if (headerFrm.hdnStatus_eigyo.value > 3) {
    	detailFrm.btnSampleCopy.disabled = true;
    }
//20160628  KPX@1502111_No.10 ADD end

    //�񐔎擾
    recCnt = detailFrm.cnt_sample.value;
    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    //�r��
    if(Kengen.toString() == "999"){

    	//�e�R���|�[�l���g����
    	headerFrm.btnToroku.disabled=true;

        //20160628  KPX@1502111_No.10 ADD start
    	detailFrm.btnSampleCopy.disabled = true;
        //20160628  KPX@1502111_No.10 ADD end

    	headerFrm.txtKizitu.disabled=true;
    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    	detailFrm.txtYouryo.disabled=true;
    	detailFrm.txtYouryo.style.backgroundColor=color_read;

    	detailFrm.txtIrisu.disabled=true;
    	detailFrm.txtIrisu.style.backgroundColor=color_read;

    	detailFrm.txtNisugata.disabled=true;
    	detailFrm.txtNisugata.style.backgroundColor=color_read;

		// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
    	for(i = 0 ; i < recCnt ; i++){

//    		detailFrm.txtGenkaKibo.disabled=true;
//    		detailFrm.txtGenkaKibo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlGenkaTani.disabled=true;
//    		detailFrm.ddlGenkaTani.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
    		detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

//    		detailFrm.txtBaikaKibo.disabled=true;
//    		detailFrm.txtBaikaKibo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
    		// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

//    		detailFrm.txtSoteiButuryo.disabled=true;
//    		detailFrm.txtSoteiButuryo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHatubaiJiki.disabled=true;
//    		detailFrm.txtHatubaiJiki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
    		detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlHanbaiKikan_t.disabled=true;
//    		detailFrm.ddlHanbaiKikan_t.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaiKikan_s.disabled=true;
//    		detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlHanbaiKikan_k.disabled=true;
//    		detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//    		detailFrm.txtKeikakuUriage.disabled=true;
//    		detailFrm.txtKeikakuUriage.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

//    		detailFrm.txtKeikakuRieki.disabled=true;
//    		detailFrm.txtKeikakuRieki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaigoUriage.disabled=true;
//    		detailFrm.txtHanbaigoUriage.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaigoRieki.disabled=true;
//    		detailFrm.txtHanbaigoRieki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).style.backgroundColor=color_read;

    	}
    	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;

    }

    //�r���ȊO
    // ADD 2013/10/22 QP@30154 okano start
    //������
    else if(kenkyu == "1"){

    	//�e�R���|�[�l���g����
    	headerFrm.btnToroku.disabled=true;

    	//20160628  KPX@1502111_No.10 ADD start
    	detailFrm.btnSampleCopy.disabled = true;
        //20160628  KPX@1502111_No.10 ADD end

    	headerFrm.txtKizitu.disabled=true;
    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    	detailFrm.txtYouryo.disabled=true;
    	detailFrm.txtYouryo.style.backgroundColor=color_read;

    	detailFrm.txtIrisu.disabled=true;
    	detailFrm.txtIrisu.style.backgroundColor=color_read;

    	detailFrm.txtNisugata.disabled=true;
    	detailFrm.txtNisugata.style.backgroundColor=color_read;

    	for(i = 0 ; i < recCnt ; i++){

    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
    		detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
    		detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaigoUriage" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaigoRieki" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).style.backgroundColor=color_read;

    	}

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;
    }
    // ADD 2013/10/22 QP@30154 okano end
    else{
    	//�Ŕԍ��ɂ�萧��
    	var eda = headerFrm.txtEdaNo.value;
    	if( eda == 0 ){
    		detailFrm.txtYouryo.disabled="true";
	    	detailFrm.txtYouryo.style.backgroundColor=color_read;
    	}

    	//�̔����Ԑݒ�
    	changeKikan();
    	//�ð���ɂ�萧��
    	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
    	var st_seikan = headerFrm.hdnStatus_seikan.value;
    	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
    	var st_kojo = headerFrm.hdnStatus_kojo.value;
    	var st_eigyo = headerFrm.hdnStatus_eigyo.value;

    	//���݂̐��Y�Ǘ����X�e�[�^�X�@>= 2�@�y�с@�c�ƃX�e�[�^�X = 4�@�̏ꍇ
    	if( st_seikan >= 2 || st_eigyo == 4 ){

    		headerFrm.txtKizitu.disabled=true;
	    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    		detailFrm.txtYouryo.disabled=true;
	    	detailFrm.txtYouryo.style.backgroundColor=color_read;

	    	detailFrm.txtIrisu.disabled=true;
	    	detailFrm.txtIrisu.style.backgroundColor=color_read;

	    	detailFrm.txtNisugata.disabled=true;
	    	detailFrm.txtNisugata.style.backgroundColor=color_read;

			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
	    	for(i = 0; i < recCnt ;i++ ){
//	    		detailFrm.txtGenkaKibo.disabled=true;
//	    		detailFrm.txtGenkaKibo.style.backgroundColor=color_read;
	    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
	    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;
//	    	}

//		    	detailFrm.ddlGenkaTani.disabled=true;
//		    	detailFrm.ddlGenkaTani.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
		    	detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtBaikaKibo.disabled=true;
//		    	detailFrm.txtBaikaKibo.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
		    	detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

				// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
	    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

	    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
	    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

	    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
	    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
	    		// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

//		    	detailFrm.txtSoteiButuryo.disabled=true;
//		    	detailFrm.txtSoteiButuryo.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
		    	detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtHatubaiJiki.disabled=true;
//		    	detailFrm.txtHatubaiJiki.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
		    	detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

//		    	detailFrm.ddlHanbaiKikan_t.disabled=true;
//		    	detailFrm.ddlHanbaiKikan_t.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
		    	detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtHanbaiKikan_s.disabled=true;
//		    	detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
		    	detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

//		    	detailFrm.ddlHanbaiKikan_k.disabled=true;
//		    	detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
		    	detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtKeikakuUriage.disabled=true;
//		    	detailFrm.txtKeikakuUriage.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
		    	detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtKeikakuRieki.disabled=true;
//		    	detailFrm.txtKeikakuRieki.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
		    	detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

	    	}
	    	// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
	    }

    }

    return true;
}


//========================================================================================
// �̔����ԑI������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function changeKikan(){

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��
    // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
    var recCnt;
    //�񐔎擾
    recCnt = detailFrm.cnt_sample.value;

    var i;
    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

    // MOD 2013/7/2 shima�yQP@30151�zNo.37 start
    for(i = 0;i < recCnt;i++){

		//�I��l�擾
	//	var val = detailFrm.ddlHanbaiKikan_t.options[detailFrm.ddlHanbaiKikan_t.selectedIndex].value;
		var val = detailDoc.getElementById("ddlHanbaiKikan_t" + i).options[detailDoc.getElementById("ddlHanbaiKikan_t" + i).selectedIndex].value;

		//�X�|�b�g�@�̏ꍇ
		if( val == "2" ){

			//���Z���~��
			var chusi = detailDoc.getElementById("col_chusi" + i ).value;
//20160628  KPX@1502111 ADD start
			var koumokuchk =detailDoc.getElementById("col_kotei" + i ).value;
			if(koumokuchk == "1"){
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;
//20160628  KPX@1502111 ADD end
			} else if(chusi == "1"){
//			if(chusi == "1"){
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor="#c0c0c0";

				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor="#c0c0c0";
			}else{
//				detailFrm.ddlHanbaiKikan_k.disabled = false;
//				detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_henshu;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_henshu;

//				detailFrm.txtHanbaiKikan_s.readOnly=false;
//			    detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_henshu;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_henshu;
			}
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 1;
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
		}
		else{
//			detailFrm.ddlHanbaiKikan_k.selectedIndex = 0;
//			detailFrm.ddlHanbaiKikan_k.disabled = true;
//			detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).selectedIndex = 0;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = true;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//			detailFrm.txtHanbaiKikan_s.value="";
//			detailFrm.txtHanbaiKikan_s.readOnly=true;
//		    detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
			detailDoc.getElementById("txtHanbaiKikan_s" + i).value="";
			detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=true;
			detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			if( val == "1"){
				detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 2;
			} else {
				detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 0;
			}
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
		}
    }
    // MOD 2013/7/2 shima�yQP@30151�zNo.37 end

}

//========================================================================================
// �������Z�A�������擾���\������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�������Z�A�������擾
//========================================================================================
function funGetGenryoInfo(mode) {

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
    //                                   �������擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0012, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  �������\��
    //------------------------------------------------------------------------------------
    //���Z���\��
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");

    //�����I��
    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    //ͯ���ڰт�̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�������[�v
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //�������Z�i�c�Ɓj���
        if (GamenId.toString() == ConGmnIdGenkaShisanEigyo.toString()) {

            //�����ݒ�
            headerFrm.hdnKengen.value = KinoId.toString();

        }
    }

    return true;

}

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
        //�������Z��ʋN�����ʒm
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
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        else if (XmlId.toString() == "RGEN0090") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0090
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

     // ADD 2015/07/24 TT.Kitazawa�yQP@40812�zNo.1 start
        //���
        else if (XmlId.toString() == "RGEN2240"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2240
                    //���[�v�J�E���g
                    var recCnt;
                    var j;
                    //-------------------------- [table]�e�[�u���i�[ -------------------------
                    //�񐔎擾
                    recCnt = detailFrm.cnt_sample.value;
                    //XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){
                        //�񐔂ɑ΂���I�u�W�F�N�g�����݂���ꍇ
                        if(detailDoc.getElementById("chkInsatu"+j)){
                            //�I�u�W�F�N�g���`�F�b�N����Ă���ꍇ
                            if(detailDoc.getElementById("chkInsatu"+j).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN2240", "table");
                                }
                                // ����CD-�Ј�CD
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // ����CD-�N
                                funXmlWrite_Tbl(reqAry[i], "table", "nen", headerFrm.txtNen.value, recInsert);
                                // ����CD-�ǔ�
                                funXmlWrite_Tbl(reqAry[i], "table", "no_oi", headerFrm.txtOiNo.value, recInsert);
                                // ����CD-�}��
                                funXmlWrite_Tbl(reqAry[i], "table", "no_eda", headerFrm.txtEdaNo.value, recInsert);
                                // ����SEQ
                                funXmlWrite_Tbl(reqAry[i], "table", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, recInsert);
                                //===========================================================================
                                // ��ʕ\���̌v�Z���ڂ̒l��n���F
                                //   �Œ�t�@�C���̒l�͍Čv�Z���ĕۑ����Ă��Ȃ��ꍇ�A��ʂ̒l�ƈقȂ��
                                //===========================================================================
                                // �����v�i�~�j�^�P�[�X
                                funXmlWrite_Tbl(reqAry[i], "table", "genkakei", detailDoc.getElementById("genkakei"+j).value, recInsert);
                                // �����v�i�~�j�^��
                                funXmlWrite_Tbl(reqAry[i], "table", "genkakeiKo", detailDoc.getElementById("genkakeiKo"+j).value, recInsert);
                                // �����v�i�~�j�^Kg
                                funXmlWrite_Tbl(reqAry[i], "table", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, recInsert);
                                // ����
                                funXmlWrite_Tbl(reqAry[i], "table", "baika", detailDoc.getElementById("baika"+j).value, recInsert);
                                // �e���i���j
                                funXmlWrite_Tbl(reqAry[i], "table", "arari", detailDoc.getElementById("arari"+j).value, recInsert);

                                // XML���R�[�h�}���J�E���g+1
                                recInsert++;
                            }
                        }
                    }
                    break;
            }
        }
     // ADD 2015/07/24 TT.Kitazawa�yQP@40812�zNo.1 end

        //�o�^   *** �s�v�I ***
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
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", "0", 0);
                    // �̗p�T���v���m�n
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    // �H��@�S�����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // �H��@�S���H��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // ���萔
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // �׎p
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // ������]
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // ������]�P��CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // ������]
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // �z�蕨��
                    // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
                    // MOD 2013/11/11 QP@30154 okano start
//	                    var s = detailFrm.txtSoteiButuryo_s.value * 1000;
//	                    s = Math.round(s)/1000;
//	                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_s", s, 0);
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_s", detailFrm.txtSoteiButuryo_s.value, 0);
                    // MOD 2013/11/11 QP@30154 okano end
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_u", detailFrm.ddlSoteiButuryo_u.options[detailFrm.ddlSoteiButuryo_u.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_k", detailFrm.ddlSoteiButuryo_k.options[detailFrm.ddlSoteiButuryo_k.selectedIndex].value, 0);
                    // ADD 2013/9/6 okano�yQP@30151�zNo.30 end
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // �̔�����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // �v�攄��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // �v�旘�v
                    funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // �̔��㔄��
                    funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // �̔��㗘�v
                    funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);
                    // �������b�g
                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // �������Z����
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);
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
                        // �����v/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
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

                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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

        //�̔����ԁ@�ʔNor�X�|�b�g
        case 6:
            atbName = "nm_literal"; //���e������
            atbCd = "cd_literal"; //���e����CD
            tableNm = "tani_hanbai_T";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        //�̔����ԁ@����
        case 7:
            atbName = "nm_literal"; //���e������
            atbCd = "cd_literal"; //���e����CD
            tableNm = "tani_hanbai_K";           //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
        //�z�蕨�ʁ@�P��
        case 8:
            atbName = "nm_literal"; //���e������
            atbCd = "cd_literal"; //���e����CD
            tableNm = "sotei_buturyo_U"; //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;

        //�z�蕨�ʁ@����
        case 9:
            atbName = "nm_literal"; //���e������
            atbCd = "cd_literal"; //���e����CD
            tableNm = "sotei_buturyo_K"; //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;
        // ADD 2013/9/6 okano�yQP@30151�zNo.30 end

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
// �쐬���F2011/01/28
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
            	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
            	var No = obj.name.slice(12);
//                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kibo_genka_cd_tani", 0, 0)) {
            	if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kibo_genka_cd_tani", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima�yQP@30151�zNo.37 end
                break;
            case 5:    //�̔����ԁi�ʔNor�X�|�b�g�j
            	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
            	var No = obj.name.slice(16);
//            	if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kikan_hanbai_t_cd", 0, 0)) {
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kikan_hanbai_t_cd", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima�yQP@30151�zNo.37 end
                break;
            case 6:    //�̔����ԁi�����j
            	// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
            	var No = obj.name.slice(16);
//                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kikan_hanbai_k_cd", 0, 0)) {
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kikan_hanbai_k_cd", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima�yQP@30151�zNo.37 end
                break;
            // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
            case 7:    //�z�蕨�ʁi���i�P�ʁj
            	var No = obj.name.slice(17);
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "sotei_buturyo_u_cd", 0, No)) {
                    selIndex = i;
                }
                break;
            case 8:    //�z�蕨�ʁi���ԒP�ʁj
            	var No = obj.name.slice(17);
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "sotei_buturyo_k_cd", 0, No)) {
                    selIndex = i;
                }
                break;
            // ADD 2013/9/6 okano�yQP@30151�zNo.30 end
        }
    }

    //�����ޯ���̲��ޯ���w��
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//�T���v�����̊�{���e�[�u�����\��
//�쐬�ҁFH.Shima
//�쐬���F2013/7/2
//����  �F�@xmlUser �F�X�V���i�[XML��
//      �F�AObjectId�F�ݒ�I�u�W�F�N�gID
//�߂�l�F�Ȃ�
//�T�v  �F�T���v�����̊�{���e�[�u���\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funKihonSubDisplay(xmlData, ObjectId) {

	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

    var OutputHtml;                         //�o��HTML
    var i;                                  //���[�v�J�E���g
    var cnt_sample;

    var genka_kibo;							//��]����
    var genka_tani;							//
    var genka_tanicd;						//
    var baika_kibo;							//��]����
    var baika_tani;							//
    var sotei_buturyo;						//�z�蕨��
    // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
    var sotei_buturyo_s;
    var sotei_buturyo_u;
    var sotei_buturyo_k;
    // ADD 2013/9/6 okano�yQP@30151�zNo.30 end
    var hatubai_jiki;						//����[�i����
    var hanbai_kikan_t;						//�̔�����
    var hanbai_kikan_s;						//
    var hanbai_kikan_k;						//
    var keikaku_uriage;						//�v�攄��
    var keikaku_rieki;						//�v�旘�v
    var hanbaigo_uriage;					//�̔��㔄��
    var hanbaigo_rieki;						//�̔��㗘�v
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

    //���~�t���O�̐ݒ�
    for(i = 0;i < cnt_sample; i++){
    	//���~�t���O�擾
		var fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
        OutputHtml += "<input type=\"hidden\" id=\"col_chusi" + i + "\" name=\"col_chusi" + i + "\" value=\"" + fg_chusi + "\">";

//20160628  KPX@1502111 ADD start
        //���ڌŒ�`�F�b�N�t���O
        fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);
        OutputHtml += "<input type=\"hidden\" id=\"col_kotei" + i + "\" name=\"col_kotei" + i + "\" value=\"" + fg_koumokuchk + "\">";
//20160628  KPX@1502111 ADD end
    }

// MOD start 2015/09/07 TT.Kitazawa
//    OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;border-left-style:none;border-right-style:none;\">";
    OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;\">";
// MOD end 2015/09/07 TT.Kitazawa
    OutputHtml += "<tr>";

	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1,16 start
    //------------------------------------- ����`�F�b�N -------------------------------------
    // seq_shisaku
    for(i = 0; i < cnt_sample; i++){
    	OutputHtml += "<th class=\"columntitle\" height=\"20px\" width=\"215\">";
    	OutputHtml += "    <input type=\"checkbox\" name=\"chkInsatu" + i + "\" id=\"chkInsatu" + i + "\" height=\"19px\" value=\"" + i + "\" tabindex=\"6\"/>";
        OutputHtml += "</th>";
    }
    OutputHtml += "</tr><tr>";
    //------------------------------------- �T���v��No. -------------------------------------
    var nm_sample = "";
    // MOD start 2015/08/26 �yQP@40812�zNo.6 TT.Kitazawa
    var no_iraisample = "";

    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
    	nm_sample = funXmlRead_3(xmlData, tableKihonSubNm, "nm_sample", 0, i);
		if( no_iraisample == ""){
			no_iraisample = nm_sample;
		}
		else{
			no_iraisample = no_iraisample + "," + nm_sample;
		}

    	//HTML����
// MOD start 2015/09/07 TT.Kitazawa
//        OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"215\">";
        OutputHtml += "<td bgcolor=\"\" height=\"20px\" width=\"215\">";
// MOD end 2015/09/07 TT.Kitazawa
        OutputHtml += "    <input type=\"text\" class=\"table_text_view\" id = \"nm_sample" + i + "\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sample + "\" tabindex=\"-1\" />";
        OutputHtml += "</td>";
    }
	headerFrm.hdnNo_iraisampleForMail.value = no_iraisample;
    // MOD end 2015/08/26 �yQP@40812�zNo.6 2015/08/26 TT.Kitazawa
    OutputHtml += "</tr><tr>";
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 end

    // MOD 2014/8/7 shima�yQP@30154�zNo.63 start
    //------------------------------------- ��]���� -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        genka_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
        fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"160\">";
// MOD start 2015/09/07 TT.Kitazawa
//        OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"215\">";
        OutputHtml += "<td bgcolor=\"\" height=\"20px\" width=\"215\">";
// MOD end 2015/09/07 TT.Kitazawa
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px; class=\"table_text_disb\" onblur=\"setKanma(this);\" readonly value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"baikaTaniSetting("+ i +");\" disabled style=\"width:87px;height:16px;\" tabindex=\"6\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px;background-color:#c0c0c0\" class=\"table_text_disb\" onblur=\"setKanma(this);\" value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"baikaTaniSetting("+ i +");\" style=\"background-color:#c0c0c0;width:87px;height:16px;\" tabindex=\"6\" />";
        }else{
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" onblur=\"setKanma(this);\" value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"setFg_Henkou();baikaTaniSetting("+ i +");\" style=\"background-color:#ffff88;width:87px;height:16px;\" tabindex=\"6\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- ��]���� -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        baika_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_baika", 0, i);
    	baika_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#ffffff;width:125px;\" readonly class=\"table_text_disb\" value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onblur=\"setKanma(this);\" style=\"width:125px;background-color:#c0c0c0\" class=\"table_text_disb\"  value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onchange=\"setFg_Henkou();\" onblur=\"setKanma(this);\" style=\"width:125px;\" class=\"table_text_disb\"  value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20 start
    //------------------------------------- �̔����� -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" readonly style=\"width:70px;text-align:right;background-color:#ffffff;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;background-color:#c0c0c0;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}else{
    		OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"setFg_Henkou();changeKikan();\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20 end

    //------------------------------------- �z�蕨�� -------------------------------------
    // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
		sotei_buturyo_s = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo_s", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

		if( sotei_buturyo_s == "" ){
		} else {
			sotei_buturyo_s = sotei_buturyo_s * 1;
		}

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#ffffff;width:70px;height:19px;\" readonly class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" style=\"background-color:#ffffff;width:60px;\" disabled tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" disabled=\"true\" style=\"background-color:#ffffff;width:70px;\" readonly tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	// MOD 2013/12/4 okano�yQP@30154�zstart
//        		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"background-color:#c0c0c0;width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#c0c0c0;width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	// MOD 2013/12/4 okano�yQP@30154�zend
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" style=\"background-color:#c0c0c0;width:60px;\" tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" disabled=\"true\" style=\"background-color:#c0c0c0;width:70px;\" tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }else{
        	// MOD 2013/12/4 okano�yQP@30154�zstart
//        		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onchange=\"setFg_Henkou();\" style=\"width:70px;height:19px;text-align:right;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" onchange=\"setFg_Henkou();\" style=\"width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	// MOD 2013/12/4 okano�yQP@30154�zend
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:60px;\" tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" onChange=\"setFg_Henkou()\" disabled=\"true\" style=\"width:70px;\" tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    // ADD 2013/9/6 okano�yQP@30151�zNo.30 end

    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        sotei_buturyo = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" readonly class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" onchange=\"setFg_Henkou();\" style=\"ime-mode:active;\" class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- �������� -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        hatubai_jiki = funXmlRead_3(xmlData, tableKihonSubNm, "ziki_hatubai", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHatubaiJiki" + i + "\" readonly class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;\" onchange=\"setFg_Henkou();\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

	// DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20 start
/*    //------------------------------------- �̔����� �i��Ɉړ��j-------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" readonly style=\"width:70px;text-align:right;background-color:#ffffff;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;background-color:#c0c0c0;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}else{
    		OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"setFg_Henkou();changeKikan();\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";
*/
	// DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20 end

    //------------------------------------- �v�攄�� -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        keikaku_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_uriage", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" readonly name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;\" onchange=\"setFg_Henkou();\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- �v�旘�v -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        keikaku_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_rieki", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_act\" readonly value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_act\" value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuRieki" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------ �̔��㔄�� ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        hanbaigo_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_uriage", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//�Œ荀�ڃ`�F�b�N��͕ҏW�s��
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHanbaigoUriage" + i + "\" readonly class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHanbaigoUriage" + i + "\" class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoUriage" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------ �̔��㗘�v ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
    	hanbaigo_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_rieki", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHanbaigoRieki" + i + "\" readonly class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }else if(fg_chusi == "1"){
        	//���Z���~��͔w�i�F���O���[��
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHanbaigoRieki" + i + "\" class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoRieki" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }
        OutputHtml += "</td>";
    }
    // ADD 2014/8/7 shima�yQP@30154�zNo.63 end

    OutputHtml += "</tr><tr>";

    //------------------------------------ �������b�g ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XML�f�[�^�擾
        seizo_rot = funXmlRead_3(xmlData, tableKihonSubNm, "seizo_roto", 0, i);

    	//HTML����
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" name=\"txtSeizoRot" + i + "\" class=\"table_text_view\" readonly value=\"" + seizo_rot + "\" tabindex=\"-1\" />";
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr>";
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
// �쐬���F2011/01/28
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
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableKeisanNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var cnt_genryo;       //�s��
    var cnt_sample;       //��
    var table_size;       //�e�[�u����
    var txtReadonly;      //�e�L�X�g�{�b�N�X���͐ݒ�
    var txtClass;         //�e�L�X�g�{�b�N�X�w�i�F

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
    var kg_kotehi;        //�Œ��/KG
    var kg_genkake;       //�����v/KG
    var baika;            //����
    var arari;            //�e���i���j

    var i;                //���[�v�J�E���g
    var j;                //���[�v�J�E���g

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
    // ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 start
    var fg_koteichk = "";	// ���ڌŒ�`�F�b�N
    // ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 end


    //------------------------------------------------------------------------------------
    //                                    �����ݒ�
    //------------------------------------------------------------------------------------
    //�ҏW����
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        txtReadonly = "";
        txtClass = henshuOkClass;
    }
    //�{��+Excel����
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
    }

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
    // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//    	table_size = 160 * cnt_sample;
    table_size = 215 * cnt_sample;
    // MOD 2013/9/6 okano�yQP@30151�zNo.30 end

	// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
    //�����
//    OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";
	// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

	// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 start
    // �y���Z���ځz�̕\������
    var ary_hyoji_chk = new Array (cnt_sample);
	// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

    //����SEQ
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        seq_shisaku = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);

        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
        arrShisaku_seq[i] = seq_shisaku;
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End

        //����SEQ
        OutputHtml += "<input type=\"hidden\"  id=\"hdnSeq_Shisaku_" + i + "\" name=\"hdnSeq_Shisaku_" + i + "\" value=\"" + seq_shisaku + "\">";
    }

    //------------------------------------------------------------------------------------
    //                                  �e�[�u������
    //------------------------------------------------------------------------------------
    OutputHtml += "<table class=\"detail\" id=\"tblList3\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\" bordercolordark=\"#000000\">";

    //�T���v��No ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        nm_sanpuru = funXmlRead_3(xmlData, tableKeisanNm, "nm_sanpuru", 0, i);

        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td align=\"right\"  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td align=\"right\"  style=\"width:210px;height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
        //OutputHtml += "            <input type=\"text\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" id = \"txtSample" + i + "\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //���Z�� -----------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	// ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 start
    	// �\���`�F�b�N�̏�����
    	ary_hyoji_chk[i] = 0;
    	// ���ڌŒ�`�F�b�N
        fg_koteichk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
        // ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 end

        //XML�f�[�^�擾
        shisan_date = funXmlRead_3(xmlData, tableKeisanNm, "shisan_date", 0, i);

        //�yQP@00342�z���Z���~
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        var val = "";
        if(fg_chusi == "1"){
        	val = "���Z���~";
        }
        else{
        	// ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 star
        	// ���ڌŒ�`�F�b�N��ON�ŁA���Z�����ݒ肳��Ă��鎞�A�\������i���ǽð�����������Ȃ��j
        	if((shisan_date != "") && (fg_koteichk == "1") ) {
        		ary_hyoji_chk[i] = 1;
        	}
        	// ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.10 end

        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
        	////�yQP@10713�z�V�T�N�C�b�N���� No.132011/10/26 TT H.SHIMA ADD START �u���Z�������R�ȑO�͕\�����Ȃ��B�v
//            shisan_date = seikan_status_check(shisan_date);
            shisan_date = seikan_status_check(shisan_date, ary_hyoji_chk[i]);
            ////�yQP@10713�z�V�T�N�C�b�N���� No.132011/10/26 TT H.SHIMA ADD END   �u���Z�������R�ȑO�͕\�����Ȃ��B�v
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

            val = shisan_date;
        }

        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td style=\"height:18px;\">";
        // MOD 2013/ okano�yQP@30151�zNo.30 end
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\"  value=\"" + val + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" id=\"txtShisanHi_" + i + "\" readonly style=\"text-align:right;\"  value=\"" + val + "\" tabindex=\"-1\" />";
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //�����v/�P�[�X -------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kesu_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
//        kesu_genkake = seikan_status_check(kesu_genkake);
        kesu_genkake = seikan_status_check(kesu_genkake, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

        //���Z���~��͔�\��
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	kesu_genkake = "";
        }

		//�yQP@10713�z2011/10/26 TT H.SHIMA ADD START �u�u����(�~)/�����v�̕\���ɕύX�v
        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        if(kesu_genkake == ""){
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
        	// OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }
        else{
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
       		// OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "�i�~�j/�P�[�X\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "�i�~�j/�P�[�X\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }

        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD END    �u�u����(�~)/�����v�̕\���ɕύX�v
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //�����v/�� ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        ko_genkake = funXmlRead_3(xmlData, tableKeisanNm, "ko_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
//        ko_genkake = seikan_status_check(ko_genkake);
        ko_genkake = seikan_status_check(ko_genkake, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

        //���Z���~��͔�\��
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	ko_genkake = "";
        }

        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD START �u�u����(�~)/�����v�̕\���ɕύX�v
        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"width:height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        if(ko_genkake == ""){
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
        	//OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }
        else{
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
        	//OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "�i�~�j/��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "�i�~�j/��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }
        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD END    �u�u����(�~)/�����v�̕\���ɕύX�v
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //�����v/KG --------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        kg_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kg_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
//        kg_genkake = seikan_status_check(kg_genkake);
        kg_genkake = seikan_status_check(kg_genkake, ary_hyoji_chk[i]);
       // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

        //���Z���~��͔�\��
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	kg_genkake = "";
        }

        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD START �u�u����(�~)/�����v�̕\���ɕύX�v
        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        if(kg_genkake == ""){
        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" />";
        }
        else{
        	//�yQP@10713�z2012/03/06 TT H.SHIMA MOD START �u�����v/��\�����Ă����s��C���v
//        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "�i�~�j/kg&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "�i�~�j/kg&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//�yQP@10713�z2012/03/06 TT H.SHIMA MOD END   �u�����v/��\�����Ă����s��C���v
        }
        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD END    �u�u����(�~)/�����v�̕\���ɕύX�v
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //���� ------------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        baika = funXmlRead_3(xmlData, tableKeisanNm, "baika", 0, i);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
//        baika = seikan_status_check(baika);
        baika = seikan_status_check(baika, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end

        //���Z���~��͔�\��
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	baika = "";
        }

        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD START �u�u����(�~)/�����v�̕\���ɕύX�v
        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        if(baika == ""){
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
//        	OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }
        else{
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
//        	OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        }
        //�yQP@10713�z2011/10/26 TT H.SHIMA ADD END    �u�u����(�~)/�����v�̕\���ɕύX�v
        OutputHtml += "        </td>";


    }
    OutputHtml += "    </tr>";

    //�e���i���j ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XML�f�[�^�擾
        arari = funXmlRead_3(xmlData, tableKeisanNm, "arari", 0, i);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 star
//        arari = seikan_status_check(arari);
        arari = seikan_status_check(arari, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.10 end
        if(arari == ""){
        }
        else{
        	arari = arari + "�@��";
        }

        //���Z���~��͔�\��
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	arari = "";
        }

        //HTML����
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano�yQP@30151�zNo.30 end
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"arari" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";

    //------------------------------------------------------------------------------------
    //                                  HTML�o��
    //------------------------------------------------------------------------------------
    //HTML���o��
    obj.innerHTML = OutputHtml;

    //�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 Start
    if(saiyo_column > 0){
    	funSaiyoDisp(saiyo_column,arrShisaku_seq,cnt_sample);
    }
	//�y�V�T�N�C�b�NH24�N�x�Ή��z�C���T 2012/04/16 End

    return true;
}

//========================================================================================
// ���Y�Ǘ����ð���`�F�b�N
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �X�V�ҁFH.Shima
// �X�V���F2015/10/22
// ����  �Fval ���ڒl
//       �Fchk �\�� = 1�A��\�� = 0  �i���Z�����ݒ肳��Ă��鎞�A=1�j
//========================================================================================
// MOD 2015/10/22 TT.Shima�yQP@40812�zNo.10 start
// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.10 start
//function seikan_status_check(val){
function seikan_status_check(val, chk){
	// ���Z���ʂ̕\���F���ǽð�����������Ȃ�
	var headerDoc = parent.header.document; //ͯ���ڰт�Document�Q��
    var detailDoc = parent.detail.document; //�����ڰт�Document�Q��
    var headerFrm = headerDoc.frm00;        //ͯ���ڰт�̫�юQ��
    var detailFrm = detailDoc.frm00;        //�����ڰт�̫�юQ��

	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
    var st_seikan = headerFrm.hdnStatus_seikan.value;
    var st_gentyo = headerFrm.hdnStatus_gentyo.value;
    var st_kojo = headerFrm.hdnStatus_kojo.value;
    var st_eigyo = headerFrm.hdnStatus_eigyo.value;

//    //���Y�Ǘ����ð���� < 3�@�̏ꍇ
//    if( st_seikan < 3 ){
//    	val = "";
//    }
//
//	// ���Z�����ݒ肳��Ă��鎞�A�ð���Ɋ֌W�Ȃ��\��
//	if(chk == 0) {
//		val = "";
//	}

    //���Y�Ǘ����ð���� < 3�@�̏ꍇ
    // ���� ���Z�����ݒ肳��Ă��Ȃ��ꍇ�A�\�����Ȃ�
    if ( st_seikan < 3 && chk == 0) {
    	val = "";
    }

//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.10 end
//MOD 2015/10/22 TT.Shima�yQP@40812�zNo.10 end
    return val;
}

//========================================================================================
// ���F�{�^����������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
//========================================================================================
function funToroku() {

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

    //�S���Ҍ�����ʂ��N������
    retVal = funOpenModalDialog("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo_Status.jsp", args, "dialogHeight:380px;dialogWidth:500px;status:no;scroll:no");

	//�o�^������
	if(retVal){
		//�ĕ\��
	    funGetKyotuInfo(1);

	    // ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start
	    if(retVal == "mail") {
	    	// �I�����b�Z�[�W�̌�A���[���N��
	    	funMail();
	    }
        // ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 end

	    //�ύX�t���O������
	    headerFrm.FgHenkou.value=false;
	}
	//�o�^������
	else{
		//�������Ȃ�
	}

    return true;

}

//========================================================================================
// �ύX�t���O
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F�ύX�t���O��on�ɂ���
//========================================================================================
function setFg_Henkou(){

    //ͯ���ڰт�Document�Q��
    var headerFrm = parent.header.document.frm00;

    //�ύX�t���O��on�ɂ���
    headerFrm.FgHenkou.value = "true";

    return true;

}


//========================================================================================
// �������J���}�}��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �T�v  �F������3�����ɃJ���}��}������
//========================================================================================
function setKanma(obj){

    obj.value = funAddComma(obj.value);
    return true;
}

//========================================================================================
// �N���}��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/29
//========================================================================================
function setNenGetu(obj){
/*
	var val = obj.value;
	try{
		if(val == ""){
		}
		else{
			var nen = val.indexOf("�N");
			var tuki = val.indexOf("��");

			if(nen == -1){
				var ret_nen = val.slice(0,4);
				ret_nen = ret_nen + "�N" + val.slice(4);
				val = ret_nen;
			}
			if(tuki == -1){
				val = val + "��";
			}
		}
	}catch(e){

    }

    obj.value = val;
*/
}

//========================================================================================
// ���Z�����`�F�b�N
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// �X�V�ҁFH.Shima
// �X�V���F2012/04/05
//========================================================================================
function chkKizitu(obj){

	//�I�u�W�F�N�g��value�l�擾
	var val = obj.value;

	//�y�V�T�N�C�b�NH24�Ή��zNo41 Start
	//�z��ɕ���
	if(val.split("/").length == 3){
		//�u/�v�ŕ���
		strTemps = val.split("/");
	}else if(val.length == 6){
		//��؂蕶������6��
		strdate = val.substring(0,2) + "/" + val.substring(2,4) + "/" + val.substring(4,6);
		strTemps = strdate.split("/");
	}else if(val.length == 7){
		//��؂蕶������7��
		strdate = val.substring(0,3) + "/" + val.substring(3,5) + "/" + val.substring(5,7);
		strTemps = strdate.split("/");
	}else if(val.length == 8){
		//��؂蕶������8��
		strdate = val.substring(0,4) + "/" + val.substring(4,6) + "/" + val.substring(6,8);
		strTemps = strdate.split("/");
	}else{
		return true;
	}

	//�N��4�������̏ꍇ�ɁA�t�H�[�}�b�g��ύX
	if(strTemps[0].length < 4){

		//���݂̔N���擾
		var date = new Date();						//�I�u�W�F�N�g�̐���
		var year = date.getYear().toString();		//���݂̔N���擾
		var f_year = year.substring(0,1);			//�N�̈ꕶ���ڂ��擾�i�t�H�[�}�b�g�p�j

		//�N�̈ꕶ���ڂɌ��݂̔N�����w��
		var formatdate = f_year + ("00" + strTemps[0]).slice(-3) + "/" + ("0" + strTemps[1]).slice(-2) + "/" + ("0" + strTemps[2]).slice(-2)

		val = formatdate;
	}else if(strTemps[0].length >= 5){
		return true;
	}
	//�y�V�T�N�C�b�NH24�Ή��zNo41 End

	if(hiduke == val){
//		return;
		return false;
	}

	hiduke = val;

	//�Ó��ȓ��t�̏ꍇ�Ƀ`�F�b�N
	if(isDate(val)){

		//�j���z��
		var myTbl = new Array("��","��","��","��","��","��","�y");
		var arrDate = val.split("/");

		//���͓��t�ݒ�
		var date_nyuryoku  = new Date(arrDate[0] , arrDate[1] - 1 ,arrDate[2]);

		//���ݓ����ݒ�
		var date_chk = new Date();
		date_chk  = new Date(date_chk.getYear() , date_chk.getMonth() ,date_chk.getDate());

		//�ߋ����̃`�F�b�N
		if(date_nyuryoku.getTime() < date_chk.getTime()){

			//���b�Z�[�W�\��
			funInfoMsgBox(E000020);

			//�l������
			hiduke = "";
			obj.value="";
//			return;
			return false;



			//�m�F���b�Z�[�W
			/*if(funConfMsgBox(E000015) == ConBtnYes){
				return;
			}
			else{
				obj.value="";
				return;
			}*/
		}

		//5�c�Ɠ��`�F�b�N
		var eigyo = 5;
		for(var i=0; i<eigyo; i++){
			//���ݓ����擾
			var baseSec = date_chk.getTime();

			//���� * 1���̃~���b���i1�����j
		    var addSec = 1 * 86400000;

		    //���ݓ�����1�������Z
		    var targetSec = baseSec + addSec;

		    //���Z������ݒ�
		    date_chk.setTime(targetSec);

		    //���Z�����̗j���擾
		    var youbi = myTbl[date_chk.getDay()];

		    //�y�E���̏ꍇ
		    if(youbi == "�y" || youbi == "��"){
		    	//�J�E���g���Ȃ�
		    	i--;
		    }
		}

		//�c�Ɠ��`�F�b�N�i���͓���5�c�Ɠ��ȓ��̏ꍇ�j
		if( date_nyuryoku.getTime() <= date_chk.getTime() ){
			//�m�F���b�Z�[�W
			if(funConfMsgBox(E000016) == ConBtnYes){
//				return;
				return true;
			}
			else{
				hiduke = "";
				obj.value="";
//				return;
				return false;
			}
		}
	}
	return true;
}
//���t�Ó����`�F�b�N
function isDate(text) {
    if (text.length == 0 || text== "") {
        return false;
    }
    var arrDate = text.split("/");
    if(arrDate.length == 3) {
        var date = new Date(arrDate[0] , arrDate[1] - 1 ,arrDate[2]);
        if(date.getFullYear() == arrDate[0] &&
          (date.getMonth() == arrDate[1] - 1) &&
           date.getDate() == arrDate[2]) {
            return true;
        }
    }
    return false;
}




//========================================================================================
// �󔒒u���֐��i"" �� "&nbsp;"�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
// ����  �F�@obj    :���W�I�{�^���I�u�W�F�N�g
// �T�v  �F���W�I�{�^���̌����ҏW
//========================================================================================
function kengenBottunSetting(obj){

    //ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //�ҏW����
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.disabled = false;
    }
    //�{��+Excel����
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.disabled = true;
    }
  //20160628  KPX@1502111_No.10 ADD start
    //���ID=190�A�{�������̏ꍇ
    else if(Kengen.toString() == 91){
        obj.disabled = true;
    }
  //20160628  KPX@1502111_No.10 ADD end
    return true;
}

//========================================================================================
// ����0���߁A�؂�グ����
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
// �ҏW�ҁFH.Shima
// �ҏW���F2013/07/02
// ����  �F�@�I�u�W�F�N�g
// �T�v  �F�����P�ʁ������P�ʂ֐ݒ肷��
//========================================================================================
function baikaTaniSetting(obj){

    var detailFrm = document.frm00;        //�����ڰт�̫�юQ��
	var detailDoc = parent.detail.document;

//    detailFrm.txtBaikaTani.value = detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].innerText;
    detailDoc.getElementById("txtBaikaTani" + obj).value = detailDoc.getElementById("ddlGenkaTani" + obj ).options[detailDoc.getElementById("ddlGenkaTani" + obj).selectedIndex].innerText;

    return true;
}


//========================================================================================
// ���t�t�H�[�}�b�g�ݒ�Ăяo��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
// �I���{�^��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�Ȃ�
// �T�v  �F��ʂ��I�����A����f�[�^�ꗗ��ʂ֑J�ڂ���
//========================================================================================
function funEnd(){

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var mode = 1;

    //���F�{�^���Ƀt�H�[�J�X�Z�b�g
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               ���͍��ڂ̕ύX�m�F
    //------------------------------------------------------------------------------------
    if(frm.FgHenkou.value == "true"){
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

        return false;
    }else{
    	//�����Ȃ�

    }

	//�r������
	funcHaitaKaijo(mode)

    //��ʂ����
    parent.close();

    return true;
}

//========================================================================================
// �r����������
// �쐬�ҁFE.Nakamura
// �쐬���F2011/01/28
// ����  �Fmode  �F�������[�h
//           	1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�I���{�^���������@�r���������������
//========================================================================================
function funcHaitaKaijo(mode){

	//ͯ�ް̫�юQ��
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.strKengenMoto.value;

    //�ҏW����
    if( Kengen.toString() == "999" ){
    	return true;
    }
    else{

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
// ������
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
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
// �쐬���F2011/01/28
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
//	//���F�{�^���Ƀt�H�[�J�X�Z�b�g
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
//
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




//=================================== �X�e�[�^�X�ݒ�_�C�A���O�@JavaScript  ================================

//========================================================================================
// �ð���ݒ菉���\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function funLoad_Status(){

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var opener_detail = window.dialogArguments[1].frm00;		//����̫��
    var frm = document.frm00;

    //�߂�l�̏�����
	window.returnValue = false;

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var XmlId = "RGEN2170";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O);

    //------------------------------------------------------------------------------------
    //                                  ���ʏ��擾
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    var mode = 1;
    if (funReadyOutput_Status(XmlId, xmlReqAry, mode ) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2170, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

   	//�X�e�[�^�X�擾
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

    //���ݽð���ݒ�
    document.getElementById("divStatusKenkyu_now").innerHTML = funStatusSetting("1",st_kenkyu);
    document.getElementById("divStatusSeikan_now").innerHTML = funStatusSetting("2",st_seikan);
    document.getElementById("divStatusGentyo_now").innerHTML = funStatusSetting("3",st_gentyo);
    document.getElementById("divStatusKojo_now").innerHTML = funStatusSetting("4",st_kojo);
    document.getElementById("divStatusEigyo_now").innerHTML = funStatusSetting("5",st_eigyo);

    //�̗p�����NO�ݒ�
    funCreateComboBox(frm.ddlSaiyoSample , xmlResAry[2] , 1, 1);
    funDefaultIndex(frm.ddlSaiyoSample, 1);

    //���Z�˗�
	//���݂̉c�ƃX�e�[�^�X�@=�@1�@�̏ꍇ
	if(st_eigyo == 1){
		frm.chk[1].disabled = false;
	}
	else{
		frm.chk[1].disabled = true;
	}

	//�m�F����
	//���݂̉c�ƃX�e�[�^�X�@=�@2�@���@���Y�Ǘ����X�e�[�^�X = 3 �̏ꍇ
	if(st_eigyo == 2 && st_seikan == 3){
		frm.chk[2].disabled = false;
	}
	else{
		frm.chk[2].disabled = true;
	}

	//�̗p�L���m��i�̗p�L��j
	//�i���݂̉c�ƃX�e�[�^�X�@>=�@2�@���@���݂̉c�ƃX�e�[�^�X�@<=�@3�j�@���@���Y�Ǘ����X�e�[�^�X = 3 �̏ꍇ
	if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
		frm.chk[3].disabled = false;

	}
	else{
		frm.chk[3].disabled = true;
	}

	//�̗p�L���m��i�̗p�L��j
	//�i���݂̉c�ƃX�e�[�^�X�@>=�@2�@���@���݂̉c�ƃX�e�[�^�X�@<=�@3�j�@���@���Y�Ǘ����X�e�[�^�X = 3 �̏ꍇ
	if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
		frm.chk[4].disabled = false;

	}
	else{
		frm.chk[4].disabled = true;
	}

	//���Z�r���ł̕s�̗p����
	//���݂̐��Y�Ǘ����X�e�[�^�X�@< 3�@���@�c�ƃX�e�[�^�X < 4 �̏ꍇ
	if( st_seikan < 3 && st_eigyo < 4 ){
		frm.chk[5].disabled = false;

	}
	else{
		frm.chk[5].disabled = true;
	}

	return true;
}

//========================================================================================
// �̗p�����No�R���{�{�b�N�X�̐���
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function chkSaiyou(){

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var frm = document.frm00; //̫�юQ��

	//�ð���擾
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

	//�̗p�L���m��i�̗p�L��j �I����
	if(frm.chk[3].checked){
		//�̗p�����No�R���{�{�b�N�X���g�p�\
		frm.ddlSaiyoSample.disabled = false;
		frm.ddlSaiyoSample.style.backgroundColor=color_henshu;

	}
	//�̗p�L���m��i�̗p�L��j�ȊO �I����
	else{
		//�̗p�����No�R���{�{�b�N�X���g�p�s��
		frm.ddlSaiyoSample.disabled = true;
		frm.ddlSaiyoSample.style.backgroundColor=color_read;

		//�̗p�O�̽ð�����͍̗p�����NO������
		if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
			frm.ddlSaiyoSample.selectedIndex = 0;
		}
	}
}

//========================================================================================
// XML�t�@�C���ɏ������݁i�ð���ݒ��ʁj
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput_Status(XmlId, reqAry, mode) {

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var opener_detail = window.dialogArguments[1].frm00;		//����̫��
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	var detailDoc = window.dialogArguments[1];
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
    var frm = document.frm00;

    var i;
    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\���i�ð���ݒ�j
        if (XmlId.toString() == "RGEN2170") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0010
                    funXmlWrite(reqAry[i], "cd_shain", opener_header.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", opener_header.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", opener_header.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", opener_header.txtEdaNo.value, 0);
                    break;
            }
        }
        //�o�^
        else if(XmlId.toString() == "RGEN2180"){
        	switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2160
                	//�I��׼޵���ݒl�擾
                	var j;
                	var st_setting;
                	var radio = document.getElementsByName("chk");
                	for( j=0; j<radio.length; j++ ){
                		if( radio[j].checked ){
                			st_setting = radio[j].value;
                		}
                	}

					// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
                	//-------------------------- [kihon]�e�[�u���i�[ -------------------------
                	/*
                	//XML��������
                	funXmlWrite(reqAry[i], "st_setting", st_setting, 0);

                    funXmlWrite(reqAry[i], "cd_shain", opener_header.txtShainCd.value, 0);

                    funXmlWrite(reqAry[i], "nen", opener_header.txtNen.value, 0);

                    funXmlWrite(reqAry[i], "no_oi", opener_header.txtOiNo.value, 0);

                    funXmlWrite(reqAry[i], "no_eda", opener_header.txtEdaNo.value, 0);

                    funXmlWrite(reqAry[i], "dt_kizitu", opener_header.txtKizitu.value, 0);

                    funXmlWrite(reqAry[i], "yoryo", opener_detail.txtYouryo.value, 0);

                    funXmlWrite(reqAry[i], "su_iri", opener_detail.txtIrisu.value, 0);

                    funXmlWrite(reqAry[i], "nisugata", opener_detail.txtNisugata.value, 0);

                    funXmlWrite(reqAry[i], "genka", opener_detail.txtGenkaKibo.value, 0);

                    var genka_tani = opener_detail.ddlGenkaTani.options[opener_detail.ddlGenkaTani.selectedIndex].value;
                    funXmlWrite(reqAry[i], "genka_tani", genka_tani, 0);

                    funXmlWrite(reqAry[i], "baika", opener_detail.txtBaikaKibo.value, 0);

                    funXmlWrite(reqAry[i], "sotei_buturyo", opener_detail.txtSoteiButuryo.value, 0);

                    funXmlWrite(reqAry[i], "dt_hatubai", opener_detail.txtHatubaiJiki.value, 0);

                    var hanbai_t = opener_detail.ddlHanbaiKikan_t.options[opener_detail.ddlHanbaiKikan_t.selectedIndex].value;
                    funXmlWrite(reqAry[i], "hanbai_t", hanbai_t, 0);

                    funXmlWrite(reqAry[i], "hanabai_s", opener_detail.txtHanbaiKikan_s.value, 0);

                    var hanabai_k = opener_detail.ddlHanbaiKikan_k.options[opener_detail.ddlHanbaiKikan_k.selectedIndex].value;
                    funXmlWrite(reqAry[i], "hanabai_k", hanabai_k, 0);

                    funXmlWrite(reqAry[i], "keikakuuriage", opener_detail.txtKeikakuUriage.value, 0);

                    funXmlWrite(reqAry[i], "keikakurieki", opener_detail.txtKeikakuRieki.value, 0);

                    funXmlWrite(reqAry[i], "kuhaku_1", opener_detail.txtHanbaigoUriage.value, 0);

                    funXmlWrite(reqAry[i], "kuhaku_2", opener_detail.txtHanbaigoRieki.value, 0);

                    funXmlWrite(reqAry[i], "memo_eigyo", opener_detail.txtGenkaMemoEigyo.value, 0);

                    var no_saiyou = frm.ddlSaiyoSample.options[frm.ddlSaiyoSample.selectedIndex].value;
                    funXmlWrite(reqAry[i], "no_saiyou", no_saiyou, 0);

                    funXmlWrite(reqAry[i], "st_kenkyu", opener_header.hdnStatus_kenkyu.value, 0);

                    funXmlWrite(reqAry[i], "st_seikan", opener_header.hdnStatus_seikan.value, 0);

                    funXmlWrite(reqAry[i], "st_gentyo", opener_header.hdnStatus_gentyo.value, 0);

                    funXmlWrite(reqAry[i], "st_kojo", opener_header.hdnStatus_kojo.value, 0);

                    funXmlWrite(reqAry[i], "st_eigyo", opener_header.hdnStatus_eigyo.value, 0);

					*/
					// DEL 2013/7/2 shima�yQP@30151�zNo.37 end
					// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
                	//XML��������
                	funXmlWrite_Tbl(reqAry[i], "kihon", "st_setting", st_setting, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", opener_header.txtShainCd.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", opener_header.txtNen.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", opener_header.txtOiNo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", opener_header.txtEdaNo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "dt_kizitu", opener_header.txtKizitu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "yoryo", opener_detail.txtYouryo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "su_iri", opener_detail.txtIrisu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", opener_detail.txtNisugata.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_eigyo", opener_detail.txtGenkaMemoEigyo.value, 0);

                    var no_saiyou = frm.ddlSaiyoSample.options[frm.ddlSaiyoSample.selectedIndex].value;
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_saiyou", no_saiyou, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", opener_header.hdnStatus_kenkyu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", opener_header.hdnStatus_seikan.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", opener_header.hdnStatus_gentyo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", opener_header.hdnStatus_kojo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", opener_header.hdnStatus_eigyo.value, 0);


                    //-------------------------- [kihonsub]�e�[�u���i�[ -------------------------
                    //�T���v�����̊�{���̗񐔎擾
                    recCnt = opener_detail.cnt_sample.value;

                    //XML�֏�������
                    for( j = 0; j < recCnt; j++ ){

                    	if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2160", "kihonsub");
                        }

                        // ����SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

                        // ������]
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "genka", detailDoc.getElementById("txtGenkaKibo"+j).value, j);
                        // ������]�P��CD
                        var genka_tani = detailDoc.getElementById("ddlGenkaTani"+j).options[detailDoc.getElementById("ddlGenkaTani"+j).selectedIndex].value
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "genka_tani", genka_tani, j);
                        // ������]
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
                        // �z�蕨��
                        // ADD 2013/9/6 okano�yQP@30151�zNo.30 start
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_s", detailDoc.getElementById("txtSoteiButuryo_s"+j).value, j);

                        var sotei_buturyo_u = detailDoc.getElementById("ddlSoteiButuryo_u"+j).options[detailDoc.getElementById("ddlSoteiButuryo_u"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_u", sotei_buturyo_u, j);

                        var sotei_buturyo_k = detailDoc.getElementById("ddlSoteiButuryo_k"+j).options[detailDoc.getElementById("ddlSoteiButuryo_k"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_k", sotei_buturyo_k, j);
                        // ADD 2013/9/6 okano�yQP@30151�zNo.30 end
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo", detailDoc.getElementById("txtSoteiButuryo"+j).value, j);

                        // ��������
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "dt_hatubai", detailDoc.getElementById("txtHatubaiJiki"+j).value, j);

                        var hanbai_t = detailDoc.getElementById("ddlHanbaiKikan_t"+j).options[detailDoc.getElementById("ddlHanbaiKikan_t"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbai_t", hanbai_t, j);

                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanabai_s", detailDoc.getElementById("txtHanbaiKikan_s"+j).value, j);

                        var hanabai_k = detailDoc.getElementById("ddlHanbaiKikan_k"+j).options[detailDoc.getElementById("ddlHanbaiKikan_k"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanabai_k", hanabai_k, j);

                        // �v�攄��
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikakuuriage", detailDoc.getElementById("txtKeikakuUriage"+j).value, j);
                        // �v�旘�v
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikakurieki", detailDoc.getElementById("txtKeikakuRieki"+j).value, j);
                        // �̔��㔄��
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "kuhaku_1", detailDoc.getElementById("txtHanbaigoUriage"+j).value, j);
                        // �̔��㗘�v
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "kuhaku_2", detailDoc.getElementById("txtHanbaigoRieki"+j).value, j);

                        //���~�t���O
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "chusi", detailDoc.getElementById("col_chusi" + j ).value , j);

                    }
                    // ADD 2013/7/2 shima�yQP@30151�zNo.37 end

                    break;
            }
        }
    }
    return true;
}

//========================================================================================
// �o�^�{�^�������i�ð���ݒ��ʁj
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function fun_Toroku(){

	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
	var opener_detail = window.dialogArguments[1].frm00;		//����̫��
    var frm = document.frm00;

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var XmlId = "RGEN2180";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2160");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2160I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2160O);

    //------------------------------------------------------------------------------------
    //                                  ���ʏ��擾
    //------------------------------------------------------------------------------------

    //�y�V�T�N�C�b�NH24�Ή��zNo41 Start
    //���Z�˗��X�e�[�^�X�ύX���Ɏ��Z�������`�F�b�N
    // MOD 2013/7/2 okano�yQP@30151�zNo.22 start
	//	var date;
	//	date = opener_header.txtKizitu.value
	//	if(frm.chk[1].checked && date.length > 0){
	//		if(!chkKizitu(opener_header.txtKizitu)){
	//			return false;
	//		}
	//	}
	var date;
	var st_eigyo = opener_header.hdnStatus_eigyo.value;
	var st_seikan = opener_header.hdnStatus_seikan.value;
	date = opener_header.txtKizitu.value
	if(((frm.chk[0].checked && (st_eigyo == 1 || (st_eigyo == 2 && st_seikan == 1))) || frm.chk[1].checked) && date.length > 0){
		if(!chkKizitu(opener_header.txtKizitu)){
			return false;
		}
	}
	// MOD 2013/7/2 okano�yQP@30151�zNo.22 end
	//�y�V�T�N�C�b�NH24�Ή��zNo41 End

	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.3 start
    //XML�̏�����("kihonsub"�����s�̓x�ǉ�������)
    setTimeout("xmlFGEN2160I.src = '../../model/FGEN2160I.xml';", ConTimer);
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.3 end

    //������XMĻ�قɐݒ�
    var mode = 1;
    if (funReadyOutput_Status(XmlId, xmlReqAry, mode ) == false) {
        return false;
    }

    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2180, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //����ү���ނ̕\��
    var dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

    	//MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start
        //����
//        funInfoMsgBox(dspMsg);

        //�߂�l�̐ݒ�
//	    window.returnValue = true;

    	window.returnValue = "1"
        // �X�e�[�^�X�ύX�̎��A���[���N���m�F���b�Z�[�W�ɕύX
        if(frm.chk[0].checked) {
        	// ����I�����b�Z�[�W
        	funInfoMsgBox(dspMsg);
        } else {
        	// �uYES�v�̎��A�ĕ\���チ�[���N��
        	if(funConfMsgBox(dspMsg) == ConBtnYes){
        		window.returnValue = "mail"
        	}
        }
      //MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start
    }

    //��ʂ����
    close(self);
    return false;

}

//========================================================================================
// ��ʂ����i�ð���ݒ��ʁj
// �쐬�ҁFY.Nishigawa
// �쐬���F2011/01/28
//========================================================================================
function fun_Close_st(){
	//��ʂ����
    close(self);
}

//========================================================================================
// �yQP@10713�z�׎p�����ݒ�
// �쐬�ҁFH.Shima
// �쐬���F2011/10/27
// �X�V�ҁFH.Shima
// �X�V���F2011/11/17
//========================================================================================
function fun_Nisugataset(){

	var frm = document.frm00;

	//�e�ʁA�e�ʒP�ʁA���萔�ɒl���ݒ肳��Ă���
	if(frm.txtYouryo.value != "" && frm.txtYouryo_tani.value != "" && frm.txtIrisu.value != ""){

		var NisugataStr = frm.txtYouryo.value + frm.txtYouryo_tani.value + "/" + frm.txtIrisu.value;

		//�׎p�ɒl���ݒ肳��Ă��Ȃ�
		if(frm.txtNisugata.value == ""){
			frm.txtNisugata.value = NisugataStr;

		//�׎p�ɒl���ݒ肳��Ă���A�ݒ肳�ꂽ�׎p�ƌv�Z�l����Ȃ�
		}else if(frm.txtNisugata.value != NisugataStr){
			//�Čv�Z�m�F
			if(funConfMsgBox(E000022) == ConBtnYes){
				frm.txtNisugata.value = NisugataStr;
			}
		}
	}

	return true;

}


//========================================================================================
//�y�V�T�N�C�b�NH24�N�x�Ή��z�̗p�T���v��No�ȊO�O���[����
//�쐬�ҁFH.SHIMA
//�쐬���F2012/04/16
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
//		alert(saiyoNm + " : " + arrSampleNm[i]);
		if(saiyoColumn != arrSampleSeq[i]){
			document.getElementById("txtSample" + i).style.background = gray;			//�T���v��No
			document.getElementById("txtShisanHi_" + i).style.background = gray;		//���Z��
			document.getElementById("genkakei" + i).style.background = gray;			//�����v/�P�[�X
			document.getElementById("genkakeiKo" + i).style.background = gray;			//�����v/��
			document.getElementById("txtKgGenkake_" + i).style.background = gray;		//�����v(�~)/kg
			document.getElementById("baika" + i).style.background = gray;				//����
			document.getElementById("arari" + i).style.background = gray;				//�e��(%)

		}
	}
}

// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 start
//========================================================================================
// ����{�^������
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// �T�v  �F������s��
//========================================================================================
function funInsatu(){

  //------------------------------------------------------------------------------------
  //                                    �ϐ��錾
  //------------------------------------------------------------------------------------
  var frm = document.frm00;    //̫�тւ̎Q��
  var headerFrm = parent.header.document.frm00; //ͯ���ڰт�Document�Q��
  var XmlId = "RGEN2240";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2240");
  var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2240I );
  var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2240O );
  var mode = 1;

  parent.header.focus();

  //------------------------------------------------------------------------------------
  //                               ���͍��ڂ̕ύX�m�F
  //------------------------------------------------------------------------------------
  if(frm.FgHenkou.value == "true"){
	  //���b�Z�[�W��\��
	  funInfoMsgBox(E000009);
	  return false;
  }

  //------------------------------------------------------------------------------------
  //                                     ���
  //------------------------------------------------------------------------------------
  //XML�̏�����
  setTimeout("xmlFGEN2240I.src = '../../model/FGEN2240I.xml';", ConTimer);

  //������XMĻ�قɐݒ�
  if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
      funClearRunMessage2();
      return false;
  }

  //����ݏ��A���ʏ����擾
  if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2240, xmlReqAry, xmlResAry, mode) == false) {
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
      //�����I��
      return false;
  }

  //̧���߽�̑ޔ�
  frm.strFilePath.value = funXmlRead(xmlFGEN2240O, "URLValue", 0);

  //�޳�۰�މ�ʂ̋N��
  funFileDownloadUrlConnect(ConConectGet, frm);

  //�����I��
  return true;

}
// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1 end

//ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.6 start
//========================================================================================
//���[�����M�{�^������
//�쐬�ҁFE.Kitazawa
//�쐬���F2015/03/03
//�T�v  �F���[�����M��ʂ�\��
//========================================================================================
function funMail(){
  //ͯ���ڰт�̫�юQ��
//	var opener_header = window.dialogArguments[0].frm00; 	//ͯ��̫��
    //ͯ���ڰт�̫�юQ��
    var headerFrm = document.frm00;

  //���݂̃X�e�[�^�X���
  var stnew_ary = new Array(5);
  stnew_ary[0] = funStatusSetting("1" ,headerFrm.hdnStatus_kenkyu.value);
  stnew_ary[1] = funStatusSetting("2", headerFrm.hdnStatus_seikan.value);
  stnew_ary[2] = funStatusSetting("3", headerFrm.hdnStatus_gentyo.value);
  stnew_ary[3] = funStatusSetting("4", headerFrm.hdnStatus_kojo.value);
  stnew_ary[4] = funStatusSetting("5", headerFrm.hdnStatus_eigyo.value);

  //������
  // 20150826 TT.Kitazawa mod start
//  var sisaku_ary = new Array(3);
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

//20160628  KPX@1502111_No.10 ADD start
//========================================================================================
// �T���v���R�s�[����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�Ȃ�
// �߂�l�F
// �T�v  �F�T���v���R�s�[�w���ʂ�\��
//========================================================================================
function funSampleCopy() {

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
		sample = detailDoc.getElementById("nm_sample"+j).value;
		// �R�s�[��Ɋ܂߂邩�ǂ����̃t���O
		chkSaki = "0";
//20160826 KPX@1502111_No.10 ADD Start
		// �R�s�[���Ɋ܂߂邩�ǂ����̃t���O
		chkMoto = "0";
//20160826 KPX@1502111_No.10 ADD End
		// ���Z���~
		if (detailDoc.getElementById("col_chusi"+j).value == "1") {
			chkSaki = "1";
		}
		// ���ڌŒ�`�F�b�N
		if (detailDoc.getElementById("col_kotei"+ j).value == "1") {
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
//			chkCnt = chkCnt + 1;
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

				//�ύX�t���O��on�ɂ���
				setFg_Henkou();
			}

		} else {
			//�R�s�[���s�FretVal[0] ��  retVal[1]
			funSampCpy(retVal[0], retVal[1], 2);

			//�ύX�t���O��on�ɂ���
			setFg_Henkou();
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
	var st_seikan = parent.header.document.frm00.hdnStatus_seikan.value;
	var no;

	if(mode == 0) {
		no = motoNo;
	} else if(mode == 1) {
		no = sakiNo;
	} else if(mode == 2) {
		//�ҏW�F���ǃX�e�[�^�X�˗��O�̂݃R�s�[��
		if (st_seikan < 2) {
			//��]�����A�P��
			detailDoc.getElementById("txtGenkaKibo"+sakiNo).value = detailDoc.getElementById("txtGenkaKibo"+motoNo).value;
			detailDoc.getElementById("ddlGenkaTani"+sakiNo).selectedIndex = detailDoc.getElementById("ddlGenkaTani"+motoNo).selectedIndex;
			//��]����A�P��
			detailDoc.getElementById("txtBaikaKibo"+sakiNo).value = detailDoc.getElementById("txtBaikaKibo"+motoNo).value;
			detailDoc.getElementById("txtBaikaTani"+sakiNo).value = detailDoc.getElementById("txtBaikaTani"+motoNo).value;
			//�̔�����
			detailDoc.getElementById("ddlHanbaiKikan_t"+sakiNo).selectedIndex = detailDoc.getElementById("ddlHanbaiKikan_t"+motoNo).selectedIndex;
			detailDoc.getElementById("txtHanbaiKikan_s"+sakiNo).value = detailDoc.getElementById("txtHanbaiKikan_s"+motoNo).value;
			detailDoc.getElementById("ddlHanbaiKikan_k"+sakiNo).selectedIndex = detailDoc.getElementById("ddlHanbaiKikan_k"+motoNo).selectedIndex;
			//�z�蕨��
			detailDoc.getElementById("txtSoteiButuryo_s"+sakiNo).value = detailDoc.getElementById("txtSoteiButuryo_s"+motoNo).value;
			detailDoc.getElementById("ddlSoteiButuryo_u"+sakiNo).selectedIndex = detailDoc.getElementById("ddlSoteiButuryo_u"+motoNo).selectedIndex;
			detailDoc.getElementById("ddlSoteiButuryo_k"+sakiNo).selectedIndex = detailDoc.getElementById("ddlSoteiButuryo_k"+motoNo).selectedIndex;
			//�z�蕨�ʔ��l
			detailDoc.getElementById("txtSoteiButuryo"+sakiNo).value = detailDoc.getElementById("txtSoteiButuryo"+motoNo).value;

			//����[�i�����i���������j
			detailDoc.getElementById("txtHatubaiJiki"+sakiNo).value = detailDoc.getElementById("txtHatubaiJiki"+motoNo).value;
			//�v�攄��^�N
			detailDoc.getElementById("txtKeikakuUriage"+sakiNo).value = detailDoc.getElementById("txtKeikakuUriage"+motoNo).value;
			//�v�旘�v�^�N
			detailDoc.getElementById("txtKeikakuRieki"+sakiNo).value = detailDoc.getElementById("txtKeikakuRieki"+motoNo).value;

			//�̔����ԑI��ύX����
			var val = detailDoc.getElementById("ddlHanbaiKikan_t" + sakiNo).options[detailDoc.getElementById("ddlHanbaiKikan_t" + sakiNo).selectedIndex].value;
			//�X�|�b�g�̏ꍇ
			if( val == "2" ){
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).style.backgroundColor=color_henshu;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).style.backgroundColor=color_henshu;
			} else {
				//�ʔN�̏ꍇ
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).disabled = true;
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).style.backgroundColor=color_read;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).readOnly=true;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).style.backgroundColor=color_read;
			}
		}

		//���ږ��F�󔒁i�ҏW�j
		detailDoc.getElementById("txtHanbaigoUriage"+sakiNo).value = detailDoc.getElementById("txtHanbaigoUriage"+motoNo).value;
		detailDoc.getElementById("txtHanbaigoRieki"+sakiNo).value = detailDoc.getElementById("txtHanbaigoRieki"+motoNo).value;

		return true;

	} else {
		return false;
	}

	//�ҏW�f�[�^�ɒl�������Ă��邩�ǂ����`�F�b�N����
	//�ҏW�F���ǃX�e�[�^�X�˗��O�̂݃R�s�[��
	if (st_seikan < 2) {
		//��]�����A�P��
		if (detailDoc.getElementById("txtGenkaKibo"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlGenkaTani"+no).selectedIndex > 0) {
			return false;
		}
		//��]����A�P��
		if (detailDoc.getElementById("txtBaikaKibo"+no).value != "") {
			return false;
		}
//20160826 KPX@1502111_No.10 MOD Start
//		if (detailDoc.getElementById("txtBaikaTani"+no).value != "") {
		if (detailDoc.getElementById("txtBaikaTani"+no).value != "" &&
				detailDoc.getElementById("txtBaikaTani"+no).value != "�@") {
			return false;
		}
//20160826 KPX@1502111_No.10 MOD End
		
		//�̔�����
		if (detailDoc.getElementById("ddlHanbaiKikan_t"+no).selectedIndex > 0) {
			return false;
		}
		if (detailDoc.getElementById("txtHanbaiKikan_s"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlHanbaiKikan_k"+no).selectedIndex > 0) {
			return false;
		}
		//�z�蕨��
		if (detailDoc.getElementById("txtSoteiButuryo_s"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlSoteiButuryo_u"+no).selectedIndex > 0) {
			return false;
		}
		if (detailDoc.getElementById("ddlSoteiButuryo_k"+no).selectedIndex > 0) {
			return false;
		}
		//�z�蕨�ʔ��l
		if (detailDoc.getElementById("txtSoteiButuryo"+no).value != "") {
			return false;
		}
		//����[�i�����i���������j
		if (detailDoc.getElementById("txtHatubaiJiki"+no).value != "") {
			return false;
		}
		//�v�攄��^�N
		if (detailDoc.getElementById("txtKeikakuUriage"+no).value != "") {
			return false;
		}
		//�v�旘�v�^�N
		if (detailDoc.getElementById("txtKeikakuRieki"+no).value != "") {
			return false;
		}
	}

	//���ږ��F�󔒁i�ҏW�j
	if (detailDoc.getElementById("txtHanbaigoUriage"+no).value != "") {
		return false;
	}
	if (detailDoc.getElementById("txtHanbaigoRieki"+no).value != "") {
		return false;
	}

	return true;
}
//20160628  KPX@1502111_No.10 ADD end
