<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　資材手配依頼書出力画面                                            -->
<!-- 作成者：TT.Shima                                                                -->
<!-- 作成日：2014/09/05                                                              -->
<!-- 概要  ：資材手配依頼書情報を入力する                                            -->
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
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>
        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad_head();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3320"></xml>
        <xml id="xmlRGEN3360"></xml>
        <xml id="xmlRGEN3370"></xml>
        <xml id="xmlRGEN3380"></xml>
        <xml id="xmlRGEN3390"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3310"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN3320I" src="../../model/FGEN3320I.xml"></xml>
        <xml id="xmlFGEN3360I" src="../../model/FGEN3360I.xml"></xml>
        <xml id="xmlFGEN3370I" src="../../model/FGEN3370I.xml"></xml>
        <xml id="xmlFGEN3380I" src="../../model/FGEN3380I.xml"></xml>
        <xml id="xmlFGEN3390I" src="../../model/FGEN3390I.xml"></xml>
        <xml id="xmlFGEN3680I" src="../../model/FGEN3680I.xml"></xml>
        <xml id="xmlFGEN3700I" src="../../model/FGEN3700I.xml"></xml>
        <xml id="xmlFGEN3730I" src="../../model/FGEN3700I.xml"></xml>
<xml id="xmlFGEN3290I" src="../../model/FGEN3290I.xml"></xml>
<xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlFGEN3320O"></xml>
        <xml id="xmlFGEN3360O"></xml>
        <xml id="xmlFGEN3370O"></xml>
        <xml id="xmlFGEN3380O"></xml>
        <xml id="xmlFGEN3390O"></xml>
        <xml id="xmlFGEN3680O"></xml>
        <xml id="xmlFGEN3700O"></xml>
        <xml id="xmlFGEN3730O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN3290O"></xml>
        <xml id="xmlFGEN3310O"></xml>

        <form name="frm00" id="frm00" method="post">
            <!-- タイトル部 -->
            <!-- タイトル・ボタン -->
            <table width="99%">
                <tr>
                    <td width="25%" class="title">資材手配依頼書出力</td>
                    <td width="75%" align="right">
                        <input type="button" class="normalbutton" id="aaa" name="btnHaashin" value="発信" style="width:100px" onClick="funNextLogic(1);">
                        <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="仮保存" style="width:100px" onClick="funNextLogic(2);">
                        <input type="button" class="normalbutton" id="btnReference" name="btnReference" value="参考資料" style="width:100px" onClick="funReference(1);">
                        <input type="button" class="normalbutton" id="btnClear" name="btnClear" value="削除" style="width:100px" onClick="funNextLogic(3);">
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" style="width:100px" onClick="funEnd();">

                        <!--  <input type="button" class="normalbutton" id="btntest" name="btntest" value="test" style="width:100px" onClick="funtest();"> -->
                       <!-- <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="複数一括選択" style="width:100px" onClick="funShizaiSearch(1);">  -->
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

            <!-- 類似呼出選択完了チェック -->
            <input type="hidden" id="hdnRuiziSelect" name="hdnRuiziSelect" value="false" />

            <!-- 表示用発注先コード -->
            <input type="hidden" id="hdnTaisyosizai_disp" name="hdnTaisyosizai_disp" />
            <!-- 表示用発注先コード -->
            <input type="hidden" id="hdnHattyusaki_disp" name="hdnHattyusaki_disp" />
            <!-- 表示用商品コード -->
            <input type="hidden" id="hdnSyohin_disp" name="hdnSyohin_disp" />
            <!-- 表示用品名 -->
            <input type="hidden" id="hdnHinmei_disp" name="hdnHinmei_disp" />
            <!-- 表示用荷姿 -->
            <input type="hidden" id="hdnNisugata_disp" name="hdnNisugata_disp" />
            <!-- 表示用旧資材コード -->
            <input type="hidden" id="hdnOldShizai_disp" name="hdnOldShizai_disp" />
            <!-- 表示用新資材コード -->
            <input type="hidden" id="hdnNewShizai_disp" name="hdnNewShizai_disp" />
            <!-- 処理用手配ステータス -->
            <input type="hidden" id="hdnStatus" name="hdnStatus" />

            <!-- Excelファイルパス -->
            <input type="hidden" id="strFilePath" name="strFilePath" />
            <input type="hidden" id="strServerConst" name="strServerConst" />
            <!-- Excelファイル名 -->
            <input type="hidden" id="strExcelFilePath" name="strExcelFilePath" />
            <!-- PDFファイル名 -->
            <input type="hidden" id="strPdfFilePath" name="strPdfFilePath" />
            <!-- 参考資料 -->
            <input type="hidden" id="sq220Category" name="sq220Category" />

            <!-- 編集チェック -->
            <input type="hidden" id="hdnHensyu" name="hdnHensyu" value="false" />

            <!-- メーラーのメールに設定するメールアドレス１ -->
            <input type="hidden" id="strMaiAddress1" name="strMaiAddress1" />
            <!-- メーラーのメールに設定するメールアドレス２ -->
            <input type="hidden" id="strMaiAddress2" name="strMaiAddress2" />
            <input type="hidden" id="hdMeilAddress" name="hdMeilAddress" value="false" />
            <!-- 発信ボタン押下時Excelのパス設定用 -->
            <input type="hidden" id="hdExcelPass" name="hdExcelPass" value="false" />

        </form>
    </body>
</html>