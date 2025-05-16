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
        <!-- 個別 -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>
        <!-- スタイルシート -->
        <link rel="stylesheet" type="text/css" href="../common/css/shisaquick.css">
		<script type="text/javascript">

	        var clipboadCopy = function(){
	            var urltext = document.getElementById("url");
	            urltext.select();
	            document.execCommand("copy");
	        }
		</script>
    </head>
	<body>
        Excelパス<br>
        <input type="text" id="url" size="30" maxlength="20" value="https://www.google.co.jp/">
        <input type="button" value="コピーする" onclick="clipboadCopy()">
        <input type="button" value="閉じる" onclick="window.close()">
	</body>
</html>