<%@ page language="java" contentType="text/html;charset=Windows-31J" %>

<!------------------------------------------------------------------------------------->
<!-- シサクイック　資材手配依頼書出力画面                                            -->
<!-- 作成者：TT.Shima                                                                -->
<!-- 作成日：2014/09/05                                                              -->
<!-- 概要  ：資材手配依頼書情報を入力する                                            -->
<!------------------------------------------------------------------------------------->
<html>
<head>
        <title>資材手配依頼書出力画面</title>
        <!-- 共通 -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
<!--         <script type="text/javascript" src="../common/js/SQ250ShizaiTehaiOutput.js"></script> -->
        <script type="text/javascript" src="../common/js/ZipFileDownload.js"></script>


        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>


        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">

        <!-- XML Document定義 -->
        <xml id="xmlRGEN3200"></xml>
        <xml id="xmlRGEN3290"></xml>
        <xml id="xmlRGEN3680"></xml>
        <xml id="xmlRGEN3700"></xml>
        <xml id="xmlRGEN3730"></xml>

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
    </head>

    <script type="text/javascript">
    <!--//
    //===================================================================================
    // フォームロード処理
    // 作成者：H.Shima
    // 作成日：2014/09/09
    // 引数  ：なし
    // 戻り値：なし
    // 概要  ：フォームロード時の初期化処理
    //===================================================================================
        function funLoad() {

            var width, height;

            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ

            //画面サイズ・位置の設定
            resizeTo(width,height);
            moveTo(0,0);

            //画面設定
            funInitScreen(ConShizaiTehaiOutputId);

            // 資材手配依頼出力画面にパラメタを設定する
            param = GetQueryString();

            var kbn = parent.detail.document.frm00.flg_hatyuu_status.value;
            var cd_shain = parent.detail.document.frm00.cd_shain.value;
            var nen = parent.detail.document.frm00.nen.value;
            var seq_shizai = parent.detail.document.frm00.seq_shizai.value;
            var no_oi = parent.detail.document.frm00.no_oi.value;
            var no_eda = parent.detail.document.frm00.no_eda.value;
            var cd_shohin =parent.detail.document.frm00.shohinCd.value;

           var data =  kbn + ":::" + cd_shain + ":::" + nen + ":::" + seq_shizai  + ":::" + no_oi + ":::" + no_eda + ":::" + cd_shohin + ":::";

            // 資材手配テーブルのデータを初期表示に設定
            funShizaiTmp(data);

        window.onunload = function() {
            parent.detail.location.href="about:blank";
            parent.header.location.href="about:blank";
        }
        return null;
    }

	// 設定したパラメタを資材手配依頼出力画面にパラメタを設定する 09/28
    function GetQueryString() {
    	var str = location.search;

            if (1 < str.length) {

                // 最初の1文字 (?記号) を除いた文字列を取得する
                var query = str.substring(1);

                // クエリの区切り記号 (&) で文字列を配列に分割する
                var parameters = query.split('&');

                var result = new Object();
                for (var i = 0; i < parameters.length; i++) {
                    // パラメータ名とパラメータ値に分割する
                    var element = parameters[i].split('=');

                    var paramName = decodeURIComponent(element[0]);

                    var paramValue = decodeURIComponent(element[1]);

                	 // 新資材コード
                    if(paramName == 'cd_shizai_new '){

                   		parent.detail.document.frm00.txtNewShizai.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.newShizai.value = paramValue;
                   	}
                  	//旧資材コード
                    if (paramName == 'cd_shizai') {

                   		parent.detail.document.frm00.txtOldShizai.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.olsShizai.value = paramValue;
                   	}
                  	//納入先⇒製造工場
                    if (paramName == 'seizoKojoNm') {
                   		parent.detail.document.frm00.txtDelivery.value = paramValue;
                   		// hidden
                   		parent.detail.document.frm00.delivery.value = paramValue;

                   	}
                  	//発注先コード
                  	if (paramName == 'cd_hattyusaki') {
//	                	parent.detail.document.frm00.cmbOrderCom1.value = ( '000' + paramValue ).slice( -3 );
	                	parent.detail.document.frm00.cmbOrderCom1.value = paramValue;
	                   	parent.detail.document.frm00.cmbOrderCom1.onchange();
                   	}
                    // 対象資材
                  	if(paramName == 'cd_taishoshizai') {
                   		//対処資材
                   	  	parent.detail.document.frm00.cmbTargetSizai.value = paramValue;

                   	}
                 	// 社員コード
                    if (paramName == 'cd_shain') {
                    	parent.detail.document.frm00.cd_shain.value = paramValue;

                   	}
                	 // 年コード過去
                 	if (paramName == 'nen') {
                   		parent.detail.document.frm00.nen.value = paramValue;

                   	}
                 	// seq資材
                	if (paramName == 'seq_shizai') {
                		parent.detail.document.frm00.seq_shizai.value = paramValue;
                	}

                	// 追番
                	if (paramName == 'no_oi') {
                   		parent.detail.document.frm00.no_oi.value = paramValue;
                   	}
                	// 枝番
                	if (paramName == 'no_eda') {
                   		parent.detail.document.frm00.no_eda.value = paramValue;
                   	}
                	// 製品コード
                	if (paramName == 'seihinCd') {
                		parent.detail.document.frm00.txtSyohin.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.shohinCd.value = paramValue;

                	}
                	// 製品名
                	if (paramName == 'seihinNm') {
                		//parent.detail.document.frm00.txtHinmei.value = paramValue;
                		parent.detail.document.frm00.txtHinmei.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.hinmei.value = paramValue;
                	}

                	// 荷姿
                	if (paramName == 'nisugata') {
                		parent.detail.document.frm00.txtNisugata.value = paramValue;
                		// hidden
                		parent.detail.document.frm00.nisugata.value = paramValue;

                	}

                	// フラグ
                	if (paramName == 'flg_hatyuu_status') {
                		parent.detail.document.frm00.flg_hatyuu_status.value = paramValue;

                	}

                }
                    // パラメータ名をキーとして連想配列に追加する
                    result[paramName] = decodeURIComponent(paramValue);
                }

            return result;
     }

    -->
    </script>

    <FRAMESET rows="10%,89%" frameborder="0" border="0" framespacing="0" onLoad="funLoad();">
		<FRAME SRC="./ShizaiTehaiOutput_head.jsp" NORESIZE NAME="header"  scrolling="no" tabindex="1">
		<FRAME SRC="" NORESIZE NAME="detail"  scrolling="auto" tabindex="2">
	</FRAMESET>

</html>
