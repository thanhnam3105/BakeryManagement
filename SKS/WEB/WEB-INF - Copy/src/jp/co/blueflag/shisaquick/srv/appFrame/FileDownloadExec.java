/*
 * �_�E�����[�h�t�@�C���폜�p�ʐM�N���X
 * �ʐM�����擾�����t�@�C���p�X�̃t�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 *
 * Class���@: �t�@�C���_�E�����[�h����
 * �����T�v : �t�@�C���_�E�����[�h���s���@
 *
 * @since   2009/05/13
 * @author  jinbo
 * @version 1.0
 */
public class FileDownloadExec extends HttpServletBase {

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
		String strDownloadFileNames = "";

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
		    strDownloadFileNames = val;
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		    	//val = new String(byteData, "Shift_JIS");
		      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
		      	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }

		}

		//�yQP@40404�zadd start TT E.Kitazawa 2014/09/08
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
		    	System.out.println(e);
		    }
		}
		//�yQP@40404�zadd end TT E.Kitazawa 2014/09/08

		ServletOutputStream out = null;
		FileInputStream in = null;

        // �_�E�����[�h
		try {
			// �w�b�_�̐ݒ�
			HttpRes.setContentType("application/octet-stream");
			HttpRes.setHeader("Content-Disposition", "attachment; filename=\"" + strDownloadFileNames + "\"");

			// �_�E�����[�h�t�@�C���̏o��
//	        ServletOutputStream out = HttpRes.getOutputStream();
	        out = HttpRes.getOutputStream();
	        System.out.println(new File(".").getAbsolutePath());
//	        FileInputStream in = new FileInputStream(ConstManager.getRootAppPath() + ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER") + "\\" + strFileNames);

			//�yQP@40404�zmod start TT E.Kitazawa 2014/09/08
	        String downLoadFile = "";
	        if (strDLPath.equals("")) {
	        	// �t�H���_�[�w�肪�Ȃ�����Default
	        	downLoadFile = ConstManager.getRootAppPath() + ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER") + "\\" + strFileNames;
	        } else {
	        	// �_�E�����[�h�t�H���_�[�̐ݒ�
				//�t�H���_�[���𕪉�
	        	String[] listDLPath = strDLPath.split(":::");
	        	// const�l���擾�i/�𕪉��j
	        	String dlPath = ConstManager.getConstValue(Category.�ݒ�, listDLPath[0]);

				// �T�u�t�H���_�[��
	        	if (listDLPath.length > 1) {
					// �T�u�t�H���_�[
					dlPath += "\\" +listDLPath[1];
				}

				downLoadFile = ConstManager.getRootAppPath() + dlPath + "\\" + strFileNames;
	        }

	        in = new FileInputStream(downLoadFile);
			//�yQP@40404�zmod end TT E.Kitazawa 2014/09/08

	        byte[] buf = new byte[4096];
	        int size;
	        while ((size = in.read(buf)) != -1) {
	            out.write(buf, 0, size);
	        }
	        in.close();
	        out.close();

		} catch (Exception e) {
	    	System.out.println(e);
		} finally {
	        in = null;
	        out = null;
		}

	}

}
