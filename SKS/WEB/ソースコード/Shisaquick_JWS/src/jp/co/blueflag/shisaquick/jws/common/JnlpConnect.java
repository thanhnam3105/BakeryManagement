package jp.co.blueflag.shisaquick.jws.common;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 * 
 * Jnlp�ڑ��N���X
 * �@�F�@jnlp�̏����擾����
 * @author k-katayama
 *
 */
public class JnlpConnect {
	
	private String strAddressAjax;		//Ajax�ʐM�p�A�h���X
	private ExceptionBase ex;				//�G���[�n���h��

	/**
	 * �R���X�g���N�^
	 */
	public JnlpConnect() {
		this.ex = null;
		this.strAddressAjax = "";
	}

	/**
	 * Ajax�ʐM�p�A�h���X�ݒ�
	 *  : Jnlp��codebase�l���AAjax�ʐM�p�̃A�h���X���擾���� 
	 * @throws ExceptionBase 
	 * @throws UnavailableServiceException 
	 */
	public void setAddressAjax() throws ExceptionBase, UnavailableServiceException {
		this.strAddressAjax = "";
		
		try {
			//codebase�l���擾����
			BasicService basicService = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
			String strCodeBase = basicService.getCodeBase().toString();
			//�A�h���X�ɐݒ�
			if ( !strCodeBase.equals("") ) {
				this.strAddressAjax = strCodeBase.substring(0, strCodeBase.length()-4) + "AjaxServlet";
			}
		} catch (UnavailableServiceException e) {
			//JNLP����ڑ����Ă��Ȃ��ꍇ�́A�������s��Ȃ�
			this.strAddressAjax = "";
			throw e;
		} catch (Exception e) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("Ajax�ʐM�p�A�h���X�擾���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}
	
	/**
	 * Ajax�ʐM�p�A�h���X �Q�b�^�[
	 * @return Ajax�ʐM�p�A�h���X��Ԃ�
	 */
	public String getStrAddressAjax() {
		return this.strAddressAjax;
	}	
	/**
	 * Ajax�ʐM�p�A�h���X �Z�b�^�[
	 * @param Ajax�ʐM�p�A�h���X���擾
	 */
	public void setStrAddressAjax(String _strAddressAjax) {
		this.strAddressAjax = _strAddressAjax;
	}
	
}
