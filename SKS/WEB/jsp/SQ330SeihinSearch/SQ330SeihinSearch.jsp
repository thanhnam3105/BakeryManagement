<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!--�yKPX@1602367�z                   -->
<!-- �V�T�N�C�b�N�@���i������� �@�@�@�@�@�@�@�@�@�@ -->
<!-- �쐬�ҁFMay Thu                  -->
<!-- �쐬���F2016/09/13               -->
<!-- �T�v  �F���i�R�[�h�Ɛ��i���̂����܂�����  �@-->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--//
    //===================================================================================
    // �X�N���[���ړ�����
    // ����  �F�Ȃ�
    // �߂�l�F�Ȃ�
    // �T�v  �F�w�b�_�[�̕\�����@��DIV�̑��Έʒu�ŕς���
    //===================================================================================
    -->
    </script>

    <script type="text/javascript">
     <!--
    window.onunload = function() {

     <!--   document.getElementById("xmlRGEN0012").src = null;
     <!--   document.getElementById("xmlFGEN0012I").src = null;
     <!--   document.getElementById("xmlFGEN0012O").src = null;


	document.getElementById("xmlUSERINFO_I").src = null;
    document.getElementById("xmlUSERINFO_O").src = null;
    document.getElementById("xmlRESULT").src = null;

    <!--    document.getElementById("xmlSA290I").src = null;
    <!--    document.getElementById("xmlSA290O").src = null;
    }

    // Input[readonly] ��BackSpace�������Ȃ�����  �iIE�ŗL�̖��j
    window.document.onkeydown = function keydown(){
        if( window.event.keyCode == 8 ){
            if(document.activeElement.readOnly){
        	  return false;
            }
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
        <script type="text/javascript" src="include/SQ330SeihinSearch.js"></script>
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
   		 <script for="tblList" event="onreadystatechange" language="JavaScript">
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
        <!--  �e�[�u�����׍s�N���b�N -->
        <script for="tblList" event="ondblclick" language="JavaScript">
            //�I���s�̔w�i�F��ύX
            funChoiceSeihin();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
		<xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
		<xml id="xmlFGEN3590"></xml>
		<xml id="xmlRGEN3590"></xml>
		<xml id="xmlFGEN3590I" src="../../model/FGEN3590I.xml"></xml>
		<xml id="xmlFGEN3590O"></xml>

       <form name="frm00" id="frm00" method="post">
            <table width="480px" border="1">
                <!-- 1��� -->
                <tr>
                    <td width="100">����</td>
					<td width="165"><input type="text" value="" name="inputNmHimei" id="inputNmHimei" size="50" tabindex="1">
                    </td>
                    <td width="100">
                    <!-- �����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSeihinNameSearch();" tabindex="2"></td>
                </tr>
            </table>
            <br/>

           <hr>

           <br/>


            <!-- [���ޏ��ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:55%; width:100%; overflow-y:scroll;overflow-x:visible; margin-top:0px;"  rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="100%" align="center">
                <colgroup>
					<col style="width:85px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
						<th class="columntitle">���i�R�[�h</th>
						<th class="columntitle">�i��</th>
						<th class="columntitle">�׎p</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:100%;">
                    <!-- [���ޏ��ꗗ] ���׍s -->
                    <tbody id="detail"></tbody>
                </table>
            </table>
            </div>
			<table>
			<tr><td><div id="divCountInfo"></div></td></tr>
			</table>
			<br/><br/>
			<table width="480px" border="1">
			<tr align="right" ><td><input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="�I��" onClick="funChoiceSeihin();" tabindex="3">
			        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="�I��" onClick="funEndClick();" tabindex="4">
			</td></tr>
			</table>
            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode">
        </form>
    </body>
</html>
