<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　原資材調達部用カテゴリマスタメンテナンス画面                      -->
<!-- 作成者：hisahori                                                                -->
<!-- 作成日：2014/10/07                                                              -->
<!-- 概要  ：リテラルデータの登録、更新、削除を行う。                                -->
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
        <script type="text/javascript" src="include/SQ061GentyoLiteralMst.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <script for=ddlCategory event=onkeydown>
            if (event.keyCode == 13 && hidGamenId.value == ConGmnIdLiteralCsv) {
                //最後のｺﾝﾄﾛｰﾙでEnterｷｰ押下の場合、ﾀﾞﾐｰﾎﾞﾀﾝにﾌｫｰｶｽを設定
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=ddlLiteralsa110 event=onkeydown>
            if (event.keyCode == 13 && document.frm00.ddlUseEdit.selectedIndex == 0) {
                //最後のｺﾝﾄﾛｰﾙでEnterｷｰ押下の場合、ﾀﾞﾐｰﾎﾞﾀﾝにﾌｫｰｶｽを設定
                document.frm00.btnDummy.focus();
            }
        </script>

        <script for=ddlGroup event=onkeydown>
            if (event.keyCode == 13) {
                //最後のｺﾝﾄﾛｰﾙでEnterｷｰ押下の場合、ﾀﾞﾐｰﾎﾞﾀﾝにﾌｫｰｶｽを設定
                document.frm00.btnDummy.focus();
            }
        </script>
    </head>

    <body class="pfcancel" onLoad="funLoad();">
        <!-- XML Document定義 -->
        <xml id="xmlJSP0611"></xml>  <!-- 610→611 変更済み -->
        <xml id="xmlJSP0621"></xml>  <!-- 620→621 変更済み -->
        <xml id="xmlJSP0631"></xml>  <!-- 630→631 変更済み -->
        <xml id="xmlJSP0632"></xml>  <!-- 追加 -->
        <xml id="xmlJSP0641"></xml>  <!-- 640→641 変更済み -->
        <xml id="xmlJSP0650"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlSA041I" src="../../model/SA041I.xml"></xml>  <!-- 40→41 変更済み -->
        <xml id="xmlSA050I" src="../../model/SA050I.xml"></xml>
        <xml id="xmlSA101I" src="../../model/SA101I.xml"></xml>  <!-- 100→101 変更済み -->
        <xml id="xmlSA102I" src="../../model/SA102I.xml"></xml>  <!-- 新規追加 -->
        <xml id="xmlSA111I" src="../../model/SA111I.xml"></xml>  <!-- 110→111 変更済み -->
        <xml id="xmlSA300I" src="../../model/SA300I.xml"></xml>
        <xml id="xmlSA320I" src="../../model/SA320I.xml"></xml>
        <xml id="xmlSA331I" src="../../model/SA331I.xml"></xml>  <!-- 330→331 変更済み -->
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

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="30%" class="title">原資材調達部用<br>カテゴリマスタメンテナンス</td>
                    <td width="70%" align="right">
                        <input type="button" id="btnDummy" name="btnDummy" value="" style="width:0px" tabindex=-1> <!-- ﾌｫｰｶｽ設定用ﾀﾞﾐｰﾎﾞﾀﾝ -->
                        <input type="button" class="normalbutton" id="btnInsert" name="btnInsert" value="新規" style="width:80px" onClick="funDataEdit(1);">
                        <input type="button" class="normalbutton" id="btnUpdate" named="btnUpdate" value="更新" style="width:80px" onClick="funDataEdit(2);">
                        <input type="button" class="normalbutton" id="btnDelete" name="btnDelete" value="削除" style="width:80px" onClick="funDataEdit(3);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="クリア" style="width:80px" onClick="funClear();">
                        <!-- <input type="button" class="normalbutton" id="btnExcel" name="btnExcel" value="Excel" style="width:80px" onClick="funOutput();"> -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:80px" onClick="funNext(0);">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <br>
            <div id="divUserInfo"></div>
            <br>

            <!-- 入力・選択 -->
            <table width="760" datasrc="#xmlSA331I" datafld="rec">
                <tr>
                    <td width="125">カテゴリ<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlCategory" name="ddlCategory" datafld="cd_category" style="width:400px;" onChange="funChangeCategory();">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>リテラル</td>
                    <td>
                        <select id="ddlLiteral" name="ddlLiteral" style="width:400px;" onChange="funSearch();" onaChange="funSearch();">
                        </select>
                        <input type="checkbox" id="chk2ndliteral" name="chk2ndliteral" onClick="click_2ndliteral();" tabindex="7">第二リテラルを使用
                    </td>
                </tr>
                <tr>
                    <td>第２リテラル</td>
                    <td>
                        <select id="ddl2ndLiteral" name="ddl2ndLiteral" style="width:400px;" onChange="funSearch2nd();" onaChange="funSearch2nd();">
                        </select>
                    </td>
                </tr>

                <!-- Line -->
                <tr>
                    <td colspan="3" align="center">
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td>リテラル名<font color="red">（必須）</font></td>
                    <td>
                        <input type="text" class="act_text" id="txtLiteralName" name="txtLiteralName" datafld="nm_literal" maxlength="60" value="" style="width:520px;">
                    </td>
                </tr>
                <tr>
                    <td>表示順<font color="red">（必須）</font></td>
                    <td>
                        <span class="ninput" format="3,0" required="true" defaultfocus="false" id="em_SortNo">
                        <input type="text" class="disb_text" id="txtSortNo" name="txtSortNo" datafld="no_sort" maxlength="3" value="" style="width:150px;">
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>備考</td>
                    <td>
                        <textarea class="act_area" id="txtBikou" name="txtBikou" datafld="biko" cols="100" rows="5" value=""></textarea>
                    </td>
                </tr>
                <tr>
                    <td>編集可否<font color="red">（必須）</font></td>
                    <td>
                        <select id="ddlUseEdit" name="ddlUseEdit" datafld="flg_edit" style="width:150px;">
                        </select>
                    </td>
                </tr>

                <!-- Line -->
                <tr>
                    <td colspan="3" align="center">
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td>第２リテラル名<input type="text"style="border:none; color:#ff0000; width:40px;" id="txt2ndliteralHissu" name="txt2ndliteralHissu" value="（必須）"></td>
                    <td>
                        <input type="text" class="act_text" id="txt2ndLiteralName" name="txt2ndLiteralName" datafld="nm_2nd_literal" maxlength="60" value="" style="width:520px;">
                    </td>
                </tr>
                <tr>
                    <td>表示順<input type="text" style="border:none; color:#ff0000; width:40px;" id="txt2ndHyojiHissu" name="txt2ndHyojiHissu" value="（必須）"></td>
                    <td>
                        <span class="ninput" format="3,0" required="true" defaultfocus="false" id="em_2ndSortNo">
                        <input type="text" class="disb_text" id="txt2ndSortNo" name="txt2ndSortNo" datafld="no_2ndsort" maxlength="3" value="" style="width:150px;">
                        </span>
                    </td>
                </tr>
            </table>

            <!-- CSVﾌｧｲﾙﾊﾟｽ(Servletでの受信用) -->
            <input type="hidden" id="strFilePath" name="strFilePath" value="">
            <input type="hidden" id="hidEditMode" name="hidEditMode" value="">
            <input type="hidden" id="hidHissuMark" name="hidHissuMark" value="（必須）">
        </form>

        <input type="hidden" id="hidGamenId" name="hidGamenId" value="">
        <input type="hidden" id="hidKinoId" name="hidKinoId" value="">
    </body>
</html>
