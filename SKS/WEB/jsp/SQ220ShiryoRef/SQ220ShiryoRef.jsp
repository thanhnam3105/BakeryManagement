<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　参考資料画面                                                      -->
<!-- 作成者：TT E.Kitazawa                                                           -->
<!-- 作成日：2014/09/01                                                              -->
<!-- 概要  ：参考資料をダウンロード、又はアップロードする。                          -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

            document.getElementById("xmlRGEN3400").src = null;
            document.getElementById("xmlFGEN3400I").src = null;
            document.getElementById("xmlFGEN3400O").src = null;

            document.getElementById("xmlRGEN3210").src = null;
            document.getElementById("xmlFGEN3210I").src = null;
            document.getElementById("xmlFGEN3210O").src = null;

            document.getElementById("xmlRGEN3220").src = null;
            document.getElementById("xmlFGEN3220I").src = null;
            document.getElementById("xmlFGEN3220O").src = null;

            document.getElementById("xmlUSERINFO_I").src = null;
            document.getElementById("xmlUSERINFO_O").src = null;
            document.getElementById("xmlRESULT").src = null;

 }

    // Input[readonly] でBackSpaceを効かなくする  （IE固有の問題）
    window.document.onkeydown = function keydown(){
        if( window.event.keyCode == 8 ){
            return false;
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
        <script type="text/javascript" src="include/SQ220ShiryoRef.js"></script>
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

    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3400"></xml>
        <xml id="xmlRGEN3210"></xml>
        <xml id="xmlRGEN3220"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3400I" src="../../model/FGEN3400I.xml"></xml>
        <xml id="xmlFGEN3210I" src="../../model/FGEN3210I.xml"></xml>
        <xml id="xmlFGEN3220I" src="../../model/FGEN3220I.xml"></xml>

        <xml id="xmlFGEN3400O"></xml>
        <xml id="xmlFGEN3210O"></xml>
        <xml id="xmlFGEN3220O"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>


        <!-- コンボボックスにデフォルトで設定されている値の退避用XML -->
        <xml id="xmlFGEN3400"></xml>


        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
            <table width="99%">
                <tr>
                    <td width="5">&nbsp;</td>
                    <td width="20%" class="title">参考資料</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- ダウンロードボタン -->
                        <input type="button" class="normalbutton" id="btnDownLoad" name="btnDownLoad" value="ダウンロード" onClick="funDownLoad();" tabindex="4">
                    <!-- アップロードボタン(submit) -->
                        <input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="アップロード" onClick="funUpLoad();" tabindex="5">
                    <!-- 終了ボタン -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funEndClick();" tabindex="6">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table border="0" cellspacing="0" width="99%">
                <tr>
                    <td width="5">&nbsp;</td>
                    <td>【参考資料】</td>
                 </tr>
                <tr>
                    <td colspan="2">
                        <hr>
                    </td>
                 </tr>
            </table>


            <!-- 参考資料選択 -->
            <table width="920" >
                <!-- 1行目 -->
                 <tr>
                    <td width="5">&nbsp;</td>
                    <td width="250">
                    <!-- カテゴリ選択 -->
                        <select id="ddlCategoryName" name="ddlCategoryName" datafld="nm_literal" style="width:230px;" onChange="funFileSearch();" onclick="funSetInput(0)" tabindex="1">
                        </select>
                    </td>
                    <td width="570">
                    <!-- 資料ファイル名：ダウンロード用 -->
                        <input type="text" value="" name="shiryoName" id="shiryoName" size="100" readonly tabindex="-1">
                    </td>
                    <td width="100"></td>
                 </tr>
                <!-- 2行目：アップロード用 -->
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>
                    <!-- アップロードする参照ファイルは「参照」ボタンで設定（入力、クリア等不可、サイズ=0にならない）。表示用Inputをピッタリ重ねて隠す！！ -->
                      <div style="position: relative;">
                      <!-- 参照ファイル名：「参照」ボタン -->
                        <input type="file" class="normalbutton" name="filename"   onchange="funChangeFile()" style="width:570px; align=right;" onclick="funSetInput(1)" onkeydown="funEnterFile(event.keyCode);" tabindex="2">
                      <span style="position: absolute; top: 0px; left: 0px;">
                      <!-- 参照ファイル名：表示用 -->
                        <input type="text" value="" name="inputName" id="inputName" size="100" readonly tabindex="-1">
                      </span>
                      </div>
                    </td>
                    <td>
                    <!-- クリアボタン：削除 -->
                        <!-- input type="button"  class="normalbutton" name="btnClear" id="btnClear" value="クリア" onClick="funClearInput();" tabindex="3" -->
                    </td>
                </tr>
            </table>

            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode" value="1">
            <!-- 選択カテゴリ名 -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- ダウンロードファイル、削除ファイル名  -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
            <!-- ファイル保存先サーバーパス（const定義名） -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- アップロード対象のフィールド名 -->
            <input type="hidden" value="filename" name="strFieldNm" id="strFieldNm">

        </form>
    </body>
</html>
