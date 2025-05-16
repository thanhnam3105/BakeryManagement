package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

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
public class FGEN3650_Logic extends LogicBase {

	/**
	 * ���e�����}�X�^�����e�F������ꗗ��񌟍�DB����
	 *
	 */
	public FGEN3650_Logic() {
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
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData, UserInfoData _userInfoData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		StringBuffer strSql = new StringBuffer();

		// ���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		// �e�[�u����
		String strTblNm = "table";
		try {

			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String cd_kaisha = userInfoData.getCd_kaisha();

			strSql = literalKanriSelectSQL(reqData, cd_kaisha);

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			String strXmlID = reqData.getID();
//			//�������ʂ��Ȃ��ꍇ
//			if (lstRecset.size() == 0){
//				em.ThrowException(ExceptionKind.�x��Exception, "W000408", "�������ʂɊY������f�[�^���L��܂���B", "", "");
//
//			}
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);
			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(0));


		} catch (Exception e) {
			em.ThrowException(e, "���ރe�[�u���������������s���܂����B");
		} finally {

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			// �ϐ��̍폜

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
	private StringBuffer literalKanriSelectSQL(RequestResponsKindBean requestData, String cd_kaisha)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		// ���e�����e�[�u��SQL
		StringBuffer strSql = new StringBuffer();

		String strCdLiteral = requestData.getFieldVale(0, 0, "cd_literal");
		//String StrCdLiteral = strFormat(beforeStrCdLiteral);
		//int cdLiteral = Integer.parseInt(strCdLiteral);

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append(" name_tenmei_kaisha");
			strSql.append(" FROM ma_temmei");
			strSql.append(" WHERE cd_kaisha = '");
			strSql.append(cd_kaisha);
			strSql.append("'");
			strSql.append(" AND cd_temmei = ");
			strSql.append(strCdLiteral);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

//	// �S�p�𔼊p�ɂ���
//	private String strFormat(String str) {
//		char[] chars = str.toCharArray();
//		StringBuilder sb = new StringBuilder(str);
//		for (int i = 0; i < chars.length; i++) {
//			if (String.valueOf(chars[i]).getBytes().length >= 2) {
//				int c = (int) sb.charAt(i);
//				if (c >= 0xFF10 && c <= 0xFF19) {
//					sb.setCharAt(i, (char) (c - 0xFEE0));
//				}
//			}
//		}
//		return  sb.toString();
//	}
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ���e�����f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(List<?> lstMstData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			// ���j���[����̃��[�h����lstMstData��null�̂���
			if (lstMstData == null|| lstMstData.size() == 0) {

				// �f�[�^���擾�ł��Ȃ����F�G���[�ɂ��Ȃ��ijs�̃��b�Z�[�W��Ԃ������j
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "�������ʂɊY������f�[�^���L��܂���B");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
			} else {

				for (int i = 0; i < lstMstData.size(); i++) {
					//�������ʂ̊i�[
					resTable.addFieldVale(0, "flg_return", "true");
					resTable.addFieldVale(0, "msg_error", "");
					resTable.addFieldVale(0, "no_errmsg", "");
					resTable.addFieldVale(0, "nm_class", "");
					resTable.addFieldVale(0, "cd_error", "");
					resTable.addFieldVale(0, "msg_system", "");

					Object items = (Object) lstMstData.get(i);

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale(i, "nm_literal", items.toString());
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
}
