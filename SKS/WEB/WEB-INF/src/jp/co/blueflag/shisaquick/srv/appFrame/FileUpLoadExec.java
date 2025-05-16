/*
 * �A�b�v���[�h�t�@�C���p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C����n��
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.FileChk;
import jp.co.blueflag.shisaquick.srv.common.FileCreate;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
*
* Class���@: �A�b�v���[�h�ʐM
* �����T�v : �t�@�C���A�b�v���[�h�p��URL�ʐM���s���A�Ώۉ�ʂ��J���@
*           �i���펞�F�A�b�v���[�h��ʁ@�E�@�ُ펞�F�G���[��ʁj
*
* @since   2014/08/26
* @author  E.Kitazawa
* @version 1.0
*/
@SuppressWarnings("serial")
public class FileUpLoadExec extends HttpServletBase {

	/**
	 * HttpPost����M
	 *  : POST�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	// �t�@�C���A�b�v���[�h���鎞�́AdoPost���\�b�h���g���B
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//		response.setContentType("text/html; charset=Shift_JIS");
		response.setContentType(ConstManager.SRV_HTML_CHARSET);
		PrintWriter out = response.getWriter();

		// �J�ڌ�JSP
		String strRef = request.getHeader("Referer");

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			// enctype="multipart/form-data" �̎��AgetParameter�Ńf�[�^�擾���ł��Ȃ�
			//          Enumeration names = request.getParameterNames();

			// �A�b�v���[�h�t�@�C�����󂯎�鏀��
			// �f�B�X�N�̈�𗘗p����A�C�e���t�@�N�g���[���쐬
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// ServletFileUpload���쐬
			ServletFileUpload sfu = new ServletFileUpload(factory);

			// �A�b�v���[�h��̃p�X
			String upPath = "";
			String upFileNm = "";

			//-- ���N�G�X�g��� --//
			// �A�b�v���[�h�� const��`�� --- strServerConst
			String strServerConst = "";

			// �A�b�v���[�h�t�@�C���̃J�E���g�i�T�u�t�H���_�[�Ɠ������j
			int intIndex = 0;
			// �T�u�t�H���_�[�� --- strSubFolder
			String[] listSubFolder = null;
			String strSubFolder = "";

			// �A�b�v���[�h����t�B�[���h�� --- strFieldNm(split)
			String[] listFieldNm = null;
			// �t�B�[���h��
			String fieldname = "";

			// �폜����t�@�C���� --- strFilePath
			String[] listDeleteFname = null;
			String strDeleteFname = "";
			String[] listTmp = null;

			// �t�@�C�����݂̂̒��o�p
			String filename = "";

			// ���N�G�X�g���t�@�C���A�C�e���̃��X�g�ɕϊ�
			List<FileItem> list = sfu.parseRequest(request);

			// ���N�G�X�g�����Ɏ擾����
			Iterator<FileItem> iterator = list.iterator();

			try {
				while(iterator.hasNext()){
					// �R���N�V��������1��1��FileItem�N���X�̃I�u�W�F�N�g�����o��
					FileItem item = (FileItem)iterator.next();

					// ���o����FileItem�ɑ΂��鏈��
					// �t�H�[�����̃R���g���[���̃f�[�^���擾����
					if (item.isFormField()){
						// �t�H�[�����̃R���g���[���̃f�[�^(text�l)���擾����
						// name�i�R���g���[�����j
						String name = item.getFieldName();

						if  (name.equals("strServerConst")) {
							// �A�b�v���[�h��� cost�l
							// strServerConst �� value�l���擾
							strServerConst = item.getString();

						} else if  (name.equals("strSubFolder")) {
							// �T�u�t�H���_�[���i�A�b�v���[�h�t�@�C�����Ɏw��j
							// strSubFolder �� value�l���擾�������i�G���R�[�h�j
							listSubFolder = encodeStr(item.getString()).split(":::");

						} else if  (name.equals("strFieldNm")) {
							// �A�b�v���[�h�t�@�C���̃t�B�[���h���i�A�b�v���[�h�t�@�C���̃`�F�b�N�p�j
							// strFieldNm �� value�l���擾������
							listFieldNm = item.getString().split(":::");

						} else if  (name.equals("strFilePath")) {
							// �폜�t�@�C���p�X�i�T�u�t�H���_�[���܂ށj
							// delFname �� value�l���擾�i�G���R�[�h�j
							listDeleteFname = encodeStr(item.getString()).split(":::");

						}
					}
				}

				// �A�b�v���[�h��̃p�X�擾
				if (!strServerConst.equals("")) {
					// const �l���擾
					upPath = ConstManager.getConstValue(Category.�ݒ�, strServerConst);
					// �A�b�v���[�h��̃T�[�o�[�p�X�ɊK�w�����鎞
					String[] strPath = upPath.split("/");
					String tmpPath = "";
					for (int i=0; i<strPath.length; i++) {
						if (i > 0) tmpPath += "/";
						tmpPath += strPath[i];
						// �T�[�o�[�p�X���擾
						upPath = getServletContext().getRealPath(tmpPath);
						// �A�b�v���[�h��t�H���_�[�̑��݃`�F�b�N
						if (!chkFileExists(upPath, out)) {
							// �t�H���_�[�����݂��Ȃ����A�f�B���N�g���쐬
							createDir(upPath, out);
						}
					}
				}

				// �폜�t�@�C�������w�肳��Ă��鎞�F�t�@�C���폜
				if (listDeleteFname != null) {
					// ���N�G�X�g�����Ɏ擾����
					for (int i=0; i < listDeleteFname.length; i++) {
						strDeleteFname = listDeleteFname[i];
						if (!strDeleteFname.equals("")) {
							// strDeleteFname �� �T�u�t�H���_�[�����܂�
							// �t�@�C�������������o��
							filename = (new File(strDeleteFname)).getName();

							// �A�b�v���[�h��̃t�@�C���p�X
							listTmp = strDeleteFname.split("\\\\");
							strSubFolder = "";
							// �폜�t�@�C�����ɃT�u�t�H���_�[���w�肳��Ă��鎞
							if (listTmp.length > 1) {
								strSubFolder = "\\" + listTmp[0];
							}
							upFileNm = upPath + "\\" + strDeleteFname;

							// �t�@�C���̑��݃`�F�b�N��A�폜
							if (fileDelete(upFileNm, upPath + strSubFolder, out)) {
								System.out.println("�t�@�C�����폜���܂����F" + upFileNm);
							}
						}
					}

				}

				// �t�@�C�������擾����
				iterator = list.iterator();
				// �������b�Z�[�W
				String strmsg = "";

				while(iterator.hasNext()){
					// �R���N�V��������1��1��FileItem�N���X�̃I�u�W�F�N�g�����o��
					FileItem item = (FileItem)iterator.next();

					// ���o����FileItem�ɑ΂��鏈��
					// �t�H�[�����̑��̃R���g���[���̃f�[�^�͏��O����
					if (!item.isFormField()){
						// �A�b�v���[�h���ꂽ�t�@�C���̂ݑΏۂ̏���
						//					  String filename = encodeStr(item.getName());		// �t�@�C���p�X�̃G���R�[�h
						// �t�@�C���p�X
						filename = item.getName();
						// �t�B�[���h��
						fieldname = item.getFieldName();
						// �t�B�[���h���őΏۃt�@�C�����ǂ����`�F�b�N����
						if (!chkFieldNm(listFieldNm, fieldname)) {
							continue;
						}

						// �A�b�v���[�h�Ώۃt�@�C���̏ꍇ
						if ((filename != null) && (!filename.equals(""))){
							// �t�@�C�������������o��
							filename = (new File(filename)).getName();

							// �A�b�v���[�h�Ώۂ̃t�B�[���h����lstSubFolder ���ݒ肳��Ă���
							// �A�b�v���[�h��̃p�X�F�T�u�t�H���_�[��ǉ�
							if ((listSubFolder != null) && (listSubFolder.length > intIndex)) {
								if (!listSubFolder[intIndex].equals("")) {
									strSubFolder = "\\" + listSubFolder[intIndex];
									upFileNm = upPath + strSubFolder + "\\" + filename;
								}
								intIndex++;
							} else {
								upFileNm = upPath + "\\" + filename;
							}

							// �T�u�t�H���_�[�̑��݃`�F�b�N�i�[���F��j
							if (!chkFileExists(upPath + strSubFolder, out)) {
								// �T�u�t�H���_�[�����݂��Ȃ����A�f�B���N�g���쐬
								createDir(upPath + strSubFolder, out);
							}

							// ���݃`�F�b�N
							if (chkFileExists(upFileNm, out)) {
								System.out.println("�t�@�C�������݂��܂��F" + upFileNm);
								// �t�@�C�������݂��鎞�A�������Ƀt�@�C���폜
								fileDelete(upFileNm, "", out);
							}

							// �A�b�v���[�h���s
							item.write(new File(upFileNm));
							// �A�b�v���[�h�t�@�C������ǉ�
							strmsg += "\n" +item.getName();
						}
					}
				}

				// �������b�Z�[�W�\���i�J�ڌ�JSP��URL��n���j
				printHeadPage(out, strRef);
				if (strmsg.equals("")) {
					strmsg +=  "\n�A�b�v���[�h�t�@�C��������܂���B";
				} else {
					strmsg +=  "\n���A�b�v���[�h���܂���";
				}
				String strln ="<input type=\"hidden\" name=\"msg\" value =\"" + strmsg + "\"> ";

				out.println(strln);
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");

			}catch (FileUploadException e) {
				printErrorPage(out, "�t�@�C���A�b�v���[�h�����Ɏ��s���܂����B", upFileNm);
				em.cnvException(e, "�t�@�C���A�b�v���[�h�����Ɏ��s���܂����B");
			} catch (Exception e) {
				printErrorPage(out, "�t�@�C���A�b�v���[�h�����Ɏ��s���܂����B", upFileNm);
				em.cnvException(e, "�t�@�C���A�b�v���[�h�����Ɏ��s���܂����B");
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			out = null;
		}
	}



	/**
	 * ������̃G���R�[�h
	 * @param argStr �A�b�v���[�h��t�@�C����
	 * @return  �G���R�[�h��̃X�g�����O
	 *
	 */
	private String encodeStr(String argStr){

		String retStr = "";

		//���N�G�X�g�����Ώۃt�@�C�������擾
		if (argStr != null){

			String val = argStr;
			retStr = val;
			try {
				byte[] byteData = val.getBytes("ISO_8859_1");
				val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
				retStr = val;
			}catch(UnsupportedEncodingException e){
				System.out.println(e);
			}

		}

		return retStr;

	}


	/**
	 * �A�b�v���[�h����t�B�[���h���̃`�F�b�N
	 * @param   argLstField �A�b�v���[�h�Ώۂ̃t�B�[���h���z��
	 *          argFieldNm  �`�F�b�N����t�B�[���h��
	 * @return  true �F�A�b�v���[�h�����s����
	 *          false�F�A�b�v���[�h�����s���Ȃ�
	 *
	 */
	private boolean chkFieldNm(String[] argLstField, String argFieldNm){

		boolean retBool = false;			// �߂�l�̐ݒ�

		if ((argLstField != null)  && (!argFieldNm.equals(""))){
			//�t�@�C������URL�𐶐�
			for(int i = 0; i < argLstField.length; i++){
				// �Ώۃt�B�[���h�̏ꍇ�Atrue��Ԃ�
				if (argLstField[i].equals(argFieldNm)) {
					retBool = true;
					break;
				}
			}
		}

		return retBool;
	}


	/**
	 * �T�[�o�[�t�@�C���`�F�b�N
	 * @param argServerFPath �A�b�v���[�h��t�@�C����
	 *
	 * @return  true �F�t�@�C�������݂���
	 *          false�F�t�@�C�������݂��Ȃ�
	 *
	 */
	private boolean chkFileExists(String argServerFPath, PrintWriter out){

		boolean retBool = false;			// �߂�l�̐ݒ�

		if (argServerFPath.equals("")) {
			return retBool;
		}

		//�t�@�C���`�F�b�N�I�u�W�F�N�g�̐���
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//�t�@�C���R�s�[���W�b�N���ďo��
			try {

				// �t�@�C���̑��݃`�F�b�N
				if (fileChk.fileChkLogic(argServerFPath) > 0) {
//					System.out.println("�t�@�C�������łɑ��݂��܂��F" + argServerFPath);
					retBool = true;
				}

			} catch (Exception e) {
				printErrorPage(out, "�t�@�C�����݃`�F�b�N�Ɏ��s���܂����B", argServerFPath);
				em.cnvException(e, "�t�@�C�����݃`�F�b�N�Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 * �t�@�C���R�s�[�i�T�[�o�[�ԁj
	 * @param argInFPath  �R�s�[���t�@�C���p�X
	 *        argOutFPath �R�s�[��t�@�C���p�X
	 * @return  true �F�t�@�C�������݂���
	 *          false�F�t�@�C�������݂��Ȃ�
	 *
	 */
	private boolean fileCopy(String argInFPath, String argOutFPath, PrintWriter out){

		boolean retBool = false;			// �߂�l�̐ݒ�

		//�t�@�C���`�F�b�N�I�u�W�F�N�g�̐���
		FileChk fileChk = new FileChk();
		//�t�@�C���V�X�e���I�u�W�F�N�g�̐���
		FileCreate fileCreate = new FileCreate();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//�t�@�C���R�s�[���W�b�N���ďo��
			try {

				// �t�@�C���̑��݃`�F�b�N
				if (fileChk.fileChkLogic(argOutFPath) > 0) {
					System.out.println("�t�@�C�������łɑ��݂��܂��F" + argOutFPath);
					retBool = true;
				}

				// �t�@�C���̑��݃`�F�b�N
				fileCreate.fileCopyLogic(argInFPath, argOutFPath);

			} catch (Exception e) {
				printErrorPage(out, "�t�@�C���R�s�[�����Ɏ��s���܂����B", "");
				em.cnvException(e, "�t�@�C���R�s�[�����Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 *�t�@�C���폜
	 * @param   argServerFPath�F �폜�t�@�C�����i�t���p�X�j
	 *          argFolder  �F �t�H���_�[��
	 * @return  true �F�t�@�C�������݂���
	 *          false�F�t�@�C�������݂��Ȃ�
	 *
	 */
	private boolean fileDelete(String argServerFPath, String argFolder, PrintWriter out){

		boolean retBool = false;			// �߂�l�̐ݒ�

		//�t�@�C���폜�I�u�W�F�N�g�̐���
		FileDelete fileDel = new FileDelete();

		//�t�@�C���`�F�b�N�I�u�W�F�N�g�̐���
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			//�t�@�C���폜���W�b�N���ďo��
			try {
				// �t�@�C�������݂��鎞
				if (chkFileExists(argServerFPath, out)) {
					// �t�@�C���폜�̎��s
					fileDel.fileDeleteLogic(argServerFPath);

					// �t�H���_�[���t�@�C�����`�F�b�N
					if (!argFolder.equals("")) {
						// �t�H���_�[������̎�
						if (fileChk.fileListChkLogic(argFolder) == null) {
							// �t�H���_�[�폜
							fileDel.fileDeleteLogic(argFolder);
						}
					}

					retBool = true;
				}

			} catch (Exception e) {
				printErrorPage(out, "�t�@�C���폜�����Ɏ��s���܂����B", argServerFPath);
				em.cnvException(e, "�t�@�C���폜�����Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileDel = null;
			}

		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 * �A�b�v���[�h��t�H���_�쐬
	 * @param argServerFPath �t�H���_��
	 * @return
	 *
	 */
	private void createDir(String argServerFPath, PrintWriter out){

		//�t�@�C���V�X�e���I�u�W�F�N�g�̐���
		FileCreate fileCreate = new FileCreate();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			//�t�@�C���`�F�b�N���W�b�N���ďo��
			try {
				// �f�B���N�g���쐬����
				fileCreate.createDirLogic(argServerFPath);

			} catch (Exception e) {
				printErrorPage(out, "�f�B���N�g���쐬�����Ɏ��s���܂����B", argServerFPath);
				em.cnvException(e, "�f�B���N�g���쐬�����Ɏ��s���܂����B");
				System.out.println(e);
			} finally {
				em = null;
				fileCreate = null;
			}


		}catch(Exception e){

		}finally{

		}

	}

	/**
	 * �A�b�v���[�h������ʃw�b�_�[�����o��
	 * @param  out    PrintWriter
	 *         strUrl �J�ڌ�JSP��URL
	 *
	 */
	private void printHeadPage(PrintWriter out, String strUrl){

		try{

			// �J�ڌ�URL�ɖ߂�
			String url = "window.open(\"" + strUrl + "\"";
			url += " ,\"_self\",\"menubar=no,resizable=yes\"); ";

			//HTML �o��
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>�A�b�v���[�h���</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
			out.println("                var msg = \"FileUpload �F\"; ");
			out.println("                msg += frm.msg.value; ");

//			out.println("                alert(msg);");          // 	���b�Z�[�W��\�����Ȃ�
			//                           �J�ڌ�JSP���I�[�v��
			out.println( url );
//			                             ��ʂ����iwindow�����Ȃ����j
//			out.println("                this.window.close();");
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
			out.println("    <body onload=\"kakunin()\">");
			out.println("    <form name=\"frm00\" method=\"POST\" enctype=\"multipart/form-data\"  >");

		}catch(Exception e){

		}finally{
		}

	}

	/**
	 * �A�b�v���[�h�����G���[���b�Z�[�W���o��
	 * @param out    PrintWriter
	 *        strHeder ���b�Z�[�W�w�b�_�[
	 *        strMsg   ���b�Z�[�W
	 */
	private void printErrorPage(PrintWriter out, String strHeder, String strMsg){


		try{
			String msg = strHeder;
			msg += "\n��������蒼���Ă��������B";


			//HTML �o��
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>�A�b�v���[�h����</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
			out.println("                var msg = \"\"; ");
			out.println("                msg += frm.msg.value; ");
			out.println("                alert(msg);");
//			out.println("                alert();");
//			                             ��ʂ����iwindow�����Ȃ����j
			out.println("                this.window.close();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("    </head>");
			out.println("    <body onload=\"kakunin()\" style=\"margin:10px;\">");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println("<br/><br/>");
			out.println(   strMsg);
			out.println("      <input type=\"hidden\" name=\"msg\" value =\"" + msg + "\">");
			out.println("    </form>");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
		}
	}

}
