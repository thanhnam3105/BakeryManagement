<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�f�U�C���X�y�[�X�o�^���                                          -->
<!-- �쐬�ҁFTT.Kitazawa                                                             -->
<!-- �쐬���F2014/09/01                                                              -->
<!-- �T�v  �F���������Ɉ�v����f�U�C���X�y�[�X�ꗗ��\������B                      -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

        document.getElementById("xmlUSERINFO_I").src = null;
        document.getElementById("xmlUSERINFO_O").src = null;
        document.getElementById("xmlRESULT").src = null;

        document.getElementById("xmlRGEN3230").src = null;
        document.getElementById("xmlFGEN3230I").src = null;
        document.getElementById("xmlFGEN3230O").src = null;

        document.getElementById("xmlRGEN3240").src = null;
        document.getElementById("xmlFGEN3240I").src = null;
        document.getElementById("xmlFGEN3240O").src = null;

        document.getElementById("xmlRGEN3250").src = null;
        document.getElementById("xmlFGEN3250I").src = null;
        document.getElementById("xmlFGEN3250O").src = null;

        document.getElementById("xmlRGEN3260").src = null;
        document.getElementById("xmlFGEN3260I").src = null;
        document.getElementById("xmlFGEN3260O").src = null;

        document.getElementById("xmlRGEN3270").src = null;
        document.getElementById("xmlFGEN3270I").src = null;
        document.getElementById("xmlFGEN3270O").src = null;

        document.getElementById("xmlRGEN3280").src = null;
        document.getElementById("xmlFGEN3280I").src = null;
        document.getElementById("xmlFGEN3280O").src = null;

        document.getElementById("xmlRGEN3410").src = null;
        document.getElementById("xmlFGEN3410I").src = null;
        document.getElementById("xmlFGEN3410O").src = null;

        document.getElementById("xmlSA290I").src = null;
        document.getElementById("xmlSA290O").src = null;

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
        <script type="text/javascript" src="include/SQ210DesignSpaceAdd.js"></script>
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
        	// �I���s�̃��W�I�{�^���Ƀ`�F�b�N������
        	if (!!frm.chk[funGetCurrentRow()]) {
        		frm.chk[funGetCurrentRow()].checked = true;
        	} else {
        		// Index ���t���Ă��Ȃ�
        		frm.chk.checked = true;
        	}

        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3250"></xml>
        <xml id="xmlRGEN3260"></xml>
        <xml id="xmlRGEN3270"></xml>
        <xml id="xmlRGEN3280"></xml>
        <xml id="xmlRGEN3410"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>

        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3250I" src="../../model/FGEN3250I.xml"></xml>
        <xml id="xmlFGEN3260I" src="../../model/FGEN3260I.xml"></xml>
        <xml id="xmlFGEN3270I" src="../../model/FGEN3270I.xml"></xml>
        <xml id="xmlFGEN3280I" src="../../model/FGEN3280I.xml"></xml>
        <xml id="xmlFGEN3410I" src="../../model/FGEN3410I.xml"></xml>

        <xml id="xmlSA290O"></xml>
        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3250O"></xml>
        <xml id="xmlFGEN3260O"></xml>
        <xml id="xmlFGEN3270O"></xml>
        <xml id="xmlFGEN3280O"></xml>
        <xml id="xmlFGEN3410O"></xml>

        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlSA290"></xml>
        <xml id="xmlFGEN3250"></xml>
        <xml id="xmlFGEN3260"></xml>


        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�f�U�C���X�y�[�X�o�^</td>
                    <td width="15%">&nbsp;</td>
                    <td width="65%" align="right">
                    <!-- �����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="11">
                    <!-- �s�ǉ��{�^�� -->
                        <input type="button" class="normalbutton" id="btnLineAdd" name="btnLineAdd" value="�s�ǉ�" onClick="funLineAdd();" tabindex="12">
                    <!-- �s�폜�{�^�� -->
                        <input type="button" class="normalbutton" id="btnLineDel" name="btnLineDel" value="�s�폜" onClick="funLineDel();" tabindex="13">
                    <!-- �o�^�{�^�� -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�o�^" onClick="funUpdate();" tabindex="14">
                    <!-- �폜�{�^�� -->
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="�폜" onClick="funDelete();" tabindex="15">
                    <!-- �Q�l�����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSiryo" name="btnSiryo" value="�Q�l����" onClick="funSiryo();" tabindex="16">
                    <!-- �I���{�^�� -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEndClick();" tabindex="17">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="400">
                <!-- 1�s�� -->
                <tr>
                    <td width="100">�����H��</td>
                    <td width="300">
                        <select id="ddlSeizoKojo" name="ddlSeizoKojo" style="width:230px;" onChange="funChangeKojo();" tabindex="1">
                        </select>
                    </td>
                </tr>
                <!-- 2�s�� -->
                <tr>
                    <td>�E��</td>
                    <td>
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="2">
                        </select>
                    </td>
                </tr>
                <!-- 3�s�� -->
                <tr>
                    <td>�������C��</td>
                    <td>
                        <select id="ddlLine" name="ddlLine" style="width:230px;" onChange="funChangeLine();" tabindex="3">
                        </select>
                     </td>
                </tr>
            </table>
            <br/>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>�y�f�U�C���X�y�[�X���z</td>
                 </tr>
            </table>
            <hr>


            <!-- [�f�U�C���X�y�[�X���ꗗ]���X�g width:870 �� 1170px -->
            <div class="scroll" id="sclList" style="height:60%;width:1190px;overflow:scroll;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1170px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:278px;"/>
                    <col style="width:471px;"/>
                    <col style="width:75x;"/>
                    <!-- col style="width:100px;"/ -->
                    <col style="width:160px;"/>
                    <col style="width:150px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative; z-index:9;">
                        <th class="columntitle">�I</th>
                        <th class="columntitle">���</th>
                        <th class="columntitle">�t�@�C��</th>
                        <th class="columntitle">�Q��</th>
                        <!-- th class="columntitle">�N���A</th -->
                        <th class="columntitle">�X�V��</th>
                        <th class="columntitle">�X�V����</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:1170px;display:none">
                    <!-- [�f�U�C���X�y�[�X���ꗗ] ���׍s -->
                    <tbody id="detail"></tbody>

                </table>
            </table>
            </div>
            <br/><br/>

            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode">
            <!-- �Q�l�����I���J�e�S�� -->
            <input type="hidden" id="sq220Category" name="sq220Category">

            <!-- �t�@�C���ۑ���T�[�o�[�p�X�iconst��`���j -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- �T�u�t�H���_�[���F�ۑ��t�@�C�����Ɏw�� �i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- �A�b�v���[�h�Ώۂ̃t�B�[���h���i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strFieldNm" id="strFieldNm">
            <!-- �_�E�����[�h�A�폜�t�@�C�����i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">

	</form>
    </body>
</html>
