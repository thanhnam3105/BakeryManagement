<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�f�U�C���X�y�[�X�o�^���                                          -->
<!-- �쐬�ҁFTT.Kitazawa                                                             -->
<!-- �쐬���F2014/09/01                                                              -->
<!-- �T�v  �F���������Ɉ�v����f�U�C���X�y�[�X�ꗗ��\������B                      -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
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
		<script type="text/javascript" src="include/SQ310HattyuLiteralMst.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

	<style>


	</style>
    <script type="text/javascript">
    <!--
        window.onunload = function() {


            <!-- // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start -->
            //document.getElementById("RGEN3590").src = null;
            document.getElementById("xmlFGEN3580O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;

            document.getElementById("xmlSA890I").src = null;
        }
    // -->
    </script>

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
       <!--
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
        -->
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <!-- �V�K�ǉ�20160912 -->
        <xml id="xmlJSP0611"></xml>  <!-- 610��611 �ύX�ς� -->
        <xml id="xmlJSP0621"></xml>  <!-- 620��621 �ύX�ς� -->
        <xml id="xmlJSP0631"></xml>  <!-- 630��631 �ύX�ς� -->
        <xml id="xmlJSP0632"></xml>  <!-- �ǉ� -->
        <xml id="xmlJSP0641"></xml>  <!-- 640��641 �ύX�ς� -->
        <xml id="xmlJSP0650"></xml>
        <xml id="xmlRGENAAA"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA041I" src="../../model/SA041I.xml"></xml>  <!-- 40��41 �ύX�ς� -->
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA101I" src="../../model/SA101I.xml"></xml>  <!-- 100��101 �ύX�ς� -->
        <xml id="xmlSA102I" src="../../model/SA102I.xml"></xml>  <!-- �V�K�ǉ� -->
        <xml id="xmlSA111I" src="../../model/SA111I.xml"></xml>  <!-- 110��111 �ύX�ς� -->
        <xml id="xmlSA300I" src="../../model/SA300I.xml"></xml>
        <xml id="xmlSA320I" src="../../model/SA320I.xml"></xml>
        <xml id="xmlSA331I" src="../../model/SA331I.xml"></xml>  <!-- 330��331 �ύX�ς� -->
        <xml id="xmlSA890I" src="../../model/SA890I.xml"></xml>
        <xml id="xmlFGEN3630I" src="../../model/FGEN3630I.xml"></xml>
        <xml id="xmlFGEN3580I" src="../../model/FGEN3580I.xml"></xml>
        <xml id="xmlFGEN3650I" src="../../model/FGEN3650I.xml"></xml>
        <xml id="xmlFGEN3670I" src="../../model/FGEN3670I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA041O"></xml>  <!-- 40��41 �ύX�ς� -->
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA101O"></xml>  <!-- 100��101 �ύX�ς� -->
        <xml id="xmlSA102O"></xml>  <!-- �V�K�ǉ� -->
        <xml id="xmlSA111O"></xml>  <!-- 110��111 �ύX�ς� -->
        <xml id="xmlSA300O"></xml>
        <xml id="xmlSA320O"></xml>
        <xml id="xmlSA331O"></xml>  <!-- 330��331 �ύX�ς� -->
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3580"></xml>
        <!-- �ꗗ -->
        <xml id="xmlRGEN3580"></xml>
        <xml id="xmlFGEN3580O"></xml>

		<xml id="xmlFGEN3630"></xml>
		<xml id="xmlFGEN3630O"></xml>
		<xml id="xmlRGEN3630"></xml>


		<xml id="xmlFGEN3650O"></xml>
		<xml id="xmlRGEN3650"></xml>
		<xml id="xmlFGEN3670"></xml>
		<xml id="xmlFGEN3670O"></xml>
		<xml id="xmlRGEN3670"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
             <table width="99%">
                <tr>
                    <td width="20%" class="title">������}�X�^</td>
                    <td width="15%">&nbsp;</td>
                    <td width="65%" align="right">
                    <!-- �����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="11">
					<!-- �o�^�{�^�� -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�o�^" onClick="funUpdate();"  disabled tabindex="12">
					<!-- Excel�{�^�� -->
                        <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel" onClick="funFileExcel();" tabindex="13">
                    <!-- �s�ǉ��{�^�� -->
                        <input type="button" class="normalbutton" id="btnLineAdd" name="btnLineAdd" value="�s�ǉ�" onClick="funLineAdd();" tabindex="14">
                    <!-- �s�폜�{�^�� -->
 <!--                       <input type="button" class="normalbutton" id="btnLineDel" name="btnLineDel" value="�s�폜" onClick="funLineDel();" tabindex="15">-->
                    <!-- �I���{�^�� -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funNext();" tabindex="16">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="800">
                <!-- 1�s�� -->
                <tr>
                    <td width="300">������R�[�h
                    	<span class="ninput" format="10,3" comma="false">
                    		<input type="text" value="" name="cdhattyu" id="cdhattyu" onchange="hattyuusakiFocusOut();" style="width:200px;" tabindex="1">
                    	</span>
                    </td>
                    <td width="300">�����於<input type="text" value="" name="nmhattyu" id="nmhattyu" onchange="hattyuusakiFocusOut();" style="width:200px;" tabindex="2">
                    </td>
                </tr>
              </table>
            <br/>

            <hr>


            <!-- [�f�U�C���X�y�[�X���ꗗ]���X�g width:870 �� 1170px -->
            <div class="scroll" id="sclList" style="height:60%;width:1250px;overflow:scroll;" rowSelect="true">
	            <table id="dataTable" name="dataTable" cellspacing="0" width="1230px" align="center">
	                <colgroup>
	                <!--<col style="width:30px;"/> -->
	                    <col style="width:100px;"/>
	                    <col style="width:220px;"/>
	                    <col style="width:60px;"/>
	                    <col style="width:60px;">
	                    <col style="width:380px;"/>
	                    <col style="width:65px;"/>
	                    <col style="width:270px;"/>
	                    <col style="width:60px;"/>
	                </colgroup>
	                <thead class="rowtitle">
	                    <tr style="top:expression(offsetParent.scrollTop);position:relative; z-index:9;">
	                    <!--<th class="columntitle">��</th> -->
	                        <th class="columntitle">������R�[�h</th>
	                        <th class="columntitle">�����於</th>
	                        <th class="columntitle">������<BR>�\����</th>
	                        <th class="columntitle">�S���҃R�[�h</th>
	                        <th class="columntitle">�S����</th>
	                        <th class="columntitle">�S����<BR>�\����</th>
	                        <th class="columntitle">���[���A�h���X</th>
	                        <th class="columntitle">���g�p</th>
	                    </tr>
	                </thead>
	                <table class="detail" id="tblList" cellpadding="0" cellspacing="0"
					border="1" style="width:1230px;display:none">
	                    <tbody id="detail"></tbody>
	                </table>
	            </table>
            </div>
             <!-- �������ʍs�� -->
            <input type="hidden" id="hidListRow" name="hidListRow">
            <!-- Excel�f�[�^�L�[�����p�W���C���g�L�[ -->
            <input type="hidden" id="hdnJoinCdLiteral" name="hdnJoinCdLiteral" />
            <input type="hidden" id="hdnJoinCd2ndLiteral" name="hdnJoinCd2ndLiteral" />
            <!-- Excel�t�@�C���p�X -->
            <input type="hidden" id="strFilePath" name="strFilePath" />
            <br/><br/>


	</form>
    </body>
</html>
