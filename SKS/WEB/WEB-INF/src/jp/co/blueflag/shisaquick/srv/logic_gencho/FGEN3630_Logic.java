package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���e�����}�X�^�����e�F�����ޒ��B���p ������ꗗ��񌟍�DB����
 *
 *
 */
public class FGEN3630_Logic extends LogicBase {

	/**
	 * ���e�����}�X�^�����e�F������ꗗ��񌟍�DB����
	 *
	 */
	public FGEN3630_Logic() {
		// ���N���X�̃R���X�g���N�^
		super();
	}

	/**
	 * ���e�����}�X�^�����e�F������ꗗ���擾SQL�쐬
	 *
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
	 * @param userInfoData
	 *            : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean requestData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();

		// ���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		// ���הԍ�
		String meisaiNum = "";

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�g�����U�N�V�����J�n
			super.createExecDB();
			//execDB.BeginTran();
			for (int i = 0; i < requestData.getCntRow(requestData.getTableID(0)); i++) {

				String shoriKbn = requestData.getFieldVale(0, i, "shoriKbn");
				execDB.BeginTran();

				// �o�^����
				if (shoriKbn.equals("1")) {

					strSql = literalKanriInsertSQL(requestData, i);
				// �X�V����
				} else if (shoriKbn.equals("2")) {
					meisaiNum = requestData.getFieldVale(0, i, "meisaiNum");
					strSql = literalKanriUpdateSQL(requestData, i);
				// �폜����
				} else if  (shoriKbn.equals("3")) {

					strSql = literalKanriDeleteSQL(requestData, i);
				}

				//SQL�����s
				execDB.execUpdateSQL(strSql.toString());
				//�R�~�b�g
				execDB.Commit();
			}


			//execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageLiteralDataKanri(resKind.getTableItem(0), 0, meisaiNum);

		} catch (ConstraintViolationException e) {

			if (execDB != null) {
				//���[���o�b�N
				execDB.Rollback();
			}

			//�@�\ID�̐ݒ�
			String strKinoId = requestData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = requestData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���הԍ����擾
			//requestData.getFieldVale(TableNo, RowNo, itemNo)

			//���X�|���X�f�[�^�̌`��
			storageLiteralDataKanri(resKind.getTableItem(0), 1, meisaiNum);

		} catch (Exception e) {

			if (execDB != null) {
				//���[���o�b�N
				execDB.Rollback();
			}
			e.printStackTrace();
		} finally {

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}


	/**
	 * ���e�����f�[�^�o�^SQL�쐬
	 *  : ���e�����f�[�^�o�^�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// �o�^�p
		StringBuffer strSql = new StringBuffer();
		try {

			// ������R�[�h
			String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
			String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);
			// �����於
			String strLiteralNm = requestData.getFieldVale(0, row, "nm_literal");
			// �����於�\���l
			String strSortNo = requestData.getFieldVale(0, row, "no_sort");
			// ���l
			// String strBiko = requestData.getFieldVale(0, 0, "biko");
			// �ҏW��
			short flgEdit = 0;
			// ��񃊃e�����R�[�h
			String strLiteral2ndCd = "";
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, row, "nm_2nd_literal");
			// ��񃊃e�����g�p�t���O�l

			// ��Q���e�����\���l
			String str2ndSortNo = requestData.getFieldVale(0, row, "no_2nd_sort");
			// ���[���A�h���X
			String strMailAddress = requestData.getFieldVale(0, row, "mail_address");
			// ���g�p
			String strFlgMishiyo = requestData.getFieldVale(0, row, "flg_mishiyo");

			String strCategoryCd = "C_hattyuusaki";

			// ���e�����f�[�^�o�^SQL�쐬
			strSql.append("INSERT INTO ma_literal (");
			strSql.append(" cd_category");
			strSql.append(" ,cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,value1");
			strSql.append(" ,value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,cd_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ) VALUES ( '");
			strSql.append("C_hattyuusaki'");// �J�e�S��CD

			// �̔ԏ���
			String strNoSeq = "";
			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			// try {
			// �g�����U�N�V�����J�n
			// super.createSearchDB();
			// searchDB.BeginTran();

			/*** ��Q���e�����R�[�h�̎擾 start **************************************/
			// �̔ԏ���
			int intNoSeq = 0;

			// ���e�����R�[�h�̍̔�SQL�쐬
			strSelSql.append("SELECT MAX(CONVERT(int, cd_2nd_literal)) + 1 AS cd_2nd_literal");
			strSelSql.append(",cd_literal");
			strSelSql.append(" FROM ma_literal ");
			strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
			strSelSql.append(" AND cd_literal = '" + strLiteralCdFormatZero + "'");
			strSelSql.append("GROUP BY cd_literal");
			strSelSql.append(" ORDER BY cd_2nd_literal desc ");
			// SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSelSql.toString());

			if (lstRecset.size() > 0) {

				Object[] items = (Object[]) lstRecset.get(0);
				intNoSeq = Integer.parseInt(items[0].toString());

				strLiteral2ndCd = String.format("%06d", intNoSeq);
			} else {
				strLiteral2ndCd = "000001";
			}

			strSql.append(" ,'" + strLiteralCdFormatZero + "'"); // ������R�[�h�i���e����CD�j
			/*** ��Q���e�����R�[�h�̎擾 end **************************************/


			/*** ���e�������ȉ� **************************************/
			strSql.append(" ,'" + strLiteralNm + "'"); // �����於
			strSql.append(" ,NULL"); // val1���e�����l1
			strSql.append(" ,NULL"); // val2 ���e�����l2
			strSql.append(" ," + strSortNo); // �\����
			strSql.append(" ,NULL");// ���l

			strSql.append(" ," + flgEdit); // �ҏW�t���O
			strSql.append(" ,NULL"); // �O���[�vCD
			strSql.append(" ," + userInfoData.getId_user()); // �o�^��ID
			strSql.append(" ,GETDATE()"); // �o�^���t
			strSql.append(" ," + userInfoData.getId_user()); // �X�VID
			strSql.append(" ,GETDATE()"); // �X�V���t
			strSql.append(" ,'" + strLiteral2ndCd + "'");// ��Q���e�����R�[�h
			strSql.append(" ,'" + strLiteral2ndNm + "'"); // ��Q���e������
			strSql.append(" ,'" + str2ndSortNo + "'"); // ��Q���e�����\����
			strSql.append(" ,1"); // ��Q���e�����g�p�t���O
			// ���[���A�h���X
			strSql.append(",'" + strMailAddress + "'");
			// ���g�p
			strSql.append(",'" + strFlgMishiyo + "'");

			strSql.append(")");

		}  catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strSql;
	}

	/**
	 * ���e�����f�[�^�o�^SQL�쐬
	 *  : ���e�����f�[�^�o�^�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriUpdateSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// �o�^�p
		StringBuffer strSql = new StringBuffer();

		try {

			// ������R�[�h
			String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
			String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);

			// �ҏW����O�̔�����R�[�h
			String strBeforeLiteralCd = requestData.getFieldVale(0, row, "hiddenCd_literal");
			// �����於
			String strLiteralNm = requestData.getFieldVale(0, row, "nm_literal");
			// �����於�\���l
			String strSortNo = requestData.getFieldVale(0, row, "no_sort");
			// ��񃊃e������
			String strLiteral2ndCd = requestData.getFieldVale(0, row, "cd_2nd_literal");
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, row, "nm_2nd_literal");

			// ��Q���e�����\���l
			String str2ndSortNo = requestData.getFieldVale(0, row, "no_2nd_sort");
			// ���[���A�h���X
			String strMailAddress = requestData.getFieldVale(0, row, "mail_address");
			// ���g�p
			String strFlgMishiyo = requestData.getFieldVale(0, row, "flg_mishiyo");

			String updateCd2ndLiteral = "";

			String strCategoryCd = "C_hattyuusaki";
			List<?> lstRecset = null;


//			//----------�d���`�F�b�N------------
//			StringBuffer strSql2 = new StringBuffer();
//			strSql2 = new StringBuffer();
//            // SQL���̍쐬
//            strSql2.append(" SELECT cd_literal  ");
//            strSql2.append(" FROM  ma_literal  ");
//            strSql2.append(" WHERE cd_category = '"+ strCategoryCd );
//            strSql2.append("'");
//            strSql2.append("   AND cd_literal = '"     + strLiteralCd );
//            strSql2.append("'");
//            strSql2.append(" AND cd_2nd_literal = '"   + strLiteral2ndCd );
//            strSql2.append("'");
//            super.createSearchDB();
//            List<?> lstRecset2 = searchDB.dbSearch(strSql2.toString());
//
//            // �X�V�f�[�^�����݂��Ȃ����A�G���[
//            if(lstRecset2.size() >= 1) {
//            	em.ThrowException(ExceptionKind.���Exception,"E000301","","","");
//            }

			//SQL���̍쐬
			strSql.append("UPDATE ma_literal SET");
			strSql.append("  cd_literal = '");
			strSql.append(strLiteralCdFormatZero + "'");// ������R�[�h
			strSql.append("  ,nm_literal = '");
			strSql.append(strLiteralNm + "'");// �����於
			strSql.append(" ,no_sort = ");
			strSql.append(strSortNo);		 // ������\����

//			strSql.append(" ,cd_2nd_literal = ");
//
//			/*** ��Q���e�����R�[�h�̎擾 start **************************************/
//			// �̔ԏ���
//			int intNoSeq = 0;
//			StringBuffer strSelSql = new StringBuffer();
//			// ���e�����R�[�h�̍̔�SQL�쐬
//			strSelSql.append("SELECT MAX(cd_2nd_literal) AS cd_2nd_literal");
//			strSelSql.append(",cd_literal");
//			strSelSql.append(" FROM ma_literal ");
//			strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
//			strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
//			strSelSql.append("GROUP BY cd_literal");
//			strSelSql.append(" ORDER BY cd_2nd_literal desc ");
//			// SQL�����s
//			super.createSearchDB();
//			lstRecset = searchDB.dbSearch(strSelSql.toString());
//
//			if (lstRecset.size() > 0) {
//				// �̔Ԍ��ʂ�ޔ�
//				Object items = (Object) lstRecset.get(0);
//				if (items.toString().equals("")) {
//					intNoSeq = 0;
//				} else {
//					intNoSeq = Integer.parseInt(items.toString()); // 001 ��
//																	// 1
//				}
//				updateCd2ndLiteral = String.valueOf(intNoSeq + 1); // �{1���ĕ������
//			} else {
//				updateCd2ndLiteral = "1";
//			}
//
//			strSql.append(" '00" + updateCd2ndLiteral + "'"); // ������R�[�h�i���e����CD�j
//			/*** ��Q���e�����R�[�h�̎擾 end **************************************/


			strSql.append(" ,nm_2nd_literal = '");
			strSql.append(strLiteral2ndNm + "'");	// ��񃊃e����
			strSql.append(" ,no_2nd_sort = '");
			strSql.append(str2ndSortNo + "'");	// ��񃊃e�����\����
			strSql.append(" ,mail_address = '");
			strSql.append(strMailAddress + "'");	// ���[���A�h���X
			strSql.append(" ,flg_mishiyo = ");
			strSql.append(strFlgMishiyo);	// ���g�p
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strBeforeLiteralCd+ "'");
			strSql.append(" AND cd_2nd_literal = '");
			strSql.append(strLiteral2ndCd+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}


	/**
	 * ���e�����f�[�^�o�^SQL�쐬
	 *  : ���e�����f�[�^�o�^�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData, int row)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// �o�^�p
		StringBuffer strSql = new StringBuffer();

		// ������R�[�h
		String strLiteralCd = requestData.getFieldVale(0, row, "cd_literal");
		String strLiteralCdFormatZero = get0SupressCode(strLiteralCd);

		// ��񃊃e������
		String strLiteral2ndCd = requestData.getFieldVale(0, row, "cd_2nd_literal");


		strSql.append("DELETE");
		strSql.append(" FROM ma_literal");
		strSql.append(" WHERE cd_category = '");
		strSql.append("C_hattyuusaki'");
		strSql.append(" AND cd_literal = '");
		strSql.append(strLiteralCdFormatZero);
		strSql.append("'");
		strSql.append(" AND cd_2nd_literal = '");
		strSql.append(strLiteral2ndCd);
		strSql.append("'");

		return strSql;
	}
	/**
	 * 0�T�v���X����
	 * @param strCdShohin
	 * @return
	 */
	private String  get0SupressCode(String strCdShohin) {
		int intCdShohin = 0;
		if (!strCdShohin.equals("")) {
			 intCdShohin = Integer.parseInt(strCdShohin);
		}
		return String.valueOf(intCdShohin);
	}
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ���e�����f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable, int num, String meisaiNum)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			if (num == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "updateErrorFlg_return", "false");
				resTable.addFieldVale(0, "meisaiNum", meisaiNum);
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			} else {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "updateErrorFlg_return", "true");
				resTable.addFieldVale(0, "meisaiNum", meisaiNum);
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
