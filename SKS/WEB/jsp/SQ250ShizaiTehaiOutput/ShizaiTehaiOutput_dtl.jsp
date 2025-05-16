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
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>

        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>

        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

    </head>

    <body class="pfcancel" onLoad="funLoad_dtl();">
        <!-- XML Document定義 -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>

        <xml id="xmlRGEN3310"></xml>

        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN3200I" src="../../model/FGEN3200I.xml"></xml>
        <xml id="xmlFGEN3290I" src="../../model/FGEN3290I.xml"></xml>
        <xml id="xmlFGEN3310I" src="../../model/FGEN3310I.xml"></xml>
        <xml id="xmlFGEN3680I" src="../../model/FGEN3680I.xml"></xml>
        <xml id="xmlFGEN3700I" src="../../model/FGEN3700I.xml"></xml>
        <xml id="xmlFGEN3730I" src="../../model/FGEN3730I.xml"></xml>

        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <xml id="xmlFGEN3200O"></xml>
        <xml id="xmlFGEN3290O"></xml>
        <xml id="xmlFGEN3310O"></xml>
        <xml id="xmlFGEN3680O"></xml>
        <xml id="xmlFGEN3700O"></xml>
        <xml id="xmlFGEN3730O"></xml>
        <xml id="xmlRESULT"></xml>

        <form name="frm00" id="frm00" method="post" width="99%">
            <!-- 入力・選択 -->
            <table>
                <tr>
                    <td width="120">新版／改版</td>
                    <td width="655">
                        <input type="radio" id="revision_new" name="revision" value="1" checked="checked" onclick="funKaihan(1)">
                        <label for="revision_new">新版</label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" id="revision_rev" name="revision" value="2" onclick="funKaihan(2)">
                        <label for="revision_rev">改版</label>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">発注先１</td>
                    <td>
                        <select name="cmbOrderCom1" id="cmbOrderCom1" style="width:245px" onChange="funChangeHattyusaki(1);" >
                        </select>
                        <select id="txtOrderUser1" name="txtOrderUser1" style="width:400px;"></select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">発注先２</td>
                    <td>
                        <select name="cmbOrderCom2" id="cmbOrderCom2" style="width:245px"onChange="funChangeHattyusaki(2);" >
                        </select>
                        <select class="act_text" id="txtOrderUser2" name="txtOrderUser2"  style="width:400px;"></select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">内　容</td>
                    <td>
                        <input type="text" class="act_text" id="txtNaiyo" name="txtNaiyo" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">製品コード</td>
                    <td>
                        <input type="text" class="disb_text" id="txtSyohin" name="txtSyohin" value="" readOnly style="width:650px;">
                    </td>
                    <td>
                    	 <input type="button" class="normalbutton" id="btnYobidasi" name="btnYobidasi" value="複数一括選択" style="width:100px" onClick="funShizaiSearch(1);">
                    </td>
                </tr>
                <tr>
                    <td width="120">製品名</td>
                    <td>
                        <textarea class="act_text" name="txtHinmei" id="txtHinmei" cols="50" rows="4" style="width:650px;"></textarea>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">荷　姿</td>
                    <td>
                        <input type="text" class="act_text" id="txtNisugata" name="txtNisugata" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">対象資材</td>
                    <td>
                        <select name="cmbTargetSizai" id="cmbTargetSizai" style="width:200px">
                        </select>
                    </td>
                    <td></td>
                </tr>
                <!--
                <tr>
                    <td width="120">発注先</td>
                    <td>
                        <select name="cmbOrder" id="cmbOrder" style="width:200px">
                        </select>
                    </td>
                    <td></td>
                </tr>
                 -->
                <tr>
                    <td width="120">納入先</td>
                    <td>
                        <input type="text" class="act_text" id="txtDelivery" name="txtDelivery" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">旧資材コード</td>
                    <td>
                        <input type="text" class="disb_text" id="txtOldShizai" name="txtOldShizai" value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">新資材コード</td>
                    <td>
                        <input type="text" class="disb_text" id="txtNewShizai" name="txtNewShizai"  value="" style="width:650px;">
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">仕様変更</td>
                    <td width="340">
                        <p id="siyohenko">
                            <input type="radio" id="specificationChange_ari" name="specificationChange" value="1" checked="checked">
                            <label for="specificationChange_ari">あり</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="specificationChange_nasi" name="specificationChange" value="2">
                            <label for="specificationChange_nasi">無し</label>
                        </p>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="120">設計①</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign1" name="txtDesign1" value="" maxlength="250" style="width:650px;backgroundColor:#ffffff" onChange="funCheckComplete(1);setFg_hensyu();" >
                    </td>
                    <td width="100">
                        <input type="button" class="normalbutton" id="btnRuizi" name="btnRuizi" value="類似データ呼出" style="width:100px" onClick="funShizaiSearch(2);">
                    </td>
                </tr>
                <tr>
                    <td width="120">設計②</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign2" name="txtDesign2" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(2);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">設計③</td>
                    <td>
                        <input type="text" class="act_text" id="txtDesign3" name="txtDesign3" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(3);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">材質</td>
                    <td>
                        <input type="text" class="act_text" id="txtZaishitsu" name="txtZaishitsu" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(4);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">備考</td>
                    <td>
                        <textarea class="act_area" name="txtBiko" id="txtBiko" maxlength="250" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(5);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">印刷色</td>
                    <td>
                        <input type="text" class="act_text" id="txtPrintColor" name="txtPrintColor" value="" maxlength="250" style="width:350px;" onChange="funCheckComplete(6);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">色番号</td>
                    <td>
                        <input type="text" class="act_text" id="txtColorNo" name="txtColorNo" value="" maxlength="250" style="width:650px;" onChange="funCheckComplete(7);setFg_hensyu();">
                    </td>
                    <td width="100">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">変更内容詳細</td>
                    <td>
                        <textarea class="act_area" name="txtChangesDetail" id="txtChangesDetail" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(8);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">納期</td>
                    <td>
                        <textarea class="act_area" name="txtDeliveryTime" id="txtDeliveryTime" cols="50" rows="2" style="width:650px;" onChange="funCheckComplete(9);setFg_hensyu();"></textarea>
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">数量</td>
                    <td>
                        <input type="text" class="act_text" id="txtQuantity" name="txtQuantity" value="" style="width:650px;" onChange="funCheckComplete(10);setFg_hensyu();">
                    </td>
                    <td>
                        <input type="button" class="normalbutton" value="確認" style="width:50px" onClick="funCheckCompleteALL();">
                    </td>
                </tr>
                <tr>
                    <td width="120">旧資材在庫</td>
                    <td>
                        <input type="text" class="act_text" id="txtOldShizaiZaiko" name="txtOldShizaiZaiko" value="旧資材在庫を、納入先工場へご連絡下さい。" style="width:650px;" onChange="funCheckComplete(11);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
                <tr>
                    <td width="120">落版</td>
                    <td>
                        <input type="text" class="act_text" id="txtRakuhan" name="txtRakuhan" value="工場とご連動の上、確実に落版をお願いします" style="width:650px;" onChange="funCheckComplete(12);setFg_hensyu();">
                    </td>
                    <td width="50">
                       　
                    </td>
                </tr>
            </table>
            <!-- cho 2016/09/27 -->
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
            <!-- 追加 -->
            <!-- 内容 -->
            <input type="hidden" id="hdnNaiyo_disp" name="hdnNaiyo_disp" />
            <!-- 納入先 -->
            <input type="hidden" id="hdnNounyusaki_disp" name="hdnNounyusaki_disp" />

            <!-- 処理用社員コード -->
            <input type="hidden" id="hdnShain" name="hdnCdShain" />
            <!-- 処理用年コード -->
            <input type="hidden" id="hdnNen" name="hdnNen" />
            <!-- 処理用追番 -->
            <input type="hidden" id="hdnNoOi" name="hdnNoOi" />
            <!-- 処理用資材順番 -->
            <input type="hidden" id="hdnSeqShizai" name="hdnSeqShizai" />
            <!-- 処理用枝番 -->
            <input type="hidden" id="hdnNoEda" name="hdnNoEda" />
            <!-- 処理用製品コード -->
            <input type="hidden" id="hdnCdShohin" name="hdnCdShohin" />

            <!-- 資材手配入力からのパラメータ -->
            <!--  社員コード -->
            <input type="hidden" id="cd_shain" name="cd_shain" />
            <!-- 年コード -->
            <input type="hidden" id="nen" name="nen" />
            <!-- 追番 -->
            <input type="hidden" id="no_oi" name="no_oi" />
            <!-- seq資材 -->
            <input type="hidden" id="seq_shizai" name="seq_shizai" />
            <!-- 枝番 -->
            <input type="hidden" id="no_eda" name="no_eda" />
            <!-- フラグ -->
            <input type="hidden" id="flg_hatyuu_status" name="flg_hatyuu_status" />
            <!-- 発注コードhidden -->
			<input type="hidden" id="hidden_Hattyuusaki_cd" name="hidden_Hattyuusaki_cd" />
			<!--対象資材hidden  -->
			<input type="hidden" id="hidden_target_sizai" name="hidden_target_sizai" />
            <!-- 製品コード -->
			<input type="hidden" id="shohinCd" name="shohinCd" />
			<!-- 製品名 -->
			<input type="hidden" id="hinmei" name="hinmei" />
			<!-- 荷姿 -->
			<input type="hidden" id="nisugata" name="nisugata" />
			<!-- 納入先 -->
			<input type="hidden" id="delivery" name="delivery" />
			<!-- 旧資材コード -->
			<input type="hidden" id="olsShizai" name="olsShizai" />
			<!-- 新資材コード -->
			<input type="hidden" id="newShizai" name="newShizai" />


            <div id="sizaid">
			</div>


			<!-- 資材手配入力画面遷移後、資材手配テーブル取得のデータ -->
<!-- 			<input type="hidden" id="tantosyamei1" name ="tantosyamei1" /> -->

			<!-- ループカウント -->
			<input type="hidden" name="loopCnt" name="loopCnt" />

        </form>

        <br><br><br><br>
        <br><br><br><br>
        <br><br><br><br>
        <br>

    </body>
</html>