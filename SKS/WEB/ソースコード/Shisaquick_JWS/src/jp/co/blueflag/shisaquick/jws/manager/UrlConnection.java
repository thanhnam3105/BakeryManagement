package jp.co.blueflag.shisaquick.jws.manager;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * URL�ʐM
 *  : URL��ݒ肵�A�u���E�U���N������
 *
 */
public class UrlConnection {
	
	private String TARGET_URL = "";
	private ExceptionBase ex;

	/**
	 * �R���X�g���N�^
	 */
	public UrlConnection(){
		
	}
	
	/**
	 * �R���X�g���N�^
	 *  : �u���E�U���N�����A������URL���y�[�W��\��
	 * @param strUrl : �ڑ���A�h���X
	 */
	public void urlFileDownLoad(String strUrl) throws ExceptionBase{
		
		this.TARGET_URL = strUrl;
		
		try {
			String strServletURL = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();
			strServletURL = strServletURL.replaceAll("AjaxServlet", "FileDownLoadServlet") + "?FName=" + this.TARGET_URL;
			
			Runtime.getRuntime().exec(new String[]{"C:\\Program Files\\Internet Explorer\\iexplore.exe",strServletURL});
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�t�@�C���_�E�����[�h�Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}
		
	}
	
}
