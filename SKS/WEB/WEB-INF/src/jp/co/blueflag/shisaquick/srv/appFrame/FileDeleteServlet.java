/*
 * �_�E�����[�h�t�@�C���폜�p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.FileChk;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;

/**
 *
 * Class���@: �_�E�����[�h�t�@�C���폜�ʐM
 * �����T�v : �_�E�����[�h�t�@�C���폜�p��URL�ʐM���s���A�Ώۃt�@�C���̍폜���s���B
 *
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class FileDeleteServlet extends HttpServlet {

	/**
	 * HttpPost����M
	 *  : POST�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		ExceptionManager em = null;

		String strFileNames = "";
	    String strFilePath = "";

		//�yQP@40404�zadd start TT E.Kitazawa 2014/10/28
	    String strMsg = "";
		//�yQP@40404�zadd end TT E.Kitazawa 2014/10/28

		try{
			em = new ExceptionManager(this.getClass().getName());

			strFileNames = req.getParameter("FName").toString();

			String url;
			try {
				url = URLEncoder.encode(req.getParameter("FName").toString() , "ASCII");

				System.out.println(url);
			} catch (UnsupportedEncodingException e) {
				printPage(resp, "UnsupportedEncodingException", strMsg);
				e.printStackTrace();
			}

			//���N�G�X�g�����Ώۃt�@�C���p�X���擾
		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
//2010/01/13�@UPDATE�@Start�@isono�@Miclosoft��S-JIS�g�������Ή�
//		    	val = new String(byteData, "Shift_JIS");
		      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
//2010/01/13�@UPDATE�@End�@�@isono�@Miclosoft��S-JIS�g�������Ή�
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
				printPage(resp, "�t�@�C���p�X�̎擾�Ɏ��s", strMsg);
				em.cnvException(e, "�t�@�C���p�X�̎擾�Ɏ��s���܂����B");
//		    	System.out.println(e);
		    }

			//�yQP@40404�zadd start TT E.Kitazawa 2014/09/18
			//���N�G�X�g�����T�[�o�[�p�X���擾
			String strDLPath = "";

			if (req.getParameter("DLPath")!= null){

			    val = req.getParameter("DLPath");
			    strDLPath = val;
			    try {
			    	byte[] byteData = val.getBytes("ISO_8859_1");
			    	//val = new String(byteData, "Shift_JIS");
			      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
			      	strDLPath = val;

			      	}catch(UnsupportedEncodingException e){
					printPage(resp, "DLPath�̎擾�Ɏ��s", strMsg);
			    	System.out.println(e);
			    }
			}
			//�yQP@40404�zadd end TT E.Kitazawa 2014/09/18

			//�yQP@40404�zmod start TT E.Kitazawa 2014/09/18
			strFilePath = ConstManager.getRootAppPath();

			//�폜�t�@�C���̃��[�gURL�𐶐�
			if (strDLPath.equals("")) {
				// �t�H���_�[�w�肪�Ȃ�����Default
				strFilePath += ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER");
				strFilePath += "\\";
	        } else {
	        	// �T�[�o�[�p�X�iconst�l�j
	        	// strDLPath �ɂ̓T�u�t�H���_�[���t������Ă���
	        	// DELETE�����ł�const�l�̂݁istrFileNames �ɃT�u�t�H���_�[��t���j
				strFilePath += ConstManager.getConstValue(Category.�ݒ�, strDLPath);
				strFilePath += "\\";
	        }

			// strFileNames�F�t�H���_�[���폜���鎞�A�T�u�t�H���_�[���܂Ŏw��̏ꍇ������
			//�yQP@40404�zmod end TT E.Kitazawa 2014/09/18
			//�t�@�C���ʂɕ���
			String[] listFName = strFileNames.split(":::");

			FileDelete fileDel = new FileDelete();

			//�t�@�C���폜���W�b�N���ďo��
			try {
				for (int i = 0; i < listFName.length; i++) {
					//�yQP@40404�zmod start TT E.Kitazawa 2014/09/18
					// listFName[i]���t�H���_�[���H ���݂���t�@�C���̎������폜���s
					if (chkFileExists(strFilePath + listFName[i]) == 1) {
						fileDel.fileDeleteLogic(strFilePath + listFName[i]);
						strMsg += "�t�@�C���폜�F" + strFilePath + listFName[i] + "<br/>";
					}

					// �ǉ��d�l�F�T�u�t�H���_�[�w��̏ꍇ�A�T�u�t�H���_�[���폜
					if (!strDLPath.equals("")) {
						// �t�H���_�[���Ƀt�@�C�����c���Ă���ꍇ�A�T�u�t�H���_�[���t�@�C�����폜
						// listFName[i] �ɃT�u�t�H���_�[���܂�
						strMsg += dirDelete(strFilePath, listFName[i], resp);
					}
					//�yQP@40404�zmod end TT E.Kitazawa 2014/09/18
				}
			} catch (Exception e) {
				printPage(resp, "1.�t�@�C���̍폜�Ɏ��s", strMsg);
				em.cnvException(e, "�t�@�C���̍폜�Ɏ��s���܂����B");
//				e.printStackTrace();
			} finally {
				em = null;
				fileDel = null;
				// strDLPath �ɃT�u�t�H���_�[���ݒ肳��Ă��鎞�������b�Z�[�W�o��
				if (!strDLPath.equals("")) {
					printPage(resp, "���폜�����I����", strMsg);
				}
			}

		}catch(Exception e){
			printPage(resp, "2.�t�@�C���̍폜�Ɏ��s", strMsg);
			e.printStackTrace();
		}finally{

		}

	}


	//�yQP@40404�zadd start TT E.Kitazawa 2014/09/18
	/**
	 * �t�H���_�[�폜
	 * @param   argServerFPath�F �T�[�o�[�p�X
	 *          argDelFile    �F �T�u�t�H���_�[���܂ލ폜�t�@�C�����A���̓T�u�t�H���_�[���̂�
	 * @return  strRet        �F �폜���ʁi�폜�t�@�C�����A�t�H���_�[���j
	 *
	 */
	private String dirDelete(String argServerFPath, String argDelFile, HttpServletResponse res){

		String[] delFile = null;			// �T�u�t�H���_�[�A�t�@�C�����𕪉�
		String strFolder = "";				// �폜�t�H���_�[��
		String strRet = "";					// �߂�l�i�폜���ʂ�Ԃ��j

		//�t�@�C���폜�I�u�W�F�N�g�̐���
		FileDelete fileDel = new FileDelete();

		//�t�@�C���`�F�b�N�I�u�W�F�N�g�̐���
		FileChk fileChk = new FileChk();

		if (argServerFPath.equals("")) {
			return strRet;
		}
		if (argDelFile.equals("")) {
			return strRet;
		}

		// �T�u�t�H���_�[�ƃt�@�C���𕪉�
		delFile = argDelFile.split("\\\\");
		strFolder = argServerFPath + delFile[0];

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			try {
				// �t�H���_�[���ꗗ���擾����
				String[] fileLst = fileChk.fileListChkLogic(strFolder);
				// �t�H���_�[���t�@�C�����`�F�b�N
				if (fileLst == null) {
					// �t�H���_�[������̎��A�t�H���_�[�폜
					fileDel.fileDeleteLogic(strFolder);
					strRet += "�t�H���_�[�폜--" + strFolder + "<br/>";
				} else {
					strRet += "�t�H���_�[���t�@�C�����F" + fileLst.length + "<br/>";

					for (int i=0; i<fileLst.length; i++) {
						// �t�H���_�[���t�@�C�����폜
						fileDel.fileDeleteLogic(strFolder + "\\" + fileLst[i]);
						strRet += "�t�@�C���폜�F" + strFolder + "\\" + fileLst[i] + "<br/>";
					}
					// �t�H���_�[�폜
					fileDel.fileDeleteLogic(strFolder);
					strRet += "�t�H���_�[�폜�F" + strFolder + "<br/>";
				}

			} catch (Exception e) {
				strRet += "�t�H���_�[�폜�����Ɏ��s���܂����B";
				printPage(res, "dirDelete:", strRet);

				em.cnvException(e, "�t�H���_�[�폜�����Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileDel = null;
			}

		}catch(Exception e){

		}finally{

		}

		return strRet;

	}

	/**
	 * �T�[�o�[�t�@�C���`�F�b�N
	 * @param argServerFPath �T�[�o�[�t�@�C���p�X
	 * @return int    �t�@�C�������݂���i�ʏ�t�@�C���j�F1
	 *                �t�@�C�������݂���i�t�H���_�[�j�F2
	 *                �t�@�C�������݂���i���̑��j�F9
	 *                �t�@�C�������݂��Ȃ����F-1
	 *
	 */
	private int chkFileExists(String argServerFPath){

		int retInt = -1;			// �߂�l�̐ݒ�

		if (argServerFPath.equals("")) {
			return retInt;
		}

		//�t�@�C���`�F�b�N�I�u�W�F�N�g�̐���
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//�t�@�C���`�F�b�N���W�b�N���ďo��
			try {

				// �t�@�C���̑��݃`�F�b�N
				retInt = fileChk.fileChkLogic(argServerFPath);

			} catch (Exception e) {
				em.cnvException(e, "�t�@�C�����݃`�F�b�N�Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retInt;

	}


	/**
	 * �폜�t�@�C���m�F���o�͂���
	 * @param res   �T�[�u���b�g���X�|���X
	 *        strHeder ���b�Z�[�W�w�b�_�[
	 *        strMsg   ���b�Z�[�W
	 */
	private void printPage(HttpServletResponse res, String strHeder, String strMsg){


		try{
			//ContentType ��ݒ�
			res.setContentType(ConstManager.SRV_HTML_CHARSET);

			//�o�͗p PrintWriter ���擾
			PrintWriter out = res.getWriter();

			//HTML �o��
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>�폜�m�F</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
//			out.println("                alert();");
//			                             ��ʂ����
//			out.println("                this.window.close();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("    </head>");
			out.println("    <body onload=\"kakunin()\" style=\"margin:10px;\">");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println(   strHeder  + "<br/><br/>");
			out.println(   strMsg);
			out.println("      <input type=\"hidden\" name=\"msg\" value =\"" + strMsg + "\">");
			out.println("    </form>");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
		}
	}

	//�yQP@40404�zadd end TT E.Kitazawa 2014/09/18

}
