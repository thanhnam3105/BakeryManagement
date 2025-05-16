package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/******************************************************************
 * 
 * ���Z�f�[�^�ێ�
 * @author k-katayama
 * 
 ******************************************************************/
public class ShisanData extends DataBase {

	private BigDecimal dciShisakuUser;		//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;		//����CD-�N
	private BigDecimal dciShisakuNum;		//����CD-�ǔ�
	private int intShisakuSeq;					//����SEQ
	private int intRirekiNo;						//������
	private String strSampleNo;				//�T���v��NO�i���́j
	private String strShisanHi;					//���Z���t
	private BigDecimal dciTorokuId;			//�o�^��ID
	private String strTorokuHi;					//�o�^���t

	/******************************************************************
	 * 
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 * 
	 ******************************************************************/
	public ShisanData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();

		//���Z�f�[�^����������
		this.initShisanData();
				
	}

	/******************************************************************
	 * 
	 * ���Z�����f�[�^����������
	 * 
	 ******************************************************************/
	private void initShisanData() {
		
		this.dciShisakuUser = new BigDecimal(0);
		this.dciShisakuYear = new BigDecimal(0);
		this.dciShisakuNum = new BigDecimal(0);
		this.intShisakuSeq = 0;
		this.intRirekiNo = 0;		
		this.strSampleNo = "";
		this.strShisanHi = "";
		this.dciTorokuId = new BigDecimal(0);
		this.strTorokuHi = "";
		
	}

	/**
	 * @return dciShisakuUser�F ����CD-�Ј�CD��Ԃ�
	 */
	public BigDecimal getDciShisakuUser() {
		return dciShisakuUser;
	}

	/**
	 * @param dciShisakuUser�F ����CD-�Ј�CD���擾����
	 */
	public void setDciShisakuUser(BigDecimal dciShisakuUser) {
		this.dciShisakuUser = dciShisakuUser;
	}

	/**
	 * @return dciShisakuYear�F ����CD-�N��Ԃ�
	 */
	public BigDecimal getDciShisakuYear() {
		return dciShisakuYear;
	}

	/**
	 * @param dciShisakuYear�F ����CD-�N���擾����
	 */
	public void setDciShisakuYear(BigDecimal dciShisakuYear) {
		this.dciShisakuYear = dciShisakuYear;
	}

	/**
	 * @return dciShisakuNum�F ����CD-�ǔԂ�Ԃ�
	 */
	public BigDecimal getDciShisakuNum() {
		return dciShisakuNum;
	}

	/**
	 * @param dciShisakuNum�F ����CD-�ǔԂ��擾����
	 */
	public void setDciShisakuNum(BigDecimal dciShisakuNum) {
		this.dciShisakuNum = dciShisakuNum;
	}

	/**
	 * @return intShisakuSeq�F ����SEQ��Ԃ�
	 */
	public int getIntShisakuSeq() {
		return intShisakuSeq;
	}

	/**
	 * @param intShisakuSeq�F ����SEQ���擾����
	 */
	public void setIntShisakuSeq(int intShisakuSeq) {
		this.intShisakuSeq = intShisakuSeq;
	}

	/**
	 * @return intRirekiNo�F ��������Ԃ�
	 */
	public int getIntRirekiNo() {
		return intRirekiNo;
	}

	/**
	 * @param intRirekiNo�F ���������擾����
	 */
	public void setIntRirekiNo(int intRirekiNo) {
		this.intRirekiNo = intRirekiNo;
	}

	/**
	 * @return strSampleNo�F �T���v��NO�i���́j��Ԃ�
	 */
	public String getStrSampleNo() {
		return strSampleNo;
	}

	/**
	 * @param strSampleNo�F �T���v��NO�i���́j���擾����
	 */
	public void setStrSampleNo(String strSampleNo) {
		this.strSampleNo = strSampleNo;
	}

	/**
	 * @return strShisanHi�F ���Z���t��Ԃ�
	 */
	public String getStrShisanHi() {
		return strShisanHi;
	}

	/**
	 * @param strShisanHi�F ���Z���t���擾����
	 */
	public void setStrShisanHi(String strShisanHi) {
		this.strShisanHi = strShisanHi;
	}

	/**
	 * @return dciTorokuId�F �o�^��ID��Ԃ�
	 */
	public BigDecimal getDciTorokuId() {
		return dciTorokuId;
	}

	/**
	 * @param dciTorokuId�F �o�^��ID���擾����
	 */
	public void setDciTorokuId(BigDecimal dciTorokuId) {
		this.dciTorokuId = dciTorokuId;
	}

	/**
//	 * @return strTorokuHi�F �o�^���t��Ԃ�
	 */
	public String getStrTorokuHi() {
		return strTorokuHi;
	}

	/**
	 * @param strTorokuHi�F �o�^���t���擾����
	 */
	public void setStrTorokuHi(String strTorokuHi) {
		this.strTorokuHi = strTorokuHi;
	}

}
