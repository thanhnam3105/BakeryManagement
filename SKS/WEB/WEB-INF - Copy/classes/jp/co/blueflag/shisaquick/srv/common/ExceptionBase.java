package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * ���ʗ�O�����N���X
 *  : ��O�������̏����i�[�B
 *    ��O�N���X�̊��N���X�B
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class ExceptionBase extends Exception {
	private static final long serialVersionUID = 1L;		//�f�t�H���g�V���A���o�[�W����ID
	
	private String UserMsg = "";			//���[�U�[���b�Z�[�W
	private String ClassName = "";			//��O�����N���X��
	private String MsgNumber = "";			//���b�Z�[�W�ԍ�
	private String SystemErrorCD = "";		//�V�X�e���G���[�R�[�h
	private String SystemErrorMsg = "";	//�V�X�e���G���[���b�Z�[�W
	
	/**
	 * �R���X�g���N�^
	 *  : ���ʗ�O�����p�R���X�g���N�^
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public ExceptionBase() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}

	/**
	 * ���[�U�[���b�Z�[�W �Q�b�^�[
	 * @return userMsg : ���[�U�[���b�Z�[�W�̒l��Ԃ�
	 */
	public String getUserMsg() {
		return UserMsg;
	}
	/**
	 * ���[�U�[���b�Z�[�W �Z�b�^�[
	 * @param _userMsg : ���[�U�[���b�Z�[�W�̒l���i�[����
	 */
	public void setUserMsg(String _userMsg) {
		UserMsg = _userMsg;
	}

	/**
	 * ��O�����N���X�� �Q�b�^�[
	 * @return className : ��O�����N���X���̒l��Ԃ�
	 */
	public String getClassName() {
		return ClassName;
	}
	/**
	 * ��O�����N���X�� �Z�b�^�[
	 * @param _className : ��O�����N���X���̒l���i�[����
	 */
	public void setClassName(String _className) {
		ClassName = _className;
	}

	/**
	 * ���b�Z�[�W�ԍ� �Q�b�^�[
	 * @return msgNumber : ���b�Z�[�W�ԍ��̒l��Ԃ�
	 */
	public String getMsgNumber() {
		return MsgNumber;
	}
	/**
	 * ���b�Z�[�W�ԍ� �Z�b�^�[
	 * @param _msgNumber : ���b�Z�[�W�ԍ��̒l���i�[����
	 */
	public void setMsgNumber(String _msgNumber) {
		MsgNumber = _msgNumber;
	}
	
	/**
	 * �V�X�e���G���[�R�[�h �Q�b�^�[
	 * @return systemErrorCD : �V�X�e���G���[�R�[�h�̒l��Ԃ�
	 */
	public String getSystemErrorCD() {
		return SystemErrorCD;
	}
	/**
	 * �V�X�e���G���[�R�[�h �Z�b�^�[
	 * @param _systemErrorCD : �V�X�e���G���[�R�[�h�̒l���i�[����
	 */
	public void setSystemErrorCD(String _systemErrorCD) {
		SystemErrorCD = _systemErrorCD;
	}

	/**
	 * �V�X�e���G���[���b�Z�[�W �Q�b�^�[
	 * @return systemErrorMsg : �V�X�e���G���[���b�Z�[�W�̒l��Ԃ�
	 */
	public String getSystemErrorMsg() {
		return SystemErrorMsg;
	}
	/**
	 * �V�X�e���G���[���b�Z�[�W �Z�b�^�[
	 * @param _systemErrorMsg : �V�X�e���G���[���b�Z�[�W�̒l���i�[����
	 */
	public void setSystemErrorMsg(String _systemErrorMsg) {
		SystemErrorMsg = _systemErrorMsg;
	}
	
}
