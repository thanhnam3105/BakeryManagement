package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���ރR�[�h����DB���� �@�\ID�FFGEN3330�@
 *
 * @author TT.Shima
 * @since 2014/09/18
 */
public class FGEN3330_Logic extends LogicBase {

	/**
	 * ���ރf�[�^�����R���X�g���N�^ : �C���X�^���X����
	 */
	public FGEN3330_Logic() {
		super();
	}

	/**
	 * ���ރf�[�^���� : ���ރf�[�^����������B
	 *
	 * @param reqData
	 *            : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData
	 *            : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// SQL�쐬�iWhere��擾�j
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL�쐬�i���ރf�[�^�擾�j
			strSql = createShizaiCodeSQL(reqData, strSql, strSqlWhere);

			// DB�C���X�^���X����
			createSearchDB();

			if (strSqlWhere != null) {
				// �������s�i���ރf�[�^�擾�j
				lstRecset = searchDB.dbSearch(strSql.toString());
				// �������ʂ��Ȃ��ꍇ
				if (lstRecset.size() == 0) {
					em.ThrowException(ExceptionKind.�x��Exception, "W000401",
							strSql.toString(), "", "");
				}
			}

			// �@�\ID�̐ݒ�
			resKind.setID(reqData.getID());

			// �e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			// ���X�|���X�f�[�^�̌`��
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "���ރR�[�h����DB�����Ɏ��s���܂����B");
		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			// �ϐ��̍폜
			strSqlWhere = null;
			strSql = null;
		}
		return resKind;
	}

	/**
	 * ���ޏ��擾SQL�쐬 : ���ޏ����擾����SQL���쐬�B
	 *
	 * @param strSql
	 *            : ��������SQL
	 * @param arrCondition
	 *            �F������������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createShizaiCodeSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strAllSql = new StringBuffer();

		try {
			// �ő�s���̎擾
			int intListRowMax = toInteger(ConstManager.getConstValue(
					ConstManager.Category.�ݒ�, "SHIZAI_CODE_LIST_ROW_MAX"));
			String strListRowMax = toString(intListRowMax + 1);

			// SQL���̍쐬
			strAllSql.append(" SELECT ");
			strAllSql.append("   result.row_no, "); // �s�ԍ�
			strAllSql.append("   result.cd_shain, "); // �Ј��R�[�h
			strAllSql.append("   result.nen, "); // �N
			strAllSql.append("   result.no_oi, "); // �ǔ�
			strAllSql.append("   result.seq_shizai, "); // �s��
			strAllSql.append("   result.no_eda, "); // �}��
			strAllSql.append("   result.cd_shizai_new, "); // ���ރR�[�h
			strAllSql.append("   result.nm_shizai_new, "); // ���ޖ�
			strAllSql.append("   result.cd_shizai, "); // �����ރR�[�h
			strAllSql.append("   result.cd_shohin, "); // ���i�i���i�j�R�[�h
			strAllSql.append("   result.nm_shohin, "); // ���i�i���i�j��
			strAllSql.append("   result.name_nisugata, "); // �׎p
			strAllSql.append("   result.nm_taisyo, "); // �Ώێ��ޖ�
			strAllSql.append("   result.cd_taisyo, "); // �Ώێ��ރR�[�h
			strAllSql.append("   result.nm_hattyu, "); // ������
			strAllSql.append("   result.cd_hattyu, "); // ������R�[�h
			strAllSql.append("   result.flg_tehai_status, "); // ��z�X�e�[�^�X
			strAllSql.append("   result.toroku , "); // �o�^��(�\���p)

			strAllSql.append("   result.naiyo ,"); // ���e
			strAllSql.append("   result.nounyusaki ,"); // �[����
			strAllSql.append("   result.dt_han_payday ,"); // �ő�x����
			strAllSql.append("   result.han_pay ,"); // �ő�
			strAllSql.append("   result.nm_file_aoyaki ,"); // �ăt�@�C����
			strAllSql.append("   result.file_path_aoyaki ,"); // �ăt�@�C���p�X
			strAllSql.append("   result.dt_hattyu ,"); // ������
			strAllSql.append("   result.sekkei1 ,"); // �݌v�@
			strAllSql.append("   result.sekkei2 ,"); // �݌v�A
			strAllSql.append("   result.sekkei3 ,"); // �݌v�B
			strAllSql.append("   result.zaishitsu ,"); // �ގ�
			strAllSql.append("   result.biko_tehai ,"); // ���l
			strAllSql.append("   result.printcolor ,"); // ����F
			strAllSql.append("   result.no_color ,"); // �F�ԍ�
			strAllSql.append("   result.henkounaiyoushosai ,"); // �ύX���e�ڍ�
			strAllSql.append("   result.nouki ,"); // �[��
			strAllSql.append("   result.suryo ,"); // ����
			strAllSql.append("   result.nm_tanto , "); // �S����
			strAllSql.append("   result.nounyu_day "); // �[����

			strAllSql.append(" FROM ");
			strAllSql.append("   ( ");
			strAllSql.append("   SELECT ");
			strAllSql.append("     ROW_NUMBER() OVER ( ");
			strAllSql.append("       ORDER BY cast(T401.cd_shizai_new AS int),");
			strAllSql.append("       T401.dt_koshin) as row_no, "); // �s�ԍ�
			strAllSql.append("     T401.cd_shain, "); // �Ј��R�[�h
			strAllSql.append("     T401.nen, "); // �N
			strAllSql.append("     T401.no_oi, "); // �ǔ�
			strAllSql.append("     T401.seq_shizai, "); // �s��
			strAllSql.append("     T401.no_eda, "); // �}��
			strAllSql.append("     T401.cd_shizai_new, "); // ���ރR�[�h
			strAllSql.append("     T341.nm_shizai_new, "); // ���ޖ�
			strAllSql.append("     T401.cd_shizai, "); // �����ރR�[�h
			strAllSql.append("     T401.cd_shohin, "); // ���i�i���i�j�R�[�h
			strAllSql.append("     T401.nm_shohin, "); // ���i�i���i�j��
			strAllSql.append("     M991.name_nisugata, "); // �׎p
			strAllSql.append("     M302A.nm_��iteral AS nm_taisyo, "); // �Ώێ��ޖ�
			strAllSql.append("     M302A.cd_��iteral AS cd_taisyo, "); // �Ώێ��ރR�[�h
			strAllSql.append("     M302B.nm_��iteral AS nm_hattyu, "); // ������
			strAllSql.append("     M302B.cd_��iteral AS cd_hattyu, "); // ������R�[�h
			strAllSql.append("     T401.flg_tehai_status ,"); // ��z�X�e�[�^�X
			strAllSql.append("     CONVERT(NVARCHAR, T401.dt_koshin, 120) AS toroku , "); // �o�^��(�\���p)

			strAllSql.append("     T401.naiyo ,"); // ���e
			strAllSql.append("     CONVERT(NVARCHAR, T401.dt_han_payday, 111) AS dt_han_payday , "); // �ő�x����
			strAllSql.append("     T401.han_pay ,"); // �ő�
			strAllSql.append("     T401.nm_file_aoyaki ,"); // �ăt�@�C����
			strAllSql.append("     T401.file_path_aoyaki ,"); // �ăt�@�C���p�X
			strAllSql.append("     CONVERT(NVARCHAR, T341.dt_hattyu, 111) AS dt_hattyu , "); // ������
			strAllSql.append("     T401.sekkei1 ,"); // �݌v�@
			strAllSql.append("     T401.sekkei2 ,"); // �݌v�A
			strAllSql.append("     T401.sekkei3 ,"); // �݌v�B
			strAllSql.append("     T401.zaishitsu ,"); // �ގ�
			strAllSql.append("     T401.biko_tehai ,"); // ���l
			strAllSql.append("     T401.printcolor ,"); // ����F
			strAllSql.append("     T401.no_color ,"); // �F�ԍ�
			strAllSql.append("     T401.henkounaiyoushosai ,"); // �ύX���e�ڍ�
			strAllSql.append("     T401.nouki ,"); // �[��
			strAllSql.append("     T401.suryo ,"); // ����
			strAllSql.append("     M101.nm_user AS nm_tanto , "); // �S����
			strAllSql.append("     M104.nm_busho AS nounyusaki , "); // �[����i�����H��j
			strAllSql.append("     CONVERT(NVARCHAR, niuke.dt_niuke, 111) AS nounyu_day "); // �[����(�\���p)

			strAllSql.append("   FROM ");
			strAllSql.append("     tr_shizai_tehai T401 ");
			strAllSql.append("       LEFT JOIN tr_shisan_shizai T341 ");
			strAllSql.append("         ON T401.cd_shohin = T341.cd_seihin ");
			strAllSql.append("        AND T401.cd_shizai = T341.cd_shizai ");
			strAllSql.append("        AND T401.cd_shizai_new = T341.cd_shizai_new ");
			strAllSql.append("        AND T401.cd_shain = T341.cd_shain ");
			strAllSql.append("        AND T401.nen = T341.nen ");
			strAllSql.append("        AND T401.no_oi = T341.no_oi ");
			strAllSql.append("        AND T401.seq_shizai = T341.seq_shizai ");
			strAllSql.append("        AND T401.no_eda = T341.no_eda ");
			strAllSql.append("       LEFT JOIN ma_Hinmei_Mst M991 ");
			strAllSql.append("         ON CONVERT(int,T401.cd_shohin) ");
			strAllSql.append("            = CONVERT(int,M991.cd_hinmei) ");
			strAllSql.append("        AND T341.cd_kaisha = M991.cd_kaisha ");
			strAllSql.append("       LEFT JOIN ( ");
			strAllSql.append("         SELECT DISTINCT ");
			strAllSql.append("           cd_��iteral, ");
			strAllSql.append("           nm_literal  ");
			strAllSql.append("         FROM ");
			strAllSql.append("           ma_literal ");
			strAllSql.append("         WHERE cd_category = 'C_taisyosizai' ");
			strAllSql.append("       ) M302A  ");
			strAllSql.append("         ON T341.cd_taisyoshizai = M302A.cd_��iteral ");
			strAllSql.append("       LEFT JOIN ( ");
			strAllSql.append("         SELECT DISTINCT ");
			strAllSql.append("           cd_��iteral, ");
			strAllSql.append("           nm_literal  ");
			strAllSql.append("         FROM ");
			strAllSql.append("           ma_literal ");
			strAllSql.append("         WHERE cd_category = 'C_hattyuusaki' ");
			strAllSql.append("       ) M302B ");
			strAllSql.append("         ON T341.cd_hattyusaki = M302B.cd_��iteral ");

			strAllSql.append("       LEFT JOIN ma_user M101 ");
			strAllSql.append("         ON   M101.id_user = T401.id_toroku ");
			strAllSql.append("       LEFT JOIN ma_busho M104 ");
			strAllSql.append("         ON  (T341.cd_kaisha = M104.cd_kaisha ");
			strAllSql.append("          AND  T341.cd_seizokojo = M104.cd_busho) ");

			strAllSql.append("       LEFT JOIN (SELECT cd_kaisha, cd_kojyo, cd_hin, dt_niuke ");
			strAllSql.append("                  from tr_nyukei_jisekiari ");
			strAllSql.append("                  group by cd_kaisha,cd_kojyo,cd_hin,dt_niuke ");
			strAllSql.append("                  )niuke ");
			strAllSql.append("         ON  (T341.cd_kaisha = convert(int, niuke.cd_kaisha) ");
			strAllSql.append("    AND  T341.cd_seizokojo = convert(int, niuke.cd_kojyo) ");
			strAllSql.append("    AND  T341.cd_seihin = convert(int, niuke.cd_hin)) ");

			strAllSql.append(strWhere);

			strAllSql.append("   ) AS result ");
			strAllSql.append(" WHERE ");
			strAllSql.append("   row_no BETWEEN 1 AND " + strListRowMax + " ");
			strAllSql.append(" ORDER BY ");
			strAllSql.append("   cast(cd_shizai_new AS int), toroku ");

			strSql = strAllSql;
		} catch (Exception e) {

			em.ThrowException(e, "���ތ��������Ɏ��s���܂����B");

		} finally {
			// �ϐ��̍폜
			strAllSql = null;
		}

		return strSql;
	}

	/**
	 * Where��擾SQL�쐬
	 *
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
	 * @param strSql
	 *            : ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		StringBuffer strWhere = new StringBuffer();

		try {

			/* �P�s�� */
			// ��z�ς�
			String kbn_tehaizumi = "";
			// ����z
			String kbn_mitehai = "";
			// ������
			String kbn_minyuryoku = "";
			/* �Q�s�� */
			// ���ރR�[�h
			String cd_shizai = "";
			// ���ޖ�
			String nm_shizai = "";
			// �����ރR�[�h
			String cd_shizai_old = "";
			/* �R�s�� */
			// ���i�i���i�j�R�[�h
			String cd_shohin = "";
			// ���i�i���i�j��
			String nm_shohin = "";
			// �[����i�����H��j
			String cd_seizoukojo = "";
			/* �S�s�� */
			// �Ώێ���
			String taisyo_shizai = "";
			// ������
			String cd_hattyusaki = "";
			// ������
			String cd_hattyusya = "";
			/* �T�s�� */
			// ������From
			String dt_hattyu_from = "";
			// ������To
			String dt_hattyu_to = "";
			// �[����From
			String dt_nonyu_from = "";
			// �[����To
			String dt_nonyu_to = "";
			// �ő�x����From
			String dt_han_payday_from = "";
			// �ő�x����To
			String dt_han_payday_to = "";
			// ���x��
			String kbn_mshiharai = "";

			kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));

			// ���ގ�z�ψꗗ�œo�^�A�b�v���[�h��̉�ʍă��[�h�p
			String strMovement_condition = toString(userInfoData.getMovement_condition());

			// ��z�ϋ�`�F�b�N
			if (kbn_tehaizumi.equals("") && strMovement_condition.length() < 36) {
				return null;

			} else if (kbn_tehaizumi.equals("") &&  strMovement_condition.length() >= 36) {

				// ��z�σ`�F�b�N�{�b�N�X
				kbn_tehaizumi = toString(userInfoData.getMovement_condition().get(0));

				/* 2�s�� */
				// ���ރR�[�h
				cd_shizai = toString(userInfoData.getMovement_condition().get(1));
				// ���ޖ�
				nm_shizai = toString(userInfoData.getMovement_condition().get(2));
				// �����ރR�[�h
				cd_shizai_old = toString(userInfoData.getMovement_condition().get(3));

				/* 3�s�� */
				// ���i�R�[�h
				cd_shohin = toString(userInfoData.getMovement_condition().get(4));
				// ���i��
				nm_shohin = toString(userInfoData.getMovement_condition().get(5));
				// �[����
				cd_seizoukojo = toString(userInfoData.getMovement_condition().get(6));

				/* 4�s�� */
				// �Ώێ���
				taisyo_shizai = toString(userInfoData.getMovement_condition().get(7));
				// ������
				cd_hattyusaki = toString(userInfoData.getMovement_condition().get(8));
				// ������
				cd_hattyusya = toString(userInfoData.getMovement_condition().get(9));

				/* 5�s�� */
				// �������ifrom�j
				String dt_hattyu_from_before = toString(userInfoData.getMovement_condition().get(10));
				dt_hattyu_from = getDateFormat(dt_hattyu_from_before);
				// ������ito�j
				String dt_hattyu_to_before = toString(userInfoData.getMovement_condition().get(11));
				dt_hattyu_to = getDateFormat(dt_hattyu_to_before);
				// �[�����ifrom�j
				String dt_nonyu_from_before = toString(userInfoData.getMovement_condition().get(12));
				dt_nonyu_from = getDateFormat(dt_nonyu_from_before);
				// �[�����ito�j
				String dt_nonyu_to_before = toString(userInfoData.getMovement_condition().get(13));
				dt_nonyu_to = getDateFormat(dt_nonyu_to_before);
				// �ő�x�����ifrom�j
				String dt_han_payday_from_before = toString(userInfoData.getMovement_condition().get(14));
				dt_han_payday_from = getDateFormat(dt_han_payday_from_before);
				// �ő�x���� (to�j
				String dt_han_payday_to_before = toString(userInfoData.getMovement_condition().get(15));
				getDateFormat(dt_han_payday_to_before);
				// ���x��
				kbn_mshiharai = toString(userInfoData.getMovement_condition().get(16));

			} else {

				// ���������擾---------------------------------------------------------------------
				/* �P�s�� */
				// ��z�ς�
				kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));
				// ����z
				kbn_mitehai = toString(reqData.getFieldVale(0, 0, "kbn_mitehai"));
				// ������
				kbn_minyuryoku = toString(reqData.getFieldVale(0, 0, "kbn_minyuryoku"));
				/* �Q�s�� */
				// ���ރR�[�h
				cd_shizai = toString(reqData.getFieldVale(0, 0, "cd_shizai"));
				// ���ޖ�
				nm_shizai = toString(reqData.getFieldVale(0, 0, "nm_shizai"));
				// �����ރR�[�h
				cd_shizai_old = toString(reqData.getFieldVale(0, 0, "cd_shizai_old"));
				/* �R�s�� */
				// ���i�i���i�j�R�[�h
				cd_shohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
				// ���i�i���i�j��
				nm_shohin = toString(reqData.getFieldVale(0, 0, "nm_shohin"));
				// �[����i�����H��j
				cd_seizoukojo = toString(reqData.getFieldVale(0, 0, "cd_seizoukojo"));
				/* �S�s�� */
				// �Ώێ���
				taisyo_shizai = toString(reqData.getFieldVale(0, 0, "taisyo_shizai"));
				// ������
				cd_hattyusaki = toString(reqData.getFieldVale(0, 0, "cd_hattyusaki"));
				// ������
				cd_hattyusya = toString(reqData.getFieldVale(0, 0, "cd_hattyusya"));
				/* �T�s�� */
				// ������From
				String dt_hattyu_from_before = toString(reqData.getFieldVale(0, 0, "dt_hattyu_from"));
				dt_hattyu_from = getDateFormat(dt_hattyu_from_before);
				// ������To
				String dt_hattyu_to_before = toString(reqData.getFieldVale(0, 0, "dt_hattyu_to"));
				dt_hattyu_to = getDateFormat(dt_hattyu_to_before);
				// �[����From
				String dt_nonyu_from_before = toString(reqData.getFieldVale(0, 0, "dt_nonyu_from"));
				dt_nonyu_from = getDateFormat(dt_nonyu_from_before);
				// �[����To
				String dt_nonyu_to_before = toString(reqData.getFieldVale(0, 0, "dt_nonyu_to"));
				dt_nonyu_to = getDateFormat(dt_nonyu_to_before);
				// �ő�x����From
				String dt_han_payday_from_before = toString(reqData.getFieldVale(0, 0, "dt_han_payday_from"));
				dt_han_payday_from = getDateFormat(dt_han_payday_from_before);
				// �ő�x����To
				String dt_han_payday_to_before = toString(reqData.getFieldVale(0, 0, "dt_han_payday_to"));
				dt_han_payday_to = getDateFormat(dt_han_payday_to_before);
				// ���x��
				kbn_mshiharai = toString(reqData.getFieldVale(0, 0, "kbn_mshiharai"));
			}
			System.out.println("cd_seizoukojo:[" + cd_seizoukojo + "]");
            System.out.println("������F[" + cd_hattyusaki + "]");

			// WHERE��SQL�쐬----------------------------------------------------------------
			strWhere.append(" WHERE 1 = 1 ");

			/* �P�s�� */
			// ��z�`�F�b�N���ڂ��`�F�b�N����Ă���ꍇ
			if ("1".equals(kbn_tehaizumi) || "1".equals(kbn_mitehai)
					|| "1".equals(kbn_minyuryoku)) {
				boolean orFlg = false;
				strWhere.append(" AND (");
				if ("1".equals(kbn_tehaizumi)) {
					strWhere.append(" flg_tehai_status = 3 ");
					orFlg = true;
				}
				if ("1".equals(kbn_mitehai)) {
					if (orFlg) {
						strWhere.append(" OR ");
					}
					strWhere.append(" flg_tehai_status = 2 ");
					orFlg = true;
				}
				if ("1".equals(kbn_minyuryoku)) {
					if (orFlg) {
						strWhere.append(" OR ");
					}
					strWhere.append(" flg_tehai_status <= 1 ");
				}
				strWhere.append(") ");
			}

			/* �Q�s�� */
			// ���ރR�[�h�����͂���Ă���ꍇ
			if (!cd_shizai.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shizai_new) = CONVERT(bigint, '"
						+ cd_shizai + "') ");
			}

			// ���ޖ������͂���Ă���ꍇ
			if (!nm_shizai.equals("")) {
				strWhere.append(" AND T341.nm_shizai_new LIKE '%" + nm_shizai
						+ "%' ");
			}

			// �����ރR�[�h�����͂���Ă���ꍇ
			if (!cd_shizai_old.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shizai) = CONVERT(bigint, '"
						+ cd_shizai_old + "') ");
			}

			/* �R�s�� */
			// ���i�i���i�j�R�[�h�����͂���Ă���ꍇ
			if (!cd_shohin.equals("")) {
				strWhere.append(" AND CONVERT(bigint, T401.cd_shohin ) = CONVERT(bigint, '"
						+ cd_shohin + "') ");
			}

			// ���i�i���i�j�������͂���Ă���ꍇ
			if (!nm_shohin.equals("")) {
				strWhere.append(" AND T401.nm_shohin LIKE '%" + nm_shohin
						+ "%' ");
			}

			// �[����i�����H��j�R�[�h�����͂���Ă���ꍇ
			if (!cd_seizoukojo.equals("")) {
				strWhere.append(" AND M104.nm_busho LIKE '%"
						+ cd_seizoukojo +  "%' ");
			}

			/* �S�s�� */
			// �Ώێ��ނ����͂���Ă���ꍇ
			if (!taisyo_shizai.equals("")) {
				strWhere.append(" AND M302A.cd_��iteral = '" + taisyo_shizai
						+ "' ");
			}
			// �����悪���͂���Ă���ꍇ
			if (!cd_hattyusaki.equals("")) {
				strWhere.append(" AND T341.cd_hattyusaki = CONVERT(int," + cd_hattyusaki + ") ");
			}
			// �����҂����͂���Ă���ꍇ
			if (!cd_hattyusya.equals("")) {
				strWhere.append(" AND T401.id_koshin = '" + cd_hattyusya + "'");
			}

			/* �T�s�� */
			// ������(From,To)�����͂���Ă���ꍇ
			if (!dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) BETWEEN '" + dt_hattyu_from + "' "
						+ " AND '" + dt_hattyu_to + "' ");
			}
			// ������(From)�����͂���Ă���ꍇ
			else if (!dt_hattyu_from.equals("") && dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) >= '" + dt_hattyu_from
						+ "' ");
			}
			// ������(To)�����͂���Ă���ꍇ
			else if (dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T341.dt_hattyu, 112) <= '" + dt_hattyu_to
						+ "' ");
			}

			// �[����(From,To)�����͂���Ă���ꍇ
			if (!dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) BETWEEN '" + dt_nonyu_from + "' "
						+ " AND '" + dt_nonyu_to + "' ");
			}
			// �[����(From)�����͂���Ă���ꍇ
			else if (!dt_nonyu_from.equals("") && dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) >= '" + dt_nonyu_from
						+ "' ");
			}
			// �[����(To)�����͂���Ă���ꍇ
			else if (dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, niuke.dt_niuke, 112) <= '" + dt_nonyu_to
						+ "' ");
			}

			// �ő�x����(From,To)�����͂���Ă���ꍇ
			if (!dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) BETWEEN '" + dt_han_payday_from + "' "
						+ " AND '" + dt_han_payday_to + "' ");
			}
			// �ő�x����(From)�����͂���Ă���ꍇ
			else if (!dt_han_payday_from.equals("") && dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) >= '" + dt_han_payday_from
						+ "' ");
			}
			// �ő�x����(To)�����͂���Ă���ꍇ
			else if (dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(VARCHAR, T401.dt_han_payday, 112) <= '" + dt_han_payday_to
						+ "' ");
			}

			// ���x�����`�F�b�N����Ă���ꍇ
			if ("1".equals(kbn_mshiharai)) {
				// �ő�x�������� ���� �őオ0�ȊO
				strWhere.append(" AND (T401.dt_han_payday IS NULL OR T401.dt_han_payday = '') ");
				strWhere.append(" AND T401.han_pay != 0 ");
			}

		} catch (Exception e) {

			em.ThrowException(e, "���ތ��������Ɏ��s���܂����B");

		} finally {

		}

		return strWhere;
	}

	private String getDateFormat(String date) {
		StringBuffer strbuf = new StringBuffer();
		String strMM;
		String strDD;
		String[] strTemps;
		if (!date.equals("")) {

			//strHanPayDayBefore =  strHanPayDay.replace("/", "");

			if (date.indexOf("/") != -1) {

				strTemps = date.split("/");

				// �N���o��
				strbuf.append(strTemps[0].toString());
				if (strTemps[1].toString().length() == 1) {
					strMM = strTemps[1].toString();
					strbuf.append("0" + strMM);

				} else {
					strbuf.append(strTemps[1].toString());;
				}
				if (strTemps[2].toString().length() == 1) {

					strDD = strTemps[2].toString();
					strbuf.append("0" + strDD);
				} else {
					strbuf.append(strTemps[2].toString());
				}

			} else {
				strbuf.append(date);
			}
		} else {
			strbuf.append("");
		}
		return strbuf.toString();

	}

	/**
	 * ���ރf�[�^�p�����[�^�[�i�[ : ���ރf�[�^�������X�|���X�f�[�^�֊i�[����B
	 *
	 * @param lstRecset
	 *            : �������ʏ�񃊃X�g
	 * @param resTable
	 *            : ���X�|���X���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShizaiData(List<?> lstRecset,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {

			if ((lstRecset == null) || (lstRecset.size() == 0)) {

				// �f�[�^���擾�ł��Ȃ����F�G���[�ɂ��Ȃ��ׁijs�̃��b�Z�[�W��Ԃ������j
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "sisakuNo", "");

			} else {



				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				String strMovement_condition = toString(userInfoData.getMovement_condition());

				if (strMovement_condition.length() >= 36 && strMovement_condition.length() < 50 ) {

					// ��z�σ`�F�b�N�{�b�N�X
					String strChkTehaizumiTemp = toString(userInfoData.getMovement_condition().get(0));

					/* 2�s�� */
					// ���ރR�[�h
					String strShizaiCdTemp = toString(userInfoData.getMovement_condition().get(1));
					// ���ޖ�
					String strShizaiNmTemp = toString(userInfoData.getMovement_condition().get(2));
					// �����ރR�[�h
					String strKyuShizaiCdTemp = toString(userInfoData.getMovement_condition().get(3));

					/* 3�s�� */
					// ���i�R�[�h
					String strSyouhinCdTemp = toString(userInfoData.getMovement_condition().get(4));
					// ���i��
					String strSyouhinNmTemp = toString(userInfoData.getMovement_condition().get(5));
					// �[����
					String strSeizoukojoTemp = toString(userInfoData.getMovement_condition().get(6));

					/* 4�s�� */
					// �Ώێ���
					String strDdlShizaiTemp = toString(userInfoData.getMovement_condition().get(7));
					// ������
					String strHattyusakiTemp = toString(userInfoData.getMovement_condition().get(8));
					// ������
					String strDdlTantoTemp = toString(userInfoData.getMovement_condition().get(9));

					/* 5�s�� */
					// �������ifrom�j
					String strHattyubiFromTemp = toString(userInfoData.getMovement_condition().get(10));
					// ������ito�j
					String strHattyubiToTemp = toString(userInfoData.getMovement_condition().get(11));
					// �[�����ifrom�j
					String strNounyudayFromTemp = toString(userInfoData.getMovement_condition().get(12));
					// �[�����ito�j
					String strNounyudayToTemp = toString(userInfoData.getMovement_condition().get(13));
					// �ő�x�����ifrom�j
					String strHanPaydayFromTemp = toString(userInfoData.getMovement_condition().get(14));
					// �ő�x���� (to�j
					String strHanPaydayToTemp = toString(userInfoData.getMovement_condition().get(15));
					// ���x��
					String strChkMshiharaiTemp = toString(userInfoData.getMovement_condition().get(16));

					// ��z�σ`�F�b�N�{�b�N�X
					resTable.addFieldVale(0, "chkTehaizumi", strChkTehaizumiTemp);
					// ���ރR�[�h
					resTable.addFieldVale(0, "txtShizaiCd", strShizaiCdTemp);
					// ���ޖ�
					resTable.addFieldVale(0, "txtShizaiNm", strShizaiNmTemp);
					// �����ރR�[�h
					resTable.addFieldVale(0, "txtOldShizaiCd", strKyuShizaiCdTemp);
					// ���i�R�[�h
					resTable.addFieldVale(0, "txtSyohinCd", strSyouhinCdTemp);
					// ���i��
					resTable.addFieldVale(0, "txtSyohinNm", strSyouhinNmTemp);
					// �[����
					resTable.addFieldVale(0, "txtSeizoukojo", strSeizoukojoTemp);
					// �Ώێ���
					resTable.addFieldVale(0, "ddlShizai", strDdlShizaiTemp);
					// ������
					resTable.addFieldVale(0, "ddlHattyusaki", strHattyusakiTemp);
					// ������
					resTable.addFieldVale(0, "ddlTanto", strDdlTantoTemp);
					// ������from
					resTable.addFieldVale(0, "txtHattyubiFrom", strHattyubiFromTemp);
					// ������to
					resTable.addFieldVale(0, "txtHattyubiTo", strHattyubiToTemp);
					// �[����
					resTable.addFieldVale(0, "txtNounyudayFrom", strNounyudayFromTemp);
					// �ő�x����from
					resTable.addFieldVale(0, "txtHanPaydayFrom", strHanPaydayFromTemp);
					// �ő�x����to
					resTable.addFieldVale(0, "txtHanPaydayTo", strHanPaydayToTemp);
					// ���x��
					resTable.addFieldVale(0, "chkMshiharai", strChkMshiharaiTemp);
				}
				// �������� �ԋp����"0"�łȂ��ꍇ�͏���I�[�o�[
				// ����I�[�o�[�̏ꍇ�͏���l��ݒ�
				String limit = "0";

				// ���[�v��
				int loop_cnt = lstRecset.size();

				// �ő�s���̎擾
				int strListRowMax = toInteger(ConstManager.getConstValue(
						ConstManager.Category.�ݒ�, "SHIZAI_CODE_LIST_ROW_MAX"));
				// �ő�s���ȏ�̌������擾���ꂽ�ꍇ
				if (strListRowMax < lstRecset.size()) {
					limit = toString(strListRowMax);
					loop_cnt = strListRowMax;
				}

				// ���ރf�[�^���[�v
				for (int i = 0; i < loop_cnt; i++) {

					// �������ʎ擾
					Object[] items = (Object[]) lstRecset.get(i);
					String row_no = toString(items[0]);					// �s�ԍ�
					String cd_shain = toString(items[1]);				// �Ј��R�[�h
					String nen = toString(items[2]);					// �N
					String no_oi = toString(items[3]);					// �ǔ�
					String seq_shizai = toString(items[4]);				// �s��
					String no_eda = toString(items[5]);					// �}��
					String cd_shizai = toString(items[6]);				// ���ރR�[�h
					String nm_shizai = toString(items[7]);				// ���ޖ�
					String cd_shizai_old = toString(items[8]);			// �����ރR�[�h
					String cd_shohin = toString(items[9]);				// ���i�i���i�j�R�[�h
					String nm_shohin = toString(items[10]);				// ���i�i���i�j��
					String nisugata = toString(items[11]);				// �׎p
					String nm_taisyo_shizai = toString(items[12]);		// �Ώێ��ޖ�
					String cd_taisyo_shizai = toString(items[13]);		// �Ώێ��ރR�[�h
					String nm_hattyusaki = toString(items[14]);			// ������
					String cd_hattyusaki = toString(items[15]);			// ������R�[�h
					String flg_status = toString(items[16]);			// ��z�X�e�[�^�X
					String dt_koshin_disp = toString(items[17]);		// �o�^��

					String naiyo = toString(items[18]);					// ���e
					String nounyusaki = toString(items[19]);			// �[����i�����H��j
					String dt_han_payday = toString(items[20]);			// �ő�x����
					String han_pay = toString(items[21]);				// �ő�
					String nm_file_aoyaki = toString(items[22]);		// �ăt�@�C����
					String file_path_aoyaki = toString(items[23]);		// �ăt�@�C���p�X
					String dt_hattyu = toString(items[24]);				// ������
					String sekkei1 = toString(items[25]);				// �݌v�@
					String sekkei2 = toString(items[26]);				// �݌v�A
					String sekkei3 = toString(items[27]);				// �݌v�B
					String zaishitsu = toString(items[28]);				// �ގ�
					String biko_tehai = toString(items[29]);			// ���l
					String printcolor = toString(items[30]);			// ����F
					String no_color = toString(items[31]);				// �F�ԍ�
					String henkounaiyoushosai = toString(items[32]);	// �ύX���e�ڍ�
					String nouki = toString(items[33]);					// �[��
					String suryo = toString(items[34]);					// ����
					String nm_tanto = toString(items[35]);				// �S����
					String nounyu_day = toString(items[36]);			// �[����


					// ���X�|���X����
					resTable.addFieldVale(i, "row_no", row_no);								// �s�ԍ�
					resTable.addFieldVale(i, "cd_shain", cd_shain);							// �Ј��R�[�h
					resTable.addFieldVale(i, "nen", nen);									// �N
					resTable.addFieldVale(i, "no_oi", no_oi);								// �ǔ�
					resTable.addFieldVale(i, "seq_shizai", seq_shizai);						// �s��
					resTable.addFieldVale(i, "no_eda", no_eda);								// �}��
					resTable.addFieldVale(i, "cd_shizai", cd_shizai);						// ���ރR�[�h
					resTable.addFieldVale(i, "nm_shizai", nm_shizai);						// ���ޖ�
					resTable.addFieldVale(i, "cd_shizai_old", cd_shizai_old);				// �����ރR�[�h
					resTable.addFieldVale(i, "cd_shohin", cd_shohin);						// ���i�i���i�j�R�[�h
					resTable.addFieldVale(i, "nm_shohin", nm_shohin);						// ���i�i���i�j��
					resTable.addFieldVale(i, "nisugata", nisugata);							// �׎p
					resTable.addFieldVale(i, "nm_taisyo_shizai", nm_taisyo_shizai);			// �Ώێ��ޖ�
					resTable.addFieldVale(i, "cd_taisyo_shizai", cd_taisyo_shizai);			// �Ώێ��ރR�[�h
					resTable.addFieldVale(i, "nm_hattyusaki", nm_hattyusaki);				// ������
					resTable.addFieldVale(i, "cd_hattyusaki", cd_hattyusaki);				// ������R�[�h
					resTable.addFieldVale(i, "flg_status", flg_status);						// ��z�X�e�[�^�X
					resTable.addFieldVale(i, "dt_koshin_disp", dt_koshin_disp);				// �o�^��(�\���p)

					resTable.addFieldVale(i, "naiyo", naiyo);								// ���e
					resTable.addFieldVale(i, "nounyusaki", nounyusaki);						// �[����i�����H��j
					resTable.addFieldVale(i, "dt_han_payday", dt_han_payday);				// �ő�x����
					resTable.addFieldVale(i, "han_pay", han_pay);							// �ő�
					resTable.addFieldVale(i, "nm_file_aoyaki", nm_file_aoyaki);				// �ăt�@�C����

					resTable.addFieldVale(i, "file_path_aoyaki", file_path_aoyaki);			// �ăt�@�C���p�X
					resTable.addFieldVale(i, "dt_hattyu", dt_hattyu);						// ������
					resTable.addFieldVale(i, "sekkei1", sekkei1);							// �݌v�@
					resTable.addFieldVale(i, "sekkei2", sekkei2);							// �݌v�A
					resTable.addFieldVale(i, "sekkei3", sekkei3);							// �݌v�B
					resTable.addFieldVale(i, "zaishitsu", zaishitsu);						// �ގ�
					resTable.addFieldVale(i, "biko_tehai", biko_tehai);						// ���l
					resTable.addFieldVale(i, "printcolor", printcolor);						// ����F
					resTable.addFieldVale(i, "no_color", no_color);							// �F�ԍ�
					resTable.addFieldVale(i, "henkounaiyoushosai", henkounaiyoushosai);		// �ύX���e�ڍ�
					resTable.addFieldVale(i, "nouki", nouki);								// �[��
					resTable.addFieldVale(i, "suryo", suryo);								// ����
					resTable.addFieldVale(i, "nm_tanto", nm_tanto);							// �S����
					resTable.addFieldVale(i, "nounyu_day", nounyu_day);						// �[����

					resTable.addFieldVale(i, "loop_cnt", Integer.toString(loop_cnt));	// �擾����
					resTable.addFieldVale(i, "limit_over", limit);						// �ő�s��

				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "���ރR�[�h���������Ɏ��s���܂����B");

		} finally {

		}

	}
}
