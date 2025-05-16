/*
 * �_�E�����[�h�t�@�C���폜�p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;

/**
 *
 * Class���@: �t�@�C���_�E�����[�h�ʐM
 * �����T�v : �t�@�C���_�E�����[�h�p��URL�ʐM���s���A�Ώۉ�ʂ��J���@
 *           �i���펞�F�_�E�����[�h��ʁ@�E�@�ُ펞�F�G���[��ʁj
 *
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class ZipFileDownLoadServlet extends HttpServletBase {

	/**
	 * HttpGet����M
	 *  : GET�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	public void doGet(HttpServletRequest HttpReq, HttpServletResponse HttpRes) {


		//���N���X�iHttpServletBase�j�̏����������s��
		super.initServlet(HttpReq, HttpRes);

		String strFileNames = "";

		//���N�G�X�g�����Ώۃt�@�C���p�X���擾
		if (req.getParameter("FName")!= null){

		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);

		      	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	e.printStackTrace();
		    }

		}

		//���N�G�X�g�����_�E�����[�h�t�H���_�[���擾
		String strDLPath = "";

		if (req.getParameter("DLPath")!= null){

		    String val = req.getParameter("DLPath");
		    strDLPath = val;
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");

		      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
		      	strDLPath = val;
		    }catch(UnsupportedEncodingException e){
		    	e.printStackTrace();
		    }
		}

		// Excel�t�@�C�������擾

		String strExcelFileName = "";

		if (req.getParameter("ExcelFilePath") != null) {

			String val = req.getParameter("ExcelFilePath");
			strExcelFileName = val;

			try {
				byte[] byteData = val.getBytes("ISO_8859_1");

				val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
				strExcelFileName = val;

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// PDF�t�@�C�������擾
		String strPdfFileName = "";

		if (req.getParameter("PdfFilePath") != null) {
			String val = req.getParameter("PdfFilePath");
			strPdfFileName = val;
			try {
				byte[] byteData =  val.getBytes("ISO_8859_1");

				val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
				strPdfFileName = val;

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}


		//�_�E�����[�h�y�[�W�̏o��
		CreatePage(strFileNames, strDLPath, strExcelFileName, strPdfFileName);
	}

	/**
	 * �_�E�����[�h�y�[�W�̍쐬
	 * @param FNames : �t�@�C����
	 *        DLPath �F�_�E�����[�h�p�X�A�T�u�t�H���_�[��
	 */
//	private void CreatePage(String FNames){
	private void CreatePage(String FNames, String DLPath, String strExcelFielName, String strPdfFielName){

		ArrayList<String> listFUrl = null;
		ArrayList<String> listDLpath = null;

		//�_�E�����[�hURL�ҏW
		listFUrl = MakeFileURL(FNames);
		//�_�E�����[�h�p�X�A�T�u�t�H���_�[���𕪉�
		listDLpath = MakeFileURL(DLPath);

		//�_�E�����[�h�y�[�W�o��
		if (DLPath == "") {
			// ������t�@�C���폜���s
			printPage(listFUrl);
		} else {
			// �_�E�����[�h�p�X���w��
			// ������t�@�C���폜���Ȃ�
			printPage(listFUrl, listDLpath, strExcelFielName, strPdfFielName);
		}

		listFUrl = null;

	}
	//�yQP@40404�zmod end TT E.Kitazawa 2014/09/08


	/**
	 * �t�@�C�������_�E�����[�hURL�ɕϊ�����
	 * @param FNames : �t�@�C����
	 * @return ArrayList<String> : URL�̃��X�g
	 */
	private ArrayList<String> MakeFileURL(String FNames){

		ArrayList<String> ret = null;
		String[] listFName = null;

		try{

			ret = new ArrayList<String>();

			//�t�@�C���ʂɕ���
			listFName = FNames.split(":::");
			//�t�@�C������URL�𐶐�
			for(int i = 0; i < listFName.length; i++){
				//�_�E�����[�hURL���i�[
				ret.add(listFName[i]);
			}

		}catch(Exception e){

		}finally{
			listFName = null;

		}
		return ret;

	}

	/**
	 * �_�E�����[�h�y�[�W���o�͂���
	 * @param listFUrl �_�E�����[�h�p��URL���X�g
	 */
	private void printPage(ArrayList<String> listFUrl){

		StringBuffer strFName = new StringBuffer();

		try{
			//ContentType ��ݒ�
//2010/01/13�@UPDATE�@Start�@isono�@Miclosoft��S-JIS�g�������Ή�
//			res.setContentType("text/html; charset=Shift_JIS");
			res.setContentType(ConstManager.SRV_HTML_CHARSET);
//2010/01/13�@UPDATE�@END�@�@isono�@Miclosoft��S-JIS�g�������Ή�

			//�o�͗p PrintWriter ���擾
			PrintWriter out = res.getWriter();

			String[] strRootPath;
			strRootPath = ConstManager.getRootURL().split("/");

			//HTML �o��
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>�_�E�����[�h���</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function funFileDelete() {");
			out.println("                document.frm00.action='/" + strRootPath[strRootPath.length-1] + "/FileDeleteServlet';");
			out.println("                document.frm00.submit();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("<style type=\"text/css\">");
			out.println(".title {");
			out.println("    height:22px;");
			out.println("    font-size:16px;");
			out.println("    font-weight:bold;");
			out.println("    background-color=#8380F5;");
			out.println("    color=#FFFFFF;");
			out.println("    text-align=center;");
			out.println("}");
			out.println("</style>");
			out.println("    </head>");
			out.println("    <body onUnload=\"funFileDelete()\" >");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println("        <center>");
			out.println("            <br><br>");
			out.println("                <table>");
			out.println("                    <tr>");
			out.println("                        <td align=\"center\" class=\"title\" width =\"220\">�t�@�C���_�E�����[�h</td>");
			out.println("                    </tr>");
			out.println("                </table>");
			out.println("            <br><br><br>");
			out.println("�_�E�����[�h�{�^�����N���b�N���ăt�@�C����ۑ����ĉ������B");
			out.println("            <br><br><br><br>");
			out.println("            <table>");

			//�t�@�C���������N�����
			for (int i = 0; i < listFUrl.size(); i++){
				out.println("<tr><td>");
				out.println(listFUrl.get(i));
				out.println("</td><td>");
//				out.println("<input type=\"button\" value=\"�_�E�����[�h\" onclick=\"javascript:location.href='/Shisaquick_SRV/FileDownloadExec?FName=" + listFUrl.get(i).split("/")[listFUrl.get(i).split("/").length-1] + "';\">");
				out.println("<input type=\"button\" name=\"daowloadshori\"  value=\"�_�E�����[�h\" onclick=\"javascript:location.href='/" + strRootPath[strRootPath.length-1] + "/FileDownloadExec?FName=" + listFUrl.get(i) + "';\">");
				out.println("</td></tr>");

				if (i != 0) {
					//��؂蕶����t������
					strFName.append(":::");
				}
//				strFName.append(listFUrl.get(i).split("/")[listFUrl.get(i).split("/").length-1]);
				strFName.append(listFUrl.get(i));
			}
			out.println("<tr><td colspan=\"2\" >");
			out.println("<input type=\"button\" value=\"����\" onClick=\"window.close()\">");
			out.println("</td><td>");
			out.println("</td>");
			out.println("</tr>");


			out.println("            </table>");
			out.println("        </center>");
			out.println("        <input type=\"hidden\" name=\"FName\" id=\"FName\" value=\"" + strFName + "\">");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
			//�ϐ��̍폜
			strFName = null;
		}

	}


	//�yQP@40404�zadd start TT E.Kitazawa 2014/09/08
	/**
	 * �_�E�����[�h�y�[�W���o�͂���
	 * @param listFUrl   �_�E�����[�h�p��URL���X�g
	 *        listDLPath �_�E�����[�h�t�@�C���̃t�H���_�[��
	 */
	private void printPage(ArrayList<String> listFUrl, ArrayList<String> listDLPath, String strExcelFielName, String strPdfFielName){

		StringBuffer strFName = new StringBuffer();
		// �_�E�����[�h�p�X�ɑΉ�����T�u�t�H���_�[���q����
		String  strDLPath = "";
		if (listDLPath == null) {
			return;
		}

		try{
			//ContentType ��ݒ�
			res.setContentType(ConstManager.SRV_HTML_CHARSET);

			//�o�͗p PrintWriter ���擾
			PrintWriter out = res.getWriter();

			String[] strRootPath;
			strRootPath = ConstManager.getRootURL().split("/");

			//HTML �o��
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>�_�E�����[�h���</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function funOnload() {");
			out.println("                document.getElementById(\"daowloadshori\").click()");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("<style type=\"text/css\">");
			out.println(".title {");
			out.println("    height:22px;");
			out.println("    font-size:16px;");
			out.println("    font-weight:bold;");
			out.println("    background-color=#8380F5;");
			out.println("    color=#FFFFFF;");
			out.println("    text-align=center;");
			out.println("}");
			out.println("</style>");
			out.println("    </head>");
			out.println("    <body  onload=\"funOnload()\" >");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println("        <center>");
			out.println("            <br><br>");
			out.println("                <table>");
			out.println("                    <tr>");
			out.println("                        <td align=\"center\" class=\"title\" width =\"220\">�t�@�C���_�E�����[�h</td>");
			out.println("                    </tr>");
			out.println("                </table>");
			out.println("            <br><br><br>");
			out.println("�_�E�����[�h�{�^�����N���b�N���ăt�@�C����ۑ����ĉ������B");
			out.println("            <br><br><br><br>");
			out.println("            <table>");

			//�t�@�C���������N�����
			for (int i = 0; i < listFUrl.size(); i++){
				// �T�[�o�[�p�X�iconst�l�j
				strDLPath = listDLPath.get(0);
				// �t�@�C�����ɑΉ�����T�u�t�H���_�[��ǉ�
				if ((i+1) < listDLPath.size()) {
					strDLPath += ":::" + listDLPath.get(i+1);
				}
				out.println("<tr><td>");
				// �t�@�C�����\��
				out.println(listFUrl.get(i));
				out.println("</td><td>");
				out.println("<input type=\"button\" name=\"daowloadshori\" value=\"�_�E�����[�h\" onclick=\"javascript:location.href='/" + strRootPath[strRootPath.length-1] + "/FileDownloadExec?FName=" + listFUrl.get(i) + "&DLPath=" + strDLPath +"';\">");
				out.println("</td></tr>");

				if (i != 0) {
					//��؂蕶����t������
					strFName.append(":::");
				}
				strFName.append(listFUrl.get(i));
			}
			out.println("            </table>");

			// zip�t�@�C���̒��g�AExcel,PDF�t�@�C������\������B
			out.println("             <br><br><br>");
			out.println("             <table>");
			out.println("                 <tr>");
			out.println("                    <td> "+ strExcelFielName + "</td>");
			out.println("                 </tr>");
			String[] pdfFils = strPdfFielName.split(":::");
			for (int i = 0; i < pdfFils.length; i++) {
				out.println("                 <tr>");
				out.println("                    <td>" + pdfFils[i] + "</td>");
				out.println("                 </tr>");
			}
			out.println("<tr><td colspan=\"2\">");
			out.println("<input type=\"button\"  value=\"����\" onClick=\"window.close()\">");
			out.println("</td><td>");
			out.println("</td>");
			out.println("</tr>");
			out.println("             </table>");
			out.println("        </center>");
			out.println("        <input type=\"hidden\" name=\"FName\" id=\"FName\" value=\"" + strFName + "\">");
//			out.println("        <input type=\"hidden\" name=\"DLPath\" id=\"DLPath\" value=\"" + DLPath + "\">");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
			//�ϐ��̍폜
			strFName = null;
		}

	}
	//�yQP@40404�zadd end TT E.Kitazawa 2014/09/08


}