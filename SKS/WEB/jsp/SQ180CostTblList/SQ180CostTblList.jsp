<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　コストテーブル一覧画面                                            -->
<!-- 作成者：TT.Sakamoto                                                             -->
<!-- 作成日：2014/02/25                                                              -->
<!-- 概要  ：検索条件に一致するコストデータを一覧で表示する。                        -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

            document.getElementById("xmlRGEN3000").src = null;
            document.getElementById("xmlFGEN3000I").src = null;
            document.getElementById("xmlFGEN3000O").src = null;

            <!-- // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start -->
            document.getElementById("xmlRGEN3010").src = null;
            <!-- // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end -->
            document.getElementById("xmlFGEN3010I").src = null;
            document.getElementById("xmlFGEN3010O").src = null;

            document.getElementById("xmlRGEN3020").src = null;
            document.getElementById("xmlFGEN3020I").src = null;
            document.getElementById("xmlFGEN3020O").src = null;

            document.getElementById("xmlRGEN3040").src = null;
            document.getElementById("xmlFGEN3040O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;
            document.getElementById("xmlUSERINFO_O").src = null;
            document.getElementById("xmlRESULT").src = null;

            document.getElementById("xmlFGEN3030I").src = null;
            document.getElementById("xmlRGEN2160").src = null;
            document.getElementById("xmlFGEN3030O").src = null;
        }
    // -->
    </script>

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
        <script type="text/javascript" src="include/SQ180CostTblList.js"></script>
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
        <script for="tblList" event="ondblclick" language="JavaScript">
            //選択行ダブルクリックによる「登録・承認」画面遷移
            funOpenConstTblAdd(2);
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3000"></xml>
        <!-- // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start -->
        <xml id="xmlRGEN3010"></xml>
        <!-- // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add end -->
        <xml id="xmlRGEN3020"></xml>
        <xml id="xmlRGEN3040"></xml>
        <xml id="xmlRGEN3070"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3000I" src="../../model/FGEN3000I.xml"></xml>
        <xml id="xmlFGEN3010I" src="../../model/FGEN3010I.xml"></xml>
        <xml id="xmlFGEN3020I" src="../../model/FGEN3020I.xml"></xml>
        <xml id="xmlFGEN3030I" src="../../model/FGEN3030I.xml"></xml>
        <xml id="xmlFGEN3070I" src="../../model/FGEN3070I.xml"></xml>
        <xml id="xmlRGEN2160"></xml>
        <xml id="xmlFGEN3070"></xml>
        <xml id="xmlFGEN3000O"></xml>
        <xml id="xmlFGEN3010O"></xml>
        <xml id="xmlFGEN3020O"></xml>
        <xml id="xmlFGEN3030O"></xml>
        <xml id="xmlFGEN3040O"></xml>
        <xml id="xmlFGEN3070O"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <!-- // 【QP@30297】No.21 2014/08/21  E.kitazawa 課題対応 --------------------- add start -->
        <!-- 全件検索対応 -->
        <xml id="xmlRGEN3100"></xml>
        <xml id="xmlFGEN3100"></xml>
        <xml id="xmlFGEN3100I" src="../../model/FGEN3100I.xml"></xml>
        <xml id="xmlFGEN3100O"></xml>
        <!-- // 【QP@30297】No.21 2014/08/21  E.kitazawa 課題対応 --------------------- add end -->

        <!-- 未使用検索対応 //【KPX@1602367】add -->
        <xml id="xmlRGEN3710"></xml>
        <xml id="xmlFGEN3710I" src="../../model/FGEN3710I.xml"></xml>
        <xml id="xmlFGEN3710O"></xml>

        <!-- コンボボックスにデフォルトで設定されている値の退避用XML -->
        <xml id="xmlFGEN3000"></xml>
        <xml id="xmlFGEN3010"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">コストテーブル一覧</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSearch();" tabindex="7">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funNext(0);" tabindex="8">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="900" datasrc="#xmlFGEN3020I" datafld="rec">
                <!-- 1列目 -->
                <tr>
                    <td width="100">メーカー名</td>
                    <td width="200">
                        <select id="ddlMakerName" name="ddlMakerName" datafld="cd_maker" style="width:180px;" onChange="funChangeMaker();" tabindex="1">
                        </select>
                    </td>
                    <td width="50">包材名</td>
                    <td width="400">
                        <select id="ddlHouzai" name="ddlHouzai" datafld="cd_houzai" style="width:360px;" tabindex="2">
                        </select>
                    </td>
                    <td width="50">版数</td>
                    <td width="70">
                        <span class="cinput" format="0000000000" required="true" defaultfocus="false" id="em_SeihouNo">
                        <input type="text" class="disb_text" id="txtHansu" name="txtHansu" datafld="no_hansu" maxlength="16" value="" style="width:50px;text-align:right;" tabindex="3">
                        </span>
                    </td>
                </tr>
            </table>

            <!-- 入力・選択 -->
            <table width="471" datasrc="#xmlFGEN3020I" datafld="rec">
                <!-- 2列目 -->
                <tr>
                    <td width="90" >登録済み
                        <input type="checkbox" id="chkKakunin" datafld="chk_kakunin" name="chkKakunin" tabindex="4">
                    </td>
                    <td width="90" >承認済み
                        <input type="checkbox" id="chkShonin" datafld="chk_shonin" name="chkShonin" tabindex="5">
                    </td>
                    <td width="64" >全件
                        <input type="checkbox" id="chkAll" name="chkAll" tabindex="6">
                    </td>
                    <td width="102" >有効版のみ
                    	<input type="checkbox" id="chkHanOnly" name="chkHanOnly" tabindex="7" checked>
                    </td>
                    <td width="90" >未使用
                    	<input type="checkbox" id="chkMishiyo" name="chkMishiyo" tabindex="8">
                    </td>
                </tr>
            </table>

            <!-- [コストテーブル一覧]リスト -->
            <div class="scroll" id="sclList" style="height:65%;width:1470px;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" cellpadding="3" width="1450px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:160px;"/>
                    <col style="width:30px;"/>
                    <col style="width:400px;"/>
                    <col style="width:120px;"/>
                    <col style="width:120px;"/>
                    <col style="width:90px;"/>
                    <col style="width:80px;"/>
                    <col style="width:400px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">№</th>
                        <th class="columntitle">メーカー名</th>
                        <th class="columntitle">版<BR>数</th>
                        <th class="columntitle">版の包材名</th>
                        <th class="columntitle">登録者</th>
                        <th class="columntitle">承認者</th>
                        <th class="columntitle">有効開始日</th>
                        <th class="columntitle">変更日</th>
                        <th class="columntitle">ベース包材名</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="3" cellspacing="0" border="1" bordercolor="#C0C0C0" style="width:1450px;display:none">
                        <!-- [コストテーブル一覧] 明細行 -->
                        <tbody id="detail"></tbody>
                    </table>
                </table>
            </table>
            </div>

            <table width="870" style="margin-top:15px;">
                <tr>
                    <td>
                    <!-- KPX@1602367 削除20160926
                        <input type="button" class="normalbutton" id="btnCopy" name="btnCopy" value="コピー" onClick="funOpenConstTblAdd(3);" disabled tabindex="-1">
                      -->
                        <input type="button" class="normalbutton" id="btnNew" name="btnNew" value="登録" onClick="funOpenConstTblAdd(2);" disabled tabindex="-1">
                        <input type="button" class="normalbutton" id="btnShonin" name="btnShonin" value="承認" onClick="funOpenConstTblAdd(4);" disabled tabindex="-1">
                    </td>
                </tr>
            </table>
            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode">
        </form>
    </body>
</html>
