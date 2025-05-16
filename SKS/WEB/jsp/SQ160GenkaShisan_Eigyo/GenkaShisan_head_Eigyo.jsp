<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　原価試算画面（ヘッダー）                                                 -->
<!-- 作成者：TT.nishigawa                                                             -->
<!-- 作成日：2009/10/20                                                              -->
<!-- 概要  ：原価試算画面のヘッダー部フレーム                                                                        -->
<!------------------------------------------------------------------------------------->
<html>

	<script type="text/javascript">
    <!--
    	//終了ボタン押下確認
	    var end_click = false;

//20160628 KPX@502111_No.1 ADD start
    window.onbeforeunload = function() {
        if( event.clientY < 0 || event.altKey ) {
            var mes = "「キャンセル」を選択して「終了」ボタンで閉じてください。\n\n";
            mes = mes + "「×」ボタンで画面を閉じると不具合が発生し、登録されている内容が失われる可能性があります。" ;
            event.returnValue = mes;
        }
    }
//20160628 KPX@502111_No.1 ADD end

		window.onunload = function() {

			//終了ボタン押下
			if(end_click){

			}
			//ウィンドウの×ボタン押下
			else{
				//排他解除
				funcHaitaKaijo(1);

				//メッセージ表示
				alert(E000021);
			}
		}
	// -->
	</script>

    <head>
        <title>原価試算システム 原価試算画面（営業）</title>
        <!-- 共通 -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start -->
        <script type="text/javascript" src="../common/js/MailControl.js"></script>
        <!-- ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 end -->
        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ160GenkaShisan.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
    </head>

    <body class="pfcancel" onLoad="funLoad_head();" tabindex="-1">

        <!-- XML Document定義 -->
        <xml id="xmlRGEN0010"></xml>
        <xml id="xmlUSERINFO_I" src="../../model/USERINFO.xml"></xml>
        <xml id="xmlFGEN0010I" src="../../model/FGEN0010I.xml"></xml>
        <xml id="xmlUSERINFO_O"></xml>
        <xml id="xmlFGEN0010O"></xml>
        <xml id="xmlRESULT"></xml>
        <xml id="xmlFGEN2020I" src="../../model/FGEN2020I.xml"></xml>
        <xml id="xmlFGEN2020O"></xml>

        <xml id="xmlRGEN0041"></xml>
        <xml id="xmlFGEN0040I" src="../../model/FGEN0040I.xml"></xml>
        <xml id="xmlFGEN0040O"></xml>

        <xml id="xmlRGEN0030"></xml>
        <xml id="xmlFGEN0030I" src="../../model/FGEN0030I.xml"></xml>
        <xml id="xmlFGEN0030O"></xml>

<!--  DEL 2015/03/03 TT.Kitazawa【QP@40812】No.1 start -->
<!--         <xml id="xmlRGEN0050"></xml>
        <xml id="xmlFGEN0020I" src="../../model/FGEN0020I.xml"></xml>
        <xml id="xmlFGEN0020O"></xml>

        <xml id="xmlRGEN0051"></xml>
        <xml id="xmlFGEN0050I" src="../../model/FGEN0050I.xml"></xml>
        <xml id="xmlFGEN0050O"></xml>
 -->
<!--  DEL 2015/03/03 TT.Kitazawa【QP@40812】No.1 end -->
<!--  ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 start -->
        <xml id="xmlRGEN2240"></xml>
        <xml id="xmlFGEN2240I" src="../../model/FGEN2240I.xml"></xml>
        <xml id="xmlFGEN2240O"></xml>
<!--  ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 end -->

        <!--2010/02/23 NAKAMURA ADD START------------------------------>
        <xml id="xmlRGEN0090"></xml>
        <xml id="xmlFGEN0090I" src="../../model/FGEN0090I.xml"></xml>
        <xml id="xmlFGEN0090O"></xml>
        <!--2010/02/23 NAKAMURA ADD END-------------------------------->

        <xml id="xmlRGEN2160"></xml>
        <!-- ADD 2013/10/22 QP@30154 okano start -->
        <xml id="xmlFGEN2130I" src="../../model/FGEN2130I.xml"></xml>
        <xml id="xmlFGEN2130O"></xml>
        <!-- ADD 2013/10/22 QP@30154 okano end -->

        <form name="frm00" id="frm00" method="post">
            <table width="99%">
                <tr>
                    <td width="19%" class="title">原価試算画面（営業）</td>
                    <td width="10%" class="title2">レベル１</td>

                    <!-- 2010/05/12 シサクイック（原価）要望 【案件No9,12】排他情報,ヘルプの表示　TT西川 START -->
                    <td width="25%" align="left">
                    </td>
                    <!-- 2010/05/12 シサクイック（原価）要望 【案件No9,12】排他情報,ヘルプの表示　TT西川 END -->

                    <td width="45%" align="right">
                        <input type="button" class="normalbutton" id="btnToroku" name="btnToroku" value="登録" onClick="funToroku()" tabindex="1">
                        <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 start -->
                        <input type="button" class="normalbutton" id="btnInsatu" name="btnInsatu" value="印刷" onClick="funInsatu()" tabindex="2">
                        <!-- ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 end -->
                        <input type="button" class="normalbutton" id="btnEnd" name="btnEnd" value="終了" onClick="funEnd();end_click=true;" tabindex="3">
                    </td>
                </tr>
            </table>

            <!-- ユーザー情報 -->
            <div id="divUserInfo"></div>

            <!-- 入力・選択 -->
            <table width="100%" cellpadding="0" cellspacing="0" datasrc="" datafld="" border="0">
                <!-- 1行目 -->
                <tr>
                    <td width="100">試作コード</td>
                    <td width="400">
                        <input type="text" class="disb_text" id="txtShainCd" name="txtShainCd" readonly datafld="" maxlength="" value="" style="width:100px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtNen" name="txtNen" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                        -&nbsp;
                        <input type="text" class="disb_text" id="txtOiNo" name="txtOiNo" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                         -&nbsp;
                        <input type="text" class="disb_text" id="txtEdaNo" name="txtEdaNo" datafld="" readonly maxlength="" value="" style="width:50px;" tabindex="-1">
                    </td>
                    <td rowspan="3" valign="top">
                       採用サンプルNo<br/>
                       <input type="text" class="disb_text" id="txtSaiyou" name="txtSaiyou" datafld="" readonly maxlength="" value="" style="width:150px;" tabindex="-1">
                       <!-- 【シサクイックH24年度対応】修正５ 2012/04/16 ADD Start -->
                       <input type="hidden" class="disb_text" id="hdnSaiyou_column" name="hdnSaiyou_column" >
                       <!-- 【シサクイックH24年度対応】修正５ 2012/04/16 ADD End   -->
                       <br/>試算期日<br/>
                       <!-- 【シサクイックH24対応】No41 Start -->
                       <!-- <input type="text" id="txtKizitu" name="txtKizitu"  maxlength="10" style="border-color:#999;border-style:solid;border-width:1px;ime-mode:disabled;background-color:#ffff88;width:150px"  onchange="setFg_Henkou();hidukeSetting(this);" onblur="chkKizitu(this);" value="" tabindex="6" /> -->
                       <input type="text" id="txtKizitu" name="txtKizitu"  maxlength="10" style="border-color:#999;border-style:solid;border-width:1px;ime-mode:disabled;background-color:#ffff88;width:150px"  onchange="setFg_Henkou();hidukeSetting(this);" value="" tabindex="6" />
                       <!-- 【シサクイックH24対応】No41 End   -->
                    </td>
                    <td rowspan="3" width="150" valign="top">
                       依頼番号
                       <br><div id="divIraiNo"></div>
                    </td>

                    <td rowspan="3" align="right">
                    	<input type="button" class="normalbutton" id="btnStatusRireki" name="btnStatusRireki" value="ｽﾃｰﾀｽ履歴" onClick="funStatusRireki_btn();" tabindex="4"><br/>
                    	<input type="button" class="normalbutton" id="btnHaita" name="btnHaita" value="使用中表示" onClick="funHaitaDisp_btn();" tabindex="5"><br/>
                        <!-- DEL 2015/05/15 TT.Kitazawa【QP@40812】No.14 start -->
                    	<!-- <input type="button" class="normalbutton" id="btnHelp" name="btnHelp" value="ヘルプ表示" onClick="funHelpDisp();" tabindex="6"> -->
                        <!-- DEL 2015/05/15 TT.Kitazawa【QP@40812】No.14 end -->
                    </td>
                </tr>

                <!-- 2行目 -->
                <tr>
                    <td>品名</td>
                    <td>
                        <input type="text" class="act_text" id="txtHinNm" name="txtHinNm" readonly  datafld="" maxlength="" value="" style="width:350px;" tabindex="-1">
                    </td>
                    <td>&nbsp;</td>
                </tr>

                <!-- 3行目 -->
                <tr>
                    <td>枝番種類</td>
                    <td>
                        <input type="text" class="act_text" id="txtShuruiEda" name="txtShuruiEda" readonly  datafld="" maxlength="" value="" style="width:150px;" tabindex="-1">
                    </td>
                    <td>&nbsp;</td>
                </tr>

                <!-- 4行目 -->
                <tr>
                    <td>&nbsp;</td>
                    <td colspan="2">
                        <input type="text" class="disb_text" bgcolor="yellow" style="width:16px;height:16px;background-color:#ffff88;" readonly  tabindex="-1" />
                        の項目は入力可能です
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        ※の項目は計算項目です
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

            </table>

            <!-- 変更フラグ -->
            <input type="hidden" value="false" name="FgHenkou" id="FgHenkou">
            <!-- 権限設定 -->
            <input type="hidden" value="" name="hdnKengen" id="hdnKengen">
            <!-- ダウンロードファイル -->
            <input type="hidden" value="" name="strFilePath" id="strFilePath">


            <!-- 【QP@00342】 -->
            <!-- 排他試作CD -->
            <input type="hidden" value="" name="strHaitaCdShisaku" id="strHaitaCdShisaku">
            <!-- 排他試作名 -->
            <input type="hidden" value="" name="strHaitaNmShisaku" id="strHaitaNmShisaku">
            <!-- 現在ｽﾃｰﾀｽ -->
            <input type="hidden" value="" name="hdnStatus_kenkyu" id="hdnStatus_kenkyu">
            <input type="hidden" value="" name="hdnStatus_seikan" id="hdnStatus_seikan">
            <input type="hidden" value="" name="hdnStatus_gentyo" id="hdnStatus_gentyo">
            <input type="hidden" value="" name="hdnStatus_kojo" id="hdnStatus_kojo">
            <input type="hidden" value="" name="hdnStatus_eigyo" id="hdnStatus_eigyo">
            <!-- ADD 2013/10/22 QP@30154 okano start -->
            <!-- 部署情報 -->
            <input type="hidden" value="" name="hdnBusho_kenkyu" id="hdnBusho_kenkyu">
            <input type="hidden" value="" name="hdnBusho_seikan" id="hdnBusho_seikan">
            <input type="hidden" value="" name="hdnBusho_gentyo" id="hdnBusho_gentyo">
            <input type="hidden" value="" name="hdnBusho_kojo" id="hdnBusho_kojo">
            <input type="hidden" value="" name="hdnBusho_eigyo" id="hdnBusho_eigyo">
            <!-- ADD 2013/10/22 QP@30154 okano end -->


            <!-- 2010/05/12 シサクイック（原価）要望 【案件No9】排他情報の表示　TT西川 START -->
            <!-- 排他会社 -->
            <input type="hidden" value="" name="strHaitaKaisha" id="strHaitaKaisha">
            <!-- 排他部署 -->
            <input type="hidden" value="" name="strHaitaBusho" id="strHaitaBusho">
            <!-- 排他ユーザ -->
            <input type="hidden" value="" name="strHaitaUser" id="strHaitaUser">
            <!-- 本来の権限 -->
            <input type="hidden" value="" name="strKengenMoto" id="strKengenMoto">
            <!-- 2010/05/12 シサクイック（原価）要望 【案件No9】排他情報の表示　TT西川 START -->


            <!-- 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 START -->
            <!-- ヘルプファイル -->
            <input type="hidden" value="" name="strHelpPath" id="strHelpPath">
            <!-- 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 START -->

            <!-- 2015/08/26 TT.Kitazawa【QP@40812】No.6 start -->
            <!-- メール渡し：試作依頼サンプルNo. -->
            <input type="hidden" value="" name="hdnNo_iraisampleForMail" id="hdnNo_iraisampleForMail">
            <!-- 2015/08/26 TT.Kitazawa【QP@40812】No.6 end -->

        </form>
    </body>
</html>
