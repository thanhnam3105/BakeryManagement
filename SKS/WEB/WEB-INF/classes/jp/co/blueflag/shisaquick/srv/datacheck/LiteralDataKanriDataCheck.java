package jp.co.blueflag.shisaquick.srv.datacheck;

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
 * @author itou
 * @since 2009/04/20
 */
public class LiteralDataKanriDataCheck extends DataCheck{
	
	/**
	 * ���e�����}�X�^�����e �F ���e�����f�[�^�Ǘ��̃`�F�b�N�����p�R���X�g���N�^ : �C���X�^���X����
	 */
	public LiteralDataKanriDataCheck() {
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
			if (!(strLiteralCd.equals(""))) {
				//SQL���̍쐬
				strSql = literalDataKanriExistenceCheckSQL(reqData, strSql);
				
				//SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
			}
			//�o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				//�`�[���R�[�h�����͂���Ă���ꍇ
				if (!(strLiteralCd.equals(""))) {
					//�f�[�^�����݂���ꍇ
					if (lstRecset.size() != 0){
						em.ThrowException(ExceptionKind.���Exception,"E000304", "�J�e�S���R�[�h : " + strCategoryCd.toString(), "���e�����R�[�h : " + strLiteralCd.toString(), "");
					}
				}
			//�X�V�E�폜�̏ꍇ
			} else {
				//�f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0){
					em.ThrowException(ExceptionKind.���Exception,"E000305","�J�e�S���R�[�h : " + strCategoryCd.toString(), "���e�����R�[�h : " + strLiteralCd.toString(), "");
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
	private StringBuffer literalDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");

			//SQL���̍쐬
			strSql.append("SELECT cd_category");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
