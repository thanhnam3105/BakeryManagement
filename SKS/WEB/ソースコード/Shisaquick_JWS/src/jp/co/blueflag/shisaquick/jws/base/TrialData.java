package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 *
 * ����f�[�^�ێ�
 *  : ����f�[�^�̊Ǘ����s��
 *
 */
public class TrialData extends DataBase {

	private BigDecimal dciShisakuUser;		//����CD-�Ј�CD
	private BigDecimal dciShisakuYear;		//����CD-�N
	private BigDecimal dciShisakuNum;		//����CD-�ǔ�
	private int intShisakuSeq;					//����SEQ
	private int intHyojiNo;						//����\����
	private String strTyuiNo;					//���ӎ���NO
	private String strSampleNo;				//�T���v��NO�i���́j
	private String strMemo;						//����
	private int intInsatuFlg;					//���Flg
	private int intZidoKei;						//�����v�ZFlg
	private int intGenkaShisan;				//�������ZNo
	private String strSeihoNo1;				//���@No-1
	private String strSeihoNo2;				//���@No-2
	private String strSeihoNo3;				//���@No-3
	private String strSeihoNo4;				//���@No-4
	private String strSeihoNo5;				//���@No-5
	private BigDecimal dciSosan;				//���_
	private int intSosanFlg;					//���_-�o��Flg
	private BigDecimal dciShokuen;			//�H��
	private int intShokuenFlg;					//�H��-�o��Flg
	private BigDecimal dciSuiSando;			//�������_�x
	private int intSuiSandoFlg;				//�������_�x-�o��Flg
	private BigDecimal dciSuiShokuen;		//�������H��
	private int intSuiShokuenFlg;				//�������H��-�o��Flg
	private BigDecimal dciSuiSakusan;		//�������|�_
	private int intSuiSakusanFlg;				//�������|�_-�o��Flg
	private String strToudo;						//���x
	private int intToudoFlg;					//���x-�o��Flg
	private String strNendo;					//�S�x
	private int intNendoFlg;					//�S�x-�o��Flg
	private String strOndo;						//���x
	private int intOndoFlg;						//���x-�o��Flg
	private String strPh;							//PH
	private int intPhFlg;							//PH - �o��Flg
	private String strSosanBunseki;			//���_�F����
	private int intSosanBunsekiFlg;			//���_�F����-�o��Flg
	private String strShokuenBunseki;		//�H���F����
	private int intShokuenBunsekiFlg;		//�H���F����-�o��Flg
	private String strHizyu;						//��d
	private int intHizyuFlg;						//��d-�o��Flg
	private String strSuibun;					//��������
	private int intSuibunFlg;					//��������-�o��Flg
	private String strArukoru;					//�A���R�[��
	private int intArukoruFlg;					//�A���R�[��-�o��Flg
	private String strSakuseiMemo;			//�쐬����
	private int intSakuseiMemoFlg;			//�쐬����-�o��Flg
	private String strHyoka;					//�]��
	private int intHyokaFlg;					//�]��-�o��Flg
	private String strFreeTitle1;				//�t���[�@�^�C�g��
	private String strFreeNaiyo1;				//�t���[�@���e
	private int intFreeFlg;						//�t���[�@-�o��Flg
	private String strFreeTitle2;				//�t���[�A�^�C�g��
	private String strFreeNaiyo2;				//�t���[�A���e
	private int intFreeFl2;						//�t���[�A-�o��Flg
	private String strFreeTitle3;				//�t���[�B�^�C�g��
	private String strFreeNaiyo3;				//�t���[�B���e
	private int intFreeFl3;						//�t���[�B-�o��Flg
	private String strShisakuHi;				//������t
	private BigDecimal dciShiagari;			//�d��d��
	private BigDecimal dciTorokuId;			//�o�^��ID
	private String strTorokuHi;					//�o�^���t
	private BigDecimal dciKosinId;			//�X�V��ID
	private String strkosinHi;					//�X�V���t
	private String strTorokuNm;				//�o�^�Җ�
	private String strKosinNm;					//�X�V�Җ�
// ADD start 20120928 QP@20505 No.24
	private BigDecimal dciMsg;					// �l�r�f
	private String strFreeTitleNendo;			//�S�x�t���[�^�C�g��
	private String strFreeNendo;				//�S�x�t���[���e
	private int intFreeNendoFlg;				//�S�x�t���[�o��flg
	private String strFreeTitleOndo;			//���x�t���[�^�C�g��
	private String strFreeOndo;					//���x�t���[���e
	private int intFreeOndoFlg;					//���x�t���[�o��flg
	private String strFreeTitleSuibunKasei;		//���������t���[�^�C�g��
	private String strFreeSuibunKasei;			//���������t���[���e
	private int intFreeSuibunKaseiFlg;			//���������|�o��flg
	private String strFreeTitleAlchol;			//�A���R�[���t���[�^�C�g��
	private String strFreeAlchol;				//�A���R�[���t���[���e
	private int intFreeAlcholFlg;				//�A���R�[���t���[�o��flg
	private BigDecimal dciJikkoSakusanNodo;			//�����|�_�Z�x
	private int intJikkoSakusanNodoFlg;			//�����|�_�Z�x�|�o��flg
	private BigDecimal dciSuisoMSG;					//�������l�r�f
	private int intSuisoMSGFlg;					//�������l�r�f�|�o��flg
	private String strFreeTitle4;				//�t���[�C�^�C�g��
	private String strFreeNaiyo4;				//�t���[�C���e
	private int intFreeFlg4;					//�t���[�C-�o��Flg
	private String strFreeTitle5;				//�t���[�D�^�C�g��
	private String strFreeNaiyo5;				//�t���[�D���e
	private int intFreeFlg5;						//�t���[�D-�o��Flg
	private String strFreeTitle6;				//�t���[�E�^�C�g��
	private String strFreeNaiyo6;				//�t���[�E���e
	private int intFreeFlg6;						//�t���[�E-�o��Flg
// ADD end 20120928 QP@20505 No.24

//2009/10/26 TT.Y.NISHIGAWA ADD START [�������Z�F���Z�˗�flg]
	private int flg_shisanIrai;               	//�������Z�˗��t���O
	private int flg_init;                       	//�����˗��f�[�^�t���O[1:���Ɉ˗�����Ă���f�[�^]
//2009/10/26 TT.Y.NISHIGAWA ADD END [�������Z�F���Z�˗�flg]

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
	private String strKeisanSiki;				//�v�Z��
//add end   -------------------------------------------------------------------------------

//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
	private int flg_cancel;						//�˗��L�����Z���t���O
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	private String strHiju_sui;						//������d
	private int intHiju_sui_fg;						//������d�@�o��FG
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public TrialData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^
		super();

		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.intShisakuSeq = 0;
		this.intHyojiNo = 0;
		this.strTyuiNo = null;
		this.strSampleNo = null;
		this.strMemo = null;
		this.intInsatuFlg = 0;
		this.intZidoKei = 0;
		this.intGenkaShisan = 0;
		this.strSeihoNo1 = null;
		this.strSeihoNo2 = null;
		this.strSeihoNo3 = null;
		this.strSeihoNo4 = null;
		this.strSeihoNo5 = null;
		this.dciSosan = null;
		this.intSosanFlg = 0;
		this.dciShokuen = null;
		this.intShokuenFlg = 0;
		this.dciSuiSando = null;
		this.intSuiSandoFlg = 0;
		this.dciSuiShokuen = null;
		this.intSuiShokuenFlg = 0;
		this.dciSuiSakusan = null;
		this.intSuiSakusanFlg = 0;
		this.strToudo = null;
		this.intToudoFlg = 0;
		this.strNendo = null;
		this.intNendoFlg = 0;
		this.strOndo = null;
		this.intOndoFlg = 0;
		this.strPh = null;
		this.intPhFlg = 0;
		this.strSosanBunseki = null;
		this.intSosanBunsekiFlg = 0;
		this.strShokuenBunseki = null;
		this.intShokuenBunsekiFlg = 0;
		this.strHizyu = null;
		this.intHizyuFlg = 0;
		this.strSuibun = null;
		this.intSuibunFlg = 0;
		this.strArukoru = null;
		this.intArukoruFlg = 0;
		this.strSakuseiMemo = null;
		this.intSakuseiMemoFlg = 0;
		this.strHyoka = null;
		this.intHyokaFlg = 0;
		this.strFreeTitle1 = null;
		this.strFreeNaiyo1 = null;
		this.intFreeFlg = 0;
		this.strFreeTitle2 = null;
		this.strFreeNaiyo2 = null;
		this.intFreeFl2 = 0;
		this.strFreeTitle3 = null;
		this.strFreeNaiyo3 = null;
		this.intFreeFl3 = 0;
		this.strShisakuHi = null;
		this.dciShiagari = null;
		this.dciTorokuId = null;
		this.strTorokuHi = null;
		this.dciKosinId = null;
		this.strkosinHi = null;
//2009/10/26 TT.Y.NISHIGAWA ADD START [�������Z�F���Z�˗�flg]
		this.flg_shisanIrai = 0;
		this.flg_init = 0;
//2009/10/26 TT.Y.NISHIGAWA ADD END [�������Z�F���Z�˗�flg]
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
		this.flg_cancel = 0;
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End --------------------------
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
		this.strHiju_sui = null;
		this.intHiju_sui_fg = 0;
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20120928 QP@20505 No.24
		this.dciMsg = null;
		this.strFreeTitleNendo = null;
		this.strFreeNendo = null;
		this.intFreeNendoFlg = 0;
		this.strFreeTitleOndo = null;
		this.strFreeOndo = null;
		this.intFreeOndoFlg = 0;
		this.strFreeTitleSuibunKasei = null;
		this.strFreeSuibunKasei = null;
		this.intFreeSuibunKaseiFlg = 0;
		this.strFreeTitleAlchol = null;
		this.strFreeAlchol = null;
		this.intFreeAlcholFlg = 0;
		this.dciJikkoSakusanNodo = null;
		this.intJikkoSakusanNodoFlg = 0;
		this.dciSuisoMSG = null;
		this.intSuisoMSGFlg = 0;
		this.strFreeTitle4 = null;
		this.strFreeNaiyo4 = null;
		this.intFreeFlg4 = 0;
		this.strFreeTitle5 = null;
		this.strFreeNaiyo5 = null;
		this.intFreeFlg5 = 0;
		this.strFreeTitle6 = null;
		this.strFreeNaiyo6 = null;
		this.intFreeFlg6 = 0;
// ADD end 20120928 QP@20505 No.24
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
	 * @param intShisakuSeq : ����SEQ�̒l���i�[����
	 */
	public void setIntShisakuSeq(int _intShisakuSeq) {
		this.intShisakuSeq = _intShisakuSeq;
	}

	/**
	 * ����\���� �Q�b�^�[
	 * @return intHyojiNo : ����\�����̒l��Ԃ�
	 */
	public int getIntHyojiNo() {
		return intHyojiNo;
	}
	/**
	 * ����\���� �Z�b�^�[
	 * @param intHyojiNo : ����\�����̒l���i�[����
	 */
	public void setIntHyojiNo(int _intHyojiNo) {
		this.intHyojiNo = _intHyojiNo;
	}

	/**
	 * ���ӎ���NO �Q�b�^�[
	 * @return strTyuiNo : ���ӎ���NO�̒l��Ԃ�
	 */
	public String getStrTyuiNo() {
		return strTyuiNo;
	}
	/**
	 * ���ӎ���NO �Z�b�^�[
	 * @param _strTyuiNo : ���ӎ���NO�̒l���i�[����
	 */
	public void setStrTyuiNo(String _strTyuiNo) {
		this.strTyuiNo = _strTyuiNo;
	}

	/**
	 * �T���v��NO�i���́j �Q�b�^�[
	 * @return strSampleNo : �T���v��NO�i���́j�̒l��Ԃ�
	 */
	public String getStrSampleNo() {
		return strSampleNo;
	}
	/**
	 * �T���v��NO�i���́j �Z�b�^�[
	 * @param _strSampleNo : �T���v��NO�i���́j�̒l���i�[����
	 */
	public void setStrSampleNo(String _strSampleNo) {
		this.strSampleNo = _strSampleNo;
	}

	/**
	 * ���� �Q�b�^�[
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
	 * ���Flg �Q�b�^�[
	 * @return intInsatuFlg : ���Flg�̒l��Ԃ�
	 */
	public int getIntInsatuFlg() {
		return intInsatuFlg;
	}
	/**
	 * ���Flg �Z�b�^�[
	 * @param _intInsatuFlg : ���Flg�̒l���i�[����
	 */
	public void setIntInsatuFlg(int _intInsatuFlg) {
		this.intInsatuFlg = _intInsatuFlg;
	}

	/**
	 * �����v�ZFlg �Q�b�^�[
	 * @return intZidoKei : �����v�ZFlg�̒l��Ԃ�
	 */
	public int getIntZidoKei() {
		return intZidoKei;
	}
	/**
	 * �����v�ZFlg �Z�b�^�[
	 * @param intZidoKei : �����v�ZFlg�̒l���i�[����
	 */
	public void setIntZidoKei(int _intZidoKei) {
		this.intZidoKei = _intZidoKei;
	}

	/**
	 * �������ZNo �Q�b�^�[
	 * @return intGenkaShisan : �������ZNo�̒l��Ԃ�
	 */
	public int getIntGenkaShisan() {
		return intGenkaShisan;
	}
	/**
	 * �������ZNo �Z�b�^�[
	 * @param _intGenkaShisan : �������ZNo�̒l���i�[����
	 */
	public void setIntGenkaShisan(int _intGenkaShisan) {
		this.intGenkaShisan = _intGenkaShisan;
	}

	/**
	 * ���@No-1 �Q�b�^�[
	 * @return strSeihoNo1 : ���@No-1�̒l��Ԃ�
	 */
	public String getStrSeihoNo1() {
		return strSeihoNo1;
	}
	/**
	 * ���@No-1 �Z�b�^�[
	 * @param _strSeihoNo1 : ���@No-1�̒l���i�[����
	 */
	public void setStrSeihoNo1(String _strSeihoNo1) {
		this.strSeihoNo1 = _strSeihoNo1;
	}

	/**
	 * ���@No-2 �Q�b�^�[
	 * @return strSeihoNo2 : ���@No-2�̒l��Ԃ�
	 */
	public String getStrSeihoNo2() {
		return strSeihoNo2;
	}
	/**
	 * ���@No-2 �Z�b�^�[
	 * @param _strSeihoNo2 : ���@No-2�̒l���i�[����
	 */
	public void setStrSeihoNo2(String _strSeihoNo2) {
		this.strSeihoNo2 = _strSeihoNo2;
	}

	/**
	 * ���@No-3 �Q�b�^�[
	 * @return strSeihoNo3 : ���@No-3�̒l��Ԃ�
	 */
	public String getStrSeihoNo3() {
		return strSeihoNo3;
	}
	/**
	 * ���@No-3 �Z�b�^�[
	 * @param _strSeihoNo3 : ���@No-3�̒l���i�[����
	 */
	public void setStrSeihoNo3(String _strSeihoNo3) {
		this.strSeihoNo3 = _strSeihoNo3;
	}

	/**
	 * ���@No-4 �Q�b�^�[
	 * @return strSeihoNo4 : ���@No-4�̒l��Ԃ�
	 */
	public String getStrSeihoNo4() {
		return strSeihoNo4;
	}
	/**
	 * ���@No-4 �Z�b�^�[
	 * @param _strSeihoNo4 : ���@No-4�̒l���i�[����
	 */
	public void setStrSeihoNo4(String _strSeihoNo4) {
		this.strSeihoNo4 = _strSeihoNo4;
	}

	/**
	 * ���@No-5 �Q�b�^�[
	 * @return strSeihoNo5 : ���@No-5�̒l��Ԃ�
	 */
	public String getStrSeihoNo5() {
		return strSeihoNo5;
	}
	/**
	 * ���@No-5 �Z�b�^�[
	 * @param _strSeihoNo5 : ���@No-5�̒l���i�[����
	 */
	public void setStrSeihoNo5(String _strSeihoNo5) {
		this.strSeihoNo5 = _strSeihoNo5;
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
	 * ���_-�o��Flg �Q�b�^�[
	 * @return intSosanFlg : ���_-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSosanFlg() {
		return intSosanFlg;
	}
	/**
	 * ���_-�o��Flg �Z�b�^�[
	 * @param _intSosanFlg : ���_-�o��Flg�̒l���i�[����
	 */
	public void setIntSosanFlg(int _intSosanFlg) {
		this.intSosanFlg = _intSosanFlg;
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
// ADD start 20121002 QP@20505 No.24
	/**
	 * �l�r�f �Q�b�^�[
	 * @return dciMsg : �l�r�f�̒l��Ԃ�
	 */
	public BigDecimal getDciMsg() {
		return dciMsg;
	}
	/**
	 * �l�r�f �Z�b�^�[
	 * @param _dciMsg : �l�r�f�̒l���i�[����
	 */
	public void setDciMsg(BigDecimal _dciMsg) {
		this.dciMsg = _dciMsg;
	}
// ADD end 20121002 QP@20505 No.24

	/**
	 * �H��-�o��Flg �Q�b�^�[
	 * @return intShokuenFlg : �H��-�o��Flg�̒l��Ԃ�
	 */
	public int getIntShokuenFlg() {
		return intShokuenFlg;
	}
	/**
	 * �H��-�o��Flg �Z�b�^�[
	 * @param _intShokuenFlg : �H��-�o��Flg�̒l���i�[����
	 */
	public void setIntShokuenFlg(int _intShokuenFlg) {
		this.intShokuenFlg = _intShokuenFlg;
	}

	/**
	 * �������_�x �Q�b�^�[
	 * @return dciSuiSando : �������_�x�̒l��Ԃ�
	 */
	public BigDecimal getDciSuiSando() {
		return dciSuiSando;
	}
	/**
	 * �������_�x �Z�b�^�[
	 * @param _dciSuiSando : �������_�x�̒l���i�[����
	 */
	public void setDciSuiSando(BigDecimal _dciSuiSando) {
		this.dciSuiSando = _dciSuiSando;
	}

	/**
	 * �������_�x-�o��Flg �Q�b�^�[
	 * @return intSuiSandoFlg : �������_�x-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSuiSandoFlg() {
		return intSuiSandoFlg;
	}
	/**
	 * �������_�x-�o��Flg �Z�b�^�[
	 * @param _intSuiSandoFlg : �������_�x-�o��Flg�̒l���i�[����
	 */
	public void setIntSuiSandoFlg(int _intSuiSandoFlg) {
		this.intSuiSandoFlg = _intSuiSandoFlg;
	}

	/**
	 * �������H�� �Q�b�^�[
	 * @return dciSuiShokuen : �������H���̒l��Ԃ�
	 */
	public BigDecimal getDciSuiShokuen() {
		return dciSuiShokuen;
	}
	/**
	 * �������H�� �Z�b�^�[
	 * @param _dciSuiShokuen : �������H���̒l���i�[����
	 */
	public void setDciSuiShokuen(BigDecimal _dciSuiShokuen) {
		this.dciSuiShokuen = _dciSuiShokuen;
	}

	/**
	 * �������H��-�o��Flg �Q�b�^�[
	 * @return intSuiShokuenFlg : �������H��-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSuiShokuenFlg() {
		return intSuiShokuenFlg;
	}
	/**
	 * �������H��-�o��Flg �Z�b�^�[
	 * @param _intSuiShokuenFlg : �������H��-�o��Flg�̒l���i�[����
	 */
	public void setIntSuiShokuenFlg(int _intSuiShokuenFlg) {
		this.intSuiShokuenFlg = _intSuiShokuenFlg;
	}

	/**
	 * �������|�_ �Q�b�^�[
	 * @return dciSuiSakusan : �������|�_�̒l��Ԃ�
	 */
	public BigDecimal getDciSuiSakusan() {
		return dciSuiSakusan;
	}
	/**
	 * �������|�_ �Z�b�^�[
	 * @param _dciSuiSakusan : �������|�_�̒l���i�[����
	 */
	public void setDciSuiSakusan(BigDecimal _dciSuiSakusan) {
		this.dciSuiSakusan = _dciSuiSakusan;
	}

	/**
	 * �������|�_-�o��Flg �Q�b�^�[
	 * @return intSuiSakusanFlg : �������|�_-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSuiSakusanFlg() {
		return intSuiSakusanFlg;
	}
	/**
	 * �������|�_-�o��Flg �Z�b�^�[
	 * @param _intSuiSakusanFlg : �������|�_-�o��Flg�̒l���i�[����
	 */
	public void setIntSuiSakusanFlg(int _intSuiSakusanFlg) {
		this.intSuiSakusanFlg = _intSuiSakusanFlg;
	}

	/**
	 * ���x �Q�b�^�[
	 * @return strToudo : ���x�̒l��Ԃ�
	 */
	public String getStrToudo() {
		return strToudo;
	}
	/**
	 * ���x �Z�b�^�[
	 * @param _strToudo : ���x�̒l���i�[����
	 */
	public void setStrToudo(String _strToudo) {
		this.strToudo = _strToudo;
	}

	/**
	 * ���x-�o��Flg �Q�b�^�[
	 * @return intToudoFlg : ���x-�o��Flg�̒l��Ԃ�
	 */
	public int getIntToudoFlg() {
		return intToudoFlg;
	}
	/**
	 * ���x-�o��Flg �Z�b�^�[
	 * @param _intToudoFlg : ���x-�o��Flg�̒l���i�[����
	 */
	public void setIntToudoFlg(int _intToudoFlg) {
		this.intToudoFlg = _intToudoFlg;
	}

	/**
	 * �S�x �Q�b�^�[
	 * @return strNendo : �S�x�̒l��Ԃ�
	 */
	public String getStrNendo() {
		return strNendo;
	}
	/**
	 * �S�x �Z�b�^�[
	 * @param _strNendo : �S�x�̒l���i�[����
	 */
	public void setStrNendo(String _strNendo) {
		this.strNendo = _strNendo;
	}

	/**
	 * �S�x-�o��Flg �Q�b�^�[
	 * @return intNendoFlg : �S�x-�o��Flg�̒l��Ԃ�
	 */
	public int getIntNendoFlg() {
		return intNendoFlg;
	}
	/**
	 * �S�x-�o��Flg �Z�b�^�[
	 * @param _intNendoFlg : �S�x-�o��Flg�̒l���i�[����
	 */
	public void setIntNendoFlg(int _intNendoFlg) {
		this.intNendoFlg = _intNendoFlg;
	}

	/**
	 * ���x �Q�b�^�[
	 * @return strOndo : ���x�̒l��Ԃ�
	 */
	public String getStrOndo() {
		return strOndo;
	}
	/**
	 * ���x �Z�b�^�[
	 * @param _strOndo : ���x�̒l���i�[����
	 */
	public void setStrOndo(String _strOndo) {
		this.strOndo = _strOndo;
	}

	/**
	 * ���x-�o��Flg �Q�b�^�[
	 * @return intOndoFlg : ���x-�o��Flg�̒l��Ԃ�
	 */
	public int getIntOndoFlg() {
		return intOndoFlg;
	}
	/**
	 * ���x-�o��Flg �Z�b�^�[
	 * @param _intOndoFlg : ���x-�o��Flg�̒l���i�[����
	 */
	public void setIntOndoFlg(int _intOndoFlg) {
		this.intOndoFlg = _intOndoFlg;
	}

	/**
	 * PH �Q�b�^�[
	 * @return strPh : PH�̒l��Ԃ�
	 */
	public String getStrPh() {
		return strPh;
	}
	/**
	 * PH �Z�b�^�[
	 * @param _strPh : PH�̒l���i�[����
	 */
	public void setStrPh(String _strPh) {
		this.strPh = _strPh;
	}

	/**
	 * PH - �o��Flg �Q�b�^�[
	 * @return intPhFlg : PH - �o��Flg�̒l��Ԃ�
	 */
	public int getIntPhFlg() {
		return intPhFlg;
	}
	/**
	 * PH - �o��Flg �Z�b�^�[
	 * @param _intPhFlg : PH - �o��Flg�̒l���i�[����
	 */
	public void setIntPhFlg(int _intPhFlg) {
		this.intPhFlg = _intPhFlg;
	}

	/**
	 * ���_�F���� �Q�b�^�[
	 * @return strSosanBunseki : ���_�F���͂̒l��Ԃ�
	 */
	public String getStrSosanBunseki() {
		return strSosanBunseki;
	}
	/**
	 * ���_�F���� �Z�b�^�[
	 * @param _strSosanBunseki : ���_�F���͂̒l���i�[����
	 */
	public void setStrSosanBunseki(String _strSosanBunseki) {
		this.strSosanBunseki = _strSosanBunseki;
	}

	/**
	 * ���_�F����-�o��Flg �Q�b�^�[
	 * @return intSosanBunsekiFlg : ���_�F����-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSosanBunsekiFlg() {
		return intSosanBunsekiFlg;
	}
	/**
	 * ���_�F����-�o��Flg �Z�b�^�[
	 * @param _intSosanBunsekiFlg : ���_�F����-�o��Flg�̒l���i�[����
	 */
	public void setIntSosanBunsekiFlg(int _intSosanBunsekiFlg) {
		this.intSosanBunsekiFlg = _intSosanBunsekiFlg;
	}

	/**
	 * �H���F���� �Q�b�^�[
	 * @return strShokuenBunseki : �H���F���͂̒l��Ԃ�
	 */
	public String getStrShokuenBunseki() {
		return strShokuenBunseki;
	}
	/**
	 * �H���F���� �Z�b�^�[
	 * @param _strShokuenBunseki : �H���F���͂̒l���i�[����
	 */
	public void setStrShokuenBunseki(String _strShokuenBunseki) {
		this.strShokuenBunseki = _strShokuenBunseki;
	}

	/**
	 * �H���F����-�o��Flg �Q�b�^�[
	 * @return intShokuenBunsekiFlg : �H���F����-�o��Flg�̒l��Ԃ�
	 */
	public int getIntShokuenBunsekiFlg() {
		return intShokuenBunsekiFlg;
	}
	/**
	 * �H���F����-�o��Flg �Z�b�^�[
	 * @param _intShokuenBunsekiFlg : �H���F����-�o��Flg�̒l���i�[����
	 */
	public void setIntShokuenBunsekiFlg(int _intShokuenBunsekiFlg) {
		this.intShokuenBunsekiFlg = _intShokuenBunsekiFlg;
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
	 * ��d-�o��Flg �Q�b�^�[
	 * @return intHizyuFlg : ��d-�o��Flg�̒l��Ԃ�
	 */
	public int getIntHizyuFlg() {
		return intHizyuFlg;
	}
	/**
	 * ��d-�o��Flg �Z�b�^�[
	 * @param _intHizyuFlg : ��d-�o��Flg�̒l���i�[����
	 */
	public void setIntHizyuFlg(int _intHizyuFlg) {
		this.intHizyuFlg = _intHizyuFlg;
	}

	/**
	 * �������� �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrSuibun() {
		return strSuibun;
	}
	/**
	 * �������� �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrSuibun(String _strSuibun) {
		this.strSuibun = _strSuibun;
	}

	/**
	 * ��������-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSuibunFlg() {
		return intSuibunFlg;
	}
	/**
	 * ��������-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntSuibunFlg(int _intSuibunFlg) {
		this.intSuibunFlg = _intSuibunFlg;
	}

	/**
	 * �A���R�[�� �Q�b�^�[
	 * @return strArukoru : �A���R�[���̒l��Ԃ�
	 */
	public String getStrArukoru() {
		return strArukoru;
	}
	/**
	 * �A���R�[�� �Z�b�^�[
	 * @param _strArukoru : �A���R�[���̒l���i�[����
	 */
	public void setStrArukoru(String _strArukoru) {
		this.strArukoru = _strArukoru;
	}

	/**
	 * �A���R�[��-�o��Flg �Q�b�^�[
	 * @return intArukoruFlg : �A���R�[��-�o��Flg�̒l��Ԃ�
	 */
	public int getIntArukoruFlg() {
		return intArukoruFlg;
	}
	/**
	 * �A���R�[��-�o��Flg �Z�b�^�[
	 * @param _intArukoruFlg : �A���R�[��-�o��Flg�̒l���i�[����
	 */
	public void setIntArukoruFlg(int _intArukoruFlg) {
		this.intArukoruFlg = _intArukoruFlg;
	}

	/**
	 * �쐬���� �Q�b�^�[
	 * @return strSakuseiMemo : �쐬�����̒l��Ԃ�
	 */
	public String getStrSakuseiMemo() {
		return strSakuseiMemo;
	}
	/**
	 * �쐬���� �Z�b�^�[
	 * @param _strSakuseiMemo : �쐬�����̒l���i�[����
	 */
	public void setStrSakuseiMemo(String _strSakuseiMemo) {
		this.strSakuseiMemo = _strSakuseiMemo;
	}

	/**
	 * �쐬����-�o��Flg �Q�b�^�[
	 * @return strSakuseiMemoFlg : �쐬����-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSakuseiMemoFlg() {
		return intSakuseiMemoFlg;
	}
	/**
	 * �쐬����-�o��Flg �Z�b�^�[
	 * @param _strSakuseiMemoFlg : �쐬����-�o��Flg�̒l���i�[����
	 */
	public void setIntSakuseiMemoFlg(int _intSakuseiMemoFlg) {
		this.intSakuseiMemoFlg = _intSakuseiMemoFlg;
	}

	/**
	 * �]�� �Q�b�^�[
	 * @return strHyoka : �]���̒l��Ԃ�
	 */
	public String getStrHyoka() {
		return strHyoka;
	}
	/**
	 * �]�� �Z�b�^�[
	 * @param _strHyoka : �]���̒l���i�[����
	 */
	public void setStrHyoka(String _strHyoka) {
		this.strHyoka = _strHyoka;
	}

	/**
	 * �]��-�o��Flg �Q�b�^�[
	 * @return intHyokaFlg : �]��-�o��Flg�̒l��Ԃ�
	 */
	public int getIntHyokaFlg() {
		return intHyokaFlg;
	}
	/**
	 * �]��-�o��Flg �Z�b�^�[
	 * @param _intHyokaFlg : �]��-�o��Flg�̒l���i�[����
	 */
	public void setIntHyokaFlg(int _intHyokaFlg) {
		this.intHyokaFlg = _intHyokaFlg;
	}

	/**
	 * �t���[�@�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle1 : �t���[�@�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle1() {
		return strFreeTitle1;
	}
	/**
	 * �t���[�@�^�C�g�� �Z�b�^�[
	 * @param _strFreeTitle1 : �t���[�@�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle1(String _strFreeTitle1) {
		this.strFreeTitle1 = _strFreeTitle1;
	}

	/**
	 * �t���[�@���e �Q�b�^�[
	 * @return strFreeNaiyo1 : �t���[�@���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo1() {
		return strFreeNaiyo1;
	}
	/**
	 * �t���[�@���e �Z�b�^�[
	 * @param _strFreeNaiyo1 : �t���[�@���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo1(String _strFreeNaiyo1) {
		this.strFreeNaiyo1 = _strFreeNaiyo1;
	}

	/**
	 * �t���[�@-�o��Flg �Q�b�^�[
	 * @return intFreeFlg : �t���[�@-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFlg() {
		return intFreeFlg;
	}
	/**
	 * �t���[�@-�o��Flg �Z�b�^�[
	 * @param _intFreeFlg : �t���[�@-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFlg(int _intFreeFlg) {
		this.intFreeFlg = _intFreeFlg;
	}

	/**
	 * �t���[�A�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle2 : �t���[�A�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle2() {
		return strFreeTitle2;
	}
	/**
	 * �t���[�A�^�C�g�� �Z�b�^�[
	 * @param strFreeTitle2 : �t���[�A�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle2(String _strFreeTitle2) {
		this.strFreeTitle2 = _strFreeTitle2;
	}

	/**
	 * �t���[�A���e �Q�b�^�[
	 * @return strFreeNaiyo2 : �t���[�A���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo2() {
		return strFreeNaiyo2;
	}
	/**
	 * �t���[�A���e �Z�b�^�[
	 * @param _strFreeNaiyo2 : �t���[�A���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo2(String _strFreeNaiyo2) {
		this.strFreeNaiyo2 = _strFreeNaiyo2;
	}

	/**
	 * �t���[�A-�o��Flg �Q�b�^�[
	 * @return intFreeFl2 : �t���[�A-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFl2() {
		return intFreeFl2;
	}
	/**
	 * �t���[�A-�o��Flg �Z�b�^�[
	 * @param _intFreeFl2 : �t���[�A-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFl2(int _intFreeFl2) {
		this.intFreeFl2 = _intFreeFl2;
	}

	/**
	 * �t���[�B�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle3 : �t���[�B�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle3() {
		return strFreeTitle3;
	}
	/**
	 * �t���[�B�^�C�g�� �Z�b�^�[
	 * @param _strFreeTitle3 : �t���[�B�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle3(String _strFreeTitle3) {
		this.strFreeTitle3 = _strFreeTitle3;
	}

	/**
	 * �t���[�B���e �Q�b�^�[
	 * @return strFreeNaiyo3 : �t���[�B���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo3() {
		return strFreeNaiyo3;
	}
	/**
	 * �t���[�B���e �Z�b�^�[
	 * @param _strFreeNaiyo3 : �t���[�B���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo3(String _strFreeNaiyo3) {
		this.strFreeNaiyo3 = _strFreeNaiyo3;
	}

	/**
	 * �t���[�B-�o��Flg �Q�b�^�[
	 * @return intFreeFl3 : �t���[�B-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFl3() {
		return intFreeFl3;
	}
	/**
	 * �t���[�B-�o��Flg �Z�b�^�[
	 * @param _intFreeFl3 : �t���[�B-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFl3(int _intFreeFl3) {
		this.intFreeFl3 = _intFreeFl3;
	}

	/**
	 * ������t �Q�b�^�[
	 * @return strShisakuHi : ������t�̒l��Ԃ�
	 */
	public String getStrShisakuHi() {
		return strShisakuHi;
	}

	/**
	 * ������t �Z�b�^�[
	 * @param strShisakuHi : ������t�̒l���i�[����
	 */
	public void setStrShisakuHi(String strShisakuHi) {
		this.strShisakuHi = strShisakuHi;
	}

	/**
	 * �d��d�� �Q�b�^�[
	 * @return dciShiagari : �d��d�ʂ̒l��Ԃ�
	 */
	public BigDecimal getDciShiagari() {
		return dciShiagari;
	}

	/**
	 * �d��d�� �Z�b�^�[
	 * @param dciShiagari : �d��d�ʂ̒l���i�[����
	 */
	public void setDciShiagari(BigDecimal dciShiagari) {
		this.dciShiagari = dciShiagari;
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
	 * @return strkosinHi : �X�V���t�̒l��Ԃ�
	 */
	public String getStrkosinHi() {
		return strkosinHi;
	}
	/**
	 * �X�V���t �Z�b�^�[
	 * @param _strkosinHi : �X�V���t�̒l���i�[����
	 */
	public void setStrkosinHi(String _strkosinHi) {
		this.strkosinHi = _strkosinHi;
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
	 * �������Z�˗��t���O �Q�b�^�[
	 * @return flg_shisanIrai : �������Z�˗��t���O�̒l��Ԃ�
	 */
	public int getFlg_shisanIrai() {
		return flg_shisanIrai;
	}

	/**
	 * �������Z�˗��t���O �Z�b�^�[
	 * @param flg_shisanIrai : �������Z�˗��t���O�̒l���i�[����
	 */
	public void setFlg_shisanIrai(int flg_shisanIrai) {
		this.flg_shisanIrai = flg_shisanIrai;
	}

	/**
	 * �����˗��f�[�^�t���O �Q�b�^�[
	 * @return flg_init : �����˗��f�[�^�t���O�̒l��Ԃ�
	 */
	public int getFlg_init() {
		return flg_init;
	}

	/**
	 * �����˗��f�[�^�t���O �Z�b�^�[
	 * @param flg_init : �����˗��f�[�^�t���O�̒l���i�[����
	 */
	public void setFlg_init(int flg_init) {
		this.flg_init = flg_init;
	}

//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
	/**
	 * �v�Z�� �Q�b�^�[
	 * @return strKeisanSiki : �v�Z���̒l��Ԃ�
	 */
	public String getStrKeisanSiki() {
		return strKeisanSiki;
	}

	/**
	 * �v�Z�� �Z�b�^�[
	 * @param strKeisanSiki : �v�Z���̒l���i�[����
	 */
	public void setStrKeisanSiki(String strKeisanSiki) {
		this.strKeisanSiki = strKeisanSiki;
	}
//add end   -------------------------------------------------------------------------------

//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
	/**
	 * �L�����Z���t���O �Q�b�^�[
	 * @return strKeisanSiki : �L�����Z���t���O�̒l��Ԃ�
	 */
	public int getFlg_cancel() {
		return flg_cancel;
	}

	/**
	 * �L�����Z���t���O �Z�b�^�[
	 * @param strKeisanSiki : �L�����Z���t���O�̒l���i�[����
	 */
	public void setFlg_cancel(int flg_cancel) {
		this.flg_cancel = flg_cancel;
	}
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	/**
	 * ������d �Q�b�^�[
	 * @return strHiju_sui : ������d�̒l��Ԃ�
	 */
	public String getStrHiju_sui() {
		return strHiju_sui;
	}
	/**
	 * ������d �Z�b�^�[
	 * @param strHiju_sui : ������d�̒l���i�[����
	 */
	public void setStrHiju_sui(String strHiju_sui) {
		this.strHiju_sui = strHiju_sui;
	}
	/**
	 * ������d�@�o��FG �Q�b�^�[
	 * @return intHiju_sui_fg :  ������d�@�o��FG�̒l��Ԃ�
	 */
	public int getIntHiju_sui_fg() {
		return intHiju_sui_fg;
	}
	/**
	 * ������d�@�o��FG �Z�b�^�[
	 * @param intHiju_sui_fg : ������d�@�o��FG�̒l���i�[����
	 */
	public void setIntHiju_sui_fg(int intHiju_sui_fg) {
		this.intHiju_sui_fg = intHiju_sui_fg;
	}
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start

// ADD start 20120928 QP@20505 No.24
	// ----------  ���S�x�t���[  ------------
	/**
	 * �S�x�t���[�^�C�g�� �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeTitleNendo() {
		return strFreeTitleNendo;
	}
	/**
	 * �S�x�t���[�^�C�g�� �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeTitleNendo(String _strFreeTitleNendo) {
		this.strFreeTitleNendo = _strFreeTitleNendo;
	}
	/**
	 * �S�x�t���[���e �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeNendo() {
		return strFreeNendo;
	}
	/**
	 * �S�x�t���[���e �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeNendo(String _strFreeNendo) {
		this.strFreeNendo = _strFreeNendo;
	}
	/**
	 * �S�x�t���[-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeNendoFlg() {
		return intFreeNendoFlg;
	}
	/**
	 * �S�x�t���[-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeNendoFlg(int _intFreeNendoFlg) {
		this.intFreeNendoFlg = _intFreeNendoFlg;
	}
	// ----------  �����x�t���[   ------------
	/**
	 * ���x�t���[�^�C�g�� �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeTitleOndo() {
		return strFreeTitleOndo;
	}
	/**
	 * ���x�t���[�^�C�g�� �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeTitleOndo(String _strFreeTitleOndo) {
		this.strFreeTitleOndo = _strFreeTitleOndo;
	}
	/**
	 * ���x�t���[���e �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeOndo() {
		return strFreeOndo;
	}
	/**
	 * ���x�t���[���e �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeOndo(String _strFreeOndo) {
		this.strFreeOndo = _strFreeOndo;
	}
	/**
	 * ���x�t���[-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeOndoFlg() {
		return intFreeOndoFlg;
	}
	/**
	 * ���x�t���[-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeOndoFlg(int _intFreeOndoFlg) {
		this.intFreeOndoFlg = _intFreeOndoFlg;
	}
	// ----------  �����������t���[   ------------
	/**
	 * ���������t���[�^�C�g�� �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeTitleSuibunKasei() {
		return strFreeTitleSuibunKasei;
	}
	/**
	 * ���������t���[�^�C�g�� �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeTitleSuibunKasei(String _strFreeTitleSuibunKasei) {
		this.strFreeTitleSuibunKasei = _strFreeTitleSuibunKasei;
	}
	/**
	 * ���������t���[���e �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeSuibunKasei() {
		return strFreeSuibunKasei;
	}
	/**
	 * ���������t���[���e �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeSuibunKasei(String _strFreeSuibunKasei) {
		this.strFreeSuibunKasei = _strFreeSuibunKasei;
	}
	/**
	 * ���������t���[-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeSuibunKaseiFlg() {
		return intFreeSuibunKaseiFlg;
	}
	/**
	 * ���������t���[-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeSuibunKaseiFlg(int _intFreebunKaseiFlg) {
		this.intFreeSuibunKaseiFlg = _intFreebunKaseiFlg;
	}
	// ----------  ���A���R�[�� �t���[����   ------------
	/**
	 * �A���R�[�� �t���[�^�C�g�� �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeTitleAlchol() {
		return strFreeTitleAlchol;
	}
	/**
	 * �A���R�[�� �t���[�^�C�g�� �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeTitleAlchol(String _strFreeTitleAlchol) {
		this.strFreeTitleAlchol = _strFreeTitleAlchol;
	}
	/**
	 * �A���R�[�� �t���[���e �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public String getStrFreeAlchol() {
		return strFreeAlchol;
	}
	/**
	 * �A���R�[�� �t���[���e �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setStrFreeAlchol(String _strFreeAlchol) {
		this.strFreeAlchol = _strFreeAlchol;
	}
	/**
	 * �A���R�[�� �t���[-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeAlcholFlg() {
		return intFreeAlcholFlg;
	}
	/**
	 * �A���R�[�� �t���[-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeAlcholFlg(int _intFreeAlcholFlg) {
		this.intFreeAlcholFlg = _intFreeAlcholFlg;
	}
	// -------------  �������|�_�Z�x  ---------------------
	/**
	 * �����|�_�Z�x �Q�b�^�[
	 * @return dciJikkoSakusanNodo : �������H���̒l��Ԃ�
	 */
	public BigDecimal getDciJikkoSakusanNodo() {
		return dciJikkoSakusanNodo;
	}
	/**
	 * �����|�_�Z�x �Z�b�^�[
	 * @param _dciJikkoSakusanNodo : �������H���̒l���i�[����
	 */
	public void setDciJikkoSakusanNodo(BigDecimal _dciJikkoSakusanNodo) {
		this.dciJikkoSakusanNodo = _dciJikkoSakusanNodo;
	}
	/**
	 * �����|�_�Z�x-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntJikkoSakusanNodoFlg() {
		return intJikkoSakusanNodoFlg;
	}
	/**
	 * �����|�_�Z�x-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntJikkoSakusanNodoFlg(int _intJikkoSakusanNodoFlg) {
		this.intJikkoSakusanNodoFlg = _intJikkoSakusanNodoFlg;
	}
	// -------------  ���������l�r�f  ---------------------
	/**
	 * �������l�r�f �Q�b�^�[
	 * @return strSuibun : ���������̒l��Ԃ�
	 */
	public BigDecimal getDciSuisoMSG() {
		return dciSuisoMSG;
	}
	/**
	 * �������l�r�f �Z�b�^�[
	 * @param _strSuibun : ���������̒l���i�[����
	 */
	public void setDciSuisoMSG(BigDecimal _dciSuisoMSG) {
		this.dciSuisoMSG = _dciSuisoMSG;
	}
	/**
	 * �������l�r�f-�o��Flg �Q�b�^�[
	 * @return intSuibunFlg : ��������-�o��Flg�̒l��Ԃ�
	 */
	public int getIntSuisoMSGFlg() {
		return intSuisoMSGFlg;
	}
	/**
	 * �������l�r�f-�o��Flg �Z�b�^�[
	 * @param _intSuibunFlg : ��������-�o��Flg�̒l���i�[����
	 */
	public void setIntSuisoMSGFlg(int _intSuisoMSGFlg) {
		this.intSuisoMSGFlg = _intSuisoMSGFlg;
	}
	// -------------  ���t���[�C  ---------------------
	/**
	 * �t���[�C�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle4 : �t���[�C�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle4() {
		return strFreeTitle4;
	}
	/**
	 * �t���[�C�^�C�g�� �Z�b�^�[
	 * @param _strFreeTitle4 : �t���[�C�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle4(String _strFreeTitle4) {
		this.strFreeTitle4 = _strFreeTitle4;
	}
	/**
	 * �t���[�C���e �Q�b�^�[
	 * @return strFreeNaiyo4 : �t���[�C���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo4() {
		return strFreeNaiyo4;
	}
	/**
	 * �t���[�C���e �Z�b�^�[
	 * @param _strFreeNaiyo41 : �t���[�C���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo4(String _strFreeNaiyo4) {
		this.strFreeNaiyo4 = _strFreeNaiyo4;
	}
	/**
	 * �t���[�C-�o��Flg �Q�b�^�[
	 * @return intFreeFlg4 : �t���[�C-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFlg4() {
		return intFreeFlg4;
	}
	/**
	 * �t���[�C-�o��Flg �Z�b�^�[
	 * @param _intFreeFlg4 : �t���[�C-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFlg4(int _intFreeFlg4) {
		this.intFreeFlg4 = _intFreeFlg4;
	}
	// -------------  ���t���[�D  ---------------------
	/**
	 * �t���[�D�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle5 : �t���[�D�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle5() {
		return strFreeTitle5;
	}
	/**
	 * �t���[�D�^�C�g�� �Z�b�^�[
	 * @param strFreeTitle5 : �t���[�D�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle5(String _strFreeTitle5) {
		this.strFreeTitle5 = _strFreeTitle5;
	}
	/**
	 * �t���[�D���e �Q�b�^�[
	 * @return strFreeNaiyo2 : �t���[�D���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo5() {
		return strFreeNaiyo5;
	}
	/**
	 * �t���[�D���e �Z�b�^�[
	 * @param _strFreeNaiyo5 : �t���[�D���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo5(String _strFreeNaiyo5) {
		this.strFreeNaiyo5 = _strFreeNaiyo5;
	}
	/**
	 * �t���[�D-�o��Flg �Q�b�^�[
	 * @return intFreeFlg5 : �t���[�D-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFlg5() {
		return intFreeFlg5;
	}
	/**
	 * �t���[�D-�o��Flg �Z�b�^�[
	 * @param _intFreeFlg5 : �t���[�D-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFlg5(int _intFreeFlg5) {
		this.intFreeFlg5 = _intFreeFlg5;
	}
	// -------------  ���t���[�E  ---------------------
	/**
	 * �t���[�E�^�C�g�� �Q�b�^�[
	 * @return strFreeTitle6 : �t���[�E�^�C�g���̒l��Ԃ�
	 */
	public String getStrFreeTitle6() {
		return strFreeTitle6;
	}
	/**
	 * �t���[�E�^�C�g�� �Z�b�^�[
	 * @param _strFreeTitle6 : �t���[�E�^�C�g���̒l���i�[����
	 */
	public void setStrFreeTitle6(String _strFreeTitle6) {
		this.strFreeTitle6 = _strFreeTitle6;
	}
	/**
	 * �t���[�E���e �Q�b�^�[
	 * @return strFreeNaiyo6 : �t���[�E���e�̒l��Ԃ�
	 */
	public String getStrFreeNaiyo6() {
		return strFreeNaiyo6;
	}
	/**
	 * �t���[�E���e �Z�b�^�[
	 * @param _strFreeNaiyo6 : �t���[�E���e�̒l���i�[����
	 */
	public void setStrFreeNaiyo6(String _strFreeNaiyo6) {
		this.strFreeNaiyo6 = _strFreeNaiyo6;
	}
	/**
	 * �t���[�E-�o��Flg �Q�b�^�[
	 * @return intFreeFlg6 : �t���[�E-�o��Flg�̒l��Ԃ�
	 */
	public int getIntFreeFlg6() {
		return intFreeFlg6;
	}
	/**
	 * �t���[�E-�o��Flg �Z�b�^�[
	 * @param _intFreeFlg6 : �t���[�E-�o��Flg�̒l���i�[����
	 */
	public void setIntFreeFlg6(int _intFreeFlg6) {
		this.intFreeFlg6 = _intFreeFlg6;
	}
// ADD end 20120928 QP@20505 No.24
}