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
        }
        function Scroll2() {
            //X�����̃X�N���[���ړ�
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList3").scrollLeft;
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
        <script type="text/javascript" src="include/SQ110GenkaShisan.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
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
                        <a href="GenkaShisan_dtl.jsp#lnkGenka"  tabindex="1">�������</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">���Z����</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai"  tabindex="1">���ޏ��</a>
                    </td>
                </tr>
            </table>
            <hr>
            
            <table border="0" width="975px" height="500px">
                <tr><td align="left" valign="top" width="370">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;">
                        <!-- ����\�� -->
                        <tr>
                            <!-- 1�s�� -->
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
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������S���`�[��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ꊇ�\��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���[�U�[
                            </td>
                            <td height="19px">
                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �p�r
                            </td>
                            <td height="19px"><input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr>
                        <!-- �������Z������ -->
                        <tr>
                            <!-- 1�s�� -->
                            <td class="td_head" rowspan="21" style="width:18px;writing-mode:tb-rl;">�������Z����</td>
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
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �S���c��
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �������@
                            </td>
                            <td height="19px"><input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 5�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �[�U���@
                            </td>
                            <td height="19px"><input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 6�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �E�ە��@
                            </td>
                            <td height="19px"><input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 7�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e��E���
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 8�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �e�ʁi���l���́j
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouryo" name="txtYouryo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ���萔�i���l���́j��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_disb" onblur="setFg_saikeisan();setKanma(this)" value="" tabindex="3" />
                            </td>
                        </tr><tr>
                            <!-- 10�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �׎p
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_act" value="" tabindex="4" />
                            </td>
                        </tr><tr>
                            <!-- 11�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �戵���x
                            </td>
                            <td height="19px"><input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 12�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �ܖ�����
                            </td>
                            <td height="19px"><input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 13�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]������
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtGenkaKibo" name="txtGenkaKibo" style="width:112px;height:20px" class="table_text_disb" onblur="setFg_saikeisan();setKanma(this)" value="" tabindex="5" />
                                <select name="ddlGenkaTani" id="ddlGenkaTani" onChange="setFg_saikeisan();baikaTaniSetting()" style="width:82px;height:16px;" tabindex="6" >
                                </select>
                            </td>
                        </tr><tr>
                            <!-- 14�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ��]������
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtBaikaKibo" name="txtBaikaKibo" onblur="setFg_saikeisan();setKanma(this)" style="width:112px;" class="table_text_disb"  value="" tabindex="7" />
                                <input type="text" id="txtBaikaTani" name="txtBaikaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 15�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �z�蕨��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSoteiButuryo" style="ime-mode:active;" name="txtSoteiButuryo" class="table_text_act" value="" tabindex="8" />
                            </td>
                        </tr><tr>
                            <!-- 16�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔�����
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaiJiki"style="ime-mode:active;" name="txtHanbaiJiki" class="table_text_act" value="" tabindex="9" />
                            </td>
                        </tr><tr>
                            <!-- 17�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�攄��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuUriage" style="ime-mode:active;" name="txtKeikakuUriage" class="table_text_act" value="" tabindex="10" />
                            </td>
                        </tr><tr>
                            <!-- 18�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �v�旘�v
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuRieki" style="ime-mode:active;" name="txtKeikakuRieki" class="table_text_act" value="" tabindex="11" />
                            </td>
                        </tr><tr>
                            <!-- 19�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔��㔄��
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoUriage" style="ime-mode:active;" name="txtHanbaigoUriage" class="table_text_act" value="" tabindex="12" />
                            </td>
                        </tr><tr>
                            <!-- 20�s�� -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                �̔��㗘�v
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoRieki" style="ime-mode:active;" name="txtHanbaigoRieki" class="table_text_act" value="" tabindex="13" />
                            </td>
                        </tr><tr>
                            <!-- 21�s�� -->
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
                <td align="left" valign="top">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="100%">
                        <tr>
                            <td class="td_head" width="565px">�������A���i���������j</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtSogoMemo" name="txtSogoMemo" class="table_textarea" readonly style="background-color:#ffffff;" tabindex="-1" >
                                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_head" width="565px">�������Z����</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtGenkaMemo" name="txtGenkaMemo" class="table_textarea" style="background-color:#ffff88;" tabindex="15" >
                                </textarea>
                            </td>
                        </tr>
                        
                    </table>
                </td>
                </tr>
            </table>
            
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
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">���Z����</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">���ޏ��</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!--���쌴�����ꗗ-->
            <table cellpadding="0" cellspacing="0" border="0" width="970px" height="500px" style="table-layout:fixed;">
                <tr>
                <!-- ���� -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td valign="top" style="width:470px;">
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--�����w�b�_�[��-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:73px;" >�I<br>��</th>
                                            <th class="columntitle" style="width:20px;height:73px;" >�H<br>��</th>
                                            <th class="columntitle" style="width:105px;" >����CD</th>
                                            <th class="columntitle" style="width:180px;" >������</th>
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
                            <div class="scroll_genka" id="sclList2" style="width:510px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
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
                         �y���Z���ځz
                    </td>
                    <td align="right">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">��{���</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="16" >�������</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">���ޏ��</a>
                    </td>
                </tr>
            </table>
            <hr>
            �y�v�Z���ځz&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            �Œ��v�Z�����F�@<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" />�Œ��/�P�[�X�@<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" />�Œ��/KG
            <table border="0" width="970px" height="400px">
                <tr>
                <!--�����H��-->
                <td align="left" valign="top" width="320">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000">
                        <tr>
                            <td class="td_head" style="width:330px;text-align:center;" >�����H��</td>
                        </tr>
                        <tr>
                            <td rowspan="1" style="width:330px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly  class="table_textarea" >
                                </textarea>
                            </td>
                        </tr>
                    </table>
                </td>
                
                <!--�ꗗ-->
                <td valign="top" align="left" width="724">
                    <table cellpadding="0" cellspacing="0" border="0"><tr>
                        <!-- �����w�b�_�[ -->
                        <td valign="top">
                            <div class="scroll_genka" id="sclList3_2" style="width:146px;overflow:hidden;" rowSelect="false">
                            <table class="detail" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
                                <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;�T���v��No</td></tr>
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
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ff0000">�Œ��i�~�j/�P�[�X��</font></td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�P�[�X��</td></tr>
                                <tr height="18"><td class="td_head">�s�Q�l�F������t</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/��</td></tr>
                                <tr height="18"><td class="td_head">�s�Q�l�FKG������t</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;������i�~�j/�s</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�ޗ���i�~�j/�s</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ff0000">�Œ��i�~�j/�s��</font></td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�����v�i�~�j/�s��</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;����</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;�e���i���j</td></tr>
                            </table>
                            </div>
                        </td>
                        <!-- �E���J���� -->
                        <td>
                            <div class="scroll_genka" id="sclList3" style="width:500px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();">
                                <div id="divGenryoShisan">
                                </div>
                            </div>
                        </td>
                    </tr></table>
                </td></tr>
                
            </table>
            
            
            <br>
            
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
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >���Z����</a>
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
            
            
        </form>
    </body>
</html>
