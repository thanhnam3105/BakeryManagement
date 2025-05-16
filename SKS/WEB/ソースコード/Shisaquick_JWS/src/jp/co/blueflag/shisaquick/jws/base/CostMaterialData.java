package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;
import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ���������f�[�^�ێ�
 *
 */
public class CostMaterialData extends DataBase {

	private BigDecimal dciShisakuUser;			//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;			//����CD-�N
	private BigDecimal dciShisakuNum;			//����CD-�ǔ�
	private int intShisakuSeq;				//����SEQ
	private int intInsatu;					//���flg
	private String strZyutenSui;			//�d�_�ʐ���
	private String strZyutenYu;			//�d�_�ʖ���
	private String strGokei;					//���v��
	private String strGenryohi;				//������
	private String strGenryohiTan;		//������i1�{�j
	private String strHizyu;					//��d
	private String strYoryo;					//�e��
	private String strIrisu;					//����
	private String strYukoBudomari;		//�L������
	private String strLevel;					//���x����
	private String strHizyuBudomari;		//��d����
	private String strZyutenAve;			//���Ϗ[�U��
	private String strGenryohiCs;			//1C/S������
	private String strZairyohiCs;			//1C/S�ޗ���
	private String strKeihiCs;				//1C/S�o��
	private String strGenkakeiCs;			//1C/S�����v
	private String strGenkakeiTan;		//1�����v
	private String strGenkakeiBai;		//1����
	private String strGenkakeiRi;			//1�e����
	private BigDecimal dciTorokuId;					//�o�^��ID
	private String strTorokuHi;				//�o�^���t
	private BigDecimal dciKosinId;					//�X�V��ID
	private String strKosinHi;				//�X�V���t
	private String strTorokuNm;				//�o�^�Җ�
	private String strKosinNm;				//�X�V�Җ�
//	private ExceptionBase ex;				//�G���[�n���h�����O
		
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public CostMaterialData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intInsatu = 0;
		this.strZyutenSui = null;
		this.strZyutenYu = null;
		this.strGokei = null;
		this.strGenryohi = null;
		this.strGenryohiTan = null;
		this.strHizyu = null;
		this.strYoryo = null;
		this.strIrisu = null;
		this.strYukoBudomari = null;
		this.strLevel = null;
		this.strHizyuBudomari = null;
		this.strZyutenAve = null;
		this.strGenryohiCs = null;
		this.strZairyohiCs = null;
		this.strKeihiCs = null;
		this.strGenkakeiCs = null;
		this.strGenkakeiTan = null;
		this.strGenkakeiBai = null;
		this.strGenkakeiRi = null;
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
	 * ���flg �Q�b�^�[
	 * @return intinsatu : ���flg�̒l��Ԃ�
	 */
	public int getIntinsatu() {
		return intInsatu;
	}
	/**
	 * ���flg �Z�b�^�[
	 * @param _intinsatu : ���flg�̒l���i�[����
	 */
	public void setIntinsatu(int _intinsatu) {
		this.intInsatu = _intinsatu;
	}

	/**
	 * �d�_�ʐ��� �Q�b�^�[
	 * @return strZyutenSui : �d�_�ʐ����̒l��Ԃ�
	 */
	public String getStrZyutenSui() {
		return strZyutenSui;
	}
	/**
	 * �d�_�ʐ��� �Z�b�^�[
	 * @param _strZyutenSui : �d�_�ʐ����̒l���i�[����
	 */
	public void setStrZyutenSui(String _strZyutenSui) {
		this.strZyutenSui = _strZyutenSui;
	}

	/**
	 * �d�_�ʖ��� �Q�b�^�[
	 * @return strZyutenYu : �d�_�ʖ����̒l��Ԃ�
	 */
	public String getStrZyutenYu() {
		return strZyutenYu;
	}
	/**
	 * �d�_�ʖ��� �Z�b�^�[
	 * @param _strZyutenYu : �d�_�ʖ����̒l���i�[����
	 */
	public void setStrZyutenYu(String _strZyutenYu) {
		this.strZyutenYu = _strZyutenYu;
	}

	/**
	 * ���v�� �Q�b�^�[
	 * @return strGokei : ���v�ʂ̒l��Ԃ�
	 */
	public String getStrGokei() {
		return strGokei;
	}
	/**
	 * ���v�� �Z�b�^�[
	 * @param _strGokei : ���v�ʂ̒l���i�[����
	 */
	public void setStrGokei(String _strGokei) {
		this.strGokei = _strGokei;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return strGenryohi : ������̒l��Ԃ�
	 */
	public String getStrGenryohi() {
		return strGenryohi;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _strGenryohi : ������̒l���i�[����
	 */
	public void setStrGenryohi(String _strGenryohi) {
		this.strGenryohi = _strGenryohi;
	}

	/**
	 * ������i1�{�j �Q�b�^�[
	 * @return strGenryohiTan : ������i1�{�j�̒l��Ԃ�
	 */
	public String getStrGenryohiTan() {
		return strGenryohiTan;
	}
	/**
	 * ������i1�{�j �Z�b�^�[
	 * @param _strGenryohiTan : ������i1�{�j�̒l���i�[����
	 */
	public void setStrGenryohiTan(String _strGenryohiTan) {
		this.strGenryohiTan = _strGenryohiTan;
	}

	/**
	 * ��d �Q�b�^�[
	 * @return strHizyu : ��d�̒l��Ԃ�
	 */
	public String getStrHizyu() {
		return strHizyu;
	}
	/**
	 * ��d �Z�b�^�[
	 * @param _strHizyu : ��d�̒l���i�[����
	 */
	public void setStrHizyu(String _strHizyu) {
		this.strHizyu = _strHizyu;
	}

	/**
	 * �e�� �Q�b�^�[
	 * @return strYoryo : �e�ʂ̒l��Ԃ�
	 */
	public String getStrYoryo() {
		return strYoryo;
	}
	/**
	 * �e�� �Z�b�^�[
	 * @param _strYoryo : �e�ʂ̒l���i�[����
	 */
	public void setStrYoryo(String _strYoryo) {
		this.strYoryo = _strYoryo;
	}

	/**
	 * ���� �Q�b�^�[
	 * @return strIrisu : �����̒l��Ԃ�
	 */
	public String getStrIrisu() {
		return strIrisu;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _strIrisu : �����̒l���i�[����
	 */
	public void setStrIrisu(String _strIrisu) {
		this.strIrisu = _strIrisu;
	}

	/**
	 * �L������ �Q�b�^�[
	 * @return strYukoBudomari : �L�������̒l��Ԃ�
	 */
	public String getStrYukoBudomari() {
		return strYukoBudomari;
	}
	/**
	 * �L������ �Z�b�^�[
	 * @param _strYukoBudomari : �L�������̒l���i�[����
	 */
	public void setStrYukoBudomari(String _strYukoBudomari) {
		this.strYukoBudomari = _strYukoBudomari;
	}

	/**
	 * ���x���� �Q�b�^�[
	 * @return strLevel : ���x���ʂ̒l��Ԃ�
	 */
	public String getStrLevel() {
		return strLevel;
	}
	/**
	 * ���x���� �Z�b�^�[
	 * @param _strLevel : ���x���ʂ̒l���i�[����
	 */
	public void setStrLevel(String _strLevel) {
		this.strLevel = _strLevel;
	}

	/**
	 * ��d���� �Q�b�^�[
	 * @return strHizyuBudomari : ��d�����̒l��Ԃ�
	 */
	public String getStrHizyuBudomari() {
		return strHizyuBudomari;
	}
	/**
	 * ��d���� �Z�b�^�[
	 * @param _strHizyuBudomari : ��d�����̒l���i�[����
	 */
	public void setStrHizyuBudomari(String _strHizyuBudomari) {
		this.strHizyuBudomari = _strHizyuBudomari;
	}

	/**
	 * ���Ϗ[�U�� �Q�b�^�[
	 * @return strZyutenAve : ���Ϗ[�U�ʂ̒l��Ԃ�
	 */
	public String getStrZyutenAve() {
		return strZyutenAve;
	}
	/**
	 * ���Ϗ[�U�� �Z�b�^�[
	 * @param _strZyutenAve : ���Ϗ[�U�ʂ̒l���i�[����
	 */
	public void setStrZyutenAve(String _strZyutenAve) {
		this.strZyutenAve = _strZyutenAve;
	}

	/**
	 * 1C/S������ �Q�b�^�[
	 * @return strGenryohiCs : 1C/S������̒l��Ԃ�
	 */
	public String getStrGenryohiCs() {
		return strGenryohiCs;
	}
	/**
	 * 1C/S������ �Z�b�^�[
	 * @param _strGenryohiCs : 1C/S������̒l���i�[����
	 */
	public void setStrGenryohiCs(String _strGenryohiCs) {
		this.strGenryohiCs = _strGenryohiCs;
	}

	/**
	 * 1C/S�ޗ��� �Q�b�^�[
	 * @return strZairyohiCs : 1C/S�ޗ���̒l��Ԃ�
	 */
	public String getStrZairyohiCs() {
		return strZairyohiCs;
	}
	/**
	 * 1C/S�ޗ��� �Z�b�^�[
	 * @param _strZairyohiCs : 1C/S�ޗ���̒l���i�[����
	 */
	public void setStrZairyohiCs(String _strZairyohiCs) {
		this.strZairyohiCs = _strZairyohiCs;
	}

	/**
	 * 1C/S�o�� �Q�b�^�[
	 * @return strKeihiCs : 1C/S�o��̒l��Ԃ�
	 */
	public String getStrKeihiCs() {
		return strKeihiCs;
	}
	/**
	 * 1C/S�o�� �Z�b�^�[
	 * @param _strKeihiCs : 1C/S�o��̒l���i�[����
	 */
	public void setStrKeihiCs(String _strKeihiCs) {
		this.strKeihiCs = _strKeihiCs;
	}

	/**
	 * 1C/S�����v �Q�b�^�[
	 * @return strGenkakeiCs : 1C/S�����v�̒l��Ԃ�
	 */
	public String getStrGenkakeiCs() {
		return strGenkakeiCs;
	}
	/**
	 * 1C/S�����v �Z�b�^�[
	 * @param _strGenkakeiCs : 1C/S�����v�̒l���i�[����
	 */
	public void setStrGenkakeiCs(String _strGenkakeiCs) {
		this.strGenkakeiCs = _strGenkakeiCs;
	}

	/**
	 * 1�����v �Q�b�^�[
	 * @return strGenkakeiTan : 1�����v�̒l��Ԃ�
	 */
	public String getStrGenkakeiTan() {
		return strGenkakeiTan;
	}
	/**
	 * 1�����v �Z�b�^�[
	 * @param _strGenkakeiTan : 1�����v�̒l���i�[����
	 */
	public void setStrGenkakeiTan(String _strGenkakeiTan) {
		this.strGenkakeiTan = _strGenkakeiTan;
	}

	/**
	 * 1���� �Q�b�^�[
	 * @return strGenkakeiBai : 1�����̒l��Ԃ�
	 */
	public String getStrGenkakeiBai() {
		return strGenkakeiBai;
	}
	/**
	 * 1���� �Z�b�^�[
	 * @param _strGenkakeiBai : 1�����̒l���i�[����
	 */
	public void setStrGenkakeiBai(String _strGenkakeiBai) {
		this.strGenkakeiBai = _strGenkakeiBai;
	}

	/**
	 * 1�e���� �Q�b�^�[
	 * @return strGenkakeiRi : 1�e�����̒l��Ԃ�
	 */
	public String getStrGenkakeiRi() {
		return strGenkakeiRi;
	}
	/**
	 * 1�e���� �Z�b�^�[
	 * @param _strGenkakeiRi : 1�e�����̒l���i�[����
	 */
	public void setStrGenkakeiRi(String _strGenkakeiRi) {
		this.strGenkakeiRi = _strGenkakeiRi;
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
