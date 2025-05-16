package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * ���샊�X�g�f�[�^�ێ�
 *  : ���샊�X�g�f�[�^�̊Ǘ����s��
 *
 */
public class PrototypeListData extends DataBase {

	private BigDecimal dciShisakuUser;	//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;	//����CD-�N
	private BigDecimal dciShisakuNum;	//����CD-�ǔ�
	private int intShisakuSeq;			//����SEQ
	private int intKoteiCd;				//�H��CD
	private int intKoteiSeq;			//�H��SEQ
	private BigDecimal dciRyo;			//��
	private String strIro;				//�F
	private BigDecimal dciTorokuId;		//�o�^��ID
	private String strTorokuHi;			//�o�^���t
	private BigDecimal dciKosinId;		//�X�V��ID
	private String strKosinHi;			//�X�V���t
	private String strTorokuNm;			//�o�^�Җ�
	private String strKosinNm;			//�X�V�Җ�
//	private ExceptionBase ex;			//�G���[�n���h�����O
	
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
	private int intHenshuOkFg;			//�ҏW�\�t���O
//add end   -------------------------------------------------------------------------------
	
	// ADD start 20120914 QP@20505 No.1
	private BigDecimal dciKouteiShiagari;//�H���d��d��
	// ADD end 20120914 QP@20505 No.1
	
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public PrototypeListData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intKoteiCd = -1;
		this.intKoteiSeq = -1;
		this.dciRyo = null;
		this.strIro = "-1";
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
		this.intHenshuOkFg = 0;
//add end   -------------------------------------------------------------------------------
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
	 * ����SEQ �Q�b�^�[
	 * @return intShisakuSeq : ����SEQ�̒l��Ԃ�
	 */
	public int getIntShisakuSeq() {
		return intShisakuSeq;
	}
	/**
	 * ����SEQ �Z�b�^�[
	 * @param _intShisakuSeq : ����SEQ�̒l���i�[����
	 */
	public void setIntShisakuSeq(int _intShisakuSeq) {
		this.intShisakuSeq = _intShisakuSeq;
	}

	/**
	 * �H��CD �Q�b�^�[
	 * @return intKoteiCd : �H��CD�̒l��Ԃ�
	 */
	public int getIntKoteiCd() {
		return intKoteiCd;
	}
	/**
	 * �H��CD �Z�b�^�[
	 * @param _intKoteiCd : �H��CD�̒l���i�[����
	 */
	public void setIntKoteiCd(int _intKoteiCd) {
		this.intKoteiCd = _intKoteiCd;
	}

	/**
	 * �H��SEQ �Q�b�^�[
	 * @return intKoteiSeq : �H��SEQ�̒l��Ԃ�
	 */
	public int getIntKoteiSeq() {
		return intKoteiSeq;
	}
	/**
	 * �H��SEQ �Z�b�^�[
	 * @param _intKoteiSeq : �H��SEQ�̒l���i�[����
	 */
	public void setIntKoteiSeq(int _intKoteiSeq) {
		this.intKoteiSeq = _intKoteiSeq;
	}

	/**
	 * �� �Q�b�^�[
	 * @return dciRyo : �ʂ̒l��Ԃ�
	 */
	public BigDecimal getDciRyo() {
		return dciRyo;
	}
	/**
	 * �� �Z�b�^�[
	 * @param _dciRyo : �ʂ̒l���i�[����
	 */
	public void setDciRyo(BigDecimal _dciRyo) {
		this.dciRyo = _dciRyo;
	}

	/**
	 * �F �Q�b�^�[
	 * @return strIro : �F�̒l��Ԃ�
	 */
	public String getStrIro() {
		return strIro;
	}
	/**
	 * �F �Z�b�^�[
	 * @param _strIro : �F�̒l���i�[����
	 */
	public void setStrIro(String _strIro) {
		this.strIro = _strIro;
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

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.33,34
	//�ҏW�\�t���O�Z�b�^���Q�b�^
	public int getIntHenshuOkFg() {
		return intHenshuOkFg;
	}
	public void setIntHanshuOkFg(int intHenshuOkFg) {
		this.intHenshuOkFg = intHenshuOkFg;
	}
//add end   -------------------------------------------------------------------------------

// ADD start 20120914 QP@20505 No.1
	/**
	 * �H���d��d�� �Q�b�^�[
	 * @return dciKouteiShiagari : �ʂ̒l��Ԃ�
	 */
	public BigDecimal getDciKouteiShiagari() {
		return dciKouteiShiagari;
	}
	/**
	 * �H���d��d�� �Z�b�^�[
	 * @param _dciKouteiShiagari : �ʂ̒l���i�[����
	 */
	public void setDciKouteiShiagari(BigDecimal _dciKouteiShiagari) {
		this.dciKouteiShiagari = _dciKouteiShiagari;
	}
//ADD end 20120914 QP@20505 No.1

}