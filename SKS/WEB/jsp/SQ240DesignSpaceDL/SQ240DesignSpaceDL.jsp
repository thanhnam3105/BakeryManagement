<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　デザインスペースダウンロード画面                                  -->
<!-- 作成者：TT.Kitazawa                                                             -->
<!-- 作成日：2014/09/01                                                              -->
<!-- 概要  ：検索条件に一致するデザインスペース一覧よりダウンロード。                -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

        document.getElementById("xmlFGEN3250I").src = null;
        document.getElementById("xmlFGEN3250O").src = null;

        document.getElementById("xmlFGEN3260I").src = null;
        document.getElementById("xmlFGEN3260O").src = null;

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
        <script type="text/javascript" src="include/SQ240DesignSpaceDL.js"></script>
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

            var frm = document.frm00;    //ﾌｫｰﾑへの参照
            // 選択行のチェックボックスのにチェックを切り替える
            if (!!frm.chk[funGetCurrentRow()]) {
                frm.chk[funGetCurrentRow()].checked = !(frm.chk[funGetCurrentRow()].checked);
            } else {
                // Index が付いていない
                frm.chk.checked = !(frm.chk.checked);
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" style="margin-left:10px;" >
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3250"></xml>
        <xml id="xmlRGEN3260"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA290O"></xml>

        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3250I" src="../../model/FGEN3250I.xml"></xml>
        <xml id="xmlFGEN3260I" src="../../model/FGEN3260I.xml"></xml>

        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3250O"></xml>
        <xml id="xmlFGEN3260O"></xml>

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="30%" class="title">デザインスペースダウンロード</td>
                    <td width="15%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- 検索ボタン -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSearch();" tabindex="5">
                    <!-- ダウンロードボタン -->
                        <input type="button" class="normalbutton" id="btnDownLoad" name="btnDownLoad" value="ダウンロード" onClick="funDownLoad();" tabindex="6">
                    <!-- 終了ボタン -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funEndClick();" tabindex="7">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

                <table width="1050">
                <!-- 1列目 -->
                <tr>
                    <td width="80">製造工場</td>
                    <td width="270">
                        <select id="ddlSeizoKojo" name="ddlSeizoKojo" style="width:230px;" onChange="funChangeKojo();" tabindex="1">
                        </select>
                    </td>
                    <td width="80">&nbsp;</td>
                    <td width="270">&nbsp;</td>
                    <td width="80">&nbsp;</td>
                    <td width="270">&nbsp;</td>
                </tr>
                <!-- 2行目 -->
                <tr>
                    <td>職場</td>
                    <td>
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="2">
                        </select>
                    </td>
                    <td>製造ライン</td>
                    <td>
                        <select id="ddlLine" name="ddlLine" style="width:230px;" onChange="funChangeLine();" tabindex="3">
                        </select>
                    </td>
                    <td>種類</td>
                    <td>
                        <select id="ddlSyurui" name="ddlSyurui" style="width:230px;"" onChange="funChangeSyurui();" tabindex="4">
                        </select>
                    </td>
                </tr>
            </table>
            <br><br>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>【デザインスペース情報】</td>
                 </tr>
            </table>
            <hr>


            <!-- [デザインスペース情報一覧]リスト -->
            <div class="scroll" id="sclList" style="height:65%;width:980px;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="960px" align="center">
                <colgroup>
                    <col style="width:45px;"/>
                    <col style="width:80px;"/>
                    <col style="width:92px;"/>
                    <col style="width:98px;"/>
                    <col style="width:240px;"/>
                    <col style="width:400px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">選択</th>
                        <th class="columntitle">製造工場</th>
                        <th class="columntitle">職場</th>
                        <th class="columntitle">製造ライン</th>
                        <th class="columntitle">種類</th>
                        <th class="columntitle">ファイル</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN3260O" datafld="rec" style="width:960px;display:none">
                        <!-- [デザインスペース情報一覧] 明細行 -->
                        <!-- tbody id="detail"></tbody -->
                      <tr class="disprow">
                        <td class="column" style="width:45px;text-align:center;"><input type="checkbox" name="chk" value=""></td>
                        <td class="column" style="width:80px;text-align:left;"><span datafld="nm_seizokojo"></span></td>
                        <td class="column" style="width:95px;text-align:left;"><span datafld="nm_shokuba"></span></td>
                        <td class="column" style="width:100px;text-align:left;"><span datafld="nm_line"></span></td>
                        <td class="column" style="width:240px;text-align:left;"><span datafld="nm_syurui"></span></td>
                        <td class="column" style="width:400px;text-align:left;"><span datafld="nm_file"></span></td>
                      </tr>
                    </table>
                </tbody>
            </table>
            </div>

            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode">
            <!-- ファイル保存先サーバーパス（const定義名） -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- サブフォルダー名：保存ファイル毎に指定 （":::"で区切る） -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- ダウンロード、削除ファイル名（":::"で区切る） -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
        </form>
    </body>
</html>
