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
 * �S���҃f�[�^�i�c�Ɓj�Ǘ������c�a�`�F�b�N �F�S���҃f�[�^�i�c�Ɓj�Ǘ��̃f�[�^�`�F�b�N���s���B
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2080_datacheck extends DataCheck {

	/**
	 * �S���҃f�[�^�i�c�Ɓj�Ǘ� �F �S���҃f�[�^�`�F�b�N�����p�R���X�g���N�^ : �C���X�^���X����
	 */
	public FGEN2080_datacheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���҃f�[�^�i�c�Ɓj�Ǘ� �F  �S���҃f�[�^�i�c�Ɓj�̃f�[�^�`�F�b�N���s���B
	 * 
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
			String strUserId = null;

			// �����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
			
			// ���[�UID�̎擾
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			
			//���[�UID�����͂���Ă���ꍇ
			if (!(strUserId.equals(""))) {
				
				// SQL���̍쐬
				strSql = tantoushaDataKanriExistenceCheckSQL(reqData, strSql);
	
				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
			}
			// �o�^�̏ꍇ
			if (strShotriKbn.equals("1")) {
				//���[�UID�����͂���Ă���ꍇ
				if (!strUserId.equals("")) {
					// �f�[�^�����݂���ꍇ
					if (lstRecset.size() != 0) {
						em.ThrowException(ExceptionKind.���Exception, "E000302", "���[�UID", strUserId.toString(), "");
					}
				}
				
			}
			// �X�V�E�폜�̏ꍇ
			else {
				// �f�[�^�����݂��Ȃ��ꍇ
				if (lstRecset.size() == 0) {
					em.ThrowException(ExceptionKind.���Exception, "E000301", "���[�UID", strUserId.toString(), "");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^�Ǘ��f�[�^�`�F�b�N�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchBD != null) {
				// �Z�b�V�����̃N���[�Y
				searchBD.Close();
				searchBD = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
		}
	}

	

	/**
	 * ���[�UID���݃`�F�b�NSQL�쐬 :���[�UID�̑��݃`�F�b�N������SQL���쐬�B
	 * 
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaDataKanriExistenceCheckSQL(RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strUserId = null;

			// ���[�UID�̎擾
			strUserId = requestData.getFieldVale(0, 0, "id_user");

			// SQL���̍쐬
			strSql.append("SELECT id_user");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
