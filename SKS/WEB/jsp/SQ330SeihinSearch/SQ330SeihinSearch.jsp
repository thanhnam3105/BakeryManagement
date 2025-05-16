<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!--【KPX@1602367】                   -->
<!-- シサクイック　製品検索画面 　　　　　　　　　　 -->
<!-- 作成者：May Thu                  -->
<!-- 作成日：2016/09/13               -->
<!-- 概要  ：製品コードと製品名のあいまい検索  　-->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--//
    //===================================================================================
    // スクロール移動制御
    // 引数  ：なし
    // 戻り値：なし
    // 概要  ：ヘッダーの表示方法をDIVの相対位置で変える
    //===================================================================================
    -->
    </script>

    <script type="text/javascript">
     <!--
    window.onunload = function() {

     <!--   document.getElementById("xmlRGEN0012").src = null;
     <!--   document.getElementById("xmlFGEN0012I").src = null;
     <!--   document.getElementById("xmlFGEN0012O").src = null;


	document.getElementById("xmlUSERINFO_I").src = null;
    document.getElementById("xmlUSERINFO_O").src = null;
    document.getElementById("xmlRESULT").src = null;

    <!--    document.getElementById("xmlSA290I").src = null;
    <!--    document.getElementById("xmlSA290O").src = null;
    }

    // Input[readonly] でBackSpaceを効かなくする  （IE固有の問題）
    window.document.onkeydown = function keydown(){
        if( window.event.keyCode == 8 ){
            if(document.activeElement.readOnly){
        	  return false;
            }
        }
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
        <script type="text/javascript" src="include/SQ330SeihinSearch.js"></script>
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
   		 <script for="tblList" event="onreadystatechange" language="JavaScript">
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
        <!--  テーブル明細行クリック -->
        <script for="tblList" event="ondblclick" language="JavaScript">
            //選択行の背景色を変更
            funChoiceSeihin();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
		<xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
		<xml id="xmlFGEN3590"></xml>
		<xml id="xmlRGEN3590"></xml>
		<xml id="xmlFGEN3590I" src="../../model/FGEN3590I.xml"></xml>
		<xml id="xmlFGEN3590O"></xml>

       <form name="frm00" id="frm00" method="post">
            <table width="480px" border="1">
                <!-- 1列目 -->
                <tr>
                    <td width="100">名称</td>
					<td width="165"><input type="text" value="" name="inputNmHimei" id="inputNmHimei" size="50" tabindex="1">
                    </td>
                    <td width="100">
                    <!-- 検索ボタン -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSeihinNameSearch();" tabindex="2"></td>
                </tr>
            </table>
            <br/>

           <hr>

           <br/>


            <!-- [資材情報一覧]リスト -->
            <div class="scroll" id="sclList" style="height:55%; width:100%; overflow-y:scroll;overflow-x:visible; margin-top:0px;"  rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="100%" align="center">
                <colgroup>
					<col style="width:85px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
						<th class="columntitle">製品コード</th>
						<th class="columntitle">品名</th>
						<th class="columntitle">荷姿</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:100%;">
                    <!-- [資材情報一覧] 明細行 -->
                    <tbody id="detail"></tbody>
                </table>
            </table>
            </div>
			<table>
			<tr><td><div id="divCountInfo"></div></td></tr>
			</table>
			<br/><br/>
			<table width="480px" border="1">
			<tr align="right" ><td><input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="選択" onClick="funChoiceSeihin();" tabindex="3">
			        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="終了" onClick="funEndClick();" tabindex="4">
			</td></tr>
			</table>
            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode">
        </form>
    </body>
</html>
