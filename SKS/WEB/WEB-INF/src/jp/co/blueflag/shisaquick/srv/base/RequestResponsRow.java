package jp.co.blueflag.shisaquick.srv.base;

/**
 * 
 * ���N�G�X�g/���X�|���X����
 *  : ���ږ�/���ڒl��ێ�����
 *
 */
public class RequestResponsRow extends ObjectBase {

	protected String strName;		//���ږ�
	protected String strValue;		//���ڒl
	
	/**
	 * �R���X�g���N�^ : �C���X�^���X����  
	 * @param name : ���ږ�
	 * @param value : ���ڒl
	 */
	public RequestResponsRow(String strFeldNm, String strFeldValue) {
		super();
		
		this.strName = strFeldNm;
		this.strValue = strFeldValue;
	}

	/**
	 * ���ږ� �Q�b�^�[
	 * @return name : ���ږ��̒l��Ԃ�
	 */
	public String getName() {
		return strName;
	}
	/**
	 * ���ږ� �Z�b�^�[
	 * @param _name : ���ږ��̒l���i�[����
	 */
	public void setName(String _strName) {
		strName = _strName;
	}

	/**
	 * ���ڒl �Q�b�^�[
	 * @return value : ���ڒl�̒l��Ԃ�
	 */
	public String getValue() {
		return strValue;
	}
	/**
	 * ���ڒl �Z�b�^�[
	 * @param _value : ���ڒl�̒l���i�[����
	 */
	public void setValue(String _strValue) {
		strValue = _strValue;
	}
	
	
}
