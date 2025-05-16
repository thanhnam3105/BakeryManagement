<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　デザインスペース登録画面                                          -->
<!-- 作成者：TT.Kitazawa                                                             -->
<!-- 作成日：2014/09/01                                                              -->
<!-- 概要  ：検索条件に一致するデザインスペース一覧を表示する。                      -->
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
		<script type="text/javascript" src="include/SQ310HattyuLiteralMst.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

	<style>


	</style>
    <script type="text/javascript">
    <!--
        window.onunload = function() {


            <!-- // 【QP@30297】No.19 E.kitazawa 課題対応 --------------------- add start -->
            //document.getElementById("RGEN3590").src = null;
            document.getElementById("xmlFGEN3580O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;

            document.getElementById("xmlSA890I").src = null;
        }
    // -->
    </script>

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
       <!--
        <script for="tblList" event="onclick" language="JavaScript">

            //選択行の背景色を変更
            funChangeSelectRowColor();

            var frm = document.frm00;    //ﾌｫｰﾑへの参照
        	// 選択行のラジオボタンにチェックを入れる
        	if (!!frm.chk[funGetCurrentRow()]) {
        		frm.chk[funGetCurrentRow()].checked = true;
        	} else {
        		// Index が付いていない
        		frm.chk.checked = true;
        	}

        </script>
        -->
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <!-- 新規追加20160912 -->
        <xml id="xmlJSP0611"></xml>  <!-- 610→611 変更済み -->
        <xml id="xmlJSP0621"></xml>  <!-- 620→621 変更済み -->
        <xml id="xmlJSP0631"></xml>  <!-- 630→631 変更済み -->
        <xml id="xmlJSP0632"></xml>  <!-- 追加 -->
        <xml id="xmlJSP0641"></xml>  <!-- 640→641 変更済み -->
        <xml id="xmlJSP0650"></xml>
        <xml id="xmlRGENAAA"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA041I" src="../../model/SA041I.xml"></xml>  <!-- 40→41 変更済み -->
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA101I" src="../../model/SA101I.xml"></xml>  <!-- 100→101 変更済み -->
        <xml id="xmlSA102I" src="../../model/SA102I.xml"></xml>  <!-- 新規追加 -->
        <xml id="xmlSA111I" src="../../model/SA111I.xml"></xml>  <!-- 110→111 変更済み -->
        <xml id="xmlSA300I" src="../../model/SA300I.xml"></xml>
        <xml id="xmlSA320I" src="../../model/SA320I.xml"></xml>
        <xml id="xmlSA331I" src="../../model/SA331I.xml"></xml>  <!-- 330→331 変更済み -->
        <xml id="xmlSA890I" src="../../model/SA890I.xml"></xml>
        <xml id="xmlFGEN3630I" src="../../model/FGEN3630I.xml"></xml>
        <xml id="xmlFGEN3580I" src="../../model/FGEN3580I.xml"></xml>
        <xml id="xmlFGEN3650I" src="../../model/FGEN3650I.xml"></xml>
        <xml id="xmlFGEN3670I" src="../../model/FGEN3670I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA041O"></xml>  <!-- 40→41 変更済み -->
        <xml id="xmlSA050O"></xml>
        <xml id="xmlSA101O"></xml>  <!-- 100→101 変更済み -->
        <xml id="xmlSA102O"></xml>  <!-- 新規追加 -->
        <xml id="xmlSA111O"></xml>  <!-- 110→111 変更済み -->
        <xml id="xmlSA300O"></xml>
        <xml id="xmlSA320O"></xml>
        <xml id="xmlSA331O"></xml>  <!-- 330→331 変更済み -->
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3580"></xml>
        <!-- 一覧 -->
        <xml id="xmlRGEN3580"></xml>
        <xml id="xmlFGEN3580O"></xml>

		<xml id="xmlFGEN3630"></xml>
		<xml id="xmlFGEN3630O"></xml>
		<xml id="xmlRGEN3630"></xml>


		<xml id="xmlFGEN3650O"></xml>
		<xml id="xmlRGEN3650"></xml>
		<xml id="xmlFGEN3670"></xml>
		<xml id="xmlFGEN3670O"></xml>
		<xml id="xmlRGEN3670"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
             <table width="99%">
                <tr>
                    <td width="20%" class="title">発注先マスタ</td>
                    <td width="15%">&nbsp;</td>
                    <td width="65%" align="right">
                    <!-- 検索ボタン -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSearch();" tabindex="11">
					<!-- 登録ボタン -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="登録" onClick="funUpdate();"  disabled tabindex="12">
					<!-- Excelボタン -->
                        <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel" onClick="funFileExcel();" tabindex="13">
                    <!-- 行追加ボタン -->
                        <input type="button" class="normalbutton" id="btnLineAdd" name="btnLineAdd" value="行追加" onClick="funLineAdd();" tabindex="14">
                    <!-- 行削除ボタン -->
 <!--                       <input type="button" class="normalbutton" id="btnLineDel" name="btnLineDel" value="行削除" onClick="funLineDel();" tabindex="15">-->
                    <!-- 終了ボタン -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funNext();" tabindex="16">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="800">
                <!-- 1行目 -->
                <tr>
                    <td width="300">発注先コード
                    	<span class="ninput" format="10,3" comma="false">
                    		<input type="text" value="" name="cdhattyu" id="cdhattyu" onchange="hattyuusakiFocusOut();" style="width:200px;" tabindex="1">
                    	</span>
                    </td>
                    <td width="300">発注先名<input type="text" value="" name="nmhattyu" id="nmhattyu" onchange="hattyuusakiFocusOut();" style="width:200px;" tabindex="2">
                    </td>
                </tr>
              </table>
            <br/>

            <hr>


            <!-- [デザインスペース情報一覧]リスト width:870 → 1170px -->
            <div class="scroll" id="sclList" style="height:60%;width:1250px;overflow:scroll;" rowSelect="true">
	            <table id="dataTable" name="dataTable" cellspacing="0" width="1230px" align="center">
	                <colgroup>
	                <!--<col style="width:30px;"/> -->
	                    <col style="width:100px;"/>
	                    <col style="width:220px;"/>
	                    <col style="width:60px;"/>
	                    <col style="width:60px;">
	                    <col style="width:380px;"/>
	                    <col style="width:65px;"/>
	                    <col style="width:270px;"/>
	                    <col style="width:60px;"/>
	                </colgroup>
	                <thead class="rowtitle">
	                    <tr style="top:expression(offsetParent.scrollTop);position:relative; z-index:9;">
	                    <!--<th class="columntitle">削</th> -->
	                        <th class="columntitle">発注先コード</th>
	                        <th class="columntitle">発注先名</th>
	                        <th class="columntitle">発注先<BR>表示順</th>
	                        <th class="columntitle">担当者コード</th>
	                        <th class="columntitle">担当者</th>
	                        <th class="columntitle">担当者<BR>表示順</th>
	                        <th class="columntitle">メールアドレス</th>
	                        <th class="columntitle">未使用</th>
	                    </tr>
	                </thead>
	                <table class="detail" id="tblList" cellpadding="0" cellspacing="0"
					border="1" style="width:1230px;display:none">
	                    <tbody id="detail"></tbody>
	                </table>
	            </table>
            </div>
             <!-- 検索結果行数 -->
            <input type="hidden" id="hidListRow" name="hidListRow">
            <!-- Excelデータキー検索用ジョイントキー -->
            <input type="hidden" id="hdnJoinCdLiteral" name="hdnJoinCdLiteral" />
            <input type="hidden" id="hdnJoinCd2ndLiteral" name="hdnJoinCd2ndLiteral" />
            <!-- Excelファイルパス -->
            <input type="hidden" id="strFilePath" name="strFilePath" />
            <br/><br/>


	</form>
    </body>
</html>
