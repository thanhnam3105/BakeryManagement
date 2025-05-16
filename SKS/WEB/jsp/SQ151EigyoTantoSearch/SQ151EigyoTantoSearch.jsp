<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　担当者検索画面                                                    -->
<!-- 作成者：TT.Jinbo                                                                -->
<!-- 作成日：2009/04/06                                                              -->
<!-- 概要  ：条件に一致する担当者の検索を行う。                                      -->
<!------------------------------------------------------------------------------------->
<!-- 更新者：TT.Jinbo                                                                -->
<!-- 更新日：2009/06/24                                                              -->
<!-- 内容  ：リストの表示高さを広げる(課題表№14)                                    -->
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
        <script type="text/javascript" src="include/SQ151EigyoTantoSearch.js"></script>
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
            //選択行の背景色を変更
            funChangeSelectRowColor();
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();" topmargin="20" leftmargin="8" marginheight="20" marginwidth="5">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN2080"></xml>
        <xml id="xmlRGEN2090"></xml>
        <xml id="xmlRGEN2100"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2090I" src="../../model/FGEN2090I.xml"></xml>
        <xml id="xmlFGEN2100I" src="../../model/FGEN2100I.xml"></xml>
        <xml id="xmlFGEN2110I" src="../../model/FGEN2110I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2090O"></xml>
        <xml id="xmlFGEN2100O"></xml>
        <xml id="xmlFGEN2110O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="18%" class="title">担当者検索（営業）</td>
                    <td width="65%" align="right">
                        <input type="button" class="normalbutton" name="btnSearch" id="btnSearch" value="検索" onClick="funSearch();">
                        <input type="button" class="normalbutton" name="btnSelect" id="btnSelect" value="選択" onClick="funSelect();">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" onClick="funClear();">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funClose();">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- 入力・選択 -->
            <table width="900" datasrc="#xmlSA240I" datafld="rec">
                <tr>
                    <td width="120">ユーザID</td>
                    <td width="230">
                        <span class="ninput" format="10,0" comma="false" required="true" defaultfocus="false" id="em_UserId">
                        <input type="text" class="disb_text" id="txtUserId" name="txtUserId" datafld="id_user" maxlength="10" value="" style="width:200px;" onBlur="funBuryZero(this, 10);" tabindex=1>
                        </span>
                    </td>
                    <td width="120">所属会社</td>
                    <td width="230">
                        <select id="ddlKaisha" name="ddlKaisha" datafld="cd_kaisha" style="width:200px;" onChange="funChangeKaisha();" tabindex=3>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" height="5"></td>
                </tr>
                <tr>
                    <td>担当者名</td>
                    <td>
                        <input type="text" class="act_text" id="txtTantoName" name="txtTantoName" datafld="nm_user" maxlength="60" value="" style="width:200px;" tabindex=2>
                    </td>
                    <td>所属部署</td>
                    <td>
                        <select id="ddlBusho" name="ddlBusho" datafld="cd_busho" style="width:200px;" tabindex=4>
                        </select>
                    </td>
                </tr>
            </table>

            <br>

            <!-- [ユーザー一覧]リスト -->
            <div class="scroll" id="sclList" style="height:67%;width:99%;" rowSelect="true" border=1>
            <table id="dataTable" name="dataTable" cellspacing="0" width="1050px" align="center">
                <colgroup>
                    <col style="width:30px;"/>
                    <col style="width:100px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <col style="width:200px;"/>
                    <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start -->
                    <!-- <col style="width:100px;"/> -->
                    <col style="width:90px;"/>
                    <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end -->
                    <col style="width:200px;"/>
                </colgroup>
                <thead class="rowtitle">
                    <tr style="top:expression(offsetParent.scrollTop);position:relative;">
                        <th class="columntitle">&nbsp;</th>
                        <th class="columntitle">ユーザID</th>
                        <th class="columntitle">担当者名</th>
                        <th class="columntitle">所属会社</th>
                        <th class="columntitle">所属部署</th>
                        <th class="columntitle">本部権限</th>
                        <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start -->
                        <!-- <th class="columntitle">担当上司</th> -->
                        <th class="columntitle">共有メンバー</th>
                        <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end -->
                    </tr>
                </thead>
                <tbody>
                    <table class="detail" id="tblList" cellpadding="0" cellspacing="0" border="1" datasrc="#xmlFGEN2110O" datafld="rec" style="width:1050px;display:none">
                        <tr class="disprow">
                            <td class="column" width="32" align="right"><span datafld="no_row"></span></td>
                            <td class="column" width="100" align="left"><span datafld="id_user"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_user"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_kaisha"></span></td>
                            <td class="column" width="200" align="left"><span datafld="nm_busho"></span></td>
                            <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 start -->
                            <!-- <td class="column" width="100" align="left"><span datafld="kbn_honbu"></span></td> -->
                            <td class="column" width="90" align="left"><span datafld="kbn_honbu"></span></td>
                            <!-- セル内改行を有効にする -->
                            <!-- <td class="column" width="200" align="left"><span datafld="nm_josi"></span></td> -->
                            <td class="column" width="200" align="left"><span datafld="nm_member" dataformatas="html"></span></td>
                            <!-- MOD 2015/05/15 TT.Kitazawa【QP@40812】No.19 end -->
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

        </form>
    </body>
</html>
