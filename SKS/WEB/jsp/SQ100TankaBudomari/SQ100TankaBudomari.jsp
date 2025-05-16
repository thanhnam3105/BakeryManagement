<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�S�H��P���������                                                -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/05/19                                                              -->
<!-- �T�v  �F���������Ɉ�v���錴���f�[�^���ꗗ�ŕ\������B                          -->
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
        <script type="text/javascript" src="include/SQ100TankaBudomari.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //�p�~�����̔w�i�F��ύX
                funChangeHaishiColor();

                //������ү���ޔ�\��
                funClearRunMessage();
            }
        </script>

        <!--  �e�[�u�����׍s�N���b�N -->
        <script for="tblList" event="onclick" language="JavaScript">
            //�I���s�̔w�i�F��ύX
            funChangeSelectRowColorLocal();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlJSP1310"></xml>
        <xml id="xmlJSP1320"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA790I" src="../../model/SA790I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA790O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" onsubmit="" method="post">
            <table width="99%">
                <tr>
                    <td width="18%" class="title">�S�H��P������</td>
                    <!-- ���[�U�[��� -->
                    <td width="68%"><div id="divUserInfo"></div></td>
                </tr>
            </table>

            <br>

            <!-- ���́E�I�� -->
            <table width="960" datasrc="#xmlSA390I" datafld="rec">
                <tr>
                    <td>���<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:180px;" tabindex="1">
                        </select>
                    </td>
                    <td>�R�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtGenryoCd" name="txtGenryoCd" datafld="cd_genryo" maxlength="11" value="" style="width:180px;" tabindex="2">
                    </td>
                    <td rowspan="2">
                        <table border="1" bordercolor="black">
                            <tr>
                                <td>
                                    <input type="radio" name="rdoGenryoKbn" id="rdoGenryoKbn" value="0" tabindex="4" CHECKED>����&nbsp;�@&nbsp;<br>
                                    <input type="radio" name="rdoGenryoKbn" id="rdoGenryoKbn" value="1" tabindex="5">����
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td rowspan="2">
                        <table border="1" bordercolor="black">
                            <tr>
                                <td>
                                    <input type="radio" name="rdoTaisho" id="rdoTaisho" value="0" tabindex="6" CHECKED>�P��&nbsp;�@&nbsp;<br>
                                    <input type="radio" name="rdoTaisho" id="rdoTaisho" value="1" tabindex="7">����
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td rowspan="2">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="����" style="height:32px;" onClick="funSearch();" tabindex="8">
                    </td>
                    <td width="170" rowspan="2" align="right">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="height:32px;" onClick="funNext(0);" tabindex="9">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>���O</td>
                    <td>
                        <input type="text" class="act_text" id="txtGenryoName" name="txtGenryoName" datafld="nm_genryo" maxlength="60" value="" style="width:180px;" tabindex="3">
                    </td>
                </tr>
            </table>

            <!-- [�S�H��P�������ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:67%;width:99%;" rowSelect="true">
            <div id="divTableList">
            <!-- <table id="dataTable" name="dataTable" cellspacing="0" width="950px" align="center"> -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="950px" align="left">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:80px;"/>
                    <col style="width:200px;"/>
                    <col style="width:650px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" width="30">&nbsp;</th>
                        <th class="columntitle" width="80">����CD</th>
                        <th class="columntitle" width="200">������</th>
                        <th class="columntitle" width="650">&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA790O" datafld="rec" style="width:950px;display:none">
                        <tr class="disprow">
                            <td class="column" width="32" align="right"><span datafld="no_row"></span></td>
                            <td class="column" width="82" align="left"><span datafld="cd_genryo"></span></td>
                            <td class="column" width="202" align="left"><span datafld="nm_genryo"></span></td>
                            <td class="column" id="lblCol" width="650" align="left"><span datafld="disp_val1"></span></td>
                        </tr>
                    </table>
                </table>
            </table>
            </div>
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
