<%@ Language=VBScript %>
<% '----------------------------------------------------------------------------------- %>
<% ' デリア物流費DB　CSVファイルダウンロード処理                                        %>
<% ' 作成者：TT.Jinbo                                                                   %>
<% ' 作成日：2008/09/10                                                                 %>
<% ' 概要  ：                                                                           %>
<% '----------------------------------------------------------------------------------- %>
<% ' 変更者：                                                                           %>
<% ' 変更日：                                                                           %>
<% ' 概要  ：                                                                           %>
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
                GET送信されました。<br>
                結果 == <%= val %>
            </center>
        </form>
    </body>
</html>
