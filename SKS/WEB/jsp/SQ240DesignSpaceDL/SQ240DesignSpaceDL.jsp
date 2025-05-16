<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�f�U�C���X�y�[�X�_�E�����[�h���                                  -->
<!-- �쐬�ҁFTT.Kitazawa                                                             -->
<!-- �쐬���F2014/09/01                                                              -->
<!-- �T�v  �F���������Ɉ�v����f�U�C���X�y�[�X�ꗗ���_�E�����[�h�B                -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

        document.getElementById("xmlFGEN3250I").src = null;
        document.getElementById("xmlFGEN3250O").src = null;

        document.getElementById("xmlFGEN3260I").src = null;
        document.getElementById("xmlFGEN3260O").src = null;

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
        <script type="text/javascript" src="include/SQ240DesignSpaceDL.js"></script>
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

            var frm = document.frm00;    //̫�тւ̎Q��
            // �I���s�̃`�F�b�N�{�b�N�X�̂Ƀ`�F�b�N��؂�ւ���
            if (!!frm.chk[funGetCurrentRow()]) {
                frm.chk[funGetCurrentRow()].checked = !(frm.chk[funGetCurrentRow()].checked);
            } else {
                // Index ���t���Ă��Ȃ�
                frm.chk.checked = !(frm.chk.checked);
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" style="margin-left:10px;" >
        <!-- XML Document��` -->
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3250"></xml>
        <xml id="xmlRGEN3260"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA290O"></xml>

        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3250I" src="../../model/FGEN3250I.xml"></xml>
        <xml id="xmlFGEN3260I" src="../../model/FGEN3260I.xml"></xml>

        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3250O"></xml>
        <xml id="xmlFGEN3260O"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="30%" class="title">�f�U�C���X�y�[�X�_�E�����[�h</td>
                    <td width="15%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- �����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="5">
                    <!-- �_�E�����[�h�{�^�� -->
                        <input type="button" class="normalbutton" id="btnDownLoad" name="btnDownLoad" value="�_�E�����[�h" onClick="funDownLoad();" tabindex="6">
                    <!-- �I���{�^�� -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEndClick();" tabindex="7">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

                <table width="1050">
                <!-- 1��� -->
                <tr>
                    <td width="80">�����H��</td>
                    <td width="270">
                        <select id="ddlSeizoKojo" name="ddlSeizoKojo" style="width:230px;" onChange="funChangeKojo();" tabindex="1">
                        </select>
                    </td>
                    <td width="80">&nbsp;</td>
                    <td width="270">&nbsp;</td>
                    <td width="80">&nbsp;</td>
                    <td width="270">&nbsp;</td>
                </tr>
                <!-- 2�s�� -->
                <tr>
                    <td>�E��</td>
                    <td>
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="2">
                        </select>
                    </td>
                    <td>�������C��</td>
                    <td>
                        <select id="ddlLine" name="ddlLine" style="width:230px;" onChange="funChangeLine();" tabindex="3">
                        </select>
                    </td>
                    <td>���</td>
                    <td>
                        <select id="ddlSyurui" name="ddlSyurui" style="width:230px;"" onChange="funChangeSyurui();" tabindex="4">
                        </select>
                    </td>
                </tr>
            </table>
            <br><br>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>�y�f�U�C���X�y�[�X���z</td>
                 </tr>
            </table>
            <hr>


            <!-- [�f�U�C���X�y�[�X���ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:65%;width:980px;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="960px" align="center">
                <colgroup>
                    <col style="width:45px;"/>
                    <col style="width:80px;"/>
                    <col style="width:92px;"/>
                    <col style="width:98px;"/>
                    <col style="width:240px;"/>
                    <col style="width:400px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">�I��</th>
                        <th class="columntitle">�����H��</th>
                        <th class="columntitle">�E��</th>
                        <th class="columntitle">�������C��</th>
                        <th class="columntitle">���</th>
                        <th class="columntitle">�t�@�C��</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN3260O" datafld="rec" style="width:960px;display:none">
                        <!-- [�f�U�C���X�y�[�X���ꗗ] ���׍s -->
                        <!-- tbody id="detail"></tbody -->
                      <tr class="disprow">
                        <td class="column" style="width:45px;text-align:center;"><input type="checkbox" name="chk" value=""></td>
                        <td class="column" style="width:80px;text-align:left;"><span datafld="nm_seizokojo"></span></td>
                        <td class="column" style="width:95px;text-align:left;"><span datafld="nm_shokuba"></span></td>
                        <td class="column" style="width:100px;text-align:left;"><span datafld="nm_line"></span></td>
                        <td class="column" style="width:240px;text-align:left;"><span datafld="nm_syurui"></span></td>
                        <td class="column" style="width:400px;text-align:left;"><span datafld="nm_file"></span></td>
                      </tr>
                    </table>
                </tbody>
            </table>
            </div>

            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode">
            <!-- �t�@�C���ۑ���T�[�o�[�p�X�iconst��`���j -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- �T�u�t�H���_�[���F�ۑ��t�@�C�����Ɏw�� �i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- �_�E�����[�h�A�폜�t�@�C�����i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
        </form>
    </body>
</html>
