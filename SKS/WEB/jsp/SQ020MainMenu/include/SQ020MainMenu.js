//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/19
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj;

    //��ʐݒ�
    funInitScreen(ConMainMenuId);

    //������ү���ޕ\��
    funShowRunMessage();

    //հ�޾���ݏ����擾
    funGetUserInfo(1);

    //������ү���ޔ�\��
    funClearRunMessage();

    obj = document.getElementById("btnMenu");
    if (obj && obj.type == "button") {
        //̫����ݒ�
        frm.btnMenu[0].focus();
    }

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
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2130I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2130O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(xmlReqAry, mode) == false) {
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
    funCreateMenuButton(xmlResAry[1], ConMainMenuId, "divBtn");

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
function funReadyOutput(reqAry, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {
        switch (i) {
            case 0:    //USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    //�yQP@00342�zFGEN2130
                break;
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
        case 1:    //����f�[�^�ꗗ
            wUrl = "../SQ040ShisakuList/SQ040ShisakuList.jsp";
            break;
        case 2:    //�}�X�^���j���[
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
        case 3:    //�������Z�ꗗ
            wUrl = "../SQ120ShisanList/SQ120ShisanList.jsp";
            break;
          //�yQP@30297�zadd start 20140501
        case 4:    //�����ޒ��B�����j���[
            wUrl = "../SQ170GenchoMenu/SQ170GenchoMenu.jsp";
            break;
          //�yQP@30297�zadd end 20140501
        case 9:    //���O�C��
            wUrl = "../SQ010Login/SQ010Login.jsp";
            break;
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

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
    if (funConfMsgBox("���C�����j���[" + I000001) == ConBtnYes) {
        //��ʂ����
        close();
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
