<%@ Language=VBScript %>
<% '----------------------------------------------------------------------------------- %>
<% ' �f���A������DB�@CSV�t�@�C���_�E�����[�h����                                        %>
<% ' �쐬�ҁFTT.Jinbo                                                                   %>
<% ' �쐬���F2008/09/10                                                                 %>
<% ' �T�v  �F                                                                           %>
<% '----------------------------------------------------------------------------------- %>
<% ' �ύX�ҁF                                                                           %>
<% ' �ύX���F                                                                           %>
<% ' �T�v  �F                                                                           %>
<% '----------------------------------------------------------------------------------- %>
<% Option Explicit %>

<%

    Dim val

On Error Resume Next

    val = request.querystring("Val")

%>
<html>
    <head>
        <title></title>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=Shift_JIS">
        <META HTTP-EQUIV="Content-Script-Type" CONTENT="text/javascript">
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    </head>

    <body>
        <form name="frm00">
            <center>
                GET���M����܂����B<br>
                ���� == <%= val %>
            </center>
        </form>
    </body>
</html>
