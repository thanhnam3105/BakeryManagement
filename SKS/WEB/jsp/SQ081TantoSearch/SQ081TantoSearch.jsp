<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�S���Ҍ������                                                    -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/06                                                              -->
<!-- �T�v  �F�����Ɉ�v����S���҂̌������s���B                                      -->
<!------------------------------------------------------------------------------------->
<!-- �X�V�ҁFTT.Jinbo                                                                -->
<!-- �X�V���F2009/06/24                                                              -->
<!-- ���e  �F���X�g�̕\���������L����(�ۑ�\��14)                                    -->
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
        <script type="text/javascript" src="include/SQ081TantoSearch.js"></script>
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
        <xml id="xmlJSP0910"></xml>
        <xml id="xmlJSP0920"></xml>
        <xml id="xmlJSP9010"></xml>
        <xml id="xmlJSP9020"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA080I" src="../../model/SA080I.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA240I" src="../../model/SA240I.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA080O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA290O"></xml>
        <xml id="xmlSA240O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="18%" class="title">�S���Ҍ���</td>
                    <td width="65%" align="right">
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
            <table width="900" datasrc="#xmlSA240I" datafld="rec">
                <tr>
                    <td width="120">���[�UID</td>
                    <td width="230">
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <input type="text" class="disb_text" id="txtUserId" name="txtUserId" datafld="id_user" maxlength="10" value="" style="width:200px;" onBlur="funBuryZero(this, 10);" tabindex=1>
                        </span>
                    </td>
                    <td width="120">�������</td>
                    <td width="230">
                        <select id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:200px;" onChange="funChangeKaisha();" tabindex=3>
                        </select>
                    </td>
                    <td width="120">�����O���[�v</td>
                    <td width="230">
                        <select id="ddlGroup" name="ddlGroup" datafld="cd_group" style="width:200px;" onChange="funChangeGroup();" tabindex=5>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" height="5"></td>
                </tr>
                <tr>
                    <td>�S���Җ�</td>
                    <td>
                        <input type="text" class="act_text" id="txtTantoName" name="txtTantoName" datafld="nm_user" maxlength="60" value="" style="width:200px;" tabindex=2>
                    </td>
                    <td>��������</td>
                    <td>
                        <select id="ddlBusho" name="ddlBusho" datafld="cd_busho" style="width:200px;" tabindex=4>
                        </select>
                    </td>
                    <td>�����`�[��</td>
                    <td>
                        <select id="ddlTeam" name="ddlTeam" datafld="cd_team" style="width:200px;" tabindex=6>
                        </select>
                    </td>
                </tr>
            </table>

            <br>

            <!-- [���[�U�[�ꗗ]���X�g -->
            <div class="scroll" id="sclList" style="height:67%;width:99%;" rowSelect="true" border=1>
<!-- 2015/03/03 TT.Kitazawa�yQP@40812�zMOD start -->
            <!-- <table id="dataTable" name="dataTable" cellspacing="0" width="1630px" align="center"> -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="1630px">
<!-- 2015/03/03 TT.Kitazawa�yQP@40812�zMOD end -->
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <col style="width:100px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">���[�UID</th>
                        <th class="columntitle">����</th>
                        <th class="columntitle">����</th>
                        <th class="columntitle">�������</th>
                        <th class="columntitle">��������</th>
                        <th class="columntitle">�����O���[�v</th>
                        <th class="columntitle">�����`�[��</th>
                        <th class="columntitle">��E</th>
                        <th class="columntitle">�S���������</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA240O" datafld="rec" style="width:1630px;display:none">
                        <tr class="disprow">
                            <td class="column" width="32" align="right"><span datafld="no_row"></span></td>
                            <td class="column" width="100" align="left"><span datafld="id_user"></span></td>
                            <td class="column" width="100" align="left"><span datafld="nm_kengen"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_user"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_kaisha"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_busho"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_group"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_team"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_yakushoku"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_tantokaisha"></span></td>
                        </tr>
                    </table>
<!-- 2015/03/03 TT.Kitazawa�yQP@40812�zMOD start -->
                </tbody>
                <!-- </table> -->
<!-- 2015/03/03 TT.Kitazawa�yQP@40812�zMOD end -->
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
