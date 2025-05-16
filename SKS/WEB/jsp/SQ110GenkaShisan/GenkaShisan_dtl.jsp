<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　原価試算画面（明細）                                                -->
<!-- 作成者：TT.Nishigawa                                                             -->
<!-- 作成日：2009/10/20                                                              -->
<!-- 概要  ：原価試算画面の明細部フレーム                                                                        -->
<!------------------------------------------------------------------------------------->
<html>

    <script type="text/javascript">
    <!--//
    //===================================================================================
    // スクロール移動制御
    // 作成者：K.Katayama
    // 作成日：2009/08/20
    // 引数  ：なし
    // 戻り値：なし
    // 概要  ：ヘッダーの表示方法をDIVの相対位置で変える
    //===================================================================================
        function Scroll1() {
            //Y方向のスクロール移動
            document.getElementById("sclList1").scrollTop = document.getElementById("sclList2").scrollTop;
            //X方向のスクロール移動
            document.getElementById("sclList3").scrollLeft = document.getElementById("sclList2").scrollLeft;

            // ADD 2013/7/2 shima【QP@30151】No.37 start
			//基本情報
            //X方向のスクロール移動
            document.getElementById("sclList4").scrollLeft = document.getElementById("sclList2").scrollLeft;
            // ADD 2013/7/2 shima【QP@30151】No.37 end
        }
        function Scroll2() {
            //X方向のスクロール移動
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList3").scrollLeft;

            // ADD 2013/7/2 shima【QP@30151】No.37 start
			//基本情報
            //X方向のスクロール移動
            document.getElementById("sclList4").scrollLeft = document.getElementById("sclList3").scrollLeft;
            // ADD 2013/7/2 shima【QP@30151】No.37 end
        }

        // ADD 2013/7/2 shima【QP@30151】No.37 start
        function Scroll3() {
        	//原料情報
            //X方向のスクロール移動
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList4").scrollLeft;
        	//計算項目
            //X方向のスクロール移動
            document.getElementById("sclList3").scrollLeft = document.getElementById("sclList4").scrollLeft;
        }
        // ADD 2013/7/2 shima【QP@30151】No.37 end
    -->
    </script>

    <script type="text/javascript">
    <!--
		window.onunload = function() {
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			document.getElementById("divKihonSub").innerHTML = null;
			// ADD 2013/7/2 shima【QP@30151】No.37 end
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
//2015/03/30 TT.Kitazawa【QP@40812】No.12 ADD START
            document.getElementById("xmlRGEN0090").src = null;
            document.getElementById("xmlFGEN0090I").src = null;
            document.getElementById("xmlFGEN0090O").src = null;
//2015/03/30 TT.Kitazawa【QP@40812】No.12 ADD END
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
        <title>シサクイックシステム 原価試算分析 原価試算画面</title>
        <!-- 共通 -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ110GenkaShisan.js"></script>


        <script type="text/javascript" src="include/js/prototype.js" charset="shift_jis"></script>
		<script type="text/javascript" src="include/js/effects.js" charset="shift_jis"></script>
		<script type="text/javascript" src="include/js/window.js" charset="shift_jis"></script>


        <!-- スタイルシート -->
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

        <!--  資材テーブル行クリック -->
        <!--  <script for="tblList4" event="onclick" language="JavaScript">-->
        <!--      //選択行の背景色を変更-->
        <!--      funChangeSelectRowColor2();-->
        <!--  </script>-->

    </head>

    <body class="pfcancel" onLoad="funLoad_dtl();" tabindex="-1">

        <!-- XML Document定義 -->
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

        <!--2015/03/30 TT.Kitazawa【QP@40812】No.12 ADD START------------------------------>
        <xml id="xmlRGEN0090"></xml>
        <xml id="xmlFGEN0090I" src="../../model/FGEN0090I.xml"></xml>
        <xml id="xmlFGEN0090O"></xml>
        <!--2015/03/30 TT.Kitazawa【QP@40812】No.12 ADD END-------------------------------->

<!-- 2016/04/2020160513  KPX@1600766 ADD start -->
        <xml id="xmlJSP0630"></xml>
        <xml id="xmlSA100I" src="../../model/SA100I.xml"></xml>
        <xml id="xmlSA100O"></xml>
<!-- 2016/04/2020160513  KPX@1600766 ADD end -->
<!-- 20160617  KPX@502111_No.5 ADD start -->
        <xml id="xmlRGEN2280"></xml>
        <xml id="xmlFGEN2280I" src="../../model/FGEN2280I.xml"></xml>
        <xml id="xmlFGEN2280O"></xml>
        <!-- データ退避用 -->
        <xml id="xmlFGEN2280"></xml>
<!-- 20160617  KPX@502111_No.5 ADD end -->
<!-- 20170515 KPX@1700856 ADD start -->
        <xml id="xmlRGEN2290"></xml>
        <xml id="xmlFGEN2290I" src="../../model/FGEN2290I.xml"></xml>
        <xml id="xmlFGEN2290O"></xml>
<!-- 20170515 KPX@1700856 ADD end -->

        <form name="frm00" id="frm00" method="post">

            <!--試作基本情報-->
            <a name="lnkKihon" />
            <table border="0" cellspacing="0" width="100%">
                <!--タイトル、ページ内リンク-->
                <tr>
                    <td>
                        【基本情報】
                    </td>
                    <td align="right" height="10px">
                        <a href="GenkaShisan_dtl.jsp#lnkGenka"  tabindex="1">原料情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!--                         	<a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">試算項目</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">計算項目</a>
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai"  tabindex="1">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>

            <!-- DEL 2013/7/2 shima【QP@30151】No.37 start -->
            <!--
            <table border="0" width="975px" height="500px">
                <tr><td align="left" valign="top" width="370">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;">
                        <!-- 試作表部 -->
                        <!--
                        <tr>
                            <!-- 1行目 -->
                            <!--
                            <td class="td_head" rowspan="5" style="width:18px;writing-mode:tb-rl;">試作表</td>
                            <td class="td_head" style="width:150px;" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                研究所担当グループ
                            </td>
                            <td width="200px" height="19px">
                                <input type="text" id="txtGroup" name="txtGroup" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 2行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                研究所担当チーム
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                一括表示
                            </td>
                            <td height="19px">
                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ユーザー
                            </td>
                            <td height="19px">
                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 5行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                用途
                            </td>
                            <td height="19px"><input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr>
                        <!-- 原価試算条件部 -->
                        <!--
                        <tr>
                            <!-- 1行目 -->
                            <!--
                            <td class="td_head" rowspan="22" style="width:18px;writing-mode:tb-rl;">原価試算条件</td>
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                製造会社
                            </td>
                            <td bgcolor="" height="19px">
                                <select name="ddlSeizoKaisya" id="ddlSeizoKaisya" onChange="setFg_saikeisan();funKojoChange()" style="width:200px;height:16px;" tabindex="1" />
                                </select>
                            </td>
                        </tr><tr>
                            <!-- 2行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                製造工場
                            </td>
                            <td bgcolor="" height="19px">
                                <select name="ddlSeizoKojo" id="ddlSeizoKojo" style="width:120px;height:16px;" tabindex="2" >
                                </select>
                                <input type="button" class="normalbutton" style="width:70px;"  id="btnArigae" name="btnArigae" value="工場変更" onClick="funAraigae()" tabindex="2"/>
                            </td>
                        </tr><tr>
                            <!-- 3行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                担当営業
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                製造方法
                            </td>
                            <td height="19px"><input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 5行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                充填方法
                            </td>
                            <td height="19px"><input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 6行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                殺菌方法
                            </td>
                            <td height="19px"><input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 7行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                容器・包材
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 8行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                容量（数値入力）
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouryo" name="txtYouryo" style="width:112px;" class="table_text_view" readonly  value="" tabindex="-1" />
                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:82px;" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                入り数（数値入力）※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_view" readonly   value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 10行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                荷姿
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 11行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                取扱温度
                            </td>
                            <td height="19px"><input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 12行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                賞味期間
                            </td>
                            <td height="19px"><input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 13行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                希望原価※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtGenkaKibo" name="txtGenkaKibo" style="width:112px;height:20px" class="table_text_view" readonly  value="" tabindex="-1" />
                                <input type="text" id="txtGenkaTani" name="txtGenkaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                                <input type="hidden" id="hdnGenkaTaniCd" name="hdnGenkaTaniCd" style="width:82px;" />
                            </td>
                        </tr><tr>
                            <!-- 14行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                希望特約※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtBaikaKibo" name="txtBaikaKibo" style="width:112px;" class="table_text_view" readonly  value="" tabindex="-1" />
                                <input type="text" id="txtBaikaTani" name="txtBaikaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 15行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                想定物量
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSoteiButuryo" style="ime-mode:active;" name="txtSoteiButuryo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 16行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                初回納品時期
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHatubaiJiki"style="ime-mode:active;" name="txtHatubaiJiki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 16行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                販売期間
                            </td>
                            <td bgcolor="" height="19px">
                            	<input type="text" id="txtHanbaiKikan_t"  name="txtHanbaiKikan_t" style="width:74px;"class="table_text_view" readonly value="" tabindex="-1" />
                            	<input type="text" id="txtHanbaiKikan_s"  name="txtHanbaiKikan_s" style="width:30px;text-align:right;"class="table_text_view" readonly value="" tabindex="-1" />
                                <input type="text" id="txtHanbaiKikan_k" name="txtHanbaiKikan_k" style="width:74px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 17行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                計画売上
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuUriage" style="ime-mode:active;" name="txtKeikakuUriage" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 18行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                計画利益
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuRieki" style="ime-mode:active;" name="txtKeikakuRieki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>

                            <!-- 19行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoUriage" style="ime-mode:active;" name="txtHanbaigoUriage" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 20行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;

                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoRieki" style="ime-mode:active;" name="txtHanbaigoRieki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 21行目 -->
                            <!--
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                製造ロット
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSeizoRot" style="ime-mode:active;" name="txtSeizoRot" class="table_text_act" value="" tabindex="14" />
                            </td>
                        </tr>
                    </table>
                </td>

                <!--総合メモ-->
                <!--
                <td align="left" valign="top">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="100%">
                        <tr>
                            <td class="td_head" width="565px">原価試算メモ</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtGenkaMemo" name="txtGenkaMemo" class="table_textarea" style="background-color:#ffff88;" tabindex="15" >
                                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_head" width="565px">原価試算メモ（営業連絡用）</td>
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
                <!-- DEL 2013/7/2 shima【QP@30151】No.37 end -->

			<!-- ADD 2013/7/2 shima【QP@30151】No.37 start -->
            <table border="0" width="1130px" height="500px" cellspacing="0" cellpadding="0" style="word-break:break-all;" >
                <tr>
                	<!--総合メモ-->
	                <td align="left" valign="top" width="435">
<!-- ADD 2013/07/19 【QP@30151】ogawa テキストボックスが伸びる件 start -->
<!-- 修正前ソース
	                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="515">
-->
<!-- 修正後ソース -->
                        <!-- MOD 2014/8/7 shima【QP@30154】No.49 start -->
                        <!-- <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="515" style="word-break:break-all;word-wrap:break-word;"> -->
 	                    <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.16 start -->
 	                    <!-- <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="554" style="word-break:break-all;word-wrap:break-word;"> -->
                        <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="573" style="word-break:break-all;word-wrap:break-word;">
                        <!-- MOD 2014/8/7 shima【QP@30154】No.49 end -->
<!--  ADD 2013/07/19 【QP@30151】ogawa end -->
                        <!-- MOD 2015/03/03 TT.Kitazawa【QP@40812】No.16 end -->

	                        <tr>
	                            <td class="td_head" width="425px">原価試算メモ</td>
	                        </tr>
	                        <tr>
	                            <td style="width:425px;height:49%;">
	                                <textarea id="txtGenkaMemo" name="txtGenkaMemo" class="table_textarea" style="background-color:#ffff88;" tabindex="15" >
	                                </textarea>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td class="td_head" width="425px">原価試算メモ（営業連絡用）</td>
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
	                        <!-- 試作表部 -->
	                        <tr>
	                            <!-- 1行目 -->
	                            <td class="td_head" rowspan="5" style="width:18px;writing-mode:tb-rl;">試作表</td>
	                            <td class="td_head" style="width:150px;" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                研究所担当グループ
	                            </td>
	                        </tr><tr>
	                            <!-- 2行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                研究所担当チーム
	                            </td>
	                        </tr><tr>
	                            <!-- 3行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                一括表示
	                            </td>
	                        </tr><tr>
	                            <!-- 4行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                ユーザー
	                            </td>
	                        </tr><tr>
	                            <!-- 5行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                用途
	                            </td>
	                        </tr>
	                        <!-- 原価試算条件部 -->
	                        <tr>
	                            <!-- 1行目 -->
	                            <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!-- 	                            <td class="td_head" rowspan="22" style="width:18px;writing-mode:tb-rl;">原価試算条件</td> -->
<!-- 	                            <td class="td_head" rowspan="24" style="width:18px;writing-mode:tb-rl;">原価試算条件</td> -->
 	                            <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
                                <td class="td_head" rowspan="25" style="width:18px;writing-mode:tb-rl;">原価試算条件</td>
	                        <!-- ADD 2013/10/22 QP@30154 okano start -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    販責会社
                                </td>
                            </tr><tr>
                            <!-- ADD 2013/10/22 QP@30154 okano end -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                製造会社
	                            </td>
	                        </tr><tr>
	                            <!-- 2行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                製造工場
	                            </td>
	                        </tr><tr>
	                            <!-- 3行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                担当営業
	                            </td>
	                        </tr><tr>
	                            <!-- 4行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                製造方法
	                            </td>
	                        </tr><tr>
	                            <!-- 5行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                充填方法
	                            </td>
	                        </tr><tr>
	                            <!-- 6行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                殺菌方法
	                            </td>
	                        </tr><tr>
	                            <!-- 7行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                容器・包材
	                            </td>
	                        </tr><tr>
	                            <!-- 8行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                容量（数値入力）
	                            </td>
	                        </tr><tr>
	                            <!-- 9行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                入り数（数値入力）※
	                            </td>
	                        </tr><tr>
	                            <!-- 10行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                荷姿
	                            </td>
	                        </tr><tr>
	                            <!-- 11行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                取扱温度
	                            </td>
	                        </tr><tr>
	                            <!-- 12行目 -->
	                            <!-- MOD 2013/11/20 QP@30154 okano start -->
<!-- 	                            <td class="td_head" height="19px"> -->
	                            <td class="td_head" height="20px">
	                            <!-- MOD 2013/11/20 QP@30154 okano end -->
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                賞味期間
	                            </td>
	                        </tr><tr>
                                <!-- 13行目 -->
                            <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.16 start -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    サンプルNo
                                </td>
                            </tr><tr>
	                            <!-- 14行目 -->
                            <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.16 end -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                希望原価※
	                            </td>
	                        </tr><tr>
	                            <!-- 15行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                希望特約※
	                            </td>
	                        </tr><tr>
                            <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.20 start -->
                                <!-- 16行目 -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    販売期間
                                </td>
                            </tr><tr>
                            <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.20 end -->
	                            <!-- 17行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                想定物量
	                            </td>
	                        </tr><tr>
                            <!-- ADD 2013/9/6 okano【QP@30151】No.30 start -->
                                <td class="td_head" height="19px">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    想定物量備考
                                </td>
                            </tr><tr>
                            <!-- ADD 2013/9/6 okano【QP@30151】No.30 end -->
	                            <!-- 16行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                初回納品時期
	                            </td>
                            <!-- DEL 2015/05/15 TT.Kitazawa【QP@40812】No.20 start -->
<!-- 	                        </tr><tr>
	                            16行目
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                販売期間
	                            </td>
 -->
                            <!-- DEL 2015/05/15 TT.Kitazawa【QP@40812】No.20 start -->
	                        </tr><tr>
                            <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.27 end -->
	                            <!-- 17行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                <!-- 計画売上 -->
                                    計画売上／年
	                            </td>
	                        </tr><tr>
	                            <!-- 18行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                <!-- 計画利益 -->
                                     計画利益／年
	                            </td>
                            <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.27 end -->
	                        </tr><tr>
	                            <!-- 19行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;

	                            </td>
	                        </tr><tr>
	                            <!-- 20行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;

	                            </td>
	                        </tr><tr>
	                            <!-- 21行目 -->
	                            <td class="td_head" height="19px">
	                                &nbsp;&nbsp;&nbsp;&nbsp;
	                                製造ロット
	                            </td>
	                        </tr>
	                    </table>
                    </td>
                	<td align="left" valign="top" width="250" >
                    	<!-- 基本情報内容 -->
	                    <table border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed; border-bottom-style:none;">
	                    	<tr>
	                            <!-- 1行目 -->
	                            <td width="175px" height="19px">
	                                <input type="text" id="txtGroup" name="txtGroup" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 2行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 3行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 4行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 5行目 -->
	                            <td height="19px">
	                            	<input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr>
	                        <!-- 原価試算条件部 -->
	                        <tr>
	                            <!-- 1行目 -->
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
	                            <!-- 2行目 -->
	                            <td bgcolor="" height="19px">
	                                <select name="ddlSeizoKojo" id="ddlSeizoKojo" style="width:105px;height:16px;" tabindex="2" >
	                                </select>
	                                <input type="button" class="normalbutton" style="width:60px;" id="btnArigae" name="btnArigae" value="工場変更" onClick="funAraigae()" tabindex="2"/>
	                            </td>
	                        </tr><tr>
	                            <!-- 3行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 4行目 -->
	                            <td height="19px">
	                            	<input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 5行目 -->
	                            <td height="19px">
	                            	<input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 6行目 -->
	                            <td height="19px">
	                            	<input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 7行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 8行目 -->
	                            <td height="19px">
	                                <input type="text" id="txtYouryo" name="txtYouryo" style="width:70px;" class="table_text_view" readonly  value="" tabindex="-1" />
	                                <input type="text" id="txtYouryo_tani" name="txtYouryo_tani" style="width:80px;" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 9行目 -->
	                            <td bgcolor="" height="19px">
	                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_view" readonly   value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 10行目 -->
	                            <td bgcolor="" height="19px">
	                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 11行目 -->
	                            <td height="19px">
	                            	<input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr><tr>
	                            <!-- 12行目 -->
	                            <!-- MOD 2015/09/07 TT.Kitazawa start -->
	                            <!-- <td height="19px"> -->
	                            <td height="19px" style="border-bottom-style:none;">
	                            <!-- MOD 2015/09/07 TT.Kitazawa end -->
	                            	<input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" />
	                            </td>
	                        </tr>
	                    </table>

                    	<!-- 基本情報スクロール列 -->
                    	<div class="scroll_Kihon" id="sclList4" style="width:527px; overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll3();" >
		                    <div id="divKihonSub">
		                    </div>
	                    </div>
                	</td>
                </tr>
            </table>
            <!-- ADD 2013/7/2 shima【QP@30151】No.37 end -->

            <br>
            <!--試作原価情報-->
            <a name="lnkGenka" />
            <table border="0" cellspacing="0" border="0" width="100%">
                <!--タイトル、ページ内リンク-->
                <tr>
                    <td>
                         【原料情報】
                    </td>
                    <td align="right" height="10px">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">基本情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!--                         <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">試算項目</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">計算項目</a>
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!--試作原価情報一覧-->
            <!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
            <!-- <table cellpadding="0" cellspacing="0" border="0" width="970px" height="500px" style="table-layout:fixed;"> -->
            <table cellpadding="0" cellspacing="0" border="0" width="1130px" height="500px" style="table-layout:fixed;">
            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                <tr>
                <!-- 左側 -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                    	<!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                    	<!-- <td valign="top" style="width:470px;"> -->
                        <td valign="top" style="width:650px;">
                        <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--左側ヘッダー部-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:75px;" >選<br>択</th>
                                            <th class="columntitle" style="width:20px;height:75px;" >工<br>程</th>
                                            <th class="columntitle" style="width:105px;" >原料CD</th>
                                            <!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                                            <!-- <th class="columntitle" style="width:180px;" >原料名</th> -->
                                            <th class="columntitle" style="width:310px;" >原料名</th>
                                            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                                            <th class="columntitle" style="width:20px;" >変<br>更</th>
                                            <th class="columntitle" style="width:70px;" >単価<br>（円/㎏）<br>※</th>
                                            <th class="columntitle" style="width:45px;" >歩留<br>（％）<br>※</th>
                                        </tr>
                                        </table>
                                    </div>
                                </td></tr>
                                <tr>
                                    <td valign="top" height="422">
                                        <!--左側カラム部-->
                                        <div id="sclList1" style="height:416px;overflow:hidden;position:relative;">
                                            <div id="divGenryo_Left"></div>
                                        </div>
                                        <input class="normalbutton" type="button" name="btnBckMst" onClick="funBckMst()" style="width:153px" value="マスタ単価・歩留" tabindex="20" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <!-- 右側 -->
                        <td align="left" valign="top">
                        	<!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                        	<!-- <div class="scroll_genka" id="sclList2" style="width:510px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" /> -->
                            <div class="scroll_genka" id="sclList2" style="width:544px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
                            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                                <!-- 右側テーブル -->
                                <div id="divGenryo_Right">
                                </div>
                        </td>
                    </tr>

                    </table>
                </td></tr>
            </table>
            <br/><br/>
            <!--試算項目情報-->
            <a name="lnkShisan" />
            <table border="0" cellspacing="0" border="0" width="100%">
                <!--タイトル、ページ内リンク-->
                <tr>
                    <td>
                    <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!--                          【試算項目】 -->
                              【計算項目】
                    <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
                    </td>
                    <td align="right">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">基本情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="17" >原料情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="18">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!--             	【計算項目】&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- DEL 2014/8/7 shima【QP@30154】No.51 start -->
<!--             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- DEL 2014/8/7 shima【QP@30154】No.51 end -->
            <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
            <!-- 【QP@10713】シサクイック改良 No.4 start -->
            <!-- 固定費計算条件：　<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" />固定費/ケース　<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" />固定費/KG -->
            <!-- MOD 2013/11/11 okano【QP@30154】start -->
<!--             固定費計算条件：　<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />固定費/ケース　<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />固定費/KG -->
            <!-- DEL 2014/8/7 shima【QP@30154】No.51 start -->
            <!-- 固定費・利益計算条件：　<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />固定費・利益/ケース　<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />固定費・利益/KG -->
            <!-- DEL 2014/8/7 shima【QP@30154】No.51 end -->
            <!-- MOD 2013/11/11 okano【QP@30154】end -->
            <!-- 【QP@10713】シサクイック改良 No.4 end   -->

            <!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
            <!-- <table border="0" width="970px" height="400px"> -->
            <table border="0" width="1150px" height="400px">
            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                <tr>
                <!--製造工程-->
                <!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                <!-- <td align="left" valign="top" width="320"> -->
                <td align="left" valign="top" width="450">
                <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->

                    <!--QP@00412_シサクイック改良 No.38 start -->
                    製造工程　サンプルNo
                    <select name="ddlSeizoNo" id="ddlSeizoNo" onChange="seizo_output()" style="width:150px;height:16px;" tabindex="20" >
                    </select>
                    <!--QP@00412_シサクイック改良 No.38 end   -->

                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000">
                        <tr>
                        	<!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                            <!-- <td class="td_head" style="width:320px;text-align:center;" >製造工程</td> -->
                            <td class="td_head" style="width:450px;text-align:center;" >製造工程</td>
                            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                        </tr>
                        <tr>
                        <!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                        <!--
                        <td rowspan="1" style="width:320px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly style="width:320px;height:399px;" class="table_textarea" >
                         -->
                        <!-- MOD 2014/8/7 shima【QP@30154】No.50 start -->
                        <!-- <td rowspan="1" style="width:500px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly style="width:450px;height:399px;" class="table_textarea" > -->
                        <td rowspan="1" style="width:500px;height:473px;">
                                <textarea id="txtSeizoKotei" readonly style="width:450px;height:473px;" class="table_textarea" >
                        <!-- MOD 2014/8/7 shima【QP@30154】No.50 end -->
                        <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
                                </textarea>
                            </td>
                        </tr>
                    </table>
                </td>

                <!--一覧-->
                <td valign="top" align="left" width="724">
                    <table cellpadding="0" cellspacing="0" border="0"><tr>
                        <!-- MOD 2014/8/7 shima【QP@30154】No.51,No.77 start -->
                    	&nbsp;固定費・利益計算条件：　<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" onClick="funChangeMode(1);" />固定費・利益/ケース　<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" onClick="funChangeMode(2);" />固定費・利益/KG
                        <!-- MOD 2014/8/7 shima【QP@30154】No.51,No.77 end -->
                        <!-- 左側ヘッダー -->
                        <td valign="top">
                            <div class="scroll_genka" id="sclList3_2" style="width:146px;overflow:hidden;" rowSelect="false">
                            <table class="detail" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
<!-- ADD 2013/7/25 ogawa【QP@30151】No.13 start-->
                                <tr height="23"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;項目固定チェック</td></tr>
<!-- ADD 2013/7/25 ogawa【QP@30151】No.13 end-->

                                <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;サンプルNo</td></tr>
                                <!--QP@00412_シサクイック改良 No.38 start -->
	                            <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;製造工程版</td></tr>
	                            <!--QP@00412_シサクイック改良 No.38 end   -->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;試算日</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;充填量水相（ｇ）※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;充填量油相（ｇ）※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;合計量（ｇ）</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;比重※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;有効歩留（％）※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;レベル量（㎏）※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;平均充填量（㎏）※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;比重加算量（㎏）</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原料費（円）/ケース※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;材料費（円）/ケース※</td></tr>
                                <!-- 【QP@10713】20111031 hagiwara mod start -->
                                <!--<tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#f00">固定費（円）/ケース※</font></td></tr>-->
                                <tr height="18"><td class="td_head" id = "kotehi_case">&nbsp;&nbsp;&nbsp;&nbsp;固定費（円）/ケース※</td></tr>
                                <!-- 【QP@10713】20111031 hagiwara mod end -->
                                <!-- ADD 2013/11/1 okano【QP@30154】start-->
                                <tr height="18"><td class="td_head" id = "rieki_case">&nbsp;&nbsp;&nbsp;&nbsp;利益（円）/ケース※</td></tr>
                                <!-- ADD 2013/11/1 okano【QP@30154】end-->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/ケース※</td></tr>
                                <tr height="18"><td class="td_head">《参考：個あたり》</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/個※</td></tr>
                                <tr height="18"><td class="td_head">《参考：KGあたり》</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原料費（円）/㎏</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;材料費（円）/㎏</td></tr>
                                <!-- 【QP@10713】20111031 hagiwara mod start -->
                                <!--<tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color = "#f00">固定費（円）/㎏※</font></td></tr>-->
                                <tr height="18"><td class="td_head" id = "kotehi_kg">&nbsp;&nbsp;&nbsp;&nbsp;固定費（円）/㎏※</td></tr>
                                <!-- 【QP@10713】20111031 hagiwara mod end -->
                                <!-- ADD 2013/11/1 okano【QP@30154】start-->
                                <tr height="18"><td class="td_head" id = "rieki_kg">&nbsp;&nbsp;&nbsp;&nbsp;利益（円）/kg※</td></tr>
                                <!-- ADD 2013/11/1 okano【QP@30154】end-->
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/㎏※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;売価</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;粗利（％）</td></tr>
                            </table>
                            </div>
                        </td>
                        <!-- 右側カラム -->
                        <td>
                        	<!-- MOD 2013/7/2 shima【QP@30151】No.37 start -->
                        	<!-- <div class="scroll_genka" id="sclList3" style="width:500px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();"> -->
                            <div class="scroll_genka" id="sclList3" style="width:527px;overflow-x:scroll;overflow-y:hidden;" rowSelect="true" onScroll="Scroll2();">
                            <!-- MOD 2013/7/2 shima【QP@30151】No.37 end -->
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
                        <input class="normalbutton" type="button" id="btnSampleCopy" name="btnSampleCopy" onClick="funSampleCopy();" value="サンプルコピー" tabindex="15" />
                    </td>
                </tr>
            </table>
            <!-- ADD 20160622  KPX@502111_No.10 end -->

            <!--試作資材情報-->
            <a name="lnkShizai" />
            <table border="0" width="100%">
                <!--タイトル、ページ内リンク-->
                <tr>
                    <td>
                        【資材情報】
                    </td>
                    <td align="right" height="12">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="25" >基本情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="26" >原料情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 start -->
<!--                         <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >試算項目</a> -->
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >計算項目</a>
                        <!-- MOD 2013/9/6 okano【QP@30151】No.30 end -->
                    </td>
                </tr>
            </table>
            <hr>

            <!--資材テーブル-->
            <div id="divGenryoShizai">
            </div>

            <br>
            <!--一括選択-->
            <input type="checkbox" onClick="shizaiIkkatu()" id="chkIkkatuShizai" name="chkIkkatuShizai" tabindex="28" />一括選択
            <br>
            <div align="right">
     <!-- ADD 2014/5/1【QP@30297】start -->
                <!--コストテーブル参照ボタン-->
                <input type="button" class="normalbutton" id="btnCostTblRef" name="btnCostTblRef" value="コスト参照" style="width:80px;visibility:hidden;" onClick="funCostTblRef()" tabindex="29" />
     <!-- ADD 2014/5/1【QP@30297】end -->
                <!--類似品検索ボタン-->
                <input type="button" class="normalbutton" id="btnRuiziSearch" name="btnRuiziSearch" value="類似品検索" style="width:80px;" onClick="funRuiziSearch()" tabindex="29" />
                <!--資材削除ボタン-->
                <input type="button" class="normalbutton" id="btnShizaiDelete" name="btnShizaiDelete" value="資材削除" style="width:80px;" onClick="funDeleteShizai()" tabindex="30" />
            </div>
            <br><br><br><br>
            <br><br><br><br>
            <br><br><br><br>
            <br>

            <!-- 試算原料表示イベントボタン -->
            <input type="button" value="" name="BtnEveGenryo" id="BtnEveGenryo" onclick="funGenryoHyoji()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- 試算資材表示イベントボタン -->
            <input type="button" value="" name="BtnEveShizai" id="BtnEveShizai" onclick="funShizaiHyoji()" style="width:0px;height:0px;" tabindex="-1"/>
            <!-- 会社 -->
            <input type="hidden" value="" name="hdnKaisha" id="hdnKaisha">
            <!-- 工場 -->
            <input type="hidden" value="" name="hdnKojo" id="hdnKojo">
            <!-- 資材コードindex（検索用一時保管） -->
            <input type="hidden" value="" name="hdnShizai" id="hdnShizai">
            <!-- 資材表行数 -->
            <input type="hidden" value="" name="hdnShizaiCount" id="hdnShizaiCount">
            <!-- 原料、資材コード桁数 -->
            <input type="hidden" value="" name="hdnCdketasu" id="hdnCdketasu">
            <!-- 処理中 -->
            <input type="button" value="" name="BtnEveStart" id="BtnEveStart" onclick="funShowRunMessage()" style="width:0px;height:0px;" tabindex="-1" />
            <!-- 処理終了 -->
            <input type="button" value="" name="BtnEveEnd" id="BtnEveEnd" onclick="funClearRunMessage()" style="width:0px;height:0px;" tabindex="-1" />

            <!-- ADD start 20120615 hisahori -->
            <input type="hidden" id="hidsetSeqShisaku" name="hidsetSeqShisaku" value="">
            <input type="hidden" id="hidsetSampleNo" name="hidsetSampleNo" value="">
            <input type="hidden" id="hidsetShisanHi" name="hidsetShisanHi" value="">
            <input type="hidden" id="hidsetShisanChushi" name="hidsetShisanChushi" value="">
            <!-- ADD end 20120615 hisahori -->
            <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start -->
            <input type="hidden" id="hidsetChkKoumoku" name="hidsetChkKoumoku" value="">
            <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end -->
            <!-- 20160617  KPX@502111_No.5 ADD start -->
            <input type="hidden" name="hdnRenkeiSeqShisaku" id="hdnRenkeiSeqShisaku" value="">
            <!-- 20160617  KPX@502111_No.5 ADD end -->
            <!-- 20170515 KPX@1700856 ADD start -->
            <input type="hidden" name="hdnTankaZeroGenryo" id="hdnTankaZeroGenryo" value="">
            <!-- 20170515 KPX@1700856 ADD end -->

        </form>
    </body>
</html>
