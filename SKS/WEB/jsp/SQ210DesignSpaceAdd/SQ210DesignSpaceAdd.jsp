<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　デザインスペース登録画面                                          -->
<!-- 作成者：TT.Kitazawa                                                             -->
<!-- 作成日：2014/09/01                                                              -->
<!-- 概要  ：検索条件に一致するデザインスペース一覧を表示する。                      -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
    <script type="text/javascript">
    <!--
        window.onunload = function() {

        document.getElementById("xmlUSERINFO_I").src = null;
        document.getElementById("xmlUSERINFO_O").src = null;
        document.getElementById("xmlRESULT").src = null;

        document.getElementById("xmlRGEN3230").src = null;
        document.getElementById("xmlFGEN3230I").src = null;
        document.getElementById("xmlFGEN3230O").src = null;

        document.getElementById("xmlRGEN3240").src = null;
        document.getElementById("xmlFGEN3240I").src = null;
        document.getElementById("xmlFGEN3240O").src = null;

        document.getElementById("xmlRGEN3250").src = null;
        document.getElementById("xmlFGEN3250I").src = null;
        document.getElementById("xmlFGEN3250O").src = null;

        document.getElementById("xmlRGEN3260").src = null;
        document.getElementById("xmlFGEN3260I").src = null;
        document.getElementById("xmlFGEN3260O").src = null;

        document.getElementById("xmlRGEN3270").src = null;
        document.getElementById("xmlFGEN3270I").src = null;
        document.getElementById("xmlFGEN3270O").src = null;

        document.getElementById("xmlRGEN3280").src = null;
        document.getElementById("xmlFGEN3280I").src = null;
        document.getElementById("xmlFGEN3280O").src = null;

        document.getElementById("xmlRGEN3410").src = null;
        document.getElementById("xmlFGEN3410I").src = null;
        document.getElementById("xmlFGEN3410O").src = null;

        document.getElementById("xmlSA290I").src = null;
        document.getElementById("xmlSA290O").src = null;

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
        <script type="text/javascript" src="include/SQ210DesignSpaceAdd.js"></script>
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
        	// 選択行のラジオボタンにチェックを入れる
        	if (!!frm.chk[funGetCurrentRow()]) {
        		frm.chk[funGetCurrentRow()].checked = true;
        	} else {
        		// Index が付いていない
        		frm.chk.checked = true;
        	}

        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3250"></xml>
        <xml id="xmlRGEN3260"></xml>
        <xml id="xmlRGEN3270"></xml>
        <xml id="xmlRGEN3280"></xml>
        <xml id="xmlRGEN3410"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>

        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3250I" src="../../model/FGEN3250I.xml"></xml>
        <xml id="xmlFGEN3260I" src="../../model/FGEN3260I.xml"></xml>
        <xml id="xmlFGEN3270I" src="../../model/FGEN3270I.xml"></xml>
        <xml id="xmlFGEN3280I" src="../../model/FGEN3280I.xml"></xml>
        <xml id="xmlFGEN3410I" src="../../model/FGEN3410I.xml"></xml>

        <xml id="xmlSA290O"></xml>
        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3250O"></xml>
        <xml id="xmlFGEN3260O"></xml>
        <xml id="xmlFGEN3270O"></xml>
        <xml id="xmlFGEN3280O"></xml>
        <xml id="xmlFGEN3410O"></xml>

        <!-- コンボボックスにデフォルトで設定されている値の退避用XML -->
        <xml id="xmlSA290"></xml>
        <xml id="xmlFGEN3250"></xml>
        <xml id="xmlFGEN3260"></xml>


        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data" >
            <table width="99%">
                <tr>
                    <td width="20%" class="title">デザインスペース登録</td>
                    <td width="15%">&nbsp;</td>
                    <td width="65%" align="right">
                    <!-- 検索ボタン -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSearch();" tabindex="11">
                    <!-- 行追加ボタン -->
                        <input type="button" class="normalbutton" id="btnLineAdd" name="btnLineAdd" value="行追加" onClick="funLineAdd();" tabindex="12">
                    <!-- 行削除ボタン -->
                        <input type="button" class="normalbutton" id="btnLineDel" name="btnLineDel" value="行削除" onClick="funLineDel();" tabindex="13">
                    <!-- 登録ボタン -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="登録" onClick="funUpdate();" tabindex="14">
                    <!-- 削除ボタン -->
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="削除" onClick="funDelete();" tabindex="15">
                    <!-- 参考資料ボタン -->
                        <input type="button" class="normalbutton" id="btnSiryo" name="btnSiryo" value="参考資料" onClick="funSiryo();" tabindex="16">
                    <!-- 終了ボタン -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funEndClick();" tabindex="17">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <table width="400">
                <!-- 1行目 -->
                <tr>
                    <td width="100">製造工場</td>
                    <td width="300">
                        <select id="ddlSeizoKojo" name="ddlSeizoKojo" style="width:230px;" onChange="funChangeKojo();" tabindex="1">
                        </select>
                    </td>
                </tr>
                <!-- 2行目 -->
                <tr>
                    <td>職場</td>
                    <td>
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="2">
                        </select>
                    </td>
                </tr>
                <!-- 3行目 -->
                <tr>
                    <td>製造ライン</td>
                    <td>
                        <select id="ddlLine" name="ddlLine" style="width:230px;" onChange="funChangeLine();" tabindex="3">
                        </select>
                     </td>
                </tr>
            </table>
            <br/>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>【デザインスペース情報】</td>
                 </tr>
            </table>
            <hr>


            <!-- [デザインスペース情報一覧]リスト width:870 → 1170px -->
            <div class="scroll" id="sclList" style="height:60%;width:1190px;overflow:scroll;" rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1170px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:278px;"/>
                    <col style="width:471px;"/>
                    <col style="width:75x;"/>
                    <!-- col style="width:100px;"/ -->
                    <col style="width:160px;"/>
                    <col style="width:150px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative; z-index:9;">
                        <th class="columntitle">選</th>
                        <th class="columntitle">種類</th>
                        <th class="columntitle">ファイル</th>
                        <th class="columntitle">参照</th>
                        <!-- th class="columntitle">クリア</th -->
                        <th class="columntitle">更新者</th>
                        <th class="columntitle">更新日時</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" style="width:1170px;display:none">
                    <!-- [デザインスペース情報一覧] 明細行 -->
                    <tbody id="detail"></tbody>

                </table>
            </table>
            </div>
            <br/><br/>

            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode">
            <!-- 参考資料選択カテゴリ -->
            <input type="hidden" id="sq220Category" name="sq220Category">

            <!-- ファイル保存先サーバーパス（const定義名） -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- サブフォルダー名：保存ファイル毎に指定 （":::"で区切る） -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- アップロード対象のフィールド名（":::"で区切る） -->
            <input type="hidden" value="" name="strFieldNm" id="strFieldNm">
            <!-- ダウンロード、削除ファイル名（":::"で区切る） -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">

	</form>
    </body>
</html>
