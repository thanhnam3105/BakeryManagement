<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���ގ�z���͉��                                                  -->
<!-- �쐬�ҁFTT.Kitazawa                                                             -->
<!-- �쐬���F2014/09/01                                                              -->
<!-- �T�v  �F���������Ɉ�v���鎑�ގ�z�ꗗ��\������B                              -->
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
        function Scroll1() {
            //Y�����̃X�N���[���ړ�
            document.getElementById("sclList1").scrollTop = document.getElementById("sclList2").scrollTop;
        }
    -->
    </script>

    <script type="text/javascript">
    <!--
        window.onunload = function() {
        document.getElementById("divGenryo_Left").innerHTML = null;
        document.getElementById("divGenryo_Right").innerHTML = null;

        document.getElementById("xmlRGEN0012").src = null;
        document.getElementById("xmlFGEN0012I").src = null;
        document.getElementById("xmlFGEN0012O").src = null;


    	document.getElementById("xmlUSERINFO_I").src = null;
        document.getElementById("xmlUSERINFO_O").src = null;
        document.getElementById("xmlRESULT").src = null;

        document.getElementById("xmlSA290I").src = null;
        document.getElementById("xmlSA290O").src = null;
        document.getElementById("xmlFGEN3450").src = null;
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
        <script type="text/javascript" src="include/SQ230ShizaiTehaiInput.js"></script>
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

    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3420"></xml>
        <xml id="xmlRGEN3440"></xml>
        <xml id="xmlRGEN3450"></xml>
        <xml id="xmlRGEN3460"></xml>
        <xml id="xmlRGEN3470"></xml>
        <xml id="xmlRGEN3480"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlSA290O"></xml>
        <xml id="xmlFGEN2130O"></xml>

        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>

        <xml id="xmlFGEN3420I" src="../../model/FGEN3420I.xml"></xml>
        <xml id="xmlFGEN3430I" src="../../model/FGEN3430I.xml"></xml>
        <xml id="xmlFGEN3440I" src="../../model/FGEN3440I.xml"></xml>
        <xml id="xmlFGEN3450I" src="../../model/FGEN3450I.xml"></xml>
        <xml id="xmlFGEN3460I" src="../../model/FGEN3460I.xml"></xml>
        <xml id="xmlFGEN3470I" src="../../model/FGEN3470I.xml"></xml>
        <xml id="xmlFGEN3480I" src="../../model/FGEN3480I.xml"></xml>

        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3420O"></xml>
        <xml id="xmlFGEN3430O"></xml>
        <xml id="xmlFGEN3440O"></xml>
        <xml id="xmlFGEN3450O"></xml>
        <xml id="xmlFGEN3460O"></xml>
        <xml id="xmlFGEN3470O"></xml>
        <xml id="xmlFGEN3480O"></xml>

        <!-- �������擾�pXML -->
        <xml id="xmlRGEN0012"></xml>
        <xml id="xmlFGEN0012I" src="../../model/FGEN0012I.xml"></xml>
        <xml id="xmlFGEN0012O"></xml>

        <!-- ���ޏ��̎w��s�f�[�^�ޔ�pXML -->
        <xml id="xmlFGEN3230"></xml>
        <xml id="xmlFGEN3450"></xml>
        <xml id="xmlRGEN3455"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">���ގ�z����</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- �����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="9">
                    <!-- �o�^�{�^�� -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�o�^" onClick="funToroku();" tabindex="10">
                    <!--  �yKPX@1602367�z TOS TAMURA add 2017/3/3 start-->
                        <input type="button" class="normalbutton" id="btnSeihinSearch" name="btnSeihinSearch" value="�R�[�h����" onClick="funSeihinSearchBox();">
                    <!--  �yKPX@1602367�z TOS TAMURA add 2017/3/3 end-->
                    <!-- �폜�{�^�� -->
                        <input type="button" class="normalbutton" id="btnDesignSpace" name="btnDesignSpace" value="�޻޲ݽ�߰�" onClick="funDesignSpace();" tabindex="11">
                    <!-- �Q�l�����{�^�� -->
                        <input type="button" class="normalbutton" id="btnSiryo" name="btnSiryo" value="�Q�l����" onClick="funSiryo();" tabindex="12">
                    <!-- �I���{�^�� -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funNext(0);" tabindex="13">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br/>
            <div id="divUserInfo"></div>
            <br/>
            <table width="1050">
                <!-- 1��� -->
                <tr>
                    <td width="100">���i�R�[�h</td>
                    <td width="150">
                      <span class="ninput" format="11,0" comma="false" name="inputCd">
                        <input type="text" value="" name="inputSeihinCd" id="inputSeihinCd" size="15"  maxlength="11" style=\"ime-mode:disabled;\" onkeydown="if(event.keyCode == 13 || event.keyCode == 9){funSeihinSearch(event.keyCode);}" tabindex="1"></span>
					<!--  �yKPX@1602367�z TOS TAMURA DELETE 2017/03/03 start-->
					<!--  �yKPX@1602367�z BRC MAY THU add start-->
                    <!--   <input type="button"  id="btnSeihinSearch" name="btnSeihinSearch" value="neko" onClick="funSeihinSearchBox();">--> 
					<!--  �yKPX@1602367�z BRC MAY THU add end-->
					<!--  �yKPX@1602367�z TOS TAMURA DELETE 2017/03/03 end-->
                    </td>
                    <td width="50"></td>
                    <td width="100">���i��</td>
                    <td width="300">
                        <input type="text" value="" name="seihinNm" id="seihinNm" size="50" readonly tabindex="-1">
                    </td>
                    <td width="100">�׎p</td>
                    <td width="300">
                        <input type="text" value="" name="nisugata" id="nisugata" size="45" readonly tabindex="-1">
                    </td>
                </tr>
                <!-- 2�s�� -->
                <tr>
                    <td>����No.</td>
                    <td colspan="2">
                        <select id="ddlShisakuNo" name="ddlShisakuNo" style="width:230px;" onChange="funChangeShisakuNo();" tabindex="2" onFocus="funFocusShisakuNo();">
                        </select>
                    </td>
                </tr>
            </table>
            <br/>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>�y�����H����z</td>
                 </tr>
            </table>
            <hr>

            <table width="1050">
                <!-- 1��� -->
                <tr>
                    <td width="100">�����H��</td>
                    <td width="150">
                        <input type="hidden" value="" name="seizoKojoCd" id="seizoKojoCd" onChange="funChangeKojo();">
                        <input type="text" value="" name="seizoKojoNm" id="seizoKojoNm" style="width:100px;" readonly tabindex="-1">
                    </td>
                    <td width="100">�E��</td>
                    <td width="300">
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="3">
                        </select>
                    </td>
                    <td width="100">�������C��</td>
                    <td width="300">
                        <select id="ddlLine" name="ddlLine" style="width:230px;" tabindex="4">
                        </select>
                    </td>
                </tr>
            </table>
            <br/>

            <!-- div class="scroll"  style="height:70%; width:100%" -->
            <div class="scroll"  style="height:65%; width:100%">
            <br/>
            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>�y���ޏ��z</td>
                 </tr>
            </table>
            <hr>

            <!-- [���ޏ��ꗗ]���X�g 80 - 70  80  60 100 70-->
            <div class="scroll" id="sclList" style="height:55%; width:1540px; overflow:scroll; margin-top:0px;"  rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1700px" align="center"><!-- 2466px May Thu �yKPX@1602367�z 2016/09/05 width update-->
                <colgroup>
                    <col style="width:48px;"/>
                    <col style="width:169px;"/>
                    <col style="width:55px;"/>
                    <col style="width:45px;"/>
                    <col style="width:60px;"/>
                    <col style="width:236px;"/>
                    <col style="width:244px;"/>
                    <col style="width:149px;"/>
                    <col style="width:45px;"/>
					<!-- May Thu �yKPX@1602367�z 2016/09/05 535 add-->
                    <col style="width:220px;"/>
                    <col style="width:58px;"/>
                    <col style="width:45px;"/>
                    <col style="width:90px;"/>
                    <col style="width:75px;"/>
					<!-- May Thu �yKPX@1602367�z 2016/09/05 end-->
                    <col style="width:90px;"/>
                    <col style="width:75px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">����<BR>�R�[�h</th>
                        <th class="columntitle">���ޖ�</th>
                        <th class="columntitle">�P����</th>
                        <th class="columntitle">�V�K</th>
                        <th class="columntitle">�V����<BR>�R�[�h</th>
                        <th class="columntitle">�V���ޖ�</th>
                        <th class="columntitle">������</th>
                        <th class="columntitle">�Ώێ���</th>
                        <th class="columntitle">����</th>
						<!-- May Thu 2016/09/05 add-->
                        <th class="columntitle">�ŉ��t�@�C��</th>
                        <th class="columntitle">�Q��</th>
						<th class="columntitle">����</th>
						<th class="columntitle">������</th>
						<th class="columntitle">������</th>
						<!-- May Thu 2016/09/05 end-->
                        <th class="columntitle">�X�V��</th>
                        <th class="columntitle">�X�V��</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" bordercolor="#C0C0C0" style="width:1700px;display:none"><!-- May Thu �yKPX@1602367�z 2016/09/05 width update-->
                    <!-- [���ޏ��ꗗ] ���׍s -->
                    <tbody id="detail"></tbody>
                </table>
            </table>
            </div>
            <br/><br/>

            <!--���쌴�����-->
            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                         �y�������z
                    </td>
                </tr>
            </table>
            <hr>

            <!--���쌴�����ꗗ  height="300px" -->
            <table cellpadding="0" cellspacing="0" border="0" width="1180px" height="170px" style="table-layout:fixed;">
                <tr>
                <!-- ���� -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td valign="top" style="width:605px;">
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--�����w�b�_�[��-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:75px;" >�I<br/>��</th>
                                            <th class="columntitle" style="width:20px;height:75px;" >�H<br/>��</th>
                                            <th class="columntitle" style="width:105px;" >����CD</th>
                                            <th class="columntitle" style="width:310px;" >������</th>
                                            <th class="columntitle" style="width:20px;" >��<br/>�X</th>
                                            <th class="columntitle" style="width:70px;" >�P��<br/>�i�~/�s�j<br/>��</th>
                                            <th class="columntitle" style="width:45px;" >����<br/>�i���j<br/>��</th>
                                        </tr>
                                        </table>
                                    </div>
                                </td></tr>
                                <tr>
                                    <!-- td valign="top" height="422" -->
                                    <td valign="top" height="292">
                                        <!--�����J������-->
                                        <div id="sclList1" style="height:286px;overflow:hidden;position:relative;">
                                            <div id="divGenryo_Left"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <!-- �E�� -->
                        <td align="left" valign="top">
                            <div class="scroll_genka" id="sclList2" style="width:544px;height:381px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
                                <!-- �E���e�[�u�� -->
                                <div id="divGenryo_Right">
                                </div>
                        </td>
                    </tr>

                    </table>
                </td></tr>
            </table>
            <br/>
            <!-- ���׍s�A�X�N���[���I��� -->
            </div>
            <!-- �������[�h -->
            <input type="hidden" id="mode" name="mode">
            <!-- �Q�l�����I���J�e�S�� -->
            <input type="hidden" id="sq220Category" name="sq220Category" >
			<!-- May Thu �yKPX@1602367�z add start 2016/09/21-->
            <input type="hidden" id="flg_hatyuu_status" name="flg_hatyuu_status" value="1" >
			<input type="hidden" id="cd_shain" name="cd_shain" value="1" >
			<input type="hidden" id="nen" name="nen" value="1" >
			<input type="hidden" id="no_oi" name="no_oi" value="1" >
			<input type="hidden" id="no_eda" name="no_eda" value="1" >
            <!-- �t�@�C���ۑ���T�[�o�[�p�X�iconst��`���j -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- �T�u�t�H���_�[���F�ۑ��t�@�C�����Ɏw�� �i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- �A�b�v���[�h�Ώۂ̃t�B�[���h���i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strFieldNm" id="strFieldNm">
            <!-- �_�E�����[�h�A�폜�t�@�C�����i":::"�ŋ�؂�j -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
			<!-- May Thu �yKPX@1602367�z add end 2016/09/21-->
        </form>
    </body>
</html>
