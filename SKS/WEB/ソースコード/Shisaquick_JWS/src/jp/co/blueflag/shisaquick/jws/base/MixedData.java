package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * �z���f�[�^�ێ�
 *
 */
public class MixedData extends DataBase {

	private BigDecimal dciShisakuUser;	//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;	//����CD-�N
	private BigDecimal dciShisakuNum;	//����CD-�ǔ�
	private int intKoteiCd;				//�H��CD
	private int intKoteiSeq;			//�H��SEQ
	private String strKouteiNm;			//�H����
	private String strKouteiZokusei;	//�H������
	private int intKoteiNo;				//�H����
	private int intGenryoNo;			//������
	private String strGenryoCd;			//����CD
	private int intKaishaCd;			//���CD
	private int intBushoCd;				//����CD
	private String strGenryoNm;			//��������
	private BigDecimal dciTanka;		//�P��
	private BigDecimal dciBudomari;		//����
	private BigDecimal dciGanyuritu;	//���ܗL��
	private BigDecimal dciSakusan;		//�|�_
	private BigDecimal dciShokuen;		//�H��
	private BigDecimal dciSosan;		//���_
	private String strIro;				//�F
	private BigDecimal dciTorokuId;		//�o�^��ID
	private String strTorokuHi;			//�o�^���t
	private BigDecimal dciKosinId;		//�X�V��ID
	private String strKosinHi;			//�X�V���t
	private String strTorokuNm;			//�o�^�Җ�
	private String strKosinNm;			//�X�V�Җ�

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
	private BigDecimal dciMaBudomari;		//�}�X�^����
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
	private BigDecimal dciMsg;		//�l�r��
// ADD end 20121002 QP@20505 No.24

//	private ExceptionBase ex;			//�G���[�n���h�����O

	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public MixedData(){
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();

		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intKoteiCd = 0;
		this.intKoteiSeq = 0;
		this.intKoteiNo = 0;
		this.intGenryoNo = 0;
		this.strGenryoCd = null;
		this.intKaishaCd = -1;
		this.intBushoCd = -1;
		this.strGenryoNm = null;
		this.dciTanka = null;
		this.dciBudomari = null;
		this.dciGanyuritu = null;
		this.dciSakusan = null;
		this.dciShokuen = null;
		this.dciSosan = null;
		this.strIro = "-1";
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strKosinHi = null;
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
		this.dciMaBudomari = null;
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
		this.dciMsg = null;
// ADD end 20121002 QP@20505 No.24
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
	 * �H���� �Q�b�^�[
	 * @return intKoteiNo : �H�����̒l��Ԃ�
	 */
	public int getIntKoteiNo() {
		return intKoteiNo;
	}
	/**
	 * �H���� �Z�b�^�[
	 * @param _intKoteiNo : �H�����̒l���i�[����
	 */
	public void setIntKoteiNo(int _intKoteiNo) {
		this.intKoteiNo = _intKoteiNo;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return intGenryoNo : �������̒l��Ԃ�
	 */
	public int getIntGenryoNo() {
		return intGenryoNo;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _intGenryoNo : �������̒l���i�[����
	 */
	public void setIntGenryoNo(int _intGenryoNo) {
		this.intGenryoNo = _intGenryoNo;
	}

	/**
	 * ����CD �Q�b�^�[
	 * @return strGenryoCd : ����CD�̒l��Ԃ�
	 */
	public String getStrGenryoCd() {
		return strGenryoCd;
	}
	/**
	 * ����CD �Z�b�^�[
	 * @param _strGenryoCd : ����CD�̒l���i�[����
	 */
	public void setStrGenryoCd(String _strGenryoCd) {
		this.strGenryoCd = _strGenryoCd;
	}

	/**
	 * ���CD �Q�b�^�[
	 * @return intKaishaCd : ���CD�̒l��Ԃ�
	 */
	public int getIntKaishaCd() {
		return intKaishaCd;
	}
	/**
	 * ���CD �Z�b�^�[
	 * @param _intKaishaCd : ���CD�̒l���i�[����
	 */
	public void setIntKaishaCd(int _intKaishaCd) {
		this.intKaishaCd = _intKaishaCd;
	}

	/**
	 * ����CD �Q�b�^�[
	 * @return intBushoCd : ����CD�̒l��Ԃ�
	 */
	public int getIntBushoCd() {
		return intBushoCd;
	}
	/**
	 * ����CD �Z�b�^�[
	 * @param _intBushoCd : ����CD�̒l���i�[����
	 */
	public void setIntBushoCd(int _intBushoCd) {
		this.intBushoCd = _intBushoCd;
	}

	/**
	 * �������� �Q�b�^�[
	 * @return strGenryoNm : �������̂̒l��Ԃ�
	 */
	public String getStrGenryoNm() {
		return strGenryoNm;
	}
	/**
	 * �������� �Z�b�^�[
	 * @param _strGenryoNm : �������̂̒l���i�[����
	 */
	public void setStrGenryoNm(String _strGenryoNm) {
		this.strGenryoNm = _strGenryoNm;
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
	 *����  �Q�b�^�[
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
	 * ���ܗL�� �Q�b�^�[
	 * @return dciGanyuritu : ���ܗL���̒l��Ԃ�
	 */
	public BigDecimal getDciGanyuritu() {
		return dciGanyuritu;
	}
	/**
	 * ���ܗL�� �Z�b�^�[
	 * @param _dciGanyuritu : ���ܗL���̒l���i�[����
	 */
	public void setDciGanyuritu(BigDecimal _dciGanyuritu) {
		this.dciGanyuritu = _dciGanyuritu;
	}

	/**
	 * �|�_ �Q�b�^�[
	 * @return dciSakusan : �|�_�̒l��Ԃ�
	 */
	public BigDecimal getDciSakusan() {
		return dciSakusan;
	}
	/**
	 * �|�_ �Z�b�^�[
	 * @param _dciSakusan : �|�_�̒l���i�[����
	 */
	public void setDciSakusan(BigDecimal _dciSakusan) {
		this.dciSakusan = _dciSakusan;
	}

	/**
	 * �H�� �Q�b�^�[
	 * @return dciShokuen : �H���̒l��Ԃ�
	 */
	public BigDecimal getDciShokuen() {
		return dciShokuen;
	}
	/**
	 * �H�� �Z�b�^�[
	 * @param _dciShokuen : �H���̒l���i�[����
	 */
	public void setDciShokuen(BigDecimal _dciShokuen) {
		this.dciShokuen = _dciShokuen;
	}

	/**
	 * ���_ �Q�b�^�[
	 * @return dciSosan : ���_�̒l��Ԃ�
	 */
	public BigDecimal getDciSosan() {
		return dciSosan;
	}
	/**
	 * ���_ �Z�b�^�[
	 * @param _dciSosan : ���_�̒l���i�[����
	 */
	public void setDciSosan(BigDecimal _dciSosan) {
		this.dciSosan = _dciSosan;
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
	 * @return strKosinHi : �̒l��Ԃ�
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
	 * �H���� �Q�b�^�[
	 * @return strKouteiNm : �H�����̒l��Ԃ�
	 */
	public String getStrKouteiNm() {
		return strKouteiNm;
	}
	/**
	 * �H���� �Z�b�^�[
	 * @param _strKouteiNm : �H�����̒l���i�[����
	 */
	public void setStrKouteiNm(String _strKouteiNm) {
		this.strKouteiNm = _strKouteiNm;
	}

	/**
	 * �H������ �Q�b�^�[
	 * @return strKouteiZokusei : �H�������̒l��Ԃ�
	 */
	public String getStrKouteiZokusei() {
		return strKouteiZokusei;
	}
	/**
	 * �H������ �Z�b�^�[
	 * @param _strKouteiZokusei : �H�������̒l���i�[����
	 */
	public void setStrKouteiZokusei(String _strKouteiZokusei) {
		this.strKouteiZokusei = _strKouteiZokusei;
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
//QP@00412_�V�T�N�C�b�N���� No.31
	/**
	 * �}�X�^���� �Q�b�^�[
	 * @return dciMaBudomari : �}�X�^�����̒l��Ԃ�
	 */
	public BigDecimal getDciMaBudomari() {
		return dciMaBudomari;
	}
	/**
	 * �}�X�^���� �Z�b�^�[
	 * @param dciMaBudomari : �}�X�^�����̒l���i�[����
	 */
	public void setDciMaBudomari(BigDecimal dciMaBudomari) {
		this.dciMaBudomari = dciMaBudomari;
	}
//add end   -------------------------------------------------------------------------------
//ADD start 20121002 QP@20505 No.24
	/**
	 * �H�� �Q�b�^�[
	 * @return dciShokuen : �H���̒l��Ԃ�
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * �H�� �Z�b�^�[
	 * @param _dciShokuen : �H���̒l���i�[����
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
//ADD end 20121002 QP@20505 No.24
}