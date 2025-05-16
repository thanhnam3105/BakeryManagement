<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@����f�[�^�ꗗ���                                                -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/03/25                                                              -->
<!-- �T�v  �F���������Ɉ�v���鎎��f�[�^���ꗗ�ŕ\������B                          -->
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
        <script type="text/javascript" src="include/SQ040ShisakuList.js"></script>
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
        <xml id="xmlJSP0310"></xml>
        <xml id="xmlJSP0320"></xml>
        <xml id="xmlJSP0330"></xml>
        <xml id="xmlJSP0340"></xml>
        <xml id="xmlJSP9020"></xml>
        <xml id="xmlRGEN1090"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA030I" src="../../model/SA030I.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA070I" src="../../model/SA070I.xml"></xml>
        <xml id="xmlSA080I" src="../../model/SA080I.xml"></xml>
        <xml id="xmlSA120I" src="../../model/SA120I.xml"></xml>
        <xml id="xmlSA200I" src="../../model/SA200I.xml"></xml>
<!--        <xml id="xmlSA230I" src="../../model/SA230I.xml"></xml>-->
        <xml id="xmlSA250I" src="../../model/SA250I.xml"></xml>
<!--        <xml id="xmlSA280I" src="../../model/SA280I.xml"></xml>-->
		<!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start -->
		<xml id="xmlSA530I" src="../../model/SA530I.xml"></xml>
		<!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End   -->
		
        <xml id="xmlSA550I" src="../../model/SA550I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA030O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA070O"></xml>
        <xml id="xmlSA080O"></xml>
        <xml id="xmlSA120O"></xml>
        <xml id="xmlSA200O"></xml>
<!--        <xml id="xmlSA230O"></xml>-->
        <xml id="xmlSA250O"></xml>
<!--        <xml id="xmlSA280O"></xml>-->
		<!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start -->
        <xml id="xmlSA530O"></xml>
		<!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End   -->
        
        <xml id="xmlSA550O"></xml>
        <xml id="xmlRESULT"></xml>
        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlSA050"></xml>
        <xml id="xmlSA080"></xml>
        <xml id="xmlSA250"></xml>
        <!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD Start -->
        <xml id="xmlSA530"></xml>
        <!-- �yQP@20505�zNo.39 2012/09/20 TT H.Shima ADD End   -->

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">����f�[�^�ꗗ</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="20">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" onClick="funClear();" tabindex="21">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funNext(0);" tabindex="22">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- ���́E�I�� -->
            <table width="870">
                <!-- 1��� -->
                <tr>
                    <td width="90">���@�������Ώ�</td>
                    <td width="50">
                        <input type="checkbox" id="chkTaisho" name="chkTaisho" onClick="funUseSeihoNo(this)" tabindex="1">
                    </td>
                    <td width="90">�����ōi����</td>
                    <td width="50">
                        <input type="checkbox" id="chkTaisho" name="chkTaisho" tabindex="2" checked>
                    </td>
                    <td width="90">�p�~�������Ώ�</td>
                    <td width="50">
                        <input type="checkbox" id="chkTaisho" name="chkTaisho" tabindex="3">
                    </td>
                    <td width="90">�����˗��̂�</td>
                    <td width="50">
                        <input type="checkbox" id="chkTaisho" name="chkTaisho" tabindex="4">
                    </td>
                    <td width="200">&nbsp;</td>
                </tr>
            </table>

            <table width="960" datasrc="#xmlSA200I" datafld="rec">
                <!-- 2��� -->
                <tr>
                    <td width="100">����No</td>
                    <td width="200">
                        <span class="cinput" format="0000000000-00-000" required="true" defaultfocus="false" id="em_ShisakuNo">
                        <input type="text" class="disb_text" id="txtShisakuNo" name="txtShisakuNo" datafld="no_shisaku" maxlength="17" value="" style="width:170px;" tabindex="5">
                        </span>
                    </td>
                    <td width="100">�����O���[�v</td>
                    <td width="200">
                        <select id="ddlGroup" name="ddlGroup" datafld="cd_group" style="width:180px;" onChange="funChangeGroup();" tabindex="8">
                        </select>
                    </td>
                    <td width="100">�W������</td>
                    <td width="200">
                        <select id="ddlGenre" name="ddlGenre" datafld="cd_genre" style="width:180px;" tabindex="12">
                        </select>
                    </td>
                    <td width="38">&nbsp;</td>
                </tr>
            </table>

            <table width="960" datasrc="#xmlSA200I" datafld="rec">
                <!-- 3��� -->
                <tr>
                    <td width="100">���@No</td>
                    <td width="200">
                        <span class="cinput" format="0000-X00-00-0000" required="true" defaultfocus="false" id="em_SeihouNo">
                        <input type="text" class="disb_text" id="txtSeihouNo" name="txtSeihouNo" datafld="no_seiho" maxlength="16" value="" style="width:170px;" tabindex="6">
                        </span>
                    </td>
                    <td width="100">�����`�[��</td>
                    <td width="200">
                        <select id="ddlTeam" name="ddlTeam" datafld="cd_team" style="width:180px;" onChange="funChangeTeam();" tabindex="9">
                        </select>
                    </td>
                    <td width="100">�ꊇ�\������</td>
                    <td width="200">
                        <select id="ddlHyoujiName" name="ddlHyoujiName" datafld="cd_ikatu" style="width:180px;" tabindex="13">
                        </select>
                    </td>
                    <td width="38">&nbsp;</td>
                </tr>
            </table>

            <table width="960" datasrc="#xmlSA200I" datafld="rec">
                <!-- 4��� -->
                <tr>
                    <td width="100">���얼</td>
                    <td width="200">
                        <input type="text" class="act_text" id="txtShisakuName" name="txtShisakuName" datafld="nm_shisaku" maxlength="100" value="" style="width:170px;" tabindex="7">
                    </td>
                    <td width="100">�S����</td>
                    <td width="200">
                        <select id="ddlTanto" name="ddlTanto" datafld="cd_tanto" style="width:180px;" tabindex="10">
                        </select>
                    </td>
                    <td width="100">���i�̗p�r</td>
                    <td width="200">
                        <input type="text" class="act_text" id="txtYouto" name="txtYouto" datafld="cd_youto" maxlength="60" value="" style="width:180px;" tabindex="14">
<!--                        <select id="ddlYouto" name="ddlYouto" datafld="cd_youto" style="width:180px;" tabindex="13">
                        </select>-->
                    </td>
                    <td width="38">&nbsp;</td>
                </tr>
            </table>

            <table width="960" datasrc="#xmlSA200I" datafld="rec">
                <!-- 5��� -->
                <tr>
                    <td width="100">&nbsp;</td>
                    <td width="205">&nbsp;</td>
                    <td width="100">���[�U�[</td>
                    <td width="200">
                        <select id="ddlUser" name="ddlUser" datafld="cd_user" style="width:180px;" tabindex="11">
                        </select>
                    </td>
                    <td width="100">��������</td>
                    <td width="200">
                        <input type="text" class="act_text" id="txtGenryo" name="txtGenryo" datafld="cd_genryo" maxlength="60" value="" style="width:180px;" tabindex="15">
<!--                        <select id="ddlGenryo" name="ddlGenryo" datafld="cd_genryo" style="width:180px;" tabindex="14">
                        </select>-->
                    </td>
                    <td width="38">&nbsp;</td>
                </tr>
            </table>

            <table width="960" datasrc="#xmlSA200I" datafld="rec">
                <!-- 6��� -->
                <tr>
                    <td width="97">�L�[���[�h����</td>
                    <td width="200">
                        <input type="text" class="act_text" id="txtKeyword" name="txtKeyword" datafld="keyword" style="width:170px;" value="" tabindex="16">
                    </td>
                    <td width="403">���J���}��؂�ɂē��͂��ĉ�����</td>
                    <td width="180" align="right">
                        <input type="button" class="normalbutton" value="���������N���A" style="width:150px;" onClick="funJokenClear();" tabindex="17">
                    </td>
                    <td width="58">&nbsp;</td>
                </tr>
            </table>

            <!-- [�����ް��ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:45%;width:99%;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1765px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:130px;"/>
                    <col style="width:125px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:60px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">����No</th>
                        <th class="columntitle">���@No</th>
                        <th class="columntitle">���얼</th>
                        <th class="columntitle">�S����</th>
                        <th class="columntitle">���[�U�[</th>
                        <th class="columntitle">�W������</th>
                        <th class="columntitle">�ꊇ�\������</th>
                        <th class="columntitle">���i�̗p�r</th>
                        <th class="columntitle">��������</th>
                        <th class="columntitle">�p�~</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA200O" datafld="rec" style="width:1765px;display:none">
                        <tr class="disprow">
                        	<!--2010/02/22 NAKAMURA UPDATE START-->
                        	<!--<td class="column" width="32" align="right"><span datafld="no_row"></span><span datafld="dara"></span></td>-->
                        	<td class="column" width="32" align="right"><span datafld="dara"></span><span datafld="no_row"></span></td>
                        	<!--2010/02/22 NAKAMURA UPDATE END---->
                            <td class="column" width="130" align="left"><span datafld="no_shisaku"></span></td>
                            <td class="column" width="125" align="left"><span datafld="no_seiho"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_hin"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_tanto"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_user"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_genre"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_ikatu"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_youto"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_genryo"></span></td>
                            <td class="column" width="60" align="left"><span datafld="nm_haishi"></span></td>
                        </tr>
                    </table>
                </table>
            </table>
            </div>

            <table width="99%">
                <!-- �f�[�^�� -->
                <tr align="center">
                    <td height="18px">
                        <span id="spnRecInfo">�f�[�^���@�F�@<span id="spnRecCnt"></span> ���ł�(<span id="spnRowMax"></span>�����ɕ\�����Ă��܂�)�@<span id="spnCurPage"></span></span>
                    </td>
                </tr>
            </table>

            <!-- �y�[�W�����N -->
            <div id="divPage" style="height:50px;font-size:12pt;"></div>

            <table width="870">
                <tr>
                    <td>
                        <input type="button" class="normalbutton" id="btnEdit" name="btnEdit" value="�ڍ�" onClick="funJavaWebStart(1);" tabindex="18">
                        <input type="button" class="normalbutton" id="btnNew" name="btnNew" value="�V�K" onClick="funJavaWebStart(2);" tabindex="19">
                        <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="���@�x���R�s�[" onClick="funJavaWebStart(3);" tabindex="20">
                        <input type="button" style="visibility:hidden" class="normalbutton" id="btnGenka" name="btnGenka" value="�������Z" onClick="funGenkaShisan()" tabindex="-1">
                    </td>
                </tr>
            </table>

            <input type="hidden" id="hidJwsMode" name="hidJwsMode" value="">
        </form>

        <input type="hidden" id="hidShisakuListKengen" name="hidShisakuListKengen" value="">
    </body>
</html>
