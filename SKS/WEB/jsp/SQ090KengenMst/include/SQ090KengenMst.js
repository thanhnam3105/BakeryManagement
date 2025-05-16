//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    //��ʐݒ�
    funInitScreen(ConKengenMstId);

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
// �����R���{�{�b�N�X�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F�����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1120";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA160");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA160I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA160O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    if (frm.ddlKengen.selectedIndex == 0) {
        //��ʂ̏�����
        funClear();
        return true;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v���錴���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1120, xmlReqAry, xmlResAry, 1) == false) {
        tblList.style.display = "none";
        return false;
    }

    //�������̐ݒ�
    frm.txtKengenName.value = funXmlRead(xmlResAry[2], "nm_kengen", 0);

    //�ް�����������
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //��ʁA�@�\�A�Q�Ɖ\�ް����̐ݒ�
        funSetName();

        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";

        //������ү���ޔ�\��
        funClearRunMessage();
    }
    
    //������ү���ޔ�\��
    funClearRunMessage();

    //���݂̐���
    frm.btnCheckUser.disabled = false;

    return true;

}

//========================================================================================
// �o�^�A�X�V�A�폜�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@mode �F�����敪
//           1�F�o�^�A2�F�X�V�A3�F�폜
// �T�v  �F�\���f�[�^�̓o�^�A�X�V�A�폜�������s��
//========================================================================================
function funDataEdit(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1140";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA340");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA340I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA340O);
    var dspMsg;

    if (mode == 1) {
        dspMsg = I000002;
    } else if (mode == 2) {
        dspMsg = I000003;
    } else {
        dspMsg = I000004;
    }

    //�m�Fү���ނ̕\��
    if (funConfMsgBox(dspMsg) != ConBtnYes) {
        return false;
    }

    //������ү���ޕ\��
    funShowRunMessage();

    frm.hidEditMode.value = mode;

    //XML�̏�����
    setTimeout("xmlSA340I.src = '../../model/SA340I.xml';", ConTimer);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�o�^�A�X�V�A�폜���������s����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1140, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //����ү���ނ̕\��
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
        //����
        funInfoMsgBox(dspMsg);

        //��ʏ����擾�E�ݒ�
        if (funGetInfo(1) == false) {
            return false;
        }

        //��ʂ̏�����
        funClear();
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// �N���A�{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F��ʂ�����������
//========================================================================================
function funClear() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʂ̏�����
    frm.reset();
    frm.btnCheckUser.disabled = true;

    //�ꗗ�̸ر
    xmlSA160O.src = "";
    tblList.style.display = "none";
    funClearCurrentRow(tblList);

    //�����ޯ���̍Đݒ�
    funCreateComboBox(frm.ddlKengen, xmlSA170O, 1);

    //̫����ݒ�
    frm.ddlKengen.focus();

    return true;

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F����ʂɑJ�ڂ���
//========================================================================================
function funNext(mode) {

    var wUrl;

    //�J�ڐ攻��
    switch (mode) {
        case 0:    //Ͻ��ƭ�
            wUrl = "../SQ030MstMenu/SQ030MstMenu.jsp";
            break;
    }

    //�J��
    funUrlConnect(wUrl, ConConectPost, document.frm00);

    return true;
}

//========================================================================================
// �����@�\�ǉ���ʋN������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F�����@�\�ǉ���ʂ��N������
//========================================================================================
function funOpenKengenAdd() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var retVal;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //�����@�\�ǉ���ʂ��N������
    retVal = funOpenModalDialog("../SQ091KengenAdd/SQ091KengenAdd.jsp", this, "dialogHeight:430px;dialogWidth:650px;status:no;scroll:no");

    if (retVal != "") {
        //�@�\�̒ǉ�
        funAddRecNode(xmlSA160O, "SA160");
        funXmlWrite(xmlSA160O, "nm_kino", retVal.split(ConDelimiter)[0], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_gamen", retVal.split(ConDelimiter)[1], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "gamen", retVal.split(ConDelimiter)[2], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_kino", retVal.split(ConDelimiter)[3], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "kino", retVal.split(ConDelimiter)[4], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "id_data", retVal.split(ConDelimiter)[5], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "data", retVal.split(ConDelimiter)[6], funGetLength(xmlSA160O)-1);
        funXmlWrite(xmlSA160O, "biko", retVal.split(ConDelimiter)[7], funGetLength(xmlSA160O)-1);

        //�l��ݒ肵��XML�̍�۰��
        xmlBuff.load(xmlSA160O);
        xmlSA160O.load(xmlBuff);

        if (tblList.style.display == "none") {
           tblList.style.display = "block";
        }
        funClearCurrentRow(tblList);
    }

    return true;

}

//========================================================================================
// �@�\�폜����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F�I���s�̋@�\���폜����
//========================================================================================
function funDelList() {

    //�s���I������Ă��Ȃ��ꍇ
    if (funGetCurrentRow().toString() == "") {
        funErrorMsgBox(E000002);
        return false;
    }

    //�w�肳�ꂽ�@�\���폜����
    funSelectRowDelete(xmlSA160O);

    return true;

}

//========================================================================================
// �����g�p���[�U�m�F�{�^������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F�I������Ă��錠�����g�p���Ă��郆�[�U���̕\�����s��
//========================================================================================
function funCheckUser() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1130";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA350");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA350I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA350O);

    //������ү���ޕ\��
    funShowRunMessage();

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    //�����g�pհ�ސ����擾����
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1130, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    //ү���ނ̕\��
    funInfoMsgBox(funXmlRead(xmlSA350O, "su_user", 0) + I000010);

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;

}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1110";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA170");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA170I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA170O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1110, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����֘A�̏������s��
    funSaveKengenInfo();

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlKengen, xmlResAry[2], 1);

    return true;

}

//========================================================================================
// �����֘A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// �T�v  �F�������̑ޔ��A��ʐ�����s��
//========================================================================================
function funSaveKengenInfo() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //�ڍׁA�V�K�����A�폜���݂̐���
    frm.btnInsert.disabled = true;
    frm.btnUpdate.disabled = true;
    frm.btnDelete.disabled = true;

    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //����Ͻ�����ݽ
        if (GamenId.toString() == ConGmnIdKengenMst.toString()) {
            //�ҏW
            if (KinoId.toString() == ConFuncIdEdit.toString()) {
                frm.btnInsert.disabled = false;
                frm.btnUpdate.disabled = false;
                frm.btnDelete.disabled = false;
            }
        }
    }

    return true;

}

//========================================================================================
// ��ʁA�@�\�A�Q�Ɖ\�f�[�^���ݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�Ȃ�
// �T�v  �F�����Ɋ֌W���鍀�ڂ̖��̂�XML�֐ݒ肷��
//========================================================================================
function funSetName() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var greccnt;
    var kreccnt;
    var dreccnt;
    var GamenId;
    var KinoId;
    var DataId;
    var i;
    var j;
    var k;
    var l;
    var xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //ں��ތ����̎擾
    reccnt = funGetLength(xmlSA160O);
    greccnt = funGetLengthLocal(xmlAuthority, 0, 0, 1);

    for (i = 0; i < reccnt; i++) {
        //ID�̑ޔ�
        GamenId = funXmlRead(xmlSA160O, "id_gamen", i);
        KinoId = funXmlRead(xmlSA160O, "id_kino", i);
        DataId = funXmlRead(xmlSA160O, "id_data", i);

        for (j = 0; j < greccnt; j++) {
            //���ID�������ꍇ
            if (GamenId == funXmlReadLocal(xmlAuthority, "id_gamen", j, 0, 0, 1)) {
                //��ʖ��̂�ݒ�
                funXmlWrite(xmlSA160O, "gamen", funXmlReadLocal(xmlAuthority, "nm_gamen", j, 0, 0, 1), i);

                //ں��ތ����̎擾
                kreccnt = funGetLengthLocal(xmlAuthority, j, 0, 2);

                for (k = 0; k < greccnt; k++) {
                    //�@�\ID�������ꍇ
                    if (KinoId == funXmlReadLocal(xmlAuthority, "id_kino", j, k, 0, 2)) {
                        //�@�\���̂�ݒ�
                        funXmlWrite(xmlSA160O, "kino", funXmlReadLocal(xmlAuthority, "nm_kino", j, k, 0, 2), i);

                        //ں��ތ����̎擾
                        dreccnt = funGetLengthLocal(xmlAuthority, j, k, 3);

                        for (l = 0; l < dreccnt; l++) {
                            //�@�\ID�������ꍇ
                            if (DataId == funXmlReadLocal(xmlAuthority, "id_data", j, k, l, 3)) {
                                //�ް����̂�ݒ�
                                funXmlWrite(xmlSA160O, "data", funXmlReadLocal(xmlAuthority, "nm_data", j, k, l, 3), i);
                            }
                        }
                    }
                }
            }
        }
    }

    //�l��ݒ肵��XML�̍�۰��
    xmlBuff.load(xmlSA160O);
    xmlSA160O.load(xmlBuff);

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
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
        if (XmlId.toString() == "JSP1110") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA170
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdGenryoInfoMst, 0);
                    break;
            }

        //�������ޑI��
        } else if (XmlId.toString() == "JSP1120"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA160
                    funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    break;
            }

        //�����g�pհ�ފm�F���݉���
        } else if (XmlId.toString() == "JSP1130"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA350
                    funXmlWrite(reqAry[i], "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    break;
            }

        //�o�^�A�X�V�A�폜���݉���
        } else if (XmlId.toString() == "JSP1140"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA340
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "cd_kengen", frm.ddlKengen.options[frm.ddlKengen.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "nm_kengen", frm.txtKengenName.value, 0);
                    funXmlWrite_Tbl(reqAry[i], "ma_kengen", "kbn_shori", frm.hidEditMode.value, 0);
                    //�@�\�̐ݒ�
                    for (j = 0; j < funGetLength(xmlSA160O); j++) {
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "SA340", "ma_kinou");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "nm_kino", funXmlRead(xmlSA160O, "nm_kino", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_gamen", funXmlRead(xmlSA160O, "id_gamen", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_kino", funXmlRead(xmlSA160O, "id_kino", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_data", funXmlRead(xmlSA160O, "id_data", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "biko", funXmlRead(xmlSA160O, "biko", j), j);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "kbn_shori", frm.hidEditMode.value, j);
                    }
                    if (funGetLength(xmlSA160O) == 0) {
                        if (reqAry[i].xml == "") {
                            funAddRecNode_Tbl(reqAry[i], "SA340", "ma_kinou");
                        }
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "nm_kino", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_gamen", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_kino", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "id_data", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "biko", "", 0);
                        funXmlWrite_Tbl(reqAry[i], "ma_kinou", "kbn_shori", frm.hidEditMode.value, 0);
                    }
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;                  //�ݒ�XML�̌���
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
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    //����Ͻ�
            atbName = "nm_kengen";
            atbCd = "cd_kengen";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = ("000" + funXmlRead(xmlData, atbCd, i)).slice(-3) + "�F" + funXmlRead(xmlData, atbName, i);
            objNewOption.value = funXmlRead(xmlData, atbCd, i);
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// XML�ǂݏo������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@xmlRead      �F�ǂݏo����XML
//       �F�AAttributeName�F���ږ�
//       �F�BGamenIdx     �F���INDEX
//       �F�CKinoIdx      �F�@�\INDEX
//       �F�DDataIdx      �F�f�[�^INDEX
//       �F�Emode         �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �߂�l�F�擾�l
// �T�v  �FXML�t�@�C������w�荀�ڂ̒l���擾����B
//========================================================================================
function funXmlReadLocal(xmlRead, AttributeName, GamenIdx, KinoIdx, DataIdx, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var objNode;
    var RowNo;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        switch (mode) {
            case 1:    //���
                objNode = xmlRead.documentElement.childNodes;
                RowNo = GamenIdx;
                break;
            case 2:    //�@�\
                objNode = xmlRead.documentElement.childNodes.item(GamenIdx).childNodes;
                RowNo = KinoIdx;
                break;
            case 3:    //�Q�Ɖ\�f�[�^
                objNode = xmlRead.documentElement.childNodes.item(GamenIdx).childNodes.item(KinoIdx).childNodes;
                RowNo = DataIdx;
                break;
        }

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return "";
        }
        //�����l���擾
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML���R�[�h�����擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/04/09
// ����  �F�@xmlData �F�����擾XML
//       �F�AGamenIdx     �F���INDEX
//       �F�BKinoIdx      �F�@�\INDEX
//       �F�Cmode    �F���[�h
//           1�F��ʁA2�F�@�\�A3�F�Q�Ɖ\�f�[�^
// �߂�l�F���R�[�h����
// �T�v  �FXML�t�@�C�����R�[�h�������擾����
//========================================================================================
function funGetLengthLocal(xmlData, GamenIdx, KinoIdx, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var obj;

    reccnt = 0;

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        switch (mode) {
            case 1:    //���
                obj = xmlData.documentElement;
                break;
            case 2:    //�@�\
                obj = xmlData.documentElement.childNodes.item(GamenIdx);
                break;
            case 3:    //�Q�Ɖ\�f�[�^
                obj = xmlData.documentElement.childNodes.item(GamenIdx).childNodes.item(KinoIdx);
                break;
        }

        try {
            //�qɰ�ނ̑�������
            if (obj.hasChildNodes()) {
                //���݂���ꍇ
                reccnt = obj.childNodes.length;
            } else {
                //���݂��Ȃ��ꍇ
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

