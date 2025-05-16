/* 
 * �_�E�����[�h�t�@�C���폜�p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

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

		String strFileNames = "";
	    String strFilePath = "";

		try{
			//���N�G�X�g�����Ώۃt�@�C���p�X���擾
		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		    	val = new String(byteData, "Shift_JIS");
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }

			//�_�E�����[�h�̃��[�gURL�𐶐�
			strFilePath = ConstManager.getRootAppPath();
			strFilePath += ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER");
			strFilePath += "\\";
			
			//�t�@�C���ʂɕ���
			String[] listFName = strFileNames.split(":::");
	    
			FileDelete fileDel = new FileDelete();

			//�t�@�C���폜���W�b�N���ďo��
			try {
				for (int i = 0; i < listFName.length; i++) {
					fileDel.fileDeleteLogic(strFilePath + listFName[i]);				
				}
			} catch (ExceptionSystem e) {
				e.printStackTrace();
			} catch (ExceptionUser e) {
				e.printStackTrace();
			} catch (ExceptionWaning e) {
				e.printStackTrace();
			}
		
		}catch(Exception e){

		}finally{
			
		}

	}
}
