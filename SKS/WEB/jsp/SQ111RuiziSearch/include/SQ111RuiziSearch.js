//========================================================================================
// �O���[�o���ϐ��̐錾
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
//========================================================================================

//���ið��ق��߰�ގw��
var CurrentPage_Seihin;
//����ð��ق��߰�ގw��
var CurrentPage_Shizai;
//�w�茅���̎w��i���i�R�[�h�j
var keta_seihin;
//�w�茅���̎w��i���ރR�[�h�j
var keta_shizai;


//========================================================================================
// ��������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F��ʂ̏����������s��
//========================================================================================
function funLoad() {
    
    //�����\��
    funShokiHyoji(1);
    
    //���i�e�[�u���w�b�_�\��
    funSeihinTableHyoji("", "seihinTable");
    
    //���ރe�[�u���w�b�_�\��
    funShizaiTableHyoji("", "shizaiTable");
    
    return true;

}


//========================================================================================
// �����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �FServlet�ƒʐM���s���A�����\���ɕK�v�ȏ����擾����
//========================================================================================
function funShokiHyoji(mode){
    
    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var frmDa = window.dialogArguments.frm00; //�e̫�тւ̎Q�Ɓi���׃t���[���j
    var XmlId = "RGEN1010";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1010","FGEN1020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1010I, xmlFGEN1020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1010O , xmlFGEN1020I );
    
    //------------------------------------------------------------------------------------
    //                                    ���ތ���
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                  ���i���������\��
    //------------------------------------------------------------------------------------
    //���i����-��ЃR���{�{�b�N�X����
    funCreateComboBox(frm.selectSeihinKaisha, xmlResAry[2], 1, 0);
    
    //���i����-��ЃR���{�{�b�N�X�I��
    funDefaultIndex(frm.selectSeihinKaisha, 1, frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value);
    
    //���i����-�H��R���{�{�b�N�X����
    funCreateComboBox(frm.selectSeihinKojo, xmlResAry[3], 2, 1);
    
    //���i����-�H��R���{�{�b�N�X�I��
    funDefaultIndex(frm.selectSeihinKojo, 1, frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value);
    
    
    //------------------------------------------------------------------------------------
    //                                  ���ތ��������\��
    //------------------------------------------------------------------------------------
    //���ތ���-��ЃR���{�{�b�N�X����
    funCreateComboBox(frm.selectShizaiKaisha, xmlResAry[2], 1, 0);
    
    //���ތ���-��ЃR���{�{�b�N�X�I��
    funDefaultIndex(frm.selectShizaiKaisha, 1, frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value);
    
    //���ތ���-�H��R���{�{�b�N�X����
    funCreateComboBox(frm.selectShizaiKojo, xmlResAry[3], 2, 1);
    
    //���ތ���-�H��R���{�{�b�N�X�I��
    funDefaultIndex(frm.selectShizaiKojo, 1, frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value);
    
    
    //------------------------------------------------------------------------------------
    //                                   �����̏����ݒ�
    //------------------------------------------------------------------------------------
    //�w�茅���̎w��i���i�R�[�h�j
    keta_seihin = frmDa.hdnCdketasu.value;
    //�w�茅���̎w��i���ރR�[�h�j
    keta_shizai = frmDa.hdnCdketasu.value;
    
    return true;
}

//========================================================================================
// �H��ꗗ�擾
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@getObj �F��ЃR���{
//       �F�AsetObj �F�H��R���{
//       �F�Bmode   �F1:���i,2:����
// �T�v  �F��ʂ̍H��ꗗ�擾�������s��
//========================================================================================
function funKojoChange(getObj, setObj, mode_keta) {
    
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
    if (funReadyOutput(XmlId, xmlReqAry, mode, getObj.options[getObj.selectedIndex].value) == false) {
        funClearRunMessage();
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
    funCreateComboBox(setObj , xmlResAry[2] , 2, 1);
    
    
    //------------------------------------------------------------------------------------
    //                                  �H��� �����ݒ�
    //------------------------------------------------------------------------------------
    //�H��R���{���[�v
    /*for ( var i = 0; i < setObj.length; i++) {
        
        //FGEN1020�i�H��R���{�擾�j�̌����擾
        var reccnt = funGetLength_3(xmlResAry[2], "kojyo", 0); //�����擾
        
        //FGEN1020���R�[�h���[�v
        for( var j = 0; j < reccnt; j++ ){
        
            //�H��R�[�h�擾
            var cd_kojo = funXmlRead(xmlResAry[2], "cd_kojyo", j);
            
            //���X�g��VALUE�l�ƍH��R�[�h���������ꍇ
            if( cd_kojo == setObj.options[i].value ){
            
                //�H��ʌ����擾
                var cd_keta = funXmlRead(xmlResAry[2], "cd_keta", j);
                
                //VALUE�l�Ɂu�H��CD:::�����v�ݒ�
                setObj.options[i].value = cd_kojo + ":::" + cd_keta;
            }
        }
    }*/
    
    //���i�A��Ќ����ݒ�
    if( mode_keta == 1 ){
        
        keta_seihin = funXmlRead(xmlResAry[2], "cd_keta", 0);
        
    }
    //���ށA��Ќ����ݒ�
    else if( mode_keta == 2 ){
        
        keta_shizai = funXmlRead(xmlResAry[2], "cd_keta", 0);
        
    }
    
    return true;
}


//========================================================================================
// ���i�����{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F���i�����{�^������
//========================================================================================
function funSeihinClick() {

    //���߰�ނ̐ݒ�
    funSetCurrentPage_Seihin(1);

    //�w���߰�ނ��ް��擾
    funSeihinSearch();
    
    return true;
}


//========================================================================================
// ���i��莑�ޓ]���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F���i��莑�ޓ]���{�^������
//========================================================================================
function funSeihin_ShizaiClick() {

    //���߰�ނ̐ݒ�
    funSetCurrentPage_Shizai(1);
    
    //������Ԃ̐ݒ�
    divKensakuZyotai.innerText = I000011;

    //�w���߰�ނ��ް��擾
    var ret = funSeihin_ShizaiSearch();


	//�f�[�^�擾������
	if(ret == true){
		// 2010.10.01 Add Arai START �yQP@00412_�V�T�N�C�b�N���ǁ@�Č���36�z
		location.href='#lnkShizaiKensaku';
	    // 2010.10.01 Add Arai END
	}
	
    return true;
}


//========================================================================================
// ���ތ����{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F���ތ����{�^������
//========================================================================================
function funShizaiClick() {

    //���߰�ނ̐ݒ�
    funSetCurrentPage_Shizai(1);
    
    //������Ԃ̐ݒ�
    divKensakuZyotai.innerText = I000012;

    //�w���߰�ނ��ް��擾
    funShizaiSearch();
    
    return true;
}


//========================================================================================
// ���ޑI���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F���ޑI���{�^������
//========================================================================================
function funShizaiSentakuClick() {
    
    //���ވꎞ�ۊǗp�̕\�֒ǋL
    var ret = funSelectShizai();
    
    if(ret == true){
    	// 2010.10.01 Add Arai START �yQP@00412_�V�T�N�C�b�N���ǁ@�Č���36�z
		location.href='#lnkShizaiSentaku';
	    // 2010.10.01 Add Arai END
    }
    
    return true;
}


//========================================================================================
// ���i����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F���i�������̎擾�A�X�V���s��
//========================================================================================
function funSeihinSearch() {
    
    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1030");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1030I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1030O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                    ���i����
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1030, xmlReqAry, xmlResAry, mode) == false) {
    	funSeihinClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                   ���i���\��
    //------------------------------------------------------------------------------------
    //���ʔ���
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
    	alert(funXmlRead(xmlResAry[0], "flg_return", 0));
        //�װ��������ү���ނ�\��
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    	
    }
	
    //���ið��ٕ\��
    funSeihinTableHyoji(xmlResAry[2], "seihinTable");
	
    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Seihin(funGetCurrentPage_Seihin(), PageCnt, "divPage", "");
    spnCurPage.innerText = funGetCurrentPage_Seihin() + "�^" + PageCnt + "�y�[�W";
    
    return true;
}


//========================================================================================
// ���i��莑�ޓ]��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F���i��莑�ޓ]��
//========================================================================================
function funSeihin_ShizaiSearch() {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1040";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1035");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1035I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1035O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                 ���i��莑�ޓ]��
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN1035I.src = '../../model/FGEN1035I.xml';", ConTimer);
    
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1040, xmlReqAry, xmlResAry, mode) == false) {
    	funShizaiClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                              ���i��莑�ޓ]�����\��
    //------------------------------------------------------------------------------------
    //���ið��ٕ\��
    funShizaiTableHyoji(xmlResAry[2], "shizaiTable");
    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo2.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt2.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax2.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Shizai(funGetCurrentPage_Shizai(), PageCnt, "divPage2", "");
    spnCurPage2.innerText = funGetCurrentPage_Shizai() + "�^" + PageCnt + "�y�[�W";
    
    return true;
}


//========================================================================================
// ���ތ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F���ތ���
//========================================================================================
function funShizaiSearch() {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1040");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1040I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1040O );
    var mode = 1;
    var PageCnt;
    var RecCnt;
    var ListMaxRow;
    
    //------------------------------------------------------------------------------------
    //                                 ���i��莑�ޓ]��
    //------------------------------------------------------------------------------------
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1050, xmlReqAry, xmlResAry, mode) == false) {
    	funShizaiClear();
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                              ���i��莑�ޓ]�����\��
    //------------------------------------------------------------------------------------
    //���ið��ٕ\��
    funShizaiTableHyoji(xmlResAry[2], "shizaiTable");
    
    //�ް������A�߰���ݸ�̐ݒ�
    spnRecInfo2.style.display = "block";
    RecCnt = funXmlRead(xmlResAry[2], "max_cnt", 0);
    spnRecCnt2.innerText = RecCnt;
    ListMaxRow = funXmlRead(xmlResAry[2], "disp_cnt", 0);
    spnRowMax2.innerText = ListMaxRow;
    PageCnt = Math.ceil(RecCnt / ListMaxRow);
    funCreatePageLink_Shizai(funGetCurrentPage_Shizai(), PageCnt, "divPage2", "");
    spnCurPage2.innerText = funGetCurrentPage_Shizai() + "�^" + PageCnt + "�y�[�W";
    
    return true;
}


//========================================================================================
// ���ޑI��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F���ޑI��
//========================================================================================
function funSelectShizai() {

    //------------------------------------------------------------------------------------
    //                                    �ϐ��錾
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //̫�тւ̎Q��
    var XmlId = "RGEN1060";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1045");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1045I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1045O );
    var mode = 1;
    
    //------------------------------------------------------------------------------------
    //                                    ���ޓ]��
    //------------------------------------------------------------------------------------
    //XML�̏�����
    setTimeout("xmlFGEN1045I.src = '../../model/FGEN1045I.xml';", ConTimer);
    //������XMĻ�قɐݒ�
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage();
        return false;
    }
    
    //����ݏ��A���ʏ����擾
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1060, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }
    
    //------------------------------------------------------------------------------------
    //                                  ���ޕ\�֒ǋL
    //------------------------------------------------------------------------------------
    //FGEN1045�i���ތ��j�̌����擾
    var reccnt = funGetLength_3(xmlResAry[2], "shizai", 0); //�����擾
    //���\�֒ǋL
    for( var i = 0; i < reccnt; i++ ){

        funAddShizaiKoho(xmlResAry[2],i);

    }
    
    return true;
}


//========================================================================================
// ���i�e�[�u�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���i�e�[�u�����\��
//========================================================================================
function funSeihinTableHyoji(xmlData, ObjectId) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableSeihinNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var max_cnt;          //������
    var no_page;          //�߰�ޔԍ�
    var max_page;         //���߰�ސ�
    var disp_cnt;         //�\������
    var cd_kaisya;        //���CD
    var cd_kojyyo;        //�H��CD
    var kigo_kojyo;       //�H��L��
    var cd_seihin;        //���iCD
    var nm_seihin;        //���i��
    var i;                //���[�v�J�E���g
    
    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = document.getElementById(ObjectId);
    OutputHtml = "";
    
    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableSeihinNm = "seihin";
    
    //��{���擾
    max_cnt = funXmlRead_3(xmlData, tablekihonNm, "max_cnt", 0, 0);
    no_page = funXmlRead_3(xmlData, tablekihonNm, "no_page", 0, 0);
    max_page = funXmlRead_3(xmlData, tablekihonNm, "max_page", 0, 0);
    disp_cnt = funXmlRead_3(xmlData, tablekihonNm, "disp_cnt", 0, 0);
    
    //�e�[�u���\��
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"1000px\">";
    OutputHtml += "<colgroup>";
    OutputHtml += "<col style=\"width:50px;\" />";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:800px;\"/>";
    OutputHtml += "</colgroup>";
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "<tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "<th class=\"columntitle\">�I��</th>";
    OutputHtml += "<th class=\"columntitle\">�H��</th>";
    OutputHtml += "<th class=\"columntitle\">���i�R�[�h</th>";
    OutputHtml += "<th class=\"columntitle\">���i��</th>";
    OutputHtml += "</tr>";
    OutputHtml += "</thead>";
    OutputHtml += "<tbody>";
    OutputHtml += "<table class=\"detail\" id=\"tblListSeihin\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"\" datafld=\"\" style=\"width:1000px;\">";
    
    //�e�[�u������
    for( i = 0; i < disp_cnt; i++ ){
        
        //XML�f�[�^�擾
        cd_kaisya = funXmlRead_3(xmlData, tableSeihinNm, "cd_kaisya", 0, i);
        cd_kojyyo = funXmlRead_3(xmlData, tableSeihinNm, "cd_kojyyo", 0, i);
        kigo_kojyo = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "kigo_kojyo", 0, i));
        cd_seihin = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "cd_seihin", 0, i));
        nm_seihin = funKuhakuChg(funXmlRead_3(xmlData, tableSeihinNm, "nm_seihin", 0, i));
        
        //���[�v�A�E�g
        if(cd_kaisya == ""){
            
            i += (disp_cnt + 1);
            
        }else{
            
            //���א���
            OutputHtml += "    <tr class=\"disprow\">";
            OutputHtml += "        <td class=\"column\" width=\"50px\" align=\"center\">";
            OutputHtml += "            <input type=\"checkbox\" name=\"chkSeihin\" tabindex=\"6\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"50px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_Kigou\" id=\"txtSeihin_Kigou\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnSeihin_kasihaCd\" id=\"hdnSeihin_kasihaCd\" value=\"" + cd_kaisya + "\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnSeihin_kojoCd\" id=\"hdnSeihin_kojoCd\" value=\"" + cd_kojyyo + "\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_seihinCd\" style=\"text-align:center\" id=\"txtSeihin_seihinCd\" class=\"table_text_view\" readonly value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"784px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtSeihin_seihinNm\" id=\"txtSeihin_seihinNm\" class=\"table_text_view\" readonly value=\"" + nm_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "    </tr>";
            
        }
        
    }
    
    OutputHtml += "</table>";
    OutputHtml += "</tbody>";
    OutputHtml += "</table>";
    
    //HTML���o��
    obj.innerHTML = OutputHtml;
    
    return true;
}


//========================================================================================
// ���ރe�[�u�����\��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@xmlUser �F�X�V���i�[XML��
//       �F�AObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���ރe�[�u�����\��
//========================================================================================
function funShizaiTableHyoji(xmlData, ObjectId) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var obj;              //�ݒ��I�u�W�F�N�g
    var tablekihonNm;     //�ǂݍ��݃e�[�u����
    var tableShizaiNm;    //�ǂݍ��݃e�[�u����
    var OutputHtml;       //�o��HTML
    var title;            //�����^�C�g��
    var max_cnt;          //������
    var no_page;          //�߰�ޔԍ�
    var max_page;         //���߰�ސ�
    var disp_cnt;         //�\������
    var cd_kaisya;        //���CD
    var cd_kojyyo;        //�H��CD
    var kigo_kojyo;       //�H��L��
    var cd_shizai;        //����CD
    var nm_shizai;        //���ޖ�
    var tanka;            //�P��
    var budomari;         //�����i���j
    var cd_seihin;        //���iCD
    var ryo;              //�g�p��
    var i;                //���[�v�J�E���g
    
    //HTML�o�̓I�u�W�F�N�g�ݒ�
    obj = document.getElementById(ObjectId);
    OutputHtml = "";
    
    //�e�[�u�����ݒ�
    tablekihonNm = "kihon";
    tableShizaiNm = "shizai";
    
    //��{���擾
    title = funXmlRead_3(xmlData, tablekihonNm, "title", 0, 0);
    max_cnt = funXmlRead_3(xmlData, tablekihonNm, "max_cnt", 0, 0);
    no_page = funXmlRead_3(xmlData, tablekihonNm, "no_page", 0, 0);
    max_page = funXmlRead_3(xmlData, tablekihonNm, "max_page", 0, 0);
    disp_cnt = funXmlRead_3(xmlData, tablekihonNm, "disp_cnt", 0, 0);
    
    //�e�[�u���\��
    OutputHtml += "<table id=\"dataTable\" name=\"dataTable\" cellspacing=\"0\" width=\"1000px\">";
    OutputHtml += "<colgroup>";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:50px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:400px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "<col style=\"width:100px;\"/>";
    OutputHtml += "</colgroup>";
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "<tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "<th class=\"columntitle\">�I��</th>";
    OutputHtml += "<th class=\"columntitle\">�H��</th>";
    OutputHtml += "<th class=\"columntitle\">���ރR�[�h</th>";
    OutputHtml += "<th class=\"columntitle\">���ޖ�</th>";
    OutputHtml += "<th class=\"columntitle\">�P��</th>";
    OutputHtml += "<th class=\"columntitle\">����(��)</th>";
    OutputHtml += "<th class=\"columntitle\">�g�p��/�P�[�X</th>";
    OutputHtml += "<th class=\"columntitle\">���i�R�[�h</th>";
    OutputHtml += "</tr>";
    OutputHtml += "</thead>";
    OutputHtml += "<tbody>";
    OutputHtml += "<table class=\"detail\" id=\"tblListShizai\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" datasrc=\"\" datafld=\"\" style=\"width:999px;\">";
    
    //�e�[�u������
    for( i = 0; i < disp_cnt; i++ ){
        
        //XML�f�[�^�擾
        cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, i);
        cd_kojyyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyyo", 0, i);
        kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, i);
        cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, i);
        nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, i);
        tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, i);
        cd_seihin = funXmlRead_3(xmlData, tableShizaiNm, "cd_seihin", 0, i);
        ryo = funXmlRead_3(xmlData, tableShizaiNm, "ryo", 0, i);
        
        //���[�v�A�E�g
        if(cd_kaisya == ""){
            
            i += (disp_cnt + 1);
            
        }else{
            
            //���א���
            OutputHtml += "    <tr class=\"disprow\">";
            OutputHtml += "        <td class=\"column\" width=\"50px\" align=\"center\">";
            OutputHtml += "            <input type=\"checkbox\" name=\"chkShizai\" tabindex=\"15\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"51px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_Kigou\" id=\"txtShizai_Kigou\" style=\"text-align:center\"  class=\"table_text_view\" readonly value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnShizai_kasihaCd\" id=\"hdnShizai_kasihaCd\" value=\"" + cd_kaisya + "\" />";
            OutputHtml += "            <input type=\"hidden\" name=\"hdnShizai_kojoCd\" id=\"hdnShizai_kojoCd\" value=\"" + cd_kojyyo + "\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_shizaiCd\" id=\"txtShizai_shizaiCd\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + cd_shizai + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"400px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_shizaiNm\" id=\"txtShizai_shizaiNm\" class=\"table_text_view\" readonly value=\"" + nm_shizai + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_tanka\" id=\"txtShizai_tanka\" style=\"text-align:right\" class=\"table_text_view\" readonly value=\"" + tanka + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_budomari\" id=\"txtShizai_budomari\" style=\"text-align:right\" class=\"table_text_view\" readonly value=\"" + budomari + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_ryo\" id=\"txtShizai_ryo\" class=\"table_text_view\" style=\"text-align:right\"  readonly value=\"" + ryo + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "        <td class=\"column\" width=\"100px\">";
            OutputHtml += "            <input type=\"text\" name=\"txtShizai_seihinCd\" id=\"txtShizai_seihinCd\" style=\"text-align:center\" class=\"table_text_view\" readonly value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
            OutputHtml += "        </td>";
            OutputHtml += "    </tr>";
            
        }
        
    }
    
    OutputHtml += "</table>";
    OutputHtml += "</tbody>";
    OutputHtml += "</table>";
    
    //HTML���o��
    obj.innerHTML = OutputHtml;
    
    return true;
}


//========================================================================================
// ���ތ��e�[�u���s�ǉ�����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// �T�v  �F���ތ��e�[�u���s�ǉ�����
//========================================================================================
function funAddShizaiKoho(xmlData,index){

    //XML�f�[�^�擾
    var tableShizaiNm = "shizai";
    var cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, index);
    var cd_kojyyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyyo", 0, index);
    var kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, index);
    var cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, index);
    var nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, index);
    var tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, index);
    var budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, index);
    var cd_seihin = funXmlRead_3(xmlData, tableShizaiNm, "cd_seihin", 0, index);
    var ryo = funXmlRead_3(xmlData, tableShizaiNm, "ryo", 0, index);

    //�e�[�u���Q��
    var tblShizai = document.getElementById("tblListKoho");

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
    cell1.className = "column";
    cell1.setAttribute("class","column");
    cell1.setAttribute("width","50px");
    cell1.setAttribute("align","center");
    cell1.innerHTML = "<input type=\"checkbox\" name=\"chkShizaiKoho\" tabindex=\"19\" />";

    //�H��L��
    cell2.className = "column";
    cell2.setAttribute("class","column");
    cell2.setAttribute("width","51px");
    cell2.setAttribute("align","center");
    cell2.innerHTML = "<input type=\"text\" id=\"txtKoho_KigouKojo\" name=\"txtKoho_KigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"" + kigo_kojyo + "\" tabindex=\"-1\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKoho_kaishaCd\" name=\"hdnKoho_kaishaCd\" value=\"" + cd_kaisya + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnkoho_kojoCd\"   name=\"hdnkoho_kojoCd\" value=\"" + cd_kojyyo + "\" />";

    //���ރR�[�h
    cell3.className = "column";
    cell3.setAttribute("class","column");
    cell3.setAttribute("width","100px");
    cell3.setAttribute("align","center");
    cell3.innerHTML = "<input type=\"text\"id=\"txtKoho_CdShizai\" name=\"txtKoho_CdShizai\" class=\"table_text_view\" readonly style=\"text-align:center\" value=\"" + cd_shizai + "\" tabindex=\"-1\" />";

    //���ޖ�
    cell4.className = "column";
    cell4.setAttribute("class","column");
    cell4.setAttribute("width","400px");
    cell4.setAttribute("align","left");
    cell4.innerHTML = "<input type=\"text\" id=\"txtKoho_NmShizai\" name=\"txtKoho_NmShizai\" class=\"table_text_view\" readonly style=\"ime-mode:active;text-align:left\" value=\"" + nm_shizai + "\" tabindex=\"-1\" />";

    //�P��
    cell5.className = "column";
    cell5.setAttribute("class","column");
    cell5.setAttribute("width","100px");
    cell5.setAttribute("align","right");
    cell5.innerHTML = "<input type=\"text\" id=\"txtKoho_TankaShizai\" name=\"txtKoho_TankaShizai\" class=\"table_text_view\" readonly style=\"text-align:right\" value=\"" + tanka + "\" tabindex=\"-1\" />";

    //����
    cell6.className = "column";
    cell6.setAttribute("class","column");
    cell6.setAttribute("width","100px");
    cell6.setAttribute("align","right");
    cell6.innerHTML = "<input type=\"text\" id=\"txtKoho_BudomariShizai\" name=\"txtKoho_BudomariShizai\" class=\"table_text_view\" readonly style=\"text-align:right\" value=\"" + budomari + "\" tabindex=\"-1\" />";

    //�g�p��/�P�[�X
    cell7.className = "column";
    cell7.setAttribute("class","column");
    cell7.setAttribute("width","100px");
    cell7.setAttribute("align","right");
    cell7.innerHTML = "<input type=\"text\" id=\"txtKoho_SiyouShizai\" name=\"txtKoho_SiyouShizai\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + ryo + "\" tabindex=\"-1\" />";
    
    //���i�R�[�h
    cell8.className = "column";
    cell8.setAttribute("class","column");
    cell8.setAttribute("width","100px");
    cell8.setAttribute("align","center");
    cell8.innerHTML = "<input type=\"text\" id=\"txtKoho_SeihinCd\" name=\"txtKoho_SeihinCd\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"" + cd_seihin + "\" tabindex=\"-1\" />";
    
   return true;
}


//========================================================================================
// ���i�e�[�u���N���A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�Ȃ�
// �߂�l�F�J�����g�y�[�W
// �T�v  �F�I�𒆂̃y�[�W�ԍ���Ԃ��B
//========================================================================================
function funSeihinClear(){
    
    var frm = document.frm00;
    
    //�e�[�u���Q��
    var tblShizai = document.getElementById("tblListSeihin");
    var gyoCount = tblShizai.rows.length;
	
	//�e�[�u���N���A
    for(var i=0; i<gyoCount; i++){
        //�s�폜
        tblShizai.deleteRow(i);
        i = i - 1;
        gyoCount = gyoCount - 1;
    }
	
	//�߰�ޏ��N���A
    spnRecCnt.innerText = "";
    spnRowMax.innerText = "";
    spnCurPage.innerText = "";
	divPage.innerText = "";
    
}


//========================================================================================
// ���ރe�[�u���N���A����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�Ȃ�
// �߂�l�F�J�����g�y�[�W
// �T�v  �F�I�𒆂̃y�[�W�ԍ���Ԃ��B
//========================================================================================
function funShizaiClear(){
    
    var frm = document.frm00;
    
    //�e�[�u���Q��
    var tblShizai = document.getElementById("tblListShizai");
    var gyoCount = tblShizai.rows.length;
	
	//�e�[�u���N���A
    for(var i=0; i<gyoCount; i++){
        //�s�폜
        tblShizai.deleteRow(i);
        i = i - 1;
        gyoCount = gyoCount - 1;
    }
	
	//�߰�ޏ��N���A
    spnRecCnt2.innerText = "";
    spnRowMax2.innerText = "";
    spnCurPage2.innerText = "";
	divPage2.innerText = "";
    
}


//========================================================================================
// ���s�폜����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F�I������Ă�����s���폜����
//========================================================================================
function funDeleteKoho(){
    
    //�e�[�u���Q��
    var frm = document.frm00; 
    var tblShizai = document.getElementById("tblListKoho");
    var gyoCount = tblShizai.rows.length;

	if(gyoCount <= 1){
        if(frm.chkShizaiKoho == null){
        	//���X�g�ɍs�����݂��Ȃ��ꍇ
        	return false;
        }else{
        	if(frm.chkShizaiKoho.checked){
            	//�I������Ă���s�̏ꍇ�s�폜
            	tblShizai.deleteRow(0);
            	}else{
            	//���X�g�ɍs�����݂��邪�`�F�b�N������Ă��Ȃ��ꍇ
            	return false;
        	}
        }
    }else{
    //���ލs�̍폜
	    for( var i=0; i < gyoCount; i++ ){
	    	//�I������Ă���s�̏ꍇ
	        if(frm.chkShizaiKoho[i].checked){
	            //�s�폜
	            tblShizai.deleteRow(i);
	            i = i - 1;
	            gyoCount = gyoCount - 1;
	        }
	    }
    }
    return true;
}


//========================================================================================
// ���m�菈��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// �T�v  �F���m�菈��
//========================================================================================
function funKakuteiKoho(){
    
    var frmDa = window.dialogArguments.frm00;    //�e̫�тւ̎Q�Ɓi���׃t���[���j
    var docDa = window.dialogArguments.document; //�edocument�ւ̎Q�Ɓi���׃t���[���j
    var frm = document.frm00;
    
    //�������Z �e�[�u���Q��
    var tblShizai = docDa.getElementById("tblList4");
    var gyoCount = tblShizai.rows.length;
    var lastIndex = -1;
    
    //�ގ��i���� �e�[�u���Q��
    var tblShizaiR = document.getElementById("tblListKoho");
    var gyoCountR = tblShizaiR.rows.length;
    
    //���͂���Ă���ŏI�s�̌���
    for(var i=0; i<gyoCount; i++){
        
        //�������͂���Ă��Ȃ��ꍇ
        if( frmDa.txtCdShizai[i].value == ""
            && frmDa.txtNmShizai[i].value == ""
            && frmDa.txtTankaShizai[i].value == ""
            && frmDa.txtBudomariShizai[i].value == ""
            && frmDa.txtSiyouShizai[i].value == "" ){
            
            //�������Ȃ�
            
            
        //�������͂���Ă���ꍇ
        }else{
           
           lastIndex = i;
           
        }
    }
    
    //�ǉ��J�n�s�̐ݒ�
    lastIndex++;
    
    //��ǉ�
    for( var i = 0; i < gyoCountR; i++ ){

        //�������Z ���ލs�ǉ�����
        funAddShizai(lastIndex);
        
        if(gyoCountR <= 1){
            
            //�H��L��
            frmDa.txtKigouKojo[lastIndex].value = frm.txtKoho_KigouKojo.value;

            //��ЃR�[�h
            frmDa.hdnKaisha_Shizai[lastIndex].value = frm.hdnKoho_kaishaCd.value;

            //�H��R�[�h
            frmDa.hdnKojo_Shizai[lastIndex].value = frm.hdnkoho_kojoCd.value;

            //���ރR�[�h
            frmDa.txtCdShizai[lastIndex].value = frm.txtKoho_CdShizai.value;

            //���ޖ�
            frmDa.txtNmShizai[lastIndex].value = frm.txtKoho_NmShizai.value;

            //�P��
            frmDa.txtTankaShizai[lastIndex].value = frm.txtKoho_TankaShizai.value;

            //����
            frmDa.txtBudomariShizai[lastIndex].value = frm.txtKoho_BudomariShizai.value;

            //�g�p��/�P�[�X
            frmDa.txtSiyouShizai[lastIndex].value = frm.txtKoho_SiyouShizai.value;
            
        }else{
            
            //�H��L��
            frmDa.txtKigouKojo[lastIndex].value = frm.txtKoho_KigouKojo[i].value;

            //��ЃR�[�h
            frmDa.hdnKaisha_Shizai[lastIndex].value = frm.hdnKoho_kaishaCd[i].value;

            //�H��R�[�h
            frmDa.hdnKojo_Shizai[lastIndex].value = frm.hdnkoho_kojoCd[i].value;

            //���ރR�[�h
            frmDa.txtCdShizai[lastIndex].value = frm.txtKoho_CdShizai[i].value;

            //���ޖ�
            frmDa.txtNmShizai[lastIndex].value = frm.txtKoho_NmShizai[i].value;

            //�P��
            frmDa.txtTankaShizai[lastIndex].value = frm.txtKoho_TankaShizai[i].value;

            //����
            frmDa.txtBudomariShizai[lastIndex].value = frm.txtKoho_BudomariShizai[i].value;

            //�g�p��/�P�[�X
            frmDa.txtSiyouShizai[lastIndex].value = frm.txtKoho_SiyouShizai[i].value;
        }
        

        //�������Z �}���s�J�E���g
        lastIndex++;

    }
    
    //��ʏI��
    funEnd();
    
    return true;
}


//========================================================================================
// ���ލs�ǉ�����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/28
// �T�v  �F�ŏI�s��ǉ�����
//========================================================================================
function funAddShizai(lastIndex){

    //�����ڰт�Document�Q��
    var detailDoc = window.dialogArguments.document;
    var frm = window.dialogArguments.frm00;
    
    // �H��@�S�����
    var cd_kaisha = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;
    // �H��@�S���H��
    var cd_kojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //�e�[�u���Q��
    var tblShizai = detailDoc.getElementById("tblList4");

    //�s���擾
    var strlength = lastIndex;
    
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
    cell7.setAttribute("width","120px");
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
// XML�t�@�C���ɏ�������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@XmlId  �FXMLID
//       �F�AreqAry �F�@�\ID�ʑ��MXML(�z��)
//       �F�BMode   �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
//       �F�Cval    �F�ݒ�l
// �߂�l�F�Ȃ�
// �T�v  �F�@�\ID���Ƃ�XML�t�@�C���Ɉ�����ݒ肷��
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode, val) {
    
    var frm = document.frm00;    //̫�тւ̎Q��
    var frmDa = window.dialogArguments.frm00; //�e̫�тւ̎Q�Ɓi���׃t���[���j
    var i;
    
    for (i = 0; i < reqAry.length; i++) {
        
        //��ʏ����\���i���ʏ��j
        if (XmlId.toString() == "RGEN1010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1010
                    funXmlWrite(reqAry[i], "cd_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, 0);
                    break;
                case 2:    //FGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", RuizihinKensakuId, 0);
                    break;
            }
        }
        //�H�ꌟ���i��ЕύX���j
        else if (XmlId.toString() == "RGEN1020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", val, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", RuizihinKensakuId, 0);
                    break;
            }
        }
        //���i����
        else if (XmlId.toString() == "RGEN1030"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1030
                    funXmlWrite(reqAry[i], "cd_kaisya", frm.selectSeihinKaisha.options[frm.selectSeihinKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", frm.selectSeihinKojo.options[frm.selectSeihinKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_seihin", frm.inputSeihinCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_seihin", frm.inputSeihinNm.value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage_Seihin(), 0);
                    break;
            }
        }
        //���i��莑�ޓ]��
        else if (XmlId.toString() == "RGEN1040"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1035
                    // ���i�e�[�u���I�u�W�F�N�g�擾
                    var tblSeihin = document.getElementById("tblListSeihin");
                    // ���ޕ\�̍s���擾
                    recCnt = tblSeihin.rows.length;
                    // XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    // XML�֏�������
                    for(var j=0; j<recCnt; j++){
                        // �I������Ă���s�̏ꍇ
                        if(recCnt <= 1){
                            
                            if(frm.chkSeihin.checked){
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnSeihin_kasihaCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnSeihin_kojoCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtSeihin_seihinCd.value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "no_page", funGetCurrentPage_Shizai(), recInsert); 
                            }
                        
                        }else{
                            
                            if(frm.chkSeihin[j].checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN1035", "table");
                                }
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnSeihin_kasihaCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnSeihin_kojoCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtSeihin_seihinCd[j].value, recInsert); 
                                funXmlWrite_Tbl(reqAry[i], "table", "no_page", funGetCurrentPage_Shizai(), recInsert); 
                            	// XML���R�[�h�}���J�E���g+1
                                recInsert++;
                            }
                        }
                        
                    }
                    break;
            }
        }
        //���i����
        else if (XmlId.toString() == "RGEN1050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1040
                    funXmlWrite(reqAry[i], "cd_kaisya", frm.selectShizaiKaisha.options[frm.selectShizaiKaisha.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", frm.selectShizaiKojo.options[frm.selectShizaiKojo.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_shizai", frm.inputShizaiCd.value, 0);
                    funXmlWrite(reqAry[i], "nm_shizai", frm.inputShizaiNm.value, 0);
                    funXmlWrite(reqAry[i], "no_page", funGetCurrentPage_Shizai(), 0);
                    break;
            }
        }
        //���ތ��]��
        else if (XmlId.toString() == "RGEN1060"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN1045
                    var tblShizai;
                    var recCnt = 0;
                    
                    //���ރe�[�u���I�u�W�F�N�g�����݂���ꍇ
                    if(document.getElementById("tblListShizai")){
                        
                        // ���ރe�[�u���I�u�W�F�N�g�擾
                        tblShizai = document.getElementById("tblListShizai");
                        // ���ޕ\�̍s���擾
                        recCnt = tblShizai.rows.length;
                        
                    }
                    // XML���R�[�h�}���J�E���g
                    var recInsert = 0;
                    // XML�֏�������
                    for(var j=0; j<recCnt; j++){
                        // �I������Ă���s�̏ꍇ
                        if(recCnt <= 1){
                            if(frm.chkShizai.checked){
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnShizai_kasihaCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnShizai_kojoCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", frm.txtShizai_shizaiCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtShizai_seihinCd.value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kojyo", frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value, recInsert);
                            }
                        }else{
                            if(frm.chkShizai[j].checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN1045", "table");
                                }
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", frm.hdnShizai_kasihaCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", frm.hdnShizai_kojoCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", frm.txtShizai_shizaiCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_seihin", frm.txtShizai_seihinCd[j].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kaisya", frmDa.ddlSeizoKaisya.options[frmDa.ddlSeizoKaisya.selectedIndex].value, recInsert);
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_tanto_kojyo", frmDa.ddlSeizoKojo.options[frmDa.ddlSeizoKojo.selectedIndex].value, recInsert);
                            	// XML���R�[�h�}���J�E���g+1
                                recInsert++;
                            }
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
        
        //��ЃR���{�{�b�N�X
        case 1:
            atbName = "nm_kaisya"; //��Ж�
            atbCd = "cd_kaisya";   //���CD
            tableNm = "kaisya";    //�e�[�u���s
            reccnt = funGetLength_3(xmlData, tableNm, 0); //�����擾
            break;
        
        //�H��R���{�{�b�N�X
        case 2:
            atbName = "nm_kojyo"; //�H�ꖼ
            atbCd = "cd_kojyo";  //�H��CD
            tableNm = "kojyo";   //�e�[�u���s
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
//       �F�Bval      �F�ݒ�l
// �T�v  �F�R���{�{�b�N�X�̃f�t�H���g�l��I������
//========================================================================================
function funDefaultIndex(obj, mode, val) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var selIndex;
    var i;

    //XML�ƃR���{�{�b�N�XVALUE�l�𔻒�
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //��ЁA�H��
                if (obj.options[i].value == val) {
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
// �󔒒u���֐��i"" �� "&nbsp;"�j
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/23
// ����  �F�Ȃ�
// �T�v  �F"" �� "&nbsp;" �֒u������
//========================================================================================
function funKuhakuChg(strChk) {
    
    //�󔒂̏ꍇ
    if(strChk == ""){
        return "&nbsp;";
    }
    //�󔒂łȂ��ꍇ
    else{
        return strChk;
    }

}

//========================================================================================
// ���i�ꊇ�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// ����  �F�Ȃ�
// �T�v  �F���i�ꊇ�I��
//========================================================================================
function funSeihinCheck() {
    
    //�����ڰт�̫�юQ��
    var frm = document.frm00;
    
    //�e�[�u���������ς݂̏ꍇ
    if(document.getElementById("tblListSeihin")){
        //�e�[�u���Q��
        var tblShizai = document.getElementById("tblListSeihin");
        var gyoCount = tblShizai.rows.length;
        //�ꊇ�`�F�b�N
        if(frm.chkSeihinIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkSeihin.checked = true;
            	}else{
            		frm.chkSeihin[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkSeihin.checked = false;
            	}else{
            		frm.chkSeihin[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}


//========================================================================================
// ���ވꊇ�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// ����  �F�Ȃ�
// �T�v  �F���ވꊇ�I��
//========================================================================================
function funShizaiCheck() {
    
    //�����ڰт�̫�юQ��
    var frm = document.frm00;
    
    //�e�[�u���������ς݂̏ꍇ
    if(document.getElementById("tblListShizai")){
        //�e�[�u���Q��
        var tblShizai = document.getElementById("tblListShizai");
        var gyoCount = tblShizai.rows.length;
        //�ꊇ�`�F�b�N
        if(frm.chkShizaiIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkShizai.checked = true;
            	}else{
            		frm.chkShizai[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
            	
            	if(gyoCount <= 1){
            		frm.chkShizai.checked = false;
            	}else{
            		frm.chkShizai[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}

//========================================================================================
// ���ꊇ�I��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/06
// ����  �F�Ȃ�
// �T�v  �F���ꊇ�I��
//========================================================================================
function funKohoCheck() {
    
    //�����ڰт�̫�юQ��
    var frm = document.frm00;
    
    //�e�[�u���������ς݂̏ꍇ
    if(document.getElementById("tblListKoho")){
        //�e�[�u���Q��
        var tblShizai = document.getElementById("tblListKoho");
        var gyoCount = tblShizai.rows.length;
        //�ꊇ�`�F�b�N
        if(frm.chkKohoIkkatu.checked){
            
            for(var i=0; i<gyoCount; i++){
            	if(gyoCount <= 1){
            		frm.chkShizaiKoho.checked = true;
            	}else{
            		frm.chkShizaiKoho[i].checked = true;
            	}
            }
        
        }else{
        
            for(var i=0; i<gyoCount; i++){
                if(gyoCount <= 1){
            		frm.chkShizaiKoho.checked = false;
            	}else{
            		frm.chkShizaiKoho[i].checked = false;
            	}
            }
            
        }
    }
    
    return true;
}


//========================================================================================
// �R�[�h0���ߏ���
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/20
// ����  �F�@obj �F�I�u�W�F�N�g
//       �F�Amode�F1:���i,2:����
// �T�v  �F�R�[�h0���ߏ���
//========================================================================================
function funInsertCdZero(obj,mode){
    
    var frm = document.frm00;
    var keta;
    
    //���i�����w��
    if( mode == 1 ){
        keta = keta_seihin;
    }
    //���ތ����w��
    else if( mode == 2 ){
        keta = keta_shizai;
    }
    
    //0���ߎ��s
    funBuryZero(obj, keta);
    
    return true;
}




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//                                 ���i�e�[�u�� �߰��No�ݒ�
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//========================================================================================
// ���i�e�[�u���y�[�W�J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/04/06
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove_Seihin(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage_Seihin(NextPage);

    //�w���߰�ނ��ް��擾
    funSeihinSearch();
}

//========================================================================================
// ���i�e�[�u���J�����g�y�[�W�ݒ菈��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@pageNo �F�J�����g�y�[�W
// �߂�l�F�Ȃ�
// �T�v  �F�I�𒆂̃y�[�W�ԍ���ݒ肷��B
//========================================================================================
function funSetCurrentPage_Seihin(PageNo) {
    
    CurrentPage_Seihin = PageNo;

    return true;

}

//========================================================================================
// ���i�e�[�u���y�[�W�����N�쐬
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@PageNo   �F���݂̃y�[�W�ԍ�
//       �F�AMaxPageNo�F�ŏI�y�[�W�ԍ�
//       �F�BLinkId   �F�y�[�W�����N�ݒ�I�u�W�F�N�gID
//       �F�CTblId    �F���ו��e�[�u���I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�y�[�W�J�ڗp��HTML���𐶐��A�o�͂���B
//========================================================================================
function funCreatePageLink_Seihin(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "�ŏ�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Seihin(1);\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>�ŏ�</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;�@";

        //�J�n�߰�ށA�I���߰�ވʒu�̎擾
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "�@";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove_Seihin(" + i + ");\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;�@";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "�Ō�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Seihin(" + MaxPageNo + ");\" tabindex=\"8\" style=\"cursor:pointer;color:blue;\"><u>�Ō�</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //�ް������݂��Ȃ��ꍇ���ݸ��\�����Ȃ�
        OutputHtml = "";
    }

    //HTML���o��
    obj.innerHTML = OutputHtml;
    CurrentPage_Seihin = PageNo;

    return true;

}

//========================================================================================
// ���i�e�[�u���J�����g�y�[�W�擾����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�Ȃ�
// �߂�l�F�J�����g�y�[�W
// �T�v  �F�I�𒆂̃y�[�W�ԍ���Ԃ��B
//========================================================================================
function funGetCurrentPage_Seihin() {

    return CurrentPage_Seihin;

}



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//                                 ���ރe�[�u�� �߰��No�ݒ�
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//========================================================================================
// ���ރe�[�u���y�[�W�J��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/04/06
// ����  �F�@NextPage   �F���̃y�[�W�ԍ�
// �߂�l�F�Ȃ�
// �T�v  �F�w��y�[�W�̏���\������B
//========================================================================================
function funPageMove_Shizai(NextPage) {

    //���߰�ނ̐ݒ�
    funSetCurrentPage_Shizai(NextPage);
    
    //�w���߰�ނ��ް��擾�i���i-���ތ����̏ꍇ�j
    if(divKensakuZyotai.innerText == I000011){
        
        //���i��莑�ޓ]��
        funSeihin_ShizaiSearch();
        
    //��L�ȊO�̏ꍇ�i���ޒP�i�����j
    }else{
        
        //���ތ���
        funShizaiSearch();
    }
    
    return true;
}

//========================================================================================
// ���ރe�[�u���J�����g�y�[�W�ݒ菈��
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@pageNo �F�J�����g�y�[�W
// �߂�l�F�Ȃ�
// �T�v  �F�I�𒆂̃y�[�W�ԍ���ݒ肷��B
//========================================================================================
function funSetCurrentPage_Shizai(PageNo) {
    
    CurrentPage_Shizai = PageNo;

    return true;

}

//========================================================================================
// ���ރe�[�u���y�[�W�����N�쐬
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�@PageNo   �F���݂̃y�[�W�ԍ�
//       �F�AMaxPageNo�F�ŏI�y�[�W�ԍ�
//       �F�BLinkId   �F�y�[�W�����N�ݒ�I�u�W�F�N�gID
//       �F�CTblId    �F���ו��e�[�u���I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�y�[�W�J�ڗp��HTML���𐶐��A�o�͂���B
//========================================================================================
function funCreatePageLink_Shizai(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "�ŏ�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Shizai(1);\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>�ŏ�</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;�@";

        //�J�n�߰�ށA�I���߰�ވʒu�̎擾
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "�@";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove_Shizai(" + i + ");\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;�@";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "�Ō�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove_Shizai(" + MaxPageNo + ");\" tabindex=\"17\" style=\"cursor:pointer;color:blue;\"><u>�Ō�</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //�ް������݂��Ȃ��ꍇ���ݸ��\�����Ȃ�
        OutputHtml = "";
    }

    //HTML���o��
    obj.innerHTML = OutputHtml;
    CurrentPage_Shizai = PageNo;

    return true;

}

//========================================================================================
// ���ރe�[�u���J�����g�y�[�W�擾����
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// ����  �F�Ȃ�
// �߂�l�F�J�����g�y�[�W
// �T�v  �F�I�𒆂̃y�[�W�ԍ���Ԃ��B
//========================================================================================
function funGetCurrentPage_Shizai() {

    return CurrentPage_Shizai;

}


//========================================================================================
// �I���{�^������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/07
// �T�v  �F�I������
//========================================================================================
function funEndClick(){
    
    //�ގ��i���� ���e�[�u���Q��
    var tblShizaiR = document.getElementById("tblListKoho");
    var gyoCountR = tblShizaiR.rows.length;
    
    //��₪�]������Ă��Ȃ��ꍇ�ɁA�m�F���b�Z�[�W
    if( gyoCountR >= 1 ){
        
        //�m�F���b�Z�[�W��\��
        if(window.confirm(E000010)){
            //�I������
            funEnd();
        }else{
            //�������Ȃ�
            return true;
        }
        
    }else{
        //�I������
        funEnd();
    }

}

//========================================================================================
// �I������
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/05
// �T�v  �F�I������
//========================================================================================
function funEnd(){
    
    //��ʂ����
    window.close();
    
}

