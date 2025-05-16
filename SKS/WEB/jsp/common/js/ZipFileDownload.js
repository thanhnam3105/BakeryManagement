//========================================================================================
// 通信処理 [ConnectionControl.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：各通信処理の制御を行う。
//========================================================================================

//========================================================================================
// XML通信処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlSend     ：送信XML名
// 戻り値：正常終了:true／異常終了:false
// 概要  ：サーバに対してXML通信を行う。
//========================================================================================
//function funXmlConnect(xmlSend) {
//
//    var xmlHttp;
//    var xmlBuff;
//    var resText;
//
//    try {
//        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
//    } catch (e) {
//        try {
//            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
//        } catch (e2) {
//            funErrorMsgBox(E000001);
//            return false;
//        }
//    }
//    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");
//
//    //ｻｰﾊﾞｰにxmlﾌｧｲﾙを送信し、AjaxClassを起動します。
//    xmlHttp.Open(ConConectPost, "/" + ConUrlPath + "/AjaxServlet", false);
//    xmlHttp.SetRequestHeader("Content-type", "text/xml; charset=UTF-8");
//    xmlHttp.Send(xmlSend);
//
//    //返信ﾃﾞｰﾀが0件の時はｴﾗｰ
//    xmlBuff.load(xmlHttp.responseXML);
//    if (xmlBuff.xml == "") {
//        funErrorMsgBox(E000001);
//        return false;
//    }
//
//    xmlSend.load(xmlBuff);
//
//    return true;
//
//}

//========================================================================================
// URL通信処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①NextUrl       ：移動先URL
//       ：②ConnectPattern：通信パターン(POST or GET)
//       ：③FormObject    ：フォームの参照
// 戻り値：なし
// 概要  ：POST、又はGET通信で指定URLに遷移する。
//========================================================================================
//function funUrlConnect(NextUrl, ConnectPattern, FormObject) {
//
//    //通信ﾊﾟﾀｰﾝの判定
//    if (ConnectPattern == ConConectGet) {
//        //GET通信
//        location.href = NextUrl;
//
//    } else {
//        //POST通信
//        FormObject.encoding = "application/x-www-form-urlencoded";
//        FormObject.action = NextUrl;
//        FormObject.target = "_self";
//        FormObject.method = ConConectPost;
//        FormObject.submit();
//    }
//
//    return true;
//
//}

//========================================================================================
// URL通信処理(ファイルダウンロード専用)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①ConnectPattern：通信パターン(POST or GET)
//       ：②FormObject    ：フォームの参照
// 戻り値：なし
// 概要  ：ファイルダウンロード画面に対してのURL通信を行う。
//========================================================================================
function funFileDownloadUrlConnect2(ConnectPattern, FormObject) {

    var pos_top;
    var pos_left;
    var height;
    var param;
    var wUrl = "/" + ConUrlPath + "/ZipFileDownLoadServlet";

    //中央になるよう画面左上座標の初期化
//    pos_top  = screen.height / 2 - 384;
//    pos_left = screen.width  / 2 - 512;
      pos_top  = (screen.height / 2) - 500;
      pos_left = (screen.width  / 2) + 300;

    //ﾊﾟﾗﾒｰﾀを指定
//    height = funAdjustScreenHeight(768);
    height = funAdjustScreenHeight(568);

//    param = "width=1024,height=" + height +
      param = "width=600,height=" + height +
            ",top=" + pos_top + ",left=" + pos_left +
            ",status=yes,toolbar=no,menubar=no,scrollbars=yes";



/*    //通信ﾊﾟﾀｰﾝの判定
    if (ConnectPattern == ConConectGet) {*/
        wUrl += "?FName=" + FormObject.strFilePath.value;


        // ダウンロードパスの指定がない場合はDefault
        // Default 時は Objectが無い
        if(FormObject.strServerConst) {
        	// ダウンロードフォルダーの指定（DOWNLOAD_FOLDER 以外）
        	if (FormObject.strServerConst.value != "") {
        		wUrl += "&DLPath=" + FormObject.strServerConst.value;
        	}
        	if (FormObject.strSubFolder) {
        		// サブフォルダー名を追加（ファイル名と同じ数のサブフォルダー）
        		if (FormObject.strSubFolder.value != "") {
        			wUrl += ":::" + FormObject.strSubFolder.value;
        		}
        	}
        }
        // Excelファイルパスがあれば
        if (FormObject.strExcelFilePath) {
        	wUrl += "&ExcelFilePath=" + FormObject.strExcelFilePath.value;
        }
        // PDFファイルパスがあれば
        if (FormObject.strPdfFilePath) {
        	wUrl += "&PdfFilePath=" + FormObject.strPdfFilePath.value;
        }


        //GET通信
        window.open(wUrl, "file_download", param);

/*    } else {
        //POST通信
        window.open("about:blank", "file_download", param);

        FormObject.encoding = "application/x-www-form-urlencoded";
        FormObject.action = wUrl;
        FormObject.target = "file_download";
        FormObject.method = ConConectPost;
        FormObject.submit();
    }*/

    return true;

}


//========================================================================================
// URL通信処理(ファイルDELETE専用)
// 作成者：E.Kitazawa
// 作成日：2014/09/19
// 引数  ：①ConnectPattern：通信パターン(POST or GET)
//       ：②FormObject    ：フォームの参照
// 戻り値：なし
// 概要  ：ファイルDELETE画面に対してのURL通信を行う。
//========================================================================================
//function funFileDeleteUrlConnect(ConnectPattern, FormObject) {
//
//	var pos_top;
//	var pos_left;
//	var param;
//
//	var nwin;
//
//	//画面左上座標の初期化
//    pos_top  = screen.height / 2 - 10;
//    pos_left = screen.width  / 2 - 10;
//
//    //ﾊﾟﾗﾒｰﾀを指定
//	param = "width=10,height=10,top=" + pos_top + ",left=" + pos_left +
//	",status=no,toolbar=no,menubar=no";
//
//	//通信ﾊﾟﾀｰﾝの判定
//	if (ConnectPattern == ConConectGet) {
//
//		//GET通信
//		// ダミーウィンドウを作成し「FileDeleteServlet」を起動。削除処理終了後、画面を閉じる
//		nwin = window.open("", "filedelete", param);
//		nwin.document.open();
//		nwin.document.write("<%@ page language=\"java\" contentType=\"text/html;charset=Windows-31J\" %>\n");
//		nwin.document.write("<html><head>\n");
//		nwin.document.write("<title></title>\n");
//		nwin.document.write("        <script>\n");
//		nwin.document.write("        <!--\n");
//		nwin.document.write("            function funFileDelete() {\n");
//		nwin.document.write("                document.frm00.action='/" + ConUrlPath + "/FileDeleteServlet';\n");
//		nwin.document.write("                document.frm00.submit();\n");
//									// 画面を閉じる
//		nwin.document.write("                this.window.close();\n");
//		nwin.document.write("            }\n");
//		nwin.document.write("        //-->\n");
//		nwin.document.write("        </script>\n");
//		nwin.document.write("    </head>\n");
//		nwin.document.write("    <body onload=\"funFileDelete()\">\n");
//		nwin.document.write("    <form name=\"frm00\" method=\"POST\" >\n");
//		nwin.document.write("        <input type=\"hidden\" name=\"FName\" id=\"FName\" value=\"" + FormObject.strFilePath.value + "\">\n");
//		nwin.document.write("        <input type=\"hidden\" name=\"DLPath\" id=\"DLPath\" value=\"" + FormObject.strServerConst.value + "\">\n");
//		nwin.document.write("    </form>\n");
//		nwin.document.write("    </body>\n");
//		nwin.document.write("</html>\n");
//		nwin.document.close();
//
//	}
//
//	return true;
//
//}
