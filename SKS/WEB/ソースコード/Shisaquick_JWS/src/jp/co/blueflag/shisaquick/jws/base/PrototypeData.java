package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ����i�f�[�^�ێ�
 *
 */
public class PrototypeData extends DataBase {

	private BigDecimal dciShisakuUser;		//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;		//����CD-�N
	private BigDecimal dciShisakuNum;		//����CD-�ǔ�
	private String strIrai;						//�˗��ԍ�
	private String strHinnm;					//�i��
	private int intKaishacd;						//�w��H��-���CD
	private int intKojoco;						//�w��H��-�H��CD
	private String strShubetu;					//���CD
	private String strShubetuNo;				//���No
	private int intGroupcd;						//�O���[�vCD
	private int intTeamcd;						//�`�[��CD
	private String strGroupNm;					//�O���[�v��
	private String strTeamNm;					//�`�[����
	private String strIkatu;						//�ꊇ�\��CD
	private String strZyanru;					//�W������CD
	private String strUsercd;					//���[�UCD
	private String strTokutyo;					//��������
	private String strYoto;						//�p�r
	private String strKakaku;					//���i��CD
	private String strTantoEigyo;				//�S���c��CD
	private String strSeizocd;					//�������@CD
	private String strZyutencd;					//�[�U���@CD
	private String strSakin;						//�E�ە��@
	private String strYokihozai;				//�e��E���
	private String strYoryo;						//�e��
	private String strTani;						//�e�ʒP��CD
	private String strIrisu;						//���萔
	private String strOndo;						//�戵���xCD
	private String strShomi;						//�ܖ�����
	private String strGenka;					//����
	private String strBaika;						//����
	private String strSotei;						//�z�蕨��
	private String strHatubai;					//��������
	private String strKeikakuUri;				//�v�攄��
	private String strKeikakuRie;				//�v�旘�v
	private String strHanbaigoUri;				//�̔��㔄��
	private String strHanbaigoRie;			//�̔��㗘�v
	private String strNishugata;				//�׎pCD
	private String strSogo;						//������
	private String strShosu;						//�����w��
	private int intHaisi;							//�p�~��
	private BigDecimal dciHaita;				//�r��
	private int intSeihoShisaku;				//���@����
	private String strShisakuMemo;			//���상��
	private int intChuiFg;						//���ӎ����\��
	private BigDecimal dciTorokuid;			//�o�^��ID
	private String strTorokuhi;					//�o�^���t
	private BigDecimal dciKosinId;			//�X�V��ID
	private String strKosinhi;					//�X�V���t
	private String strTorokuNm;				//�o�^�Җ�
	private String strKosinNm;					//�X�V�Җ�
	//2010/02/25 NAKAMURA ADD START-----------------------
	private String strHaitaKaishaNm;			//�r����Ж�
	private String strHaitaBushoNm;			//�r��������
	private String strHaitaShimei;				//�r������
	//2010/02/25 NAKAMURA ADD END-------------------------
	
	//�yQP@00342�z
	private String strNmEigyoTanto;			//�r������
	
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	private String strPt_kotei;					//�H���p�^�[��
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
	
	//�yQP@20505_No.38�z2012/10/17 TT.SHIMA ADD Start
	private String strSecret;					//�V�[�N���b�g���[�h�t���O
	//�yQP@20505_No.38�z2012/10/17 TT.SHIMA ADD End
	
//	private ExceptionBase ex;					//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public PrototypeData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.strIrai = null;
		this.strHinnm = null;
		this.intKaishacd = -1;
		this.intKojoco = -1;
		this.strShubetu = null;
		this.strShubetuNo = null;
		this.intGroupcd = -1;
		this.intTeamcd = -1;
		this.strIkatu = null;
		this.strZyanru = null;
		this.strUsercd = null;
		this.strTokutyo = null;
		this.strYoto = null;
		this.strKakaku = null;
		this.strTantoEigyo = null;
		this.strSeizocd = null;
		this.strZyutencd = null;
		this.strSakin = null;
		this.strYokihozai = null;
		this.strYoryo = null;
		this.strTani = null;
		this.strIrisu = null;
		this.strOndo = null;
		this.strShomi = null;
		this.strGenka = null;
		this.strBaika = null;
		this.strSotei = null;
		this.strHatubai = null;
		this.strKeikakuUri = null;
		this.strKeikakuRie = null;
		this.strHanbaigoUri = null;
		this.strHanbaigoRie = null;
		this.strNishugata = null;
		this.strSogo = null;
		this.strShosu = null;
		this.intHaisi = 0;
		this.dciHaita = null;
		this.intSeihoShisaku = 0;
		this.strShisakuMemo = null;
		this.intChuiFg = 0;
		this.dciTorokuid = null;
		this.strTorokuhi = null;
		this.strTorokuNm = null;
		this.dciKosinId = null;
		this.strKosinhi = null;
		this.strKosinNm = null;
		//2010/02/25 NAKAMURA ADD START-----------
		this.strHaitaKaishaNm = null;
		this.strHaitaBushoNm = null;
		this.strHaitaShimei = null;
		//2010/02/25 NAKAMURA ADD END-------------
		
		//�yQP@00342�z
		this.strNmEigyoTanto = null;
		
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
		this.strPt_kotei = null;
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
		
		//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
		this.strSecret = null;
		//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
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
	 * �˗��ԍ� �Q�b�^�[
	 * @return strIrai : �˗��ԍ��̒l��Ԃ�
	 */
	public String getStrIrai() {
		return strIrai;
	}
	/**
	 * �˗��ԍ� �Z�b�^�[
	 * @param _strIrai : �˗��ԍ��̒l���i�[����
	 */
	public void setStrIrai(String _strIrai) {
		this.strIrai = _strIrai;
	}

	/**
	 * �i�� �Q�b�^�[
	 * @return strHinnm : �i���̒l��Ԃ�
	 */
	public String getStrHinnm() {
		return strHinnm;
	}
	/**
	 * �i�� �Z�b�^�[
	 * @param _strHinnm : �i���̒l���i�[����
	 */
	public void setStrHinnm(String _strHinnm) {
		this.strHinnm = _strHinnm;
	}

	/**
	 * �w��H��-���CD �Q�b�^�[
	 * @return intKaishacd : �w��H��-���CD�̒l��Ԃ�
	 */
	public int getIntKaishacd() {
		return intKaishacd;
	}
	/**
	 * �w��H��-���CD �Z�b�^�[
	 * @param _intKaishacd : �w��H��-���CD�̒l���i�[����
	 */
	public void setIntKaishacd(int _intKaishacd) {
		this.intKaishacd = _intKaishacd;
	}

	/**
	 * �w��H��-�H��CD �Q�b�^�[
	 * @return intKojoco : �w��H��-�H��CD�̒l��Ԃ�
	 */
	public int getIntKojoco() {
		return intKojoco;
	}
	/**
	 * �w��H��-�H��CD �Z�b�^�[
	 * @param _intKojoco : �w��H��-�H��CD�̒l���i�[����
	 */
	public void setIntKojoco(int _intKojoco) {
		this.intKojoco = _intKojoco;
	}

	/**
	 * ���CD �Q�b�^�[
	 * @return strShubetu : ���CD�̒l��Ԃ�
	 */
	public String getStrShubetu() {
		return strShubetu;
	}
	/**
	 * ���CD �Z�b�^�[
	 * @param _strShubetu : ���CD�̒l���i�[����
	 */
	public void setStrShubetu(String _strShubetu) {
		this.strShubetu = _strShubetu;
	}
	
	/**
	 * ���No �Q�b�^�[
	 * @return strShubetuNo : ���No�̒l��Ԃ�
	 */
	public String getStrShubetuNo() {
		return strShubetuNo;
	}
	/**
	 * ���No �Z�b�^�[
	 * @param _intShubetuNo : ���No�̒l���i�[����
	 */
	public void setStrShubetuNo(String _strShubetuNo) {
		if(_strShubetuNo!=null && _strShubetuNo.length() > 0 && _strShubetuNo.length() == 1){
			_strShubetuNo = "0" + _strShubetuNo;
		}
		this.strShubetuNo = _strShubetuNo;
	}

	/**
	 * �O���[�vCD �Q�b�^�[
	 * @return intGroupcd : �O���[�vCD�̒l��Ԃ�
	 */
	public int getIntGroupcd() {
		return intGroupcd;
	}
	/**
	 * �O���[�vCD �Z�b�^�[
	 * @param _intGroupcd : �O���[�vCD�̒l���i�[����
	 */
	public void setIntGroupcd(int _intGroupcd) {
		this.intGroupcd = _intGroupcd;
	}

	/**
	 * �`�[��CD �Q�b�^�[
	 * @return intTeamcd : �`�[��CD�̒l��Ԃ�
	 */
	public int getIntTeamcd() {
		return intTeamcd;
	}
	/**
	 * �`�[��CD �Z�b�^�[
	 * @param intTeamcd : �`�[��CD�̒l���i�[����
	 */
	public void setIntTeamcd(int _intTeamcd) {
		this.intTeamcd = _intTeamcd;
	}

	/**
	 * �ꊇ�\��CD �Q�b�^�[
	 * @return strIkatu : �ꊇ�\��CD�̒l��Ԃ�
	 */
	public String getStrIkatu() {
		return strIkatu;
	}
	/**
	 * �ꊇ�\��CD �Z�b�^�[
	 * @param _strIkatu : �ꊇ�\��CD�̒l���i�[����
	 */
	public void setStrIkatu(String _strIkatu) {
		this.strIkatu = _strIkatu;
	}

	/**
	 * �W������CD �Q�b�^�[
	 * @return strZyanru : �W������CD�̒l��Ԃ�
	 */
	public String getStrZyanru() {
		return strZyanru;
	}
	/**
	 * �W������CD �Z�b�^�[
	 * @param strZyanru : �W������CD�̒l���i�[����
	 */
	public void setStrZyanru(String _strZyanru) {
		this.strZyanru = _strZyanru;
	}

	/**
	 * ���[�UCD �Q�b�^�[
	 * @return strUsercd : ���[�UCD�̒l��Ԃ�
	 */
	public String getStrUsercd() {
		return strUsercd;
	}
	/**
	 * ���[�UCD �Z�b�^�[
	 * @param _strUsercd : ���[�UCD�̒l���i�[����
	 */
	public void setStrUsercd(String _strUsercd) {
		this.strUsercd = _strUsercd;
	}

	/**
	 * �������� �Q�b�^�[
	 * @return strTokutyo : ���������̒l��Ԃ�
	 */
	public String getStrTokutyo() {
		return strTokutyo;
	}
	/**
	 * �������� �Z�b�^�[
	 * @param _strTokutyo : ���������̒l���i�[����
	 */
	public void setStrTokutyo(String _strTokutyo) {
		this.strTokutyo = _strTokutyo;
	}

	/**
	 * �p�r �Q�b�^�[
	 * @return strYoto : �p�r�̒l��Ԃ�
	 */
	public String getStrYoto() {
		return strYoto;
	}
	/**
	 * �p�r �Z�b�^�[
	 * @param _strYoto : �p�r�̒l���i�[����
	 */
	public void setStrYoto(String _strYoto) {
		this.strYoto = _strYoto;
	}

	/**
	 * ���i��CD �Q�b�^�[
	 * @return strKakaku : ���i��CD�̒l��Ԃ�
	 */
	public String getStrKakaku() {
		return strKakaku;
	}
	/**
	 * ���i��CD �Z�b�^�[
	 * @param _strKakaku : ���i��CD�̒l���i�[����
	 */
	public void setStrKakaku(String _strKakaku) {
		this.strKakaku = _strKakaku;
	}

	/**
	 * �S���c��CD �Q�b�^�[
	 * @return strTantoEigyo : �S���c��CD�̒l��Ԃ�
	 */
	public String getStrTantoEigyo() {
		return strTantoEigyo;
	}
	/**
	 * �S���c��CD �Z�b�^�[
	 * @param _strTantoEigyo : �S���c��CD�̒l���i�[����
	 */
	public void setStrTantoEigyo(String _strTantoEigyo) {
		this.strTantoEigyo = _strTantoEigyo;
	}

	/**
	 * �������@CD �Q�b�^�[
	 * @return strSeizocd : �������@CD�̒l��Ԃ�
	 */
	public String getStrSeizocd() {
		return strSeizocd;
	}
	/**
	 * �������@CD �Z�b�^�[
	 * @param _strSeizocd : �������@CD�̒l���i�[����
	 */
	public void setStrSeizocd(String _strSeizocd) {
		this.strSeizocd = _strSeizocd;
	}

	/**
	 * �[�U���@CD �Q�b�^�[
	 * @return strZyutencd : �[�U���@CD�̒l��Ԃ�
	 */
	public String getStrZyutencd() {
		return strZyutencd;
	}
	/**
	 * �[�U���@CD �Z�b�^�[
	 * @param _strZyutencd : �[�U���@CD�̒l���i�[����
	 */
	public void setStrZyutencd(String _strZyutencd) {
		this.strZyutencd = _strZyutencd;
	}

	/**
	 * �E�ە��@ �Q�b�^�[
	 * @return strSakin : �E�ە��@�̒l��Ԃ�
	 */
	public String getStrSakin() {
		return strSakin;
	}
	/**
	 * �E�ە��@ �Z�b�^�[
	 * @param _strSakin : �E�ە��@�̒l���i�[����
	 */
	public void setStrSakin(String _strSakin) {
		this.strSakin = _strSakin;
	}

	/**
	 * �e��E��� �Q�b�^�[
	 * @return strYokihozai : �e��E��ނ̒l��Ԃ�
	 */
	public String getStrYokihozai() {
		return strYokihozai;
	}
	/**
	 * �e��E��� �Z�b�^�[
	 * @param _strYokihozai : �e��E��ނ̒l���i�[����
	 */
	public void setStrYokihozai(String _strYokihozai) {
		this.strYokihozai = _strYokihozai;
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
	 * �e�ʒP��CD �Q�b�^�[
	 * @return strTani : �e�ʒP��CD�̒l��Ԃ�
	 */
	public String getStrTani() {
		return strTani;
	}
	/**
	 * �e�ʒP��CD �Z�b�^�[
	 * @param _strTani : �e�ʒP��CD�̒l���i�[����
	 */
	public void setStrTani(String _strTani) {
		this.strTani = _strTani;
	}

	/**
	 * ���萔 �Q�b�^�[
	 * @return strIrisu : ���萔�̒l��Ԃ�
	 */
	public String getStrIrisu() {
		return strIrisu;
	}
	/**
	 * ���萔 �Z�b�^�[
	 * @param _strIrisu : ���萔�̒l���i�[����
	 */
	public void setStrIrisu(String _strIrisu) {
		this.strIrisu = _strIrisu;
	}

	/**
	 * �戵���xCD �Q�b�^�[
	 * @return strOndo : �戵���xCD�̒l��Ԃ�
	 */
	public String getStrOndo() {
		return strOndo;
	}
	/**
	 * �戵���xCD �Z�b�^�[
	 * @param _strOndo : �戵���xCD�̒l���i�[����
	 */
	public void setStrOndo(String _strOndo) {
		this.strOndo = _strOndo;
	}

	/**
	 * �ܖ����� �Q�b�^�[
	 * @return strShomi : �ܖ����Ԃ̒l��Ԃ�
	 */
	public String getStrShomi() {
		return strShomi;
	}
	/**
	 * �ܖ����� �Z�b�^�[
	 * @param _strShomi : �ܖ����Ԃ̒l���i�[����
	 */
	public void setStrShomi(String _strShomi) {
		this.strShomi = _strShomi;
	}

	/**
	 * ���� �Q�b�^�[
	 * @return strGenka : �����̒l��Ԃ�
	 */
	public String getStrGenka() {
		return strGenka;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _strGenka : �����̒l���i�[����
	 */
	public void setStrGenka(String _strGenka) {
		this.strGenka = _strGenka;
	}

	/**
	 * ���� �Q�b�^�[
	 * @return strBaika : �����̒l��Ԃ�
	 */
	public String getStrBaika() {
		return strBaika;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _strBaika : �����̒l���i�[����
	 */
	public void setStrBaika(String _strBaika) {
		this.strBaika = _strBaika;
	}

	/**
	 * �z�蕨�� �Q�b�^�[
	 * @return strSotei : �z�蕨�ʂ̒l��Ԃ�
	 */
	public String getStrSotei() {
		return strSotei;
	}
	/**
	 * �z�蕨�� �Z�b�^�[
	 * @param _strSotei : �z�蕨�ʂ̒l���i�[����
	 */
	public void setStrSotei(String _strSotei) {
		this.strSotei = _strSotei;
	}

	/**
	 * �������� �Q�b�^�[
	 * @return strHatubai : ���������̒l��Ԃ�
	 */
	public String getStrHatubai() {
		return strHatubai;
	}
	/**
	 * �������� �Z�b�^�[
	 * @param _strHatubai : ���������̒l���i�[����
	 */
	public void setStrHatubai(String _strHatubai) {
		this.strHatubai = _strHatubai;
	}

	/**
	 * �v�攄�� �Q�b�^�[
	 * @return strKeikakuUri : �v�攄��̒l��Ԃ�
	 */
	public String getStrKeikakuUri() {
		return strKeikakuUri;
	}
	/**
	 * �v�攄�� �Z�b�^�[
	 * @param _strKeikakuUri : �v�攄��̒l���i�[����
	 */
	public void setStrKeikakuUri(String _strKeikakuUri) {
		this.strKeikakuUri = _strKeikakuUri;
	}

	/**
	 * �v�旘�v �Q�b�^�[
	 * @return strKeikakuRie : �v�旘�v�̒l��Ԃ�
	 */
	public String getStrKeikakuRie() {
		return strKeikakuRie;
	}
	/**
	 * �v�旘�v �Z�b�^�[
	 * @param _strKeikakuRie : �v�旘�v�̒l���i�[����
	 */
	public void setStrKeikakuRie(String _strKeikakuRie) {
		this.strKeikakuRie = _strKeikakuRie;
	}

	/**
	 * �̔��㔄�� �Q�b�^�[
	 * @return strHanbaigoUri : �̔��㔄��̒l��Ԃ�
	 */
	public String getStrHanbaigoUri() {
		return strHanbaigoUri;
	}
	/**
	 * �̔��㔄�� �Z�b�^�[
	 * @param _strHanbaigoUri : �̔��㔄��̒l���i�[����
	 */
	public void setStrHanbaigoUri(String _strHanbaigoUri) {
		this.strHanbaigoUri = _strHanbaigoUri;
	}

	/**
	 * �̔��㗘�v �Q�b�^�[
	 * @return strHanbaigoRie : �̔��㗘�v�̒l��Ԃ�
	 */
	public String getStrHanbaigoRie() {
		return strHanbaigoRie;
	}
	/**
	 * �̔��㗘�v �Z�b�^�[
	 * @param _strHanbaigoRie : �̔��㗘�v�̒l���i�[����
	 */
	public void setStrHanbaigoRie(String _strHanbaigoRie) {
		this.strHanbaigoRie = _strHanbaigoRie;
	}

	/**
	 * �׎pCD �Q�b�^�[
	 * @return strNishugata : �׎pCD�̒l��Ԃ�
	 */
	public String getStrNishugata() {
		return strNishugata;
	}
	/**
	 * �׎pCD �Z�b�^�[
	 * @param _strNishugata : �׎pCD�̒l���i�[����
	 */
	public void setStrNishugata(String _strNishugata) {
		this.strNishugata = _strNishugata;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return strSogo : �����ӂ̒l��Ԃ�
	 */
	public String getStrSogo() {
		return strSogo;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _strSogo : �����ӂ̒l���i�[����
	 */
	public void setStrSogo(String _strSogo) {
		this.strSogo = _strSogo;
	}

	/**
	 * �����w�� �Q�b�^�[
	 * @return strShosu : �����w��̃R�[�h��Ԃ�
	 */
	public String getStrShosu() {
		return strShosu;
	}
	/**
	 * �����w�� �Z�b�^�[
	 * @param _intShosu : �����w��̒l���i�[����
	 */
	public void setStrShosu(String _strShosu) {
		this.strShosu = _strShosu;
	}

	/**
	 * �p�~�� �Q�b�^�[
	 * @return intHaisi : �p�~��̒l��Ԃ�
	 */
	public int getIntHaisi() {
		return intHaisi;
	}
	/**
	 * �p�~�� �Z�b�^�[
	 * @param _intHaisi : �p�~��̒l���i�[����
	 */
	public void setIntHaisi(int _intHaisi) {
		this.intHaisi = _intHaisi;
	}
	
	/**
	 * �r���敪 �Q�b�^�[
	 * @return dciHaita : �r���敪�̒l��Ԃ�
	 */
	public BigDecimal getDciHaita() {
		return dciHaita;
	}

	/**
	 * �r���敪 �Z�b�^�[
	 * @param _dciHaita : �r���敪�̒l���i�[����
	 */
	public void setDciHaita(BigDecimal _dciHaita) {
		this.dciHaita = _dciHaita;
	}
	
	/**
	 * @return intSeihoShisaku
	 */
	public int getIntSeihoShisaku() {
		return intSeihoShisaku;
	}

	/**
	 * @param intSeihoShisaku �Z�b�g���� intSeihoShisaku
	 */
	public void setIntSeihoShisaku(int intSeihoShisaku) {
		this.intSeihoShisaku = intSeihoShisaku;
	}
	
	/**
	 * ���상�� �Q�b�^�[
	 * @return strShisakuMemo : ���상���̒l��Ԃ�
	 */
	public String getStrShisakuMemo() {
		return strShisakuMemo;
	}

	/**
	 * ���상�� �Z�b�^�[
	 * @param strShisakuMemo : ���상���̒l���i�[����
	 */
	public void setStrShisakuMemo(String strShisakuMemo) {
		this.strShisakuMemo = strShisakuMemo;
	}
	
	/**
	 * ���ӎ����\�� �Q�b�^�[
	 * @return intChuiFg : ���ӎ����\���̒l��Ԃ�
	 */
	public int getIntChuiFg() {
		return intChuiFg;
	}

	/**
	 * ���ӎ����\�� �Z�b�^�[
	 * @param intChuiFg : ���ӎ����\���̒l���i�[����
	 */
	public void setIntChuiFg(int intChuiFg) {
		this.intChuiFg = intChuiFg;
	}

	/**
	 * �o�^��ID �Q�b�^�[
	 * @return dciTorokuid : �o�^��ID�̒l��Ԃ�
	 */
	public BigDecimal getDciTorokuid() {
		return dciTorokuid;
	}
	/**
	 * �o�^��ID �Z�b�^�[
	 * @param _dciTorokuid : �o�^��ID�̒l���i�[����
	 */
	public void setDciTorokuid(BigDecimal _dciTorokuid) {
		this.dciTorokuid = _dciTorokuid;
	}

	/**
	 * �o�^���t �Q�b�^�[
	 * @return strTorokuhi : �o�^���t�̒l��Ԃ�
	 */
	public String getStrTorokuhi() {
		return strTorokuhi;
	}
	/**
	 * �o�^���t �Z�b�^�[
	 * @param _strTorokuhi : �o�^���t�̒l���i�[����
	 */
	public void setStrTorokuhi(String _strTorokuhi) {
		this.strTorokuhi = _strTorokuhi;
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
	public void setDciKosinid(BigDecimal _dciKosinId) {
		this.dciKosinId = _dciKosinId;
	}

	/**
	 * �X�V���t �Q�b�^�[
	 * @return strKosinhi : �X�V���t�̒l��Ԃ�
	 */
	public String getStrKosinhi() {
		return strKosinhi;
	}
	/**
	 * �X�V���t �Z�b�^�[
	 * @param _strKosinhi : �X�V���t�̒l���i�[����
	 */
	public void setStrKosinhi(String _strKosinhi) {
		this.strKosinhi = _strKosinhi;
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
	
	/**
	 * �O���[�v�� �Q�b�^�[
	 * @return strGroupNm : �o�^�Җ��̒l��Ԃ�
	 */
	public String getStrGroupNm() {
		return strGroupNm;
	}
	/**
	 * �O���[�v�� �Z�b�^�[
	 * @param _strGroupNm : �o�^�Җ��̒l���i�[����
	 */
	public void setStrGroupNm(String _strGroupNm) {
		this.strGroupNm = _strGroupNm;
	}
	
	/**
	 * �`�[���� �Q�b�^�[
	 * @return strTeamNm : �X�V�Җ��̒l��Ԃ�
	 */
	public String getStrTeamNm() {
		return strTeamNm;
	}
	/**
	 * �`�[���� �Z�b�^�[
	 * @param _strTeamNm : �X�V�Җ��̒l���i�[����
	 */
	public void setStrTeamNm(String _strTeamNm) {
		this.strTeamNm = _strTeamNm;
	}
	
	//2010/02/25 NAKAMURA ADD START---------------------
	/**
	 * �r����Ж� �Q�b�^�[
	 * @return strHaitaKaishaNm : �r����Ж��̒l��Ԃ�
	 */
	public String getStrHaitaKaishaNm() {
		return strHaitaKaishaNm;
	}
	/**
	 * �r����Ж� �Z�b�^�[
	 * @param _strHaitaKaishaNm : �r����Ж��̒l���i�[����
	 */
	public void setStrHaitaKaishaNm(String _strHaitaKaishaNm) {
		this.strHaitaKaishaNm = _strHaitaKaishaNm;
	}
	
	/**
	 * �r�������� �Q�b�^�[
	 * @return strHaitaBushoNm : �r���������̒l��Ԃ�
	 */
	public String getStrHaitaBushoNm() {
		return strHaitaBushoNm;
	}
	/**
	 * �r�������� �Z�b�^�[
	 * @param _strHaitaBushoNm : �r���������̒l���i�[����
	 */
	public void setStrHaitaBushoNm(String _strHaitaBushoNm) {
		this.strHaitaBushoNm = _strHaitaBushoNm;
	}	
	
	/**
	 * �r������ �Q�b�^�[
	 * @return strHaitaShimei : �r�������̒l��Ԃ�
	 */
	public String getStrHaitaShimei() {
		return strHaitaShimei;
	}
	/**
	 * �r�������@�Z�b�^�[
	 * @param _strHaitaShimei : �r�������̒l���i�[����
	 */
	public void setStrHaitaShimei(String _strHaitaShimei) {
		this.strHaitaShimei = _strHaitaShimei;
	}	
	//2010/02/25 NAKAMURA ADD END-----------------------
	
	//�yQP@00342�z
	/**
	 * �S���҉c�Ɩ� �Q�b�^�[
	 * @return strNmEigyoTanto : �S���҉c�Ɩ��̒l��Ԃ�
	 */
	public String getStrNmEigyoTanto() {
		return strNmEigyoTanto;
	}
	/**
	 * �S���҉c�Ɩ� �Z�b�^�[
	 * @param strNmEigyoTanto : �S���҉c�Ɩ��̒l���i�[����
	 */
	public void setStrNmEigyoTanto(String strNmEigyoTanto) {
		this.strNmEigyoTanto = strNmEigyoTanto;
	}
	
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	/**
	 * �H���p�^�[�� �Q�b�^�[
	 * @return strPt_kotei : �H���p�^�[���̒l��Ԃ�
	 */
	public String getStrPt_kotei() {
		return strPt_kotei;
	}
	/**
	 * �H���p�^�[�� �Z�b�^�[
	 * @param strPt_kotei : �H���p�^�[���̒l���i�[����
	 */
	public void setStrPt_kotei(String strPt_kotei) {
		this.strPt_kotei = strPt_kotei;
	}
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
	/**
	 * �V�[�N���b�g �Q�b�^�[
	 * @return strSecret : �V�[�N���b�g���[�h�̒l��Ԃ�
	 */
	public String getStrSecret() {
		return strSecret;
	}
	/**
	 * �V�[�N���b�g �Z�b�^�[
	 * @param strSecret : �V�[�N���b�g���[�h�̒l���i�[����
	 */
	public void setStrSecret(String strSecret) {
		this.strSecret = strSecret;
	}
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

}
