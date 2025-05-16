<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�����S����Вǉ����                                              -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/06                                                              -->
<!-- �T�v  �F�����S����Ђ̌����A�Ăяo������ʂւ̔��f���s���B                      -->
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
        <script type="text/javascript" src="include/SQ082KasihaAdd.js"></script>
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
            //�I���s�̔w�i�F��ύX
            funChangeSelectRowColor();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document��` -->
        <xml id="xmlJSP1010"></xml>
        <xml id="xmlJSP9030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA220I" src="../../model/SA220I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA220O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="25%" class="title">�����S����Ќ���</td>
                    <td width="70%" align="right">
                        <input type="button" class="normalbutton" name="btnSearch" id="btnSearch" value="����" onClick="funSearch();">
                        <input type="button" class="normalbutton" name="btnSelect" id="btnSelect" value="�I��" onClick="funSelect();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- ���́E�I�� -->
            <table width="610" datasrc="#xmlSA220I" datafld="rec">
                <tr>
                    <td>��Ж�</td>
                    <td>
                        <input type="text" class="act_text" id="txtKaishaName" name="txtKaishaName" datafld="nm_kaisha" maxlength="100" value="" style="width:300px;">
                    </td>
                </tr>
            </table>

            <br>

            <!-- [�����S����Јꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:60%;width:750;" rowSelect="true" border=1>
            <table id="dataTable" name="dataTable" cellspacing="0" width="730px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <col style="width:600px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">���CD</th>
                        <th class="columntitle">��Ж�</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA220O" datafld="rec" style="width:730px;display:none">
                        <tr class="disprow">
                            <td class="column" width="33" align="right"><span datafld="no_row"></span></td>
                            <td class="column" width="102" align="left"><span datafld="cd_kaisha"></span></td>
                            <td class="column" width="600" align="left"><span datafld="nm_kaisha"></span></td>
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
        </form>
    </body>
</html>
