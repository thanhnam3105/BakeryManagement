<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　コストテーブル登録・承認画面                                      -->
<!-- 作成者：TT.Sakamoto                                                             -->
<!-- 作成日：2014/02/25                                                              -->
<!-- 概要  ：検索条件に一致するコストデータを一覧で表示する。                        -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title></title>
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
        <script type="text/javascript" src="include/SQ190CostTblAdd.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script language="JavaScript" type="text/javascript">
            var myVersion;

            // ブラウザのバージョン
            // ブラウザ名取得
            myBsr   = navigator.appName;
            // エージェント取得
            myAgent = navigator.userAgent;
            // OP チェック
            myTop = myAgent.indexOf("Opera",0);
            // OPでない場合
            if (myTop == -1){

               // IE チェック
               myTop = myAgent.indexOf("MSIE",0);
               // IEでない場合
               if (myTop == -1){
                  // NN チェック
                  myTop = myAgent.indexOf("Mozilla/",0);
                  // NNでない場合
                  if (myTop == -1){
                     myVersion = "";
                  // NNの場合
                  }else{
                     // NNのバージョン切り取り
                     myLast = myAgent.indexOf(" ",myTop);
                     myVer = myAgent.substring(myTop+8,myLast);
                     myVersion = myVer;
                  }
               // IEの場合
               }else{
                  myLast = myAgent.indexOf(";",myTop);
                  // IEのバージョン切り取り
                  myVer = myAgent.substring(myTop+5,myLast);
                  myVersion = myVer;
               }
            // OPの場合
            }else{
               myBsr = "Opera";
               myLast = myAgent.indexOf(" ",myTop+6);
               // OPのバージョン切り取り
               myVer = myAgent.substring(myTop+6,myLast);
               myVersion = myVer;
            }

            // 【QP@40404】No.117 E.kitazawa 課題対応 --------------------- add start
            // Input[readonly] でBackSpaceを効かなくする  （IE固有の問題）
            window.document.onkeydown = function keydown(){
                if( window.event.keyCode == 8 ){
                    if(document.activeElement.readOnly){
                      return false;
                    }
                }
            }
            // 【QP@40404】No.117 E.kitazawa 課題対応 --------------------- add end

            </script>

        <script language="VBScript">
            dim javawsInstalled

            'MsgBox myVersion

            dim jws
            jws = split(ConCheckJWSver,ConDelimiter)

            Function funGetJWSInstall()

                on error resume next

                If myVersion = 6 Then
                'IE6の場合、JRE1.4.2ﾊﾞｰｼﾞｮﾝﾁｪｯｸ用のｵﾌﾞｼﾞｪｸﾄを生成する
                    If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.4.2.0"))) Then
                        javawsInstalled = 0
                    Else
                        javawsInstalled = 1
                    End If
                'ElseIf myVersion >= 7 Then
                    'IE8の場合、JRE1.5.0ﾊﾞｰｼﾞｮﾝﾁｪｯｸ用のｵﾌﾞｼﾞｪｸﾄを生成する
                    'If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.5.0.0"))) Then
                        'javawsInstalled = 0
                    'Else
                        'javawsInstalled = 1
                    'End If
                ElseIf myVersion >= 7 Then
                    'IE7以降の場合、Constに設定されているﾊﾞｰｼﾞｮﾝﾁｪｯｸ用のｵﾌﾞｼﾞｪｸﾄを生成する
                    dim i
                    i = 0
                    do
                        If Not(IsObject(CreateObject("JavaWebStart.isInstalled." & jws(i)))) Then
                           javawsInstalled = 0
                        Else
                           javawsInstalled = 1
                        End If

                        i = i + 1
                    Loop Until i > Ubound(jws) Or javawsInstalled = 1
                Else

                End If

                'ﾊﾞｰｼﾞｮﾝﾁｪｯｸ用のｵﾌﾞｼﾞｪｸﾄを生成する
                'If Not(IsObject(CreateObject("JavaWebStart.isInstalled.1.4.2.0"))) Then
                '    javawsInstalled = 0
                'Else
                '    javawsInstalled = 1
                'End If

            End Function
        </script>

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //処理中ﾒｯｾｰｼﾞ非表示
                funClearRunMessage();
            }
        </script>

        <!--  テーブル明細行クリック -->
        <script for="tblList" event="onclick" language="JavaScript">
            //選択行の背景色を変更
            funChangeSelectRowColor();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3010"></xml>
        <xml id="xmlRGEN3030"></xml>
        <xml id="xmlRGEN3050"></xml>
        <xml id="xmlRGEN3060"></xml>
        <xml id="xmlRGEN3080"></xml>
        <xml id="xmlRGEN3600"></xml>
        <xml id="xmlRGEN3610"></xml>
        <xml id="xmlRGEN3660"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3000I" src="../../model/FGEN3000I.xml"></xml>
        <xml id="xmlFGEN3010I" src="../../model/FGEN3010I.xml"></xml>
        <xml id="xmlFGEN3030I" src="../../model/FGEN3030I.xml"></xml>
        <xml id="xmlFGEN3050I" src="../../model/FGEN3050I.xml"></xml>
        <xml id="xmlFGEN3060I" src="../../model/FGEN3060I.xml"></xml>
        <xml id="xmlFGEN3080I" src="../../model/FGEN3080I.xml"></xml>
        <xml id="xmlFGEN3600I" src="../../model/FGEN3600I.xml"></xml>
        <xml id="xmlFGEN3610I" src="../../model/FGEN3610I.xml"></xml>
        <xml id="xmlFGEN3660I" src="../../model/FGEN3660I.xml"></xml>

        <xml id="xmlRGEN2160"></xml>
        <xml id="xmlFGEN3000O"></xml>
        <xml id="xmlFGEN3010O"></xml>
        <xml id="xmlFGEN3030O"></xml>
        <xml id="xmlFGEN3050O"></xml>
        <xml id="xmlFGEN3060O"></xml>
        <xml id="xmlFGEN3080O"></xml>
        <xml id="xmlFGEN3600O"></xml>
        <xml id="xmlFGEN3610O"></xml>
        <xml id="xmlFGEN3660O"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- // 【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add start -->
        <!-- 全件検索対応 -->
        <xml id="xmlRGEN3110"></xml>
        <xml id="xmlFGEN3110"></xml>
        <xml id="xmlFGEN3110I" src="../../model/FGEN3110I.xml"></xml>
        <xml id="xmlFGEN3110O"></xml>
        <!-- // 【QP@30297】No.22 2014/08/22 E.kitazawa 課題対応 --------------------- add end -->

        <!-- コンボボックスにデフォルトで設定されている値の退避用XML -->
        <xml id="xmlFGEN3000"></xml>
        <xml id="xmlFGEN3010"></xml>
        <xml id="xmlFGEN3610"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">コストテーブル登録・承認</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnNext" name="btnNext" value="終了" onClick="funNext();" tabindex="13">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

          <div id="costInfo" style="border:double 4px;padding:4px;border-color:#4169E1;width:85%;">
            <table datasrc="#xmlRGEN3040I" datafld="rec">
                <caption align="left">【検索ベース単価情報】</caption>
                <!-- 1列目 -->
                <tr>
                    <td width="87">メーカー名</td>
                    <td width="200">
                        <select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" tabindex="1" onChange="funChangeMaker();">
                        </select>
                    </td>
                    <td width="100">ベース包材名</td>
                    <td width="400">
                        <select id="ddlHouzai" name="ddlHouzai" datafld="cd_houzai" style="width:360px;" tabindex="2" onChange="funChangeBaseHouzai();">
                        </select>
                    </td>
                    <td width="150">ベース版数・有効開始日</td>
                    <td width="150">
                        <select id="ddlBaseHansu" name="ddlBaseHansu" datafld="no_basehouzai" style="width:150px;" tabindex="3" onChange="funBaseSearch();">
                        </select>
<!--                         <span class="ninput" format="9" comma="false"> -->
<!--                         <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="text-align:right;width:180px;" tabindex="3" onChange="return funExistData();"> -->
<!--                         </span> -->
                    </td>
<!--                     <td width="70">&nbsp;&nbsp;版数</td> -->
<!--                     <td width="70"> -->
<!--                         <span class="ninput" format="9" comma="false"> -->
<!--                         <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="text-align:right;width:50px;" tabindex="4" onChange="return funExistData();"> -->
<!--                         </span> -->
<!--                     </td> -->
                </tr>
            </table>
          </div>

           <table width="99%">
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </table>

         <div id="costInfo" style="border:double 4px;padding:4px;border-color:#4169E1;width:85%;">
            <table datasrc="#xmlRGEN3040I" datafld="rec">
                <caption align="left">【入力コストテーブル情報】</caption>
                <tr>
                    <td width="92">コストテーブル版数</td>
                    <td width="70">
                        <span class="ninput" format="4" comma="false">
                        <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="4" value="" style="text-align:right;width:50px;" tabindex="4" >
                        </span>
                    </td>
                    <td width="100">有効開始日</td>
                    <td width="200">
                        <input type="text" class="disb_text" id="txtYuko" name="txtYuko"  datafld="dt_yuko" maxlength="10" value="" style="width:180px;" tabindex="4">
                    </td>
                    <td width="100">個装使用量</td>
                    <td width="100">
                        <!-- //  数値、ピリオドのみ入力許可する -->
                        <span class="ninput" format="10,3" comma="false">
                            <input type="text" class="disb_text" id="txtShiyoRyo" name="txtShiyoRyo" maxlength="21" value="" style="text-align:right;" tabindex="5" onblur="this.value = funAddComma(this.value); return true;">
                        </span>
                    </td>
                    <td width="80">
                        <input type="button" class="normalbutton" id="btnCalc" name="btnCalc" value="計算" onClick="funCalc();" tabindex="6" style="width:60px;" tabindex="6">

                    </td>
                    <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
                    <td width="100">アップ率（％）</td>
                    <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
                    <td width="100">
                        <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
                        <input type="text" class="disb_text" id="txtUpRitu" name="txtUpRitu" maxlength="5" value="" style="text-align:right;" tabindex="7" onblur="this.value = funNumFormatChange(this.value,1); return true;">
                        <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
                    </td>
                    <td width="80">
                        <input type="button" class="normalbutton" id="btnUpRituCalc" name="btnUpRituCalc" value="計算" onClick="funUpRituCalc();" tabindex="8" style="width:60px;">
                    </td>
                    <td width="185"></td>
                    <td width="80">未使用
                    	<input type="checkbox" id="chkMishiyo" name="chkMishiyo" tabindex="9">
                    </td>
                </tr>
            </table>

            <!-- //add start【QP@30297】課題 hisahori -->
            <table width="99%">
                <tr>
                	<td width="100">コスト包材名</td>
                	<td align="left">
                		<textarea class="disb_text" name="costHouzai" id="costHouzai" maxlength="10" cols="50" rows="2" style="width:1200px;" tabindex="9"></textarea>
<!--                 		<input type="text" class="" id="" datafld="" maxlength="200" value="" style="width:1200px" tabindex="9"> -->
<!--                 		<input type="button" class="normalbutton" id="houzaiTouroku" name="houzaiTouroku" value="包材登録" onClick="" tabindex="10" style="width:60px;"> -->
                	</td>
<!--                     <td align="right">単位（円）　　</td> -->
                </tr>
            </table>
            <!-- //add end【QP@30297】課題 hisahori -->
          </div>

           <table width="99%">
                <tr>
                    <td align="right">単位（円）　　</td>
                </tr>
            </table>

            <!-- [コストテーブル一覧]リスト -->
            <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
            <div class="scroll" id="sclList" style="height:42%;width:99%;" rowSelect="true">
            <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="1880px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <% for(int col = 0; col < 30; col++) { %>
                    <col style="width:50px;"/>
                    <% } %>
                </colgroup>
                <thead class="rowtitle" id="tblHeader">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">&nbsp;&nbsp;</th>
                        <th class="columntitle" style="text-align:right;">ロット数</th>
                        <% for(int col = 0; col < 30; col++) { %>
                        <th class="columntitle" style="text-align:right;">
                        	<!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
                            <input type="text" class="disb_text" id="txtHeader_<%=(col + 1)%>" style="width:50px;border-width:0px;text-align:right;" onkeyDown="return funNumOnly();" maxlength="13" onblur="this.value = funAddComma(this.value); return true;">
                            <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
                        </th>
                        <% } %>
                    </tr>
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" style="text-align:right;">№</th>
                     <!-- 2017/3/16 TOs_tamura delete start -->
                        <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
                        <!--<th class="columntitle" style="text-align:left;">色数／使用量</th>-->
                        <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
                     <!-- 2017/3/16 TOs_tamura delete end -->
                     
                     <!-- 2017/3/16 TOs_tamura add start -->
                        <th class="columntitle" style="text-align:left;">色数&nbsp;使用量→　　　　　　　&nbsp;↓</th>
                     <!-- 2017/3/16 TOs_tamura add end -->
                     
                        <% for(int col = 0; col < 30; col++) { %>
                        <th class="columntitle" style="text-align:right;"><input type="text" class="disb_text" id="calcRslt<%=(col + 1)%>" style="width:50px;border-width:0px;text-align:right;" readonly></th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:1880px;display:none">
                        <tr class="disprow">
                        <% for(int row = 0; row < 30; row++) { %>
                        <tr class="disprow">
                            <td class="column" style="width:30px;text-align:right;"><%=(row + 1)%></td>
                            <td class="column" style="width:100px;text-align:left;">
                              <!-- // 【QP@30297】No.25 2014/08/20  E.kitazawa 課題対応 --------------------- mod start -->
                                <!-- input type="text" class="act_text" id="nm_title_<%=(row + 1)%>" style="background-color:transparent;width:100px;border-width:0px;text-align:left;" onkeyDown="funCheckChange(); return true;" -->
                                <input type="text" class="act_text" id="nm_title_<%=(row + 1)%>" style="background-color:transparent;width:100px;border-width:0px;text-align:left;" onkeyDown="funCellIdou(event.keyCode); funCheckChange(); return true;">
                              <!-- // 【QP@30297】No.25 2014/08/20  E.kitazawa 課題対応 --------------------- mod start -->
                            </td>
                            <% for(int col = 0; col < 30; col++) { %>
                            <td class="column" style="width:50px;">
	                          <!-- // 【QP@30297】No.23,25 2014/08/20  E.kitazawa 課題対応 --------------------- mod start -->
	                            <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
                                <!-- input type="text" id="no_value_<%=(row + 1)%>_<%=(col + 1)%>" class="disb_text" style="background-color:transparent;width:50px;border-width:0px;text-align:right;" maxlength="16" onblur="this.value = funAddComma(funNumFormatChange(this.value,3)); return true;" -->
                                <span class="ninput" format="10,2" comma="false">
                                    <input type="text" id="no_value_<%=(row + 1)%>_<%=(col + 1)%>" class="disb_text" style="background-color:transparent;width:50px;border-width:0px;text-align:right;" maxlength="16" onkeydown="funCellIdou(event.keyCode);" onblur="this.value = funAddComma(funNumFormatChange(this.value,2)); return true;">
                                </span>
                                <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->
                              <!-- // 【QP@30297】No.23,25 2014/08/20  E.kitazawa 課題対応 --------------------- mod end -->
                            </td>
                            <% } %>
                        </tr>
                        <% } %>
                    </table>
                </table>
            </table>
            </div>

			<!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod start -->
            <table style="margin-top:10px;">
                <tr>
                    <td width="80">備考（原調）</td>
                    <td>
                        <!-- // 【QP@30297】No.24 2014/08/21  E.kitazawa 課題対応 --------------------- mod start -->
                        <!-- input type="text" class="act_text" id="txtBiko" name="txtBiko" maxlength="50" value="" style="width:500px;" onKeyPress="return funCheckChange();" tabindex="9" -->
                        <input type="text" class="act_text" id="txtBiko" name="txtBiko" maxlength="200" value="" style="width:1200px;" onKeyPress="return funCheckChange();" tabindex="9">
                        <!-- // 【QP@30297】No.24 2014/08/21  E.kitazawa 課題対応 --------------------- mod end -->
                    </td>
                </tr>
                <tr>
                    <td width="80">備考（工場）</td>
                    <td>
                        <!-- // 【QP@30297】No.24 2014/08/21  E.kitazawa 課題対応 --------------------- mod start -->
                        <!-- input type="text" class="act_text" id="txtBiko_kojo" name="txtBiko_kojo" maxlength="50" value="" style="width:500px;" onKeyPress="return funCheckChange();" tabindex="9" -->
                        <input type="text" class="act_text" id="txtBiko_kojo" name="txtBiko_kojo" maxlength="200" value="" style="width:1200px;" onKeyPress="return funCheckChange();" tabindex="10">
                        <!-- // 【QP@30297】No.24 2014/08/21  E.kitazawa 課題対応 --------------------- mod end -->
                    </td>
                </tr>
            </table>
            <!-- // 【QP@30297】 TT.nishigawa 課題対応 --------------------- mod end -->

            <table style="margin-top:10px;">
                <tr>
                    <td width="10"><input type="checkbox" id="chk_kakunin" name="chkKakunin" tabindex="11" onClick="funCheckData();"></td>
                    <td width="46">登録者：</td>
                    <td width="120" id="lblKakunin">－</td>
                    <td width="10"><input type="checkbox" id="chk_shonin" name="chkShonin" tabindex="12"></td>
                    <td width="46">承認者：</td>
                    <td width="120"id="lblShonin">－</td>
                </tr>
            </table>

            <!-- table width="870" style="margin-top:10px;clear:left;" -->
            <table width="400" style="margin-top:10px;clear:left;">
                <tr>
                    <td>
                        <input type="button" class="normalbutton" id="btnDataEdit" name="btnDataEdit" value="登録" onClick="funDataEdit(1);" tabindex="13">
                    </td>
                    <!-- // 【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add start -->
                    <td>
                        <input type="button" class="normalbutton" id="btnDataDelete" name="btnDataDelete" value="削除" onClick="funDataDelete();"  tabindex="14">
                    </td>
                    <!-- // 【QP@30297】No.22 2014/08/22  E.kitazawa 課題対応 --------------------- add end -->
                </tr>
            </table>
        </form>
    </body>
</html>
