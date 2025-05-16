<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �yQP@00342�z�V�T�N�C�b�N�@�X�e�[�^�X�N���A���                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                                -->
<!-- �쐬���F2011/01/26                                                              -->
<!-- �T�v  �F���݃X�e�[�^�X�̕\���ƁA�X�e�[�^�X�N���A���s���B                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title>�������Z�V�X�e�� �X�e�[�^�X�N���A���</title>
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
        <script type="text/javascript" src="include/SQ130StatusClear.js"></script>
        <!-- �X�^�C���V�[�g -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="" onLoad="funLoad()">

        <!-- XML Document��` -->
        <xml id="xmlRGEN2020"></xml>
        <xml id="xmlRGEN2030"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2010I" src="../../model/FGEN2010I.xml"></xml>
        <xml id="xmlFGEN2020I" src="../../model/FGEN2020I.xml"></xml>
        <xml id="xmlFGEN2030I" src="../../model/FGEN2030I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2010O"></xml>
        <xml id="xmlFGEN2020O"></xml>
        <xml id="xmlFGEN2030O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">�X�e�[�^�X�N���A</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="�N���A" onClick="funClear();" tabindex="21">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="�I��" onClick="funClose();" tabindex="22">
                    </td>
                </tr>
            </table>

            <br/>

			<table width="300" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>���݂̈˗��E���F��</td>
                </tr>
            </table>
			<table width="300" border="1" cellspacing="0" cellpadding="0">
                <tr>
                    <th class="columntitle" width="50">������</th>
                    <th class="columntitle" width="50">����</th>
                    <th class="columntitle" width="50">����</th>
                    <th class="columntitle" width="50">�H��</th>
                    <th class="columntitle" width="50">�c��</th>
                </tr>
                <tr>
                    <td align="center"><div id="divStatusKenkyu_now" /></td>
                    <td align="center"><div id="divStatusSeikan_now" /></td>
                    <td align="center"><div id="divStatusGentyo_now" /></td>
                    <td align="center"><div id="divStatusKojo_now" /></td>
                    <td align="center"><div id="divStatusEigyo_now" /></td>
                </tr>
            </table>

            <br><br>

            <table width="99%" cellspacing="0" cellpadding="0">
                <tr>
                    <td>���L����X�e�[�^�X�̖߂����I�����A�u�N���A�v�{�^���������ĉ������B<hr size="1"></td>
                </tr>
            </table>

            <div id="sclList" style="width:632;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="629" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:350px;"/>
                    <col style="width:250px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr>
                        <th class="columntitle">�I</th>
                        <th class="columntitle">�V�X�e���Ɩ�</th>
                        <th class="columntitle">
                        	<table width="250" cellspacing="0" cellpadding="0" border="1" frame="void">
                        		<tr class="columntitle" >
                        			<th colspan="5">�˗��E���F��</th>
                        		</tr>
                        		<tr class="columntitle" >
                        			<th width="50">������</th>
                        			<th width="50">����</th>
                        			<th width="50">����</th>
                        			<th width="50">�H��</th>
                        			<th width="50">�c��</th>
                        		</tr>
                        	</table>
                        </th>
                    </tr>
                </thead>
                <tbody>
                	<table cellpadding="0" cellspacing="0" border="0">
                	    <tr>
                	       <td><div id="divStatusTable" /></td>
                	    </tr>
                	</table>
                </tbody>
            </table>
            </div>

            <table width="99%">
                <tr align="left">
                    <td height="18px">
                        <span id="spnRecInfo">�����݂̈˗��E���F�󋵂͉��F�̍s�ɂȂ�܂��B</span>
                    </td>
                </tr>
            </table>

            <!--ADD start 20120614 hisahori-->
    <!-- 20160607  KPX@1502111_No9 MOD start ���Z���N���A�@�\�폜�F�e�[�u�����\���ɂ��� -->
            <!--���Z���N���A�I���e�[�u��-->
            <!--
            <br><br>
            <table width="99%" cellspacing="0" cellpadding="0">
                <tr>
                    <td>�H��ɂĎ��Z���e���Ċm�F����ꍇ�́u���Z���v���N���A���܂��B�ΏۃT���v��No��I�����Ă��������B<hr size="1"></td>
                </tr>
            </table>
            -->
            <!-- KPX@1502111_No9 ��\���ɕύX -->
            <!-- <div id="sclList" style="width:332;" rowSelect="true"> -->
            <div id="sclList" style="display:none">
    <!-- 20160607  KPX@1502111 MOD end -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="329" align="center">

                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:150px;"/>
                    <col style="width:150px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr>
                        <th class="columntitle">�I</th>
                        <th class="columntitle">�T���v��NO</th>
                        <th class="columntitle">���Z��</th>
                    </tr>
                </thead>

                 <tbody>
                	<table cellpadding="0" cellspacing="0" border="0">
                	    <tr>
                	       <td><div id="divDtShisanClearSelect" />
                	       </td>
                	    </tr>
                	</table>
                </tbody>
            </table>
            </div>

            <!--ADD end 20120614 hisahori-->

            <input type="hidden" id="hidShisakuNo_shaincd" name="hidShisakuNo_shaincd" value="">
            <input type="hidden" id="hidShisakuNo_nen" name="hidShisakuNo_nen" value="">
            <input type="hidden" id="hidShisakuNo_oi" name="hidShisakuNo_oi" value="">
            <input type="hidden" id="hidShisakuNo_eda" name="hidShisakuNo_eda" value="">
            <!-- ADD start 20120615 hisahori -->
            <input type="hidden" id="hidSeqShisaku" name="hidSeqShisaku" value="">
            <input type="hidden" id="hidSampleNo" name="hidSampleNo" value="">
            <input type="hidden" id="hidShisanHi" name="hidShisanHi" value="">
            <input type="hidden" id="hidShisanChushi" name="hidShisanChushi" value="">
            <!-- ADD end 20120615 hisahori -->
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 start -->
            <input type="hidden" id="hidChkKoumoku" name="hidChkKoumoku" value="">
            <!-- ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.29 end -->

        </form>
    </body>
</html>
