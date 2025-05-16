<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���e�����}�X�^�����e�i���X���                                    -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/04                                                              -->
<!-- �T�v  �F���e�����f�[�^�̓o�^�A�X�V�A�폜���s���B                                -->
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
        <script type="text/javascript" src="include/SQ060LiteralMst.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=ddlCategory event=onkeydown>
            if (event.keyCode == 13 && hidGamenId.value == ConGmnIdLiteralCsv) {
                //�Ō�̺��۰ق�Enter�������̏ꍇ�A��а���݂�̫�����ݒ�
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=ddlLiteral event=onkeydown>
            if (event.keyCode == 13 && document.frm00.ddlUseEdit.selectedIndex == 0) {
                //�Ō�̺��۰ق�Enter�������̏ꍇ�A��а���݂�̫�����ݒ�
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=ddlGroup event=onkeydown>
            if (event.keyCode == 13) {
                //�Ō�̺��۰ق�Enter�������̏ꍇ�A��а���݂�̫�����ݒ�
                document.frm00.btnDummy.focus();
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document��` -->
        <xml id="xmlJSP0610"></xml>
        <xml id="xmlJSP0620"></xml>
        <xml id="xmlJSP0630"></xml>
        <xml id="xmlJSP0640"></xml>
        <xml id="xmlJSP0650"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA040I" src="../../model/SA040I.xml"></xml>
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA100I" src="../../model/SA100I.xml"></xml>
        <xml id="xmlSA110I" src="../../model/SA110I.xml"></xml>
        <xml id="xmlSA300I" src="../../model/SA300I.xml"></xml>
        <xml id="xmlSA320I" src="../../model/SA320I.xml"></xml>
        <xml id="xmlSA330I" src="../../model/SA330I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA040O"></xml>
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA100O"></xml>
        <xml id="xmlSA110O"></xml>
        <xml id="xmlSA300O"></xml>
        <xml id="xmlSA320O"></xml>
        <xml id="xmlSA330O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="30%" class="title">�J�e�S���}�X�^�����e�i���X</td>
                    <td width="70%" align="right">
                        <input type="button" id="btnDummy" name="btnDummy" value="" style="width:0px" tabindex=-1> <!-- ̫����ݒ�p��а���� -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="�V�K" style="width:80px" onClick="funDataEdit(1);">
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="�X�V" style="width:80px" onClick="funDataEdit(2);">
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="�폜" style="width:80px" onClick="funDataEdit(3);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" style="width:80px" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel" style="width:80px" onClick="funOutput();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="width:80px" onClick="funNext(0);">
                    </td>
                </tr>
            </table>

            <!-- ���[�U�[��� -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- ���́E�I�� -->
            <table width="760" datasrc="#xmlSA330I" datafld="rec">
                <tr>
                    <td width="120">�J�e�S��<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlCategory" name="ddlCategory" datafld="cd_category" style="width:400px;" onChange="funChangeCategory();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>���e����</td>
                    <td>
                        <select id="ddlLiteral" name="ddlLiteral" style="width:400px;" onChange="funSearch();" onaChange="funSearch();">
                        </select>
                    </td>
                </tr>

                <!-- Line -->
                <tr>
                    <td colspan="2" align="center">
                        <hr>
                    </td>
                </tr>

<!--                <tr>
                    <td>���e�����R�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtLiteralCd" name="txtLiteralCd" datafld="cd_literal" maxlength="10" value="" style="width:150px;">
                    </td>
                </tr>-->
                <tr>
                    <td>���e������<font color="red">�i�K�{�j</font></td>
                    <td>
                        <input type="text" class="act_text" id="txtLiteralName" name="txtLiteralName" datafld="nm_literal" maxlength="60" value="" style="width:520px;">
                    </td>
                </tr>
                <tr>
                    <td>���e�����l1</td>
                    <td>
                        <span class="ninput" format="9,0" required="true" defaultfocus="false" id="em_Value1">
                        <input type="text" class="disb_text" id="txtValue1" name="txtValue1" datafld="value1" maxlength="9" value="" style="width:150px;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>���e�����l2</td>
                    <td>
                        <span class="ninput" format="9,0" required="true" defaultfocus="false" id="em_Value2">
                        <input type="text" class="disb_text" id="txtValue2" name="txtValue2" datafld="value2" maxlength="9" value="" style="width:150px;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>�\����<font color="red">�i�K�{�j</font></td>
                    <td>
                        <span class="ninput" format="3,0" required="true" defaultfocus="false" id="em_SortNo">
                        <input type="text" class="disb_text" id="txtSortNo" name="txtSortNo" datafld="no_sort" maxlength="3" value="" style="width:150px;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>���l</td>
                    <td>
                        <textarea class="act_area" id="txtBikou" name="txtBikou" datafld="biko" cols="100" rows="5" value=""></textarea>
                    </td>
                </tr>
                <tr>
                    <td>�ҏW��<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlUseEdit" name="ddlUseEdit" datafld="flg_edit" style="width:150px;">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>�O���[�v</td>
                    <td>
                        <select id="ddlGroup" name="ddlGroup" datafld="cd_group" style="width:400px;">
                        </select>
                    </td>
                </tr>
            </table>

            <!-- CSV̧���߽(Servlet�ł̎�M�p) -->
            <input type="hidden" id="strFilePath" name="strFilePath" value="">
            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
        </form>

        <input type="hidden" id="hidGamenId" name="hidGamenId" value="">
        <input type="hidden" id="hidKinoId" name="hidKinoId" value="">
    </body>
</html>
