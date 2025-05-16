/* 
 * �_�E�����[�h�t�@�C���폜�p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class FileDownLoadServlet extends HttpServletBase {
	
	/**
	 * HttpGet����M
	 *  : GET�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	public void doGet(HttpServletRequest HttpReq, HttpServletResponse HttpRes) {

		//http://localhost:8080/Shisaquick_SRV/FileDownLoadServlet?FName=a:::b
		//a:::b�@=�@�_�E�����[�h�ΏہA�t�@�C��a�@�t�@�C��b
		BufferedReader br;
		
		//���N���X�iHttpServletBase�j�̏����������s��
		super.initServlet(HttpReq, HttpRes);
		
		String strFileNames = "";
		
		//���N�G�X�g�����Ώۃt�@�C���p�X���擾
		if (req.getParameter("FName")!= null){
			strFileNames = req.getParameter("FName").toString();
			
			String url;
			try {
				url = URLEncoder.encode(req.getParameter("FName").toString() , "ASCII");
				
				System.out.println(url);
			} catch (UnsupportedEncodingException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			
		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		    	val = new String(byteData, "Shift_JIS");
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }
			
		}
		
		//�_�E�����[�h�y�[�W�̏o��
		CreatePage(strFileNames);
	}

	private void CreatePage(String FNames){
		
		ArrayList<String> listFUrl = null;
		
		//�_�E�����[�hURL�ҏW
		listFUrl = MakeFileURL(FNames);
		
		//�_�E�����[�h�y�[�W�o��
		printPage(listFUrl);
		
		listFUrl = null;
		
	}
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
			res.setContentType("text/html; charset=Shift_JIS");

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
			out.println("    <body onUnload=\"funFileDelete()\">");   
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
				out.println("<input type=\"button\" value=\"�_�E�����[�h\" onclick=\"javascript:location.href='/" + strRootPath[strRootPath.length-1] + "/FileDownloadExec?FName=" + listFUrl.get(i) + "';\">");
				out.println("</td></tr>");

				if (i != 0) {
					//��؂蕶����t������
					strFName.append(":::");
				}
//				strFName.append(listFUrl.get(i).split("/")[listFUrl.get(i).split("/").length-1]);
				strFName.append(listFUrl.get(i));
			}

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
	
}