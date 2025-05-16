package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * �萔XML�i�[Row
 *  : �R���X�gXML��ǂݍ��݃��X�g�̍s
 *
 */
public class XmlConstListRow {

	private String Code;			//�R���X�g�̃R�[�h
	private String Value;			//�R���X�g�̒l
	
	/**
	 * �R���X�g���N�^
	 * @param strCode : �R�[�h
	 * @param strValue : �l
	 */
	public XmlConstListRow(String strCode, String strValue) {
		//�@ �C���X�^���X�𐶐�����
		this.Code = "";
		this.Value = "";
		
		//�A �l�̊i�[
		this.Code = strCode;
		this.Value = strValue;
	}
	
	/**
	 * �R���X�g�̃R�[�h �Q�b�^�[
	 * @return code : �R���X�g�̃R�[�h�̒l��Ԃ�
	 */
	public String getCode() {
		return this.Code;
	}
	/**
	 * �R���X�g�̃R�[�h �Z�b�^�[
	 * @param _code : �R���X�g�̃R�[�h�̒l���i�[����
	 */
	public void setCode(String _code) {
		this.Code = _code;
	}
	
	/**
	 * �R���X�g�l �Q�b�^�[
	 * @return value : �R���X�g�l�̒l��Ԃ�
	 */
	public String getValue() {
		return this.Value;
	}
	/**
	 * �R���X�g�l �Z�b�^�[
	 * @param _value : �R���X�g�l�̒l���i�[����
	 */
	public void setValue(String _value) {
		this.Value = _value;
	}
	
}
