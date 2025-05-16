package jp.co.blueflag.shisaquick.jws.data;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JnlpConnect;
import jp.co.blueflag.shisaquick.jws.common.ModeCtrl;
import jp.co.blueflag.shisaquick.jws.disp.DownloadDisp;
import jp.co.blueflag.shisaquick.jws.disp.ManufacturingSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.TrialMainDisp;
import jp.co.blueflag.shisaquick.jws.manager.*;

/**
 *
 * ���ʃf�[�^�Ǘ��N���X
 *
 */
public class DataCtrl {

	//���̃N���X�ɗB��̃C���X�^���X
	private static DataCtrl instance = new DataCtrl();

	//�e��f�[�^�N���X
	//����e�[�u���f�[�^�ێ�
	private TrialTblData TrialTblData;
	//���[�U�}�X�^�f�[�^�ێ�
	private UserMstData UserMstData;
	//�N���b�v�{�[�h�f�[�^�ێ�
	private ClipboardData ClipboardData;
	//�z��f�[�^�ێ�
	private ArrayData ArrayData;
	//�p�����[�^�f�[�^�ێ�
	private ParamData ParamData;
	//��Ѓf�[�^�ێ�
	private KaishaData kaishaData;
	//�����f�[�^�ێ�
	private BushoData bushoData;
	//���e�����f�[�^�ێ�
// MOD start 20121003 QP@20505 No.24
//	private LiteralData[] literalData = new LiteralData[26];
	private LiteralData[] literalData = new LiteralData[27];
//	// MOD end 20121003 QP@20505 No.24

	//�����f�[�^�ێ�
	private MaterialMstData materialMstData;
	//Result�f�[�^�ێ�
	private ResultData resultData;
	//���b�Z�[�W�R���g���[��
	private MessageCtrl messageCtrl;

	//���[�h�ҏW�N���X
	private ModeCtrl modeCtrl;

	//�����H�����
	private ManufacturingSubDisp manufacturingSubDisp;

	//�_�E�����[�h�p�҂����
	private  DownloadDisp DownloadDisp;

	//Jnlp�ڑ��N���X
	private JnlpConnect jnlpConnect;

	//���Z�����f�[�^
	private ShisanRirekiKanriData shisanRirekiKanriData;


	private ExceptionBase ex;

	/**
	 * �R���X�g���N�^(private)
	 */
	private DataCtrl(){
		try{
			TrialTblData = new TrialTblData();
			UserMstData = new UserMstData();
			ClipboardData = new ClipboardData();
			ArrayData = new ArrayData();
			ParamData = new ParamData();
			kaishaData = new KaishaData();
			bushoData = new BushoData();
			materialMstData = new MaterialMstData();
			resultData = new ResultData();
			modeCtrl = new ModeCtrl();
			manufacturingSubDisp = new ManufacturingSubDisp("�����H�����");
			DownloadDisp = new DownloadDisp();
			shisanRirekiKanriData = new ShisanRirekiKanriData();

			messageCtrl = new MessageCtrl();
			jnlpConnect = new JnlpConnect();

			//���e�����f�[�^�N���X�̐���
			literalData[0]  = new LiteralData("SA600");		//00 : �H������
			literalData[1]  = new LiteralData("SA610");		//01 : �ꊇ�\��
			literalData[2]  = new LiteralData("SA620");		//02 : �W������
			literalData[3]  = new LiteralData("SA630");		//03 : ���[�U
			literalData[4]  = new LiteralData("SA640");		//04 : ��������
			literalData[5]  = new LiteralData("SA650");		//05 : �p�r
			literalData[6]  = new LiteralData("SA660");		//06 : ���i��
			literalData[7]  = new LiteralData("SA670");		//07 : ���
			literalData[8]  = new LiteralData("SA680");		//08 : �����w��
			literalData[9]  = new LiteralData("SA690");		//09 : �S���c��
			literalData[10] = new LiteralData("SA700");	//10 : �������@
			literalData[11] = new LiteralData("SA710");	//11 : �[�U���@
			literalData[12] = new LiteralData("SA720");	//12 : �E�ە��@
			literalData[13] = new LiteralData("SA730");	//13 : �e����
			literalData[14] = new LiteralData("SA740");	//14 : �e��
			literalData[15] = new LiteralData("SA750");	//15 : �P��
			literalData[16] = new LiteralData("SA760");	//16 : �׎p
			literalData[17] = new LiteralData("SA770");	//17 : �戵���x
			literalData[18] = new LiteralData("SA780");	//18 : �ܖ�����
			literalData[19] = new LiteralData("SA850");	//19 : ���No
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			literalData[20] = new LiteralData("SA900");	//20 : �H���p�^�[��
			literalData[21] = new LiteralData("SA910");	//21 : ���i��d
			literalData[22] = new LiteralData("SA920");	//22 : ������d
			literalData[23] = new LiteralData("SA930");	//23 : �������P�t�^�C�v�@�H������
			literalData[24] = new LiteralData("SA940");	//24 : �������Q�t�^�C�v�@�H������
			literalData[25] = new LiteralData("SA950");	//25 : ���̑��E���H�^�C�v�@�H������
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121003 QP@20505 No.24
			literalData[26] = new LiteralData("SA960");	//26 : �����|�_�Z�x
// ADD end 20121003 QP@20505 No.24
		}catch(Exception e){
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���ʃf�[�^�Ǘ��̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			this.messageCtrl.PrintErrMessage(ex);

		}finally{

		}

	}

	/**
	 * �C���X�^���X�擾���\�b�h
	 * @return
	 */
	public static DataCtrl getInstance() {
		return instance;
	}

	/**
	 * ���b�Z�[�W�\������
	 * @param printBexception
	 */
	public void PrintMessage(ExceptionBase printBexception){

			//�f�o�b�O���x���ݒ�
			this.getMessageCtrl().setDebugLevel(this.getResultData().getStrDebuglevel());

			//Result���ʂ�true�̏ꍇ�i�����j
			if ( this.getResultData().isReturnFlgCheck() ) {

				//ExceptionBase�f�[�^���G���[�\��
				this.getMessageCtrl().PrintErrMessage(printBexception);

			}
			//Result���ʂ�false�̏ꍇ�i���s�j
			else {

				//Result�f�[�^���G���[�\��
				this.getMessageCtrl().PrintMessage(this.resultData);

				//Result�f�[�^������
				this.resultData.initResultData();

			}
	}

	/**
	 * ����e�[�u���f�[�^�ێ��N���X �Q�b�^�[
	 * @return ����e�[�u���f�[�^�ێ��N���X
	 */
	public TrialTblData getTrialTblData() {
		return this.TrialTblData;
	}

	/**
	 * ���[�U�}�X�^�f�[�^�ێ��N���X �Q�b�^�[
	 * @return ���[�U�}�X�^�f�[�^�ێ��N���X
	 */
	public UserMstData getUserMstData() {
		return this.UserMstData;
	}

	/**
	 * �N���b�v�{�[�h�f�[�^�ێ��N���X �Q�b�^�[
	 * @return �N���b�v�{�[�h�f�[�^�ێ��N���X
	 */
	public ClipboardData getClipboardData() {
		return this.ClipboardData;
	}

	/**
	 * �z��f�[�^�ێ��N���X �Q�b�^�[
	 * @return �z��f�[�^�ێ��N���X
	 */
	public ArrayData getArrayData() {
		return this.ArrayData;
	}

	/**
	 * �p�����[�^�f�[�^�ێ��N���X �Q�b�^�[
	 * @return �p�����[�^�f�[�^�ێ��N���X
	 */
	public ParamData getParamData() {
		return this.ParamData;
	}

	/**
	 * ��Ѓf�[�^�ێ��N���X �Q�b�^�[
	 * @return ��Ѓf�[�^�ێ��N���X
	 */
	public KaishaData getKaishaData() {
		return this.kaishaData;
	}

	/**
	 * �����f�[�^�ێ��N���X �Q�b�^�[
	 * @return �����f�[�^�ێ��N���X
	 */
	public BushoData getBushoData() {
		return this.bushoData;
	}

	/**
	 * �����f�[�^�ێ��N���X �Q�b�^�[
	 * @return �����f�[�^�ێ��N���X
	 */
	public MaterialMstData getMaterialMstData() {
		return this.materialMstData;
	}

	/**
	 * Result�f�[�^�ێ��N���X�@�Q�b�^�[
	 * @return Result�f�[�^�ێ��N���X
	 */
	public ResultData getResultData() {
		return this.resultData;
	}

	/**
	 * ���b�Z�[�W�R���g���[���N���X �Q�b�^�[
	 * @return ���b�Z�[�W�R���g���[���N���X
	 */
	public MessageCtrl getMessageCtrl() {
		return this.messageCtrl;
	}

	/**
	 * ���[�h�ҏW�N���X �Q�b�^�[
	 * @return ���[�h�ҏW�N���X
	 */
	public ModeCtrl getModeCtrl() {
		return this.modeCtrl;
	}

	/**
	 * �����H����ʃN���X �Q�b�^�[
	 * @return �����H����ʃN���X
	 */
	public ManufacturingSubDisp getManufacturingSubDisp() {
		return manufacturingSubDisp;
	}

	/**
	 * �_�E�����[�h�p�҂���ʃN���X �Q�b�^�[
	 * @return �_�E�����[�h�p�҂���ʃN���X
	 */
	public DownloadDisp getDownloadDisp() {
		return DownloadDisp;
	}

	/**
	 * ���Z�����f�[�^�ێ��N���X �Q�b�^�[
	 * @return ���Z�����f�[�^�ێ��N���X
	 */
	public ShisanRirekiKanriData getShisanRirekiKanriData() {
		return this.shisanRirekiKanriData;
	}

	/**
	 * Jnlp�ڑ��N���X �Q�b�^�[
	 * @return Jnlp�ڑ��N���X
	 */
	public JnlpConnect getJnlpConnect() {
		return this.jnlpConnect;
	}

	/**
	 *  ���e�����f�[�^�ySA600 : �H�������z �Q�b�^�[
	 *  @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataZokusei() { return this.literalData[0]; }

	/**
	 * ���e�����f�[�^�ySA610 : �ꊇ�\���z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataIkatu() { return this.literalData[1]; }

	/**
	 * ���e�����f�[�^�ySA620 : �W�������z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataZyanru() { return this.literalData[2]; }

	/**
	 * ���e�����f�[�^�ySA630 : ���[�U�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataUser() { return this.literalData[3]; }

	/**
	 * ���e�����f�[�^�ySA640 : ���������z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataTokutyo() { return this.literalData[4]; }

	/**
	 * ���e�����f�[�^�ySA650 : �p�r�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataYoto() { return this.literalData[5]; }

	/**
	 * ���e�����f�[�^�ySA660 : ���i�сz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataKakaku() { return this.literalData[6]; }

	/**
	 * ���e�����f�[�^�ySA670 : ��ʁz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataShubetu() { return this.literalData[7]; }

	/**
	 * ���e�����f�[�^�ySA680 : �����w��z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataShosu() { return this.literalData[8]; }

	/**
	 * ���e�����f�[�^�ySA690 : �S���c�Ɓz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataTanto() { return this.literalData[9]; }

	/**
	 * ���e�����f�[�^�ySA700 : �������@�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataSeizo() { return this.literalData[10]; }

	/**
	 * ���e�����f�[�^�ySA710 : �[�U���@�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataZyuten() { return this.literalData[11]; }

	/**
	 * ���e�����f�[�^�ySA720 : �E�ە��@�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataSakin() { return this.literalData[12]; }

	/**
	 * ���e�����f�[�^�ySA730 : �e���ށz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataYoki() { return this.literalData[13]; }

	/**
	 * ���e�����f�[�^�ySA740 : �e�ʁz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataYoryo() { return this.literalData[14]; }

	/**
	 * ���e�����f�[�^�ySA750 : �P�ʁz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataTani() { return this.literalData[15]; }

	/**
	 * ���e�����f�[�^�ySA760 : �׎p�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataNisugata() { return this.literalData[16]; }

	/**
	 * ���e�����f�[�^�ySA770 : �戵���x�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataOndo() { return this.literalData[17]; }

	/**
	 * ���e�����f�[�^�ySA780 : �ܖ����ԁz �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataShomi() { return this.literalData[18]; }

	/**
	 * ���e�����f�[�^�ySA850 : ���No�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataShubetuNo() { return this.literalData[19]; }


//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	/**
	 * ���e�����f�[�^�ySA900 : �H���p�^�[���z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataKoteiPtn() { return this.literalData[20]; }
	/**
	 * ���e�����f�[�^�ySA910 : ���i��d�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataSeihinHiju() { return this.literalData[21]; }
	/**
	 * ���e�����f�[�^�ySA920 : ������d�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataYusoHiju() { return this.literalData[22]; }

	/**
	 * ���e�����f�[�^�ySA930 :  �������P�t�^�C�v�@�H�������z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataKotei_tyomi1() { return this.literalData[23]; }
	/**
	 * ���e�����f�[�^�ySA940 : �������Q�t�^�C�v�@�H�������z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataKotei_tyomi2() { return this.literalData[24]; }
	/**
	 * ���e�����f�[�^�ySA950 : ���̑��E���H�^�C�v�@�H�������z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
	public LiteralData getLiteralDataKotei_sonota() { return this.literalData[25]; }

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

// ADD start 20121003 QP@20505 No.24
	public LiteralData getLiteralDataJikkoSakusanNodo() { return this.literalData[26]; }
	/**
	 * ���e�����f�[�^�ySA960 : �����|�_�Z�x�z �Q�b�^�[
	 * @return ���e�����f�[�^�ێ��N���X
	 */
// ADD end 20121003 QP@20505 No.24

	/**
	 * ����f�[�^�Z�b�^�[
	 */
	public void setTrialTblData(TrialTblData trialTblData) {
		TrialTblData = null;
		Runtime.getRuntime().gc();
		TrialTblData = trialTblData;
	}

}