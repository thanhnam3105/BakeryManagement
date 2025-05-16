package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/**
 * 
 * �p�����[�^�f�[�^�ێ��N���X
 *
 */
public class ParamData extends DataBase {

	private BigDecimal dciUser;			//���[�UID
	private String strSisaku_user;	//����CD_���[�U
	private String strSisaku_nen;	//����CD_�N
	private String strSisaku_oi;	//����CD_�ǔ�
	private String strMode;			//���[�h
	
	/**
	 * �p�����[�^�f�[�^�@�R���X�g���N�^
	 */
	public ParamData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.dciUser = new BigDecimal(0);
		this.strSisaku_user = "";
		this.strSisaku_nen = "";
		this.strSisaku_oi = "";
		this.strMode = "";
	}

	/**
	 * ���[�UID �Q�b�^�[
	 * @return dciUser : ���[�UID�̒l��Ԃ�
	 */
	public BigDecimal getDciUser() {
		return dciUser;
	}
	/**
	 * ���[�UID �Z�b�^�[
	 * @param _dciUser : ���[�UID�̒l���i�[����
	 */
	public void setDciUser(BigDecimal _dciUser) {
		this.dciUser = _dciUser;
	}

	/**
	 * ����CD_���[�U �Q�b�^�[
	 * @return strSisaku_user : �̒l��Ԃ�
	 */
	public String getStrSisaku_user() {
		return strSisaku_user;
		
	}
	/**
	 * ����CD_�N �Q�b�^�[
	 * @return getStrSisaku_nen : �̒l��Ԃ�
	 */
	public String getStrSisaku_nen() {
		return strSisaku_nen;
		
	}
	/**
	 * ����CD_�ǔ� �Q�b�^�[
	 * @return strSisaku : �̒l��Ԃ�
	 */
	public String getStrSisaku_oi() {
		return strSisaku_oi;
		
	}
	/**
	 * ����CD �Z�b�^�[
	 * @param _strSisaku : ����CD�̒l���i�[����
	 */
	public void setStrSisaku(String _strSisaku) {
		if(_strSisaku.equals("new")){
			this.strSisaku_user = null;
			this.strSisaku_nen = null;
			this.strSisaku_oi = null;
		}else{
			String[] str1Ary = _strSisaku.split("-");
			this.strSisaku_user = str1Ary[0];
			this.strSisaku_nen = str1Ary[1];
			this.strSisaku_oi = str1Ary[2];
		}
	}

	/**
	 * ���[�h �Q�b�^�[
	 * @return strMode : ���[�h�̒l��Ԃ�
	 */
	public String getStrMode() {
		return strMode;
	}
	/**
	 * ���[�h �Z�b�^�[
	 * @param _strMode : ���[�h�̒l���i�[����
	 */
	public void setStrMode(String _strMode) {
		this.strMode = _strMode;
	}
	
}
