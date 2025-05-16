<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�S���҃}�X�^�����e�i���X���                                      -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/06                                                              -->
<!-- �T�v  �F�S���҃f�[�^�̓o�^�A�X�V�A�폜���s���B                                  -->
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
        <script type="text/javascript" src="include/SQ150EigyoTantoMst.js"></script>
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

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlRGEN2040"></xml>
        <xml id="xmlRGEN2050"></xml>
        <xml id="xmlRGEN2060"></xml>
        <xml id="xmlRGEN2070"></xml>
        <xml id="xmlJSP0010"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2040I" src="../../model/FGEN2040I.xml"></xml>
        <xml id="xmlFGEN2050I" src="../../model/FGEN2050I.xml"></xml>
        <xml id="xmlFGEN2120I" src="../../model/FGEN2120I.xml"></xml>
        <xml id="xmlFGEN2060I" src="../../model/FGEN2060I.xml"></xml>
        <xml id="xmlFGEN2070I" src="../../model/FGEN2070I.xml"></xml>
        <xml id="xmlFGEN2080I" src="../../model/FGEN2080I.xml"></xml>
        <xml id="xmlSA010I" src="../../model/SA010I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2040O"></xml>
        <xml id="xmlFGEN2050O"></xml>
        <xml id="xmlFGEN2120O"></xml>
        <xml id="xmlFGEN2060O"></xml>
        <xml id="xmlFGEN2070O"></xml>
        <xml id="xmlFGEN2080O"></xml>
        <xml id="xmlSA010O"></xml>

        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- �^�C�g���E�{�^�� -->
            <table width="99%">
                <tr>
                    <td width="35%" class="title">�S���҃}�X�^�����e�i���X�i�c�Ɓj</td>
                    <td width="65%" align="right">
                        <!-- MOD 2013/9/25 okano�yQP@30151�zNo.28 start -->
<!--                         <input type="button" class="normalbutton" id="btnMenu" name="btnMenu" value="���C�����j���[��" style="width:120px" onClick="funNextMenu();"> -->
                        <input type="button" class="normalbutton" id="btnMenu" name="btnMenu" value="���C�����j���[��" style="width:120px" onClick="funNext(1);">
                        <!-- MOD 2013/9/25 okano�yQP@30151�zNo.28 end -->
                        <!-- MOD start 2015/03/03 TT.Kitazawa�yQP@40812�zNo.5 -->
                        <!-- <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�V�K" style="width:80px" onClick="funDataEdit(1);"> -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�V�K�o�^" style="width:80px" onClick="funDataEdit(1);">
                        <!-- MOD end 2015/03/03 TT.Kitazawa�yQP@40812�zNo.5 -->
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="�X�V" style="width:80px" onClick="funDataEdit(2);">
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="�폜" style="width:80px" onClick="funDataEdit(3);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" style="width:80px" onClick="funClear();">
                        <!-- MOD 2013/9/25 okano�yQP@30151�zNo.28 start -->
<!--                         <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funNext(0);"> -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funClose();">
                        <!-- MOD 2013/9/25 okano�yQP@30151�zNo.28 end -->
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- ���́E�I�� -->
            <table width="660" align="center">
                <tr>
                    <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.23 start -->
                    <!-- <td width="120">���[�UID<font color="red">�i�K�{�j</font></td> -->
                    <td width="120">�Ј��ԍ�<font color="red">�i�K�{�j</font></td>
                    <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.23 end -->
                    <td width="340">
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <!-- �yQP@10713�z�V�T�N�C�b�N���� No.25 -->
                        <!-- <input type="text" class="disb_text" id="txtUserId" name="txtUserId" maxlength="10" value="" style="width:300px;" onBlur="funBuryZero(this, 10);funSearch();" onChange="funChangeUserId();"> -->
                        <input type="text" class="disb_text" id="txtUserId" name="txtUserId" maxlength="10" value="" style="width:300px;" onBlur="funBuryZero(this, 10);funSearch();" onChange="funChangeUserId();funChangeKengen();">
                        </span>
                    </td>
                    <td align="left">
                        <input type="button" class="normalbutton" id="btnSearchUser" name="btnSearchUser" value="����" style="width:80px" onClick="funSearchUser();">
                    </td>
                </tr>

                <!-- Line -->
                <tr>
                    <td colspan="3" align="center"><hr></td>
                </tr>

                <tr>
                    <td width="120">�p�X���[�h<font color="red">�i�K�{�j</font></td>
                    <td width="310">
                        <input type="password" class="disb_text" id="txtPass" name="txtPass" maxlength="30" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">����<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlKengen" id="ddlKengen" name="ddlKengen" style="width:300px;" onChange="funChangeKengen();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">����<font color="red">�i�K�{�j</font></td>
                    <td>
                       <input type="text" class="act_text" id="txtUserName" name="txtUserName" maxlength="60" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">�������<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" style="width:300px;" onChange="funChangeKaisha();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120">��������<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlBusho" name="ddlBusho" style="width:300px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" height="10">&nbsp;</td>
                </tr>
            </table>

            <br>

            <table width="660" align="center">
                <tr>
                    <!-- MOD start 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 -->
                    <!-- <td width="120" valign="top">�S����i</td> -->
                    <td width="120" valign="top">���L�����o�[</td>
                    <td width="340" valign="top" align="left">
                        <!-- <input type="text" class="act_text" id="txtUserNameJosi" name="txtUserNameJosi" maxlength="60" value="" style="width:300px;" disabled>
                        <input type="hidden" id="hdnUserNameJosi" name="hdnUserNameJosi" maxlength="60" value=""> -->

                        <div id="sclList" style="overflow-y:scroll;height:118px;width:315px;" rowSelect="true">
                        <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN2060O" datafld="rec" style="width:300px;display:none;">
                            <tr class="disprow">
                                <td class="column" width="300" align="left" disabled><span datafld="nm_member"></span></td>
                            </tr>
                        </table>
                        </div>
                    </td>
                    <td width="187" valign="bottom">
                        <!-- <input type="button" class="normalbutton" id="btnJosiSearch" name="btnJosiSearch" value="����" style="width:80px" onClick="funSearchUserJosi();">
                        <input type="button" class="normalbutton" id="btnJosiDel" name="btnJosiDel" value="��i�N���A" style="width:80px" onClick="funDelJosi();"> -->

                        <input type="button" class="normalbutton" id="btnAddList" name="btnAddList" value="�ǉ�" style="width:80px" onClick="funAddList();">
                        <input type="button" class="normalbutton" id="btnDelList" name="btnDelList" value="�폜" style="width:80px" onClick="funDelList();">
                    </td>
                    <!-- MOD end 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 -->
                </tr>
            </table>

            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
            <input type="hidden" id="hidOpnerSearch" name="hidOpnerSearch" value="">
        </form>

        <input type="hidden" id="hidMode" name="hidMode" value="">
    </body>
</html>
