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
    // �쐬�ҁFK.Katayama
    // �쐬���F2009/08/20
    // ����  �F�Ȃ�
    // �߂�l�F�Ȃ�
    // �T�v  �F�w�b�_�[�̕\�����@��DIV�̑��Έʒu�ŕς���
    //===================================================================================
        function Scroll1() {
            //Y�����̃X�N���[���ړ�
            document.getElementById("sclList1").scrollTop = document.getElementById("sclList2").scrollTop;
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList3").scrollLeft = document.getElementById("sclList2").scrollLeft;

            // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//��{���
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList4").scrollLeft = document.getElementById("sclList2").scrollLeft;
            // ADD 2013/7/2 shima�yQP@30151�zNo.37 end
        }
        function Scroll2() {
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList3").scrollLeft;

            // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//��{���
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList4").scrollLeft = document.getElementById("sclList3").scrollLeft;
            // ADD 2013/7/2 shima�yQP@30151�zNo.37 end
        }

        // ADD 2013/7/2 shima�yQP@30151�zNo.37 start
        function Scroll3() {
        	//�������
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList4").scrollLeft;
        	//�v�Z����
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList3").scrollLeft = document.getElementById("sclList4").scrollLeft;
        }
        // ADD 2013/7/2 shima�yQP@30151�zNo.37 end
    -->
    </script>

    <script type="text/javascript">
    <!--
		window.onunload = function() {
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			document.getElementById("divKihonSub").innerHTML = null;
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
			document.getElementById("divGenryo_Left").innerHTML = null;
			document.getElementById("divGenryo_Right").innerHTML = null;
			document.getElementById("divGenryoShisan").innerHTML = null;
			document.getElementById("divGenryoShizai").innerHTML = null;

			document.getElementById("xmlUSERINFO_I").src = null;
			document.getElementById("xmlUSERINFO_O").src = null;
			document.getElementById("xmlRESULT").src = null;

			document.getElementById("xmlRGEN0011").src = null;
			document.getElementById("xmlFGEN0011I").src = null;
			document.getElementById("xmlFGEN0011O").src = null;

			document.getElementById("xmlRGEN0012").src = null;
			document.getElementById("xmlFGEN0012I").src = null;
			document.getElementById("xmlFGEN0012O").src = null;

			document.getElementById("xmlRGEN0013").src = null;
			document.getElementById("xmlFGEN0013I").src = null;
			document.getElementById("xmlFGEN0013O").src = null;

			document.getElementById("xmlRGEN0041").src = null;
			document.getElementById("xmlFGEN0040I").src = null;
			document.getElementById("xmlFGEN0040O").src = null;

			document.getElementById("xmlRGEN1020").src = null;
			document.getElementById("xmlFGEN1020I").src = null;
			document.getElementById("xmlFGEN1020O").src = null;

			document.getElementById("xmlRGEN0020").src = null;
			document.getElementById("xmlFGEN0060I").src = null;
			document.getElementById("xmlFGEN0060O").src = null;

			document.getElementById("xmlRGEN0070").src = null;
			document.getElementById("xmlFGEN0070I").src = null;
			document.getElementById("xmlFGEN0070O").src = null;

			document.getElementById("xmlRGEN0080").src = null;
			document.getElementById("xmlFGEN0080I").src = null;
			document.getElementById("xmlFGEN0080O").src = null;

//add start 20140919
			document.getElementById("xmlRGEN2021").src = null;
			document.getElementById("xmlFGEN2021I").src = null;
			document.getElementById("xmlFGEN2021O").src = null;
//add end 20140919
//add start 20150722
			document.getElementById("xmlRGEN2022").src = null;
			document.getElementById("xmlFGEN2022I").src = null;
			document.getElementById("xmlFGEN2022O").src = null;
//add end 20150722
//2015/03/30 TT.Kitazawa�yQP@40812�zNo.12 ADD START
            document.getElementById("xmlRGEN0090").src = null;
            document.getElementById("xmlFGEN0090I").src = null;
            document.getElementById("xmlFGEN0090O").src = null;
//2015/03/30 TT.Kitazawa�yQP@40812�zNo.12 ADD END
//20160513  KPX@1600766 ADD start
            document.getElementById("xmlSA100O").src = null;
            document.getElementById("xmlSA100I").src = null;
            document.getElementById("xmlJSP0630").src = null;
//20160513  KPX@1600766 ADD end
//20160617  KPX@502111_No.5 ADD start
            document.getElementById("xmlRGEN2280").src = null;
            document.getElementById("xmlFGEN2280I").src = null;
            document.getElementById("xmlFGEN2280O").src = null;
            document.getElementById("xmlFGEN2280").src = null;
//20160617  KPX@502111_No.5 ADD end
		}
	// -->
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
        <script type="text/javascript" src="include/SQ110GenkaShisan.js"></script>


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
        <!--  <script for="tblList4" event="onclick" language="JavaScript">-->
        <!--      //�I���s�̔w�i�F��ύX-->
        <!--      funChangeSelectRowColor2();-->
        <!--  </script>-->

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

<!-- add start 20140919 -->
        <xml id="xmlRGEN2021"></xml>
        <xml id="xmlFGEN2021I" src="../../model/FGEN2021I.xml"></xml>
        <xml id="xmlFGEN2021O"></xml>
<!-- add end 20140919 -->
<!-- add start 20150722 -->
        <xml id="xmlRGEN2022"></xml>
        <xml id="xmlFGEN2022I" src="../../model/FGEN2022I.xml"></xml>
        <xml id="xmlFGEN2022O"></xml>
<!-- add end 20150722 -->

        <!--2015/03/30 TT.Kitazawa�yQP@40812�zNo.12 ADD START------------------------------>
        <xml id="xmlRGEN0090"></xml>
        <xml id="xmlFGEN0090I" src="../../model/FGEN0090I.xml"></xml>
        <xml id="xmlFGEN0090O"></xml>
        <!--2015/03/30 TT.Kitazawa�yQP@40812�zNo.12 ADD END-------------------------------->

<!-- 2016/04/2020160513  KPX@1600766 ADD start -->
        <xml id="xmlJSP0630"></xml>
        <xml id="xmlSA100I" src="../../model/SA100I.xml"></xml>
        <xml id="xmlSA100O"></xml>
<!-- 2016/04/2020160513  KPX@1600766 ADD end -->
<!-- 20160617  KPX@502111_No.5 ADD start -->
        <xml id="xmlRGEN2280"></xml>
        <xml id="xmlFGEN2280I" src="../../model/FGEN2280I.xml"></xml>
        <xml id="xmlFGEN2280O"></xml>
        <!-- �f�[�^�ޔ�p -->
        <xml id="xmlFGEN2280"></xml>
<!-- 20160617  KPX@502111_No.5 ADD end -->
<!-- 20170515 KPX@1700856 ADD start -->
        <xml id="xmlRGEN2290"></xml>
        <xml id="xmlFGEN2290I" src="../../model/FGEN2290I.xml"></xml>
        <xml id="xmlFGEN2290O"></xml>
<!-- 20170515 KPX@1700856 ADD end -->

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
                        <a href="GenkaShisan_dtl.jsp#lnkGenka"  tabindex="1">�������</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                         	<a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">���Z����</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">�v�Z����</a>
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai"  tabindex="1">���ޏ��</a>
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
                                <select name="ddlSeizoKaisya" id="ddlSeizoKaisya" onChange="setFg_saikeisan();funKojoChange()" style="width:200px;height:16px;" tabindex="1" />
                                </select>
                            </td>
                        </tr><tr>
                            <!-- 2�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �����H��
                            </td>
                            <td bgcolor="" height="19px">
                                <select name="ddlSeizoKojo" id="ddlSeizoKojo" style="width:120px;height:16px;" tabindex="2" >
                                </select>
                                <input type="button" class="normalbutton" style="width:70px;"  id="btnArigae" name="btnArigae" value="�H��ύX" onClick="funAraigae()" tabindex="2"/>
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
                                <input type="text" id="txtYouryo" name="txtYouryo" style="width:112px;" class="table_text_view" readonly  value="" tabindex="-1" />
                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:82px;" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���萔�i���l���́j��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_view" readonly   value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 10�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �׎p
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_view" readonly value="" tabindex="-1" />
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
                                <input type="text" id="txtGenkaKibo" name="txtGenkaKibo" style="width:112px;height:20px" class="table_text_view" readonly  value="" tabindex="-1" />
                                <input type="text" id="txtGenkaTani" name="txtGenkaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                                <input type="hidden" id="hdnGenkaTaniCd" name="hdnGenkaTaniCd" style="width:82px;" />
                            </td>
                        </tr><tr>
                            <!-- 14�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]����
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtBaikaKibo" name="txtBaikaKibo" style="width:112px;" class="table_text_view" readonly  value="" tabindex="-1" />
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
                                <input type="text" id="txtSoteiButuryo" style="ime-mode:active;" name="txtSoteiButuryo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 16�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ����[�i����
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHatubaiJiki"style="ime-mode:active;" name="txtHatubaiJiki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 16�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔�����
                            </td>
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtHanbaiKikan_t"  name="txtHanbaiKikan_t" style="width:74px;"class="table_text_view" readonly value="" tabindex="-1" />
                            	<input type="text" id="txtHanbaiKikan_s"  name="txtHanbaiKikan_s" style="width:30px;text-align:right;"class="table_text_view" readonly value="" tabindex="-1" />
                                <input type="text" id="txtHanbaiKikan_k" name="txtHanbaiKikan_k" style="width:74px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 17�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�攄��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuUriage" style="ime-mode:active;" name="txtKeikakuUriage" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 18�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�旘�v
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuRieki" style="ime-mode:active;" name="txtKeikakuRieki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>

                            <!-- 19�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoUriage" style="ime-mode:active;" name="txtHanbaigoUriage" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 20�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoRieki" style="ime-mode:active;" name="txtHanbaigoRieki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 21�s�� -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������b�g
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSeizoRot" style="ime-mode:active;" name="txtSeizoRot" class="table_text_act" value="" tabindex="14" />
                            </td>
                        </tr>
                    </table>
                </td>

                <!--��������-->
                <!--
                <td align="left" valign="top">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="100%">
                        <tr>
                            <td class="td_head" width="565px">�������Z����</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtGenkaMemo" name="txtGenkaMemo" class="table_textarea" style="background-color:#ffff88;" tabindex="15" >
                                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_head" width="565px">�������Z�����i�c�ƘA���p�j</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtGenkaMemoEigyo" name="txtGenkaMemoEigyo" class="table_textarea" style="background-color:#ffff88;" tabindex="16" >
                                </textarea>
                            </td>
                        </tr>

                    </table>
                </td>
                 -->
                <!-- DEL 2013/7/2 shima�yQP@30151�zNo.37 end -->

			<!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 start -->
            <table border="0" width="1130px" height="500px" cellspacing="0" cellpadding="0" style="word-break:break-all;" >
                <tr>
                	<!--��������-->
	                <td align="left" valign="top" width="435">
<!-- ADD 2013/07/19 �yQP@30151�zogawa �e�L�X�g�{�b�N�X���L�т錏 start -->
<!-- �C���O�\�[�X
	                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="515">
-->
<!-- �C����\�[�X -->
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.49 start -->
                        <!-- <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="515" style="word-break:break-all;word-wrap:break-word;"> -->
 	                    <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 start -->
 	                    <!-- <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="554" style="word-break:break-all;word-wrap:break-word;"> -->
                        <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="573" style="word-break:break-all;word-wrap:break-word;">
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.49 end -->
<!--  ADD 2013/07/19 �yQP@30151�zogawa end -->
                        <!-- MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 end -->

	                        <tr>
	                            <td class="td_head" width="425px">�������Z����</td>
	                        </tr>
	                        <tr>
	                            <td style="width:425px;height:49%;">
	                                <textarea id="txtGenkaMemo" name="txtGenkaMemo" class="table_textarea" style="background-color:#ffff88;" tabindex="15" >
	                                </textarea>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="td_head" width="425px">�������Z�����i�c�ƘA���p�j</td>
	                        </tr>
	                        <tr>
	                            <td style="width:425px;height:49%;">
	                                <textarea id="txtGenkaMemoEigyo" name="txtGenkaMemoEigyo" class="table_textarea" style="background-color:#ffff88;" tabindex="16" >
	                                </textarea>
	                            </td>
	                        </tr>
	                    </table>
	                </td>

                	<!-- <td align="left" valign="top" width="650"> -->
                	<td align="left" valign="top" width="165">
	                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;">
	                        <!-- ����\�� -->
	                        <tr>
	                            <!-- 1�s�� -->
	                            <td class="td_head" rowspan="5" style="width:18px;writing-mode:tb-rl;">����\</td>
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
	                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!-- 	                            <td class="td_head" rowspan="22" style="width:18px;writing-mode:tb-rl;">�������Z����</td> -->
<!-- 	                            <td class="td_head" rowspan="24" style="width:18px;writing-mode:tb-rl;">�������Z����</td> -->
 	                            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                                <td class="td_head" rowspan="25" style="width:18px;writing-mode:tb-rl;">�������Z����</td>
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
	                                �������@
	                            </td>
	                        </tr><tr>
	                            <!-- 5�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �[�U���@
	                            </td>
	                        </tr><tr>
	                            <!-- 6�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �E�ە��@
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
<!-- 	                            <td class="td_head" height="19px"> -->
	                            <td class="td_head" height="20px">
	                            <!-- MOD 2013/11/20 QP@30154 okano end -->
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �ܖ�����
	                            </td>
	                        </tr><tr>
                                <!-- 13�s�� -->
                            <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.16 start -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    �T���v��No
                                </td>
                            </tr><tr>
	                            <!-- 14�s�� -->
                            <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.16 end -->
	                            <td class="td_head" height="19px">
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
                            <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start -->
                                <!-- 16�s�� -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    �̔�����
                                </td>
                            </tr><tr>
                            <!-- ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 end -->
	                            <!-- 17�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �z�蕨��
	                            </td>
	                        </tr><tr>
                            <!-- ADD 2013/9/6 okano�yQP@30151�zNo.30 start -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    �z�蕨�ʔ��l
                                </td>
                            </tr><tr>
                            <!-- ADD 2013/9/6 okano�yQP@30151�zNo.30 end -->
	                            <!-- 16�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                ����[�i����
	                            </td>
                            <!-- DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start -->
<!-- 	                        </tr><tr>
	                            16�s��
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �̔�����
	                            </td>
 -->
                            <!-- DEL 2015/05/15 TT.Kitazawa�yQP@40812�zNo.20 start -->
	                        </tr><tr>
                            <!-- MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.27 end -->
	                            <!-- 17�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                <!-- �v�攄�� -->
                                    �v�攄��^�N
	                            </td>
	                        </tr><tr>
	                            <!-- 18�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                <!-- �v�旘�v -->
                                     �v�旘�v�^�N
	                            </td>
                            <!-- MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.27 end -->
	                        </tr><tr>
	                            <!-- 19�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;

	                            </td>
	                        </tr><tr>
	                            <!-- 20�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;

	                            </td>
	                        </tr><tr>
	                            <!-- 21�s�� -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                �������b�g
	                            </td>
	                        </tr>
	                    </table>
                    </td>
                	<td align="left" valign="top" width="250" >
                    	<!-- ��{�����e -->
	                    <table border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed; border-bottom-style:none;">
	                    	<tr>
	                            <!-- 1�s�� -->
	                            <td width="175px" height="19px">
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
	                            <td height="19px">
	                            	<input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr>
	                        <!-- �������Z������ -->
	                        <tr>
	                            <!-- 1�s�� -->
	                            <td bgcolor="" height="19px">
	                        <!-- ADD 2013/10/22 QP@30154 okano start -->
	                                <input type="text" id="txtHanseki" name="txtHanseki" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <td height="19px">
	                        <!-- ADD 2013/10/22 QP@30154 okano end -->
	                                <select name="ddlSeizoKaisya" id="ddlSeizoKaisya" onChange="setFg_saikeisan();funKojoChange()" style="width:170px;height:16px;" tabindex="1">
	                                </select>
	                            </td>
	                        </tr><tr>
	                            <!-- 2�s�� -->
	                            <td bgcolor="" height="19px">
	                                <select name="ddlSeizoKojo" id="ddlSeizoKojo" style="width:105px;height:16px;" tabindex="2" >
	                                </select>
	                                <input type="button" class="normalbutton" style="width:60px;" id="btnArigae" name="btnArigae" value="�H��ύX" onClick="funAraigae()" tabindex="2"/>
	                            </td>
	                        </tr><tr>
	                            <!-- 3�s�� -->
	                            <td height="19px">
	                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 4�s�� -->
	                            <td height="19px">
	                            	<input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 5�s�� -->
	                            <td height="19px">
	                            	<input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 6�s�� -->
	                            <td height="19px">
	                            	<input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 7�s�� -->
	                            <td height="19px">
	                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 8�s�� -->
	                            <td height="19px">
	                                <input type="text" id="txtYouryo" name="txtYouryo" style="width:70px;" class="table_text_view" readonly  value="" tabindex="-1" />
	                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:80px;" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 9�s�� -->
	                            <td bgcolor="" height="19px">
	                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_view" readonly   value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 10�s�� -->
	                            <td bgcolor="" height="19px">
	                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_view" readonly value="" tabindex="-1" />
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

                    	<!-- ��{���X�N���[���� -->
                    	<div class="scroll_Kihon" id="sclList4" style="width:527px; overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll3();" >
		                    <div id="divKihonSub">
		                    </div>
	                    </div>
                	</td>
                </tr>
            </table>
            <!-- ADD 2013/7/2 shima�yQP@30151�zNo.37 end -->

            <br>
            <!--���쌴�����-->
            <a name="lnkGenka" />
            <table border="0" cellspacing="0" border="0" width="100%">
                <!--�^�C�g���A�y�[�W�������N-->
                <tr>
                    <td>
                         �y�������z
                    </td>
                    <td align="right" height="10px">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">��{���</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                         <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">���Z����</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">�v�Z����</a>
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">���ޏ��</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!--���쌴�����ꗗ-->
            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
            <!-- <table cellpadding="0" cellspacing="0" border="0" width="970px" height="500px" style="table-layout:fixed;"> -->
            <table cellpadding="0" cellspacing="0" border="0" width="1130px" height="500px" style="table-layout:fixed;">
            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                <tr>
                <!-- ���� -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                    	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                    	<!-- <td valign="top" style="width:470px;"> -->
                        <td valign="top" style="width:650px;">
                        <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--�����w�b�_�[��-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:75px;" >�I<br>��</th>
                                            <th class="columntitle" style="width:20px;height:75px;" >�H<br>��</th>
                                            <th class="columntitle" style="width:105px;" >����CD</th>
                                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                                            <!-- <th class="columntitle" style="width:180px;" >������</th> -->
                                            <th class="columntitle" style="width:310px;" >������</th>
                                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                                            <th class="columntitle" style="width:20px;" >��<br>�X</th>
                                            <th class="columntitle" style="width:70px;" >�P��<br>�i�~/�s�j<br>��</th>
                                            <th class="columntitle" style="width:45px;" >����<br>�i���j<br>��</th>
                                        </tr>
                                        </table>
                                    </div>
                                </td></tr>
                                <tr>
                                    <td valign="top" height="422">
                                        <!--�����J������-->
                                        <div id="sclList1" style="height:416px;overflow:hidden;position:relative;">
                                            <div id="divGenryo_Left"></div>
                                        </div>
                                        <input class="normalbutton" type="button" name="btnBckMst" onClick="funBckMst()" style="width:153px" value="�}�X�^�P���E����" tabindex="20" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <!-- �E�� -->
                        <td align="left" valign="top">
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                        	<!-- <div class="scroll_genka" id="sclList2" style="width:510px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" /> -->
                            <div class="scroll_genka" id="sclList2" style="width:544px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                                <!-- �E���e�[�u�� -->
                                <div id="divGenryo_Right">
                                </div>
                        </td>
                    </tr>

                    </table>
                </td></tr>
            </table>
            <br/><br/>
            <!--���Z���ڏ��-->
            <a name="lnkShisan" />
            <table border="0" cellspacing="0" border="0" width="100%">
                <!--�^�C�g���A�y�[�W�������N-->
                <tr>
                    <td>
                    <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                          �y���Z���ځz -->
                              �y�v�Z���ځz
                    <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                    </td>
                    <td align="right">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">��{���</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="17" >�������</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="18">���ޏ��</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--             	�y�v�Z���ځz&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- DEL 2014/8/7 shima�yQP@30154�zNo.51 start -->
<!--             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- DEL 2014/8/7 shima�yQP@30154�zNo.51 end -->
            <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
            <!-- �yQP@10713�z�V�T�N�C�b�N���� No.4 start -->
            <!-- �Œ��v�Z�����F�@<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" />�Œ��/�P�[�X�@<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" />�Œ��/KG -->
            <!-- MOD 2013/11/11 okano�yQP@30154�zstart -->
<!--             �Œ��v�Z�����F�@<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />�Œ��/�P�[�X�@<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />�Œ��/KG -->
            <!-- DEL 2014/8/7 shima�yQP@30154�zNo.51 start -->
            <!-- �Œ��E���v�v�Z�����F�@<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />�Œ��E���v/�P�[�X�@<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />�Œ��E���v/KG -->
            <!-- DEL 2014/8/7 shima�yQP@30154�zNo.51 end -->
            <!-- MOD 2013/11/11 okano�yQP@30154�zend -->
            <!-- �yQP@10713�z�V�T�N�C�b�N���� No.4 end   -->

            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
            <!-- <table border="0" width="970px" height="400px"> -->
            <table border="0" width="1150px" height="400px">
            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                <tr>
                <!--�����H��-->
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                <!-- <td align="left" valign="top" width="320"> -->
                <td align="left" valign="top" width="450">
                <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->

                    <!--QP@00412_�V�T�N�C�b�N���� No.38 start -->
                    �����H���@�T���v��No
                    <select name="ddlSeizoNo" id="ddlSeizoNo" onChange="seizo_output()" style="width:150px;height:16px;" tabindex="20" >
                    </select>
                    <!--QP@00412_�V�T�N�C�b�N���� No.38 end   -->

                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000">
                        <tr>
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                            <!-- <td class="td_head" style="width:320px;text-align:center;" >�����H��</td> -->
                            <td class="td_head" style="width:450px;text-align:center;" >�����H��</td>
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                        </tr>
                        <tr>
                        <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                        <!--
                        <td rowspan="1" style="width:320px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly style="width:320px;height:399px;" class="table_textarea" >
                         -->
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.50 start -->
                        <!-- <td rowspan="1" style="width:500px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly style="width:450px;height:399px;" class="table_textarea" > -->
                        <td rowspan="1" style="width:500px;height:473px;">
                                <textarea id="txtSeizoKotei" readonly style="width:450px;height:473px;" class="table_textarea" >
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.50 end -->
                        <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                                </textarea>
                            </td>
                        </tr>
                    </table>
                </td>

                <!--�ꗗ-->
                <td valign="top" align="left" width="724">
                    <table cellpadding="0" cellspacing="0" border="0"><tr>
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.51,No.77 start -->
                    	&nbsp;�Œ��E���v�v�Z�����F�@<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />�Œ��E���v/�P�[�X�@<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />�Œ��E���v/KG
                        <!-- MOD 2014/8/7 shima�yQP@30154�zNo.51,No.77 end -->
                        <!-- �����w�b�_�[ -->
                        <td valign="top">
                            <div class="scroll_genka" id="sclList3_2" style="width:146px;overflow:hidden;" rowSelect="false">
                            <table class="detail" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
<!-- ADD 2013/7/25 ogawa�yQP@30151�zNo.13 start-->
                                <tr height="23"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;���ڌŒ�`�F�b�N</td></tr>
<!-- ADD 2013/7/25 ogawa�yQP@30151�zNo.13 end-->

                                <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;�T���v��No</td></tr>
                                <!--QP@00412_�V�T�N�C�b�N���� No.38 start -->
	                            <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;�����H����</td></tr>
	                            <!--QP@00412_�V�T�N�C�b�N���� No.38 end   -->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;���Z��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�[�U�ʐ����i���j��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�[�U�ʖ����i���j��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;���v�ʁi���j</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;��d��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�L�������i���j��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;���x���ʁi�s�j��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;���Ϗ[�U�ʁi�s�j��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;��d���Z�ʁi�s�j</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;������i�~�j/�P�[�X��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�ޗ���i�~�j/�P�[�X��</td></tr>
                                <!-- �yQP@10713�z20111031 hagiwara mod start -->
                                <!--<tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#f00">�Œ��i�~�j/�P�[�X��</font></td></tr>-->
                                <tr height="18"><td class="td_head" id = "kotehi_case">&nbsp;&nbsp;&nbsp;&nbsp;�Œ��i�~�j/�P�[�X��</td></tr>
                                <!-- �yQP@10713�z20111031 hagiwara mod end -->
                                <!-- ADD 2013/11/1 okano�yQP@30154�zstart-->
                                <tr height="18"><td class="td_head" id = "rieki_case">&nbsp;&nbsp;&nbsp;&nbsp;���v�i�~�j/�P�[�X��</td></tr>
                                <!-- ADD 2013/11/1 okano�yQP@30154�zend-->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�P�[�X��</td></tr>
                                <tr height="18"><td class="td_head">�s�Q�l�F������t</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/��</td></tr>
                                <tr height="18"><td class="td_head">�s�Q�l�FKG������t</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;������i�~�j/�s</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�ޗ���i�~�j/�s</td></tr>
                                <!-- �yQP@10713�z20111031 hagiwara mod start -->
                                <!--<tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color = "#f00">�Œ��i�~�j/�s��</font></td></tr>-->
                                <tr height="18"><td class="td_head" id = "kotehi_kg">&nbsp;&nbsp;&nbsp;&nbsp;�Œ��i�~�j/�s��</td></tr>
                                <!-- �yQP@10713�z20111031 hagiwara mod end -->
                                <!-- ADD 2013/11/1 okano�yQP@30154�zstart-->
                                <tr height="18"><td class="td_head" id = "rieki_kg">&nbsp;&nbsp;&nbsp;&nbsp;���v�i�~�j/kg��</td></tr>
                                <!-- ADD 2013/11/1 okano�yQP@30154�zend-->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�s��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;����</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�e���i���j</td></tr>
                            </table>
                            </div>
                        </td>
                        <!-- �E���J���� -->
                        <td>
                        	<!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 start -->
                        	<!-- <div class="scroll_genka" id="sclList3" style="width:500px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();"> -->
                            <div class="scroll_genka" id="sclList3" style="width:527px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();">
                            <!-- MOD 2013/7/2 shima�yQP@30151�zNo.37 end -->
                                <div id="divGenryoShisan">
                                </div>
                            </div>
                        </td>
                    </tr></table>
                </td></tr>

            </table>


            <!-- <br> --><!-- DEL 20160622  KPX@502111_No.10 -->
            <!-- ADD 20160622  KPX@502111_No.10 start -->
             <table border="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td align="right">
                        <input class="normalbutton" type="button" id="btnSampleCopy" name="btnSampleCopy" onClick="funSampleCopy();" value="�T���v���R�s�[" tabindex="15" />
                    </td>
                </tr>
            </table>
            <!-- ADD 20160622  KPX@502111_No.10 end -->

            <!--���쎑�ޏ��-->
            <a name="lnkShizai" />
            <table border="0" width="100%">
                <!--�^�C�g���A�y�[�W�������N-->
                <tr>
                    <td>
                        �y���ޏ��z
                    </td>
                    <td align="right" height="12">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="25" >��{���</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="26" >�������</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 start -->
<!--                         <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >���Z����</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >�v�Z����</a>
                        <!-- MOD 2013/9/6 okano�yQP@30151�zNo.30 end -->
                    </td>
                </tr>
            </table>
            <hr>

            <!--���ރe�[�u��-->
            <div id="divGenryoShizai">
            </div>

            <br>
            <!--�ꊇ�I��-->
            <input type="checkbox" onClick="shizaiIkkatu()" id="chkIkkatuShizai" name="chkIkkatuShizai" tabindex="28" />�ꊇ�I��
            <br>
            <div align="right">
     <!-- ADD 2014/5/1�yQP@30297�zstart -->
                <!--�R�X�g�e�[�u���Q�ƃ{�^��-->
                <input type="button" class="normalbutton" id="btnCostTblRef" name="btnCostTblRef" value="�R�X�g�Q��" style="width:80px;visibility:hidden;" onClick="funCostTblRef()" tabindex="29" />
     <!-- ADD 2014/5/1�yQP@30297�zend -->
                <!--�ގ��i�����{�^��-->
                <input type="button" class="normalbutton" id="btnRuiziSearch" name="btnRuiziSearch" value="�ގ��i����" style="width:80px;" onClick="funRuiziSearch()" tabindex="29" />
                <!--���ލ폜�{�^��-->
                <input type="button" class="normalbutton" id="btnShizaiDelete" name="btnShizaiDelete" value="���ލ폜" style="width:80px;" onClick="funDeleteShizai()" tabindex="30" />
            </div>
            <br><br><br><br>
            <br><br><br><br>
            <br><br><br><br>
            <br>

            <!-- ���Z�����\���C�x���g�{�^�� -->
            <input type="button" value="" name="BtnEveGenryo" id="BtnEveGenryo" onclick="funGenryoHyoji()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- ���Z���ޕ\���C�x���g�{�^�� -->
            <input type="button" value="" name="BtnEveShizai" id="BtnEveShizai" onclick="funShizaiHyoji()" style="width:0px;height:0px;" tabindex="-1"/>
            <!-- ��� -->
            <input type="hidden" value="" name="hdnKaisha" id="hdnKaisha">
            <!-- �H�� -->
            <input type="hidden" value="" name="hdnKojo" id="hdnKojo">
            <!-- ���ރR�[�hindex�i�����p�ꎞ�ۊǁj -->
            <input type="hidden" value="" name="hdnShizai" id="hdnShizai">
            <!-- ���ޕ\�s�� -->
            <input type="hidden" value="" name="hdnShizaiCount" id="hdnShizaiCount">
            <!-- �����A���ރR�[�h���� -->
            <input type="hidden" value="" name="hdnCdketasu" id="hdnCdketasu">
            <!-- ������ -->
            <input type="button" value="" name="BtnEveStart" id="BtnEveStart" onclick="funShowRunMessage()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- �����I�� -->
            <input type="button" value="" name="BtnEveEnd" id="BtnEveEnd" onclick="funClearRunMessage()" style="width:0px;height:0px;" tabindex="-1" />

            <!-- ADD start 20120615 hisahori -->
            <input type="hidden" id="hidsetSeqShisaku" name="hidsetSeqShisaku" value="">
            <input type="hidden" id="hidsetSampleNo" name="hidsetSampleNo" value="">
            <input type="hidden" id="hidsetShisanHi" name="hidsetShisanHi" value="">
            <input type="hidden" id="hidsetShisanChushi" name="hidsetShisanChushi" value="">
            <!-- ADD end 20120615 hisahori -->
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start -->
            <input type="hidden" id="hidsetChkKoumoku" name="hidsetChkKoumoku" value="">
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end -->
            <!-- 20160617  KPX@502111_No.5 ADD start -->
            <input type="hidden" name="hdnRenkeiSeqShisaku" id="hdnRenkeiSeqShisaku" value="">
            <!-- 20160617  KPX@502111_No.5 ADD end -->
            <!-- 20170515 KPX@1700856 ADD start -->
            <input type="hidden" name="hdnTankaZeroGenryo" id="hdnTankaZeroGenryo" value="">
            <!-- 20170515 KPX@1700856 ADD end -->

        </form>
    </body>
</html>
