<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�������Z��ʁi���ׁj                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                             -->
<!-- �쐬���F2009/10/20                                                              -->
<!-- �T�v  �F�������Z��ʂ̖��ו��t���[��                                                                        -->
<!------------------------------------------------------------------------------------->
<html>

	<script type="text/javascript">
    <!--//
    //===================================================================================
    // �X�N���[���ړ�����
    // �쐬�ҁFH.Shima
    // �쐬���F2013/07/02
    // ����  �F�Ȃ�
    // �߂�l�F�Ȃ�
    // �T�v  �F�w�b�_�[�̕\�����@��DIV�̑��Έʒu�ŕς���
    //===================================================================================
        function Scroll1() {
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList3").scrollLeft = document.getElementById("sclList4").scrollLeft;
        }
        function Scroll2() {
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList4").scrollLeft = document.getElementById("sclList3").scrollLeft
        }
    -->
    </script>

	<head>
        <title>�V�T�N�C�b�N�V�X�e�� �������Z���� �������Z���</title>
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
        <script type="text/javascript" src="include/SQ160GenkaShisan.js"></script>

        <script type="text/javascript" src="include/js/prototype.js" charset="shift_jis"></script>
		<script type="text/javascript" src="include/js/effects.js" charset="shift_jis"></script>
		<script type="text/javascript" src="include/js/window.js" charset="shift_jis"></script>


        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">


        <link rel="stylesheet" href="include/css/default.css" type="text/css" media="all">
		<link rel="stylesheet" href="include/css/alphacube.css" type="text/css" media="all">
		<link rel="stylesheet" href="include/css/main.css" type="text/css" media="all">


        <style>

           a:hover {
              background:#ffffff; text-decoration:none;
           } /*BG color is a must for IE6*/

           a.tooltip span {
              display:none; padding:2px 3px;margin-left:-100px;margin-top:10px;
           }

           a.tooltip:hover span{
              display:inline; position:absolute; background:#ffffff; border:1px solid #cccccc; color:#6c6c6c;
           }

        </style>

        <!--  ���ރe�[�u���s�N���b�N -->
        <script for="tblList4" event="onclick" language="JavaScript">
            //�I���s�̔w�i�F��ύX
            funChangeSelectRowColor2();
        </script>

    </head>

    <body class="pfcancel" onLoad="funLoad_dtl();" tabindex="-1">

        <!-- XML Document��` -->
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>

        <xml id="xmlRESULT"></xml>

        <xml id="xmlRGEN0011"></xml>
        <xml id="xmlFGEN0011I" src="../../model/FGEN0011I.xml"></xml>
        <xml id="xmlFGEN0011O"></xml>

        <xml id="xmlRGEN0012"></xml>
        <xml id="xmlFGEN0012I" src="../../model/FGEN0012I.xml"></xml>
        <xml id="xmlFGEN0012O"></xml>

        <xml id="xmlRGEN0013"></xml>
        <xml id="xmlFGEN0013I" src="../../model/FGEN0013I.xml"></xml>
        <xml id="xmlFGEN0013O"></xml>

        <xml id="xmlRGEN0041"></xml>
        <xml id="xmlFGEN0040I" src="../../model/FGEN0040I.xml"></xml>
        <xml id="xmlFGEN0040O"></xml>

        <xml id="xmlRGEN1020"></xml>
        <xml id="xmlFGEN1020I" src="../../model/FGEN1020I.xml"></xml>
        <xml id="xmlFGEN1020O"></xml>

        <xml id="xmlRGEN0020"></xml>
        <xml id="xmlFGEN0060I" src="../../model/FGEN0060I.xml"></xml>
        <xml id="xmlFGEN0060O"></xml>

        <xml id="xmlRGEN0070"></xml>
        <xml id="xmlFGEN0070I" src="../../model/FGEN0070I.xml"></xml>
        <xml id="xmlFGEN0070O"></xml>

        <xml id="xmlRGEN0080"></xml>
        <xml id="xmlFGEN0080I" src="../../model/FGEN0080I.xml"></xml>
        <xml id="xmlFGEN0080O"></xml>

        <form name="frm00" id="frm00" method="post">

            <!--�����{���-->
            <a name="lnkKihon" />
            <table border="0" cellspacing="0" width="100%">
                <!--�^�C�g���A�y�[�W�������N-->
                <tr>
                    <td>
                        �y��{���z
                    </td>
                    <td align="right" height="10px">
                        <a href="GenkaShisan_dtl_Eigyo.jsp#lnkShisan"  tabindex="1">���Z����</a>
                    </td>
                </tr>
            </table>
            <hr>

            <!-- DEL 2013/7/2 shima�yQP@30151�zNo.37 start -->
            <!--
            <table border="0" width="975px" height="500px">
                <tr><td align="left" valign="top" width="370">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;">
                        <!-- ����\�� -->
                        <!--
                        <tr>
                            <!-- 1�s�� -->
                            <!--
                            <td class="td_head" rowspan="5" style="width:18px;writing-mode:tb-rl;">����\</td>
                            <td class="td_head" style="width:150px;" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������S���O���[�v
                            </td>
                            <td width="200px" height="19px">
                                <input type="text" id="txtGroup" name="txtGroup" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������S���`�[��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ꊇ�\��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���[�U�[
                            </td>
                            <td height="19px">
                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �p�r
                            </td>
                            <td height="19px"><input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr>
                        <!-- �������Z������ -->
                        <!--
                        <tr>
                            <!-- 1�s�� -->
                            <!--
                            <td class="td_head" rowspan="22" style="width:18px;writing-mode:tb-rl;">�������Z����</td>
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������
                            </td>
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtSeizoKaisha" name="txtSeizoKaisha" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �����H��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSeizoKojo" name="txtSeizoKojo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �S���c��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
            <!-- �yQP@10713�z20111031 hagiwara mod start -->
            <!--
                                �������@
                            </td>
                            <td height="19px"><input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �[�U���@
                            </td>
                            <td height="19px"><input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 6�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �E�ە��@
                            </td>
                            <td height="19px"><input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
            -->
            <!--
        	                </td>
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="hidden" id="txtSeizohoho" name="txtSeizohoho" value="" />
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            </td>
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="hidden" id="txtJutenhoho" name="txtJutenhoho" value="" />
                            </td>
                        </tr><tr>
                            <!-- 6�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            </td>
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="text" id="txtSakinhoho" name="txtSakinhoho" value="" />
                            </td>
              <!-- �yQP@10713�z20111031 hagiwara mod end   -->
              <!--
                        </tr><tr>
                            <!-- 7�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e��E���
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 8�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e�ʁi���l���́j
                            </td>
                            <td height="19px">
                            	<!-- �yQP@10713�z -->
                            	<!--
                            	<input type="text" id="txtYouryo" name="txtYouryo" onchange="setFg_Henkou();setKanma(this);" onblur="" style="width:112px;" class="table_text_disb"  value="" tabindex="2" />
                            	<!-- <input type="text" id="txtYouryo" name="txtYouryo" onchange="setFg_Henkou();" onblur="setKanma(this)" style="width:112px;" class="table_text_disb"  value="" tabindex="2" /> -->
                            	<!--
                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���萔�i���l���́j��
                            </td>
                            <td bgcolor="" height="19px">
                            	<!-- �yQP@10713�z -->
                            	<!--
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_disb" onchange="setFg_Henkou();" onblur="setKanma(this);fun_Nisugataset();" value="" tabindex="3" />
                                <!-- <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_disb" onchange="setFg_Henkou();" onblur="setKanma(this)" value="" tabindex="3" /> -->
                                <!--
                            </td>
                        </tr><tr>
                            <!-- 10�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �׎p
                            </td>
                            <!-- �yQP@10713�z -->
                            <!-- <td bgcolor="" height="19px"> -->
                            <!--
                            <td  height="19px">
                                <!--  <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" onchange="setFg_Henkou();" class="table_text_act" value="" tabindex="4" /> -->
                                <!--
                            	<input type="text" id="txtNisugata" name="txtNisugata" class="table_text_act" value="" tabindex="4" />

                            </td>
                        </tr><tr>
                            <!-- 11�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �戵���x
                            </td>
                            <td height="19px"><input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 12�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ܖ�����
                            </td>
                            <td height="19px"><input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 13�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]������
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtGenkaKibo" name="txtGenkaKibo" style="width:112px;height:20px" class="table_text_disb" onchange="setFg_Henkou();" onblur="setKanma(this);" value="" tabindex="5" />
                                <select name="ddlGenkaTani" id="ddlGenkaTani" onChange="setFg_Henkou();baikaTaniSetting();" style="background-color:#ffff88;width:82px;height:16px;" tabindex="6" >
                                </select>
                            </td>
                        </tr><tr>
                            <!-- 14�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            	<!-- �yQP@10713�z -->
                            	<!--
                                ��]����
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtBaikaKibo" name="txtBaikaKibo" onchange="setFg_Henkou();" onblur="setKanma(this);" style="width:112px;" class="table_text_disb"  value="" tabindex="7" />
                                <input type="text" id="txtBaikaTani" name="txtBaikaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 15�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �z�蕨��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSoteiButuryo" name="txtSoteiButuryo" onchange="setFg_Henkou();" style="ime-mode:active;" class="table_text_act" value="" tabindex="8" />
                            </td>
                        </tr><tr>
                            <!-- 16�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ����[�i����
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHatubaiJiki"style="ime-mode:active;" onchange="setFg_Henkou();" name="txtHatubaiJiki" class="table_text_act" value="" onblur="setNenGetu(this)" tabindex="9" />
                            </td>

                        </tr><tr>
                            <!-- 16�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔�����
                            </td>
                            <td bgcolor="" height="19px">
                            	<select name="ddlHanbaiKikan_t" id="ddlHanbaiKikan_t" onChange="setFg_Henkou();changeKikan();" style="background-color:#ffff88;width:74px;height:16px;" tabindex="10" >
                                </select>
                                <input type="text" id="txtHanbaiKikan_s" name="txtHanbaiKikan_s" style="width:30px;text-align:right;" class="table_text_disb" onchange="setFg_Henkou();"  value="" tabindex="11" />
                                <select name="ddlHanbaiKikan_k" id="ddlHanbaiKikan_k" onChange="setFg_Henkou()" style="background-color:#ffff88;width:74px;height:16px;" tabindex="11" >
                                </select>
                            </td>

                        </tr><tr>
                            <!-- 17�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�攄��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuUriage" style="ime-mode:active;" onchange="setFg_Henkou();" name="txtKeikakuUriage" class="table_text_act" value="" tabindex="12" />
                            </td>
                        </tr><tr>
                            <!-- 18�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�旘�v
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuRieki" style="ime-mode:active;" name="txtKeikakuRieki" onchange="setFg_Henkou();" class="table_text_act" value="" tabindex="13" />
                            </td>
                        </tr><tr>
                            <!-- 19�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoUriage" style="ime-mode:active;" name="txtHanbaigoUriage" onchange="setFg_Henkou();" class="table_text_act" value="" tabindex="14" />
                            </td>
                        </tr><tr>
                            <!-- 20�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoRieki" style="ime-mode:active;" name="txtHanbaigoRieki" onchange="setFg_Henkou();" class="table_text_act" value="" tabindex="15" />
                            </td>
                        </tr><tr>
                            <!-- 21�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������b�g
                            </td>
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtSeizoRot" name="txtSeizoRot" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr>
                    </table>
                     -->
        	<!-- DEL 2013/7/2 shima�yQP@30151�zNo.37 end -->

            <!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 start -->
			<table border="0" width="985px" height="530px" cellspacing="0" cellpadding="0" style="word-break:break-all;" >
            <tr>
            	<td align="left" valign="top" width="170" rowspan="2">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;border-right-style:none;">

						<tr>
                           <!-- 1�s�� -->
                            <td class="td_head" rowspan="5" style="width:20px;writing-mode:tb-rl;">����\</td>
                            <td class="td_head" style="width:150px;" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������S���O���[�v
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������S���`�[��
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ꊇ�\��
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���[�U�[
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �p�r
                            </td>
                        </tr>
                        <!-- �������Z������ -->
                        <tr>
                            <!-- 1�s�� -->
                            <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1,16 start -->
                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                             <td class="td_head" rowspan="22" style="width:18px;writing-mode:tb-rl;">�������Z����</td> -->
<!--                             <td class="td_head" rowspan="24" style="width:18px;writing-mode:tb-rl;">�������Z����</td> -->
                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                            <td class="td_head" rowspan="26" style="width:18px;writing-mode:tb-rl;">�������Z����</td>
                            <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1,16 end -->

                        <!-- ADD 2013/10/22 QP@30154 okano start -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̐Ӊ��
                            </td>
                        </tr><tr>
                        <!-- ADD 2013/10/22 QP@30154 okano end -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �����H��
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �S���c��
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            </td>
                        </tr><tr>
                            <!-- 6�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                            </td>
                        </tr><tr>
                            <!-- 7�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e��E���
                            </td>
                        </tr><tr>
                            <!-- 8�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e�ʁi���l���́j
                            </td>
                        </tr><tr>
                            <!-- 9�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���萔�i���l���́j��
                            </td>
                        </tr><tr>
                            <!-- 10�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �׎p
                            </td>
                        </tr><tr>
                            <!-- 11�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �戵���x
                            </td>
                        </tr><tr>
                            <!-- 12�s�� -->
                            <!-- MOD 2013/11/20 QP@30154 okano start -->
<!--                             <td class="td_head" height="19px"> -->
                            <td class="td_head" height="20px">
                            <!-- MOD 2013/11/20 QP@30154 okano end -->
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ܖ�����
                            </td>
                        </tr><tr>
                            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1,16 start -->
                            <!-- 13�s�� ����`�F�b�N -->
                            <td class="td_head" height="20px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    ���
                            </td>
                        </tr><tr>
                            <!-- 14�s�� -->
                            <td class="td_head" height="20px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    �T���v��No
                            </td>
                        </tr><tr>
                            <!-- 14�s�� -->
                            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.1,16 end -->
                            <!-- MOD 2013/11/20 QP@30154 okano start -->
<!--                             <td class="td_head" height="19px"> -->
                            <td class="td_head" height="20px">
                            <!-- MOD 2013/11/20 QP@30154 okano end -->
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]������
                            </td>
                        </tr><tr>
                            <!-- 15�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]����
                            </td>
                        </tr><tr>
                            <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20,27 start -->
                            <!-- 16�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔�����
                            </td>
                        </tr><tr>
                            <!-- 17�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �z�蕨��
                            </td>
                        </tr><tr>
                        <!-- ADD 2013/9/6 okano�yQP@30151�zNo.30 start -->
                            <!-- 18�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �z�蕨�ʔ��l
                            </td>
                        </tr><tr>
                        <!-- ADD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                            <!-- 19�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ����[�i����
                            </td>

<!--                         </tr><tr>
                            16�s��
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔�����
                            </td>
 -->
                        </tr><tr>
                            <!-- 20�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <!-- �v�攄�� -->
                                �v�攄��^�N
                            </td>
                        </tr><tr>
                            <!-- 21�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <!-- �v�旘�v -->
                                �v�旘�v�^�N
                            </td>
                            <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.20,27 end -->
                        </tr><tr>
                            <!-- 22�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                        </tr><tr>
                            <!-- 23�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                        </tr><tr>
                            <!-- 24�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������b�g
                            </td>
                        </tr>
                    </table>
                </td>

                <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                 <td align="left" valign="top" width="165px" > -->
                <td align="left" valign="top" width="220px" >
                <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                	<table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed; border-bottom-style:none;">
                        <!-- ����\�� -->
                        <tr>
                            <!-- 1�s�� -->
                            <!-- <td width="200px" height="19px"> -->
                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                             <td width="160px" height="19px"> -->
                            <td width="215px" height="19px">
                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                                <input type="text" id="txtGroup" name="txtGroup" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <td height="19px">
                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <td height="19px">
                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td height="19px">
                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td height="19px"><input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr>
                        <!-- �������Z������ -->
                        <tr>
                        <!-- ADD 2013/10/22 QP@30154 okano start -->
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanseki" name="txtHanseki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                        <!-- ADD 2013/10/22 QP@30154 okano end -->
                            <!-- 1�s�� -->
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtSeizoKaisha" name="txtSeizoKaisha" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSeizoKojo" name="txtSeizoKojo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <td height="19px">
                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="hidden" id="txtSeizohoho" name="txtSeizohoho" value="" />
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="hidden" id="txtJutenhoho" name="txtJutenhoho" value="" />
                            </td>
                        </tr><tr>
                            <!-- 6�s�� -->
                            <td height="19px">
                            	<input type="text" class="table_text_view" readonly tabindex="-1" />
                            	<input type="text" id="txtSakinhoho" name="txtSakinhoho" value="" />
                            </td>
                        </tr><tr>
                            <!-- 7�s�� -->
                            <td height="19px">
                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 8�s�� -->
                            <td height="19px">
                            	<input type="text" id="txtYouryo" name="txtYouryo" onchange="setFg_Henkou();setKanma(this);" onblur="" style="width:70px;" class="table_text_disb"  value="" tabindex="2" />
                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:80px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9�s�� -->
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_disb" onchange="setFg_Henkou();" onblur="setKanma(this);fun_Nisugataset();" value="" tabindex="3" />
                            </td>
                        </tr><tr>
                            <!-- 10�s�� -->
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtNisugata" name="txtNisugata" class="table_text_act" value="" tabindex="4" />
                            </td>
                        </tr><tr>
                            <!-- 11�s�� -->
                            <td height="19px">
                            	<input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 12�s�� -->
                            <!-- MOD 2015/09/07 TT.Kitazawa start -->
                            <!-- <td height="19px"> -->
                            <td height="19px" style="border-bottom-style:none;">
                            <!-- MOD 2015/09/07 TT.Kitazawa end -->
                            	<input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr>
                	</table>
                </td>
                <!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 end -->

                <!--��������-->
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                <!-- <td align="left" valign="top"> -->
                <td align="left" valign="top" width="600">
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="99%">
                        <tr>
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                            <!-- <td class="td_head" width="565px">�������Z�����i�c�ƘA���p�j</td> -->
                            <!-- MOD 2013/11/15 okano�yQP@30154�zstart -->
<!--                             	<td class="td_head" width="470px">�������Z�����i�c�ƘA���p�j</td> -->
                            <td class="td_head" width="423px">�������Z�����i�c�ƘA���p�j</td>
                            <!-- MOD 2013/11/15 okano�yQP@30154�zend -->
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                        </tr>
                        <tr>
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                            <!-- <td style="width:565px;height:96%;"> -->
                            <!-- MOD 2013/11/15 okano�yQP@30154�zstart -->
<!--                             	<td style="width:470px;height:96%;"> -->
                            <td style="width:423px;height:96%;">
                            <!-- MOD 2013/11/15 okano�yQP@30154�zend -->
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                                <textarea id="txtGenkaMemoEigyo" name="txtGenkaMemoEigyo" onchange="setFg_Henkou();" class="table_textarea" style="background-color:#ffff88;" tabindex="19" >
                                </textarea>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>

            <!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 start -->
            <tr>
            	<td colspan="2">
            	    <!-- ��{���X�N���[���� -->
                    <!-- <div class="scroll_genka" id="sclList4" style="width:482px; overflow-x:scroll;overflow-y:hidden;" rowSelect="true"  > -->
                    <!-- MOD 2015/09/07 TT.Kitazawa start -->
                    <!-- <div class="scroll_genka" id="sclList4" style="width:647px; overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll1();" > -->
                    <div class="scroll_Kihon" id="sclList4" style="width:647px; overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll1();" >
		            <!-- MOD 2015/09/07 TT.Kitazawa end -->
		                <div id="divKihonSub" ></div>
	                </div>
	            </td>
            </tr>
            <!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 end -->

            </table>


            <!-- ADD 20160622  KPX@502111_No.10 start -->
             <table border="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td align="right">
                        <input class="normalbutton" type="button" id="btnSampleCopy" name="btnSampleCopy" onClick="funSampleCopy();" value="�T���v���R�s�[" tabindex="20" />
                    </td>
                </tr>
            </table>
            <!-- ADD 20160622  KPX@502111_No.10 end -->


            <br/><!-- <br/> -->
            <!--���Z���ڏ��-->
            <a name="lnkShisan" />
            <table border="0" cellspacing="0" border="0" width="100%">
                <!--�^�C�g���A�y�[�W�������N-->
                <tr>
                    <td>
                         �y���Z���ځz
                    </td>
                    <td align="right">
                        <a href="GenkaShisan_dtl_Eigyo.jsp#lnkKihon" tabindex="20">��{���</a>
                    </td>
                </tr>
            </table>
            <hr>
            <table border="0" width="970px" height="400px">
                <tr>
                <!--�ꗗ-->
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                <!-- <td valign="top" align="left" width="724"> -->
                <td valign="top" align="left" width="724" style="padding-left: 22px">
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                    <table cellpadding="0" cellspacing="0" border="0"><tr>
                        <!-- �����w�b�_�[ -->
                        <td valign="top">
                            <div class="scroll_genka" id="sclList3_2" style="width:146px;overflow:hidden;" rowSelect="false">
                            <table class="detail" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
                                <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;�T���v��No</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;���Z��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�P�[�X��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�s��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;����</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�e���i���j</td></tr>
                            </table>
                            </div>
                        </td>
                        <!-- �E���J���� -->
                        <td>
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                        	<!-- <div class="scroll_genka" id="sclList3" style="width:800px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" > -->
                            <div class="scroll_genka" id="sclList3" style="width:648px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();" >
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->

                                <div id="divGenryoShisan">
                                </div>
                            </div>
                        </td>
                    </tr>
                    <!-- ADD 2013/6/17 okano�yQP@30151�zNo.31 start -->
                    <tr height="50" valign="bottom">
                    	<td></td>
                    	<td>
                    		<font color="red">
                    			�����������オ�菤�i�܂ŁA�L���ȉ��i�ł��B<br>
                    			���V�N�x�����グ�̏ꍇ�́A���i�ύX����������\��������܂��̂ŁA�����ӂ��������B<br>
                    			���z�蕨�ʁE���������E�������ԂɕύX������ꍇ�́A���A���肢�܂��B
                    		</font>
                    	</td>
                    </tr>
                    <!-- ADD 2013/6/17 okano�yQP@30151�zNo.31 end -->
                    </table>
                </td></tr>

            </table>

            <!-- ���Z�����\���C�x���g�{�^�� -->
            <input type="button" value="" name="BtnEveGenryo" id="BtnEveGenryo" onclick="funGenryoHyoji()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- ��� -->
            <input type="hidden" value="" name="hdnKaisha" id="hdnKaisha">
            <!-- �H�� -->
            <input type="hidden" value="" name="hdnKojo" id="hdnKojo">
            <!-- �����A���ރR�[�h���� -->
            <input type="hidden" value="" name="hdnCdketasu" id="hdnCdketasu">
            <!-- ������ -->
            <input type="button" value="" name="BtnEveStart" id="BtnEveStart" onclick="funShowRunMessage()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- �����I�� -->
            <input type="button" value="" name="BtnEveEnd" id="BtnEveEnd" onclick="funClearRunMessage()" style="width:0px;height:0px;" tabindex="-1" />


        </form>
    </body>
</html>
