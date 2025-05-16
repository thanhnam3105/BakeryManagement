package jp.co.blueflag.shisaquick.jws.common;

import java.io.Serializable;

/**
 * 
 * ���ʃG���[����
 *  : ��O�����̊�{������s��
 *
 */
public class ExceptionBase extends Exception implements Serializable  {

	private String strErrmsg;			//�G���[���b�Z�[�W
	private String strErrShori;			//�G���[������
	private String strMsgNo;			//���b�Z�[�WNo
	private String strErrCd;			    //�G���[�R�[�h
	private String strSystemMsg;		//�V�X�e�����b�Z�[�W

	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public ExceptionBase() {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();

		this.strErrmsg = "";
		this.strErrShori = "";
		this.strMsgNo = "";
		this.strErrCd = "";
		this.strSystemMsg = "";
	}


	/**
	 * �R���X�g���N�^
	 * @param e : Exception�N���X
	 * @param strSetErrmsg : �G���[���b�Z�[�W
	 * @param stSetrErrShori : �G���[������
	 * @param strSetMsgNo : ���b�Z�[�WNo
	 * @param strSetErrCd : �G���[�R�[�h
	 * @param strSetSystemMsg : �V�X�e�����b�Z�[�W
	 */
	public ExceptionBase(Exception e, String strSetErrmsg, String stSetrErrShori, String strSetMsgNo, String strSetErrCd, String strSetSystemMsg) {
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super(e);

		this.strErrmsg = strSetErrmsg;
		this.strErrShori = stSetrErrShori;
		this.strMsgNo = strSetMsgNo;
		this.strErrCd = strSetErrCd;
		this.strSystemMsg = strSetSystemMsg;
	}

	/**
	 * �G���[���b�Z�[�W �Q�b�^�[
	 * @return strErrmsg : �G���[���b�Z�[�W�̒l��Ԃ�
	 */
	public String getStrErrmsg() {
		return strErrmsg;
	}

	/**
	 * �G���[���b�Z�[�W �Z�b�^�[
	 * @param _strErrmsg : �G���[���b�Z�[�W�̒l���i�[����
	 */
	public void setStrErrmsg(String _strErrmsg) {
		this.strErrmsg = _strErrmsg;
	}

	/**
	 * �G���[������ �Q�b�^�[
	 * @return strErrShori : �G���[�������̒l��Ԃ�
	 */
	public String getStrErrShori() {
		return strErrShori;
	}

	/**
	 * �G���[������ �Z�b�^�[
	 * @param _strErrShori : �G���[�������̒l���i�[����
	 */
	public void setStrErrShori(String _strErrShori) {
		this.strErrShori = _strErrShori;
	}

	/**
	 * ���b�Z�[�WNo �Q�b�^�[
	 * @return strMsgNo : ���b�Z�[�WNo�̒l��Ԃ�
	 */
	public String getStrMsgNo() {
		return strMsgNo;
	}

	/**
	 * ���b�Z�[�WNo �Z�b�^�[
	 * @param _strMsgNo : ���b�Z�[�WNo�̒l���i�[����
	 */
	public void setStrMsgNo(String _strMsgNo) {
		this.strMsgNo = _strMsgNo;
	}

	/**
	 * �G���[�R�[�h �Q�b�^�[
	 * @return strErrCd : �G���[�R�[�h�̒l��Ԃ�
	 */
	public String getStrErrCd() {
		return strErrCd;
	}

	/**
	 * �G���[�R�[�h �Z�b�^�[
	 * @param _strErrCd : �G���[�R�[�h�̒l���i�[����
	 */
	public void setStrErrCd(String _strErrCd) {
		this.strErrCd = _strErrCd;
	}

	/**
	 * �V�X�e�����b�Z�[�W �Q�b�^�[
	 * @return strSystemMsg : �V�X�e�����b�Z�[�W�̒l��Ԃ�
	 */
	public String getStrSystemMsg() {
		return strSystemMsg;
	}

	/**
	 * �V�X�e�����b�Z�[�W �Z�b�^�[
	 * @param _strSystemMsg : �V�X�e�����b�Z�[�W�̒l���i�[����
	 */
	public void setStrSystemMsg(String _strSystemMsg) {
		this.strSystemMsg = _strSystemMsg;
	}
	
}
