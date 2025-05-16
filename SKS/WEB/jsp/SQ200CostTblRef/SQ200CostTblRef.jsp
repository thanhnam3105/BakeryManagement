<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�R�X�g�e�[�u���Q�Ɖ��                                            -->
<!-- �쐬�ҁFTT.Sakamoto                                                             -->
<!-- �쐬���F2014/02/25                                                              -->
<!-- �T�v  �F���������Ɉ�v����R�X�g�f�[�^���ꗗ�ŕ\������B                        -->
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
        <script type="text/javascript" src="include/SQ200CostTblRef.js"></script>
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
        <xml id="xmlRGEN3000"></xml>
        <!-- // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add start -->
        <xml id="xmlRGEN3010"></xml>
        <!-- // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- add end -->
        <xml id="xmlRGEN3030"></xml>
        <xml id="xmlRGEN3040"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3000I" src="../../model/FGEN3000I.xml"></xml>
        <xml id="xmlFGEN3010I" src="../../model/FGEN3010I.xml"></xml>
        <xml id="xmlFGEN3040I" src="../../model/FGEN3040I.xml"></xml>
        <xml id="xmlFGEN3000O"></xml>
        <xml id="xmlFGEN3010O"></xml>
        <xml id="xmlFGEN3040O"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
        <xml id="xmlRGEN3090"></xml>
        <xml id="xmlFGEN3090"></xml>
        <xml id="xmlFGEN3090I" src="../../model/FGEN3090I.xml"></xml>
        <xml id="xmlFGEN3090O"></xml>
        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->

        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlFGEN3000"></xml>
        <xml id="xmlFGEN3010"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�R�X�g�e�[�u���Q��</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" onClick="funSearch();" tabindex="6">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funEndClick();" tabindex="7">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="1200" datasrc="#xmlFGEN3030I" datafld="rec">
                <!-- 1��� -->
                <tr>
                    <td width="100">���[�J�[��</td>
                    <td width="200">
                    <!-- // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start -->
                        <!-- select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" tabindex="1" onchange="funHanSearch();" -->
                        <select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" tabindex="1" onchange="funChangeMaker();">
                        </select>
                    <!-- // �yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end -->
                    </td>
                    <td width="100">���</td>
                    <td width="200">
                        <select id="ddlHouzai" name="ddlHouzai" datafld="cd_houzai" style="width:180px;" tabindex="2" onchange="funHanSearch();">
                        </select>
                    </td>
                    <td width="100">�Ő�</td>
                    <td width="200">
                        <span class="cinput" format="0000000000" required="true" defaultfocus="false" id="em_SeihouNo">
                            <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="text-align:right;width:180px;" tabindex="3">
                        </span>
                    </td>
                    <td width="100">���g�p��</td>
                    <td width="100">
                    <!-- // �yQP@30297�zNo.17 2014/08/20 E.kitazawa �ۑ�Ή� --------------------- mod start -->
                        <!-- span class="ninput" format="10" comma="false" -->
                        <span class="ninput" format="10,3" comma="false">

                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                        <!-- input type="text" class="disb_text" id="txtShiyoRyo" name="txtShiyoRyo" maxlength="21" value="" style="text-align:right;width:100px;" tabindex="4" onblur="this.value = funAddComma(this.value); return true;" -->
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                          <input type="text" class="disb_text" id="txtShiyoRyo" name="txtShiyoRyo" maxlength="21" value="" style="text-align:right;" tabindex="4" onblur="this.value = funAddComma(this.value); return true;">
                        </span>
                    <!-- // �yQP@30297�zNo.17 2014/08/20 E.kitazawa �ۑ�Ή� --------------------- mod end -->
                    </td>
                    <td width="100">
                        <input type="button" class="normalbutton" id="btnCalc" name="btnCalc" value="�v�Z" onClick="funCalc();" tabindex="5">
                    </td>
                </tr>

                <!-- //add start�yQP@30297�z�ۑ� hisahori -->
                <table width="99%">
                    <tr>
                        <td align="right">�P�ʁi�~�j�@�@</td>
                    </tr>
                </table>
                <!-- //add end�yQP@30297�z�ۑ� hisahori -->

                <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                <tr>
                	<td colspan=9><br><hr></td>
                </tr>
                <tr>
	                <td width="100">�L���J�n���F</td>
	                <td width="100"><span id="txtYuko"></span>
	                </td>
                </tr>
                <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
            </table>

            <!-- [�R�X�g�e�[�u���ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:65%;width:99%;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1880px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                    <col style="width:50px;"/>
                </colgroup>
                <thead class="rowtitle" id="tblHeader">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">&nbsp;&nbsp;</th>
                        <th class="columntitle" style="text-align:right;">���b�g��</th>
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader01" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader02" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader03" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader04" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader05" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader06" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader07" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader08" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader09" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader10" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader11" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader12" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader13" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader14" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader15" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader16" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader17" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader18" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader19" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader20" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader21" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader22" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader23" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader24" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader25" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader26" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader27" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader28" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader29" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="tblHeader30" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                    </tr>
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">��</th>
                        <!-- // �yQP@30297�z�ۑ�Ή� mod start -->
                        <th class="columntitle" style="text-align:left;">�F���^�g�p��</th>
                        <!-- // �yQP@30297�z�ۑ�Ή� mod end -->
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt01" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt02" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt03" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt04" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt05" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt06" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt07" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt08" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt09" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt10" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt11" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt12" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt13" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt14" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt15" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt16" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt17" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt18" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt19" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt20" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt21" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt22" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt23" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt24" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt25" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt26" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt27" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt28" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt29" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <th class="columntitle" style="text-align:right;"><input type="text" id="calcRslt30" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></th>
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                    </tr>
                </thead>
                <tbody>
                    <!-- // �yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- del start -->
                    <!-- table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN3040O" datafld="rec" style="width:1880px;display:none">
                        <tr class="disprow" -->
                        	<!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                            <!--
                            <td class="column" style="width:30px;text-align:right;"><span datafld="no_row"></span></td>
                            <td class="column" style="width:98px;text-align:left;"><span datafld="nm_title"></span></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value01" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value02" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value03" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value04" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value05" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value06" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value07" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value08" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value09" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value10" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value11" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value12" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value13" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value14" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value15" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value16" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value17" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value18" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value19" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value20" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value21" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value22" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value23" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value24" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value25" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value26" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value27" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value28" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value29" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                            <td class="column" style="width:50px;text-align:right;"><input type="text" datafld="no_value30" style="width:50px;border-width:0px;text-align:right;" maxlength="13" readonly></td>
                             -->
                            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                        <!-- /tr>
                    </table -->
                    <!-- // �yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- del end -->

                    <!-- // �yQP@30297�zNo.23 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add start -->
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:1880px;display:none">
                        <tr class="disprow">
                        <% for(int row = 0; row < 30; row++) { %>
                        <tr class="disprow">
                            <td class="column" style="width:30px;text-align:right;"><%=(row + 1)%></td>
                            <td class="column" style="width:100px;text-align:left;">
                                <input type="text" class="act_text" id="nm_title_<%=(row + 1)%>" style="width:100px;border-width:0px;text-align:left;">
                            </td>
                            <% for(int col = 0; col < 30; col++) { %>
                            <td class="column" style="width:50px;text-align:right;">
                                <!-- �����_�ȉ�3�ʐ؂�̂� -->
                                <input type="text" id="no_value_<%=(row + 1)%>_<%=(col + 1)%>" class="disb_text" style="width:50px;border-width:0px;text-align:right;" maxlength="13"  readonly>
                            </td>
                            <% } %>
                        </tr>
                        <% } %>
                    </table>
                    <!-- //�yQP@30297�zNo.23  2014/08/20  E.kitazawa �ۑ�Ή� --------------------- add end -->
                </table>
            </table>
            </div>

            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
            <table style="margin-top:10px;">
                <!--
                <tr>
                    <td width="80">���l�i�����j�F</td>
                    <td><span id="txtBiko"></span>
                    </td>
                </tr>
                -->
                <tr>
                    <td width="80">���l�i�H��j�F</td>
                    <td><span id="txtBiko_kojo"></span>
                    </td>
                </tr>
            </table>
            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->

        </form>
    </body>
</html>
