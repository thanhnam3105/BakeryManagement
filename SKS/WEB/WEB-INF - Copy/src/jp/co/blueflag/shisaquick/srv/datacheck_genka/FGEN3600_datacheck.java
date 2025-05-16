package jp.co.blueflag.shisaquick.srv.datacheck_genka;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 *
 * ���e�����f�[�^�Ǘ��f�[�^�`�F�b�N���� : ���e�����f�[�^�Ǘ��̃f�[�^�`�F�b�N���s���B
 *
 * @author BRC Koizumi
 * @since 2016/10/6
 */
public class FGEN3600_datacheck extends DataCheck{

	/**
	 * ���e�����}�X�^�����e �F ���e�����f�[�^�Ǘ��̃`�F�b�N�����p�R���X�g���N�^ : �C���X�^���X����
	 */
	public FGEN3600_datacheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���e�����}�X�^�����e : ���e�����f�[�^���̃f�[�^�`�F�b�N���s���B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			String strShotriKbn = null;

			//�����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			// �J�e�S���R�[�h
			String strCategoryCd = reqData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = reqData.getFieldVale(0, 0, "cd_literal");
			// ��Q���e�����R�[�h
			String str2ndLiteralCd = reqData.getFieldVale(0, 0, "cd_2nd_literal");
			// ��Q���e������
			String str2ndLiteralNm = reqData.getFieldVale(0, 0, "nm_2nd_literal");

			// ���e�����R�[�h�����͂���Ă���
			if (!(strLiteralCd.equals(""))) {
				// ��Q���e�����������͂���Ă���ꍇ
				if (!(str2ndLiteralNm.equals(""))) {
					//SQL���̍쐬
					strSql = literalDataKanriExistenceCheckSQL2nd(reqData, strSql);

					//SQL�����s
					super.createSearchDB();
					lstRecset = searchBD.dbSearch(strSql.toString());
				}
			}

			//�V�K�o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				// ��Q���e�����������͂���Ă���ꍇ
				if (!(str2ndLiteralNm.equals(""))) {
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.���Exception,"E000304", "���[�J�[�� : " + strCategoryCd.toString(), "��ޖ� : " + str2ndLiteralNm.toString(), "");
//						em.ThrowException(ExceptionKind.���Exception,"E000304", "�J�e�S���R�[�h : " + strCategoryCd.toString(), "���e�����R�[�h : " + strLiteralCd.toString() + "��Q���e�����R�[�h : " + str2ndLiteralCd.toString(), "");
					}
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����f�[�^�Ǘ��f�[�^�`�F�b�N�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchBD != null) {
				//�Z�b�V�����̃N���[�Y
				searchBD.Close();
				searchBD = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
	}

	/**
	 * ���e�����R�[�h���݃`�F�b�NSQL�쐬
	 *  : ���e�����̑��݃`�F�b�N������SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalDataKanriExistenceCheckSQL2nd(RequestResponsKindBean requestData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// ��Q���e������
			String str2ndLiteralNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");

			//SQL���̍쐬
			strSql.append("SELECT cd_category");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			strSql.append(" AND nm_2nd_literal = '");
			strSql.append(str2ndLiteralNm+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}