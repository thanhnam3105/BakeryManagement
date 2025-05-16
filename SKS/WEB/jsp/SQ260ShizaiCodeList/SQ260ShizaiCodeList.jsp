<%@ page language="java" contentType="text/html;charset=Windows-31J" %>

<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���ރR�[�h�ďo���                                                -->
<!-- �쐬�ҁFTT.Shima                                                                -->
<!-- �쐬���F2014/09/09                                                              -->
<!-- �T�v  �F���ރR�[�h����������                                                    -->
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
        <script type="text/javascript">
            <!--
                var str = location.search;
                var gamenId = str.substring(1, str.length);

                var script = document.createElement("script");
                script.type = "text/javascript";

                if(gamenId == "shizai"){
                    // ���ރR�[�h�ďo
                    script.src = "include/SQ260ShizaiCode.js";
                } else if (gamenId == "ruizi"){
                	// �ގ��R�[�h�ďo
                    script.src = "include/SQ260RuiziData.js";
                } else if (gamenId == "shizaiTehai"){
                	// ���ގ�z�ψꗗ
                	script.src = "include/SQ260ShizaiTehaiZumi.js";
                }
                document.getElementsByTagName("head")[0].appendChild(script);
            // -->
        </script>

        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //������ү���ޔ�\��
                funClearRunMessage();
            }
        </script>

        <!--  �e�[�u�����׍s�N���b�N -->
        <script for="tblList" event="onclick" language="JavaScript">
            var frm = document.frm00;    //̫�тւ̎Q��

            var row = -1;
            // �N���b�N���ꂽ�A�N�e�B�u�G�������g���擾�iCurrentRow ���擾�ł��Ȃ��ׁj
            var activeElementName = document.activeElement.name;
            // ���׍s�̃J�����g�s�i�r�����N���b�N�������ȂǁAundefined�ɂȂ�j
            if (!!activeElementName) {
                var nameAry = activeElementName.split("_");
                // funGetCurrentRow();
                row = nameAry[nameAry.length - 1];
            }

            if (row >= 0) {
                // ���ރR�[�h�ďo�̏ꍇ�F
                //   �I���s�̃`�F�b�N�{�b�N�X��؂�ւ���
                if (!!frm.chkShizai){
                    // 2��ڂ̃C�x���g�����Ń`�F�b�N��؂�ւ�
                    if (row == funGetCurrentRow()) {
                        // �����s���N���b�N�������ׂ̈ɃN���A
                        funSetCurrentRow(-1);
                        if (!!frm.chkShizai[row]) {
                            // �\���������s
                            frm.chkShizai[row].checked = !(frm.chkShizai[row].checked);
                        } else {
                            // �\����1�s�iIndex ���t���Ă��Ȃ��j
                            frm.chkShizai.checked = !(frm.chkShizai.checked);
                        }
                    } else {
                        // �J�����g�s��ݒ肷��
                        clickItiran(row);
                    }

                // �ގ��R�[�h�ďo�̏ꍇ�F���W�I�{�^��
                }else if (!!frm.chk){
                    if (!!frm.chk[row]){
                        clickItiran(row);
                        frm.chk[row].checked = true;
                    } else {
                        clickItiran(0);
                        frm.chk.checked = true;
                    }
                }
            }

        </script>

        <!-- �X�^�C���V�[�g -->
<!--         <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css"> -->

        <META HTTP-EQUIV="Content-Type" content="text/html; charset=shift_jis">
        <META HTTP-EQUIV="Content-Language" content="ja">
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document��` -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3330"></xml>
        <xml id="xmlRGEN3340"></xml>
        <xml id="xmlRGEN3350"></xml>
        <xml id="xmlRGEN3620"></xml>
        <!-- nakamura -->
        <xml id="xmlRGEN3690"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3300I" src="../../model/FGEN3300I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>
        <xml id="xmlFGEN3330I" src="../../model/FGEN3330I.xml"></xml>
        <xml id="xmlFGEN3340I" src="../../model/FGEN3340I.xml"></xml>
        <xml id="xmlFGEN3350I" src="../../model/FGEN3350I.xml"></xml>
        <xml id="xmlFGEN3620I" src="../../model/FGEN3620I.xml"></xml>
        <!-- nakamura -->
        <xml id="xmlFGEN3690I" src="../../model/FGEN3690I.xml"></xml>

        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3300O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3330O"></xml>
        <xml id="xmlFGEN3340O"></xml>
        <xml id="xmlFGEN3350O"></xml>
        <xml id="xmlFGEN3620O"></xml>
        <!-- nakamura -->
        <xml id="xmlFGEN3690O"></xml>
        <xml id="xmlFGEN3690"></xml>
		<xml id="xmlRGEN3700"></xml>

        <xml id="xmlSA290O"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data">
            <!-- �^�C�g���� -->
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="20%" class="title" id="lblTitle">
                    </td>
                    <td width="75%" align="right">

                    <!--2017/3/13 tamura add start-->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" style="width:80px" onClick="funSearch();">
                        <input type="button" class="normalbutton" id="btnCompletion" name="btnCompletion" value="�I������" style="width:80px" onClick="funCompletion();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" style="width:80px" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnOutput" name="btnOutput" value="EXCEL" style="width:100px" onClick="funExcelOut();">
                        <input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="�A�b�v���[�h" style="width:100px" onClick="funToroku();"><!-- nakamura�ύX -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funEnd();">
                    <!--2017/3/13 tamura add end-->

                    <!--2017/3/13 tamura delete start-->
                   	   <!--<input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="�A�b�v���[�h" style="width:100px" onClick="funToroku();">--><!-- nakamura�ύX -->
                       <!--<input type="button" class="normalbutton" id="btnOutput" name="btnOutput" value="EXCEL" style="width:100px" onClick="funExcelOut();">-->
                       <!--<input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" style="width:80px" onClick="funSearch();">-->
                       <!--<input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" style="width:80px" onClick="funClear();">-->
                       <!--<input type="button" class="normalbutton" id="btnCompletion" name="btnCompletion" value="�I������" style="width:80px" onClick="funCompletion();">-->
                       <!--<input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funEnd();">-->
                    <!--2017/3/13 tamura delete end -->

                    </td>
                </tr>
            </table>

            <!-- ��� -->
            <table width="99%">
                <tr>
                    <td align="right">
                        <div id="divUserInfo"></div>
                    </td>
                </tr>
            </table>

            <table>
                <!-- 1��� -->
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="2" >
                        <input type="checkbox" id="chkTehaizumi" name="chkTehaizumi" tabindex="1">
                        <label for="chkTehaizumi"> ��z�ς�</label>&nbsp;&nbsp;
                        <input type="checkbox" id="chkMitehai" name="chkMitehai" tabindex="2">
                        <label for="chkMitehai"> ����z&nbsp;&nbsp;</label>
                        <input type="checkbox" id="chkMinyuryoku" name="chkMinyuryoku" tabindex="3">
                        <label for="chkMinyuryoku"> ������</label>
                        <input type="checkbox" id="chkEdit" name="chkEdit" onClick="funChangeEdit();" tabindex="4">
                        <label for="chkEdit"> �ҏW</label>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <!-- 2��� -->
                <tr>
                    <td width="100">���ރR�[�h</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" id="txtShizaiCd" name="txtShizaiCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="5" >
                        </span>
                    </td>
                    <td width="100">���ޖ�</td>
                    <td width="300">
                        <input type="text" class=act_text id="txtShizaiNm" name="txtShizaiNm" style="width:270px;" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="6">
                    </td>
                    <td width="100">�����ރR�[�h</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" id="txtOldShizaiCd" name="txtOldShizaiCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="7">
                        </span>
                    </td>
                </tr>
                <!-- 3��� -->
                <tr>
                    <td width="100">���i�R�[�h</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" class=disb_text id="txtSyohinCd" name="txtSyohinCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="8">
                        </span>
                    </td>
                    <td width="100">���i��</td>
                    <td width="300">
                        <input type="text" class=act_text id="txtSyohinNm" name="txtSyohinNm" style="width:270px;" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="9">
                    </td>
                    <!-- �yKPX@1602367�zadd 20160912 -->
                    <td width="100">�[����</td>
                    <td width="200">
                        <input type="text" class=act_text id="txtSeizoukojo" name="txtSeizoukojo" style="width:170px;" onChange="funChangeKojo();" tabindex="10">
                        <input type="hidden" value="" name="seizoKojoCd" id="seizoKojoCd" onChange="funChangeKojo();">
                    </td>
                </tr>
                <!-- 4��� -->
                <tr>
                    <td width="100">�Ώێ���</td>
                    <td width="200">
                        <select id="ddlShizai" name="ddlShizai" style="width:170px;" onChange="funChangeSearch();" tabindex="10">
                        </select>
                    </td>
                    <td width="100">������</td>
                    <td width="200">
                        <select id="ddlHattyusaki" name="ddlHattyusaki" style="width:170px;" onChange="funChangeSearch();" tabindex="11">
                        </select>
                    </td>
                    <!-- �yKPX@1602367�zupd 20160912 -->
                    <td width="100">������</td>
                    <td width="200">
                        <select id="ddlTanto" name="ddlTanto" style="width:170px;" onChange="funChangeSearch();" tabindex="12">
                        </select>
                    </td>
                </tr>
                <!-- �yKPX@1602367�zadd 20160912 -->
                <!-- 5��� -->
                <tr>
                    <td width="100">������</td>
                    <td width="200">

                            <input type="text" class="act_text disb_text" id="txtHattyubiFrom" name="txtHattyubiFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="13">

                        �`

                            <input type="text" class="act_text disb_text" id="txtHattyubiTo" name="txtHattyubiTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="14">

                    </td>
                    <td width="100">�[����</td>
                    <td width="200">
                        <span class="disb_text">
                            <input type="text" class="act_text disb_text" id="txtNounyudayFrom" name="txtNounyudayFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="15">
                        </span>
                        �`

                            <input type="text" class="act_text disb_text" id="txtNounyudayTo" name="txtNounyudayTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="16">

<!--                         <input type="text" class=ninput id="txtNounyuday" name="txtNounyuday" style="width:170px;" maxlength="10" onChange="funChangeSearch();" tabindex="15"> -->
                    </td>
                    <td width="100">�ő�x����</td>
                    <td width="200">
                            <input type="text" class="act_text disb_text" id="txtHanPaydayFrom" name="txtHanPaydayFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="17">
                        �`
                            <input type="text" class="act_text disb_text" id="txtHanPaydayTo" name="txtHanPaydayTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="18">
<!--                         <input type="text" class=ninput id="txtHanPayday" name="txtHanPayday" style="width:170px;" maxlength="10" onChange="funChangeSearch();" tabindex="16"> -->
                    </td>
                    <td>
                    <input type="checkbox" id="chkMshiharai" name="chkMshiharai"  onClick="this.blur();this.focus();" onChange="funChangeSearch();" tabindex="19">
                        <label for="chkEdit"> ���x��</label>
                    </td>
                </tr>

            </table>

            <!-- [�����ް��ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:60%;width:100%;" rowSelect="true" align="left">
                <table id="dataTable" name="dataTable" cellspacing="0" width="4365px" >
                <colgroup>
                    <col style="width:30px;"/><!-- No -->
                    <col style="width:30px;"/><!-- �I���{�^�� -->
                    <col id="colHeadTanto" style="width:100px;"/><!-- �S���� -->
                    <col id="colHeadNaiyo" style="width:200px;"/><!-- ���e -->
                    <col style="width:50px;"/><!-- ���i�R�[�h -->
                    <col style="width:150px;"/><!-- ���i�� -->
                    <col id="colHeadNisugata" style="width:150px;"/><!-- �׎p -->
                    <col style="width:120px;"/><!-- �Ώێ��� -->
                    <col id="colHeadNmHattyusaki" style="width:150px;"/><!-- ������ -->
                    <col id="colHeadNmNounyusaki_" style="width:150px;"/><!-- �[���� -->
                    <col id="colHeadCdShizaiOld" style="width:50px;"/><!-- �����ރR�[�h -->
                    <col style="width:50px;"/><!-- ���ރR�[�h -->
                    <col style="width:150px;"/><!-- ���ޖ� -->
                    <col id="colHeadSekkei1" style="width:200px;"/><!-- �݌v�@ -->
                    <col id="colHeadSekkei2" style="width:200px;"/><!-- �݌v�A -->
                    <col id="colHeadSekkei3" style="width:200px;"/><!-- �݌v�B -->
                    <col id="colHeadZaishitsu" style="width:200px;"/><!-- �ގ� -->
                    <col id="colHeadBikoTehai" style="width:300px;"/><!-- ���l -->
                    <col id="colHeadPrintcolor" style="width:200px;"/><!-- ����F -->
                    <col id="colHeadNo_color" style="width:200px;"/><!-- �F�ԍ� -->
                    <col id="colHeadHenkounaiyoushosai" style="width:200px;"/><!-- �ύX���e�ڍ� -->
                    <col id="colHeadNouki" style="width:150px;"/><!-- �[�� -->
                    <col id="colHeadSuryo" style="width:150px;"/><!-- ���� -->
                    <col id="colHeadTorokuDisp" style="width:150px;"/><!-- �o�^�� -->
                    <col id="colHeadNounyuDay" style="width:100px;"/><!-- �[���� -->
                    <col style="width:150px;"/><!-- �ő�-->
                    <col style="width:100px;"/><!-- �ő�x���� -->
                    <col style="width:480px;"/><!-- �ăA�b�v���[�h -->
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;z-index:1">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">�I</th>
                        <th class="columntitle">�S����</th>
                        <th class="columntitle">���e</th>
                        <th class="columntitle">���i<br>�R�[�h</th>
                        <th class="columntitle">���i��</th>
                        <th class="columntitle">�׎p</th>
                        <th class="columntitle">�Ώێ���</th>
                        <th class="columntitle">������</th>
                        <th class="columntitle">�[����</th>
                        <th class="columntitle">������<br>�R�[�h</th>
                        <th class="columntitle">����<br>�R�[�h</th>
                        <th class="columntitle">���ޖ�</th>
                        <th class="columntitle">�݌v�@</th>
                        <th class="columntitle">�݌v�A</th>
                        <th class="columntitle">�݌v�B</th>
                        <th class="columntitle">�ގ�</th>
                        <th class="columntitle">���l</th>
                        <th class="columntitle">����F</th>
                        <th class="columntitle">�F�ԍ�</th>
                        <th class="columntitle">�ύX���e�ڍ�</th>
                        <th class="columntitle">�[��</th>
                        <th class="columntitle">����</th>
                        <th class="columntitle">�o�^��</th>
                        <th class="columntitle">�[����</th>
                        <th class="columntitle">�ő�</th>
                        <th class="columntitle">�ő�x����</th>
                        <th class="columntitle">�ăA�b�v���[�h</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="0" style="width:4315px;">
                        <tr>
                            <td>
                                <div id="divMeisai" name="divMeisai" />
                            </td>
                        <tr>
                    </table>
                </table>
            </div>
            <!-- �������ʍs�� -->
            <input type="hidden" id="hidListRow" name="hidListRow">

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
