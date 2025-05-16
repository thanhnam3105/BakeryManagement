package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * �����f�[�^�ێ�
 *  : �����f�[�^�Ɋւ�������Ǘ�����
 *
 */
public class MaterialData extends DataBase {

	private int intKaishacd;			//��ЃR�[�h
	private int intBushocd;				//�����R�[�h
	private String strGenryocd;			//�����R�[�h
	private String strGenryonm;			//������
	private String strKaishanm;			//��Ж�
	private String strBushonm;			//������
	private BigDecimal dciSakusan;		//�|�_
	private BigDecimal dciShokuen;		//�H��
	private BigDecimal dciSousan;		//���_
	private BigDecimal dciGanyu;		//���ܗL��
	private String strHyoji;			//�\����
	private String strTenka;			//�Y����
	private String strMemo;				//����
	private String strEiyono1;			//�h�{�v�Z�H�i�ԍ��P
	private String strEiyono2;			//�h�{�v�Z�H�i�ԍ��Q
	private String strEiyono3;			//�h�{�v�Z�H�i�ԍ��R
	private String strEiyono4;			//�h�{�v�Z�H�i�ԍ��S
	private String strEiyono5;			//�h�{�v�Z�H�i�ԍ��T
	private String strWariai1;			//�����P
	private String strWariai2;			//�����Q
	private String strWariai3;			//�����R
	private String strWariai4;			//�����S
	private String strWariai5;			//�����T
	private String strNyurokuhi;		//���͓�
	private BigDecimal dciNyurokucd;	//���͎҃R�[�h
	private String strNyurokunm;		//���͎Җ�
	private int intKakunin;				//���͏��m�F�t���O
	private String strKakuninhi;		//�m�F��
	private BigDecimal dciKakunincd;	//�m�F�҃R�[�h
	private String strKakuninnm;		//�m�F�Җ�
	private int intHaisicd;				//�p�~�t���O
	private String strkakuteicd;		//�m��R�[�h
	private String strKonyu;			//�ŏI�w����
	private BigDecimal dciTanka;		//�P��
	private BigDecimal dciBudomari;		//����
	private String strNisugata;			//�׎p
	private BigDecimal dciTorokuId;		//�o�^��ID
	private String strTorokuDt;			//�o�^���t
	private String strTorokuNm;			//�o�^�Җ�
	private BigDecimal dciKosinId;		//�X�V��ID
	private String strKosinDt;			//�X�V���t
	private String strKosinNm;			//�X�V�Җ�
	private int intListRowMax;			//�\���ő匟��
	private int intMaxRow;				//���R�[�h����

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	private int intShiyoFlg;			//�g�p���уt���O
	private int intMishiyoFlg;			//���g�p�t���O
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
	private BigDecimal ma_dciBudomari;		//����
//add end --------------------------------------------------------------------------------------

	private int intHankou;				//���͒l���ق̗L��
	private XmlData xdtData;			//XML�f�[�^
// ADD start 20121002 QP@20505 No.24
	private BigDecimal dciMsg;		//�l�r�f
// ADD end 20121002 QP@20505 No.24


	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	 public MaterialData() {
		 //�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		 super();

		 this.intKaishacd = -1;
		 this.intBushocd = -1;
		 this.strGenryocd = null;
		 this.strKaishanm = null;
		 this.strBushonm = null;
		 this.strGenryonm = null;
		 this.dciSakusan = null;
		 this.dciShokuen = null;
		 this.dciSousan = null;
		 this.dciGanyu = null;
		 this.strHyoji = null;
		 this.strTenka = null;
		 this.strMemo = null;
		 this.strEiyono1 = null;
		 this.strEiyono2 = null;
		 this.strEiyono3 = null;
		 this.strEiyono4 = null;
		 this.strEiyono5 = null;
		 this.strWariai1 = null;
		 this.strWariai2 = null;
		 this.strWariai3 = null;
		 this.strWariai4 = null;
		 this.strWariai5 = null;

		 this.intHaisicd = 0;
		 this.strkakuteicd = null;
		 this.strKakuninhi = null;
		 this.dciKakunincd = null;
		 this.strKakuninnm = null;

		 this.strNyurokuhi = null;
		 this.dciNyurokucd = null;
		 this.strNyurokunm = null;

		 this.intKakunin = 0;
		 this.strKonyu = null;
		 this.dciTanka = null;
		 this.dciBudomari = null;
		 this.strNisugata = null;

		 this.dciTorokuId = null;
		 this.strTorokuDt = null;
		 this.strTorokuNm = null;
		 this.dciKosinId = null;
		 this.strKosinDt = null;
		 this.strKosinNm = null;
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
		 this.intShiyoFlg = 0;
		 this.intMishiyoFlg = 0;
//add end --------------------------------------------------------------------------------------
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
		 this.ma_dciBudomari = null;
//add end --------------------------------------------------------------------------------------
		 this.intHankou = 0;
// ADD start 20121002 QP@20505 No.24
		 this.dciMsg = null;
// ADD end 20121002 QP@20505 No.24

	 }


	/**
	 * ��ЃR�[�h �Q�b�^�[
	 * @return intKaishacd : ��ЃR�[�h�̒l��Ԃ�
	 */
	public int getIntKaishacd() {
		return intKaishacd;
	}
	/**
	 * ��ЃR�[�h �Z�b�^�[
	 * @param _intKaishacd : ��ЃR�[�h�̒l���i�[����
	 */
	public void setIntKaishacd(int _intKaishacd) {
		this.intKaishacd = _intKaishacd;
	}

	/**
	 * �����R�[�h  �Q�b�^�[
	 * @return intBushocd : �����R�[�h�̒l��Ԃ�
	 */
	public int getIntBushocd() {
		return intBushocd;
	}
	/**
	 * �����R�[�h �Z�b�^�[
	 * @param _intBushocd : �����R�[�h�̒l���i�[����
	 */
	public void setIntBushocd(int _intBushocd) {
		this.intBushocd = _intBushocd;
	}

	/**
	 * �����R�[�h  �Q�b�^�[
	 * @return strGenryocd : �����R�[�h�̒l��Ԃ�
	 */
	public String getStrGenryocd() {
		return strGenryocd;
	}
	/**
	 * �����R�[�h �Z�b�^�[
	 * @param _strGenryocd : �����R�[�h�̒l���i�[����
	 */
	public void setStrGenryocd(String _strGenryocd) {
		this.strGenryocd = _strGenryocd;
	}

	/**
	 * ������  �Q�b�^�[
	 * @return strGenryonm : �������̒l��Ԃ�
	 */
	public String getStrGenryonm() {
		return strGenryonm;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _strGenryonm : �������̒l���i�[����
	 */
	public void setStrGenryonm(String _strGenryonm) {
		this.strGenryonm = _strGenryonm;
	}

	/**
	 * ��Ж� �Q�b�^�[
	 * @return strKaishanm : ��Ж��̒l��Ԃ�
	 */
	public String getStrKaishanm() {
		return strKaishanm;
	}
	/**
	 * ��Ж� �Z�b�^�[
	 * @param _strKaishanm : ��Ж��̒l���i�[����
	 */
	public void setStrKaishanm(String _strKaishanm) {
		this.strKaishanm = _strKaishanm;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return strBushonm : �������̒l��Ԃ�
	 */
	public String getStrBushonm() {
		return strBushonm;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _strBushonm : �������̒l���i�[����
	 */
	public void setStrBushonm(String _strBushonm) {
		this.strBushonm = _strBushonm;
	}

	/**
	 * �|�_  �Q�b�^�[
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
	 * �H��  �Q�b�^�[
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

// ADD start 20121002 QP@20505 No.24
	/**
	 * �l�r�f  �Q�b�^�[
	 * @return dciMsg : �l�r�f�̒l��Ԃ�
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * �l�r�f �Z�b�^�[
	 * @param _dciShokuen : �H���̒l���i�[����
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
// ADD end 20121002 QP@20505 No.24

	/**
	 * ���_  �Q�b�^�[
	 * @return dciSousan : ���_�̒l��Ԃ�
	 */
	public BigDecimal getDciSousan() {
		return dciSousan;
	}
	/**
	 * ���_ �Z�b�^�[
	 * @param _dciSousan : ���_�̒l���i�[����
	 */
	public void setDciSousan(BigDecimal _dciSousan) {
		this.dciSousan = _dciSousan;
	}

	/**
	 * ���ܗL��  �Q�b�^�[
	 * @return intGanyu : ���ܗL���̒l��Ԃ�
	 */
	public BigDecimal getDciGanyu() {
		return dciGanyu;
	}
	/**
	 * ���ܗL�� �Z�b�^�[
	 * @param _dciGanyu : ���ܗL���̒l���i�[����
	 */
	public void setDciGanyu(BigDecimal _dciGanyu) {
		this.dciGanyu = _dciGanyu;
	}

	/**
	 * �\����  �Q�b�^�[
	 * @return strHyoji : �\���Ă̒l��Ԃ�
	 */
	public String getStrHyoji() {
		return strHyoji;
	}
	/**
	 * �\���� �Z�b�^�[
	 * @param _strHyoji : �\���Ă̒l���i�[����
	 */
	public void setStrHyoji(String _strHyoji) {
		this.strHyoji = _strHyoji;
	}

	/**
	 * �Y����  �Q�b�^�[
	 * @return strTenka : �Y�����̒l��Ԃ�
	 */
	public String getStrTenka() {
		return strTenka;
	}
	/**
	 * �Y���� �Z�b�^�[
	 * @param _strTenka : �Y�����̒l���i�[����
	 */
	public void setStrTenka(String _strTenka) {
		this.strTenka = _strTenka;
	}

	/**
	 * �h�{�v�Z�H�i�ԍ�1  �Q�b�^�[
	 * @return strEiyono1 : �h�{�v�Z�H�i�ԍ�1�̒l��Ԃ�
	 */
	public String getStrEiyono1() {
		return strEiyono1;
	}
	/**
	 * �h�{�v�Z�H�i�ԍ�1 �Z�b�^�[
	 * @param _strEiyono1 : �h�{�v�Z�H�i�ԍ�1�̒l���i�[����
	 */
	public void setStrEiyono1(String _strEiyono1) {
		this.strEiyono1 = _strEiyono1;
	}

	/**
	 * �h�{�v�Z�H�i�ԍ�2  �Q�b�^�[
	 * @return strEiyono2 : �h�{�v�Z�H�i�ԍ�2�̒l��Ԃ�
	 */
	public String getStrEiyono2() {
		return strEiyono2;
	}
	/**
	 * �h�{�v�Z�H�i�ԍ�2 �Z�b�^�[
	 * @param _strEiyono2 : �h�{�v�Z�H�i�ԍ�3�̒l���i�[����
	 */
	public void setStrEiyono2(String _strEiyono2) {
		this.strEiyono2 = _strEiyono2;
	}

	/**
	 * �h�{�v�Z�H�i�ԍ�3  �Q�b�^�[
	 * @return strEiyono3 : �h�{�v�Z�H�i�ԍ�3�̒l��Ԃ�
	 */
	public String getStrEiyono3() {
		return strEiyono3;
	}
	/**
	 * �h�{�v�Z�H�i�ԍ�3 �Z�b�^�[
	 * @param _strEiyono3 : �h�{�v�Z�H�i�ԍ�3�̒l���i�[����
	 */
	public void setStrEiyono3(String _strEiyono3) {
		this.strEiyono3 = _strEiyono3;
	}

	/**
	 * �h�{�v�Z�H�i�ԍ�4  �Q�b�^�[
	 * @return strEiyono4 : �h�{�v�Z�H�i�ԍ�4�̒l��Ԃ�
	 */
	public String getStrEiyono4() {
		return strEiyono4;
	}
	/**
	 * �h�{�v�Z�H�i�ԍ�4 �Z�b�^�[
	 * @param _strEiyono4 : �h�{�v�Z�H�i�ԍ�4�̒l���i�[����
	 */
	public void setStrEiyono4(String _strEiyono4) {
		this.strEiyono4 = _strEiyono4;
	}

	/**
	 * �h�{�v�Z�H�i�ԍ�5  �Q�b�^�[
	 * @return strEiyono5 : �h�{�v�Z�H�i�ԍ�5�̒l��Ԃ�
	 */
	public String getStrEiyono5() {
		return strEiyono5;
	}
	/**
	 * �h�{�v�Z�H�i�ԍ�5 �Z�b�^�[
	 * @param _strEiyono5 : �h�{�v�Z�H�i�ԍ�5�̒l���i�[����
	 */
	public void setStrEiyono5(String _strEiyono5) {
		this.strEiyono5 = _strEiyono5;
	}

	/**
	 * ����1  �Q�b�^�[
	 * @return strWariai1 : ����1�̒l��Ԃ�
	 */
	public String getStrWariai1() {
		return strWariai1;
	}
	/**
	 * ����1 �Z�b�^�[
	 * @param _strWariai1 : ����1�̒l���i�[����
	 */
	public void setStrWariai1(String _strWariai1) {
		this.strWariai1 = _strWariai1;
	}

	/**
	 * ����2  �Q�b�^�[
	 * @return strWariai2 : ����2�̒l��Ԃ�
	 */
	public String getStrWariai2() {
		return strWariai2;
	}
	/**
	 * ����2 �Z�b�^�[
	 * @param _strWariai2 : ����2�̒l���i�[����
	 */
	public void setStrWariai2(String _strWariai2) {
		this.strWariai2 = _strWariai2;
	}

	/**
	 * ����3  �Q�b�^�[
	 * @return strWariai3 : ����3�̒l��Ԃ�
	 */
	public String getStrWariai3() {
		return strWariai3;
	}
	/**
	 * ����3 �Z�b�^�[
	 * @param _strWariai3 : ����3�̒l���i�[����
	 */
	public void setStrWariai3(String _strWariai3) {
		this.strWariai3 = _strWariai3;
	}

	/**
	 * ����4  �Q�b�^�[
	 * @return strWariai4 : ����4�̒l��Ԃ�
	 */
	public String getStrWariai4() {
		return strWariai4;
	}
	/**
	 * ����4 �Z�b�^�[
	 * @param _strWariai4 : ����4�̒l���i�[����
	 */
	public void setStrWariai4(String _strWariai4) {
		this.strWariai4 = _strWariai4;
	}

	/**
	 * ����5  �Q�b�^�[
	 * @return strWariai5 : ����5�̒l��Ԃ�
	 */
	public String getStrWariai5() {
		return strWariai5;
	}
	/**
	 * ����5 �Z�b�^�[
	 * @param _strWariai5 : ����5�̒l���i�[����
	 */
	public void setStrWariai5(String _strWariai5) {
		this.strWariai5 = _strWariai5;
	}

	/**
	 * ����  �Q�b�^�[
	 * @return strMemo : �����̒l��Ԃ�
	 */
	public String getStrMemo() {
		return strMemo;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _strMemo : �����̒l���i�[����
	 */
	public void setStrMemo(String _strMemo) {
		this.strMemo = _strMemo;
	}

	/**
	 * ���͓�  �Q�b�^�[
	 * @return strNyurokuhi : ���͓��̒l��Ԃ�
	 */
	public String getStrNyurokuhi() {
		return strNyurokuhi;
	}
	/**
	 * ���͓� �Z�b�^�[
	 * @param _strNyurokuhi : ���͓��̒l���i�[����
	 */
	public void setStrNyurokuhi(String _strNyurokuhi) {
		this.strNyurokuhi = _strNyurokuhi;
	}

	/**
	 * ���͎҃R�[�h �Q�b�^�[
	 * @return dciNyurokucd : ���͎҃R�[�h�̒l��Ԃ�
	 */
	public BigDecimal getDciNyurokucd() {
		return dciNyurokucd;
	}
	/**
	 * ���͎҃R�[�h �Z�b�^�[
	 * @param _dciNyurokucd : ���͎҃R�[�h�̒l���i�[����
	 */
	public void setDciNyurokucd(BigDecimal _dciNyurokucd) {
		this.dciNyurokucd = _dciNyurokucd;
	}

	/**
	 * ���͎Җ� �Q�b�^�[
	 * @return strNyurokunm : ���͎Җ��̒l��Ԃ�
	 */
	public String getStrNyurokunm() {
		return strNyurokunm;
	}
	/**
	 * ���͎Җ� �Z�b�^�[
	 * @param _strNyurokunm : ���͎Җ��̒l���i�[����
	 */
	public void setStrNyurokunm(String _strNyurokunm) {
		this.strNyurokunm = _strNyurokunm;
	}

	/**
	 * ���͏��m�F�t���O �Q�b�^�[
	 * @return intKakunin : ���͏��m�F�t���O�̒l��Ԃ�
	 */
	public int getIntKakunin() {
		return intKakunin;
	}
	/**
	 * ���͏��m�F�t���O �Z�b�^�[
	 * @param _intKakunin : ���͏��m�F�t���O�̒l���i�[����
	 */
	public void setIntKakunin(int _intKakunin) {
		this.intKakunin = _intKakunin;
	}

	/**
	 * �m�F�� �Q�b�^�[
	 * @return strKakuninhi : �m�F���̒l��Ԃ�
	 */
	public String getStrKakuninhi() {
		return strKakuninhi;
	}
	/**
	 * �m�F�� �Z�b�^�[
	 * @param _strKakuninhi : �m�F���̒l���i�[����
	 */
	public void setStrKakuninhi(String _strKakuninhi) {
		this.strKakuninhi = _strKakuninhi;
	}

	/**
	 * �m�F�҃R�[�h �Q�b�^�[
	 * @return dciKakunincd : �m�F�҃R�[�h�̒l��Ԃ�
	 */
	public BigDecimal getDciKakunincd() {
		return dciKakunincd;
	}
	/**
	 * �m�F�҃R�[�h �Z�b�^�[
	 * @param _dciKakunincd : �m�F�҃R�[�h�̒l���i�[����
	 */
	public void setDciKakunincd(BigDecimal _dciKakunincd) {
		this.dciKakunincd = _dciKakunincd;
	}

	/**
	 * �m�F�Җ� �Q�b�^�[
	 * @return strKakuninnm : �m�F�Җ��̒l��Ԃ�
	 */
	public String getStrKakuninnm() {
		return strKakuninnm;
	}
	/**
	 * �m�F�Җ� �Z�b�^�[
	 * @param _strKakuninnm : �m�F�Җ��̒l���i�[����
	 */
	public void setStrKakuninnm(String _strKakuninnm) {
		this.strKakuninnm = _strKakuninnm;
	}

	/**
	 * �p�~�t���O �Q�b�^�[
	 * @return intHaisicd : �p�~�t���O�̒l��Ԃ�
	 */
	public int getIntHaisicd() {
		return intHaisicd;
	}
	/**
	 * �p�~�t���O �Z�b�^�[
	 * @param _intHaisicd : �p�~�t���O�̒l���i�[����
	 */
	public void setIntHaisicd(int _intHaisicd) {
		this.intHaisicd = _intHaisicd;
	}

	/**
	 * �m��R�[�h �Q�b�^�[
	 * @return strkakuteicd : �m��R�[�h�̒l��Ԃ�
	 */
	public String getStrkakuteicd() {
		return strkakuteicd;
	}
	/**
	 * �m��R�[�h �Z�b�^�[
	 * @param _strkakuteicd : �m��R�[�h�̒l���i�[����
	 */
	public void setStrkakuteicd(String _strkakuteicd) {
		this.strkakuteicd = _strkakuteicd;
	}

	/**
	 * �ŏI�w���� �Q�b�^�[
	 * @return strKonyu : �ŏI�w�����̒l��Ԃ�
	 */
	public String getStrKonyu() {
		return strKonyu;
	}
	/**
	 * �ŏI�w���� �Z�b�^�[
	 * @param _strKonyu : �ŏI�w�����̒l���i�[����
	 */
	public void setStrKonyu(String _strKonyu) {
		this.strKonyu = _strKonyu;
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
	 * �׎p �Q�b�^�[
	 * @return strNisugata : �׎p�̒l��Ԃ�
	 */
	public String getStrNisugata() {
		return strNisugata;
	}
	/**
	 * �׎p �Z�b�^�[
	 * @param _strNisugata : �׎p�̒l���i�[����
	 */
	public void setStrNisugata(String _strNisugata) {
		this.strNisugata = _strNisugata;
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
	 * @return strTorokuDt : �o�^���t�̒l��Ԃ�
	 */
	public String getStrTorokuDt() {
		return strTorokuDt;
	}
	/**
	 * �o�^���t �Z�b�^�[
	 * @param _strTorokuDt : �o�^���t�̒l���i�[����
	 */
	public void setStrTorokuDt(String _strTorokuDt) {
		this.strTorokuDt = _strTorokuDt;
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
	 * @return strKosinDt : �X�V���t�̒l��Ԃ�
	 */
	public String getStrKosinDt() {
		return strKosinDt;
	}
	/**
	 * �X�V���t �Z�b�^�[
	 * @param _strKosinDt : �X�V���t�̒l���i�[����
	 */
	public void setStrKosinDt(String _strKosinDt) {
		this.strKosinDt = _strKosinDt;
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

	/**
	 * �\���ő匏�� �Q�b�^�[
	 * @return intListRowMax : �\���ő匏���̒l��Ԃ�
	 */
	public int getIntListRowMax() {
		return intListRowMax;
	}
	/**
	 * �\���ő匏�� �Z�b�^�[
	 * @param _intListRowMax : �\���ő匏���̒l���i�[����
	 */
	public void setIntListRowMax(int _intListRowMax) {
		this.intListRowMax = _intListRowMax;
	}

	/**
	 * ���R�[�h���� �Q�b�^�[
	 * @return intMaxRow : ���R�[�h�����̒l��Ԃ�
	 */
	public int getIntMaxRow() {
		return intMaxRow;
	}
	/**
	 * ���R�[�h���� �Z�b�^�[
	 * @param _intMaxRow : ���R�[�h�����̒l���i�[����
	 */
	public void setIntMaxRow(int _intMaxRow) {
		this.intMaxRow = _intMaxRow;
	}

	/**
	 * ���͒l���ق̗L�� �Q�b�^�[
	 * @return intHankou : ���͒l���ق̗L���̒l��Ԃ�
	 */
	public int getIntHankou() {
		return intHankou;
	}
	/**
	 * ���͒l���ق̗L�� �Z�b�^�[
	 * @param _intHankou : ���͒l���ق̗L���̒l���i�[����
	 */
	public void setIntHankou(int _intHankou) {
		this.intHankou = _intHankou;
	}

	/**
	 * XML�f�[�^ �Z�b�^�[
	 * @param _xdtData : XML�f�[�^�̒l���i�[����
	 */
	public void setXdtData(XmlData _xdtData) {
		this.xdtData = _xdtData;
	}
	/**
	 * XML�f�[�^ �Q�b�^�[
	 * @return xdtData : XML�f�[�^�̒l��Ԃ�
	 */
	public XmlData getXdtData() {
		return xdtData;
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
	/**
	 * �g�p���уt���O �Q�b�^�[
	 * @return intShiyoFlg : �g�p���уt���O�̒l��Ԃ�
	 */
	public int getIntShiyoFlg() {
		return intShiyoFlg;
	}
	/**
	 * �g�p���уt���O �Z�b�^�[
	 * @param _intShiyoFlg : �g�p���уt���O�̒l���i�[����
	 */
	public void setIntShiyoFlg(int _intShiyoFlg) {
		this.intShiyoFlg = _intShiyoFlg;
	}

	/**
	 *�@���g�p�t���O �Q�b�^�[
	 * @return intMishiyoFlg : ���g�p�t���O�̒l��Ԃ�
	 */
	public int getIntMishiyoFlg() {
		return intMishiyoFlg;
	}
	/**
	 * ���g�p�t���O �Z�b�^�[
	 * @param _intMishiyoFlg : ���g�p�t���O�̒l���i�[����
	 */
	public void setIntMishiyoFlg(int _intMishiyoFlg) {
		this.intMishiyoFlg = _intMishiyoFlg;
	}
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
	public BigDecimal getMa_dciBudomari() {
		return ma_dciBudomari;
	}
	public void setMa_dciBudomari(BigDecimal ma_dciBudomari) {
		this.ma_dciBudomari = ma_dciBudomari;
	}
//add end --------------------------------------------------------------------------------------

	public void dispMateData(){
		//�@��ЃR�[�h
		System.out.println("��ЃR�[�h�F" + this.getIntKaishacd());
		//�@�����R�[�h
		System.out.println("�����R�[�h�F" + this.getIntBushocd());
		//�@�����R�[�h
		System.out.println("�����R�[�h�F" + this.getStrGenryocd());
		//�@��Ж�
		System.out.println("��Ж��F" + this.getStrKaishanm());
		//�@������
		System.out.println("�������F" + this.getStrBushonm());
		//�@������
		System.out.println("�������F" + this.getStrGenryonm());
		//�@�|�_
		System.out.println("�|�_�F" + this.getDciSakusan());
		//�@�H��
		System.out.println("�H���F" + this.getDciShokuen());
		//�@���_
		System.out.println("���_�F" + this.getDciSousan());
		//�@���ܗL��
		System.out.println("���ܗL���F" + this.getDciGanyu());
		//�@�\����
		System.out.println("�\���āF" + this.getStrHyoji());
		//�@�Y����
		System.out.println("�Y�����F" + this.getStrTenka());
		//�@�h�{�v�Z�H�i�ԍ�1
		System.out.println("�h�{�v�Z�H�i�ԍ�1�F" + this.getStrEiyono1());
		//�@�h�{�v�Z�H�i�ԍ�2
		System.out.println("�h�{�v�Z�H�i�ԍ�2�F" + this.getStrEiyono2());
		//�@�h�{�v�Z�H�i�ԍ�3
		System.out.println("�h�{�v�Z�H�i�ԍ�3�F" + this.getStrEiyono3());
		//�@�h�{�v�Z�H�i�ԍ�4
		System.out.println("�h�{�v�Z�H�i�ԍ�4�F" + this.getStrEiyono4());
		//�@�h�{�v�Z�H�i�ԍ�5
		System.out.println("�h�{�v�Z�H�i�ԍ�5�F" + this.getStrEiyono5());
		//�@����1
		System.out.println("����1�F" + this.getStrWariai1());
		//�@����2
		System.out.println("����2�F" + this.getStrWariai2());
		//�@����3
		System.out.println("����3�F" + this.getStrWariai3());
		//�@����4
		System.out.println("����4�F" + this.getStrWariai4());
		//�@����5
		System.out.println("����5�F" + this.getStrWariai5());
		//�@����
		System.out.println("�����F" + this.getStrMemo());
		//�@���͓�
		System.out.println("���͓��F" + this.getStrNyurokuhi());
		//�@���͎�ID
		System.out.println("���͎�ID�F" + this.getDciNyurokucd());
		//�@���͎Җ�
		System.out.println("���͎Җ��F" + this.getStrNyurokunm());
		//�@�m�F��
		System.out.println("�m�F���F" + this.getStrKakuninhi());
		//�@�m�F��ID
		System.out.println("�m�F��ID�F" + this.getDciKakunincd());
		//�@�m�F�Җ�
		System.out.println("�m�F�Җ��F" + this.getStrKakuninnm());
		//�@�p�~�敪
		System.out.println("�p�~�敪�F" + this.getIntHaisicd());
		//�@�m��R�[�h
		System.out.println("�m��R�[�h�F" + this.getStrkakuteicd());
		//�@�ŏI�w����
		System.out.println("�ŏI�w�����F" + this.getStrKonyu());
		//�@�P��
		System.out.println("�P���F" + this.getDciTanka());
		//�@����
		System.out.println("�����F" + this.getDciBudomari());
		//�@�׎p
		System.out.println("�׎p�F" + this.getStrNisugata());
		//�@�o�^��ID
		System.out.println("�o�^��ID�F" + this.getDciTorokuId());
		//�@�o�^���t
		System.out.println("�o�^���t�F" + this.getStrTorokuDt());
		//�@�o�^�Җ�
		System.out.println("�o�^�Җ��F" + this.getStrTorokuNm());
		//�@�X�V��ID
		System.out.println("�X�V��ID�F" + this.getDciKosinId());
		//�@�X�V���t
		System.out.println("�X�V���t�F" + this.getStrKosinDt());
		//�@�X�V�Җ�
		System.out.println("�X�V�Җ��F" + this.getStrKosinNm());
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
		//�@�g�p���уt���O
		System.out.println("�g�p���уt���O�F" + this.getIntShiyoFlg());
		//�@���g�p�t���O
		System.out.println("���g�p�t���O�F" + this.getIntMishiyoFlg());
//add end --------------------------------------------------------------------------------------
	}

}
