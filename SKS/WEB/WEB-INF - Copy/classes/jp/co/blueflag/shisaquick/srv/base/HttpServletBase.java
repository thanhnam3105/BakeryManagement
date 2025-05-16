package jp.co.blueflag.shisaquick.srv.base;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

public class HttpServletBase extends HttpServlet {
	
	//���N�G�X�g
	protected HttpServletRequest req = null;
	//���X�|���X
	protected HttpServletResponse res = null;
	//�G�N�Z�v�V�����}�l�[�W���[
	protected ExceptionManager em = null;
	
	/**
	 * HttpServlet�ʐM���N���X�A����������
	 * @param _req�@:�@Http���N�G�X�g
	 * @param _res�@�F�@Http���X�|���X
	 */
	protected void initServlet(HttpServletRequest _req, HttpServletResponse _res){
		
		//�G�N�Z�v�V�����}�l�[�W���̐���
		this.em = new ExceptionManager(this.getClass().getName());
		//���N�G�X�g�̑ޔ�
		req = _req;
		//���X�|���X�̑ޔ�
		res = _res;
		//�R���X�g���̎擾
		//���[�gURL
		setRootURL();
		//���[�gAPP�p�X
		setRootAppPath();
		
		//��N�G�X�g�J�E���g
		ReqCount();
		
	}
	/**
	 * URL�̃��[�g���擾����
	 * @return String URL�̃��[�g
	 */
	private void setRootURL(){
		
		StringBuffer sbRequestURL = req.getRequestURL (); 
		String sServletPath = req.getServletPath (); 
		int index = sbRequestURL.lastIndexOf (sServletPath); 
		ConstManager.setRootURL(sbRequestURL.substring (0, index+1)); 
	
	}
	/**
	 * Tomcat�̃��[�g�p�X���擾����
	 * @return String ���[�g�p�X
	 */
	private void setRootAppPath(){

		ServletContext sc = getServletContext();
		ConstManager.setRootAppPath(sc.getRealPath("/"));
	
	}
	/**
	 * �A�N�Z�X�J�E���g���̕\��
	 */
	private void ReqCount(){
		
		try{
			if (ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL") != "0"){
				ConstManager.ReqCount++;
				System.out.print("��Req Count =[" + Long.toString(ConstManager.ReqCount) + "] \n");
				
			}
			
		}catch(Exception e){
			
		}
		
	}

}
