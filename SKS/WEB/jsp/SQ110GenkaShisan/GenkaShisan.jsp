<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- シサクイック　原価試算画面（フレーム分割）                                                -->
<!-- 作成者：TT.Nishigawa                                                                -->
<!-- 作成日：2009/10/20                                                              -->
<!-- 概要  ：原価試算画面のフレーム分割ページ                          -->
<!------------------------------------------------------------------------------------->

<HTML>
	<script type="text/javascript">
    <!--//
    //===================================================================================
    // フォームロード処理
    // 作成者：K.Katayama
    // 作成日：2009/02/14
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
            
        }
        
        window.onunload = function() {
			parent.detail.location.href="about:blank";
			parent.header.location.href="about:blank";
		}
    -->
    </script>
	<HEAD>
		<title>原価試算システム 原価試算画面</title>
		<!-- 共通 -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=shift_jis">
		<META HTTP-EQUIV="Content-Language" content="ja">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	</HEAD>
	
    <form name="frm00" id="frm00" method="post">
        <input type="hidden" value="test" id="test" name="test">
    </form>
    
	<FRAMESET rows="22%,78%" frameborder="0" border="0" framespacing="0" onLoad="funLoad();">
		<FRAME SRC="./GenkaShisan_head.jsp" NORESIZE NAME="header"  scrolling="no" tabindex="1">
		<FRAME SRC="" NORESIZE NAME="detail"  scrolling="auto" tabindex="2">
	</FRAMESET>
	
	
</HTML>