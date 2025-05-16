<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�Q�l�������                                                      -->
<!-- �쐬�ҁFTT E.Kitazawa                                                           -->
<!-- �쐬���F2014/09/01                                                              -->
<!-- �T�v  �F�Q�l�������_�E�����[�h�A���̓A�b�v���[�h����B                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

            document.getElementById("xmlRGEN3400").src = null;
            document.getElementById("xmlFGEN3400I").src = null;
            document.getElementById("xmlFGEN3400O").src = null;

            document.getElementById("xmlRGEN3210").src = null;
            document.getElementById("xmlFGEN3210I").src = null;
            document.getElementById("xmlFGEN3210O").src = null;

            document.getElementById("xmlRGEN3220").src = null;
            document.getElementById("xmlFGEN3220I").src = null;
            document.getElementById("xmlFGEN3220O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;
            document.getElementById("xmlUSERINFO_O").src = null;
            document.getElementById("xmlRESULT").src = null;

 }

    // Input[readonly] ��BackSpace�������Ȃ�����  �iIE�ŗL�̖��j
    window.document.onkeydown = function keydown(){
        if( window.event.keyCode == 8 ){
            return false;
        }
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
        <script type="text/javascript" src="include/SQ220ShiryoRef.js"></script>
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

    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3400"></xml>
        <xml id="xmlRGEN3210"></xml>
        <xml id="xmlRGEN3220"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3400I" src="../../model/FGEN3400I.xml"></xml>
        <xml id="xmlFGEN3210I" src="../../model/FGEN3210I.xml"></xml>
        <xml id="xmlFGEN3220I" src="../../model/FGEN3220I.xml"></xml>

        <xml id="xmlFGEN3400O"></xml>
        <xml id="xmlFGEN3210O"></xml>
        <xml id="xmlFGEN3220O"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>


        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlFGEN3400"></xml>


        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
            <table width="99%">
                <tr>
                    <td width="5">&nbsp;</td>
                    <td width="20%" class="title">�Q�l����</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- �_�E�����[�h�{�^�� -->
                        <input type="button" class="normalbutton" id="btnDownLoad" name="btnDownLoad" value="�_�E�����[�h" onClick="funDownLoad();" tabindex="4">
                    <!-- �A�b�v���[�h�{�^��(submit) -->
                        <input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="�A�b�v���[�h" onClick="funUpLoad();" tabindex="5">
                    <!-- �I���{�^�� -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEndClick();" tabindex="6">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table border="0" cellspacing="0" width="99%">
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>�y�Q�l�����z</td>
                 </tr>
                <tr>
                    <td colspan="2">
                        <hr>
                    </td>
                 </tr>
            </table>


            <!-- �Q�l�����I�� -->
            <table width="920" >
                <!-- 1�s�� -->
                 <tr>
                    <td width="5">&nbsp;</td>
                    <td width="250">
                    <!-- �J�e�S���I�� -->
                        <select id="ddlCategoryName" name="ddlCategoryName" datafld="nm_literal" style="width:230px;" onChange="funFileSearch();" onclick="funSetInput(0)" tabindex="1">
                        </select>
                    </td>
                    <td width="570">
                    <!-- �����t�@�C�����F�_�E�����[�h�p -->
                        <input type="text" value="" name="shiryoName" id="shiryoName" size="100" readonly tabindex="-1">
                    </td>
                    <td width="100"></td>
                 </tr>
                <!-- 2�s�ځF�A�b�v���[�h�p -->
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>
                    <!-- �A�b�v���[�h����Q�ƃt�@�C���́u�Q�Ɓv�{�^���Őݒ�i���́A�N���A���s�A�T�C�Y=0�ɂȂ�Ȃ��j�B�\���pInput���s�b�^���d�˂ĉB���I�I -->
                      <div style="position: relative;">
                      <!-- �Q�ƃt�@�C�����F�u�Q�Ɓv�{�^�� -->
                        <input type="file" class="normalbutton" name="filename"   onchange="funChangeFile()" style="width:570px; align=right;" onclick="funSetInput(1)" onkeydown="funEnterFile(event.keyCode);" tabindex="2">
                      <span style="position: absolute; top: 0px; left: 0px;">
                      <!-- �Q�ƃt�@�C�����F�\���p -->
                        <input type="text" value="" name="inputName" id="inputName" size="100" readonly tabindex="-1">
                      </span>
                      </div>
                    </td>
                    <td>
                    <!-- �N���A�{�^���F�폜 -->
                        <!-- input type="button"  class="normalbutton" name="btnClear" id="btnClear" value="�N���A" onClick="funClearInput();" tabindex="3" -->
                    </td>
                </tr>
            </table>

            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode" value="1">
            <!-- �I���J�e�S���� -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- �_�E�����[�h�t�@�C���A�폜�t�@�C����  -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
            <!-- �t�@�C���ۑ���T�[�o�[�p�X�iconst��`���j -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- �A�b�v���[�h�Ώۂ̃t�B�[���h�� -->
            <input type="hidden" value="filename" name="strFieldNm" id="strFieldNm">

        </form>
    </body>
</html>
