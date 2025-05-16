//========================================================================================
// �ʐM���� [ConnectionControl.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �F�e�ʐM�����̐�����s���B
//========================================================================================

//========================================================================================
// XML�ʐM����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlSend     �F���MXML��
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�T�[�o�ɑ΂���XML�ʐM���s���B
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
//    //���ް��xmļ�ق𑗐M���AAjaxClass���N�����܂��B
//    xmlHttp.Open(ConConectPost, "/" + ConUrlPath + "/AjaxServlet", false);
//    xmlHttp.SetRequestHeader("Content-type", "text/xml; charset=UTF-8");
//    xmlHttp.Send(xmlSend);
//
//    //�ԐM�ް���0���̎��ʹװ
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
// URL�ʐM����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@NextUrl       �F�ړ���URL
//       �F�AConnectPattern�F�ʐM�p�^�[��(POST or GET)
//       �F�BFormObject    �F�t�H�[���̎Q��
// �߂�l�F�Ȃ�
// �T�v  �FPOST�A����GET�ʐM�Ŏw��URL�ɑJ�ڂ���B
//========================================================================================
//function funUrlConnect(NextUrl, ConnectPattern, FormObject) {
//
//    //�ʐM����݂̔���
//    if (ConnectPattern == ConConectGet) {
//        //GET�ʐM
//        location.href = NextUrl;
//
//    } else {
//        //POST�ʐM
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
// URL�ʐM����(�t�@�C���_�E�����[�h��p)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@ConnectPattern�F�ʐM�p�^�[��(POST or GET)
//       �F�AFormObject    �F�t�H�[���̎Q��
// �߂�l�F�Ȃ�
// �T�v  �F�t�@�C���_�E�����[�h��ʂɑ΂��Ă�URL�ʐM���s���B
//========================================================================================
function funFileDownloadUrlConnect2(ConnectPattern, FormObject) {

    var pos_top;
    var pos_left;
    var height;
    var param;
    var wUrl = "/" + ConUrlPath + "/ZipFileDownLoadServlet";

    //�����ɂȂ�悤��ʍ�����W�̏�����
//    pos_top  = screen.height / 2 - 384;
//    pos_left = screen.width  / 2 - 512;
      pos_top  = (screen.height / 2) - 500;
      pos_left = (screen.width  / 2) + 300;

    //���Ұ����w��
//    height = funAdjustScreenHeight(768);
    height = funAdjustScreenHeight(568);

//    param = "width=1024,height=" + height +
      param = "width=600,height=" + height +
            ",top=" + pos_top + ",left=" + pos_left +
            ",status=yes,toolbar=no,menubar=no,scrollbars=yes";



/*    //�ʐM����݂̔���
    if (ConnectPattern == ConConectGet) {*/
        wUrl += "?FName=" + FormObject.strFilePath.value;


        // �_�E�����[�h�p�X�̎w�肪�Ȃ��ꍇ��Default
        // Default ���� Object������
        if(FormObject.strServerConst) {
        	// �_�E�����[�h�t�H���_�[�̎w��iDOWNLOAD_FOLDER �ȊO�j
        	if (FormObject.strServerConst.value != "") {
        		wUrl += "&DLPath=" + FormObject.strServerConst.value;
        	}
        	if (FormObject.strSubFolder) {
        		// �T�u�t�H���_�[����ǉ��i�t�@�C�����Ɠ������̃T�u�t�H���_�[�j
        		if (FormObject.strSubFolder.value != "") {
        			wUrl += ":::" + FormObject.strSubFolder.value;
        		}
        	}
        }
        // Excel�t�@�C���p�X�������
        if (FormObject.strExcelFilePath) {
        	wUrl += "&ExcelFilePath=" + FormObject.strExcelFilePath.value;
        }
        // PDF�t�@�C���p�X�������
        if (FormObject.strPdfFilePath) {
        	wUrl += "&PdfFilePath=" + FormObject.strPdfFilePath.value;
        }


        //GET�ʐM
        window.open(wUrl, "file_download", param);

/*    } else {
        //POST�ʐM
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
// URL�ʐM����(�t�@�C��DELETE��p)
// �쐬�ҁFE.Kitazawa
// �쐬���F2014/09/19
// ����  �F�@ConnectPattern�F�ʐM�p�^�[��(POST or GET)
//       �F�AFormObject    �F�t�H�[���̎Q��
// �߂�l�F�Ȃ�
// �T�v  �F�t�@�C��DELETE��ʂɑ΂��Ă�URL�ʐM���s���B
//========================================================================================
//function funFileDeleteUrlConnect(ConnectPattern, FormObject) {
//
//	var pos_top;
//	var pos_left;
//	var param;
//
//	var nwin;
//
//	//��ʍ�����W�̏�����
//    pos_top  = screen.height / 2 - 10;
//    pos_left = screen.width  / 2 - 10;
//
//    //���Ұ����w��
//	param = "width=10,height=10,top=" + pos_top + ",left=" + pos_left +
//	",status=no,toolbar=no,menubar=no";
//
//	//�ʐM����݂̔���
//	if (ConnectPattern == ConConectGet) {
//
//		//GET�ʐM
//		// �_�~�[�E�B���h�E���쐬���uFileDeleteServlet�v���N���B�폜�����I����A��ʂ����
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
//									// ��ʂ����
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
