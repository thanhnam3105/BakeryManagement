package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;

/**
 * 
 * ���N�G�X�g/���X�|���XField
 *  : ���ږ�/���ڒl��ێ�����
 *
 */
public class RequestResponsFieldBean extends RequestResponsRow {
	
	/**
	 * �R���X�g���N�^ : �C���X�^���X����  
	 */
	public RequestResponsFieldBean() {
		//���׽�̃R���X�g���N�^
		super("", "");

	}
	/**
	 * �R���X�g���N�^ : �C���X�^���X����  
	 * @param name : ���ږ�
	 * @param value : ���ڒl
	 */
	public RequestResponsFieldBean(String strFeldNm, String strFeldValue) {
		//���׽�̃R���X�g���N�^
		super(strFeldNm, strFeldValue);
		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());
	}

}
