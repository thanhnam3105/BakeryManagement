<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　資材手配入力画面                                                  -->
<!-- 作成者：TT.Kitazawa                                                             -->
<!-- 作成日：2014/09/01                                                              -->
<!-- 概要  ：検索条件に一致する資材手配一覧を表示する。                              -->
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
        function Scroll1() {
            //Y方向のスクロール移動
            document.getElementById("sclList1").scrollTop = document.getElementById("sclList2").scrollTop;
        }
    -->
    </script>

    <script type="text/javascript">
    <!--
        window.onunload = function() {
        document.getElementById("divGenryo_Left").innerHTML = null;
        document.getElementById("divGenryo_Right").innerHTML = null;

        document.getElementById("xmlRGEN0012").src = null;
        document.getElementById("xmlFGEN0012I").src = null;
        document.getElementById("xmlFGEN0012O").src = null;


    	document.getElementById("xmlUSERINFO_I").src = null;
        document.getElementById("xmlUSERINFO_O").src = null;
        document.getElementById("xmlRESULT").src = null;

        document.getElementById("xmlSA290I").src = null;
        document.getElementById("xmlSA290O").src = null;
        document.getElementById("xmlFGEN3450").src = null;
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
        <script type="text/javascript" src="include/SQ230ShizaiTehaiInput.js"></script>
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

    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3230"></xml>
        <xml id="xmlRGEN3240"></xml>
        <xml id="xmlRGEN3420"></xml>
        <xml id="xmlRGEN3440"></xml>
        <xml id="xmlRGEN3450"></xml>
        <xml id="xmlRGEN3460"></xml>
        <xml id="xmlRGEN3470"></xml>
        <xml id="xmlRGEN3480"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlSA290O"></xml>
        <xml id="xmlFGEN2130O"></xml>

        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3230I" src="../../model/FGEN3230I.xml"></xml>
        <xml id="xmlFGEN3240I" src="../../model/FGEN3240I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>

        <xml id="xmlFGEN3420I" src="../../model/FGEN3420I.xml"></xml>
        <xml id="xmlFGEN3430I" src="../../model/FGEN3430I.xml"></xml>
        <xml id="xmlFGEN3440I" src="../../model/FGEN3440I.xml"></xml>
        <xml id="xmlFGEN3450I" src="../../model/FGEN3450I.xml"></xml>
        <xml id="xmlFGEN3460I" src="../../model/FGEN3460I.xml"></xml>
        <xml id="xmlFGEN3470I" src="../../model/FGEN3470I.xml"></xml>
        <xml id="xmlFGEN3480I" src="../../model/FGEN3480I.xml"></xml>

        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3230O"></xml>
        <xml id="xmlFGEN3240O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3420O"></xml>
        <xml id="xmlFGEN3430O"></xml>
        <xml id="xmlFGEN3440O"></xml>
        <xml id="xmlFGEN3450O"></xml>
        <xml id="xmlFGEN3460O"></xml>
        <xml id="xmlFGEN3470O"></xml>
        <xml id="xmlFGEN3480O"></xml>

        <!-- 原料情報取得用XML -->
        <xml id="xmlRGEN0012"></xml>
        <xml id="xmlFGEN0012I" src="../../model/FGEN0012I.xml"></xml>
        <xml id="xmlFGEN0012O"></xml>

        <!-- 資材情報の指定行データ退避用XML -->
        <xml id="xmlFGEN3230"></xml>
        <xml id="xmlFGEN3450"></xml>
        <xml id="xmlRGEN3455"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data">
            <table width="99%">
                <tr>
                    <td width="20%" class="title">資材手配入力</td>
                    <td width="25%">&nbsp;</td>
                    <td width="55%" align="right">
                    <!-- 検索ボタン -->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" onClick="funSearch();" tabindex="9">
                    <!-- 登録ボタン -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="登録" onClick="funToroku();" tabindex="10">
                    <!--  【KPX@1602367】 TOS TAMURA add 2017/3/3 start-->
                        <input type="button" class="normalbutton" id="btnSeihinSearch" name="btnSeihinSearch" value="コード検索" onClick="funSeihinSearchBox();">
                    <!--  【KPX@1602367】 TOS TAMURA add 2017/3/3 end-->
                    <!-- 削除ボタン -->
                        <input type="button" class="normalbutton" id="btnDesignSpace" name="btnDesignSpace" value="ﾃﾞｻﾞｲﾝｽﾍﾟｰｽ" onClick="funDesignSpace();" tabindex="11">
                    <!-- 参考資料ボタン -->
                        <input type="button" class="normalbutton" id="btnSiryo" name="btnSiryo" value="参考資料" onClick="funSiryo();" tabindex="12">
                    <!-- 終了ボタン -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funNext(0);" tabindex="13">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br/>
            <div id="divUserInfo"></div>
            <br/>
            <table width="1050">
                <!-- 1列目 -->
                <tr>
                    <td width="100">製品コード</td>
                    <td width="150">
                      <span class="ninput" format="11,0" comma="false" name="inputCd">
                        <input type="text" value="" name="inputSeihinCd" id="inputSeihinCd" size="15"  maxlength="11" style=\"ime-mode:disabled;\" onkeydown="if(event.keyCode == 13 || event.keyCode == 9){funSeihinSearch(event.keyCode);}" tabindex="1"></span>
					<!--  【KPX@1602367】 TOS TAMURA DELETE 2017/03/03 start-->
					<!--  【KPX@1602367】 BRC MAY THU add start-->
                    <!--   <input type="button"  id="btnSeihinSearch" name="btnSeihinSearch" value="neko" onClick="funSeihinSearchBox();">--> 
					<!--  【KPX@1602367】 BRC MAY THU add end-->
					<!--  【KPX@1602367】 TOS TAMURA DELETE 2017/03/03 end-->
                    </td>
                    <td width="50"></td>
                    <td width="100">製品名</td>
                    <td width="300">
                        <input type="text" value="" name="seihinNm" id="seihinNm" size="50" readonly tabindex="-1">
                    </td>
                    <td width="100">荷姿</td>
                    <td width="300">
                        <input type="text" value="" name="nisugata" id="nisugata" size="45" readonly tabindex="-1">
                    </td>
                </tr>
                <!-- 2行目 -->
                <tr>
                    <td>試作No.</td>
                    <td colspan="2">
                        <select id="ddlShisakuNo" name="ddlShisakuNo" style="width:230px;" onChange="funChangeShisakuNo();" tabindex="2" onFocus="funFocusShisakuNo();">
                        </select>
                    </td>
                </tr>
            </table>
            <br/>

            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>【製造工場情報】</td>
                 </tr>
            </table>
            <hr>

            <table width="1050">
                <!-- 1列目 -->
                <tr>
                    <td width="100">製造工場</td>
                    <td width="150">
                        <input type="hidden" value="" name="seizoKojoCd" id="seizoKojoCd" onChange="funChangeKojo();">
                        <input type="text" value="" name="seizoKojoNm" id="seizoKojoNm" style="width:100px;" readonly tabindex="-1">
                    </td>
                    <td width="100">職場</td>
                    <td width="300">
                        <select id="ddlShokuba" name="ddlShokuba" style="width:230px;" onChange="funChangeShokuba();" tabindex="3">
                        </select>
                    </td>
                    <td width="100">製造ライン</td>
                    <td width="300">
                        <select id="ddlLine" name="ddlLine" style="width:230px;" tabindex="4">
                        </select>
                    </td>
                </tr>
            </table>
            <br/>

            <!-- div class="scroll"  style="height:70%; width:100%" -->
            <div class="scroll"  style="height:65%; width:100%">
            <br/>
            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>【資材情報】</td>
                 </tr>
            </table>
            <hr>

            <!-- [資材情報一覧]リスト 80 - 70  80  60 100 70-->
            <div class="scroll" id="sclList" style="height:55%; width:1540px; overflow:scroll; margin-top:0px;"  rowSelect="true">
            <table id="dataTable" name="dataTable" cellspacing="0" width="1700px" align="center"><!-- 2466px May Thu 【KPX@1602367】 2016/09/05 width update-->
                <colgroup>
                    <col style="width:48px;"/>
                    <col style="width:169px;"/>
                    <col style="width:55px;"/>
                    <col style="width:45px;"/>
                    <col style="width:60px;"/>
                    <col style="width:236px;"/>
                    <col style="width:244px;"/>
                    <col style="width:149px;"/>
                    <col style="width:45px;"/>
					<!-- May Thu 【KPX@1602367】 2016/09/05 535 add-->
                    <col style="width:220px;"/>
                    <col style="width:58px;"/>
                    <col style="width:45px;"/>
                    <col style="width:90px;"/>
                    <col style="width:75px;"/>
					<!-- May Thu 【KPX@1602367】 2016/09/05 end-->
                    <col style="width:90px;"/>
                    <col style="width:75px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">資材<BR>コード</th>
                        <th class="columntitle">資材名</th>
                        <th class="columntitle">単価※</th>
                        <th class="columntitle">新規</th>
                        <th class="columntitle">新資材<BR>コード</th>
                        <th class="columntitle">新資材名</th>
                        <th class="columntitle">発注先</th>
                        <th class="columntitle">対象資材</th>
                        <th class="columntitle">完了</th>
						<!-- May Thu 2016/09/05 add-->
                        <th class="columntitle">版下ファイル</th>
                        <th class="columntitle">参照</th>
						<th class="columntitle">発注</th>
						<th class="columntitle">発注者</th>
						<th class="columntitle">発注日</th>
						<!-- May Thu 2016/09/05 end-->
                        <th class="columntitle">更新者</th>
                        <th class="columntitle">更新日</th>
                    </tr>
                </thead>
                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" bordercolor="#C0C0C0" style="width:1700px;display:none"><!-- May Thu 【KPX@1602367】 2016/09/05 width update-->
                    <!-- [資材情報一覧] 明細行 -->
                    <tbody id="detail"></tbody>
                </table>
            </table>
            </div>
            <br/><br/>

            <!--試作原価情報-->
            <table border="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                         【原料情報】
                    </td>
                </tr>
            </table>
            <hr>

            <!--試作原価情報一覧  height="300px" -->
            <table cellpadding="0" cellspacing="0" border="0" width="1180px" height="170px" style="table-layout:fixed;">
                <tr>
                <!-- 左側 -->
                <td valign="top">
                    <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td valign="top" style="width:605px;">
                            <table class="detail" cellpadding="0" cellspacing="0" border="0" >
                                <tr><td height="72">
                                    <!--左側ヘッダー部-->
                                    <div id="LHeaderDiv" style="">
                                        <table id="data1" cellpadding="0" cellspacing="0" border="1">
                                        <tr>
                                            <th class="columntitle" style="width:20px;height:75px;" >選<br/>択</th>
                                            <th class="columntitle" style="width:20px;height:75px;" >工<br/>程</th>
                                            <th class="columntitle" style="width:105px;" >原料CD</th>
                                            <th class="columntitle" style="width:310px;" >原料名</th>
                                            <th class="columntitle" style="width:20px;" >変<br/>更</th>
                                            <th class="columntitle" style="width:70px;" >単価<br/>（円/㎏）<br/>※</th>
                                            <th class="columntitle" style="width:45px;" >歩留<br/>（％）<br/>※</th>
                                        </tr>
                                        </table>
                                    </div>
                                </td></tr>
                                <tr>
                                    <!-- td valign="top" height="422" -->
                                    <td valign="top" height="292">
                                        <!--左側カラム部-->
                                        <div id="sclList1" style="height:286px;overflow:hidden;position:relative;">
                                            <div id="divGenryo_Left"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <!-- 右側 -->
                        <td align="left" valign="top">
                            <div class="scroll_genka" id="sclList2" style="width:544px;height:381px;overflow:scroll;" rowSelect="true" onScroll="Scroll1();" />
                                <!-- 右側テーブル -->
                                <div id="divGenryo_Right">
                                </div>
                        </td>
                    </tr>

                    </table>
                </td></tr>
            </table>
            <br/>
            <!-- 明細行、スクロール終わり -->
            </div>
            <!-- 処理モード -->
            <input type="hidden" id="mode" name="mode">
            <!-- 参考資料選択カテゴリ -->
            <input type="hidden" id="sq220Category" name="sq220Category" >
			<!-- May Thu 【KPX@1602367】 add start 2016/09/21-->
            <input type="hidden" id="flg_hatyuu_status" name="flg_hatyuu_status" value="1" >
			<input type="hidden" id="cd_shain" name="cd_shain" value="1" >
			<input type="hidden" id="nen" name="nen" value="1" >
			<input type="hidden" id="no_oi" name="no_oi" value="1" >
			<input type="hidden" id="no_eda" name="no_eda" value="1" >
            <!-- ファイル保存先サーバーパス（const定義名） -->
            <input type="hidden" value="" name="strServerConst" id="strServerConst">
            <!-- サブフォルダー名：保存ファイル毎に指定 （":::"で区切る） -->
            <input type="hidden" value="" name="strSubFolder" id="strSubFolder">
            <!-- アップロード対象のフィールド名（":::"で区切る） -->
            <input type="hidden" value="" name="strFieldNm" id="strFieldNm">
            <!-- ダウンロード、削除ファイル名（":::"で区切る） -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">
			<!-- May Thu 【KPX@1602367】 add end 2016/09/21-->
        </form>
    </body>
</html>
