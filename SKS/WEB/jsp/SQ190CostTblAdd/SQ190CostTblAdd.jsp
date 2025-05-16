<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�R�X�g�e�[�u���o�^�E���F���                                      -->
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
        <script type="text/javascript" src="include/SQ190CostTblAdd.js"></script>
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

            // �yQP@40404�zNo.117 E.kitazawa �ۑ�Ή� --------------------- add start
            // Input[readonly] ��BackSpace�������Ȃ�����  �iIE�ŗL�̖��j
            window.document.onkeydown = function keydown(){
                if( window.event.keyCode == 8 ){
                    if(document.activeElement.readOnly){
                      return false;
                    }
                }
            }
            // �yQP@40404�zNo.117 E.kitazawa �ۑ�Ή� --------------------- add end

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
        <xml id="xmlRGEN3010"></xml>
        <xml id="xmlRGEN3030"></xml>
        <xml id="xmlRGEN3050"></xml>
        <xml id="xmlRGEN3060"></xml>
        <xml id="xmlRGEN3080"></xml>
        <xml id="xmlRGEN3600"></xml>
        <xml id="xmlRGEN3610"></xml>
        <xml id="xmlRGEN3660"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3000I" src="../../model/FGEN3000I.xml"></xml>
        <xml id="xmlFGEN3010I" src="../../model/FGEN3010I.xml"></xml>
        <xml id="xmlFGEN3030I" src="../../model/FGEN3030I.xml"></xml>
        <xml id="xmlFGEN3050I" src="../../model/FGEN3050I.xml"></xml>
        <xml id="xmlFGEN3060I" src="../../model/FGEN3060I.xml"></xml>
        <xml id="xmlFGEN3080I" src="../../model/FGEN3080I.xml"></xml>
        <xml id="xmlFGEN3600I" src="../../model/FGEN3600I.xml"></xml>
        <xml id="xmlFGEN3610I" src="../../model/FGEN3610I.xml"></xml>
        <xml id="xmlFGEN3660I" src="../../model/FGEN3660I.xml"></xml>

        <xml id="xmlRGEN2160"></xml>
        <xml id="xmlFGEN3000O"></xml>
        <xml id="xmlFGEN3010O"></xml>
        <xml id="xmlFGEN3030O"></xml>
        <xml id="xmlFGEN3050O"></xml>
        <xml id="xmlFGEN3060O"></xml>
        <xml id="xmlFGEN3080O"></xml>
        <xml id="xmlFGEN3600O"></xml>
        <xml id="xmlFGEN3610O"></xml>
        <xml id="xmlFGEN3660O"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- // �yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add start -->
        <!-- �S�������Ή� -->
        <xml id="xmlRGEN3110"></xml>
        <xml id="xmlFGEN3110"></xml>
        <xml id="xmlFGEN3110I" src="../../model/FGEN3110I.xml"></xml>
        <xml id="xmlFGEN3110O"></xml>
        <!-- // �yQP@30297�zNo.22 2014/08/22 E.kitazawa �ۑ�Ή� --------------------- add end -->

        <!-- �R���{�{�b�N�X�Ƀf�t�H���g�Őݒ肳��Ă���l�̑ޔ�pXML -->
        <xml id="xmlFGEN3000"></xml>
        <xml id="xmlFGEN3010"></xml>
        <xml id="xmlFGEN3610"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�R�X�g�e�[�u���o�^�E���F</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnNext" name="btnNext" value="�I��" onClick="funNext();" tabindex="13">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

          <div id="costInfo" style="border:double 4px;padding:4px;border-color:#4169E1;width:85%;">
            <table datasrc="#xmlRGEN3040I" datafld="rec">
                <caption align="left">�y�����x�[�X�P�����z</caption>
                <!-- 1��� -->
                <tr>
                    <td width="87">���[�J�[��</td>
                    <td width="200">
                        <select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" tabindex="1" onChange="funChangeMaker();">
                        </select>
                    </td>
                    <td width="100">�x�[�X��ޖ�</td>
                    <td width="400">
                        <select id="ddlHouzai" name="ddlHouzai" datafld="cd_houzai" style="width:360px;" tabindex="2" onChange="funChangeBaseHouzai();">
                        </select>
                    </td>
                    <td width="150">�x�[�X�Ő��E�L���J�n��</td>
                    <td width="150">
                        <select id="ddlBaseHansu" name="ddlBaseHansu" datafld="no_basehouzai" style="width:150px;" tabindex="3" onChange="funBaseSearch();">
                        </select>
<!--                         <span class="ninput" format="9" comma="false"> -->
<!--                         <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="text-align:right;width:180px;" tabindex="3" onChange="return funExistData();"> -->
<!--                         </span> -->
                    </td>
<!--                     <td width="70">&nbsp;&nbsp;�Ő�</td> -->
<!--                     <td width="70"> -->
<!--                         <span class="ninput" format="9" comma="false"> -->
<!--                         <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="text-align:right;width:50px;" tabindex="4" onChange="return funExistData();"> -->
<!--                         </span> -->
<!--                     </td> -->
                </tr>
            </table>
          </div>

           <table width="99%">
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </table>

         <div id="costInfo" style="border:double 4px;padding:4px;border-color:#4169E1;width:85%;">
            <table datasrc="#xmlRGEN3040I" datafld="rec">
                <caption align="left">�y���̓R�X�g�e�[�u�����z</caption>
                <tr>
                    <td width="92">�R�X�g�e�[�u���Ő�</td>
                    <td width="70">
                        <span class="ninput" format="4" comma="false">
                        <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="4" value="" style="text-align:right;width:50px;" tabindex="4" >
                        </span>
                    </td>
                    <td width="100">�L���J�n��</td>
                    <td width="200">
                        <input type="text" class="disb_text" id="txtYuko" name="txtYuko"  datafld="dt_yuko" maxlength="10" value="" style="width:180px;" tabindex="4">
                    </td>
                    <td width="100">���g�p��</td>
                    <td width="100">
                        <!-- //  ���l�A�s���I�h�̂ݓ��͋����� -->
                        <span class="ninput" format="10,3" comma="false">
                            <input type="text" class="disb_text" id="txtShiyoRyo" name="txtShiyoRyo" maxlength="21" value="" style="text-align:right;" tabindex="5" onblur="this.value = funAddComma(this.value); return true;">
                        </span>
                    </td>
                    <td width="80">
                        <input type="button" class="normalbutton" id="btnCalc" name="btnCalc" value="�v�Z" onClick="funCalc();" tabindex="6" style="width:60px;" tabindex="6">

                    </td>
                    <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                    <td width="100">�A�b�v���i���j</td>
                    <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                    <td width="100">
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                        <input type="text" class="disb_text" id="txtUpRitu" name="txtUpRitu" maxlength="5" value="" style="text-align:right;" tabindex="7" onblur="this.value = funNumFormatChange(this.value,1); return true;">
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                    </td>
                    <td width="80">
                        <input type="button" class="normalbutton" id="btnUpRituCalc" name="btnUpRituCalc" value="�v�Z" onClick="funUpRituCalc();" tabindex="8" style="width:60px;">
                    </td>
                    <td width="185"></td>
                    <td width="80">���g�p
                    	<input type="checkbox" id="chkMishiyo" name="chkMishiyo" tabindex="9">
                    </td>
                </tr>
            </table>

            <!-- //add start�yQP@30297�z�ۑ� hisahori -->
            <table width="99%">
                <tr>
                	<td width="100">�R�X�g��ޖ�</td>
                	<td align="left">
                		<textarea class="disb_text" name="costHouzai" id="costHouzai" maxlength="10" cols="50" rows="2" style="width:1200px;" tabindex="9"></textarea>
<!--                 		<input type="text" class="" id="" datafld="" maxlength="200" value="" style="width:1200px" tabindex="9"> -->
<!--                 		<input type="button" class="normalbutton" id="houzaiTouroku" name="houzaiTouroku" value="��ޓo�^" onClick="" tabindex="10" style="width:60px;"> -->
                	</td>
<!--                     <td align="right">�P�ʁi�~�j�@�@</td> -->
                </tr>
            </table>
            <!-- //add end�yQP@30297�z�ۑ� hisahori -->
          </div>

           <table width="99%">
                <tr>
                    <td align="right">�P�ʁi�~�j�@�@</td>
                </tr>
            </table>

            <!-- [�R�X�g�e�[�u���ꗗ]���X�g -->
            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
            <div class="scroll" id="sclList" style="height:42%;width:99%;" rowSelect="true">
            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="1880px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <% for(int col = 0; col < 30; col++) { %>
                    <col style="width:50px;"/>
                    <% } %>
                </colgroup>
                <thead class="rowtitle" id="tblHeader">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">&nbsp;&nbsp;</th>
                        <th class="columntitle" style="text-align:right;">���b�g��</th>
                        <% for(int col = 0; col < 30; col++) { %>
                        <th class="columntitle" style="text-align:right;">
                        	<!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                            <input type="text" class="disb_text" id="txtHeader_<%=(col + 1)%>" style="width:50px;border-width:0px;text-align:right;" onkeyDown="return funNumOnly();" maxlength="13" onblur="this.value = funAddComma(this.value); return true;">
                            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                        </th>
                        <% } %>
                    </tr>
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">��</th>
                     <!-- 2017/3/16 TOs_tamura delete start -->
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                        <!--<th class="columntitle" style="text-align:left;">�F���^�g�p��</th>-->
                        <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                     <!-- 2017/3/16 TOs_tamura delete end -->
                     
                     <!-- 2017/3/16 TOs_tamura add start -->
                        <th class="columntitle" style="text-align:left;">�F��&nbsp;�g�p�ʁ��@�@�@�@�@�@�@&nbsp;��</th>
                     <!-- 2017/3/16 TOs_tamura add end -->
                     
                        <% for(int col = 0; col < 30; col++) { %>
                        <th class="columntitle" style="text-align:right;"><input type="text" class="disb_text" id="calcRslt<%=(col + 1)%>" style="width:50px;border-width:0px;text-align:right;" readonly></th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:1880px;display:none">
                        <tr class="disprow">
                        <% for(int row = 0; row < 30; row++) { %>
                        <tr class="disprow">
                            <td class="column" style="width:30px;text-align:right;"><%=(row + 1)%></td>
                            <td class="column" style="width:100px;text-align:left;">
                              <!-- // �yQP@30297�zNo.25 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start -->
                                <!-- input type="text" class="act_text" id="nm_title_<%=(row + 1)%>" style="background-color:transparent;width:100px;border-width:0px;text-align:left;" onkeyDown="funCheckChange(); return true;" -->
                                <input type="text" class="act_text" id="nm_title_<%=(row + 1)%>" style="background-color:transparent;width:100px;border-width:0px;text-align:left;" onkeyDown="funCellIdou(event.keyCode); funCheckChange(); return true;">
                              <!-- // �yQP@30297�zNo.25 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start -->
                            </td>
                            <% for(int col = 0; col < 30; col++) { %>
                            <td class="column" style="width:50px;">
	                          <!-- // �yQP@30297�zNo.23,25 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod start -->
	                            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
                                <!-- input type="text" id="no_value_<%=(row + 1)%>_<%=(col + 1)%>" class="disb_text" style="background-color:transparent;width:50px;border-width:0px;text-align:right;" maxlength="16" onblur="this.value = funAddComma(funNumFormatChange(this.value,3)); return true;" -->
                                <span class="ninput" format="10,2" comma="false">
                                    <input type="text" id="no_value_<%=(row + 1)%>_<%=(col + 1)%>" class="disb_text" style="background-color:transparent;width:50px;border-width:0px;text-align:right;" maxlength="16" onkeydown="funCellIdou(event.keyCode);" onblur="this.value = funAddComma(funNumFormatChange(this.value,2)); return true;">
                                </span>
                                <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->
                              <!-- // �yQP@30297�zNo.23,25 2014/08/20  E.kitazawa �ۑ�Ή� --------------------- mod end -->
                            </td>
                            <% } %>
                        </tr>
                        <% } %>
                    </table>
                </table>
            </table>
            </div>

			<!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start -->
            <table style="margin-top:10px;">
                <tr>
                    <td width="80">���l�i�����j</td>
                    <td>
                        <!-- // �yQP@30297�zNo.24 2014/08/21  E.kitazawa �ۑ�Ή� --------------------- mod start -->
                        <!-- input type="text" class="act_text" id="txtBiko" name="txtBiko" maxlength="50" value="" style="width:500px;" onKeyPress="return funCheckChange();" tabindex="9" -->
                        <input type="text" class="act_text" id="txtBiko" name="txtBiko" maxlength="200" value="" style="width:1200px;" onKeyPress="return funCheckChange();" tabindex="9">
                        <!-- // �yQP@30297�zNo.24 2014/08/21  E.kitazawa �ۑ�Ή� --------------------- mod end -->
                    </td>
                </tr>
                <tr>
                    <td width="80">���l�i�H��j</td>
                    <td>
                        <!-- // �yQP@30297�zNo.24 2014/08/21  E.kitazawa �ۑ�Ή� --------------------- mod start -->
                        <!-- input type="text" class="act_text" id="txtBiko_kojo" name="txtBiko_kojo" maxlength="50" value="" style="width:500px;" onKeyPress="return funCheckChange();" tabindex="9" -->
                        <input type="text" class="act_text" id="txtBiko_kojo" name="txtBiko_kojo" maxlength="200" value="" style="width:1200px;" onKeyPress="return funCheckChange();" tabindex="10">
                        <!-- // �yQP@30297�zNo.24 2014/08/21  E.kitazawa �ۑ�Ή� --------------------- mod end -->
                    </td>
                </tr>
            </table>
            <!-- // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end -->

            <table style="margin-top:10px;">
                <tr>
                    <td width="10"><input type="checkbox" id="chk_kakunin" name="chkKakunin" tabindex="11" onClick="funCheckData();"></td>
                    <td width="46">�o�^�ҁF</td>
                    <td width="120" id="lblKakunin">�|</td>
                    <td width="10"><input type="checkbox" id="chk_shonin" name="chkShonin" tabindex="12"></td>
                    <td width="46">���F�ҁF</td>
                    <td width="120"id="lblShonin">�|</td>
                </tr>
            </table>

            <!-- table width="870" style="margin-top:10px;clear:left;" -->
            <table width="400" style="margin-top:10px;clear:left;">
                <tr>
                    <td>
                        <input type="button" class="normalbutton" id="btnDataEdit" name="btnDataEdit" value="�o�^" onClick="funDataEdit(1);" tabindex="13">
                    </td>
                    <!-- // �yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add start -->
                    <td>
                        <input type="button" class="normalbutton" id="btnDataDelete" name="btnDataDelete" value="�폜" onClick="funDataDelete();"  tabindex="14">
                    </td>
                    <!-- // �yQP@30297�zNo.22 2014/08/22  E.kitazawa �ۑ�Ή� --------------------- add end -->
                </tr>
            </table>
        </form>
    </body>
</html>
