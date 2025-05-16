package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���ޏ��擾DB����
 *  �@�\ID�FFGEN3340�@
 *
 * @author TT.Shima
 * @since  2014/09/25
 */
public class FGEN3340_Logic extends LogicBase {

	/**
	 * ���ޏ��擾�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3340_Logic(){
		super();
	}

	/**
	 * ���ޏ��擾 : ���ޏ����擾����B
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
			strSql = createTehaiSQL(reqData, strSql, strSqlWhere);

			// DB�C���X�^���X����
			createSearchDB();

			// �������s�i���ރf�[�^�擾�j
			lstRecset = searchDB.dbSearch(strSql.toString());

			// �������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.�x��Exception, "W000401",
						strSql.toString(), "", "");
			}

			// �@�\ID�̐ݒ�
			resKind.setID(reqData.getID());

			// �e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			// ���X�|���X�f�[�^�̌`��
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");
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
	 * �擾SQL�쐬
	 *  : ��z�����擾����SQL���쐬�B
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
	private StringBuffer createTehaiSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strAllSql = new StringBuffer();

		try {
			// SQL���̍쐬
			strAllSql.append(" SELECT ");
			strAllSql.append("   T401.cd_shain, ");
			strAllSql.append("   T401.nen, ");
			strAllSql.append("   T401.no_oi, ");
			strAllSql.append("   T401.seq_shizai, ");
			strAllSql.append("   T401.no_eda, ");
//�yKPX@1602367�z20161020 add mod start
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shohin), 6) AS cd_shohin, "); // ���i�i���i�j�R�[�h
			strAllSql.append("   T401.nm_shohin, ");
			strAllSql.append("   M991.name_nisugata, ");
			strAllSql.append("   T341.cd_taisyoshizai, ");
			strAllSql.append("   T341.cd_hattyusaki, ");
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shizai), 6) AS cd_shizai, "); // �����ރR�[�h
			strAllSql.append("   RIGHT('000000' + convert(varchar,T401.cd_shizai_new), 6) AS cd_shizai_new, "); // ���ރR�[�h
			strAllSql.append("   T401.flg_tehai_status, ");

			strAllSql.append("   T401.naiyo ,");			 		// ���e
			strAllSql.append("   M104.nm_busho AS nounyusaki, "); 	// �[����i�����H��j

			// �ǉ�
			strAllSql.append("   T401.dt_han_payday AS dt_han_payday, "); 	// �ő�x����
			strAllSql.append("   T401.han_pay AS han_pay, "); 	// �ő�
			strAllSql.append("   T401.nm_file_aoyaki AS nm_file_aoyaki, "); 	// �Ă��t�@�C����
			strAllSql.append("   T401.file_path_aoyaki AS file_path_aoyaki "); 	// �Ă��t�@�C���p�X

			strAllSql.append(" FROM ");
			strAllSql.append("   tr_shizai_tehai T401 ");
			strAllSql.append("     LEFT JOIN tr_shisan_shizai T341 ");
			strAllSql.append("       ON T401.cd_shohin = T341.cd_seihin ");
			strAllSql.append("      AND T401.cd_shizai = T341.cd_shizai ");
			strAllSql.append("      AND T401.cd_shizai_new = T341.cd_shizai_new ");
			strAllSql.append("      AND T401.cd_shain = T341.cd_shain ");
			strAllSql.append("      AND T401.nen = T341.nen ");
			strAllSql.append("      AND T401.no_oi = T341.no_oi ");
			strAllSql.append("      AND T401.seq_shizai = T341.seq_shizai ");
			strAllSql.append("      AND T401.no_eda = T341.no_eda ");

			strAllSql.append("     LEFT JOIN ma_Hinmei_Mst M991 ");
			strAllSql.append("         ON CONVERT(int,T401.cd_shohin) ");
			strAllSql.append("            = CONVERT(int,M991.cd_hinmei) ");
			strAllSql.append("      AND T341.cd_kaisha = M991.cd_kaisha ");
			strAllSql.append("     LEFT JOIN ma_busho M104 ");
			strAllSql.append("         ON  (T341.cd_kaisha = M104.cd_kaisha ");
			strAllSql.append("      AND  T341.cd_seizokojo = M104.cd_busho) ");
//�yKPX@1602367�z20161020 add end
			strAllSql.append(strWhere);
			strAllSql.append(" ORDER BY ");
			strAllSql.append("   T401.cd_shohin, ");
			strAllSql.append("   T401.nm_shohin, ");
			strAllSql.append("   M991.name_nisugata ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");
		} finally {

		}
		return strAllSql;
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
			strWhere.append(" WHERE (");

			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				// ���������擾---------------------------------------------------------------------

				// �Ј��R�[�h
				String cd_shain = toString(reqData.getFieldVale(0, i, "cd_shain"));

				// �N
				String nen = toString(reqData.getFieldVale(0, i, "nen"));

				// �ǔ�
				String no_oi = toString(reqData.getFieldVale(0, i, "no_oi"));

				// �s�ԍ�
				String seq_shizai = toString(reqData.getFieldVale(0, i, "seq_shizai"));

				// �}��
				String no_eda = toString(reqData.getFieldVale(0, i, "no_eda"));

				// WHERE��SQL�쐬----------------------------------------------------------------

				if(i != 0){
					strWhere.append(" OR ");
				}

				strWhere.append(" (T401.cd_shain = '" + cd_shain + "' ");
				strWhere.append(" AND T401.nen = '" + nen + "' ");
				strWhere.append(" AND T401.no_oi = '" + no_oi + "' ");
				strWhere.append(" AND T401.seq_shizai = '" + seq_shizai + "' ");
				strWhere.append(" AND T401.no_eda = '" + no_eda + "') ");

			}
			strWhere.append(" ) ");

		} catch (Exception e) {

			em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");

		} finally {

		}

		return strWhere;
	}

	/**
	 * ���ޏ��p�����[�^�[�i�[ : ���ޏ������X�|���X�f�[�^�֊i�[����B
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
			for(int i = 0; i < lstRecset.size(); i++){
				// �������ʎ擾
				Object[] items = (Object[]) lstRecset.get(i);
				String cd_shain = toString(items[0]);
				String nen = toString(items[1]);
				String no_oi = toString(items[2]);
				String seq_shizai = toString(items[3]);
				String no_eda = toString(items[4]);
				String cd_shohin = toString(items[5]);
				String nm_shohin = toString(items[6]);
				String name_nisugata = toString(items[7]);
				String cd_taisyosizai = toString(items[8]);
				String cd_hattyusaki = toString(items[9]);
				String cd_shizai = toString(items[10]);
				String cd_shizai_new = toString(items[11]);
				String flg_tehai_status = toString(items[12]);
				String naiyo = toString(items[13]);					// ���e
				String nounyusaki = toString(items[14]);			// �[����i�����H��j

				String dt_han_payday = toString(items[15]);		// �ő�x����
				String han_pay = toString(items[16]);		// �ő�
				String nm_file_aoyaki = toString(items[17]);		// �Ă��t�@�C����
				String file_path_aoyaki = toString(items[18]);		// �Ă��t�@�C���p�X

				// �������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				// ���X�|���X����
				resTable.addFieldVale(i, "cd_shain", cd_shain);
				resTable.addFieldVale(i, "nen", nen);
				resTable.addFieldVale(i, "no_oi", no_oi);
				resTable.addFieldVale(i, "seq_shizai", seq_shizai);
				resTable.addFieldVale(i, "no_eda", no_eda);
				resTable.addFieldVale(i, "cd_shohin", cd_shohin);
				resTable.addFieldVale(i, "nm_shohin", nm_shohin);
				resTable.addFieldVale(i, "name_nisugata", name_nisugata);
				resTable.addFieldVale(i, "cd_taisyosizai", cd_taisyosizai);
				resTable.addFieldVale(i, "cd_hattyusaki", cd_hattyusaki);
				resTable.addFieldVale(i, "cd_shizai", cd_shizai);
				resTable.addFieldVale(i, "cd_shizai_new", cd_shizai_new);
				resTable.addFieldVale(i, "flg_tehai_status", flg_tehai_status);
				resTable.addFieldVale(i, "naiyo", naiyo);						// ���e
				resTable.addFieldVale(i, "nounyusaki", nounyusaki);				// �[����i�����H��j

				resTable.addFieldVale(i, "dt_han_payday", dt_han_payday);					// �ő�x����
				resTable.addFieldVale(i, "han_pay", han_pay);				// �ő�
				resTable.addFieldVale(i, "nm_file_aoyaki", nm_file_aoyaki);					// �Ă��t�@�C����
				resTable.addFieldVale(i, "file_path_aoyaki", file_path_aoyaki);				// �Ă��t�@�C���p�X

				resTable.addFieldVale(i, "loop_cnt", Integer.toString(lstRecset.size()));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
