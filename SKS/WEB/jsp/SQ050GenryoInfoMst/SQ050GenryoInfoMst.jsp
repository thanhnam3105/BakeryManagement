<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　原料分析情報マスタメンテナンス画面                                -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/01                                                              -->
<!-- 概要  ：検索条件に一致する原料データを一覧で表示する。                          -->
<!------------------------------------------------------------------------------------->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/06/24                                                              -->
<!-- 内容  ：工場コンボで未選択も可能にする(課題表№16)                              -->
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
        <script type="text/javascript" src="include/SQ050GenryoInfoMst.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=tblList event=onreadystatechange>
            if (tblList.readyState == 'complete') {
                //廃止原料の背景色を変更
                funChangeHaishiColor();

                //処理中ﾒｯｾｰｼﾞ非表示
                funClearRunMessage();
            }
        </script>

        <!--  テーブル明細行クリック -->
        <script for="tblList" event="onclick" language="JavaScript">
            //選択行の背景色を変更
            funChangeSelectRowColorLocal();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlJSP0410"></xml>
        <xml id="xmlJSP0420"></xml>
        <xml id="xmlJSP0430"></xml>
        <xml id="xmlJSP0440"></xml>
        <xml id="xmlJSP0450"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA180I" src="../../model/SA180I.xml"></xml>
        <xml id="xmlSA360I" src="../../model/SA360I.xml"></xml>
        <xml id="xmlSA370I" src="../../model/SA370I.xml"></xml>
        <xml id="xmlSA390I" src="../../model/SA390I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA180O"></xml>
        <xml id="xmlSA360O"></xml>
        <xml id="xmlSA370O"></xml>
        <xml id="xmlSA390O"></xml>
        <xml id="xmlRESULT"></xml>
        <!-- コンボボックスにデフォルトで設定されている値の退避用XML -->
        <xml id="xmlSA140"></xml>
        <xml id="xmlSA180"></xml>

<!--add start------------------------------------------------------->
<!--QP@00412_シサクイック改良 No.4-->
        <xml id="xmlSA880"></xml>
        <xml id="xmlSA880I" src="../../model/SA880I.xml"></xml>
        <xml id="xmlSA880O"></xml>
<!--add end -------------------------------------------------------->

        <form name="frm00" id="frm00" onsubmit="" method="post">
            <table width="99%">
                <tr>
                    <td width="32%" class="title">原料分析情報マスタメンテナンス</td>
                    <!-- ユーザー情報 -->
                    <td width="68%"><div id="divUserInfo"></div></td>
                </tr>
            </table>

            <br>

<!--mod start------------------------------------------------------->
<!--QP@00412_シサクイック改良 No.4 項目追加-->
<!--QP@00412_シサクイック改良 No.5 タブ順の変更-->
            <!-- 入力・選択 -->
            <table width="870">
                <!-- 1列目 -->
                <tr>
                    <td width="60">新規原料</td>
                    <td width="50">
<!--                    <input type="checkbox" id="chkGenryo" name="chkGenryo" tabindex="1" CHECKED>-->
                        <input type="checkbox" id="chkGenryo" name="chkGenryo" tabindex="7" CHECKED>
                    </td>
                    <td width="60">既存原料</td>
                    <td width="50">
<!--                    <input type="checkbox" id="chkGenryo" name="chkGenryo" tabindex="2" CHECKED>-->
                        <input type="checkbox" id="chkGenryo" name="chkGenryo" tabindex="8" CHECKED>
                    </td>
                    <td width="*%">&nbsp;</td>
                </tr>
            </table>

<!--        <table width="960" datasrc="#xmlSA390I" datafld="rec">-->
            <table width="990" datasrc="#xmlSA390I" datafld="rec">
                <!-- 2列目 -->
                <tr>
                    <td rowspan="2">
<!--                    <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" style="height:32px;" onClick="funSearch();" tabindex="3">-->
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" style="height:32px;" onClick="funSearch();" tabindex="9">
                    </td>
                    <td>原料コード</td>
                    <td>
<!--                    <input type="text" class="disb_text" id="txtGenryoCd" name="txtGenryoCd" datafld="cd_genryo" maxlength="11" value="" style="width:200px;" tabindex="4">-->
                        <input type="text" class="disb_text" id="txtGenryoCd" name="txtGenryoCd" datafld="cd_genryo" maxlength="11" value="" style="width:200px;" tabindex="2">
                    </td>
                    <td>※データチェックと確認チェックは別に行って下さい</td>
                    <!--使用実績-->
                    <td><div id="divShiyoNm"></div></td>
                    <td>
                    	<input type="radio" name="rdoShiyo" id="rdoShiyo" value="0" tabindex="3"></input>
                    </td>
                    <td>会社<font color="red">（必須）</font></td>
                    <td>
<!--                    <select id="ddlKaisha" id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:200px;" onChange="funChangeKaisha();" tabindex="6">-->
                        <select id="ddlKaisha" id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:200px;" onChange="funChangeKaisha();" tabindex="5">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>原料名称</td>
                    <td>
<!--                    <input type="text" class="act_text" id="txtGenryoName" name="txtGenryoName" datafld="nm_genryo" maxlength="60" value="" style="width:200px;" tabindex="5">-->
                        <input type="text" class="act_text" id="txtGenryoName" name="txtGenryoName" datafld="nm_genryo" maxlength="60" value="" style="width:200px;" tabindex="1">
                    </td>
                    <td>※データ変換すると確認項目は消えます</td>
                    <!--全件-->
                    <td>全件</td>
                    <td>
                    	<input type="radio" name="rdoShiyo" id="rdoShiyo" value="1" tabindex="4" CHECKED></input>
                    </td>
                    <td>工場</td>
                    <td>
<!--                    <select id="ddlKojo" id="ddlKojo" name="ddlKojo" datafld="cd_kojo" style="width:200px;" tabindex="7">-->
                        <select id="ddlKojo" id="ddlKojo" name="ddlKojo" datafld="cd_kojo" style="width:200px;" onChange="funChangeKojo();" tabindex="6">
                        </select>
                    </td>
                </tr>
                <tr><td height="5"></tr>
            </table>

            <!-- [原料分析情報一覧]リスト -->
            <div class="scroll" id="sclList" style="height:60%;width:99%;" rowSelect="true">
<!--        <table id="dataTable" name="dataTable" cellspacing="0" width="2310px" align="center">-->
            <table id="dataTable" name="dataTable" cellspacing="0" width="2434px" align="center">
                <colgroup>
<!--                <col style="width:30px;"/>-->
                    <col style="width:80px;"/>
                    <col style="width:200px;"/>
                    <col style="width:20px;"/>		<!--使用実績-->
                    <col style="width:20px;"/>		<!--未使用-->
                    <col style="width:80px;"/>
                    <col style="width:80px;"/>
                    <col style="width:80px;"/>
                    <col style="width:80px;"/>
                    <col style="width:80px;"/>		<!--ADD QP@20505 20121005-->
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:82px;"/>
                    <col style="width:100px;"/>
                    <col style="width:100px;"/>
                    <col style="width:60px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
<!--                <th class="columntitle" width="30">&nbsp;</th>-->
                    <th class="columntitle" width="80">原料CD</th>
                    <th class="columntitle" width="200">原料名</th>
                    <th class="columntitle" width="20"><div id="divShiyoNmLst"></div></th>		<!--使用実績-->
                    <th class="columntitle" width="20">未<br>使</th>				<!--未使用-->
                    <th class="columntitle" width="80">酢酸<br>(%)</th>
                    <th class="columntitle" width="80">食塩<br>(%)</th>
                    <th class="columntitle" width="80">総酸<br>(%)</th>
                    <th class="columntitle" width="80">ＭＳＧ<br>(%)</th>			<!--ADD QP@20505 20121005-->
                    <th class="columntitle" width="80">油含<br>(%)</th>
                    <th class="columntitle" width="200">表示案</th>
                    <th class="columntitle" width="200">添加物</th>
                    <th class="columntitle" width="200">メモ</th>
                    <th class="columntitle" width="82">栄養計算<br>食品番号1</th>
                    <th class="columntitle" width="82">割合1<br>(%)</th>
                    <th class="columntitle" width="82">栄養計算<br>食品番号2</th>
                    <th class="columntitle" width="82">割合2<br>(%)</th>
                    <th class="columntitle" width="82">栄養計算<br>食品番号3</th>
                    <th class="columntitle" width="82">割合3<br>(%)</th>
                    <th class="columntitle" width="82">栄養計算<br>食品番号4</th>
                    <th class="columntitle" width="82">割合4<br>(%)</th>
                    <th class="columntitle" width="82">栄養計算<br>食品番号5</th>
                    <th class="columntitle" width="82">割合5<br>(%)</th>
                    <th class="columntitle" width="100">最終使用日</th>
                    <th class="columntitle" width="100">確定コード</th>
                    <th class="columntitle" width="60">廃止区</th>
                    </tr>
                </thead>
                <tbody>
<!--                <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA390O" datafld="rec" style="width:2310px;display:none">-->
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA390O" datafld="rec" style="width:2434px;display:none">
                        <tr class="disprow">
<!--                        <td class="column" width="32" align="right"><span datafld="no_row"></span></td>-->
                            <td class="column" width="82" align="left"><span datafld="cd_genryo"></span></td>
                            <td class="column" width="199" align="left"><span datafld="nm_genryo"></span></td>
                            <td class="column" width="21" align="center"><span datafld="flg_shiyo"></span></td>			<!--使用実績-->
                            <td class="column" width="21" align="center"><span datafld="flg_mishiyo"></span></td>		<!--未使用-->
                            <td class="column" width="82" align="right"><span datafld="ritu_sakusan"></span></td>
                            <td class="column" width="81" align="right"><span datafld="ritu_shokuen"></span></td>
                            <td class="column" width="81" align="right"><span datafld="ritu_sousan"></span></td>
                            <td class="column" width="81" align="right"><span datafld="ritu_msg"></span></td>			<!--ADD QP@20505 20121005-->
                            <td class="column" width="81" align="right"><span datafld="ritu_abura"></span></td>
                            <td class="column" width="200" align="left"><span datafld="hyojian"></span></td>
                            <td class="column" width="200" align="left"><span datafld="tenkabutu"></span></td>
                            <td class="column" width="200" align="left"><span datafld="memo"></span></td>
                            <td class="column" width="83" align="left"><span datafld="no_eiyo1"></span></td>
                            <td class="column" width="83" align="left"><span datafld="wariai1"></span></td>
                            <td class="column" width="83" align="left"><span datafld="no_eiyo2"></span></td>
                            <td class="column" width="83" align="left"><span datafld="wariai2"></span></td>
                            <td class="column" width="83" align="left"><span datafld="no_eiyo3"></span></td>
                            <td class="column" width="83" align="left"><span datafld="wariai3"></span></td>
                            <td class="column" width="83" align="left"><span datafld="no_eiyo4"></span></td>
                            <td class="column" width="83" align="left"><span datafld="wariai4"></span></td>
                            <td class="column" width="83" align="left"><span datafld="no_eiyo5"></span></td>
                            <td class="column" width="83" align="left"><span datafld="wariai5"></span></td>
                            <td class="column" width="100" align="left"><span datafld="dt_konyu"></span></td>
                            <td class="column" width="100" align="left"><span datafld="cd_kakutei"></span></td>
                            <td class="column" width="60" align="left"><span datafld="nm_haishi"></span></td>
                        </tr>
                    </table>
                </table>
            </table>
            </div>

            <table width="99%">
                <!-- データ数 -->
                <tr align="center">
                    <td height="18px">
                        <span id="spnRecInfo">データ数　：　<span id="spnRecCnt"></span> 件です(<span id="spnRowMax"></span>件毎に表示しています)　<span id="spnCurPage"></span></span>
                    </td>
                </tr>
            </table>

            <!-- ページリンク -->
            <div id="divPage" style="height:50px;font-size:12pt;"></div>

            <!-- ボタン部 -->
            <table width="99%">
                <tr>
                     <td align="left">
<!--                     <input type="button" class="normalbutton" id="btnEdit" name="btnEdit" value="詳細" style="height:32px;" onClick="funOpenGenryoInput(1);" tabindex="8">-->
                         <input type="button" class="normalbutton" id="btnEdit" name="btnEdit" value="詳細" style="height:32px;" onClick="funOpenGenryoInput(1);" tabindex="10">
                     </td>
                     <td align="left">
<!--                     <input type="button" class="normalbutton" id="btnNew" name="btnNew" value="新規原料" style="height:32px;" onClick="funOpenGenryoInput(2);" tabindex="9">-->
                         <input type="button" class="normalbutton" id="btnNew" name="btnNew" value="新規原料" style="height:32px;" onClick="funOpenGenryoInput(2);" tabindex="11">
                     </td>
                     <td align="left">
<!--                     <input type="button" class="normalbutton" id="btnDel" name="btnDel" value="削除" style="height:32px;" onClick="funDelete();" tabindex="10">-->
                         <input type="button" class="normalbutton" id="btnDel" name="btnDel" value="削除" style="height:32px;" onClick="funDelete();" tabindex="12">
                     </td>
                     <td align="left">
<!--                     <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="height:32px;" onClick="funClear();" tabindex="11">-->
                         <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="height:32px;" onClick="funClear();" tabindex="13">
                     </td>
                     <td width="220">&nbsp;</td>
                     <td align="right">
<!--                     <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel出力" style="height:32px;" onClick="funOutput();" tabindex="12">-->
                         <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel出力" style="height:32px;" onClick="funOutput();" tabindex="14">
                     </td>
                     <td align="right">
<!--                     <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="height:32px;" onClick="funNext(0);" tabindex="13">-->
                         <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="height:32px;" onClick="funNext(0);" tabindex="15">
                     </td>
                </tr>
            </table>
<!--mod end------------------------------------------------------->

            <input type="hidden" id="hidMode" name="hidMode" value="">
            <input type="hidden" id="hidKaishaCd" name="hidKaishaCd" value="">
            <input type="hidden" id="hidKojoCd" name="hidKojoCd" value="">
            <input type="hidden" id="hidGenryoCd" name="hidGenryoCd" value="">
            <!-- CSVﾌｧｲﾙﾊﾟｽ(Servletでの受信用) -->
            <input type="hidden" id="strFilePath" name="strFilePath" value="">
        </form>

        <input type="hidden" id="hidGenryoInfoKengen" name="hidGenryoInfoKengen" value="">
    </body>
</html>
