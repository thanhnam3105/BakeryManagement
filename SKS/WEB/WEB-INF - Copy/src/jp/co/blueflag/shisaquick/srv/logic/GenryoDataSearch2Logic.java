package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 *
 * ���͒l���́F�������͏�񌟍�DB����
 *  : ���͒l���́F�������͏�����������B
 * @author jinbo
 * @since  2009/04/07
 */
public class GenryoDataSearch2Logic extends LogicBase{

	/**
	 * �������͏��}�X�^�����e�F�������͏�񌟍�DB����
	 * : �C���X�^���X����
	 */
	public GenryoDataSearch2Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���͒l���́F�������͏��擾SQL�쐬
	 *  : �������͏����擾����SQL���쐬�B
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

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strShoriKbn = null;

			//�����敪�̎擾
			strShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�V�K�̏ꍇ
			if (strShoriKbn.equals("1")) {
				storageGenryouData2New(resKind.getTableItem(0));

			//�ڍׂ̏ꍇ
			} else {
				//SQL���̍쐬
				strSql = genryoData2CreateSQL(reqData, strSql);

				//SQL�����s
				super.createSearchDB();
				lstRecset = searchDB.dbSearch(strSql.toString());

				//�������ʂ��Ȃ��ꍇ
				if (lstRecset.size() == 0 && strShoriKbn.equals("2")){
					em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				}

				//���X�|���X�f�[�^�̌`��
				storageGenryouData2(lstRecset, resKind.getTableItem(0));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
		}
		return resKind;

	}

	/**
	 * �������͏��擾SQL�쐬
	 *  : �������͏����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			String strGenryoCd = null;
			String strKaishaCd = null;
			String strKojoCd = null;
//			String dataId = null;

//			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + UserInfoData.getId_user();

			//�����R�[�h�̎擾
			strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�H��R�[�h�̎擾
			strKojoCd = reqData.getFieldVale(0, 0, "cd_busho");

//			//�����f�[�^ID�擾
//			for (int i = 0; i < UserInfoData.getId_gamen().size(); i++) {
//				if (UserInfoData.getId_gamen().get(i).toString().equals("20")){
//					//�������͏��}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
//					dataId = UserInfoData.getId_data().get(i).toString();
//				}
//			}

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  M401.cd_kaisha");
			strSql.append(" ,M401.cd_genryo");
			strSql.append(" ,M402.nm_genryo");
			strSql.append(" ,CASE WHEN M401.ritu_sakusan IS NULL THEN ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M401.ritu_sakusan)) END as ritu_sakusan");
			strSql.append(" ,CASE WHEN M401.ritu_shokuen IS NULL THEN ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M401.ritu_shokuen)) END as ritu_shokuen");
			strSql.append(" ,CASE WHEN M401.ritu_sousan IS NULL THEN ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M401.ritu_sousan)) END as ritu_sousan");
			strSql.append(" ,CASE WHEN M401.ritu_abura IS NULL THEN ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M401.ritu_abura)) END as ritu_abura");
			strSql.append(" ,ISNULL(M401.hyojian,'') as hyojian");
			strSql.append(" ,ISNULL(M401.tenkabutu,'') as tenkabutu");
			strSql.append(" ,ISNULL(M401.memo,'') as memo");
			strSql.append(" ,ISNULL(M401.no_eiyo1,'') as no_eiyo1");
			strSql.append(" ,ISNULL(M401.no_eiyo2,'') as no_eiyo2");
			strSql.append(" ,ISNULL(M401.no_eiyo3,'') as no_eiyo3");
			strSql.append(" ,ISNULL(M401.no_eiyo4,'') as no_eiyo4");
			strSql.append(" ,ISNULL(M401.no_eiyo5,'') as no_eiyo5");
			strSql.append(" ,ISNULL(M401.wariai1,'') as wariai1");
			strSql.append(" ,ISNULL(M401.wariai2,'') as wariai2");
			strSql.append(" ,ISNULL(M401.wariai3,'') as wariai3");
			strSql.append(" ,ISNULL(M401.wariai4,'') as wariai4");
			strSql.append(" ,ISNULL(M401.wariai5,'') as wariai5");
			strSql.append(" ,M401.kbn_haishi");
			strSql.append(" ,ISNULL(M401.cd_kakutei,'') as cd_kakutei");
			strSql.append(" ,ISNULL(CONVERT(VARCHAR,M401.id_kakunin),'') as id_kakunin");
			strSql.append(" ,ISNULL(M1012.nm_user,'') as nm_kakunin");
			strSql.append(" ,ISNULL(convert(varchar(10),M401.dt_kakunin,111),'') as dt_kakunin");
//			strSql.append(" ,M401.id_toroku");;
  			strSql.append(" ,M401.id_koshin");
//			strSql.append(" ,ISNULL(M1011.nm_user,'') as nm_toroku");
			strSql.append(" ,ISNULL(M1011.nm_user,'') as nm_koshin");
//			strSql.append(" ,convert(varchar(10),M401.dt_toroku,111) as dt_toroku");
			strSql.append(" ,convert(varchar(10),M401.dt_koshin,111) as dt_koshin");
// ADD start 20121005 QP@20505 No.24
			strSql.append(" ,CASE WHEN M401.ritu_msg IS NULL THEN ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(5,2),M401.ritu_msg)) END as ritu_msg");
// ADD end 20121005 QP@20505 No.24
// ADD start 20160610  KPX@1502111_No.5
			strSql.append(" ,M402.cd_busho");
// ADD end 20160610  KPX@1502111_No.5
			strSql.append(" FROM ma_genryokojo M402");
			strSql.append("      INNER JOIN ma_genryo M401");
			strSql.append("      ON M402.cd_kaisha = M401.cd_kaisha");
			strSql.append("      AND M402.cd_genryo = M401.cd_genryo");
			strSql.append("      LEFT JOIN ma_user M1011");
//			strSql.append("      ON M1011.id_user = M401.id_toroku");
			strSql.append("      ON M1011.id_user = M401.id_koshin");
			strSql.append("      LEFT JOIN ma_user M1012");
			strSql.append("      ON M1012.id_user = M401.id_kakunin");
			strSql.append(" WHERE M402.cd_kaisha = ");
			strSql.append(strKaishaCd);
			strSql.append(" AND M402.cd_busho IN (");
			strSql.append(strKojoCd);
			strSql.append("," + ConstManager.getConstValue(ConstManager.Category.�ݒ�, "SHINKIGENRYO_BUSHOCD") + ")");
			strSql.append(" AND M402.cd_genryo = '");
			strSql.append(strGenryoCd);
			strSql.append("'");

			//�������������ݒ�
//			//�S�����
//			if(dataId.equals("1")) {
//				strSql.append(" AND M402.cd_kaisha in ( ");
//				strSql.append(strSqlTanto + " ) ");
//
//			//���H�ꕪ
//			} else if (dataId.equals("2")) {
//				strSql.append(" AND M402.cd_busho = ");
//				strSql.append(UserInfoData.getCd_busho());
//			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * ���͒l���́F�������͏��p�����[�^�[�i�[
	 *  : �������͏������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenryouData2(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "cd_genryo", items[1].toString());
				resTable.addFieldVale(i, "nm_genryo", items[2].toString());
				resTable.addFieldVale(i, "ritu_sakusan", items[3].toString());
				resTable.addFieldVale(i, "ritu_shokuen", items[4].toString());
				resTable.addFieldVale(i, "ritu_sousan", items[5].toString());
				resTable.addFieldVale(i, "ritu_abura", items[6].toString());
				resTable.addFieldVale(i, "hyojian", items[7].toString());
				resTable.addFieldVale(i, "tenkabutu", items[8].toString());
				resTable.addFieldVale(i, "memo", items[9].toString());
				resTable.addFieldVale(i, "no_eiyo1", items[10].toString());
				resTable.addFieldVale(i, "no_eiyo2", items[11].toString());
				resTable.addFieldVale(i, "no_eiyo3", items[12].toString());
				resTable.addFieldVale(i, "no_eiyo4", items[13].toString());
				resTable.addFieldVale(i, "no_eiyo5", items[14].toString());
				resTable.addFieldVale(i, "wariai1", items[15].toString());
				resTable.addFieldVale(i, "wariai2", items[16].toString());
				resTable.addFieldVale(i, "wariai3", items[17].toString());
				resTable.addFieldVale(i, "wariai4", items[18].toString());
				resTable.addFieldVale(i, "wariai5", items[19].toString());
				resTable.addFieldVale(i, "kbn_haishi", items[20].toString());
				resTable.addFieldVale(i, "cd_kakutei", items[21].toString());
				resTable.addFieldVale(i, "id_kakunin", items[22].toString());
				resTable.addFieldVale(i, "nm_kakunin", items[23].toString());
				resTable.addFieldVale(i, "dt_kakunin", items[24].toString());
//				resTable.addFieldVale(i, "id_toroku", items[25].toString());
//				resTable.addFieldVale(i, "nm_toroku", items[26].toString());
//				resTable.addFieldVale(i, "dt_toroku", items[27].toString());
				resTable.addFieldVale(i, "id_koshin", items[25].toString());
				resTable.addFieldVale(i, "nm_koshin", items[26].toString());
				resTable.addFieldVale(i, "dt_koshin", items[27].toString());
// ADD start 20121005 QP@20505 No.24
				resTable.addFieldVale(i, "ritu_msg", items[28].toString());
// ADD end 20121005 QP@20505 No.24
// ADD start 20160610  KPX@1502111_No.5
				resTable.addFieldVale(i, "cd_busho", items[29].toString());
// ADD end 20160610  KPX@1502111_No.5
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}

	/**
	 * ���͒l���́F�������͏��p�����[�^�[�i�[
	 *  : �������͏������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenryouData2New(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			//���ʂ����X�|���X�f�[�^�Ɋi�[
			resTable.addFieldVale(0, "cd_kaisha", "");
			resTable.addFieldVale(0, "cd_genryo", "");
			resTable.addFieldVale(0, "nm_genryo", "");
			resTable.addFieldVale(0, "ritu_sakusan", "");
			resTable.addFieldVale(0, "ritu_shokuen", "");
			resTable.addFieldVale(0, "ritu_sousan", "");
			resTable.addFieldVale(0, "ritu_abura", "");
			resTable.addFieldVale(0, "hyojian", "");
			resTable.addFieldVale(0, "tenkabutu", "");
			resTable.addFieldVale(0, "memo", "");
			resTable.addFieldVale(0, "no_eiyo1", "");
			resTable.addFieldVale(0, "no_eiyo2", "");
			resTable.addFieldVale(0, "no_eiyo3", "");
			resTable.addFieldVale(0, "no_eiyo4", "");
			resTable.addFieldVale(0, "no_eiyo5", "");
			resTable.addFieldVale(0, "wariai1", "");
			resTable.addFieldVale(0, "wariai2", "");
			resTable.addFieldVale(0, "wariai3", "");
			resTable.addFieldVale(0, "wariai4", "");
			resTable.addFieldVale(0, "wariai5", "");
			resTable.addFieldVale(0, "kbn_haishi", "");
			resTable.addFieldVale(0, "cd_kakutei", "");
			resTable.addFieldVale(0, "id_kakunin", "");
			resTable.addFieldVale(0, "nm_kakunin", "");
			resTable.addFieldVale(0, "dt_kakunin", "");
//			resTable.addFieldVale(0, "id_toroku", "");
//			resTable.addFieldVale(0, "nm_toroku", "");
//			resTable.addFieldVale(0, "dt_toroku", "");
			resTable.addFieldVale(0, "id_koshin", "");
  			resTable.addFieldVale(0, "nm_koshin", "");
 			resTable.addFieldVale(0, "dt_koshin", "");
// ADD start 20121005 QP@20505 No.24
			resTable.addFieldVale(0, "ritu_msg", "");
// ADD end 20121005 QP@20505 No.24

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}

}
