<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!-----------------------------------------------------------------------------------
 ' �f���A������DB�@CSV�t�@�C���_�E�����[�h����                                       
 ' �쐬�ҁFTT.Jinbo                                                                  
 ' �쐬���F2008/09/10                                                                
 ' �T�v  �F                                                                          
 '-----------------------------------------------------------------------------------
 ' �ύX�ҁF                                                                          
 ' �ύX���F                                                                          
 ' �T�v  �F                                                                          
 '----------------------------------------------------------------------------------->

<html>
    <head>
        <title>�T���v���y�[�W</title>
   <!--===================================================================
        INCLUDE SCRIPT & CSS
     ===================================================================-->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>

    <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css" />

<!-- 
    <xml id="xmlJSP0010"></xml>
    <xml id="xmlSA020I" src="SA020I.xml"></xml>
    <xml id="xmlSA020O"></xml> -->
    
    <!-- �e�X�g�@�F�@�C���v�b�g�`�F�b�N
    <xml id="xmlJW_TEST" src="./JW/JW020.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW030.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW040.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW050.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW060.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW110.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW210.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW610.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW620.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW630.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW710.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW720.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW/JW730.xml"></xml>
    -->
    <xml id="xmlJW_TEST" src="JW820.xml"></xml>
    
    <!-- ���[�p
    <xml id="xmlJW_TEST" src="./JW_E/JW130.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW_E/JW740.xml"></xml>
    <xml id="xmlJW_TEST" src="./JW_E/JW750.xml"></xml>
    -->
    
    <!-- �e�X�g�@�F�@�������W�b�N
    <xml id="xmlSA590I" src="./SA/SA590I.xml"></xml>
    <xml id="xmlSA590O"></xml>
    <xml id="xmlSA570I" src="./SA/SA570I.xml"></xml>
    <xml id="xmlSA570O"></xml>
    <xml id="xmlSA510I" src="./SA/SA510I.xml"></xml>
    <xml id="xmlSA510O"></xml>
    -->
    
    <!-- �e�X�g�@�F�@�o�^�E�X�V���W�b�N
    <xml id="xmlSA420I" src="./SA/SA420I.xml"></xml>
    <xml id="xmlSA420O"></xml>
    <xml id="xmlSA410I" src="./SA/SA410I.xml"></xml>
    <xml id="xmlSA410O"></xml>
    <xml id="xmlSA370I" src="./SA/SA370I.xml"></xml>
    <xml id="xmlSA370O"></xml>
    <xml id="xmlSA380I" src="./SA/SA380I.xml"></xml>
    <xml id="xmlSA380O"></xml>
    <xml id="xmlSA490I" src="./SA/SA490I.xml"></xml>
    <xml id="xmlSA490O"></xml>
    <xml id="xmlSA380I" src="./SA/SA380I.xml"></xml>
    <xml id="xmlSA380O"></xml>
    -->

        <script language="JavaScript">
        <!--

            function funFile() {
var ret;
var RequestList = new Array();
var ResponseList = new Array();
var FunctionIdList = new Array();

funXmlConnect(xmlJW_TEST);

/*
//SA590
RequestList[0] = xmlSA590I;
ResponseList[0] = xmlSA590O;
FunctionIdList[0] = "SA590O";
//SA570
RequestList[0] = xmlSA570I;
ResponseList[0] = xmlSA570O;
FunctionIdList[0] = "SA570O";
//SA510
RequestList[0] = xmlSA510I;
ResponseList[0] = xmlSA510O;
FunctionIdList[0] = "SA510O";
//SA420
RequestList[0] = xmlSA420I;
ResponseList[0] = xmlSA420O;
FunctionIdList[0] = "SA420O";
//SA410
RequestList[0] = xmlSA410I;
ResponseList[0] = xmlSA410O;
FunctionIdList[0] = "SA410O";
//SA370
RequestList[0] = xmlSA370I;
ResponseList[0] = xmlSA370O;
FunctionIdList[0] = "SA370O";
//SA380
RequestList[0] = xmlSA380I;
ResponseList[0] = xmlSA380O;
FunctionIdList[0] = "SA380O";
//SA490
RequestList[0] = xmlSA490I;
ResponseList[0] = xmlSA490O;
FunctionIdList[0] = "SA490O";
//SA380
RequestList[0] = xmlSA380I;
ResponseList[0] = xmlSA380O;
FunctionIdList[0] = "SA380O";
//Ajax�ʐM
if (funAjaxConnection("JSP0010", FunctionIdList, xmlJSP0010, RequestList, ResponseList, 1) == false ) {
    alert("error");
    return false;
}
*/

/*
//���[�U���\��
funInformationDisplay(xmlSA020O, 1, "divInfo");

//�y�[�W�����N�\��
funCreatePageLink(1, 17, "divPage", "tblList");

//�{�^���\��
funCreateMenuButton(xmlSA020O, ConMainMenuId, "divBtnMain");
funCreateMenuButton(xmlSA020O, ConMstMenuId, "divBtnMst");

//��ʑJ��
//funUrlConnect("post.asp?val=�����I", ConConectPost, document.frm00);

/*
//XML����
funXmlMerge(xmlJSP0010,xmlSA020I,"JSP0010");
funXmlMerge(xmlJSP0010,xmlSA010I,"JSP0010");
alert("JSP0010�F" + xmlJSP0010.xml);

//XML����
funXmlDivision(xmlJSP0010O,xmlSA020O,"SA020O");
funXmlDivision(xmlJSP0010O,xmlSA010O,"SA010O");
alert("SA020O�F" + xmlSA020O.xml);
alert("SA010O�F" + xmlSA010O.xml);

//XML�ǂݍ���
ret = funXmlRead(xmlSA020O,"id_user",0);
alert(ret);

//XML��������
if (funXmlWrite(xmlSA020O,"id_user","159",0) == false) {

alert("error");
}


ret = funXmlRead(xmlSA020O,"id_user",0);
alert(ret);

//�߂�l�`�F�b�N
if (funResultCheck(xmlSA020O) == false) {
}
*/

//  JSL.MSGBOX.Info('��񃁃b�Z�[�W...'); //������...
//  JSL.MSGBOX.Warn('���[�j���O���b�Z�[�W...'); //������...
//  JSL.MSGBOX.YesNo('�͂��A���������b�Z�[�W...'); //������...
//  JSL.MSGBOX.YesNoCancel('�͂��A�������A�L�����Z�����b�Z�[�W...'); //������...
            }

            function funItem(mode) {
                funItemControl(document.frm00.txtTest, mode);
                funItemControl(document.frm00.ddlTest, mode);
                funItemControl(document.frm00.chkTest, mode);
                funItemControl(document.frm00.btnTest, mode);
            }

        //-->
        </script>

        <!--  LOGIN�N���b�N -->
        <script for="tblList" event="onclick" language="JavaScript">
            funChangeSelectRowColor();
        </script>
    </head>

    <body onLoad="funFile();">
        <form name="frm00">
            <center>
                <br>
                <!-- �w�b�_�[�� -->
                <table>
                    <tr>
                        <td align="center" class="g_title" colspan="3"><b>�y�V�T�N�C�b�N���ʊ֐��e�X�g�z</b></td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3">�@</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ010Login/SQ010Login.jsp?id=1001','shisakuikku','menubar=no,resizable=yes');">�V�T�N�C�b�N �V���O���T�C���I��</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ010Login/SQ010Login.jsp','shisakuikku','menubar=no,resizable=yes');">�V�T�N�C�b�N ���O�C��</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ020MainMenu/SQ020MainMenu.jsp','shisakuikku','menubar=no,resizable=yes');">���C�����j���[</td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3"><a href="javascript:window.open('../SQ030MstMenu/SQ030MstMenu.jsp','shisakuikku','menubar=no,resizable=yes');">�}�X�^���j���[</td>
                    </tr>
                    <tr>
                        <td align="center" class="g_title">
                            <input type="text" name="txtTest" id="txtTest" value="" onBlur="funBuryZero(this, 10);">
                        </td>
                        <td align="center" class="g_title">
                            <select name="ddlTest" id="ddlTest" style="width:100px">
                                <options value="0">�Ă���</options>
                            </select>
                        </td>
                        <td align="center" class="g_title">
                            <input type="checkbox" name="chkTest" id="chkTest" value="">
                        </td>
                        <td align="center" class="g_title">
                            <input type="button" class="normalbutton" name="btnTest" id="btnTest" value="test">
                        </td>
                    </tr>
                </table>
                <br>
            </center>
            <br>
<input type="button" class="normalbutton" onclick="funItem(true)" value="���۰�ۯ�">
<input type="button" class="normalbutton" onclick="funItem(false)" value="���۰�ۯ�����">
            <br>
            <div id="divInfo"></div>
            <br>
<button onclick="funInfoMsgBox('��񃁃b�Z�[�W...');">���b�Z�[�W[Info]</button>
<button onclick="funErrorMsgBox('�x�����b�Z�[�W...');">���b�Z�[�W[Warn]</button>
<button onclick="funWarnMsgBox('�͂��A�������A�L�����Z�����b�Z�[�W...');">���b�Z�[�W[Yes/no/Cancel]</button>
<button onclick="funConfMsgBox('�͂��A���������b�Z�[�W...');">���b�Z�[�W[Yes/no]</button>
            <br>
            <br>
<button onclick="funShowRunMessage()">������</button>
<button onclick="funClearRunMessage()">��������</button>
            <br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <div id="divBtnMain"></div>
        </td>
    </tr>
</table>
            <br><br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <div id="divBtnMst"></div>
        </td>
    </tr>
</table>
            <br><br>
<button onclick="funSelectRowDelete(xmlUser)">�s�폜</button>
<span class="scrtable">
<div class="scroll" id="sclList" style="height:150px;width:100%;" rowSelect="true">
<table id="tbl1" name="dataTable" cellspacing="0" style="width:983;">
    <colgroup>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
        <col style="width:150px;"/>
    </colgroup>
    <thead class="rowtitle">
        <tr style="top:expression(offsetParent.scrollTop);position:relative;">
            <td class="columntitle" style="text-align:center">�S���Җ�</td>
            <td class="columntitle" style="text-align:center">��Ж�</td>
            <td class="columntitle" style="text-align:center">������</td>
            <td class="columntitle" style="text-align:center">�O���[�v��</td>
            <td class="columntitle" style="text-align:center">�`�[����</td>
            <td class="columntitle" style="text-align:center">��E��</td>
            <td class="columntitle" style="text-align:center">�V�X�e���o�[�W����</td>
            <td class="columntitle" style="text-align:center">����ID</td>
        </tr>
    </thead>
    <tbody class="disprow">
        <table id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA020O" datafld="rec" style="width:983;word-break:break-all;word-wrap:break-word;font-size:9pt;" datapagesize=5>
            <tr class="disprow" >
                <td class="column" style="width:150px;"><span datafld="nm_user"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_kaisha"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_busho"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_group"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_team"></span></td>
                <td class="column" style="width:150px;"><span datafld="nm_yakushoku"></span></td>
                <td class="column" style="width:150px;"><span datafld="ver_system"></span></td>
                <td class="column" style="width:150px;"><span datafld="id_gamen"></span></td>
            </tr>
        </table>
    </tbody>
</table>
</div>
</span>
            <br><br>
<table style="width:100%; text-align:center;">
    <tr>
        <td style="text-align:center;">
            <span id="divPage"></span>
        </td>
    </tr>
</table>
            <br><br>
<input type="button" class="normalbutton" onclick="tblList.previousPage()" value="�O�y�[�W">
<input type="button" class="normalbutton" onclick="tblList.nextPage()" value="���y�[�W">

            <input type="hidden" name="hidVal" value="�����I">
        </form>
    </body>
</html>
