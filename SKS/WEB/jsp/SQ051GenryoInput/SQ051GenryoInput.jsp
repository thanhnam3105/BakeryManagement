<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���͒l���͉��                                                    -->
<!-- �쐬�ҁFTT.Jinbo                                                                -->
<!-- �쐬���F2009/04/03                                                              -->
<!-- �T�v  �F�����f�[�^�̓o�^�A�X�V���s���B                                          -->
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
        <script type="text/javascript" src="include/SQ051GenryoInput.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document��` -->
        <xml id="xmlJSP0510"></xml>
        <xml id="xmlJSP0520"></xml>
        <xml id="xmlJ010"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA380I" src="../../model/SA380I.xml"></xml>
        <xml id="xmlSA400I" src="../../model/SA400I.xml"></xml>
        <xml id="xmlSA410I" src="../../model/SA410I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA380O"></xml>
        <xml id="xmlSA400O"></xml>
        <xml id="xmlSA410O"></xml>
        <xml id="xmlRESULT"></xml>

<!-- 20160610  KPX@1502111_No.5 ADD start �A�g�ݒ荀�ڒǉ� -->
        <xml id="xmlJW821"></xml>
        <xml id="xmlFGEN2260I" src="../../model/FGEN2260I.xml"></xml>
        <xml id="xmlFGEN2270I" src="../../model/FGEN2270I.xml"></xml>
        <xml id="xmlFGEN2260O"></xml>
        <xml id="xmlFGEN2270O"></xml>
<!-- 20160610  KPX@1502111_No.5 ADD end �A�g�ݒ荀�ڒǉ� -->

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="18%" class="title">�����m�F</td>
                    <!-- ���[�U�[��� -->
                    <td width="82%"><div id="divUserInfo"></div></td>
                </tr>
            </table>

            <br>

            <!-- ���́E�I�� -->
            <table width="750" datasrc="#xmlSA380I" datafld="rec">
                <tr>
                    <td class="td_head" width="120">���<font color="red">�i�K�{�j</font></td>
                    <td>
                        <select id="ddlKaisha" id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:200px;" tabindex="6">
                            <option value=""></option>
                        </select>
                    </td>
                </tr>

<!-- 20160610  KPX@1502111_No.5 ADD start �A�g�ݒ荀�ڒǉ� -->
                <tr>
                    <td class="td_head" width="120">����m��</td>
                    <td width="450">
                        <input type="text" class="disb_text" id="txtShainCd" name="txtShainCd" readonly datafld="cd_shain" maxlength="" value="" style="width:100px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtNen" name="txtNen" readonly datafld="nen" maxlength="" value="" style="width:50px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtOiNo" name="txtOiNo" readonly datafld="no_oi" maxlength="" value="" style="width:50px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtEdaNo" name="txtEdaNo" readonly datafld="no_eda" maxlength="" value="" style="width:50px;" tabindex="-1">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">���얼</td>
                    <td>
                        <input type="text" class="disb_text" id="txtHinNm" name="txtHinNm" readonly  datafld="nm_hin" maxlength="" value="" style="width:600px;" tabindex="-1">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�T���v���m��</td>
                    <td>
                        <input type="text" class="disb_text" id="txtSampleNo" name="txtSampleNo" readonly  datafld="nm_sample" maxlength="" value="" style="width:200px;" tabindex="-1">
                    </td>
                </tr>
<!-- 20160607  KPX@1502111_No.5 ADD end �A�g�ݒ荀�ڒǉ� -->

                <tr>
                    <td class="td_head" width="120">�����R�[�h</td>
                    <td>
                        <input type="text" class="disb_text" id="txtGenryoCd" name="txtGenryoCd" datafld="cd_genryo" maxlength="11" value="" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">������<font color="red">�i�K�{�j</font></td>
                    <td>
                        <input type="text" class="act_text" id="txtGenryoName" name="txtGenryoName" datafld="nm_genryo" maxlength="60" value="" style="width:600px;">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�|�_(%)</td>
                    <td>
                        <span class="ninput" format="3,2" required="true" defaultfocus="false" id="em_Sakusan">
                        <input type="text" class="disb_text" id="txtSakusan" name="txtSakusan" datafld="ritu_sakusan" value="" style="width:200px;text-align:right;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�H��(%)</td>
                    <td>
                        <span class="ninput" format="3,2" required="true" defaultfocus="false" id="em_Shokuen">
                        <input type="text" class="disb_text" id="txtShokuen" name="txtShokuen" datafld="ritu_shokuen" value="" style="width:200px;text-align:right;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">���_(%)</td>
                    <td>
                        <span class="ninput" format="3,2" required="true" defaultfocus="false" id="em_Sosan">
                        <input type="text" class="disb_text" id="txtSosan" name="txtSosan" datafld="ritu_sousan" value="" style="width:200px;text-align:right;">
                        </span>
                    </td>
                </tr>
                <!-- ADD start QP@20505 No.24 20121005 -->
                <tr>
                    <td class="td_head" width="120">�l�r�f(%)</td>
                    <td>
                        <span class="ninput" format="3,2" required="true" defaultfocus="false" id="em_Msg">
                        <input type="text" class="disb_text" id="txtMsg" name="txtMsg" datafld="ritu_msg" value="" style="width:200px;text-align:right;">
                        </span>
                    </td>
                </tr>
                <!-- ADD end QP@20505 No.24 20121005 -->
                <tr>
                    <td class="td_head" width="120">���ܗL��(%)</td>
                    <td>
                        <span class="ninput" format="3,2" required="true" defaultfocus="false" id="em_Ganyu">
                        <input type="text" class="disb_text" id="txtGanyu" name="txtGanyu" datafld="ritu_abura" value="" style="width:200px;text-align:right;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�\����</td>
                    <td>
                        <textarea class="act_area" id="txtHyojian" name="txtHyojian" datafld="hyojian" cols="120" rows="4"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�Y����</td>
                    <td>
                        <textarea class="act_area" id="txtTenkabutu" name="txtTenkabutu" datafld="tenkabutu" cols="120" rows="4"></textarea>
                    </td>
                </tr>
            </table>

            <br>

            <table width="650" datasrc="#xmlSA380I" datafld="rec">
                <tr>
                    <td class="td_head" width="120">�h�{�v�Z�H�i�ԍ�</td>
                    <td>
                        <input type="text" class="disb_text" id="txtEiyoNo" name="txtEiyoNo" datafld="no_eiyo1" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtEiyoNo" name="txtEiyoNo" datafld="no_eiyo2" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtEiyoNo" name="txtEiyoNo" datafld="no_eiyo3" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtEiyoNo" name="txtEiyoNo" datafld="no_eiyo4" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtEiyoNo" name="txtEiyoNo" datafld="no_eiyo5" maxlength="10" value="" style="width:100px;">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">����(%)</td>
                    <td>
                        <input type="text" class="disb_text" id="txtWariai" name="txtWariai" datafld="wariai1" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtWariai" name="txtWariai" datafld="wariai2" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtWariai" name="txtWariai" datafld="wariai3" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtWariai" name="txtWariai" datafld="wariai4" maxlength="10" value="" style="width:100px;">
                    </td>
                    <td>
                        <input type="text" class="disb_text" id="txtWariai" name="txtWariai" datafld="wariai5" maxlength="10" value="" style="width:100px;">
                    </td>
                </tr>
            </table>

            <br>

            <table width="750" datasrc="#xmlSA380I" datafld="rec">
                <tr>
                    <td class="td_head" width="120">����</td>
                    <td colspan="3">
                        <textarea class="act_area" id="txtMemo" name="txtMemo" datafld="memo" cols="120" rows="4"></textarea>
                    </td>
                </tr>
            </table>

            <table width="750" datasrc="#xmlSA380I" datafld="rec">
                <tr>
                    <td class="td_head" width="120">���͓�</td>
                    <td width="160">
                        <!--<input type="text" class="disb_text" id="txtInputDt" name="txtInputDt" datafld="dt_toroku" value="" style="width:130px;">-->
                        <input type="text" class="disb_text" id="txtInputDt" name="txtInputDt" datafld="dt_koshin" value="" style="width:130px;">
                    </td>
                    <td class="td_head" width="120">���͎�</td>
                    <td>
                        <!--<input type="text" class="act_text" id="txtInputName" name="txtInputName" value="" style="width:300px;">-->
                        <input type="text" class="act_text" id="txtInputName" name="txtInputName" datafld="nm_koshin" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">���͏��m�F</td>
                    <td width="160">
                        �m�F�@<input type="checkbox" id="chkKakunin" name="chkKakunin" onClick="funChkKakunin(this);">
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�m�F��</td>
                    <td width="160">
                        <input type="text" class="disb_text" id="txtCheckDt" name="txtCheckDt" datafld="dt_kakunin" value="" style="width:130px;">
                    </td>
                    <td class="td_head" width="120">�m�F��</td>
                    <td>
                        <input type="text" class="act_text" id="txtCheckName" name="txtCheckName" value="" style="width:300px;">
                    </td>
                </tr>
                <tr>
                    <td class="td_head" width="120">�p�~��</td>
                    <td width="160">
                        �p�~�@<input type="checkbox" id="chkHaishi" name="chkHaishi">
                    </td>
                    <td class="td_head" width="120">�m��R�[�h</td>
                    <td>
                        <span class="cinput" format="00000000000" required="true" defaultfocus="false" id="em_KakuteiCd">
                        <input type="text" class="disb_text" id="txtKakuteiCd" name="txtKakuteiCd" datafld="cd_kakutei" maxlength="11" value="" style="width:300px;">
                        </span>
                    </td>
                </tr>
            </table>

            <br>

            <table width="750" align="center">
                <tr>
                    <td align="right">
                        <input type="button" class="normalbutton" id="btnEdit" name="btnEdit" value="�o�^" style="height:32px;" onClick="funEdit();">
                        &nbsp;
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" style="height:32px;" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <input type="hidden" id="hidMode" name="hidMode" value="">
            <input type="hidden" id="hidKaishaCd" name="hidKaishaCd" value="">
            <input type="hidden" id="hidKojoCd" name="hidKojoCd" value="">
            <input type="hidden" id="hidGenryoCd" name="hidGenryoCd" value="">

<!-- 20160610  KPX@1502111_No.5 ADD start �V�K�����A�g���擾�p -->
            <input type="hidden" id="hidGenryoBushoCd" name="hidGenryoBushoCd" value="">
            <input type="hidden" id="hidShisakuSeq" name="hidShisakuSeq" value="">
<!-- 20160610  KPX@1502111_No.5 ADD end  -->

<!--add start------------------------------------------------------->
<!--QP@00412_�V�T�N�C�b�N���� No.13-->
            <!--�ޔ�p-->
            <input type="hidden" id="hidGenryoName" name="hidGenryoName" value="">
            <input type="hidden" id="hidSakusan" name="hidSakusan" value="">
            <input type="hidden" id="hidShokuen" name="hidShokuen" value="">
            <input type="hidden" id="hidSosan" name="hidSosan" value="">
            <input type="hidden" id="hidMsg" name="hidMsg" value="">		<!-- ADD start QP@20505 No.24 20121005 -->
            <input type="hidden" id="hidGanyu" name="hidGanyu" value="">
            <input type="hidden" id="hidHyojian" name="hidHyojian" value="">
            <input type="hidden" id="hidTenkabutu" name="hidTenkabutu" value="">
            <input type="hidden" id="hidEiyoNo1" name="hidEiyoNo1" value="">
            <input type="hidden" id="hidEiyoNo2" name="hidEiyoNo2" value="">
            <input type="hidden" id="hidEiyoNo3" name="hidEiyoNo3" value="">
            <input type="hidden" id="hidEiyoNo4" name="hidEiyoNo4" value="">
            <input type="hidden" id="hidEiyoNo5" name="hidEiyoNo5" value="">
            <input type="hidden" id="hidWariai1" name="hidWariai1" value="">
            <input type="hidden" id="hidWariai2" name="hidWariai2" value="">
            <input type="hidden" id="hidWariai3" name="hidWariai3" value="">
            <input type="hidden" id="hidWariai4" name="hidWariai4" value="">
            <input type="hidden" id="hidWariai5" name="hidWariai5" value="">
            <input type="hidden" id="hidMemo" name="hidMemo" value="">
            <input type="hidden" id="hidKakuteiCd" name="hidKakuteiCd" value="">
            <!--�m�F���-->
            <input type="hidden" id="hidKakunin" name="hidKakunin" value="">
            <input type="hidden" id="hidCheckDt" name="hidCheckDt" value="">
            <input type="hidden" id="hidCheckName" name="hidCheckName" value="">
<!--add end------------------------------------------------------->
        </form>
    </body>
</html>
