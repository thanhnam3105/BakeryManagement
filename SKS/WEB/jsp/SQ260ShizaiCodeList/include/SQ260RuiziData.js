var textValue;
//========================================================================================
// �����\������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/4
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

    var frm = document.frm00; // ̫�тւ̎Q��

    //��ʐݒ�
    funInitScreen(ConRuiziDataId);

    // ��ʏ����擾�E�ݒ�
    if (funGetInfo(1) == false) {
        return false;
    }

    document.getElementById("lblTitle").innerHTML = "�ގ��f�[�^�ďo���";
    // ��z�ς݂Ƀ`�F�b�N
    frm.chkTehaizumi.checked = true;

    // ��z�敪�`�F�b�N�{�b�N�X��񊈐�
    frm.chkTehaizumi.disabled = true;
    frm.chkMitehai.disabled = true;
    frm.chkMinyuryoku.disabled = true;
    frm.chkEdit.disabled = true;

    // �{�^���񊈐�
    frm.btnUpLoad.disabled = true;
    frm.btnOutput.disabled = true;
    frm.btnCompletion.disabled = true;

    return true;
}

//========================================================================================
// ��ʏ��擾����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �����P�Fmode �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �T�v  �F���[�U�̃Z�b�V�������A�R���{�{�b�N�X�̏����擾����
//========================================================================================
function funGetInfo(mode) {

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3200";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3200","FGEN3300","FGEN3310","SA290");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3200I, xmlFGEN3300I, xmlFGEN3310I,xmlSA290I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3200O, xmlFGEN3300O, xmlFGEN3310O,xmlSA290O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3200, xmlReqAry, xmlResAry,
            mode) == false) {
        return false;
    }

    // հ�ޏ��\��
    funInformationDisplay(xmlResAry[1], 2, "divUserInfo");

    // �Ώێ���
    funCreateComboBox(frm.ddlShizai, xmlResAry[2], 2, 2);

    // ������
    funCreateComboBox(frm.ddlTanto, xmlResAry[3], 3, 2);

    // ������
    funCreateComboBox(frm.ddlHattyusaki, xmlResAry[4], 1, 2);

}

//========================================================================================
// XML�t�@�C���ɏ�������
// �쐬�ҁFH.Shima
// �쐬���F2009/04/01
// �����P�FXmlId  �FXMLID
// �����Q�FreqAry �F�@�\ID�ʑ��MXML(�z��)
// �����R�FMode   �F�������[�h
//         1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var i;

    for (i = 0; i < reqAry.length; i++) {
        //��ʏ����\��
        if (XmlId.toString() == "RGEN3200") {
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
            	case 4:    //SA290
    				funXmlWrite(reqAry[i], "id_user", "", 0);
    				funXmlWrite(reqAry[i], "id_gamen", ConGmnIdRuiziData, 0);
    				break;
            }
        }
        // ����
        else if (XmlId.toString() == "RGEN3330") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3330
                // �`�F�b�N�{�b�N�X�ݒ�
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML��������
                // ��z�敪
                // ��z�ς�
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // ����z
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // ������
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* �������� �P�s�� */
                // ���ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // ���ޖ�
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // �����ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* �������� �Q�s�� */
                // ���i�R�[�h
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // ���i��
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // �[����i�����H��j
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // �[����i�����H�ꖼ�j
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* �������� �R�s�� */
                // �Ώێ���
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* �������� �S�s�� */
                // ������From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // ������To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // �[����From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // �[����To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // �ő�x����
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // �`�F�b�N�{�b�N�X�ݒ�
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // ���x��
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);

                break;
            }
        } // �I������
        else if (XmlId.toString() == "RGEN3350"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0)
                    break;
                case 1:    //FGEN3350
                    // XML��莎��R�[�h�擾
                    var obj_cd_shain   = document.getElementById("cd_shain_" + funGetCurrentRow());
                    var cd_shain       = obj_cd_shain.value;
                    var obj_nen        = document.getElementById("nen_" + funGetCurrentRow());
                    var nen            = obj_nen.value;
                    var obj_no_oi      = document.getElementById("no_oi_" + funGetCurrentRow());
                    var no_oi          = obj_no_oi.value;
                    var obj_seq_shizai = document.getElementById("seq_shizai_" + funGetCurrentRow());
                    var seq_shizai     = obj_seq_shizai.value;
                    var obj_no_eda     = document.getElementById("no_eda_" + funGetCurrentRow());
                    var no_eda         = obj_no_eda.value;

                    funXmlWrite(reqAry[i], "cd_shain", cd_shain, 0);
                    funXmlWrite(reqAry[i], "nen", nen, 0);
                    funXmlWrite(reqAry[i], "no_oi", no_oi, 0);
                    funXmlWrite(reqAry[i], "seq_shizai", seq_shizai, 0);
                    funXmlWrite(reqAry[i], "no_eda", no_eda, 0);

            }
        } // Excel�o�͌���
        else if (XmlId.toString() == "RGEN3620") {
            switch (i) {
            case 0:    // USERINFO
                funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                funXmlWrite(reqAry[i], "id_user", "", 0);
                break;
            case 1:    // FGEN3620
                // �`�F�b�N�{�b�N�X�ݒ�
                var tehaizumi = 0;
                if(frm.chkTehaizumi.checked == true) {
                    tehaizumi = 1;
                }
                var mitehai = 0;
                var minyuryoku = 0;

                // XML��������
                // ��z�敪
                // ��z�ς�
                funXmlWrite(reqAry[i], "kbn_tehaizumi", tehaizumi, 0);
                // ����z
                funXmlWrite(reqAry[i], "kbn_mitehai", mitehai, 0);
                // ������
                funXmlWrite(reqAry[i], "kbn_minyuryoku", minyuryoku, 0);

                /* �������� �P�s�� */
                // ���ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai", frm.txtShizaiCd.value, 0);
                // ���ޖ�
                funXmlWrite(reqAry[i], "nm_shizai", frm.txtShizaiNm.value, 0);
                // �����ރR�[�h
                funXmlWrite(reqAry[i], "cd_shizai_old", frm.txtOldShizaiCd.value, 0);

                /* �������� �Q�s�� */
                // ���i�i���i�j�R�[�h
                funXmlWrite(reqAry[i], "cd_shohin", frm.txtSyohinCd.value, 0);
                // ���i�i���i�j��
                funXmlWrite(reqAry[i], "nm_shohin", frm.txtSyohinNm.value, 0);
                // �[����i�����H��j
                funXmlWrite(reqAry[i], "cd_seizoukojo", frm.txtSeizoukojo.value, 0);
                // �[����i�����H��j��
                funXmlWrite(reqAry[i], "nm_seizoukojo", frm.txtSeizoukojo.value, 0);

                /* �������� �R�s�� */
                // �Ώێ���
                funXmlWrite(reqAry[i], "taisyo_shizai", frm.ddlShizai.options[frm.ddlShizai.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusaki", frm.ddlHattyusaki.options[frm.ddlHattyusaki.selectedIndex].value, 0);
                // ������
                funXmlWrite(reqAry[i], "cd_hattyusya", frm.ddlTanto.options[frm.ddlTanto.selectedIndex].value, 0);

                /* �������� �S�s�� */
                // ������From
                funXmlWrite(reqAry[i], "dt_hattyu_from", frm.txtHattyubiFrom.value, 0);
                // ������To
                funXmlWrite(reqAry[i], "dt_hattyu_to", frm.txtHattyubiTo.value, 0);
                // �[����From
                funXmlWrite(reqAry[i], "dt_nonyu_from", frm.txtNounyudayFrom.value, 0);
                // �[����To
                funXmlWrite(reqAry[i], "dt_nonyu_to", frm.txtNounyudayTo.value, 0);
                // �ő�x����From
                funXmlWrite(reqAry[i], "dt_han_payday_from", frm.txtHanPaydayFrom.value, 0);
                // �ő�x����To
                funXmlWrite(reqAry[i], "dt_han_payday_to", frm.txtHanPaydayTo.value, 0);

                // �`�F�b�N�{�b�N�X�ݒ�
                var mshiharai = 0;
                if(frm.chkMshiharai.checked == true) {
                	mshiharai = 1;
                }
                // ���x��
                funXmlWrite(reqAry[i], "kbn_mshiharai", mshiharai, 0);
                break;
            }
        }
    }

    return true;

}

//========================================================================================
// ���X�g�̐���
// �쐬�ҁFH.Shima
// �쐬���F2014/09/12
// �����P�F�擾XML
// �����Q�FHTML
// �����R�F�C���f�b�N�X
// �����S�F�ő�s��
// �T�v  �F���X�g���𐶐�����B
//========================================================================================
function DataSet(xmlResAry, html , i , cnt){

    if(i < cnt){

        var tableNm = "table";

        //���X�|���X�f�[�^�擾-------------------------------------------------------------------------------
        var row_no        = funXmlRead_3(xmlResAry[2], tableNm, "row_no", 0, i);									// �sNo
       	var nm_tanto      = funXmlRead_3(xmlResAry[2], tableNm, "nm_tanto", 0, i);									// �S����
        var naiyo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "naiyo", 0, i));							// ���e
        var cd_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shohin", 0, i));						// ���i�i���i�j�R�[�h
        var nm_shohin     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shohin", 0, i));						// ���i�i���i�j��
        var nisugata      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nisugata", 0, i));						// �׎p�^�����i�����j
        var target_shizai = funXmlRead_3(xmlResAry[2], tableNm, "nm_taisyo_shizai", 0, i);							// �Ώێ��ށi���e�������j
        var nm_hattyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_hattyusaki", 0, i));					// ������
        var cd_hattyusaki = funXmlRead_3(xmlResAry[2], tableNm, "cd_hattyusaki", 0, i);								// ������R�[�h
        var nm_nounyusaki = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyusaki", 0, i));					// �[����i�����H��j
        var cd_shizai_old = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai_old", 0, i));					// �����ރR�[�h
        var cd_shizai     = funXmlRead_3(xmlResAry[2], tableNm, "cd_shizai", 0, i);									// ���ރR�[�h
        var nm_shizai     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_shizai", 0, i));						// ���ޖ�
        var sekkei1       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, i));						// �݌v�@
        var sekkei2       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, i));						// �݌v�A
        var sekkei3       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, i));						// �݌v�B
        var zaishitsu     = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, i));						// �ގ�
        var biko_tehai    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, i));					// ���l
        var printcolor    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, i));					// ����F
        var no_color      = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, i));						// �F�ԍ�
        var henkounaiyoushosai   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, i));		// �ύX���e�ڍ�
        var nouki         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, i));							// �[��
        var suryo         = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, i));							// ����
        var nounyu_day    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nounyu_day", 0, i));					// �[����
        var han_pay       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "han_pay", 0, i));						// �ő�
        var dt_han_payday   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_han_payday", 0, i));				// �ő�x����
        // null�̏ꍇ'1900/01/01'�ɍX�V�����̂�'1900/01/01'�̏ꍇ""�ɍX�V
        if (dt_han_payday == '1900/01/01') {
        	dt_han_payday = "";
        }
        var han_upload    = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "file_path_aoyaki", 0, i));				// �ăA�b�v���[�h
        var nm_file       = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "nm_file_aoyaki", 0, i));				// �ۑ��t�@�C����

        var cd_shain      = funXmlRead_3(xmlResAry[2], tableNm, "cd_shain", 0, i);									// �Ј��R�[�h
        var nen           = funXmlRead_3(xmlResAry[2], tableNm, "nen", 0, i);										// �N
        var no_oi         = funXmlRead_3(xmlResAry[2], tableNm, "no_oi", 0, i);										// �ǔ�
        var seq_shizai    = funXmlRead_3(xmlResAry[2], tableNm, "seq_shizai", 0, i);								// ����SEQ
        var no_eda        = funXmlRead_3(xmlResAry[2], tableNm, "no_eda", 0, i);									// �}��
        var flg_status    = funXmlRead_3(xmlResAry[2], tableNm, "flg_status", 0, i);								// ��z�X�e�[�^�X
        var toroku_disp   = funSetNbsp(funXmlRead_3(xmlResAry[2], tableNm, "dt_koshin_disp", 0, i));				// �o�^��

        var objColor;
        objColor = henshuOkColor;

        // �����͐F
        var notInputColor     = "#99ffff";

        // ����z�F
        var notArrangeColor   = "#ffbbff";

        //HTML�o�̓I�u�W�F�N�g�ݒ�---------------------------------------------------------------------------
        //TR�s�J�n
        var output_html = "";

        //�sNo
        if("3" === flg_status){
            // ��z�ς�
            output_html += "<tr class=\"disprow\" bgcolor=\"" + deactiveSelectedColor + "\" onDblClick=\"funCompletion();\">";
        } else if("2" === flg_status) {
            // ����z
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notArrangeColor + "\" onDblClick=\"funCompletion();\">";
        } else {
            // ������
            output_html += "<tr class=\"disprow\" bgcolor=\"" + notInputColor + "\" onDblClick=\"funCompletion();\">";
        }

        //�sNo
        output_html += "    <td class=\"column\" width=\"28\"  align=\"right\">";
        output_html += "        <input type=\"text\" id=\"no_row_" + i + "\" name=\"no_row_" + i + "\" style=\"background-color:transparent;width:26px;border-width:0px;text-align:left\" readOnly value=\"" + row_no + "\" >";
        output_html += "        <input type=\"hidden\" id=\"cd_shain_" + i + "\" name=\"cd_shain_" + i + "\" readOnly value=\"" + cd_shain + "\" >";
        output_html += "        <input type=\"hidden\" id=\"nen_" + i + "\" name=\"nen_" + i + "\" readOnly value=\"" + nen + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_oi_" + i + "\" name=\"no_oi_" + i + "\" readOnly value=\"" + no_oi + "\" >";
        output_html += "        <input type=\"hidden\" id=\"seq_shizai_" + i + "\" name=\"seq_shizai_" + i + "\" readOnly value=\"" + seq_shizai + "\" >";
        output_html += "        <input type=\"hidden\" id=\"no_eda_" + i + "\" name=\"no_eda_" + i + "\" readOnly value=\"" + no_eda + "\" >";
        output_html += "    </td>";

        //�I���{�^��
        output_html += "    <td class=\"column\" width=\"32\" align=\"center\">";
        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
//        output_html += "        <input type=\"radio\" id=\"chk\" name=\"chk\" onclick=\"clickItiran(" + i + ");\" onDblClick=\"funCompletion();\" style=\"width:28px;\" value=\"" + i + "\" tabindex=\"-1\">";
        output_html += "    </td>";

        //�S����
        output_html += "    <td class=\"column\" width=\"99\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_tanto_" + i + "\" name=\"nm_tanto_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nm_tanto + "\" >";
        output_html += "    </td>";

        //���e
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"naiyo_" + i + "\" name=\"naiyo_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + naiyo + "\" >";
        output_html += "    </td>";

        //���i�i���i�j�R�[�h
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shohin_" + i + "\" name=\"cd_shohin_" + i + "\" style=\"background-color:transparent;width:48;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shohin,6) + "\" >";
        output_html += "    </td>";

        //���i�i���i�j��
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shohin_" + i + "\" name=\"nm_shohin_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_shohin + "\" >";
        output_html += "    </td>";

        //�׎p
        output_html += "    <td class=\"column\" width=\"147\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nisugata_" + i + "\" name=\"nisugata_" + i + "\" style=\"background-color:transparent;width:145px;border-width:0px;text-align:left\" readOnly value=\"" + nisugata + "\" >";
        output_html += "    </td>";

        //�Ώێ���
        output_html += "    <td class=\"column\" width=\"117\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"target_shizaia_" + i + "\" name=\"target_shizai_" + i + "\" style=\"background-color:transparent;width:115px;border-width:0px;text-align:left\" readOnly value=\"" + target_shizai + "\" >";
        output_html += "    </td>";

        //������
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_hattyusaki_" + i + "\" name=\"nm_hattyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_hattyusaki + "\" >";
        output_html += "    </td>";

        //�[����i�����H��j
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_nounyusaki_" + i + "\" name=\"nm_nounyusaki_" + i + "\" style=\"background-color:transparent;width:144px;border-width:0px;text-align:left\" readOnly value=\"" + nm_nounyusaki + "\" >";
        output_html += "    </td>";

        //�����ރR�[�h
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_old_" + i + "\" name=\"cd_shizai_old_" + i + "\" style=\"background-color:transparent;width:47;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai_old,6) + "\" >";
        output_html += "    </td>";

        //���ރR�[�h
        output_html += "    <td class=\"column\" width=\"50\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"cd_shizai_" + i + "\" name=\"cd_shizai_" + i + "\" style=\"background-color:transparent;width:47px;border-width:0px;text-align:left\" readOnly value=\"" + fillsZero(cd_shizai, 6) + "\" >";
        output_html += "    </td>";

        //���ޖ�
        output_html += "    <td class=\"column\" width=\"146\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nm_shizai_" + i + "\" name=\"nm_shizai_" + i + "\" style=\"background-color:transparent;width:144;border-width:0px;text-align:left\" readOnly value=\"" + nm_shizai + "\" >";
        output_html += "    </td>";

        //�݌v�@
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei1_" + i + "\" name=\"sekkei1_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + sekkei1 + "\" >";
        output_html += "    </td>";

        //�݌v�A
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei2_" + i + "\" name=\"sekkei2_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei2 + "\" >";
        output_html += "    </td>";

        //�݌v�B
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"sekkei3_" + i + "\" name=\"sekkei3_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + sekkei3 + "\" >";
        output_html += "    </td>";

        //�ގ�
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"zaishitsu_" + i + "\" name=\"zaishitsu_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + zaishitsu + "\" >";
        output_html += "    </td>";

        //���l
        output_html += "    <td class=\"column\" width=\"294\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"biko_tehai_" + i + "\" name=\"biko_tehai_" + i + "\" style=\"background-color:transparent;width:294;border-width:0px;text-align:left\" readOnly value=\"" + biko_tehai + "\" >";
        output_html += "    </td>";

        //����F
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"printcolor_" + i + "\" name=\"printcolor_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + printcolor + "\" >";
        output_html += "    </td>";

        //�F�ԍ�
        output_html += "    <td class=\"column\" width=\"196\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"no_color_" + i + "\" name=\"no_color_" + i + "\" style=\"background-color:transparent;width:194;border-width:0px;text-align:left\" readOnly value=\"" + no_color + "\" >";
        output_html += "    </td>";

        //�ύX���e�ڍ�
        output_html += "    <td class=\"column\" width=\"195\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"henkounaiyoushosai_" + i + "\" name=\"henkounaiyoushosai_" + i + "\" style=\"background-color:transparent;width:193;border-width:0px;text-align:left\" readOnly value=\"" + henkounaiyoushosai + "\" >";
        output_html += "    </td>";

        //�[��
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nouki_" + i + "\" name=\"nouki_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + nouki + "\" >";
        output_html += "    </td>";

        //����
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"suryo_" + i + "\" name=\"suryo_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + suryo + "\" >";
        output_html += "    </td>";

        //�o�^��
        output_html += "    <td class=\"column\" width=\"148\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"toroku_disp_" + i + "\" name=\"toroku_disp_" + i + "\" style=\"background-color:transparent;width:148px;border-width:0px;text-align:left\" readOnly value=\"" + toroku_disp + "\" >";
        output_html += "    </td>";

        //�[����
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"nounyu_day_" + i + "\" name=\"nounyu_day_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + nounyu_day + "\" >";
        output_html += "    </td>";

        //�ő�
        output_html += "    <td class=\"column\" width=\"148\" align=\"right\" >";
        output_html += "        <input type=\"text\" id=\"han_pay_" + i + "\" name=\"han_pay_" + i + "\" style=\"background-color:transparent;width:147px;border-width:0px;text-align:right\" readOnly value=\"" + funAddComma(han_pay) + "\" >";
        output_html += "    </td>";

        //�ő�x����
        output_html += "    <td class=\"column\" width=\"98\" align=\"left\" >";
        output_html += "        <input type=\"text\" id=\"han_payday_" + i + "\" name=\"han_payday_" + i + "\" style=\"background-color:transparent;width:96px;border-width:0px;text-align:left\" readOnly value=\"" + dt_han_payday + "\" >";
        output_html += "    </td>";

        //�ŉ��i�āj�A�b�v���[�h
        output_html += "    <td class=\"column\" width=\"468\" align=\"left\" >";
        //�ۑ�����Ă���t�@�C����
        output_html += "        <input type=\"hidden\" id=\"nm_file_" + i + "\" name=\"nm_file_" + i + "\" value=\"" + nm_file + "\" tabindex=\"-1\">";
        output_html += "        <div style=\"position: relative;\">";
        // �Q�ƃ{�^��
        output_html += "            <input type=\"file\" id=\"filename_" + i + "\" name=\"filename_" + i + "\" class=\"normalbutton\" size=\"464\" style=\"width:464px;\"";
        output_html += "onChange=\"funChangeFile(" + i + ")\" onclick=\"funSetInput(" + i + ")\" onkeydown=\"funEnterFile(" + i + ", event.keyCode);\" disabled=\"disabled\" >";
        // �\���p�t�@�C����
        output_html += "            <span style=\"position: absolute; top: 0px; left: 0px; z-index:1;\">";
        output_html += "                <input type=\"text\" id=\"inputName_" + i + "\" name=\"inputName_" + i + "\" value=\"\" size=\"76\" readonly tabindex=\"-1\" >";
        output_html += "            </sapn>";
        output_html += "        </div>";
        output_html += "    </td>";

        //TR�s��
        output_html += "</tr>";
        html += output_html;

        //�ċA�����i���f�[�^��HTML�����j
        setTimeout(function(){ DataSet( xmlResAry , html , ( i + 1 ) , cnt ); }, 0);
    } else {
        //�ꗗ����HTML�ݒ�
        var obj = document.getElementById("divMeisai");
        html = html + "</table>";
        obj.innerHTML = html;

        // �I���s�̏�����
        funSetCurrentRow("");

        //�\���I����Ɍ����n�A�N�V�����𑀍�\
        document.getElementById("btnOutput").disabled = false;
        document.getElementById("btnSearch").disabled = false;
        document.getElementById("btnClear").disabled = false;
        document.getElementById("btnCompletion").disabled = false;
        document.getElementById("btnEnd").disabled = false;

        var limit_over = funXmlRead_3(xmlResAry[2], "table", "limit_over", 0, (i - 1));
        if("0" != limit_over){
            funErrorMsgBox(E000051 + limit_over + E000052);
        }

        xmlResAry = null;
        html = null;

        // ���b�Z�[�W�̔�\��
        funClearRunMessage();

        //�����I��
        return true;
    }
}

//========================================================================================
// ��������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// ����  �F�Ȃ�
// �T�v  �F������茟�����s���A���ʂ��ꗗ�ɕ\��
//========================================================================================
function funSearch(){
    var frm = document.frm00; // ̫�тւ̎Q��

    // ��z�敪�I���`�F�b�N�i�ގ��f�[�^�́A��z�ς݂̂݁j
    var checkFlg = frm.chkTehaizumi.checked;

    if(!checkFlg){
        funErrorMsgBox(E000038);
        return false;
    }
    funClearList();

    funShowRunMessage();

    setTimeout(function(){ funDataSearch() }, 0);
}

//========================================================================================
// �f�[�^��������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// �T�v  �F������茟�����s���A���ʂ��ꗗ�ɕ\��
//========================================================================================
function funDataSearch(){

    var frm = document.frm00; // ̫�тւ̎Q��
    var XmlId = "RGEN3330";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3330");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3330I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3330O);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // ����ݏ��A�����ޯ���̏����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3330, xmlReqAry, xmlResAry,
            1) == false) {
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }

    // �������ʍs���̐ݒ�
    var loop_cnt = funXmlRead(xmlResAry[2], "loop_cnt", 0);
    frm.hidListRow.value = loop_cnt;

    //�ް��擾������
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
        //�\�����Ɍ����n�A�N�V�����𑀍�s��
        document.getElementById("btnOutput").disabled = true;
        document.getElementById("btnSearch").disabled = true;
        document.getElementById("btnClear").disabled = true;
        document.getElementById("btnCompletion").disabled = true;
        document.getElementById("btnEnd").disabled = true;

        var output_html = "";
        output_html = output_html + "<table cellpadding=\"0\" id=\"tblList\" cellspacing=\"0\" border=\"1\">";

        setTimeout(function(){ DataSet(xmlResAry, output_html ,0 ,loop_cnt); }, 0);
    }

    //������ү���ޔ�\��
    funClearRunMessage();

    return true;
}

//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/19
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AxmlData  �F�ݒ�XML
//       �F�Bmode     �F���[�h
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, kara) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;

    //�����ޯ���̸ر
    funClearSelect(obj, kara);

    //�����擾
    reccnt = funGetLength(xmlData);

    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return true;
    }

    //�������̎擾
    switch (mode) {
        case 1:    // ������Ͻ�
            atbName = "nm_hattyusaki";
            atbCd   = "cd_hattyusaki";
            break;

        case 2:    // �Ώێ���
            atbName = "nm_literal";
            atbCd   = "cd_literal";
            break;

        case 3:    // ������
            atbName = "nm_user";
            atbCd   = "id_user";
            break;
    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead(xmlData, atbCd, i) != "" && funXmlRead(xmlData, atbName, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead(xmlData, atbName, i);
            objNewOption.value     = funXmlRead(xmlData, atbCd, i);
        }
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �ꗗ�I������
// �쐬�ҁFH.Shima
// �쐬���F2014/9/12
// ����  �F�C���f�b�N�X
// �T�v  �F�I���s���n�C���C�g
//========================================================================================
function clickItiran(row){
    funChangeSelectRowColorStockPreviousColor();
}

//========================================================================================
// �N���A����
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// ����  �F�Ȃ�
// �T�v  �F��������������������B
//========================================================================================
function funClear(){
    funClearJoken();
    funClearList();
}

//========================================================================================
// ���������N���A�{�^����������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/11
// ����  �F�Ȃ�
// �T�v  �F��������������������
//========================================================================================
function funClearJoken(){
    var frm = document.frm00;    //̫�тւ̎Q��

    frm.reset();
    // ��z�ς݂Ƀ`�F�b�N
    frm.chkTehaizumi.checked = true;
    // Excel�{�^���񊈐�
    frm.btnOutput.disabled = true;
}

//========================================================================================
// �ꗗ�N���A����
// �쐬�ҁFH.Shima
// �쐬���F2014/9/12
// ����  �F�Ȃ�
// �T�v  �F�ꗗ�̏�������������
//========================================================================================
function funClearList(){

    document.getElementById("divMeisai").innerHTML = "";
    funSetCurrentRow("");
    // �I�������{�^���񊈐�
    document.getElementById("btnCompletion").disabled = true;

}

//========================================================================================
// �I����������
// �쐬�ҁFH.Shima
// �쐬���F2014/9/12
// ����  �F�Ȃ�
// �T�v  �F�ꗗ����I�����ꂽ�����˗���ʂɑ���
//========================================================================================
function funCompletion(){

    var frm = document.frm00;                    // ̫�тւ̎Q��
    var frmDa = window.dialogArguments.frm00;    // �e̫�тւ̎Q�Ɓi���׃t���[���j
    var docDa = window.dialogArguments.document; // �edocument�ւ̎Q�Ɓi���׃t���[���j
    var XmlId = "RGEN3350";
    var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3350");
    var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3350I);
    var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3350O);
    var tableNm = "table";

    //�I������Ă��Ȃ��ꍇ
    if(funGetCurrentRow().toString() == ""){
        funErrorMsgBox(E000002);
        return false;
    }
    //�I������Ă���ꍇ
    else {

        // ������XMĻ�قɐݒ�
        if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
            funClearRunMessage();
            return false;
        }

        // ����ݏ��A�����ޯ���̏����擾
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3350, xmlReqAry, xmlResAry, 1) == false) {
            return false;
        }

        /* ���ގ�z�˗����o�́i�e�t�H�[���j�ɒl��ݒ� */
        // �V�ŁA���� ���ʍ���
        frmDa.txtDesign1.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei1", 0, 0);				// �݌v�@
        frmDa.txtDesign2.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei2", 0, 0);				// �݌v�A
        frmDa.txtDesign3.value         = funXmlRead_3(xmlResAry[2], tableNm, "sekkei3", 0, 0);				// �݌v�B
        frmDa.txtZaishitsu.value       = funXmlRead_3(xmlResAry[2], tableNm, "zaishitsu", 0, 0);			// �ގ�
        frmDa.txtBiko.value            = funXmlRead_3(xmlResAry[2], tableNm, "biko_tehai", 0, 0);			// ���l
        frmDa.txtPrintColor.value      = funXmlRead_3(xmlResAry[2], tableNm, "printcolor", 0, 0);			// ����F
        frmDa.txtColorNo.value         = funXmlRead_3(xmlResAry[2], tableNm, "no_color", 0, 0);				// �F�ԍ�
        frmDa.txtChangesDetail.value   = funXmlRead_3(xmlResAry[2], tableNm, "henkounaiyoushosai", 0, 0);	// �ύX���e�ڍ�
        frmDa.txtDeliveryTime.value    = funXmlRead_3(xmlResAry[2], tableNm, "nouki", 0, 0);				// �[��
        frmDa.txtQuantity.value        = funXmlRead_3(xmlResAry[2], tableNm, "suryo", 0, 0);				// ����

        // ���ł̂�
        if (frmDa.revision_rev.checked) {
            frmDa.txtOldShizaiZaiko.value  = funXmlRead_3(xmlResAry[2], tableNm, "old_sizaizaiko", 0, 0);	// �����ލ݌�
            frmDa.txtRakuhan.value         = funXmlRead_3(xmlResAry[2], tableNm, "rakuhan", 0, 0);			// ����
        }

        // �ގ��I������
        frmDa.hdnRuiziSelect.value    = "true";
        // �I������
        funEnd();
    }
}

//========================================================================================
// �I������
// �쐬�ҁFH.Shima
// �쐬���F2014/09/09
// ����  �F�Ȃ�
// �T�v  �F���j���[��ʂɖ߂�B
//========================================================================================
function funEnd(){

    parent.close();

    return true;
}

//========================================================================================
// �󔒎��̐ݒ�
// �쐬�ҁFH.Shima
// �쐬���F2014/09/19
//========================================================================================
function funSetNbsp(val) {

    if( val == "" || val == "NULL" ){
        val = "&nbsp;";
    }

    return val;
}

//========================================================================================
// �I���s����
// �쐬�ҁFH.Shima
// �쐬���F2014/10/14
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̔w�i�F��ێ����A�I��F�ɕύX����B
//========================================================================================
function funChangeSelectRowColorStockPreviousColor() {
	var deactiveColor = deactiveSelectedColor;	// �I���s�̔�A�N�e�B�u�F
    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        if(CurrentRow !== oTR.rowIndex){
            if(CurrentRow !== ""){
                BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);
                if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
                    //�w�i�F��߂�
                    oTBL.rows(BeforeRow).style.backgroundColor = deactiveColor;
                }
            }
            // �w�i�F�̕ێ�
            deactiveColor = oTR.style.backgroundColor;
        }

        //�w�i�F��ύX
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        //���čs�̑ޔ�
        CurrentRow = oTR.rowIndex;
    }

    return true;
}

//========================================================================================
// �[�����ߏ���
// �쐬�ҁFH.Shima
// �쐬���F2014/12/12
//========================================================================================
function fillsZero(obj, keta){
    var ret = obj;

    while(ret.length < keta){
        ret = "0" + ret;
    }
    return ret;
}

//========================================================================================
// �[����i�����H��j���͎�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/05
// ����  �F�Ȃ�
// �T�v  �F�����H��R�[�h�ݒ�
//========================================================================================
function funChangeKojo() {

	var frm = document.frm00;    //̫�тւ̎Q��

	// �[����i�����H��j�R�[�h��ݒ�
	//frm.seizoKojoCd.value = funGetXmldata(xmlSA290O, "nm_busho", frm.txtSeizoukojo.value, "cd_busho");
	// Excel�{�^���񊈐�
	funChangeSearch();

	return;
}

//========================================================================================
// XML�f�[�^��茟���s�̃R�[�h�������O �ϊ�
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/04
// ����  �F�@xmlData  �FXML�f�[�^
//       �F�Akomoku   �F�������ږ�
//       �F�Btext     �F�����l
//       �F�Cret      �F�擾���ږ�
// return�F�R�[�h�l���͖��O
// �T�v  �F��v����R�[�h���͖��O���擾
//========================================================================================
function funGetXmldata(xmlData, komoku, text, ret) {

	//�����擾
	var reccnt = funGetLength(xmlData);
	// �߂�l
	var retStr = "";
	for (var i = 0; i < reccnt; i++) {
		// �������ڒl���������ꍇ�AIndex�ݒ�
		if (funXmlRead(xmlData, komoku, i) == text) {
			//�w�荀�ږ��̒l��Ԃ�
			retStr = funXmlRead(xmlData, ret, i);
			break;
		}
	}

 return retStr;
}

//========================================================================================
// Excel�o�͏���
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/04
// �T�v  �FExcel�o�͂����s����
//========================================================================================
function funExcelOut() {
	// �t�H�[���Q��
	var frm = document.frm00;

	var XmlId = "RGEN3620";
	var FuncIdAry = new Array(ConResult, ConUserInfo, "FGEN3620");
	var xmlReqAry = new Array(xmlUSERINFO_I, xmlFGEN3620I);
	var xmlResAry = new Array(xmlRESULT, xmlUSERINFO_O, xmlFGEN3620O);

	// Excel�o�͊m�Fү���ނ̕\��
    if (funConfMsgBox(I000008) != ConBtnYes) {
        return false;
    }

    // XML�̏�����
    setTimeout("xmlFGEN3620I.src = '../../model/FGEN3620I.xml';", ConTimer);

    // ������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        funClearRunMessage();
        return false;
    }

    // �o�͎��s
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN3620, xmlReqAry, xmlResAry,
            1) == false) {
        return false;
    }

    // �t�@�C���p�X�̑ޔ�
    frm.strFilePath.value = funXmlRead(xmlFGEN3620O, "URLValue", 0);

    //�޳�۰�މ�ʂ̋N��
    funFileDownloadUrlConnect(ConConectGet, frm);

	return true;
}

//========================================================================================
// ���������ύX
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/13
// ����  �F�Ȃ�
// �T�v  �FExcel�{�^���񊈐�
//========================================================================================
function funChangeSearch() {

	// �{�^���񊈐�
	document.getElementById("btnOutput").disabled = true;

	return;
}

//========================================================================================
// ���������ύX
// �쐬�ҁFBRC Koizumi
// �쐬���F2016/10/13
// ����  �F�Ȃ�
// �T�v  �FExcel�{�^���񊈐�
//========================================================================================
function funTextChange(value){
	// ���������ύX���A÷�Ă��ύX���ꂽ�ꍇ�̂݃{�^����񊈐��ɂ���
	if (textValue == value) {
		return;
	}

	funChangeSearch();
	return;
}