<%@ page language="java" contentType="text/html;charset=Windows-31J" %>

<!------------------------------------------------------------------------------------->
<!-- シサクイック　資材コード呼出画面                                                -->
<!-- 作成者：TT.Shima                                                                -->
<!-- 作成日：2014/09/09                                                              -->
<!-- 概要  ：資材コードを検索する                                                    -->
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
        <script type="text/javascript">
            <!--
                var str = location.search;
                var gamenId = str.substring(1, str.length);

                var script = document.createElement("script");
                script.type = "text/javascript";

                if(gamenId == "shizai"){
                    // 資材コード呼出
                    script.src = "include/SQ260ShizaiCode.js";
                } else if (gamenId == "ruizi"){
                	// 類似コード呼出
                    script.src = "include/SQ260RuiziData.js";
                } else if (gamenId == "shizaiTehai"){
                	// 資材手配済一覧
                	script.src = "include/SQ260ShizaiTehaiZumi.js";
                }
                document.getElementsByTagName("head")[0].appendChild(script);
            // -->
        </script>

        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //処理中ﾒｯｾｰｼﾞ非表示
                funClearRunMessage();
            }
        </script>

        <!--  テーブル明細行クリック -->
        <script for="tblList" event="onclick" language="JavaScript">
            var frm = document.frm00;    //ﾌｫｰﾑへの参照

            var row = -1;
            // クリックされたアクティブエレメントを取得（CurrentRow が取得できない為）
            var activeElementName = document.activeElement.name;
            // 明細行のカレント行（罫線をクリックした時など、undefinedになる）
            if (!!activeElementName) {
                var nameAry = activeElementName.split("_");
                // funGetCurrentRow();
                row = nameAry[nameAry.length - 1];
            }

            if (row >= 0) {
                // 資材コード呼出の場合：
                //   選択行のチェックボックスを切り替える
                if (!!frm.chkShizai){
                    // 2回目のイベント処理でチェックを切り替え
                    if (row == funGetCurrentRow()) {
                        // 同じ行をクリックした時の為にクリア
                        funSetCurrentRow(-1);
                        if (!!frm.chkShizai[row]) {
                            // 表示が複数行
                            frm.chkShizai[row].checked = !(frm.chkShizai[row].checked);
                        } else {
                            // 表示が1行（Index が付いていない）
                            frm.chkShizai.checked = !(frm.chkShizai.checked);
                        }
                    } else {
                        // カレント行を設定する
                        clickItiran(row);
                    }

                // 類似コード呼出の場合：ラジオボタン
                }else if (!!frm.chk){
                    if (!!frm.chk[row]){
                        clickItiran(row);
                        frm.chk[row].checked = true;
                    } else {
                        clickItiran(0);
                        frm.chk.checked = true;
                    }
                }
            }

        </script>

        <!-- スタイルシート -->
<!--         <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css"> -->

        <META HTTP-EQUIV="Content-Type" content="text/html; charset=shift_jis">
        <META HTTP-EQUIV="Content-Language" content="ja">
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3330"></xml>
        <xml id="xmlRGEN3340"></xml>
        <xml id="xmlRGEN3350"></xml>
        <xml id="xmlRGEN3620"></xml>
        <!-- nakamura -->
        <xml id="xmlRGEN3690"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3300I" src="../../model/FGEN3300I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>
        <xml id="xmlFGEN3330I" src="../../model/FGEN3330I.xml"></xml>
        <xml id="xmlFGEN3340I" src="../../model/FGEN3340I.xml"></xml>
        <xml id="xmlFGEN3350I" src="../../model/FGEN3350I.xml"></xml>
        <xml id="xmlFGEN3620I" src="../../model/FGEN3620I.xml"></xml>
        <!-- nakamura -->
        <xml id="xmlFGEN3690I" src="../../model/FGEN3690I.xml"></xml>

        <xml id="xmlSA290I" src="../../model/SA290I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3300O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3330O"></xml>
        <xml id="xmlFGEN3340O"></xml>
        <xml id="xmlFGEN3350O"></xml>
        <xml id="xmlFGEN3620O"></xml>
        <!-- nakamura -->
        <xml id="xmlFGEN3690O"></xml>
        <xml id="xmlFGEN3690"></xml>
		<xml id="xmlRGEN3700"></xml>

        <xml id="xmlSA290O"></xml>

        <form name="frm00" id="frm00" method="post" enctype="multipart/form-data">
            <!-- タイトル部 -->
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="20%" class="title" id="lblTitle">
                    </td>
                    <td width="75%" align="right">

                    <!--2017/3/13 tamura add start-->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" style="width:80px" onClick="funSearch();">
                        <input type="button" class="normalbutton" id="btnCompletion" name="btnCompletion" value="選択完了" style="width:80px" onClick="funCompletion();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="width:80px" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnOutput" name="btnOutput" value="EXCEL" style="width:100px" onClick="funExcelOut();">
                        <input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="アップロード" style="width:100px" onClick="funToroku();"><!-- nakamura変更 -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funEnd();">
                    <!--2017/3/13 tamura add end-->

                    <!--2017/3/13 tamura delete start-->
                   	   <!--<input type="button" class="normalbutton" id="btnUpLoad" name="btnUpLoad" value="アップロード" style="width:100px" onClick="funToroku();">--><!-- nakamura変更 -->
                       <!--<input type="button" class="normalbutton" id="btnOutput" name="btnOutput" value="EXCEL" style="width:100px" onClick="funExcelOut();">-->
                       <!--<input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" style="width:80px" onClick="funSearch();">-->
                       <!--<input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="width:80px" onClick="funClear();">-->
                       <!--<input type="button" class="normalbutton" id="btnCompletion" name="btnCompletion" value="選択完了" style="width:80px" onClick="funCompletion();">-->
                       <!--<input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funEnd();">-->
                    <!--2017/3/13 tamura delete end -->

                    </td>
                </tr>
            </table>

            <!-- 情報 -->
            <table width="99%">
                <tr>
                    <td align="right">
                        <div id="divUserInfo"></div>
                    </td>
                </tr>
            </table>

            <table>
                <!-- 1列目 -->
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="2" >
                        <input type="checkbox" id="chkTehaizumi" name="chkTehaizumi" tabindex="1">
                        <label for="chkTehaizumi"> 手配済み</label>&nbsp;&nbsp;
                        <input type="checkbox" id="chkMitehai" name="chkMitehai" tabindex="2">
                        <label for="chkMitehai"> 未手配&nbsp;&nbsp;</label>
                        <input type="checkbox" id="chkMinyuryoku" name="chkMinyuryoku" tabindex="3">
                        <label for="chkMinyuryoku"> 未入力</label>
                        <input type="checkbox" id="chkEdit" name="chkEdit" onClick="funChangeEdit();" tabindex="4">
                        <label for="chkEdit"> 編集</label>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <!-- 2列目 -->
                <tr>
                    <td width="100">資材コード</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" id="txtShizaiCd" name="txtShizaiCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="5" >
                        </span>
                    </td>
                    <td width="100">資材名</td>
                    <td width="300">
                        <input type="text" class=act_text id="txtShizaiNm" name="txtShizaiNm" style="width:270px;" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="6">
                    </td>
                    <td width="100">旧資材コード</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" id="txtOldShizaiCd" name="txtOldShizaiCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="7">
                        </span>
                    </td>
                </tr>
                <!-- 3列目 -->
                <tr>
                    <td width="100">製品コード</td>
                    <td width="200">
                        <span class="ninput" format="6,0" comma="false">
                            <input type="text" class=disb_text id="txtSyohinCd" name="txtSyohinCd" style="width:170px;" maxlength="6" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="8">
                        </span>
                    </td>
                    <td width="100">製品名</td>
                    <td width="300">
                        <input type="text" class=act_text id="txtSyohinNm" name="txtSyohinNm" style="width:270px;" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="9">
                    </td>
                    <!-- 【KPX@1602367】add 20160912 -->
                    <td width="100">納入先</td>
                    <td width="200">
                        <input type="text" class=act_text id="txtSeizoukojo" name="txtSeizoukojo" style="width:170px;" onChange="funChangeKojo();" tabindex="10">
                        <input type="hidden" value="" name="seizoKojoCd" id="seizoKojoCd" onChange="funChangeKojo();">
                    </td>
                </tr>
                <!-- 4列目 -->
                <tr>
                    <td width="100">対象資材</td>
                    <td width="200">
                        <select id="ddlShizai" name="ddlShizai" style="width:170px;" onChange="funChangeSearch();" tabindex="10">
                        </select>
                    </td>
                    <td width="100">発注先</td>
                    <td width="200">
                        <select id="ddlHattyusaki" name="ddlHattyusaki" style="width:170px;" onChange="funChangeSearch();" tabindex="11">
                        </select>
                    </td>
                    <!-- 【KPX@1602367】upd 20160912 -->
                    <td width="100">発注者</td>
                    <td width="200">
                        <select id="ddlTanto" name="ddlTanto" style="width:170px;" onChange="funChangeSearch();" tabindex="12">
                        </select>
                    </td>
                </tr>
                <!-- 【KPX@1602367】add 20160912 -->
                <!-- 5列目 -->
                <tr>
                    <td width="100">発注日</td>
                    <td width="200">

                            <input type="text" class="act_text disb_text" id="txtHattyubiFrom" name="txtHattyubiFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="13">

                        ～

                            <input type="text" class="act_text disb_text" id="txtHattyubiTo" name="txtHattyubiTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="14">

                    </td>
                    <td width="100">納入日</td>
                    <td width="200">
                        <span class="disb_text">
                            <input type="text" class="act_text disb_text" id="txtNounyudayFrom" name="txtNounyudayFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="15">
                        </span>
                        ～

                            <input type="text" class="act_text disb_text" id="txtNounyudayTo" name="txtNounyudayTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="16">

<!--                         <input type="text" class=ninput id="txtNounyuday" name="txtNounyuday" style="width:170px;" maxlength="10" onChange="funChangeSearch();" tabindex="15"> -->
                    </td>
                    <td width="100">版代支払日</td>
                    <td width="200">
                            <input type="text" class="act_text disb_text" id="txtHanPaydayFrom" name="txtHanPaydayFrom" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="17">
                        ～
                            <input type="text" class="act_text disb_text" id="txtHanPaydayTo" name="txtHanPaydayTo" style="width:75px;" maxlength="10" onFocus="textValue = this.value" onBlur="funTextChange(this.value);" tabindex="18">
<!--                         <input type="text" class=ninput id="txtHanPayday" name="txtHanPayday" style="width:170px;" maxlength="10" onChange="funChangeSearch();" tabindex="16"> -->
                    </td>
                    <td>
                    <input type="checkbox" id="chkMshiharai" name="chkMshiharai"  onClick="this.blur();this.focus();" onChange="funChangeSearch();" tabindex="19">
                        <label for="chkEdit"> 未支払</label>
                    </td>
                </tr>

            </table>

            <!-- [試作ﾃﾞｰﾀ一覧]リスト -->
            <div class="scroll" id="sclList" style="height:60%;width:100%;" rowSelect="true" align="left">
                <table id="dataTable" name="dataTable" cellspacing="0" width="4365px" >
                <colgroup>
                    <col style="width:30px;"/><!-- No -->
                    <col style="width:30px;"/><!-- 選択ボタン -->
                    <col id="colHeadTanto" style="width:100px;"/><!-- 担当者 -->
                    <col id="colHeadNaiyo" style="width:200px;"/><!-- 内容 -->
                    <col style="width:50px;"/><!-- 製品コード -->
                    <col style="width:150px;"/><!-- 製品名 -->
                    <col id="colHeadNisugata" style="width:150px;"/><!-- 荷姿 -->
                    <col style="width:120px;"/><!-- 対象資材 -->
                    <col id="colHeadNmHattyusaki" style="width:150px;"/><!-- 発注先 -->
                    <col id="colHeadNmNounyusaki_" style="width:150px;"/><!-- 納入先 -->
                    <col id="colHeadCdShizaiOld" style="width:50px;"/><!-- 旧資材コード -->
                    <col style="width:50px;"/><!-- 資材コード -->
                    <col style="width:150px;"/><!-- 資材名 -->
                    <col id="colHeadSekkei1" style="width:200px;"/><!-- 設計① -->
                    <col id="colHeadSekkei2" style="width:200px;"/><!-- 設計② -->
                    <col id="colHeadSekkei3" style="width:200px;"/><!-- 設計③ -->
                    <col id="colHeadZaishitsu" style="width:200px;"/><!-- 材質 -->
                    <col id="colHeadBikoTehai" style="width:300px;"/><!-- 備考 -->
                    <col id="colHeadPrintcolor" style="width:200px;"/><!-- 印刷色 -->
                    <col id="colHeadNo_color" style="width:200px;"/><!-- 色番号 -->
                    <col id="colHeadHenkounaiyoushosai" style="width:200px;"/><!-- 変更内容詳細 -->
                    <col id="colHeadNouki" style="width:150px;"/><!-- 納期 -->
                    <col id="colHeadSuryo" style="width:150px;"/><!-- 数量 -->
                    <col id="colHeadTorokuDisp" style="width:150px;"/><!-- 登録日 -->
                    <col id="colHeadNounyuDay" style="width:100px;"/><!-- 納入日 -->
                    <col style="width:150px;"/><!-- 版代-->
                    <col style="width:100px;"/><!-- 版代支払日 -->
                    <col style="width:480px;"/><!-- 青焼アップロード -->
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;z-index:1">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">選</th>
                        <th class="columntitle">担当者</th>
                        <th class="columntitle">内容</th>
                        <th class="columntitle">製品<br>コード</th>
                        <th class="columntitle">製品名</th>
                        <th class="columntitle">荷姿</th>
                        <th class="columntitle">対象資材</th>
                        <th class="columntitle">発注先</th>
                        <th class="columntitle">納入先</th>
                        <th class="columntitle">旧資材<br>コード</th>
                        <th class="columntitle">資材<br>コード</th>
                        <th class="columntitle">資材名</th>
                        <th class="columntitle">設計①</th>
                        <th class="columntitle">設計②</th>
                        <th class="columntitle">設計③</th>
                        <th class="columntitle">材質</th>
                        <th class="columntitle">備考</th>
                        <th class="columntitle">印刷色</th>
                        <th class="columntitle">色番号</th>
                        <th class="columntitle">変更内容詳細</th>
                        <th class="columntitle">納期</th>
                        <th class="columntitle">数量</th>
                        <th class="columntitle">登録日</th>
                        <th class="columntitle">納入日</th>
                        <th class="columntitle">版代</th>
                        <th class="columntitle">版代支払日</th>
                        <th class="columntitle">青焼アップロード</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="0" style="width:4315px;">
                        <tr>
                            <td>
                                <div id="divMeisai" name="divMeisai" />
                            </td>
                        <tr>
                    </table>
                </table>
            </div>
            <!-- 検索結果行数 -->
            <input type="hidden" id="hidListRow" name="hidListRow">

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
