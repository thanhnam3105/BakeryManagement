package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class FGEN3620_Logic extends LogicBaseJExcel {

	/**
	 * ���ގ�z�ꗗ_Excel�\�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean ret = null;
		//�����f�[�^
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();
		//�G�N�Z���t�@�C���p�X
		String downLoadPath = "";

		try {
			// SQL�쐬�iWhere��擾�j
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL�쐬�i���ރf�[�^�擾�j
			strSql = createShizaiCodeSQL(reqData, strSql, strSqlWhere);

			// DB����
			super.createSearchDB();
			lstRecset = getData(strSql.toString());

			// Excel�t�@�C������
			downLoadPath = makeExcelFile1(lstRecset, reqData);

			//���X�|���X�f�[�^����
			ret = CreateRespons(downLoadPath, reqData);
			ret.setID(reqData.getID());

		} catch (Exception e) {
			em.ThrowException(e, "���ގ�z�ꗗExcel�\�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
		}
		return reqData;

	}

	/**
	 * ���ގ�z�ꗗ�擾SQL�쐬 : ���ޏ����擾����SQL���쐬�B
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
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shizai_new), 6) AS cd_shizai_new, "); // ���ރR�[�h
			strAllSql.append("     T341.nm_shizai_new, "); // ���ޖ�
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shizai), 6) AS cd_shizai, "); // �����ރR�[�h
			strAllSql.append("     RIGHT('000000' + convert(varchar,T401.cd_shohin), 6) AS cd_shohin, "); // ���i�i���i�j�R�[�h
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
			strAllSql.append("     REPLACE(CONVERT(nvarchar,CONVERT(money, T401.han_pay), 1), '.00', '') as han_pay ,"); // �ő�
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

			em.ThrowException(e, "���ގ�z�ꗗExcel�\�̐����Ɏ��s���܂����B");

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
			// ���������擾---------------------------------------------------------------------
			/* �P�s�� */
			// ��z�ς�
			String kbn_tehaizumi = toString(reqData.getFieldVale(0, 0, "kbn_tehaizumi"));
			// ����z
			String kbn_mitehai = toString(reqData.getFieldVale(0, 0, "kbn_mitehai"));
			// ������
			String kbn_minyuryoku = toString(reqData.getFieldVale(0, 0, "kbn_minyuryoku"));
			/* �Q�s�� */
			// ���ރR�[�h
			String cd_shizai = toString(reqData.getFieldVale(0, 0, "cd_shizai"));
			// ���ޖ�
			String nm_shizai = toString(reqData.getFieldVale(0, 0, "nm_shizai"));
			// �����ރR�[�h
			String cd_shizai_old = toString(reqData.getFieldVale(0, 0, "cd_shizai_old"));
			/* �R�s�� */
			// ���i�i���i�j�R�[�h
			String cd_shohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
			// ���i�i���i�j��
			String nm_shohin = toString(reqData.getFieldVale(0, 0, "nm_shohin"));
			// �[����i�����H��j
			String cd_seizoukojo = toString(reqData.getFieldVale(0, 0, "cd_seizoukojo"));
			/* �S�s�� */
			// �Ώێ���
			String taisyo_shizai = toString(reqData.getFieldVale(0, 0, "taisyo_shizai"));
			// ������
			String cd_hattyusaki = toString(reqData.getFieldVale(0, 0, "cd_hattyusaki"));
			// ������
			String cd_hattyusya = toString(reqData.getFieldVale(0, 0, "cd_hattyusya"));
			/* �T�s�� */
			// ������From
			String dt_hattyu_from = toString(reqData.getFieldVale(0, 0, "dt_hattyu_from"));
			// ������To
			String dt_hattyu_to = toString(reqData.getFieldVale(0, 0, "dt_hattyu_to"));
			// �[����From
			String dt_nonyu_from = toString(reqData.getFieldVale(0, 0, "dt_nonyu_from"));
			// �[����To
			String dt_nonyu_to = toString(reqData.getFieldVale(0, 0, "dt_nonyu_to"));
			// �ő�x����From
			String dt_han_payday_from = toString(reqData.getFieldVale(0, 0, "dt_han_payday_from"));
			// �ő�x����To
			String dt_han_payday_to = toString(reqData.getFieldVale(0, 0, "dt_han_payday_to"));
			// ���x��
			String kbn_mshiharai = toString(reqData.getFieldVale(0, 0, "kbn_mshiharai"));

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
				strWhere.append(" AND CONVERT(int, T341.cd_seizokojo ) = CONVERT(int, '"
						+ cd_seizoukojo + "') ");
			}

			/* �S�s�� */
			// �Ώێ��ނ����͂���Ă���ꍇ
			if (!taisyo_shizai.equals("")) {
				strWhere.append(" AND M302A.cd_��iteral = '" + taisyo_shizai
						+ "' ");
			}

			// �����悪���͂���Ă���ꍇ
			if (!cd_hattyusaki.equals("")) {
				strWhere.append(" AND T341.cd_hattyusaki = '" + cd_hattyusaki
						+ "' ");
			}
			// �����҂����͂���Ă���ꍇ
			if (!cd_hattyusya.equals("")) {
				strWhere.append(" AND T401.id_koshin = '" + cd_hattyusya + "'");
			}

			/* �T�s�� */
			// ������(From,To)�����͂���Ă���ꍇ
			if (!dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) BETWEEN '" + dt_hattyu_from.replace("/", "") + "' "
						+ " AND '" + dt_hattyu_to.replace("/", "") + "' ");
			}
			// ������(From)�����͂���Ă���ꍇ
			else if (!dt_hattyu_from.equals("") && dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) >= '" + dt_hattyu_from.replace("/", "")
						+ "' ");
			}
			// ������(To)�����͂���Ă���ꍇ
			else if (dt_hattyu_from.equals("") && !dt_hattyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T341.dt_hattyu, 112) <= '" + dt_hattyu_to.replace("/", "")
						+ "' ");
			}

			// �[����(From,To)�����͂���Ă���ꍇ
			if (!dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) BETWEEN '" + dt_nonyu_from.replace("/", "") + "' "
						+ " AND '" + dt_nonyu_to.replace("/", "") + "' ");
			}
			// �[����(From)�����͂���Ă���ꍇ
			else if (!dt_nonyu_from.equals("") && dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) >= '" + dt_nonyu_from.replace("/", "")
						+ "' ");
			}
			// �[����(To)�����͂���Ă���ꍇ
			else if (dt_nonyu_from.equals("") && !dt_nonyu_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, niuke.dt_niuke, 112) <= '" + dt_nonyu_to.replace("/", "")
						+ "' ");
			}

			// �ő�x����(From,To)�����͂���Ă���ꍇ
			if (!dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) BETWEEN '" + dt_han_payday_from.replace("/", "") + "' "
						+ " AND '" + dt_han_payday_to.replace("/", "") + "' ");
			}
			// �ő�x����(From)�����͂���Ă���ꍇ
			else if (!dt_han_payday_from.equals("") && dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) >= '" + dt_han_payday_from.replace("/", "")
						+ "' ");
			}
			// �ő�x����(To)�����͂���Ă���ꍇ
			else if (dt_han_payday_from.equals("") && !dt_han_payday_to.equals("")) {
				strWhere.append(" AND CONVERT(NVARCHAR, T401.dt_han_payday, 112) <= '" + dt_han_payday_to.replace("/", "")
						+ "' ");
			}

			// ���x�����`�F�b�N����Ă���ꍇ
			if ("1".equals(kbn_mshiharai)) {
				strWhere.append(" AND (T401.dt_han_payday IS NULL OR T401.dt_han_payday = '') ");
				strWhere.append(" AND T401.han_pay != 0 ");
			}

		} catch (Exception e) {

			em.ThrowException(e, "���ތ��������Ɏ��s���܂����B");

		} finally {

		}

		return strWhere;
	}

	/**
	 * �Ώۂ̎��ރf�[�^����������
	 * @param strSql : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData(String strSql) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//��������
		List<?> ret = null;

		try {
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "���ރf�[�^�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;

	}

	/**
	 * ���ގ�z�ꗗExcel�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try {

			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("���ގ�z�ꗗ");
			ret = super.ExcelOutput("");
			for (int i = 0; i < lstRecset.size(); i++) {

				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);
				// �l�̎擾
				String nm_tanto = toString(items[35]);				// �S����
				String naiyo = toString(items[18]);					// ���e
				String cd_shohin = toString(items[9]);				// ���i�i���i�j�R�[�h
				String nm_shohin = toString(items[10]);				// ���i�i���i�j��
				String nisugata = toString(items[11]);				// �׎p
				String nm_taisyo_shizai = toString(items[12]);		// �Ώێ��ޖ�
				String nm_hattyusaki = toString(items[14]);			// ������
				String nounyusaki = toString(items[19]);			// �[����
				String cd_shizai_old = toString(items[8]);			// �����ރR�[�h
				String cd_shizai = toString(items[6]);				// ���ރR�[�h
				String nm_shizai = toString(items[7]);				// ���ޖ�
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
				String dt_koshin_disp = toString(items[17]);		// �o�^��
				String nounyu_day = toString(items[36]);			// �[����
				String han_pay = toString(items[21]);				// �ő�
				String dt_han_payday = toString(items[20]);			// �ő�x����
				String nm_file_aoyaki = toString(items[22]);		// �ăA�b�v���[�h(�ăt�@�C����)

				// Excel�ɒl���Z�b�g����
				super.ExcelSetValue("�S����", nm_tanto);
				super.ExcelSetValue("���e", naiyo);
				super.ExcelSetValue("���i�R�[�h", cd_shohin);
				super.ExcelSetValue("���i��", nm_shohin);
				super.ExcelSetValue("�׎p", nisugata);
				super.ExcelSetValue("�Ώێ���", nm_taisyo_shizai);
				super.ExcelSetValue("������", nm_hattyusaki);
				super.ExcelSetValue("�[����", nounyusaki);
				super.ExcelSetValue("�����ރR�[�h", cd_shizai_old);
				super.ExcelSetValue("���ރR�[�h", cd_shizai);
				super.ExcelSetValue("���ޖ�", nm_shizai);
				super.ExcelSetValue("�݌v�@", sekkei1);
				super.ExcelSetValue("�݌v�A", sekkei2);
				super.ExcelSetValue("�݌v�B", sekkei3);
				super.ExcelSetValue("�ގ�", zaishitsu);
				super.ExcelSetValue("���l", biko_tehai);
				super.ExcelSetValue("����F", printcolor);
				super.ExcelSetValue("�F�ԍ�", no_color);
				super.ExcelSetValue("�ύX���e�ڍ�", henkounaiyoushosai);
				super.ExcelSetValue("�[��", nouki);
				super.ExcelSetValue("����", suryo);
				super.ExcelSetValue("�o�^��", dt_koshin_disp);
				super.ExcelSetValue("�[����", nounyu_day);
				super.ExcelSetValue("�ő�", han_pay);
				super.ExcelSetValue("�ő�x����", dt_han_payday);
				super.ExcelSetValue("�ăA�b�v���[�h", nm_file_aoyaki);

			}

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		return ret;
	}

	/**
	 * ���X�|���X�f�[�^�𐶐�����
	 * @param DownLoadPath : �t�@�C���p�X�����t�@�C���i�[��(�_�E�����[�h�p�����[�^)
	 * @return RequestResponsKindBean : ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath,  RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�t�@�C���p�X	�����t�@�C���i�[��
			reqData.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//�������ʇ@	������
			reqData.addFieldVale(0, 0, "flg_return", "true");
			//�������ʇA	���b�Z�[�W
			reqData.addFieldVale(0, 0, "msg_error", "");
			//�������ʇB	��������
			reqData.addFieldVale(0, 0, "no_errmsg", "");
			//�������ʇE	���b�Z�[�W�ԍ�
			reqData.addFieldVale(0, 0, "nm_class", "");
			//�������ʇC	�G���[�R�[�h
			reqData.addFieldVale(0, 0, "cd_error", "");
			//�������ʇD	�V�X�e�����b�Z�[�W
			reqData.addFieldVale(0, 0, "msg_system", "");
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return reqData;
	}

}
