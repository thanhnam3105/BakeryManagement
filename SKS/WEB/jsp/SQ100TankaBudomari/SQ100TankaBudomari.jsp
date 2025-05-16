<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　全工場単価歩留画面                                                -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/05/19                                                              -->
<!-- 概要  ：検索条件に一致する原料データを一覧で表示する。                          -->
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
        <script type="text/javascript" src="include/SQ100TankaBudomari.js"></script>
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
        <xml id="xmlJSP1310"></xml>
        <xml id="xmlJSP1320"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA140I" src="../../model/SA140I.xml"></xml>
        <xml id="xmlSA790I" src="../../model/SA790I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlSA140O"></xml>
        <xml id="xmlSA790O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" onsubmit="" method="post">
            <table width="99%">
                <tr>
                    <td width="18%" class="title">全工場単価歩留</td>
                    <!-- ユーザー情報 -->
                    <td width="68%"><div id="divUserInfo"></div></td>
                </tr>
            </table>

            <br>

            <!-- 入力・選択 -->
            <table width="960" datasrc="#xmlSA390I" datafld="rec">
                <tr>
                    <td>会社<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:180px;" tabindex="1">
                        </select>
                    </td>
                    <td>コード</td>
                    <td>
                        <input type="text" class="disb_text" id="txtGenryoCd" name="txtGenryoCd" datafld="cd_genryo" maxlength="11" value="" style="width:180px;" tabindex="2">
                    </td>
                    <td rowspan="2">
                        <table border="1" bordercolor="black">
                            <tr>
                                <td>
                                    <input type="radio" name="rdoGenryoKbn" id="rdoGenryoKbn" value="0" tabindex="4" CHECKED>原料&nbsp;　&nbsp;<br>
                                    <input type="radio" name="rdoGenryoKbn" id="rdoGenryoKbn" value="1" tabindex="5">資材
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td rowspan="2">
                        <table border="1" bordercolor="black">
                            <tr>
                                <td>
                                    <input type="radio" name="rdoTaisho" id="rdoTaisho" value="0" tabindex="6" CHECKED>単価&nbsp;　&nbsp;<br>
                                    <input type="radio" name="rdoTaisho" id="rdoTaisho" value="1" tabindex="7">歩留
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td rowspan="2">
                        <input type="button" class="normalbutton" id="btnSearch" name="btnSearch" value="検索" style="height:32px;" onClick="funSearch();" tabindex="8">
                    </td>
                    <td width="170" rowspan="2" align="right">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="height:32px;" onClick="funNext(0);" tabindex="9">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>名前</td>
                    <td>
                        <input type="text" class="act_text" id="txtGenryoName" name="txtGenryoName" datafld="nm_genryo" maxlength="60" value="" style="width:180px;" tabindex="3">
                    </td>
                </tr>
            </table>

            <!-- [全工場単価歩留一覧]リスト -->
            <div class="scroll" id="sclList" style="height:67%;width:99%;" rowSelect="true">
            <div id="divTableList">
            <!-- <table id="dataTable" name="dataTable" cellspacing="0" width="950px" align="center"> -->
            <table id="dataTable" name="dataTable" cellspacing="0" width="950px" align="left">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:80px;"/>
                    <col style="width:200px;"/>
                    <col style="width:650px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle" width="30">&nbsp;</th>
                        <th class="columntitle" width="80">原料CD</th>
                        <th class="columntitle" width="200">原料名</th>
                        <th class="columntitle" width="650">&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlSA790O" datafld="rec" style="width:950px;display:none">
                        <tr class="disprow">
                            <td class="column" width="32" align="right"><span datafld="no_row"></span></td>
                            <td class="column" width="82" align="left"><span datafld="cd_genryo"></span></td>
                            <td class="column" width="202" align="left"><span datafld="nm_genryo"></span></td>
                            <td class="column" id="lblCol" width="650" align="left"><span datafld="disp_val1"></span></td>
                        </tr>
                    </table>
                </table>
            </table>
            </div>
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
        </form>
    </body>
</html>
