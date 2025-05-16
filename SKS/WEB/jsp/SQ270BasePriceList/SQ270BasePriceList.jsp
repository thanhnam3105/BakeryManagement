<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�x�[�X�P���ꗗ���                                                -->
<!-- �쐬�ҁFBRC Koizumi                                                             -->
<!-- �쐬���F2016/09/01                                                              -->
<!-- �T�v  �F���������Ɉ�v����x�[�X�P�����ꗗ�ŕ\������B                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

            document.getElementById("xmlRGEN3000").src = null;
            document.getElementById("xmlFGEN3000I").src = null;
            document.getElementById("xmlFGEN3000O").src = null;

            document.getElementById("xmlRGEN3010").src = null;
            document.getElementById("xmlFGEN3010I").src = null;
            document.getElementById("xmlFGEN3010O").src = null;

            document.getElementById("xmlRGEN3500").src = null;
            document.getElementById("xmlFGEN3500I").src = null;
            document.getElementById("xmlFGEN3500O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;
            document.getElementById("xmlUSERINFO_O").src = null;
            document.getElementById("xmlRESULT").src = null;

            document.getElementById("xmlFGEN3520I").src = null;
            document.getElementById("xmlRGEN2160").src = null;
            document.getElementById("xmlFGEN3520O").src = null;
        }
    // -->
    </script>

        <title></title>
        <!-- ���� -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- �� -->
        <script type="text/javascript" src="include/SQ270BasePriceList.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script language="JavaScript" type="text/javascript">
            var myVersion;

            // �u���E�U�̃o�[�W����
            // �u���E�U���擾
            myBsr   = navigator.appName;
            // �G�[�W�F���g�擾
            myAgent = navigator.userAgent;
            // OP �`�F�b�N
            myTop = myAgent.indexOf("Opera",0);
            // OP�łȂ��ꍇ
            if (myTop == -1){

               // IE �`�F�b�N
               myTop = myAgent.indexOf("MSIE",0);
               // IE�łȂ��ꍇ
               if (myTop == -1){
                  // NN �`�F�b�N
                  myTop = myAgent.indexOf("Mozilla/",0);
                  // NN�łȂ��ꍇ
                  if (myTop == -1){
                     myVersion = "";
                  // NN�̏ꍇ
                  }else{
                     // NN�̃o�[�W�����؂���
                     myLast = myAgent.indexOf(" ",myTop);
                     myVer = myAgent.substring(myTop+8,myLast);
                     myVersion = myVer;
                  }
               // IE�̏ꍇ
               }else{
                  myLast = myAgent.indexOf(";",myTop);
                  // IE�̃o�[�W�����؂���
                  myVer = myAgent.substring(myTop+5,myLast);
                  myVersion = myVer;
               }
            // OP�̏ꍇ
            }else{
               myBsr = "Opera";
               myLast = myAgent.indexOf(" ",myTop+6);
               // OP�̃o�[�W�����؂���
               myVer = myAgent.substring(myTop+6,myLast);
               myVersion = myVer;
            }

        </script>

        <script language="VBScript">
            dim javawsInstalled

            'MsgBox myVersion

            dim jws
            jws = split(ConCheckJWSver,ConDelimiter)

            Function funGetJWSInstall()

                on error resume next

                If myVersion = 6 Then
                'IE6�̏ꍇ�AJRE1.4.2�ް�ޮ������p�̵�޼ު�Ă𐶐�����
                    If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.4.2.0"))) Then
                        javawsInstalled = 0
                    Else
                        javawsInstalled = 1
                    End If
                'ElseIf myVersion >= 7 Then
                    'IE8�̏ꍇ�AJRE1.5.0�ް�ޮ������p�̵�޼ު�Ă𐶐�����
                    'If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.5.0.0"))) Then
                        'javawsInstalled = 0
                    'Else
                        'javawsInstalled = 1
                    'End If
                ElseIf myVersion >= 7 Then
                    'IE7�ȍ~�̏ꍇ�AConst�ɐݒ肳��Ă����ް�ޮ������p�̵�޼ު�Ă𐶐�����
                    dim i
                    i = 0
                    do
                        If Not(IsObject(CreateObject("JavaWebStart.isInstalled." & jws(i)))) Then
                           javawsInstalled = 0
                        Else
                           javawsInstalled = 1
                        End If

                        i = i + 1
                    Loop Until i > Ubound(jws) Or javawsInstalled = 1
                Else

                End If

                '�ް�ޮ������p�̵�޼ު�Ă𐶐�����
                'If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.4.2.0"))) Then
                '    javawsInstalled = 0
                'Else
                '    javawsInstalled = 1
                'End If

            End Function
        </script>

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //������ү���ޔ�\��
                funClearRunMessage();
            }
        </script>

        <!--  �e�[�u�����׍s�N���b�N -->
        <script for="tblList" event="onclick" language="JavaScript">
            //�I���s�̔w�i�F��ύX
            funChangeSelectRowColor();
        </script>
        <script for="tblList" event="ondblclick" language="JavaScript">
            //�I���s�_�u���N���b�N�ɂ��u�o�^�E���F�v��ʑJ��
            funOpenBasePriceAdd(2);
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3000"></xml>
        <xml id="xmlRGEN3010"></xml>
        <xml id="xmlRGEN3500"></xml>
        <xml id="xmlRGEN3040"></xml>
        <xml id="xmlRGEN3550"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3000I" src="../../model/FGEN3000I.xml"></xml>
        <xml id="xmlFGEN3010I" src="../../model/FGEN3010I.xml"></xml>
        <xml id="xmlFGEN3500I" src="../../model/FGEN3500I.xml"></xml>
        <xml id="xmlFGEN3520I" src="../../model/FGEN3520I.xml"></xml>
        <xml id="xmlFGEN3550I" src="../../model/FGEN3550I.xml"></xml>
        <xml id="xmlRGEN2160"></xml>
        <xml id="xmlFGEN3550"></xml>
        <xml id="xmlFGEN3000O"></xml>
        <xml id="xmlFGEN3010O"></xml>
        <xml id="xmlFGEN3500O"></xml>
        <xml id="xmlFGEN3520O"></xml>
        <xml id="xmlFGEN3040O"></xml>
        <xml id="xmlFGEN3550O"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- �S�������Ή� -->
        <xml id="xmlRGEN3510"></xml>
        <xml id="xmlFGEN3510"></xml>
        <xml id="xmlFGEN3510I" src="../../model/FGEN3510I.xml"></xml>
        <xml id="xmlFGEN3510O"></xml>

        <!-- ���g�p�����Ή� -->
        <xml id="xmlRGEN3720"></xml>
        <xml id="xmlFGEN3720I" src="../../model/FGEN3720I.xml"></xml>
        <xml id="xmlFGEN3720O"></xml>

        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlFGEN3000"></xml>
        <xml id="xmlFGEN3010"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�x�[�X�P���ꗗ</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="7">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funNext();" tabindex="8">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="900" datasrc="#xmlFGEN3500I" datafld="rec">
                <!-- 1��� -->
                <tr>
                    <td width="100">���[�J�[��</td>
                    <td width="200">
                        <select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" onChange="funChangeMaker();" tabindex="1">
                        </select>
                    </td>
                    <td width="50">��ޖ�</td>
                    <td width="400">
                        <select id="ddlHouzai" name="ddlHouzai" datafld="cd_houzai" style="width:360px;" tabindex="2">
                        </select>
                    </td>
                    <td width="50">�Ő�</td>
                    <td width="70">
                        <span class="cinput" format="0000000000" required="true" defaultfocus="false" id="em_SeihouNo">
                        <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="width:50px;text-align:right;" tabindex="3">
                        </span>
                    </td>
                </tr>
            </table>

            <!-- ���́E�I�� -->
            <table width="471" datasrc="#xmlFGEN3500I" datafld="rec">
                <!-- 2��� -->
                <tr>
                    <td width="90">�o�^�ς�
                        <input type="checkbox" id="chkKakunin" datafld="chk_kakunin" name="chkKakunin" tabindex="4">
                    </td>
                    <td width="90">���F�ς�
                        <input type="checkbox" id="chkShonin" datafld="chk_shonin" name="chkShonin" tabindex="5">
                    </td>
                    <td width="64">�S��
                        <input type="checkbox" id="chkAll" name="chkAll" tabindex="6">
                    </td>
                    <td width="102">�L���ł̂�
                    	<input type="checkbox" id="chkHanOnly" name="chkHanOnly" tabindex="7" checked>
                    </td>
                    <td width="90">���g�p
                    	<input type="checkbox" id="chkMishiyo" name="chkMishiyo" tabindex="8">
                    </td>
                </tr>
            </table>

            <!-- [�x�[�X�P���ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:65%;width:1470px;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" cellpadding="3" width="1450px" align="center">
                <colgroup>
                    <col style="width:30px;"/><!--  No. -->
                    <col style="width:160px;"/><!-- ���[�J�[�� -->
                    <col style="width:30px;"/><!--  �Ő� -->
                    <col style="width:400px;"/><!-- �ł̕�ޖ� -->
                    <col style="width:120px;"/><!-- �o�^�� -->
                    <col style="width:120px;"/><!-- ���F�� -->
                    <col style="width:90px;"/><!-- �L���J�n�� -->
                    <col style="width:80px;"/><!-- �ύX�� -->
                    <col style="width:400px;"/><!-- �x�[�X��ޖ� -->
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">��</th>
                        <th class="columntitle">���[�J�[��</th>
                        <th class="columntitle">��<BR>��</th>
                        <th class="columntitle">�ł̕�ޖ�</th>
                        <th class="columntitle">�o�^��</th>
                        <th class="columntitle">���F��</th>
                        <th class="columntitle">�L���J�n��</th>
                        <th class="columntitle">�ύX��</th>
                        <th class="columntitle">�x�[�X��ޖ�</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="3" cellspacing="0" border="1" bordercolor="#C0C0C0" style="width:1450px;display:none">
                        <!-- [�x�[�X�P���ꗗ] ���׍s -->
                        <tbody id="detail"></tbody>
                    </table>
                </tbody>
            </table>
            </div>

            <table width="870" style="margin-top:10px;">
                <tr>
                    <td>
                        <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="�R�s�[" onClick="funOpenBasePriceAdd(3);" disabled tabindex="-1">
                        <input type="button" class="normalbutton" id="btnNew" name="btnNew" value="�o�^" onClick="funOpenBasePriceAdd(2);" disabled tabindex="-1">
                        <input type="button" class="normalbutton" id="btnShonin" name="btnShonin" value="���F" onClick="funOpenBasePriceAdd(4);" disabled tabindex="-1">
                    </td>
                </tr>
            </table>
            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode">
        </form>
    </body>
</html>
