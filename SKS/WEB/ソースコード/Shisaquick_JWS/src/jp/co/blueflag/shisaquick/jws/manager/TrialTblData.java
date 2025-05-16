package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;

/**
 * 
 * ����e�[�u���f�[�^�ێ�
 * 
 */
public class TrialTblData extends DataBase {

	private PrototypeData ptdtShaisakuHin; // ����i�f�[�^�ێ�
	private MixedData midtHaigou; // �z���f�[�^�ێ�
	private TrialData tldtShisakuRetu; // �����f�[�^�ێ�
	private PrototypeListData pldtShisakuList; // ���샊�X�g�f�[�^�ێ�
	private ManufacturingData mfdtSeizo; // �����H���f�[�^�ێ�
	private ShizaiData shdtShizai; // ���ރf�[�^�ێ�
	private CostMaterialData cmdtGenka; // ���������f�[�^�ێ�
	private ArrayList aryHaigou; // �z���f�[�^�z��
	private ArrayList aryShisakuRetu; // �����f�[�^�z��
	private ArrayList aryShisakuList; // ���샊�X�g�f�[�^�z��
	private ArrayList arySeizo; // �����H���f�[�^�z��
	private ArrayList aryShizai; // ���ރf�[�^�z��
	private ArrayList aryGenka; // ���������f�[�^�z��
	private int intSelectKotei; // �I���H��
	private ArrayList arySelectGenka; // �I������
	private int intMaxKotei; // �ő�H����
	private ArrayList aryMaxGenka; // �ő匴����
	private int intSelectRetu; // �I�������
	private ArrayList arySelectTyui; // �I�𒍈ӎ���No
	private int intMaxRetu; // �ő厎���
	private int[] intOutput; // ��ɕ\���ێ�
	private ArrayList aryCopyKeisan; // �����R�s�[�v�Z�z��
	// private OutputTrialList otltShisakuHyo; //����\�f�[�^�ێ�
	// private OutputSample osltSample; //�T���v���������f�[�^�ێ�
	// private XmlData xdtData; //XML�f�[�^
	private ExceptionBase ex; // �G���[�n���h�����O
	private boolean HenkouFg; // �f�[�^�ύX�t���O

	/**
	 * 0000.�R���X�g���N�^ : �X�[�p�[�N���X�̃R���X�g���N�^���`�B���������s���B
	 * 
	 * @param xdtData
	 *            : XML�f�[�^
	 */
	public TrialTblData() throws ExceptionBase {
		super();

		try {
			// �I������������
			arySelectGenka = new ArrayList();
			arySelectGenka.add(new ArrayList());
			arySelectGenka.add(new ArrayList());

			// �f�[�^�ύX�t���O������
			HenkouFg = false;

			// �G���[�N���X
			this.ex = new ExceptionBase();

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0001_1.�f�[�^�ݒ�i�V�K�j : ����e�[�u���f�[�^�𐶐�����B
	 * 
	 * @param flg
	 *            �@�F�@0=�S�V�K,1=�����R�s�[
	 * @throws ExceptionBase
	 */
	public void setTraialData(int flg) throws ExceptionBase {

		try {
			aryHaigou = new ArrayList();
			aryShisakuRetu = new ArrayList();
			aryShisakuList = new ArrayList();
			arySeizo = new ArrayList();
			aryShizai = new ArrayList();
			aryGenka = new ArrayList();

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX DEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�z DEL end

			/**********************************************************
			 * �@T110�i�[
			 *********************************************************/
			/***************** ����i�f�[�^�֒l�Z�b�g ********************/
			// �S�V�K�̏ꍇ
			if (flg == 0) {
				ptdtShaisakuHin = new PrototypeData();

				// �@�w��H��-���CD
				ptdtShaisakuHin.setIntKaishacd(Integer.parseInt(DataCtrl
						.getInstance().getKaishaData().getArtKaishaCd().get(0)
						.toString()));
				// �@�w��H��-�H��CD
				ptdtShaisakuHin.setIntKojoco(DataCtrl.getInstance()
						.getUserMstData().getIntBushocd());
				// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
				// �@�̐Ӊ��CD
				ptdtShaisakuHin.setIntHansekicd(Integer.parseInt(DataCtrl
						.getInstance().getHansekiData().getArtKaishaCd().get(0)
						.toString()));
				// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 end

				// �����R�s�[�̏ꍇ
			} else {

				// �L�[����
				ptdtShaisakuHin.setDciShisakuUser(null);
				ptdtShaisakuHin.setDciShisakuYear(null);
				ptdtShaisakuHin.setDciShisakuNum(null);

				// �p�~
				ptdtShaisakuHin.setIntHaisi(0);

				// 2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start
				// -------------------------
				// �H���p�^�[��
				ptdtShaisakuHin.setStrPt_kotei(null);
				// 2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End
				// --------------------------

			}

			// �@�O���[�vCD
			ptdtShaisakuHin.setIntGroupcd(DataCtrl.getInstance()
					.getUserMstData().getIntGroupcd());

			// �@�`�[��CD
			ptdtShaisakuHin.setIntTeamcd(DataCtrl.getInstance()
					.getUserMstData().getIntTeamcd());

			// �@�O���[�v��
			ptdtShaisakuHin.setStrGroupNm(DataCtrl.getInstance()
					.getUserMstData().getStrGroupnm());

			// �@�`�[����
			ptdtShaisakuHin.setStrTeamNm(DataCtrl.getInstance()
					.getUserMstData().getStrTeamnm());

			// �@���@����
			ptdtShaisakuHin.setIntSeihoShisaku(0);

			// �@�o�^��ID
			ptdtShaisakuHin.setDciTorokuid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�o�^���t
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());

			// �@�X�V��ID
			ptdtShaisakuHin.setDciKosinid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�X�V���t
			// UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V�������Ńf�[�^���쐬�B
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());
			ptdtShaisakuHin.setStrKosinhi("");

			// �@�o�^�Җ�
			ptdtShaisakuHin.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�X�V�Җ�
			ptdtShaisakuHin.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 2010/02/25 NAKAMURA ADD START--------------
			// �r����Ж�
			ptdtShaisakuHin.setStrHaitaKaishaNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaKaishanm());
			// �r��������
			ptdtShaisakuHin.setStrHaitaBushoNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaBushonm());
			// �r������
			ptdtShaisakuHin.setStrHaitaShimei(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaShimei());
			// 2010/02/25 NAKAMURA ADD END----------------

			// �yQP@20505_No.21�z2012/10/17 TT H.SHIMA ADD Start
			ptdtShaisakuHin.setStrSecret(null);
			// �yQP@20505_No.21�z2012/10/17 TT H.SHIMA ADD End

			/**********************************************************
			 * �@T120�i�[
			 *********************************************************/
			MixedData addMidtHaigou = new MixedData();

			/***************** �z���f�[�^�֒l�Z�b�g *********************/
			// �@�H��CD
			addMidtHaigou.setIntKoteiCd(1);

			// �@�H��SEQ
			addMidtHaigou.setIntKoteiSeq(1);

			// �@�H����
			addMidtHaigou.setIntKoteiNo(1);

			// �@������
			addMidtHaigou.setIntGenryoNo(1);

			// �@���CD
			addMidtHaigou.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());

			// �@����CD
			addMidtHaigou.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());

			// �@�o�^��ID
			addMidtHaigou.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�o�^���t
			addMidtHaigou.setStrTorokuHi(getSysDate());

			// �@�X�V��ID
			addMidtHaigou.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// �@�X�V���t
			addMidtHaigou.setStrKosinHi(getSysDate());

			// �@�o�^�Җ�
			addMidtHaigou.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�X�V�Җ�
			addMidtHaigou.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// �@�z���f�[�^�z��֒ǉ�
			aryHaigou.add(addMidtHaigou);

			/**********************************************************
			 * �@T131�i�[
			 *********************************************************/
			TrialData addTldtShisakuRetu = new TrialData();

			/***************** �����f�[�^�֒l�Z�b�g *******************/
			// �@����SEQ
			addTldtShisakuRetu.setIntShisakuSeq(1);

			// �@����\����
			addTldtShisakuRetu.setIntHyojiNo(1);

			// �@�o�^��ID
			addTldtShisakuRetu.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�o�^���t
			addTldtShisakuRetu.setStrTorokuHi(getSysDate());

			// �@�X�V��ID
			addTldtShisakuRetu.setDciKosinId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�X�V���t
			addTldtShisakuRetu.setStrkosinHi(getSysDate());

			// �@�o�^�Җ�
			addTldtShisakuRetu.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�X�V�Җ�
			addTldtShisakuRetu.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�����f�[�^�z��֒ǉ�
			aryShisakuRetu.add(addTldtShisakuRetu);

			/**********************************************************
			 * �@T132�i�[
			 *********************************************************/
			PrototypeListData addPldtShisakuList = new PrototypeListData();

			/***************** ���샊�X�g�f�[�^�֒l�Z�b�g *****************/
			// �@����SEQ
			addPldtShisakuList.setIntShisakuSeq(1);

			// �@�H��CD
			addPldtShisakuList.setIntKoteiCd(1);

			// �@�H��SEQ
			addPldtShisakuList.setIntKoteiSeq(1);

			// �@�o�^��ID
			addPldtShisakuList.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�o�^���t
			addPldtShisakuList.setStrTorokuHi(getSysDate());

			// �@�X�V��ID
			addPldtShisakuList.setDciKosinId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�X�V���t
			addPldtShisakuList.setStrKosinHi(getSysDate());

			// �@�o�^�Җ�
			addPldtShisakuList.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�X�V�Җ�
			addPldtShisakuList.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@���샊�X�g�f�[�^�z��֒ǉ�
			aryShisakuList.add(addPldtShisakuList);

			/**********************************************************
			 * �@T133�i�[
			 *********************************************************/
			arySeizo = new ArrayList();

			/**********************************************************
			 * �@T140�i�[
			 *********************************************************/
			ShizaiData addShdtShizai = new ShizaiData();

			/***************** ���ރf�[�^�֒l�Z�b�g *********************/
			// �@����SEQ
			addShdtShizai.setIntShizaiSeq(1);

			// �@�\����
			addShdtShizai.setIntHyojiNo(1);

			// �@�o�^��ID
			addShdtShizai.setDciTorokuId(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());

			// �@�o�^���t
			addShdtShizai.setStrTorokuHi(getSysDate());

			// �@�X�V��ID
			addShdtShizai.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// �@�X�V���t
			addShdtShizai.setStrKosinHi(getSysDate());

			// �@�o�^�Җ�
			addShdtShizai.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// �@�X�V�Җ�
			addShdtShizai.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// ���ރf�[�^�z��֒ǉ�
			aryShizai.add(addShdtShizai);

			/**********************************************************
			 * �@T141�i�[
			 *********************************************************/
			CostMaterialData addCmdtGenka = new CostMaterialData();

			/***************** ���������f�[�^�֒l�Z�b�g ******************/
			// �@����SEQ
			addCmdtGenka.setIntShisakuSeq(1);

			// �@�o�^��ID
			addCmdtGenka.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// �@�o�^���t
			addCmdtGenka.setStrTorokuHi(getSysDate());

			// �@�X�V��ID
			addCmdtGenka.setDciKosinId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());

			// �@�X�V���t
			addCmdtGenka.setStrKosinHi(getSysDate());

			// �@�o�^�Җ�
			addCmdtGenka.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// �@�X�V�Җ�
			addCmdtGenka.setStrKosinNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());

			// ���������f�[�^�z��֒ǉ�
			aryGenka.add(addCmdtGenka);

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̃f�[�^�ݒ�i�V�K�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0001.�f�[�^�ݒ�i�ڍ�or���@�R�s�[�j : XML�f�[�^��莎��e�[�u���f�[�^�𐶐�����B
	 * 
	 * @param xdtData
	 *            : XML�f�[�^
	 * @throws ExceptionBase
	 */
	public void setTraialData(XmlData xdtSetXml) throws ExceptionBase {

		try {
			ptdtShaisakuHin = new PrototypeData();
			aryHaigou = new ArrayList();
			aryShisakuRetu = new ArrayList();
			aryShisakuList = new ArrayList();
			arySeizo = new ArrayList();
			aryShizai = new ArrayList();
			aryGenka = new ArrayList();

			XmlData xdtData = xdtSetXml;

			/**********************************************************
			 * �@T110�i�[
			 *********************************************************/
			// �@ID�i�[
			String strKinoId = "tr_shisakuhin";

			// �@�S�̔z��擾
			ArrayList t110 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT110 = (ArrayList) t110.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT110.size(); i++) {
				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT110.get(i))
						.get(0));

				// �@�f�[�^�i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** ����i�f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						ptdtShaisakuHin
								.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						ptdtShaisakuHin
								.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						ptdtShaisakuHin
								.setDciShisakuNum(checkNullDecimal(recVal));

						// �@�˗��ԍ�
					}
					if (recNm == "no_irai") {
						ptdtShaisakuHin.setStrIrai(checkNullString(recVal));

						// �@�i��
					}
					if (recNm == "nm_hin") {
						ptdtShaisakuHin.setStrHinnm(checkNullString(recVal));
						// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
						// �@�̐Ӊ��CD
					}
					if (recNm == "cd_hanseki") {
						ptdtShaisakuHin.setIntHansekicd(checkNullInt(recVal));
						// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start

						// �@�w��H��-���CD
					}
					if (recNm == "cd_kaisha") {
						ptdtShaisakuHin.setIntKaishacd(checkNullInt(recVal));

						// �@�w��H��-�H��CD
					}
					if (recNm == "cd_kojo") {
						ptdtShaisakuHin.setIntKojoco(checkNullInt(recVal));

						// �@���CD
					}
					if (recNm == "cd_shubetu") {
						ptdtShaisakuHin.setStrShubetu(checkNullString(recVal));

						// �@���No
					}
					if (recNm == "no_shubetu") {
						ptdtShaisakuHin
								.setStrShubetuNo(checkNullString(recVal));

						// �@�O���[�vCD
					}
					if (recNm == "cd_group") {
						ptdtShaisakuHin.setIntGroupcd(checkNullInt(recVal));

						// �@�`�[��CD
					}
					if (recNm == "cd_team") {
						ptdtShaisakuHin.setIntTeamcd(checkNullInt(recVal));

						// �@�O���[�v��
					}
					if (recNm == "nm_group") {
						ptdtShaisakuHin.setStrGroupNm(checkNullString(recVal));

						// �@�`�[����
					}
					if (recNm == "nm_team") {
						ptdtShaisakuHin.setStrTeamNm(checkNullString(recVal));

						// �@�ꊇ�\��CD
					}
					if (recNm == "cd_ikatu") {
						ptdtShaisakuHin.setStrIkatu(checkNullString(recVal));

						// �@�W������CD
					}
					if (recNm == "cd_genre") {
						ptdtShaisakuHin.setStrZyanru(checkNullString(recVal));

						// �@���[�UCD
					}
					if (recNm == "cd_user") {
						ptdtShaisakuHin.setStrUsercd(checkNullString(recVal));

						// �@��������
					}
					if (recNm == "tokuchogenryo") {
						ptdtShaisakuHin.setStrTokutyo(checkNullString(recVal));

						// �@�p�r
					}
					if (recNm == "youto") {
						ptdtShaisakuHin.setStrYoto(checkNullString(recVal));

						// �@���i��CD
					}
					if (recNm == "cd_kakaku") {
						ptdtShaisakuHin.setStrKakaku(checkNullString(recVal));

						// �@�S���c��CD
					}
					if (recNm == "cd_eigyo") {
						ptdtShaisakuHin
								.setStrTantoEigyo(checkNullString(recVal));

						// �@�������@CD
					}
					if (recNm == "cd_hoho") {
						ptdtShaisakuHin.setStrSeizocd(checkNullString(recVal));

						// �@�[�U���@CD
					}
					if (recNm == "cd_juten") {
						ptdtShaisakuHin.setStrZyutencd(checkNullString(recVal));

						// �@�E�ە��@
					}
					if (recNm == "hoho_sakin") {
						ptdtShaisakuHin.setStrSakin(checkNullString(recVal));

						// �@�e��E���
					}
					if (recNm == "youki") {
						ptdtShaisakuHin
								.setStrYokihozai(checkNullString(recVal));

						// �@�e��
					}
					if (recNm == "yoryo") {
						ptdtShaisakuHin.setStrYoryo(checkNullString(recVal));

						// �@�e�ʒP��CD
					}
					if (recNm == "cd_tani") {
						ptdtShaisakuHin.setStrTani(checkNullString(recVal));

						// �@���萔
					}
					if (recNm == "su_iri") {
						ptdtShaisakuHin.setStrIrisu(checkNullString(recVal));

						// �@�戵���xCD
					}
					if (recNm == "cd_ondo") {
						ptdtShaisakuHin.setStrOndo(checkNullString(recVal));

						// �@�ܖ�����
					}
					if (recNm == "shomikikan") {
						ptdtShaisakuHin.setStrShomi(checkNullString(recVal));

						// �@����
					}
					if (recNm == "genka") {
						ptdtShaisakuHin.setStrGenka(checkNullString(recVal));

						// �@����
					}
					if (recNm == "baika") {
						ptdtShaisakuHin.setStrBaika(checkNullString(recVal));

						// �@�z�蕨��
					}
					if (recNm == "buturyo") {
						ptdtShaisakuHin.setStrSotei(checkNullString(recVal));

						// �@��������
					}
					if (recNm == "dt_hatubai") {
						ptdtShaisakuHin.setStrHatubai(checkNullString(recVal));

						// �@�v�攄��
					}
					if (recNm == "uriage_k") {
						ptdtShaisakuHin
								.setStrKeikakuUri(checkNullString(recVal));

						// �@�v�旘�v
					}
					if (recNm == "rieki_k") {
						ptdtShaisakuHin
								.setStrKeikakuRie(checkNullString(recVal));

						// �@�̔��㔄��
					}
					if (recNm == "uriage_h") {
						ptdtShaisakuHin
								.setStrHanbaigoUri(checkNullString(recVal));

						// �@�̔��㗘�v
					}
					if (recNm == "rieki_h") {
						ptdtShaisakuHin
								.setStrHanbaigoRie(checkNullString(recVal));

						// �@�׎pCD
					}
					if (recNm == "cd_nisugata") {
						ptdtShaisakuHin
								.setStrNishugata(checkNullString(recVal));

						// �@������
					}
					if (recNm == "memo") {
						ptdtShaisakuHin.setStrSogo(checkNullString(recVal));

						// �@�����w��
					}
					if (recNm == "keta_shosu") {
						ptdtShaisakuHin.setStrShosu(checkNullString(recVal));

						// �@�p�~��
					}
					if (recNm == "kbn_haishi") {
						ptdtShaisakuHin.setIntHaisi(checkNullInt(recVal));

						// �@�r��
					}
					if (recNm == "kbn_haita") {
						ptdtShaisakuHin.setDciHaita(checkNullDecimal(recVal));

						// �@���@����
					}
					if (recNm == "seq_shisaku") {
						ptdtShaisakuHin
								.setIntSeihoShisaku(checkNullInt(recVal));

						// �@���상��
					}
					if (recNm == "memo_shisaku") {
						ptdtShaisakuHin
								.setStrShisakuMemo(checkNullString(recVal));

						// �@���ӎ����\��
					}
					if (recNm == "flg_chui") {
						// �l���󕶎��łȂ��ꍇ
						if (!recVal.equals("")) {
							ptdtShaisakuHin.setIntChuiFg(Integer
									.parseInt(recVal));
						}

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						ptdtShaisakuHin
								.setDciTorokuid(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						ptdtShaisakuHin.setStrTorokuhi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						ptdtShaisakuHin.setDciKosinid(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						ptdtShaisakuHin.setStrKosinhi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						ptdtShaisakuHin.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						ptdtShaisakuHin.setStrKosinNm(checkNullString(recVal));

						// 2010/02/25 NAKAMURA ADD
						// START----------------------------
						// �@�r����Ж�
					}
					if (recNm == "nm_kaisha_haita") {
						ptdtShaisakuHin
								.setStrHaitaKaishaNm(checkNullString(recVal));
						// �@�r��������
					}
					if (recNm == "nm_busho_haita") {
						ptdtShaisakuHin
								.setStrHaitaBushoNm(checkNullString(recVal));
						// �@�r������
					}
					if (recNm == "nm_user_haita") {
						ptdtShaisakuHin
								.setStrHaitaShimei(checkNullString(recVal));
					}
					// 2010/02/25 NAKAMURA ADD END------------------------------

					// �yQP@00342�z
					if (recNm == "nm_eigyo_tanto") {
						ptdtShaisakuHin
								.setStrNmEigyoTanto(checkNullString(recVal));
					}

					// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					if (recNm == "pt_kotei") {
						ptdtShaisakuHin.setStrPt_kotei(checkNullString(recVal));
					}
					// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

					// �yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
					if (recNm == "flg_secret") {
						ptdtShaisakuHin.setStrSecret(checkNullString(recVal));
					}
					// �yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

				}
				recData.clear();
			}
			tableT110.clear();
			t110.clear();

			/**********************************************************
			 * �@T120�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_haigo";

			// �@�S�̔z��擾
			ArrayList t120 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT120 = (ArrayList) t120.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT120.size(); i++) {

				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT120.get(i))
						.get(0));
				// �@�z���f�[�^������
				midtHaigou = new MixedData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** �z���f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						midtHaigou.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						midtHaigou.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						midtHaigou.setDciShisakuNum(checkNullDecimal(recVal));

						// �@�H��CD
					}
					if (recNm == "cd_kotei") {
						midtHaigou.setIntKoteiCd(checkNullInt(recVal));

						// �@�H��SEQ
					}
					if (recNm == "seq_kotei") {
						midtHaigou.setIntKoteiSeq(checkNullInt(recVal));

						// �@�H����
					}
					if (recNm == "nm_kotei") {
						midtHaigou.setStrKouteiNm(checkNullString(recVal));

						// �@�H������
					}
					if (recNm == "zoku_kotei") {
						midtHaigou.setStrKouteiZokusei(checkNullString(recVal));

						// �@�H����
					}
					if (recNm == "sort_kotei") {
						midtHaigou.setIntKoteiNo(checkNullInt(recVal));

						// �@������
					}
					if (recNm == "sort_genryo") {
						midtHaigou.setIntGenryoNo(checkNullInt(recVal));

						// �@����CD
					}
					if (recNm == "cd_genryo") {
						midtHaigou.setStrGenryoCd(checkNullString(recVal));

						// �@���CD
					}
					if (recNm == "cd_kaisha") {
						midtHaigou.setIntKaishaCd(checkNullInt(recVal));

						// �@����CD
					}
					if (recNm == "cd_busho") {
						midtHaigou.setIntBushoCd(checkNullInt(recVal));

						// �@��������
					}
					if (recNm == "nm_genryo") {
						midtHaigou.setStrGenryoNm(checkNullString(recVal));

						// �@�P��
					}
					if (recNm == "tanka") {
						midtHaigou.setDciTanka(checkNullDecimal(recVal));

						// �@����
					}
					if (recNm == "budomari") {
						midtHaigou.setDciBudomari(checkNullDecimal(recVal));

						// �@���ܗL��
					}
					if (recNm == "ritu_abura") {
						midtHaigou.setDciGanyuritu(checkNullDecimal(recVal));

						// �@�|�_
					}
					if (recNm == "ritu_sakusan") {
						midtHaigou.setDciSakusan(checkNullDecimal(recVal));

						// �@�H��
					}
					if (recNm == "ritu_shokuen") {
						midtHaigou.setDciShokuen(checkNullDecimal(recVal));
						// ADD start 20121002 QP@20505 No.24
						// �@�l�r�f
					}
					if (recNm == "ritu_msg") {
						midtHaigou.setDciMsg(checkNullDecimal(recVal));
						// ADD end 20121002 QP@20505 No.24
						// �@���_
					}
					if (recNm == "ritu_sousan") {
						midtHaigou.setDciSosan(checkNullDecimal(recVal));

						// �@�F
					}
					if (recNm == "color") {
						midtHaigou.setStrIro(checkNullString(recVal));

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						midtHaigou.setDciTorokuId(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						midtHaigou.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						midtHaigou.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						midtHaigou.setStrKosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						midtHaigou.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						midtHaigou.setStrKosinNm(checkNullString(recVal));

					}

					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_�V�T�N�C�b�N���� No.31
					// �@�X�V�Җ�
					if (recNm == "ma_budomari") {
						midtHaigou.setDciMaBudomari(checkNullDecimal(recVal));
					}
					// add end
					// -------------------------------------------------------------------------------

				}
				// �@�z���f�[�^�z��֒ǉ�
				aryHaigou.add(midtHaigou);
				recData.clear();
			}
			tableT120.clear();
			t120.clear();

			/**********************************************************
			 * �@T131�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_shisaku";

			// �@�S�̔z��擾
			ArrayList t131 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT131 = (ArrayList) t131.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT131.size(); i++) {

				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT131.get(i))
						.get(0));
				// �@�����f�[�^������
				tldtShisakuRetu = new TrialData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** �����f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						tldtShisakuRetu
								.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						tldtShisakuRetu
								.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						tldtShisakuRetu
								.setDciShisakuNum(checkNullDecimal(recVal));

						// �@����SEQ
					}
					if (recNm == "seq_shisaku") {
						tldtShisakuRetu.setIntShisakuSeq(checkNullInt(recVal));

						// �@����\����
					}
					if (recNm == "sort_shisaku") {
						tldtShisakuRetu.setIntHyojiNo(checkNullInt(recVal));

						// �@���ӎ���NO
					}
					if (recNm == "no_chui") {
						tldtShisakuRetu.setStrTyuiNo(checkNullString(recVal));

						// �@�T���v��NO�i���́j
					}
					if (recNm == "nm_sample") {
						tldtShisakuRetu.setStrSampleNo(checkNullString(recVal));

						// �@����
					}
					if (recNm == "memo") {
						tldtShisakuRetu.setStrMemo(checkNullString(recVal));

						// �@���Flg
					}
					if (recNm == "flg_print") {
						tldtShisakuRetu.setIntInsatuFlg(checkNullInt(recVal));

						// �@�����v�ZFlg
					}
					if (recNm == "flg_auto") {
						tldtShisakuRetu.setIntZidoKei(checkNullInt(recVal));

						// �@�������ZNo
					}
					if (recNm == "no_shisan") {
						tldtShisakuRetu.setIntGenkaShisan(checkNullInt(recVal));

						// �@���@No-1
					}
					if (recNm == "no_seiho1") {
						tldtShisakuRetu.setStrSeihoNo1(checkNullString(recVal));

						// �@���@No-2
					}
					if (recNm == "no_seiho2") {
						tldtShisakuRetu.setStrSeihoNo2(checkNullString(recVal));

						// �@���@No-3
					}
					if (recNm == "no_seiho3") {
						tldtShisakuRetu.setStrSeihoNo3(checkNullString(recVal));

						// �@���@No-4
					}
					if (recNm == "no_seiho4") {
						tldtShisakuRetu.setStrSeihoNo4(checkNullString(recVal));

						// �@���@No-5
					}
					if (recNm == "seiho_no5") {
						tldtShisakuRetu.setStrSeihoNo5(checkNullString(recVal));

						// �@���_
					}
					if (recNm == "ritu_sousan") {
						tldtShisakuRetu.setDciSosan(checkNullDecimal(recVal));

						// �@���_-�o��Flg
					}
					if (recNm == "flg_sousan") {
						tldtShisakuRetu.setIntSosanFlg(checkNullInt(recVal));

						// �@�H��
					}
					if (recNm == "ritu_shokuen") {
						tldtShisakuRetu.setDciShokuen(checkNullDecimal(recVal));
						// ADD start 20121002 QP@20505 No.24
						// �@�l�r�f
					}
					if (recNm == "ritu_msg") {
						tldtShisakuRetu.setDciMsg(checkNullDecimal(recVal));
						// ADD end 20121002 QP@20505 No.24
						// �@�H��-�o��Flg
					}
					if (recNm == "flg_shokuen") {
						tldtShisakuRetu.setIntShokuenFlg(checkNullInt(recVal));

						// �@�������_�x
					}
					if (recNm == "sando_suiso") {
						tldtShisakuRetu
								.setDciSuiSando(checkNullDecimal(recVal));

						// �@�������_�x-�o��Flg
					}
					if (recNm == "flg_sando_suiso") {
						tldtShisakuRetu.setIntSuiSandoFlg(checkNullInt(recVal));

						// �@�������H��
					}
					if (recNm == "shokuen_suiso") {
						tldtShisakuRetu
								.setDciSuiShokuen(checkNullDecimal(recVal));

						// �@�������H��-�o��Flg
					}
					if (recNm == "flg_shokuen_suiso") {
						tldtShisakuRetu
								.setIntSuiShokuenFlg(checkNullInt(recVal));

						// �@�������|�_
					}
					if (recNm == "sakusan_suiso") {
						tldtShisakuRetu
								.setDciSuiSakusan(checkNullDecimal(recVal));

						// �@�������|�_-�o��Flg
					}
					if (recNm == "flg_sakusan_suiso") {
						tldtShisakuRetu
								.setIntSuiSakusanFlg(checkNullInt(recVal));

						// �@���x
					}
					if (recNm == "toudo") {
						tldtShisakuRetu.setStrToudo(checkNullString(recVal));

						// �@���x-�o��Flg
					}
					if (recNm == "flg_toudo") {
						tldtShisakuRetu.setIntToudoFlg(checkNullInt(recVal));

						// �@�S�x
					}
					if (recNm == "nendo") {
						tldtShisakuRetu.setStrNendo(checkNullString(recVal));

						// �@�S�x-�o��Flg
					}
					if (recNm == "flg_nendo") {
						tldtShisakuRetu.setIntNendoFlg(checkNullInt(recVal));

						// �@���x
					}
					if (recNm == "ondo") {
						tldtShisakuRetu.setStrOndo(checkNullString(recVal));

						// �@���x-�o��Flg
					}
					if (recNm == "flg_ondo") {
						tldtShisakuRetu.setIntOndoFlg(checkNullInt(recVal));

						// �@PH
					}
					if (recNm == "ph") {
						tldtShisakuRetu.setStrPh(checkNullString(recVal));

						// �@PH - �o��Flg
					}
					if (recNm == "flg_ph") {
						tldtShisakuRetu.setIntPhFlg(checkNullInt(recVal));

						// �@���_�F����
					}
					if (recNm == "ritu_sousan_bunseki") {
						tldtShisakuRetu
								.setStrSosanBunseki(checkNullString(recVal));

						// �@���_�F����-�o��Flg
					}
					if (recNm == "flg_sousan_bunseki") {
						tldtShisakuRetu
								.setIntSosanBunsekiFlg(checkNullInt(recVal));

						// �@�H���F����
					}
					if (recNm == "ritu_shokuen_bunseki") {
						tldtShisakuRetu
								.setStrShokuenBunseki(checkNullString(recVal));

						// �@�H���F����-�o��Flg
					}
					if (recNm == "flg_shokuen_bunseki") {
						tldtShisakuRetu
								.setIntShokuenBunsekiFlg(checkNullInt(recVal));

						// �@��d
					}
					if (recNm == "hiju") {
						tldtShisakuRetu.setStrHizyu(checkNullString(recVal));

						// �@��d-�o��Flg
					}
					if (recNm == "flg_hiju") {
						tldtShisakuRetu.setIntHizyuFlg(checkNullInt(recVal));

						// �@��������
					}
					if (recNm == "suibun_kasei") {
						tldtShisakuRetu.setStrSuibun(checkNullString(recVal));

						// �@��������-�o��Flg
					}
					if (recNm == "flg_suibun_kasei") {
						tldtShisakuRetu.setIntSuibunFlg(checkNullInt(recVal));

						// �@�A���R�[��
					}
					if (recNm == "alcohol") {
						tldtShisakuRetu.setStrArukoru(checkNullString(recVal));

						// �@�A���R�[��-�o��Flg
					}
					if (recNm == "flg_alcohol") {
						tldtShisakuRetu.setIntArukoruFlg(checkNullInt(recVal));

						// �@�쐬����
					}
					if (recNm == "memo_sakusei") {
						tldtShisakuRetu
								.setStrSakuseiMemo(checkNullString(recVal));

						// �@�쐬����-�o��Flg
					}
					if (recNm == "flg_memo") {
						tldtShisakuRetu
								.setIntSakuseiMemoFlg(checkNullInt(recVal));

						// �@�]��
					}
					if (recNm == "hyoka") {
						tldtShisakuRetu.setStrHyoka(checkNullString(recVal));

						// �@�]��-�o��Flg
					}
					if (recNm == "flg_hyoka") {
						tldtShisakuRetu.setIntHyokaFlg(checkNullInt(recVal));

						// �@�t���[�@�^�C�g��
					}
					if (recNm == "free_title1") {
						tldtShisakuRetu
								.setStrFreeTitle1(checkNullString(recVal));

						// �@�t���[�@���e
					}
					if (recNm == "free_value1") {
						tldtShisakuRetu
								.setStrFreeNaiyo1(checkNullString(recVal));

						// �@�t���[�@-�o��Flg
					}
					if (recNm == "flg_free1") {
						tldtShisakuRetu.setIntFreeFlg(checkNullInt(recVal));

						// �@�t���[�A�^�C�g��
					}
					if (recNm == "free_title2") {
						tldtShisakuRetu
								.setStrFreeTitle2(checkNullString(recVal));

						// �@�t���[�A���e
					}
					if (recNm == "free_value2") {
						tldtShisakuRetu
								.setStrFreeNaiyo2(checkNullString(recVal));

						// �@�t���[�A-�o��Flg
					}
					if (recNm == "flg_free2") {
						tldtShisakuRetu.setIntFreeFl2(checkNullInt(recVal));

						// �@�t���[�B�^�C�g��
					}
					if (recNm == "free_title3") {
						tldtShisakuRetu
								.setStrFreeTitle3(checkNullString(recVal));

						// �@�t���[�B���e
					}
					if (recNm == "free_value3") {
						tldtShisakuRetu
								.setStrFreeNaiyo3(checkNullString(recVal));

						// �@�t���[�B-�o��Flg
					}
					if (recNm == "flg_free3") {
						tldtShisakuRetu.setIntFreeFl3(checkNullInt(recVal));

						// �@������t
					}
					if (recNm == "dt_shisaku") {
						tldtShisakuRetu
								.setStrShisakuHi(checkNullString(recVal));

						// �d��d��
					}
					if (recNm == "juryo_shiagari_g") {
						tldtShisakuRetu
								.setDciShiagari(checkNullDecimal(recVal));

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						tldtShisakuRetu.setDciTorokuId(new BigDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						tldtShisakuRetu.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						tldtShisakuRetu.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						tldtShisakuRetu.setStrkosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						tldtShisakuRetu.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						tldtShisakuRetu.setStrKosinNm(checkNullString(recVal));

					}
					// 2009/10/26 TT.Y.NISHIGAWA ADD START [�������Z�F���Z�˗�flg]
					// �@���Z�˗�flg
					if (recNm == "flg_shisanIrai") {
						tldtShisakuRetu.setFlg_shisanIrai(Integer
								.parseInt(recVal));
						// �@���Z�˗�flg
						if (Integer.parseInt(recVal) == 1) {
							tldtShisakuRetu.setFlg_init(1);
						}
					}
					// 2009/10/26 TT.Y.NISHIGAWA ADD END [�������Z�F���Z�˗�flg]
					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_�V�T�N�C�b�N���� No.7
					if (recNm == "siki_keisan") {
						tldtShisakuRetu.setStrKeisanSiki(recVal);
					}
					// add end
					// -------------------------------------------------------------------------------
					// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					// ������d
					if (recNm == "hiju_sui") {
						tldtShisakuRetu.setStrHiju_sui(checkNullString(recVal));
					}
					// ������d�@�o��FG
					if (recNm == "flg_hiju_sui") {
						tldtShisakuRetu.setIntHiju_sui_fg(checkNullInt(recVal));
					}
					// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
					// ADD start 20121009 QP@20505 No.24
					// �@�������� �t���[ �^�C�g��
					if (recNm == "freetitle_suibun_kasei") {
						tldtShisakuRetu
								.setStrFreeTitleSuibunKasei(checkNullString(recVal));

						// �@�������� �t���[ ���e
					}
					if (recNm == "free_suibun_kasei") {
						tldtShisakuRetu
								.setStrFreeSuibunKasei(checkNullString(recVal));

						// �@�������� �t���[ -�o��Flg
					}
					if (recNm == "flg_freeSuibunKasei") {
						tldtShisakuRetu
								.setIntFreeSuibunKaseiFlg(checkNullInt(recVal));

						// �@�A���R�[�� �t���[ �^�C�g��
					}
					if (recNm == "freetitle_alcohol") {
						tldtShisakuRetu
								.setStrFreeTitleAlchol(checkNullString(recVal));

						// �@�A���R�[�� �t���[ ���e
					}
					if (recNm == "free_alcohol") {
						tldtShisakuRetu
								.setStrFreeAlchol(checkNullString(recVal));

						// �@�A���R�[�� �t���[ -�o��Flg
					}
					if (recNm == "flg_freeAlchol") {
						tldtShisakuRetu
								.setIntFreeAlcholFlg(checkNullInt(recVal));

						// �@�����|�_�Z�x
					}
					if (recNm == "jikkoSakusanNodo") {
						tldtShisakuRetu
								.setDciJikkoSakusanNodo(checkNullDecimal(recVal));

						// �@�����|�_�Z�x-�o��Flg
					}
					if (recNm == "flg_jikkoSakusanNodo") {
						tldtShisakuRetu
								.setIntJikkoSakusanNodoFlg(checkNullInt(recVal));

						// �@�������l�r�f
					}
					if (recNm == "msg_suiso") {
						tldtShisakuRetu
								.setDciSuisoMSG(checkNullDecimal(recVal));

						// �@�������l�r�f-�o��Flg
					}
					if (recNm == "flg_msg_suiso") {
						tldtShisakuRetu.setIntSuisoMSGFlg(checkNullInt(recVal));

						// �@�t���[�S�x �^�C�g��
					}
					if (recNm == "freetitle_nendo") {
						tldtShisakuRetu
								.setStrFreeTitleNendo(checkNullString(recVal));

						// �@�t���[�S�x
					}
					if (recNm == "free_nendo") {
						tldtShisakuRetu
								.setStrFreeNendo(checkNullString(recVal));

						// �@�t���[�S�x-�o��Flg
					}
					if (recNm == "flg_freeNendo") {
						tldtShisakuRetu
								.setIntFreeNendoFlg(checkNullInt(recVal));

						// �@�t���[���x �^�C�g��
					}
					if (recNm == "freetitle_ondo") {
						tldtShisakuRetu
								.setStrFreeTitleOndo(checkNullString(recVal));

						// �@�t���[���x
					}
					if (recNm == "free_ondo") {
						tldtShisakuRetu.setStrFreeOndo(checkNullString(recVal));

						// //�@�t���[���x-�o��Flg
						// }if(recNm == "flg_freeOndo"){
						// tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));

						// �@�t���[�C�^�C�g��
					}
					if (recNm == "free_title4") {
						tldtShisakuRetu
								.setStrFreeTitle4(checkNullString(recVal));

						// �@�t���[�C���e
					}
					if (recNm == "free_value4") {
						tldtShisakuRetu
								.setStrFreeNaiyo4(checkNullString(recVal));

						// �@�t���[�C-�o��Flg
					}
					if (recNm == "flg_free4") {
						tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));

						// �@�t���[�D�^�C�g��
					}
					if (recNm == "free_title5") {
						tldtShisakuRetu
								.setStrFreeTitle5(checkNullString(recVal));

						// �@�t���[�D���e
					}
					if (recNm == "free_value5") {
						tldtShisakuRetu
								.setStrFreeNaiyo5(checkNullString(recVal));

						// �@�t���[�D-�o��Flg
					}
					if (recNm == "flg_free5") {
						tldtShisakuRetu.setIntFreeFlg5(checkNullInt(recVal));

						// �@�t���[�E�^�C�g��
					}
					if (recNm == "free_title6") {
						tldtShisakuRetu
								.setStrFreeTitle6(checkNullString(recVal));

						// �@�t���[�E���e
					}
					if (recNm == "free_value6") {
						tldtShisakuRetu
								.setStrFreeNaiyo6(checkNullString(recVal));

						// �@�t���[�E-�o��Flg
					}
					if (recNm == "flg_free6") {
						tldtShisakuRetu.setIntFreeFlg6(checkNullInt(recVal));

					}
					// ADD end 20121009 QP@20505 No.24
				}
				// �@�����f�[�^�z��֒ǉ�
				aryShisakuRetu.add(tldtShisakuRetu);
				recData.clear();
			}
			tableT131.clear();
			t131.clear();

			/**********************************************************
			 * �@T132�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_shisaku_list";

			// �@�S�̔z��擾
			ArrayList t132 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT132 = (ArrayList) t132.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT132.size(); i++) {

				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT132.get(i))
						.get(0));
				// �@���샊�X�g�f�[�^������
				pldtShisakuList = new PrototypeListData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** ���샊�X�g�f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						pldtShisakuList
								.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						pldtShisakuList
								.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						pldtShisakuList
								.setDciShisakuNum(checkNullDecimal(recVal));

						// �@����SEQ
					}
					if (recNm == "seq_shisaku") {
						pldtShisakuList.setIntShisakuSeq(checkNullInt(recVal));

						// �@�H��CD
					}
					if (recNm == "cd_kotei") {
						pldtShisakuList.setIntKoteiCd(checkNullInt(recVal));

						// �@�H��SEQ
					}
					if (recNm == "seq_kotei") {
						pldtShisakuList.setIntKoteiSeq(checkNullInt(recVal));

						// �@��
					}
					if (recNm == "quantity") {
						pldtShisakuList.setDciRyo(checkNullDecimal(recVal));

						// �@�F
					}
					if (recNm == "color") {
						pldtShisakuList.setStrIro(checkNullString(recVal));

						// ADD start 20120914 QP@20505 No.1
						// �@�H���d��d��
					}
					if (recNm == "juryo_shiagari_seq") {
						pldtShisakuList
								.setDciKouteiShiagari(checkNullDecimal(recVal));
						// ADD end 20120914 QP@20505 No.1

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						pldtShisakuList
								.setDciTorokuId(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						pldtShisakuList.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						pldtShisakuList.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						pldtShisakuList.setStrKosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "id_koshin") {
						pldtShisakuList.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "dt_koshin") {
						pldtShisakuList.setStrKosinNm(checkNullString(recVal));

					}
					// // ADD start 20120928 QP@20505 No.24
					// //�@�S�x�t���[�^�C�g��
					// if(recNm == "freetitle_nendo"){
					// tldtShisakuRetu.setStrFreeTitleNendo(checkNullString(recVal));
					//
					// //�@�S�x�t���[���e
					// }if(recNm == "free_nendo"){
					// tldtShisakuRetu.setStrFreeNendo(checkNullString(recVal));
					//
					// //�@�S�x�t���[-�o��Flg
					// }if(recNm == "flg_freeNendo"){
					// tldtShisakuRetu.setIntFreeNendoFlg(checkNullInt(recVal));
					//
					// //�@���x�t���[�^�C�g��
					// }if(recNm == "freetitle_ondo"){
					// tldtShisakuRetu.setStrFreeTitleOndo(checkNullString(recVal));
					//
					// //�@���x�t���[���e
					// }if(recNm == "free_ondo"){
					// tldtShisakuRetu.setStrFreeOndo(checkNullString(recVal));
					//
					// //�@���x�t���[-�o��Flg
					// }if(recNm == "flg_freeOndo"){
					// tldtShisakuRetu.setIntFreeOndoFlg(checkNullInt(recVal));
					//
					// //�@���������t���[�^�C�g��
					// }if(recNm == "freetitle_suibun_kasei"){
					// tldtShisakuRetu.setStrFreeTitleSuibunKasei(checkNullString(recVal));
					//
					// //�@���������t���[���e
					// }if(recNm == "free_suibun_kasei"){
					// tldtShisakuRetu.setStrFreeSuibunKasei(checkNullString(recVal));
					//
					// //�@���������t���[-�o��Flg
					// }if(recNm == "flg_freeSuibunKasei"){
					// tldtShisakuRetu.setIntFreeSuibunKaseiFlg(checkNullInt(recVal));
					//
					// //�@�A���R�[�� �t���[�^�C�g��
					// }if(recNm == "freetitle_alcohol"){
					// tldtShisakuRetu.setStrFreeTitleAlchol(checkNullString(recVal));
					//
					// //�@�A���R�[�� �t���[���e
					// }if(recNm == "free_alcohol"){
					// tldtShisakuRetu.setStrFreeAlchol(checkNullString(recVal));
					//
					// //�@�A���R�[�� �t���[-�o��Flg
					// }if(recNm == "flg_freeAlchol"){
					// tldtShisakuRetu.setIntFreeAlcholFlg(checkNullInt(recVal));
					//
					// //�@���s�|�_�Z�x
					// }if(recNm == "jikkoSakusanNodo"){
					// tldtShisakuRetu.setDciJikkoSakusanNodo(checkNullDecimal(recVal));
					//
					// //�@���s�|�_�Z�x-�o��Flg
					// }if(recNm == "flg_jikkoSakusanNodo"){
					// tldtShisakuRetu.setIntJikkoSakusanNodoFlg(checkNullInt(recVal));
					//
					// //�@�������l�r�f
					// }if(recNm == "msg_suiso"){
					// tldtShisakuRetu.setDciSuisoMSG(checkNullDecimal(recVal));
					//
					// //�@�������l�r�f-�o��Flg
					// }if(recNm == "flg_msg_suiso"){
					// tldtShisakuRetu.setIntSuisoMSGFlg(checkNullInt(recVal));
					//
					// //�@�t���[�C�^�C�g��
					// }if(recNm == "free_title4"){
					// tldtShisakuRetu.setStrFreeTitle4(checkNullString(recVal));
					//
					// //�@�t���[�C���e
					// }if(recNm == "free_value4"){
					// tldtShisakuRetu.setStrFreeNaiyo4(checkNullString(recVal));
					//
					// //�@�t���[�C-�o��Flg
					// }if(recNm == "flg_free4"){
					// tldtShisakuRetu.setIntFreeFlg4(checkNullInt(recVal));
					//
					// //�@�t���[�D�^�C�g��
					// }if(recNm == "free_title5"){
					// tldtShisakuRetu.setStrFreeTitle5(checkNullString(recVal));
					//
					// //�@�t���[�D���e
					// }if(recNm == "free_value5"){
					// tldtShisakuRetu.setStrFreeNaiyo5(checkNullString(recVal));
					//
					// //�@�t���[�D-�o��Flg
					// }if(recNm == "flg_free5"){
					// tldtShisakuRetu.setIntFreeFlg5(checkNullInt(recVal));
					//
					// //�@�t���[�E�^�C�g��
					// }if(recNm == "free_title6"){
					// tldtShisakuRetu.setStrFreeTitle6(checkNullString(recVal));
					//
					// //�@�t���[�E���e
					// }if(recNm == "free_value6"){
					// tldtShisakuRetu.setStrFreeNaiyo6(checkNullString(recVal));
					//
					// //�@�t���[�E-�o��Flg
					// }if(recNm == "flg_free6"){
					// tldtShisakuRetu.setIntFreeFlg6(checkNullInt(recVal));
					//
					// }
					// // ADD end 20120928 QP@20505 No.24
				}
				// �@���샊�X�g�f�[�^�z��֒ǉ�
				aryShisakuList.add(pldtShisakuList);
				recData.clear();
			}
			tableT132.clear();
			t132.clear();

			/**********************************************************
			 * �@T133�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_cyuui";

			// �@�S�̔z��擾
			ArrayList t133 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT133 = (ArrayList) t133.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT133.size(); i++) {

				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT133.get(i))
						.get(0));
				// �@�����H���f�[�^������
				mfdtSeizo = new ManufacturingData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** �����H���f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						mfdtSeizo.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						mfdtSeizo.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						mfdtSeizo.setDciShisakuNum(checkNullDecimal(recVal));

						// �@���ӎ���NO
					}
					if (recNm == "no_chui") {
						mfdtSeizo.setIntTyuiNo(checkNullInt(recVal));

						// �@���ӎ���
					}
					if (recNm == "chuijiko") {
						mfdtSeizo.setStrTyuiNaiyo(checkNullString(recVal));

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						mfdtSeizo.setDciTorokuId(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						mfdtSeizo.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						mfdtSeizo.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						mfdtSeizo.setStrKosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						mfdtSeizo.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						mfdtSeizo.setStrKosinNm(checkNullString(recVal));

					}

				}
				// �����H���f�[�^�z��֒ǉ�
				arySeizo.add(mfdtSeizo);
				recData.clear();
			}
			tableT133.clear();
			t133.clear();

			/**********************************************************
			 * �@T140�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_shizai";

			// �@�S�̔z��擾
			ArrayList t140 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT140 = (ArrayList) t140.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT140.size(); i++) {
				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT140.get(i))
						.get(0));
				// �@���ރf�[�^������
				shdtShizai = new ShizaiData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** ���ރf�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						shdtShizai.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						shdtShizai.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						shdtShizai.setDciShisakuNum(checkNullDecimal(recVal));

						// �@����SEQ
					}
					if (recNm == "seq_shizai") {
						shdtShizai.setIntShizaiSeq(checkNullInt(recVal));

						// �@�\����
					}
					if (recNm == "no_sort") {
						shdtShizai.setIntHyojiNo(checkNullInt(recVal));

						// �@����CD
					}
					if (recNm == "cd_shizai") {
						shdtShizai.setStrShizaiCd(checkNullString(recVal));

						// �@���ޖ���
					}
					if (recNm == "nm_shizai") {
						shdtShizai.setStrShizaiNm(checkNullString(recVal));

						// �@�P��
					}
					if (recNm == "tanka") {
						shdtShizai.setDciTanka(checkNullDecimal(recVal));

						// �@����
					}
					if (recNm == "budomari") {
						shdtShizai.setDciBudomari(checkNullDecimal(recVal));

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						shdtShizai.setDciTorokuId(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						shdtShizai.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						shdtShizai.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						shdtShizai.setStrKosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						shdtShizai.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						shdtShizai.setStrKosinNm(checkNullString(recVal));

					}

				}
				// ���ރf�[�^�z��֒ǉ�
				aryShizai.add(shdtShizai);
				recData.clear();
			}
			tableT140.clear();
			t140.clear();

			/**********************************************************
			 * �@T141�i�[
			 *********************************************************/
			// �@ID�i�[
			strKinoId = "tr_genryo";

			// �@�S�̔z��擾
			ArrayList t141 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT141 = (ArrayList) t141.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT141.size(); i++) {
				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT141.get(i))
						.get(0));
				// �@���������f�[�^������
				cmdtGenka = new CostMaterialData();

				// �@�f�[�^�֊i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** ���������f�[�^�֒l�Z�b�g *********************/
					// �@����CD-�Ј�CD
					if (recNm == "cd_shain") {
						cmdtGenka.setDciShisakuUser(checkNullDecimal(recVal));

						// �@����CD-�N
					}
					if (recNm == "nen") {
						cmdtGenka.setDciShisakuYear(checkNullDecimal(recVal));

						// �@����CD-�ǔ�
					}
					if (recNm == "no_oi") {
						cmdtGenka.setDciShisakuNum(checkNullDecimal(recVal));

						// �@����SEQ
					}
					if (recNm == "seq_shisaku") {
						cmdtGenka.setIntShisakuSeq(checkNullInt(recVal));

						// �@���flg
					}
					if (recNm == "flg_print") {
						cmdtGenka.setIntinsatu(checkNullInt(recVal));

						// �@�d�_�ʐ���
					}
					if (recNm == "zyusui") {
						cmdtGenka.setStrZyutenSui(checkNullString(recVal));

						// �@�d�_�ʖ���
					}
					if (recNm == "zyuabura") {
						cmdtGenka.setStrZyutenYu(checkNullString(recVal));

						// �@���v��
					}
					if (recNm == "gokei") {
						cmdtGenka.setStrGokei(checkNullString(recVal));

						// �@������
					}
					if (recNm == "genryohi") {
						cmdtGenka.setStrGenryohi(checkNullString(recVal));

						// �@������i1�{�j
					}
					if (recNm == "genryohi1") {
						cmdtGenka.setStrGenryohiTan(checkNullString(recVal));

						// �@��d
					}
					if (recNm == "hizyu") {
						cmdtGenka.setStrHizyu(checkNullString(recVal));

						// �@�e��
					}
					if (recNm == "yoryo") {
						cmdtGenka.setStrYoryo(checkNullString(recVal));

						// �@����
					}
					if (recNm == "irisu") {
						cmdtGenka.setStrIrisu(checkNullString(recVal));

						// �@�L������
					}
					if (recNm == "yukobudomari") {
						cmdtGenka.setStrYukoBudomari(checkNullString(recVal));

						// �@���x����
					}
					if (recNm == "reberu") {
						cmdtGenka.setStrLevel(checkNullString(recVal));

						// �@��d����
					}
					if (recNm == "hizyubudomari") {
						cmdtGenka.setStrHizyuBudomari(checkNullString(recVal));

						// �@���Ϗ[�U��
					}
					if (recNm == "heikinzyu") {
						cmdtGenka.setStrZyutenAve(checkNullString(recVal));

						// �@1C/S������
					}
					if (recNm == "cs_genryo") {
						cmdtGenka.setStrGenryohiCs(checkNullString(recVal));

						// �@1C/S�ޗ���
					}
					if (recNm == "cs_zairyohi") {
						cmdtGenka.setStrZairyohiCs(checkNullString(recVal));

						// �@1C/S�o��
					}
					if (recNm == "cs_keihi") {
						cmdtGenka.setStrKeihiCs(checkNullString(recVal));

						// �@1C/S�����v
					}
					if (recNm == "cs_genka") {
						cmdtGenka.setStrGenkakeiCs(checkNullString(recVal));

						// �@1�����v
					}
					if (recNm == "ko_genka") {
						cmdtGenka.setStrGenkakeiTan(checkNullString(recVal));

						// �@1����
					}
					if (recNm == "ko_baika") {
						cmdtGenka.setStrGenkakeiBai(checkNullString(recVal));

						// �@1�e����
					}
					if (recNm == "ko_riritu") {
						cmdtGenka.setStrGenkakeiRi(checkNullString(recVal));

						// �@�o�^��ID
					}
					if (recNm == "id_toroku") {
						cmdtGenka.setDciTorokuId(checkNullDecimal(recVal));

						// �@�o�^���t
					}
					if (recNm == "dt_toroku") {
						cmdtGenka.setStrTorokuHi(checkNullString(recVal));

						// �@�X�V��ID
					}
					if (recNm == "id_koshin") {
						cmdtGenka.setDciKosinId(checkNullDecimal(recVal));

						// �@�X�V���t
					}
					if (recNm == "dt_koshin") {
						cmdtGenka.setStrKosinHi(checkNullString(recVal));

						// �@�o�^�Җ�
					}
					if (recNm == "nm_toroku") {
						cmdtGenka.setStrTorokuNm(checkNullString(recVal));

						// �@�X�V�Җ�
					}
					if (recNm == "nm_koshin") {
						cmdtGenka.setStrKosinNm(checkNullString(recVal));

					}

				}
				// ���������f�[�^�z��֒ǉ�
				aryGenka.add(cmdtGenka);
				recData.clear();
			}
			tableT141.clear();
			t141.clear();

		} catch (Exception e) {
			e.printStackTrace();
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̃f�[�^�ݒ�i�ڍ�or���@�R�s�[�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0002.����i�R�s�[�i�����R�s�[�j : ����i�e�[�u���f�[�^�̃R�s�[���s���i����i�f�[�^�̂݃R�s�[�j
	 * 
	 * @throws ExceptionBase
	 */
	public void CopyShisakuhin() throws ExceptionBase {

		try {
			// �����R�s�[
			setTraialData(1);

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�R�s�[�i�����R�s�[�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0003.����i�R�s�[�i�S�R�s�[�j : ����i�e�[�u���f�[�^�̃R�s�[���s��
	 * 
	 * @throws ExceptionBase
	 */
	public void CopyAllShisakuhin() throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			/**********************************************************
			 * �@T110�i�[
			 *********************************************************/
			// �@����CD-�Ј�CD
			ptdtShaisakuHin.setDciShisakuUser(null);
			// �@����CD-�N
			ptdtShaisakuHin.setDciShisakuYear(null);
			// �@����CD-�ǔ�
			ptdtShaisakuHin.setDciShisakuNum(null);
			// �@�O���[�vCD
			ptdtShaisakuHin.setIntGroupcd(DataCtrl.getInstance()
					.getUserMstData().getIntGroupcd());
			// �@�`�[��CD
			ptdtShaisakuHin.setIntTeamcd(DataCtrl.getInstance()
					.getUserMstData().getIntTeamcd());
			// �@�O���[�v��
			ptdtShaisakuHin.setStrGroupNm(DataCtrl.getInstance()
					.getUserMstData().getStrGroupnm());
			// �@�`�[����
			ptdtShaisakuHin.setStrTeamNm(DataCtrl.getInstance()
					.getUserMstData().getStrTeamnm());
			// �@�p�~
			ptdtShaisakuHin.setIntHaisi(0);
			// �@���@����
			ptdtShaisakuHin.setIntSeihoShisaku(0);
			// �@�o�^��ID
			ptdtShaisakuHin.setDciTorokuid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());
			// �@�o�^���t
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());
			// �@�X�V��ID
			ptdtShaisakuHin.setDciKosinid(DataCtrl.getInstance()
					.getUserMstData().getDciUserid());
			// �@�X�V���t
			// UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V�������Ńf�[�^���쐬�B
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());
			ptdtShaisakuHin.setStrKosinhi("");
			// �@�o�^�Җ�
			ptdtShaisakuHin.setStrTorokuNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());
			// �@�X�V�Җ�
			ptdtShaisakuHin.setStrKosinNm(DataCtrl.getInstance()
					.getUserMstData().getStrUsernm());

			// 2010/02/25 NAKAMURA ADD
			// START---------------------------------------------------
			// �r����Ж�
			ptdtShaisakuHin.setStrHaitaKaishaNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaKaishanm());
			// �r��������
			ptdtShaisakuHin.setStrHaitaBushoNm(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaBushonm());
			// �r������
			ptdtShaisakuHin.setStrHaitaShimei(DataCtrl.getInstance()
					.getUserMstData().getStrHaitaShimei());
			// 2010/02/25 NAKAMURA ADD
			// END-----------------------------------------------------
			// �yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			ptdtShaisakuHin.setStrSecret(null);
			// �yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			/**********************************************************
			 * �@T120�i�[
			 *********************************************************/
			ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData()
					.SearchHaigoData(0);
			for (int i = 0; i < aryHaigo.size(); i++) {
				// �@����CD-�Ј�CD
				((MixedData) aryHaigo.get(i)).setDciShisakuUser(null);
				// �@����CD-�N
				((MixedData) aryHaigo.get(i)).setDciShisakuYear(null);
				// �@����CD-�ǔ�
				((MixedData) aryHaigo.get(i)).setDciShisakuNum(null);
				// �@�o�^��ID
				((MixedData) aryHaigo.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�o�^���t
				((MixedData) aryHaigo.get(i)).setStrTorokuHi(getSysDate());
				// �@�X�V��ID
				((MixedData) aryHaigo.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�X�V���t
				((MixedData) aryHaigo.get(i)).setStrKosinHi(getSysDate());
				// �@�o�^�Җ�
				((MixedData) aryHaigo.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// �@�X�V�Җ�
				((MixedData) aryHaigo.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * �@T131�i�[
			 *********************************************************/
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);
			for (int i = 0; i < aryRetu.size(); i++) {
				// �@����CD-�Ј�CD
				((TrialData) aryRetu.get(i)).setDciShisakuUser(null);
				// �@����CD-�N
				((TrialData) aryRetu.get(i)).setDciShisakuYear(null);
				// �@����CD-�ǔ�
				((TrialData) aryRetu.get(i)).setDciShisakuNum(null);
				// �@�������Z
				((TrialData) aryRetu.get(i)).setIntGenkaShisan(0);
				// �@���@No-1
				((TrialData) aryRetu.get(i)).setStrSeihoNo1(null);
				// �@���@No-2
				((TrialData) aryRetu.get(i)).setStrSeihoNo2(null);
				// �@���@No-3
				((TrialData) aryRetu.get(i)).setStrSeihoNo3(null);
				// �@���@No-4
				((TrialData) aryRetu.get(i)).setStrSeihoNo4(null);
				// �@���@No-5
				((TrialData) aryRetu.get(i)).setStrSeihoNo5(null);
				// �@�o�^��ID
				((TrialData) aryRetu.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�o�^���t
				((TrialData) aryRetu.get(i)).setStrTorokuHi(getSysDate());
				// �@�X�V��ID
				((TrialData) aryRetu.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�X�V���t
				((TrialData) aryRetu.get(i)).setStrkosinHi(getSysDate());
				// �@�o�^�Җ�
				((TrialData) aryRetu.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// �@�X�V�Җ�
				((TrialData) aryRetu.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// �@�����˗��t���O
				((TrialData) aryRetu.get(i)).setFlg_shisanIrai(0);
				// �@�����˗��f�[�^�t���O
				((TrialData) aryRetu.get(i)).setFlg_init(0);

			}

			/**********************************************************
			 * �@T132�i�[
			 *********************************************************/
			ArrayList aryList = DataCtrl.getInstance().getTrialTblData()
					.getAryShisakuList();
			for (int i = 0; i < aryList.size(); i++) {
				// �@����CD-�Ј�CD
				((PrototypeListData) aryList.get(i)).setDciShisakuUser(null);
				// �@����CD-�N
				((PrototypeListData) aryList.get(i)).setDciShisakuYear(null);
				// �@����CD-�ǔ�
				((PrototypeListData) aryList.get(i)).setDciShisakuNum(null);
				// �@�o�^��ID
				((PrototypeListData) aryList.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�o�^���t
				((PrototypeListData) aryList.get(i))
						.setStrTorokuHi(getSysDate());
				// �@�X�V��ID
				((PrototypeListData) aryList.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�X�V���t
				((PrototypeListData) aryList.get(i))
						.setStrKosinHi(getSysDate());
				// �@�o�^�Җ�
				((PrototypeListData) aryList.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// �@�X�V�Җ�
				((PrototypeListData) aryList.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * �@T133�i�[
			 *********************************************************/
			ArrayList arySeizo = DataCtrl.getInstance().getTrialTblData()
					.SearchSeizoKouteiData(0);
			for (int i = 0; i < arySeizo.size(); i++) {
				// �@����CD-�Ј�CD
				((ManufacturingData) arySeizo.get(i)).setDciShisakuUser(null);
				// �@����CD-�N
				((ManufacturingData) arySeizo.get(i)).setDciShisakuYear(null);
				// �@����CD-�ǔ�
				((ManufacturingData) arySeizo.get(i)).setDciShisakuNum(null);
				// �@�o�^��ID
				((ManufacturingData) arySeizo.get(i)).setDciTorokuId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�o�^���t
				((ManufacturingData) arySeizo.get(i))
						.setStrTorokuHi(getSysDate());
				// �@�X�V��ID
				((ManufacturingData) arySeizo.get(i)).setDciKosinId(DataCtrl
						.getInstance().getUserMstData().getDciUserid());
				// �@�X�V���t
				((ManufacturingData) arySeizo.get(i))
						.setStrKosinHi(getSysDate());
				// �@�o�^�Җ�
				((ManufacturingData) arySeizo.get(i)).setStrTorokuNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
				// �@�X�V�Җ�
				((ManufacturingData) arySeizo.get(i)).setStrKosinNm(DataCtrl
						.getInstance().getUserMstData().getStrUsernm());
			}

			/**********************************************************
			 * �@T140�i�[
			 *********************************************************/

			/**********************************************************
			 * �@T141�i�[
			 *********************************************************/
			// ���������f�[�^�擾
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData()
					.SearchGenkaGenryoData(0);
			for (int i = 0; i < aryGenka.size(); i++) {
				CostMaterialData costMaterialData = (CostMaterialData) aryGenka
						.get(i);

				// ����CD-�Ј�CD
				costMaterialData.setDciShisakuUser(null);
				// ����CD-�N
				costMaterialData.setDciShisakuYear(null);
				// ����CD-�ǔ�
				costMaterialData.setDciShisakuNum(null);
				// �@�o�^��ID
				costMaterialData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �@�o�^���t
				costMaterialData.setStrTorokuHi(getSysDate());
				// �@�X�V��ID
				costMaterialData.setDciKosinId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �@�X�V���t
				costMaterialData.setStrKosinHi(getSysDate());
				// �@�o�^�Җ�
				costMaterialData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �@�X�V�Җ�
				costMaterialData.setStrKosinNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�R�s�[�i�S�R�s�[�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0004.�ŐV�A�ߋ��̐��@�R�s�[�̊Ǘ�������
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @throws ExceptionBase
	 */
	public void CopySeihou(int intShisakuSeq) throws ExceptionBase {

		try {

			// �d�l���m��

		} catch (Exception e) {

			ex.setStrErrmsg("");
			ex.setStrErrShori("");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("");

			throw ex;

		} finally {

		}

	}

	/**
	 * 0005.����i�f�[�^���� : ����i�f�[�^����������
	 * 
	 * @return ����i�f�[�^
	 */
	public PrototypeData SearchShisakuhinData() {
		return ptdtShaisakuHin;
	}

	/**
	 * 0006.����i����R�[�h�X�V : ����i�f�[�^�ɑ΂��Ď���R�[�h��ݒ肷��
	 * 
	 * @param dciShainCd
	 *            : ����CD-�Ј�CD
	 * @param dciNen
	 *            : ����CD-�N
	 * @param dciTuiban
	 *            : ����CD-�ǔ�
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdShisakuCd(BigDecimal dciShainCd, BigDecimal dciNen,
			BigDecimal dciTuiban, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// ����R�[�h�̐ݒ�
			ptdtShaisakuHin.setDciShisakuUser(dciShainCd);
			ptdtShaisakuHin.setDciShisakuYear(dciNen);
			ptdtShaisakuHin.setDciShisakuNum(dciTuiban);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i����R�[�h�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0007.����i�˗��ԍ��X�V : ����i�f�[�^�ɑ΂��Ĉ˗��ԍ���ݒ肷��
	 * 
	 * @param strIraiNo
	 *            ; �˗��ԍ�
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdIraiNo(String strIraiNo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrIrai(), strIraiNo);
			// �yKPX1500671�zMOD end

			// �˗��ԍ��ݒ�
			ptdtShaisakuHin.setStrIrai(strIraiNo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�˗��ԍ��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0008.����i��ʔԍ��X�V : ����i�f�[�^�ɑ΂��Ď�ʔԍ���ݒ肷��
	 * 
	 * @param intSyubetuNo
	 *            : ��ʔԍ�
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdSyubetuNo(String strSyubetuNo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrShubetuNo(), strSyubetuNo);
			// �yKPX1500671�zMOD end

			// ��ʔԍ��ݒ�҂��@DB��
			ptdtShaisakuHin.setStrShubetuNo(strSyubetuNo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i��ʔԍ��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0009.����i�p�~��X�V : ����i�f�[�^�ɑ΂��Ĕp�~���ݒ肷��
	 * 
	 * @param intHaishi
	 *            : �p�~��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdHaishi(int intHaishi, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getIntHaisi(), intHaishi);
			// �yKPX1500671�zMOD end

			// �p�~��ݒ�
			ptdtShaisakuHin.setIntHaisi(intHaishi);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�p�~��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0010.����i�i���X�V : ����i�f�[�^�ɑ΂��ĕi����ݒ肷��
	 * 
	 * @param strHinmei
	 *            : �i��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdHinmei(String strHinmei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrHinnm(), strHinmei);
			// �yKPX1500671�zMOD end

			// �i���ݒ�
			ptdtShaisakuHin.setStrHinnm(strHinmei);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�i���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0011.����i�`�[��CD�X�V : ����i�f�[�^�ɑ΂��ă`�[��CD��ݒ肷��
	 * 
	 * @param intTeamCd
	 *            : �`�[��CD
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdTeamCd(int intTeamCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			// �`�[��CD�ݒ�
			ptdtShaisakuHin.setIntTeamcd(intTeamCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�`�[��CD�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0012.����i�O���[�vCD�X�V : ����i�f�[�^�ɑ΂��ăO���[�vCD��ݒ肷��
	 * 
	 * @param intGroupCd
	 *            : �O���[�vCD
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdGroupCd(int intGroupCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			// �O���[�vCD�ݒ�
			ptdtShaisakuHin.setIntGroupcd(intGroupCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�O���[�vCD�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0013.����i�ꊇ�\���X�V : ����i�f�[�^�ɑ΂��Ĉꊇ�\����ݒ肷��
	 * 
	 * @param strIkkatu
	 *            : �ꊇ�\��
	 * @param iUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdIkkatsuHyouji(String strIkkatu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrIkatu(), strIkkatu);
			// �yKPX1500671�zMOD end

			// �ꊇ�\���ݒ�
			ptdtShaisakuHin.setStrIkatu(strIkkatu);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�ꊇ�\���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0014.����i�W�������X�V : ����i�f�[�^�ɑ΂��ăW��������ݒ肷��
	 * 
	 * @param intJanru
	 *            : �W������
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdJanru(String strJanru, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrZyanru(), strJanru);
			// �yKPX1500671�zMOD end

			// �W�������ݒ�
			ptdtShaisakuHin.setStrZyanru(strJanru);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�W�������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0015.����i���[�U�X�V : ����i�f�[�^�ɑ΂��ă��[�U��ݒ肷��
	 * 
	 * @param strUserCd
	 *            : ���[�UCD
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdUser(String strUserCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrUsercd(), strUserCd);
			// �yKPX1500671�zMOD end

			// ���[�U�[CD�ݒ�
			ptdtShaisakuHin.setStrUsercd(strUserCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i���[�U�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0016.����i���������X�V : ����i�f�[�^�ɑ΂��ē���������ݒ肷��
	 * 
	 * @param strTokutyoGenryo
	 *            : ��������
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdGenryo(String strTokutyoGenryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrTokutyo(), strTokutyoGenryo);
			// �yKPX1500671�zMOD end

			// ���������ݒ�
			ptdtShaisakuHin.setStrTokutyo(strTokutyoGenryo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i���������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0017.����i�p�r�X�V : ����i�f�[�^�ɑ΂��ėp�r��ݒ肷��
	 * 
	 * @param strYouto
	 *            : �p�r
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdYouto(String strYouto, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrYoto(), strYouto);
			// �yKPX1500671�zMOD end

			// �p�r�ݒ�
			ptdtShaisakuHin.setStrYoto(strYouto);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�p�r�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0018.����i���i�эX�V : ����i�f�[�^�ɑ΂��ĉ��i�т�ݒ肷��
	 * 
	 * @param strKakakutai
	 *            : ���i��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdKakakutai(String strKakakutai, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrKakaku(), strKakakutai);
			// �yKPX1500671�zMOD end

			// ���i�ѐݒ�
			ptdtShaisakuHin.setStrKakaku(strKakakutai);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i���i�эX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0019.����i��ʔԍ��X�V : ����i�f�[�^�ɑ΂��Ď�ʂ�ݒ肷��
	 * 
	 * @param strSyubetu
	 *            : ���
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdHinsyubetu(String strSyubetu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrShubetu(), strSyubetu);
			// �yKPX1500671�zMOD end

			// ��ʐݒ�
			ptdtShaisakuHin.setStrShubetu(strSyubetu);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i��ʔԍ��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0020.����i�����w��X�V : ����i�f�[�^�ɑ΂��ď����w���ݒ肷��
	 * 
	 * @param strSyosuShitei
	 *            : �����w��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdSyousuShitei(String strSyosuShitei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			String strBefSyosu = ptdtShaisakuHin.getStrShosu();
			if (null != strBefSyosu) {
				// �O�[���r��
				strBefSyosu = String.valueOf(Integer.parseInt(strBefSyosu));
			}
			String strAftSyosu = strSyosuShitei;
			if (null != strAftSyosu) {
				// �O�[���r��
				strAftSyosu = String.valueOf(Integer.parseInt(strAftSyosu));
			}
			chkHenkouData(strBefSyosu, strAftSyosu);
			// �yKPX1500671�zMOD end

			// �����w��ݒ�
			ptdtShaisakuHin.setStrShosu(strSyosuShitei);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�����w��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
	/**
	 * 0021.����i�̐Ӊ�ЍX�V : ����i�f�[�^�ɑ΂��Ĕ̐Ӊ�Ђ�ݒ肷��
	 * 
	 * @param intTantoKaishaCd
	 *            : �̐Ӊ��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdHanseki(int intHansekiCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getIntHansekicd(), intHansekiCd);
			// �yKPX1500671�zMOD end

			// �̐Ӊ�Аݒ�
			ptdtShaisakuHin.setIntHansekicd(intHansekiCd);
			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�̐Ӊ�ЍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 end

	/**
	 * 0021.����i�S����ЍX�V : ����i�f�[�^�ɑ΂��ĒS����Ђ�ݒ肷��
	 * 
	 * @param intTantoKaishaCd
	 *            : �S�����
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdTantoKaisha(int intTantoKaishaCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getIntKaishacd(), intTantoKaishaCd);
			// �yKPX1500671�zMOD end

			// �S����Аݒ�
			ptdtShaisakuHin.setIntKaishacd(intTantoKaishaCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�S����ЍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0022.����i�S���H��X�V : ����i�f�[�^�ɑ΂��ĒS���H���ݒ肷��
	 * 
	 * @param intTantoKojoCd
	 *            : �S���H��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 */
	public void UpdTantoKojo(int intTantoKojoCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getIntKojoco(), intTantoKojoCd);
			// �yKPX1500671�zMOD end

			// �S���H��ݒ�
			ptdtShaisakuHin.setIntKojoco(intTantoKojoCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�S���H��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0023.����i�S���c�ƍX�V : ����i�f�[�^�ɑ΂��ĒS���c�Ƃ�ݒ肷��
	 * 
	 * @param strTantoEigyoCd
	 *            : �S���c��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdTantoEigyo(String strTantoEigyoCd, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			String strBefTantoEigyoCd = ptdtShaisakuHin.getStrTantoEigyo();
			if (null != strBefTantoEigyoCd) {
				// �O�[���r��
				strBefTantoEigyoCd = String.valueOf(Integer.parseInt(strBefTantoEigyoCd));
			}
			String strAftTantoEigyoCd = strTantoEigyoCd;
			if (null != strAftTantoEigyoCd) {
				// �O�[���r��
				strAftTantoEigyoCd = String.valueOf(Integer.parseInt(strAftTantoEigyoCd));
			}
			chkHenkouData(strBefTantoEigyoCd, strAftTantoEigyoCd);
			// �yKPX1500671�zMOD end

			// �S���c�Ɛݒ�
			ptdtShaisakuHin.setStrTantoEigyo(strTantoEigyoCd);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�S���c�ƍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0024.����i�������@�X�V : ����i�f�[�^�ɑ΂��Đ������@��ݒ肷��
	 * 
	 * @param strSeizoHouho
	 *            : �������@
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdSeizoHouho(String strSeizoHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrSeizocd(), strSeizoHouho);
			// �yKPX1500671�zMOD end

			// �������@�ݒ�
			ptdtShaisakuHin.setStrSeizocd(strSeizoHouho);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�������@�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0025.����i�[�U���@�X�V : ����i�f�[�^�ɑ΂��ď[�U���@��ݒ肷��
	 * 
	 * @param strJutenHouho
	 *            : �[�U���@
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdJutenHouho(String strJutenHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrZyutencd(), strJutenHouho);
			// �yKPX1500671�zMOD end

			// �[�U���@�ݒ�
			ptdtShaisakuHin.setStrZyutencd(strJutenHouho);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�[�U���@�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0026.����i�E�ە��@�X�V : ����i�f�[�^�ɑ΂��ĎE�ە��@��ݒ肷��
	 * 
	 * @param strSakkinHouho
	 *            : �E�ە��@
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdSakkinHouho(String strSakkinHouho, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrSakin(), strSakkinHouho);
			// �yKPX1500671�zMOD end

			// �E�ە��@�ݒ�
			ptdtShaisakuHin.setStrSakin(strSakkinHouho);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			ptdtShaisakuHin.setStrTorokuhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�E�ە��@�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0027.����i�e��E��ލX�V : ����i�f�[�^�ɑ΂��ėe��E��ނ�ݒ肷��
	 * 
	 * @param strYoukiHouzai
	 *            : �e��E���
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdYoukiHouzai(String strYoukiHouzai, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrYokihozai(), strYoukiHouzai);
			// �yKPX1500671�zMOD end

			// ��ސݒ�
			ptdtShaisakuHin.setStrYokihozai(strYoukiHouzai);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�e��E��ލX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0028.����i�e�ʍX�V : ����i�f�[�^�ɑ΂��ėe�ʂ�ݒ肷��
	 * 
	 * @param strYouryo
	 *            : �e��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdYouryo(String strYouryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrYoryo(), strYouryo);
			// �yKPX1500671�zMOD end

			// �e�ʐݒ�
			ptdtShaisakuHin.setStrYoryo(strYouryo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�e�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0029.����i�e�ʒP�ʍX�V : ����i�f�[�^�ɑ΂��ėe�ʒP�ʂ�ݒ肷��
	 * 
	 * @param strYouryoTani
	 *            : �e�ʒP��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdYouryoTani(String strYouryoTani, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrTani(), strYouryoTani);
			// �yKPX1500671�zMOD end

			// �e�ʒP�ʐݒ�
			ptdtShaisakuHin.setStrTani(strYouryoTani);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�e�ʒP�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0030.����i���萔�X�V : ����i�f�[�^�ɑ΂��ē��萔��ݒ肷��
	 * 
	 * @param strIriSu
	 *            : ���萔
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdIriSu(String strIriSu, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrIrisu(), strIriSu);
			// �yKPX1500671�zMOD end

			// ���萔�ݒ�
			ptdtShaisakuHin.setStrIrisu(strIriSu);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i���萔�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0031.����i�׎p�X�V : ����i�f�[�^�ɑ΂��ĉ׎p��ݒ肷��
	 * 
	 * @param strNisugata
	 *            : �׎p
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdNisugata(String strNisugata, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrNishugata(), strNisugata);
			// �yKPX1500671�zMOD end

			// �׎p�ݒ�
			ptdtShaisakuHin.setStrNishugata(strNisugata);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�׎p�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0032.����i�戵���x�X�V : ����i�f�[�^�ɑ΂��Ď戵���x��ݒ肷��
	 * 
	 * @param strOndo
	 *            : �戵���x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdOndo(String strOndo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrOndo(), strOndo);
			// �yKPX1500671�zMOD end

			// �戵���x�ݒ�
			ptdtShaisakuHin.setStrOndo(strOndo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�戵���x�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0033.����i�ܖ����ԍX�V : ����i�f�[�^�ɑ΂��ďܖ����Ԃ�ݒ肷��
	 * 
	 * @param strSyoumikigen
	 *            : �ܖ�����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdSyoumikigen(String strSyoumikigen, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrShomi(), strSyoumikigen);
			// �yKPX1500671�zMOD end

			// �ܖ����Ԑݒ�
			ptdtShaisakuHin.setStrShomi(strSyoumikigen);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�ܖ����ԍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0034.����i������]�X�V : ����i�f�[�^�ɑ΂��Č�����]��ݒ肷��
	 * 
	 * @param strGenkaKibo
	 *            : ������]
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdGenkaKibo(String strGenkaKibo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrGenka(), strGenkaKibo);
			// �yKPX1500671�zMOD end

			// ������]�ݒ�
			ptdtShaisakuHin.setStrGenka(strGenkaKibo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i������]�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0035.����i������]�X�V : ����i�f�[�^�ɑ΂��Ĕ�����]��ݒ肷��
	 * 
	 * @param strBaikaKibo
	 *            : ������]
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdBaikaKibo(String strBaikaKibo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrBaika(), strBaikaKibo);
			// �yKPX1500671�zMOD end

			// ������]�ݒ�
			ptdtShaisakuHin.setStrBaika(strBaikaKibo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i������]�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0036.����i�z�蕨�ʍX�V : ����i�f�[�^�ɑ΂��đz�蕨�ʂ�ݒ肷��
	 * 
	 * @param strSouteiButuryo
	 *            : �z�蕨��
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdSouteiButuryo(String strSouteiButuryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrSotei(), strSouteiButuryo);
			// �yKPX1500671�zMOD end

			// �z�蕨�ʐݒ�
			ptdtShaisakuHin.setStrSotei(strSouteiButuryo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�z�蕨�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0037.����i�̔������X�V : ����i�f�[�^�ɑ΂��Ĕ̔�������ݒ肷��
	 * 
	 * @param strHanbaiJiki
	 *            : �̔�����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHanbaiJiki(String strHanbaiJiki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrHatubai(), strHanbaiJiki);
			// �yKPX1500671�zMOD end

			// �̔������ݒ�
			ptdtShaisakuHin.setStrHatubai(strHanbaiJiki);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�̔������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0038.����i�v�攄��X�V : ����i�f�[�^�ɑ΂��Čv�攄���ݒ肷��
	 * 
	 * @param strKeikakuUriage
	 *            : �v�攄��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdKeikakuUriage(String strKeikakuUriage, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrKeikakuUri(), strKeikakuUriage);
			// �yKPX1500671�zMOD end

			// �v�攄��ݒ�
			ptdtShaisakuHin.setStrKeikakuUri(strKeikakuUriage);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�v�攄��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0039.����i�v�旘�v�X�V : ����i�f�[�^�ɑ΂��Čv�旘�v��ݒ肷��
	 * 
	 * @param strKeikakuRieki
	 *            : �v�旘�v
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdKeikakuRieki(String strKeikakuRieki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrKeikakuRie(), strKeikakuRieki);
			// �yKPX1500671�zMOD end

			// �v�旘�v�ݒ�
			ptdtShaisakuHin.setStrKeikakuRie(strKeikakuRieki);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�v�旘�v�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0040.����i�̔��㔄��X�V : ����i�f�[�^�ɑ΂��Ĕ̔��㔄���ݒ肷��
	 * 
	 * @param strHanbaigoUriage
	 *            : �̔��㔄��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHanbaigoUriage(String strHanbaigoUriage, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrHanbaigoUri(),
					strHanbaigoUriage);
			// �yKPX1500671�zMOD end

			// �̔��㔄��ݒ�
			ptdtShaisakuHin.setStrHanbaigoUri(strHanbaigoUriage);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�̔��㔄��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0041.����i�̔��㗘�v�X�V : ����i�f�[�^�ɑ΂��Ĕ̔��㗘�v��ݒ肷��
	 * 
	 * @param strHanbaigoRieki
	 *            : �̔��㗘�v
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHanbaigoRieki(String strHanbaigoRieki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrHanbaigoRie(), strHanbaigoRieki);
			// �yKPX1500671�zMOD end

			// �̔��㗘�v�ݒ�
			ptdtShaisakuHin.setStrHanbaigoRie(strHanbaigoRieki);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i�̔��㗘�v�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0042.����i���������X�V : ����i�f�[�^�ɑ΂��đ���������ݒ肷��
	 * 
	 * @param strSogoMemo
	 *            : ��������
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdSogoMemo(String strSogoMemo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrSogo(), strSogoMemo);
			// �yKPX1500671�zMOD end

			// ���������ݒ�
			ptdtShaisakuHin.setStrSogo(strSogoMemo);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// DEL 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߍX�V
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i���������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0043.�z���f�[�^���� : �z���f�[�^�̌���
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @return �z��i�z���f�[�^�j
	 */
	public ArrayList SearchHaigoData(int intKouteiCd) throws ExceptionBase {

		// �V�K���X�g�C���X�^���X����
		ArrayList arr = new ArrayList();

		try {

			// �����F�H��CD���A0�̏ꍇ
			if (intKouteiCd == 0) {
				// �ő�H�����擾
				intMaxKotei = 0;
				for (int i = 0; i < aryHaigou.size(); i++) {
					MixedData mixedData = (MixedData) aryHaigou.get(i);
					if (intMaxKotei < mixedData.getIntKoteiNo()) {
						intMaxKotei = mixedData.getIntKoteiNo();
					}
				}

				arr = aryHaigou;
			} else {

				Iterator ite = aryHaigou.iterator();
				// ���X�g���������[�v
				while (ite.hasNext()) {
					// �z���f�[�^�I�u�W�F�N�g�擾
					MixedData mixData = (MixedData) ite.next();

					// �����F�H��CD�Ɣz���f�[�^�I�u�W�F�N�g�F�H��CD����v�����ꍇ
					if (intKouteiCd == mixData.getIntKoteiCd()) {
						// �ԋp���X�g�ɔz���f�[�^�I�u�W�F�N�g�ǉ�
						arr.add(mixData);
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0044.�z���H���I�� : �u�I���H���v�@��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 */
	public void SelectHaigoKoutei(int intKouteiCd) throws ExceptionBase {

		try {

			// �I���H��CD�ƁA����:�H��CD����v�����ꍇ
			if (intSelectKotei == intKouteiCd) {

				// �I���H��CD������
				intSelectKotei = 0;

				// �I���H��CD�ƁA����:�H��CD����v���Ȃ��ꍇ
			} else {

				// �����F�H��CD��I���H��CD�֐ݒ�
				intSelectKotei = intKouteiCd;

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H���I�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0045.�z���H���f�[�^�ǉ� : �z���f�[�^�ւ̍H���ǉ��������s��
	 * 
	 * @return MixedData : �ǉ��z���f�[�^
	 */
	public MixedData AddHaigoKoutei() throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// �ő�H���b�c�擾
			int max_koteiCd = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData MixedData = (MixedData) aryHaigou.get(i);
				if (max_koteiCd < MixedData.getIntKoteiCd()) {
					max_koteiCd = MixedData.getIntKoteiCd();
				}
			}
			// �ő�H���b�c��+1���Z
			max_koteiCd++;
			// �z���f�[�^�ǉ�
			MixedData addMixedData = new MixedData();
			// ����R�[�h-�Ј��b�c
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// ����R�[�h-�N
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// ����R�[�h-�ǔ�
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// �H���b�c
			addMixedData.setIntKoteiCd(max_koteiCd);
			// �H���r�d�p
			addMixedData.setIntKoteiSeq(1);
			// ���CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// ����CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// �o�^�҂h�c
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// �o�^�Җ�
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// �o�^���t
			addMixedData.setStrTorokuHi(getSysDate());

			// �z���f�[�^�֒ǉ�
			aryHaigou.add(addMixedData);

			// ���샊�X�g�f�[�^�ǉ�
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);
				// ���샊�X�g�f�[�^����
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// ����b�c-�Ј��b�c
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// ����b�c-�N
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// ����b�c-�ǔ�
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// ����r�d�p
				addPrototypeListData.setIntShisakuSeq(TrialData
						.getIntShisakuSeq());
				// �H���b�c
				addPrototypeListData.setIntKoteiCd(max_koteiCd);
				// �H���r�d�p
				addPrototypeListData.setIntKoteiSeq(1);
				// �o�^�҂h�c
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �o�^�Җ�
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �o�^���t
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// ���샊�X�g�f�[�^�֒ǉ�
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H���f�[�^�ǉ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0046.�z���H�����ړ� : �u�I���H���v�̍H�����̓���ւ����s��
	 * 
	 * @param strHoukouShitei
	 *            : �����w��(��or��)
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void MoveHaigoKoutei(String strHoukouShitei, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// �����w�蔻��
			if (strHoukouShitei.equals("��")) {

				// �I���H������1�ȊO�̏ꍇ�̂ݏ�������B
				if (intSelectKotei != 1) {

					Iterator ite = aryHaigou.iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// �z���f�[�^�I�u�W�F�N�g�擾
						MixedData mixData = (MixedData) ite.next();

						// �I���H�����Ǝ擾�H��������v
						if (intSelectKotei == mixData.getIntKoteiNo()) {

							// �I���H������-1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() - 1);
							// �X�V�ҏ��̐ݒ�
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

							// �I���H���� - 1�Ǝ擾�H��������v
						} else if ((intSelectKotei - 1) == mixData
								.getIntKoteiNo()) {

							// �I���H���H������+1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() + 1);
							// �X�V�ҏ��̐ݒ�
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

						}
					}
					// �I���H������-1
					intSelectKotei = intSelectKotei - 1;
				}
			} else if (strHoukouShitei.equals("��")) {

				// �I���H������1�ȊO�̏ꍇ�̂ݏ�������B
				if (intSelectKotei != 1) {

					Iterator ite = aryHaigou.iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// �z���f�[�^�I�u�W�F�N�g�擾
						MixedData mixData = (MixedData) ite.next();

						// �I���H�����Ǝ擾�H��������v
						if (intSelectKotei == mixData.getIntKoteiNo()) {

							// �I���H������+1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() + 1);
							// �X�V�ҏ��̐ݒ�
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

							// �I���H���� - 1�Ǝ擾�H��������v
						} else if ((intSelectKotei + 1) == mixData
								.getIntKoteiNo()) {

							// �I���H���H������-1
							mixData.setIntKoteiCd(mixData.getIntKoteiNo() - 1);
							// �X�V�ҏ��̐ݒ�
							mixData.setDciKosinId(dciUserId);
							mixData.setStrKosinHi(getSysDate());

						}
					}

					// �I���H������+1
					intSelectKotei = intSelectKotei + 1;
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H�����ړ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0047.�z���H���폜 : �z���A���샊�X�g�f�[�^���폜����
	 * 
	 * @param KoteiCd
	 *            : �폜�p�H���b�c
	 */
	public void DelHaigoKoutei(int KoteiCd) throws ExceptionBase {
		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// �z���f�[�^�폜
			Iterator iteHaigo = aryHaigou.iterator();
			// ���X�g���������[�v
			while (iteHaigo.hasNext()) {
				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) iteHaigo.next();
				// �H��CD����v
				if (KoteiCd == mixData.getIntKoteiCd()) {
					iteHaigo.remove();
				}
			}

			// ���샊�X�g�f�[�^�폜
			Iterator iteShisaku = aryShisakuList.iterator();
			// ���X�g���������[�v
			while (iteShisaku.hasNext()) {
				// �z���f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) iteShisaku
						.next();
				// �H��CD����v
				if (KoteiCd == PrototypeListData.getIntKoteiCd()) {
					iteShisaku.remove();
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H���폜�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0048.�z���H���f�[�^�X�V�i�H�����j : �w��H���f�[�^�ɑ΂��čH������ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param strKouteiMei
	 *            : �H����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoKouteimei(int intKouteiCd, String strKouteiMei,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD����v
				if (intKouteiCd == mixData.getIntKoteiCd()) {
					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getStrKouteiNm(), strKouteiMei);
					// �yKPX1500671�zMOD end

					// �H�����ݒ�
					mixData.setStrKouteiNm(strKouteiMei);
					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H���f�[�^�X�V�i�H�����j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0049.�z���H���f�[�^�X�V�i�H�������j : �w��H���f�[�^�ɑ΂��čH��������ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiZokusei
	 *            : �H������
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoZokusei(int intKouteiCd, String strKouteiZokusei,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD����v
				if (intKouteiCd == mixData.getIntKoteiCd()) {
					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getStrKouteiZokusei(),
							strKouteiZokusei);
					// �yKPX1500671�zMOD end

					// �H�������ݒ�
					mixData.setStrKouteiZokusei(strKouteiZokusei);
					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���H���f�[�^�X�V�i�H�������j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0050.�z�������I�� : �u�I�������v�@��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 */
	public void SelectHaigoGenryo(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		try {

			// �H��CD�E�H��SEQ�擾
			ArrayList arrKouteiCd = (ArrayList) arySelectGenka.get(0);
			ArrayList arrKouteiSeq = (ArrayList) arySelectGenka.get(1);

			// �폜FLG
			boolean blDelFlg = false;

			// �I����������
			for (int i = 0; i < arrKouteiCd.size(); i++) {
				// �����H��CD�E�H��SEQ�ƈ�v����ꍇ
				if (arrKouteiCd.get(i).equals(Integer.toString(intKouteiCd))
						&& arrKouteiSeq.get(i).equals(
								Integer.toString(intKouteiSeq))) {

					// �I�������폜
					arrKouteiCd.remove(i);
					arrKouteiSeq.remove(i);

					arySelectGenka.set(0, arrKouteiCd);
					arySelectGenka.set(1, arrKouteiSeq);

					blDelFlg = true;
				}
			}

			// �폜�Ώۂ��Ȃ��ꍇ
			if (!blDelFlg) {
				// �I�������ݒ�
				arrKouteiCd.add(Integer.toString(intKouteiCd));
				arrKouteiSeq.add(Integer.toString(intKouteiSeq));
				arySelectGenka.set(0, arrKouteiCd);
				arySelectGenka.set(1, arrKouteiSeq);
			}

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������I�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0051.�z�������f�[�^�ǉ� : �z���f�[�^�ւ̌����ǉ��������s��
	 * 
	 * @param koteiCd
	 *            : �H��CD
	 * @param koteiSeq
	 *            : �H��SEQ
	 * @return MixedData : �ǉ��z���f�[�^
	 */
	public MixedData AddHaigoGenryo(String koteiCd) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// --------------------------- �z���f�[�^�ǉ� ---------------------------
			// �ő�H��SEQ,�������擾
			int intMaxKoteiSeq = 0;
			int koteiNo = 0;
			String koteiNm = "";
			String koteiZoku = "";

			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData selHaigou = (MixedData) aryHaigou.get(i);
				// �f�[�^�ێ����̔z���f�[�^�F�H��CD�@�Ɓ@�����F�H��CD���������ꍇ
				if (selHaigou.getIntKoteiCd() == Integer.parseInt(koteiCd)) {
					// �ő�H��SEQ�擾
					if (intMaxKoteiSeq < selHaigou.getIntKoteiSeq()) {
						intMaxKoteiSeq = selHaigou.getIntKoteiSeq();
					}
					// �H����
					koteiNo = selHaigou.getIntKoteiNo();
					// �H����
					koteiNm = selHaigou.getStrKouteiNm();
					// �H������
					koteiZoku = selHaigou.getStrKouteiZokusei();
				}
			}
			intMaxKoteiSeq++;

			// �ǉ��p�z���f�[�^
			MixedData addMixedData = new MixedData();
			// ����-�Ј�CD
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// ����-�N
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// ����-�ǔ�
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// �H��CD
			addMixedData.setIntKoteiCd(Integer.parseInt(koteiCd));
			// �H��SEQ
			addMixedData.setIntKoteiSeq(intMaxKoteiSeq);
			// �H����
			addMixedData.setStrKouteiNm(koteiNm);
			// �H������
			addMixedData.setStrKouteiZokusei(koteiZoku);
			// �H����
			addMixedData.setIntKoteiNo(koteiNo);
			// ���CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// ����CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// �o�^��ID
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// �o�^�Җ�
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// �o�^��
			addMixedData.setStrTorokuHi(getSysDate());

			// �z���f�[�^�ǉ�
			aryHaigou.add(addMixedData);

			// -------------------------- ���샊�X�g�f�[�^�ǉ� ---------------------------
			int count = aryShisakuRetu.size();
			for (int i = 0; i < count; i++) {
				TrialData selTrialData = (TrialData) aryShisakuRetu.get(i);
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// ����-�Ј�CD
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// ����-�N
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// ����-�ǔ�
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// ����SEQ
				addPrototypeListData.setIntShisakuSeq(selTrialData
						.getIntShisakuSeq());
				// �H��CD
				addPrototypeListData.setIntKoteiCd(Integer.parseInt(koteiCd));
				// �H��SEQ
				addPrototypeListData.setIntKoteiSeq(intMaxKoteiSeq);
				// �o�^��ID
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �o�^�Җ�
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �o�^���t
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// ���샊�X�g�f�[�^�ǉ�
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������f�[�^�ǉ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0051_1.�z�������f�[�^�ǉ� : 0051.�z�������f�[�^�ǉ����\�b�h���I�[�o���[�h : �����s���Ȃ��ꍇ�̔z���f�[�^�ւ̌����ǉ��������s��
	 * 
	 * @param koteiCd
	 *            : �H��CD
	 * @param koteiSeq
	 *            : �H��SEQ
	 * @param koteiNo
	 *            : �H����
	 * @param koteiNm
	 *            : �H����
	 * @param koteiZoku
	 *            : �H������
	 * @return MixedData : �ǉ��z���f�[�^
	 */
	public MixedData AddHaigoGenryo(String koteiCd, String koteiSeq,
			int koteiNo, String koteiNm, String koteiZoku) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// --------------------------- �z���f�[�^�ǉ� ---------------------------
			// �ǉ��p�z���f�[�^
			MixedData addMixedData = new MixedData();
			// ����-�Ј�CD
			addMixedData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// ����-�N
			addMixedData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// ����-�ǔ�
			addMixedData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// �H��CD
			addMixedData.setIntKoteiCd(Integer.parseInt(koteiCd));
			// �H��SEQ
			addMixedData.setIntKoteiSeq(Integer.parseInt(koteiSeq));
			// �H����
			addMixedData.setStrKouteiNm(koteiNm);
			// �H������
			addMixedData.setStrKouteiZokusei(koteiZoku);
			// �H����
			addMixedData.setIntKoteiNo(koteiNo);
			// ���CD
			addMixedData.setIntKaishaCd(ptdtShaisakuHin.getIntKaishacd());
			// ����CD
			addMixedData.setIntBushoCd(ptdtShaisakuHin.getIntKojoco());
			// �o�^��ID
			addMixedData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// �o�^�Җ�
			addMixedData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// �o�^��
			addMixedData.setStrTorokuHi(getSysDate());

			// �z���f�[�^�ǉ�
			aryHaigou.add(addMixedData);

			// -------------------------- ���샊�X�g�f�[�^�ǉ� ---------------------------
			int count = aryShisakuRetu.size();
			for (int i = 0; i < count; i++) {

				TrialData selTrialData = (TrialData) aryShisakuRetu.get(i);
				PrototypeListData addPrototypeListData = new PrototypeListData();
				// ����-�Ј�CD
				addPrototypeListData
						.setDciShisakuUser(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_user()));
				// ����-�N
				addPrototypeListData
						.setDciShisakuYear(checkNullDecimal(DataCtrl
								.getInstance().getParamData()
								.getStrSisaku_nen()));
				// ����-�ǔ�
				addPrototypeListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// ����SEQ
				addPrototypeListData.setIntShisakuSeq(selTrialData
						.getIntShisakuSeq());
				// �H��CD
				addPrototypeListData.setIntKoteiCd(Integer.parseInt(koteiCd));
				// �H��SEQ
				addPrototypeListData.setIntKoteiSeq(Integer.parseInt(koteiSeq));
				// �o�^��ID
				addPrototypeListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �o�^�Җ�
				addPrototypeListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �o�^���t
				addPrototypeListData.setStrTorokuHi(getSysDate());

				// ���샊�X�g�f�[�^�ǉ�
				aryShisakuList.add(addPrototypeListData);
			}

			return addMixedData;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������f�[�^�ǉ��i�����s���Ȃ��ꍇ�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0051_2.�������ݒ� : ��������ݒ肷��
	 * 
	 * @param KoteiCd
	 *            : �ړ����H���b�c
	 * @param KoteiSeq
	 *            : �ړ����H���r�d�p
	 * @param GenryoNo
	 *            : ������
	 */
	public void NoHaigoGenryo(String KoteiCd, String KoteiSeq, int GenryoNo)
			throws ExceptionBase {
		try {
			// �������ݒ�
			for (int i = 0; i < aryHaigou.size(); i++) {

				MixedData selHaigou = (MixedData) aryHaigou.get(i);

				if (selHaigou.getIntKoteiCd() == Integer.parseInt(KoteiCd)
						&& selHaigou.getIntKoteiSeq() == Integer
								.parseInt(KoteiSeq)) {

					selHaigou.setIntGenryoNo(GenryoNo);

				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̌������ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}

	}

	/**
	 * 0051_3.�������ݒ� : �H������������ݒ肷��
	 * 
	 * @param KoteiCd
	 *            : �ړ����H���b�c
	 * @param KoteiSeq
	 *            : �ړ����H���r�d�p
	 * @param KoteiNo
	 *            : ������
	 * @param GenryoNo
	 *            : ������
	 */
	public void NoHaigoGenryo(String KoteiCd, String KoteiSeq, int KoteiNo,
			int GenryoNo) throws ExceptionBase {
		try {

			// �H�����������ݒ�
			for (int i = 0; i < aryHaigou.size(); i++) {

				MixedData selHaigou = (MixedData) aryHaigou.get(i);

				if (selHaigou.getIntKoteiCd() == Integer.parseInt(KoteiCd)
						&& selHaigou.getIntKoteiSeq() == Integer
								.parseInt(KoteiSeq)) {

					// �H�����ݒ�
					selHaigou.setIntKoteiNo(KoteiNo);

					// �������ݒ�
					selHaigou.setIntGenryoNo(GenryoNo);

				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̌������ݒ�i�H�����������j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/**
	 * 0052.�z�������f�[�^�ړ� : �u�I�������v�̌������̓���ւ����s��
	 * 
	 * @param KoteiCd_moto
	 *            : �ړ����H���b�c
	 * @param KoteiSeq_moto
	 *            : �ړ����H���r�d�p
	 * @param KoteiCd_saki
	 *            : �ړ���H���b�c
	 * @param KoteiSeq_saki
	 *            : �ړ���H���r�d�p
	 * @param strHoukouShitei
	 *            : �ړ���
	 * @return MixedData : �ړ��z���f�[�^
	 */
	public MixedData MoveHaigoGenryo(String KoteiCd_moto, String KoteiSeq_moto,
			String KoteiCd_saki, String KoteiSeq_saki, int HoukouShitei)
			throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// �������擾�p
			MixedData Haigou_saki = new MixedData();

			// ----------------------- ���H�����ւ̌������ړ�����
			// -------------------------------
			if (!KoteiCd_moto.equals(KoteiCd_saki)) {

				// �ړ����z�����擾
				MixedData Haigou_moto = new MixedData();
				for (int i = 0; i < aryHaigou.size(); i++) {

					MixedData selHaigou = (MixedData) aryHaigou.get(i);

					if (selHaigou.getIntKoteiCd() == Integer
							.parseInt(KoteiCd_moto)
							&& selHaigou.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {
						Haigou_moto = selHaigou;
					}
				}

				// �ړ���H���ֈړ���������}��
				Haigou_saki = AddHaigoGenryo(KoteiCd_saki);

				// ����CD
				Haigou_saki.setStrGenryoCd(Haigou_moto.getStrGenryoCd());
				// ���CD
				Haigou_saki.setIntKaishaCd(Haigou_moto.getIntKaishaCd());
				// ����CD
				Haigou_saki.setIntBushoCd(Haigou_moto.getIntBushoCd());
				// ��������
				Haigou_saki.setStrGenryoNm(Haigou_moto.getStrGenryoNm());
				// �P��
				Haigou_saki.setDciTanka(Haigou_moto.getDciTanka());
				// ����
				Haigou_saki.setDciBudomari(Haigou_moto.getDciBudomari());
				// ���ܗL��
				Haigou_saki.setDciGanyuritu(Haigou_moto.getDciGanyuritu());
				// �|�_
				Haigou_saki.setDciSakusan(Haigou_moto.getDciSakusan());
				// �H��
				Haigou_saki.setDciShokuen(Haigou_moto.getDciShokuen());
				// ADD start 20121002 QP@20505 No.24
				// �l�r�f
				Haigou_saki.setDciMsg(Haigou_moto.getDciMsg());
				// ADD end 20121002 QP@20505 No.24
				// ���_
				Haigou_saki.setDciSosan(Haigou_moto.getDciSosan());
				// �F
				Haigou_saki.setStrIro(Haigou_moto.getStrIro());
				// �o�^��ID
				Haigou_saki.setDciTorokuId(Haigou_moto.getDciTorokuId());
				// �o�^�Җ�
				Haigou_saki.setStrTorokuNm(Haigou_moto.getStrTorokuNm());
				// �o�^���t
				Haigou_saki.setStrTorokuHi(Haigou_moto.getStrTorokuHi());
				// �X�V��ID
				Haigou_saki.setDciKosinId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �X�V�Җ�
				Haigou_saki.setStrKosinNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �X�V���t
				Haigou_saki.setStrKosinHi(getSysDate());

				// �}�X�^�����܂�
				Haigou_saki.setDciMaBudomari(Haigou_moto.getDciMaBudomari());

				// //�}����f�[�^�X�V
				// for(int i=0; i<aryHaigou.size(); i++){
				//
				// MixedData selHaigou = (MixedData)aryHaigou.get(i);
				//
				// if(Haigou_saki.getIntKoteiCd() == selHaigou.getIntKoteiCd()
				// && Haigou_saki.getIntKoteiSeq() ==
				// selHaigou.getIntKoteiSeq()){
				//
				// //�f�[�^�X�V
				// aryHaigou.set(i, Haigou_saki);
				// }
				// }

				// ���샊�X�g�f�[�^�X�V
				for (int j = 0; j < aryShisakuList.size(); j++) {

					// ���샊�X�g�f�[�^�擾
					PrototypeListData pld = (PrototypeListData) aryShisakuList
							.get(j);

					// �ړ����f�[�^�擾
					if (pld.getIntKoteiCd() == Integer.parseInt(KoteiCd_moto)
							&& pld.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {

						// �ړ����f�[�^�ޔ�
						PrototypeListData pld_moto = pld;

						// �ړ���f�[�^�֍X�V
						for (int k = 0; k < aryShisakuList.size(); k++) {

							PrototypeListData pldUpd = (PrototypeListData) aryShisakuList
									.get(k);

							// �ړ���f�[�^�擾
							if (pldUpd.getIntKoteiCd() == Haigou_saki
									.getIntKoteiCd()
									&& pldUpd.getIntKoteiSeq() == Haigou_saki
											.getIntKoteiSeq()
									&& pldUpd.getIntShisakuSeq() == pld_moto
											.getIntShisakuSeq()) {

								// �ړ���f�[�^�ֈړ����f�[�^�X�V
								PrototypeListData pld_saki = pldUpd;

								// ��
								pld_saki.setDciRyo(pld_moto.getDciRyo());
								// �F
								pld_saki.setStrIro(pld_moto.getStrIro());
								// �X�V��ID
								pld_saki.setDciKosinId(DataCtrl.getInstance()
										.getUserMstData().getDciUserid());
								// �X�V�Җ�
								pld_saki.setStrKosinNm(DataCtrl.getInstance()
										.getUserMstData().getStrUsernm());
								// �X�V��
								pld_saki.setStrKosinHi(getSysDate());

							}
						}
					}
				}

				// �ړ����������ړ����H�����폜
				DelHaigoGenryo(KoteiCd_moto, KoteiSeq_moto);

				// ----------------------- ���H�����ւ̌������ړ�����
				// -------------------------------
			} else {

				for (int i = 0; i < aryHaigou.size(); i++) {

					MixedData selHaigou = (MixedData) aryHaigou.get(i);

					if (selHaigou.getIntKoteiCd() == Integer
							.parseInt(KoteiCd_moto)
							&& selHaigou.getIntKoteiSeq() == Integer
									.parseInt(KoteiSeq_moto)) {

						Haigou_saki = selHaigou;
					}
				}
			}

			return Haigou_saki;

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������f�[�^�ړ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0053.�z�������폜 : �z���f�[�^�ւ̌����폜�������s��
	 * 
	 * @param koteiCd
	 *            : �H��CD
	 * @param koteiSeq
	 *            : �H��SEQ
	 * 
	 */
	public void DelHaigoGenryo(String koteiCd, String koteiSeq)
			throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// �z���f�[�^�폜
			Iterator iteHaigo = aryHaigou.iterator();

			// ���X�g���������[�v
			while (iteHaigo.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) iteHaigo.next();

				// �H��CD����v
				if (mixData.getIntKoteiCd() == Integer.parseInt(koteiCd)
						&& mixData.getIntKoteiSeq() == Integer
								.parseInt(koteiSeq)) {

					iteHaigo.remove();

				}
			}

			// ���샊�X�g�f�[�^�폜
			Iterator iteShisaku = aryShisakuList.iterator();

			// ���X�g���������[�v
			while (iteShisaku.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) iteShisaku
						.next();

				// �H��CD����v
				if (PrototypeListData.getIntKoteiCd() == Integer
						.parseInt(koteiCd)
						&& PrototypeListData.getIntKoteiSeq() == Integer
								.parseInt(koteiSeq)) {

					iteShisaku.remove();

				}
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������폜�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054.�z������CD�X�V : �w�茴���f�[�^�ɑ΂��Č���CD��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param strGenryoCd
	 *            : ����CD
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoCd(int intKouteiCd, int intKouteiSeq,
			String strGenryoCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getStrGenryoCd(), strGenryoCd);
					// �yKPX1500671�zMOD end

					// ����CD�ݒ�
					mixData.setStrGenryoCd(strGenryoCd);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z������CD�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054_1.�z���������CD�X�V : �w�茴���f�[�^�ɑ΂��ĉ��CD��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param intKaishaCd
	 *            : ���CD
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoKaishaCd(int intKouteiCd, int intKouteiSeq,
			int intKaishaCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getIntKaishaCd(), intKaishaCd);
					// �yKPX1500671�zMOD end

					// ���CD�ݒ�
					mixData.setIntKaishaCd(intKaishaCd);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������CD�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0054_2.�z�������H��CD�X�V : �w�茴���f�[�^�ɑ΂��čH��CD��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param intKojoCd
	 *            : �H��CD
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoKojoCd(int intKouteiCd, int intKouteiSeq,
			int intKojoCd, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getIntBushoCd(), intKojoCd);
					// �yKPX1500671�zMOD end

					// �H��CD�ݒ�
					mixData.setIntBushoCd(intKojoCd);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������H��CD�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0055.�z���������̍X�V : �w�茴���f�[�^�ɑ΂��Č������̂�ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param strGenryoMei
	 *            : ��������
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoMei(int intKouteiCd, int intKouteiSeq,
			String strGenryoMei, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getStrGenryoNm(), strGenryoMei);
					// �yKPX1500671�zMOD end

					// �������̐ݒ�
					mixData.setStrGenryoNm(strGenryoMei);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������̍X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0056.�z�������P���X�V : �w�茴���f�[�^�ɑ΂��Č����P����ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciGenryoTanka
	 *            : �����P��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoTanka(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciGenryoTanka, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getDciTanka(), dciGenryoTanka);
					// �yKPX1500671�zMOD end

					// �P���ݒ�
					mixData.setDciTanka(dciGenryoTanka);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������P���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0057.�z�����������X�V : �w�茴���f�[�^�ɑ΂��Č���������ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciBudomari
	 *            : ��������
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoBudomari(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciBudomari, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getDciBudomari(), dciBudomari);
					// �yKPX1500671�zMOD end

					// �����ݒ�
					mixData.setDciBudomari(dciBudomari);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�����������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0058.�z���������ܗL���X�V : �w�茴���f�[�^�ɑ΂��Č������ܗL����ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciYuganyuryo
	 *            : �������ܗL��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoYuganyuryo(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciYuganyuryo, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getDciGanyuritu(), dciYuganyuryo);
					// �yKPX1500671�zMOD end

					// ���ܗL�ʐݒ�
					mixData.setDciGanyuritu(dciYuganyuryo);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������ܗL���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0059.�z�������|�_�X�V : �w�茴���f�[�^�ɑ΂��Č����|�_��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciSakusan
	 *            : �����|�_
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoSakusan(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSakusan, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �|�_�ݒ�
					mixData.setDciSakusan(dciSakusan);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������|�_�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0060.�z�������H���X�V : �w�茴���f�[�^�ɑ΂��Č����H����ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciSyokuen
	 *            : �����H��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoSyokuen(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSyokuen, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �H���ݒ�
					mixData.setDciShokuen(dciSyokuen);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������H���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121002 QP@20505 No.24
	/**
	 * 0060.�z�������l�r�f�X�V : �w�茴���f�[�^�ɑ΂��Č����l�r�f��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciMsg
	 *            : �����l�r�f
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoMsg(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciMsg, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �l�r�f�ݒ�
					mixData.setDciMsg(dciMsg);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������H���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121002 QP@20505 No.24
	/**
	 * 0061.�z���������_�X�V : �w�茴���f�[�^�ɑ΂��Č������_��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciSousan
	 *            : �������_
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigoGenryoSousan(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciSousan, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zMOD end

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// ���_�ݒ�
					mixData.setDciSosan(dciSousan);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������_�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0062.�z�������F�X�V : �w�茴���f�[�^�ɑ΂��Č����F��ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param strColor
	 *            : �����F
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdHaigouGenryoColor(int intKouteiCd, int intKouteiSeq,
			String strColor, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(mixData.getStrIro(), strColor);
					// �yKPX1500671�zMOD end

					// �����F�ݒ�
					mixData.setStrIro(strColor);

					// �X�V�ҏ��̐ݒ�
					mixData.setDciKosinId(dciUserId);
					mixData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������F�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0063.�����f�[�^���� : �����f�[�^�̌���
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @return �z��i�����f�[�^�j
	 */
	public ArrayList SearchShisakuRetuData(int intShisakuSeq)
			throws ExceptionBase {

		// ���X�g�C���X�^���X����
		ArrayList arr = new ArrayList();

		try {

			// �����F����SEQ���A0�̏ꍇ
			if (intShisakuSeq == 0) {
				arr = aryShisakuRetu;
			} else {

				Iterator ite = aryShisakuRetu.iterator();

				// ���X�g���������[�v
				while (ite.hasNext()) {
					// �����f�[�^�I�u�W�F�N�g�擾
					TrialData trialData = (TrialData) ite.next();

					// �����F����SEQ�Ǝ����f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
					if (intShisakuSeq == trialData.getIntShisakuSeq()) {
						// �ԋp���X�g�Ɏ����f�[�^�I�u�W�F�N�g�ǉ�
						arr.add(trialData);
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0064.�����H���f�[�^���� : �����H���f�[�^�̌���
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intChuiJikouNo
	 *            : ���ӎ���No
	 * @return �z��i�����f�[�^�j
	 */
	public ArrayList SearchSeizoKouteiData(int intChuiJikouNo)
			throws ExceptionBase {

		// �V�K���X�g�C���X�^���X����
		ArrayList arr = new ArrayList();

		try {

			// �����F���ӎ���No���A0�̏ꍇ
			if (intChuiJikouNo == 0) {
				arr = arySeizo;

			} else {

				Iterator ite = arySeizo.iterator();

				// ���X�g���������[�v
				while (ite.hasNext()) {
					// �����H���f�[�^�I�u�W�F�N�g�擾
					ManufacturingData manufacturingData = (ManufacturingData) ite
							.next();

					// �����F����SEQ�E���ӎ���No�Ɛ����H���I�u�W�F�N�g�F����SEQ�E���ӎ���No����v�����ꍇ
					if (intChuiJikouNo == manufacturingData.getIntTyuiNo()) {
						// �ԋp���X�g�ɐ����H���I�u�W�F�N�g�ǉ�
						arr.add(manufacturingData);
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̐����H���f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0065.�����I�� : �u�I�������v��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 */
	public void SelectShisakuRetu(int intShisakuSeq) throws ExceptionBase {

		try {

			// �I�������ƁA����:���삪��v�����ꍇ
			if (intSelectRetu == intShisakuSeq) {

				// �I������񏉊���
				intSelectRetu = 0;

				// �I�������ƁA����:����SEQ����v���Ȃ��ꍇ
			} else {

				// �����F����SEQ��I�������֐ݒ�
				intSelectRetu = intShisakuSeq;

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����I�������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0066.����񒍈ӎ���No�X�V : �w�莎���f�[�^�ɑ΂��Ē��ӎ���No��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intChuiJikouNo
	 *            : ���ӎ���No
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdChuiJikouNo(int intShisakuSeq, String strChuiJikouNo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrTyuiNo(), strChuiJikouNo);
					// �yKPX1500671�zMOD end

					// ���ӎ���No�ݒ�
					trialData.setStrTyuiNo(strChuiJikouNo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񒍈ӎ���No�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0067.�����f�[�^�ǉ� : �����f�[�^�ւ̎����ǉ��������s��
	 * 
	 * @return TrialData : �ǉ������f�[�^
	 */
	public TrialData AddShisakuRetu() throws ExceptionBase {

		try {
			// ADD start 20121002 QP@20505 No.24
			String ptValue = "0";
			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				ptValue = "0";
			} else {
				// �H���p�^�[����Value1�擾
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn()
						.selectLiteralVal1(ptKotei);
			}
			// ADD end 20121002 QP@20505 No.24

			// �f�[�^�ύX
			HenkouFg = true;

			// �ő厎��SEQ�擾
			int maxShisakuSeq = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);

				if (maxShisakuSeq < TrialData.getIntShisakuSeq()) {
					maxShisakuSeq = TrialData.getIntShisakuSeq();
				}

			}

			// �ő厎��SEQ�։��Z
			maxShisakuSeq++;

			// �ǉ��p�̎����f�[�^����
			TrialData addTrialData = new TrialData();
			// ����R�[�h-�Ј�CD
			addTrialData.setDciShisakuUser(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_user()));
			// ����R�[�h-�N
			addTrialData.setDciShisakuYear(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_nen()));
			// ����R�[�h-�ǔ�
			addTrialData.setDciShisakuNum(checkNullDecimal(DataCtrl
					.getInstance().getParamData().getStrSisaku_oi()));
			// ����SEQ
			addTrialData.setIntShisakuSeq(maxShisakuSeq);
			// �o�^��ID
			addTrialData.setDciTorokuId(DataCtrl.getInstance().getUserMstData()
					.getDciUserid());
			// �o�^�Җ�
			addTrialData.setStrTorokuNm(DataCtrl.getInstance().getUserMstData()
					.getStrUsernm());
			// �o�^���t
			addTrialData.setStrTorokuHi(getSysDate());

			// �o��Flg�̐ݒ�
			if (aryShisakuRetu.size() != 0) {

				// �P��O�̎���f�[�^���擾
				TrialData trialBeforeData = (TrialData) DataCtrl.getInstance()
						.getTrialTblData()
						.SearchShisakuRetuData(maxShisakuSeq - 1).get(0);
				// ���_
				addTrialData.setIntSosanFlg(trialBeforeData.getIntSosanFlg());
				// �H��
				addTrialData.setIntShokuenFlg(trialBeforeData
						.getIntShokuenFlg());
				// �������_�x
				addTrialData.setIntSuiSandoFlg(trialBeforeData
						.getIntSuiSandoFlg());
				// �������H��
				addTrialData.setIntSuiShokuenFlg(trialBeforeData
						.getIntSuiShokuenFlg());
				// �������|�_
				addTrialData.setIntSuiSakusanFlg(trialBeforeData
						.getIntSuiSakusanFlg());
				// ADD start 20121002 QP@20505 No.24
				if (ptValue.equals("0") || ptValue.equals("3")) {
					// ADD end 20121002 QP@20505 No.24
					// ���x
					addTrialData.setIntToudoFlg(trialBeforeData
							.getIntToudoFlg());
					// �S�x
					addTrialData.setIntNendoFlg(trialBeforeData
							.getIntNendoFlg());
					// ���x
					addTrialData.setIntOndoFlg(trialBeforeData.getIntOndoFlg());
					// pH
					addTrialData.setIntPhFlg(trialBeforeData.getIntPhFlg());
					// ���_�i���́j
					addTrialData.setIntSosanBunsekiFlg(trialBeforeData
							.getIntSosanBunsekiFlg());
					// �H���i���́j
					addTrialData.setIntShokuenBunsekiFlg(trialBeforeData
							.getIntShokuenBunsekiFlg());
					// ��d
					addTrialData.setIntHizyuFlg(trialBeforeData
							.getIntHizyuFlg());
					// ������d
					addTrialData.setIntHiju_sui_fg(trialBeforeData
							.getIntHiju_sui_fg());
					// MOD start 20121002 QP@20505 No.24
					// //��������
					// addTrialData.setIntSuibunFlg(trialBeforeData.getIntSuibunFlg());
					// //�A���R�[��
					// addTrialData.setIntArukoruFlg(trialBeforeData.getIntArukoruFlg());
					// ���������t���[
					addTrialData.setIntFreeSuibunKaseiFlg(trialBeforeData
							.getIntFreeSuibunKaseiFlg());
					// �A���R�[���t���[
					addTrialData.setIntFreeAlcholFlg(trialBeforeData
							.getIntFreeAlcholFlg());
					// MOD end 20121002 QP@20505 No.24
					// �쐬����
					addTrialData.setIntSakuseiMemoFlg(trialBeforeData
							.getIntSakuseiMemoFlg());
					// �]��
					addTrialData.setIntHyokaFlg(trialBeforeData
							.getIntHyokaFlg());
					// �t���[�@
					addTrialData.setIntFreeFlg(trialBeforeData.getIntFreeFlg());
					// �t���[�A
					addTrialData.setIntFreeFl2(trialBeforeData.getIntFreeFl2());
					// �t���[�B
					addTrialData.setIntFreeFl3(trialBeforeData.getIntFreeFl3());

					// ADD start 20121002 QP@20505 No.24
					// ���������t���[�^�C�g��
					addTrialData.setStrFreeTitleSuibunKasei(trialBeforeData
							.getStrFreeTitleSuibunKasei());
					// �A���R�[���t���[�^�C�g��
					addTrialData.setStrFreeTitleAlchol(trialBeforeData
							.getStrFreeTitleAlchol());
					// �t���[�@�^�C�g��
					addTrialData.setStrFreeTitle1(trialBeforeData
							.getStrFreeTitle1());
					// �t���[�A�^�C�g��
					addTrialData.setStrFreeTitle2(trialBeforeData
							.getStrFreeTitle2());
					// �t���[�B�^�C�g��
					addTrialData.setStrFreeTitle3(trialBeforeData
							.getStrFreeTitle3());
					// �t���[�C�^�C�g��
					addTrialData.setStrFreeTitle4(trialBeforeData
							.getStrFreeTitle4());
					// �t���[�D�^�C�g��
					addTrialData.setStrFreeTitle5(trialBeforeData
							.getStrFreeTitle5());
					// �t���[�E�^�C�g��
					addTrialData.setStrFreeTitle6(trialBeforeData
							.getStrFreeTitle6());
				} else {
					// �H���p�^�[�� �P�t�E�Q�t �̏ꍇ
					// �����|�_�Z�x
					addTrialData.setIntJikkoSakusanNodoFlg(trialBeforeData
							.getIntJikkoSakusanNodoFlg());
					// �������l�r�f
					addTrialData.setIntSuisoMSGFlg(trialBeforeData
							.getIntSuisoMSGFlg());
					// pH
					addTrialData.setIntPhFlg(trialBeforeData.getIntPhFlg());
					// ��d
					addTrialData.setIntHizyuFlg(trialBeforeData
							.getIntHizyuFlg());
					// ������d
					addTrialData.setIntHiju_sui_fg(trialBeforeData
							.getIntHiju_sui_fg());
					// �S�x�t���[
					addTrialData.setIntFreeNendoFlg(trialBeforeData
							.getIntFreeNendoFlg());
					// ���x�t���[
					addTrialData.setIntFreeOndoFlg(trialBeforeData
							.getIntFreeOndoFlg());
					// �t���[�@
					addTrialData.setIntFreeFlg(trialBeforeData.getIntFreeFlg());
					// �t���[�A
					addTrialData.setIntFreeFl2(trialBeforeData.getIntFreeFl2());
					// �t���[�B
					addTrialData.setIntFreeFl3(trialBeforeData.getIntFreeFl3());
					// �t���[�C
					addTrialData.setIntFreeFlg4(trialBeforeData
							.getIntFreeFlg4());
					// �t���[�D
					addTrialData.setIntFreeFlg5(trialBeforeData
							.getIntFreeFlg5());
					// �t���[�E
					addTrialData.setIntFreeFlg6(trialBeforeData
							.getIntFreeFlg6());

					// �S�x�t���[�^�C�g��
					addTrialData.setStrFreeTitleNendo(trialBeforeData
							.getStrFreeTitleNendo());
					// ���x�t���[�^�C�g��
					addTrialData.setStrFreeTitleOndo(trialBeforeData
							.getStrFreeTitleOndo());
					// �t���[�@�^�C�g��
					addTrialData.setStrFreeTitle1(trialBeforeData
							.getStrFreeTitle1());
					// �t���[�A�^�C�g��
					addTrialData.setStrFreeTitle2(trialBeforeData
							.getStrFreeTitle2());
					// �t���[�B�^�C�g��
					addTrialData.setStrFreeTitle3(trialBeforeData
							.getStrFreeTitle3());
					// �t���[�C�^�C�g��
					addTrialData.setStrFreeTitle4(trialBeforeData
							.getStrFreeTitle4());
					// �t���[�D�^�C�g��
					addTrialData.setStrFreeTitle5(trialBeforeData
							.getStrFreeTitle5());
					// �t���[�E�^�C�g��
					addTrialData.setStrFreeTitle6(trialBeforeData
							.getStrFreeTitle6());
				}
				// ADD end 20121002 QP@20505 No.24

			}

			// �����f�[�^�z��֒ǉ�
			aryShisakuRetu.add(addTrialData);

			// ���샊�X�g�f�[�^�z��֒ǉ�
			for (int i = 0; i < aryHaigou.size(); i++) {
				MixedData MixedData = (MixedData) aryHaigou.get(i);
				// ���샊�X�g�f�[�^����
				PrototypeListData addListData = new PrototypeListData();

				// ����R�[�h-�Ј�CD
				addListData.setDciShisakuUser(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_user()));
				// ����R�[�h-�N
				addListData.setDciShisakuYear(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_nen()));
				// ����R�[�h-�ǔ�
				addListData.setDciShisakuNum(checkNullDecimal(DataCtrl
						.getInstance().getParamData().getStrSisaku_oi()));
				// ����SEQ
				addListData.setIntShisakuSeq(maxShisakuSeq);
				// �H��CD
				addListData.setIntKoteiCd(MixedData.getIntKoteiCd());
				// �H��SEQ
				addListData.setIntKoteiSeq(MixedData.getIntKoteiSeq());
				// �o�^��ID
				addListData.setDciTorokuId(DataCtrl.getInstance()
						.getUserMstData().getDciUserid());
				// �o�^�Җ�
				addListData.setStrTorokuNm(DataCtrl.getInstance()
						.getUserMstData().getStrUsernm());
				// �o�^���t
				addListData.setStrTorokuHi(getSysDate());

				// ���샊�X�g�f�[�^�z��֒ǉ�
				aryShisakuList.add(addListData);
			}

			return addTrialData;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����f�[�^�ǉ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0068.�����f�[�^�폜 : �u�I�������v�̎����f�[�^���폜����
	 * 
	 * @param delSeq
	 *            : �폜����SEQ
	 */
	public void DelShsiakuRetu(int delSeq) throws ExceptionBase {
		try {

			// �f�[�^�ύX
			HenkouFg = true;

			// ����i�f�[�^�폜�i���@����j
			if (ptdtShaisakuHin.getIntSeihoShisaku() == delSeq) {
				ptdtShaisakuHin.setIntSeihoShisaku(0);
			}

			// �����f�[�^�폜
			Iterator iteRetu = aryShisakuRetu.iterator();
			while (iteRetu.hasNext()) {
				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) iteRetu.next();
				// ����SEQ����v
				if (delSeq == trialData.getIntShisakuSeq()) {
					iteRetu.remove();
				}
			}

			// ���샊�X�g�f�[�^�폜
			Iterator iteList = aryShisakuList.iterator();
			while (iteList.hasNext()) {
				// �����I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) iteList
						.next();
				// ����SEQ����v
				if (delSeq == PrototypeListData.getIntShisakuSeq()) {
					iteList.remove();
				}
			}

			// ���������f�[�^�폜
			Iterator iteGenka = aryGenka.iterator();
			while (iteGenka.hasNext()) {
				// �����I�u�W�F�N�g�擾
				CostMaterialData costMaterialData = (CostMaterialData) iteGenka
						.next();
				// ����SEQ����v
				if (delSeq == costMaterialData.getIntShisakuSeq()) {
					iteGenka.remove();
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����f�[�^�폜�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0068_1.����񏇐ݒ� : ����񏇂̐ݒ���s��
	 * 
	 * @param seq
	 *            : ����SEQ
	 * @param no
	 *            : ���쏇
	 */
	public void SetRetuNo(int seq, int no) throws ExceptionBase {
		try {
			// ����񏇐ݒ�
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);
				if (TrialData.getIntShisakuSeq() == seq) {
					TrialData.setIntHyojiNo(no);
				}
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񏇐ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0069.����񏇈ړ� : �u�I�������v�̎���񏇂̓���ւ����s��
	 * 
	 * @param strHoukouShitei
	 *            : �����w��(��or��)
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void MoveShisakuRetu(String strHoukouShitei, int iUserId)
			throws ExceptionBase {

		try {

			// ������

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񏇈ړ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070.�������t�X�V : �w�莎���f�[�^�ɑ΂��Ď������t��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strDate
	 *            : ���t
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdShisaukRetuDate(int intShisakuSeq, String strDate,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrShisakuHi(), strDate);
					// �yKPX1500671�zMOD end

					// ������t�ݒ�
					trialData.setStrShisakuHi(strDate);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎������t�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * �����H���d��d�ʍX�V : �w�莎���f�[�^�ɑ΂��čH���d��d�ʂ�ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciShiagari
	 *            : �d��d��
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdKouteiShiagari(int intShisakuSeq, int intKoteiCode,
			BigDecimal dciKouteiShiagari, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ����v
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKoteiCode == prototypeListData.getIntKoteiCd()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(prototypeListData.getDciKouteiShiagari(),
							dciKouteiShiagari);
					// �yKPX1500671�zMOD end

					// �H���d��d�ʐݒ�
					prototypeListData.setDciKouteiShiagari(dciKouteiShiagari);

					// �X�V�ҏ��̐ݒ�
					prototypeListData.setDciKosinId(dciUserId);
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����H���d��d�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_2.�����d��d�ʍX�V : �w�莎���f�[�^�ɑ΂��Ďd��d�ʂ�ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciShiagari
	 *            : �d��d��
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdShiagariRetuDate(int intShisakuSeq, BigDecimal dciShiagari,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getDciShiagari(), dciShiagari);
					// �yKPX1500671�zMOD end

					// �d��d�ʐݒ�
					trialData.setDciShiagari(dciShiagari);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����d��d�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_3.����񐻖@No1�X�V : �w�莎���f�[�^�ɑ΂��Đ��@No-1��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strSeihoNo1
	 *            : ���@No-1
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdRetuSeiho1(int intShisakuSeq, String strSeihoNo1,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���@No-1�ݒ�
					trialData.setStrSeihoNo1(strSeihoNo1);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐻖@No1�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * 0070_4.����񐻖@No2�X�V : �w�莎���f�[�^�ɑ΂��Đ��@No-2��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strSeihoNo2
	 *            : ���@No-2
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdRetuSeiho2(int intShisakuSeq, String strSeihoNo2,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���@No-2�ݒ�
					trialData.setStrSeihoNo2(strSeihoNo2);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐻖@No2�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_5.����񐻖@No3�X�V : �w�莎���f�[�^�ɑ΂��Đ��@No-3��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strSeihoNo3
	 *            : ���@No-3
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdRetuSeiho3(int intShisakuSeq, String strSeihoNo3,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���@No-3�ݒ�
					trialData.setStrSeihoNo3(strSeihoNo3);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���.����񐻖@No3�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_6.����񐻖@No4�X�V : �w�莎���f�[�^�ɑ΂��Đ��@No-4��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strSeihoNo4
	 *            : ���@No-4
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdRetuSeiho4(int intShisakuSeq, String strSeihoNo4,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���@No-4�ݒ�
					trialData.setStrSeihoNo4(strSeihoNo4);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���.����񐻖@No4�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0070_6.����񐻖@No5�X�V : �w�莎���f�[�^�ɑ΂��Đ��@No-5��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strSeihoNo5
	 *            : ���@No-5
	 * @param iUserId
	 *            : ���[�UID
	 */
	public void UpdRetuSeiho5(int intShisakuSeq, String strSeihoNo5,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			 HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���@No-5�ݒ�
					trialData.setStrSeihoNo5(strSeihoNo5);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐻖@No5�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0071.�����T���v��No�X�V : �w�莎���f�[�^�ɑ΂��Ď����T���v��No��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intSampleNo
	 *            : �T���v��No
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSampleNo(int intShisakuSeq, String strSampleNo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrSampleNo(), strSampleNo);
					// �yKPX1500671�zMOD end

					// �T���v��No�ݒ�
					trialData.setStrSampleNo(strSampleNo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����T���v��No�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0072.����񃁃��X�V : �w�莎���f�[�^�ɑ΂��ă�����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strMemo
	 *            : ����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuMemo(int intShisakuSeq, String strMemo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrMemo(), strMemo);
					// �yKPX1500671�zMOD end

					// �����ݒ�
					trialData.setStrMemo(strMemo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񃁃��X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0073.�������FG�X�V : �w�莎���f�[�^�ɑ΂��Ĉ��FG��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intPrintFg
	 *            : ���FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuPrintFg(int intShisakuSeq, int intPrintFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getIntInsatuFlg(), intPrintFg);
					// �yKPX1500671�zMOD end

					// ���FLG�ݒ�
					trialData.setIntInsatuFlg(intPrintFg);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎������FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0074.����񎩓��v�ZFG�X�V : �w�莎���f�[�^�ɑ΂��Ď����v�ZFG��ݒ肷��
	 * 
	 * @param intJidouKeisanFg
	 *            : �����v�Z���FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuJidouKeisanFg(int intJidouKeisanFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntZidoKei(), intJidouKeisanFg);
				// �yKPX1500671�zMOD end

				// ���FLG�ݒ�
				trialData.setIntZidoKei(intJidouKeisanFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񎩓��v�ZFG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0075.����񑍎_�o��FG�X�V : �w�莎���f�[�^�ɑ΂��đ��_�o��FG��ݒ肷��
	 * 
	 * @param intSousanFg
	 *            : ���_�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSousanFg(int intSousanFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSosanFlg(), intSousanFg);
				// �yKPX1500671�zMOD end

				// ���_�o��FLG�ݒ�
				trialData.setIntSosanFlg(intSousanFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񑍎_�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0076.�����H���o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĐH���o��FG��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intSyokuenFg
	 *            : �H���o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSyokuenFg(int intSyokuenFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntShokuenFlg(), intSyokuenFg);
				// �yKPX1500671�zMOD end

				// �H���o��FLG�ݒ�
				trialData.setIntShokuenFlg(intSyokuenFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����H���o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0077.����񐅑����_�x�o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ������_�x�o��FG��ݒ肷��
	 * 
	 * @param intSuisoSandoFg
	 *            : �������_�x�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSandoFg(int intSuisoSandoFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSuiSandoFlg(), intSuisoSandoFg);
				// �yKPX1500671�zMOD end

				// �������_�x�o��FLG�ݒ�
				trialData.setIntSuiSandoFlg(intSuisoSandoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����_�x�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0078.����񐅑����H���o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ������H���o��FG��ݒ肷��
	 * 
	 * @param intSuisoSyokuenFg
	 *            : �������H���o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSyokuenFg(int intSuisoSyokuenFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSuiShokuenFlg(),
						intSuisoSyokuenFg);
				// �yKPX1500671�zMOD end

				// �������H���o��FLG�ݒ�
				trialData.setIntSuiShokuenFlg(intSuisoSyokuenFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����H���o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0079.����񐅑����|�_�o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ������|�_�o��FG��ݒ肷��
	 * 
	 * @param intSuisoSakusanFg
	 *            : �������|�_�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSakusanFg(int intSuisoSakusanFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSuiSakusanFlg(),
						intSuisoSakusanFg);
				// �yKPX1500671�zMOD end

				// �������|�_�o��FLG�ݒ�
				trialData.setIntSuiSakusanFlg(intSuisoSakusanFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����|�_�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0080.����񓜓x�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ē��x�o��FG��ݒ肷��
	 * 
	 * @param intToudoFg
	 *            : ���x�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuToudoFg(int intToudoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntToudoFlg(), intToudoFg);
				// �yKPX1500671�zMOD end

				// ���x�o��FLG�ݒ�
				trialData.setIntToudoFlg(intToudoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񓜓x�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0081.�����S�x�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĔS�x�o��FG��ݒ肷��
	 * 
	 * @param intNendoFg
	 *            : �S�x�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuNendoFg(int intNendoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntNendoFlg(), intNendoFg);
				// �yKPX1500671�zMOD end

				// �S�x�o��FLG�ݒ�
				trialData.setIntNendoFlg(intNendoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����S�x�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0082.����񉷓x�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĉ��x�o��FG��ݒ肷��
	 * 
	 * @param intOndoFg
	 *            : ���x�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuOndoFg(int intOndoFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntOndoFlg(), intOndoFg);
				// �yKPX1500671�zMOD end

				// ���x�o��FLG�ݒ�
				trialData.setIntOndoFlg(intOndoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񉷓x�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0083.�����pH�o��FG�X�V : �w�莎���f�[�^�ɑ΂���pH�o��FG��ݒ肷��
	 * 
	 * @param intPhFg
	 *            : pH�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuPhFg(int intPhFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntPhFlg(), intPhFg);
				// �yKPX1500671�zMOD end

				// pH�o��FLG�ݒ�
				trialData.setIntPhFlg(intPhFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����pH�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0084.����񑍎_-���͏o��FG�X�V : �w�莎���f�[�^�ɑ΂��đ��_-���͏o��FG��ݒ肷��
	 * 
	 * @param intSousanBunsekiFg
	 *            : ���_-���͏o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSousanBunsekiFg(int intSousanBunsekiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSosanBunsekiFlg(),
						intSousanBunsekiFg);
				// �yKPX1500671�zMOD end

				// ���_-���͏o��FLG�ݒ�
				trialData.setIntSosanBunsekiFlg(intSousanBunsekiFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񑍎_-���͏o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0085.�����H��-���͏o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĐH��-���͏o��FG��ݒ肷��
	 * 
	 * @param intSyokuenBunsekiFg
	 *            : �H��-���͏o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSyokuenBunsekiFg(int intSyokuenBunsekiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntShokuenBunsekiFlg(),
						intSyokuenBunsekiFg);
				// �yKPX1500671�zMOD end

				// �H��-���͏o��FLG�ݒ�
				trialData.setIntShokuenBunsekiFlg(intSyokuenBunsekiFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����H��-���͏o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0086.������d�o��FG�X�V : �w�莎���f�[�^�ɑ΂��Ĕ�d�o��FG��ݒ肷��
	 * 
	 * @param intHijuFg
	 *            : ��d�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuHijuFg(int intHijuFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntHizyuFlg(), intHijuFg);
				// �yKPX1500671�zMOD end

				// ��d�o��FLG�ݒ�
				trialData.setIntHizyuFlg(intHijuFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎�����d�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0087.����񐅕������o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ��������o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : ���������o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuibunKaseiFg(int intSuibunKaseiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSuibunFlg(), intSuibunKaseiFg);
				// �yKPX1500671�zMOD end

				// ���������o��FLG�ݒ�
				trialData.setIntSuibunFlg(intSuibunKaseiFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅕������o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0088.�����A���R�[���o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăA���R�[���o��FG��ݒ肷��
	 * 
	 * @param intArukoruFg
	 *            : �A���R�[���o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuArukoruFg(int intArukoruFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntArukoruFlg(), intArukoruFg);
				// �yKPX1500671�zMOD end

				// �A���R�[���o��FLG�ݒ�
				trialData.setIntArukoruFlg(intArukoruFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����A���R�[���o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0089.�����t���[�@�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�@�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_1
	 *            : �t���[�@�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_1(int intFreeFg_1, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFlg(), intFreeFg_1);
				// �yKPX1500671�zMOD end

				// �t���[�@�o��FLG�ݒ�
				trialData.setIntFreeFlg(intFreeFg_1);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�@�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0090.�����t���[�A�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�A�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_2
	 *            : �t���[�A�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_2(int intFreeFg_2, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFl2(), intFreeFg_2);
				// �yKPX1500671�zMOD end

				// �t���[�A�o��FLG�ݒ�
				trialData.setIntFreeFl2(intFreeFg_2);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�A�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0091.�����t���[�B�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�B�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_3
	 *            : �t���[�B�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_3(int intFreeFg_3, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFl3(), intFreeFg_3);
				// �yKPX1500671�zMOD end

				// �t���[�B�o��FLG�ݒ�
				trialData.setIntFreeFl3(intFreeFg_3);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�B�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121001 QP@20505 No.24
	/**
	 * ����� ���������t���[�o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ��������t���[�o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : ���������t���[�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeSuibunKaseiFg(int intFreeSuibunKaseiFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeSuibunKaseiFlg(),
						intFreeSuibunKaseiFg);
				// �yKPX1500671�zMOD end

				// ���������t���[�o��FLG�ݒ�
				trialData.setIntFreeSuibunKaseiFlg(intFreeSuibunKaseiFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅕������t���[�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �A���R�[���t���[�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăA���R�[���t���[�o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : �A���R�[���t���[�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeAlcholFg(int intFreeAlcholFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeAlcholFlg(), intFreeAlcholFg);
				// �yKPX1500671�zMOD end

				// �A���R�[���t���[�o��FLG�ݒ�
				trialData.setIntFreeAlcholFlg(intFreeAlcholFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����A���R�[���t���[�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �����|�_�Z�x �o��FG�X�V : �w�莎���f�[�^�ɑ΂��Ď����|�_�Z�x �o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : �����|�_�Z�x �o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuJikkoSakusanNodoFg(int intJSNFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntJikkoSakusanNodoFlg(), intJSNFg);
				// �yKPX1500671�zMOD end

				// �����|�_�Z�x �o��FLG�ݒ�
				trialData.setIntJikkoSakusanNodoFlg(intJSNFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �����|�_�Z�x �o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �������l�r�f �o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ������l�r�f �o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : �������l�r�f �o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoMSGFg(int intSuisoMSGFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntSuisoMSGFlg(), intSuisoMSGFg);
				// �yKPX1500671�zMOD end

				// �������l�r�f �o��FLG�ݒ�
				trialData.setIntSuisoMSGFlg(intSuisoMSGFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �������l�r�f �o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �S�x�t���[�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĔS�x�t���[�o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : �S�x�t���[�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNendoFg(int intFreeNendoFg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeNendoFlg(), intFreeNendoFg);
				// �yKPX1500671�zMOD end

				// �S�x�t���[�o��FLG�ݒ�
				trialData.setIntFreeNendoFlg(intFreeNendoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �S�x�t���[ �o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� ���x�t���[�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ĉ��x�t���[�o��FG��ݒ肷��
	 * 
	 * @param intSuibunKaseiFg
	 *            : ���x�t���[�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeOndoFg(int intFreeOndoFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeNendoFlg(), intFreeOndoFg);
				// �yKPX1500671�zMOD end

				// ���x�t���[�o��FLG�ݒ�
				trialData.setIntFreeNendoFlg(intFreeOndoFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� ���x�t���[ �o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �t���[�C�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�C�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_1
	 *            : �t���[�C�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_4(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFlg4(), intFreeFg);
				// �yKPX1500671�zMOD end

				// �t���[�C�o��FLG�ݒ�
				trialData.setIntFreeFlg4(intFreeFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���C�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �t���[�D�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�D�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_1
	 *            : �t���[�D�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_5(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFlg5(), intFreeFg);
				// �yKPX1500671�zMOD end

				// �t���[�D�o��FLG�ݒ�
				trialData.setIntFreeFlg5(intFreeFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�D�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �t���[�E�o��FG�X�V : �w�莎���f�[�^�ɑ΂��ăt���[�E�o��FG��ݒ肷��
	 * 
	 * @param intFreeFg_1
	 *            : �t���[�E�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeFg_6(int intFreeFg, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntFreeFlg6(), intFreeFg);
				// �yKPX1500671�zMOD end

				// �t���[�E�o��FLG�ݒ�
				trialData.setIntFreeFlg6(intFreeFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�E�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121001 QP@20505 No.24

	/**
	 * 0092.����񌴉����ZNo�X�V : �w�莎���f�[�^�ɑ΂��Č������ZNo��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intGenkashisanNo
	 *            : �������ZNo
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuGenkashisanNo(int intShisakuSeq,
			int intGenkashisanNo, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �������ZNo�ݒ�
					trialData.setIntGenkaShisan(intGenkashisanNo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񌴉����ZNo�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0093.����񑍎_�X�V : �w�莎���f�[�^�ɑ΂��đ��_��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciSousan
	 *            : ���_
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSousan(int intShisakuSeq, BigDecimal dciSousan,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���_�ݒ�
					trialData.setDciSosan(dciSousan);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񑍎_�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0094.�����H���X�V : �w�莎���f�[�^�ɑ΂��ĐH����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciSyokuen
	 *            : �H��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSyokuen(int intShisakuSeq, BigDecimal dciSyokuen,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �H���ݒ�
					trialData.setDciShokuen(dciSyokuen);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����H���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20121002 QP@20505 No.24
	/**
	 * 0094.�����l�r�f�X�V : �w�莎���f�[�^�ɑ΂��Ăl�r�f��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciMsg
	 *            : �l�r�f
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuMsg(int intShisakuSeq, BigDecimal dciMsg,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �l�r�f�ݒ�
					trialData.setDciMsg(dciMsg);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����l�r�f�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20121002 QP@20505 No.24

	/**
	 * 0095.����񐅑����_�x�X�V : �w�莎���f�[�^�ɑ΂��Đ������_�x��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciSuisoSando
	 *            : �������_�x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSando(int intShisakuSeq,
			BigDecimal dciSuisoSando, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �������_�x�ݒ�
					trialData.setDciSuiSando(dciSuisoSando);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����_�x�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0096.����񐅑����H���X�V : �w�莎���f�[�^�ɑ΂��Đ������H����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciSuisoSyokuen
	 *            : �������H��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSyokuen(int intShisakuSeq,
			BigDecimal dciSuisoSyokuen, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �������H���ݒ�
					trialData.setDciSuiShokuen(dciSuisoSyokuen);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����H���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0097.����񐅑����|�_�X�V : �w�莎���f�[�^�ɑ΂��Đ������|�_��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param dciSuisoSakusan
	 *            : �������|�_
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoSakusan(int intShisakuSeq,
			BigDecimal dciSuisoSakusan, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �������|�_�ݒ�
					trialData.setDciSuiSakusan(dciSuisoSakusan);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅑����|�_�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0098.����񓜓x�X�V : �w�莎���f�[�^�ɑ΂��ē��x��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intToudo
	 *            : ���x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuToudo(int intShisakuSeq, String strToudo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrToudo(), strToudo);
					// �yKPX1500671�zMOD end

					// ���x�ݒ�
					trialData.setStrToudo(strToudo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񓜓x�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0099.�����S�x�X�V : �w�莎���f�[�^�ɑ΂��ĔS�x��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intNendo
	 *            : �S�x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuNendo(int intShisakuSeq, String strNendo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrNendo(), strNendo);
					// �yKPX1500671�zMOD end

					// �S�x�ݒ�
					trialData.setStrNendo(strNendo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����S�x�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0100.����񉷓x�X�V : �w�莎���f�[�^�ɑ΂��ĉ��x��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intOndo
	 *            : ���x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuOndo(int intShisakuSeq, String strOndo,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrOndo(), strOndo);
					// �yKPX1500671�zMOD end

					// ���x�ݒ�
					trialData.setStrOndo(strOndo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񉷓x�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0101.�����pH�X�V : �w�莎���f�[�^�ɑ΂���pH��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intPh
	 *            : pH
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuPh(int intShisakuSeq, String strPh,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrPh(), strPh);
					// �yKPX1500671�zMOD end

					// pH�ݒ�
					trialData.setStrPh(strPh);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����pH�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0102.����񑍎_-���͍X�V : �w�莎���f�[�^�ɑ΂��đ��_-���͂�ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intSousanBunseki
	 *            : ���_-����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSousanBunseki(int intShisakuSeq,
			String strSousanBunseki, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrSosanBunseki(),
							strSousanBunseki);
					// �yKPX1500671�zMOD end

					// ���_-���͐ݒ�
					trialData.setStrSosanBunseki(strSousanBunseki);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			ex.setStrErrmsg("");
			ex.setStrErrShori("");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("");

			throw ex;

		} finally {

		}

	}

	/**
	 * 0103.�����H��-���͍X�V : �w�莎���f�[�^�ɑ΂��đ��_-�H����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intSyokuenBunseki
	 *            : �H��-����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSyokuenBunseki(int intShisakuSeq,
			String strSyokuenBunseki, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrShokuenBunseki(),
							strSyokuenBunseki);
					// �yKPX1500671�zMOD end

					// �H��-���͐ݒ�
					trialData.setStrShokuenBunseki(strSyokuenBunseki);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����H��-���͍X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0104.������d�X�V : �w�莎���f�[�^�ɑ΂��Ĕ�d��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intHiju
	 *            : ��d
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuHiju(int intShisakuSeq, String strHiju,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �H���p�^�[���擾
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					// �H���p�^�[����Value1�擾
					String ptValue = null;
					if (ptKotei == null || ptKotei.length() == 0) {
					} else {
						// �H���p�^�[����Value1�擾
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}
					String yoryoTani = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrTani();
					String taniValue1 = "";
					if (yoryoTani == null || yoryoTani.length() == 0) {
					} else {
						taniValue1 = DataCtrl.getInstance()
								.getLiteralDataTani()
								.selectLiteralVal1(yoryoTani);
					}
					// 1�t ���� �e�ʂ��uml�v�̏ꍇ
					if (JwsConstManager.JWS_KOTEITYPE_1.equals(ptValue)
							&& "1".equals(taniValue1)) {
						// �f�[�^�ύX�`�F�b�N
						chkHenkouData(trialData.getStrHizyu(), strHiju);
					}
					// �yKPX1500671�zMOD end

					// ��d�ݒ�
					trialData.setStrHizyu(strHiju);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎�����d�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0105.����񐅕������X�V : �w�莎���f�[�^�ɑ΂��Đ���������ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intSuibunKasei
	 *            : ��������
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuibunKasei(int intShisakuSeq,
			String strSuibunKasei, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// ���������ݒ�
					trialData.setStrSuibun(strSuibunKasei);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���񐅕������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0106.�����A���R�[���X�V : �w�莎���f�[�^�ɑ΂��ăA���R�[����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intArukoru
	 *            : �A���R�[��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuArukoru(int intShisakuSeq, String strArukoru,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �A���R�[���ݒ�
					trialData.setStrArukoru(strArukoru);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����A���R�[���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0107.�����t���[�^�C�g���@�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���@��ݒ肷��
	 * 
	 * @param strFreeTitle_1
	 *            : �t���[�^�C�g���@
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_1(String strFreeTitle_1,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle1(), strFreeTitle_1);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���@�ݒ�
				trialData.setStrFreeTitle1(strFreeTitle_1);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���@�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0108.�����t���[���e�@�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�@��ݒ肷��
	 * 
	 * @param strFreeNaiyou_1
	 *            : �t���[���e�@
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_1(int intShisakuSeq,
			String strFreeNaiyou_1, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo1(), strFreeNaiyou_1);
					// �yKPX1500671�zMOD end

					// �t���[���e�@�ݒ�
					trialData.setStrFreeNaiyo1(strFreeNaiyou_1);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�@�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0109.�����t���[�^�C�g���A�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���A��ݒ肷��
	 * 
	 * @param strFreeTitle_2
	 *            : �t���[�^�C�g���A
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_2(String strFreeTitle_2,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle2(), strFreeTitle_2);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���A�ݒ�
				trialData.setStrFreeTitle2(strFreeTitle_2);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���A�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0110.�����t���[���e�A�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�A��ݒ肷��
	 * 
	 * @param strFreeNaiyou_2
	 *            : �t���[���e�A
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_2(int intShisakuSeq,
			String strFreeNaiyou_2, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo2(), strFreeNaiyou_2);
					// �yKPX1500671�zMOD end

					// �t���[���e�A�ݒ�
					trialData.setStrFreeNaiyo2(strFreeNaiyou_2);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�A�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0111.�����t���[�^�C�g���B�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���B��ݒ肷��
	 * 
	 * @param strFreeTitle_3
	 *            : �t���[�^�C�g���B
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_3(String strFreeTitle_3,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle3(), strFreeTitle_3);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���B�ݒ�
				trialData.setStrFreeTitle3(strFreeTitle_3);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���B�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0112.�����t���[���e�B�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�B��ݒ肷��
	 * 
	 * @param strFreeNaiyou_3
	 *            : �t���[���e�B
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_3(int intShisakuSeq,
			String strFreeNaiyou_3, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo3(), strFreeNaiyou_3);
					// �yKPX1500671�zMOD end

					// �t���[���e�B�ݒ�
					trialData.setStrFreeNaiyo3(strFreeNaiyou_3);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�B�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// ADD start 20120928 QP@20505 No.24
	/**
	 * ����� �����������[�^�C�g���X�V : �S�����f�[�^�ɑ΂��Đ��������t���[�^�C�g�����e�̒l��ݒ肷��
	 * 
	 * @param strFreeTitle_Ondo
	 *            : ���������t���[�^�C�g��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_SuibunKasei(
			String strFreeTitle_SuibunKasei, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitleSuibunKasei(),
						strFreeTitle_SuibunKasei);
				// �yKPX1500671�zMOD end

				// ���������t���[�^�C�g���ݒ�
				trialData.setStrFreeTitleSuibunKasei(strFreeTitle_SuibunKasei);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� ���������t���[�^�C�g�� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� ���������t���[���e �X�V : �w�莎���f�[�^�ɑ΂��Đ��������t���[���e��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : ���������t���[���e
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeSuibunKasei(int intShisakuSeq,
			String strFreeSuibunKasei, BigDecimal dciUserId)
			throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeSuibunKasei(),
							strFreeSuibunKasei);
					// �yKPX1500671�zMOD end

					// ���������t���[���e �ݒ�
					trialData.setStrFreeSuibunKasei(strFreeSuibunKasei);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� ���������t���[���e �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �A���R�[�����[�^�C�g���X�V : �S�����f�[�^�ɑ΂��ăA���R�[���t���[�^�C�g�����e�̒l��ݒ肷��
	 * 
	 * @param strFreeTitle_Ondo
	 *            : �A���R�[���t���[�^�C�g��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_Alchol(String strFreeTitle_Alchol,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitleAlchol(),
						strFreeTitle_Alchol);
				// �yKPX1500671�zMOD end

				// �A���R�[���t���[�^�C�g���ݒ�
				trialData.setStrFreeTitleAlchol(strFreeTitle_Alchol);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �A���R�[���t���[�^�C�g�� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �A���R�[���t���[���e �X�V : �w�莎���f�[�^�ɑ΂��ăA���R�[���t���[���e��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : �A���R�[���t���[���e
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeAlchol(int intShisakuSeq,
			String strFreeAlchol, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeAlchol(), strFreeAlchol);
					// �yKPX1500671�zMOD end

					// �A���R�[���t���[���e �ݒ�
					trialData.setStrFreeAlchol(strFreeAlchol);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �A���R�[���t���[���e �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �����|�_�Z�x �X�V : �w�莎���f�[�^�ɑ΂��Ď����|�_�Z�x��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : �����|�_�Z�x
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuJikkoSakusanNodo(int intShisakuSeq,
			BigDecimal dciJsn, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �����|�_�Z�x�i���j�ݒ�
					trialData.setDciJikkoSakusanNodo(dciJsn);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �����|�_�Z�x �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �������l�r�f �X�V : �w�莎���f�[�^�ɑ΂��Đ������l�r�f��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : �������l�r�f
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSuisoMSG(int intShisakuSeq,
			BigDecimal dciSuisoMsg, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FDEL start
			// �f�[�^�ύX
			// HenkouFg = true;
			// �yKPX1500671�zDEL end

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �������l�r�f�ݒ�
					trialData.setDciSuisoMSG(dciSuisoMsg);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �������l�r�f �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �S�x�t���[�^�C�g���X�V : �S�����f�[�^�ɑ΂��ĔS�x�t���[�^�C�g�����e�̒l��ݒ肷��
	 * 
	 * @param strFreeTitle_Ondo
	 *            : �S�x�t���[�^�C�g��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_Nendo(String strFreeTitle_Nendo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitleNendo(),
						strFreeTitle_Nendo);
				// �yKPX1500671�zMOD end

				// ���x�t���[�^�C�g���ݒ�
				trialData.setStrFreeTitleNendo(strFreeTitle_Nendo);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �S�x�t���[�^�C�g�� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� �S�x�t���[���e �X�V : �w�莎���f�[�^�ɑ΂��ĔS�x�t���[���e��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : �S�x�t���[���e
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNendo(int intShisakuSeq, String strFreeNendo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNendo(), strFreeNendo);
					// �yKPX1500671�zMOD end

					// �S�x�t���[���e�ݒ�
					trialData.setStrFreeNendo(strFreeNendo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� �S�x�t���[���� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� ���x�t���[�^�C�g���X�V : �S�����f�[�^�ɑ΂��ĉ��x�t���[�^�C�g�����e�̒l��ݒ肷��
	 * 
	 * @param strFreeTitle_Ondo
	 *            : ���x�t���[�^�C�g��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_Ondo(String strFreeTitle_Ondo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitleOndo(),
						strFreeTitle_Ondo);
				// �yKPX1500671�zMOD end

				// ���x�t���[�^�C�g���ݒ�
				trialData.setStrFreeTitleOndo(strFreeTitle_Ondo);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� ���x�t���[�^�C�g�� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * ����� ���x�t���[���e �X�V : �w�莎���f�[�^�ɑ΂��ĉ��x�t���[���e��ݒ肷��
	 * 
	 * @param strFreeOndo
	 *            : ���x�t���[���e
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeOndo(int intShisakuSeq, String strFreeOndo,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeOndo(), strFreeOndo);
					// �yKPX1500671�zMOD end

					// ���x�t���[���e�ݒ�
					trialData.setStrFreeOndo(strFreeOndo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���� ���x�t���[���� �X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[�^�C�g���C�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���C��ݒ肷��
	 * 
	 * @param strFreeTitle_4
	 *            : �t���[�^�C�g���C
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_4(String strFreeTitle_4,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle4(), strFreeTitle_4);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���C�ݒ�
				trialData.setStrFreeTitle4(strFreeTitle_4);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���C�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[���e�C�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�C��ݒ肷��
	 * 
	 * @param strFreeNaiyou_4
	 *            : �t���[���e�C
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_4(int intShisakuSeq,
			String strFreeNaiyou_4, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo4(), strFreeNaiyou_4);
					// �yKPX1500671�zMOD end

					// �t���[���e�C�ݒ�
					trialData.setStrFreeNaiyo4(strFreeNaiyou_4);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�C�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[�^�C�g���D�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���D��ݒ肷��
	 * 
	 * @param strFreeTitle_5
	 *            : �t���[�^�C�g���D
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_5(String strFreeTitle_5,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle5(), strFreeTitle_5);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���D�ݒ�
				trialData.setStrFreeTitle5(strFreeTitle_5);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���D�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[���e�D�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�D��ݒ肷��
	 * 
	 * @param strFreeNaiyou_5
	 *            : �t���[���e�D
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_5(int intShisakuSeq,
			String strFreeNaiyou_5, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo5(), strFreeNaiyou_5);
					// �yKPX1500671�zMOD end

					// �t���[���e�D�ݒ�
					trialData.setStrFreeNaiyo5(strFreeNaiyou_5);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�D�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[�^�C�g���E�X�V : �S�����f�[�^�ɑ΂��ăt���[�^�C�g���E�ݒ肷��
	 * 
	 * @param strFreeTitle_6
	 *            : �t���[�^�C�g���E
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeTitle_6(String strFreeTitle_6,
			BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getStrFreeTitle6(), strFreeTitle_6);
				// �yKPX1500671�zMOD end

				// �t���[�^�C�g���E�ݒ�
				trialData.setStrFreeTitle6(strFreeTitle_6);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[�^�C�g���E�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * �����t���[���e�E�X�V : �w�莎���f�[�^�ɑ΂��ăt���[���e�E��ݒ肷��
	 * 
	 * @param strFreeNaiyou_6
	 *            : �t���[���e�E
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuFreeNaiyou_6(int intShisakuSeq,
			String strFreeNaiyou_6, BigDecimal dciUserId) throws ExceptionBase {
		try {
			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrFreeNaiyo6(), strFreeNaiyou_6);
					// �yKPX1500671�zMOD end

					// �t���[���e�E�ݒ�
					trialData.setStrFreeNaiyo6(strFreeNaiyou_6);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());
				}
			}
		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����t���[���e�E�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	// ADD end 20120928 QP@20505 No.24

	/**
	 * 0113.�����쐬�����X�V : �w�莎���f�[�^�ɑ΂��č쐬������ݒ肷��
	 * 
	 * @param strSakuseiMemo
	 *            : �쐬����
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuSakuseiMemo(int intShisakuSeq,
			String strSakuseiMemo, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrSakuseiMemo(), strSakuseiMemo);
					// �yKPX1500671�zMOD end

					// �쐬�����ݒ�
					trialData.setStrSakuseiMemo(strSakuseiMemo);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����쐬�����X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0114.�����]���X�V : �w�莎���f�[�^�ɑ΂��ĕ]����ݒ肷��
	 * 
	 * @param strHyouka
	 *            : �]��
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuHyouka(int intShisakuSeq, String strHyouka,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getStrHyoka(), strHyouka);
					// �yKPX1500671�zMOD end

					// �]���ݒ�
					trialData.setStrHyoka(strHyouka);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����]���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0115.���샊�X�g�ʍX�V : ���샊�X�g�f�[�^�ɑ΂��ėʂ�ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciRyou
	 *            : ��
	 */
	public void UpdShisakuListRyo(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq, BigDecimal dciRyou) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// ���샊�X�g�I�u�W�F�N�g�擾
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�E�H��CD�E�H��SEQ����v�����ꍇ
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKouteiCd == prototypeListData.getIntKoteiCd()
						&& intKouteiSeq == prototypeListData.getIntKoteiSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(prototypeListData.getDciRyo(), dciRyou);
					// �yKPX1500671�zMOD end

					// �ʐݒ�
					prototypeListData.setDciRyo(dciRyou);

					// �X�V�ҏ��̐ݒ�
					prototypeListData.setDciKosinId(DataCtrl.getInstance()
							.getUserMstData().getDciUserid());
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎��샊�X�g�ʍX�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0116.���샊�X�g�F�X�V : ���샊�X�g�f�[�^�ɑ΂��ĐF��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param strColor
	 *            : �F
	 * @param dcdciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuListColor(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq, String strColor, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			Iterator ite = aryShisakuList.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// ���샊�X�g�I�u�W�F�N�g�擾
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�E�H��CD�E�H��SEQ����v�����ꍇ
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()
						&& intKouteiCd == prototypeListData.getIntKoteiCd()
						&& intKouteiSeq == prototypeListData.getIntKoteiSeq()) {

					// �F�ݒ�
					prototypeListData.setStrIro(strColor);

					// �X�V�ҏ��̐ݒ�
					prototypeListData.setDciKosinId(dciUserId);
					prototypeListData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎��샊�X�g�F�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0118.�����H���f�[�^�ǉ� : �����H���f�[�^�ɑ΂��Ē��ӎ�����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intChuiJikouNo
	 *            : ���ӎ���No
	 * @param strChuiJikou
	 *            : ���ӎ���
	 * @param dciUserId
	 *            : ���[�UID
	 * @return ManufacturingData : �ǉ������H���f�[�^
	 */
	public ManufacturingData AddSeizoKouteiData(int intShsakuSeq,
			int intChuiJikouNo, String strChuiJikou, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �f�[�^�ύX
			HenkouFg = true;

			Iterator ite = arySeizo.iterator();

			// ���ӎ���No�ő�l & Key����
			int iMaxTyuiNo = 0;
			BigDecimal dciShisakuShainCd = ptdtShaisakuHin.getDciShisakuUser();
			BigDecimal dciShisakuYear = ptdtShaisakuHin.getDciShisakuYear();
			BigDecimal dciShisakuOiBan = ptdtShaisakuHin.getDciShisakuNum();

			ManufacturingData newManufacturingData = new ManufacturingData();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����H���I�u�W�F�N�g�擾
				ManufacturingData manufacturingData = (ManufacturingData) ite
						.next();

				// ���ӎ���No�̍ő�l�ݒ�
				if (iMaxTyuiNo < manufacturingData.getIntTyuiNo()) {

					// ���ӎ���No�ő�l�̎擾�AKEY���ڂ̐ݒ���s���B
					iMaxTyuiNo = manufacturingData.getIntTyuiNo();
					dciShisakuShainCd = manufacturingData.getDciShisakuUser();
					dciShisakuYear = manufacturingData.getDciShisakuYear();
					dciShisakuOiBan = manufacturingData.getDciShisakuNum();

				}
			}

			// �V�K�����H���f�[�^�ݒ�
			newManufacturingData.setDciShisakuUser(dciShisakuShainCd);
			newManufacturingData.setDciShisakuYear(dciShisakuYear);
			newManufacturingData.setDciShisakuNum(dciShisakuOiBan);
			newManufacturingData.setIntTyuiNo(iMaxTyuiNo + 1);
			newManufacturingData.setStrTyuiNaiyo(strChuiJikou);

			// �X�V�ҏ��̐ݒ�
			newManufacturingData.setDciTorokuId(dciUserId);
			newManufacturingData.setStrTorokuHi(getSysDate());
			newManufacturingData.setDciKosinId(dciUserId);
			newManufacturingData.setStrKosinHi(getSysDate());

			// �����H�����X�g�ɒǉ�
			arySeizo.add(newManufacturingData);

			return newManufacturingData;

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̐����H���f�[�^�ǉ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0119.�����H���f�[�^�X�V : �����H���f�[�^�ɑ΂��Ē��ӎ�����ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intChuiJikouNo
	 *            : ���ӎ���No
	 * @param strChuiJikou
	 *            : ���ӎ���
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdSeizoKouteiData(int intChuiJikouNo, String strChuiJikou,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = arySeizo.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����H���I�u�W�F�N�g�擾
				ManufacturingData manufacturingData = (ManufacturingData) ite
						.next();

				// ���ӎ�������v�����ꍇ
				if (intChuiJikouNo == manufacturingData.getIntTyuiNo()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(manufacturingData.getStrTyuiNaiyo(),
							strChuiJikou);
					// �yKPX1500671�zMOD end

					// ���ӎ���No���擾����B
					manufacturingData.setStrTyuiNaiyo(strChuiJikou);

					// �X�V�ҏ��̐ݒ�
					manufacturingData.setDciKosinId(dciUserId);
					manufacturingData.setStrKosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̐����H���f�[�^�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0129.����\�o�� : ����\�o�͂ɕK�v�ȃf�[�^�𐶐����ԋp
	 * 
	 * @return ����\�f�[�^
	 */
	public PrototypeListData OutShisakuList() throws ExceptionBase {

		try {

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���\�o�͂����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return null;
	}

	// /**
	// * 0130.�T���v���\�o�� : �T���v���������o�͂ɕK�v�ȃf�[�^�𐶐����ԋp
	// * @return �T���v���������f�[�^
	// */
	// public OutputSample OutSampleList() {
	// return null;
	// }

	/**
	 * 0131.�����R�s�[�v�Z����ݒ� : �����R�s�[�v�Z�z��֎���v�Z����o�^����
	 * 
	 * @param intKeisanRetusu
	 *            : �v�Z��
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strKeisanFugo
	 *            : �v�Z����
	 */
	public void SetShisaku_ShisakuRetuCopyKeisan(int intKeisanRetusu,
			int intShisakuSeq, String strKeisanFugo) throws ExceptionBase {

		try {

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����R�s�[�v�Z����ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0132.�����R�s�[�v�Z�H���ݒ� : �����R�s�[�v�Z�z��֍H���v�Z����o�^����
	 * 
	 * @param intKeisanRetusu
	 *            : �v�Z��
	 * @param intKouteiRetusu
	 *            : �H����
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param strKeisanFugo
	 *            : �v�Z����
	 */
	public void SetKoutei_ShisakuRetuCopyKeisan(int intKeisanRetusu,
			int intKouteiRetusu, int intKouteiCd, int intKouteiSeq,
			String strKeisanFugo) throws ExceptionBase {

		try {

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����R�s�[�v�Z�H���ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0133.�����R�s�[�v�Z���s : �����R�s�[�v�Z���s��
	 */
	public void RunShisakuRetuCopyKeisan() throws ExceptionBase {

		try {

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����R�s�[�v�Z���s�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0134.���������f�[�^���� : ���������f�[�^�̌���
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @return �z��i���������f�[�^�j
	 */
	public ArrayList SearchGenkaGenryoData(int intShisakuSeq)
			throws ExceptionBase {

		// �V�K���X�g�C���X�^���X����
		ArrayList arr = new ArrayList();

		try {

			// �����F����SEQ���A0�̏ꍇ
			if (intShisakuSeq == 0) {
				arr = aryGenka;
			} else {

				Iterator ite = aryGenka.iterator();

				// ���X�g���������[�v
				while (ite.hasNext()) {
					// �����f�[�^�I�u�W�F�N�g�擾
					CostMaterialData costMaterialData = (CostMaterialData) ite
							.next();

					// �����F����SEQ�Ǝ����f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
					if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {
						// �ԋp���X�g�Ɏ����f�[�^�I�u�W�F�N�g�ǉ�
						arr.add(costMaterialData);
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���������e�[�u���f�[�^�ێ��̌��������f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return arr;
	}

	/**
	 * 0135.���������f�[�^�ǉ� : ���������f�[�^�̒ǉ�����
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @return �z��i���������f�[�^�j
	 */
	public void AddGenkaGenryoData(int intShisakuSeq) throws ExceptionBase {
		CostMaterialData costMaterialData = null;
		int intNewFlg = 0; // �V�K�f�[�^Flg

		try {

			// ����e�[�u���f�[�^���擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// ���������e�[�u���f�[�^����
			if (this.SearchGenkaGenryoData(intShisakuSeq).size() == 0) {
				// �ǉ�
				costMaterialData = new CostMaterialData();
				intNewFlg = 0;

			} else {
				// �X�V
				costMaterialData = (CostMaterialData) this
						.SearchGenkaGenryoData(intShisakuSeq).get(0);
				intNewFlg = 1;

			}

			// �V�K�f�[�^�ݒ�
			if (intNewFlg == 0) {
				// ����CD
				costMaterialData.setDciShisakuUser(this.ptdtShaisakuHin
						.getDciShisakuUser());
				costMaterialData.setDciShisakuYear(this.ptdtShaisakuHin
						.getDciShisakuYear());
				costMaterialData.setDciShisakuNum(this.ptdtShaisakuHin
						.getDciShisakuNum());
				// ����SEQ
				costMaterialData.setIntShisakuSeq(intShisakuSeq);
				// �o�^��ID�E�o�^���t
				costMaterialData.setDciTorokuId(this.ptdtShaisakuHin
						.getDciShisakuUser());
				costMaterialData.setStrTorokuHi(this.getSysDate());

			}

			// �X�V�f�[�^�ݒ�
			// ��d
			costMaterialData.setStrHizyu(trialData.getStrHizyu());
			// �e��
			costMaterialData.setStrYoryo(this.ptdtShaisakuHin.getStrYoryo());
			// ���萔
			costMaterialData.setStrIrisu(this.ptdtShaisakuHin.getStrIrisu());
			// ����
			costMaterialData.setStrGenkakeiBai(this.ptdtShaisakuHin
					.getStrBaika());
			// �X�V��ID�E�X�V���t
			costMaterialData.setDciKosinId(this.ptdtShaisakuHin
					.getDciShisakuUser());
			costMaterialData.setStrKosinHi(this.getSysDate());

			// �v�Z����
			UpdGenkaGenryoMath(costMaterialData);

			if (intNewFlg == 0) {
				// �V�K ���������e�[�u���f�[�^��ǉ�
				this.aryGenka.add(costMaterialData);

			} else {
				// �X�V ���������e�[�u���f�[�^��ݒ�
				for (int i = 0; i < this.aryGenka.size(); i++) {
					CostMaterialData csm = (CostMaterialData) this.aryGenka
							.get(i);

					if (csm.getIntShisakuSeq() == costMaterialData
							.getIntShisakuSeq()) {
						csm = costMaterialData;

					}

				}

			}

			// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			// �[�U�ʂ��v�Z
			String keisan = DataCtrl.getInstance().getTrialTblData()
					.KeisanZyutenType1(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0134);
			// costMaterialData.setStrZyutenSui(keisan);

			// �����[�U�ʂ��v�Z
			String keisan1 = DataCtrl.getInstance().getTrialTblData()
					.KeisanSuisoZyuten(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan1),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0134);
			// costMaterialData.setStrZyutenSui(keisan1);

			// �����[�U�ʂ��v�Z
			String keisan2 = DataCtrl.getInstance().getTrialTblData()
					.KeisanYusoZyuten(costMaterialData.getIntShisakuSeq());
			UpdGenkaValue(costMaterialData.getIntShisakuSeq(), DataCtrl
					.getInstance().getTrialTblData().checkNullString(keisan2),
					DataCtrl.getInstance().getUserMstData().getDciUserid(),
					JwsConstManager.JWS_COMPONENT_0135);
			// costMaterialData.setStrZyutenYu(keisan2);
			// 2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

		} catch (Exception e) {
			e.printStackTrace();
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���������e�[�u���f�[�^�ێ��̌��������f�[�^�ǉ������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			costMaterialData = null;

		}

	}

	/**
	 * 0136.�������� ���FG�X�V : �w�莎���f�[�^�ɑ΂��Ĉ��FG��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param Intinsatu
	 *            : ���FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdGenkaPrintFg(int intShisakuSeq, int Intinsatu,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = this.aryGenka.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				CostMaterialData costMaterialData = (CostMaterialData) ite
						.next();

				// ����SEQ����v
				if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(costMaterialData.getIntinsatu(), Intinsatu);
					// �yKPX1500671�zMOD end

					// ���FLG�ݒ�
					costMaterialData.setIntinsatu(Intinsatu);

					// �X�V�ҏ��̐ݒ�
					costMaterialData.setDciKosinId(dciUserId);
					costMaterialData.setStrKosinHi(getSysDate());

				}

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���������e�[�u���f�[�^�ێ��̈��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0137.�������� ��ʒl�X�V : �w�莎���f�[�^�ɑ΂��ĉ�ʒl��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strValue
	 *            : �ݒ�l
	 * @param dciUserId
	 *            : ���[�UID
	 * @param strName
	 *            : ���ږ�
	 */
	public void UpdGenkaValue(int intShisakuSeq, String strValue,
			BigDecimal dciUserId, String strName) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = this.aryGenka.iterator();

			// �l��Null�̏ꍇ�A�󔒂�ݒ肷��
			if (strName == null) {
				strName = "";
			}

			while (ite.hasNext()) {

				// ���������e�[�u���f�[�^�̎擾
				CostMaterialData costMaterialData = (CostMaterialData) ite
						.next();

				// ���������e�[�u���f�[�^�ɉ�ʂ�著���Ă����l��ݒ肷��
				if (intShisakuSeq == costMaterialData.getIntShisakuSeq()) {

					// �H���p�^�[���擾
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					// �H���p�^�[����Value1�擾
					String ptValue = null;

					if (ptKotei != null && ptKotei.length() > 0) {
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}

					// �[�U�ʐ���
					if (strName.equals(JwsConstManager.JWS_COMPONENT_0134)) {

						// ���̑��E���H�̏ꍇ
						if (JwsConstManager.JWS_KOTEITYPE_3.equals(ptValue)) {
							// �f�[�^�ύX�`�F�b�N�i���l��r�j
							chkHenkouDecData(
									costMaterialData.getStrZyutenSui(),
									strValue);
						}

						costMaterialData.setStrZyutenSui(strValue);

						// �[�U�ʖ���
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0135)) {

						// ���̑��E���H�̏ꍇ
						if (JwsConstManager.JWS_KOTEITYPE_3.equals(ptValue)) {
							// �f�[�^�ύX�`�F�b�N�i���l��r�j
							chkHenkouDecData(costMaterialData.getStrZyutenYu(),
									strValue);
						}

						costMaterialData.setStrZyutenYu(strValue);

						// //���v��
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0136) )
						// {
						// costMaterialData.setStrGokei(strValue);
						//
						// //������(kg)
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0137) )
						// {
						// costMaterialData.setStrGenryohi(strValue);
						//
						// //������(�P�{��)
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0138) )
						// {
						// costMaterialData.setStrGenryohiTan(strValue);

						// ��d
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0139)) {

						costMaterialData.setStrHizyu(strValue);

						// �e��
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0140)) {

						costMaterialData.setStrYoryo(strValue);

						// ����
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0141)) {

						costMaterialData.setStrIrisu(strValue);

						// �L������
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0127)) {
						// �f�[�^�ύX�`�F�b�N�i���l��r�j
						chkHenkouDecData(costMaterialData.getStrYukoBudomari(),
								strValue);

						costMaterialData.setStrYukoBudomari(strValue);

						// //���x����
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0142) )
						// {
						// costMaterialData.setStrLevel(strValue);
						//
						// //��d���Z��
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0143) )
						// {
						// costMaterialData.setStrHizyuBudomari(strValue);

						// ���Ϗ[�U��
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0128)) {
						// �f�[�^�ύX�`�F�b�N�i���l��r�j
						chkHenkouDecData(costMaterialData.getStrZyutenAve(),
								strValue);

						costMaterialData.setStrZyutenAve(strValue);

						// //������/cs
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0144) )
						// {
						// costMaterialData.setStrGenryohiCs(strValue);

						// �ޗ���
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0145)) {
						// �f�[�^�ύX�`�F�b�N�i���l��r�j
						chkHenkouDecData(costMaterialData.getStrZairyohiCs(),
								strValue);

						costMaterialData.setStrZairyohiCs(strValue);

						// �Œ��
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0129)) {
						// �f�[�^�ύX�`�F�b�N�i���l��r�j
						chkHenkouDecData(costMaterialData.getStrKeihiCs(),
								strValue);

						costMaterialData.setStrKeihiCs(strValue);

						// //�����v/cs
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0146) )
						// {
						// costMaterialData.setStrGenkakeiCs(strValue);
						//
						// //�����v/��
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0147) )
						// {
						// costMaterialData.setStrGenkakeiTan(strValue);

						// ����
					} else if (strName
							.equals(JwsConstManager.JWS_COMPONENT_0130)) {

						costMaterialData.setStrGenkakeiBai(strValue);

						// //�e��
						// } else if (
						// strName.equals(JwsConstManager.JWS_COMPONENT_0148) )
						// {
						// costMaterialData.setStrGenkakeiRi(strValue);

					}
					// �yKPX1500671�zMOD end

					// �v�Z����
					UpdGenkaGenryoMath(costMaterialData);

					// �X�V�ҏ��̐ݒ�
					costMaterialData.setDciKosinId(dciUserId);
					costMaterialData.setStrKosinHi(getSysDate());

				}

			}

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���������e�[�u���f�[�^�ێ��̒l�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0138.���������e�[�u���v�Z����
	 * 
	 * @param costMaterialData
	 *            ���������f�[�^
	 * @throws ExceptionBase
	 */
	public void UpdGenkaGenryoMath(CostMaterialData costMaterialData)
			throws ExceptionBase {

		try {

			// �e��
			double dblYouryo = checkNumericDouble(this.ptdtShaisakuHin
					.getStrYoryo());
			if (this.ptdtShaisakuHin.getStrYoryo() != null) {
				costMaterialData.setStrYoryo(SetShosuKeta(
						Double.toString(dblYouryo), 0));

			} else {
				costMaterialData.setStrYoryo(null);

			}

			// ����
			double dblIrisu = checkNumericDouble(this.ptdtShaisakuHin
					.getStrIrisu());
			if (this.ptdtShaisakuHin.getStrIrisu() != null) {
				costMaterialData.setStrIrisu(SetShosuKeta(
						Double.toString(dblIrisu), 0));

			} else {
				costMaterialData.setStrIrisu(null);

			}

			// ��d
			TrialData trialData = (TrialData) (this
					.SearchShisakuRetuData(costMaterialData.getIntShisakuSeq())
					.get(0));
			double dblHizyu = checkNumericDouble(trialData.getStrHizyu());
			if (trialData.getStrHizyu() != null) {
				costMaterialData.setStrHizyu(SetShosuKeta(
						Double.toString(dblHizyu), 3));

			} else {
				costMaterialData.setStrHizyu(null);

			}

			// �ޗ���
			double dblZairyohi = checkNumericDouble(costMaterialData
					.getStrZairyohiCs());
			if (costMaterialData.getStrZairyohiCs() != null) {
				costMaterialData.setStrZairyohiCs(SetShosuKeta(
						Double.toString(dblZairyohi), 2));

			} else {
				costMaterialData.setStrZairyohiCs(null);

			}

			// �Œ��
			double dblKeihi = checkNumericDouble(costMaterialData
					.getStrKeihiCs());
			if (costMaterialData.getStrKeihiCs() != null) {
				costMaterialData.setStrKeihiCs(SetShosuKeta(
						Double.toString(dblKeihi), 2));

			} else {
				costMaterialData.setStrKeihiCs(null);

			}

			// ����
			double dblBaika = checkNumericDouble(this.ptdtShaisakuHin
					.getStrBaika());
			if (this.ptdtShaisakuHin.getStrBaika() != null) {
				costMaterialData.setStrGenkakeiBai(SetShosuKeta(
						Double.toString(dblBaika), 2));

			} else {
				costMaterialData.setStrGenkakeiBai(null);

			}

			// �[�U�ʐ���
			double dblZyutenryoSuiso = checkNumericDouble(costMaterialData
					.getStrZyutenSui());
			if (costMaterialData.getStrZyutenSui() != null) {
				costMaterialData.setStrZyutenSui(SetShosuKeta(
						Double.toString(dblZyutenryoSuiso), 2));

			} else {
				// mod start
				// --------------------------------------------------------------------------------------
				// QP@00412_�V�T�N�C�b�N���� No.25
				// costMaterialData.setStrZyutenSui(null);
				costMaterialData.setStrZyutenSui(SetShosuKeta("0", 2));
				// mod end
				// --------------------------------------------------------------------------------------

			}

			// �[�U�ʖ���
			double dblZyutenryoYuso = checkNumericDouble(costMaterialData
					.getStrZyutenYu());
			if (costMaterialData.getStrZyutenYu() != null) {
				costMaterialData.setStrZyutenYu(SetShosuKeta(
						Double.toString(dblZyutenryoYuso), 2));

			} else {
				// mod start
				// --------------------------------------------------------------------------------------
				// QP@00412_�V�T�N�C�b�N���� No.25
				// costMaterialData.setStrZyutenYu(null);
				costMaterialData.setStrZyutenYu(SetShosuKeta("0", 2));
				// mod end
				// --------------------------------------------------------------------------------------

			}

			// ���Ϗ[�U��
			double dblZyutenryoAve = checkNumericDouble(costMaterialData
					.getStrZyutenAve());
			if (costMaterialData.getStrZyutenAve() != null) {
				costMaterialData.setStrZyutenAve(SetShosuKeta(
						Double.toString(dblZyutenryoAve), 2));

			} else {
				costMaterialData.setStrZyutenAve(null);

			}

			// �L������
			double dblYukoBudomari = checkNumericDouble(costMaterialData
					.getStrYukoBudomari());
			if (costMaterialData.getStrYukoBudomari() != null) {
				costMaterialData.setStrYukoBudomari(SetShosuKeta(
						Double.toString(dblYukoBudomari), 2));

			} else {
				costMaterialData.setStrYukoBudomari(null);

			}

			// ���v(�P�{:g)
			// : �e��x��d
			double dblGoukei = dblYouryo * dblHizyu;
			costMaterialData.setStrGokei(SetShosuKeta(
					Double.toString(dblGoukei), 3));

			// ���x����(g)
			// : �e�� x ����
			double dblLevelRyo = dblYouryo * dblIrisu;
			costMaterialData.setStrLevel(SetShosuKeta(
					Double.toString(dblLevelRyo), 2));

			// mod start
			// --------------------------------------------------------------------------------------
			// QP@00412_�V�T�N�C�b�N���� No.24
			// ��d���Z��(g)
			// // : ���x����x��d
			// costMaterialData.setStrHizyuBudomari(SetShosuKeta(Double.toString(dblLevelRyo*dblHizyu),2));
			// : ���Ϗ[�U��x��d
			costMaterialData.setStrHizyuBudomari(SetShosuKeta2(
					Double.toString(dblZyutenryoAve * dblHizyu), 2));
			// mod end
			// --------------------------------------------------------------------------------------

			// �H���`�F�b�N
			if (CheckKotei() == 1 || CheckKotei() == 2) {

				// ������/�P�[�X
				double dblGenryohiCs = 0.0;
				dblGenryohiCs = KeisanGenryoHi(costMaterialData);
				costMaterialData.setStrGenryohiCs(SetShosuKeta(
						Double.toString(dblGenryohiCs), 2));

				// ������i��g)
				// MOD 2013/8/2 okano�yQP@30151�zNo.34 start
				// : �u������/�P�[�X�v���u���x���ʁi���j�v*�P�O�O�O
				// double dblGenryohiKg = dblGenryohiCs / dblLevelRyo * 1000;
				// : �u������/�P�[�X�v��(�u���x���ʁi���j�v*�P�O�O�O * ��d)
				double dblGenryohiKg = dblGenryohiCs / (dblLevelRyo * dblHizyu)
						* 1000;
				// MOD 2013/8/2 okano�yQP@30151�zNo.34 end
				costMaterialData.setStrGenryohi(SetShosuKeta(
						Double.toString(dblGenryohiKg), 2));

				// ������i�P�{��)
				// : �u������/�P�[�X�v/����
				double dblGenryohiTan = dblGenryohiCs / dblIrisu;
				costMaterialData.setStrGenryohiTan(SetShosuKeta(
						Double.toString(dblGenryohiTan), 2));

				// �����v/�P�[�X
				// : �u������/�P�[�X�v�{�u�ޗ���/�P�[�X�v�i���͍��ځj�{�u�Œ��/�P�[�X�v�i���͍��ځj
				double dblGenkakeiCs = dblGenryohiCs + dblZairyohi + dblKeihi;
				costMaterialData.setStrGenkakeiCs(SetShosuKeta(
						Double.toString(dblGenkakeiCs), 2));

				// �����v/��
				// : �u�����v/�P�[�X�v/�u���萔�v
				double dblGenkakeiTan = dblGenkakeiCs / dblIrisu;
				costMaterialData.setStrGenkakeiTan(SetShosuKeta(
						Double.toString(dblGenkakeiTan), 2));

				// �e���i���j
				// : 1-�i�u�����v/�v/[�����v) ... %�\���ɕύX
				double dblSori = (1 - (dblGenkakeiTan / dblBaika)) * 100;
				costMaterialData.setStrGenkakeiRi(SetShosuKeta(
						Double.toString(dblSori), 2));

				// System.out.println(dblSori);

			} else {

				// �H�����s���ȏꍇ�A������Ɋւ�鍀�ڂ�NULL���i�[����

				// ������/�P�[�X
				costMaterialData.setStrGenryohiCs(null);
				// ������i��g)
				costMaterialData.setStrGenryohi(null);
				// ������i�P�{��)
				costMaterialData.setStrGenryohiTan(null);
				// �����v/�P�[�X
				costMaterialData.setStrGenkakeiCs(null);
				// �����v/��
				costMaterialData.setStrGenkakeiTan(null);
				// �e���i���j
				costMaterialData.setStrGenkakeiRi(null);

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���������e�[�u���v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * 0139.������(kg)�̌v�Z���� : ��������v�Z���A���������f�[�^�Ɋi�[����
	 * 
	 * @return ������/cs
	 * @throws ExceptionBase
	 */
	public double KeisanGenryoHi(CostMaterialData costMaterialData)
			throws ExceptionBase {

		// �v�Z���� : ������/cs
		double ret = 0.0;

		try {

			// ����SEQ
			int intShisakuSeq = costMaterialData.getIntShisakuSeq();

			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);
			;

			// �z���f�[�^�̎擾
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// �������v�d�ʁE�P���A�������v�d�ʁE�P��
			double dblSuisoGokeiryo = 0.0;
			double dblSuisoTanka = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblYusoTanka = 0.0;

			// �[�U�ʐ���
			double dblZyutenryoSuiso = checkNumericDouble(costMaterialData
					.getStrZyutenSui());
			// �[�U�ʖ���
			double dblZyutenryoYuso = checkNumericDouble(costMaterialData
					.getStrZyutenYu());
			// ���Ϗ[�U��
			double dblZyutenryoAve = checkNumericDouble(costMaterialData
					.getStrZyutenAve());
			// �e��
			double dblYoryo = checkNumericDouble(this.ptdtShaisakuHin
					.getStrYoryo());
			// ����
			double dblIrisu = checkNumericDouble(this.ptdtShaisakuHin
					.getStrIrisu());
			// ���x����
			double dblLevelryo = dblYoryo * dblIrisu;
			// �L������
			double dblYukoBudomari = checkNumericDouble(costMaterialData
					.getStrYukoBudomari()) / 100;
			// �d�オ�荇�v�d��
			double dblShiagariJuryo = dblSuisoGokeiryo;
			if (trialData.getDciShiagari() != null) {
				dblShiagariJuryo = checkNumericDouble(trialData
						.getDciShiagari());

			}

			// ���e�����l
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// ���X�g���������[�v
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// �H�������̎擾
				String strZokusei = haigoData.getStrKouteiZokusei();

				// �H�����I�΂�Ă��Ȃ��ꍇ
				if (strZokusei != null) {

					// ���e�����l�̎擾
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----�H�����v�d�ʂ����߂�----------------------

					// �z���f�[�^���l�̎擾

					// �H��CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// �H��SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());
					// �P��
					double dblTanka = checkNumericDouble(haigoData
							.getDciTanka());
					// ����
					double dblBudomari = checkNumericDouble(haigoData
							.getDciBudomari()) / 100;

					// ----���샊�X�g�f�[�^���A�z���ʂ��擾�������s��----------------------

					// �z����
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// ����SEQ�A�H��CD�A�H��SEQ����v�����ꍇ
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// �z���ʂ��擾
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ���[�v�𔲂���
							break;

						}

					}

					// ----���e�����l2�ɂ�镪�򏈗�----------------------
					if (strLiteralVal2.equals("1")
							|| strLiteralVal2.equals("2")) {

						// �����̌v�Z�������s�� [1:�E�ے����t, 2:���� or 1:�\�[�X, 2:�ʏ[�U���]
						// �� �u���̑������t�ȊO�p�^�[���v�̏ꍇ�́A
						// �@�@�\�[�X�ƕʏ[�U��ނ̍��v�d�ʂƋ��z�v�����߂�

						// ���v�d�ʂ����߂�
						dblSuisoGokeiryo += dblRyo;

						// ���v�P�������߂�
						// :�@�z����*�P��/����
						dblSuisoTanka += checkErrNumericDouble(dblRyo
								* dblTanka / dblBudomari);

					} else if (strLiteralVal2.equals("3")) {

						// �����̌v�Z�������s�� [3:����]

						// ���v�d�ʂ����߂�
						dblYusoGokeiryo += dblRyo;

						// ���v�P�������߂�
						// :�@�z����*�P��/����
						dblYusoTanka += checkErrNumericDouble(dblRyo * dblTanka
								/ dblBudomari);

					}

				}
			}

			// --------------������/�P�[�X�����߂�--------------

			// �����t�p�^�[��
			if (strLiteralVal1.equals("1")) {

				// �@ (�������v�P�� / �������v�d�� * �[�U�ʐ���(g) /1000)
				double dblAnsSuiso = checkErrNumericDouble(dblSuisoTanka
						/ dblSuisoGokeiryo * dblZyutenryoSuiso / 1000);

				// �A (�������v�P�� / �������v�d�� * �[�U�ʖ���(g) /1000)
				double dblAnsYuso = checkErrNumericDouble(dblYusoTanka
						/ dblYusoGokeiryo * dblZyutenryoYuso / 1000);

				// �B ���Ϗ[�U�� / ���x���� / �L�������@* ����
				double dblAnsVal = checkErrNumericDouble(dblZyutenryoAve
						/ dblLevelryo / dblYukoBudomari * dblIrisu);

				// ������/�P�[�X���� = [ (�@ + �A) * �B ]
				ret = (dblAnsSuiso + dblAnsYuso) * dblAnsVal;

				// ���̑������t�ȊO�p�^�[��
			} else if (strLiteralVal1.equals("2")) {

				// ���v�P��/ �d�オ��d�ʍ��v * ���Ϗ[�U�� / 1000 / �L������(%)
				ret = checkErrNumericDouble(dblSuisoTanka / dblShiagariJuryo
						* dblZyutenryoAve / 1000 / dblYukoBudomari);

			}

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("������(kg)�̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/**
	 * 0140.�H���`�F�b�N : �z���\(����\�@)��ʂ̒����t���������݂��Ă��邩�̃`�F�b�N���s��
	 * 
	 * @return �`�F�b�N���� [-1:���݂��Ă���A0:�I������Ă��Ȃ��A 1:�����t�I���A 2:���̑������t�ȊO�I��]
	 * @throws ExceptionBase
	 */
	public int CheckKotei() throws ExceptionBase {

		int ret = 0;

		try {

			// �z���f�[�^���X�g�̎擾
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// �H������ȏ㑶�݂���ꍇ�A���݃`�F�b�N���s��
			if (aryHaigoData.size() > 0) {

				// �H������
				String strKoteiZoku = ((MixedData) aryHaigoData.get(0))
						.getStrKouteiZokusei();

				if (strKoteiZoku != null) {

					String strValue1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strKoteiZoku);
					ret = checkNullInt(strValue1);

					for (int i = 1; i < aryHaigoData.size(); i++) {

						// �z���f�[�^�̎擾
						MixedData haigoData = (MixedData) aryHaigoData.get(i);

						String strChkZokusei = haigoData.getStrKouteiZokusei();
						if (strChkZokusei != null) {

							String strValue1Chk = DataCtrl.getInstance()
									.getLiteralDataZokusei()
									.selectLiteralVal1(strChkZokusei);

							// �z���\(����\�@)��ʂ̍H�������݂��Ă��邩���`�F�b�N
							if (!strValue1.equals(strValue1Chk)) {
								// ���݂��Ă���ꍇ
								ret = -1;

								// �����𒆒f����
								break;

							} else {
								// ���݂��Ă��Ȃ��ꍇ�A���e�����l1���i�[
								if (strValue1.equals("1")
										|| strValue1.equals("2")) {
									// �����t�A���̑������t�ȊO�̏ꍇ

									ret = checkNullInt(strValue1);

								} else {
									// �����t�A���̑������t�ȊO�ł͂Ȃ��ꍇ

									ret = 3;

								}

							}

						} else {
							ret = 0;

							// �����𒆒f����
							break;

						}

					}

				}

			} else {

			}

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�H���`�F�b�N���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/**
	 * �V�X�e�����t �Q�b�^�[
	 * 
	 * @return �V�X�e�����t�̒l��Ԃ�
	 */
	public String getSysDate() {
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}

	/**
	 * @return intSelectKotei
	 */
	public int getIntSelectKotei() {
		return intSelectKotei;
	}

	/**
	 * @param intSelectKotei
	 *            �Z�b�g���� intMaxKotei
	 */
	public void setIntSelectKotei(int intSelectKotei) {
		this.intSelectKotei = intSelectKotei;
	}

	/**
	 * @return intMaxKotei
	 */
	public int getIntMaxKotei() {
		return intMaxKotei;
	}

	/**
	 * @param intMaxKotei
	 *            �Z�b�g���� intMaxKotei
	 */
	public void setIntMaxKotei(int intMaxKotei) {
		this.intMaxKotei = intMaxKotei;
	}

	/**
	 * @return aryShisakuList
	 */
	public ArrayList getAryShisakuList() {
		return aryShisakuList;
	}

	/**
	 * @param aryShisakuList
	 *            �Z�b�g���� aryShisakuList
	 */
	public void setAryShisakuList(ArrayList aryShisakuList) {
		this.aryShisakuList = aryShisakuList;
	}

	/**
	 * ����i�f�[�^�\��
	 */
	public void dispPrototype() {

		System.out.println("");
		System.out
				.println("=========================�@T110�@========================");

		// ����i�f�[�^�\��

		System.out.println("");
		System.out.println("============�@" + 1 + "���ځ@============");

		System.out.println("����CD-�Ј�CD�F" + ptdtShaisakuHin.getDciShisakuUser());
		System.out.println("����CD-�N�F" + ptdtShaisakuHin.getDciShisakuYear());
		System.out.println("����CD-�ǔԁF" + ptdtShaisakuHin.getDciShisakuNum());
		System.out.println("�˗��ԍ��F" + ptdtShaisakuHin.getStrIrai());
		System.out.println("�i���F" + ptdtShaisakuHin.getStrHinnm());
		// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
		System.out.println("�̐Ӊ��CD�F" + ptdtShaisakuHin.getIntHansekicd());
		// ADD 2013/06/19 ogawa �yQP@30151�zNo.9 start
		System.out.println("�w��H��-���CD�F" + ptdtShaisakuHin.getIntKaishacd());
		System.out.println("�w��H��-�H��CD�F" + ptdtShaisakuHin.getIntKojoco());
		System.out.println("���CD�F" + ptdtShaisakuHin.getStrShubetu());
		System.out.println("���No�F" + ptdtShaisakuHin.getStrShubetuNo());
		System.out.println("�O���[�vCD�F" + ptdtShaisakuHin.getIntGroupcd());
		System.out.println("�`�[��CD�F" + ptdtShaisakuHin.getIntTeamcd());
		System.out.println("�ꊇ�\��CD�F" + ptdtShaisakuHin.getStrIkatu());
		System.out.println("�W������CD�F" + ptdtShaisakuHin.getStrZyanru());
		System.out.println("���[�UCD�F" + ptdtShaisakuHin.getStrUsercd());
		System.out.println("���������F" + ptdtShaisakuHin.getStrTokutyo());
		System.out.println("�p�r�F" + ptdtShaisakuHin.getStrYoto());
		System.out.println("���i��CD�F" + ptdtShaisakuHin.getStrKakaku());
		System.out.println("�S���c��CD�F" + ptdtShaisakuHin.getStrTantoEigyo());
		System.out.println("�������@CD�F" + ptdtShaisakuHin.getStrSeizocd());
		System.out.println("�[�U���@CD�F" + ptdtShaisakuHin.getStrZyutencd());
		System.out.println("�E�ە��@�F" + ptdtShaisakuHin.getStrSakin());
		System.out.println("�e��E��ށF" + ptdtShaisakuHin.getStrYokihozai());
		System.out.println("�e�ʁF" + ptdtShaisakuHin.getStrYoryo());
		System.out.println("�e�ʒP��CD�F" + ptdtShaisakuHin.getStrTani());
		System.out.println("���萔�F" + ptdtShaisakuHin.getStrIrisu());
		System.out.println("�戵���xCD�F" + ptdtShaisakuHin.getStrOndo());
		System.out.println("�ܖ����ԁF" + ptdtShaisakuHin.getStrShomi());
		System.out.println("�����F" + ptdtShaisakuHin.getStrGenka());
		System.out.println("�����F" + ptdtShaisakuHin.getStrBaika());
		System.out.println("�z�蕨�ʁF" + ptdtShaisakuHin.getStrSotei());
		System.out.println("���������F" + ptdtShaisakuHin.getStrHatubai());
		System.out.println("�v�攄��F" + ptdtShaisakuHin.getStrKeikakuUri());
		System.out.println("�v�旘�v�F" + ptdtShaisakuHin.getStrKeikakuRie());
		System.out.println("�̔��㔄��F" + ptdtShaisakuHin.getStrHanbaigoUri());
		System.out.println("�̔��㗘�v�F" + ptdtShaisakuHin.getStrHanbaigoRie());
		System.out.println("�׎p�F" + ptdtShaisakuHin.getStrNishugata());
		System.out.println("�����ӁF" + ptdtShaisakuHin.getStrSogo());
		System.out.println("�����w��F" + ptdtShaisakuHin.getStrShosu());
		System.out.println("�p�~��F" + ptdtShaisakuHin.getIntHaisi());
		System.out.println("�r���F" + ptdtShaisakuHin.getDciHaita());
		System.out.println("���@����F" + ptdtShaisakuHin.getIntSeihoShisaku());
		System.out.println("���상���F" + ptdtShaisakuHin.getStrShisakuMemo());
		System.out.println("���ӎ����\���F" + ptdtShaisakuHin.getIntChuiFg());
		System.out.println("�o�^��ID�F" + ptdtShaisakuHin.getDciTorokuid());
		System.out.println("�o�^���t�F" + ptdtShaisakuHin.getStrTorokuhi());
		System.out.println("�X�V��ID�F" + ptdtShaisakuHin.getDciKosinId());
		System.out.println("�X�V���t�F" + ptdtShaisakuHin.getStrKosinhi());
		System.out.println("�H���p�^�[���F" + ptdtShaisakuHin.getStrPt_kotei());
		System.out.println("�V�[�N���b�g�F" + ptdtShaisakuHin.getStrSecret());
	}

	/**
	 * �z���f�[�^�\��
	 */
	public void dispHaigo() {

		System.out.println("");
		System.out
				.println("=========================�@T120�@========================");

		// �z���f�[�^�z��\��
		for (int i = 0; i < this.aryHaigou.size(); i++) {

			MixedData MixedData = (MixedData) this.aryHaigou.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F" + MixedData.getDciShisakuUser());
			System.out.println("����CD-�N�F" + MixedData.getDciShisakuYear());
			System.out.println("����CD-�ǔԁF" + MixedData.getDciShisakuNum());
			System.out.println("�H��CD�F" + MixedData.getIntKoteiCd());
			System.out.println("�H��SEQ�F" + MixedData.getIntKoteiSeq());
			System.out.println("�H�����F" + MixedData.getStrKouteiNm());
			System.out.println("�H�������F" + MixedData.getStrKouteiZokusei());
			System.out.println("�H�����F" + MixedData.getIntKoteiNo());
			System.out.println("�������F" + MixedData.getIntGenryoNo());
			System.out.println("����CD�F" + MixedData.getStrGenryoCd());
			System.out.println("���CD�F" + MixedData.getIntKaishaCd());
			System.out.println("����CD�F" + MixedData.getIntBushoCd());
			System.out.println("�������́F" + MixedData.getStrGenryoNm());
			System.out.println("�P���F" + MixedData.getDciTanka());
			System.out.println("�����F" + MixedData.getDciBudomari());
			System.out.println("���ܗL���F" + MixedData.getDciGanyuritu());
			System.out.println("�|�_�F" + MixedData.getDciSakusan());
			System.out.println("�H���F" + MixedData.getDciShokuen());
			System.out.println("���_�F" + MixedData.getDciSosan());
			System.out.println("�F�F" + MixedData.getStrIro());
			System.out.println("�o�^��ID�F" + MixedData.getDciTorokuId());
			System.out.println("�o�^���t�F" + MixedData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + MixedData.getDciKosinId());
			System.out.println("�X�V���t�F" + MixedData.getStrKosinHi());
			System.out.println("�}�X�^�����F" + MixedData.getDciMaBudomari());

		}
	}

	/**
	 * �����f�[�^�\��
	 */
	public void dispTrial() {

		System.out.println("");
		System.out
				.println("=========================�@T131�@========================");

		// �����f�[�^�z��\��
		for (int i = 0; i < this.aryShisakuRetu.size(); i++) {

			TrialData TrialData = (TrialData) this.aryShisakuRetu.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F" + TrialData.getDciShisakuUser());
			System.out.println("����CD-�N�F" + TrialData.getDciShisakuYear());
			System.out.println("����CD-�ǔԁF" + TrialData.getDciShisakuNum());
			System.out.println("����SEQ�F" + TrialData.getIntShisakuSeq());
			System.out.println("����\�����F" + TrialData.getIntHyojiNo());
			System.out.println("���ӎ���NO�F" + TrialData.getStrTyuiNo());
			System.out.println("�T���v��NO�i���́j�F" + TrialData.getStrSampleNo());
			System.out.println("�����F" + TrialData.getStrMemo());
			System.out.println("���상���F" + TrialData.getStrSakuseiMemo());
			System.out.println("���Flg�F" + TrialData.getIntInsatuFlg());
			System.out.println("�����v�ZFlg�F" + TrialData.getIntZidoKei());
			System.out.println("�������ZNo�F" + TrialData.getIntGenkaShisan());
			System.out.println("���@No-1�F" + TrialData.getStrSeihoNo1());
			System.out.println("���@No-2�F" + TrialData.getStrSeihoNo2());
			System.out.println("���@No-3�F" + TrialData.getStrSeihoNo3());
			System.out.println("���@No-4�F" + TrialData.getStrSeihoNo4());
			System.out.println("���@No-5�F" + TrialData.getStrSeihoNo5());
			System.out.println("���_�F" + TrialData.getDciSosan());
			System.out.println("���_-�o��Flg�F" + TrialData.getIntSosanFlg());
			System.out.println("�H���F" + TrialData.getDciShokuen());
			System.out.println("�H��-�o��Flg�F" + TrialData.getIntShokuenFlg());
			System.out.println("�������_�x�F" + TrialData.getDciSuiSando());
			System.out.println("�������_�x-�o��Flg�F" + TrialData.getIntSuiSandoFlg());
			System.out.println("�������H���F" + TrialData.getDciSuiShokuen());
			System.out
					.println("�������H��-�o��Flg�F" + TrialData.getIntSuiShokuenFlg());
			System.out.println("�������|�_�F" + TrialData.getDciSuiSakusan());
			System.out
					.println("�������|�_-�o��Flg�F" + TrialData.getIntSuiSakusanFlg());
			System.out.println("���x�F" + TrialData.getStrToudo());
			System.out.println("���x-�o��Flg�F" + TrialData.getIntToudoFlg());
			System.out.println("�S�x�F" + TrialData.getStrNendo());
			System.out.println("�S�x-�o��Flg�F" + TrialData.getIntNendoFlg());
			System.out.println("���x�F" + TrialData.getStrOndo());
			System.out.println("���x-�o��Flg�F" + TrialData.getIntOndoFlg());
			System.out.println("PH�F" + TrialData.getStrPh());
			System.out.println("PH - �o��Flg�F" + TrialData.getIntPhFlg());
			System.out.println("���_�F���́F" + TrialData.getStrSosanBunseki());
			System.out.println("���_�F����-�o��Flg�F"
					+ TrialData.getIntSosanBunsekiFlg());
			System.out.println("�H���F���́F" + TrialData.getStrShokuenBunseki());
			System.out.println("�H���F����-�o��Flg�F"
					+ TrialData.getIntShokuenBunsekiFlg());
			System.out.println("��d�F" + TrialData.getStrHizyu());
			System.out.println("��d-�o��Flg�F" + TrialData.getIntHizyuFlg());
			System.out.println("���������F" + TrialData.getStrSuibun());
			System.out.println("��������-�o��Flg�F" + TrialData.getIntSuibunFlg());
			System.out.println("�A���R�[���F" + TrialData.getStrArukoru());
			System.out.println("�A���R�[��-�o��Flg�F" + TrialData.getIntArukoruFlg());
			System.out.println("�쐬�����F" + TrialData.getStrSakuseiMemo());
			System.out
					.println("�쐬����-�o��Flg�F" + TrialData.getIntSakuseiMemoFlg());
			System.out.println("�]���F" + TrialData.getStrHyoka());
			System.out.println("�]��-�o��Flg�F" + TrialData.getIntHyokaFlg());
			System.out.println("�t���[�@�^�C�g���F" + TrialData.getStrFreeTitle1());
			System.out.println("�t���[�@���e�F" + TrialData.getStrFreeNaiyo1());
			System.out.println("�t���[�@-�o��Flg�F" + TrialData.getIntFreeFlg());
			System.out.println("�t���[�A�^�C�g���F" + TrialData.getStrFreeTitle2());
			System.out.println("�t���[�A���e�F" + TrialData.getStrFreeNaiyo2());
			System.out.println("�t���[�A-�o��Flg�F" + TrialData.getIntFreeFl2());
			System.out.println("�t���[�B�^�C�g���F" + TrialData.getStrFreeTitle3());
			System.out.println("�t���[�B���e�F" + TrialData.getStrFreeNaiyo3());
			System.out.println("�t���[�B-�o��Flg�F" + TrialData.getIntFreeFl3());
			System.out.println("������t�F" + TrialData.getStrShisakuHi());
			System.out.println("�d��d�ʁF" + TrialData.getDciShiagari());
			System.out.println("�o�^��ID�F" + TrialData.getDciTorokuId());
			System.out.println("�o�^���t�F" + TrialData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + TrialData.getDciKosinId());
			System.out.println("�X�V���t�F" + TrialData.getStrkosinHi());
			System.out.println("�������Z�˗��F" + TrialData.getFlg_shisanIrai());
			System.out.println("�����˗��f�[�^�t���O�F" + TrialData.getFlg_init());
			System.out.println("�v�Z���F" + TrialData.getStrKeisanSiki());
			System.out.println("�L�����Z��FG�F" + TrialData.getFlg_cancel());
			System.out.println("������d�F" + TrialData.getStrHiju_sui());
			System.out.println("������d�@�o��FG�F" + TrialData.getIntHiju_sui_fg());

		}
	}

	/**
	 * ���샊�X�g�f�[�^�\��
	 */
	public void dispProtoList() {

		System.out.println("");
		System.out
				.println("=========================�@T132�@========================");

		// ���샊�X�g�f�[�^�z��\��
		for (int i = 0; i < this.aryShisakuList.size(); i++) {

			PrototypeListData PrototypeListData = (PrototypeListData) this.aryShisakuList
					.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F"
					+ PrototypeListData.getDciShisakuUser());
			System.out.println("����CD-�N�F"
					+ PrototypeListData.getDciShisakuYear());
			System.out.println("����CD-�ǔԁF"
					+ PrototypeListData.getDciShisakuNum());
			System.out.println("����SEQ�F" + PrototypeListData.getIntShisakuSeq());
			System.out.println("�H��CD�F" + PrototypeListData.getIntKoteiCd());
			System.out.println("�H��SEQ�F" + PrototypeListData.getIntKoteiSeq());
			System.out.println("�ʁF" + PrototypeListData.getDciRyo());
			System.out.println("�F�F" + PrototypeListData.getStrIro());
			System.out.println("�o�^��ID�F" + PrototypeListData.getDciTorokuId());
			System.out.println("�o�^���t�F" + PrototypeListData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + PrototypeListData.getDciKosinId());
			System.out.println("�X�V���t�F" + PrototypeListData.getStrKosinHi());
			// ADD start 20120914 QP@20505 No.1
			System.out.println("�H���d��d�ʁF"
					+ PrototypeListData.getDciKouteiShiagari());
			// ADD end 20120914 QP@20505 No.1

			System.out.println("�yQP@00412�z�ҏW�\�t���O�F"
					+ PrototypeListData.getIntHenshuOkFg());
		}

	}

	/**
	 * �����H���f�[�^�\��
	 */
	public void dispManufacturingData() {

		System.out.println("");
		System.out
				.println("=========================�@T133�@========================");

		// �����H���f�[�^�z��\��
		for (int i = 0; i < this.arySeizo.size(); i++) {

			ManufacturingData ManufacturingData = (ManufacturingData) this.arySeizo
					.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F"
					+ ManufacturingData.getDciShisakuUser());
			System.out.println("����CD-�N�F"
					+ ManufacturingData.getDciShisakuYear());
			System.out.println("����CD-�ǔԁF"
					+ ManufacturingData.getDciShisakuNum());
			System.out.println("���ӎ���NO�F" + ManufacturingData.getIntTyuiNo());
			System.out.println("���ӎ����F" + ManufacturingData.getStrTyuiNaiyo());
			System.out.println("�o�^��ID�F" + ManufacturingData.getDciTorokuId());
			System.out.println("�o�^���t�F" + ManufacturingData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + ManufacturingData.getDciKosinId());
			System.out.println("�X�V���t�F" + ManufacturingData.getStrKosinHi());
		}
	}

	/**
	 * ���ރf�[�^�\��
	 */
	public void dispShizaiData() {

		System.out.println("");
		System.out
				.println("=========================�@T140�@========================");

		// ���ރf�[�^�z��\��
		for (int i = 0; i < this.aryShizai.size(); i++) {

			ShizaiData ShizaiData = (ShizaiData) this.aryShizai.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F" + ShizaiData.getDciShisakuUser());
			System.out.println("����CD-�N�F" + ShizaiData.getDciShisakuYear());
			System.out.println("����CD-�ǔԁF" + ShizaiData.getDciShisakuNum());
			System.out.println("����SEQ�F" + ShizaiData.getIntShizaiSeq());
			System.out.println("�\�����F" + ShizaiData.getIntHyojiNo());
			System.out.println("����CD�F" + ShizaiData.getStrShizaiCd());
			System.out.println("���ޖ��́F" + ShizaiData.getStrShizaiNm());
			System.out.println("�P���F" + ShizaiData.getDciTanka());
			System.out.println("�����F" + ShizaiData.getDciBudomari());
			System.out.println("�o�^��ID�F" + ShizaiData.getDciTorokuId());
			System.out.println("�o�^���t�F" + ShizaiData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + ShizaiData.getDciKosinId());
			System.out.println("�X�V���t�F" + ShizaiData.getStrKosinHi());
		}
	}

	/**
	 * ���������f�[�^�\��
	 */
	public void dispCostMaterialData() {

		System.out.println("");
		System.out
				.println("=========================�@T141�@========================");

		// ���������f�[�^�z��\��
		for (int i = 0; i < this.aryGenka.size(); i++) {

			CostMaterialData CostMaterialData = (CostMaterialData) this.aryGenka
					.get(i);

			System.out.println("");
			System.out.println("============�@" + (i + 1) + "���ځ@============");

			System.out.println("����CD-�Ј�CD�F"
					+ CostMaterialData.getDciShisakuUser());
			System.out
					.println("����CD-�N�F" + CostMaterialData.getDciShisakuYear());
			System.out
					.println("����CD-�ǔԁF" + CostMaterialData.getDciShisakuNum());

			System.out.println("����SEQ�F" + CostMaterialData.getIntShisakuSeq());
			System.out.println("���flg�F" + CostMaterialData.getIntinsatu());
			System.out.println("�d�_�ʐ����F" + CostMaterialData.getStrZyutenSui());
			System.out.println("�d�_�ʖ����F" + CostMaterialData.getStrZyutenYu());
			System.out.println("���v�ʁF" + CostMaterialData.getStrGokei());
			System.out.println("������F" + CostMaterialData.getStrGenryohi());
			System.out.println("������i1�{�j�F"
					+ CostMaterialData.getStrGenryohiTan());
			System.out.println("��d�F" + CostMaterialData.getStrHizyu());
			System.out.println("�e�ʁF" + CostMaterialData.getStrYoryo());
			System.out.println("�����F" + CostMaterialData.getStrIrisu());
			System.out.println("�L�������F" + CostMaterialData.getStrYukoBudomari());
			System.out.println("���x���ʁF" + CostMaterialData.getStrLevel());
			System.out
					.println("��d�����F" + CostMaterialData.getStrHizyuBudomari());
			System.out.println("���Ϗ[�U�ʁF" + CostMaterialData.getStrZyutenAve());
			System.out
					.println("1C/S������F" + CostMaterialData.getStrGenryohiCs());
			System.out
					.println("1C/S�ޗ���F" + CostMaterialData.getStrZairyohiCs());
			System.out.println("1C/S�o��F" + CostMaterialData.getStrKeihiCs());
			System.out
					.println("1C/S�����v�F" + CostMaterialData.getStrGenkakeiCs());
			System.out.println("1�����v�F" + CostMaterialData.getStrGenkakeiTan());
			System.out.println("1�����F" + CostMaterialData.getStrGenkakeiBai());
			System.out.println("1�e�����F" + CostMaterialData.getStrGenkakeiRi());
			System.out.println("�o�^��ID�F" + CostMaterialData.getDciTorokuId());
			System.out.println("�o�^���t�F" + CostMaterialData.getStrTorokuHi());
			System.out.println("�X�V��ID�F" + CostMaterialData.getDciKosinId());
			System.out.println("�X�V���t�F" + CostMaterialData.getStrKosinHi());
		}
	}

	/**
	 * �󕶎��`�F�b�N�iString�j
	 */
	public String checkNullString(String val) {
		String ret = null;
		try {
			// �l���󕶎��łȂ��ꍇ
			if (!val.equals("")) {
				ret = val;
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * �󕶎��`�F�b�N�iString�j
	 */
	public String toString(Object val, String def) {

		String ret = def;

		try {

			ret = val.toString();

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	public String toString(Object obj, String def, String reprceTg) {

		String ret = def;
		try {

			ret = toString(obj, def);

			ret = ret.replaceAll(reprceTg, "");

		} catch (Exception e) {

		}
		return ret;

	}

	/**
	 * �󕶎��`�F�b�N�iDecimal�j
	 */
	public BigDecimal checkNullDecimal(String val) {
		BigDecimal ret = null;
		try {
			// �l���󕶎��łȂ��ꍇ
			if (!val.equals("")) {
				ret = new BigDecimal(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * �󕶎��`�F�b�N�iInt�j
	 */
	public int checkNullInt(String val) {
		int ret = -1;
		try {
			// �l���󕶎��łȂ��ꍇ
			if (!val.equals("")) {
				ret = Integer.parseInt(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * ���l�`�F�b�N�iDouble�j
	 */
	public double checkNumericDouble(String val) {
		double ret = 0.0;
		try {
			// �l���󕶎��łȂ��ꍇ
			if (!val.equals("")) {
				ret = Double.parseDouble(val);
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * ���l�`�F�b�N�iDouble�j
	 */
	public double checkNumericDouble(Object val) {
		double ret = 0.0;
		try {
			// �l���󕶎��łȂ��ꍇ
			if (val != null) {
				ret = Double.parseDouble(val.toString());
			}
		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * �G���[���l�`�F�b�N(Double) : �Ώےl��NaN����Infinite�̏ꍇ�̔��菈��
	 * 
	 * @param val
	 *            : �Ώےl
	 * @return double�^�̒l
	 */
	private double checkErrNumericDouble(double val) {
		double ret = 0.0;

		try {

			// �l��NaN, Infinite�̏ꍇ
			if (Double.isNaN(val) || Double.isInfinite(val)) {
				ret = 0.0;

			} else {
				ret = val;

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * �����������킹����(���͐؂�グ)
	 * 
	 * @param dblValue
	 *            : �Ώےl
	 * @param intKetasu
	 *            : �w�菬������
	 * @return �����킹�ҏW�㕶����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// �Ώےl���󕶎��ł͖����ꍇ
			if (checkNullString(strValue) != "") {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(strValue);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_UP)
						.toString();

			} else {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(ret);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_UP)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/**
	 * �����������킹����(���͐؂�グ)
	 * 
	 * @param dblValue
	 *            : �Ώےl
	 * @param intKetasu
	 *            : �w�菬������
	 * @return �����킹�ҏW�㕶����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta2(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// �Ώےl���󕶎��ł͖����ꍇ
			if (checkNullString(strValue) != "") {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(strValue);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_DOWN)
						.toString();

			} else {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(ret);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_DOWN)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/**
	 * �����������킹����( �l�̌ܓ� )
	 * 
	 * @param dblValue
	 *            : �Ώےl
	 * @param intKetasu
	 *            : �w�菬������
	 * @return �����킹�ҏW�㕶����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String SetShosuKeta3(String strValue, int intKetasu)
			throws ExceptionBase {

		String ret = "0";

		try {

			// �Ώےl���󕶎��ł͖����ꍇ
			if (checkNullString(strValue) != "") {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(strValue);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_UP)
						.toString();

			} else {

				// �l��Decimal�ɐݒ�
				BigDecimal dciValue = new BigDecimal(ret);

				// �l���w�菬�������ɍ��킹��
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_UP)
						.toString();

			}

		} catch (Exception e) {

		} finally {

		}
		return ret;

	}

	/************************************************************************************
	 * 
	 * �R�����g�s���� : �w�蕶����A�w�茅���ɂăR�����g�s�𔻒肷��i�w�茅���S�Ă�9�������ꍇ�̓R�����g�s�j
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public boolean commentChk(String strVal, int intKeta) {

		// �߂�l������
		boolean ret = true;

		try {

			// �w�蕶����NULL�̏ꍇ
			if (strVal == null) {

				ret = false;

			}
			// �w�蕶����NULL�łȂ��ꍇ
			else {

				// �w�蕶����̌������w�茅�������̏ꍇ
				if (strVal.length() < intKeta) {

					ret = false;

				}
				// �w�蕶����̌������w�茅�������łȂ��ꍇ
				else {

					// �w�蕶�������ALL9�`�F�b�N
					for (int i = 0; i < strVal.length(); i++) {

						// 1�������Ɏ擾
						String subVal = strVal.substring(i, i + 1);

						// �������u9�v�̏ꍇ
						if (subVal.equals("9")) {

							// �������Ȃ�

						}
						// �������u9�v�ȊO�̏ꍇ
						else {

							ret = false;

						}

					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		// �ԋp
		return ret;
	}

	/************************************************************************************
	 * 
	 * �w���Ё@���������擾 : ����B��ʂŎw�肵����Ђ̌��������擾
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public int getKaishaGenryo() {

		int ret = Integer.parseInt(JwsConstManager.JWS_KETA_GENRYO);

		try {

			// �w���ЃR�[�h�擾
			int kaishaCd = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuhinData().getIntKaishacd();

			// ��Ѓf�[�^�R�[�h����
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

			for (int i = 0; i < kaishaData.getArtKaishaCd().size(); i++) {

				// ��ЃR�[�h
				String getkaishaCd = kaishaData.getArtKaishaCd().get(i)
						.toString();

				// ������
				String getketa_genryo = kaishaData.getAryKaishaGenryo().get(i)
						.toString();

				// �w���ЃR�[�h�Ɠ������ꍇ
				if (Integer.parseInt(getkaishaCd) == kaishaCd) {

					// �������ݒ�
					ret = Integer.parseInt(getketa_genryo);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * ����� �������ZFG�X�V : �w�莎���f�[�^�ɑ΂��Č������ZFG��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param genkaIraiFg
	 *            : �������Z�˗�FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuGenkaIraiFg(int intShisakuSeq, int genkaIraiFg,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getFlg_shisanIrai(), genkaIraiFg);
					// �yKPX1500671�zMOD end

					// �T���v��No�ݒ�
					trialData.setFlg_shisanIrai(genkaIraiFg);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����T���v��No�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// 2011/04/12 QP@10181_No.67 TT Nishigawa Change Start
	// -------------------------
	/**
	 * ����� �L�����Z��FG�X�V : �w�莎���f�[�^�ɑ΂��ăL�����Z��FG��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param genkaCancelFg
	 *            : �L�����Z��FG
	 * @param dciUserId
	 *            : ���[�UID
	 */
	public void UpdShisakuRetuCancelIraiFg(int intShisakuSeq,
			int genkaCancelFg, BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �f�[�^�ύX�`�F�b�N
					chkHenkouData(trialData.getFlg_cancel(), genkaCancelFg);
					// �yKPX1500671�zMOD end

					// �L�����Z��FG�ݒ�
					trialData.setFlg_cancel(genkaCancelFg);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����T���v��No�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	// 2011/04/12 QP@10181_No.67 TT Nishigawa Change End
	// -------------------------

	/**
	 * ����� �������ZFG�ҏW�s�w�� : �o�^���ɁA�����˗��f�[�^�t���O��on�ɂ���
	 */
	public void setShisakuRetuFlg_initCtrl() throws ExceptionBase {

		try {

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �������Z�˗�����Ă���f�[�^�̏ꍇ
				if (trialData.getFlg_shisanIrai() == 1) {

					// �����˗��f�[�^�t���O��on
					trialData.setFlg_init(1);

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎����T���v��No�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/**
	 * �f�[�^�ύX�t���O �Q�b�^�[
	 * 
	 * @return HenkouFg : �f�[�^�ύX�t���O�ifalse:�f�[�^�ύX�����Atrue:�f�[�^�ύX�L��j
	 */
	public boolean getHenkouFg() {
		return HenkouFg;
	}

	/**
	 * �f�[�^�ύX�t���O �Z�b�^�[
	 * 
	 * @param henkouFg
	 *            : �f�[�^�ύX�t���O�ifalse:�f�[�^�ύX�����Atrue:�f�[�^�ύX�L��j
	 */
	public void setHenkouFg(boolean henkouFg) {
		HenkouFg = henkouFg;
	}

	/**
	 * �V�K�����`�F�b�N
	 * 
	 * @param strCd
	 *            : �����R�[�h
	 */
	public boolean chkNgenryo(String strCd) {

		boolean rec = true;

		// �����R�[�h���󔒂̏ꍇ
		if (strCd == null || strCd.length() == 0) {

			rec = false;

		}
		// �����R�[�h���󔒂łȂ��ꍇ
		else {

			// �����R�[�h�̈ꕶ���ڂ��擾
			String strFirst = strCd.substring(0, 1);

			// �ꕶ���ڂ�N�̏ꍇ
			if (strFirst.equals("N")) {

				// �������Ȃ�

			}
			// �ꕶ���ڂ�N�łȂ��ꍇ
			else {

				rec = false;

			}
		}

		return rec;

	}

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.33,34
	/**
	 * JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ�@���P
	 */
	public void setShisakuListHenshuOkFg() throws ExceptionBase {

		try {

			// �P�D�����\�����Ɠo�^���Ɏ��샊�X�g�f�[�^�ɉ��L�Q�`�T�̏����Ńt���O��ݒ�

			// ����񌏐������[�v
			for (int i = 0; i < aryShisakuRetu.size(); i++) {
				// �����f�[�^�擾
				TrialData TrialData = (TrialData) aryShisakuRetu.get(i);

				// �Q�D�˗��m��t���O���P�̃f�[�^
				if (TrialData.getFlg_init() == 1) {

					// ���샊�X�g���������[�v
					Iterator ite = aryShisakuList.iterator();
					while (ite.hasNext()) {

						// ���샊�X�g�I�u�W�F�N�g�擾
						PrototypeListData prototypeListData = (PrototypeListData) ite
								.next();

						// ����SEQ����v�����ꍇ
						if (TrialData.getIntShisakuSeq() == prototypeListData
								.getIntShisakuSeq()) {

							// �R�D���g�̔z���ʂɓ��͂̂Ȃ��f�[�^�i�� or 0�j
							if (prototypeListData.getDciRyo() == null
									|| (prototypeListData
											.getDciRyo()
											.compareTo(new BigDecimal("0.0000")) == 0)) {
								prototypeListData.setIntHanshuOkFg(0);
							}
							// �S�D���g�̔z���ʂɓ��͂̂���f�[�^
							else {
								prototypeListData.setIntHanshuOkFg(1);
							}
						}
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ�@���P
	 */
	public void setShisakuListHenshuOkFg_init() throws ExceptionBase {

		try {
			// ���샊�X�g���������[�v
			Iterator ite = aryShisakuList.iterator();
			while (ite.hasNext()) {
				// ���샊�X�g�I�u�W�F�N�g�擾
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();
				prototypeListData.setIntHanshuOkFg(0);
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * JWS���샊�X�g�f�[�^.�ҏW�ۃ`�F�b�N�@���Q �@�@�F�@�e�������u0�v�̏ꍇ�͑S������
	 * 
	 * @param : ����SEQ
	 * @param : �H��CD
	 * @param : �H��SEQ
	 */
	public boolean checkListHenshuOkFg(int intShisakuSeq, int intKoteiCd,
			int intKoteiSeq) throws ExceptionBase {

		boolean ret = true;

		try {

			// �P�D��������Ɏ��샊�X�g�f�[�^�擾
			Iterator ite = aryShisakuList.iterator();
			while (ite.hasNext()) {

				// �ҏW�t���O�`�F�b�N�Ώ�
				boolean blnChkFgShisakuSeq = false;
				boolean blnChkFgKoteiCd = false;
				boolean blnChkFgKoteiSeq = false;

				// ���샊�X�g�I�u�W�F�N�g�擾
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�����i0�̏ꍇ�͑S�������j
				if (intShisakuSeq == 0) {
					blnChkFgShisakuSeq = true;
				} else {
					if (intShisakuSeq == prototypeListData.getIntShisakuSeq()) {
						blnChkFgShisakuSeq = true;
					}
				}

				// �H��CD�����i0�̏ꍇ�͑S�������j
				if (intKoteiCd == 0) {
					blnChkFgKoteiCd = true;
				} else {
					if (intKoteiCd == prototypeListData.getIntKoteiCd()) {
						blnChkFgKoteiCd = true;
					}
				}

				// �H��SEQ�����i0�̏ꍇ�͑S�������j
				if (intKoteiSeq == 0) {
					blnChkFgKoteiSeq = true;
				} else {
					if (intKoteiSeq == prototypeListData.getIntKoteiSeq()) {
						blnChkFgKoteiSeq = true;
					}
				}

				// �ҏW�\�t���O�`�F�b�N
				if (blnChkFgShisakuSeq && blnChkFgKoteiCd && blnChkFgKoteiSeq) {
					int intHenshuOkFg = prototypeListData.getIntHenshuOkFg();
					if (intHenshuOkFg == 1) {
						ret = false;
					}
				}
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * �����ҏW�ۃ`�F�b�N�@���T
	 * 
	 * @param : ����SEQ
	 */
	public boolean checkShisakuIraiKakuteiFg(int intShisakuSeq)
			throws ExceptionBase {

		boolean ret = true;

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {
				// �����f�[�^�I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �����F����SEQ�Ǝ����f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ�i0�̏ꍇ�͑S���j
				if (intShisakuSeq == trialData.getIntShisakuSeq()
						|| intShisakuSeq == 0) {
					// �˗��m��t���O���`�F�b�N
					if (trialData.getFlg_init() == 1) {
						ret = false;
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ���JWS���샊�X�g�f�[�^.�ҏW�\�t���O�ݒ肪���s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * �z���f�[�^�F���� : �z�����X�g���z���f�[�^�̐F������
	 * 
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @throws ExceptionBase
	 */
	public String searchHaigouGenryoColor(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		String ret = "-1";

		try {
			Iterator ite = aryHaigou.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �����F�ݒ�
					ret = mixData.getStrIro();

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������F���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * ���샊�X�g�f�[�^�F���� : ���샊�X�g���z���ʃf�[�^�̐F������
	 * 
	 * @param intShisakuSeq
	 *            : ����r�d�p
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @throws ExceptionBase
	 */
	public String searchShisakuListColor(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		String ret = "-1";

		try {
			Iterator ite = aryShisakuList.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// �H��CD�E�H��SEQ����v
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// �����F�ݒ�
					ret = PrototypeListData.getStrIro();

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������F���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;

	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.8
	/**
	 * �f�[�^�ݒ�i�V�K�j : XML�f�[�^�i����i�q�d�b�j���R���X�g�l��ݒ肷��B
	 * 
	 * @param xdtData
	 *            : XML�f�[�^
	 * @throws ExceptionBase
	 */
	public void setConstData(XmlData xdtSetXml) throws ExceptionBase {

		try {
			XmlData xdtData = xdtSetXml;

			/**********************************************************
			 * �@T110�i�[
			 *********************************************************/
			// �@ID�i�[
			String strKinoId = "tr_shisakuhin";

			// �@�S�̔z��擾
			ArrayList t110 = xdtData.GetAryTag(strKinoId);

			// �@�e�[�u���z��擾
			ArrayList tableT110 = (ArrayList) t110.get(0);

			// �@���R�[�h�擾
			for (int i = 1; i < tableT110.size(); i++) {
				// �@�P���R�[�h�擾
				ArrayList recData = ((ArrayList) ((ArrayList) tableT110.get(i))
						.get(0));

				// �@�f�[�^�i�[
				for (int j = 0; j < recData.size(); j++) {

					// �@���ږ��擾
					String recNm = ((String[]) recData.get(j))[1];
					// �@���ڒl�擾
					String recVal = ((String[]) recData.get(j))[2];

					/***************** JWS�R���X�g�}�l�[�W���f�[�^�֒l�Z�b�g *********************/
					// ��\�H��Z�b�g
					if (recNm == "cd_daihyo_kojo") {
						JwsConstManager.JWS_CD_DAIHYO_KOJO = checkNullString(recVal);
					}
					// ��\��ЃZ�b�g
					if (recNm == "cd_daihyo_kaisha") {
						JwsConstManager.JWS_CD_DAIHYO_KAISHA = checkNullString(recVal);
					}
					// add start
					// -------------------------------------------------------------------------------
					// QP@00412_�V�T�N�C�b�N���� No.4
					// ��\�H��Z�b�g
					if (recNm == "nm_shiyo") {
						JwsConstManager.JWS_NM_SHIYO = checkNullString(recVal);
					}
					// add end
					// -------------------------------------------------------------------------------
				}
				recData.clear();
			}
			tableT110.clear();
			t110.clear();

		} catch (Exception e) {
			e.printStackTrace();
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̃f�[�^�ݒ�i�ڍ�or���@�R�s�[�j�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.30
	/**
	 * �V�K����or�R�����g�sor�󔒍s�`�F�b�N : �z�����X�g���z���f�[�^�̌����R�[�h��茟��
	 * 
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @return boolean : �V�K����or�R�����g�sor�󔒍s:ture ����:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouGenryoCdSinki(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		boolean ret = false;

		try {
			Iterator ite = aryHaigou.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �V�K����or�R�����g�sor�󔒍s�̏ꍇ
					if (mixData.getStrGenryoCd() == null) {
						ret = true;
					} else if (mixData.getStrGenryoCd().indexOf("N") != -1) {
						ret = true;
					} else if (mixData.getStrGenryoCd().indexOf("n") != -1) {
						ret = true;
					} else if (commentChk(mixData.getStrGenryoCd())) {
						ret = true;
					}

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������F���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	// 2011/06/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/**
	 * ��ЃR�[�h�`�F�b�N : �z�����X�g���z���f�[�^�̉�ЃR�[�h��茟��
	 * 
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @return boolean : �L���[�s�[:ture �L���[�s�[�ȊO:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouKaishaCd(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		boolean ret = false;

		try {

			// ��{���̉�ЃR�[�h���L���[�s�[�̏ꍇ
			if (DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData()
					.getIntKaishacd() == Integer
					.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {

			} else {
				return false;
			}

			Iterator ite = aryHaigou.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// ��ЃR�[�h���L���[�s�[�̏ꍇ
					if (mixData.getIntKaishaCd() == Integer
							.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {

						// �������̏ꍇ
						if (mixData.getStrGenryoNm() != null
								&& mixData.getStrGenryoNm().substring(0, 1)
										.equals(JwsConstManager.JWS_MARK_0001)) {
							ret = false;
						} else {
							ret = true;
						}
					}
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	// 2011/06/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------

	/************************************************************************************
	 * 
	 * �R�����g�s���� : �R�����g�s�𔻒肷��i�S�Ă�9�������ꍇ�̓R�����g�s�j
	 * 
	 * @author TT nishigawa
	 * 
	 ************************************************************************************/
	public boolean commentChk(String strVal) {

		// �߂�l������
		boolean ret = true;

		try {
			// �w�蕶����NULL�̏ꍇ
			if (strVal == null) {
				ret = false;
			}
			// �w�蕶����NULL�łȂ��ꍇ
			else {
				// �w�蕶�������ALL9�`�F�b�N
				for (int i = 0; i < strVal.length(); i++) {

					// 1�������Ɏ擾
					String subVal = strVal.substring(i, i + 1);

					// �������u9�v�̏ꍇ
					if (subVal.equals("9")) {

						// �������Ȃ�

					}
					// �������u9�v�ȊO�̏ꍇ
					else {
						ret = false;
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.31
	/**
	 * �}�X�^������r�`�F�b�N
	 * 
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @return boolean : ����:ture ����:false
	 * @throws ExceptionBase
	 */
	public boolean searchHaigouGenryoMaBudomari(int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		boolean ret = false;

		try {
			Iterator ite = aryHaigou.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �󔒍s�ƃR�����g�s�Ɓ�������true��ԋp
					if (mixData.getStrGenryoCd() == null) {
						ret = true;
					} else if (commentChk(mixData.getStrGenryoCd())) {
						ret = true;
					}

					else if (mixData.getStrGenryoNm() != null
							&& mixData.getStrGenryoNm().indexOf(
									JwsConstManager.JWS_MARK_0001) != -1) {
						ret = true;
					}

					// �V�K�����A���������̏ꍇ
					else {
						// null�`�F�b�N
						if (mixData.getDciBudomari() == null
								|| mixData.getDciMaBudomari() == null) {
							// �z���f�[�^�ƃ}�X�^�̕���������̏ꍇ
							if (mixData.getDciBudomari() == null
									&& mixData.getDciMaBudomari() == null) {
								ret = true;
							}
						} else {
							// �z���f�[�^�ƃ}�X�^�̕���������̏ꍇ
							if (mixData.getDciBudomari().compareTo(
									mixData.getDciMaBudomari()) == 0) {
								ret = true;
							}
						}
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�������F���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/**
	 * �z�������}�X�^�����X�V : �w�茴���f�[�^�ɑ΂��ă}�X�^������ݒ肷��
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @param dciBudomari
	 *            : ��������
	 */
	public void UpdHaigoGenryoMaBudomari(int intKouteiCd, int intKouteiSeq,
			BigDecimal dciBudomari) throws ExceptionBase {

		try {

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �����ݒ�
					mixData.setDciMaBudomari(dciBudomari);

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z�����������X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.7
	/**
	 * �R�s�[�Ώیv�Z���ϊ� �@�������̌v�Z����������̌����J�n�ʒu����͂��A �@�v�Z�\�Ȍv�Z���ɕϊ�����
	 * 
	 * @param strkeisanSiki
	 *            : �v�Z��
	 * @param index
	 *            : �����J�n�ʒu
	 * @return �ϊ���v�Z��
	 */
	public String changeKeisanLogic(String strkeisanSiki, int index) {

		// �ԋp������
		String ret = strkeisanSiki;

		// �����J�n�ʒu
		int intStartIndex = index;

		// ���݂̕����ʒu
		int intSerchIndex = index;

		// ���ʊJ�n�ʒu
		int intKakkoStartIndex = index;

		// �ŏI�����ʒu
		int intLastIndex = ret.length();

		// ���ʃJ�E���g
		int intKakkoCount = 0;

		// �u���ʊJ���v�w��
		String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

		// �u���ʕ��v�w��
		String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

		// ����SEQ�ݒ蕶����
		String strSeqMark = JwsConstManager.JWS_COPY_0005;

		// �����I��
		boolean blnEnd = false;

		try {

			// �����J�n�ʒu���ŏI�����܂Ń��[�v
			for (int i = intStartIndex; i < intLastIndex; i++) {

				// ���݂̕����ʒu��ݒ�
				intSerchIndex = i;

				// �������擾
				String str = ret.substring(i, i + 1);

				// �u���ʊJ���v���������ꍇ
				if (str.equals(strKakkoHiraki)) {

					// ���ʊJ�n�ʒu��ݒ�
					if (intKakkoCount == 0) {
						intKakkoStartIndex = i;
					}

					// �A������u���ʊJ���v���������ꍇ�ɁA���ʃJ�E���g+1
					intKakkoCount += 1;

				} else {

					// �u���ʊJ���v�łȂ��ꍇ�Ƀ��[�v�A�E�g
					if (intKakkoCount > 0) {
						i = intLastIndex;
					}
				}
			}

			// �u���ʊJ���v����ȏ㑶�݂����ꍇ
			if (intKakkoCount > 0) {

				// ���݂̕����ʒu���ŏI�����܂Ń��[�v
				for (int i = intSerchIndex + 1; i < intLastIndex; i++) {

					// ���݂̕����ʒu��ݒ�
					intSerchIndex = i;

					// �������擾
					String str = ret.substring(i, i + 1);

					// �u���ʊJ���v���������ꍇ
					if (str.equals(strKakkoHiraki)) {

						// ���ʃJ�E���g+1
						intKakkoCount += 1;

					}
					// �u���ʕ��v���������ꍇ
					else if (str.equals(strKakkoTozi)) {

						// ���ʃJ�E���g-1
						intKakkoCount -= 1;

					}
					// ��L�ȊO�̕���
					else {

						// �������Ȃ�

					}

					// ���ʃJ�E���g��0�̏ꍇ
					if (intKakkoCount == 0) {

						// ���[�v�A�E�g
						i = intLastIndex;

					}
				}
			}
			// �v�Z�����Ɋ��ʂ����݂��Ȃ��ꍇ
			else {

				// �����I��
				blnEnd = true;

			}

			// ���ʃJ�E���g��0�̏ꍇ
			if (intKakkoCount == 0) {

				// �����I���̏ꍇ�i���ʂ�����Ȃ��ꍇ�j
				if (blnEnd) {

					return ret;

				} else {

					// �����J�n�ʒu���猻�݂̕����ʒu�܂ł̕����v�Z�����擾
					String strBubunKeisan = ret.substring(intKakkoStartIndex,
							intSerchIndex + 1);

					// �����z��̎擾
					ArrayList aryRetu = DataCtrl.getInstance()
							.getTrialTblData().SearchShisakuRetuData(0);

					// ����z�񃋁[�v
					for (int i = 0; i < aryRetu.size(); i++) {

						// �����f�[�^�擾
						TrialData TrialData = (TrialData) aryRetu.get(i);

						// ����񖼎擾�i���ʕt���j
						String strRetuName = strKakkoHiraki
								+ TrialData.getStrKeisanSiki() + strKakkoTozi;

						// �����v�Z���ƃT���v��No���������ꍇ
						if (strBubunKeisan.equals(strRetuName)) {

							// ����SEQ�擾
							int intSeq = TrialData.getIntShisakuSeq();

							// ���ʂ��Ǝ���SEQ�ƒu��������
							String ret1 = ret.substring(0, intKakkoStartIndex);
							String ret2 = ret.substring(intSerchIndex + 1,
									intLastIndex);
							ret = ret1 + strSeqMark + intSeq + ret2;

						}

					}

					// �ċA�����ɂ��Č���
					ret = changeKeisanLogic(ret, intStartIndex + 1);

				}
			} else {

				// ���ʂ��Ó��łȂ�
				// System.out.println("���ʂ̊֌W���Ó��łȂ�");

			}

		} catch (Exception e) {

			// e.printStackTrace();
			return ret;

		}

		return ret;
	}

	/**
	 * �ϊ��v�Z����莎��SEQ��z���ʂɒu��
	 * 
	 * @param strkeisanSiki
	 *            : �v�Z��
	 * @param intKoteiCd
	 *            : �H��CD
	 * @param intKoteiSeq
	 *            : �H��SEQ
	 * @return �z���ݒ�ς݌v�Z��
	 */
	public String getKeisanShisakuSeq(String strKeisanSiki, int intKoteiCd,
			int intKoteiSeq) {

		String ret = strKeisanSiki;

		try {

			// ����SEQ�ݒ蕶����
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// �����z��̎擾
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// ����z�񃋁[�v
			for (int i = 0; i < aryRetu.size(); i++) {

				// �����f�[�^�擾
				TrialData TrialData = (TrialData) aryRetu.get(i);

				// ����SEQ�擾
				int intSeq = TrialData.getIntShisakuSeq();

				// �ϊ��p����SEQ����
				String changeSeq = strSeqMark + intSeq;

				// ����SEQ���v�Z�����ɑ��݂���ꍇ
				if (ret.indexOf(changeSeq) >= 0) {

					// ���샊�X�g�z��擾
					ArrayList aryShisakuList = DataCtrl.getInstance()
							.getTrialTblData().getAryShisakuList();

					// ���샊�X�g�z�񃋁[�v
					for (int j = 0; j < aryShisakuList.size(); j++) {

						// ���샊�X�g�f�[�^�擾
						PrototypeListData PrototypeListData = (PrototypeListData) aryShisakuList
								.get(j);

						// �H��CD�擾
						int intPltKoteiCd = PrototypeListData.getIntKoteiCd();

						// �H��SEQ�擾
						int intPltKoteiSeq = PrototypeListData.getIntKoteiSeq();

						// ����SEQ�擾
						int intPltShisakuSeq = PrototypeListData
								.getIntShisakuSeq();

						// �Ώۂ̔z���ł���ꍇ
						if (intPltKoteiCd == intKoteiCd
								&& intPltKoteiSeq == intKoteiSeq
								&& intPltShisakuSeq == intSeq) {

							// �z���ʂ̎擾
							String strRyo = toString(
									PrototypeListData.getDciRyo(), "");

							// �z���ʂ��󔒂̏ꍇ
							if (strRyo.equals("")) {
								ret = "";
							}
							// �z���ʂ����͂���Ă���ꍇ
							else {
								// �v�Z�����̎���SEQ��z���ʂɒu��
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * �ϊ��v�Z����莎��SEQ�����v�d��d�ʂɒu��
	 * 
	 * @param strkeisanSiki
	 *            : �v�Z��
	 * @return �z���ݒ�ς݌v�Z��
	 */
	public String getKeisanShisakuSeqSiagari(String strKeisanSiki) {

		String ret = strKeisanSiki;

		try {

			// ����SEQ�ݒ蕶����
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// �����z��̎擾
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// �ő厎��SEQ�擾
			int maxSeq = 0;
			for (int j = 0; j < aryRetu.size(); j++) {
				// �����f�[�^�擾
				TrialData TrialData = (TrialData) aryRetu.get(j);
				// ����SEQ�擾
				int intSeq = TrialData.getIntShisakuSeq();
				// �ő厎��SEQ�ݒ�
				if (intSeq > maxSeq) {
					maxSeq = intSeq;
				}
			}

			// ����SEQ���傫�������珬�������փ��[�v
			for (int k = maxSeq; k >= 0; k--) {
				// ����z�񃋁[�v
				for (int i = 0; i < aryRetu.size(); i++) {

					// �����f�[�^�擾
					TrialData TrialData = (TrialData) aryRetu.get(i);

					// ����SEQ�擾
					int intSeq = TrialData.getIntShisakuSeq();

					// ���[�vSEQ�Ɠ������ꍇ
					if (intSeq == k) {
						// �ϊ��p����SEQ����
						String changeSeq = strSeqMark + intSeq;

						// ����SEQ���v�Z�����ɑ��݂���ꍇ
						if (ret.indexOf(changeSeq) >= 0) {

							// ���v�d��d�ʂ̎擾
							String strRyo = toString(
									TrialData.getDciShiagari(), "");

							// ���v�d��d�ʂ��󔒂̏ꍇ
							if (strRyo.equals("")) {
								ret = "";
							}
							// ���v�d��d�ʂ����͂���Ă���ꍇ
							else {
								// �v�Z�����̎���SEQ�����v�d��d�ʂɒu��
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}

			// //����z�񃋁[�v
			// for( int i = 0; i < aryRetu.size(); i++ ){
			//
			// //�����f�[�^�擾
			// TrialData TrialData = (TrialData)aryRetu.get(i);
			//
			// //����SEQ�擾
			// int intSeq = TrialData.getIntShisakuSeq();
			//
			// //�ϊ��p����SEQ����
			// String changeSeq = strSeqMark + intSeq;
			//
			// //����SEQ���v�Z�����ɑ��݂���ꍇ
			// if( ret.indexOf(changeSeq) >= 0){
			//
			// //���v�d��d�ʂ̎擾
			// String strRyo = toString(TrialData.getDciShiagari(),"");
			//
			// //���v�d��d�ʂ��󔒂̏ꍇ
			// if(strRyo.equals("")){
			// ret = "";
			// }
			// //���v�d��d�ʂ����͂���Ă���ꍇ
			// else{
			// //�v�Z�����̎���SEQ�����v�d��d�ʂɒu��
			// ret = ret.replaceAll( changeSeq , strRyo );
			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// QP@20505 2012/10/26 No1 Add Start
	/**
	 * �ϊ��v�Z����莎��SEQ���H���d��d�ʂɒu��
	 * 
	 * @param strkeisanSiki
	 *            : �v�Z��
	 * @return �z���ݒ�ς݌v�Z��
	 */
	public String getKeisanShisakuSeqKoteiSiagari(String strKeisanSiki,
			int koteiban) {

		String ret = strKeisanSiki;

		try {

			// ����SEQ�ݒ蕶����
			String strSeqMark = JwsConstManager.JWS_COPY_0005;

			// �����z��̎擾
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData()
					.SearchShisakuRetuData(0);

			// �ő厎��SEQ�擾
			int maxSeq = 0;
			for (int j = 0; j < aryRetu.size(); j++) {
				// �����f�[�^�擾
				TrialData TrialData = (TrialData) aryRetu.get(j);
				// ����SEQ�擾
				int intSeq = TrialData.getIntShisakuSeq();
				// �ő厎��SEQ�ݒ�
				if (intSeq > maxSeq) {
					maxSeq = intSeq;
				}
			}

			// ����SEQ���傫�������珬�������փ��[�v
			for (int k = maxSeq; k >= 0; k--) {
				// ����z�񃋁[�v
				for (int i = 0; i < aryRetu.size(); i++) {

					// �����f�[�^�擾
					TrialData TrialData = (TrialData) aryRetu.get(i);

					// ����SEQ�擾
					int intSeq = TrialData.getIntShisakuSeq();

					// ���[�vSEQ�Ɠ������ꍇ
					if (intSeq == k) {
						// �ϊ��p����SEQ����
						String changeSeq = strSeqMark + intSeq;

						// ����SEQ���v�Z�����ɑ��݂���ꍇ
						if (ret.indexOf(changeSeq) >= 0) {
							// int firstKoteiSeq = 1;
							int intKoteiCode = DataCtrl.getInstance()
									.getTrialTblData()
									.getSerchKoteiCode(intSeq, koteiban);
							// ADD 20130408 start
							// �����s�폜��A�����R�s�[����ƁA�H��SEQ���擾�ł��Ȃ��s��̏C��
							// PrototypeListData pd =
							// DataCtrl.getInstance().getTrialTblData().searchShisakuListData(
							// intSeq,
							// intKoteiCode,
							// firstKoteiSeq);
							PrototypeListData pd = DataCtrl
									.getInstance()
									.getTrialTblData()
									.searchShisakuListData_Copy(intSeq,
											intKoteiCode);
							// ADD 20130408 end
							String strRyo = DataCtrl.getInstance()
									.getTrialTblData()
									.toString((pd.getDciKouteiShiagari()), "");

							// ���v�d��d�ʂ��󔒂̏ꍇ
							if (strRyo.equals("")) {
								ret = "";
							}
							// ���v�d��d�ʂ����͂���Ă���ꍇ
							else {
								// �v�Z�����̎���SEQ�����v�d��d�ʂɒu��
								ret = ret.replaceAll(changeSeq, strRyo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// QP@20505 2012/10/26 No1 Add End

	/**
	 * �@�v�Z���s
	 * 
	 * @param strkeisanSiki
	 *            : �v�Z��
	 * @return �v�Z����
	 */
	public String execKeisan(String strkeisanSiki) {

		String ret = "";

		// �v�Z�����󔒂̏ꍇ
		if (toString(strkeisanSiki, "", " ").equals("")) {

			// �������Ȃ�

		}
		// �v�Z�����󔒂łȂ��ꍇ
		else {
			try {

				// �v�Z�N���X��ݒ�
				Rule rule = ExpRuleFactory.getDefaultRule();

				// �v�Z�������
				Expression exp = rule.parse(strkeisanSiki);

				// �v�Z���{
				double result = exp.evalDouble();

				// ���ʂ�ԋp�ϐ���
				ret = Double.toString(result);

				// �v�Z���ʂ����l���ǂ������m�F
				BigDecimal bigDecimal = new BigDecimal(ret);

			} catch (Exception e) {

				// �ԋp�l�ɋ󔒂�ݒ�
				ret = "";

			}
		}

		return ret;

	}

	/**
	 * �����f�[�^�@�v�Z������ : �����f�[�^�̌v�Z������
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @return ������i�v�Z���j
	 */
	public String SearchShisakuKeisanSiki(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {
				// �����f�[�^�I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �����F����SEQ�Ǝ����f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					ret = trialData.getStrKeisanSiki();
				}
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̌v�Z�����������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;
	}

	/**
	 * �����f�[�^�@�v�Z���X�V
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param strKeisanSiki
	 *            : �v�Z��
	 */
	public void UpdShisakuKeisanSiki(int intShisakuSeq, String strKeisanSiki)
			throws ExceptionBase {

		try {
			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {
				// �����f�[�^�I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �����F����SEQ�Ǝ����f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					trialData.setStrKeisanSiki(strKeisanSiki);
				}
			}
		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̌v�Z�����������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * ���샊�X�g�f�[�^�z���ʌ��� : ���샊�X�g���z���ʂ�����
	 * 
	 * @param intShisakuSeq
	 *            : ����r�d�p
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @throws ExceptionBase
	 */
	public String searchShisakuListRyo(int intShisakuSeq, int intKouteiCd,
			int intKouteiSeq) throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuList.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�E�H��CD�E�H��SEQ����v
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// �z���ʐݒ�
					ret = PrototypeListData.getDciRyo().toString();

				}
			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	/**
	 * �����f�[�^�d��d�ʌ��� : ����񃊃X�g��莎���f�[�^�̎d��d�ʂ�����
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @throws ExceptionBase
	 */
	public String searchShisakuRetuSiagari(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �d��d�ʐݒ�
					ret = trialData.getDciShiagari().toString();

				}
			}

		} catch (Exception e) {

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// add start
	// -------------------------------------------------------------------------------
	// QP@00412_�V�T�N�C�b�N���� No.2
	/**
	 * �T���v��No���ꖼ�̃`�F�b�N : �w�肳�ꂽ����SEQ�̃T���v�����̂Ɠ���̂��̂�����̂����`�F�b�N
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @return ret : ����T���v��No
	 * @throws ExceptionBase
	 */
	public String checkDistSampleNo(int intShisakuSeq) throws ExceptionBase {

		String ret = null;

		try {
			String checkSampleNo = "";

			// ���X�g���������[�v_�T���v��No�擾�p
			Iterator chk_ite = aryShisakuRetu.iterator();
			while (chk_ite.hasNext()) {
				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) chk_ite.next();
				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {
					// �T���v��No�擾
					checkSampleNo = trialData.getStrSampleNo();
					if (checkSampleNo == null) {
						checkSampleNo = "";
					}
					break;
				}
			}

			// ���X�g���������[�v_�T���v��No��r�p
			Iterator dis_ite = aryShisakuRetu.iterator();
			while (dis_ite.hasNext()) {
				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) dis_ite.next();
				// ����SEQ����v���Ȃ�
				if (intShisakuSeq != trialData.getIntShisakuSeq()) {
					// �T���v��No�擾
					String distSampleNo = trialData.getStrSampleNo();
					if (distSampleNo == null) {
						distSampleNo = "";
					}
					// �T���v��No��r�F����T���v��No�����݂���ꍇ
					if (checkSampleNo.equals(distSampleNo)) {
						ret = distSampleNo;
						if (ret.equals("")) {
							ret = "�i�󔒁j";
						}
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * �T���v��No���ꖼ�̃`�F�b�N�i�S���j : �w�肳�ꂽ����SEQ�̃T���v�����̂Ɠ���̂��̂�����̂����`�F�b�N�i�S���j
	 * 
	 * @param ����
	 * @return ret : ����T���v��No
	 * @throws ExceptionBase
	 */
	public String checkDistSampleNo_ALL() throws ExceptionBase {

		String ret = null;

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// ���X�g���������[�v_�T���v��No�擾�p
			while (ite.hasNext()) {
				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();
				// �T���v��No���ꖼ�̃`�F�b�N
				String chk = null;
				chk = this.checkDistSampleNo(trialData.getIntShisakuSeq());
				if (chk == null) {

				} else {
					if (ret == null) {
						ret = "";
						ret = ret + chk;
					} else {
						ret = ret + "\n" + chk;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

		return ret;
	}

	/**
	 * �����f�[�^�T���v��No���� : ����񃊃X�g��莎���f�[�^�̃T���v��No������
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @throws ExceptionBase
	 */
	public String searchShisakuRetuSampleNo(int intShisakuSeq)
			throws ExceptionBase {

		String ret = "";

		try {
			Iterator ite = aryShisakuRetu.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �T���v��No
					ret = trialData.getStrSampleNo();
				}
			}

		} catch (Exception e) {

		} finally {

		}

		return ret;
	}

	// add end
	// -------------------------------------------------------------------------------

	// 2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start
	// -------------------------
	/*************************************************************************************************
	 * 
	 * ����񐅑���d�o��FG�X�V : �w�莎���f�[�^�ɑ΂��Đ�����d�o��FG��ݒ肷��
	 * 
	 * @param intHijuFg
	 *            : ������d�o��FG
	 * @param dciUserId
	 *            : ���[�UID
	 * 
	 *************************************************************************************************/
	public void UpdShisakuRetuSuiHijuFg(int intHijuFg, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// �f�[�^�ύX�`�F�b�N
				chkHenkouData(trialData.getIntHiju_sui_fg(), intHijuFg);
				// �yKPX1500671�zMOD end

				// ������d�o��FLG�ݒ�
				trialData.setIntHiju_sui_fg(intHijuFg);

				// �X�V�ҏ��̐ݒ�
				trialData.setDciKosinId(dciUserId);
				trialData.setStrkosinHi(getSysDate());

			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎�����d�o��FG�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * ����񐅑���d�X�V : �w�莎���f�[�^�ɑ΂��Đ�����d��ݒ肷��
	 * 
	 * @param intShisakuSeq
	 *            : ����SEQ
	 * @param intHiju
	 *            : ������d
	 * @param dciUserId
	 *            : ���[�UID
	 * 
	 *************************************************************************************************/
	public void UpdShisakuRetuSuiHiju(int intShisakuSeq, String strHiju,
			BigDecimal dciUserId) throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			Iterator ite = aryShisakuRetu.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �����I�u�W�F�N�g�擾
				TrialData trialData = (TrialData) ite.next();

				// ����SEQ����v
				if (intShisakuSeq == trialData.getIntShisakuSeq()) {

					// �H���p�^�[���擾
					String ptKotei = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrPt_kotei();
					String ptValue = null;
					// �H���p�^�[����Value1�擾
					if (ptKotei != null && ptKotei.length() > 0) {
						ptValue = DataCtrl.getInstance()
								.getLiteralDataKoteiPtn()
								.selectLiteralVal1(ptKotei);
					}

					String yoryoTani = DataCtrl.getInstance().getTrialTblData()
							.SearchShisakuhinData().getStrTani();
					// �e�ʒP�ʂ���Value1�擾
					String taniValue1 = "";
					if (yoryoTani == null || yoryoTani.length() == 0) {
					} else {
						taniValue1 = DataCtrl.getInstance()
								.getLiteralDataTani()
								.selectLiteralVal1(yoryoTani);
					}

					// 2�t�^�C�v ���� �P�ʁuml�v
					if (JwsConstManager.JWS_KOTEITYPE_2.equals(ptValue)
							&& "1".equals(taniValue1)) {

						// �f�[�^�ύX�`�F�b�N
						chkHenkouData(trialData.getStrHiju_sui(), strHiju);
					}
					// �yKPX1500671�zMOD end

					// ������d�ݒ�
					trialData.setStrHiju_sui(strHiju);

					// �X�V�ҏ��̐ݒ�
					trialData.setDciKosinId(dciUserId);
					trialData.setStrkosinHi(getSysDate());

				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎�����d�X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * ����i�H���p�^�[���X�V : ����i�f�[�^�ɑ΂��čH���p�^�[����ݒ肷��
	 * 
	 * @param strKoteiPtn
	 *            : �H���p�^�[��
	 * @param dciUserId
	 *            : ���[�UID
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void UpdKoteiPtn(String strKoteiPtn, BigDecimal dciUserId)
			throws ExceptionBase {

		try {

			// �yKPX1500671�z�f�[�^�ύX�t���O�̐ݒ�^�C�~���O�ύX�FMOD start
			// �f�[�^�ύX
			// HenkouFg = true;

			// �f�[�^�ύX�`�F�b�N
			chkHenkouData(ptdtShaisakuHin.getStrPt_kotei(), strKoteiPtn);
			// �yKPX1500671�zMOD end

			// ����i�H���p�^�[���X�V
			ptdtShaisakuHin.setStrPt_kotei(strKoteiPtn);

			// �X�V�ҏ��̐ݒ�
			ptdtShaisakuHin.setDciKosinid(dciUserId);
			// UPD 2012.4.30�@�yH24�N�x�Ή��z�X�V���Ŏ���i�̍X�V�r��������s�����ߕs�p�ӂ̐ݒ���폜
			// ptdtShaisakuHin.setStrKosinhi(getSysDate());

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̎���i����i�H���p�^�[���X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

	}

	/*************************************************************************************************
	 * 
	 * ���i��d�̌v�Z����
	 * 
	 * @return ���i��d
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanSeihinHiju(int intShisakuSeq) throws ExceptionBase {

		// �v�Z���� : ���i��d
		String ret = "";

		try {

			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// ���Ɉ˗�����Ă���f�[�^�̌v�Z�͍s��Ȃ�
			if (trialData.getFlg_init() == 1) {
				return trialData.getStrHizyu();
			}

			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return trialData.getStrHizyu();
			}
			// �H���p�^�[�����u�󔒁v�łȂ��ꍇ
			else {

				// �H���p�^�[����Value1�擾
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// �e�ʒP�ʂ���Value1�擾
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();
				String taniValue1 = "";
				if (yoryoTani == null || yoryoTani.length() == 0) {
					return trialData.getStrHizyu();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// �H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return trialData.getStrHizyu();
				}
				// �H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {
					// �e�ʂ��uml�v�̏ꍇ
					if (taniValue1.equals("1")) {
						// �������s
					}
					// �e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
					else {
						return trialData.getStrHizyu();
					}
				}
				// �H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return trialData.getStrHizyu();
				}
			}

			// �z���f�[�^�̎擾
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// �E�ے����t�d�ʁA�h���������v�d�ʁA�h���������v�d�ʁA������d�A������d�A���i��d
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinHiju = 0.0;

			// ���e�����l
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// ���X�g���������[�v
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// �H�������̎擾
				String strZokusei = haigoData.getStrKouteiZokusei();

				// �H�����I�΂�Ă���ꍇ
				if (strZokusei != null) {

					// ���e�����l�̎擾
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----�H�����v�d�ʂ����߂�----------------------

					// �z���f�[�^���l�̎擾

					// �H��CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// �H��SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----���샊�X�g�f�[�^���A�z���ʂ��擾�������s��----------------------

					// �z����
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// ����SEQ�A�H��CD�A�H��SEQ����v�����ꍇ
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// �z���ʂ��擾
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ���[�v�𔲂���
							break;

						}

					}

					// ----���e�����l2�ɂ�镪�򏈗�----------------------
					// 1:�E�ے����t
					if (strLiteralVal2.equals("1")) {

						// �E�ے����t�̍��v�d�ʂ����߂�
						dblSakinGokeiryo += dblRyo;

					}
					// 2:����
					else if (strLiteralVal2.equals("2")) {

						// �����̍��v�d�ʂ����߂�
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:����
					else if (strLiteralVal2.equals("3")) {

						// �����̍��v�d�ʂ����߂�
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------���i��d�����߂�--------------

			// ( �E�ے����t�d�� �{ �h���������v�d�� �{ �h���������v�d�� ) �� ( ( ( �E�ے����t�d�� �{ �h���������v�d�� ) ��
			// ������d ) �{ ( �h���������v�d�� �� ������d ) )
			dblSeihinHiju = (dblSakinGokeiryo + dblSuisoGokeiryo + dblYusoGokeiryo)
					/ (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju));

			// ���������𒲐�
			ret = SetShosuKeta(Double.toString(dblSeihinHiju), 3);

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���i��d�̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * ������1�t�^�C�v�@�[�U�ʂ̌v�Z����
	 * 
	 * @return �[�U��
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanZyutenType1(int intShisakuSeq) throws ExceptionBase {

		// �v�Z���� : �[�U��
		String ret = "";

		try {
			// �����f�[�^�擾
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// ���Ɉ˗�����Ă���f�[�^�̌v�Z�͍s��Ȃ�
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenSui();
			}

			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenSui();
			}
			// �H���p�^�[�����u�󔒁v�łȂ��ꍇ
			else {

				// �H���p�^�[����Value1�擾
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// �e�ʒP�ʂ���Value1�擾
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();
				String taniValue1 = "";
				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenSui();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// �H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					// �������s
				}
				// �H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {
					return costMaterialData.getStrZyutenSui();
				}
				// �H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenSui();
				}
			}

			// �[�U�ʁA���i�e�ʁA���i��d
			double dblZyutenryo = 0.0;
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblSeihinHiju = checkNumericDouble(trialData.getStrHizyu());

			// --------------�[�U�ʂ����߂�--------------

			// ���i�e�ʁ~���i��d
			dblZyutenryo = dblSeihinYoryo * dblSeihinHiju;

			// MOD start 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX
			// ���������𒲐�
			// ret = SetShosuKeta(Double.toString(dblZyutenryo),2);
			ret = SetShosuKeta3(Double.toString(dblZyutenryo), 2);
			// MOD end 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			e.printStackTrace();

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("������1�t�^�C�v�@�[�U�ʂ̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * ������2�t�^�C�v�@�����[�U�ʂ̌v�Z����
	 * 
	 * @return �����[�U��
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanSuisoZyuten(int intShisakuSeq) throws ExceptionBase {

		// �v�Z���� : �����[�U��
		String ret = "";

		try {
			// �����f�[�^�擾
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
			String taniValue1 = "";
			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

			// ���Ɉ˗�����Ă���f�[�^�̌v�Z�͍s��Ȃ�
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenSui();
			}

			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenSui();
			}
			// �H���p�^�[�����u�󔒁v�łȂ��ꍇ
			else {

				// �H���p�^�[����Value1�擾
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// �e�ʒP�ʂ���Value1�擾
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();

				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -Start
				// String taniValue1 = "";
				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -End

				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenSui();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// �H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return costMaterialData.getStrZyutenSui();
				}
				// �H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {

					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
					// �e�ʂ��uml�v�ug�v�̏ꍇ
					// if(taniValue1.equals("1")){
					if (taniValue1.equals("1") || taniValue1.equals("2")) {
						// �������s
					}
					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

					// �e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
					else {
						return costMaterialData.getStrZyutenSui();
					}
				}
				// �H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenSui();
				}
			}

			// �z���f�[�^�̎擾
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// �E�ے����t�d�ʁA�h���������v�d�ʁA�h���������v�d�ʁA������d�A������d�A���i�e�ʁA�����[�U��
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblSuisoZyuten = 0.0;

			// ���e�����l
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// ���X�g���������[�v
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// �H�������̎擾
				String strZokusei = haigoData.getStrKouteiZokusei();

				// �H�����I�΂�Ă���ꍇ
				if (strZokusei != null) {

					// ���e�����l�̎擾
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----�H�����v�d�ʂ����߂�----------------------

					// �z���f�[�^���l�̎擾

					// �H��CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// �H��SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----���샊�X�g�f�[�^���A�z���ʂ��擾�������s��----------------------

					// �z����
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// ����SEQ�A�H��CD�A�H��SEQ����v�����ꍇ
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// �z���ʂ��擾
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ���[�v�𔲂���
							break;

						}

					}

					// ----���e�����l2�ɂ�镪�򏈗�----------------------
					// 1:�E�ے����t
					if (strLiteralVal2.equals("1")) {

						// �E�ے����t�̍��v�d�ʂ����߂�
						dblSakinGokeiryo += dblRyo;

					}
					// 2:����
					else if (strLiteralVal2.equals("2")) {

						// �����̍��v�d�ʂ����߂�
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:����
					else if (strLiteralVal2.equals("3")) {

						// �����̍��v�d�ʂ����߂�
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------�����[�U�ʂ����߂�--------------
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
			// �e�ʂ��uml�v�̏ꍇ
			if (taniValue1.equals("1")) {

				// ( �E�ے����t�d�� �{ �h���������v�d�� ) �~ ( ���i�e�� �� ( ( ( �E�ے����t�d�� �{ �h���������v�d�� )
				// �� ������d ) �{ ( �h���������v�d�� �� ������d ) ) )
				dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo)
						* (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));

			}
			// �e�ʂ��ug�v�̏ꍇ
			else if (taniValue1.equals("2")) {

				// ( �E�ے����t�d�� �{ �h���������v�d�� ) �~ ( ���i�e�� �� ( ( �E�ے����t�d�� �{ �h���������v�d�� ) �{
				// ( �h���������v�d�� ) )
				dblSuisoZyuten = (dblSakinGokeiryo + dblSuisoGokeiryo)
						* (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));

			}
			// ����ȊO�̏ꍇ
			else {

				throw new Exception();

			}
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

			// MOD start 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX
			// ���������𒲐�
			// ret = SetShosuKeta(Double.toString(dblSuisoZyuten),2);
			ret = SetShosuKeta3(Double.toString(dblSuisoZyuten), 2);
			// MOD end 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("������2�t�^�C�v�@�����[�U�ʂ̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	/*************************************************************************************************
	 * 
	 * ������2�t�^�C�v�@�����[�U�ʂ̌v�Z����
	 * 
	 * @return �����[�U��
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public String KeisanYusoZyuten(int intShisakuSeq) throws ExceptionBase {

		// �v�Z���� : �����[�U��
		String ret = "";

		try {
			// �����f�[�^�擾
			CostMaterialData costMaterialData = (CostMaterialData) this
					.SearchGenkaGenryoData(intShisakuSeq).get(0);

			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
			String taniValue1 = "";
			// QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

			// ���Ɉ˗�����Ă���f�[�^�̌v�Z�͍s��Ȃ�
			if (trialData.getFlg_init() == 1) {
				return costMaterialData.getStrZyutenYu();
			}

			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return costMaterialData.getStrZyutenYu();
			}
			// �H���p�^�[�����u�󔒁v�łȂ��ꍇ
			else {

				// �H���p�^�[����Value1�擾
				String ptValue = DataCtrl.getInstance()
						.getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

				// �e�ʒP�ʂ���Value1�擾
				String yoryoTani = DataCtrl.getInstance().getTrialTblData()
						.SearchShisakuhinData().getStrTani();

				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -Start
				// String taniValue1 = "";
				// QP@20505 No.2 2012/09/05 TT H.SHIMA DEL -End

				if (yoryoTani == null || yoryoTani.length() == 0) {
					return costMaterialData.getStrZyutenYu();
				} else {
					taniValue1 = DataCtrl.getInstance().getLiteralDataTani()
							.selectLiteralVal1(yoryoTani);
				}

				// �H���p�^�[�����u�������P�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)) {
					return costMaterialData.getStrZyutenYu();
				}
				// �H���p�^�[�����u�������Q�t�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)) {

					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
					// �e�ʂ��uml�v�̏ꍇ
					// if(taniValue1.equals("1")){
					if (taniValue1.equals("1") || taniValue1.equals("2")) {
						// �������s
					}
					// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

					// �e�ʂ��u�󔒁v�̏ꍇ�iml�ȊO�̏ꍇ�j
					else {
						return costMaterialData.getStrZyutenYu();
					}
				}
				// �H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ-------------------------------------------------------------
				else if (ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
					return costMaterialData.getStrZyutenYu();
				}
			}

			// �z���f�[�^�̎擾
			ArrayList aryHaigoData = this.SearchHaigoData(0);

			// �E�ے����t�d�ʁA�h���������v�d�ʁA�h���������v�d�ʁA������d�A������d�A���i�e�ʁA�����[�U��
			double dblSakinGokeiryo = 0.0;
			double dblSuisoGokeiryo = 0.0;
			double dblYusoGokeiryo = 0.0;
			double dblSuisoHiju = checkNumericDouble(trialData.getStrHiju_sui());
			double dblYusoHiju = checkNumericDouble(DataCtrl.getInstance()
					.getLiteralDataYusoHiju().getAryBiko().get(0));
			double dblSeihinYoryo = checkNumericDouble(SearchShisakuhinData()
					.getStrYoryo());
			double dblYusoZyuten = 0.0;

			// ���e�����l
			String strLiteralVal1 = "";
			String strLiteralVal2 = "";

			// ���X�g���������[�v
			for (int i = 0; i < aryHaigoData.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigoData.get(i);

				// �H�������̎擾
				String strZokusei = haigoData.getStrKouteiZokusei();

				// �H�����I�΂�Ă���ꍇ
				if (strZokusei != null) {

					// ���e�����l�̎擾
					strLiteralVal1 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal1(strZokusei);
					strLiteralVal2 = DataCtrl.getInstance()
							.getLiteralDataZokusei()
							.selectLiteralVal2(strZokusei);

					// ----�H�����v�d�ʂ����߂�----------------------

					// �z���f�[�^���l�̎擾

					// �H��CD
					int intKoteiCd = checkNullInt(""
							+ haigoData.getIntKoteiCd());
					// �H��SEQ
					int intKoteiSeq = checkNullInt(""
							+ haigoData.getIntKoteiSeq());

					// ----���샊�X�g�f�[�^���A�z���ʂ��擾�������s��----------------------

					// �z����
					double dblRyo = 0.0;

					Iterator ite = this.getAryShisakuList().iterator();

					// ���X�g���������[�v
					while (ite.hasNext()) {
						// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
						PrototypeListData shisakuRetu = (PrototypeListData) ite
								.next();

						// ����SEQ�A�H��CD�A�H��SEQ����v�����ꍇ
						if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()
								&& intKoteiCd == shisakuRetu.getIntKoteiCd()
								&& intKoteiSeq == shisakuRetu.getIntKoteiSeq()) {

							// �z���ʂ��擾
							dblRyo = checkNumericDouble(shisakuRetu.getDciRyo());

							// ���[�v�𔲂���
							break;

						}

					}

					// ----���e�����l2�ɂ�镪�򏈗�----------------------
					// 1:�E�ے����t
					if (strLiteralVal2.equals("1")) {

						// �E�ے����t�̍��v�d�ʂ����߂�
						dblSakinGokeiryo += dblRyo;

					}
					// 2:����
					else if (strLiteralVal2.equals("2")) {

						// �����̍��v�d�ʂ����߂�
						dblSuisoGokeiryo += dblRyo;

					}
					// 3:����
					else if (strLiteralVal2.equals("3")) {

						// �����̍��v�d�ʂ����߂�
						dblYusoGokeiryo += dblRyo;

					}

				}
			}

			// --------------�����[�U�ʂ����߂�--------------
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
			// �e�ʂ��uml�v�̏ꍇ
			if (taniValue1.equals("1")) {

				// �h���������v�d�� �~ ( ���i�e�� �� ( ( ( �E�ے����t�d�� �{ �h���������v�d�� ) �� ������d ) �{ (
				// �h���������v�d�� �� ������d ) ) )
				dblYusoZyuten = dblYusoGokeiryo
						* (dblSeihinYoryo / (((dblSakinGokeiryo + dblSuisoGokeiryo) / dblSuisoHiju) + (dblYusoGokeiryo / dblYusoHiju)));

			}
			// �e�ʂ��ug�v�̏ꍇ
			else if (taniValue1.equals("2")) {

				// �h���������v�d�� �~ ( ���i�e�� �� ( ( �E�ے����t�d�� �{ �h���������v�d�� ) �{ ( �h���������v�d�� ) )
				dblYusoZyuten = dblYusoGokeiryo
						* (dblSeihinYoryo / ((dblSakinGokeiryo + dblSuisoGokeiryo) + dblYusoGokeiryo));

			}
			// ����ȊO�̏ꍇ
			else {

				throw new Exception();

			}
			// QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End

			// MOD start 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX
			// ���������𒲐�
			// ret = SetShosuKeta(Double.toString(dblYusoZyuten),2);
			ret = SetShosuKeta3(Double.toString(dblYusoZyuten), 2);
			// MOD end 20121121 QP@20505 �؂�グ���l�̌ܓ��ɕύX

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("������2�t�^�C�v�@�����[�U�ʂ̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	// 2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End
	// -------------------------

	/*************************************************************************************************
	 * 
	 * �z���f�[�^�z��@���������ёւ�
	 * 
	 * @return �Ȃ�
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryHaigo() throws ExceptionBase {
		try {
			ArrayList aryHaigoCopy = new ArrayList();

			// �ő匴�����擾
			int m_genryo = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigou.get(i);

				// �������ݒ�
				if (m_genryo < haigoData.getIntGenryoNo()) {
					m_genryo = haigoData.getIntGenryoNo();
				}
			}

			// �z����ёւ�
			for (int i = 0; i <= m_genryo; i++) {

				// �z���f�[�^�z�񃋁[�v
				for (int j = 0; j < aryHaigou.size(); j++) {

					// �z���f�[�^�̎擾
					MixedData haigoData = (MixedData) aryHaigou.get(j);

					// �z���f�[�^�ǉ�
					if (i == haigoData.getIntGenryoNo()) {
						aryHaigoCopy.add(haigoData);
					}
				}
			}

			// �R�s�[�f�[�^�ݒ�
			aryHaigou = null;
			aryHaigou = aryHaigoCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�z���f�[�^�z��@���������ёւ����������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*************************************************************************************************
	 * 
	 * ����f�[�^�z��@���쏇���ёւ�
	 * 
	 * @return �Ȃ�
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryShisaku() throws ExceptionBase {
		try {
			ArrayList aryShisakuCopy = new ArrayList();

			// �ő厎�쏇�擾
			int m_shisaku = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				// ����f�[�^�̎擾
				TrialData shisakuData = (TrialData) aryShisakuRetu.get(i);

				// ���쏇�ݒ�
				if (m_shisaku < shisakuData.getIntHyojiNo()) {
					m_shisaku = shisakuData.getIntHyojiNo();
				}
			}

			// �z����ёւ�
			for (int i = 0; i <= m_shisaku; i++) {

				// ����f�[�^�z�񃋁[�v
				for (int j = 0; j < aryShisakuRetu.size(); j++) {

					// ����f�[�^�̎擾
					TrialData shisakuData = (TrialData) aryShisakuRetu.get(j);

					// ����f�[�^�ǉ�
					if (i == shisakuData.getIntHyojiNo()) {
						aryShisakuCopy.add(shisakuData);
					}
				}
			}

			// �R�s�[�f�[�^�ݒ�
			aryShisakuRetu = null;
			aryShisakuRetu = aryShisakuCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����f�[�^�z��@���쏇���ёւ����������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*************************************************************************************************
	 * 
	 * ���샊�X�g�f�[�^�z��@���샊�X�g�����ёւ�
	 * 
	 * @return �Ȃ�
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public void sortAryShisakuList() throws ExceptionBase {
		try {
			ArrayList aryShisakuListCopy = new ArrayList();

			// �ő匴�����擾
			int m_genryo = 0;
			for (int i = 0; i < aryHaigou.size(); i++) {

				// �z���f�[�^�̎擾
				MixedData haigoData = (MixedData) aryHaigou.get(i);

				// �������ݒ�
				if (m_genryo < haigoData.getIntGenryoNo()) {
					m_genryo = haigoData.getIntGenryoNo();
				}
			}

			// �ő厎�쏇�擾
			int m_shisaku = 0;
			for (int i = 0; i < aryShisakuRetu.size(); i++) {

				// ����f�[�^�̎擾
				TrialData shisakuData = (TrialData) aryShisakuRetu.get(i);

				// ���쏇�ݒ�
				if (m_shisaku < shisakuData.getIntHyojiNo()) {
					m_shisaku = shisakuData.getIntHyojiNo();
				}
			}

			// �z����ёւ�
			for (int i = 0; i <= m_shisaku; i++) {

				// ����f�[�^�z�񃋁[�v
				for (int j = 0; j < aryShisakuRetu.size(); j++) {

					// ����f�[�^�̎擾
					TrialData shisakuData = (TrialData) aryShisakuRetu.get(j);

					// ����f�[�^�ǉ�
					if (i == shisakuData.getIntHyojiNo()) {

						// ����SEQ�擾
						int shisaku_seq = shisakuData.getIntShisakuSeq();

						// �z����ёւ�
						for (int k = 0; k <= m_genryo; k++) {

							// �z���f�[�^�z�񃋁[�v
							for (int l = 0; l < aryHaigou.size(); l++) {

								// �z���f�[�^�̎擾
								MixedData haigoData = (MixedData) aryHaigou
										.get(l);

								// �z���f�[�^�ǉ�
								if (k == haigoData.getIntGenryoNo()) {

									// �H��CD�擾
									int kotei_cd = haigoData.getIntKoteiCd();

									// �H��SEQ�擾
									int kotei_seq = haigoData.getIntKoteiSeq();

									// ���샊�X�g�擾
									PrototypeListData PrototypeListData = this
											.searchShisakuListData(shisaku_seq,
													kotei_cd, kotei_seq);
									aryShisakuListCopy.add(PrototypeListData);
								}
							}
						}
					}
				}
			}

			// �R�s�[�f�[�^�ݒ�
			aryShisakuList = null;
			aryShisakuList = aryShisakuListCopy;

		} catch (Exception e) {

			e.printStackTrace();

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����f�[�^�z��@���쏇���ёւ����������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/**
	 * ���샊�X�g�f�[�^�z���ʌ��� : ���샊�X�g���z���ʂ������i�S�R�s�[�����Ŏg�p�j
	 * 
	 * @param intShisakuSeq
	 *            : ����r�d�p
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @throws ExceptionBase
	 */
	public PrototypeListData searchShisakuListData(int intShisakuSeq,
			int intKouteiCd, int intKouteiSeq) throws ExceptionBase {

		PrototypeListData ret = null;

		try {
			Iterator ite = aryShisakuList.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {

				// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�E�H��CD�E�H��SEQ����v
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()
						&& intKouteiSeq == PrototypeListData.getIntKoteiSeq()) {

					// �z���ʐݒ�
					ret = PrototypeListData;

				}
			}

		} catch (Exception e) {

		} finally {

		}
		return ret;
	}

	// ADD 20130408 start �����s�폜��A�����R�s�[����ƁA�H��SEQ���擾�ł��Ȃ��s��̏C��
	/**
	 * ���샊�X�g�f�[�^�z���ʌ����i�����R�s�[�Łj : ���샊�X�g���z���ʂ������i�����R�s�[�����Ŏg�p����ꍇ�ɁA�H��SEQ����肵�Ȃ��j
	 * 
	 * @param intShisakuSeq
	 *            : ����r�d�p
	 * @param intKouteiCd
	 *            : �H���b�c
	 * @param intKouteiSeq
	 *            : �H���r�d�p
	 * @throws ExceptionBase
	 */
	public PrototypeListData searchShisakuListData_Copy(int intShisakuSeq,
			int intKouteiCd) throws ExceptionBase {
		PrototypeListData ret = null;
		try {
			Iterator ite = aryShisakuList.iterator();
			// ���X�g���������[�v
			while (ite.hasNext()) {
				// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
				PrototypeListData PrototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ�E�H��CD����v
				if (intShisakuSeq == PrototypeListData.getIntShisakuSeq()
						&& intKouteiCd == PrototypeListData.getIntKoteiCd()) {
					// �z���ʐݒ�
					ret = PrototypeListData;
				}
			}
		} catch (Exception e) {
		} finally {
		}
		return ret;
	}

	// ADD 20130408 end

	/**
	 * 0055.�z���������̎擾 : �w�茴���f�[�^�̌������̂��擾����
	 * 
	 * @param intKouteiCd
	 *            : �H��CD
	 * @param intKouteiSeq
	 *            : �H��SEQ
	 * @return ������
	 */
	public String SearchHaigoGenryoMei(int intKouteiCd, int intKouteiSeq)
			throws ExceptionBase {

		String ret = "";

		try {

			Iterator ite = aryHaigou.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {

				// �z���f�[�^�I�u�W�F�N�g�擾
				MixedData mixData = (MixedData) ite.next();

				// �H��CD�E�H��SEQ����v
				if (intKouteiCd == mixData.getIntKoteiCd()
						&& intKouteiSeq == mixData.getIntKoteiSeq()) {

					// �������̐ݒ�
					ret = mixData.getStrGenryoNm();
				}
			}

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("����e�[�u���f�[�^�ێ��̔z���������̍X�V�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;

	}

	/*************************************************************************************************
	 * 
	 * ���샊�X�g�f�[�^ �H���R�[�h�擾
	 * 
	 * @param intShisakuSeq
	 *            �F����SEQ
	 * @param intKoteiRow
	 *            �F�H���ԍ�
	 * @return �H���R�[�h
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public int getSerchKoteiCode(int intShisakuSeq, int intKoteiRow)
			throws ExceptionBase {

		int ret = 0;

		try {
			ArrayList koteiCodeList = new ArrayList();
			ArrayList uniqueKoteiCodeList = new ArrayList();

			Iterator ite = aryShisakuList.iterator();

			// ���X�g���������[�v
			while (ite.hasNext()) {
				PrototypeListData prototypeListData = (PrototypeListData) ite
						.next();

				// ����SEQ����v
				if (intShisakuSeq == prototypeListData.getIntShisakuSeq()) {

					// �H���R�[�h�ݒ�
					koteiCodeList.add(new Integer(prototypeListData
							.getIntKoteiCd()));
				}
			}

			// �H���R�[�h����ӂ�
			Set set = new LinkedHashSet();
			set.addAll(koteiCodeList);
			uniqueKoteiCodeList.addAll(set);

			// �H���ԍ��Ɉ�v����H���R�[�h��ret�ɐݒ�
			ret = Integer.parseInt(String.valueOf(uniqueKoteiCodeList
					.get(intKoteiRow - 1)));

		} catch (Exception e) {

			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�z���\�H���R�[�h�̎擾�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}

		return ret;
	}

	/*************************************************************************************************
	 * 
	 * �����|�_�Z�x�̌v�Z����
	 * 
	 * @return �����|�_�Z�x
	 * @throws ExceptionBase
	 * 
	 *************************************************************************************************/
	public BigDecimal KeisanJikkoSakusanNodo(int _intShisakuSeq)
			throws ExceptionBase {

		// �v�Z���� : �����|�_�Z�x
		BigDecimal ret = new BigDecimal("0.00");
		// �����(Start:1)
		int intShisakuSeq = _intShisakuSeq;

		try {
			// �����f�[�^�̎擾
			TrialData trialData = (TrialData) this.SearchShisakuRetuData(
					intShisakuSeq).get(0);

			// ���Ɉ˗�����Ă���f�[�^�̌v�Z�͍s��Ȃ�
			if (trialData.getFlg_init() == 1) {
				return ret;
			}

			// �H���p�^�[�����u�󔒁v�̏ꍇ
			String ptKotei = SearchShisakuhinData().getStrPt_kotei();
			if (ptKotei == null || ptKotei.length() == 0) {
				return ret;
			} else
			// �H���p�^�[�����u���̑��E���H�^�C�v�v�̏ꍇ
			if (ptKotei.equals(JwsConstManager.JWS_KOTEITYPE_3)) {
				return ret;
			}

			// �z���f�[�^�擾
			ArrayList HaigoData = this.SearchHaigoData(0);

			// ------------------------ �v�Z�K�v����
			// -----------------------------------
			// �@���v�d��(g)
			BigDecimal goleiZyuryo = new BigDecimal("0.00");
			// �A���ܗL���v��
			BigDecimal goleiGanyu = new BigDecimal("0.00");
			// �B�|�_���v��
			BigDecimal goleiSakusan = new BigDecimal("0.00");
			// �E�l�r�f���v��
			BigDecimal goleiMsg = new BigDecimal("0.00");

			Iterator ite = this.getAryShisakuList().iterator();

			int i = 0;
			// ���X�g���������[�v
			while (ite.hasNext()) {
				// ���샊�X�g�f�[�^�I�u�W�F�N�g�擾
				PrototypeListData shisakuRetu = (PrototypeListData) ite.next();

				// ����SEQ�A�H��CD�A�H��SEQ����v�����ꍇ
				if (intShisakuSeq == shisakuRetu.getIntShisakuSeq()) {

					// �z���ʂ��擾
					BigDecimal ryoVal = shisakuRetu.getDciRyo();
					// �z����
					BigDecimal bdRyo = new BigDecimal("0.000");
					if (ryoVal != null) {
						bdRyo = new BigDecimal(ryoVal.toString());
					}

					// �@���v��
					if (shisakuRetu.getDciRyo() != null) {
						goleiZyuryo = goleiZyuryo.add(shisakuRetu.getDciRyo());
					}
					// �z���f�[�^�̎擾
					MixedData haigoData = (MixedData) HaigoData.get(i);

					// �A���ܗL���v�ʉ��Z
					if (haigoData.getDciGanyuritu() != null) {
						goleiGanyu = goleiGanyu.add(bdRyo.multiply(haigoData
								.getDciGanyuritu().multiply(
										new BigDecimal("0.01"))));
					}
					// �B�|�_���v�ʉ��Z
					if (haigoData.getDciSakusan() != null) {
						goleiSakusan = goleiSakusan.add(bdRyo
								.multiply(haigoData.getDciSakusan()));
					}
					// �E�l�r�f���v�ʉ��Z
					if (haigoData.getDciMsg() != null) {
						goleiMsg = goleiMsg.add(bdRyo.multiply(haigoData
								.getDciMsg()));
					}

					// �J�E���g�A�b�v
					i++;
				}
			}

			// --------------------------
			// �������|�_�v�Z����------------------------------
			// �B�|�_���v��/�i�@���v�ʁ[�A���ܗL���v�ʁj
			BigDecimal sui_sakusan = new BigDecimal("0.00");
			if (goleiSakusan.intValue() > 0
					&& (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0) {
				sui_sakusan = goleiSakusan.divide(
						(goleiZyuryo.subtract(goleiGanyu)), 2,
						BigDecimal.ROUND_HALF_UP);
			}

			// �E�l�r�f���v��/�i�@���v�ʁ|�A���ܗL���v�ʁj
			BigDecimal sui_msg = new BigDecimal("0.00");
			if (goleiMsg.intValue() > 0
					&& (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0) {
				sui_msg = goleiMsg.divide((goleiZyuryo.subtract(goleiGanyu)),
						2, BigDecimal.ROUND_HALF_UP);
			}

			// �������|�_�|�������l�r�f�~(0.5791�~���g�|1.9104)/ 187.13�~60
			double dblJsnKoteiValue1 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(0));
			double dblJsnKoteiValue2 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(1));
			double dblJsnKoteiValue3 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(2));
			double dblJsnKoteiValue4 = checkNumericDouble(DataCtrl
					.getInstance().getLiteralDataJikkoSakusanNodo()
					.getAryBiko().get(3));
			String strPh = "";
			BigDecimal ph = new BigDecimal("0.00");
			BigDecimal dci_sui_sakusan = new BigDecimal(sui_sakusan.toString());
			BigDecimal kotei1 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue1)); // 0.5791
			BigDecimal kotei2 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue2)); // 1.9104
			BigDecimal kotei3 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue3)); // 187.13
			BigDecimal kotei4 = new BigDecimal(
					String.valueOf(dblJsnKoteiValue4)); // 60
			ArrayList aryTrialData = aryShisakuRetu;
			int cntList = aryTrialData.size();
			TrialData TrialData = new TrialData();
			for (int k = 0; k < cntList; k++) {
				TrialData = ((TrialData) aryTrialData.get(k)); // �����f�[�^
				if (TrialData.getIntShisakuSeq() == intShisakuSeq) {
					strPh = TrialData.getStrPh();
				}
			}
			if (strPh == null) {
			} else {
				if (strPh.length() > 0) {
					// 2013/04/01 MOD Start
					// ph = new BigDecimal(strPh);
					ph = new BigDecimal(strPh.replaceAll(" ", "").replaceAll(
							"�@", ""));
					// 2013/04/01 MOD End
				}
			}
			// �v�Z
			BigDecimal jsn = new BigDecimal("0.00");
			jsn = ph.multiply(kotei1); // 0.5791�~ph
			jsn = jsn.subtract(kotei2); // jsn�|1.9104
			if (sui_msg.doubleValue() > 0 && jsn.doubleValue() > 0) {
				jsn = jsn.multiply(sui_msg); // jsn�~������MSG
				jsn = jsn.divide(kotei3, 4, BigDecimal.ROUND_HALF_UP); // jsn��187.13
				jsn = jsn.multiply(kotei4); // jsn�~60
				jsn = dci_sui_sakusan.subtract(jsn); // �������|�_�|jsn
				if (jsn.doubleValue() > 0) {
					jsn = jsn.setScale(2, BigDecimal.ROUND_HALF_UP);
				} else {
					// �Ō�̌��Z�Ń}�C�i�X�l�ɂȂ�ꍇ
					jsn = new BigDecimal("0.00");
				}
			} else {
				// ����Z����O��0�l���������ꍇ
				jsn = new BigDecimal("0.00");
			}

			ret = jsn;

		} catch (ExceptionBase eb) {
			throw eb;

		} catch (Exception e) {
			// �G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����|�_�Z�x�̌v�Z���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
		return ret;

	}

	// �f�[�^�ύX�`�F�b�N�FADD start
	/*******************************************************************************************
	 * 
	 * �f�[�^�ύX�`�F�b�N �F�f�[�^���ύX����Ă��鎞�A�ύX�t���O��true �ɂ���
	 * 
	 * @param getData
	 *            �@�F�@�ύX�O�f�[�^
	 * @param setData
	 *            �@�F�@�ύX��f�[�^
	 * @author TT kitazawa
	 * 
	 ******************************************************************************************/
	private void chkHenkouData(int getData, int setData) {

		if (getData != setData) {
			// �f�[�^�ύX
			HenkouFg = true;
		}
	}

	private void chkHenkouData(String getData, String setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// �f�[�^�ύX
				HenkouFg = true;
			}

		} else if (!getData.equals(setData)) {
			// �f�[�^�ύX
			HenkouFg = true;
		}
	}

	private void chkHenkouData(BigDecimal getData, BigDecimal setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// �f�[�^�ύX
				HenkouFg = true;
			}

		} else {

			// �󕶎��`�F�b�N
			if (setData == null) {
				// �f�[�^�ύX
				HenkouFg = true;

			} else {
				if (getData.compareTo(setData) != 0) {
					// �f�[�^�ύX
					HenkouFg = true;
				}
			}
		}
	}

	// String�f�[�^�𐔒l��r
	private void chkHenkouDecData(String getData, String setData) {

		if (getData == null) {
			if (setData == null) {
				//
			} else {
				// �f�[�^�ύX
				HenkouFg = true;
			}

		} else {
			// �󕶎��`�F�b�N
			BigDecimal getDecData = checkNullDecimal(getData);
			BigDecimal setDecData = checkNullDecimal(setData);

			// ���l������ȊO�̎��Anull���Ԃ��
			if (getDecData == null || setDecData == null) {
				// �f�[�^�ύX
				HenkouFg = true;

			} else {
				if (getDecData.compareTo(setDecData) != 0) {
					// �f�[�^�ύX
					HenkouFg = true;
				}
			}
		}
	}

	// �f�[�^�ύX�`�F�b�N�FADD end

}