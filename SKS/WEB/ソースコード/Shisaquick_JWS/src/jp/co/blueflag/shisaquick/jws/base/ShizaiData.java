package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ���ރf�[�^�ێ�
 *
 */
public class ShizaiData extends DataBase {

	private BigDecimal dciShisakuUser;	//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;	//����CD-�N
	private BigDecimal dciShisakuNum;	//����CD-�ǔ�
	private int intShizaiSeq;		//����SEQ
	private int intHyojiNo;			//�\����
	private String strShizaiCd;			//����CD
	private String strShizaiNm;		//���ޖ���
	private BigDecimal dciTanka;			//�P��
	private BigDecimal dciBudomari;	//����
	private BigDecimal dciTorokuId;			//�o�^��ID
	private String strTorokuHi;		//�o�^���t
	private BigDecimal dciKosinId;			//�X�V��ID
	private String strKosinHi;		//�X�V���t
	private String strTorokuNm;				//�o�^�Җ�
	private String strKosinNm;				//�X�V�Җ�
//	private ExceptionBase ex;				//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public ShizaiData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShizaiSeq = 0;
		this.intHyojiNo = 0;
		this.strShizaiCd = null;
		this.strShizaiNm = null;
		this.dciTanka = null;
		this.dciBudomari = null;
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
	 * @param  _dciShisakuUser : ����CD-�Ј�CD�̒l���i�[����
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
	 * @param  _dciShisakuNum : ����CD-�ǔԂ̒l���i�[����
	 */
	public void setDciShisakuNum(BigDecimal _dciShisakuNum) {
		this.dciShisakuNum = _dciShisakuNum;
	}

	/**
	 * ����SEQ �Q�b�^�[
	 * @return intShizaiSeq : ����SEQ�̒l��Ԃ�
	 */
	public int getIntShizaiSeq() {
		return intShizaiSeq;
	}
	/**
	 * ����SEQ �Z�b�^�[
	 * @param intShizaiSeq : ����SEQ�̒l���i�[����
	 */
	public void setIntShizaiSeq(int _intShizaiSeq) {
		this.intShizaiSeq = _intShizaiSeq;
	}

	/**
	 * �\���� �Q�b�^�[
	 * @return intHyojiNo : �\�����̒l��Ԃ�
	 */
	public int getIntHyojiNo() {
		return intHyojiNo;
	}
	/**
	 * �\���� �Z�b�^�[
	 * @param  _intHyojiNo : �\�����̒l���i�[����
	 */
	public void setIntHyojiNo(int  _intHyojiNo) {
		this.intHyojiNo = _intHyojiNo;
	}

	/**
	 * ����CD �Q�b�^�[
	 * @return strShizaiCd : ����CD�̒l��Ԃ�
	 */
	public String getStrShizaiCd() {
		return strShizaiCd;
	}
	/**
	 * ����CD �Z�b�^�[
	 * @param _strShizaiCd : ����CD�̒l���i�[����
	 */
	public void setStrShizaiCd(String _strShizaiCd) {
		this.strShizaiCd = _strShizaiCd;
	}

	/**
	 * ���ޖ��� �Q�b�^�[
	 * @return strShizaiNm : ���ޖ��̂̒l��Ԃ�
	 */
	public String getStrShizaiNm() {
		return strShizaiNm;
	}
	/**
	 * ���ޖ��� �Z�b�^�[
	 * @param _strShizaiNm : ���ޖ��̂̒l���i�[����
	 */
	public void setStrShizaiNm(String _strShizaiNm) {
		this.strShizaiNm = _strShizaiNm;
	}

	/**
	 * �P�� �Q�b�^�[
	 * @return dciTanka : �P���̒l��Ԃ�
	 */
	public BigDecimal getDciTanka() {
		return dciTanka;
	}
	/**
	 * �P�� �Z�b�^�[
	 * @param _dciTanka : �P���̒l���i�[����
	 */
	public void setDciTanka(BigDecimal _dciTanka) {
		this.dciTanka = _dciTanka;
	}

	/**
	 * ���� �Q�b�^�[
	 * @return dciBudomari : �����̒l��Ԃ�
	 */
	public BigDecimal getDciBudomari() {
		return dciBudomari;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _dciBudomari : �����̒l���i�[����
	 */
	public void setDciBudomari(BigDecimal _dciBudomari) {
		this.dciBudomari = _dciBudomari;
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
