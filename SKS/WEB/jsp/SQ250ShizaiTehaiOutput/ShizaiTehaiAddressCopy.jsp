<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@���ގ�z�˗����o�͉��                                            -->
<!-- �쐬�ҁFTT.Shima                                                                -->
<!-- �쐬���F2014/09/05                                                              -->
<!-- �T�v  �F���ގ�z�˗���������͂���                                            -->
<!------------------------------------------------------------------------------------->
<html>
    <head>
        <title></title>
        <!-- ���� -->
        <script type="text/javascript" src="../common/js/Const.js"></script>
        <script type="text/javascript" src="../common/js/MessageControl.js"></script>
        <script type="text/javascript" src="../common/js/ConnectionControl.js"></script>
        <script type="text/javascript" src="../common/js/XmlControl.js"></script>
        <script type="text/javascript" src="../common/js/AjaxControl.js"></script>
        <script type="text/javascript" src="../common/js/CheckControl.js"></script>
        <script type="text/javascript" src="../common/js/DisplayControl.js"></script>
        <script type="text/javascript" src="../common/js/InputControl.js"></script>
        <!-- �� -->
        <script type="text/javascript" src="include/SQ250ShizaiTehaiOutput.js"></script>
        <!-- �X�^�C���V�[�g -->
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
        Excel�p�X<br>
        <input type="text" id="url" size="30" maxlength="20" value="https://www.google.co.jp/">
        <input type="button" value="�R�s�[����" onclick="clipboadCopy()">
        <input type="button" value="����" onclick="window.close()">
	</body>
</html>