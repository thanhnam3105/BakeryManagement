//========================================================================================
// �����\������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00;    //̫�тւ̎Q��

    //��ʐݒ�
    funInitScreen(ConTankaBudomariId);

    //������ү���ޕ\��
    funShowRunMessage();

    //��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    //��ʂ̏�����
    funClearList();

    //������ү���ޔ�\��
    funClearRunMessage();

    frm.ddlKaisha.focus();

    return true;

}

//========================================================================================
// �����{�^����������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�Ȃ�
// �T�v  �F�����f�[�^�̌������s��
//========================================================================================
function funSearch() {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(1);

    //�w���߰�ނ��ް��擾
    funSearchData();
}

//========================================================================================
// ��������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�Ȃ�
// �T�v  �F�����f�[�^�̌������s��
//========================================================================================
function funSearchData() {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1320";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA790");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA790I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA790O);
    var PageCnt;
    var RecCnt;
    var ListMaxRow;

    //������ү���ޕ\��
    funShowRunMessage();

    //�I���s�̏�����
    funSetCurrentRow("");

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        //�ꗗ�̸ر
        funClearList();
        funClearRunMessage();
        return false;
    }

    //���������Ɉ�v���錴���ް����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1320, xmlReqAry, xmlResAry, 1) == false) {
        //HTML�̏o��
        divTableList.innerHTML = funCreateListHTML(xmlResAry[2]);
        funClearList();
        return false;
    }

    //HTML�̏o��
    divTableList.innerHTML = funCreateListHTML(xmlResAry[2]);

    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_row", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "list_max_row", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink(funGetCurrentPage(), PageCnt, "divPage", "tblList");
    spnCurPage.innerText = funGetCurrentPage() + "�^" + PageCnt + "�y�[�W";

    //�ް�����������
    if (funGetLength(xmlResAry[2]) > 0 && funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\��
        tblList.style.display = "block";
    } else {
        //��\��
        tblList.style.display = "none";

        //�ꗗ�̸ر
        funClearList();

        //������ү���ޔ�\��
        funClearRunMessage();
    }

    return true;

}

//========================================================================================
// �ꗗHTML��������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�@xmlObj �FXML�I�u�W�F�N�g
// �߂�l�F�쐬����HTML
// �T�v  �F�ꗗ��HTML���쐬����
//========================================================================================
// �X�V�ҁFM.Jinbo
// �X�V���F2009/05/19
// ���e  �F�P���̌����𐮐�8���A����2���ɕύX(�ۑ�\��17)
//========================================================================================
function funCreateListHTML(xmlObj) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;
    var OutputHtml;
    var ListMaxCol;
    var HeadAry = new Array();

    //�H�ꌏ���̎擾
    ListMaxCol = funXmlRead(xmlObj, "cnt_kojo", 0);

    //�H�ꖼ�̑ޔ�
    for (i = 0; i <= ListMaxCol; i++) {
        HeadAry[i] = funXmlRead(xmlObj, "disp_val" + (i+1), 0);
    }

    //����XML���擪�s(�H�ꖼ�̊i�[�s)�̍폜
    funSetCurrentRow(0);
    funSelectRowDelete(xmlObj);
    funSetCurrentRow("");

    //�ꗗ��HTML���쐬
    OutputHtml = ""
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"" + (310 + (85 * ListMaxCol)) + "px\">";
    OutputHtml += "    <colgroup>";
    OutputHtml += "        <col style=\"width:30px;\"/>";
    OutputHtml += "        <col style=\"width:80px;\"/>";
    OutputHtml += "        <col style=\"width:200px;\"/>";
    for (i = 0; i < ListMaxCol; i++) {
        //�H�ꌏ�����A��𐶐�����
        OutputHtml += "        <col style=\"width:85px;\"/>";
    }
    OutputHtml += "    </colgroup>";
    OutputHtml += "    <thead class=\"rowtitle\">";
    OutputHtml += "        <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "            <th class=\"columntitle\" width=\"30\">&nbsp;</th>";
    //�����敪�𔻒�
    if (frm.rdoGenryoKbn[0].checked) {
        //�����̏ꍇ
        OutputHtml += "            <th class=\"columntitle\" width=\"80\">����CD</th>";
        OutputHtml += "            <th class=\"columntitle\" width=\"200\">������</th>";
    } else {
        //���ނ̏ꍇ
        OutputHtml += "            <th class=\"columntitle\" width=\"80\">����CD</th>";
        OutputHtml += "            <th class=\"columntitle\" width=\"200\">���ޖ�</th>";
    }
    for (i = 0; i < ListMaxCol; i++) {
        //�H�ꌏ�����A��𐶐�����
        OutputHtml += "            <th class=\"columntitle\" width=\"85\">" + HeadAry[i] + "</th>";
    }
    OutputHtml += "        </tr>";
    OutputHtml += "    </thead>";
    OutputHtml += "    <tbody>";
    OutputHtml += "        <table class=\"detail\" id=\"tblList\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"#xmlSA790O\" datafld=\"rec\" style=\"width:" + (310 + (85 * ListMaxCol)) + "px;display:none\">";
    OutputHtml += "            <tr class=\"disprow\">";
    OutputHtml += "                <td class=\"column\" width=\"32\" align=\"right\"><span datafld=\"no_row\"></span></td>";
    OutputHtml += "                <td class=\"column\" width=\"83\" align=\"left\"><span datafld=\"cd_genryo\"></span></td>";
    OutputHtml += "                <td class=\"column\" width=\"202\" align=\"left\"><span datafld=\"nm_genryo\"></span></td>";
    for (i = 1; i <= ListMaxCol; i++) {
        //�H�ꌏ�����A��𐶐�����
        OutputHtml += "                <td class=\"column\" id=\"lblCol\" width=\"87\" align=\"right\"><span datafld=\"disp_val" + i + "\"></span></td>";
    }
    OutputHtml += "            </tr>";
    OutputHtml += "        </table>";
    OutputHtml += "    </table>";
    OutputHtml += "</table>";

    //�쐬����HTML��Ԃ�
    return OutputHtml;

}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList() {

    //�ꗗ�̸ر
    xmlSA790O.src = "";
    tblList.style.display = "none";
    spnRecCnt.innerText = "0";
    funSetCurrentRow("");
    funCreatePageLink(1, 1, "divPage", "tblList");
    funClearCurrentRow(tblList);
    funSetCurrentPage(1);
    spnRecInfo.style.display = "none";

}

//========================================================================================
// ����ʑJ�ڏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
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
// ��ʏ��擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�@mode  �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "JSP1310";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA140");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA140I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA140O);

    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    //����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP1310, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    //�����ޯ���̍쐬
    funCreateComboBox(frm.ddlKaisha, xmlResAry[2], 1);
    funDefaultIndex(frm.ddlKaisha, 1);

    return true;

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
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
        if (XmlId.toString() == "JSP1310") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA140
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", ConGmnIdTankaBudomari, 0);
                    break;
            }

        //�������݉���
        } else if (XmlId.toString() == "JSP1320"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA790
                    funXmlWrite(reqAry[i], "cd_kaisha", frm.ddlKaisha.options[frm.ddlKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_genryo", frm.txtGenryoCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_genryo", frm.txtGenryoName.value, 0);
                    if (frm.rdoGenryoKbn[0].checked) {
                        funXmlWrite(reqAry[i], "kbn_data", "0", 0);
                    } else if (frm.rdoGenryoKbn[1].checked) {
                        funXmlWrite(reqAry[i], "kbn_data", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "kbn_data", "", 0);
                    }
                    if (frm.rdoTaisho[0].checked) {
                        funXmlWrite(reqAry[i], "item_output", "0", 0);
                    } else if (frm.rdoTaisho[1].checked) {
                        funXmlWrite(reqAry[i], "item_output", "1", 0);
                    } else {
                        funXmlWrite(reqAry[i], "item_output", "", 0);
                    }
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage(), 0);
                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
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
    funClearSelect(obj, 1);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�󔒍s�̒ǉ�
        funClearSelect(obj, 2);
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

    //�����S����Ђ����o�^�̏ꍇ
    if (obj.length == 0) {
        //�󔒍s�̒ǉ�
        funClearSelect(obj, 2);
        return true;
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �f�t�H���g�l�I������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //��к���
                if (obj.options[i].value == funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0)) {
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
// ���o�^�H��̔w�i�F�ύX����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�Amode     �F���[�h
// �T�v  �F���o�^�H��̔w�i�F��ύX����
//========================================================================================
function funChangeHaishiColor(obj, mode) {

    var i;
    var j;
    var Idx;
    var reccnt = funGetLength(xmlSA790O);
    var ListMaxCol = funXmlRead(xmlSA790O, "cnt_kojo", 0);
    var val;

    //�w�i�F�̕ύX
    for (i = 0; i < reccnt; i++) {
        Idx = 1;
        for (j = ListMaxCol * i; j < ListMaxCol * (i+1); j++) {
            //�ް��̎擾
            val = funXmlRead(xmlSA790O, "disp_val" + Idx, i);

            if (val == "") {
                //���o�^�̏ꍇ
                if (ListMaxCol == 1 && reccnt == 1) {
                    lblCol.style.backgroundColor = haishiRowColor;
                } else {
                    lblCol[j].style.backgroundColor = haishiRowColor;
                }
            } else {
                //�o�^����Ă���ꍇ
                if (ListMaxCol == 1 && reccnt == 1) {
                    lblCol.style.backgroundColor = deactiveSelectedColor;
                } else {
                    lblCol[j].style.backgroundColor = deactiveSelectedColor;
                }

                //��ϕҏW���s��
                funXmlWrite(xmlSA790O, "disp_val" + Idx, funAddComma(val), i);
            }

            Idx += 1;
        }
    }

    return true;
}

//========================================================================================
// �I���s����(���[�J����)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̔w�i�F��ύX����B
//========================================================================================
function funChangeSelectRowColorLocal() {

    var i;
    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;
    var reccnt = funGetLength(xmlSA790O);
    var ListMaxCol = funXmlRead(xmlSA790O, "cnt_kojo", 0);

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (funGetCurrentRow() == "" ? 0 : funGetCurrentRow() / 1);

        //�w�i�F��ύX
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;
        for (i = ListMaxCol * oTR.rowIndex; i < ListMaxCol * (oTR.rowIndex+1); i++) {
            if (ListMaxCol == 1 && reccnt == 1) {
                lblCol.style.backgroundColor = activeSelectedColor;
            } else {
                lblCol[i].style.backgroundColor = activeSelectedColor;
            }
        }

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //�w�i�F��߂�
            oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;

            //�O��I���s�̔w�i�F���ύX����Ă���ꍇ
            if (funGetCurrentRow().toString() != "") {
                Idx = 1;
                //�O��I���s�̔w�i�F�����ɖ߂�
                for (i = ListMaxCol * funGetCurrentRow(); i < ListMaxCol * (funGetCurrentRow()+1); i++) {
                    if (funXmlRead(xmlSA790O, "disp_val" + Idx, funGetCurrentRow()) == "") {
                        //���o�^�̏ꍇ
                        if (ListMaxCol == 1 && reccnt == 1) {
                            lblCol.style.backgroundColor = haishiRowColor;
                        } else {
                            lblCol[i].style.backgroundColor = haishiRowColor;
                        }
                    } else {
                        //�o�^����Ă���ꍇ
                        if (ListMaxCol == 1 && reccnt == 1) {
                            lblCol.style.backgroundColor = deactiveSelectedColor;
                        } else {
                            lblCol[i].style.backgroundColor = deactiveSelectedColor;
                        }
                    }

                    Idx += 1;
                }
            }
        }

        //���čs�̑ޔ�
        funSetCurrentRow(oTR.rowIndex);
    }

    return true;

}

//========================================================================================
// �y�[�W�J��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/05/19
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage(NextPage);

    //�w���߰�ނ��ް��擾
    funSearchData();
}

