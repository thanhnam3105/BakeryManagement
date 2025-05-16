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
        }
        function Scroll2() {
            //X方向のスクロール移動
            document.getElementById("sclList2").scrollLeft = document.getElementById("sclList3").scrollLeft;
        }
    -->
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
        <!-- スタイルシート -->
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
        
        <!--  資材テーブル行クリック -->
        <script for="tblList4" event="onclick" language="JavaScript">
            //選択行の背景色を変更
            funChangeSelectRowColor2();
        </script>
        
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
                        <a href="GenkaShisan_dtl.jsp#lnkShisan"  tabindex="1">試算項目</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai"  tabindex="1">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>
            
            <table border="0" width="975px" height="500px">
                <tr><td align="left" valign="top" width="370">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" style="table-layout:fixed;">
                        <!-- 試作表部 -->
                        <tr>
                            <!-- 1行目 -->
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
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                研究所担当チーム
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTeam" name="txtTeam" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 3行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                一括表示
                            </td>
                            <td height="19px">
                                <input type="text" id="txtIkkatu" name="txtIkkatu" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                ユーザー
                            </td>
                            <td height="19px">
                                <input type="text" id="txtUser" name="txtUser" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 5行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                用途
                            </td>
                            <td height="19px"><input type="text" id="txtYouto" name="txtYouto" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr>
                        <!-- 原価試算条件部 -->
                        <tr>
                            <!-- 1行目 -->
                            <td class="td_head" rowspan="21" style="width:18px;writing-mode:tb-rl;">原価試算条件</td>
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
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                担当営業
                            </td>
                            <td height="19px">
                                <input type="text" id="txtTantoEigyo" name="txtTantoEigyo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 4行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                製造方法
                            </td>
                            <td height="19px"><input type="text" id="txtSeizohoho" name="txtSeizohoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 5行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                充填方法
                            </td>
                            <td height="19px"><input type="text" id="txtJutenhoho" name="txtJutenhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 6行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                殺菌方法
                            </td>
                            <td height="19px"><input type="text" id="txtSakinhoho" name="txtSakinhoho" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 7行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                容器・包材
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouki" name="txtYouki" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 8行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                容量（数値入力）
                            </td>
                            <td height="19px">
                                <input type="text" id="txtYouryo" name="txtYouryo" class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 9行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                入り数（数値入力）※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtIrisu" name="txtIrisu" class="table_text_disb" onblur="setFg_saikeisan();setKanma(this)" value="" tabindex="3" />
                            </td>
                        </tr><tr>
                            <!-- 10行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                荷姿
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtNisugata" name="txtNisugata" style="ime-mode:active;" class="table_text_act" value="" tabindex="4" />
                            </td>
                        </tr><tr>
                            <!-- 11行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                取扱温度
                            </td>
                            <td height="19px"><input type="text" id="txtOndo" name="txtOndo" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 12行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                賞味期間
                            </td>
                            <td height="19px"><input type="text" id="txtShomiKikan" name="txtShomiKikan" class="table_text_view" readonly value="" tabindex="-1" /></td>
                        </tr><tr>
                            <!-- 13行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                希望原価※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtGenkaKibo" name="txtGenkaKibo" style="width:112px;height:20px" class="table_text_disb" onblur="setFg_saikeisan();setKanma(this)" value="" tabindex="5" />
                                <select name="ddlGenkaTani" id="ddlGenkaTani" onChange="setFg_saikeisan();baikaTaniSetting()" style="width:82px;height:16px;" tabindex="6" >
                                </select>
                            </td>
                        </tr><tr>
                            <!-- 14行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                希望売価※
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtBaikaKibo" name="txtBaikaKibo" onblur="setFg_saikeisan();setKanma(this)" style="width:112px;" class="table_text_disb"  value="" tabindex="7" />
                                <input type="text" id="txtBaikaTani" name="txtBaikaTani" style="width:82px;"class="table_text_view" readonly value="" tabindex="-1" />
                            </td>
                        </tr><tr>
                            <!-- 15行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                想定物量
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtSoteiButuryo" style="ime-mode:active;" name="txtSoteiButuryo" class="table_text_act" value="" tabindex="8" />
                            </td>
                        </tr><tr>
                            <!-- 16行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                販売時期
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaiJiki"style="ime-mode:active;" name="txtHanbaiJiki" class="table_text_act" value="" tabindex="9" />
                            </td>
                        </tr><tr>
                            <!-- 17行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                計画売上
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuUriage" style="ime-mode:active;" name="txtKeikakuUriage" class="table_text_act" value="" tabindex="10" />
                            </td>
                        </tr><tr>
                            <!-- 18行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                計画利益
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtKeikakuRieki" style="ime-mode:active;" name="txtKeikakuRieki" class="table_text_act" value="" tabindex="11" />
                            </td>
                        </tr><tr>
                            <!-- 19行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                販売後売上
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoUriage" style="ime-mode:active;" name="txtHanbaigoUriage" class="table_text_act" value="" tabindex="12" />
                            </td>
                        </tr><tr>
                            <!-- 20行目 -->
                            <td class="td_head" height="19px">
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                販売後利益
                            </td>
                            <td bgcolor="" height="19px">
                                <input type="text" id="txtHanbaigoRieki" style="ime-mode:active;" name="txtHanbaigoRieki" class="table_text_act" value="" tabindex="13" />
                            </td>
                        </tr><tr>
                            <!-- 21行目 -->
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
                <td align="left" valign="top">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000" height="100%">
                        <tr>
                            <td class="td_head" width="565px">研究所連絡（総合メモ）</td>
                        </tr>
                        <tr>
                            <td style="width:565px;height:49%;">
                                <textarea id="txtSogoMemo" name="txtSogoMemo" class="table_textarea" readonly style="background-color:#ffffff;" tabindex="-1" >
                                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_head" width="565px">原価試算メモ</td>
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
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="16">試算項目</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>
            <!--試作原価情報一覧-->
            <table cellpadding="0" cellspacing="0" border="0" width="970px" height="500px" style="table-layout:fixed;">
                <tr>
                <!-- 左側 -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td valign="top" style="width:470px;">
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--左側ヘッダー部-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:73px;" >選<br>択</th>
                                            <th class="columntitle" style="width:20px;height:73px;" >工<br>程</th>
                                            <th class="columntitle" style="width:105px;" >原料CD</th>
                                            <th class="columntitle" style="width:180px;" >原料名</th>
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
                            <div class="scroll_genka" id="sclList2" style="width:510px;height:511px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
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
                         【試算項目】
                    </td>
                    <td align="right">
                        <a href="GenkaShisan_dtl.jsp#lnkKihon" tabindex="16">基本情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkGenka" tabindex="16" >原料情報</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="GenkaShisan_dtl.jsp#lnkShizai" tabindex="17">資材情報</a>
                    </td>
                </tr>
            </table>
            <hr>
            【計算項目】&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            固定費計算条件：　<input type="radio" id="radioKoteihi"  name="radioKoteihi" value="1" tabindex="20" />固定費/ケース　<input type="radio" id="radioKoteihi" name="radioKoteihi" value="2" tabindex="20" />固定費/KG
            <table border="0" width="970px" height="400px">
                <tr>
                <!--製造工程-->
                <td align="left" valign="top" width="320">
                    <table  border="1" cellspacing="0" cellpadding="0" bordercolor="#000000">
                        <tr>
                            <td class="td_head" style="width:330px;text-align:center;" >製造工程</td>
                        </tr>
                        <tr>
                            <td rowspan="1" style="width:330px;height:399px;">
                                <textarea id="txtSeizoKotei" readonly  class="table_textarea" >
                                </textarea>
                            </td>
                        </tr>
                    </table>
                </td>
                
                <!--一覧-->
                <td valign="top" align="left" width="724">
                    <table cellpadding="0" cellspacing="0" border="0"><tr>
                        <!-- 左側ヘッダー -->
                        <td valign="top">
                            <div class="scroll_genka" id="sclList3_2" style="width:146px;overflow:hidden;" rowSelect="false">
                            <table class="detail" cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
                                <tr height="18"><td class="td_head" style="width:140px;">&nbsp;&nbsp;&nbsp;&nbsp;サンプルNo</td></tr>
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
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ff0000">固定費（円）/ケース※</font></td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/ケース※</td></tr>
                                <tr height="18"><td class="td_head">《参考：個あたり》</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/個※</td></tr>
                                <tr height="18"><td class="td_head">《参考：KGあたり》</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原料費（円）/㎏</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;材料費（円）/㎏</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ff0000">固定費（円）/㎏※</font></td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;原価計（円）/㎏※</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;売価</td></tr>
                                <tr height="18"><td class="td_head">&nbsp;&nbsp;&nbsp;&nbsp;粗利（％）</td></tr>
                            </table>
                            </div>
                        </td>
                        <!-- 右側カラム -->
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
                        <a href="GenkaShisan_dtl.jsp#lnkShisan" tabindex="26" >試算項目</a>
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
            
            
        </form>
    </body>
</html>
