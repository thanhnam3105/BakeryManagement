<%@ page language="java" contentType="text/html;charset=Windows-31J" %>
<!------------------------------------------------------------------------------------->
<!-- �V�T�N�C�b�N�@�������Z��ʁi�t���[�������j                                                -->
<!-- �쐬�ҁFTT.Nishigawa                                                                -->
<!-- �쐬���F2009/10/20                                                              -->
<!-- �T�v  �F�������Z��ʂ̃t���[�������y�[�W                          -->
<!------------------------------------------------------------------------------------->

<HTML>
	<script type="text/javascript">
    <!--//
    //===================================================================================
    // �t�H�[�����[�h����
    // �쐬�ҁFK.Katayama
    // �쐬���F2009/02/14
    // ����  �F�Ȃ�
    // �߂�l�F�Ȃ�
    // �T�v  �F�t�H�[�����[�h���̏���������
    //===================================================================================
        function funLoad() {
            
            var width, height;
            
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            
            //��ʃT�C�Y�E�ʒu�̐ݒ�
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
		<title>�������Z�V�X�e�� �������Z���</title>
		<!-- ���� -->
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