package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * �����H���f�[�^�ێ�
 *  : �����H���f�[�^�̊Ǘ����s��
 *
 */
public class ManufacturingData extends DataBase {

	private BigDecimal dciShisakuUser;	//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;	//����CD-�N
	private BigDecimal dciShisakuNum;	//����CD-�ǔ�
	private int intTyuiNo;				//���ӎ���NO
	private String strTyuiNaiyo;		//���ӎ���
	private BigDecimal dciTorokuId;		//�o�^��ID
	private String strTorokuHi;			//�o�^���t
	private BigDecimal dciKosinId;		//�X�V��ID
	private String strKosinHi;			//�X�V���t
	private String strTorokuNm;			//�o�^�Җ�
	private String strKosinNm;			//�X�V�Җ�
//	private ExceptionBase ex;			//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public ManufacturingData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intTyuiNo = -1;
		this.strTyuiNaiyo = null;
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
	}
	
	/**
	 * ����CD-�Ј�CD �Q�b�^�[
	 * @return dciShisakuUser : ����CD-�Ј�CD�̒l��Ԃ�
	 */
	public BigDecimal getDciShisakuUser() {
		return dciShisakuUser;
	}
	/**
	 * ����CD-�Ј�CD �Z�b�^�[
	 * @param _dciShisakuUser : ����CD-�Ј�CD�̒l���i�[����
	 */
	public void setDciShisakuUser(BigDecimal _dciShisakuUser) {
		this.dciShisakuUser = _dciShisakuUser;
	}

	/**
	 * ����CD-�N �Q�b�^�[
	 * @return dciShisakuYear : ����CD-�N�̒l��Ԃ�
	 */
	public BigDecimal getDciShisakuYear() {
		return dciShisakuYear;
	}
	/**
	 * ����CD-�N �Z�b�^�[
	 * @param _dciShisakuYear : ����CD-�N�̒l���i�[����
	 */
	public void setDciShisakuYear(BigDecimal _dciShisakuYear) {
		this.dciShisakuYear = _dciShisakuYear;
	}

	/**
	 * ����CD-�ǔ� �Q�b�^�[
	 * @return dciShisakuNum : ����CD-�ǔԂ̒l��Ԃ�
	 */
	public BigDecimal getDciShisakuNum() {
		return dciShisakuNum;
	}
	/**
	 * ����CD-�ǔ� �Z�b�^�[
	 * @param _dciShisakuNum : ����CD-�ǔԂ̒l���i�[����
	 */
	public void setDciShisakuNum(BigDecimal _dciShisakuNum) {
		this.dciShisakuNum = _dciShisakuNum;
	}

	/**
	 * ���ӎ���NO �Q�b�^�[
	 * @return intTyuiNo : ���ӎ���NO�̒l��Ԃ�
	 */
	public int getIntTyuiNo() {
		return intTyuiNo;
	}
	/**
	 * ���ӎ���NO �Z�b�^�[
	 * @param _intTyuiNo : ���ӎ���NO�̒l���i�[����
	 */
	public void setIntTyuiNo(int _intTyuiNo) {
		this.intTyuiNo = _intTyuiNo;
	}

	/**
	 * ���ӎ��� �Q�b�^�[
	 * @return intTyuiNaiyo : ���ӎ����̒l��Ԃ�
	 */
	public String getStrTyuiNaiyo() {
		return strTyuiNaiyo;
	}
	/**
	 * ���ӎ��� �Z�b�^�[
	 * @param _strTyuiNaiyo : ���ӎ����̒l���i�[����
	 */
	public void setStrTyuiNaiyo(String _strTyuiNaiyo) {
		this.strTyuiNaiyo = _strTyuiNaiyo;
	}

	/**
	 * �o�^��ID �Q�b�^�[
	 * @return dciTorokuId : �o�^��ID�̒l��Ԃ�
	 */
	public BigDecimal getDciTorokuId() {
		return dciTorokuId;
	}
	/**
	 * �o�^��ID �Z�b�^�[
	 * @param _dciTorokuId : �o�^��ID�̒l���i�[����
	 */
	public void setDciTorokuId(BigDecimal _dciTorokuId) {
		this.dciTorokuId = _dciTorokuId;
	}

	/**
	 * �o�^���t �Q�b�^�[
	 * @return strTorokuHi : �o�^���t�̒l��Ԃ�
	 */
	public String getStrTorokuHi() {
		return strTorokuHi;
	}
	/**
	 * �o�^���t �Z�b�^�[
	 * @param _strTorokuHi : �o�^���t�̒l���i�[����
	 */
	public void setStrTorokuHi(String _strTorokuHi) {
		this.strTorokuHi = _strTorokuHi;
	}

	/**
	 * �X�V��ID �Q�b�^�[
	 * @return dciKosinId : �X�V��ID�̒l��Ԃ�
	 */
	public BigDecimal getDciKosinId() {
		return dciKosinId;
	}
	/**
	 * �X�V��ID �Z�b�^�[
	 * @param _dciKosinId : �X�V��ID�̒l���i�[����
	 */
	public void setDciKosinId(BigDecimal _dciKosinId) {
		this.dciKosinId = _dciKosinId;
	}

	/**
	 * �X�V���t �Q�b�^�[
	 * @return strKosinHi : �X�V���t�̒l��Ԃ�
	 */
	public String getStrKosinHi() {
		return strKosinHi;
	}
	/**
	 * �X�V���t �Z�b�^�[
	 * @param _strKosinHi : �X�V���t�̒l���i�[����
	 */
	public void setStrKosinHi(String _strKosinHi) {
		this.strKosinHi = _strKosinHi;
	}
	
	/**
	 * �o�^�Җ� �Q�b�^�[
	 * @return strTorokuNm : �o�^�Җ��̒l��Ԃ�
	 */
	public String getStrTorokuNm() {
		return strTorokuNm;
	}
	/**
	 * �o�^�Җ� �Z�b�^�[
	 * @param _strTorokuNm : �o�^�Җ��̒l���i�[����
	 */
	public void setStrTorokuNm(String _strTorokuNm) {
		this.strTorokuNm = _strTorokuNm;
	}
	
	/**
	 * �X�V�Җ� �Q�b�^�[
	 * @return strKosinNm : �X�V�Җ��̒l��Ԃ�
	 */
	public String getStrKosinNm() {
		return strKosinNm;
	}
	/**
	 * �X�V�Җ� �Z�b�^�[
	 * @param _strKosinNm : �X�V�Җ��̒l���i�[����
	 */
	public void setStrKosinNm(String _strKosinNm) {
		this.strKosinNm = _strKosinNm;
	}

}
